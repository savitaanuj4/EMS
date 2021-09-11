
package com.mysql.cj.jdbc.ha;

import java.sql.SQLException;
import com.mysql.cj.jdbc.JdbcConnection;

public interface ReplicationConnection extends JdbcConnection
{
    long getConnectionGroupId();
    
    JdbcConnection getCurrentConnection();
    
    JdbcConnection getMasterConnection();
    
    void promoteSlaveToMaster(final String p0) throws SQLException;
    
    void removeMasterHost(final String p0) throws SQLException;
    
    void removeMasterHost(final String p0, final boolean p1) throws SQLException;
    
    boolean isHostMaster(final String p0);
    
    JdbcConnection getSlavesConnection();
    
    void addSlaveHost(final String p0) throws SQLException;
    
    void removeSlave(final String p0) throws SQLException;
    
    void removeSlave(final String p0, final boolean p1) throws SQLException;
    
    boolean isHostSlave(final String p0);
}
