
package com.mysql.cj.jdbc.ha;

import java.sql.SQLException;
import java.util.HashMap;
import com.mysql.cj.exceptions.ExceptionInterceptor;
import com.mysql.cj.jdbc.exceptions.SQLError;
import com.mysql.cj.Messages;
import java.util.Collection;
import java.util.ArrayList;
import com.mysql.cj.jdbc.ConnectionImpl;
import com.mysql.cj.jdbc.JdbcConnection;
import java.util.Map;
import java.util.List;
import java.lang.reflect.InvocationHandler;

public class RandomBalanceStrategy implements BalanceStrategy
{
    @Override
    public ConnectionImpl pickConnection(final InvocationHandler proxy, final List<String> configuredHosts, final Map<String, JdbcConnection> liveConnections, final long[] responseTimes, final int numRetries) throws SQLException {
        final int numHosts = configuredHosts.size();
        SQLException ex = null;
        final List<String> whiteList = new ArrayList<String>(numHosts);
        whiteList.addAll(configuredHosts);
        Map<String, Long> blackList = ((LoadBalancedConnectionProxy)proxy).getGlobalBlacklist();
        whiteList.removeAll(blackList.keySet());
        Map<String, Integer> whiteListMap = this.getArrayIndexMap(whiteList);
        int attempts = 0;
        while (attempts < numRetries) {
            final int random = (int)Math.floor(Math.random() * whiteList.size());
            if (whiteList.size() == 0) {
                throw SQLError.createSQLException(Messages.getString("RandomBalanceStrategy.0"), null);
            }
            final String hostPortSpec = whiteList.get(random);
            ConnectionImpl conn = liveConnections.get(hostPortSpec);
            if (conn == null) {
                try {
                    conn = ((LoadBalancedConnectionProxy)proxy).createConnectionForHost(hostPortSpec);
                }
                catch (SQLException sqlEx) {
                    ex = sqlEx;
                    if (((LoadBalancedConnectionProxy)proxy).shouldExceptionTriggerConnectionSwitch(sqlEx)) {
                        final Integer whiteListIndex = whiteListMap.get(hostPortSpec);
                        if (whiteListIndex != null) {
                            whiteList.remove((int)whiteListIndex);
                            whiteListMap = this.getArrayIndexMap(whiteList);
                        }
                        ((LoadBalancedConnectionProxy)proxy).addToGlobalBlacklist(hostPortSpec);
                        if (whiteList.size() != 0) {
                            continue;
                        }
                        ++attempts;
                        try {
                            Thread.sleep(250L);
                        }
                        catch (InterruptedException ex2) {}
                        whiteListMap = new HashMap<String, Integer>(numHosts);
                        whiteList.addAll(configuredHosts);
                        blackList = ((LoadBalancedConnectionProxy)proxy).getGlobalBlacklist();
                        whiteList.removeAll(blackList.keySet());
                        whiteListMap = this.getArrayIndexMap(whiteList);
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
    
    private Map<String, Integer> getArrayIndexMap(final List<String> l) {
        final Map<String, Integer> m = new HashMap<String, Integer>(l.size());
        for (int i = 0; i < l.size(); ++i) {
            m.put(l.get(i), i);
        }
        return m;
    }
}
