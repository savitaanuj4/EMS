
package com.mysql.cj.protocol;

import java.nio.channels.NetworkChannel;
import java.net.SocketAddress;
import java.util.Set;
import java.net.SocketOption;
import javax.net.ssl.SSLException;
import com.mysql.cj.exceptions.CJCommunicationsException;
import java.util.concurrent.Future;
import java.io.IOException;
import com.mysql.cj.exceptions.AssertionFailedException;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.SSLEngineResult;
import java.nio.BufferOverflowException;
import java.nio.channels.spi.AsynchronousChannelProvider;
import java.util.concurrent.LinkedBlockingQueue;
import javax.net.ssl.SSLEngine;
import java.nio.ByteBuffer;
import java.nio.channels.CompletionHandler;
import java.nio.channels.AsynchronousSocketChannel;

public class TlsAsynchronousSocketChannel extends AsynchronousSocketChannel implements CompletionHandler<Integer, Void>
{
    private static final ByteBuffer emptyBuffer;
    private AsynchronousSocketChannel channel;
    private SSLEngine sslEngine;
    private ByteBuffer cipherTextBuffer;
    private ByteBuffer clearTextBuffer;
    private CompletionHandler<Integer, ?> handler;
    private ByteBuffer dst;
    private SerializingBufferWriter bufferWriter;
    private LinkedBlockingQueue<ByteBuffer> cipherTextBuffers;
    
    public TlsAsynchronousSocketChannel(final AsynchronousSocketChannel in, final SSLEngine sslEngine) {
        super(null);
        this.cipherTextBuffers = new LinkedBlockingQueue<ByteBuffer>();
        this.sslEngine = sslEngine;
        this.channel = in;
        this.sslEngine = sslEngine;
        (this.cipherTextBuffer = ByteBuffer.allocate(sslEngine.getSession().getPacketBufferSize())).flip();
        (this.clearTextBuffer = ByteBuffer.allocate(sslEngine.getSession().getApplicationBufferSize())).flip();
        this.bufferWriter = new SerializingBufferWriter(this.channel);
    }
    
    @Override
    public void completed(final Integer result, final Void attachment) {
        if (result < 0) {
            final CompletionHandler<Integer, ?> h = this.handler;
            this.handler = null;
            h.completed(result, null);
            return;
        }
        this.cipherTextBuffer.flip();
        this.decryptAndDispatch();
    }
    
    @Override
    public void failed(final Throwable exc, final Void attachment) {
        final CompletionHandler<Integer, ?> h = this.handler;
        this.handler = null;
        h.failed(exc, null);
    }
    
    private synchronized void decryptAndDispatch() {
        try {
            this.clearTextBuffer.clear();
            final SSLEngineResult res = this.sslEngine.unwrap(this.cipherTextBuffer, this.clearTextBuffer);
            switch (res.getStatus()) {
                case BUFFER_UNDERFLOW: {
                    final int newPeerNetDataSize = this.sslEngine.getSession().getPacketBufferSize();
                    if (newPeerNetDataSize > this.cipherTextBuffer.capacity()) {
                        final ByteBuffer newPeerNetData = ByteBuffer.allocate(newPeerNetDataSize);
                        newPeerNetData.put(this.cipherTextBuffer);
                        newPeerNetData.flip();
                        this.cipherTextBuffer = newPeerNetData;
                    }
                    else {
                        this.cipherTextBuffer.compact();
                    }
                    this.channel.read(this.cipherTextBuffer, (Object)null, (CompletionHandler<Integer, ? super Object>)this);
                }
                case BUFFER_OVERFLOW: {
                    throw new BufferOverflowException();
                }
                case OK: {
                    this.clearTextBuffer.flip();
                    this.dispatchData();
                    break;
                }
                case CLOSED: {
                    this.handler.completed(-1, null);
                    break;
                }
            }
        }
        catch (Throwable ex) {
            this.failed(ex, null);
        }
    }
    
    @Override
    public <A> void read(final ByteBuffer dest, final long timeout, final TimeUnit unit, final A attachment, final CompletionHandler<Integer, ? super A> hdlr) {
        try {
            if (this.handler != null) {
                hdlr.completed(Integer.valueOf(0), (Object)null);
            }
            this.handler = hdlr;
            this.dst = dest;
            if (this.clearTextBuffer.hasRemaining()) {
                this.dispatchData();
            }
            else if (this.cipherTextBuffer.hasRemaining()) {
                this.decryptAndDispatch();
            }
            else {
                this.cipherTextBuffer.clear();
                this.channel.read(this.cipherTextBuffer, (Object)null, (CompletionHandler<Integer, ? super Object>)this);
            }
        }
        catch (Throwable ex) {
            hdlr.failed(ex, (Object)null);
        }
    }
    
    @Override
    public <A> void read(final ByteBuffer[] dsts, final int offset, final int length, final long timeout, final TimeUnit unit, final A attachment, final CompletionHandler<Long, ? super A> hdlr) {
        hdlr.failed((Throwable)new UnsupportedOperationException(), (Object)null);
    }
    
    private synchronized void dispatchData() {
        final int transferred = Math.min(this.dst.remaining(), this.clearTextBuffer.remaining());
        if (this.clearTextBuffer.remaining() > this.dst.remaining()) {
            final int newLimit = this.clearTextBuffer.position() + transferred;
            final ByteBuffer src = this.clearTextBuffer.duplicate();
            src.limit(newLimit);
            this.dst.put(src);
            this.clearTextBuffer.position(this.clearTextBuffer.position() + transferred);
        }
        else {
            this.dst.put(this.clearTextBuffer);
        }
        final CompletionHandler<Integer, ?> h = this.handler;
        this.handler = null;
        if (this.channel.isOpen()) {
            this.channel.read(TlsAsynchronousSocketChannel.emptyBuffer, (Object)null, (CompletionHandler<Integer, ? super Object>)new CompletionHandler<Integer, Void>() {
                @Override
                public void completed(final Integer result, final Void attachment) {
                    h.completed(transferred, null);
                }
                
                @Override
                public void failed(final Throwable t, final Void attachment) {
                    t.printStackTrace();
                    h.failed(AssertionFailedException.shouldNotHappen(new Exception(t)), null);
                }
            });
        }
        else {
            h.completed(transferred, null);
        }
    }
    
    @Override
    public void close() throws IOException {
        this.channel.close();
    }
    
    @Override
    public boolean isOpen() {
        return this.channel.isOpen();
    }
    
    @Override
    public Future<Integer> read(final ByteBuffer dest) {
        throw new UnsupportedOperationException("This channel does not support direct reads");
    }
    
    @Override
    public Future<Integer> write(final ByteBuffer src) {
        throw new UnsupportedOperationException("This channel does not support writes");
    }
    
    private boolean isDrained(final ByteBuffer[] buffers) {
        for (final ByteBuffer b : buffers) {
            if (b.hasRemaining()) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public <A> void write(final ByteBuffer[] srcs, final int offset, final int length, final long timeout, final TimeUnit unit, final A attachment, final CompletionHandler<Long, ? super A> hdlr) {
        try {
            long totalWriteSize = 0L;
            ByteBuffer cipherText;
            while (true) {
                cipherText = this.getCipherTextBuffer();
                final SSLEngineResult res = this.sslEngine.wrap(srcs, offset, length, cipherText);
                if (res.getStatus() != SSLEngineResult.Status.OK) {
                    hdlr.failed((Throwable)new CJCommunicationsException("Unacceptable SSLEngine result: " + res), (Object)null);
                }
                totalWriteSize += res.bytesConsumed();
                cipherText.flip();
                if (this.isDrained(srcs)) {
                    break;
                }
                this.bufferWriter.queueBuffer(cipherText, new ErrorPropagatingCompletionHandler<Long>(hdlr, () -> this.putCipherTextBuffer(cipherText)));
            }
            final long finalTotal = totalWriteSize;
            final long l;
            final ByteBuffer buf;
            final Runnable successHandler = () -> {
                hdlr.completed(Long.valueOf(l), (Object)null);
                this.putCipherTextBuffer(buf);
                return;
            };
            this.bufferWriter.queueBuffer(cipherText, new ErrorPropagatingCompletionHandler<Long>(hdlr, successHandler));
        }
        catch (SSLException ex) {
            hdlr.failed((Throwable)new CJCommunicationsException(ex), (Object)null);
        }
        catch (Throwable ex2) {
            hdlr.failed(ex2, (Object)null);
        }
    }
    
    @Override
    public <A> void write(final ByteBuffer src, final long timeout, final TimeUnit unit, final A attachment, final CompletionHandler<Integer, ? super A> hdlr) {
        hdlr.failed((Throwable)new UnsupportedOperationException(), (Object)null);
    }
    
    private ByteBuffer getCipherTextBuffer() {
        final ByteBuffer buf = this.cipherTextBuffers.poll();
        if (buf == null) {
            return ByteBuffer.allocate(this.sslEngine.getSession().getPacketBufferSize());
        }
        buf.clear();
        return buf;
    }
    
    private void putCipherTextBuffer(final ByteBuffer buf) {
        if (this.cipherTextBuffers.size() < 10) {
            this.cipherTextBuffers.offer(buf);
        }
    }
    
    @Override
    public <T> T getOption(final SocketOption<T> name) throws IOException {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public Set<SocketOption<?>> supportedOptions() {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public AsynchronousSocketChannel bind(final SocketAddress local) throws IOException {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public <T> AsynchronousSocketChannel setOption(final SocketOption<T> name, final T value) throws IOException {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public AsynchronousSocketChannel shutdownInput() throws IOException {
        return this.channel.shutdownInput();
    }
    
    @Override
    public AsynchronousSocketChannel shutdownOutput() throws IOException {
        return this.channel.shutdownOutput();
    }
    
    @Override
    public SocketAddress getRemoteAddress() throws IOException {
        return this.channel.getRemoteAddress();
    }
    
    @Override
    public <A> void connect(final SocketAddress remote, final A attachment, final CompletionHandler<Void, ? super A> hdlr) {
        hdlr.failed((Throwable)new UnsupportedOperationException(), (Object)null);
    }
    
    @Override
    public Future<Void> connect(final SocketAddress remote) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public SocketAddress getLocalAddress() throws IOException {
        return this.channel.getLocalAddress();
    }
    
    static {
        emptyBuffer = ByteBuffer.allocate(0);
    }
    
    private static class ErrorPropagatingCompletionHandler<V> implements CompletionHandler<V, Void>
    {
        private CompletionHandler<Long, ?> target;
        private Runnable success;
        
        public ErrorPropagatingCompletionHandler(final CompletionHandler<Long, ?> target, final Runnable success) {
            this.target = target;
            this.success = success;
        }
        
        @Override
        public void completed(final V result, final Void attachment) {
            this.success.run();
        }
        
        @Override
        public void failed(final Throwable ex, final Void attachment) {
            this.target.failed(ex, null);
        }
    }
}
