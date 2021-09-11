
package com.mysql.cj.exceptions;

public class PropertyNotModifiableException extends CJException
{
    private static final long serialVersionUID = -8001652264426656450L;
    
    public PropertyNotModifiableException() {
    }
    
    public PropertyNotModifiableException(final String message) {
        super(message);
    }
    
    public PropertyNotModifiableException(final String message, final Throwable cause) {
        super(message, cause);
    }
    
    public PropertyNotModifiableException(final Throwable cause) {
        super(cause);
    }
    
    protected PropertyNotModifiableException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
