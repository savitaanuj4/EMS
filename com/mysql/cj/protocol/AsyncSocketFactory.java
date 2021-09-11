
package com.mysql.cj.protocol;

import java.util.concurrent.Future;
import java.util.concurrent.ExecutionException;
import java.io.IOException;
import com.mysql.cj.exceptions.CJCommunicationsException;
import java.net.SocketAddress;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.io.Closeable;
import com.mysql.cj.conf.PropertySet;
import java.nio.channels.AsynchronousSocketChannel;

public class AsyncSocketFactory implements SocketFactory
{
    AsynchronousSocketChannel channel;
    
    @Override
    public <T extends Closeable> T connect(final String host, final int port, final PropertySet props, final int loginTimeout) throws IOException {
        try {
            (this.channel = AsynchronousSocketChannel.open()).setOption(StandardSocketOptions.SO_SNDBUF, Integer.valueOf(131072));
            this.channel.setOption(StandardSocketOptions.SO_RCVBUF, Integer.valueOf(131072));
            final Future<Void> connectPromise = this.channel.connect(new InetSocketAddress(host, port));
            connectPromise.get();
        }
        catch (CJCommunicationsException e) {
            throw e;
        }
        catch (IOException | InterruptedException | ExecutionException | RuntimeException ex3) {
            final Exception ex2;
            final Exception ex = ex2;
            throw new CJCommunicationsException(ex);
        }
        return (T)this.channel;
    }
    
    @Override
    public <T extends Closeable> T performTlsHandshake(final SocketConnection socketConnection, final ServerSession serverSession) throws IOException {
        return (T)(this.channel = ExportControlled.startTlsOnAsynchronousChannel(this.channel, socketConnection));
    }
}
