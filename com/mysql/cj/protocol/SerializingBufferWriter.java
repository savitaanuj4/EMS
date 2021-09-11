
package com.mysql.cj.protocol;

import java.util.function.Predicate;
import java.util.Objects;
import java.nio.channels.WritePendingException;
import java.nio.channels.ReadPendingException;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.Queue;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class SerializingBufferWriter implements CompletionHandler<Long, Void>
{
    private static int WRITES_AT_ONCE;
    protected AsynchronousSocketChannel channel;
    private Queue<ByteBufferWrapper> pendingWrites;
    
    public SerializingBufferWriter(final AsynchronousSocketChannel channel) {
        this.pendingWrites = new LinkedList<ByteBufferWrapper>();
        this.channel = channel;
    }
    
    private void initiateWrite() {
        try {
            final ByteBuffer[] bufs = this.pendingWrites.stream().limit(SerializingBufferWriter.WRITES_AT_ONCE).map((Function<? super Object, ?>)ByteBufferWrapper::getBuffer).toArray(ByteBuffer[]::new);
            this.channel.write(bufs, 0, bufs.length, 0L, TimeUnit.MILLISECONDS, (Object)null, (CompletionHandler<Long, ? super Object>)this);
        }
        catch (ReadPendingException | WritePendingException ex2) {
            final IllegalStateException ex;
            final IllegalStateException t = ex;
        }
        catch (Throwable t2) {
            this.failed(t2, (Void)null);
        }
    }
    
    public void queueBuffer(final ByteBuffer buf, final CompletionHandler<Long, Void> callback) {
        synchronized (this.pendingWrites) {
            this.pendingWrites.add(new ByteBufferWrapper(buf, callback));
            if (this.pendingWrites.size() == 1) {
                this.initiateWrite();
            }
        }
    }
    
    @Override
    public void completed(final Long bytesWritten, final Void v) {
        final LinkedList<CompletionHandler<Long, Void>> completedWrites = new LinkedList<CompletionHandler<Long, Void>>();
        synchronized (this.pendingWrites) {
            while (this.pendingWrites.peek() != null && !this.pendingWrites.peek().getBuffer().hasRemaining() && completedWrites.size() < SerializingBufferWriter.WRITES_AT_ONCE) {
                completedWrites.add(this.pendingWrites.remove().getHandler());
            }
            completedWrites.stream().filter(Objects::nonNull).forEach(l -> {
                try {
                    l.completed(0L, null);
                }
                catch (Throwable ex) {
                    try {
                        l.failed(ex, null);
                    }
                    catch (Throwable ex2) {
                        ex2.printStackTrace();
                    }
                }
                return;
            });
            if (this.pendingWrites.size() > 0) {
                this.initiateWrite();
            }
        }
    }
    
    @Override
    public void failed(final Throwable t, final Void v) {
        try {
            this.channel.close();
        }
        catch (Exception ex) {}
        final LinkedList<CompletionHandler<Long, Void>> failedWrites = new LinkedList<CompletionHandler<Long, Void>>();
        synchronized (this.pendingWrites) {
            while (this.pendingWrites.peek() != null) {
                final ByteBufferWrapper bw = this.pendingWrites.remove();
                if (bw.getHandler() != null) {
                    failedWrites.add(bw.getHandler());
                }
            }
        }
        failedWrites.forEach(l -> {
            try {
                l.failed(t, null);
            }
            catch (Exception ex2) {}
            return;
        });
        failedWrites.clear();
    }
    
    public void setChannel(final AsynchronousSocketChannel channel) {
        this.channel = channel;
    }
    
    static {
        SerializingBufferWriter.WRITES_AT_ONCE = 200;
    }
    
    private static class ByteBufferWrapper
    {
        private ByteBuffer buffer;
        private CompletionHandler<Long, Void> handler;
        
        ByteBufferWrapper(final ByteBuffer buffer, final CompletionHandler<Long, Void> completionHandler) {
            this.handler = null;
            this.buffer = buffer;
            this.handler = completionHandler;
        }
        
        public ByteBuffer getBuffer() {
            return this.buffer;
        }
        
        public CompletionHandler<Long, Void> getHandler() {
            return this.handler;
        }
    }
}
