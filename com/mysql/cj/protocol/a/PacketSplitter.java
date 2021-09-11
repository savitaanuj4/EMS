
package com.mysql.cj.protocol.a;

public class PacketSplitter
{
    private int totalSize;
    private int currentPacketLen;
    private int offset;
    
    public PacketSplitter(final int totalSize) {
        this.currentPacketLen = 0;
        this.offset = 0;
        this.totalSize = totalSize;
    }
    
    public int getPacketLen() {
        return this.currentPacketLen;
    }
    
    public int getOffset() {
        return this.offset;
    }
    
    public boolean nextPacket() {
        this.offset += this.currentPacketLen;
        if (this.currentPacketLen == 16777215 && this.offset == this.totalSize) {
            this.currentPacketLen = 0;
            return true;
        }
        if (this.totalSize == 0) {
            this.totalSize = -1;
            return true;
        }
        this.currentPacketLen = this.totalSize - this.offset;
        if (this.currentPacketLen > 16777215) {
            this.currentPacketLen = 16777215;
        }
        return this.offset < this.totalSize;
    }
}
