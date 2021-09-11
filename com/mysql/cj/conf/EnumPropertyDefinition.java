
package com.mysql.cj.conf;

import com.mysql.cj.Messages;
import com.mysql.cj.util.StringUtils;
import com.mysql.cj.exceptions.ExceptionInterceptor;
import java.util.function.Function;
import java.util.Arrays;
import com.mysql.cj.exceptions.ExceptionFactory;

public class EnumPropertyDefinition<T extends Enum<T>> extends AbstractPropertyDefinition<T>
{
    private static final long serialVersionUID = -3297521968759540444L;
    private Class<T> enumType;
    
    public EnumPropertyDefinition(final PropertyKey key, final T defaultValue, final boolean isRuntimeModifiable, final String description, final String sinceVersion, final String category, final int orderInCategory) {
        super(key, defaultValue, isRuntimeModifiable, description, sinceVersion, category, orderInCategory);
        if (defaultValue == null) {
            throw ExceptionFactory.createException("Enum property '" + key.getKeyName() + "' cannot be initialized with null.");
        }
        this.enumType = defaultValue.getDeclaringClass();
    }
    
    @Override
    public String[] getAllowableValues() {
        return Arrays.stream(this.enumType.getEnumConstants()).map((Function<? super T, ?>)Enum::toString).sorted().toArray(String[]::new);
    }
    
    @Override
    public T parseObject(final String value, final ExceptionInterceptor exceptionInterceptor) {
        try {
            return Enum.valueOf(this.enumType, value.toUpperCase());
        }
        catch (Exception e) {
            throw ExceptionFactory.createException(Messages.getString("PropertyDefinition.1", new Object[] { this.getName(), StringUtils.stringArrayToString(this.getAllowableValues(), "'", "', '", "' or '", "'"), value }), e, exceptionInterceptor);
        }
    }
    
    @Override
    public RuntimeProperty<T> createRuntimeProperty() {
        return new EnumProperty<T>(this);
    }
}
