
package com.mysql.cj.xdevapi;

import java.util.ArrayList;
import java.util.Map;
import com.mysql.cj.x.protobuf.MysqlxExpr;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.LinkedList;
import com.mysql.cj.x.protobuf.MysqlxCrud;
import java.util.List;

public class InsertParams
{
    private List<MysqlxCrud.Column> projection;
    private List<MysqlxCrud.Insert.TypedRow> rows;
    
    public InsertParams() {
        this.rows = new LinkedList<MysqlxCrud.Insert.TypedRow>();
    }
    
    public void setProjection(final String[] projection) {
        this.projection = Arrays.stream(projection).map(p -> new ExprParser(p, true).parseTableInsertField()).collect((Collector<? super Object, ?, List<MysqlxCrud.Column>>)Collectors.toList());
    }
    
    public Object getProjection() {
        return this.projection;
    }
    
    public void addRow(final List<Object> row) {
        this.rows.add(MysqlxCrud.Insert.TypedRow.newBuilder().addAllField((Iterable<? extends MysqlxExpr.Expr>)row.stream().map(f -> ExprUtil.argObjectToExpr(f, true)).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList())).build());
    }
    
    public Object getRows() {
        return this.rows;
    }
    
    public void setFieldsAndValues(final Map<String, Object> fieldsAndValues) {
        this.projection = new ArrayList<MysqlxCrud.Column>();
        final MysqlxCrud.Insert.TypedRow.Builder rowBuilder = MysqlxCrud.Insert.TypedRow.newBuilder();
        final MysqlxCrud.Insert.TypedRow.Builder builder;
        fieldsAndValues.entrySet().stream().forEach(e -> {
            this.projection.add(new ExprParser(e.getKey(), true).parseTableInsertField());
            builder.addField(ExprUtil.argObjectToExpr(e.getValue(), true));
            return;
        });
        this.rows.add(rowBuilder.build());
    }
}
