
package com.mysql.cj.xdevapi;

import com.mysql.cj.protocol.x.StatementExecuteOk;
import java.util.function.Supplier;
import com.mysql.cj.result.RowList;
import java.util.function.BiFunction;
import com.mysql.cj.protocol.ColumnDefinition;
import java.util.concurrent.CompletableFuture;
import com.mysql.cj.MysqlxSession;

public class FindStatementImpl extends FilterableStatement<FindStatement, DocResult> implements FindStatement
{
    private MysqlxSession mysqlxSession;
    
    FindStatementImpl(final MysqlxSession mysqlxSession, final String schema, final String collection, final String criteria) {
        super(new DocFilterParams(schema, collection));
        this.mysqlxSession = mysqlxSession;
        if (criteria != null && criteria.length() > 0) {
            this.filterParams.setCriteria(criteria);
        }
    }
    
    @Override
    public DocResultImpl execute() {
        return this.mysqlxSession.find(this.filterParams, metadata -> (rows, task) -> new DocResultImpl(rows, task));
    }
    
    @Override
    public CompletableFuture<DocResult> executeAsync() {
        return this.mysqlxSession.asyncFind(this.filterParams, metadata -> (rows, task) -> new DocResultImpl(rows, task));
    }
    
    @Override
    public FindStatement fields(final String... projection) {
        this.filterParams.setFields(projection);
        return this;
    }
    
    @Override
    public FindStatement fields(final Expression docProjection) {
        ((DocFilterParams)this.filterParams).setFields(docProjection);
        return this;
    }
    
    @Override
    public FindStatement groupBy(final String... groupBy) {
        this.filterParams.setGrouping(groupBy);
        return this;
    }
    
    @Override
    public FindStatement having(final String having) {
        this.filterParams.setGroupingCriteria(having);
        return this;
    }
    
    @Override
    public FindStatement lockShared() {
        return this.lockShared(Statement.LockContention.DEFAULT);
    }
    
    @Override
    public FindStatement lockShared(final Statement.LockContention lockContention) {
        this.filterParams.setLock(FilterParams.RowLock.SHARED_LOCK);
        switch (lockContention) {
            case NOWAIT: {
                this.filterParams.setLockOption(FilterParams.RowLockOptions.NOWAIT);
                break;
            }
            case SKIP_LOCKED: {
                this.filterParams.setLockOption(FilterParams.RowLockOptions.SKIP_LOCKED);
                break;
            }
        }
        return this;
    }
    
    @Override
    public FindStatement lockExclusive() {
        return this.lockExclusive(Statement.LockContention.DEFAULT);
    }
    
    @Override
    public FindStatement lockExclusive(final Statement.LockContention lockContention) {
        this.filterParams.setLock(FilterParams.RowLock.EXCLUSIVE_LOCK);
        switch (lockContention) {
            case NOWAIT: {
                this.filterParams.setLockOption(FilterParams.RowLockOptions.NOWAIT);
                break;
            }
            case SKIP_LOCKED: {
                this.filterParams.setLockOption(FilterParams.RowLockOptions.SKIP_LOCKED);
                break;
            }
        }
        return this;
    }
}
