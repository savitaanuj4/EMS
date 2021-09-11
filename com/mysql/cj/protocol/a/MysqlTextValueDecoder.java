
package com.mysql.cj.protocol.a;

import java.math.BigDecimal;
import java.math.BigInteger;
import com.mysql.cj.util.StringUtils;
import com.mysql.cj.exceptions.DataReadException;
import com.mysql.cj.Messages;
import com.mysql.cj.result.ValueFactory;
import com.mysql.cj.protocol.ValueDecoder;

public class MysqlTextValueDecoder implements ValueDecoder
{
    public static final int DATE_BUF_LEN = 10;
    public static final int TIME_STR_LEN_MIN = 8;
    public static final int TIME_STR_LEN_MAX = 17;
    public static final int TIMESTAMP_NOFRAC_STR_LEN = 19;
    public static final int TIMESTAMP_STR_LEN_MAX = 26;
    public static final int TIMESTAMP_STR_LEN_WITH_NANOS = 29;
    private static final int MAX_SIGNED_LONG_LEN = 20;
    
    @Override
    public <T> T decodeDate(final byte[] bytes, final int offset, final int length, final ValueFactory<T> vf) {
        if (length != 10) {
            throw new DataReadException(Messages.getString("ResultSet.InvalidLengthForType", new Object[] { length, "DATE" }));
        }
        final int year = StringUtils.getInt(bytes, offset, offset + 4);
        final int month = StringUtils.getInt(bytes, offset + 5, offset + 7);
        final int day = StringUtils.getInt(bytes, offset + 8, offset + 10);
        return vf.createFromDate(year, month, day);
    }
    
    @Override
    public <T> T decodeTime(final byte[] bytes, final int offset, final int length, final ValueFactory<T> vf) {
        int pos = 0;
        if (length < 8 || length > 17) {
            throw new DataReadException(Messages.getString("ResultSet.InvalidLengthForType", new Object[] { length, "TIME" }));
        }
        boolean negative = false;
        if (bytes[offset] == 45) {
            ++pos;
            negative = true;
        }
        int segmentLen;
        for (segmentLen = 0; Character.isDigit((char)bytes[offset + pos + segmentLen]); ++segmentLen) {}
        if (segmentLen == 0 || bytes[offset + pos + segmentLen] != 58) {
            throw new DataReadException(Messages.getString("ResultSet.InvalidFormatForType", new Object[] { "TIME", StringUtils.toString(bytes, offset, length) }));
        }
        int hours = StringUtils.getInt(bytes, offset + pos, offset + pos + segmentLen);
        if (negative) {
            hours *= -1;
        }
        for (pos += segmentLen + 1, segmentLen = 0; Character.isDigit((char)bytes[offset + pos + segmentLen]); ++segmentLen) {}
        if (segmentLen != 2 || bytes[offset + pos + segmentLen] != 58) {
            throw new DataReadException(Messages.getString("ResultSet.InvalidFormatForType", new Object[] { "TIME", StringUtils.toString(bytes, offset, length) }));
        }
        final int minutes = StringUtils.getInt(bytes, offset + pos, offset + pos + segmentLen);
        for (pos += segmentLen + 1, segmentLen = 0; offset + pos + segmentLen < offset + length && Character.isDigit((char)bytes[offset + pos + segmentLen]); ++segmentLen) {}
        if (segmentLen != 2) {
            throw new DataReadException(Messages.getString("ResultSet.InvalidFormatForType", new Object[] { StringUtils.toString(bytes, offset, length), "TIME" }));
        }
        final int seconds = StringUtils.getInt(bytes, offset + pos, offset + pos + segmentLen);
        pos += segmentLen;
        int nanos = 0;
        if (length > pos) {
            ++pos;
            for (segmentLen = 0; offset + pos + segmentLen < offset + length && Character.isDigit((char)bytes[offset + pos + segmentLen]); ++segmentLen) {}
            if (segmentLen + pos != length) {
                throw new DataReadException(Messages.getString("ResultSet.InvalidFormatForType", new Object[] { StringUtils.toString(bytes, offset, length), "TIME" }));
            }
            nanos = StringUtils.getInt(bytes, offset + pos, offset + pos + segmentLen);
            nanos *= (int)Math.pow(10.0, 9 - segmentLen);
        }
        return vf.createFromTime(hours, minutes, seconds, nanos);
    }
    
    @Override
    public <T> T decodeTimestamp(final byte[] bytes, final int offset, final int length, final ValueFactory<T> vf) {
        if (length < 19 || (length > 26 && length != 29)) {
            throw new DataReadException(Messages.getString("ResultSet.InvalidLengthForType", new Object[] { length, "TIMESTAMP" }));
        }
        if (length != 19 && (bytes[offset + 19] != 46 || length < 21)) {
            throw new DataReadException(Messages.getString("ResultSet.InvalidFormatForType", new Object[] { StringUtils.toString(bytes, offset, length), "TIMESTAMP" }));
        }
        if (bytes[offset + 4] != 45 || bytes[offset + 7] != 45 || bytes[offset + 10] != 32 || bytes[offset + 13] != 58 || bytes[offset + 16] != 58) {
            throw new DataReadException(Messages.getString("ResultSet.InvalidFormatForType", new Object[] { StringUtils.toString(bytes, offset, length), "TIMESTAMP" }));
        }
        final int year = StringUtils.getInt(bytes, offset, offset + 4);
        final int month = StringUtils.getInt(bytes, offset + 5, offset + 7);
        final int day = StringUtils.getInt(bytes, offset + 8, offset + 10);
        final int hours = StringUtils.getInt(bytes, offset + 11, offset + 13);
        final int minutes = StringUtils.getInt(bytes, offset + 14, offset + 16);
        final int seconds = StringUtils.getInt(bytes, offset + 17, offset + 19);
        int nanos;
        if (length == 29) {
            nanos = StringUtils.getInt(bytes, offset + 20, offset + length);
        }
        else {
            nanos = ((length == 19) ? 0 : StringUtils.getInt(bytes, offset + 20, offset + length));
            nanos *= (int)Math.pow(10.0, 9 - (length - 19 - 1));
        }
        return vf.createFromTimestamp(year, month, day, hours, minutes, seconds, nanos);
    }
    
    @Override
    public <T> T decodeUInt1(final byte[] bytes, final int offset, final int length, final ValueFactory<T> vf) {
        return vf.createFromLong(StringUtils.getInt(bytes, offset, offset + length));
    }
    
    @Override
    public <T> T decodeInt1(final byte[] bytes, final int offset, final int length, final ValueFactory<T> vf) {
        return vf.createFromLong(StringUtils.getInt(bytes, offset, offset + length));
    }
    
    @Override
    public <T> T decodeUInt2(final byte[] bytes, final int offset, final int length, final ValueFactory<T> vf) {
        return vf.createFromLong(StringUtils.getInt(bytes, offset, offset + length));
    }
    
    @Override
    public <T> T decodeInt2(final byte[] bytes, final int offset, final int length, final ValueFactory<T> vf) {
        return vf.createFromLong(StringUtils.getInt(bytes, offset, offset + length));
    }
    
    @Override
    public <T> T decodeUInt4(final byte[] bytes, final int offset, final int length, final ValueFactory<T> vf) {
        return vf.createFromLong(StringUtils.getLong(bytes, offset, offset + length));
    }
    
    @Override
    public <T> T decodeInt4(final byte[] bytes, final int offset, final int length, final ValueFactory<T> vf) {
        return vf.createFromLong(StringUtils.getInt(bytes, offset, offset + length));
    }
    
    @Override
    public <T> T decodeUInt8(final byte[] bytes, final int offset, final int length, final ValueFactory<T> vf) {
        if (length <= 19 && bytes[0] >= 48 && bytes[0] <= 56) {
            return (T)this.decodeInt8(bytes, offset, length, (ValueFactory<Object>)vf);
        }
        final BigInteger i = new BigInteger(StringUtils.toAsciiString(bytes, offset, length));
        return vf.createFromBigInteger(i);
    }
    
    @Override
    public <T> T decodeInt8(final byte[] bytes, final int offset, final int length, final ValueFactory<T> vf) {
        return vf.createFromLong(StringUtils.getLong(bytes, offset, offset + length));
    }
    
    @Override
    public <T> T decodeFloat(final byte[] bytes, final int offset, final int length, final ValueFactory<T> vf) {
        return (T)this.decodeDouble(bytes, offset, length, (ValueFactory<Object>)vf);
    }
    
    @Override
    public <T> T decodeDouble(final byte[] bytes, final int offset, final int length, final ValueFactory<T> vf) {
        final double d = Double.parseDouble(StringUtils.toAsciiString(bytes, offset, length));
        return vf.createFromDouble(d);
    }
    
    @Override
    public <T> T decodeDecimal(final byte[] bytes, final int offset, final int length, final ValueFactory<T> vf) {
        final BigDecimal d = new BigDecimal(StringUtils.toAsciiString(bytes, offset, length));
        return vf.createFromBigDecimal(d);
    }
    
    @Override
    public <T> T decodeByteArray(final byte[] bytes, final int offset, final int length, final ValueFactory<T> vf) {
        return vf.createFromBytes(bytes, offset, length);
    }
    
    @Override
    public <T> T decodeBit(final byte[] bytes, final int offset, final int length, final ValueFactory<T> vf) {
        return vf.createFromBit(bytes, offset, length);
    }
    
    @Override
    public <T> T decodeSet(final byte[] bytes, final int offset, final int length, final ValueFactory<T> vf) {
        return (T)this.decodeByteArray(bytes, offset, length, (ValueFactory<Object>)vf);
    }
}
