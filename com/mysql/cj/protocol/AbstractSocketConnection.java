
package com.mysql.cj.protocol;

import com.mysql.cj.exceptions.CJException;
import com.mysql.jdbc.SocketFactoryWrapper;
import com.mysql.cj.exceptions.ExceptionFactory;
import com.mysql.cj.exceptions.UnableToConnectException;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.IOException;
import com.mysql.cj.Messages;
import com.mysql.cj.conf.PropertySet;
import com.mysql.cj.exceptions.ExceptionInterceptor;
import java.io.BufferedOutputStream;
import java.net.Socket;

public abstract class AbstractSocketConnection implements SocketConnection
{
    protected String host;
    protected int port;
    protected SocketFactory socketFactory;
    protected Socket mysqlSocket;
    protected FullReadInputStream mysqlInput;
    protected BufferedOutputStream mysqlOutput;
    protected ExceptionInterceptor exceptionInterceptor;
    protected PropertySet propertySet;
    
    public AbstractSocketConnection() {
        this.host = null;
        this.port = 3306;
        this.socketFactory = null;
        this.mysqlSocket = null;
        this.mysqlInput = null;
        this.mysqlOutput = null;
    }
    
    @Override
    public String getHost() {
        return this.host;
    }
    
    @Override
    public int getPort() {
        return this.port;
    }
    
    @Override
    public Socket getMysqlSocket() {
        return this.mysqlSocket;
    }
    
    @Override
    public FullReadInputStream getMysqlInput() throws IOException {
        if (this.mysqlInput != null) {
            return this.mysqlInput;
        }
        throw new IOException(Messages.getString("SocketConnection.2"));
    }
    
    @Override
    public void setMysqlInput(final FullReadInputStream mysqlInput) {
        this.mysqlInput = mysqlInput;
    }
    
    @Override
    public BufferedOutputStream getMysqlOutput() throws IOException {
        if (this.mysqlOutput != null) {
            return this.mysqlOutput;
        }
        throw new IOException(Messages.getString("SocketConnection.2"));
    }
    
    @Override
    public boolean isSSLEstablished() {
        return ExportControlled.enabled() && ExportControlled.isSSLEstablished(this.getMysqlSocket());
    }
    
    @Override
    public SocketFactory getSocketFactory() {
        return this.socketFactory;
    }
    
    @Override
    public void setSocketFactory(final SocketFactory socketFactory) {
        this.socketFactory = socketFactory;
    }
    
    @Override
    public void forceClose() {
        try {
            this.getNetworkResources().forceClose();
        }
        finally {
            this.mysqlSocket = null;
            this.mysqlInput = null;
            this.mysqlOutput = null;
        }
    }
    
    @Override
    public NetworkResources getNetworkResources() {
        return new NetworkResources(this.mysqlSocket, this.mysqlInput, this.mysqlOutput);
    }
    
    @Override
    public ExceptionInterceptor getExceptionInterceptor() {
        return this.exceptionInterceptor;
    }
    
    @Override
    public PropertySet getPropertySet() {
        return this.propertySet;
    }
    
    protected SocketFactory createSocketFactory(final String socketFactoryClassName) {
        try {
            if (socketFactoryClassName == null) {
                throw ExceptionFactory.createException(UnableToConnectException.class, Messages.getString("SocketConnection.0"), this.getExceptionInterceptor());
            }
            final Object sf = Class.forName(socketFactoryClassName).newInstance();
            if (sf instanceof SocketFactory) {
                return (SocketFactory)Class.forName(socketFactoryClassName).newInstance();
            }
            try {
                return new SocketFactoryWrapper(sf);
            }
            catch (IllegalAccessException | ClassNotFoundException ex3) {
                final Exception ex2;
                final Exception ex = ex2;
                throw ExceptionFactory.createException(UnableToConnectException.class, Messages.getString("SocketConnection.1", new String[] { socketFactoryClassName }), this.getExceptionInterceptor());
            }
        }
        catch (InstantiationException ex4) {}
        catch (IllegalAccessException ex5) {}
        catch (ClassNotFoundException ex6) {}
        catch (CJException ex7) {}
    }
}
