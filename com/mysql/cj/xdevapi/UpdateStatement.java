
package com.mysql.cj.xdevapi;

import java.util.Map;

public interface UpdateStatement extends Statement<UpdateStatement, Result>
{
    UpdateStatement set(final Map<String, Object> p0);
    
    UpdateStatement set(final String p0, final Object p1);
    
    UpdateStatement where(final String p0);
    
    UpdateStatement orderBy(final String... p0);
    
    UpdateStatement limit(final long p0);
}
