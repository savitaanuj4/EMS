
package com.mysql.cj.jdbc.interceptors;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.mysql.cj.exceptions.ExceptionFactory;
import com.mysql.cj.protocol.Resultset;
import com.mysql.cj.protocol.ServerSession;
import com.mysql.cj.Query;
import java.util.function.Supplier;
import com.mysql.cj.log.Log;
import java.util.Properties;
import com.mysql.cj.MysqlConnection;
import com.mysql.cj.jdbc.JdbcConnection;
import com.mysql.cj.interceptors.QueryInterceptor;

public class SessionAssociationInterceptor implements QueryInterceptor
{
    protected String currentSessionKey;
    protected static final ThreadLocal<String> sessionLocal;
    private JdbcConnection connection;
    
    public static final void setSessionKey(final String key) {
        SessionAssociationInterceptor.sessionLocal.set(key);
    }
    
    public static final void resetSessionKey() {
        SessionAssociationInterceptor.sessionLocal.set(null);
    }
    
    public static final String getSessionKey() {
        return SessionAssociationInterceptor.sessionLocal.get();
    }
    
    @Override
    public boolean executeTopLevelOnly() {
        return true;
    }
    
    @Override
    public QueryInterceptor init(final MysqlConnection conn, final Properties props, final Log log) {
        this.connection = (JdbcConnection)conn;
        return this;
    }
    
    @Override
    public <T extends Resultset> T postProcess(final Supplier<String> sql, final Query interceptedQuery, final T originalResultSet, final ServerSession serverSession) {
        return null;
    }
    
    @Override
    public <T extends Resultset> T preProcess(final Supplier<String> sql, final Query interceptedQuery) {
        final String key = getSessionKey();
        if (key != null && !key.equals(this.currentSessionKey)) {
            try {
                final PreparedStatement pstmt = this.connection.clientPrepareStatement("SET @mysql_proxy_session=?");
                try {
                    pstmt.setString(1, key);
                    pstmt.execute();
                }
                finally {
                    pstmt.close();
                }
            }
            catch (SQLException ex) {
                throw ExceptionFactory.createException(ex.getMessage(), ex);
            }
            this.currentSessionKey = key;
        }
        return null;
    }
    
    @Override
    public void destroy() {
        this.connection = null;
    }
    
    static {
        sessionLocal = new ThreadLocal<String>();
    }
}
