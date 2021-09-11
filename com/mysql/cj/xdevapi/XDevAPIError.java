
package com.mysql.cj.xdevapi;

import com.mysql.cj.exceptions.CJException;

public class XDevAPIError extends CJException
{
    private static final long serialVersionUID = 9102723045325569686L;
    
    public XDevAPIError(final String message) {
        super(message);
    }
    
    public XDevAPIError(final String message, final Throwable t) {
        super(message, t);
    }
}
