
package com.mysql.cj.conf.url;

import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.Collections;
import java.util.Map;
import java.util.Iterator;
import java.util.Collection;
import com.mysql.cj.conf.PropertyKey;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Properties;
import com.mysql.cj.conf.ConnectionUrlParser;
import com.mysql.cj.conf.HostInfo;
import java.util.List;
import com.mysql.cj.conf.ConnectionUrl;

public class ReplicationConnectionUrl extends ConnectionUrl
{
    private static final String TYPE_MASTER = "MASTER";
    private static final String TYPE_SLAVE = "SLAVE";
    private List<HostInfo> masterHosts;
    private List<HostInfo> slaveHosts;
    
    public ReplicationConnectionUrl(final ConnectionUrlParser connStrParser, final Properties info) {
        super(connStrParser, info);
        this.masterHosts = new ArrayList<HostInfo>();
        this.slaveHosts = new ArrayList<HostInfo>();
        this.type = Type.REPLICATION_CONNECTION;
        final LinkedList<HostInfo> undefinedHosts = new LinkedList<HostInfo>();
        for (final HostInfo hi : this.hosts) {
            final Map<String, String> hostProperties = hi.getHostProperties();
            if (hostProperties.containsKey(PropertyKey.TYPE.getKeyName())) {
                if ("MASTER".equalsIgnoreCase(hostProperties.get(PropertyKey.TYPE.getKeyName()))) {
                    this.masterHosts.add(hi);
                }
                else if ("SLAVE".equalsIgnoreCase(hostProperties.get(PropertyKey.TYPE.getKeyName()))) {
                    this.slaveHosts.add(hi);
                }
                else {
                    undefinedHosts.add(hi);
                }
            }
            else {
                undefinedHosts.add(hi);
            }
        }
        if (!undefinedHosts.isEmpty()) {
            if (this.masterHosts.isEmpty()) {
                this.masterHosts.add(undefinedHosts.removeFirst());
            }
            this.slaveHosts.addAll(undefinedHosts);
        }
    }
    
    public ReplicationConnectionUrl(final List<HostInfo> masters, final List<HostInfo> slaves, final Map<String, String> properties) {
        this.masterHosts = new ArrayList<HostInfo>();
        this.slaveHosts = new ArrayList<HostInfo>();
        this.originalConnStr = Type.REPLICATION_CONNECTION.getScheme() + "//**internally_generated**" + System.currentTimeMillis() + "**";
        this.type = Type.REPLICATION_CONNECTION;
        this.hosts.addAll(masters);
        this.hosts.addAll(slaves);
        this.masterHosts.addAll(masters);
        this.slaveHosts.addAll(slaves);
        this.properties.putAll(properties);
        this.injectPerTypeProperties(this.properties);
        this.setupPropertiesTransformer();
    }
    
    public HostInfo getMasterHostOrSpawnIsolated(final String hostPortPair) {
        return super.getHostOrSpawnIsolated(hostPortPair, this.masterHosts);
    }
    
    public List<HostInfo> getMastersList() {
        return Collections.unmodifiableList((List<? extends HostInfo>)this.masterHosts);
    }
    
    public List<String> getMastersListAsHostPortPairs() {
        return this.masterHosts.stream().map(hi -> hi.getHostPortPair()).collect((Collector<? super Object, ?, List<String>>)Collectors.toList());
    }
    
    public List<HostInfo> getMasterHostsListFromHostPortPairs(final Collection<String> hostPortPairs) {
        return hostPortPairs.stream().map((Function<? super String, ?>)this::getMasterHostOrSpawnIsolated).collect((Collector<? super Object, ?, List<HostInfo>>)Collectors.toList());
    }
    
    public HostInfo getSlaveHostOrSpawnIsolated(final String hostPortPair) {
        return super.getHostOrSpawnIsolated(hostPortPair, this.slaveHosts);
    }
    
    public List<HostInfo> getSlavesList() {
        return Collections.unmodifiableList((List<? extends HostInfo>)this.slaveHosts);
    }
    
    public List<String> getSlavesListAsHostPortPairs() {
        return this.slaveHosts.stream().map(hi -> hi.getHostPortPair()).collect((Collector<? super Object, ?, List<String>>)Collectors.toList());
    }
    
    public List<HostInfo> getSlaveHostsListFromHostPortPairs(final Collection<String> hostPortPairs) {
        return hostPortPairs.stream().map((Function<? super String, ?>)this::getSlaveHostOrSpawnIsolated).collect((Collector<? super Object, ?, List<HostInfo>>)Collectors.toList());
    }
}
