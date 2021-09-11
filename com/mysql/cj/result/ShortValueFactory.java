
package com.mysql.cj.result;

import com.mysql.cj.util.DataTypeUtil;
import java.math.BigDecimal;
import java.math.BigInteger;

public class ShortValueFactory extends DefaultValueFactory<Short>
{
    @Override
    public Short createFromBigInteger(final BigInteger i) {
        return (short)i.intValue();
    }
    
    @Override
    public Short createFromLong(final long l) {
        return (short)l;
    }
    
    @Override
    public Short createFromBigDecimal(final BigDecimal d) {
        return (short)d.longValue();
    }
    
    @Override
    public Short createFromDouble(final double d) {
        return (short)d;
    }
    
    @Override
    public Short createFromBit(final byte[] bytes, final int offset, final int length) {
        return this.createFromLong(DataTypeUtil.bitToLong(bytes, offset, length));
    }
    
    @Override
    public Short createFromNull() {
        return 0;
    }
    
    @Override
    public String getTargetTypeName() {
        return Short.class.getName();
    }
}
