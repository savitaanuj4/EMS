
package com.mysql.cj.xdevapi;

import java.util.HashMap;

public class JsonString implements JsonValue
{
    static HashMap<Character, String> escapeChars;
    private String val;
    
    public JsonString() {
        this.val = "";
    }
    
    public String getString() {
        return this.val;
    }
    
    public JsonString setValue(final String value) {
        this.val = value;
        return this;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("\"");
        for (int i = 0; i < this.val.length(); ++i) {
            if (JsonString.escapeChars.containsKey(this.val.charAt(i))) {
                sb.append(JsonString.escapeChars.get(this.val.charAt(i)));
            }
            else {
                sb.append(this.val.charAt(i));
            }
        }
        sb.append("\"");
        return sb.toString();
    }
    
    static {
        JsonString.escapeChars = new HashMap<Character, String>();
        for (final JsonParser.EscapeChar ec : JsonParser.EscapeChar.values()) {
            JsonString.escapeChars.put(ec.CHAR, ec.ESCAPED);
        }
    }
}
