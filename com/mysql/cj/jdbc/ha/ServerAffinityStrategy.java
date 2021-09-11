
package com.mysql.cj.jdbc.ha;

import java.sql.SQLException;
import com.mysql.cj.jdbc.ConnectionImpl;
import com.mysql.cj.jdbc.JdbcConnection;
import java.util.Map;
import java.util.List;
import java.lang.reflect.InvocationHandler;
import com.mysql.cj.util.StringUtils;

public class ServerAffinityStrategy extends RandomBalanceStrategy
{
    public String[] affinityOrderedServers;
    
    public ServerAffinityStrategy(final String affinityOrdervers) {
        this.affinityOrderedServers = null;
        if (!StringUtils.isNullOrEmpty(affinityOrdervers)) {
            this.affinityOrderedServers = affinityOrdervers.split(",");
        }
    }
    
    @Override
    public ConnectionImpl pickConnection(final InvocationHandler proxy, final List<String> configuredHosts, final Map<String, JdbcConnection> liveConnections, final long[] responseTimes, final int numRetries) throws SQLException {
        if (this.affinityOrderedServers == null) {
            return super.pickConnection(proxy, configuredHosts, liveConnections, responseTimes, numRetries);
        }
        final Map<String, Long> blackList = ((LoadBalancedConnectionProxy)proxy).getGlobalBlacklist();
        for (final String host : this.affinityOrderedServers) {
            if (configuredHosts.contains(host) && !blackList.containsKey(host)) {
                ConnectionImpl conn = liveConnections.get(host);
                if (conn != null) {
                    return conn;
                }
                try {
                    conn = ((LoadBalancedConnectionProxy)proxy).createConnectionForHost(host);
                    return conn;
                }
                catch (SQLException sqlEx) {
                    if (((LoadBalancedConnectionProxy)proxy).shouldExceptionTriggerConnectionSwitch(sqlEx)) {
                        ((LoadBalancedConnectionProxy)proxy).addToGlobalBlacklist(host);
                    }
                }
            }
        }
        return super.pickConnection(proxy, configuredHosts, liveConnections, responseTimes, numRetries);
    }
}
