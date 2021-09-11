
package com.mysql.cj.protocol.a;

import java.io.IOException;
import com.mysql.cj.util.StringUtils;
import com.mysql.cj.log.Log;
import com.mysql.cj.protocol.MessageSender;

public class TracingPacketSender implements MessageSender<NativePacketPayload>
{
    private MessageSender<NativePacketPayload> packetSender;
    private String host;
    private long serverThreadId;
    private Log log;
    
    public TracingPacketSender(final MessageSender<NativePacketPayload> packetSender, final Log log, final String host, final long serverThreadId) {
        this.packetSender = packetSender;
        this.host = host;
        this.serverThreadId = serverThreadId;
        this.log = log;
    }
    
    public void setServerThreadId(final long serverThreadId) {
        this.serverThreadId = serverThreadId;
    }
    
    private void logPacket(final byte[] packet, final int packetLen, final byte packetSequence) {
        final StringBuilder traceMessageBuf = new StringBuilder();
        traceMessageBuf.append("send packet payload:\n");
        traceMessageBuf.append("host: '");
        traceMessageBuf.append(this.host);
        traceMessageBuf.append("' serverThreadId: '");
        traceMessageBuf.append(this.serverThreadId);
        traceMessageBuf.append("' packetLen: '");
        traceMessageBuf.append(packetLen);
        traceMessageBuf.append("' packetSequence: '");
        traceMessageBuf.append(packetSequence);
        traceMessageBuf.append("'\n");
        traceMessageBuf.append(StringUtils.dumpAsHex(packet, packetLen));
        this.log.logTrace(traceMessageBuf.toString());
    }
    
    @Override
    public void send(final byte[] packet, final int packetLen, final byte packetSequence) throws IOException {
        this.logPacket(packet, packetLen, packetSequence);
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
