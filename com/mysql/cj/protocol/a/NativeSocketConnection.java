
package com.mysql.cj.protocol.a;

import com.mysql.cj.exceptions.CJOperationNotSupportedException;
import java.nio.channels.AsynchronousSocketChannel;
import com.mysql.cj.exceptions.FeatureNotAvailableException;
import com.mysql.cj.exceptions.SSLParamsException;
import java.io.InputStream;
import java.io.IOException;
import com.mysql.cj.protocol.PacketReceivedTimeHolder;
import com.mysql.cj.protocol.ServerSession;
import com.mysql.cj.exceptions.ExceptionFactory;
import com.mysql.cj.protocol.PacketSentTimeHolder;
import java.io.BufferedOutputStream;
import com.mysql.cj.protocol.FullReadInputStream;
import java.io.BufferedInputStream;
import com.mysql.cj.protocol.ReadAheadInputStream;
import java.net.Socket;
import com.mysql.cj.conf.PropertyKey;
import com.mysql.cj.log.Log;
import com.mysql.cj.exceptions.ExceptionInterceptor;
import com.mysql.cj.conf.PropertySet;
import com.mysql.cj.protocol.SocketConnection;
import com.mysql.cj.protocol.AbstractSocketConnection;

public class NativeSocketConnection extends AbstractSocketConnection implements SocketConnection
{
    @Override
    public void connect(final String hostName, final int portNumber, final PropertySet propSet, final ExceptionInterceptor excInterceptor, final Log log, final int loginTimeout) {
        try {
            this.port = portNumber;
            this.host = hostName;
            this.propertySet = propSet;
            this.exceptionInterceptor = excInterceptor;
            this.socketFactory = this.createSocketFactory(propSet.getStringProperty(PropertyKey.socketFactory).getStringValue());
            this.mysqlSocket = this.socketFactory.connect(this.host, this.port, propSet, loginTimeout);
            final int socketTimeout = propSet.getIntegerProperty(PropertyKey.socketTimeout).getValue();
            if (socketTimeout != 0) {
                try {
                    this.mysqlSocket.setSoTimeout(socketTimeout);
                }
                catch (Exception ex) {}
            }
            this.socketFactory.beforeHandshake();
            InputStream rawInputStream;
            if (propSet.getBooleanProperty(PropertyKey.useReadAheadInput).getValue()) {
                rawInputStream = new ReadAheadInputStream(this.mysqlSocket.getInputStream(), 16384, propSet.getBooleanProperty(PropertyKey.traceProtocol).getValue(), log);
            }
            else if (propSet.getBooleanProperty(PropertyKey.useUnbufferedInput).getValue()) {
                rawInputStream = this.mysqlSocket.getInputStream();
            }
            else {
                rawInputStream = new BufferedInputStream(this.mysqlSocket.getInputStream(), 16384);
            }
            this.mysqlInput = new FullReadInputStream(rawInputStream);
            this.mysqlOutput = new BufferedOutputStream(this.mysqlSocket.getOutputStream(), 16384);
        }
        catch (IOException ioEx) {
            throw ExceptionFactory.createCommunicationsException(propSet, null, new PacketSentTimeHolder() {}, null, ioEx, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void performTlsHandshake(final ServerSession serverSession) throws SSLParamsException, FeatureNotAvailableException, IOException {
        this.mysqlSocket = this.socketFactory.performTlsHandshake(this, serverSession);
        this.mysqlInput = new FullReadInputStream(this.propertySet.getBooleanProperty(PropertyKey.useUnbufferedInput).getValue() ? this.getMysqlSocket().getInputStream() : new BufferedInputStream(this.getMysqlSocket().getInputStream(), 16384));
        (this.mysqlOutput = new BufferedOutputStream(this.getMysqlSocket().getOutputStream(), 16384)).flush();
    }
    
    @Override
    public AsynchronousSocketChannel getAsynchronousSocketChannel() {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, "Not supported");
    }
}
