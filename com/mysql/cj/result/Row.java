
package com.mysql.cj.result;

import com.mysql.cj.exceptions.ExceptionFactory;
import com.mysql.cj.Messages;
import com.mysql.cj.exceptions.CJOperationNotSupportedException;
import com.mysql.cj.protocol.ColumnDefinition;
import com.mysql.cj.protocol.ProtocolEntity;

public interface Row extends ProtocolEntity
{
     <T> T getValue(final int p0, final ValueFactory<T> p1);
    
    default Row setMetadata(final ColumnDefinition columnDefinition) {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, Messages.getString("OperationNotSupportedException.0"));
    }
    
    default byte[] getBytes(final int columnIndex) {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, Messages.getString("OperationNotSupportedException.0"));
    }
    
    default void setBytes(final int columnIndex, final byte[] value) {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, Messages.getString("OperationNotSupportedException.0"));
    }
    
    boolean getNull(final int p0);
    
    boolean wasNull();
}
