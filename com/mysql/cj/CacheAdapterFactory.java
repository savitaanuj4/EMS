
package com.mysql.cj;

public interface CacheAdapterFactory<K, V>
{
    CacheAdapter<K, V> getInstance(final Object p0, final String p1, final int p2, final int p3);
}
