
package com.mysql.cj.jdbc.result;

import java.sql.ResultSetMetaData;
import com.mysql.cj.result.DefaultColumnDefinition;

public class CachedResultSetMetaDataImpl extends DefaultColumnDefinition implements CachedResultSetMetaData
{
    ResultSetMetaData metadata;
    
    @Override
    public ResultSetMetaData getMetadata() {
        return this.metadata;
    }
    
    @Override
    public void setMetadata(final ResultSetMetaData metadata) {
        this.metadata = metadata;
    }
}
