
package com.mysql.cj.xdevapi;

import java.util.Iterator;
import java.util.TreeMap;

public class DbDocImpl extends TreeMap<String, JsonValue> implements DbDoc
{
    private static final long serialVersionUID = 6557406141541247905L;
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        for (final String key : ((TreeMap<String, V>)this).keySet()) {
            if (sb.length() > 1) {
                sb.append(",");
            }
            sb.append("\"").append(key).append("\":").append(((TreeMap<K, JsonValue>)this).get(key).toString());
        }
        sb.append("}");
        return sb.toString();
    }
    
    @Override
    public String toFormattedString() {
        final StringBuilder sb = new StringBuilder("{");
        for (final String key : ((TreeMap<String, V>)this).keySet()) {
            if (sb.length() > 1) {
                sb.append(",");
            }
            sb.append("\n\"").append(key).append("\" : ").append(((TreeMap<K, JsonValue>)this).get(key).toFormattedString());
        }
        if (this.size() > 0) {
            sb.append("\n");
        }
        sb.append("}");
        return sb.toString();
    }
    
    @Override
    public DbDoc add(final String key, final JsonValue val) {
        this.put(key, val);
        return this;
    }
}
