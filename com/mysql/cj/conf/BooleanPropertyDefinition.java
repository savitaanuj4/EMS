
package com.mysql.cj.conf;

import java.util.function.Function;
import java.util.Arrays;
import com.mysql.cj.exceptions.ExceptionFactory;
import com.mysql.cj.Messages;
import com.mysql.cj.util.StringUtils;
import com.mysql.cj.exceptions.ExceptionInterceptor;

public class BooleanPropertyDefinition extends AbstractPropertyDefinition<Boolean>
{
    private static final long serialVersionUID = -7288366734350231540L;
    
    public BooleanPropertyDefinition(final PropertyKey key, final Boolean defaultValue, final boolean isRuntimeModifiable, final String description, final String sinceVersion, final String category, final int orderInCategory) {
        super(key, defaultValue, isRuntimeModifiable, description, sinceVersion, category, orderInCategory);
    }
    
    @Override
    public String[] getAllowableValues() {
        return getBooleanAllowableValues();
    }
    
    @Override
    public Boolean parseObject(final String value, final ExceptionInterceptor exceptionInterceptor) {
        return booleanFrom(this.getName(), value, exceptionInterceptor);
    }
    
    @Override
    public RuntimeProperty<Boolean> createRuntimeProperty() {
        return new BooleanProperty(this);
    }
    
    public static Boolean booleanFrom(final String name, final String value, final ExceptionInterceptor exceptionInterceptor) {
        try {
            return AllowableValues.valueOf(value.toUpperCase()).asBoolean();
        }
        catch (Exception e) {
            throw ExceptionFactory.createException(Messages.getString("PropertyDefinition.1", new Object[] { name, StringUtils.stringArrayToString(getBooleanAllowableValues(), "'", "', '", "' or '", "'"), value }), e, exceptionInterceptor);
        }
    }
    
    public static String[] getBooleanAllowableValues() {
        return Arrays.stream(AllowableValues.values()).map((Function<? super AllowableValues, ?>)Enum::toString).toArray(String[]::new);
    }
    
    public enum AllowableValues
    {
        TRUE(true), 
        FALSE(false), 
        YES(true), 
        NO(false);
        
        private boolean asBoolean;
        
        private AllowableValues(final boolean booleanValue) {
            this.asBoolean = booleanValue;
        }
        
        public boolean asBoolean() {
            return this.asBoolean;
        }
    }
}
