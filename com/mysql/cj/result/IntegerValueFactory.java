
package com.mysql.cj.result;

import com.mysql.cj.util.DataTypeUtil;
import java.math.BigDecimal;
import java.math.BigInteger;

public class IntegerValueFactory extends DefaultValueFactory<Integer>
{
    @Override
    public Integer createFromBigInteger(final BigInteger i) {
        return i.intValue();
    }
    
    @Override
    public Integer createFromLong(final long l) {
        return (int)l;
    }
    
    @Override
    public Integer createFromBigDecimal(final BigDecimal d) {
        return (int)d.longValue();
    }
    
    @Override
    public Integer createFromDouble(final double d) {
        return (int)d;
    }
    
    @Override
    public Integer createFromBit(final byte[] bytes, final int offset, final int length) {
        return (int)DataTypeUtil.bitToLong(bytes, offset, length);
    }
    
    @Override
    public Integer createFromNull() {
        return 0;
    }
    
    @Override
    public String getTargetTypeName() {
        return Integer.class.getName();
    }
}
