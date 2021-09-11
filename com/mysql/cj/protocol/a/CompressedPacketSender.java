
package com.mysql.cj.protocol.a;

import java.io.IOException;
import java.util.zip.Deflater;
import java.io.BufferedOutputStream;
import com.mysql.cj.protocol.MessageSender;

public class CompressedPacketSender implements MessageSender<NativePacketPayload>
{
    private BufferedOutputStream outputStream;
    private Deflater deflater;
    private byte[] compressedPacket;
    private byte compressedSequenceId;
    private int compressedPayloadLen;
    public static final int COMP_HEADER_LENGTH = 7;
    public static final int MIN_COMPRESS_LEN = 50;
    
    public CompressedPacketSender(final BufferedOutputStream outputStream) {
        this.deflater = new Deflater();
        this.compressedSequenceId = 0;
        this.compressedPayloadLen = 0;
        this.outputStream = outputStream;
    }
    
    public void stop() {
        this.deflater.end();
        this.deflater = null;
    }
    
    private void resetPacket() {
        this.compressedPayloadLen = 0;
        this.deflater.reset();
    }
    
    private void addUncompressedHeader(final byte packetSequence, final int uncompressedPacketLen) {
        final byte[] uncompressedHeader = new byte[4];
        NativeUtils.encodeMysqlThreeByteInteger(uncompressedPacketLen, uncompressedHeader, 0);
        uncompressedHeader[3] = packetSequence;
        this.deflater.setInput(uncompressedHeader);
        this.compressedPayloadLen += this.deflater.deflate(this.compressedPacket, this.compressedPayloadLen, this.compressedPacket.length - this.compressedPayloadLen);
    }
    
    private void addPayload(final byte[] payload, final int payloadOffset, final int payloadLen) {
        this.deflater.setInput(payload, payloadOffset, payloadLen);
        this.compressedPayloadLen += this.deflater.deflate(this.compressedPacket, this.compressedPayloadLen, this.compressedPacket.length - this.compressedPayloadLen);
    }
    
    private void completeCompression() {
        this.deflater.finish();
        this.compressedPayloadLen += this.deflater.deflate(this.compressedPacket, this.compressedPayloadLen, this.compressedPacket.length - this.compressedPayloadLen);
    }
    
    private void writeCompressedHeader(final int compLen, final byte seq, final int uncompLen) throws IOException {
        this.outputStream.write(NativeUtils.encodeMysqlThreeByteInteger(compLen));
        this.outputStream.write(seq);
        this.outputStream.write(NativeUtils.encodeMysqlThreeByteInteger(uncompLen));
    }
    
    private void writeUncompressedHeader(final int packetLen, final byte packetSequence) throws IOException {
        this.outputStream.write(NativeUtils.encodeMysqlThreeByteInteger(packetLen));
        this.outputStream.write(packetSequence);
    }
    
    private void sendCompressedPacket(final int uncompressedPayloadLen) throws IOException {
        final int compressedPayloadLen = this.compressedPayloadLen;
        final byte compressedSequenceId = this.compressedSequenceId;
        this.compressedSequenceId = (byte)(compressedSequenceId + 1);
        this.writeCompressedHeader(compressedPayloadLen, compressedSequenceId, uncompressedPayloadLen);
        this.outputStream.write(this.compressedPacket, 0, this.compressedPayloadLen);
    }
    
    @Override
    public void send(final byte[] packet, final int packetLen, byte packetSequence) throws IOException {
        this.compressedSequenceId = packetSequence;
        if (packetLen < 50) {
            this.writeCompressedHeader(packetLen + 4, this.compressedSequenceId, 0);
            this.writeUncompressedHeader(packetLen, packetSequence);
            this.outputStream.write(packet, 0, packetLen);
            this.outputStream.flush();
            return;
        }
        if (packetLen + 4 > 16777215) {
            this.compressedPacket = new byte[16777215];
        }
        else {
            this.compressedPacket = new byte[4 + packetLen];
        }
        final PacketSplitter packetSplitter = new PacketSplitter(packetLen);
        int unsentPayloadLen = 0;
        int unsentOffset = 0;
        while (true) {
            this.compressedPayloadLen = 0;
            if (!packetSplitter.nextPacket()) {
                break;
            }
            if (unsentPayloadLen > 0) {
                this.addPayload(packet, unsentOffset, unsentPayloadLen);
            }
            final int remaining = 16777215 - unsentPayloadLen;
            final int len = Math.min(remaining, 4 + packetSplitter.getPacketLen());
            final int lenNoHdr = len - 4;
            this.addUncompressedHeader(packetSequence, packetSplitter.getPacketLen());
            this.addPayload(packet, packetSplitter.getOffset(), lenNoHdr);
            this.completeCompression();
            if (this.compressedPayloadLen >= len) {
                final int compLen = unsentPayloadLen + len;
                final byte compressedSequenceId = this.compressedSequenceId;
                this.compressedSequenceId = (byte)(compressedSequenceId + 1);
                this.writeCompressedHeader(compLen, compressedSequenceId, 0);
                this.outputStream.write(packet, unsentOffset, unsentPayloadLen);
                this.writeUncompressedHeader(lenNoHdr, packetSequence);
                this.outputStream.write(packet, packetSplitter.getOffset(), lenNoHdr);
            }
            else {
                this.sendCompressedPacket(len + unsentPayloadLen);
            }
            ++packetSequence;
            unsentPayloadLen = packetSplitter.getPacketLen() - lenNoHdr;
            unsentOffset = packetSplitter.getOffset() + lenNoHdr;
            this.resetPacket();
        }
        if (unsentPayloadLen > 0) {
            this.addPayload(packet, unsentOffset, unsentPayloadLen);
            this.completeCompression();
            if (this.compressedPayloadLen >= unsentPayloadLen) {
                this.writeCompressedHeader(unsentPayloadLen, this.compressedSequenceId, 0);
                this.outputStream.write(packet, unsentOffset, unsentPayloadLen);
            }
            else {
                this.sendCompressedPacket(unsentPayloadLen);
            }
            this.resetPacket();
        }
        this.outputStream.flush();
        this.compressedPacket = null;
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
