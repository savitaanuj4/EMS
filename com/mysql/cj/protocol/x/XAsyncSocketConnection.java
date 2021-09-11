
package com.mysql.cj.protocol.x;

import java.io.BufferedOutputStream;
import com.mysql.cj.protocol.FullReadInputStream;
import java.net.Socket;
import com.mysql.cj.exceptions.ExceptionFactory;
import com.mysql.cj.exceptions.CJOperationNotSupportedException;
import com.mysql.cj.protocol.NetworkResources;
import com.mysql.cj.exceptions.FeatureNotAvailableException;
import com.mysql.cj.exceptions.SSLParamsException;
import com.mysql.cj.protocol.ServerSession;
import java.io.IOException;
import com.mysql.cj.exceptions.CJCommunicationsException;
import com.mysql.cj.protocol.AsyncSocketFactory;
import com.mysql.cj.log.Log;
import com.mysql.cj.exceptions.ExceptionInterceptor;
import com.mysql.cj.conf.PropertySet;
import java.nio.channels.AsynchronousSocketChannel;
import com.mysql.cj.protocol.SocketConnection;
import com.mysql.cj.protocol.AbstractSocketConnection;

public class XAsyncSocketConnection extends AbstractSocketConnection implements SocketConnection
{
    AsynchronousSocketChannel channel;
    
    @Override
    public void connect(final String hostName, final int portNumber, final PropertySet propSet, final ExceptionInterceptor excInterceptor, final Log log, final int loginTimeout) {
        this.port = portNumber;
        this.host = hostName;
        this.propertySet = propSet;
        this.socketFactory = new AsyncSocketFactory();
        try {
            this.channel = this.socketFactory.connect(hostName, portNumber, propSet, loginTimeout);
        }
        catch (CJCommunicationsException e) {
            throw e;
        }
        catch (IOException | RuntimeException ex3) {
            final Exception ex2;
            final Exception ex = ex2;
            throw new CJCommunicationsException(ex);
        }
    }
    
    @Override
    public void performTlsHandshake(final ServerSession serverSession) throws SSLParamsException, FeatureNotAvailableException, IOException {
        this.channel = this.socketFactory.performTlsHandshake(this, serverSession);
    }
    
    @Override
    public AsynchronousSocketChannel getAsynchronousSocketChannel() {
        return this.channel;
    }
    
    @Override
    public final void forceClose() {
        try {
            if (this.channel != null && this.channel.isOpen()) {
                this.channel.close();
            }
        }
        catch (IOException ex) {}
        finally {
            this.channel = null;
        }
    }
    
    @Override
    public NetworkResources getNetworkResources() {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, "Not supported");
    }
    
    @Override
    public Socket getMysqlSocket() {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, "Not supported");
    }
    
    @Override
    public FullReadInputStream getMysqlInput() {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, "Not supported");
    }
    
    @Override
    public void setMysqlInput(final FullReadInputStream mysqlInput) {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, "Not supported");
    }
    
    @Override
    public BufferedOutputStream getMysqlOutput() {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, "Not supported");
    }
    
    @Override
    public boolean isSSLEstablished() {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, "Not supported");
    }
    
    @Override
    public ExceptionInterceptor getExceptionInterceptor() {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, "Not supported");
    }
    
    @Override
    public boolean isSynchronous() {
        return false;
    }
}
