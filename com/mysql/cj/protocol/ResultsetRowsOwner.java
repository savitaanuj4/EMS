
package com.mysql.cj.protocol;

import com.mysql.cj.Session;
import com.mysql.cj.MysqlConnection;

public interface ResultsetRowsOwner
{
    void closeOwner(final boolean p0);
    
    MysqlConnection getConnection();
    
    Session getSession();
    
    Object getSyncMutex();
    
    long getConnectionId();
    
    String getPointOfOrigin();
    
    int getOwnerFetchSize();
    
    String getCurrentCatalog();
    
    int getOwningStatementId();
    
    int getOwningStatementMaxRows();
    
    int getOwningStatementFetchSize();
    
    long getOwningStatementServerId();
}
