
package com.mysql.cj.protocol.a;

import java.io.IOException;
import java.io.BufferedOutputStream;
import com.mysql.cj.protocol.MessageSender;

public class SimplePacketSender implements MessageSender<NativePacketPayload>
{
    private BufferedOutputStream outputStream;
    
    public SimplePacketSender(final BufferedOutputStream outputStream) {
        this.outputStream = outputStream;
    }
    
    @Override
    public void send(final byte[] packet, final int packetLen, byte packetSequence) throws IOException {
        final PacketSplitter packetSplitter = new PacketSplitter(packetLen);
        while (packetSplitter.nextPacket()) {
            this.outputStream.write(NativeUtils.encodeMysqlThreeByteInteger(packetSplitter.getPacketLen()));
            final BufferedOutputStream outputStream = this.outputStream;
            final byte b = packetSequence;
            ++packetSequence;
            outputStream.write(b);
            this.outputStream.write(packet, packetSplitter.getOffset(), packetSplitter.getPacketLen());
        }
        this.outputStream.flush();
    }
    
    @Override
    public MessageSender<NativePacketPayload> undecorateAll() {
        return this;
    }
    
    @Override
    public MessageSender<NativePacketPayload> undecorate() {
        return this;
    }
}
