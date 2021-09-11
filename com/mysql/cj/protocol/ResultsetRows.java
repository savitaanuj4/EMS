
package com.mysql.cj.protocol;

import com.mysql.cj.exceptions.ExceptionFactory;
import com.mysql.cj.Messages;
import com.mysql.cj.exceptions.CJOperationNotSupportedException;
import com.mysql.cj.result.Row;
import com.mysql.cj.result.RowList;

public interface ResultsetRows extends RowList, ProtocolEntity
{
    default void addRow(final Row row) {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, Messages.getString("OperationNotSupportedException.0"));
    }
    
    default void afterLast() {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, Messages.getString("OperationNotSupportedException.0"));
    }
    
    default void beforeFirst() {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, Messages.getString("OperationNotSupportedException.0"));
    }
    
    default void beforeLast() {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, Messages.getString("OperationNotSupportedException.0"));
    }
    
    default void close() {
    }
    
    ResultsetRowsOwner getOwner();
    
    boolean isAfterLast();
    
    boolean isBeforeFirst();
    
    default boolean isDynamic() {
        return true;
    }
    
    default boolean isEmpty() {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, Messages.getString("OperationNotSupportedException.0"));
    }
    
    default boolean isFirst() {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, Messages.getString("OperationNotSupportedException.0"));
    }
    
    default boolean isLast() {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, Messages.getString("OperationNotSupportedException.0"));
    }
    
    default void moveRowRelative(final int rows) {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, Messages.getString("OperationNotSupportedException.0"));
    }
    
    default void setCurrentRow(final int rowNumber) {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, Messages.getString("OperationNotSupportedException.0"));
    }
    
    void setOwner(final ResultsetRowsOwner p0);
    
    boolean wasEmpty();
    
    void setMetadata(final ColumnDefinition p0);
    
    ColumnDefinition getMetadata();
}
