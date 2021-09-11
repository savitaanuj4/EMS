
package com.mysql.cj.protocol;

import com.mysql.cj.Messages;
import java.net.SocketAddress;
import java.net.InetSocketAddress;
import java.net.InetAddress;
import java.io.Closeable;
import java.io.IOException;
import java.net.SocketException;
import com.mysql.cj.conf.PropertyKey;
import com.mysql.cj.conf.PropertySet;
import java.net.Socket;

public class StandardSocketFactory implements SocketFactory
{
    protected String host;
    protected int port;
    protected Socket rawSocket;
    protected Socket sslSocket;
    protected int loginTimeoutCountdown;
    protected long loginTimeoutCheckTimestamp;
    protected int socketTimeoutBackup;
    
    public StandardSocketFactory() {
        this.host = null;
        this.port = 3306;
        this.rawSocket = null;
        this.sslSocket = null;
        this.loginTimeoutCountdown = 0;
        this.loginTimeoutCheckTimestamp = System.currentTimeMillis();
        this.socketTimeoutBackup = 0;
    }
    
    protected Socket createSocket(final PropertySet props) {
        return new Socket();
    }
    
    private void configureSocket(final Socket sock, final PropertySet pset) throws SocketException, IOException {
        sock.setTcpNoDelay(pset.getBooleanProperty(PropertyKey.tcpNoDelay).getValue());
        sock.setKeepAlive(pset.getBooleanProperty(PropertyKey.tcpKeepAlive).getValue());
        final int receiveBufferSize = pset.getIntegerProperty(PropertyKey.tcpRcvBuf).getValue();
        if (receiveBufferSize > 0) {
            sock.setReceiveBufferSize(receiveBufferSize);
        }
        final int sendBufferSize = pset.getIntegerProperty(PropertyKey.tcpSndBuf).getValue();
        if (sendBufferSize > 0) {
            sock.setSendBufferSize(sendBufferSize);
        }
        final int trafficClass = pset.getIntegerProperty(PropertyKey.tcpTrafficClass).getValue();
        if (trafficClass > 0) {
            sock.setTrafficClass(trafficClass);
        }
    }
    
    @Override
    public <T extends Closeable> T connect(final String hostname, final int portNumber, final PropertySet pset, final int loginTimeout) throws IOException {
        this.loginTimeoutCountdown = loginTimeout;
        if (pset != null) {
            this.host = hostname;
            this.port = portNumber;
            final String localSocketHostname = pset.getStringProperty(PropertyKey.localSocketAddress).getValue();
            InetSocketAddress localSockAddr = null;
            if (localSocketHostname != null && localSocketHostname.length() > 0) {
                localSockAddr = new InetSocketAddress(InetAddress.getByName(localSocketHostname), 0);
            }
            final int connectTimeout = pset.getIntegerProperty(PropertyKey.connectTimeout).getValue();
            if (this.host != null) {
                final InetAddress[] possibleAddresses = InetAddress.getAllByName(this.host);
                if (possibleAddresses.length == 0) {
                    throw new SocketException("No addresses for host");
                }
                SocketException lastException = null;
                int i = 0;
                while (i < possibleAddresses.length) {
                    try {
                        this.configureSocket(this.rawSocket = this.createSocket(pset), pset);
                        final InetSocketAddress sockAddr = new InetSocketAddress(possibleAddresses[i], this.port);
                        if (localSockAddr != null) {
                            this.rawSocket.bind(localSockAddr);
                        }
                        this.rawSocket.connect(sockAddr, this.getRealTimeout(connectTimeout));
                    }
                    catch (SocketException ex) {
                        lastException = ex;
                        this.resetLoginTimeCountdown();
                        this.rawSocket = null;
                        ++i;
                        continue;
                    }
                    break;
                }
                if (this.rawSocket == null && lastException != null) {
                    throw lastException;
                }
                this.resetLoginTimeCountdown();
                this.sslSocket = this.rawSocket;
                return (T)this.rawSocket;
            }
        }
        throw new SocketException("Unable to create socket");
    }
    
    @Override
    public void beforeHandshake() throws IOException {
        this.resetLoginTimeCountdown();
        this.socketTimeoutBackup = this.rawSocket.getSoTimeout();
        this.rawSocket.setSoTimeout(this.getRealTimeout(this.socketTimeoutBackup));
    }
    
    @Override
    public <T extends Closeable> T performTlsHandshake(final SocketConnection socketConnection, final ServerSession serverSession) throws IOException {
        return (T)(this.sslSocket = ExportControlled.performTlsHandshake(this.rawSocket, socketConnection, (serverSession == null) ? null : serverSession.getServerVersion()));
    }
    
    @Override
    public void afterHandshake() throws IOException {
        this.resetLoginTimeCountdown();
        this.rawSocket.setSoTimeout(this.socketTimeoutBackup);
    }
    
    protected void resetLoginTimeCountdown() throws SocketException {
        if (this.loginTimeoutCountdown > 0) {
            final long now = System.currentTimeMillis();
            this.loginTimeoutCountdown -= (int)(now - this.loginTimeoutCheckTimestamp);
            if (this.loginTimeoutCountdown <= 0) {
                throw new SocketException(Messages.getString("Connection.LoginTimeout"));
            }
            this.loginTimeoutCheckTimestamp = now;
        }
    }
    
    protected int getRealTimeout(final int expectedTimeout) {
        if (this.loginTimeoutCountdown > 0 && (expectedTimeout == 0 || expectedTimeout > this.loginTimeoutCountdown)) {
            return this.loginTimeoutCountdown;
        }
        return expectedTimeout;
    }
}
