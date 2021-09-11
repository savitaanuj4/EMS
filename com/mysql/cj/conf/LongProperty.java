
package com.mysql.cj.conf;

import com.mysql.cj.exceptions.ExceptionFactory;
import com.mysql.cj.exceptions.WrongArgumentException;
import com.mysql.cj.exceptions.ExceptionInterceptor;

public class LongProperty extends AbstractRuntimeProperty<Long>
{
    private static final long serialVersionUID = 1814429804634837665L;
    
    protected LongProperty(final PropertyDefinition<Long> propertyDefinition) {
        super(propertyDefinition);
    }
    
    @Override
    protected void checkRange(final Long val, final String valueAsString, final ExceptionInterceptor exceptionInterceptor) {
        if (val < this.getPropertyDefinition().getLowerBound() || val > this.getPropertyDefinition().getUpperBound()) {
            throw ExceptionFactory.createException(WrongArgumentException.class, "The connection property '" + this.getPropertyDefinition().getName() + "' only accepts long integer values in the range of " + this.getPropertyDefinition().getLowerBound() + " - " + this.getPropertyDefinition().getUpperBound() + ", the value '" + ((valueAsString == null) ? Long.valueOf(val) : valueAsString) + "' exceeds this range.", exceptionInterceptor);
        }
    }
}
