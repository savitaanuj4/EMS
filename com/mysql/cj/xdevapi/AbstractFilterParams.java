
package com.mysql.cj.xdevapi;

import java.util.function.Function;
import java.util.stream.IntStream;
import com.mysql.cj.exceptions.WrongArgumentException;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.Map;
import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import com.mysql.cj.x.protobuf.MysqlxExpr;
import java.util.List;
import com.mysql.cj.x.protobuf.MysqlxCrud;

public abstract class AbstractFilterParams implements FilterParams
{
    protected MysqlxCrud.Collection collection;
    protected Long limit;
    protected Long offset;
    protected String[] orderExpr;
    private List<MysqlxCrud.Order> order;
    protected String criteriaStr;
    private MysqlxExpr.Expr criteria;
    protected MysqlxDatatypes.Scalar[] args;
    private Map<String, Integer> placeholderNameToPosition;
    protected boolean isRelational;
    protected String[] groupBy;
    private List<MysqlxExpr.Expr> grouping;
    String having;
    private MysqlxExpr.Expr groupingCriteria;
    protected String[] projection;
    protected List<MysqlxCrud.Projection> fields;
    protected RowLock lock;
    protected RowLockOptions lockOption;
    
    public AbstractFilterParams(final String schemaName, final String collectionName, final boolean isRelational) {
        this.collection = ExprUtil.buildCollection(schemaName, collectionName);
        this.isRelational = isRelational;
    }
    
    @Override
    public Object getCollection() {
        return this.collection;
    }
    
    @Override
    public Object getOrder() {
        return this.order;
    }
    
    @Override
    public void setOrder(final String... orderExpression) {
        this.orderExpr = orderExpression;
        this.order = new ExprParser(Arrays.stream(orderExpression).collect(Collectors.joining(", ")), this.isRelational).parseOrderSpec();
    }
    
    @Override
    public Long getLimit() {
        return this.limit;
    }
    
    @Override
    public void setLimit(final Long limit) {
        this.limit = limit;
    }
    
    @Override
    public Long getOffset() {
        return this.offset;
    }
    
    @Override
    public void setOffset(final Long offset) {
        this.offset = offset;
    }
    
    @Override
    public Object getCriteria() {
        return this.criteria;
    }
    
    @Override
    public void setCriteria(final String criteriaString) {
        this.criteriaStr = criteriaString;
        final ExprParser parser = new ExprParser(criteriaString, this.isRelational);
        this.criteria = parser.parse();
        if (parser.getPositionalPlaceholderCount() > 0) {
            this.placeholderNameToPosition = parser.getPlaceholderNameToPositionMap();
            this.args = new MysqlxDatatypes.Scalar[parser.getPositionalPlaceholderCount()];
        }
    }
    
    @Override
    public Object getArgs() {
        if (this.args == null) {
            return null;
        }
        return Arrays.asList(this.args);
    }
    
    @Override
    public void addArg(final String name, final Object value) {
        if (this.args == null) {
            throw new WrongArgumentException("No placeholders");
        }
        if (this.placeholderNameToPosition.get(name) == null) {
            throw new WrongArgumentException("Unknown placeholder: " + name);
        }
        this.args[this.placeholderNameToPosition.get(name)] = ExprUtil.argObjectToScalar(value);
    }
    
    @Override
    public void verifyAllArgsBound() {
        if (this.args != null) {
            final WrongArgumentException ex;
            IntStream.range(0, this.args.length).filter(i -> this.args[i] == null).mapToObj(i -> this.placeholderNameToPosition.entrySet().stream().filter(e -> e.getValue() == i).map((Function<? super Object, ? extends String>)Map.Entry::getKey).findFirst().get()).forEach(name -> {
                new WrongArgumentException("Placeholder '" + name + "' is not bound");
                throw ex;
            });
        }
    }
    
    @Override
    public void clearArgs() {
        if (this.args != null) {
            IntStream.range(0, this.args.length).forEach(i -> this.args[i] = null);
        }
    }
    
    @Override
    public boolean isRelational() {
        return this.isRelational;
    }
    
    @Override
    public abstract void setFields(final String... p0);
    
    @Override
    public Object getFields() {
        return this.fields;
    }
    
    @Override
    public void setGrouping(final String... groupBy) {
        this.groupBy = groupBy;
        this.grouping = new ExprParser(Arrays.stream(groupBy).collect(Collectors.joining(", ")), this.isRelational()).parseExprList();
    }
    
    @Override
    public Object getGrouping() {
        return this.grouping;
    }
    
    @Override
    public void setGroupingCriteria(final String having) {
        this.having = having;
        this.groupingCriteria = new ExprParser(having, this.isRelational()).parse();
    }
    
    @Override
    public Object getGroupingCriteria() {
        return this.groupingCriteria;
    }
    
    @Override
    public RowLock getLock() {
        return this.lock;
    }
    
    @Override
    public void setLock(final RowLock rowLock) {
        this.lock = rowLock;
    }
    
    @Override
    public RowLockOptions getLockOption() {
        return this.lockOption;
    }
    
    @Override
    public void setLockOption(final RowLockOptions lockOption) {
        this.lockOption = lockOption;
    }
}
