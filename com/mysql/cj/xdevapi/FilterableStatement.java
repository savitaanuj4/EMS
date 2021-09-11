
package com.mysql.cj.xdevapi;

public abstract class FilterableStatement<STMT_T, RES_T> implements Statement<STMT_T, RES_T>
{
    protected FilterParams filterParams;
    
    public FilterableStatement(final FilterParams filterParams) {
        this.filterParams = filterParams;
    }
    
    public STMT_T where(final String searchCondition) {
        this.filterParams.setCriteria(searchCondition);
        return (STMT_T)this;
    }
    
    public STMT_T sort(final String... sortFields) {
        return this.orderBy(sortFields);
    }
    
    public STMT_T orderBy(final String... sortFields) {
        this.filterParams.setOrder(sortFields);
        return (STMT_T)this;
    }
    
    public STMT_T limit(final long numberOfRows) {
        this.filterParams.setLimit(numberOfRows);
        return (STMT_T)this;
    }
    
    public STMT_T offset(final long limitOffset) {
        this.filterParams.setOffset(limitOffset);
        return (STMT_T)this;
    }
    
    public boolean isRelational() {
        return this.filterParams.isRelational();
    }
    
    @Override
    public STMT_T clearBindings() {
        this.filterParams.clearArgs();
        return (STMT_T)this;
    }
    
    @Override
    public STMT_T bind(final String argName, final Object value) {
        this.filterParams.addArg(argName, value);
        return (STMT_T)this;
    }
}
