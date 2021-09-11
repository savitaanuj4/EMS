
package com.mysql.cj.protocol.x;

import com.mysql.cj.protocol.Message;
import java.io.UnsupportedEncodingException;
import com.mysql.cj.exceptions.WrongArgumentException;
import com.mysql.cj.MysqlType;
import com.mysql.cj.CharsetMapping;
import com.mysql.cj.util.LazyString;
import com.mysql.cj.x.protobuf.MysqlxResultset;
import com.mysql.cj.result.Field;
import com.mysql.cj.protocol.ProtocolEntityFactory;

public class FieldFactory implements ProtocolEntityFactory<Field, XMessage>
{
    private static final int XPROTOCOL_COLUMN_BYTES_CONTENT_TYPE_GEOMETRY = 1;
    private static final int XPROTOCOL_COLUMN_BYTES_CONTENT_TYPE_JSON = 2;
    private static final int XPROTOCOL_COLUMN_FLAGS_UINT_ZEROFILL = 1;
    private static final int XPROTOCOL_COLUMN_FLAGS_DOUBLE_UNSIGNED = 1;
    private static final int XPROTOCOL_COLUMN_FLAGS_FLOAT_UNSIGNED = 1;
    private static final int XPROTOCOL_COLUMN_FLAGS_DECIMAL_UNSIGNED = 1;
    private static final int XPROTOCOL_COLUMN_FLAGS_BYTES_RIGHTPAD = 1;
    private static final int XPROTOCOL_COLUMN_FLAGS_DATETIME_TIMESTAMP = 1;
    private static final int XPROTOCOL_COLUMN_FLAGS_NOT_NULL = 16;
    private static final int XPROTOCOL_COLUMN_FLAGS_PRIMARY_KEY = 32;
    private static final int XPROTOCOL_COLUMN_FLAGS_UNIQUE_KEY = 64;
    private static final int XPROTOCOL_COLUMN_FLAGS_MULTIPLE_KEY = 128;
    private static final int XPROTOCOL_COLUMN_FLAGS_AUTO_INCREMENT = 256;
    String metadataCharacterSet;
    
    public FieldFactory(final String metadataCharSet) {
        this.metadataCharacterSet = metadataCharSet;
    }
    
    @Override
    public Field createFromMessage(final XMessage message) {
        return this.columnMetaDataToField((MysqlxResultset.ColumnMetaData)message.getMessage(), this.metadataCharacterSet);
    }
    
    private Field columnMetaDataToField(final MysqlxResultset.ColumnMetaData col, final String characterSet) {
        try {
            final LazyString databaseName = new LazyString(col.getSchema().toString(characterSet));
            final LazyString tableName = new LazyString(col.getTable().toString(characterSet));
            final LazyString originalTableName = new LazyString(col.getOriginalTable().toString(characterSet));
            final LazyString columnName = new LazyString(col.getName().toString(characterSet));
            final LazyString originalColumnName = new LazyString(col.getOriginalName().toString(characterSet));
            final long length = Integer.toUnsignedLong(col.getLength());
            final int decimals = col.getFractionalDigits();
            int collationIndex = 0;
            if (col.hasCollation()) {
                collationIndex = (int)col.getCollation();
            }
            final String encoding = CharsetMapping.COLLATION_INDEX_TO_COLLATION_NAME[collationIndex];
            MysqlType mysqlType = this.findMysqlType(col.getType(), col.getContentType(), col.getFlags(), collationIndex);
            final int mysqlTypeId = this.xProtocolTypeToMysqlType(col.getType(), col.getContentType());
            short flags = 0;
            if (col.getType().equals(MysqlxResultset.ColumnMetaData.FieldType.UINT) && 0 < (col.getFlags() & 0x1)) {
                flags |= 0x40;
            }
            else if (col.getType().equals(MysqlxResultset.ColumnMetaData.FieldType.BYTES) && 0 < (col.getFlags() & 0x1)) {
                mysqlType = MysqlType.CHAR;
            }
            else if (col.getType().equals(MysqlxResultset.ColumnMetaData.FieldType.DATETIME) && 0 < (col.getFlags() & 0x1)) {
                mysqlType = MysqlType.TIMESTAMP;
            }
            if ((col.getFlags() & 0x10) > 0) {
                flags |= 0x1;
            }
            if ((col.getFlags() & 0x20) > 0) {
                flags |= 0x2;
            }
            if ((col.getFlags() & 0x40) > 0) {
                flags |= 0x4;
            }
            if ((col.getFlags() & 0x80) > 0) {
                flags |= 0x8;
            }
            if ((col.getFlags() & 0x100) > 0) {
                flags |= 0x200;
            }
            final Field f = new Field(databaseName, tableName, originalTableName, columnName, originalColumnName, length, mysqlTypeId, flags, decimals, collationIndex, encoding, mysqlType);
            return f;
        }
        catch (UnsupportedEncodingException ex) {
            throw new WrongArgumentException("Unable to decode metadata strings", ex);
        }
    }
    
    private MysqlType findMysqlType(final MysqlxResultset.ColumnMetaData.FieldType type, final int contentType, final int flags, final int collationIndex) {
        switch (type) {
            case SINT: {
                return MysqlType.BIGINT;
            }
            case UINT: {
                return MysqlType.BIGINT_UNSIGNED;
            }
            case FLOAT: {
                return (0 < (flags & 0x1)) ? MysqlType.FLOAT_UNSIGNED : MysqlType.FLOAT;
            }
            case DOUBLE: {
                return (0 < (flags & 0x1)) ? MysqlType.DOUBLE_UNSIGNED : MysqlType.DOUBLE;
            }
            case DECIMAL: {
                return (0 < (flags & 0x1)) ? MysqlType.DECIMAL_UNSIGNED : MysqlType.DECIMAL;
            }
            case BYTES: {
                switch (contentType) {
                    case 1: {
                        return MysqlType.GEOMETRY;
                    }
                    case 2: {
                        return MysqlType.JSON;
                    }
                    default: {
                        if (collationIndex == 33) {
                            return MysqlType.VARBINARY;
                        }
                        return MysqlType.VARCHAR;
                    }
                }
                break;
            }
            case TIME: {
                return MysqlType.TIME;
            }
            case DATETIME: {
                return MysqlType.DATETIME;
            }
            case SET: {
                return MysqlType.SET;
            }
            case ENUM: {
                return MysqlType.ENUM;
            }
            case BIT: {
                return MysqlType.BIT;
            }
            default: {
                throw new WrongArgumentException("TODO: unknown field type: " + type);
            }
        }
    }
    
    private int xProtocolTypeToMysqlType(final MysqlxResultset.ColumnMetaData.FieldType type, final int contentType) {
        switch (type) {
            case SINT: {
                return 8;
            }
            case UINT: {
                return 8;
            }
            case FLOAT: {
                return 4;
            }
            case DOUBLE: {
                return 5;
            }
            case DECIMAL: {
                return 246;
            }
            case BYTES: {
                switch (contentType) {
                    case 1: {
                        return 255;
                    }
                    case 2: {
                        return 245;
                    }
                    default: {
                        return 15;
                    }
                }
                break;
            }
            case TIME: {
                return 11;
            }
            case DATETIME: {
                return 12;
            }
            case SET: {
                return 248;
            }
            case ENUM: {
                return 247;
            }
            case BIT: {
                return 16;
            }
            default: {
                throw new WrongArgumentException("TODO: unknown field type: " + type);
            }
        }
    }
}
