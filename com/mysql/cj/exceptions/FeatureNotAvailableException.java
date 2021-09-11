
package com.mysql.cj.exceptions;

public class FeatureNotAvailableException extends CJException
{
    private static final long serialVersionUID = -6649508222074639690L;
    
    public FeatureNotAvailableException() {
    }
    
    public FeatureNotAvailableException(final String message) {
        super(message);
    }
    
    public FeatureNotAvailableException(final String message, final Throwable cause) {
        super(message, cause);
    }
    
    public FeatureNotAvailableException(final Throwable cause) {
        super(cause);
    }
    
    public FeatureNotAvailableException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
