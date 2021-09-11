
package com.mysql.cj.jdbc.ha;

import com.mysql.cj.exceptions.ExceptionFactory;
import java.sql.SQLException;
import com.mysql.cj.util.StringUtils;
import com.mysql.cj.protocol.Resultset;
import com.mysql.cj.protocol.ServerSession;
import com.mysql.cj.Query;
import java.util.function.Supplier;
import com.mysql.cj.conf.PropertyKey;
import com.mysql.cj.log.Log;
import java.util.Properties;
import com.mysql.cj.MysqlConnection;
import com.mysql.cj.jdbc.JdbcConnection;
import com.mysql.cj.interceptors.QueryInterceptor;

public class LoadBalancedAutoCommitInterceptor implements QueryInterceptor
{
    private int matchingAfterStatementCount;
    private int matchingAfterStatementThreshold;
    private String matchingAfterStatementRegex;
    private JdbcConnection conn;
    private LoadBalancedConnectionProxy proxy;
    private boolean countStatements;
    
    public LoadBalancedAutoCommitInterceptor() {
        this.matchingAfterStatementCount = 0;
        this.matchingAfterStatementThreshold = 0;
        this.proxy = null;
        this.countStatements = false;
    }
    
    @Override
    public void destroy() {
        this.conn = null;
        this.proxy = null;
    }
    
    @Override
    public boolean executeTopLevelOnly() {
        return false;
    }
    
    @Override
    public QueryInterceptor init(final MysqlConnection connection, final Properties props, final Log log) {
        this.conn = (JdbcConnection)connection;
        final String autoCommitSwapThresholdAsString = props.getProperty(PropertyKey.loadBalanceAutoCommitStatementThreshold.getKeyName(), "0");
        try {
            this.matchingAfterStatementThreshold = Integer.parseInt(autoCommitSwapThresholdAsString);
        }
        catch (NumberFormatException ex) {}
        final String autoCommitSwapRegex = props.getProperty(PropertyKey.loadBalanceAutoCommitStatementRegex.getKeyName(), "");
        if (!"".equals(autoCommitSwapRegex)) {
            this.matchingAfterStatementRegex = autoCommitSwapRegex;
        }
        return this;
    }
    
    @Override
    public <T extends Resultset> T postProcess(final Supplier<String> sql, final Query interceptedQuery, final T originalResultSet, final ServerSession serverSession) {
        try {
            if (!this.countStatements || StringUtils.startsWithIgnoreCase(sql.get(), "SET") || StringUtils.startsWithIgnoreCase(sql.get(), "SHOW")) {
                return originalResultSet;
            }
            if (!this.conn.getAutoCommit()) {
                this.matchingAfterStatementCount = 0;
                return originalResultSet;
            }
            if (this.proxy == null && this.conn.isProxySet()) {
                JdbcConnection lcl_proxy;
                for (lcl_proxy = this.conn.getMultiHostSafeProxy(); lcl_proxy != null && !(lcl_proxy instanceof LoadBalancedMySQLConnection); lcl_proxy = lcl_proxy.getMultiHostSafeProxy()) {}
                if (lcl_proxy != null) {
                    this.proxy = ((LoadBalancedMySQLConnection)lcl_proxy).getThisAsProxy();
                }
            }
            if (this.proxy == null) {
                return originalResultSet;
            }
            if (this.matchingAfterStatementRegex == null || sql.get().matches(this.matchingAfterStatementRegex)) {
                ++this.matchingAfterStatementCount;
            }
            if (this.matchingAfterStatementCount >= this.matchingAfterStatementThreshold) {
                this.matchingAfterStatementCount = 0;
                try {
                    this.proxy.pickNewConnection();
                }
                catch (SQLException ex2) {}
            }
        }
        catch (SQLException ex) {
            throw ExceptionFactory.createException(ex.getMessage(), ex);
        }
        return originalResultSet;
    }
    
    @Override
    public <T extends Resultset> T preProcess(final Supplier<String> sql, final Query interceptedQuery) {
        return null;
    }
    
    void pauseCounters() {
        this.countStatements = false;
    }
    
    void resumeCounters() {
        this.countStatements = true;
    }
}
