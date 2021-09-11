
package com.mysql.cj.result;

import com.mysql.cj.CharsetMapping;
import com.mysql.cj.ServerVersion;
import com.mysql.cj.MysqlType;
import com.mysql.cj.util.LazyString;

public class Field
{
    private int collationIndex;
    private String encoding;
    private int colDecimals;
    private short colFlag;
    private LazyString databaseName;
    private LazyString tableName;
    private LazyString originalTableName;
    private LazyString columnName;
    private LazyString originalColumnName;
    private String fullName;
    private long length;
    private int mysqlTypeId;
    private MysqlType mysqlType;
    
    public Field(final LazyString databaseName, final LazyString tableName, final LazyString originalTableName, final LazyString columnName, final LazyString originalColumnName, final long length, final int mysqlTypeId, final short colFlag, final int colDecimals, final int collationIndex, final String encoding, final MysqlType mysqlType) {
        this.collationIndex = 0;
        this.encoding = "US-ASCII";
        this.databaseName = null;
        this.tableName = null;
        this.originalTableName = null;
        this.columnName = null;
        this.originalColumnName = null;
        this.fullName = null;
        this.mysqlTypeId = -1;
        this.mysqlType = MysqlType.UNKNOWN;
        this.databaseName = databaseName;
        this.tableName = tableName;
        this.originalTableName = originalTableName;
        this.columnName = columnName;
        this.originalColumnName = originalColumnName;
        this.length = length;
        this.colFlag = colFlag;
        this.colDecimals = colDecimals;
        this.mysqlTypeId = mysqlTypeId;
        this.collationIndex = collationIndex;
        this.encoding = ("UnicodeBig".equals(encoding) ? "UTF-16" : encoding);
        if (mysqlType == MysqlType.JSON) {
            this.encoding = "UTF-8";
        }
        this.mysqlType = mysqlType;
        this.adjustFlagsByMysqlType();
    }
    
    private void adjustFlagsByMysqlType() {
        switch (this.mysqlType) {
            case BIT: {
                if (this.length > 1L) {
                    this.colFlag |= 0x80;
                    this.colFlag |= 0x10;
                    break;
                }
                break;
            }
            case BINARY:
            case VARBINARY: {
                this.colFlag |= 0x80;
                this.colFlag |= 0x10;
                break;
            }
            case DECIMAL_UNSIGNED:
            case TINYINT_UNSIGNED:
            case SMALLINT_UNSIGNED:
            case INT_UNSIGNED:
            case FLOAT_UNSIGNED:
            case DOUBLE_UNSIGNED:
            case BIGINT_UNSIGNED:
            case MEDIUMINT_UNSIGNED: {
                this.colFlag |= 0x20;
                break;
            }
        }
    }
    
    public Field(final String tableName, final String columnName, final int collationIndex, final String encoding, final MysqlType mysqlType, final int length) {
        this.collationIndex = 0;
        this.encoding = "US-ASCII";
        this.databaseName = null;
        this.tableName = null;
        this.originalTableName = null;
        this.columnName = null;
        this.originalColumnName = null;
        this.fullName = null;
        this.mysqlTypeId = -1;
        this.mysqlType = MysqlType.UNKNOWN;
        this.databaseName = new LazyString(null);
        this.tableName = new LazyString(tableName);
        this.originalTableName = new LazyString(null);
        this.columnName = new LazyString(columnName);
        this.originalColumnName = new LazyString(null);
        this.length = length;
        this.mysqlType = mysqlType;
        this.colFlag = 0;
        this.colDecimals = 0;
        this.adjustFlagsByMysqlType();
        switch (mysqlType) {
            case CHAR:
            case VARCHAR:
            case TINYTEXT:
            case TEXT:
            case MEDIUMTEXT:
            case LONGTEXT:
            case JSON: {
                this.collationIndex = collationIndex;
                this.encoding = ("UnicodeBig".equals(encoding) ? "UTF-16" : encoding);
                break;
            }
        }
    }
    
    public String getEncoding() {
        return this.encoding;
    }
    
    public void setEncoding(final String javaEncodingName, final ServerVersion version) {
        this.encoding = javaEncodingName;
        this.collationIndex = CharsetMapping.getCollationIndexForJavaEncoding(javaEncodingName, version);
    }
    
    public String getColumnLabel() {
        return this.getName();
    }
    
    public String getDatabaseName() {
        return this.databaseName.toString();
    }
    
    public int getDecimals() {
        return this.colDecimals;
    }
    
    public String getFullName() {
        if (this.fullName == null) {
            final StringBuilder fullNameBuf = new StringBuilder(this.tableName.length() + 1 + this.columnName.length());
            fullNameBuf.append(this.tableName.toString());
            fullNameBuf.append('.');
            fullNameBuf.append(this.columnName.toString());
            this.fullName = fullNameBuf.toString();
        }
        return this.fullName;
    }
    
    public long getLength() {
        return this.length;
    }
    
    public int getMysqlTypeId() {
        return this.mysqlTypeId;
    }
    
    public void setMysqlTypeId(final int id) {
        this.mysqlTypeId = id;
    }
    
    public String getName() {
        return this.columnName.toString();
    }
    
    public String getOriginalName() {
        return this.originalColumnName.toString();
    }
    
    public String getOriginalTableName() {
        return this.originalTableName.toString();
    }
    
    public int getJavaType() {
        return this.mysqlType.getJdbcType();
    }
    
    public String getTableName() {
        return this.tableName.toString();
    }
    
    public boolean isAutoIncrement() {
        return (this.colFlag & 0x200) > 0;
    }
    
    public boolean isBinary() {
        return (this.colFlag & 0x80) > 0;
    }
    
    public void setBinary() {
        this.colFlag |= 0x80;
    }
    
    public boolean isBlob() {
        return (this.colFlag & 0x10) > 0;
    }
    
    public void setBlob() {
        this.colFlag |= 0x10;
    }
    
    public boolean isMultipleKey() {
        return (this.colFlag & 0x8) > 0;
    }
    
    public boolean isNotNull() {
        return (this.colFlag & 0x1) > 0;
    }
    
    public boolean isPrimaryKey() {
        return (this.colFlag & 0x2) > 0;
    }
    
    public boolean isFromFunction() {
        return this.originalTableName.length() == 0;
    }
    
    public boolean isReadOnly() {
        return this.originalColumnName.length() == 0 && this.originalTableName.length() == 0;
    }
    
    public boolean isUniqueKey() {
        return (this.colFlag & 0x4) > 0;
    }
    
    public boolean isUnsigned() {
        return (this.colFlag & 0x20) > 0;
    }
    
    public boolean isZeroFill() {
        return (this.colFlag & 0x40) > 0;
    }
    
    @Override
    public String toString() {
        try {
            final StringBuilder asString = new StringBuilder();
            asString.append(super.toString());
            asString.append("[");
            asString.append("catalog=");
            asString.append(this.getDatabaseName());
            asString.append(",tableName=");
            asString.append(this.getTableName());
            asString.append(",originalTableName=");
            asString.append(this.getOriginalTableName());
            asString.append(",columnName=");
            asString.append(this.getName());
            asString.append(",originalColumnName=");
            asString.append(this.getOriginalName());
            asString.append(",mysqlType=");
            asString.append(this.getMysqlTypeId());
            asString.append("(");
            final MysqlType ft = this.getMysqlType();
            if (ft.equals(MysqlType.UNKNOWN)) {
                asString.append(" Unknown MySQL Type # ");
                asString.append(this.getMysqlTypeId());
            }
            else {
                asString.append("FIELD_TYPE_");
                asString.append(ft.getName());
            }
            asString.append(")");
            asString.append(",sqlType=");
            asString.append(ft.getJdbcType());
            asString.append(",flags=");
            if (this.isAutoIncrement()) {
                asString.append(" AUTO_INCREMENT");
            }
            if (this.isPrimaryKey()) {
                asString.append(" PRIMARY_KEY");
            }
            if (this.isUniqueKey()) {
                asString.append(" UNIQUE_KEY");
            }
            if (this.isBinary()) {
                asString.append(" BINARY");
            }
            if (this.isBlob()) {
                asString.append(" BLOB");
            }
            if (this.isMultipleKey()) {
                asString.append(" MULTI_KEY");
            }
            if (this.isUnsigned()) {
                asString.append(" UNSIGNED");
            }
            if (this.isZeroFill()) {
                asString.append(" ZEROFILL");
            }
            asString.append(", charsetIndex=");
            asString.append(this.collationIndex);
            asString.append(", charsetName=");
            asString.append(this.encoding);
            asString.append("]");
            return asString.toString();
        }
        catch (Throwable t) {
            return super.toString() + "[<unable to generate contents>]";
        }
    }
    
    public boolean isSingleBit() {
        return this.length <= 1L;
    }
    
    public boolean getValueNeedsQuoting() {
        switch (this.mysqlType) {
            case BIT:
            case DECIMAL_UNSIGNED:
            case TINYINT_UNSIGNED:
            case SMALLINT_UNSIGNED:
            case INT_UNSIGNED:
            case FLOAT_UNSIGNED:
            case DOUBLE_UNSIGNED:
            case BIGINT_UNSIGNED:
            case MEDIUMINT_UNSIGNED:
            case BIGINT:
            case DECIMAL:
            case DOUBLE:
            case INT:
            case MEDIUMINT:
            case FLOAT:
            case SMALLINT:
            case TINYINT: {
                return false;
            }
            default: {
                return true;
            }
        }
    }
    
    public int getCollationIndex() {
        return this.collationIndex;
    }
    
    public MysqlType getMysqlType() {
        return this.mysqlType;
    }
    
    public void setMysqlType(final MysqlType mysqlType) {
        this.mysqlType = mysqlType;
    }
    
    public short getFlags() {
        return this.colFlag;
    }
    
    public void setFlags(final short colFlag) {
        this.colFlag = colFlag;
    }
}
