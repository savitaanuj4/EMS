
package com.mysql.cj.result;

import java.nio.ByteBuffer;
import java.math.BigDecimal;
import java.math.BigInteger;

public class DoubleValueFactory extends DefaultValueFactory<Double>
{
    @Override
    public Double createFromBigInteger(final BigInteger i) {
        return i.doubleValue();
    }
    
    @Override
    public Double createFromLong(final long l) {
        return Double.valueOf(l);
    }
    
    @Override
    public Double createFromBigDecimal(final BigDecimal d) {
        return d.doubleValue();
    }
    
    @Override
    public Double createFromDouble(final double d) {
        return d;
    }
    
    @Override
    public Double createFromBit(final byte[] bytes, final int offset, final int length) {
        return new BigInteger(ByteBuffer.allocate(length + 1).put((byte)0).put(bytes, offset, length).array()).doubleValue();
    }
    
    @Override
    public Double createFromNull() {
        return 0.0;
    }
    
    @Override
    public String getTargetTypeName() {
        return Double.class.getName();
    }
}
