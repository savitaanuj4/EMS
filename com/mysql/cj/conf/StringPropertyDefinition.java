
package com.mysql.cj.conf;

import com.mysql.cj.exceptions.ExceptionInterceptor;

public class StringPropertyDefinition extends AbstractPropertyDefinition<String>
{
    private static final long serialVersionUID = 8228934389127796555L;
    
    public StringPropertyDefinition(final String name, final String alias, final String defaultValue, final boolean isRuntimeModifiable, final String description, final String sinceVersion, final String category, final int orderInCategory) {
        super(name, alias, defaultValue, isRuntimeModifiable, description, sinceVersion, category, orderInCategory);
    }
    
    public StringPropertyDefinition(final PropertyKey key, final String defaultValue, final boolean isRuntimeModifiable, final String description, final String sinceVersion, final String category, final int orderInCategory) {
        super(key, defaultValue, isRuntimeModifiable, description, sinceVersion, category, orderInCategory);
    }
    
    @Override
    public String parseObject(final String value, final ExceptionInterceptor exceptionInterceptor) {
        return value;
    }
    
    @Override
    public RuntimeProperty<String> createRuntimeProperty() {
        return new StringProperty(this);
    }
}
