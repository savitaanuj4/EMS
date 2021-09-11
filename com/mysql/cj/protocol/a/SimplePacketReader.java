
package com.mysql.cj.protocol.a;

import com.mysql.cj.protocol.Message;
import com.mysql.cj.protocol.MessageHeader;
import com.mysql.cj.Messages;
import java.util.Optional;
import java.io.IOException;
import com.mysql.cj.exceptions.CJPacketTooBigException;
import com.mysql.cj.conf.RuntimeProperty;
import com.mysql.cj.protocol.SocketConnection;
import com.mysql.cj.protocol.MessageReader;

public class SimplePacketReader implements MessageReader<NativePacketHeader, NativePacketPayload>
{
    protected SocketConnection socketConnection;
    protected RuntimeProperty<Integer> maxAllowedPacket;
    private byte readPacketSequence;
    
    public SimplePacketReader(final SocketConnection socketConnection, final RuntimeProperty<Integer> maxAllowedPacket) {
        this.readPacketSequence = -1;
        this.socketConnection = socketConnection;
        this.maxAllowedPacket = maxAllowedPacket;
    }
    
    @Override
    public NativePacketHeader readHeader() throws IOException {
        final NativePacketHeader hdr = new NativePacketHeader();
        try {
            this.socketConnection.getMysqlInput().readFully(hdr.getBuffer().array(), 0, 4);
            final int packetLength = hdr.getMessageSize();
            if (packetLength > this.maxAllowedPacket.getValue()) {
                throw new CJPacketTooBigException(packetLength, this.maxAllowedPacket.getValue());
            }
        }
        catch (IOException | CJPacketTooBigException ex2) {
            final Exception ex;
            final Exception e = ex;
            try {
                this.socketConnection.forceClose();
            }
            catch (Exception ex3) {}
            throw e;
        }
        this.readPacketSequence = hdr.getMessageSequence();
        return hdr;
    }
    
    @Override
    public NativePacketPayload readMessage(final Optional<NativePacketPayload> reuse, final NativePacketHeader header) throws IOException {
        try {
            final int packetLength = header.getMessageSize();
            NativePacketPayload buf;
            if (reuse.isPresent()) {
                buf = reuse.get();
                buf.setPosition(0);
                if (buf.getByteBuffer().length < packetLength) {
                    buf.setByteBuffer(new byte[packetLength]);
                }
                buf.setPayloadLength(packetLength);
            }
            else {
                buf = new NativePacketPayload(new byte[packetLength]);
            }
            final int numBytesRead = this.socketConnection.getMysqlInput().readFully(buf.getByteBuffer(), 0, packetLength);
            if (numBytesRead != packetLength) {
                throw new IOException(Messages.getString("PacketReader.1", new Object[] { packetLength, numBytesRead }));
            }
            return buf;
        }
        catch (IOException e) {
            try {
                this.socketConnection.forceClose();
            }
            catch (Exception ex) {}
            throw e;
        }
    }
    
    @Override
    public byte getMessageSequence() {
        return this.readPacketSequence;
    }
    
    @Override
    public void resetMessageSequence() {
        this.readPacketSequence = 0;
    }
}
