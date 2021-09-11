
package com.mysql.cj.conf;

import com.mysql.cj.util.StringUtils;
import com.mysql.cj.exceptions.ExceptionInterceptor;

public class MemorySizePropertyDefinition extends IntegerPropertyDefinition
{
    private static final long serialVersionUID = -6878680905514177949L;
    
    public MemorySizePropertyDefinition(final PropertyKey key, final int defaultValue, final boolean isRuntimeModifiable, final String description, final String sinceVersion, final String category, final int orderInCategory) {
        super(key, defaultValue, isRuntimeModifiable, description, sinceVersion, category, orderInCategory);
    }
    
    public MemorySizePropertyDefinition(final PropertyKey key, final int defaultValue, final boolean isRuntimeModifiable, final String description, final String sinceVersion, final String category, final int orderInCategory, final int lowerBound, final int upperBound) {
        super(key, defaultValue, isRuntimeModifiable, description, sinceVersion, category, orderInCategory, lowerBound, upperBound);
    }
    
    @Override
    public Integer parseObject(String value, final ExceptionInterceptor exceptionInterceptor) {
        this.multiplier = 1;
        if (value.endsWith("k") || value.endsWith("K") || value.endsWith("kb") || value.endsWith("Kb") || value.endsWith("kB") || value.endsWith("KB")) {
            this.multiplier = 1024;
            final int indexOfK = StringUtils.indexOfIgnoreCase(value, "k");
            value = value.substring(0, indexOfK);
        }
        else if (value.endsWith("m") || value.endsWith("M") || value.endsWith("mb") || value.endsWith("Mb") || value.endsWith("mB") || value.endsWith("MB")) {
            this.multiplier = 1048576;
            final int indexOfM = StringUtils.indexOfIgnoreCase(value, "m");
            value = value.substring(0, indexOfM);
        }
        else if (value.endsWith("g") || value.endsWith("G") || value.endsWith("gb") || value.endsWith("Gb") || value.endsWith("gB") || value.endsWith("GB")) {
            this.multiplier = 1073741824;
            final int indexOfG = StringUtils.indexOfIgnoreCase(value, "g");
            value = value.substring(0, indexOfG);
        }
        return super.parseObject(value, exceptionInterceptor);
    }
    
    @Override
    public RuntimeProperty<Integer> createRuntimeProperty() {
        return new MemorySizeProperty(this);
    }
}
