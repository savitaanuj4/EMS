
package com.mysql.cj;

public interface DataStoreMetadata
{
    boolean schemaExists(final String p0);
    
    boolean tableExists(final String p0, final String p1);
    
    long getTableRowCount(final String p0, final String p1);
}
