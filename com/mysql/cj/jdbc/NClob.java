
package com.mysql.cj.jdbc;

import com.mysql.cj.exceptions.ExceptionInterceptor;

public class NClob extends Clob implements java.sql.NClob
{
    NClob(final ExceptionInterceptor exceptionInterceptor) {
        super(exceptionInterceptor);
    }
    
    public NClob(final String charDataInit, final ExceptionInterceptor exceptionInterceptor) {
        super(charDataInit, exceptionInterceptor);
    }
}
