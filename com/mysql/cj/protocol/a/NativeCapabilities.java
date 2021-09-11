
package com.mysql.cj.protocol.a;

import com.mysql.cj.ServerVersion;
import com.mysql.cj.protocol.ServerCapabilities;

public class NativeCapabilities implements ServerCapabilities
{
    private NativePacketPayload initialHandshakePacket;
    private byte protocolVersion;
    private ServerVersion serverVersion;
    private long threadId;
    private String seed;
    private int capabilityFlags;
    private int serverDefaultCollationIndex;
    private int statusFlags;
    private int authPluginDataLength;
    private boolean serverHasFracSecsSupport;
    
    public NativeCapabilities() {
        this.protocolVersion = 0;
        this.threadId = -1L;
        this.statusFlags = 0;
        this.authPluginDataLength = 0;
        this.serverHasFracSecsSupport = true;
    }
    
    public NativePacketPayload getInitialHandshakePacket() {
        return this.initialHandshakePacket;
    }
    
    public void setInitialHandshakePacket(final NativePacketPayload initialHandshakePacket) {
        this.initialHandshakePacket = initialHandshakePacket;
        this.setProtocolVersion((byte)initialHandshakePacket.readInteger(NativeConstants.IntegerDataType.INT1));
        this.setServerVersion(ServerVersion.parseVersion(initialHandshakePacket.readString(NativeConstants.StringSelfDataType.STRING_TERM, "ASCII")));
        this.setThreadId(initialHandshakePacket.readInteger(NativeConstants.IntegerDataType.INT4));
        this.setSeed(initialHandshakePacket.readString(NativeConstants.StringLengthDataType.STRING_FIXED, "ASCII", 8));
        initialHandshakePacket.readInteger(NativeConstants.IntegerDataType.INT1);
        int flags = 0;
        if (initialHandshakePacket.getPosition() < initialHandshakePacket.getPayloadLength()) {
            flags = (int)initialHandshakePacket.readInteger(NativeConstants.IntegerDataType.INT2);
        }
        this.setServerDefaultCollationIndex((int)initialHandshakePacket.readInteger(NativeConstants.IntegerDataType.INT1));
        this.setStatusFlags((int)initialHandshakePacket.readInteger(NativeConstants.IntegerDataType.INT2));
        flags |= (int)initialHandshakePacket.readInteger(NativeConstants.IntegerDataType.INT2) << 16;
        this.setCapabilityFlags(flags);
        if ((flags & 0x80000) != 0x0) {
            this.authPluginDataLength = (int)initialHandshakePacket.readInteger(NativeConstants.IntegerDataType.INT1);
        }
        else {
            initialHandshakePacket.readInteger(NativeConstants.IntegerDataType.INT1);
        }
        initialHandshakePacket.setPosition(initialHandshakePacket.getPosition() + 10);
        this.serverHasFracSecsSupport = this.serverVersion.meetsMinimum(new ServerVersion(5, 6, 4));
    }
    
    @Override
    public int getCapabilityFlags() {
        return this.capabilityFlags;
    }
    
    @Override
    public void setCapabilityFlags(final int capabilityFlags) {
        this.capabilityFlags = capabilityFlags;
    }
    
    public byte getProtocolVersion() {
        return this.protocolVersion;
    }
    
    public void setProtocolVersion(final byte protocolVersion) {
        this.protocolVersion = protocolVersion;
    }
    
    @Override
    public ServerVersion getServerVersion() {
        return this.serverVersion;
    }
    
    @Override
    public void setServerVersion(final ServerVersion serverVersion) {
        this.serverVersion = serverVersion;
    }
    
    public long getThreadId() {
        return this.threadId;
    }
    
    public void setThreadId(final long threadId) {
        this.threadId = threadId;
    }
    
    public String getSeed() {
        return this.seed;
    }
    
    public void setSeed(final String seed) {
        this.seed = seed;
    }
    
    public int getServerDefaultCollationIndex() {
        return this.serverDefaultCollationIndex;
    }
    
    public void setServerDefaultCollationIndex(final int serverDefaultCollationIndex) {
        this.serverDefaultCollationIndex = serverDefaultCollationIndex;
    }
    
    public int getStatusFlags() {
        return this.statusFlags;
    }
    
    public void setStatusFlags(final int statusFlags) {
        this.statusFlags = statusFlags;
    }
    
    public int getAuthPluginDataLength() {
        return this.authPluginDataLength;
    }
    
    public void setAuthPluginDataLength(final int authPluginDataLength) {
        this.authPluginDataLength = authPluginDataLength;
    }
    
    @Override
    public boolean serverSupportsFracSecs() {
        return this.serverHasFracSecsSupport;
    }
}
