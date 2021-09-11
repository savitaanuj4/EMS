
package com.mysql.cj.result;

import java.math.BigDecimal;
import java.math.BigInteger;
import com.mysql.cj.exceptions.NumberOutOfRange;
import com.mysql.cj.Messages;

public class FloatingPointBoundsEnforcer<T> extends BaseDecoratingValueFactory<T>
{
    private double min;
    private double max;
    
    public FloatingPointBoundsEnforcer(final ValueFactory<T> targetVf, final double min, final double max) {
        super(targetVf);
        this.min = min;
        this.max = max;
    }
    
    @Override
    public T createFromLong(final long l) {
        if (l < this.min || l > this.max) {
            throw new NumberOutOfRange(Messages.getString("ResultSet.NumberOutOfRange", new Object[] { l, this.targetVf.getTargetTypeName() }));
        }
        return this.targetVf.createFromLong(l);
    }
    
    @Override
    public T createFromBigInteger(final BigInteger i) {
        if (new BigDecimal(i).compareTo(BigDecimal.valueOf(this.min)) < 0 || new BigDecimal(i).compareTo(BigDecimal.valueOf(this.max)) > 0) {
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
