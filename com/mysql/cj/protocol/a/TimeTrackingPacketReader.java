
package com.mysql.cj.protocol.a;

import com.mysql.cj.protocol.Message;
import com.mysql.cj.protocol.MessageHeader;
import java.util.Optional;
import java.io.IOException;
import com.mysql.cj.protocol.PacketReceivedTimeHolder;
import com.mysql.cj.protocol.MessageReader;

public class TimeTrackingPacketReader implements MessageReader<NativePacketHeader, NativePacketPayload>, PacketReceivedTimeHolder
{
    private MessageReader<NativePacketHeader, NativePacketPayload> packetReader;
    private long lastPacketReceivedTimeMs;
    
    public TimeTrackingPacketReader(final MessageReader<NativePacketHeader, NativePacketPayload> messageReader) {
        this.lastPacketReceivedTimeMs = 0L;
        this.packetReader = messageReader;
    }
    
    @Override
    public NativePacketHeader readHeader() throws IOException {
        return this.packetReader.readHeader();
    }
    
    @Override
    public NativePacketPayload readMessage(final Optional<NativePacketPayload> reuse, final NativePacketHeader header) throws IOException {
        final NativePacketPayload buf = this.packetReader.readMessage(reuse, header);
        this.lastPacketReceivedTimeMs = System.currentTimeMillis();
        return buf;
    }
    
    @Override
    public long getLastPacketReceivedTime() {
        return this.lastPacketReceivedTimeMs;
    }
    
    @Override
    public byte getMessageSequence() {
        return this.packetReader.getMessageSequence();
    }
    
    @Override
    public void resetMessageSequence() {
        this.packetReader.resetMessageSequence();
    }
    
    @Override
    public MessageReader<NativePacketHeader, NativePacketPayload> undecorateAll() {
        return this.packetReader.undecorateAll();
    }
    
    @Override
    public MessageReader<NativePacketHeader, NativePacketPayload> undecorate() {
        return this.packetReader;
    }
}
