
package com.mysql.cj.exceptions;

public class StatementIsClosedException extends CJException
{
    private static final long serialVersionUID = -4214028635985851906L;
    
    public StatementIsClosedException() {
        this.setSQLState("S1009");
    }
    
    public StatementIsClosedException(final String message) {
        super(message);
        this.setSQLState("S1009");
    }
    
    public StatementIsClosedException(final String message, final Throwable cause) {
        super(message, cause);
        this.setSQLState("S1009");
    }
    
    public StatementIsClosedException(final Throwable cause) {
        super(cause);
        this.setSQLState("S1009");
    }
    
    protected StatementIsClosedException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.setSQLState("S1009");
    }
}
