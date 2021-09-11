
package com.mysql.cj.protocol.x;

import com.mysql.cj.protocol.Message;
import com.mysql.cj.x.protobuf.Mysqlx;
import com.mysql.cj.x.protobuf.MysqlxResultset;
import com.google.protobuf.GeneratedMessageV3;
import java.util.function.Supplier;
import com.mysql.cj.result.RowList;
import java.util.function.BiFunction;
import com.mysql.cj.protocol.ColumnDefinition;
import java.util.function.Function;
import com.mysql.cj.xdevapi.SqlUpdateResult;
import com.mysql.cj.protocol.ResultListener;
import com.mysql.cj.xdevapi.SqlDataResult;
import java.util.TimeZone;
import com.mysql.cj.result.Field;
import com.mysql.cj.protocol.ProtocolEntityFactory;
import com.mysql.cj.xdevapi.SqlResult;
import java.util.concurrent.CompletableFuture;
import com.mysql.cj.protocol.MessageListener;

public class SqlResultMessageListener implements MessageListener<XMessage>
{
    private ResultType resultType;
    private CompletableFuture<SqlResult> resultF;
    private StatementExecuteOkMessageListener okListener;
    private ResultMessageListener resultListener;
    private ResultCreatingResultListener<SqlResult> resultCreator;
    
    public SqlResultMessageListener(final CompletableFuture<SqlResult> resultF, final ProtocolEntityFactory<Field, XMessage> colToField, final TimeZone defaultTimeZone) {
        this.resultF = resultF;
        final Function<ColumnDefinition, BiFunction<RowList, Supplier<StatementExecuteOk>, SqlResult>> resultCtor = metadata -> (rows, task) -> new SqlDataResult(metadata, defaultTimeZone, rows, task);
        this.resultCreator = new ResultCreatingResultListener<SqlResult>(resultCtor, resultF);
        this.resultListener = new ResultMessageListener(colToField, this.resultCreator);
        final CompletableFuture<StatementExecuteOk> okF = new CompletableFuture<StatementExecuteOk>();
        okF.whenComplete((ok, ex) -> {
            if (ex != null) {
                this.resultF.completeExceptionally(ex);
            }
            else {
                this.resultF.complete(new SqlUpdateResult(ok));
            }
            return;
        });
        this.okListener = new StatementExecuteOkMessageListener(okF);
    }
    
    @Override
    public Boolean createFromMessage(final XMessage message) {
        final GeneratedMessageV3 msg = (GeneratedMessageV3)message.getMessage();
        final Class<? extends GeneratedMessageV3> msgClass = msg.getClass();
        if (this.resultType == null) {
            if (MysqlxResultset.ColumnMetaData.class.equals(msgClass)) {
                this.resultType = ResultType.DATA;
            }
            else if (!Mysqlx.Error.class.equals(msgClass)) {
                this.resultType = ResultType.UPDATE;
            }
        }
        if (this.resultType == ResultType.DATA) {
            return this.resultListener.createFromMessage(message);
        }
        return this.okListener.createFromMessage(message);
    }
    
    @Override
    public void error(final Throwable ex) {
        this.resultF.completeExceptionally(ex);
    }
    
    private enum ResultType
    {
        UPDATE, 
        DATA;
    }
}
