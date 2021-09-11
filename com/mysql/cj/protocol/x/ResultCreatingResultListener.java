
package com.mysql.cj.protocol.x;

import com.mysql.cj.protocol.ProtocolEntity;
import com.mysql.cj.result.BufferedRowList;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import com.mysql.cj.result.RowList;
import java.util.function.BiFunction;
import java.util.function.Function;
import com.mysql.cj.result.Row;
import java.util.List;
import com.mysql.cj.protocol.ColumnDefinition;
import com.mysql.cj.protocol.ResultListener;

public class ResultCreatingResultListener<RES_T> implements ResultListener<StatementExecuteOk>
{
    private ColumnDefinition metadata;
    private List<Row> rows;
    private Function<ColumnDefinition, BiFunction<RowList, Supplier<StatementExecuteOk>, RES_T>> resultCtor;
    private CompletableFuture<RES_T> future;
    
    public ResultCreatingResultListener(final Function<ColumnDefinition, BiFunction<RowList, Supplier<StatementExecuteOk>, RES_T>> resultCtor, final CompletableFuture<RES_T> future) {
        this.rows = new ArrayList<Row>();
        this.resultCtor = resultCtor;
        this.future = future;
    }
    
    @Override
    public void onMetadata(final ColumnDefinition metadataFields) {
        this.metadata = metadataFields;
    }
    
    @Override
    public void onRow(final Row r) {
        this.rows.add(r);
    }
    
    @Override
    public void onComplete(final StatementExecuteOk ok) {
        final RowList rowList = new BufferedRowList(this.rows);
        final RES_T result = this.resultCtor.apply(this.metadata).apply(rowList, () -> ok);
        this.future.complete(result);
    }
    
    @Override
    public void onException(final Throwable t) {
        this.future.completeExceptionally(t);
    }
}
