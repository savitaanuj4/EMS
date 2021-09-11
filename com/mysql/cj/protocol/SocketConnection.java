
package com.mysql.cj.protocol;

import java.nio.channels.AsynchronousSocketChannel;
import java.io.BufferedOutputStream;
import java.net.Socket;
import java.io.IOException;
import com.mysql.cj.exceptions.FeatureNotAvailableException;
import com.mysql.cj.exceptions.SSLParamsException;
import com.mysql.cj.log.Log;
import com.mysql.cj.exceptions.ExceptionInterceptor;
import com.mysql.cj.conf.PropertySet;

public interface SocketConnection
{
    void connect(final String p0, final int p1, final PropertySet p2, final ExceptionInterceptor p3, final Log p4, final int p5);
    
    void performTlsHandshake(final ServerSession p0) throws SSLParamsException, FeatureNotAvailableException, IOException;
    
    void forceClose();
    
    NetworkResources getNetworkResources();
    
    String getHost();
    
    int getPort();
    
    Socket getMysqlSocket() throws IOException;
    
    FullReadInputStream getMysqlInput() throws IOException;
    
    void setMysqlInput(final FullReadInputStream p0);
    
    BufferedOutputStream getMysqlOutput() throws IOException;
    
    boolean isSSLEstablished();
    
    SocketFactory getSocketFactory();
    
    void setSocketFactory(final SocketFactory p0);
    
    ExceptionInterceptor getExceptionInterceptor();
    
    PropertySet getPropertySet();
    
    default boolean isSynchronous() {
        return true;
    }
    
    AsynchronousSocketChannel getAsynchronousSocketChannel();
}
