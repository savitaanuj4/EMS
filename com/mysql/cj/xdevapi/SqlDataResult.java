
package com.mysql.cj.xdevapi;

import com.mysql.cj.exceptions.FeatureNotAvailableException;
import com.mysql.cj.protocol.x.StatementExecuteOk;
import java.util.function.Supplier;
import com.mysql.cj.result.RowList;
import java.util.TimeZone;
import com.mysql.cj.protocol.ColumnDefinition;

public class SqlDataResult extends RowResultImpl implements SqlResult
{
    public SqlDataResult(final ColumnDefinition metadata, final TimeZone defaultTimeZone, final RowList rows, final Supplier<StatementExecuteOk> completer) {
        super(metadata, defaultTimeZone, rows, completer);
    }
    
    @Override
    public boolean nextResult() {
        throw new FeatureNotAvailableException("Not a multi-result");
    }
    
    @Override
    public long getAffectedItemsCount() {
        return this.getStatementExecuteOk().getRowsAffected();
    }
    
    @Override
    public Long getAutoIncrementValue() {
        throw new XDevAPIError("Method getAutoIncrementValue() is allowed only for insert statements.");
    }
}
