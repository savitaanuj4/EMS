
package com.mysql.cj.jdbc;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.sql.Date;
import java.sql.Time;
import java.io.Reader;
import java.sql.PreparedStatement;
import java.net.URL;
import java.io.InputStream;
import com.mysql.cj.exceptions.WrongArgumentException;
import java.io.IOException;
import com.mysql.cj.protocol.ProtocolEntityFactory;
import com.mysql.cj.exceptions.ExceptionFactory;
import com.mysql.cj.protocol.a.NativePacketPayload;
import java.sql.ParameterMetaData;
import java.sql.ResultSetMetaData;
import com.mysql.cj.conf.PropertyKey;
import com.mysql.cj.jdbc.result.ResultSetInternalMethods;
import com.mysql.cj.protocol.Message;
import com.mysql.cj.protocol.ColumnDefinition;
import com.mysql.cj.exceptions.ExceptionInterceptor;
import com.mysql.cj.CancelQueryTask;
import com.mysql.cj.jdbc.exceptions.MySQLStatementCancelledException;
import com.mysql.cj.jdbc.exceptions.MySQLTimeoutException;
import com.mysql.cj.Query;
import com.mysql.cj.result.Row;
import java.util.ArrayList;
import com.mysql.cj.jdbc.exceptions.SQLError;
import com.mysql.cj.MysqlType;
import com.mysql.cj.ServerPreparedQueryBindings;
import com.mysql.cj.ServerPreparedQueryBindValue;
import com.mysql.cj.Messages;
import com.mysql.cj.ServerPreparedQuery;
import com.mysql.cj.jdbc.exceptions.SQLExceptionsMapping;
import com.mysql.cj.Session;
import com.mysql.cj.ParseInfo;
import com.mysql.cj.PreparedQuery;
import java.sql.SQLException;
import com.mysql.cj.exceptions.CJException;

public class ServerPreparedStatement extends ClientPreparedStatement
{
    private boolean hasOnDuplicateKeyUpdate;
    private boolean invalid;
    private CJException invalidationException;
    protected boolean isCached;
    
    protected static ServerPreparedStatement getInstance(final JdbcConnection conn, final String sql, final String catalog, final int resultSetType, final int resultSetConcurrency) throws SQLException {
        return new ServerPreparedStatement(conn, sql, catalog, resultSetType, resultSetConcurrency);
    }
    
    protected ServerPreparedStatement(final JdbcConnection conn, final String sql, final String catalog, final int resultSetType, final int resultSetConcurrency) throws SQLException {
        super(conn, catalog);
        this.hasOnDuplicateKeyUpdate = false;
        this.invalid = false;
        this.isCached = false;
        this.checkNullOrEmptyQuery(sql);
        final String statementComment = this.session.getProtocol().getQueryComment();
        ((PreparedQuery)this.query).setOriginalSql((statementComment == null) ? sql : ("/* " + statementComment + " */ " + sql));
        ((PreparedQuery)this.query).setParseInfo(new ParseInfo(((PreparedQuery)this.query).getOriginalSql(), this.session, this.charEncoding));
        this.hasOnDuplicateKeyUpdate = (((PreparedQuery)this.query).getParseInfo().getFirstStmtChar() == 'I' && this.containsOnDuplicateKeyInString(sql));
        try {
            this.serverPrepare(sql);
        }
        catch (CJException | SQLException ex2) {
            final Exception ex;
            final Exception sqlEx = ex;
            this.realClose(false, true);
            throw SQLExceptionsMapping.translateException(sqlEx, this.exceptionInterceptor);
        }
        this.setResultSetType(resultSetType);
        this.setResultSetConcurrency(resultSetConcurrency);
    }
    
    @Override
    protected void initQuery() {
        this.query = ServerPreparedQuery.getInstance(this.session);
    }
    
    @Override
    public String toString() {
        final StringBuilder toStringBuf = new StringBuilder();
        toStringBuf.append(this.getClass().getName() + "[");
        toStringBuf.append(((ServerPreparedQuery)this.query).getServerStatementId());
        toStringBuf.append("]: ");
        try {
            toStringBuf.append(this.asSql());
        }
        catch (SQLException sqlEx) {
            toStringBuf.append(Messages.getString("ServerPreparedStatement.6"));
            toStringBuf.append(sqlEx);
        }
        return toStringBuf.toString();
    }
    
    @Override
    public void addBatch() throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                this.query.addBatch(((PreparedQuery)this.query).getQueryBindings().clone());
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public String asSql(final boolean quoteStreamsAndUnknowns) throws SQLException {
        synchronized (this.checkClosed().getConnectionMutex()) {
            ClientPreparedStatement pStmtForSub = null;
            try {
                pStmtForSub = ClientPreparedStatement.getInstance(this.connection, ((PreparedQuery)this.query).getOriginalSql(), this.getCurrentCatalog());
                final int numParameters = ((PreparedQuery)pStmtForSub.query).getParameterCount();
                final int ourNumParameters = ((PreparedQuery)this.query).getParameterCount();
                final ServerPreparedQueryBindValue[] parameterBindings = ((ServerPreparedQuery)this.query).getQueryBindings().getBindValues();
                for (int i = 0; i < numParameters && i < ourNumParameters; ++i) {
                    if (parameterBindings[i] != null) {
                        if (parameterBindings[i].isNull()) {
                            pStmtForSub.setNull(i + 1, MysqlType.NULL);
                        }
                        else {
                            final ServerPreparedQueryBindValue bindValue = parameterBindings[i];
                            switch (bindValue.bufferType) {
                                case 1: {
                                    pStmtForSub.setByte(i + 1, ((Long)bindValue.value).byteValue());
                                    break;
                                }
                                case 2: {
                                    pStmtForSub.setShort(i + 1, ((Long)bindValue.value).shortValue());
                                    break;
                                }
                                case 3: {
                                    pStmtForSub.setInt(i + 1, ((Long)bindValue.value).intValue());
                                    break;
                                }
                                case 8: {
                                    pStmtForSub.setLong(i + 1, (long)bindValue.value);
                                    break;
                                }
                                case 4: {
                                    pStmtForSub.setFloat(i + 1, (float)bindValue.value);
                                    break;
                                }
                                case 5: {
                                    pStmtForSub.setDouble(i + 1, (double)bindValue.value);
                                    break;
                                }
                                default: {
                                    pStmtForSub.setObject(i + 1, parameterBindings[i].value);
                                    break;
                                }
                            }
                        }
                    }
                }
                return pStmtForSub.asSql(quoteStreamsAndUnknowns);
            }
            finally {
                if (pStmtForSub != null) {
                    try {
                        pStmtForSub.close();
                    }
                    catch (SQLException ex) {}
                }
            }
        }
    }
    
    @Override
    protected JdbcConnection checkClosed() {
        if (this.invalid) {
            throw this.invalidationException;
        }
        return super.checkClosed();
    }
    
    @Override
    public void clearParameters() {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                ((ServerPreparedQuery)this.query).clearParameters(true);
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    protected void setClosed(final boolean flag) {
        this.isClosed = flag;
    }
    
    @Override
    public void close() throws SQLException {
        try {
            final JdbcConnection locallyScopedConn = this.connection;
            if (locallyScopedConn == null) {
                return;
            }
            synchronized (locallyScopedConn.getConnectionMutex()) {
                if (this.isCached && this.isPoolable() && !this.isClosed) {
                    this.clearParameters();
                    this.isClosed = true;
                    this.connection.recachePreparedStatement(this);
                    return;
                }
                this.isClosed = false;
                this.realClose(true, true);
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    protected long[] executeBatchSerially(final int batchTimeout) throws SQLException {
        synchronized (this.checkClosed().getConnectionMutex()) {
            final JdbcConnection locallyScopedConn = this.connection;
            if (locallyScopedConn.isReadOnly()) {
                throw SQLError.createSQLException(Messages.getString("ServerPreparedStatement.2") + Messages.getString("ServerPreparedStatement.3"), "S1009", this.exceptionInterceptor);
            }
            this.clearWarnings();
            final ServerPreparedQueryBindValue[] oldBindValues = ((ServerPreparedQuery)this.query).getQueryBindings().getBindValues();
            try {
                long[] updateCounts = null;
                if (this.query.getBatchedArgs() != null) {
                    final int nbrCommands = this.query.getBatchedArgs().size();
                    updateCounts = new long[nbrCommands];
                    if (this.retrieveGeneratedKeys) {
                        this.batchedGeneratedKeys = new ArrayList<Row>(nbrCommands);
                    }
                    for (int i = 0; i < nbrCommands; ++i) {
                        updateCounts[i] = -3L;
                    }
                    SQLException sqlEx = null;
                    int commandIndex = 0;
                    ServerPreparedQueryBindValue[] previousBindValuesForBatch = null;
                    CancelQueryTask timeoutTask = null;
                    try {
                        timeoutTask = this.startQueryTimer(this, batchTimeout);
                        for (commandIndex = 0; commandIndex < nbrCommands; ++commandIndex) {
                            final Object arg = this.query.getBatchedArgs().get(commandIndex);
                            try {
                                if (arg instanceof String) {
                                    updateCounts[commandIndex] = this.executeUpdateInternal((String)arg, true, this.retrieveGeneratedKeys);
                                    this.getBatchedGeneratedKeys((this.results.getFirstCharOfQuery() == 'I' && this.containsOnDuplicateKeyInString((String)arg)) ? 1 : 0);
                                }
                                else {
                                    ((ServerPreparedQuery)this.query).setQueryBindings((ServerPreparedQueryBindings)arg);
                                    final ServerPreparedQueryBindValue[] parameterBindings = ((ServerPreparedQuery)this.query).getQueryBindings().getBindValues();
                                    if (previousBindValuesForBatch != null) {
                                        for (int j = 0; j < parameterBindings.length; ++j) {
                                            if (parameterBindings[j].bufferType != previousBindValuesForBatch[j].bufferType) {
                                                ((ServerPreparedQuery)this.query).getQueryBindings().getSendTypesToServer().set(true);
                                                break;
                                            }
                                        }
                                    }
                                    try {
                                        updateCounts[commandIndex] = this.executeUpdateInternal(false, true);
                                    }
                                    finally {
                                        previousBindValuesForBatch = parameterBindings;
                                    }
                                    this.getBatchedGeneratedKeys(this.containsOnDuplicateKeyUpdateInSQL() ? 1 : 0);
                                }
                            }
                            catch (SQLException ex) {
                                updateCounts[commandIndex] = -3L;
                                if (!this.continueBatchOnError || ex instanceof MySQLTimeoutException || ex instanceof MySQLStatementCancelledException || this.hasDeadlockOrTimeoutRolledBackTx(ex)) {
                                    final long[] newUpdateCounts = new long[commandIndex];
                                    System.arraycopy(updateCounts, 0, newUpdateCounts, 0, commandIndex);
                                    throw SQLError.createBatchUpdateException(ex, newUpdateCounts, this.exceptionInterceptor);
                                }
                                sqlEx = ex;
                            }
                        }
                    }
                    finally {
                        this.stopQueryTimer(timeoutTask, false, false);
                        this.resetCancelledState();
                    }
                    if (sqlEx != null) {
                        throw SQLError.createBatchUpdateException(sqlEx, updateCounts, this.exceptionInterceptor);
                    }
                }
                return (updateCounts != null) ? updateCounts : new long[0];
            }
            finally {
                ((ServerPreparedQuery)this.query).getQueryBindings().setBindValues(oldBindValues);
                ((ServerPreparedQuery)this.query).getQueryBindings().getSendTypesToServer().set(true);
                this.clearBatch();
            }
        }
    }
    
    private static SQLException appendMessageToException(final SQLException sqlEx, final String messageToAppend, final ExceptionInterceptor interceptor) {
        final String sqlState = sqlEx.getSQLState();
        final int vendorErrorCode = sqlEx.getErrorCode();
        final SQLException sqlExceptionWithNewMessage = SQLError.createSQLException(sqlEx.getMessage() + messageToAppend, sqlState, vendorErrorCode, interceptor);
        sqlExceptionWithNewMessage.setStackTrace(sqlEx.getStackTrace());
        return sqlExceptionWithNewMessage;
    }
    
    @Override
    protected <M extends Message> ResultSetInternalMethods executeInternal(final int maxRowsToRetrieve, final M sendPacket, final boolean createStreamingResultSet, final boolean queryIsSelectOnly, final ColumnDefinition metadata, final boolean isBatch) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                ((PreparedQuery)this.query).getQueryBindings().setNumberOfExecutions(((PreparedQuery)this.query).getQueryBindings().getNumberOfExecutions() + 1);
                try {
                    return this.serverExecute(maxRowsToRetrieve, createStreamingResultSet, metadata);
                }
                catch (SQLException sqlEx) {
                    if (this.session.getPropertySet().getBooleanProperty(PropertyKey.enablePacketDebug).getValue()) {
                        this.session.dumpPacketRingBuffer();
                    }
                    if (this.dumpQueriesOnException.getValue()) {
                        final String extractedSql = this.toString();
                        final StringBuilder messageBuf = new StringBuilder(extractedSql.length() + 32);
                        messageBuf.append("\n\nQuery being executed when exception was thrown:\n");
                        messageBuf.append(extractedSql);
                        messageBuf.append("\n\n");
                        sqlEx = appendMessageToException(sqlEx, messageBuf.toString(), this.exceptionInterceptor);
                    }
                    throw sqlEx;
                }
                catch (Exception ex) {
                    if (this.session.getPropertySet().getBooleanProperty(PropertyKey.enablePacketDebug).getValue()) {
                        this.session.dumpPacketRingBuffer();
                    }
                    SQLException sqlEx2 = SQLError.createSQLException(ex.toString(), "S1000", ex, this.exceptionInterceptor);
                    if (this.dumpQueriesOnException.getValue()) {
                        final String extractedSql2 = this.toString();
                        final StringBuilder messageBuf2 = new StringBuilder(extractedSql2.length() + 32);
                        messageBuf2.append("\n\nQuery being executed when exception was thrown:\n");
                        messageBuf2.append(extractedSql2);
                        messageBuf2.append("\n\n");
                        sqlEx2 = appendMessageToException(sqlEx2, messageBuf2.toString(), this.exceptionInterceptor);
                    }
                    throw sqlEx2;
                }
            }
        }
        catch (CJException ex2) {
            throw SQLExceptionsMapping.translateException(ex2, this.getExceptionInterceptor());
        }
    }
    
    protected ServerPreparedQueryBindValue getBinding(final int parameterIndex, final boolean forLongData) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                final int i = this.getCoreParameterIndex(parameterIndex);
                return ((ServerPreparedQuery)this.query).getQueryBindings().getBinding(i, forLongData);
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public ResultSetMetaData getMetaData() throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                final ColumnDefinition resultFields = ((ServerPreparedQuery)this.query).getResultFields();
                return (resultFields == null || resultFields.getFields() == null) ? null : new com.mysql.cj.jdbc.result.ResultSetMetaData(this.session, resultFields.getFields(), this.session.getPropertySet().getBooleanProperty(PropertyKey.useOldAliasMetadataBehavior).getValue(), this.session.getPropertySet().getBooleanProperty(PropertyKey.yearIsDateType).getValue(), this.exceptionInterceptor);
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public ParameterMetaData getParameterMetaData() throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                if (this.parameterMetaData == null) {
                    this.parameterMetaData = new MysqlParameterMetadata(this.session, ((ServerPreparedQuery)this.query).getParameterFields(), ((PreparedQuery)this.query).getParameterCount(), this.exceptionInterceptor);
                }
                return this.parameterMetaData;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean isNull(final int paramIndex) {
        throw new IllegalArgumentException(Messages.getString("ServerPreparedStatement.7"));
    }
    
    @Override
    public void realClose(final boolean calledExplicitly, final boolean closeOpenResults) throws SQLException {
        try {
            final JdbcConnection locallyScopedConn = this.connection;
            if (locallyScopedConn == null) {
                return;
            }
            synchronized (locallyScopedConn.getConnectionMutex()) {
                if (this.connection != null) {
                    CJException exceptionDuringClose = null;
                    if (calledExplicitly && !this.connection.isClosed()) {
                        synchronized (this.connection.getConnectionMutex()) {
                            try {
                                this.session.sendCommand(this.commandBuilder.buildComStmtClose(null, ((ServerPreparedQuery)this.query).getServerStatementId()), true, 0);
                            }
                            catch (CJException sqlEx) {
                                exceptionDuringClose = sqlEx;
                            }
                        }
                    }
                    if (this.isCached) {
                        this.connection.decachePreparedStatement(this);
                        this.isCached = false;
                    }
                    super.realClose(calledExplicitly, closeOpenResults);
                    ((ServerPreparedQuery)this.query).clearParameters(false);
                    if (exceptionDuringClose != null) {
                        throw exceptionDuringClose;
                    }
                }
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    protected void rePrepare() {
        synchronized (this.checkClosed().getConnectionMutex()) {
            this.invalidationException = null;
            try {
                this.serverPrepare(((PreparedQuery)this.query).getOriginalSql());
            }
            catch (Exception ex) {
                this.invalidationException = ExceptionFactory.createException(ex.getMessage(), ex);
            }
            if (this.invalidationException != null) {
                this.invalid = true;
                this.query.closeQuery();
                if (this.results != null) {
                    try {
                        this.results.close();
                    }
                    catch (Exception ex2) {}
                }
                if (this.generatedKeysResults != null) {
                    try {
                        this.generatedKeysResults.close();
                    }
                    catch (Exception ex3) {}
                }
                try {
                    this.closeAllOpenResults();
                }
                catch (Exception ex4) {}
                if (this.connection != null && !this.dontTrackOpenResources.getValue()) {
                    this.connection.unregisterStatement(this);
                }
            }
        }
    }
    
    protected ResultSetInternalMethods serverExecute(final int maxRowsToRetrieve, final boolean createStreamingResultSet, final ColumnDefinition metadata) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                return this.results = ((ServerPreparedQuery)this.query).serverExecute(maxRowsToRetrieve, createStreamingResultSet, metadata, (ProtocolEntityFactory<ResultSetInternalMethods, NativePacketPayload>)this.resultSetFactory);
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    protected void serverPrepare(final String sql) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                SQLException t = null;
                try {
                    final ServerPreparedQuery q = (ServerPreparedQuery)this.query;
                    q.serverPrepare(sql);
                    try {
                        this.session.clearInputStream();
                    }
                    catch (Exception e) {
                        if (t == null) {
                            t = SQLError.createCommunicationsException(this.connection, this.session.getProtocol().getPacketSentTimeHolder(), this.session.getProtocol().getPacketReceivedTimeHolder(), e, this.exceptionInterceptor);
                        }
                    }
                    if (t != null) {
                        throw t;
                    }
                }
                catch (IOException ioEx) {
                    t = SQLError.createCommunicationsException(this.connection, this.session.getProtocol().getPacketSentTimeHolder(), this.session.getProtocol().getPacketReceivedTimeHolder(), ioEx, this.exceptionInterceptor);
                }
                catch (CJException sqlEx) {
                    SQLException ex = SQLExceptionsMapping.translateException(sqlEx);
                    if (this.dumpQueriesOnException.getValue()) {
                        final StringBuilder messageBuf = new StringBuilder(((PreparedQuery)this.query).getOriginalSql().length() + 32);
                        messageBuf.append("\n\nQuery being prepared when exception was thrown:\n\n");
                        messageBuf.append(((PreparedQuery)this.query).getOriginalSql());
                        ex = appendMessageToException(ex, messageBuf.toString(), this.exceptionInterceptor);
                    }
                    t = ex;
                }
                finally {
                    try {
                        this.session.clearInputStream();
                    }
                    catch (Exception e2) {
                        if (t == null) {
                            t = SQLError.createCommunicationsException(this.connection, this.session.getProtocol().getPacketSentTimeHolder(), this.session.getProtocol().getPacketReceivedTimeHolder(), e2, this.exceptionInterceptor);
                        }
                    }
                    if (t != null) {
                        throw t;
                    }
                }
            }
        }
        catch (CJException ex2) {
            throw SQLExceptionsMapping.translateException(ex2, this.getExceptionInterceptor());
        }
    }
    
    @Override
    protected void checkBounds(final int parameterIndex, final int parameterIndexOffset) throws SQLException {
        final int paramCount = ((PreparedQuery)this.query).getParameterCount();
        if (paramCount == 0) {
            throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("ServerPreparedStatement.8"), this.session.getExceptionInterceptor());
        }
        if (parameterIndex < 0 || parameterIndex > paramCount) {
            throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("ServerPreparedStatement.9") + (parameterIndex + 1) + Messages.getString("ServerPreparedStatement.10") + paramCount, this.session.getExceptionInterceptor());
        }
    }
    
    @Deprecated
    @Override
    public void setUnicodeStream(final int parameterIndex, final InputStream x, final int length) throws SQLException {
        try {
            this.checkClosed();
            throw SQLError.createSQLFeatureNotSupportedException();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setURL(final int parameterIndex, final URL x) throws SQLException {
        try {
            this.checkClosed();
            this.setString(parameterIndex, x.toString());
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public long getServerStatementId() {
        return ((ServerPreparedQuery)this.query).getServerStatementId();
    }
    
    @Override
    protected int setOneBatchedParameterSet(final PreparedStatement batchedStatement, int batchedParamIndex, final Object paramSet) throws SQLException {
        final ServerPreparedQueryBindValue[] paramArg = ((ServerPreparedQueryBindings)paramSet).getBindValues();
        for (int j = 0; j < paramArg.length; ++j) {
            if (paramArg[j].isNull()) {
                batchedStatement.setNull(batchedParamIndex++, MysqlType.NULL.getJdbcType());
            }
            else if (paramArg[j].isStream()) {
                final Object value = paramArg[j].value;
                if (value instanceof InputStream) {
                    batchedStatement.setBinaryStream(batchedParamIndex++, (InputStream)value, paramArg[j].getStreamLength());
                }
                else {
                    batchedStatement.setCharacterStream(batchedParamIndex++, (Reader)value, paramArg[j].getStreamLength());
                }
            }
            else {
                switch (paramArg[j].bufferType) {
                    case 1: {
                        batchedStatement.setByte(batchedParamIndex++, ((Long)paramArg[j].value).byteValue());
                        break;
                    }
                    case 2: {
                        batchedStatement.setShort(batchedParamIndex++, ((Long)paramArg[j].value).shortValue());
                        break;
                    }
                    case 3: {
                        batchedStatement.setInt(batchedParamIndex++, ((Long)paramArg[j].value).intValue());
                        break;
                    }
                    case 8: {
                        batchedStatement.setLong(batchedParamIndex++, (long)paramArg[j].value);
                        break;
                    }
                    case 4: {
                        batchedStatement.setFloat(batchedParamIndex++, (float)paramArg[j].value);
                        break;
                    }
                    case 5: {
                        batchedStatement.setDouble(batchedParamIndex++, (double)paramArg[j].value);
                        break;
                    }
                    case 11: {
                        batchedStatement.setTime(batchedParamIndex++, (Time)paramArg[j].value);
                        break;
                    }
                    case 10: {
                        batchedStatement.setDate(batchedParamIndex++, (Date)paramArg[j].value);
                        break;
                    }
                    case 7:
                    case 12: {
                        batchedStatement.setTimestamp(batchedParamIndex++, (Timestamp)paramArg[j].value);
                        break;
                    }
                    case 0:
                    case 15:
                    case 246:
                    case 253:
                    case 254: {
                        final Object value = paramArg[j].value;
                        if (value instanceof byte[]) {
                            batchedStatement.setBytes(batchedParamIndex, (byte[])value);
                        }
                        else {
                            batchedStatement.setString(batchedParamIndex, (String)value);
                        }
                        if (batchedStatement instanceof ServerPreparedStatement) {
                            final ServerPreparedQueryBindValue asBound = ((ServerPreparedStatement)batchedStatement).getBinding(batchedParamIndex, false);
                            asBound.bufferType = paramArg[j].bufferType;
                        }
                        ++batchedParamIndex;
                        break;
                    }
                    default: {
                        throw new IllegalArgumentException(Messages.getString("ServerPreparedStatement.26", new Object[] { batchedParamIndex }));
                    }
                }
            }
        }
        return batchedParamIndex;
    }
    
    @Override
    protected boolean containsOnDuplicateKeyUpdateInSQL() {
        return this.hasOnDuplicateKeyUpdate;
    }
    
    @Override
    protected ClientPreparedStatement prepareBatchedInsertSQL(final JdbcConnection localConn, final int numBatches) throws SQLException {
        synchronized (this.checkClosed().getConnectionMutex()) {
            try {
                final ClientPreparedStatement pstmt = localConn.prepareStatement(((PreparedQuery)this.query).getParseInfo().getSqlForBatch(numBatches), this.resultSetConcurrency, this.query.getResultType().getIntValue()).unwrap(ClientPreparedStatement.class);
                pstmt.setRetrieveGeneratedKeys(this.retrieveGeneratedKeys);
                return pstmt;
            }
            catch (UnsupportedEncodingException e) {
                final SQLException sqlEx = SQLError.createSQLException(Messages.getString("ServerPreparedStatement.27"), "S1000", this.exceptionInterceptor);
                sqlEx.initCause(e);
                throw sqlEx;
            }
        }
    }
    
    @Override
    public void setPoolable(final boolean poolable) throws SQLException {
        try {
            if (!poolable) {
                this.connection.decachePreparedStatement(this);
            }
            super.setPoolable(poolable);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
}
