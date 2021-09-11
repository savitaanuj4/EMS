
package com.mysql.cj.conf;

import com.mysql.cj.exceptions.ExceptionInterceptor;
import java.io.Serializable;

public abstract class AbstractPropertyDefinition<T> implements PropertyDefinition<T>, Serializable
{
    private static final long serialVersionUID = 2696624840927848766L;
    private PropertyKey key;
    private String name;
    private String ccAlias;
    private T defaultValue;
    private boolean isRuntimeModifiable;
    private String description;
    private String sinceVersion;
    private String category;
    private int order;
    private int lowerBound;
    private int upperBound;
    
    public AbstractPropertyDefinition(final String name, final String camelCaseAlias, final T defaultValue, final boolean isRuntimeModifiable, final String description, final String sinceVersion, final String category, final int orderInCategory) {
        this.key = null;
        this.name = name;
        this.ccAlias = camelCaseAlias;
        this.setDefaultValue(defaultValue);
        this.setRuntimeModifiable(isRuntimeModifiable);
        this.setDescription(description);
        this.setSinceVersion(sinceVersion);
        this.setCategory(category);
        this.setOrder(orderInCategory);
    }
    
    public AbstractPropertyDefinition(final PropertyKey key, final T defaultValue, final boolean isRuntimeModifiable, final String description, final String sinceVersion, final String category, final int orderInCategory) {
        this.key = null;
        this.key = key;
        this.name = key.getKeyName();
        this.ccAlias = key.getCcAlias();
        this.setDefaultValue(defaultValue);
        this.setRuntimeModifiable(isRuntimeModifiable);
        this.setDescription(description);
        this.setSinceVersion(sinceVersion);
        this.setCategory(category);
        this.setOrder(orderInCategory);
    }
    
    public AbstractPropertyDefinition(final PropertyKey key, final T defaultValue, final boolean isRuntimeModifiable, final String description, final String sinceVersion, final String category, final int orderInCategory, final int lowerBound, final int upperBound) {
        this(key, defaultValue, isRuntimeModifiable, description, sinceVersion, category, orderInCategory);
        this.setLowerBound(lowerBound);
        this.setUpperBound(upperBound);
    }
    
    @Override
    public boolean hasValueConstraints() {
        return this.getAllowableValues() != null && this.getAllowableValues().length > 0;
    }
    
    @Override
    public boolean isRangeBased() {
        return false;
    }
    
    @Override
    public PropertyKey getPropertyKey() {
        return this.key;
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    @Override
    public String getCcAlias() {
        return this.ccAlias;
    }
    
    @Override
    public boolean hasCcAlias() {
        return this.ccAlias != null && this.ccAlias.length() > 0;
    }
    
    @Override
    public T getDefaultValue() {
        return this.defaultValue;
    }
    
    public void setDefaultValue(final T defaultValue) {
        this.defaultValue = defaultValue;
    }
    
    @Override
    public boolean isRuntimeModifiable() {
        return this.isRuntimeModifiable;
    }
    
    public void setRuntimeModifiable(final boolean isRuntimeModifiable) {
        this.isRuntimeModifiable = isRuntimeModifiable;
    }
    
    @Override
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(final String description) {
        this.description = description;
    }
    
    @Override
    public String getSinceVersion() {
        return this.sinceVersion;
    }
    
    public void setSinceVersion(final String sinceVersion) {
        this.sinceVersion = sinceVersion;
    }
    
    @Override
    public String getCategory() {
        return this.category;
    }
    
    public void setCategory(final String category) {
        this.category = category;
    }
    
    @Override
    public int getOrder() {
        return this.order;
    }
    
    public void setOrder(final int order) {
        this.order = order;
    }
    
    @Override
    public String[] getAllowableValues() {
        return null;
    }
    
    @Override
    public int getLowerBound() {
        return this.lowerBound;
    }
    
    public void setLowerBound(final int lowerBound) {
        this.lowerBound = lowerBound;
    }
    
    @Override
    public int getUpperBound() {
        return this.upperBound;
    }
    
    public void setUpperBound(final int upperBound) {
        this.upperBound = upperBound;
    }
    
    @Override
    public abstract T parseObject(final String p0, final ExceptionInterceptor p1);
}
