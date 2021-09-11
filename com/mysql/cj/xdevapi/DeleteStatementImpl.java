
package com.mysql.cj.xdevapi;

import java.util.concurrent.CompletableFuture;
import com.mysql.cj.protocol.Message;
import com.mysql.cj.protocol.x.XMessageBuilder;
import com.mysql.cj.protocol.x.StatementExecuteOk;
import com.mysql.cj.MysqlxSession;

public class DeleteStatementImpl extends FilterableStatement<DeleteStatement, Result> implements DeleteStatement
{
    private MysqlxSession mysqlxSession;
    
    DeleteStatementImpl(final MysqlxSession mysqlxSession, final String schema, final String table) {
        super(new TableFilterParams(schema, table));
        this.mysqlxSession = mysqlxSession;
    }
    
    @Override
    public Result execute() {
        final StatementExecuteOk ok = this.mysqlxSession.sendMessage(((XMessageBuilder)this.mysqlxSession.getMessageBuilder()).buildDelete(this.filterParams));
        return new UpdateResult(ok);
    }
    
    @Override
    public CompletableFuture<Result> executeAsync() {
        final CompletableFuture<StatementExecuteOk> okF = this.mysqlxSession.asyncSendMessage(((XMessageBuilder)this.mysqlxSession.getMessageBuilder()).buildDelete(this.filterParams));
        return okF.thenApply(ok -> new UpdateResult(ok));
    }
}
