
package com.mysql.cj.conf;

import com.mysql.cj.exceptions.ExceptionFactory;
import com.mysql.cj.Messages;
import com.mysql.cj.exceptions.PropertyNotModifiableException;
import java.util.Iterator;
import java.util.ArrayList;
import javax.naming.RefAddr;
import javax.naming.Reference;
import com.mysql.cj.exceptions.ExceptionInterceptor;
import java.util.Properties;
import java.lang.ref.WeakReference;
import java.util.List;
import java.io.Serializable;

public abstract class AbstractRuntimeProperty<T> implements RuntimeProperty<T>, Serializable
{
    private static final long serialVersionUID = -3424722534876438236L;
    private PropertyDefinition<T> propertyDefinition;
    protected T value;
    protected T initialValue;
    protected boolean wasExplicitlySet;
    private List<WeakReference<RuntimePropertyListener>> listeners;
    
    public AbstractRuntimeProperty() {
        this.wasExplicitlySet = false;
    }
    
    protected AbstractRuntimeProperty(final PropertyDefinition<T> propertyDefinition) {
        this.wasExplicitlySet = false;
        this.propertyDefinition = propertyDefinition;
        this.value = propertyDefinition.getDefaultValue();
        this.initialValue = propertyDefinition.getDefaultValue();
    }
    
    @Override
    public PropertyDefinition<T> getPropertyDefinition() {
        return this.propertyDefinition;
    }
    
    @Override
    public void initializeFrom(final Properties extractFrom, final ExceptionInterceptor exceptionInterceptor) {
        final String name = this.getPropertyDefinition().getName();
        if (extractFrom.containsKey(name)) {
            final String extractedValue = (String)extractFrom.remove(name);
            if (extractedValue != null) {
                this.setValueInternal(extractedValue, exceptionInterceptor);
                this.initialValue = this.value;
            }
        }
    }
    
    @Override
    public void initializeFrom(final Reference ref, final ExceptionInterceptor exceptionInterceptor) {
        final RefAddr refAddr = ref.get(this.getPropertyDefinition().getName());
        if (refAddr != null) {
            final String refContentAsString = (String)refAddr.getContent();
            if (refContentAsString != null) {
                this.setValueInternal(refContentAsString, exceptionInterceptor);
                this.initialValue = this.value;
            }
        }
    }
    
    @Override
    public void resetValue() {
        this.value = this.initialValue;
        this.invokeListeners();
    }
    
    @Override
    public boolean isExplicitlySet() {
        return this.wasExplicitlySet;
    }
    
    @Override
    public void addListener(final RuntimePropertyListener l) {
        if (this.listeners == null) {
            this.listeners = new ArrayList<WeakReference<RuntimePropertyListener>>();
        }
        boolean found = false;
        for (final WeakReference<RuntimePropertyListener> weakReference : this.listeners) {
            if (l.equals(weakReference.get())) {
                found = true;
                break;
            }
        }
        if (!found) {
            this.listeners.add(new WeakReference<RuntimePropertyListener>(l));
        }
    }
    
    @Override
    public void removeListener(final RuntimePropertyListener listener) {
        if (this.listeners != null) {
            for (final WeakReference<RuntimePropertyListener> wr : this.listeners) {
                final RuntimePropertyListener l = wr.get();
                if (l.equals(listener)) {
                    this.listeners.remove(wr);
                    break;
                }
            }
        }
    }
    
    protected void invokeListeners() {
        if (this.listeners != null) {
            for (final WeakReference<RuntimePropertyListener> wr : this.listeners) {
                final RuntimePropertyListener l = wr.get();
                if (l != null) {
                    l.handlePropertyChange(this);
                }
                else {
                    this.listeners.remove(wr);
                }
            }
        }
    }
    
    @Override
    public T getValue() {
        return this.value;
    }
    
    @Override
    public T getInitialValue() {
        return this.initialValue;
    }
    
    @Override
    public String getStringValue() {
        return (this.value == null) ? null : this.value.toString();
    }
    
    public void setValueInternal(final String value, final ExceptionInterceptor exceptionInterceptor) {
        this.setValueInternal(this.getPropertyDefinition().parseObject(value, exceptionInterceptor), value, exceptionInterceptor);
    }
    
    public void setValueInternal(final T value, final String valueAsString, final ExceptionInterceptor exceptionInterceptor) {
        if (this.getPropertyDefinition().isRangeBased()) {
            this.checkRange(value, valueAsString, exceptionInterceptor);
        }
        this.value = value;
        this.wasExplicitlySet = true;
    }
    
    protected void checkRange(final T val, final String valueAsString, final ExceptionInterceptor exceptionInterceptor) {
    }
    
    @Override
    public void setValue(final T value) {
        this.setValue(value, null);
    }
    
    @Override
    public void setValue(final T value, final ExceptionInterceptor exceptionInterceptor) {
        if (this.getPropertyDefinition().isRuntimeModifiable()) {
            this.setValueInternal(value, null, exceptionInterceptor);
            this.invokeListeners();
            return;
        }
        throw ExceptionFactory.createException(PropertyNotModifiableException.class, Messages.getString("ConnectionProperties.dynamicChangeIsNotAllowed", new Object[] { "'" + this.getPropertyDefinition().getName() + "'" }));
    }
}
