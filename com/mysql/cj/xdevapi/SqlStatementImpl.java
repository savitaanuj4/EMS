
package com.mysql.cj.xdevapi;

import com.mysql.cj.exceptions.FeatureNotAvailableException;
import java.util.Map;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.ArrayList;
import java.util.List;
import com.mysql.cj.MysqlxSession;

public class SqlStatementImpl implements SqlStatement
{
    private MysqlxSession mysqlxSession;
    private String sql;
    private List<Object> args;
    
    public SqlStatementImpl(final MysqlxSession mysqlxSession, final String sql) {
        this.args = new ArrayList<Object>();
        this.mysqlxSession = mysqlxSession;
        this.sql = sql;
    }
    
    @Override
    public SqlResult execute() {
        return this.mysqlxSession.executeSql(this.sql, this.args);
    }
    
    @Override
    public CompletableFuture<SqlResult> executeAsync() {
        return this.mysqlxSession.asyncExecuteSql(this.sql, this.args);
    }
    
    @Override
    public SqlStatement clearBindings() {
        this.args.clear();
        return this;
    }
    
    @Override
    public SqlStatement bind(final List<Object> values) {
        this.args.addAll(values);
        return this;
    }
    
    @Override
    public SqlStatement bind(final Map<String, Object> values) {
        throw new FeatureNotAvailableException("Cannot bind named parameters for SQL statements");
    }
}
