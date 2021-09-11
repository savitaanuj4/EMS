
package com.mysql.cj.result;

import java.math.BigDecimal;
import java.math.BigInteger;

public abstract class BaseDecoratingValueFactory<T> implements ValueFactory<T>
{
    protected ValueFactory<T> targetVf;
    
    public BaseDecoratingValueFactory(final ValueFactory<T> targetVf) {
        this.targetVf = targetVf;
    }
    
    @Override
    public T createFromDate(final int year, final int month, final int day) {
        return this.targetVf.createFromDate(year, month, day);
    }
    
    @Override
    public T createFromTime(final int hours, final int minutes, final int seconds, final int nanos) {
        return this.targetVf.createFromTime(hours, minutes, seconds, nanos);
    }
    
    @Override
    public T createFromTimestamp(final int year, final int month, final int day, final int hours, final int minutes, final int seconds, final int nanos) {
        return this.targetVf.createFromTimestamp(year, month, day, hours, minutes, seconds, nanos);
    }
    
    @Override
    public T createFromLong(final long l) {
        return this.targetVf.createFromLong(l);
    }
    
    @Override
    public T createFromBigInteger(final BigInteger i) {
        return this.targetVf.createFromBigInteger(i);
    }
    
    @Override
    public T createFromDouble(final double d) {
        return this.targetVf.createFromDouble(d);
    }
    
    @Override
    public T createFromBigDecimal(final BigDecimal d) {
        return this.targetVf.createFromBigDecimal(d);
    }
    
    @Override
    public T createFromBytes(final byte[] bytes, final int offset, final int length) {
        return this.targetVf.createFromBytes(bytes, offset, length);
    }
    
    @Override
    public T createFromBit(final byte[] bytes, final int offset, final int length) {
        return this.targetVf.createFromBit(bytes, offset, length);
    }
    
    @Override
    public T createFromNull() {
        return this.targetVf.createFromNull();
    }
    
    @Override
    public String getTargetTypeName() {
        return this.targetVf.getTargetTypeName();
    }
}
