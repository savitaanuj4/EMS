
package com.mysql.cj.result;

import java.math.BigDecimal;
import java.math.BigInteger;
import com.mysql.cj.exceptions.NumberOutOfRange;
import com.mysql.cj.Messages;

public class IntegerBoundsEnforcer<T> extends BaseDecoratingValueFactory<T>
{
    private long min;
    private long max;
    
    public IntegerBoundsEnforcer(final ValueFactory<T> targetVf, final long min, final long max) {
        super(targetVf);
        this.min = min;
        this.max = max;
    }
    
    @Override
    public T createFromLong(final long l) {
        if (l < this.min || l > this.max) {
            throw new NumberOutOfRange(Messages.getString("ResultSet.NumberOutOfRange", new Object[] { Long.valueOf(l).toString(), this.targetVf.getTargetTypeName() }));
        }
        return this.targetVf.createFromLong(l);
    }
    
    @Override
    public T createFromBigInteger(final BigInteger i) {
        if (i.compareTo(BigInteger.valueOf(this.min)) < 0 || i.compareTo(BigInteger.valueOf(this.max)) > 0) {
            throw new NumberOutOfRange(Messages.getString("ResultSet.NumberOutOfRange", new Object[] { i, this.targetVf.getTargetTypeName() }));
        }
        return this.targetVf.createFromBigInteger(i);
    }
    
    @Override
    public T createFromDouble(final double d) {
        if (d < this.min || d > this.max) {
            throw new NumberOutOfRange(Messages.getString("ResultSet.NumberOutOfRange", new Object[] { d, this.targetVf.getTargetTypeName() }));
        }
        return this.targetVf.createFromDouble(d);
    }
    
    @Override
    public T createFromBigDecimal(final BigDecimal d) {
        if (d.compareTo(BigDecimal.valueOf(this.min)) < 0 || d.compareTo(BigDecimal.valueOf(this.max)) > 0) {
            throw new NumberOutOfRange(Messages.getString("ResultSet.NumberOutOfRange", new Object[] { d, this.targetVf.getTargetTypeName() }));
        }
        return this.targetVf.createFromBigDecimal(d);
    }
}
