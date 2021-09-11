
package com.mysql.cj.xdevapi;

public interface AddStatement extends Statement<AddStatement, AddResult>
{
    AddStatement add(final String p0);
    
    AddStatement add(final DbDoc... p0);
    
    boolean isUpsert();
    
    AddStatement setUpsert(final boolean p0);
}
