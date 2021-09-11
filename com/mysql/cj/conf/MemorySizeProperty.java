
package com.mysql.cj.conf;

import javax.naming.Reference;
import com.mysql.cj.exceptions.ExceptionInterceptor;
import java.util.Properties;

public class MemorySizeProperty extends IntegerProperty
{
    private static final long serialVersionUID = 4200558564320133284L;
    private String initialValueAsString;
    protected String valueAsString;
    
    protected MemorySizeProperty(final PropertyDefinition<Integer> propertyDefinition) {
        super(propertyDefinition);
        this.valueAsString = propertyDefinition.getDefaultValue().toString();
    }
    
    @Override
    public void initializeFrom(final Properties extractFrom, final ExceptionInterceptor exceptionInterceptor) {
        super.initializeFrom(extractFrom, exceptionInterceptor);
        this.initialValueAsString = this.valueAsString;
    }
    
    @Override
    public void initializeFrom(final Reference ref, final ExceptionInterceptor exceptionInterceptor) {
        super.initializeFrom(ref, exceptionInterceptor);
        this.initialValueAsString = this.valueAsString;
    }
    
    @Override
    public String getStringValue() {
        return this.valueAsString;
    }
    
    @Override
    public void setValueInternal(final Integer value, final String valueAsString, final ExceptionInterceptor exceptionInterceptor) {
        super.setValueInternal(value, valueAsString, exceptionInterceptor);
        this.valueAsString = ((valueAsString == null) ? String.valueOf((int)value) : valueAsString);
    }
    
    @Override
    public void resetValue() {
        this.value = this.initialValue;
        this.valueAsString = this.initialValueAsString;
        this.invokeListeners();
    }
}
