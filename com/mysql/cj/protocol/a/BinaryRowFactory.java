
package com.mysql.cj.protocol.a;

import com.mysql.cj.protocol.Message;
import com.mysql.cj.exceptions.ExceptionFactory;
import com.mysql.cj.Messages;
import com.mysql.cj.protocol.ValueDecoder;
import com.mysql.cj.protocol.a.result.ByteArrayRow;
import com.mysql.cj.result.Field;
import com.mysql.cj.protocol.a.result.BinaryBufferRow;
import com.mysql.cj.conf.PropertyKey;
import com.mysql.cj.protocol.Resultset;
import com.mysql.cj.protocol.ColumnDefinition;
import com.mysql.cj.protocol.ResultsetRow;
import com.mysql.cj.protocol.ProtocolEntityFactory;

public class BinaryRowFactory extends AbstractRowFactory implements ProtocolEntityFactory<ResultsetRow, NativePacketPayload>
{
    public BinaryRowFactory(final NativeProtocol protocol, final ColumnDefinition columnDefinition, final Resultset.Concurrency resultSetConcurrency, final boolean canReuseRowPacketForBufferRow) {
        this.columnDefinition = columnDefinition;
        this.resultSetConcurrency = resultSetConcurrency;
        this.canReuseRowPacketForBufferRow = canReuseRowPacketForBufferRow;
        this.useBufferRowSizeThreshold = protocol.getPropertySet().getMemorySizeProperty(PropertyKey.largeRowSizeThreshold);
        this.exceptionInterceptor = protocol.getExceptionInterceptor();
        this.valueDecoder = new MysqlBinaryValueDecoder();
    }
    
    @Override
    public ResultsetRow createFromMessage(final NativePacketPayload rowPacket) {
        final boolean useBufferRow = this.canReuseRowPacketForBufferRow || this.columnDefinition.hasLargeFields() || rowPacket.getPayloadLength() >= this.useBufferRowSizeThreshold.getValue();
        rowPacket.setPosition(rowPacket.getPosition() + 1);
        if (this.resultSetConcurrency == Resultset.Concurrency.UPDATABLE || !useBufferRow) {
            return this.unpackBinaryResultSetRow(this.columnDefinition.getFields(), rowPacket);
        }
        return new BinaryBufferRow(rowPacket, this.columnDefinition, this.exceptionInterceptor, this.valueDecoder);
    }
    
    @Override
    public boolean canReuseRowPacketForBufferRow() {
        return this.canReuseRowPacketForBufferRow;
    }
    
    private final ResultsetRow unpackBinaryResultSetRow(final Field[] fields, final NativePacketPayload binaryData) {
        final int numFields = fields.length;
        final byte[][] unpackedRowBytes = new byte[numFields][];
        final int nullCount = (numFields + 9) / 8;
        int nullMaskPos = binaryData.getPosition();
        binaryData.setPosition(nullMaskPos + nullCount);
        int bit = 4;
        final byte[] buf = binaryData.getByteBuffer();
        for (int i = 0; i < numFields; ++i) {
            if ((buf[nullMaskPos] & bit) != 0x0) {
                unpackedRowBytes[i] = null;
            }
            else {
                this.extractNativeEncodedColumn(binaryData, fields, i, unpackedRowBytes);
            }
            if (((bit <<= 1) & 0xFF) == 0x0) {
                bit = 1;
                ++nullMaskPos;
            }
        }
        return new ByteArrayRow(unpackedRowBytes, this.exceptionInterceptor, new MysqlBinaryValueDecoder());
    }
    
    private final void extractNativeEncodedColumn(final NativePacketPayload binaryData, final Field[] fields, final int columnIndex, final byte[][] unpackedRowData) {
        final int type = fields[columnIndex].getMysqlTypeId();
        final int len = NativeUtils.getBinaryEncodedLength(type);
        if (type != 6) {
            if (len == 0) {
                unpackedRowData[columnIndex] = binaryData.readBytes(NativeConstants.StringSelfDataType.STRING_LENENC);
            }
            else {
                if (len <= 0) {
                    throw ExceptionFactory.createException(Messages.getString("MysqlIO.97", new Object[] { type, columnIndex, fields.length }));
                }
                unpackedRowData[columnIndex] = binaryData.readBytes(NativeConstants.StringLengthDataType.STRING_FIXED, len);
            }
        }
    }
}
