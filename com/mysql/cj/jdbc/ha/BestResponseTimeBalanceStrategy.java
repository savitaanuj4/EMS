
package com.mysql.cj.jdbc.ha;

import java.sql.SQLException;
import com.mysql.cj.jdbc.ConnectionImpl;
import com.mysql.cj.jdbc.JdbcConnection;
import java.util.Map;
import java.util.List;
import java.lang.reflect.InvocationHandler;

public class BestResponseTimeBalanceStrategy implements BalanceStrategy
{
    @Override
    public ConnectionImpl pickConnection(final InvocationHandler proxy, final List<String> configuredHosts, final Map<String, JdbcConnection> liveConnections, final long[] responseTimes, final int numRetries) throws SQLException {
        Map<String, Long> blackList = ((LoadBalancedConnectionProxy)proxy).getGlobalBlacklist();
        SQLException ex = null;
        int attempts = 0;
        while (attempts < numRetries) {
            long minResponseTime = Long.MAX_VALUE;
            int bestHostIndex = 0;
            if (blackList.size() == configuredHosts.size()) {
                blackList = ((LoadBalancedConnectionProxy)proxy).getGlobalBlacklist();
            }
            for (int i = 0; i < responseTimes.length; ++i) {
                final long candidateResponseTime = responseTimes[i];
                if (candidateResponseTime < minResponseTime && !blackList.containsKey(configuredHosts.get(i))) {
                    if (candidateResponseTime == 0L) {
                        bestHostIndex = i;
                        break;
                    }
                    bestHostIndex = i;
                    minResponseTime = candidateResponseTime;
                }
            }
            final String bestHost = configuredHosts.get(bestHostIndex);
            ConnectionImpl conn = liveConnections.get(bestHost);
            if (conn == null) {
                try {
                    conn = ((LoadBalancedConnectionProxy)proxy).createConnectionForHost(bestHost);
                }
                catch (SQLException sqlEx) {
                    ex = sqlEx;
                    if (((LoadBalancedConnectionProxy)proxy).shouldExceptionTriggerConnectionSwitch(sqlEx)) {
                        ((LoadBalancedConnectionProxy)proxy).addToGlobalBlacklist(bestHost);
                        blackList.put(bestHost, null);
                        if (blackList.size() != configuredHosts.size()) {
                            continue;
                        }
                        ++attempts;
                        try {
                            Thread.sleep(250L);
                        }
                        catch (InterruptedException ex2) {}
                        blackList = ((LoadBalancedConnectionProxy)proxy).getGlobalBlacklist();
                        continue;
                    }
                    throw sqlEx;
                }
            }
            return conn;
        }
        if (ex != null) {
            throw ex;
        }
        return null;
    }
}
