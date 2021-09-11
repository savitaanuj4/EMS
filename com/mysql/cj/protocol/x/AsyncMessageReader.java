
package com.mysql.cj.protocol.x;

import com.mysql.cj.exceptions.WrongArgumentException;
import java.util.ArrayList;
import com.mysql.cj.x.protobuf.Mysqlx;
import java.util.List;
import com.google.protobuf.InvalidProtocolBufferException;
import com.mysql.cj.exceptions.AssertionFailedException;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.Parser;
import com.mysql.cj.x.protobuf.MysqlxNotice;
import java.nio.channels.AsynchronousCloseException;
import com.mysql.cj.protocol.MessageHeader;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.TimeUnit;
import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.Message;
import com.mysql.cj.exceptions.CJCommunicationsException;
import com.mysql.cj.conf.PropertyKey;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.BlockingQueue;
import com.mysql.cj.protocol.MessageListener;
import com.mysql.cj.conf.RuntimeProperty;
import java.nio.channels.CompletionHandler;
import com.mysql.cj.protocol.SocketConnection;
import com.mysql.cj.conf.PropertySet;
import java.nio.ByteBuffer;
import com.mysql.cj.protocol.MessageReader;

public class AsyncMessageReader implements MessageReader<XMessageHeader, XMessage>
{
    private static int READ_AHEAD_DEPTH;
    CompletedRead currentReadResult;
    ByteBuffer messageBuf;
    private PropertySet propertySet;
    SocketConnection sc;
    CompletionHandler<Integer, Void> headerCompletionHandler;
    CompletionHandler<Integer, Void> messageCompletionHandler;
    RuntimeProperty<Integer> asyncTimeout;
    MessageListener<XMessage> currentMessageListener;
    private BlockingQueue<MessageListener<XMessage>> messageListenerQueue;
    BlockingQueue<CompletedRead> pendingCompletedReadQueue;
    CompletableFuture<XMessageHeader> pendingMsgHeader;
    Object pendingMsgMonitor;
    boolean stopAfterNextMessage;
    
    public AsyncMessageReader(final PropertySet propertySet, final SocketConnection socketConnection) {
        this.headerCompletionHandler = new HeaderCompletionHandler();
        this.messageCompletionHandler = new MessageCompletionHandler();
        this.messageListenerQueue = new LinkedBlockingQueue<MessageListener<XMessage>>();
        this.pendingCompletedReadQueue = new LinkedBlockingQueue<CompletedRead>(AsyncMessageReader.READ_AHEAD_DEPTH);
        this.pendingMsgMonitor = new Object();
        this.stopAfterNextMessage = false;
        this.propertySet = propertySet;
        this.sc = socketConnection;
        this.asyncTimeout = this.propertySet.getIntegerProperty(PropertyKey.xdevapiAsyncResponseTimeout);
    }
    
    @Override
    public void start() {
        this.headerCompletionHandler.completed(0, null);
    }
    
    @Override
    public void stopAfterNextMessage() {
        this.stopAfterNextMessage = true;
    }
    
    private void checkClosed() {
        if (!this.sc.getAsynchronousSocketChannel().isOpen()) {
            throw new CJCommunicationsException("Socket closed");
        }
    }
    
    @Override
    public void pushMessageListener(final MessageListener<XMessage> l) {
        this.checkClosed();
        this.messageListenerQueue.add(l);
    }
    
    MessageListener<XMessage> getMessageListener(final boolean block) {
        try {
            if (this.currentMessageListener == null) {
                this.currentMessageListener = (block ? this.messageListenerQueue.take() : this.messageListenerQueue.poll());
            }
            return this.currentMessageListener;
        }
        catch (InterruptedException ex) {
            throw new CJCommunicationsException(ex);
        }
    }
    
    void dispatchMessage() {
        if (this.pendingCompletedReadQueue.isEmpty()) {
            return;
        }
        if (this.getMessageListener(true) != null) {
            CompletedRead res;
            try {
                res = this.pendingCompletedReadQueue.take();
            }
            catch (InterruptedException e) {
                throw new CJCommunicationsException("Failed to peek pending message", e);
            }
            final GeneratedMessageV3 message = res.message;
            synchronized (this.pendingMsgMonitor) {
                final boolean currentListenerDone = this.currentMessageListener.createFromMessage(new XMessage((Message)message));
                if (currentListenerDone) {
                    this.currentMessageListener = null;
                }
                this.pendingMsgHeader = null;
            }
        }
    }
    
    void onError(final Throwable t) {
        try {
            this.sc.getAsynchronousSocketChannel().close();
        }
        catch (Exception ex) {}
        if (this.currentMessageListener != null) {
            try {
                this.currentMessageListener.error(t);
            }
            catch (Exception ex2) {}
            this.currentMessageListener = null;
        }
        this.messageListenerQueue.forEach(l -> {
            try {
                l.error(t);
            }
            catch (Exception ex3) {}
            return;
        });
        synchronized (this.pendingMsgMonitor) {
            (this.pendingMsgHeader = new CompletableFuture<XMessageHeader>()).completeExceptionally(t);
            this.pendingMsgMonitor.notify();
        }
        this.messageListenerQueue.clear();
    }
    
    @Override
    public XMessageHeader readHeader() throws IOException {
        XMessageHeader mh;
        synchronized (this.pendingMsgMonitor) {
            this.checkClosed();
            while (this.pendingMsgHeader == null) {
                try {
                    this.pendingMsgMonitor.wait();
                    continue;
                }
                catch (InterruptedException ex) {
                    throw new CJCommunicationsException(ex);
                }
                break;
            }
            try {
                mh = this.pendingMsgHeader.get();
            }
            catch (ExecutionException ex2) {
                throw new CJCommunicationsException("Failed to peek pending message", ex2.getCause());
            }
            catch (InterruptedException ex) {
                throw new CJCommunicationsException(ex);
            }
        }
        if (mh.getMessageType() == 1) {
            this.readMessage((Optional<XMessage>)null, mh);
        }
        return mh;
    }
    
    @Override
    public XMessage readMessage(final Optional<XMessage> reuse, final XMessageHeader hdr) throws IOException {
        return this.readMessage(reuse, hdr.getMessageType());
    }
    
    @Override
    public XMessage readMessage(final Optional<XMessage> reuse, final int expectedType) throws IOException {
        final Class<? extends GeneratedMessageV3> expectedClass = MessageConstants.getMessageClassForType(expectedType);
        final CompletableFuture<XMessage> future = new CompletableFuture<XMessage>();
        final SyncXMessageListener<? extends GeneratedMessageV3> r = new SyncXMessageListener<GeneratedMessageV3>(future, expectedClass);
        this.pushMessageListener(r);
        try {
            return future.get(this.asyncTimeout.getValue(), TimeUnit.SECONDS);
        }
        catch (ExecutionException ex) {
            if (XProtocolError.class.equals(ex.getCause().getClass())) {
                throw new XProtocolError((XProtocolError)ex.getCause());
            }
            throw new CJCommunicationsException(ex.getCause().getMessage(), ex.getCause());
        }
        catch (InterruptedException | TimeoutException ex4) {
            final Exception ex3;
            final Exception ex2 = ex3;
            throw new CJCommunicationsException(ex2);
        }
    }
    
    static {
        AsyncMessageReader.READ_AHEAD_DEPTH = 10;
    }
    
    private static class CompletedRead
    {
        public XMessageHeader header;
        public GeneratedMessageV3 message;
        
        public CompletedRead() {
            this.header = null;
            this.message = null;
        }
    }
    
    private class HeaderCompletionHandler implements CompletionHandler<Integer, Void>
    {
        public HeaderCompletionHandler() {
        }
        
        @Override
        public void completed(final Integer bytesRead, final Void attachment) {
            if (bytesRead < 0) {
                AsyncMessageReader.this.onError(new CJCommunicationsException("Socket closed"));
                return;
            }
            try {
                if (AsyncMessageReader.this.currentReadResult == null) {
                    AsyncMessageReader.this.currentReadResult = new CompletedRead();
                    AsyncMessageReader.this.currentReadResult.header = new XMessageHeader();
                }
                if (AsyncMessageReader.this.currentReadResult.header.getBuffer().position() < 5) {
                    AsyncMessageReader.this.sc.getAsynchronousSocketChannel().read(AsyncMessageReader.this.currentReadResult.header.getBuffer(), (Object)null, (CompletionHandler<Integer, ? super Object>)this);
                    return;
                }
                AsyncMessageReader.this.messageBuf = ByteBuffer.allocate(AsyncMessageReader.this.currentReadResult.header.getMessageSize());
                if (AsyncMessageReader.this.getMessageListener(false) == null) {
                    synchronized (AsyncMessageReader.this.pendingMsgMonitor) {
                        AsyncMessageReader.this.pendingMsgHeader = CompletableFuture.completedFuture(AsyncMessageReader.this.currentReadResult.header);
                        AsyncMessageReader.this.pendingMsgMonitor.notify();
                    }
                }
                AsyncMessageReader.this.messageCompletionHandler.completed(0, null);
            }
            catch (Throwable t) {
                AsyncMessageReader.this.onError(t);
            }
        }
        
        @Override
        public void failed(final Throwable exc, final Void attachment) {
            if (AsyncMessageReader.this.getMessageListener(false) != null) {
                synchronized (AsyncMessageReader.this.pendingMsgMonitor) {
                    AsyncMessageReader.this.pendingMsgMonitor.notify();
                }
                if (AsynchronousCloseException.class.equals(exc.getClass())) {
                    AsyncMessageReader.this.currentMessageListener.error(new CJCommunicationsException("Socket closed", exc));
                }
                else {
                    AsyncMessageReader.this.currentMessageListener.error(exc);
                }
            }
            AsyncMessageReader.this.currentMessageListener = null;
        }
    }
    
    private class MessageCompletionHandler implements CompletionHandler<Integer, Void>
    {
        public MessageCompletionHandler() {
        }
        
        @Override
        public void completed(final Integer bytesRead, final Void attachment) {
            if (bytesRead < 0) {
                AsyncMessageReader.this.onError(new CJCommunicationsException("Socket closed"));
                return;
            }
            try {
                if (AsyncMessageReader.this.messageBuf.position() < AsyncMessageReader.this.currentReadResult.header.getMessageSize()) {
                    AsyncMessageReader.this.sc.getAsynchronousSocketChannel().read(AsyncMessageReader.this.messageBuf, (Object)null, (CompletionHandler<Integer, ? super Object>)this);
                    return;
                }
                final ByteBuffer buf = AsyncMessageReader.this.messageBuf;
                AsyncMessageReader.this.messageBuf = null;
                final Class<? extends GeneratedMessageV3> messageClass = MessageConstants.getMessageClassForType(AsyncMessageReader.this.currentReadResult.header.getMessageType());
                final boolean localStopAfterNextMessage = AsyncMessageReader.this.stopAfterNextMessage;
                buf.flip();
                AsyncMessageReader.this.currentReadResult.message = this.parseMessage(messageClass, buf);
                AsyncMessageReader.this.pendingCompletedReadQueue.add(AsyncMessageReader.this.currentReadResult);
                AsyncMessageReader.this.currentReadResult = null;
                AsyncMessageReader.this.dispatchMessage();
                if (localStopAfterNextMessage && messageClass != MysqlxNotice.Frame.class) {
                    AsyncMessageReader.this.stopAfterNextMessage = false;
                    AsyncMessageReader.this.currentReadResult = null;
                    return;
                }
                AsyncMessageReader.this.headerCompletionHandler.completed(0, null);
            }
            catch (Throwable t) {
                AsyncMessageReader.this.onError(t);
            }
        }
        
        @Override
        public void failed(final Throwable exc, final Void attachment) {
            if (AsyncMessageReader.this.getMessageListener(false) != null) {
                synchronized (AsyncMessageReader.this.pendingMsgMonitor) {
                    AsyncMessageReader.this.pendingMsgMonitor.notify();
                }
                if (AsynchronousCloseException.class.equals(exc.getClass())) {
                    AsyncMessageReader.this.currentMessageListener.error(new CJCommunicationsException("Socket closed", exc));
                }
                else {
                    AsyncMessageReader.this.currentMessageListener.error(exc);
                }
            }
            AsyncMessageReader.this.currentMessageListener = null;
        }
        
        private GeneratedMessageV3 parseMessage(final Class<? extends GeneratedMessageV3> messageClass, final ByteBuffer buf) {
            try {
                final Parser<? extends GeneratedMessageV3> parser = MessageConstants.MESSAGE_CLASS_TO_PARSER.get(messageClass);
                return (GeneratedMessageV3)parser.parseFrom(CodedInputStream.newInstance(buf));
            }
            catch (InvalidProtocolBufferException ex) {
                throw AssertionFailedException.shouldNotHappen((Exception)ex);
            }
        }
    }
    
    private static final class SyncXMessageListener<T extends GeneratedMessageV3> implements MessageListener<XMessage>
    {
        private CompletableFuture<XMessage> future;
        private Class<T> expectedClass;
        List<Notice> notices;
        
        public SyncXMessageListener(final CompletableFuture<XMessage> future, final Class<T> expectedClass) {
            this.notices = null;
            this.future = future;
            this.expectedClass = expectedClass;
        }
        
        @Override
        public Boolean createFromMessage(final XMessage msg) {
            final Class<? extends GeneratedMessageV3> msgClass = (Class<? extends GeneratedMessageV3>)msg.getMessage().getClass();
            if (Mysqlx.Error.class.equals(msgClass)) {
                this.future.completeExceptionally(new XProtocolError(Mysqlx.Error.class.cast(msg.getMessage())));
                return true;
            }
            if (this.expectedClass.equals(msgClass)) {
                this.future.complete(msg.addNotices(this.notices));
                this.notices = null;
                return true;
            }
            if (MysqlxNotice.Frame.class.equals(msgClass)) {
                if (this.notices == null) {
                    this.notices = new ArrayList<Notice>();
                }
                this.notices.add(Notice.getInstance(msg));
                return false;
            }
            this.future.completeExceptionally(new WrongArgumentException("Unhandled msg class (" + msgClass + ") + msg=" + msg.getMessage()));
            return true;
        }
        
        @Override
        public void error(final Throwable ex) {
            this.future.completeExceptionally(ex);
        }
    }
}
