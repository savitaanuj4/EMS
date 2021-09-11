
package com.mysql.cj.xdevapi;

public interface ModifyStatement extends Statement<ModifyStatement, Result>
{
    ModifyStatement sort(final String... p0);
    
    ModifyStatement limit(final long p0);
    
    ModifyStatement set(final String p0, final Object p1);
    
    ModifyStatement change(final String p0, final Object p1);
    
    ModifyStatement unset(final String... p0);
    
    ModifyStatement patch(final DbDoc p0);
    
    ModifyStatement patch(final String p0);
    
    ModifyStatement arrayInsert(final String p0, final Object p1);
    
    ModifyStatement arrayAppend(final String p0, final Object p1);
}
