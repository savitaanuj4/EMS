
package com.mysql.cj.xdevapi;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import com.mysql.cj.protocol.Message;
import com.mysql.cj.protocol.x.XMessageBuilder;
import com.mysql.cj.protocol.x.StatementExecuteOk;
import java.util.Map;
import com.mysql.cj.MysqlxSession;

public class InsertStatementImpl implements InsertStatement
{
    private MysqlxSession mysqlxSession;
    private String schemaName;
    private String tableName;
    private InsertParams insertParams;
    
    InsertStatementImpl(final MysqlxSession mysqlxSession, final String schema, final String table, final String[] fields) {
        this.insertParams = new InsertParams();
        this.mysqlxSession = mysqlxSession;
        this.schemaName = schema;
        this.tableName = table;
        this.insertParams.setProjection(fields);
    }
    
    InsertStatementImpl(final MysqlxSession mysqlxSession, final String schema, final String table, final Map<String, Object> fieldsAndValues) {
        this.insertParams = new InsertParams();
        this.mysqlxSession = mysqlxSession;
        this.schemaName = schema;
        this.tableName = table;
        this.insertParams.setFieldsAndValues(fieldsAndValues);
    }
    
    @Override
    public InsertResult execute() {
        final StatementExecuteOk ok = this.mysqlxSession.sendMessage(((XMessageBuilder)this.mysqlxSession.getMessageBuilder()).buildRowInsert(this.schemaName, this.tableName, this.insertParams));
        return new InsertResultImpl(ok);
    }
    
    @Override
    public CompletableFuture<InsertResult> executeAsync() {
        final CompletableFuture<StatementExecuteOk> okF = this.mysqlxSession.asyncSendMessage(((XMessageBuilder)this.mysqlxSession.getMessageBuilder()).buildRowInsert(this.schemaName, this.tableName, this.insertParams));
        return okF.thenApply(ok -> new InsertResultImpl(ok));
    }
    
    @Override
    public InsertStatement values(final List<Object> row) {
        this.insertParams.addRow(row);
        return this;
    }
}
