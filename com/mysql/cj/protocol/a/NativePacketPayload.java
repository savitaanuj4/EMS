
package com.mysql.cj.protocol.a;

import com.mysql.cj.Constants;
import com.mysql.cj.exceptions.ExceptionFactory;
import com.mysql.cj.Messages;
import com.mysql.cj.exceptions.WrongArgumentException;
import com.mysql.cj.util.StringUtils;
import com.mysql.cj.protocol.Message;

public class NativePacketPayload implements Message
{
    static final int NO_LENGTH_LIMIT = -1;
    public static final long NULL_LENGTH = -1L;
    public static final short TYPE_ID_ERROR = 255;
    public static final short TYPE_ID_EOF = 254;
    public static final short TYPE_ID_AUTH_SWITCH = 254;
    public static final short TYPE_ID_LOCAL_INFILE = 251;
    public static final short TYPE_ID_OK = 0;
    private int payloadLength;
    private byte[] byteBuffer;
    private int position;
    static final int MAX_BYTES_TO_DUMP = 1024;
    
    @Override
    public String toString() {
        final int numBytes = (this.position <= this.payloadLength) ? this.position : this.payloadLength;
        final int numBytesToDump = (numBytes < 1024) ? numBytes : 1024;
        this.position = 0;
        final String dumped = StringUtils.dumpAsHex(this.readBytes(NativeConstants.StringLengthDataType.STRING_FIXED, numBytesToDump), numBytesToDump);
        if (numBytesToDump < numBytes) {
            return dumped + " ....(packet exceeds max. dump length)";
        }
        return dumped;
    }
    
    public String toSuperString() {
        return super.toString();
    }
    
    public NativePacketPayload(final byte[] buf) {
        this.payloadLength = 0;
        this.position = 0;
        this.byteBuffer = buf;
        this.payloadLength = buf.length;
    }
    
    public NativePacketPayload(final int size) {
        this.payloadLength = 0;
        this.position = 0;
        this.byteBuffer = new byte[size];
        this.payloadLength = size;
    }
    
    public int getCapacity() {
        return this.byteBuffer.length;
    }
    
    public final void ensureCapacity(final int additionalData) {
        if (this.position + additionalData > this.byteBuffer.length) {
            int newLength = (int)(this.byteBuffer.length * 1.25);
            if (newLength < this.byteBuffer.length + additionalData) {
                newLength = this.byteBuffer.length + (int)(additionalData * 1.25);
            }
            if (newLength < this.byteBuffer.length) {
                newLength = this.byteBuffer.length + additionalData;
            }
            final byte[] newBytes = new byte[newLength];
            System.arraycopy(this.byteBuffer, 0, newBytes, 0, this.byteBuffer.length);
            this.byteBuffer = newBytes;
        }
    }
    
    @Override
    public byte[] getByteBuffer() {
        return this.byteBuffer;
    }
    
    public void setByteBuffer(final byte[] byteBufferToSet) {
        this.byteBuffer = byteBufferToSet;
    }
    
    public int getPayloadLength() {
        return this.payloadLength;
    }
    
    public void setPayloadLength(final int bufLengthToSet) {
        if (bufLengthToSet > this.byteBuffer.length) {
            throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("Buffer.0"));
        }
        this.payloadLength = bufLengthToSet;
    }
    
    private void adjustPayloadLength() {
        if (this.position > this.payloadLength) {
            this.payloadLength = this.position;
        }
    }
    
    @Override
    public int getPosition() {
        return this.position;
    }
    
    public void setPosition(final int positionToSet) {
        this.position = positionToSet;
    }
    
    public boolean isErrorPacket() {
        return (this.byteBuffer[0] & 0xFF) == 0xFF;
    }
    
    public final boolean isEOFPacket() {
        return (this.byteBuffer[0] & 0xFF) == 0xFE && this.getPayloadLength() <= 5;
    }
    
    public final boolean isAuthMethodSwitchRequestPacket() {
        return (this.byteBuffer[0] & 0xFF) == 0xFE;
    }
    
    public final boolean isOKPacket() {
        return (this.byteBuffer[0] & 0xFF) == 0x0;
    }
    
    public final boolean isResultSetOKPacket() {
        return (this.byteBuffer[0] & 0xFF) == 0xFE && this.getPayloadLength() < 16777215;
    }
    
    public final boolean isAuthMoreData() {
        return (this.byteBuffer[0] & 0xFF) == 0x1;
    }
    
    public void writeInteger(final NativeConstants.IntegerDataType type, final long l) {
        switch (type) {
            case INT1: {
                this.ensureCapacity(1);
                final byte[] b = this.byteBuffer;
                b[this.position++] = (byte)(l & 0xFFL);
                break;
            }
            case INT2: {
                this.ensureCapacity(2);
                final byte[] b = this.byteBuffer;
                b[this.position++] = (byte)(l & 0xFFL);
                b[this.position++] = (byte)(l >>> 8);
                break;
            }
            case INT3: {
                this.ensureCapacity(3);
                final byte[] b = this.byteBuffer;
                b[this.position++] = (byte)(l & 0xFFL);
                b[this.position++] = (byte)(l >>> 8);
                b[this.position++] = (byte)(l >>> 16);
                break;
            }
            case INT4: {
                this.ensureCapacity(4);
                final byte[] b = this.byteBuffer;
                b[this.position++] = (byte)(l & 0xFFL);
                b[this.position++] = (byte)(l >>> 8);
                b[this.position++] = (byte)(l >>> 16);
                b[this.position++] = (byte)(l >>> 24);
                break;
            }
            case INT6: {
                this.ensureCapacity(6);
                final byte[] b = this.byteBuffer;
                b[this.position++] = (byte)(l & 0xFFL);
                b[this.position++] = (byte)(l >>> 8);
                b[this.position++] = (byte)(l >>> 16);
                b[this.position++] = (byte)(l >>> 24);
                b[this.position++] = (byte)(l >>> 32);
                b[this.position++] = (byte)(l >>> 40);
                break;
            }
            case INT8: {
                this.ensureCapacity(8);
                final byte[] b = this.byteBuffer;
                b[this.position++] = (byte)(l & 0xFFL);
                b[this.position++] = (byte)(l >>> 8);
                b[this.position++] = (byte)(l >>> 16);
                b[this.position++] = (byte)(l >>> 24);
                b[this.position++] = (byte)(l >>> 32);
                b[this.position++] = (byte)(l >>> 40);
                b[this.position++] = (byte)(l >>> 48);
                b[this.position++] = (byte)(l >>> 56);
                break;
            }
            case INT_LENENC: {
                if (l < 251L) {
                    this.ensureCapacity(1);
                    this.writeInteger(NativeConstants.IntegerDataType.INT1, l);
                    break;
                }
                if (l < 65536L) {
                    this.ensureCapacity(3);
                    this.writeInteger(NativeConstants.IntegerDataType.INT1, 252L);
                    this.writeInteger(NativeConstants.IntegerDataType.INT2, l);
                    break;
                }
                if (l < 16777216L) {
                    this.ensureCapacity(4);
                    this.writeInteger(NativeConstants.IntegerDataType.INT1, 253L);
                    this.writeInteger(NativeConstants.IntegerDataType.INT3, l);
                    break;
                }
                this.ensureCapacity(9);
                this.writeInteger(NativeConstants.IntegerDataType.INT1, 254L);
                this.writeInteger(NativeConstants.IntegerDataType.INT8, l);
                break;
            }
        }
        this.adjustPayloadLength();
    }
    
    public final long readInteger(final NativeConstants.IntegerDataType type) {
        final byte[] b = this.byteBuffer;
        switch (type) {
            case INT1: {
                return b[this.position++] & 0xFF;
            }
            case INT2: {
                return (b[this.position++] & 0xFF) | (b[this.position++] & 0xFF) << 8;
            }
            case INT3: {
                return (b[this.position++] & 0xFF) | (b[this.position++] & 0xFF) << 8 | (b[this.position++] & 0xFF) << 16;
            }
            case INT4: {
                return ((long)b[this.position++] & 0xFFL) | ((long)b[this.position++] & 0xFFL) << 8 | (long)(b[this.position++] & 0xFF) << 16 | (long)(b[this.position++] & 0xFF) << 24;
            }
            case INT6: {
                return (long)(b[this.position++] & 0xFF) | (long)(b[this.position++] & 0xFF) << 8 | (long)(b[this.position++] & 0xFF) << 16 | (long)(b[this.position++] & 0xFF) << 24 | (long)(b[this.position++] & 0xFF) << 32 | (long)(b[this.position++] & 0xFF) << 40;
            }
            case INT8: {
                return (long)(b[this.position++] & 0xFF) | (long)(b[this.position++] & 0xFF) << 8 | (long)(b[this.position++] & 0xFF) << 16 | (long)(b[this.position++] & 0xFF) << 24 | (long)(b[this.position++] & 0xFF) << 32 | (long)(b[this.position++] & 0xFF) << 40 | (long)(b[this.position++] & 0xFF) << 48 | (long)(b[this.position++] & 0xFF) << 56;
            }
            case INT_LENENC: {
                final int sw = b[this.position++] & 0xFF;
                switch (sw) {
                    case 251: {
                        return -1L;
                    }
                    case 252: {
                        return this.readInteger(NativeConstants.IntegerDataType.INT2);
                    }
                    case 253: {
                        return this.readInteger(NativeConstants.IntegerDataType.INT3);
                    }
                    case 254: {
                        return this.readInteger(NativeConstants.IntegerDataType.INT8);
                    }
                    default: {
                        return sw;
                    }
                }
                break;
            }
            default: {
                return b[this.position++] & 0xFF;
            }
        }
    }
    
    public final void writeBytes(final NativeConstants.StringSelfDataType type, final byte[] b) {
        this.writeBytes(type, b, 0, b.length);
    }
    
    public final void writeBytes(final NativeConstants.StringLengthDataType type, final byte[] b) {
        this.writeBytes(type, b, 0, b.length);
    }
    
    public void writeBytes(final NativeConstants.StringSelfDataType type, final byte[] b, final int offset, final int len) {
        switch (type) {
            case STRING_EOF: {
                this.writeBytes(NativeConstants.StringLengthDataType.STRING_FIXED, b, offset, len);
                break;
            }
            case STRING_TERM: {
                this.ensureCapacity(len + 1);
                this.writeBytes(NativeConstants.StringLengthDataType.STRING_FIXED, b, offset, len);
                this.byteBuffer[this.position++] = 0;
                break;
            }
            case STRING_LENENC: {
                this.ensureCapacity(len + 9);
                this.writeInteger(NativeConstants.IntegerDataType.INT_LENENC, len);
                this.writeBytes(NativeConstants.StringLengthDataType.STRING_FIXED, b, offset, len);
                break;
            }
        }
        this.adjustPayloadLength();
    }
    
    public void writeBytes(final NativeConstants.StringLengthDataType type, final byte[] b, final int offset, final int len) {
        switch (type) {
            case STRING_FIXED:
            case STRING_VAR: {
                this.ensureCapacity(len);
                System.arraycopy(b, offset, this.byteBuffer, this.position, len);
                this.position += len;
                break;
            }
        }
        this.adjustPayloadLength();
    }
    
    public byte[] readBytes(final NativeConstants.StringSelfDataType type) {
        switch (type) {
            case STRING_TERM: {
                int i;
                for (i = this.position; i < this.payloadLength && this.byteBuffer[i] != 0; ++i) {}
                final byte[] b = this.readBytes(NativeConstants.StringLengthDataType.STRING_FIXED, i - this.position);
                ++this.position;
                return b;
            }
            case STRING_LENENC: {
                final long l = this.readInteger(NativeConstants.IntegerDataType.INT_LENENC);
                return (byte[])((l == -1L) ? null : ((l == 0L) ? Constants.EMPTY_BYTE_ARRAY : this.readBytes(NativeConstants.StringLengthDataType.STRING_FIXED, (int)l)));
            }
            case STRING_EOF: {
                return this.readBytes(NativeConstants.StringLengthDataType.STRING_FIXED, this.payloadLength - this.position);
            }
            default: {
                return null;
            }
        }
    }
    
    public void skipBytes(final NativeConstants.StringSelfDataType type) {
        switch (type) {
            case STRING_TERM: {
                while (this.position < this.payloadLength && this.byteBuffer[this.position] != 0) {
                    ++this.position;
                }
                ++this.position;
                break;
            }
            case STRING_LENENC: {
                final long len = this.readInteger(NativeConstants.IntegerDataType.INT_LENENC);
                if (len != -1L && len != 0L) {
                    this.position += (int)len;
                    break;
                }
                break;
            }
            case STRING_EOF: {
                this.position = this.payloadLength;
                break;
            }
        }
    }
    
    public byte[] readBytes(final NativeConstants.StringLengthDataType type, final int len) {
        switch (type) {
            case STRING_FIXED:
            case STRING_VAR: {
                final byte[] b = new byte[len];
                System.arraycopy(this.byteBuffer, this.position, b, 0, len);
                this.position += len;
                return b;
            }
            default: {
                return null;
            }
        }
    }
    
    public String readString(final NativeConstants.StringSelfDataType type, final String encoding) {
        String res = null;
        switch (type) {
            case STRING_TERM: {
                int i;
                for (i = this.position; i < this.payloadLength && this.byteBuffer[i] != 0; ++i) {}
                res = this.readString(NativeConstants.StringLengthDataType.STRING_FIXED, encoding, i - this.position);
                ++this.position;
                break;
            }
            case STRING_LENENC: {
                final long l = this.readInteger(NativeConstants.IntegerDataType.INT_LENENC);
                return (l == -1L) ? null : ((l == 0L) ? "" : this.readString(NativeConstants.StringLengthDataType.STRING_FIXED, encoding, (int)l));
            }
            case STRING_EOF: {
                return this.readString(NativeConstants.StringLengthDataType.STRING_FIXED, encoding, this.payloadLength - this.position);
            }
        }
        return res;
    }
    
    public String readString(final NativeConstants.StringLengthDataType type, final String encoding, final int len) {
        String res = null;
        switch (type) {
            case STRING_FIXED:
            case STRING_VAR: {
                if (this.position + len > this.payloadLength) {
                    throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("Buffer.1"));
                }
                res = StringUtils.toString(this.byteBuffer, this.position, len, encoding);
                this.position += len;
                break;
            }
        }
        return res;
    }
    
    public static String extractSqlFromPacket(final String possibleSqlQuery, final NativePacketPayload packet, final int endOfQueryPacketPosition, final int maxQuerySizeToLog) {
        String extractedSql = null;
        if (possibleSqlQuery != null) {
            if (possibleSqlQuery.length() > maxQuerySizeToLog) {
                final StringBuilder truncatedQueryBuf = new StringBuilder(possibleSqlQuery.substring(0, maxQuerySizeToLog));
                truncatedQueryBuf.append(Messages.getString("MysqlIO.25"));
                extractedSql = truncatedQueryBuf.toString();
            }
            else {
                extractedSql = possibleSqlQuery;
            }
        }
        if (extractedSql == null) {
            int extractPosition = endOfQueryPacketPosition;
            boolean truncated = false;
            if (endOfQueryPacketPosition > maxQuerySizeToLog) {
                extractPosition = maxQuerySizeToLog;
                truncated = true;
            }
            extractedSql = StringUtils.toString(packet.getByteBuffer(), 1, extractPosition - 1);
            if (truncated) {
                extractedSql += Messages.getString("MysqlIO.25");
            }
        }
        return extractedSql;
    }
}
