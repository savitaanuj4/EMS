
package com.mysql.cj.result;

import java.nio.ByteBuffer;
import java.math.BigDecimal;
import java.math.BigInteger;

public class FloatValueFactory extends DefaultValueFactory<Float>
{
    @Override
    public Float createFromBigInteger(final BigInteger i) {
        return (float)i.doubleValue();
    }
    
    @Override
    public Float createFromLong(final long l) {
        return Float.valueOf(l);
    }
    
    @Override
    public Float createFromBigDecimal(final BigDecimal d) {
        return (float)d.doubleValue();
    }
    
    @Override
    public Float createFromDouble(final double d) {
        return (float)d;
    }
    
    @Override
    public Float createFromBit(final byte[] bytes, final int offset, final int length) {
        return new BigInteger(ByteBuffer.allocate(length + 1).put((byte)0).put(bytes, offset, length).array()).floatValue();
    }
    
    @Override
    public Float createFromNull() {
        return 0.0f;
    }
    
    @Override
    public String getTargetTypeName() {
        return Float.class.getName();
    }
}
