
package com.mysql.cj.protocol.x;

import java.util.concurrent.CompletableFuture;
import java.nio.channels.CompletionHandler;

public class ErrorToFutureCompletionHandler<T> implements CompletionHandler<T, Void>
{
    private CompletableFuture<?> future;
    private Runnable successCallback;
    
    public ErrorToFutureCompletionHandler(final CompletableFuture<?> future, final Runnable successCallback) {
        this.future = future;
        this.successCallback = successCallback;
    }
    
    @Override
    public void completed(final T result, final Void attachment) {
        this.successCallback.run();
    }
    
    @Override
    public void failed(final Throwable ex, final Void attachment) {
        this.future.completeExceptionally(ex);
    }
}
