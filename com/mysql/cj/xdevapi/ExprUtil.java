
package com.mysql.cj.xdevapi;

import java.util.TimeZone;
import java.util.Calendar;
import com.mysql.cj.util.TimeUtil;
import com.mysql.cj.exceptions.FeatureNotAvailableException;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.List;
import java.sql.Timestamp;
import java.sql.Time;
import java.sql.Date;
import com.mysql.cj.exceptions.WrongArgumentException;
import com.mysql.cj.x.protobuf.MysqlxCrud;
import com.google.protobuf.ByteString;
import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import com.mysql.cj.x.protobuf.MysqlxExpr;
import java.text.SimpleDateFormat;

public class ExprUtil
{
    private static SimpleDateFormat javaSqlDateFormat;
    private static SimpleDateFormat javaSqlTimestampFormat;
    private static SimpleDateFormat javaSqlTimeFormat;
    private static SimpleDateFormat javaUtilDateFormat;
    
    public static MysqlxExpr.Expr buildLiteralNullScalar() {
        return buildLiteralExpr(nullScalar());
    }
    
    public static MysqlxExpr.Expr buildLiteralScalar(final double d) {
        return buildLiteralExpr(scalarOf(d));
    }
    
    public static MysqlxExpr.Expr buildLiteralScalar(final long l) {
        return buildLiteralExpr(scalarOf(l));
    }
    
    public static MysqlxExpr.Expr buildLiteralScalar(final String str) {
        return buildLiteralExpr(scalarOf(str));
    }
    
    public static MysqlxExpr.Expr buildLiteralScalar(final byte[] bytes) {
        return buildLiteralExpr(scalarOf(bytes));
    }
    
    public static MysqlxExpr.Expr buildLiteralScalar(final boolean b) {
        return buildLiteralExpr(scalarOf(b));
    }
    
    public static MysqlxExpr.Expr buildLiteralExpr(final MysqlxDatatypes.Scalar scalar) {
        return MysqlxExpr.Expr.newBuilder().setType(MysqlxExpr.Expr.Type.LITERAL).setLiteral(scalar).build();
    }
    
    public static MysqlxDatatypes.Scalar nullScalar() {
        return MysqlxDatatypes.Scalar.newBuilder().setType(MysqlxDatatypes.Scalar.Type.V_NULL).build();
    }
    
    public static MysqlxDatatypes.Scalar scalarOf(final double d) {
        return MysqlxDatatypes.Scalar.newBuilder().setType(MysqlxDatatypes.Scalar.Type.V_DOUBLE).setVDouble(d).build();
    }
    
    public static MysqlxDatatypes.Scalar scalarOf(final long l) {
        return MysqlxDatatypes.Scalar.newBuilder().setType(MysqlxDatatypes.Scalar.Type.V_SINT).setVSignedInt(l).build();
    }
    
    public static MysqlxDatatypes.Scalar scalarOf(final String str) {
        final MysqlxDatatypes.Scalar.String sstr = MysqlxDatatypes.Scalar.String.newBuilder().setValue(ByteString.copyFromUtf8(str)).build();
        return MysqlxDatatypes.Scalar.newBuilder().setType(MysqlxDatatypes.Scalar.Type.V_STRING).setVString(sstr).build();
    }
    
    public static MysqlxDatatypes.Scalar scalarOf(final byte[] bytes) {
        final MysqlxDatatypes.Scalar.Octets.Builder o = MysqlxDatatypes.Scalar.Octets.newBuilder().setValue(ByteString.copyFrom(bytes));
        return MysqlxDatatypes.Scalar.newBuilder().setType(MysqlxDatatypes.Scalar.Type.V_OCTETS).setVOctets(o).build();
    }
    
    public static MysqlxDatatypes.Scalar scalarOf(final boolean b) {
        return MysqlxDatatypes.Scalar.newBuilder().setType(MysqlxDatatypes.Scalar.Type.V_BOOL).setVBool(b).build();
    }
    
    public static MysqlxDatatypes.Any buildAny(final String str) {
        final MysqlxDatatypes.Scalar.String sstr = MysqlxDatatypes.Scalar.String.newBuilder().setValue(ByteString.copyFromUtf8(str)).build();
        final MysqlxDatatypes.Scalar s = MysqlxDatatypes.Scalar.newBuilder().setType(MysqlxDatatypes.Scalar.Type.V_STRING).setVString(sstr).build();
        final MysqlxDatatypes.Any a = MysqlxDatatypes.Any.newBuilder().setType(MysqlxDatatypes.Any.Type.SCALAR).setScalar(s).build();
        return a;
    }
    
    public static MysqlxDatatypes.Any buildAny(final boolean b) {
        return MysqlxDatatypes.Any.newBuilder().setType(MysqlxDatatypes.Any.Type.SCALAR).setScalar(scalarOf(b)).build();
    }
    
    public static MysqlxCrud.Collection buildCollection(final String schemaName, final String collectionName) {
        return MysqlxCrud.Collection.newBuilder().setSchema(schemaName).setName(collectionName).build();
    }
    
    public static MysqlxDatatypes.Scalar argObjectToScalar(final Object value) {
        final MysqlxExpr.Expr e = argObjectToExpr(value, false);
        if (!e.hasLiteral()) {
            throw new WrongArgumentException("No literal interpretation of argument: " + value);
        }
        return e.getLiteral();
    }
    
    public static MysqlxDatatypes.Any argObjectToScalarAny(final Object value) {
        final MysqlxDatatypes.Scalar s = argObjectToScalar(value);
        return MysqlxDatatypes.Any.newBuilder().setType(MysqlxDatatypes.Any.Type.SCALAR).setScalar(s).build();
    }
    
    public static MysqlxExpr.Expr argObjectToExpr(final Object value, final boolean allowRelationalColumns) {
        if (value == null) {
            return buildLiteralNullScalar();
        }
        if (value.getClass() == Boolean.class) {
            return buildLiteralScalar((boolean)value);
        }
        if (value.getClass() == Byte.class) {
            return buildLiteralScalar((long)value);
        }
        if (value.getClass() == Short.class) {
            return buildLiteralScalar((long)value);
        }
        if (value.getClass() == Integer.class) {
            return buildLiteralScalar((long)value);
        }
        if (value.getClass() == Long.class) {
            return buildLiteralScalar((long)value);
        }
        if (value.getClass() == Float.class) {
            return buildLiteralScalar((double)value);
        }
        if (value.getClass() == Double.class) {
            return buildLiteralScalar((double)value);
        }
        if (value.getClass() == String.class) {
            return buildLiteralScalar((String)value);
        }
        if (value.getClass() == Expression.class) {
            return new ExprParser(((Expression)value).getExpressionString(), allowRelationalColumns).parse();
        }
        if (value.getClass() == Date.class) {
            return buildLiteralScalar(ExprUtil.javaSqlDateFormat.format((java.util.Date)value));
        }
        if (value.getClass() == Time.class) {
            return buildLiteralScalar(ExprUtil.javaSqlTimeFormat.format((java.util.Date)value));
        }
        if (value.getClass() == Timestamp.class) {
            return buildLiteralScalar(ExprUtil.javaSqlTimestampFormat.format((java.util.Date)value));
        }
        if (value.getClass() == java.util.Date.class) {
            return buildLiteralScalar(ExprUtil.javaUtilDateFormat.format((java.util.Date)value));
        }
        if (DbDoc.class.isAssignableFrom(value.getClass())) {
            return new ExprParser(((DbDoc)value).toString()).parse();
        }
        if (value.getClass() == JsonArray.class) {
            return MysqlxExpr.Expr.newBuilder().setType(MysqlxExpr.Expr.Type.ARRAY).setArray(MysqlxExpr.Expr.newBuilder().setType(MysqlxExpr.Expr.Type.ARRAY).getArrayBuilder().addAllValue((Iterable<? extends MysqlxExpr.Expr>)((JsonArray)value).stream().map(f -> argObjectToExpr(f, true)).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList()))).build();
        }
        if (value.getClass() == JsonString.class) {
            return buildLiteralScalar(((JsonString)value).getString());
        }
        if (value.getClass() == JsonNumber.class) {
            return buildLiteralScalar(((JsonNumber)value).getInteger());
        }
        throw new FeatureNotAvailableException("TODO: other types: BigDecimal");
    }
    
    static {
        ExprUtil.javaSqlDateFormat = TimeUtil.getSimpleDateFormat(null, "yyyy-MM-dd", null, null);
        ExprUtil.javaSqlTimestampFormat = TimeUtil.getSimpleDateFormat(null, "yyyy-MM-dd'T'HH:mm:ss.S", null, null);
        ExprUtil.javaSqlTimeFormat = TimeUtil.getSimpleDateFormat(null, "HH:mm:ss.S", null, null);
        ExprUtil.javaUtilDateFormat = TimeUtil.getSimpleDateFormat(null, "yyyy-MM-dd'T'HH:mm:ss.S", null, null);
    }
}
