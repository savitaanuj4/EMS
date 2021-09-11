
package com.mysql.cj.conf;

import com.mysql.cj.exceptions.ExceptionFactory;
import com.mysql.cj.exceptions.WrongArgumentException;
import com.mysql.cj.exceptions.ExceptionInterceptor;

public class IntegerProperty extends AbstractRuntimeProperty<Integer>
{
    private static final long serialVersionUID = 9208223182595760858L;
    
    public IntegerProperty(final PropertyDefinition<Integer> propertyDefinition) {
        super(propertyDefinition);
    }
    
    @Override
    protected void checkRange(final Integer val, final String valueAsString, final ExceptionInterceptor exceptionInterceptor) {
        if (val < this.getPropertyDefinition().getLowerBound() || val > this.getPropertyDefinition().getUpperBound()) {
            throw ExceptionFactory.createException(WrongArgumentException.class, "The connection property '" + this.getPropertyDefinition().getName() + "' only accepts integer values in the range of " + this.getPropertyDefinition().getLowerBound() + " - " + this.getPropertyDefinition().getUpperBound() + ", the value '" + ((valueAsString == null) ? Integer.valueOf(val) : valueAsString) + "' exceeds this range.", exceptionInterceptor);
        }
    }
}
