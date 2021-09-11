
package com.mysql.cj.xdevapi;

import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import com.mysql.cj.protocol.Message;
import com.mysql.cj.protocol.x.XMessageBuilder;
import com.mysql.cj.protocol.x.StatementExecuteOk;
import com.mysql.cj.Messages;
import java.util.ArrayList;
import java.util.List;
import com.mysql.cj.MysqlxSession;

public class ModifyStatementImpl extends FilterableStatement<ModifyStatement, Result> implements ModifyStatement
{
    private MysqlxSession mysqlxSession;
    private List<UpdateSpec> updates;
    
    ModifyStatementImpl(final MysqlxSession mysqlxSession, final String schema, final String collection, final String criteria) {
        super(new DocFilterParams(schema, collection));
        this.updates = new ArrayList<UpdateSpec>();
        if (criteria == null || criteria.trim().length() == 0) {
            throw new XDevAPIError(Messages.getString("ModifyStatement.0", new String[] { "criteria" }));
        }
        this.filterParams.setCriteria(criteria);
        this.mysqlxSession = mysqlxSession;
    }
    
    @Override
    public Result execute() {
        final StatementExecuteOk ok = this.mysqlxSession.sendMessage(((XMessageBuilder)this.mysqlxSession.getMessageBuilder()).buildDocUpdate(this.filterParams, this.updates));
        return new UpdateResult(ok);
    }
    
    @Override
    public CompletableFuture<Result> executeAsync() {
        final CompletableFuture<StatementExecuteOk> okF = this.mysqlxSession.asyncSendMessage(((XMessageBuilder)this.mysqlxSession.getMessageBuilder()).buildDocUpdate(this.filterParams, this.updates));
        return okF.thenApply(ok -> new UpdateResult(ok));
    }
    
    @Override
    public ModifyStatement set(final String docPath, final Object value) {
        this.updates.add(new UpdateSpec(UpdateType.ITEM_SET, docPath).setValue(value));
        return this;
    }
    
    @Override
    public ModifyStatement change(final String docPath, final Object value) {
        this.updates.add(new UpdateSpec(UpdateType.ITEM_REPLACE, docPath).setValue(value));
        return this;
    }
    
    @Override
    public ModifyStatement unset(final String... fields) {
        this.updates.addAll(Arrays.stream(fields).map(docPath -> new UpdateSpec(UpdateType.ITEM_REMOVE, docPath)).collect((Collector<? super Object, ?, Collection<? extends UpdateSpec>>)Collectors.toList()));
        return this;
    }
    
    @Override
    public ModifyStatement patch(final DbDoc document) {
        return this.patch(document.toString());
    }
    
    @Override
    public ModifyStatement patch(final String document) {
        this.updates.add(new UpdateSpec(UpdateType.MERGE_PATCH, "").setValue(Expression.expr(document)));
        return this;
    }
    
    @Override
    public ModifyStatement arrayInsert(final String field, final Object value) {
        this.updates.add(new UpdateSpec(UpdateType.ARRAY_INSERT, field).setValue(value));
        return this;
    }
    
    @Override
    public ModifyStatement arrayAppend(final String docPath, final Object value) {
        this.updates.add(new UpdateSpec(UpdateType.ARRAY_APPEND, docPath).setValue(value));
        return this;
    }
}
