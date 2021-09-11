
package com.mysql.cj.result;

import java.nio.ByteBuffer;
import com.mysql.cj.util.StringUtils;
import java.math.BigDecimal;
import java.math.BigInteger;

public class StringValueFactory implements ValueFactory<String>
{
    private String encoding;
    
    public StringValueFactory() {
    }
    
    public StringValueFactory(final String encoding) {
        this.encoding = encoding;
    }
    
    @Override
    public String createFromDate(final int year, final int month, final int day) {
        return String.format("%04d-%02d-%02d", year, month, day);
    }
    
    @Override
    public String createFromTime(final int hours, final int minutes, final int seconds, final int nanos) {
        if (nanos > 0) {
            return String.format("%02d:%02d:%02d.%09d", hours, minutes, seconds, nanos);
        }
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
    
    @Override
    public String createFromTimestamp(final int year, final int month, final int day, final int hours, final int minutes, final int seconds, final int nanos) {
        return String.format("%s %s", this.createFromDate(year, month, day), this.createFromTime(hours, minutes, seconds, nanos));
    }
    
    @Override
    public String createFromLong(final long l) {
        return String.valueOf(l);
    }
    
    @Override
    public String createFromBigInteger(final BigInteger i) {
        return i.toString();
    }
    
    @Override
    public String createFromDouble(final double d) {
        return String.valueOf(d);
    }
    
    @Override
    public String createFromBigDecimal(final BigDecimal d) {
        return d.toString();
    }
    
    @Override
    public String createFromBytes(final byte[] bytes, final int offset, final int length) {
        return StringUtils.toString(bytes, offset, length, this.encoding);
    }
    
    @Override
    public String createFromBit(final byte[] bytes, final int offset, final int length) {
        return new BigInteger(ByteBuffer.allocate(length + 1).put((byte)0).put(bytes, offset, length).array()).toString();
    }
    
    @Override
    public String createFromNull() {
        return null;
    }
    
    @Override
    public String getTargetTypeName() {
        return String.class.getName();
    }
}
