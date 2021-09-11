
package com.mysql.cj;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import com.mysql.cj.protocol.a.NativePacketPayload;
import com.mysql.cj.protocol.a.NativeConstants;
import com.mysql.cj.protocol.Message;
import com.mysql.cj.util.StringUtils;
import com.mysql.cj.exceptions.ExceptionFactory;
import com.mysql.cj.exceptions.WrongArgumentException;
import com.mysql.cj.conf.PropertyKey;
import com.mysql.cj.conf.RuntimeProperty;

public abstract class AbstractPreparedQuery<T extends QueryBindings<?>> extends AbstractQuery implements PreparedQuery<T>
{
    protected ParseInfo parseInfo;
    protected T queryBindings;
    protected String originalSql;
    protected int parameterCount;
    protected RuntimeProperty<Boolean> autoClosePStmtStreams;
    protected int batchCommandIndex;
    protected RuntimeProperty<Boolean> useStreamLengthsInPrepStmts;
    private byte[] streamConvertBuf;
    private boolean usingAnsiMode;
    
    public AbstractPreparedQuery(final NativeSession sess) {
        super(sess);
        this.queryBindings = null;
        this.originalSql = null;
        this.batchCommandIndex = -1;
        this.streamConvertBuf = null;
        this.autoClosePStmtStreams = this.session.getPropertySet().getBooleanProperty(PropertyKey.autoClosePStmtStreams);
        this.useStreamLengthsInPrepStmts = this.session.getPropertySet().getBooleanProperty(PropertyKey.useStreamLengthsInPrepStmts);
        this.usingAnsiMode = !this.session.getServerSession().useAnsiQuotedIdentifiers();
    }
    
    @Override
    public void closeQuery() {
        this.streamConvertBuf = null;
        super.closeQuery();
    }
    
    @Override
    public ParseInfo getParseInfo() {
        return this.parseInfo;
    }
    
    @Override
    public void setParseInfo(final ParseInfo parseInfo) {
        this.parseInfo = parseInfo;
    }
    
    @Override
    public String getOriginalSql() {
        return this.originalSql;
    }
    
    @Override
    public void setOriginalSql(final String originalSql) {
        this.originalSql = originalSql;
    }
    
    @Override
    public int getParameterCount() {
        return this.parameterCount;
    }
    
    @Override
    public void setParameterCount(final int parameterCount) {
        this.parameterCount = parameterCount;
    }
    
    @Override
    public T getQueryBindings() {
        return this.queryBindings;
    }
    
    @Override
    public void setQueryBindings(final T queryBindings) {
        this.queryBindings = queryBindings;
    }
    
    @Override
    public int getBatchCommandIndex() {
        return this.batchCommandIndex;
    }
    
    @Override
    public void setBatchCommandIndex(final int batchCommandIndex) {
        this.batchCommandIndex = batchCommandIndex;
    }
    
    @Override
    public int computeBatchSize(final int numBatchedArgs) {
        final long[] combinedValues = this.computeMaxParameterSetSizeAndBatchSize(numBatchedArgs);
        final long maxSizeOfParameterSet = combinedValues[0];
        final long sizeOfEntireBatch = combinedValues[1];
        if (sizeOfEntireBatch < this.maxAllowedPacket.getValue() - this.originalSql.length()) {
            return numBatchedArgs;
        }
        return (int)Math.max(1L, (this.maxAllowedPacket.getValue() - this.originalSql.length()) / maxSizeOfParameterSet);
    }
    
    @Override
    public void checkNullOrEmptyQuery(final String sql) {
        if (sql == null) {
            throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("PreparedQuery.0"), this.session.getExceptionInterceptor());
        }
        if (sql.length() == 0) {
            throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("PreparedQuery.1"), this.session.getExceptionInterceptor());
        }
    }
    
    @Override
    public String asSql() {
        return this.asSql(false);
    }
    
    @Override
    public String asSql(final boolean quoteStreamsAndUnknowns) {
        final StringBuilder buf = new StringBuilder();
        Object batchArg = null;
        if (this.batchCommandIndex != -1) {
            batchArg = this.batchedArgs.get(this.batchCommandIndex);
        }
        final byte[][] staticSqlStrings = this.parseInfo.getStaticSql();
        for (int i = 0; i < this.parameterCount; ++i) {
            buf.append((this.charEncoding != null) ? StringUtils.toString(staticSqlStrings[i], this.charEncoding) : StringUtils.toString(staticSqlStrings[i]));
            byte[] val = null;
            if (batchArg != null && batchArg instanceof String) {
                buf.append((String)batchArg);
            }
            else {
                val = (byte[])((this.batchCommandIndex == -1) ? ((this.queryBindings == null) ? null : ((BindValue)this.queryBindings.getBindValues()[i]).getByteValue()) : ((QueryBindings)batchArg).getBindValues()[i].getByteValue());
                final boolean isStreamParam = (this.batchCommandIndex == -1) ? (this.queryBindings != null && ((BindValue)this.queryBindings.getBindValues()[i]).isStream()) : ((QueryBindings)batchArg).getBindValues()[i].isStream();
                if (val == null && !isStreamParam) {
                    buf.append(quoteStreamsAndUnknowns ? "'** NOT SPECIFIED **'" : "** NOT SPECIFIED **");
                }
                else if (isStreamParam) {
                    buf.append(quoteStreamsAndUnknowns ? "'** STREAM DATA **'" : "** STREAM DATA **");
                }
                else {
                    buf.append(StringUtils.toString(val, this.charEncoding));
                }
            }
        }
        buf.append((this.charEncoding != null) ? StringUtils.toString(staticSqlStrings[this.parameterCount], this.charEncoding) : StringUtils.toAsciiString(staticSqlStrings[this.parameterCount]));
        return buf.toString();
    }
    
    protected abstract long[] computeMaxParameterSetSizeAndBatchSize(final int p0);
    
    @Override
    public <M extends Message> M fillSendPacket() {
        synchronized (this) {
            return this.fillSendPacket(this.queryBindings);
        }
    }
    
    @Override
    public <M extends Message> M fillSendPacket(final QueryBindings<?> bindings) {
        synchronized (this) {
            final BindValue[] bindValues = (BindValue[])bindings.getBindValues();
            final NativePacketPayload sendPacket = this.session.getSharedSendPacket();
            sendPacket.writeInteger(NativeConstants.IntegerDataType.INT1, 3L);
            final boolean useStreamLengths = this.useStreamLengthsInPrepStmts.getValue();
            int ensurePacketSize = 0;
            final String statementComment = this.session.getProtocol().getQueryComment();
            byte[] commentAsBytes = null;
            if (statementComment != null) {
                commentAsBytes = StringUtils.getBytes(statementComment, this.charEncoding);
                ensurePacketSize += commentAsBytes.length;
                ensurePacketSize += 6;
            }
            for (int i = 0; i < bindValues.length; ++i) {
                if (bindValues[i].isStream() && useStreamLengths) {
                    ensurePacketSize += (int)bindValues[i].getStreamLength();
                }
            }
            if (ensurePacketSize != 0) {
                sendPacket.ensureCapacity(ensurePacketSize);
            }
            if (commentAsBytes != null) {
                sendPacket.writeBytes(NativeConstants.StringLengthDataType.STRING_FIXED, Constants.SLASH_STAR_SPACE_AS_BYTES);
                sendPacket.writeBytes(NativeConstants.StringLengthDataType.STRING_FIXED, commentAsBytes);
                sendPacket.writeBytes(NativeConstants.StringLengthDataType.STRING_FIXED, Constants.SPACE_STAR_SLASH_SPACE_AS_BYTES);
            }
            final byte[][] staticSqlStrings = this.parseInfo.getStaticSql();
            for (int j = 0; j < bindValues.length; ++j) {
                bindings.checkParameterSet(j);
                sendPacket.writeBytes(NativeConstants.StringLengthDataType.STRING_FIXED, staticSqlStrings[j]);
                if (bindValues[j].isStream()) {
                    this.streamToBytes(sendPacket, bindValues[j].getStreamValue(), true, bindValues[j].getStreamLength(), useStreamLengths);
                }
                else {
                    sendPacket.writeBytes(NativeConstants.StringLengthDataType.STRING_FIXED, bindValues[j].getByteValue());
                }
            }
            sendPacket.writeBytes(NativeConstants.StringLengthDataType.STRING_FIXED, staticSqlStrings[bindValues.length]);
            return (M)sendPacket;
        }
    }
    
    private final void streamToBytes(final NativePacketPayload packet, InputStream in, final boolean escape, final long streamLength, boolean useLength) {
        try {
            if (this.streamConvertBuf == null) {
                this.streamConvertBuf = new byte[4096];
            }
            final boolean hexEscape = this.session.getServerSession().isNoBackslashEscapesSet();
            if (streamLength == -1L) {
                useLength = false;
            }
            int bc = useLength ? this.readblock(in, this.streamConvertBuf, (int)streamLength) : this.readblock(in, this.streamConvertBuf);
            int lengthLeftToRead = (int)streamLength - bc;
            packet.writeBytes(NativeConstants.StringLengthDataType.STRING_FIXED, StringUtils.getBytes(hexEscape ? "x" : "_binary"));
            if (escape) {
                packet.writeInteger(NativeConstants.IntegerDataType.INT1, 39L);
            }
            while (bc > 0) {
                if (hexEscape) {
                    ((AbstractQueryBindings)this.queryBindings).hexEscapeBlock(this.streamConvertBuf, packet, bc);
                }
                else if (escape) {
                    this.escapeblockFast(this.streamConvertBuf, packet, bc);
                }
                else {
                    packet.writeBytes(NativeConstants.StringLengthDataType.STRING_FIXED, this.streamConvertBuf, 0, bc);
                }
                if (useLength) {
                    bc = this.readblock(in, this.streamConvertBuf, lengthLeftToRead);
                    if (bc <= 0) {
                        continue;
                    }
                    lengthLeftToRead -= bc;
                }
                else {
                    bc = this.readblock(in, this.streamConvertBuf);
                }
            }
            if (escape) {
                packet.writeInteger(NativeConstants.IntegerDataType.INT1, 39L);
            }
        }
        finally {
            if (this.autoClosePStmtStreams.getValue()) {
                try {
                    in.close();
                }
                catch (IOException ex) {}
                in = null;
            }
        }
    }
    
    protected final byte[] streamToBytes(InputStream in, final boolean escape, final long streamLength, boolean useLength) {
        in.mark(Integer.MAX_VALUE);
        try {
            if (this.streamConvertBuf == null) {
                this.streamConvertBuf = new byte[4096];
            }
            if (streamLength == -1L) {
                useLength = false;
            }
            final ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
            int bc = useLength ? this.readblock(in, this.streamConvertBuf, (int)streamLength) : this.readblock(in, this.streamConvertBuf);
            int lengthLeftToRead = (int)streamLength - bc;
            if (escape) {
                bytesOut.write(95);
                bytesOut.write(98);
                bytesOut.write(105);
                bytesOut.write(110);
                bytesOut.write(97);
                bytesOut.write(114);
                bytesOut.write(121);
                bytesOut.write(39);
            }
            while (bc > 0) {
                if (escape) {
                    StringUtils.escapeblockFast(this.streamConvertBuf, bytesOut, bc, this.usingAnsiMode);
                }
                else {
                    bytesOut.write(this.streamConvertBuf, 0, bc);
                }
                if (useLength) {
                    bc = this.readblock(in, this.streamConvertBuf, lengthLeftToRead);
                    if (bc <= 0) {
                        continue;
                    }
                    lengthLeftToRead -= bc;
                }
                else {
                    bc = this.readblock(in, this.streamConvertBuf);
                }
            }
            if (escape) {
                bytesOut.write(39);
            }
            return bytesOut.toByteArray();
        }
        finally {
            try {
                in.reset();
            }
            catch (IOException ex) {}
            if (this.autoClosePStmtStreams.getValue()) {
                try {
                    in.close();
                }
                catch (IOException ex2) {}
                in = null;
            }
        }
    }
    
    private final int readblock(final InputStream i, final byte[] b) {
        try {
            return i.read(b);
        }
        catch (Throwable ex) {
            throw ExceptionFactory.createException(Messages.getString("PreparedStatement.56") + ex.getClass().getName(), this.session.getExceptionInterceptor());
        }
    }
    
    private final int readblock(final InputStream i, final byte[] b, final int length) {
        try {
            int lengthToRead = length;
            if (lengthToRead > b.length) {
                lengthToRead = b.length;
            }
            return i.read(b, 0, lengthToRead);
        }
        catch (Throwable ex) {
            throw ExceptionFactory.createException(Messages.getString("PreparedStatement.56") + ex.getClass().getName(), this.session.getExceptionInterceptor());
        }
    }
    
    private final void escapeblockFast(final byte[] buf, final NativePacketPayload packet, final int size) {
        int lastwritten = 0;
        for (int i = 0; i < size; ++i) {
            final byte b = buf[i];
            if (b == 0) {
                if (i > lastwritten) {
                    packet.writeBytes(NativeConstants.StringLengthDataType.STRING_FIXED, buf, lastwritten, i - lastwritten);
                }
                packet.writeInteger(NativeConstants.IntegerDataType.INT1, 92L);
                packet.writeInteger(NativeConstants.IntegerDataType.INT1, 48L);
                lastwritten = i + 1;
            }
            else if (b == 92 || b == 39 || (!this.usingAnsiMode && b == 34)) {
                if (i > lastwritten) {
                    packet.writeBytes(NativeConstants.StringLengthDataType.STRING_FIXED, buf, lastwritten, i - lastwritten);
                }
                packet.writeInteger(NativeConstants.IntegerDataType.INT1, 92L);
                lastwritten = i;
            }
        }
        if (lastwritten < size) {
            packet.writeBytes(NativeConstants.StringLengthDataType.STRING_FIXED, buf, lastwritten, size - lastwritten);
        }
    }
}
