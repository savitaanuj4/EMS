
package com.mysql.cj.jdbc.integration.c3p0;

import java.sql.SQLException;
import com.mysql.cj.exceptions.CJCommunicationsException;
import com.mysql.cj.jdbc.exceptions.CommunicationsException;
import java.sql.Statement;
import com.mchange.v2.c3p0.C3P0ProxyConnection;
import java.sql.Connection;
import com.mysql.cj.jdbc.JdbcConnection;
import java.lang.reflect.Method;
import com.mchange.v2.c3p0.QueryConnectionTester;

public final class MysqlConnectionTester implements QueryConnectionTester
{
    private static final long serialVersionUID = 3256444690067896368L;
    private static final Object[] NO_ARGS_ARRAY;
    private transient Method pingMethod;
    
    public MysqlConnectionTester() {
        try {
            this.pingMethod = JdbcConnection.class.getMethod("ping", (Class<?>[])null);
        }
        catch (Exception ex) {}
    }
    
    public int activeCheckConnection(final Connection con) {
        try {
            if (this.pingMethod != null) {
                if (con instanceof JdbcConnection) {
                    ((JdbcConnection)con).ping();
                }
                else {
                    final C3P0ProxyConnection castCon = (C3P0ProxyConnection)con;
                    castCon.rawConnectionOperation(this.pingMethod, C3P0ProxyConnection.RAW_CONNECTION, MysqlConnectionTester.NO_ARGS_ARRAY);
                }
            }
            else {
                Statement pingStatement = null;
                try {
                    pingStatement = con.createStatement();
                    pingStatement.executeQuery("SELECT 1").close();
                }
                finally {
                    if (pingStatement != null) {
                        pingStatement.close();
                    }
                }
            }
            return 0;
        }
        catch (Exception ex) {
            return -1;
        }
    }
    
    public int statusOnException(final Connection arg0, final Throwable throwable) {
        if (throwable instanceof CommunicationsException || throwable instanceof CJCommunicationsException) {
            return -1;
        }
        if (!(throwable instanceof SQLException)) {
            return -1;
        }
        final String sqlState = ((SQLException)throwable).getSQLState();
        if (sqlState != null && sqlState.startsWith("08")) {
            return -1;
        }
        return 0;
    }
    
    public int activeCheckConnection(final Connection arg0, final String arg1) {
        return 0;
    }
    
    static {
        NO_ARGS_ARRAY = new Object[0];
    }
}
