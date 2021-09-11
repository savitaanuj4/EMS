
package com.mysql.cj;

import com.mysql.cj.protocol.Message;
import com.mysql.cj.exceptions.WrongArgumentException;
import java.io.Reader;
import java.sql.Blob;
import java.io.InputStream;
import com.mysql.cj.exceptions.CJException;
import com.mysql.cj.log.ProfilerEventHandlerFactory;
import com.mysql.cj.exceptions.ExceptionFactory;
import com.mysql.cj.protocol.Resultset;
import java.io.IOException;
import com.mysql.cj.protocol.a.NativePacketPayload;
import com.mysql.cj.protocol.ProtocolEntityFactory;
import com.mysql.cj.protocol.a.ColumnDefinitionFactory;
import com.mysql.cj.log.ProfilerEvent;
import com.mysql.cj.log.ProfilerEventImpl;
import com.mysql.cj.util.LogUtils;
import com.mysql.cj.protocol.a.NativeConstants;
import com.mysql.cj.util.StringUtils;
import com.mysql.cj.conf.PropertyKey;
import com.mysql.cj.protocol.a.NativeMessageBuilder;
import com.mysql.cj.conf.RuntimeProperty;
import com.mysql.cj.protocol.ColumnDefinition;
import com.mysql.cj.result.Field;

public class ServerPreparedQuery extends AbstractPreparedQuery<ServerPreparedQueryBindings>
{
    public static final int BLOB_STREAM_READ_BUF_SIZE = 8192;
    public static final byte OPEN_CURSOR_FLAG = 1;
    private long serverStatementId;
    private Field[] parameterFields;
    private ColumnDefinition resultFields;
    protected RuntimeProperty<Boolean> gatherPerfMetrics;
    protected boolean logSlowQueries;
    private boolean useAutoSlowLog;
    protected RuntimeProperty<Integer> slowQueryThresholdMillis;
    protected RuntimeProperty<Boolean> explainSlowQueries;
    protected boolean queryWasSlow;
    protected NativeMessageBuilder commandBuilder;
    
    public static ServerPreparedQuery getInstance(final NativeSession sess) {
        if (sess.getPropertySet().getBooleanProperty(PropertyKey.autoGenerateTestcaseScript).getValue()) {
            return new ServerPreparedQueryTestcaseGenerator(sess);
        }
        return new ServerPreparedQuery(sess);
    }
    
    protected ServerPreparedQuery(final NativeSession sess) {
        super(sess);
        this.logSlowQueries = false;
        this.queryWasSlow = false;
        this.commandBuilder = new NativeMessageBuilder();
        this.gatherPerfMetrics = sess.getPropertySet().getBooleanProperty(PropertyKey.gatherPerfMetrics);
        this.logSlowQueries = sess.getPropertySet().getBooleanProperty(PropertyKey.logSlowQueries).getValue();
        this.useAutoSlowLog = sess.getPropertySet().getBooleanProperty(PropertyKey.autoSlowLog).getValue();
        this.slowQueryThresholdMillis = sess.getPropertySet().getIntegerProperty(PropertyKey.slowQueryThresholdMillis);
        this.explainSlowQueries = sess.getPropertySet().getBooleanProperty(PropertyKey.explainSlowQueries);
    }
    
    public void serverPrepare(final String sql) throws IOException {
        this.session.checkClosed();
        synchronized (this.session) {
            long begin = 0L;
            if (this.profileSQL) {
                begin = System.currentTimeMillis();
            }
            final boolean loadDataQuery = StringUtils.startsWithIgnoreCaseAndWs(sql, "LOAD DATA");
            String characterEncoding = null;
            final String connectionEncoding = this.session.getPropertySet().getStringProperty(PropertyKey.characterEncoding).getValue();
            if (!loadDataQuery && connectionEncoding != null) {
                characterEncoding = connectionEncoding;
            }
            final NativePacketPayload prepareResultPacket = this.session.sendCommand(this.commandBuilder.buildComStmtPrepare(this.session.getSharedSendPacket(), sql, characterEncoding), false, 0);
            prepareResultPacket.setPosition(1);
            this.serverStatementId = prepareResultPacket.readInteger(NativeConstants.IntegerDataType.INT4);
            final int fieldCount = (int)prepareResultPacket.readInteger(NativeConstants.IntegerDataType.INT2);
            this.setParameterCount((int)prepareResultPacket.readInteger(NativeConstants.IntegerDataType.INT2));
            this.queryBindings = (T)new ServerPreparedQueryBindings(this.parameterCount, this.session);
            ((ServerPreparedQueryBindings)this.queryBindings).setLoadDataQuery(loadDataQuery);
            this.session.incrementNumberOfPrepares();
            if (this.profileSQL) {
                this.eventSink.consumeEvent(new ProfilerEventImpl((byte)2, "", this.getCurrentCatalog(), this.session.getThreadId(), this.statementId, -1, System.currentTimeMillis(), this.session.getCurrentTimeNanosOrMillis() - begin, this.session.getQueryTimingUnits(), null, LogUtils.findCallingClassAndMethod(new Throwable()), this.truncateQueryToLog(sql)));
            }
            final boolean checkEOF = !this.session.getServerSession().isEOFDeprecated();
            if (this.parameterCount > 0) {
                if (checkEOF) {
                    this.session.getProtocol().skipPacket();
                }
                this.parameterFields = this.session.getProtocol().read(ColumnDefinition.class, new ColumnDefinitionFactory(this.parameterCount, null)).getFields();
            }
            if (fieldCount > 0) {
                this.resultFields = this.session.getProtocol().read(ColumnDefinition.class, new ColumnDefinitionFactory(fieldCount, null));
            }
        }
    }
    
    @Override
    public void statementBegins() {
        super.statementBegins();
        this.queryWasSlow = false;
    }
    
    public <T extends Resultset> T serverExecute(final int maxRowsToRetrieve, final boolean createStreamingResultSet, final ColumnDefinition metadata, final ProtocolEntityFactory<T, NativePacketPayload> resultSetFactory) {
        if (this.session.shouldIntercept()) {
            final T interceptedResults = this.session.invokeQueryInterceptorsPre(() -> this.getOriginalSql(), this, true);
            if (interceptedResults != null) {
                return interceptedResults;
            }
        }
        String queryAsString = "";
        if (this.profileSQL || this.logSlowQueries || this.gatherPerfMetrics.getValue()) {
            queryAsString = this.asSql(true);
        }
        final NativePacketPayload packet = this.prepareExecutePacket();
        final NativePacketPayload resPacket = this.sendExecutePacket(packet, queryAsString);
        final T rs = this.readExecuteResult(resPacket, maxRowsToRetrieve, createStreamingResultSet, metadata, resultSetFactory, queryAsString);
        return rs;
    }
    
    public NativePacketPayload prepareExecutePacket() {
        final ServerPreparedQueryBindValue[] parameterBindings = ((ServerPreparedQueryBindings)this.queryBindings).getBindValues();
        if (((ServerPreparedQueryBindings)this.queryBindings).isLongParameterSwitchDetected()) {
            boolean firstFound = false;
            long boundTimeToCheck = 0L;
            for (int i = 0; i < this.parameterCount - 1; ++i) {
                if (parameterBindings[i].isStream()) {
                    if (firstFound && boundTimeToCheck != parameterBindings[i].boundBeforeExecutionNum) {
                        throw ExceptionFactory.createException(Messages.getString("ServerPreparedStatement.11") + Messages.getString("ServerPreparedStatement.12"), "S1C00", 0, true, null, this.session.getExceptionInterceptor());
                    }
                    firstFound = true;
                    boundTimeToCheck = parameterBindings[i].boundBeforeExecutionNum;
                }
            }
            this.serverResetStatement();
        }
        ((ServerPreparedQueryBindings)this.queryBindings).checkAllParametersSet();
        for (int j = 0; j < this.parameterCount; ++j) {
            if (parameterBindings[j].isStream()) {
                this.serverLongData(j, parameterBindings[j]);
            }
        }
        final NativePacketPayload packet = this.session.getSharedSendPacket();
        packet.writeInteger(NativeConstants.IntegerDataType.INT1, 23L);
        packet.writeInteger(NativeConstants.IntegerDataType.INT4, this.serverStatementId);
        if (this.resultFields != null && this.resultFields.getFields() != null && this.useCursorFetch && this.resultSetType == Resultset.Type.FORWARD_ONLY && this.fetchSize > 0) {
            packet.writeInteger(NativeConstants.IntegerDataType.INT1, 1L);
        }
        else {
            packet.writeInteger(NativeConstants.IntegerDataType.INT1, 0L);
        }
        packet.writeInteger(NativeConstants.IntegerDataType.INT4, 1L);
        final int nullCount = (this.parameterCount + 7) / 8;
        final int nullBitsPosition = packet.getPosition();
        for (int i = 0; i < nullCount; ++i) {
            packet.writeInteger(NativeConstants.IntegerDataType.INT1, 0L);
        }
        final byte[] nullBitsBuffer = new byte[nullCount];
        packet.writeInteger(NativeConstants.IntegerDataType.INT1, ((ServerPreparedQueryBindings)this.queryBindings).getSendTypesToServer().get() ? 1 : 0);
        if (((ServerPreparedQueryBindings)this.queryBindings).getSendTypesToServer().get()) {
            for (int k = 0; k < this.parameterCount; ++k) {
                packet.writeInteger(NativeConstants.IntegerDataType.INT2, parameterBindings[k].bufferType);
            }
        }
        for (int k = 0; k < this.parameterCount; ++k) {
            if (!parameterBindings[k].isStream()) {
                if (!parameterBindings[k].isNull()) {
                    parameterBindings[k].storeBinding(packet, ((ServerPreparedQueryBindings)this.queryBindings).isLoadDataQuery(), this.charEncoding, this.session.getExceptionInterceptor());
                }
                else {
                    final byte[] array = nullBitsBuffer;
                    final int n = k / 8;
                    array[n] |= (byte)(1 << (k & 0x7));
                }
            }
        }
        final int endPosition = packet.getPosition();
        packet.setPosition(nullBitsPosition);
        packet.writeBytes(NativeConstants.StringLengthDataType.STRING_FIXED, nullBitsBuffer);
        packet.setPosition(endPosition);
        return packet;
    }
    
    public NativePacketPayload sendExecutePacket(final NativePacketPayload packet, final String queryAsString) {
        long begin = 0L;
        final boolean gatherPerformanceMetrics = this.gatherPerfMetrics.getValue();
        if (this.profileSQL || this.logSlowQueries || gatherPerformanceMetrics) {
            begin = this.session.getCurrentTimeNanosOrMillis();
        }
        this.resetCancelledState();
        CancelQueryTask timeoutTask = null;
        try {
            timeoutTask = this.startQueryTimer(this, this.timeoutInMillis);
            this.statementBegins();
            final NativePacketPayload resultPacket = this.session.sendCommand(packet, false, 0);
            long queryEndTime = 0L;
            if (this.logSlowQueries || gatherPerformanceMetrics || this.profileSQL) {
                queryEndTime = this.session.getCurrentTimeNanosOrMillis();
            }
            if (timeoutTask != null) {
                this.stopQueryTimer(timeoutTask, true, true);
                timeoutTask = null;
            }
            if (this.logSlowQueries || gatherPerformanceMetrics) {
                final long elapsedTime = queryEndTime - begin;
                if (this.logSlowQueries) {
                    if (this.useAutoSlowLog) {
                        this.queryWasSlow = (elapsedTime > this.slowQueryThresholdMillis.getValue());
                    }
                    else {
                        this.queryWasSlow = this.session.getProtocol().getMetricsHolder().isAbonormallyLongQuery(elapsedTime);
                        this.session.getProtocol().getMetricsHolder().reportQueryTime(elapsedTime);
                    }
                }
                if (this.queryWasSlow) {
                    final StringBuilder mesgBuf = new StringBuilder(48 + this.originalSql.length());
                    mesgBuf.append(Messages.getString("ServerPreparedStatement.15"));
                    mesgBuf.append(this.session.getSlowQueryThreshold());
                    mesgBuf.append(Messages.getString("ServerPreparedStatement.15a"));
                    mesgBuf.append(elapsedTime);
                    mesgBuf.append(Messages.getString("ServerPreparedStatement.16"));
                    mesgBuf.append("as prepared: ");
                    mesgBuf.append(this.originalSql);
                    mesgBuf.append("\n\n with parameters bound:\n\n");
                    mesgBuf.append(queryAsString);
                    this.eventSink.consumeEvent(new ProfilerEventImpl((byte)6, "", this.getCurrentCatalog(), this.session.getThreadId(), this.getId(), 0, System.currentTimeMillis(), elapsedTime, this.session.getQueryTimingUnits(), null, LogUtils.findCallingClassAndMethod(new Throwable()), mesgBuf.toString()));
                }
                if (gatherPerformanceMetrics) {
                    this.session.registerQueryExecutionTime(elapsedTime);
                }
            }
            this.session.incrementNumberOfPreparedExecutes();
            if (this.profileSQL) {
                this.setEventSink(ProfilerEventHandlerFactory.getInstance(this.session));
                this.eventSink.consumeEvent(new ProfilerEventImpl((byte)4, "", this.getCurrentCatalog(), this.session.getThreadId(), this.statementId, -1, System.currentTimeMillis(), this.session.getCurrentTimeNanosOrMillis() - begin, this.session.getQueryTimingUnits(), null, LogUtils.findCallingClassAndMethod(new Throwable()), this.truncateQueryToLog(queryAsString)));
            }
            return resultPacket;
        }
        catch (CJException sqlEx) {
            if (this.session.shouldIntercept()) {
                this.session.invokeQueryInterceptorsPost(() -> this.getOriginalSql(), this, (Resultset)null, true);
            }
            throw sqlEx;
        }
        finally {
            this.statementExecuting.set(false);
            this.stopQueryTimer(timeoutTask, false, false);
        }
    }
    
    public <T extends Resultset> T readExecuteResult(final NativePacketPayload resultPacket, final int maxRowsToRetrieve, final boolean createStreamingResultSet, final ColumnDefinition metadata, final ProtocolEntityFactory<T, NativePacketPayload> resultSetFactory, final String queryAsString) {
        try {
            long fetchStartTime = 0L;
            if (this.profileSQL || this.logSlowQueries || this.gatherPerfMetrics.getValue()) {
                fetchStartTime = this.session.getCurrentTimeNanosOrMillis();
            }
            T rs = this.session.getProtocol().readAllResults(maxRowsToRetrieve, createStreamingResultSet, resultPacket, true, (metadata != null) ? metadata : this.resultFields, resultSetFactory);
            if (this.session.shouldIntercept()) {
                final T interceptedResults = this.session.invokeQueryInterceptorsPost(() -> this.getOriginalSql(), this, rs, true);
                if (interceptedResults != null) {
                    rs = interceptedResults;
                }
            }
            if (this.profileSQL) {
                final long fetchEndTime = this.session.getCurrentTimeNanosOrMillis();
                this.eventSink.consumeEvent(new ProfilerEventImpl((byte)5, "", this.getCurrentCatalog(), this.session.getThreadId(), this.getId(), rs.getResultId(), System.currentTimeMillis(), fetchEndTime - fetchStartTime, this.session.getQueryTimingUnits(), null, LogUtils.findCallingClassAndMethod(new Throwable()), null));
            }
            if (this.queryWasSlow && this.explainSlowQueries.getValue()) {
                this.session.getProtocol().explainSlowQuery(queryAsString, queryAsString);
            }
            ((ServerPreparedQueryBindings)this.queryBindings).getSendTypesToServer().set(false);
            if (this.session.hadWarnings()) {
                this.session.getProtocol().scanForAndThrowDataTruncation();
            }
            return rs;
        }
        catch (IOException ioEx) {
            throw ExceptionFactory.createCommunicationsException(this.session.getPropertySet(), this.session.getServerSession(), this.session.getProtocol().getPacketSentTimeHolder(), this.session.getProtocol().getPacketReceivedTimeHolder(), ioEx, this.session.getExceptionInterceptor());
        }
        catch (CJException sqlEx) {
            if (this.session.shouldIntercept()) {
                this.session.invokeQueryInterceptorsPost(() -> this.getOriginalSql(), this, (Resultset)null, true);
            }
            throw sqlEx;
        }
    }
    
    private void serverLongData(final int parameterIndex, final ServerPreparedQueryBindValue longData) {
        synchronized (this) {
            final NativePacketPayload packet = this.session.getSharedSendPacket();
            final Object value = longData.value;
            if (value instanceof byte[]) {
                this.session.sendCommand(this.commandBuilder.buildComStmtSendLongData(packet, this.serverStatementId, parameterIndex, (byte[])value), true, 0);
            }
            else if (value instanceof InputStream) {
                this.storeStream(parameterIndex, packet, (InputStream)value);
            }
            else {
                if (value instanceof Blob) {
                    try {
                        this.storeStream(parameterIndex, packet, ((Blob)value).getBinaryStream());
                        return;
                    }
                    catch (Throwable t) {
                        throw ExceptionFactory.createException(t.getMessage(), this.session.getExceptionInterceptor());
                    }
                }
                if (!(value instanceof Reader)) {
                    throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("ServerPreparedStatement.18") + value.getClass().getName() + "'", this.session.getExceptionInterceptor());
                }
                this.storeReader(parameterIndex, packet, (Reader)value);
            }
        }
    }
    
    @Override
    public void closeQuery() {
        this.queryBindings = null;
        this.parameterFields = null;
        this.resultFields = null;
        super.closeQuery();
    }
    
    public long getServerStatementId() {
        return this.serverStatementId;
    }
    
    public void setServerStatementId(final long serverStatementId) {
        this.serverStatementId = serverStatementId;
    }
    
    public Field[] getParameterFields() {
        return this.parameterFields;
    }
    
    public void setParameterFields(final Field[] parameterFields) {
        this.parameterFields = parameterFields;
    }
    
    public ColumnDefinition getResultFields() {
        return this.resultFields;
    }
    
    public void setResultFields(final ColumnDefinition resultFields) {
        this.resultFields = resultFields;
    }
    
    public void storeStream(final int parameterIndex, final NativePacketPayload packet, final InputStream inStream) {
        this.session.checkClosed();
        synchronized (this.session) {
            final byte[] buf = new byte[8192];
            int numRead = 0;
            try {
                int bytesInPacket = 0;
                int totalBytesRead = 0;
                int bytesReadAtLastSend = 0;
                final int packetIsFullAt = this.session.getPropertySet().getMemorySizeProperty(PropertyKey.blobSendChunkSize).getValue();
                packet.setPosition(0);
                packet.writeInteger(NativeConstants.IntegerDataType.INT1, 24L);
                packet.writeInteger(NativeConstants.IntegerDataType.INT4, this.serverStatementId);
                packet.writeInteger(NativeConstants.IntegerDataType.INT2, parameterIndex);
                boolean readAny = false;
                while ((numRead = inStream.read(buf)) != -1) {
                    readAny = true;
                    packet.writeBytes(NativeConstants.StringLengthDataType.STRING_FIXED, buf, 0, numRead);
                    bytesInPacket += numRead;
                    totalBytesRead += numRead;
                    if (bytesInPacket >= packetIsFullAt) {
                        bytesReadAtLastSend = totalBytesRead;
                        this.session.sendCommand(packet, true, 0);
                        bytesInPacket = 0;
                        packet.setPosition(0);
                        packet.writeInteger(NativeConstants.IntegerDataType.INT1, 24L);
                        packet.writeInteger(NativeConstants.IntegerDataType.INT4, this.serverStatementId);
                        packet.writeInteger(NativeConstants.IntegerDataType.INT2, parameterIndex);
                    }
                }
                if (totalBytesRead != bytesReadAtLastSend) {
                    this.session.sendCommand(packet, true, 0);
                }
                if (!readAny) {
                    this.session.sendCommand(packet, true, 0);
                }
            }
            catch (IOException ioEx) {
                throw ExceptionFactory.createException(Messages.getString("ServerPreparedStatement.25") + ioEx.toString(), ioEx, this.session.getExceptionInterceptor());
            }
            finally {
                if (this.autoClosePStmtStreams.getValue() && inStream != null) {
                    try {
                        inStream.close();
                    }
                    catch (IOException ex) {}
                }
            }
        }
    }
    
    public void storeReader(final int parameterIndex, final NativePacketPayload packet, final Reader inStream) {
        this.session.checkClosed();
        synchronized (this.session) {
            final String forcedEncoding = this.session.getPropertySet().getStringProperty(PropertyKey.clobCharacterEncoding).getStringValue();
            final String clobEncoding = (forcedEncoding == null) ? this.session.getPropertySet().getStringProperty(PropertyKey.characterEncoding).getValue() : forcedEncoding;
            int maxBytesChar = 2;
            if (clobEncoding != null) {
                if (!clobEncoding.equals("UTF-16")) {
                    maxBytesChar = this.session.getServerSession().getMaxBytesPerChar(clobEncoding);
                    if (maxBytesChar == 1) {
                        maxBytesChar = 2;
                    }
                }
                else {
                    maxBytesChar = 4;
                }
            }
            final char[] buf = new char[8192 / maxBytesChar];
            int numRead = 0;
            int bytesInPacket = 0;
            int totalBytesRead = 0;
            int bytesReadAtLastSend = 0;
            final int packetIsFullAt = this.session.getPropertySet().getMemorySizeProperty(PropertyKey.blobSendChunkSize).getValue();
            try {
                packet.setPosition(0);
                packet.writeInteger(NativeConstants.IntegerDataType.INT1, 24L);
                packet.writeInteger(NativeConstants.IntegerDataType.INT4, this.serverStatementId);
                packet.writeInteger(NativeConstants.IntegerDataType.INT2, parameterIndex);
                boolean readAny = false;
                while ((numRead = inStream.read(buf)) != -1) {
                    readAny = true;
                    final byte[] valueAsBytes = StringUtils.getBytes(buf, 0, numRead, clobEncoding);
                    packet.writeBytes(NativeConstants.StringSelfDataType.STRING_EOF, valueAsBytes);
                    bytesInPacket += valueAsBytes.length;
                    totalBytesRead += valueAsBytes.length;
                    if (bytesInPacket >= packetIsFullAt) {
                        bytesReadAtLastSend = totalBytesRead;
                        this.session.sendCommand(packet, true, 0);
                        bytesInPacket = 0;
                        packet.setPosition(0);
                        packet.writeInteger(NativeConstants.IntegerDataType.INT1, 24L);
                        packet.writeInteger(NativeConstants.IntegerDataType.INT4, this.serverStatementId);
                        packet.writeInteger(NativeConstants.IntegerDataType.INT2, parameterIndex);
                    }
                }
                if (totalBytesRead != bytesReadAtLastSend) {
                    this.session.sendCommand(packet, true, 0);
                }
                if (!readAny) {
                    this.session.sendCommand(packet, true, 0);
                }
            }
            catch (IOException ioEx) {
                throw ExceptionFactory.createException(Messages.getString("ServerPreparedStatement.24") + ioEx.toString(), ioEx, this.session.getExceptionInterceptor());
            }
            finally {
                if (this.autoClosePStmtStreams.getValue() && inStream != null) {
                    try {
                        inStream.close();
                    }
                    catch (IOException ex) {}
                }
            }
        }
    }
    
    public void clearParameters(final boolean clearServerParameters) {
        boolean hadLongData = false;
        if (this.queryBindings != null) {
            hadLongData = ((ServerPreparedQueryBindings)this.queryBindings).clearBindValues();
            ((ServerPreparedQueryBindings)this.queryBindings).setLongParameterSwitchDetected(!clearServerParameters || !hadLongData);
        }
        if (clearServerParameters && hadLongData) {
            this.serverResetStatement();
        }
    }
    
    public void serverResetStatement() {
        this.session.checkClosed();
        synchronized (this.session) {
            try {
                this.session.sendCommand(this.commandBuilder.buildComStmtReset(this.session.getSharedSendPacket(), this.serverStatementId), false, 0);
            }
            finally {
                this.session.clearInputStream();
            }
        }
    }
    
    @Override
    protected long[] computeMaxParameterSetSizeAndBatchSize(final int numBatchedArgs) {
        long sizeOfEntireBatch = 10L;
        long maxSizeOfParameterSet = 0L;
        for (int i = 0; i < numBatchedArgs; ++i) {
            final ServerPreparedQueryBindValue[] paramArg = this.batchedArgs.get(i).getBindValues();
            long sizeOfParameterSet = (this.parameterCount + 7) / 8;
            sizeOfParameterSet += this.parameterCount * 2;
            final ServerPreparedQueryBindValue[] parameterBindings = ((ServerPreparedQueryBindings)this.queryBindings).getBindValues();
            for (int j = 0; j < parameterBindings.length; ++j) {
                if (!paramArg[j].isNull()) {
                    final long size = paramArg[j].getBoundLength();
                    if (paramArg[j].isStream()) {
                        if (size != -1L) {
                            sizeOfParameterSet += size;
                        }
                    }
                    else {
                        sizeOfParameterSet += size;
                    }
                }
            }
            sizeOfEntireBatch += sizeOfParameterSet;
            if (sizeOfParameterSet > maxSizeOfParameterSet) {
                maxSizeOfParameterSet = sizeOfParameterSet;
            }
        }
        return new long[] { maxSizeOfParameterSet, sizeOfEntireBatch };
    }
    
    private String truncateQueryToLog(final String sql) {
        String queryStr = null;
        final int maxQuerySizeToLog = this.session.getPropertySet().getIntegerProperty(PropertyKey.maxQuerySizeToLog).getValue();
        if (sql.length() > maxQuerySizeToLog) {
            final StringBuilder queryBuf = new StringBuilder(maxQuerySizeToLog + 12);
            queryBuf.append(sql.substring(0, maxQuerySizeToLog));
            queryBuf.append(Messages.getString("MysqlIO.25"));
            queryStr = queryBuf.toString();
        }
        else {
            queryStr = sql;
        }
        return queryStr;
    }
    
    @Override
    public <M extends Message> M fillSendPacket() {
        return null;
    }
    
    @Override
    public <M extends Message> M fillSendPacket(final QueryBindings<?> bindings) {
        return null;
    }
}
