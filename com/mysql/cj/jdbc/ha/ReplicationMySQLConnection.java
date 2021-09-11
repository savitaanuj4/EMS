
package com.mysql.cj.jdbc.ha;

import com.mysql.cj.jdbc.exceptions.SQLError;
import com.mysql.cj.Messages;
import java.util.concurrent.Executor;
import java.util.Map;
import java.util.Properties;
import com.mysql.cj.exceptions.CJException;
import com.mysql.cj.jdbc.exceptions.SQLExceptionsMapping;
import java.sql.SQLException;
import com.mysql.cj.jdbc.JdbcConnection;

public class ReplicationMySQLConnection extends MultiHostMySQLConnection implements ReplicationConnection
{
    public ReplicationMySQLConnection(final MultiHostConnectionProxy proxy) {
        super(proxy);
    }
    
    @Override
    public ReplicationConnectionProxy getThisAsProxy() {
        return (ReplicationConnectionProxy)super.getThisAsProxy();
    }
    
    @Override
    public JdbcConnection getActiveMySQLConnection() {
        return this.getCurrentConnection();
    }
    
    @Override
    public synchronized JdbcConnection getCurrentConnection() {
        return this.getThisAsProxy().getCurrentConnection();
    }
    
    @Override
    public long getConnectionGroupId() {
        return this.getThisAsProxy().getConnectionGroupId();
    }
    
    @Override
    public synchronized JdbcConnection getMasterConnection() {
        return this.getThisAsProxy().getMasterConnection();
    }
    
    private JdbcConnection getValidatedMasterConnection() {
        final JdbcConnection conn = this.getThisAsProxy().masterConnection;
        try {
            return (conn == null || conn.isClosed()) ? null : conn;
        }
        catch (SQLException e) {
            return null;
        }
    }
    
    @Override
    public void promoteSlaveToMaster(final String host) throws SQLException {
        try {
            this.getThisAsProxy().promoteSlaveToMaster(host);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void removeMasterHost(final String host) throws SQLException {
        try {
            this.getThisAsProxy().removeMasterHost(host);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void removeMasterHost(final String host, final boolean waitUntilNotInUse) throws SQLException {
        try {
            this.getThisAsProxy().removeMasterHost(host, waitUntilNotInUse);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean isHostMaster(final String host) {
        return this.getThisAsProxy().isHostMaster(host);
    }
    
    @Override
    public synchronized JdbcConnection getSlavesConnection() {
        return this.getThisAsProxy().getSlavesConnection();
    }
    
    private JdbcConnection getValidatedSlavesConnection() {
        final JdbcConnection conn = this.getThisAsProxy().slavesConnection;
        try {
            return (conn == null || conn.isClosed()) ? null : conn;
        }
        catch (SQLException e) {
            return null;
        }
    }
    
    @Override
    public void addSlaveHost(final String host) throws SQLException {
        try {
            this.getThisAsProxy().addSlaveHost(host);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void removeSlave(final String host) throws SQLException {
        try {
            this.getThisAsProxy().removeSlave(host);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void removeSlave(final String host, final boolean closeGently) throws SQLException {
        try {
            this.getThisAsProxy().removeSlave(host, closeGently);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean isHostSlave(final String host) {
        return this.getThisAsProxy().isHostSlave(host);
    }
    
    @Override
    public void setReadOnly(final boolean readOnlyFlag) throws SQLException {
        try {
            this.getThisAsProxy().setReadOnly(readOnlyFlag);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean isReadOnly() throws SQLException {
        try {
            return this.getThisAsProxy().isReadOnly();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public synchronized void ping() throws SQLException {
        try {
            try {
                final JdbcConnection conn;
                if ((conn = this.getValidatedMasterConnection()) != null) {
                    conn.ping();
                }
            }
            catch (SQLException e) {
                if (this.isMasterConnection()) {
                    throw e;
                }
            }
            try {
                final JdbcConnection conn;
                if ((conn = this.getValidatedSlavesConnection()) != null) {
                    conn.ping();
                }
            }
            catch (SQLException e) {
                if (!this.isMasterConnection()) {
                    throw e;
                }
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public synchronized void changeUser(final String userName, final String newPassword) throws SQLException {
        try {
            JdbcConnection conn;
            if ((conn = this.getValidatedMasterConnection()) != null) {
                conn.changeUser(userName, newPassword);
            }
            if ((conn = this.getValidatedSlavesConnection()) != null) {
                conn.changeUser(userName, newPassword);
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public synchronized void setStatementComment(final String comment) {
        JdbcConnection conn;
        if ((conn = this.getValidatedMasterConnection()) != null) {
            conn.setStatementComment(comment);
        }
        if ((conn = this.getValidatedSlavesConnection()) != null) {
            conn.setStatementComment(comment);
        }
    }
    
    @Override
    public boolean hasSameProperties(final JdbcConnection c) {
        final JdbcConnection connM = this.getValidatedMasterConnection();
        final JdbcConnection connS = this.getValidatedSlavesConnection();
        return (connM != null || connS != null) && (connM == null || connM.hasSameProperties(c)) && (connS == null || connS.hasSameProperties(c));
    }
    
    @Override
    public Properties getProperties() {
        final Properties props = new Properties();
        JdbcConnection conn;
        if ((conn = this.getValidatedMasterConnection()) != null) {
            props.putAll(conn.getProperties());
        }
        if ((conn = this.getValidatedSlavesConnection()) != null) {
            props.putAll(conn.getProperties());
        }
        return props;
    }
    
    @Override
    public void abort(final Executor executor) throws SQLException {
        try {
            this.getThisAsProxy().doAbort(executor);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void abortInternal() throws SQLException {
        try {
            this.getThisAsProxy().doAbortInternal();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setProxy(final JdbcConnection proxy) {
        this.getThisAsProxy().setProxy(proxy);
    }
    
    @Override
    public boolean isWrapperFor(final Class<?> iface) throws SQLException {
        try {
            return iface.isInstance(this);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public <T> T unwrap(final Class<T> iface) throws SQLException {
        try {
            try {
                return iface.cast(this);
            }
            catch (ClassCastException cce) {
                throw SQLError.createSQLException(Messages.getString("Common.UnableToUnwrap", new Object[] { iface.toString() }), "S1009", this.getExceptionInterceptor());
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Deprecated
    @Override
    public synchronized void clearHasTriedMaster() {
        this.getThisAsProxy().masterConnection.clearHasTriedMaster();
        this.getThisAsProxy().slavesConnection.clearHasTriedMaster();
    }
}
