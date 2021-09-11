
package com.mysql.cj.xdevapi;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface Statement<STMT_T, RES_T>
{
    RES_T execute();
    
    CompletableFuture<RES_T> executeAsync();
    
    default STMT_T clearBindings() {
        throw new UnsupportedOperationException("This statement doesn't support bound parameters");
    }
    
    default STMT_T bind(final String argName, final Object value) {
        throw new UnsupportedOperationException("This statement doesn't support bound parameters");
    }
    
    default STMT_T bind(final Map<String, Object> values) {
        this.clearBindings();
        values.entrySet().forEach(e -> this.bind(e.getKey(), e.getValue()));
        return (STMT_T)this;
    }
    
    default STMT_T bind(final List<Object> values) {
        this.clearBindings();
        IntStream.range(0, values.size()).forEach(i -> this.bind(String.valueOf(i), values.get(i)));
        return (STMT_T)this;
    }
    
    default STMT_T bind(final Object... values) {
        return this.bind(Arrays.asList(values));
    }
    
    public enum LockContention
    {
        DEFAULT, 
        NOWAIT, 
        SKIP_LOCKED;
    }
}
