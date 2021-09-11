
package com.mysql.cj.jdbc.jmx;

import com.mysql.cj.jdbc.ha.ReplicationConnectionGroup;
import java.util.Iterator;
import com.mysql.cj.jdbc.ha.ReplicationConnectionGroupManager;
import java.sql.SQLException;
import javax.management.MBeanServer;
import com.mysql.cj.exceptions.ExceptionInterceptor;
import com.mysql.cj.jdbc.exceptions.SQLError;
import com.mysql.cj.Messages;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

public class ReplicationGroupManager implements ReplicationGroupManagerMBean
{
    private boolean isJmxRegistered;
    
    public ReplicationGroupManager() {
        this.isJmxRegistered = false;
    }
    
    public synchronized void registerJmx() throws SQLException {
        if (this.isJmxRegistered) {
            return;
        }
        final MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        try {
            final ObjectName name = new ObjectName("com.mysql.cj.jdbc.jmx:type=ReplicationGroupManager");
            mbs.registerMBean(this, name);
            this.isJmxRegistered = true;
        }
        catch (Exception e) {
            throw SQLError.createSQLException(Messages.getString("ReplicationGroupManager.0"), null, e, null);
        }
    }
    
    @Override
    public void addSlaveHost(final String groupFilter, final String host) throws SQLException {
        ReplicationConnectionGroupManager.addSlaveHost(groupFilter, host);
    }
    
    @Override
    public void removeSlaveHost(final String groupFilter, final String host) throws SQLException {
        ReplicationConnectionGroupManager.removeSlaveHost(groupFilter, host);
    }
    
    @Override
    public void promoteSlaveToMaster(final String groupFilter, final String host) throws SQLException {
        ReplicationConnectionGroupManager.promoteSlaveToMaster(groupFilter, host);
    }
    
    @Override
    public void removeMasterHost(final String groupFilter, final String host) throws SQLException {
        ReplicationConnectionGroupManager.removeMasterHost(groupFilter, host);
    }
    
    @Override
    public String getMasterHostsList(final String group) {
        final StringBuilder sb = new StringBuilder("");
        boolean found = false;
        for (final String host : ReplicationConnectionGroupManager.getMasterHosts(group)) {
            if (found) {
                sb.append(",");
            }
            found = true;
            sb.append(host);
        }
        return sb.toString();
    }
    
    @Override
    public String getSlaveHostsList(final String group) {
        final StringBuilder sb = new StringBuilder("");
        boolean found = false;
        for (final String host : ReplicationConnectionGroupManager.getSlaveHosts(group)) {
            if (found) {
                sb.append(",");
            }
            found = true;
            sb.append(host);
        }
        return sb.toString();
    }
    
    @Override
    public String getRegisteredConnectionGroups() {
        final StringBuilder sb = new StringBuilder("");
        boolean found = false;
        for (final ReplicationConnectionGroup group : ReplicationConnectionGroupManager.getGroupsMatching(null)) {
            if (found) {
                sb.append(",");
            }
            found = true;
            sb.append(group.getGroupName());
        }
        return sb.toString();
    }
    
    @Override
    public int getActiveMasterHostCount(final String group) {
        return ReplicationConnectionGroupManager.getMasterHosts(group).size();
    }
    
    @Override
    public int getActiveSlaveHostCount(final String group) {
        return ReplicationConnectionGroupManager.getSlaveHosts(group).size();
    }
    
    @Override
    public int getSlavePromotionCount(final String group) {
        return ReplicationConnectionGroupManager.getNumberOfMasterPromotion(group);
    }
    
    @Override
    public long getTotalLogicalConnectionCount(final String group) {
        return ReplicationConnectionGroupManager.getTotalConnectionCount(group);
    }
    
    @Override
    public long getActiveLogicalConnectionCount(final String group) {
        return ReplicationConnectionGroupManager.getActiveConnectionCount(group);
    }
}
