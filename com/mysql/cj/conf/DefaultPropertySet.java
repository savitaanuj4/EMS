
package com.mysql.cj.conf;

import com.mysql.cj.Messages;
import com.mysql.cj.exceptions.CJException;
import com.mysql.cj.exceptions.ExceptionInterceptor;
import java.util.Properties;
import com.mysql.cj.exceptions.ExceptionFactory;
import com.mysql.cj.exceptions.WrongArgumentException;
import java.util.Iterator;
import java.util.HashMap;
import java.util.Map;
import java.io.Serializable;

public class DefaultPropertySet implements PropertySet, Serializable
{
    private static final long serialVersionUID = -5156024634430650528L;
    private final Map<PropertyKey, RuntimeProperty<?>> PROPERTY_KEY_TO_RUNTIME_PROPERTY;
    private final Map<String, RuntimeProperty<?>> PROPERTY_NAME_TO_RUNTIME_PROPERTY;
    
    public DefaultPropertySet() {
        this.PROPERTY_KEY_TO_RUNTIME_PROPERTY = new HashMap<PropertyKey, RuntimeProperty<?>>();
        this.PROPERTY_NAME_TO_RUNTIME_PROPERTY = new HashMap<String, RuntimeProperty<?>>();
        for (final PropertyDefinition<?> pdef : PropertyDefinitions.PROPERTY_KEY_TO_PROPERTY_DEFINITION.values()) {
            this.addProperty(pdef.createRuntimeProperty());
        }
    }
    
    @Override
    public void addProperty(final RuntimeProperty<?> prop) {
        final PropertyDefinition<?> def = prop.getPropertyDefinition();
        if (def.getPropertyKey() != null) {
            this.PROPERTY_KEY_TO_RUNTIME_PROPERTY.put(def.getPropertyKey(), prop);
        }
        else {
            this.PROPERTY_NAME_TO_RUNTIME_PROPERTY.put(def.getName(), prop);
            if (def.hasCcAlias()) {
                this.PROPERTY_NAME_TO_RUNTIME_PROPERTY.put(def.getCcAlias(), prop);
            }
        }
    }
    
    @Override
    public void removeProperty(final String name) {
        final PropertyKey key = PropertyKey.fromValue(name);
        if (key != null) {
            this.PROPERTY_KEY_TO_RUNTIME_PROPERTY.remove(key);
        }
        else {
            final RuntimeProperty<?> prop = this.PROPERTY_NAME_TO_RUNTIME_PROPERTY.remove(name);
            if (prop != null) {
                if (!name.equals(prop.getPropertyDefinition().getName())) {
                    this.PROPERTY_NAME_TO_RUNTIME_PROPERTY.remove(prop.getPropertyDefinition().getName());
                }
                else if (prop.getPropertyDefinition().hasCcAlias()) {
                    this.PROPERTY_NAME_TO_RUNTIME_PROPERTY.remove(prop.getPropertyDefinition().getCcAlias());
                }
            }
        }
    }
    
    @Override
    public void removeProperty(final PropertyKey key) {
        this.PROPERTY_KEY_TO_RUNTIME_PROPERTY.remove(key);
    }
    
    @Override
    public <T> RuntimeProperty<T> getProperty(final String name) {
        try {
            final PropertyKey key = PropertyKey.fromValue(name);
            if (key != null) {
                return this.getProperty(key);
            }
            return (RuntimeProperty<T>)this.PROPERTY_NAME_TO_RUNTIME_PROPERTY.get(name);
        }
        catch (ClassCastException ex) {
            throw ExceptionFactory.createException(WrongArgumentException.class, ex.getMessage(), ex);
        }
    }
    
    @Override
    public <T> RuntimeProperty<T> getProperty(final PropertyKey key) {
        try {
            RuntimeProperty<T> prop = (RuntimeProperty<T>)this.PROPERTY_KEY_TO_RUNTIME_PROPERTY.get(key);
            if (prop == null) {
                prop = (RuntimeProperty<T>)this.PROPERTY_NAME_TO_RUNTIME_PROPERTY.get(key.getKeyName());
            }
            return prop;
        }
        catch (ClassCastException ex) {
            throw ExceptionFactory.createException(WrongArgumentException.class, ex.getMessage(), ex);
        }
    }
    
    @Override
    public RuntimeProperty<Boolean> getBooleanProperty(final String name) {
        return this.getProperty(name);
    }
    
    @Override
    public RuntimeProperty<Boolean> getBooleanProperty(final PropertyKey key) {
        return this.getProperty(key);
    }
    
    @Override
    public RuntimeProperty<Integer> getIntegerProperty(final String name) {
        return this.getProperty(name);
    }
    
    @Override
    public RuntimeProperty<Integer> getIntegerProperty(final PropertyKey key) {
        return this.getProperty(key);
    }
    
    @Override
    public RuntimeProperty<Long> getLongProperty(final String name) {
        return this.getProperty(name);
    }
    
    @Override
    public RuntimeProperty<Long> getLongProperty(final PropertyKey key) {
        return this.getProperty(key);
    }
    
    @Override
    public RuntimeProperty<Integer> getMemorySizeProperty(final String name) {
        return this.getProperty(name);
    }
    
    @Override
    public RuntimeProperty<Integer> getMemorySizeProperty(final PropertyKey key) {
        return this.getProperty(key);
    }
    
    @Override
    public RuntimeProperty<String> getStringProperty(final String name) {
        return this.getProperty(name);
    }
    
    @Override
    public RuntimeProperty<String> getStringProperty(final PropertyKey key) {
        return this.getProperty(key);
    }
    
    @Override
    public <T extends Enum<T>> RuntimeProperty<T> getEnumProperty(final String name) {
        return (RuntimeProperty<T>)this.getProperty(name);
    }
    
    @Override
    public <T extends Enum<T>> RuntimeProperty<T> getEnumProperty(final PropertyKey key) {
        return (RuntimeProperty<T>)this.getProperty(key);
    }
    
    @Override
    public void initializeProperties(final Properties props) {
        if (props != null) {
            final Properties infoCopy = (Properties)props.clone();
            infoCopy.remove(PropertyKey.HOST.getKeyName());
            infoCopy.remove(PropertyKey.PORT.getKeyName());
            infoCopy.remove(PropertyKey.USER.getKeyName());
            infoCopy.remove(PropertyKey.PASSWORD.getKeyName());
            infoCopy.remove(PropertyKey.DBNAME.getKeyName());
            for (final PropertyKey propKey : PropertyDefinitions.PROPERTY_KEY_TO_PROPERTY_DEFINITION.keySet()) {
                try {
                    final RuntimeProperty<?> propToSet = this.getProperty(propKey);
                    propToSet.initializeFrom(infoCopy, null);
                }
                catch (CJException e) {
                    throw ExceptionFactory.createException(WrongArgumentException.class, e.getMessage(), e);
                }
            }
            final RuntimeProperty<PropertyDefinitions.SslMode> sslMode = this.getEnumProperty(PropertyKey.sslMode);
            if (!sslMode.isExplicitlySet()) {
                final RuntimeProperty<Boolean> useSSL = this.getBooleanProperty(PropertyKey.useSSL);
                final RuntimeProperty<Boolean> verifyServerCertificate = this.getBooleanProperty(PropertyKey.verifyServerCertificate);
                final RuntimeProperty<Boolean> requireSSL = this.getBooleanProperty(PropertyKey.requireSSL);
                if (useSSL.isExplicitlySet() || verifyServerCertificate.isExplicitlySet() || requireSSL.isExplicitlySet()) {
                    if (!useSSL.getValue()) {
                        sslMode.setValue(PropertyDefinitions.SslMode.DISABLED);
                    }
                    else if (verifyServerCertificate.getValue()) {
                        sslMode.setValue(PropertyDefinitions.SslMode.VERIFY_CA);
                    }
                    else if (requireSSL.getValue()) {
                        sslMode.setValue(PropertyDefinitions.SslMode.REQUIRED);
                    }
                }
            }
            for (final Object key : infoCopy.keySet()) {
                final String val = infoCopy.getProperty((String)key);
                final PropertyDefinition<String> def = new StringPropertyDefinition((String)key, null, val, true, Messages.getString("ConnectionProperties.unknown"), "8.0.10", PropertyDefinitions.CATEGORY_USER_DEFINED, Integer.MIN_VALUE);
                final RuntimeProperty<String> p = new StringProperty(def);
                this.addProperty(p);
            }
            this.postInitialization();
        }
    }
    
    @Override
    public void postInitialization() {
    }
    
    @Override
    public Properties exposeAsProperties() {
        final Properties props = new Properties();
        for (final PropertyKey propKey : this.PROPERTY_KEY_TO_RUNTIME_PROPERTY.keySet()) {
            if (!props.containsKey(propKey.getKeyName())) {
                final RuntimeProperty<?> propToGet = this.getProperty(propKey);
                final String propValue = propToGet.getStringValue();
                if (propValue == null) {
                    continue;
                }
                props.setProperty(propToGet.getPropertyDefinition().getName(), propValue);
            }
        }
        for (final String propName : this.PROPERTY_NAME_TO_RUNTIME_PROPERTY.keySet()) {
            if (!props.containsKey(propName)) {
                final RuntimeProperty<?> propToGet = this.getProperty(propName);
                final String propValue = propToGet.getStringValue();
                if (propValue == null) {
                    continue;
                }
                props.setProperty(propToGet.getPropertyDefinition().getName(), propValue);
            }
        }
        return props;
    }
    
    @Override
    public void reset() {
        this.PROPERTY_KEY_TO_RUNTIME_PROPERTY.values().forEach(p -> p.resetValue());
        this.PROPERTY_NAME_TO_RUNTIME_PROPERTY.values().forEach(p -> p.resetValue());
        this.postInitialization();
    }
}
