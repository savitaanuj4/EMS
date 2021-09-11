
package com.mysql.cj.protocol.a;

import java.io.IOException;
import com.mysql.cj.protocol.ProtocolEntity;
import com.mysql.cj.MysqlType;
import com.mysql.cj.util.LazyString;
import com.mysql.cj.result.Field;
import com.mysql.cj.protocol.ProtocolEntityFactory;
import com.mysql.cj.protocol.ColumnDefinition;
import com.mysql.cj.protocol.ProtocolEntityReader;

public class ColumnDefinitionReader implements ProtocolEntityReader<ColumnDefinition, NativePacketPayload>
{
    private NativeProtocol protocol;
    
    public ColumnDefinitionReader(final NativeProtocol prot) {
        this.protocol = prot;
    }
    
    @Override
    public ColumnDefinition read(final ProtocolEntityFactory<ColumnDefinition, NativePacketPayload> sf) {
        final ColumnDefinitionFactory cdf = (ColumnDefinitionFactory)sf;
        final long columnCount = cdf.getColumnCount();
        final ColumnDefinition cdef = cdf.getColumnDefinitionFromCache();
        if (cdef != null && !cdf.mergeColumnDefinitions()) {
            for (int i = 0; i < columnCount; ++i) {
                this.protocol.skipPacket();
            }
            return cdef;
        }
        Field[] fields = null;
        final boolean checkEOF = !this.protocol.getServerSession().isEOFDeprecated();
        fields = new Field[(int)columnCount];
        for (int j = 0; j < columnCount; ++j) {
            final NativePacketPayload fieldPacket = this.protocol.readMessage(null);
            if (checkEOF && fieldPacket.isEOFPacket()) {
                break;
            }
            fields[j] = this.unpackField(fieldPacket, this.protocol.getServerSession().getCharacterSetMetadata());
        }
        return cdf.createFromFields(fields);
    }
    
    protected Field unpackField(final NativePacketPayload packet, final String characterSetMetadata) {
        int length = (int)packet.readInteger(NativeConstants.IntegerDataType.INT_LENENC);
        packet.setPosition(packet.getPosition() + length);
        length = (int)packet.readInteger(NativeConstants.IntegerDataType.INT_LENENC);
        int offset = packet.getPosition();
        final LazyString databaseName = new LazyString(packet.getByteBuffer(), offset, length, characterSetMetadata);
        packet.setPosition(packet.getPosition() + length);
        length = (int)packet.readInteger(NativeConstants.IntegerDataType.INT_LENENC);
        offset = packet.getPosition();
        final LazyString tableName = new LazyString(packet.getByteBuffer(), offset, length, characterSetMetadata);
        packet.setPosition(packet.getPosition() + length);
        length = (int)packet.readInteger(NativeConstants.IntegerDataType.INT_LENENC);
        offset = packet.getPosition();
        final LazyString originalTableName = new LazyString(packet.getByteBuffer(), offset, length, characterSetMetadata);
        packet.setPosition(packet.getPosition() + length);
        length = (int)packet.readInteger(NativeConstants.IntegerDataType.INT_LENENC);
        offset = packet.getPosition();
        final LazyString columnName = new LazyString(packet.getByteBuffer(), offset, length, characterSetMetadata);
        packet.setPosition(packet.getPosition() + length);
        length = (int)packet.readInteger(NativeConstants.IntegerDataType.INT_LENENC);
        offset = packet.getPosition();
        final LazyString originalColumnName = new LazyString(packet.getByteBuffer(), offset, length, characterSetMetadata);
        packet.setPosition(packet.getPosition() + length);
        packet.readInteger(NativeConstants.IntegerDataType.INT1);
        final short collationIndex = (short)packet.readInteger(NativeConstants.IntegerDataType.INT2);
        final long colLength = packet.readInteger(NativeConstants.IntegerDataType.INT4);
        final int colType = (int)packet.readInteger(NativeConstants.IntegerDataType.INT1);
        final short colFlag = (short)packet.readInteger(this.protocol.getServerSession().hasLongColumnInfo() ? NativeConstants.IntegerDataType.INT2 : NativeConstants.IntegerDataType.INT1);
        final int colDecimals = (int)packet.readInteger(NativeConstants.IntegerDataType.INT1);
        final String encoding = this.protocol.getServerSession().getEncodingForIndex(collationIndex);
        final MysqlType mysqlType = NativeProtocol.findMysqlType(this.protocol.getPropertySet(), colType, colFlag, colLength, tableName, originalTableName, collationIndex, encoding);
        return new Field(databaseName, tableName, originalTableName, columnName, originalColumnName, colLength, colType, colFlag, colDecimals, collationIndex, encoding, mysqlType);
    }
}
