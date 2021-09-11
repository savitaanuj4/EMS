
package com.mysql.cj.jdbc.ha;

import java.sql.SQLException;
import com.mysql.cj.jdbc.ConnectionImpl;
import com.mysql.cj.jdbc.JdbcConnection;
import java.util.Map;
import java.util.List;
import java.lang.reflect.InvocationHandler;

public class SequentialBalanceStrategy implements BalanceStrategy
{
    private int currentHostIndex;
    
    public SequentialBalanceStrategy() {
        this.currentHostIndex = -1;
    }
    
    @Override
    public ConnectionImpl pickConnection(final InvocationHandler proxy, final List<String> configuredHosts, final Map<String, JdbcConnection> liveConnections, final long[] responseTimes, final int numRetries) throws SQLException {
        final int numHosts = configuredHosts.size();
        SQLException ex = null;
        Map<String, Long> blackList = ((LoadBalancedConnectionProxy)proxy).getGlobalBlacklist();
        for (int attempts = 0; attempts < numRetries; ++attempts) {
            if (numHosts == 1) {
                this.currentHostIndex = 0;
            }
            else if (this.currentHostIndex == -1) {
                int i;
                int random;
                for (random = (i = (int)Math.floor(Math.random() * numHosts)); i < numHosts; ++i) {
                    if (!blackList.containsKey(configuredHosts.get(i))) {
                        this.currentHostIndex = i;
                        break;
                    }
                }
                if (this.currentHostIndex == -1) {
                    for (i = 0; i < random; ++i) {
                        if (!blackList.containsKey(configuredHosts.get(i))) {
                            this.currentHostIndex = i;
                            break;
                        }
                    }
                }
                if (this.currentHostIndex == -1) {
                    blackList = ((LoadBalancedConnectionProxy)proxy).getGlobalBlacklist();
                    try {
                        Thread.sleep(250L);
                    }
                    catch (InterruptedException ex2) {}
                    continue;
                }
            }
            else {
                int j = this.currentHostIndex + 1;
                boolean foundGoodHost = false;
                while (j < numHosts) {
                    if (!blackList.containsKey(configuredHosts.get(j))) {
                        this.currentHostIndex = j;
                        foundGoodHost = true;
                        break;
                    }
                    ++j;
                }
                if (!foundGoodHost) {
                    for (j = 0; j < this.currentHostIndex; ++j) {
                        if (!blackList.containsKey(configuredHosts.get(j))) {
                            this.currentHostIndex = j;
                            foundGoodHost = true;
                            break;
                        }
                    }
                }
                if (!foundGoodHost) {
                    blackList = ((LoadBalancedConnectionProxy)proxy).getGlobalBlacklist();
                    try {
                        Thread.sleep(250L);
                    }
                    catch (InterruptedException ex3) {}
                    continue;
                }
            }
            final String hostPortSpec = configuredHosts.get(this.currentHostIndex);
            ConnectionImpl conn = liveConnections.get(hostPortSpec);
            if (conn == null) {
                try {
                    conn = ((LoadBalancedConnectionProxy)proxy).createConnectionForHost(hostPortSpec);
                }
                catch (SQLException sqlEx) {
                    ex = sqlEx;
                    if (((LoadBalancedConnectionProxy)proxy).shouldExceptionTriggerConnectionSwitch(sqlEx)) {
                        ((LoadBalancedConnectionProxy)proxy).addToGlobalBlacklist(hostPortSpec);
                        try {
                            Thread.sleep(250L);
                        }
                        catch (InterruptedException ex4) {}
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
