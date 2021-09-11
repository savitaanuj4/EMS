
package com.mysql.cj.xdevapi;

import java.math.BigDecimal;

public class JsonNumber implements JsonValue
{
    private String val;
    
    public JsonNumber() {
        this.val = "null";
    }
    
    public Integer getInteger() {
        return new BigDecimal(this.val).intValue();
    }
    
    public BigDecimal getBigDecimal() {
        return new BigDecimal(this.val);
    }
    
    public JsonNumber setValue(final String value) {
        this.val = new BigDecimal(value).toString();
        return this;
    }
    
    @Override
    public String toString() {
        return this.val;
    }
}
