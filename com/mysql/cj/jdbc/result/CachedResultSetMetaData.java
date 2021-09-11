
package com.mysql.cj.jdbc.result;

import java.sql.ResultSetMetaData;
import com.mysql.cj.protocol.ColumnDefinition;

public interface CachedResultSetMetaData extends ColumnDefinition
{
    ResultSetMetaData getMetadata();
    
    void setMetadata(final ResultSetMetaData p0);
}
