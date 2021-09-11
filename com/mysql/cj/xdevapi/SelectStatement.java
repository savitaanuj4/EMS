
package com.mysql.cj.xdevapi;

public interface SelectStatement extends Statement<SelectStatement, RowResult>
{
    SelectStatement where(final String p0);
    
    SelectStatement groupBy(final String... p0);
    
    SelectStatement having(final String p0);
    
    SelectStatement orderBy(final String... p0);
    
    SelectStatement limit(final long p0);
    
    SelectStatement offset(final long p0);
    
    SelectStatement lockShared();
    
    SelectStatement lockShared(final LockContention p0);
    
    SelectStatement lockExclusive();
    
    SelectStatement lockExclusive(final LockContention p0);
    
    FilterParams getFilterParams();
}
