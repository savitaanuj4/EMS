
package com.mysql.cj.protocol.a;

import java.nio.ByteBuffer;
import com.mysql.cj.protocol.MessageHeader;

public class NativePacketHeader implements MessageHeader
{
    protected ByteBuffer packetHeaderBuf;
    
    public NativePacketHeader() {
        this.packetHeaderBuf = ByteBuffer.allocate(4);
    }
    
    public NativePacketHeader(final byte[] buf) {
        this.packetHeaderBuf = ByteBuffer.wrap(buf);
    }
    
    @Override
    public ByteBuffer getBuffer() {
        return this.packetHeaderBuf;
    }
    
    @Override
    public int getMessageSize() {
        return (this.packetHeaderBuf.array()[0] & 0xFF) + ((this.packetHeaderBuf.array()[1] & 0xFF) << 8) + ((this.packetHeaderBuf.array()[2] & 0xFF) << 16);
    }
    
    @Override
    public byte getMessageSequence() {
        return this.packetHeaderBuf.array()[3];
    }
}
