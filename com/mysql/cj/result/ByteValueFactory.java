
package com.mysql.cj.result;

import java.math.BigDecimal;
import java.math.BigInteger;

public class ByteValueFactory extends DefaultValueFactory<Byte>
{
    @Override
    public Byte createFromBigInteger(final BigInteger i) {
        return (byte)i.intValue();
    }
    
    @Override
    public Byte createFromLong(final long l) {
        return (byte)l;
    }
    
    @Override
    public Byte createFromBigDecimal(final BigDecimal d) {
        return (byte)d.longValue();
    }
    
    @Override
    public Byte createFromDouble(final double d) {
        return (byte)d;
    }
    
    @Override
    public Byte createFromBit(final byte[] bytes, final int offset, final int length) {
        return bytes[offset + length - 1];
    }
    
    @Override
    public Byte createFromNull() {
        return 0;
    }
    
    @Override
    public String getTargetTypeName() {
        return Byte.class.getName();
    }
}
