
package com.mysql.cj.protocol.x;

import com.mysql.cj.protocol.Message;
import com.google.protobuf.GeneratedMessageV3;
import com.mysql.cj.exceptions.WrongArgumentException;
import com.mysql.cj.x.protobuf.MysqlxResultset;
import com.mysql.cj.x.protobuf.Mysqlx;
import com.mysql.cj.x.protobuf.MysqlxSql;
import com.mysql.cj.x.protobuf.MysqlxNotice;
import java.util.concurrent.CompletableFuture;
import com.mysql.cj.protocol.MessageListener;

public class StatementExecuteOkMessageListener implements MessageListener<XMessage>
{
    private StatementExecuteOkBuilder builder;
    private CompletableFuture<StatementExecuteOk> future;
    
    public StatementExecuteOkMessageListener(final CompletableFuture<StatementExecuteOk> future) {
        this.builder = new StatementExecuteOkBuilder();
        this.future = new CompletableFuture<StatementExecuteOk>();
        this.future = future;
    }
    
    @Override
    public Boolean createFromMessage(final XMessage message) {
        final Class<? extends GeneratedMessageV3> msgClass = (Class<? extends GeneratedMessageV3>)message.getMessage().getClass();
        if (MysqlxNotice.Frame.class.equals(msgClass)) {
            this.builder.addNotice(Notice.getInstance(message));
            return false;
        }
        if (MysqlxSql.StmtExecuteOk.class.equals(msgClass)) {
            this.future.complete(this.builder.build());
            return true;
        }
        if (Mysqlx.Error.class.equals(msgClass)) {
            this.future.completeExceptionally(new XProtocolError(Mysqlx.Error.class.cast(message.getMessage())));
            return true;
        }
        if (MysqlxResultset.FetchDone.class.equals(msgClass)) {
            return false;
        }
        this.future.completeExceptionally(new WrongArgumentException("Unhandled msg class (" + msgClass + ") + msg=" + message.getMessage()));
        return true;
    }
    
    @Override
    public void error(final Throwable ex) {
        this.future.completeExceptionally(ex);
    }
}
