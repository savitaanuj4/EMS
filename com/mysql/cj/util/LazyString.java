
package com.mysql.cj.util;

import java.util.function.Supplier;

public class LazyString implements Supplier<String>
{
    private String string;
    private byte[] buffer;
    private int offset;
    private int length;
    private String encoding;
    
    public LazyString(final String string) {
        this.string = string;
    }
    
    public LazyString(final byte[] buffer, final int offset, final int length, final String encoding) {
        this.buffer = buffer;
        this.offset = offset;
        this.length = length;
        this.encoding = encoding;
    }
    
    public LazyString(final byte[] buffer, final int offset, final int length) {
        this.buffer = buffer;
        this.offset = offset;
        this.length = length;
    }
    
    private String createAndCacheString() {
        if (this.length > 0) {
            this.string = ((this.encoding == null) ? StringUtils.toString(this.buffer, this.offset, this.length) : StringUtils.toString(this.buffer, this.offset, this.length, this.encoding));
        }
        return this.string;
    }
    
    @Override
    public String toString() {
        if (this.string != null) {
            return this.string;
        }
        return this.createAndCacheString();
    }
    
    public int length() {
        if (this.string != null) {
            return this.string.length();
        }
        return this.length;
    }
    
    @Override
    public String get() {
        return this.toString();
    }
}
