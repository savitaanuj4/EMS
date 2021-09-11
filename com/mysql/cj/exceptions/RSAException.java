
package com.mysql.cj.exceptions;

public class RSAException extends CJException
{
    private static final long serialVersionUID = -1878681511263159173L;
    
    public RSAException() {
    }
    
    public RSAException(final String message) {
        super(message);
    }
    
    public RSAException(final String message, final Throwable cause) {
        super(message, cause);
    }
    
    public RSAException(final Throwable cause) {
        super(cause);
    }
    
    protected RSAException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
