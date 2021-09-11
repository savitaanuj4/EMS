
package com.mysql.cj.conf;

import com.mysql.cj.exceptions.ExceptionFactory;
import com.mysql.cj.exceptions.WrongArgumentException;
import com.mysql.cj.exceptions.ExceptionInterceptor;

public class IntegerPropertyDefinition extends AbstractPropertyDefinition<Integer>
{
    private static final long serialVersionUID = 4151893695173946081L;
    protected int multiplier;
    
    public IntegerPropertyDefinition(final PropertyKey key, final int defaultValue, final boolean isRuntimeModifiable, final String description, final String sinceVersion, final String category, final int orderInCategory) {
        super(key, defaultValue, isRuntimeModifiable, description, sinceVersion, category, orderInCategory);
        this.multiplier = 1;
    }
    
    public IntegerPropertyDefinition(final PropertyKey key, final int defaultValue, final boolean isRuntimeModifiable, final String description, final String sinceVersion, final String category, final int orderInCategory, final int lowerBound, final int upperBound) {
        super(key, defaultValue, isRuntimeModifiable, description, sinceVersion, category, orderInCategory, lowerBound, upperBound);
        this.multiplier = 1;
    }
    
    @Override
    public boolean isRangeBased() {
        return this.getUpperBound() != this.getLowerBound();
    }
    
    @Override
    public Integer parseObject(final String value, final ExceptionInterceptor exceptionInterceptor) {
        return integerFrom(this.getName(), value, this.multiplier, exceptionInterceptor);
    }
    
    @Override
    public RuntimeProperty<Integer> createRuntimeProperty() {
        return new IntegerProperty(this);
    }
    
    public static Integer integerFrom(final String name, final String value, final int multiplier, final ExceptionInterceptor exceptionInterceptor) {
        try {
            final int intValue = (int)(Double.valueOf(value) * multiplier);
            return intValue;
        }
        catch (NumberFormatException nfe) {
            throw ExceptionFactory.createException(WrongArgumentException.class, "The connection property '" + name + "' only accepts integer values. The value '" + value + "' can not be converted to an integer.", exceptionInterceptor);
        }
    }
}
