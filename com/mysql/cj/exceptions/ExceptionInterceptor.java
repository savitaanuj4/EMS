
package com.mysql.cj.exceptions;

import com.mysql.cj.log.Log;
import java.util.Properties;

public interface ExceptionInterceptor
{
    ExceptionInterceptor init(final Properties p0, final Log p1);
    
    void destroy();
    
    Exception interceptException(final Exception p0);
}
