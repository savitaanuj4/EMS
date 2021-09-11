
package com.mysql.cj.conf;

import javax.naming.Reference;
import com.mysql.cj.exceptions.ExceptionInterceptor;
import java.util.Properties;

public interface RuntimeProperty<T>
{
    PropertyDefinition<T> getPropertyDefinition();
    
    void initializeFrom(final Properties p0, final ExceptionInterceptor p1);
    
    void initializeFrom(final Reference p0, final ExceptionInterceptor p1);
    
    void resetValue();
    
    boolean isExplicitlySet();
    
    void addListener(final RuntimePropertyListener p0);
    
    void removeListener(final RuntimePropertyListener p0);
    
    T getValue();
    
    T getInitialValue();
    
    String getStringValue();
    
    void setValue(final T p0);
    
    void setValue(final T p0, final ExceptionInterceptor p1);
    
    @FunctionalInterface
    public interface RuntimePropertyListener
    {
        void handlePropertyChange(final RuntimeProperty<?> p0);
    }
}
