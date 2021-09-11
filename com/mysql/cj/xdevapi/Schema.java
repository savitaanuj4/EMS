
package com.mysql.cj.xdevapi;

import java.util.List;

public interface Schema extends DatabaseObject
{
    List<Collection> getCollections();
    
    List<Collection> getCollections(final String p0);
    
    List<Table> getTables();
    
    List<Table> getTables(final String p0);
    
    Collection getCollection(final String p0);
    
    Collection getCollection(final String p0, final boolean p1);
    
    Table getCollectionAsTable(final String p0);
    
    Table getTable(final String p0);
    
    Table getTable(final String p0, final boolean p1);
    
    Collection createCollection(final String p0);
    
    Collection createCollection(final String p0, final boolean p1);
    
    void dropCollection(final String p0);
}
