
package com.mysql.cj.xdevapi;

import java.util.Map;

public interface Collection extends DatabaseObject
{
    AddStatement add(final Map<String, ?> p0);
    
    AddStatement add(final String... p0);
    
    AddStatement add(final DbDoc p0);
    
    AddStatement add(final DbDoc... p0);
    
    FindStatement find();
    
    FindStatement find(final String p0);
    
    ModifyStatement modify(final String p0);
    
    RemoveStatement remove(final String p0);
    
    Result createIndex(final String p0, final DbDoc p1);
    
    Result createIndex(final String p0, final String p1);
    
    void dropIndex(final String p0);
    
    long count();
    
    DbDoc newDoc();
    
    Result replaceOne(final String p0, final DbDoc p1);
    
    Result replaceOne(final String p0, final String p1);
    
    Result addOrReplaceOne(final String p0, final DbDoc p1);
    
    Result addOrReplaceOne(final String p0, final String p1);
    
    DbDoc getOne(final String p0);
    
    Result removeOne(final String p0);
}
