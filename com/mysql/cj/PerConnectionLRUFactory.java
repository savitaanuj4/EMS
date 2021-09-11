
package com.mysql.cj;

import java.util.Iterator;
import java.util.Set;
import com.mysql.cj.util.LRUCache;

public class PerConnectionLRUFactory implements CacheAdapterFactory<String, ParseInfo>
{
    @Override
    public CacheAdapter<String, ParseInfo> getInstance(final Object syncMutex, final String url, final int cacheMaxSize, final int maxKeySize) {
        return new PerConnectionLRU(syncMutex, cacheMaxSize, maxKeySize);
    }
    
    class PerConnectionLRU implements CacheAdapter<String, ParseInfo>
    {
        private final int cacheSqlLimit;
        private final LRUCache<String, ParseInfo> cache;
        private final Object syncMutex;
        
        protected PerConnectionLRU(final Object syncMutex, final int cacheMaxSize, final int maxKeySize) {
            final int cacheSize = cacheMaxSize;
            this.cacheSqlLimit = maxKeySize;
            this.cache = new LRUCache<String, ParseInfo>(cacheSize);
            this.syncMutex = syncMutex;
        }
        
        @Override
        public ParseInfo get(final String key) {
            if (key == null || key.length() > this.cacheSqlLimit) {
                return null;
            }
            synchronized (this.syncMutex) {
                return this.cache.get(key);
            }
        }
        
        @Override
        public void put(final String key, final ParseInfo value) {
            if (key == null || key.length() > this.cacheSqlLimit) {
                return;
            }
            synchronized (this.syncMutex) {
                this.cache.put(key, value);
            }
        }
        
        @Override
        public void invalidate(final String key) {
            synchronized (this.syncMutex) {
                this.cache.remove(key);
            }
        }
        
        @Override
        public void invalidateAll(final Set<String> keys) {
            synchronized (this.syncMutex) {
                for (final String key : keys) {
                    this.cache.remove(key);
                }
            }
        }
        
        @Override
        public void invalidateAll() {
            synchronized (this.syncMutex) {
                this.cache.clear();
            }
        }
    }
}
