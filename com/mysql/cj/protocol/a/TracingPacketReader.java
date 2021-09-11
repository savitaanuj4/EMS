
package com.mysql.cj.protocol.a;

import com.mysql.cj.protocol.Message;
import com.mysql.cj.protocol.MessageHeader;
import java.util.Optional;
import java.io.IOException;
import com.mysql.cj.util.StringUtils;
import com.mysql.cj.Messages;
import com.mysql.cj.log.Log;
import com.mysql.cj.protocol.MessageReader;

public class TracingPacketReader implements MessageReader<NativePacketHeader, NativePacketPayload>
{
    private static final int MAX_PACKET_DUMP_LENGTH = 1024;
    private MessageReader<NativePacketHeader, NativePacketPayload> packetReader;
    private Log log;
    
    public TracingPacketReader(final MessageReader<NativePacketHeader, NativePacketPayload> packetReader, final Log log) {
        this.packetReader = packetReader;
        this.log = log;
    }
    
    @Override
    public NativePacketHeader readHeader() throws IOException {
        final NativePacketHeader hdr = this.packetReader.readHeader();
        final StringBuilder traceMessageBuf = new StringBuilder();
        traceMessageBuf.append(Messages.getString("PacketReader.3"));
        traceMessageBuf.append(hdr.getMessageSize());
        traceMessageBuf.append(Messages.getString("PacketReader.4"));
        traceMessageBuf.append(StringUtils.dumpAsHex(hdr.getBuffer().array(), 4));
        this.log.logTrace(traceMessageBuf.toString());
        return hdr;
    }
    
    @Override
    public NativePacketPayload readMessage(final Optional<NativePacketPayload> reuse, final NativePacketHeader header) throws IOException {
        final int packetLength = header.getMessageSize();
        final NativePacketPayload buf = this.packetReader.readMessage(reuse, header);
        final StringBuilder traceMessageBuf = new StringBuilder();
        traceMessageBuf.append(Messages.getString(reuse.isPresent() ? "PacketReader.5" : "PacketReader.6"));
        traceMessageBuf.append(StringUtils.dumpAsHex(buf.getByteBuffer(), (packetLength < 1024) ? packetLength : 1024));
        if (packetLength > 1024) {
            traceMessageBuf.append(Messages.getString("PacketReader.7"));
            traceMessageBuf.append(1024);
            traceMessageBuf.append(Messages.getString("PacketReader.8"));
        }
        this.log.logTrace(traceMessageBuf.toString());
        return buf;
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
