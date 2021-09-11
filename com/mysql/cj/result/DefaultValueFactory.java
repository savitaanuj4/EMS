
package com.mysql.cj.result;

import java.math.BigDecimal;
import java.math.BigInteger;
import com.mysql.cj.exceptions.DataConversionException;
import com.mysql.cj.Messages;

public abstract class DefaultValueFactory<T> implements ValueFactory<T>
{
    private T unsupported(final String sourceType) {
        throw new DataConversionException(Messages.getString("ResultSet.UnsupportedConversion", new Object[] { sourceType, this.getTargetTypeName() }));
    }
    
    @Override
    public T createFromDate(final int year, final int month, final int day) {
        return this.unsupported("DATE");
    }
    
    @Override
    public T createFromTime(final int hours, final int minutes, final int seconds, final int nanos) {
        return this.unsupported("TIME");
    }
    
    @Override
    public T createFromTimestamp(final int year, final int month, final int day, final int hours, final int minutes, final int seconds, final int nanos) {
        return this.unsupported("TIMESTAMP");
    }
    
    @Override
    public T createFromLong(final long l) {
        return this.unsupported("LONG");
    }
    
    @Override
    public T createFromBigInteger(final BigInteger i) {
        return this.unsupported("BIGINT");
    }
    
    @Override
    public T createFromDouble(final double d) {
        return this.unsupported("DOUBLE");
    }
    
    @Override
    public T createFromBigDecimal(final BigDecimal d) {
        return this.unsupported("DECIMAL");
    }
    
    @Override
    public T createFromBytes(final byte[] bytes, final int offset, final int length) {
        return this.unsupported("VARCHAR/TEXT/BLOB");
    }
    
    @Override
    public T createFromBit(final byte[] bytes, final int offset, final int length) {
        return this.unsupported("BIT");
    }
    
    @Override
    public T createFromNull() {
        return null;
    }
}
