
package com.mysql.cj.log;

import com.mysql.cj.util.StringUtils;
import java.util.Date;

public class ProfilerEventImpl implements ProfilerEvent
{
    private byte eventType;
    protected long connectionId;
    protected int statementId;
    protected int resultSetId;
    protected long eventCreationTime;
    protected long eventDuration;
    protected String durationUnits;
    protected int hostNameIndex;
    protected String hostName;
    protected int catalogIndex;
    protected String catalog;
    protected int eventCreationPointIndex;
    protected String eventCreationPointDesc;
    protected String message;
    
    public ProfilerEventImpl(final byte eventType, final String hostName, final String catalog, final long connectionId, final int statementId, final int resultSetId, final long eventCreationTime, final long eventDuration, final String durationUnits, final String eventCreationPointDesc, final String eventCreationPoint, final String message) {
        this.setEventType(eventType);
        this.connectionId = connectionId;
        this.statementId = statementId;
        this.resultSetId = resultSetId;
        this.eventCreationTime = eventCreationTime;
        this.eventDuration = eventDuration;
        this.durationUnits = durationUnits;
        this.eventCreationPointDesc = eventCreationPointDesc;
        this.message = message;
    }
    
    @Override
    public String getEventCreationPointAsString() {
        return this.eventCreationPointDesc;
    }
    
    @Override
    public String toString() {
        final StringBuilder buf = new StringBuilder();
        switch (this.getEventType()) {
            case 4: {
                buf.append("EXECUTE");
                break;
            }
            case 5: {
                buf.append("FETCH");
                break;
            }
            case 1: {
                buf.append("CONSTRUCT");
                break;
            }
            case 2: {
                buf.append("PREPARE");
                break;
            }
            case 3: {
                buf.append("QUERY");
                break;
            }
            case 0: {
                buf.append("WARN");
                break;
            }
            case 6: {
                buf.append("SLOW QUERY");
                break;
            }
            default: {
                buf.append("UNKNOWN");
                break;
            }
        }
        buf.append(" created: ");
        buf.append(new Date(this.eventCreationTime));
        buf.append(" duration: ");
        buf.append(this.eventDuration);
        buf.append(" connection: ");
        buf.append(this.connectionId);
        buf.append(" statement: ");
        buf.append(this.statementId);
        buf.append(" resultset: ");
        buf.append(this.resultSetId);
        if (this.message != null) {
            buf.append(" message: ");
            buf.append(this.message);
        }
        if (this.eventCreationPointDesc != null) {
            buf.append("\n\nEvent Created at:\n");
            buf.append(this.eventCreationPointDesc);
        }
        return buf.toString();
    }
    
    public static ProfilerEvent unpack(final byte[] buf) {
        int pos = 0;
        final byte eventType = buf[pos++];
        final long connectionId = readInt(buf, pos);
        pos += 8;
        final int statementId = readInt(buf, pos);
        pos += 4;
        final int resultSetId = readInt(buf, pos);
        pos += 4;
        final long eventCreationTime = readLong(buf, pos);
        pos += 8;
        final long eventDuration = readLong(buf, pos);
        pos += 4;
        final byte[] eventDurationUnits = readBytes(buf, pos);
        pos += 4;
        if (eventDurationUnits != null) {
            pos += eventDurationUnits.length;
        }
        readInt(buf, pos);
        pos += 4;
        final byte[] eventCreationAsBytes = readBytes(buf, pos);
        pos += 4;
        if (eventCreationAsBytes != null) {
            pos += eventCreationAsBytes.length;
        }
        final byte[] message = readBytes(buf, pos);
        pos += 4;
        if (message != null) {
            pos += message.length;
        }
        return new ProfilerEventImpl(eventType, "", "", connectionId, statementId, resultSetId, eventCreationTime, eventDuration, StringUtils.toString(eventDurationUnits, "ISO8859_1"), StringUtils.toString(eventCreationAsBytes, "ISO8859_1"), null, StringUtils.toString(message, "ISO8859_1"));
    }
    
    @Override
    public byte[] pack() {
        int len = 29;
        byte[] eventCreationAsBytes = null;
        this.getEventCreationPointAsString();
        if (this.eventCreationPointDesc != null) {
            eventCreationAsBytes = StringUtils.getBytes(this.eventCreationPointDesc, "ISO8859_1");
            len += 4 + eventCreationAsBytes.length;
        }
        else {
            len += 4;
        }
        byte[] messageAsBytes = null;
        if (this.message != null) {
            messageAsBytes = StringUtils.getBytes(this.message, "ISO8859_1");
            len += 4 + messageAsBytes.length;
        }
        else {
            len += 4;
        }
        byte[] durationUnitsAsBytes = null;
        if (this.durationUnits != null) {
            durationUnitsAsBytes = StringUtils.getBytes(this.durationUnits, "ISO8859_1");
            len += 4 + durationUnitsAsBytes.length;
        }
        else {
            len += 4;
            durationUnitsAsBytes = StringUtils.getBytes("", "ISO8859_1");
        }
        final byte[] buf = new byte[len];
        int pos = 0;
        buf[pos++] = this.getEventType();
        pos = writeLong(this.connectionId, buf, pos);
        pos = writeInt(this.statementId, buf, pos);
        pos = writeInt(this.resultSetId, buf, pos);
        pos = writeLong(this.eventCreationTime, buf, pos);
        pos = writeLong(this.eventDuration, buf, pos);
        pos = writeBytes(durationUnitsAsBytes, buf, pos);
        pos = writeInt(this.eventCreationPointIndex, buf, pos);
        if (eventCreationAsBytes != null) {
            pos = writeBytes(eventCreationAsBytes, buf, pos);
        }
        else {
            pos = writeInt(0, buf, pos);
        }
        if (messageAsBytes != null) {
            pos = writeBytes(messageAsBytes, buf, pos);
        }
        else {
            pos = writeInt(0, buf, pos);
        }
        return buf;
    }
    
    private static int writeInt(final int i, final byte[] buf, int pos) {
        buf[pos++] = (byte)(i & 0xFF);
        buf[pos++] = (byte)(i >>> 8);
        buf[pos++] = (byte)(i >>> 16);
        buf[pos++] = (byte)(i >>> 24);
        return pos;
    }
    
    private static int writeLong(final long l, final byte[] buf, int pos) {
        buf[pos++] = (byte)(l & 0xFFL);
        buf[pos++] = (byte)(l >>> 8);
        buf[pos++] = (byte)(l >>> 16);
        buf[pos++] = (byte)(l >>> 24);
        buf[pos++] = (byte)(l >>> 32);
        buf[pos++] = (byte)(l >>> 40);
        buf[pos++] = (byte)(l >>> 48);
        buf[pos++] = (byte)(l >>> 56);
        return pos;
    }
    
    private static int writeBytes(final byte[] msg, final byte[] buf, int pos) {
        pos = writeInt(msg.length, buf, pos);
        System.arraycopy(msg, 0, buf, pos, msg.length);
        return pos + msg.length;
    }
    
    private static int readInt(final byte[] buf, int pos) {
        return (buf[pos++] & 0xFF) | (buf[pos++] & 0xFF) << 8 | (buf[pos++] & 0xFF) << 16 | (buf[pos++] & 0xFF) << 24;
    }
    
    private static long readLong(final byte[] buf, int pos) {
        return (long)(buf[pos++] & 0xFF) | (long)(buf[pos++] & 0xFF) << 8 | (long)(buf[pos++] & 0xFF) << 16 | (long)(buf[pos++] & 0xFF) << 24 | (long)(buf[pos++] & 0xFF) << 32 | (long)(buf[pos++] & 0xFF) << 40 | (long)(buf[pos++] & 0xFF) << 48 | (long)(buf[pos++] & 0xFF) << 56;
    }
    
    private static byte[] readBytes(final byte[] buf, int pos) {
        final int length = readInt(buf, pos);
        pos += 4;
        final byte[] msg = new byte[length];
        System.arraycopy(buf, pos, msg, 0, length);
        return msg;
    }
    
    @Override
    public String getCatalog() {
        return this.catalog;
    }
    
    @Override
    public long getConnectionId() {
        return this.connectionId;
    }
    
    @Override
    public long getEventCreationTime() {
        return this.eventCreationTime;
    }
    
    @Override
    public long getEventDuration() {
        return this.eventDuration;
    }
    
    @Override
    public String getDurationUnits() {
        return this.durationUnits;
    }
    
    @Override
    public byte getEventType() {
        return this.eventType;
    }
    
    @Override
    public int getResultSetId() {
        return this.resultSetId;
    }
    
    @Override
    public int getStatementId() {
        return this.statementId;
    }
    
    @Override
    public String getMessage() {
        return this.message;
    }
    
    @Override
    public void setEventType(final byte eventType) {
        this.eventType = eventType;
    }
}
