
package com.mysql.cj.xdevapi;

public enum JsonLiteral implements JsonValue
{
    TRUE("true"), 
    FALSE("false"), 
    NULL("null");
    
    public final String value;
    
    private JsonLiteral(final String val) {
        this.value = val;
    }
    
    @Override
    public String toString() {
        return this.value;
    }
}
