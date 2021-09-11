
package com.mysql.cj.result;

import java.math.BigDecimal;
import java.math.BigInteger;

public interface ValueFactory<T>
{
    T createFromDate(final int p0, final int p1, final int p2);
    
    T createFromTime(final int p0, final int p1, final int p2, final int p3);
    
    T createFromTimestamp(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6);
    
    T createFromLong(final long p0);
    
    T createFromBigInteger(final BigInteger p0);
    
    T createFromDouble(final double p0);
    
    T createFromBigDecimal(final BigDecimal p0);
    
    T createFromBytes(final byte[] p0, final int p1, final int p2);
    
    T createFromBit(final byte[] p0, final int p1, final int p2);
    
    T createFromNull();
    
    String getTargetTypeName();
}
