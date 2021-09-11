
package com.mysql.cj.protocol.a;

import java.io.IOException;
import com.mysql.cj.protocol.PacketSentTimeHolder;
import com.mysql.cj.protocol.MessageSender;

public class TimeTrackingPacketSender implements MessageSender<NativePacketPayload>, PacketSentTimeHolder
{
    private MessageSender<NativePacketPayload> packetSender;
    private long lastPacketSentTime;
    private long previousPacketSentTime;
    
    public TimeTrackingPacketSender(final MessageSender<NativePacketPayload> packetSender) {
        this.lastPacketSentTime = 0L;
        this.previousPacketSentTime = 0L;
        this.packetSender = packetSender;
    }
    
    @Override
    public void send(final byte[] packet, final int packetLen, final byte packetSequence) throws IOException {
        this.packetSender.send(packet, packetLen, packetSequence);
        this.previousPacketSentTime = this.lastPacketSentTime;
        this.lastPacketSentTime = System.currentTimeMillis();
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
    public MessageSender<NativePacketPayload> undecorateAll() {
        return this.packetSender.undecorateAll();
    }
    
    @Override
    public MessageSender<NativePacketPayload> undecorate() {
        return this.packetSender;
    }
}
