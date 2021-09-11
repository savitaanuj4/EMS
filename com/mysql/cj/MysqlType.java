
package com.mysql.cj;

import java.sql.Time;
import java.sql.Date;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.math.BigDecimal;
import com.mysql.cj.exceptions.FeatureNotAvailableException;
import com.mysql.cj.util.StringUtils;
import java.sql.SQLType;

public enum MysqlType implements SQLType
{
    DECIMAL("DECIMAL", 3, (Class<?>)BigDecimal.class, 64, true, Long.valueOf(65L), "[(M[,D])] [UNSIGNED] [ZEROFILL]"), 
    DECIMAL_UNSIGNED("DECIMAL UNSIGNED", 3, (Class<?>)BigDecimal.class, 96, true, Long.valueOf(65L), "[(M[,D])] [UNSIGNED] [ZEROFILL]"), 
    TINYINT("TINYINT", -6, (Class<?>)Integer.class, 64, true, Long.valueOf(3L), "[(M)] [UNSIGNED] [ZEROFILL]"), 
    TINYINT_UNSIGNED("TINYINT UNSIGNED", -6, (Class<?>)Integer.class, 96, true, Long.valueOf(3L), "[(M)] [UNSIGNED] [ZEROFILL]"), 
    BOOLEAN("BOOLEAN", 16, (Class<?>)Boolean.class, 0, false, Long.valueOf(3L), ""), 
    SMALLINT("SMALLINT", 5, (Class<?>)Integer.class, 64, true, Long.valueOf(5L), "[(M)] [UNSIGNED] [ZEROFILL]"), 
    SMALLINT_UNSIGNED("SMALLINT UNSIGNED", 5, (Class<?>)Integer.class, 96, true, Long.valueOf(5L), "[(M)] [UNSIGNED] [ZEROFILL]"), 
    INT("INT", 4, (Class<?>)Integer.class, 64, true, Long.valueOf(10L), "[(M)] [UNSIGNED] [ZEROFILL]"), 
    INT_UNSIGNED("INT UNSIGNED", 4, (Class<?>)Long.class, 96, true, Long.valueOf(10L), "[(M)] [UNSIGNED] [ZEROFILL]"), 
    FLOAT("FLOAT", 7, (Class<?>)Float.class, 64, true, Long.valueOf(12L), "[(M,D)] [UNSIGNED] [ZEROFILL]"), 
    FLOAT_UNSIGNED("FLOAT UNSIGNED", 7, (Class<?>)Float.class, 96, true, Long.valueOf(12L), "[(M,D)] [UNSIGNED] [ZEROFILL]"), 
    DOUBLE("DOUBLE", 8, (Class<?>)Double.class, 64, true, Long.valueOf(22L), "[(M,D)] [UNSIGNED] [ZEROFILL]"), 
    DOUBLE_UNSIGNED("DOUBLE UNSIGNED", 8, (Class<?>)Double.class, 96, true, Long.valueOf(22L), "[(M,D)] [UNSIGNED] [ZEROFILL]"), 
    NULL("NULL", 0, (Class<?>)Object.class, 0, false, Long.valueOf(0L), ""), 
    TIMESTAMP("TIMESTAMP", 93, (Class<?>)Timestamp.class, 0, false, Long.valueOf(26L), "[(fsp)]"), 
    BIGINT("BIGINT", -5, (Class<?>)Long.class, 64, true, Long.valueOf(19L), "[(M)] [UNSIGNED] [ZEROFILL]"), 
    BIGINT_UNSIGNED("BIGINT UNSIGNED", -5, (Class<?>)BigInteger.class, 96, true, Long.valueOf(20L), "[(M)] [UNSIGNED] [ZEROFILL]"), 
    MEDIUMINT("MEDIUMINT", 4, (Class<?>)Integer.class, 64, true, Long.valueOf(7L), "[(M)] [UNSIGNED] [ZEROFILL]"), 
    MEDIUMINT_UNSIGNED("MEDIUMINT UNSIGNED", 4, (Class<?>)Integer.class, 96, true, Long.valueOf(8L), "[(M)] [UNSIGNED] [ZEROFILL]"), 
    DATE("DATE", 91, (Class<?>)Date.class, 0, false, Long.valueOf(10L), ""), 
    TIME("TIME", 92, (Class<?>)Time.class, 0, false, Long.valueOf(16L), "[(fsp)]"), 
    DATETIME("DATETIME", 93, (Class<?>)Timestamp.class, 0, false, Long.valueOf(26L), "[(fsp)]"), 
    YEAR("YEAR", 91, (Class<?>)Date.class, 0, false, Long.valueOf(4L), "[(4)]"), 
    VARCHAR("VARCHAR", 12, (Class<?>)String.class, 0, false, Long.valueOf(65535L), "(M) [CHARACTER SET charset_name] [COLLATE collation_name]"), 
    VARBINARY("VARBINARY", -3, (Class<?>)null, 0, false, Long.valueOf(65535L), "(M)"), 
    BIT("BIT", -7, (Class<?>)Boolean.class, 0, true, Long.valueOf(1L), "[(M)]"), 
    JSON("JSON", -1, (Class<?>)String.class, 0, false, Long.valueOf(1073741824L), ""), 
    ENUM("ENUM", 1, (Class<?>)String.class, 0, false, Long.valueOf(65535L), "('value1','value2',...) [CHARACTER SET charset_name] [COLLATE collation_name]"), 
    SET("SET", 1, (Class<?>)String.class, 0, false, Long.valueOf(64L), "('value1','value2',...) [CHARACTER SET charset_name] [COLLATE collation_name]"), 
    TINYBLOB("TINYBLOB", -3, (Class<?>)null, 0, false, Long.valueOf(255L), ""), 
    TINYTEXT("TINYTEXT", 12, (Class<?>)String.class, 0, false, Long.valueOf(255L), " [CHARACTER SET charset_name] [COLLATE collation_name]"), 
    MEDIUMBLOB("MEDIUMBLOB", -4, (Class<?>)null, 0, false, Long.valueOf(16777215L), ""), 
    MEDIUMTEXT("MEDIUMTEXT", -1, (Class<?>)String.class, 0, false, Long.valueOf(16777215L), " [CHARACTER SET charset_name] [COLLATE collation_name]"), 
    LONGBLOB("LONGBLOB", -4, (Class<?>)null, 0, false, Long.valueOf(4294967295L), ""), 
    LONGTEXT("LONGTEXT", -1, (Class<?>)String.class, 0, false, Long.valueOf(4294967295L), " [CHARACTER SET charset_name] [COLLATE collation_name]"), 
    BLOB("BLOB", -4, (Class<?>)null, 0, false, Long.valueOf(65535L), "[(M)]"), 
    TEXT("TEXT", -1, (Class<?>)String.class, 0, false, Long.valueOf(65535L), "[(M)] [CHARACTER SET charset_name] [COLLATE collation_name]"), 
    CHAR("CHAR", 1, (Class<?>)String.class, 0, false, Long.valueOf(255L), "[(M)] [CHARACTER SET charset_name] [COLLATE collation_name]"), 
    BINARY("BINARY", -2, (Class<?>)null, 0, false, Long.valueOf(255L), "(M)"), 
    GEOMETRY("GEOMETRY", -2, (Class<?>)null, 0, false, Long.valueOf(65535L), ""), 
    UNKNOWN("UNKNOWN", 1111, (Class<?>)null, 0, false, Long.valueOf(65535L), "");
    
    private final String name;
    protected int jdbcType;
    protected final Class<?> javaClass;
    private final int flagsMask;
    private final boolean isDecimal;
    private final Long precision;
    private final String createParams;
    public static final int FIELD_FLAG_NOT_NULL = 1;
    public static final int FIELD_FLAG_PRIMARY_KEY = 2;
    public static final int FIELD_FLAG_UNIQUE_KEY = 4;
    public static final int FIELD_FLAG_MULTIPLE_KEY = 8;
    public static final int FIELD_FLAG_BLOB = 16;
    public static final int FIELD_FLAG_UNSIGNED = 32;
    public static final int FIELD_FLAG_ZEROFILL = 64;
    public static final int FIELD_FLAG_BINARY = 128;
    public static final int FIELD_FLAG_AUTO_INCREMENT = 512;
    private static final boolean IS_DECIMAL = true;
    private static final boolean IS_NOT_DECIMAL = false;
    public static final int FIELD_TYPE_DECIMAL = 0;
    public static final int FIELD_TYPE_TINY = 1;
    public static final int FIELD_TYPE_SHORT = 2;
    public static final int FIELD_TYPE_LONG = 3;
    public static final int FIELD_TYPE_FLOAT = 4;
    public static final int FIELD_TYPE_DOUBLE = 5;
    public static final int FIELD_TYPE_NULL = 6;
    public static final int FIELD_TYPE_TIMESTAMP = 7;
    public static final int FIELD_TYPE_LONGLONG = 8;
    public static final int FIELD_TYPE_INT24 = 9;
    public static final int FIELD_TYPE_DATE = 10;
    public static final int FIELD_TYPE_TIME = 11;
    public static final int FIELD_TYPE_DATETIME = 12;
    public static final int FIELD_TYPE_YEAR = 13;
    public static final int FIELD_TYPE_VARCHAR = 15;
    public static final int FIELD_TYPE_BIT = 16;
    public static final int FIELD_TYPE_JSON = 245;
    public static final int FIELD_TYPE_NEWDECIMAL = 246;
    public static final int FIELD_TYPE_ENUM = 247;
    public static final int FIELD_TYPE_SET = 248;
    public static final int FIELD_TYPE_TINY_BLOB = 249;
    public static final int FIELD_TYPE_MEDIUM_BLOB = 250;
    public static final int FIELD_TYPE_LONG_BLOB = 251;
    public static final int FIELD_TYPE_BLOB = 252;
    public static final int FIELD_TYPE_VAR_STRING = 253;
    public static final int FIELD_TYPE_STRING = 254;
    public static final int FIELD_TYPE_GEOMETRY = 255;
    
    public static MysqlType getByName(final String fullMysqlTypeName) {
        String typeName = "";
        if (fullMysqlTypeName.indexOf("(") != -1) {
            typeName = fullMysqlTypeName.substring(0, fullMysqlTypeName.indexOf("(")).trim();
        }
        else {
            typeName = fullMysqlTypeName;
        }
        if (StringUtils.indexOfIgnoreCase(typeName, "DECIMAL") != -1 || StringUtils.indexOfIgnoreCase(typeName, "DEC") != -1 || StringUtils.indexOfIgnoreCase(typeName, "NUMERIC") != -1 || StringUtils.indexOfIgnoreCase(typeName, "FIXED") != -1) {
            return (StringUtils.indexOfIgnoreCase(fullMysqlTypeName, "UNSIGNED") != -1) ? MysqlType.DECIMAL_UNSIGNED : MysqlType.DECIMAL;
        }
        if (StringUtils.indexOfIgnoreCase(typeName, "TINYBLOB") != -1) {
            return MysqlType.TINYBLOB;
        }
        if (StringUtils.indexOfIgnoreCase(typeName, "TINYTEXT") != -1) {
            return MysqlType.TINYTEXT;
        }
        if (StringUtils.indexOfIgnoreCase(typeName, "TINYINT") != -1 || StringUtils.indexOfIgnoreCase(typeName, "TINY") != -1 || StringUtils.indexOfIgnoreCase(typeName, "INT1") != -1) {
            return (StringUtils.indexOfIgnoreCase(fullMysqlTypeName, "UNSIGNED") != -1) ? MysqlType.TINYINT_UNSIGNED : MysqlType.TINYINT;
        }
        if (StringUtils.indexOfIgnoreCase(typeName, "MEDIUMINT") != -1 || StringUtils.indexOfIgnoreCase(typeName, "INT24") != -1 || StringUtils.indexOfIgnoreCase(typeName, "INT3") != -1 || StringUtils.indexOfIgnoreCase(typeName, "MIDDLEINT") != -1) {
            return (StringUtils.indexOfIgnoreCase(fullMysqlTypeName, "UNSIGNED") != -1) ? MysqlType.MEDIUMINT_UNSIGNED : MysqlType.MEDIUMINT;
        }
        if (StringUtils.indexOfIgnoreCase(typeName, "SMALLINT") != -1 || StringUtils.indexOfIgnoreCase(typeName, "INT2") != -1) {
            return (StringUtils.indexOfIgnoreCase(fullMysqlTypeName, "UNSIGNED") != -1) ? MysqlType.SMALLINT_UNSIGNED : MysqlType.SMALLINT;
        }
        if (StringUtils.indexOfIgnoreCase(typeName, "BIGINT") != -1 || StringUtils.indexOfIgnoreCase(typeName, "SERIAL") != -1 || StringUtils.indexOfIgnoreCase(typeName, "INT8") != -1) {
            return (StringUtils.indexOfIgnoreCase(fullMysqlTypeName, "UNSIGNED") != -1) ? MysqlType.BIGINT_UNSIGNED : MysqlType.BIGINT;
        }
        if (StringUtils.indexOfIgnoreCase(typeName, "POINT") != -1) {
            return MysqlType.GEOMETRY;
        }
        if (StringUtils.indexOfIgnoreCase(typeName, "INT") != -1 || StringUtils.indexOfIgnoreCase(typeName, "INTEGER") != -1 || StringUtils.indexOfIgnoreCase(typeName, "INT4") != -1) {
            return (StringUtils.indexOfIgnoreCase(fullMysqlTypeName, "UNSIGNED") != -1) ? MysqlType.INT_UNSIGNED : MysqlType.INT;
        }
        if (StringUtils.indexOfIgnoreCase(typeName, "DOUBLE") != -1 || StringUtils.indexOfIgnoreCase(typeName, "REAL") != -1 || StringUtils.indexOfIgnoreCase(typeName, "FLOAT8") != -1) {
            return (StringUtils.indexOfIgnoreCase(fullMysqlTypeName, "UNSIGNED") != -1) ? MysqlType.DOUBLE_UNSIGNED : MysqlType.DOUBLE;
        }
        if (StringUtils.indexOfIgnoreCase(typeName, "FLOAT") != -1) {
            return (StringUtils.indexOfIgnoreCase(fullMysqlTypeName, "UNSIGNED") != -1) ? MysqlType.FLOAT_UNSIGNED : MysqlType.FLOAT;
        }
        if (StringUtils.indexOfIgnoreCase(typeName, "NULL") != -1) {
            return MysqlType.NULL;
        }
        if (StringUtils.indexOfIgnoreCase(typeName, "TIMESTAMP") != -1) {
            return MysqlType.TIMESTAMP;
        }
        if (StringUtils.indexOfIgnoreCase(typeName, "DATETIME") != -1) {
            return MysqlType.DATETIME;
        }
        if (StringUtils.indexOfIgnoreCase(typeName, "DATE") != -1) {
            return MysqlType.DATE;
        }
        if (StringUtils.indexOfIgnoreCase(typeName, "TIME") != -1) {
            return MysqlType.TIME;
        }
        if (StringUtils.indexOfIgnoreCase(typeName, "YEAR") != -1) {
            return MysqlType.YEAR;
        }
        if (StringUtils.indexOfIgnoreCase(typeName, "LONGBLOB") != -1) {
            return MysqlType.LONGBLOB;
        }
        if (StringUtils.indexOfIgnoreCase(typeName, "LONGTEXT") != -1) {
            return MysqlType.LONGTEXT;
        }
        if (StringUtils.indexOfIgnoreCase(typeName, "MEDIUMBLOB") != -1 || StringUtils.indexOfIgnoreCase(typeName, "LONG VARBINARY") != -1) {
            return MysqlType.MEDIUMBLOB;
        }
        if (StringUtils.indexOfIgnoreCase(typeName, "MEDIUMTEXT") != -1 || StringUtils.indexOfIgnoreCase(typeName, "LONG VARCHAR") != -1 || StringUtils.indexOfIgnoreCase(typeName, "LONG") != -1) {
            return MysqlType.MEDIUMTEXT;
        }
        if (StringUtils.indexOfIgnoreCase(typeName, "VARCHAR") != -1 || StringUtils.indexOfIgnoreCase(typeName, "NVARCHAR") != -1 || StringUtils.indexOfIgnoreCase(typeName, "NATIONAL VARCHAR") != -1 || StringUtils.indexOfIgnoreCase(typeName, "CHARACTER VARYING") != -1) {
            return MysqlType.VARCHAR;
        }
        if (StringUtils.indexOfIgnoreCase(typeName, "VARBINARY") != -1) {
            return MysqlType.VARBINARY;
        }
        if (StringUtils.indexOfIgnoreCase(typeName, "BINARY") != -1 || StringUtils.indexOfIgnoreCase(typeName, "CHAR BYTE") != -1) {
            return MysqlType.BINARY;
        }
        if (StringUtils.indexOfIgnoreCase(typeName, "LINESTRING") != -1) {
            return MysqlType.GEOMETRY;
        }
        if (StringUtils.indexOfIgnoreCase(typeName, "STRING") != -1 || StringUtils.indexOfIgnoreCase(typeName, "CHAR") != -1 || StringUtils.indexOfIgnoreCase(typeName, "NCHAR") != -1 || StringUtils.indexOfIgnoreCase(typeName, "NATIONAL CHAR") != -1 || StringUtils.indexOfIgnoreCase(typeName, "CHARACTER") != -1) {
            return MysqlType.CHAR;
        }
        if (StringUtils.indexOfIgnoreCase(typeName, "BOOLEAN") != -1 || StringUtils.indexOfIgnoreCase(typeName, "BOOL") != -1) {
            return MysqlType.BOOLEAN;
        }
        if (StringUtils.indexOfIgnoreCase(typeName, "BIT") != -1) {
            return MysqlType.BIT;
        }
        if (StringUtils.indexOfIgnoreCase(typeName, "JSON") != -1) {
            return MysqlType.JSON;
        }
        if (StringUtils.indexOfIgnoreCase(typeName, "ENUM") != -1) {
            return MysqlType.ENUM;
        }
        if (StringUtils.indexOfIgnoreCase(typeName, "SET") != -1) {
            return MysqlType.SET;
        }
        if (StringUtils.indexOfIgnoreCase(typeName, "BLOB") != -1) {
            return MysqlType.BLOB;
        }
        if (StringUtils.indexOfIgnoreCase(typeName, "TEXT") != -1) {
            return MysqlType.TEXT;
        }
        if (StringUtils.indexOfIgnoreCase(typeName, "GEOM") != -1 || StringUtils.indexOfIgnoreCase(typeName, "POINT") != -1 || StringUtils.indexOfIgnoreCase(typeName, "POLYGON") != -1) {
            return MysqlType.GEOMETRY;
        }
        return MysqlType.UNKNOWN;
    }
    
    public static MysqlType getByJdbcType(final int jdbcType) {
        switch (jdbcType) {
            case -5: {
                return MysqlType.BIGINT;
            }
            case -2: {
                return MysqlType.BINARY;
            }
            case -7: {
                return MysqlType.BIT;
            }
            case 16: {
                return MysqlType.BOOLEAN;
            }
            case -15:
            case 1: {
                return MysqlType.CHAR;
            }
            case 91: {
                return MysqlType.DATE;
            }
            case 2:
            case 3: {
                return MysqlType.DECIMAL;
            }
            case 6:
            case 8: {
                return MysqlType.DOUBLE;
            }
            case 4: {
                return MysqlType.INT;
            }
            case -4:
            case 2000:
            case 2004: {
                return MysqlType.BLOB;
            }
            case -16:
            case -1:
            case 2005:
            case 2011: {
                return MysqlType.TEXT;
            }
            case 0: {
                return MysqlType.NULL;
            }
            case 7: {
                return MysqlType.FLOAT;
            }
            case 5: {
                return MysqlType.SMALLINT;
            }
            case 92: {
                return MysqlType.TIME;
            }
            case 93: {
                return MysqlType.TIMESTAMP;
            }
            case -6: {
                return MysqlType.TINYINT;
            }
            case -3: {
                return MysqlType.VARBINARY;
            }
            case -9:
            case 12:
            case 70:
            case 2009: {
                return MysqlType.VARCHAR;
            }
            case 2012: {
                throw new FeatureNotAvailableException("REF_CURSOR type is not supported");
            }
            case 2013: {
                throw new FeatureNotAvailableException("TIME_WITH_TIMEZONE type is not supported");
            }
            case 2014: {
                throw new FeatureNotAvailableException("TIMESTAMP_WITH_TIMEZONE type is not supported");
            }
            default: {
                return MysqlType.UNKNOWN;
            }
        }
    }
    
    public static boolean supportsConvert(final int fromType, final int toType) {
        switch (fromType) {
            case -4:
            case -3:
            case -2:
            case -1:
            case 1:
            case 12: {
                switch (toType) {
                    case -6:
                    case -5:
                    case -4:
                    case -3:
                    case -2:
                    case -1:
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                    case 8:
                    case 12:
                    case 91:
                    case 92:
                    case 93:
                    case 1111: {
                        return true;
                    }
                    default: {
                        return false;
                    }
                }
                break;
            }
            case -7: {
                return false;
            }
            case -6:
            case -5:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8: {
                switch (toType) {
                    case -6:
                    case -5:
                    case -4:
                    case -3:
                    case -2:
                    case -1:
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                    case 8:
                    case 12: {
                        return true;
                    }
                    default: {
                        return false;
                    }
                }
                break;
            }
            case 0: {
                return false;
            }
            case 1111: {
                switch (toType) {
                    case -4:
                    case -3:
                    case -2:
                    case -1:
                    case 1:
                    case 12: {
                        return true;
                    }
                    default: {
                        return false;
                    }
                }
                break;
            }
            case 91: {
                switch (toType) {
                    case -4:
                    case -3:
                    case -2:
                    case -1:
                    case 1:
                    case 12: {
                        return true;
                    }
                    default: {
                        return false;
                    }
                }
                break;
            }
            case 92: {
                switch (toType) {
                    case -4:
                    case -3:
                    case -2:
                    case -1:
                    case 1:
                    case 12: {
                        return true;
                    }
                    default: {
                        return false;
                    }
                }
                break;
            }
            case 93: {
                switch (toType) {
                    case -4:
                    case -3:
                    case -2:
                    case -1:
                    case 1:
                    case 12:
                    case 91:
                    case 92: {
                        return true;
                    }
                    default: {
                        return false;
                    }
                }
                break;
            }
            default: {
                return false;
            }
        }
    }
    
    public static boolean isSigned(final MysqlType type) {
        switch (type) {
            case DECIMAL:
            case TINYINT:
            case SMALLINT:
            case INT:
            case BIGINT:
            case MEDIUMINT:
            case FLOAT:
            case DOUBLE: {
                return true;
            }
            default: {
                return false;
            }
        }
    }
    
    private MysqlType(final String mysqlTypeName, final int jdbcType, final Class<?> javaClass, final int allowedFlags, final boolean isDec, final Long precision, final String createParams) {
        this.name = mysqlTypeName;
        this.jdbcType = jdbcType;
        this.javaClass = javaClass;
        this.flagsMask = allowedFlags;
        this.isDecimal = isDec;
        this.precision = precision;
        this.createParams = createParams;
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    public int getJdbcType() {
        return this.jdbcType;
    }
    
    public boolean isAllowed(final int flag) {
        return (this.flagsMask & flag) > 0;
    }
    
    public String getClassName() {
        if (this.javaClass == null) {
            return "[B";
        }
        return this.javaClass.getName();
    }
    
    public boolean isDecimal() {
        return this.isDecimal;
    }
    
    public Long getPrecision() {
        return this.precision;
    }
    
    public String getCreateParams() {
        return this.createParams;
    }
    
    @Override
    public String getVendor() {
        return "com.mysql.cj";
    }
    
    @Override
    public Integer getVendorTypeNumber() {
        return this.jdbcType;
    }
}
