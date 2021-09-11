
package com.mysql.cj.exceptions;

public class UnsupportedConnectionStringException extends CJException
{
    private static final long serialVersionUID = 3991597077197801820L;
    
    public UnsupportedConnectionStringException() {
        this.setSQLState("S1009");
    }
    
    public UnsupportedConnectionStringException(final String message) {
        super(message);
        this.setSQLState("S1009");
    }
    
    public UnsupportedConnectionStringException(final String message, final Throwable cause) {
        super(message, cause);
        this.setSQLState("S1009");
    }
    
    public UnsupportedConnectionStringException(final Throwable cause) {
        super(cause);
        this.setSQLState("S1009");
    }
    
    public UnsupportedConnectionStringException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.setSQLState("S1009");
    }
}
