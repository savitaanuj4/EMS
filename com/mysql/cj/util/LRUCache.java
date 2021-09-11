
package com.mysql.cj.util;

import java.util.Map;
import java.util.LinkedHashMap;

public class LRUCache<K, V> extends LinkedHashMap<K, V>
{
    private static final long serialVersionUID = 1L;
    protected int maxElements;
    
    public LRUCache(final int maxSize) {
        super(maxSize, 0.75f, true);
        this.maxElements = maxSize;
    }
    
    @Override
    protected boolean removeEldestEntry(final Map.Entry<K, V> eldest) {
        return this.size() > this.maxElements;
    }
}
