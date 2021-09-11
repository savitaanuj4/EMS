
package com.mysql.cj.xdevapi;

import java.util.Iterator;
import java.util.ArrayList;

public class JsonArray extends ArrayList<JsonValue> implements JsonValue
{
    private static final long serialVersionUID = 6557406141541247905L;
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("[");
        for (final JsonValue val : this) {
            if (sb.length() > 1) {
                sb.append(",");
            }
            sb.append(val.toString());
        }
        sb.append("]");
        return sb.toString();
    }
    
    @Override
    public String toFormattedString() {
        final StringBuilder sb = new StringBuilder("[");
        for (final JsonValue val : this) {
            if (sb.length() > 1) {
                sb.append(", ");
            }
            sb.append(val.toFormattedString());
        }
        sb.append("]");
        return sb.toString();
    }
    
    public JsonArray addValue(final JsonValue val) {
        this.add(val);
        return this;
    }
}
