
package com.mysql.cj.jdbc;

import com.mysql.cj.jdbc.result.ResultSetInternalMethods;
import com.mysql.cj.exceptions.ExceptionInterceptor;
import com.mysql.cj.PingTarget;
import java.io.InputStream;
import java.sql.SQLException;
import com.mysql.cj.Query;
import java.sql.Statement;

public interface JdbcStatement extends Statement, Query
{
    public static final int MAX_ROWS = 50000000;
    
    void enableStreamingResults() throws SQLException;
    
    void disableStreamingResults() throws SQLException;
    
    void setLocalInfileInputStream(final InputStream p0);
    
    InputStream getLocalInfileInputStream();
    
    void setPingTarget(final PingTarget p0);
    
    ExceptionInterceptor getExceptionInterceptor();
    
    void removeOpenResultSet(final ResultSetInternalMethods p0);
    
    int getOpenResultSetCount();
    
    void setHoldResultsOpenOverClose(final boolean p0);
    
    Query getQuery();
}
