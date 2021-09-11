
package com.mysql.cj.xdevapi;

import java.util.List;
import com.mysql.cj.exceptions.FeatureNotAvailableException;
import com.mysql.cj.protocol.x.StatementExecuteOk;

public class SqlUpdateResult extends UpdateResult implements SqlResult
{
    public SqlUpdateResult(final StatementExecuteOk ok) {
        super(ok);
    }
    
    @Override
    public boolean hasData() {
        return false;
    }
    
    @Override
    public boolean nextResult() {
        throw new FeatureNotAvailableException("Not a multi-result");
    }
    
    @Override
    public List<Row> fetchAll() {
        throw new FeatureNotAvailableException("No data");
    }
    
    @Override
    public Row next() {
        throw new FeatureNotAvailableException("No data");
    }
    
    @Override
    public boolean hasNext() {
        throw new FeatureNotAvailableException("No data");
    }
    
    @Override
    public int getColumnCount() {
        throw new FeatureNotAvailableException("No data");
    }
    
    @Override
    public List<Column> getColumns() {
        throw new FeatureNotAvailableException("No data");
    }
    
    @Override
    public List<String> getColumnNames() {
        throw new FeatureNotAvailableException("No data");
    }
    
    @Override
    public long count() {
        throw new FeatureNotAvailableException("No data");
    }
    
    @Override
    public Long getAutoIncrementValue() {
        return this.ok.getLastInsertId();
    }
}
