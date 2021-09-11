
package com.mysql.cj.conf;

import java.util.Properties;

public interface PropertySet
{
    void addProperty(final RuntimeProperty<?> p0);
    
    void removeProperty(final String p0);
    
    void removeProperty(final PropertyKey p0);
    
     <T> RuntimeProperty<T> getProperty(final String p0);
    
     <T> RuntimeProperty<T> getProperty(final PropertyKey p0);
    
    RuntimeProperty<Boolean> getBooleanProperty(final String p0);
    
    RuntimeProperty<Boolean> getBooleanProperty(final PropertyKey p0);
    
    RuntimeProperty<Integer> getIntegerProperty(final String p0);
    
    RuntimeProperty<Integer> getIntegerProperty(final PropertyKey p0);
    
    RuntimeProperty<Long> getLongProperty(final String p0);
    
    RuntimeProperty<Long> getLongProperty(final PropertyKey p0);
    
    RuntimeProperty<Integer> getMemorySizeProperty(final String p0);
    
    RuntimeProperty<Integer> getMemorySizeProperty(final PropertyKey p0);
    
    RuntimeProperty<String> getStringProperty(final String p0);
    
    RuntimeProperty<String> getStringProperty(final PropertyKey p0);
    
     <T extends Enum<T>> RuntimeProperty<T> getEnumProperty(final String p0);
    
     <T extends Enum<T>> RuntimeProperty<T> getEnumProperty(final PropertyKey p0);
    
    void initializeProperties(final Properties p0);
    
    void postInitialization();
    
    Properties exposeAsProperties();
    
    void reset();
}
