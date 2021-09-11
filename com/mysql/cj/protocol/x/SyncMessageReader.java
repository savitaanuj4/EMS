
package com.mysql.cj.protocol.x;

import java.util.concurrent.TimeUnit;
import com.mysql.cj.protocol.MessageHeader;
import java.util.List;
import com.google.protobuf.Message;
import java.util.ArrayList;
import java.util.Optional;
import com.google.protobuf.InvalidProtocolBufferException;
import com.mysql.cj.exceptions.WrongArgumentException;
import com.google.protobuf.Parser;
import com.google.protobuf.GeneratedMessageV3;
import com.mysql.cj.exceptions.CJCommunicationsException;
import java.io.IOException;
import com.mysql.cj.x.protobuf.Mysqlx;
import java.util.concurrent.LinkedBlockingQueue;
import com.mysql.cj.protocol.MessageListener;
import java.util.concurrent.BlockingQueue;
import com.mysql.cj.protocol.FullReadInputStream;
import com.mysql.cj.protocol.MessageReader;

public class SyncMessageReader implements MessageReader<XMessageHeader, XMessage>
{
    private FullReadInputStream inputStream;
    private XMessageHeader header;
    BlockingQueue<MessageListener<XMessage>> messageListenerQueue;
    Object dispatchingThreadMonitor;
    Object waitingSyncOperationMonitor;
    Thread dispatchingThread;
    
    public SyncMessageReader(final FullReadInputStream inputStream) {
        this.messageListenerQueue = new LinkedBlockingQueue<MessageListener<XMessage>>();
        this.dispatchingThreadMonitor = new Object();
        this.waitingSyncOperationMonitor = new Object();
        this.dispatchingThread = null;
        this.inputStream = inputStream;
    }
    
    @Override
    public XMessageHeader readHeader() throws IOException {
        synchronized (this.waitingSyncOperationMonitor) {
            if (this.header == null) {
                this.header = this.readHeaderLocal();
            }
            if (this.header.getMessageType() == 1) {
                throw new XProtocolError(this.readMessageLocal(Mysqlx.Error.class));
            }
            return this.header;
        }
    }
    
    private XMessageHeader readHeaderLocal() throws IOException {
        try {
            final byte[] len = new byte[5];
            this.inputStream.readFully(len);
            this.header = new XMessageHeader(len);
        }
        catch (IOException ex) {
            throw new CJCommunicationsException("Cannot read packet header", ex);
        }
        return this.header;
    }
    
    private <T extends GeneratedMessageV3> T readMessageLocal(final Class<T> messageClass) {
        final Parser<T> parser = (Parser<T>)MessageConstants.MESSAGE_CLASS_TO_PARSER.get(messageClass);
        final byte[] packet = new byte[this.header.getMessageSize()];
        try {
            this.inputStream.readFully(packet);
        }
        catch (IOException ex) {
            throw new CJCommunicationsException("Cannot read packet payload", ex);
        }
        try {
            return (T)parser.parseFrom(packet);
        }
        catch (InvalidProtocolBufferException ex2) {
            throw new WrongArgumentException((Throwable)ex2);
        }
        finally {
            this.header = null;
        }
    }
    
    @Override
    public XMessage readMessage(final Optional<XMessage> reuse, final XMessageHeader hdr) throws IOException {
        return this.readMessage(reuse, hdr.getMessageType());
    }
    
    @Override
    public XMessage readMessage(final Optional<XMessage> reuse, final int expectedType) throws IOException {
        synchronized (this.waitingSyncOperationMonitor) {
            try {
                final Class<? extends GeneratedMessageV3> expectedClass = MessageConstants.getMessageClassForType(expectedType);
                List<Notice> notices = null;
                XMessageHeader hdr;
                while ((hdr = this.readHeader()).getMessageType() == 11 && expectedType != 11) {
                    if (notices == null) {
                        notices = new ArrayList<Notice>();
                    }
                    notices.add(Notice.getInstance(new XMessage((Message)this.readMessageLocal(MessageConstants.getMessageClassForType(11)))));
                }
                final Class<? extends GeneratedMessageV3> messageClass = MessageConstants.getMessageClassForType(hdr.getMessageType());
                if (expectedClass != messageClass) {
                    throw new WrongArgumentException("Unexpected message class. Expected '" + expectedClass.getSimpleName() + "' but actually received '" + messageClass.getSimpleName() + "'");
                }
                return new XMessage((Message)this.readMessageLocal(messageClass)).addNotices(notices);
            }
            catch (IOException e) {
                throw new XProtocolError(e.getMessage(), e);
            }
        }
    }
    
    @Override
    public void pushMessageListener(final MessageListener<XMessage> listener) {
        try {
            this.messageListenerQueue.put(listener);
        }
        catch (InterruptedException e) {
            throw new CJCommunicationsException("Cannot queue message listener.", e);
        }
        synchronized (this.dispatchingThreadMonitor) {
            if (this.dispatchingThread == null) {
                final ListenersDispatcher ld = new ListenersDispatcher();
                (this.dispatchingThread = new Thread(ld, "Message listeners dispatching thread")).start();
                int millis = 5000;
                while (!ld.started) {
                    try {
                        Thread.sleep(10L);
                        millis -= 10;
                    }
                    catch (InterruptedException e2) {
                        throw new XProtocolError(e2.getMessage(), e2);
                    }
                    if (millis <= 0) {
                        throw new XProtocolError("Timeout for starting ListenersDispatcher exceeded.");
                    }
                }
            }
        }
    }
    
    private class ListenersDispatcher implements Runnable
    {
        private static final long POLL_TIMEOUT = 100L;
        boolean started;
        
        public ListenersDispatcher() {
            this.started = false;
        }
        
        @Override
        public void run() {
            synchronized (SyncMessageReader.this.waitingSyncOperationMonitor) {
                this.started = true;
                try {
                    while (true) {
                        final MessageListener<XMessage> l;
                        if ((l = SyncMessageReader.this.messageListenerQueue.poll(100L, TimeUnit.MILLISECONDS)) == null) {
                            synchronized (SyncMessageReader.this.dispatchingThreadMonitor) {
                                if (SyncMessageReader.this.messageListenerQueue.peek() == null) {
                                    SyncMessageReader.this.dispatchingThread = null;
                                    break;
                                }
                                continue;
                            }
                        }
                        else {
                            try {
                                XMessage msg = null;
                                do {
                                    final XMessageHeader hdr = SyncMessageReader.this.readHeader();
                                    msg = SyncMessageReader.this.readMessage((Optional<XMessage>)null, hdr);
                                } while (!l.createFromMessage(msg));
                            }
                            catch (Throwable t) {
                                l.error(t);
                            }
                        }
                    }
                }
                catch (InterruptedException e) {
                    throw new CJCommunicationsException("Read operation interrupted.", e);
                }
            }
        }
    }
}
