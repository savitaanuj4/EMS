
package com.mysql.cj.result;

import com.mysql.cj.util.DataTypeUtil;
import java.math.BigDecimal;
import java.math.BigInteger;

public class LongValueFactory extends DefaultValueFactory<Long>
{
    @Override
    public Long createFromBigInteger(final BigInteger i) {
        return i.longValue();
    }
    
    @Override
    public Long createFromLong(final long l) {
        return l;
    }
    
    @Override
    public Long createFromBigDecimal(final BigDecimal d) {
        return d.longValue();
    }
    
    @Override
    public Long createFromDouble(final double d) {
        return (long)d;
    }
    
    @Override
    public Long createFromBit(final byte[] bytes, final int offset, final int length) {
        return DataTypeUtil.bitToLong(bytes, offset, length);
    }
    
    @Override
    public Long createFromNull() {
        return 0L;
    }
    
    @Override
    public String getTargetTypeName() {
        return Long.class.getName();
    }
}
