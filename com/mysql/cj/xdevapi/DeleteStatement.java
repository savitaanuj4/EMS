
package com.mysql.cj.xdevapi;

public interface DeleteStatement extends Statement<DeleteStatement, Result>
{
    DeleteStatement where(final String p0);
    
    DeleteStatement orderBy(final String... p0);
    
    DeleteStatement limit(final long p0);
}
