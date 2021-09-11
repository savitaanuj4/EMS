
package com.mysql.cj.jdbc.interceptors;

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import com.mysql.cj.exceptions.ExceptionFactory;
import com.mysql.cj.jdbc.util.ResultSetUtil;
import com.mysql.cj.util.Util;
import com.mysql.cj.protocol.Resultset;
import com.mysql.cj.protocol.ServerSession;
import com.mysql.cj.Query;
import java.util.function.Supplier;
import java.util.Properties;
import com.mysql.cj.MysqlConnection;
import java.util.HashMap;
import com.mysql.cj.log.Log;
import com.mysql.cj.jdbc.JdbcConnection;
import java.util.Map;
import com.mysql.cj.interceptors.QueryInterceptor;

public class ServerStatusDiffInterceptor implements QueryInterceptor
{
    private Map<String, String> preExecuteValues;
    private Map<String, String> postExecuteValues;
    private JdbcConnection connection;
    private Log log;
    
    public ServerStatusDiffInterceptor() {
        this.preExecuteValues = new HashMap<String, String>();
        this.postExecuteValues = new HashMap<String, String>();
    }
    
    @Override
    public QueryInterceptor init(final MysqlConnection conn, final Properties props, final Log l) {
        this.connection = (JdbcConnection)conn;
        this.log = l;
        return this;
    }
    
    @Override
    public <T extends Resultset> T postProcess(final Supplier<String> sql, final Query interceptedQuery, final T originalResultSet, final ServerSession serverSession) {
        this.populateMapWithSessionStatusValues(this.postExecuteValues);
        this.log.logInfo("Server status change for query:\n" + Util.calculateDifferences(this.preExecuteValues, this.postExecuteValues));
        return null;
    }
    
    private void populateMapWithSessionStatusValues(final Map<String, String> toPopulate) {
        Statement stmt = null;
        ResultSet rs = null;
        try {
            try {
                toPopulate.clear();
                stmt = this.connection.createStatement();
                rs = stmt.executeQuery("SHOW SESSION STATUS");
                ResultSetUtil.resultSetToMap(toPopulate, rs);
            }
            finally {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            }
        }
        catch (SQLException ex) {
            throw ExceptionFactory.createException(ex.getMessage(), ex);
        }
    }
    
    @Override
    public <T extends Resultset> T preProcess(final Supplier<String> sql, final Query interceptedQuery) {
        this.populateMapWithSessionStatusValues(this.preExecuteValues);
        return null;
    }
    
    @Override
    public boolean executeTopLevelOnly() {
        return true;
    }
    
    @Override
    public void destroy() {
        this.connection = null;
        this.log = null;
    }
}
