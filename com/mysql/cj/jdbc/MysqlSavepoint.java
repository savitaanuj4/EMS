
package com.mysql.cj.jdbc;

import com.mysql.cj.exceptions.CJException;
import com.mysql.cj.jdbc.exceptions.SQLExceptionsMapping;
import com.mysql.cj.jdbc.exceptions.SQLError;
import com.mysql.cj.Messages;
import java.sql.SQLException;
import com.mysql.cj.util.StringUtils;
import com.mysql.cj.exceptions.ExceptionInterceptor;
import java.sql.Savepoint;

public class MysqlSavepoint implements Savepoint
{
    private String savepointName;
    private ExceptionInterceptor exceptionInterceptor;
    
    MysqlSavepoint(final ExceptionInterceptor exceptionInterceptor) throws SQLException {
        this(StringUtils.getUniqueSavepointId(), exceptionInterceptor);
    }
    
    MysqlSavepoint(final String name, final ExceptionInterceptor exceptionInterceptor) throws SQLException {
        if (name == null || name.length() == 0) {
            throw SQLError.createSQLException(Messages.getString("MysqlSavepoint.0"), "S1009", exceptionInterceptor);
        }
        this.savepointName = name;
        this.exceptionInterceptor = exceptionInterceptor;
    }
    
    @Override
    public int getSavepointId() throws SQLException {
        try {
            throw SQLError.createSQLException(Messages.getString("MysqlSavepoint.1"), "S1C00", this.exceptionInterceptor);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.exceptionInterceptor);
        }
    }
    
    @Override
    public String getSavepointName() throws SQLException {
        try {
            return this.savepointName;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.exceptionInterceptor);
        }
    }
}
