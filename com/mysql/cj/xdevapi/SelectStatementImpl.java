
package com.mysql.cj.xdevapi;

import com.mysql.cj.protocol.x.StatementExecuteOk;
import java.util.function.Supplier;
import com.mysql.cj.result.RowList;
import java.util.function.BiFunction;
import com.mysql.cj.protocol.ColumnDefinition;
import java.util.concurrent.CompletableFuture;
import com.mysql.cj.MysqlxSession;

public class SelectStatementImpl extends FilterableStatement<SelectStatement, RowResult> implements SelectStatement
{
    private MysqlxSession mysqlxSession;
    
    SelectStatementImpl(final MysqlxSession mysqlxSession, final String schema, final String table, final String... projection) {
        super(new TableFilterParams(schema, table));
        this.mysqlxSession = mysqlxSession;
        if (projection != null && projection.length > 0) {
            this.filterParams.setFields(projection);
        }
    }
    
    @Override
    public RowResultImpl execute() {
        return this.mysqlxSession.find(this.filterParams, metadata -> (rows, task) -> new RowResultImpl(metadata, this.mysqlxSession.getServerSession().getDefaultTimeZone(), rows, task));
    }
    
    @Override
    public CompletableFuture<RowResult> executeAsync() {
        return this.mysqlxSession.asyncFind(this.filterParams, metadata -> (rows, task) -> new RowResultImpl(metadata, this.mysqlxSession.getServerSession().getDefaultTimeZone(), rows, task));
    }
    
    @Override
    public SelectStatement groupBy(final String... groupBy) {
        this.filterParams.setGrouping(groupBy);
        return this;
    }
    
    @Override
    public SelectStatement having(final String having) {
        this.filterParams.setGroupingCriteria(having);
        return this;
    }
    
    @Override
    public FilterParams getFilterParams() {
        return this.filterParams;
    }
    
    @Override
    public SelectStatement lockShared() {
        return this.lockShared(Statement.LockContention.DEFAULT);
    }
    
    @Override
    public SelectStatement lockShared(final Statement.LockContention lockContention) {
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
    public SelectStatement lockExclusive() {
        return this.lockExclusive(Statement.LockContention.DEFAULT);
    }
    
    @Override
    public SelectStatement lockExclusive(final Statement.LockContention lockContention) {
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
