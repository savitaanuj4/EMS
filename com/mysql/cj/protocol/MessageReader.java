
package com.mysql.cj.protocol;

import com.mysql.cj.exceptions.ExceptionFactory;
import com.mysql.cj.exceptions.CJOperationNotSupportedException;
import java.util.Optional;
import java.io.IOException;

public interface MessageReader<H extends MessageHeader, M extends Message>
{
    H readHeader() throws IOException;
    
    M readMessage(final Optional<M> p0, final H p1) throws IOException;
    
    default M readMessage(final Optional<M> reuse, final int expectedType) throws IOException {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, "Not supported");
    }
    
    default void pushMessageListener(final MessageListener<M> l) {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, "Not supported");
    }
    
    default byte getMessageSequence() {
        return 0;
    }
    
    default void resetMessageSequence() {
    }
    
    default MessageReader<H, M> undecorateAll() {
        return this;
    }
    
    default MessageReader<H, M> undecorate() {
        return this;
    }
    
    default void start() {
    }
    
    default void stopAfterNextMessage() {
    }
}
