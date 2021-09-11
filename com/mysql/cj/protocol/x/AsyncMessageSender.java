
package com.mysql.cj.protocol.x;

import com.mysql.cj.protocol.Message;
import com.google.protobuf.MessageLite;
import java.io.IOException;
import com.google.protobuf.CodedOutputStream;
import java.nio.ByteOrder;
import java.nio.ByteBuffer;
import com.mysql.cj.exceptions.CJPacketTooBigException;
import com.mysql.cj.Messages;
import java.util.concurrent.ExecutionException;
import com.mysql.cj.exceptions.CJCommunicationsException;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CompletableFuture;
import java.nio.channels.AsynchronousSocketChannel;
import com.mysql.cj.protocol.SerializingBufferWriter;
import com.mysql.cj.protocol.MessageSender;

public class AsyncMessageSender implements MessageSender<XMessage>
{
    private static final int HEADER_LEN = 5;
    private int maxAllowedPacket;
    private SerializingBufferWriter bufferWriter;
    
    public AsyncMessageSender(final AsynchronousSocketChannel channel) {
        this.maxAllowedPacket = -1;
        this.bufferWriter = new SerializingBufferWriter(channel);
    }
    
    @Override
    public void send(final XMessage message) {
        final CompletableFuture<Void> f = new CompletableFuture<Void>();
        this.send(message, (CompletionHandler<Long, Void>)new ErrorToFutureCompletionHandler<Long>(f, () -> f.complete(null)));
        try {
            f.get();
        }
        catch (ExecutionException ex) {
            throw new CJCommunicationsException("Failed to write message", ex.getCause());
        }
        catch (InterruptedException ex2) {
            throw new CJCommunicationsException("Failed to write message", ex2);
        }
    }
    
    @Override
    public void send(final XMessage message, final CompletionHandler<Long, Void> callback) {
        final MessageLite msg = (MessageLite)message.getMessage();
        final int type = MessageConstants.getTypeForMessageClass(msg.getClass());
        final int size = msg.getSerializedSize();
        final int payloadSize = size + 1;
        if (this.maxAllowedPacket > 0 && payloadSize > this.maxAllowedPacket) {
            throw new CJPacketTooBigException(Messages.getString("PacketTooBigException.1", new Object[] { size, this.maxAllowedPacket }));
        }
        final ByteBuffer messageBuf = ByteBuffer.allocate(5 + size).order(ByteOrder.LITTLE_ENDIAN).putInt(payloadSize);
        messageBuf.put((byte)type);
        try {
            msg.writeTo(CodedOutputStream.newInstance(messageBuf.array(), 5, size));
            messageBuf.position(messageBuf.limit());
        }
        catch (IOException ex) {
            throw new CJCommunicationsException("Unable to write message", ex);
        }
        messageBuf.flip();
        this.bufferWriter.queueBuffer(messageBuf, callback);
    }
    
    @Override
    public void setMaxAllowedPacket(final int maxAllowedPacket) {
        this.maxAllowedPacket = maxAllowedPacket;
    }
    
    public void setChannel(final AsynchronousSocketChannel channel) {
        this.bufferWriter.setChannel(channel);
    }
}
