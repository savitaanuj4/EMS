
package com.mysql.cj.exceptions;

import com.mysql.cj.Messages;

public class CJTimeoutException extends CJException
{
    private static final long serialVersionUID = -7440108828056331100L;
    
    public CJTimeoutException() {
        super(Messages.getString("MySQLTimeoutException.0"));
    }
    
    public CJTimeoutException(final String message) {
        super(message);
    }
    
    public CJTimeoutException(final Throwable cause) {
        super(cause);
    }
    
    public CJTimeoutException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
