
package com.mysql.cj.jdbc.admin;

import com.mysql.cj.jdbc.Driver;
import java.util.Properties;
import java.sql.SQLException;
import com.mysql.cj.jdbc.ConnectionImpl;
import com.mysql.cj.exceptions.ExceptionInterceptor;
import com.mysql.cj.jdbc.exceptions.SQLError;
import com.mysql.cj.Messages;
import java.sql.Connection;
import com.mysql.cj.jdbc.JdbcConnection;

public class MiniAdmin
{
    private JdbcConnection conn;
    
    public MiniAdmin(final Connection conn) throws SQLException {
        if (conn == null) {
            throw SQLError.createSQLException(Messages.getString("MiniAdmin.0"), "S1000", null);
        }
        if (!(conn instanceof JdbcConnection)) {
            throw SQLError.createSQLException(Messages.getString("MiniAdmin.1"), "S1000", ((ConnectionImpl)conn).getExceptionInterceptor());
        }
        this.conn = (JdbcConnection)conn;
    }
    
    public MiniAdmin(final String jdbcUrl) throws SQLException {
        this(jdbcUrl, new Properties());
    }
    
    public MiniAdmin(final String jdbcUrl, final Properties props) throws SQLException {
        this.conn = (JdbcConnection)new Driver().connect(jdbcUrl, props);
    }
    
    public void shutdown() throws SQLException {
        this.conn.shutdownServer();
    }
}
