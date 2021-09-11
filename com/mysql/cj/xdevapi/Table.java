
package com.mysql.cj.xdevapi;

import java.util.Map;

public interface Table extends DatabaseObject
{
    InsertStatement insert();
    
    InsertStatement insert(final String... p0);
    
    InsertStatement insert(final Map<String, Object> p0);
    
    SelectStatement select(final String... p0);
    
    UpdateStatement update();
    
    DeleteStatement delete();
    
    long count();
    
    boolean isView();
}
