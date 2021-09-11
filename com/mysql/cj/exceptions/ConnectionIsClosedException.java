
package com.mysql.cj.exceptions;

public class ConnectionIsClosedException extends CJException
{
    private static final long serialVersionUID = -8001652264426656450L;
    
    public ConnectionIsClosedException() {
        this.setSQLState("08003");
    }
    
    public ConnectionIsClosedException(final String message) {
        super(message);
        this.setSQLState("08003");
    }
    
    public ConnectionIsClosedException(final String message, final Throwable cause) {
        super(message, cause);
        this.setSQLState("08003");
    }
    
    public ConnectionIsClosedException(final Throwable cause) {
        super(cause);
        this.setSQLState("08003");
    }
    
    protected ConnectionIsClosedException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.setSQLState("08003");
    }
}
