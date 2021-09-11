
package com.mysql.cj.protocol.x;

import com.google.protobuf.ByteString;
import com.mysql.cj.result.Field;
import com.mysql.cj.exceptions.DataReadException;
import com.mysql.cj.result.ValueFactory;
import com.mysql.cj.x.protobuf.MysqlxResultset;
import com.mysql.cj.protocol.ColumnDefinition;
import com.mysql.cj.result.Row;

public class XProtocolRow implements Row
{
    private ColumnDefinition metadata;
    private MysqlxResultset.Row rowMessage;
    private boolean wasNull;
    
    public XProtocolRow(final ColumnDefinition metadata, final MysqlxResultset.Row rowMessage) {
        this.wasNull = false;
        this.metadata = metadata;
        this.rowMessage = rowMessage;
    }
    
    @Override
    public <T> T getValue(final int columnIndex, final ValueFactory<T> vf) {
        if (columnIndex >= this.metadata.getFields().length) {
            throw new DataReadException("Invalid column");
        }
        final Field f = this.metadata.getFields()[columnIndex];
        final ByteString byteString = this.rowMessage.getField(columnIndex);
        if (byteString.size() == 0) {
            final T result = vf.createFromNull();
            this.wasNull = (result == null);
            return result;
        }
        switch (f.getMysqlTypeId()) {
            case 16: {
                this.wasNull = false;
                return XProtocolDecoder.instance.decodeBit(byteString.toByteArray(), 0, byteString.size(), vf);
            }
            case 12: {
                this.wasNull = false;
                return XProtocolDecoder.instance.decodeTimestamp(byteString.toByteArray(), 0, byteString.size(), vf);
            }
            case 5: {
                this.wasNull = false;
                return XProtocolDecoder.instance.decodeDouble(byteString.toByteArray(), 0, byteString.size(), vf);
            }
            case 247: {
                this.wasNull = false;
                return XProtocolDecoder.instance.decodeByteArray(byteString.toByteArray(), 0, byteString.size(), vf);
            }
            case 4: {
                this.wasNull = false;
                return XProtocolDecoder.instance.decodeFloat(byteString.toByteArray(), 0, byteString.size(), vf);
            }
            case 245: {
                this.wasNull = false;
                return XProtocolDecoder.instance.decodeByteArray(byteString.toByteArray(), 0, byteString.size(), vf);
            }
            case 8: {
                this.wasNull = false;
                if (f.isUnsigned()) {
                    return XProtocolDecoder.instance.decodeUInt8(byteString.toByteArray(), 0, byteString.size(), vf);
                }
                return XProtocolDecoder.instance.decodeInt8(byteString.toByteArray(), 0, byteString.size(), vf);
            }
            case 246: {
                this.wasNull = false;
                return XProtocolDecoder.instance.decodeDecimal(byteString.toByteArray(), 0, byteString.size(), vf);
            }
            case 248: {
                this.wasNull = false;
                return XProtocolDecoder.instance.decodeSet(byteString.toByteArray(), 0, byteString.size(), vf);
            }
            case 11: {
                this.wasNull = false;
                return XProtocolDecoder.instance.decodeTime(byteString.toByteArray(), 0, byteString.size(), vf);
            }
            case 15: {
                this.wasNull = false;
                return XProtocolDecoder.instance.decodeByteArray(byteString.toByteArray(), 0, byteString.size(), vf);
            }
            case 253: {
                this.wasNull = false;
                return XProtocolDecoder.instance.decodeByteArray(byteString.toByteArray(), 0, byteString.size(), vf);
            }
            default: {
                throw new DataReadException("Unknown MySQL type constant: " + f.getMysqlTypeId());
            }
        }
    }
    
    @Override
    public boolean getNull(final int columnIndex) {
        final ByteString byteString = this.rowMessage.getField(columnIndex);
        return this.wasNull = (byteString.size() == 0);
    }
    
    @Override
    public boolean wasNull() {
        return this.wasNull;
    }
}
