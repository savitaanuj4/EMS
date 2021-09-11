
package com.mysql.cj;

import com.mysql.cj.exceptions.ExceptionInterceptor;
import java.util.Properties;
import com.mysql.cj.conf.PropertySet;

public interface MysqlConnection
{
    PropertySet getPropertySet();
    
    void createNewIO(final boolean p0);
    
    long getId();
    
    Properties getProperties();
    
    Object getConnectionMutex();
    
    Session getSession();
    
    String getURL();
    
    String getUser();
    
    ExceptionInterceptor getExceptionInterceptor();
    
    void checkClosed();
    
    void normalClose();
    
    void cleanup(final Throwable p0);
}
