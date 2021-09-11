
package com.mysql.cj.protocol;

import com.mysql.cj.exceptions.ExceptionFactory;
import com.mysql.cj.exceptions.CJOperationNotSupportedException;

public interface ProtocolEntityFactory<T, M extends Message>
{
    default T createFromMessage(final M message) {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, "Not allowed");
    }
    
    default Resultset.Type getResultSetType() {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, "Not allowed");
    }
    
    default Resultset.Concurrency getResultSetConcurrency() {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, "Not allowed");
    }
    
    default int getFetchSize() {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, "Not allowed");
    }
    
    default T createFromProtocolEntity(final ProtocolEntity protocolEntity) {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, "Not allowed");
    }
}
