
package com.mysql.cj.xdevapi;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import com.mysql.cj.protocol.Message;
import com.mysql.cj.protocol.x.XMessageBuilder;
import com.mysql.cj.protocol.x.StatementExecuteOk;
import com.mysql.cj.MysqlxSession;

public class UpdateStatementImpl extends FilterableStatement<UpdateStatement, Result> implements UpdateStatement
{
    private MysqlxSession mysqlxSession;
    private UpdateParams updateParams;
    
    UpdateStatementImpl(final MysqlxSession mysqlxSession, final String schema, final String table) {
        super(new TableFilterParams(schema, table));
        this.updateParams = new UpdateParams();
        this.mysqlxSession = mysqlxSession;
    }
    
    @Override
    public Result execute() {
        final StatementExecuteOk ok = this.mysqlxSession.sendMessage(((XMessageBuilder)this.mysqlxSession.getMessageBuilder()).buildRowUpdate(this.filterParams, this.updateParams));
        return new UpdateResult(ok);
    }
    
    @Override
    public CompletableFuture<Result> executeAsync() {
        final CompletableFuture<StatementExecuteOk> okF = this.mysqlxSession.asyncSendMessage(((XMessageBuilder)this.mysqlxSession.getMessageBuilder()).buildRowUpdate(this.filterParams, this.updateParams));
        return okF.thenApply(ok -> new UpdateResult(ok));
    }
    
    @Override
    public UpdateStatement set(final Map<String, Object> fieldsAndValues) {
        this.updateParams.setUpdates(fieldsAndValues);
        return this;
    }
    
    @Override
    public UpdateStatement set(final String field, final Object value) {
        this.updateParams.addUpdate(field, value);
        return this;
    }
}
