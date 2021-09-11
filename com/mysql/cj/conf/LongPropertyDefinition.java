
package com.mysql.cj.conf;

import com.mysql.cj.exceptions.ExceptionFactory;
import com.mysql.cj.exceptions.WrongArgumentException;
import com.mysql.cj.exceptions.ExceptionInterceptor;

public class LongPropertyDefinition extends AbstractPropertyDefinition<Long>
{
    private static final long serialVersionUID = -5264490959206230852L;
    
    public LongPropertyDefinition(final PropertyKey key, final long defaultValue, final boolean isRuntimeModifiable, final String description, final String sinceVersion, final String category, final int orderInCategory) {
        super(key, defaultValue, isRuntimeModifiable, description, sinceVersion, category, orderInCategory);
    }
    
    public LongPropertyDefinition(final PropertyKey key, final long defaultValue, final boolean isRuntimeModifiable, final String description, final String sinceVersion, final String category, final int orderInCategory, final long lowerBound, final long upperBound) {
        super(key, defaultValue, isRuntimeModifiable, description, sinceVersion, category, orderInCategory, (int)lowerBound, (int)upperBound);
    }
    
    @Override
    public Long parseObject(final String value, final ExceptionInterceptor exceptionInterceptor) {
        try {
            return Double.valueOf(value).longValue();
        }
        catch (NumberFormatException nfe) {
            throw ExceptionFactory.createException(WrongArgumentException.class, "The connection property '" + this.getName() + "' only accepts long integer values. The value '" + value + "' can not be converted to a long integer.", exceptionInterceptor);
        }
    }
    
    @Override
    public boolean isRangeBased() {
        return this.getUpperBound() != this.getLowerBound();
    }
    
    @Override
    public RuntimeProperty<Long> createRuntimeProperty() {
        return new LongProperty(this);
    }
}
