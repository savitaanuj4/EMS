
package com.mysql.cj.xdevapi;

import java.util.List;

public interface Session
{
    List<Schema> getSchemas();
    
    Schema getSchema(final String p0);
    
    String getDefaultSchemaName();
    
    Schema getDefaultSchema();
    
    Schema createSchema(final String p0);
    
    Schema createSchema(final String p0, final boolean p1);
    
    void dropSchema(final String p0);
    
    String getUri();
    
    boolean isOpen();
    
    void close();
    
    void startTransaction();
    
    void commit();
    
    void rollback();
    
    String setSavepoint();
    
    String setSavepoint(final String p0);
    
    void rollbackTo(final String p0);
    
    void releaseSavepoint(final String p0);
    
    SqlStatement sql(final String p0);
}
