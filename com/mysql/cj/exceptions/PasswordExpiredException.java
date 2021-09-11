
package com.mysql.cj.exceptions;

public class PasswordExpiredException extends CJException
{
    private static final long serialVersionUID = -3807215681364413250L;
    
    public PasswordExpiredException() {
        this.setVendorCode(1820);
    }
    
    public PasswordExpiredException(final String message) {
        super(message);
        this.setVendorCode(1820);
    }
    
    public PasswordExpiredException(final String message, final Throwable cause) {
        super(message, cause);
        this.setVendorCode(1820);
    }
    
    public PasswordExpiredException(final Throwable cause) {
        super(cause);
        this.setVendorCode(1820);
    }
    
    protected PasswordExpiredException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.setVendorCode(1820);
    }
}
