
package com.mysql.cj.protocol.x;

import com.mysql.cj.protocol.Message;
import java.nio.channels.CompletionHandler;
import com.google.protobuf.MessageLite;
import java.io.IOException;
import com.mysql.cj.exceptions.CJCommunicationsException;
import java.io.OutputStream;
import java.nio.ByteOrder;
import java.nio.ByteBuffer;
import com.mysql.cj.exceptions.CJPacketTooBigException;
import com.mysql.cj.Messages;
import java.io.BufferedOutputStream;
import com.mysql.cj.protocol.PacketSentTimeHolder;
import com.mysql.cj.protocol.MessageSender;

public class SyncMessageSender implements MessageSender<XMessage>, PacketSentTimeHolder
{
    static final int HEADER_LEN = 5;
    private BufferedOutputStream outputStream;
    private long lastPacketSentTime;
    private long previousPacketSentTime;
    private int maxAllowedPacket;
    Object waitingAsyncOperationMonitor;
    
    public SyncMessageSender(final BufferedOutputStream os) {
        this.lastPacketSentTime = 0L;
        this.previousPacketSentTime = 0L;
        this.maxAllowedPacket = -1;
        this.waitingAsyncOperationMonitor = new Object();
        this.outputStream = os;
    }
    
    @Override
    public void send(final XMessage message) {
        synchronized (this.waitingAsyncOperationMonitor) {
            final MessageLite msg = (MessageLite)message.getMessage();
            try {
                final int type = MessageConstants.getTypeForMessageClass(msg.getClass());
                final int size = 1 + msg.getSerializedSize();
                if (this.maxAllowedPacket > 0 && size > this.maxAllowedPacket) {
                    throw new CJPacketTooBigException(Messages.getString("PacketTooBigException.1", new Object[] { size, this.maxAllowedPacket }));
                }
                final byte[] sizeHeader = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(size).array();
                this.outputStream.write(sizeHeader);
                this.outputStream.write(type);
                msg.writeTo((OutputStream)this.outputStream);
                this.outputStream.flush();
                this.previousPacketSentTime = this.lastPacketSentTime;
                this.lastPacketSentTime = System.currentTimeMillis();
            }
            catch (IOException ex) {
                throw new CJCommunicationsException("Unable to write message", ex);
            }
        }
    }
    
    @Override
    public void send(final XMessage message, final CompletionHandler<Long, Void> callback) {
        synchronized (this.waitingAsyncOperationMonitor) {
            final MessageLite msg = (MessageLite)message.getMessage();
            try {
                this.send(message);
                final long result = 5 + msg.getSerializedSize();
                callback.completed(result, null);
            }
            catch (Throwable t) {
                callback.failed(t, null);
            }
        }
    }
    
    @Override
    public long getLastPacketSentTime() {
        return this.lastPacketSentTime;
    }
    
    @Override
    public long getPreviousPacketSentTime() {
        return this.previousPacketSentTime;
    }
    
    @Override
    public void setMaxAllowedPacket(final int maxAllowedPacket) {
        this.maxAllowedPacket = maxAllowedPacket;
    }
}
