
package com.mysql.cj.protocol.a;

import com.mysql.cj.protocol.Message;
import com.mysql.cj.protocol.a.result.TextBufferRow;
import com.mysql.cj.protocol.a.result.ByteArrayRow;
import com.mysql.cj.conf.PropertyKey;
import com.mysql.cj.protocol.Resultset;
import com.mysql.cj.protocol.ColumnDefinition;
import com.mysql.cj.protocol.ResultsetRow;
import com.mysql.cj.protocol.ProtocolEntityFactory;

public class TextRowFactory extends AbstractRowFactory implements ProtocolEntityFactory<ResultsetRow, NativePacketPayload>
{
    public TextRowFactory(final NativeProtocol protocol, final ColumnDefinition colDefinition, final Resultset.Concurrency resultSetConcurrency, final boolean canReuseRowPacketForBufferRow) {
        this.columnDefinition = colDefinition;
        this.resultSetConcurrency = resultSetConcurrency;
        this.canReuseRowPacketForBufferRow = canReuseRowPacketForBufferRow;
        this.useBufferRowSizeThreshold = protocol.getPropertySet().getMemorySizeProperty(PropertyKey.largeRowSizeThreshold);
        this.exceptionInterceptor = protocol.getExceptionInterceptor();
        this.valueDecoder = new MysqlTextValueDecoder();
    }
    
    @Override
    public ResultsetRow createFromMessage(final NativePacketPayload rowPacket) {
        final boolean useBufferRow = this.canReuseRowPacketForBufferRow || this.columnDefinition.hasLargeFields() || rowPacket.getPayloadLength() >= this.useBufferRowSizeThreshold.getValue();
        if (this.resultSetConcurrency == Resultset.Concurrency.UPDATABLE || !useBufferRow) {
            final byte[][] rowBytes = new byte[this.columnDefinition.getFields().length][];
            for (int i = 0; i < this.columnDefinition.getFields().length; ++i) {
                rowBytes[i] = rowPacket.readBytes(NativeConstants.StringSelfDataType.STRING_LENENC);
            }
            return new ByteArrayRow(rowBytes, this.exceptionInterceptor);
        }
        return new TextBufferRow(rowPacket, this.columnDefinition, this.exceptionInterceptor, this.valueDecoder);
    }
    
    @Override
    public boolean canReuseRowPacketForBufferRow() {
        return this.canReuseRowPacketForBufferRow;
    }
}
