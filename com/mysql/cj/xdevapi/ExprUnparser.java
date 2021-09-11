
package com.mysql.cj.xdevapi;

import java.util.HashSet;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Iterator;
import com.mysql.cj.x.protobuf.MysqlxExpr;
import java.util.List;
import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import java.util.Set;

public class ExprUnparser
{
    static Set<String> infixOperators;
    
    static String scalarToString(final MysqlxDatatypes.Scalar e) {
        switch (e.getType()) {
            case V_SINT: {
                return "" + e.getVSignedInt();
            }
            case V_OCTETS: {
                return "\"" + escapeLiteral(e.getVOctets().getValue().toStringUtf8()) + "\"";
            }
            case V_STRING: {
                return "\"" + escapeLiteral(e.getVString().getValue().toStringUtf8()) + "\"";
            }
            case V_DOUBLE: {
                return "" + e.getVDouble();
            }
            case V_BOOL: {
                return e.getVBool() ? "TRUE" : "FALSE";
            }
            case V_NULL: {
                return "NULL";
            }
            default: {
                throw new IllegalArgumentException("Unknown type tag: " + e.getType());
            }
        }
    }
    
    static String documentPathToString(final List<MysqlxExpr.DocumentPathItem> items) {
        final StringBuilder docPathString = new StringBuilder();
        for (final MysqlxExpr.DocumentPathItem item : items) {
            switch (item.getType()) {
                case MEMBER: {
                    docPathString.append(".").append(quoteDocumentPathMember(item.getValue()));
                    continue;
                }
                case MEMBER_ASTERISK: {
                    docPathString.append(".*");
                    continue;
                }
                case ARRAY_INDEX: {
                    docPathString.append("[").append("" + Integer.toUnsignedLong(item.getIndex())).append("]");
                    continue;
                }
                case ARRAY_INDEX_ASTERISK: {
                    docPathString.append("[*]");
                    continue;
                }
                case DOUBLE_ASTERISK: {
                    docPathString.append("**");
                    continue;
                }
            }
        }
        return docPathString.toString();
    }
    
    static String columnIdentifierToString(final MysqlxExpr.ColumnIdentifier e) {
        if (e.hasName()) {
            String s = quoteIdentifier(e.getName());
            if (e.hasTableName()) {
                s = quoteIdentifier(e.getTableName()) + "." + s;
            }
            if (e.hasSchemaName()) {
                s = quoteIdentifier(e.getSchemaName()) + "." + s;
            }
            if (e.getDocumentPathCount() > 0) {
                s = s + "->$" + documentPathToString(e.getDocumentPathList());
            }
            return s;
        }
        return "$" + documentPathToString(e.getDocumentPathList());
    }
    
    static String functionCallToString(final MysqlxExpr.FunctionCall e) {
        final MysqlxExpr.Identifier i = e.getName();
        String s = quoteIdentifier(i.getName());
        if (i.hasSchemaName()) {
            s = quoteIdentifier(i.getSchemaName()) + "." + s;
        }
        s += "(";
        for (final MysqlxExpr.Expr p : e.getParamList()) {
            s = s + exprToString(p) + ", ";
        }
        s = s.replaceAll(", $", "");
        s += ")";
        return s;
    }
    
    static String paramListToString(final List<String> params) {
        String s = "(";
        boolean first = true;
        for (final String param : params) {
            if (!first) {
                s += ", ";
            }
            first = false;
            s += param;
        }
        return s + ")";
    }
    
    static String operatorToString(final MysqlxExpr.Operator e) {
        String name = e.getName();
        final List<String> params = new ArrayList<String>();
        for (final MysqlxExpr.Expr p : e.getParamList()) {
            params.add(exprToString(p));
        }
        if ("between".equals(name) || "not_between".equals(name)) {
            name = name.replaceAll("not_between", "not between");
            return String.format("(%s %s %s AND %s)", params.get(0), name, params.get(1), params.get(2));
        }
        if ("in".equals(name) || "not_in".equals(name)) {
            name = name.replaceAll("not_in", "not in");
            return String.format("%s %s%s", params.get(0), name, paramListToString(params.subList(1, params.size())));
        }
        if ("like".equals(name) || "not_like".equals(name)) {
            name = name.replaceAll("not_like", "not like");
            String s = String.format("%s %s %s", params.get(0), name, params.get(1));
            if (params.size() == 3) {
                s = s + " ESCAPE " + params.get(2);
            }
            return s;
        }
        if ("regexp".equals(name) || "not_regexp".equals("name")) {
            name = name.replaceAll("not_regexp", "not regexp");
            return String.format("(%s %s %s)", params.get(0), name, params.get(1));
        }
        if ("cast".equals(name)) {
            return String.format("cast(%s AS %s)", params.get(0), params.get(1).replaceAll("\"", ""));
        }
        if ((name.length() < 3 || ExprUnparser.infixOperators.contains(name)) && params.size() == 2) {
            return String.format("(%s %s %s)", params.get(0), name, params.get(1));
        }
        if (params.size() == 1) {
            return String.format("%s%s", name, params.get(0));
        }
        if (params.size() == 0) {
            return name;
        }
        return name + paramListToString(params);
    }
    
    static String objectToString(final MysqlxExpr.Object o) {
        final String fields = o.getFldList().stream().map(f -> "'" + quoteJsonKey(f.getKey()) + "'" + ":" + exprToString(f.getValue())).collect((Collector<? super Object, ?, String>)Collectors.joining(", "));
        return "{" + fields + "}";
    }
    
    public static String escapeLiteral(final String s) {
        return s.replaceAll("\"", "\"\"");
    }
    
    public static String quoteIdentifier(final String ident) {
        if (ident.contains("`") || ident.contains("\"") || ident.contains("'") || ident.contains("$") || ident.contains(".") || ident.contains("-")) {
            return "`" + ident.replaceAll("`", "``") + "`";
        }
        return ident;
    }
    
    public static String quoteJsonKey(final String key) {
        return key.replaceAll("'", "\\\\'");
    }
    
    public static String quoteDocumentPathMember(final String member) {
        if (!member.matches("[a-zA-Z0-9_]*")) {
            return "\"" + member.replaceAll("\"", "\\\\\"") + "\"";
        }
        return member;
    }
    
    public static String exprToString(final MysqlxExpr.Expr e) {
        switch (e.getType()) {
            case LITERAL: {
                return scalarToString(e.getLiteral());
            }
            case IDENT: {
                return columnIdentifierToString(e.getIdentifier());
            }
            case FUNC_CALL: {
                return functionCallToString(e.getFunctionCall());
            }
            case OPERATOR: {
                return operatorToString(e.getOperator());
            }
            case PLACEHOLDER: {
                return ":" + Integer.toUnsignedLong(e.getPosition());
            }
            case OBJECT: {
                return objectToString(e.getObject());
            }
            default: {
                throw new IllegalArgumentException("Unknown type tag: " + e.getType());
            }
        }
    }
    
    static {
        (ExprUnparser.infixOperators = new HashSet<String>()).add("and");
        ExprUnparser.infixOperators.add("or");
    }
}
