
package com.mysql.cj.protocol.a.result;

import com.mysql.cj.protocol.a.NativeConstants;
import com.mysql.cj.protocol.a.NativePacketPayload;
import com.mysql.cj.protocol.ProtocolEntity;

public class OkPacket implements ProtocolEntity
{
    private long updateCount;
    private long updateID;
    private int statusFlags;
    private int warningCount;
    private String info;
    
    public OkPacket() {
        this.updateCount = -1L;
        this.updateID = -1L;
        this.statusFlags = 0;
        this.warningCount = 0;
        this.info = null;
    }
    
    public static OkPacket parse(final NativePacketPayload buf, final String errorMessageEncoding) {
        final OkPacket ok = new OkPacket();
        buf.setPosition(1);
        ok.setUpdateCount(buf.readInteger(NativeConstants.IntegerDataType.INT_LENENC));
        ok.setUpdateID(buf.readInteger(NativeConstants.IntegerDataType.INT_LENENC));
        ok.setStatusFlags((int)buf.readInteger(NativeConstants.IntegerDataType.INT2));
        ok.setWarningCount((int)buf.readInteger(NativeConstants.IntegerDataType.INT2));
        ok.setInfo(buf.readString(NativeConstants.StringSelfDataType.STRING_TERM, errorMessageEncoding));
        return ok;
    }
    
    public long getUpdateCount() {
        return this.updateCount;
    }
    
    public void setUpdateCount(final long updateCount) {
        this.updateCount = updateCount;
    }
    
    public long getUpdateID() {
        return this.updateID;
    }
    
    public void setUpdateID(final long updateID) {
        this.updateID = updateID;
    }
    
    public String getInfo() {
        return this.info;
    }
    
    public void setInfo(final String info) {
        this.info = info;
    }
    
    public int getStatusFlags() {
        return this.statusFlags;
    }
    
    public void setStatusFlags(final int statusFlags) {
        this.statusFlags = statusFlags;
    }
    
    public int getWarningCount() {
        return this.warningCount;
    }
    
    public void setWarningCount(final int warningCount) {
        this.warningCount = warningCount;
    }
}
