
package com.mysql.cj.conf.url;

import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import com.mysql.cj.util.StringUtils;
import com.mysql.cj.conf.PropertyKey;
import java.util.Collection;
import java.util.Map;
import com.mysql.cj.conf.HostInfo;
import java.util.List;
import java.util.Properties;
import com.mysql.cj.conf.ConnectionUrlParser;
import com.mysql.cj.conf.ConnectionUrl;

public class LoadbalanceConnectionUrl extends ConnectionUrl
{
    public LoadbalanceConnectionUrl(final ConnectionUrlParser connStrParser, final Properties info) {
        super(connStrParser, info);
        this.type = Type.LOADBALANCE_CONNECTION;
    }
    
    public LoadbalanceConnectionUrl(final List<HostInfo> hosts, final Map<String, String> properties) {
        this.originalConnStr = Type.LOADBALANCE_CONNECTION.getScheme() + "//**internally_generated**" + System.currentTimeMillis() + "**";
        this.type = Type.LOADBALANCE_CONNECTION;
        this.hosts.addAll(hosts);
        this.properties.putAll(properties);
        this.injectPerTypeProperties(this.properties);
        this.setupPropertiesTransformer();
    }
    
    @Override
    protected void injectPerTypeProperties(final Map<String, String> props) {
        if (props.containsKey(PropertyKey.loadBalanceAutoCommitStatementThreshold.getKeyName())) {
            try {
                final int autoCommitSwapThreshold = Integer.parseInt(props.get(PropertyKey.loadBalanceAutoCommitStatementThreshold.getKeyName()));
                if (autoCommitSwapThreshold > 0) {
                    final String queryInterceptors = props.get(PropertyKey.queryInterceptors.getKeyName());
                    final String lbi = "com.mysql.cj.jdbc.ha.LoadBalancedAutoCommitInterceptor";
                    if (StringUtils.isNullOrEmpty(queryInterceptors)) {
                        props.put(PropertyKey.queryInterceptors.getKeyName(), lbi);
                    }
                    else {
                        props.put(PropertyKey.queryInterceptors.getKeyName(), queryInterceptors + "," + lbi);
                    }
                }
            }
            catch (Throwable t) {}
        }
    }
    
    public List<String> getHostInfoListAsHostPortPairs() {
        return this.hosts.stream().map(hi -> hi.getHostPortPair()).collect((Collector<? super Object, ?, List<String>>)Collectors.toList());
    }
    
    public List<HostInfo> getHostInfoListFromHostPortPairs(final Collection<String> hostPortPairs) {
        return hostPortPairs.stream().map((Function<? super String, ?>)this::getHostOrSpawnIsolated).collect((Collector<? super Object, ?, List<HostInfo>>)Collectors.toList());
    }
}
