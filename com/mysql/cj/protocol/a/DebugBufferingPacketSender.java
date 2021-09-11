
package com.mysql.cj.protocol.a;

import java.io.IOException;
import com.mysql.cj.util.StringUtils;
import com.mysql.cj.conf.RuntimeProperty;
import java.util.LinkedList;
import com.mysql.cj.protocol.MessageSender;

public class DebugBufferingPacketSender implements MessageSender<NativePacketPayload>
{
    private MessageSender<NativePacketPayload> packetSender;
    private LinkedList<StringBuilder> packetDebugBuffer;
    private RuntimeProperty<Integer> packetDebugBufferSize;
    private int maxPacketDumpLength;
    private static final int DEBUG_MSG_LEN = 64;
    
    public DebugBufferingPacketSender(final MessageSender<NativePacketPayload> packetSender, final LinkedList<StringBuilder> packetDebugBuffer, final RuntimeProperty<Integer> packetDebugBufferSize) {
        this.maxPacketDumpLength = 1024;
        this.packetSender = packetSender;
        this.packetDebugBuffer = packetDebugBuffer;
        this.packetDebugBufferSize = packetDebugBufferSize;
    }
    
    public void setMaxPacketDumpLength(final int maxPacketDumpLength) {
        this.maxPacketDumpLength = maxPacketDumpLength;
    }
    
    private void pushPacketToDebugBuffer(final byte[] packet, final int packetLen) {
        final int bytesToDump = Math.min(this.maxPacketDumpLength, packetLen);
        final String packetPayload = StringUtils.dumpAsHex(packet, bytesToDump);
        final StringBuilder packetDump = new StringBuilder(68 + packetPayload.length());
        packetDump.append("Client ");
        packetDump.append(packet.toString());
        packetDump.append("--------------------> Server\n");
        packetDump.append("\nPacket payload:\n\n");
        packetDump.append(packetPayload);
        if (packetLen > this.maxPacketDumpLength) {
            packetDump.append("\nNote: Packet of " + packetLen + " bytes truncated to " + this.maxPacketDumpLength + " bytes.\n");
        }
        if (this.packetDebugBuffer.size() + 1 > this.packetDebugBufferSize.getValue()) {
            this.packetDebugBuffer.removeFirst();
        }
        this.packetDebugBuffer.addLast(packetDump);
    }
    
    @Override
    public void send(final byte[] packet, final int packetLen, final byte packetSequence) throws IOException {
        this.pushPacketToDebugBuffer(packet, packetLen);
        this.packetSender.send(packet, packetLen, packetSequence);
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
