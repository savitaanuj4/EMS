
package com.mysql.cj.conf;

public class StringProperty extends AbstractRuntimeProperty<String>
{
    private static final long serialVersionUID = -4141084145739428803L;
    
    protected StringProperty(final PropertyDefinition<String> propertyDefinition) {
        super(propertyDefinition);
    }
    
    @Override
    public String getStringValue() {
        return (String)this.value;
    }
}
