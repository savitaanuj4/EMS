
package com.mysql.cj.jdbc.exceptions;

import com.mysql.cj.Messages;
import java.sql.SQLNonTransientException;

public class MySQLStatementCancelledException extends SQLNonTransientException
{
    static final long serialVersionUID = -8762717748377197378L;
    
    public MySQLStatementCancelledException(final String reason, final String SQLState, final int vendorCode) {
        super(reason, SQLState, vendorCode);
    }
    
    public MySQLStatementCancelledException(final String reason, final String SQLState) {
        super(reason, SQLState);
    }
    
    public MySQLStatementCancelledException(final String reason) {
        super(reason);
    }
    
    public MySQLStatementCancelledException() {
        super(Messages.getString("MySQLStatementCancelledException.0"));
    }
}
