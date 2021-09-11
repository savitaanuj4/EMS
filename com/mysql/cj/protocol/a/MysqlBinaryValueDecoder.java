
package com.mysql.cj.protocol.a;

import java.math.BigDecimal;
import com.mysql.cj.util.StringUtils;
import java.math.BigInteger;
import com.mysql.cj.exceptions.DataReadException;
import com.mysql.cj.Messages;
import com.mysql.cj.result.ValueFactory;
import com.mysql.cj.protocol.ValueDecoder;

public class MysqlBinaryValueDecoder implements ValueDecoder
{
    @Override
    public <T> T decodeTimestamp(final byte[] bytes, final int offset, final int length, final ValueFactory<T> vf) {
        if (length == 0) {
            return vf.createFromTimestamp(0, 0, 0, 0, 0, 0, 0);
        }
        if (length != 4 && length != 11 && length != 7) {
            throw new DataReadException(Messages.getString("ResultSet.InvalidLengthForType", new Object[] { length, "TIMESTAMP" }));
        }
        int year = 0;
        int month = 0;
        int day = 0;
        int hours = 0;
        int minutes = 0;
        int seconds = 0;
        int nanos = 0;
        year = ((bytes[offset + 0] & 0xFF) | (bytes[offset + 1] & 0xFF) << 8);
        month = bytes[offset + 2];
        day = bytes[offset + 3];
        if (length > 4) {
            hours = bytes[offset + 4];
            minutes = bytes[offset + 5];
            seconds = bytes[offset + 6];
        }
        if (length > 7) {
            nanos = 1000 * ((bytes[offset + 7] & 0xFF) | (bytes[offset + 8] & 0xFF) << 8 | (bytes[offset + 9] & 0xFF) << 16 | (bytes[offset + 10] & 0xFF) << 24);
        }
        return vf.createFromTimestamp(year, month, day, hours, minutes, seconds, nanos);
    }
    
    @Override
    public <T> T decodeTime(final byte[] bytes, final int offset, final int length, final ValueFactory<T> vf) {
        if (length == 0) {
            return vf.createFromTime(0, 0, 0, 0);
        }
        if (length != 8 && length != 12) {
            throw new DataReadException(Messages.getString("ResultSet.InvalidLengthForType", new Object[] { length, "TIME" }));
        }
        int days = 0;
        int hours = 0;
        int minutes = 0;
        int seconds = 0;
        int nanos = 0;
        final boolean negative = bytes[offset] == 1;
        days = ((bytes[offset + 1] & 0xFF) | (bytes[offset + 2] & 0xFF) << 8 | (bytes[offset + 3] & 0xFF) << 16 | (bytes[offset + 4] & 0xFF) << 24);
        hours = bytes[offset + 5];
        minutes = bytes[offset + 6];
        seconds = bytes[offset + 7];
        if (negative) {
            days *= -1;
        }
        if (length > 7) {
            nanos = (1000 * (bytes[offset + 1] & 0xFF) | (bytes[offset + 2] & 0xFF) << 8 | (bytes[offset + 3] & 0xFF) << 16 | (bytes[offset + 4] & 0xFF) << 24);
        }
        return vf.createFromTime(days * 24 + hours, minutes, seconds, nanos);
    }
    
    @Override
    public <T> T decodeDate(final byte[] bytes, final int offset, final int length, final ValueFactory<T> vf) {
        if (length == 0) {
            return vf.createFromDate(0, 0, 0);
        }
        if (length != 4) {
            throw new DataReadException(Messages.getString("ResultSet.InvalidLengthForType", new Object[] { length, "DATE" }));
        }
        final int year = (bytes[offset] & 0xFF) | (bytes[offset + 1] & 0xFF) << 8;
        final int month = bytes[offset + 2];
        final int day = bytes[offset + 3];
        return vf.createFromDate(year, month, day);
    }
    
    @Override
    public <T> T decodeUInt1(final byte[] bytes, final int offset, final int length, final ValueFactory<T> vf) {
        if (length != 1) {
            throw new DataReadException(Messages.getString("ResultSet.InvalidLengthForType", new Object[] { length, "BYTE" }));
        }
        return vf.createFromLong(bytes[offset] & 0xFF);
    }
    
    @Override
    public <T> T decodeInt1(final byte[] bytes, final int offset, final int length, final ValueFactory<T> vf) {
        if (length != 1) {
            throw new DataReadException(Messages.getString("ResultSet.InvalidLengthForType", new Object[] { length, "BYTE" }));
        }
        return vf.createFromLong(bytes[offset]);
    }
    
    @Override
    public <T> T decodeUInt2(final byte[] bytes, final int offset, final int length, final ValueFactory<T> vf) {
        if (length != 2) {
            throw new DataReadException(Messages.getString("ResultSet.InvalidLengthForType", new Object[] { length, "SHORT" }));
        }
        final int asInt = (bytes[offset] & 0xFF) | (bytes[offset + 1] & 0xFF) << 8;
        return vf.createFromLong(asInt);
    }
    
    @Override
    public <T> T decodeInt2(final byte[] bytes, final int offset, final int length, final ValueFactory<T> vf) {
        if (length != 2) {
            throw new DataReadException(Messages.getString("ResultSet.InvalidLengthForType", new Object[] { length, "SHORT" }));
        }
        final short asShort = (short)((bytes[offset] & 0xFF) | (bytes[offset + 1] & 0xFF) << 8);
        return vf.createFromLong(asShort);
    }
    
    @Override
    public <T> T decodeUInt4(final byte[] bytes, final int offset, final int length, final ValueFactory<T> vf) {
        if (length != 4) {
            throw new DataReadException(Messages.getString("ResultSet.InvalidLengthForType", new Object[] { length, "INT" }));
        }
        final long asLong = (long)((bytes[offset] & 0xFF) | (bytes[offset + 1] & 0xFF) << 8 | (bytes[offset + 2] & 0xFF) << 16) | (long)(bytes[offset + 3] & 0xFF) << 24;
        return vf.createFromLong(asLong);
    }
    
    @Override
    public <T> T decodeInt4(final byte[] bytes, final int offset, final int length, final ValueFactory<T> vf) {
        if (length != 4) {
            throw new DataReadException(Messages.getString("ResultSet.InvalidLengthForType", new Object[] { length, "SHORT" }));
        }
        final int asInt = (bytes[offset] & 0xFF) | (bytes[offset + 1] & 0xFF) << 8 | (bytes[offset + 2] & 0xFF) << 16 | (bytes[offset + 3] & 0xFF) << 24;
        return vf.createFromLong(asInt);
    }
    
    @Override
    public <T> T decodeInt8(final byte[] bytes, final int offset, final int length, final ValueFactory<T> vf) {
        if (length != 8) {
            throw new DataReadException(Messages.getString("ResultSet.InvalidLengthForType", new Object[] { length, "LONG" }));
        }
        final long asLong = (long)(bytes[offset] & 0xFF) | (long)(bytes[offset + 1] & 0xFF) << 8 | (long)(bytes[offset + 2] & 0xFF) << 16 | (long)(bytes[offset + 3] & 0xFF) << 24 | (long)(bytes[offset + 4] & 0xFF) << 32 | (long)(bytes[offset + 5] & 0xFF) << 40 | (long)(bytes[offset + 6] & 0xFF) << 48 | (long)(bytes[offset + 7] & 0xFF) << 56;
        return vf.createFromLong(asLong);
    }
    
    @Override
    public <T> T decodeUInt8(final byte[] bytes, final int offset, final int length, final ValueFactory<T> vf) {
        if (length != 8) {
            throw new DataReadException(Messages.getString("ResultSet.InvalidLengthForType", new Object[] { length, "LONG" }));
        }
        if ((bytes[offset + 7] & 0x80) == 0x0) {
            return (T)this.decodeInt8(bytes, offset, length, (ValueFactory<Object>)vf);
        }
        final byte[] bigEndian = { 0, bytes[offset + 7], bytes[offset + 6], bytes[offset + 5], bytes[offset + 4], bytes[offset + 3], bytes[offset + 2], bytes[offset + 1], bytes[offset] };
        final BigInteger bigInt = new BigInteger(bigEndian);
        return vf.createFromBigInteger(bigInt);
    }
    
    @Override
    public <T> T decodeFloat(final byte[] bytes, final int offset, final int length, final ValueFactory<T> vf) {
        if (length != 4) {
            throw new DataReadException(Messages.getString("ResultSet.InvalidLengthForType", new Object[] { length, "FLOAT" }));
        }
        final int asInt = (bytes[offset] & 0xFF) | (bytes[offset + 1] & 0xFF) << 8 | (bytes[offset + 2] & 0xFF) << 16 | (bytes[offset + 3] & 0xFF) << 24;
        return vf.createFromDouble(Float.intBitsToFloat(asInt));
    }
    
    @Override
    public <T> T decodeDouble(final byte[] bytes, final int offset, final int length, final ValueFactory<T> vf) {
        if (length != 8) {
            throw new DataReadException(Messages.getString("ResultSet.InvalidLengthForType", new Object[] { length, "DOUBLE" }));
        }
        final long valueAsLong = (long)(bytes[offset + 0] & 0xFF) | (long)(bytes[offset + 1] & 0xFF) << 8 | (long)(bytes[offset + 2] & 0xFF) << 16 | (long)(bytes[offset + 3] & 0xFF) << 24 | (long)(bytes[offset + 4] & 0xFF) << 32 | (long)(bytes[offset + 5] & 0xFF) << 40 | (long)(bytes[offset + 6] & 0xFF) << 48 | (long)(bytes[offset + 7] & 0xFF) << 56;
        return vf.createFromDouble(Double.longBitsToDouble(valueAsLong));
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
