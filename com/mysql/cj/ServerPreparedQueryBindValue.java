
package com.mysql.cj;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Locale;
import com.mysql.cj.exceptions.CJException;
import com.mysql.cj.exceptions.ExceptionFactory;
import com.mysql.cj.util.StringUtils;
import com.mysql.cj.protocol.a.NativeConstants;
import com.mysql.cj.exceptions.ExceptionInterceptor;
import com.mysql.cj.protocol.a.NativePacketPayload;
import java.util.TimeZone;
import java.util.Calendar;

public class ServerPreparedQueryBindValue extends ClientPreparedQueryBindValue implements BindValue
{
    public long boundBeforeExecutionNum;
    public int bufferType;
    public Calendar calendar;
    private TimeZone defaultTimeZone;
    
    public ServerPreparedQueryBindValue(final TimeZone defaultTZ) {
        this.boundBeforeExecutionNum = 0L;
        this.defaultTimeZone = defaultTZ;
    }
    
    @Override
    public ServerPreparedQueryBindValue clone() {
        return new ServerPreparedQueryBindValue(this);
    }
    
    private ServerPreparedQueryBindValue(final ServerPreparedQueryBindValue copyMe) {
        super(copyMe);
        this.boundBeforeExecutionNum = 0L;
        this.defaultTimeZone = copyMe.defaultTimeZone;
        this.bufferType = copyMe.bufferType;
        this.calendar = copyMe.calendar;
    }
    
    @Override
    public void reset() {
        super.reset();
        this.calendar = null;
    }
    
    public boolean resetToType(final int bufType, final long numberOfExecutions) {
        boolean sendTypesToServer = false;
        this.reset();
        if (bufType != 6 || this.bufferType == 0) {
            if (this.bufferType != bufType) {
                sendTypesToServer = true;
                this.bufferType = bufType;
            }
        }
        this.isSet = true;
        this.boundBeforeExecutionNum = numberOfExecutions;
        return sendTypesToServer;
    }
    
    @Override
    public String toString() {
        return this.toString(false);
    }
    
    public String toString(final boolean quoteIfNeeded) {
        if (this.isStream) {
            return "' STREAM DATA '";
        }
        if (this.isNull) {
            return "NULL";
        }
        switch (this.bufferType) {
            case 1:
            case 2:
            case 3:
            case 8: {
                return String.valueOf((long)this.value);
            }
            case 4: {
                return String.valueOf((float)this.value);
            }
            case 5: {
                return String.valueOf((double)this.value);
            }
            case 7:
            case 10:
            case 11:
            case 12:
            case 15:
            case 253:
            case 254: {
                if (quoteIfNeeded) {
                    return "'" + String.valueOf(this.value) + "'";
                }
                return String.valueOf(this.value);
            }
            default: {
                if (this.value instanceof byte[]) {
                    return "byte data";
                }
                if (quoteIfNeeded) {
                    return "'" + String.valueOf(this.value) + "'";
                }
                return String.valueOf(this.value);
            }
        }
    }
    
    public long getBoundLength() {
        if (this.isNull) {
            return 0L;
        }
        if (this.isStream) {
            return this.streamLength;
        }
        switch (this.bufferType) {
            case 1: {
                return 1L;
            }
            case 2: {
                return 2L;
            }
            case 3: {
                return 4L;
            }
            case 8: {
                return 8L;
            }
            case 4: {
                return 4L;
            }
            case 5: {
                return 8L;
            }
            case 11: {
                return 9L;
            }
            case 10: {
                return 7L;
            }
            case 7:
            case 12: {
                return 11L;
            }
            case 0:
            case 15:
            case 246:
            case 253:
            case 254: {
                if (this.value instanceof byte[]) {
                    return ((byte[])this.value).length;
                }
                return ((String)this.value).length();
            }
            default: {
                return 0L;
            }
        }
    }
    
    public void storeBinding(final NativePacketPayload intoPacket, final boolean isLoadDataQuery, final String characterEncoding, final ExceptionInterceptor interceptor) {
        synchronized (this) {
            try {
                switch (this.bufferType) {
                    case 1: {
                        intoPacket.writeInteger(NativeConstants.IntegerDataType.INT1, (long)this.value);
                    }
                    case 2: {
                        intoPacket.writeInteger(NativeConstants.IntegerDataType.INT2, (long)this.value);
                    }
                    case 3: {
                        intoPacket.writeInteger(NativeConstants.IntegerDataType.INT4, (long)this.value);
                    }
                    case 8: {
                        intoPacket.writeInteger(NativeConstants.IntegerDataType.INT8, (long)this.value);
                    }
                    case 4: {
                        intoPacket.writeInteger(NativeConstants.IntegerDataType.INT4, Float.floatToIntBits((float)this.value));
                    }
                    case 5: {
                        intoPacket.writeInteger(NativeConstants.IntegerDataType.INT8, Double.doubleToLongBits((double)this.value));
                    }
                    case 11: {
                        this.storeTime(intoPacket);
                    }
                    case 7:
                    case 10:
                    case 12: {
                        this.storeDateTime(intoPacket);
                    }
                    case 0:
                    case 15:
                    case 246:
                    case 253:
                    case 254: {
                        if (this.value instanceof byte[]) {
                            intoPacket.writeBytes(NativeConstants.StringSelfDataType.STRING_LENENC, (byte[])this.value);
                        }
                        else if (!isLoadDataQuery) {
                            intoPacket.writeBytes(NativeConstants.StringSelfDataType.STRING_LENENC, StringUtils.getBytes((String)this.value, characterEncoding));
                        }
                        else {
                            intoPacket.writeBytes(NativeConstants.StringSelfDataType.STRING_LENENC, StringUtils.getBytes((String)this.value));
                        }
                    }
                }
            }
            catch (CJException uEE) {
                throw ExceptionFactory.createException(Messages.getString("ServerPreparedStatement.22") + characterEncoding + "'", uEE, interceptor);
            }
        }
    }
    
    private void storeTime(final NativePacketPayload intoPacket) {
        intoPacket.ensureCapacity(9);
        intoPacket.writeInteger(NativeConstants.IntegerDataType.INT1, 8L);
        intoPacket.writeInteger(NativeConstants.IntegerDataType.INT1, 0L);
        intoPacket.writeInteger(NativeConstants.IntegerDataType.INT4, 0L);
        if (this.calendar == null) {
            this.calendar = Calendar.getInstance(this.defaultTimeZone, Locale.US);
        }
        this.calendar.setTime((Date)this.value);
        intoPacket.writeInteger(NativeConstants.IntegerDataType.INT1, this.calendar.get(11));
        intoPacket.writeInteger(NativeConstants.IntegerDataType.INT1, this.calendar.get(12));
        intoPacket.writeInteger(NativeConstants.IntegerDataType.INT1, this.calendar.get(13));
    }
    
    private void storeDateTime(final NativePacketPayload intoPacket) {
        synchronized (this) {
            if (this.calendar == null) {
                this.calendar = Calendar.getInstance(this.defaultTimeZone, Locale.US);
            }
            this.calendar.setTime((Date)this.value);
            if (this.value instanceof java.sql.Date) {
                this.calendar.set(11, 0);
                this.calendar.set(12, 0);
                this.calendar.set(13, 0);
            }
            byte length = 7;
            if (this.value instanceof Timestamp) {
                length = 11;
            }
            intoPacket.ensureCapacity(length);
            intoPacket.writeInteger(NativeConstants.IntegerDataType.INT1, length);
            final int year = this.calendar.get(1);
            final int month = this.calendar.get(2) + 1;
            final int date = this.calendar.get(5);
            intoPacket.writeInteger(NativeConstants.IntegerDataType.INT2, year);
            intoPacket.writeInteger(NativeConstants.IntegerDataType.INT1, month);
            intoPacket.writeInteger(NativeConstants.IntegerDataType.INT1, date);
            if (this.value instanceof java.sql.Date) {
                intoPacket.writeInteger(NativeConstants.IntegerDataType.INT1, 0L);
                intoPacket.writeInteger(NativeConstants.IntegerDataType.INT1, 0L);
                intoPacket.writeInteger(NativeConstants.IntegerDataType.INT1, 0L);
            }
            else {
                intoPacket.writeInteger(NativeConstants.IntegerDataType.INT1, this.calendar.get(11));
                intoPacket.writeInteger(NativeConstants.IntegerDataType.INT1, this.calendar.get(12));
                intoPacket.writeInteger(NativeConstants.IntegerDataType.INT1, this.calendar.get(13));
            }
            if (length == 11) {
                intoPacket.writeInteger(NativeConstants.IntegerDataType.INT4, ((Timestamp)this.value).getNanos() / 1000);
            }
        }
    }
}
