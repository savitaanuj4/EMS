
package com.mysql.cj.jdbc.exceptions;

import java.sql.SQLNonTransientException;

public class MySQLQueryInterruptedException extends SQLNonTransientException
{
    private static final long serialVersionUID = -8714521137662613517L;
    
    public MySQLQueryInterruptedException() {
    }
    
    public MySQLQueryInterruptedException(final String reason, final String SQLState, final int vendorCode) {
        super(reason, SQLState, vendorCode);
    }
    
    public MySQLQueryInterruptedException(final String reason, final String SQLState) {
        super(reason, SQLState);
    }
    
    public MySQLQueryInterruptedException(final String reason) {
        super(reason);
    }
}
