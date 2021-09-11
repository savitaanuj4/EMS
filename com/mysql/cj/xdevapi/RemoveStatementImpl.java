
package com.mysql.cj.xdevapi;

import java.util.concurrent.CompletableFuture;
import com.mysql.cj.protocol.Message;
import com.mysql.cj.protocol.x.XMessageBuilder;
import com.mysql.cj.protocol.x.StatementExecuteOk;
import com.mysql.cj.Messages;
import com.mysql.cj.MysqlxSession;

public class RemoveStatementImpl extends FilterableStatement<RemoveStatement, Result> implements RemoveStatement
{
    private MysqlxSession mysqlxSession;
    
    RemoveStatementImpl(final MysqlxSession mysqlxSession, final String schema, final String collection, final String criteria) {
        super(new DocFilterParams(schema, collection));
        if (criteria == null || criteria.trim().length() == 0) {
            throw new XDevAPIError(Messages.getString("RemoveStatement.0", new String[] { "criteria" }));
        }
        this.filterParams.setCriteria(criteria);
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
