
package com.mysql.cj.protocol.x;

import java.nio.ByteOrder;
import java.nio.ByteBuffer;
import com.mysql.cj.protocol.MessageHeader;

public class XMessageHeader implements MessageHeader
{
    private ByteBuffer headerBuf;
    private int messageType;
    private int messageSize;
    
    public XMessageHeader() {
        this.messageType = -1;
        this.messageSize = -1;
        this.headerBuf = ByteBuffer.allocate(5).order(ByteOrder.LITTLE_ENDIAN);
    }
    
    public XMessageHeader(final byte[] buf) {
        this.messageType = -1;
        this.messageSize = -1;
        this.headerBuf = ByteBuffer.wrap(buf).order(ByteOrder.LITTLE_ENDIAN);
    }
    
    private void parseBuffer() {
        if (this.messageSize == -1) {
            this.headerBuf.position(0);
            this.messageSize = this.headerBuf.getInt() - 1;
            this.messageType = this.headerBuf.get();
        }
    }
    
    @Override
    public ByteBuffer getBuffer() {
        return this.headerBuf;
    }
    
    @Override
    public int getMessageSize() {
        this.parseBuffer();
        return this.messageSize;
    }
    
    @Override
    public byte getMessageSequence() {
        return 0;
    }
    
    public int getMessageType() {
        this.parseBuffer();
        return this.messageType;
    }
}
