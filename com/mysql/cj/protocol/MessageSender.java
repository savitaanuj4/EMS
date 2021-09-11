
package com.mysql.cj.protocol;

import java.nio.channels.CompletionHandler;
import java.io.IOException;
import com.mysql.cj.exceptions.ExceptionFactory;
import com.mysql.cj.exceptions.CJOperationNotSupportedException;

public interface MessageSender<M extends Message>
{
    default void send(final byte[] message, final int messageLen, final byte messageSequence) throws IOException {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, "Not supported");
    }
    
    default void send(final M message) {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, "Not supported");
    }
    
    default void send(final M message, final CompletionHandler<Long, Void> callback) {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, "Not supported");
    }
    
    default void setMaxAllowedPacket(final int maxAllowedPacket) {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, "Not supported");
    }
    
    default MessageSender<M> undecorateAll() {
        return this;
    }
    
    default MessageSender<M> undecorate() {
        return this;
    }
}
