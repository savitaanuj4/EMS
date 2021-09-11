
package com.mysql.cj.jdbc;

import java.util.HashMap;
import java.sql.Connection;
import com.mysql.cj.exceptions.CJException;
import com.mysql.cj.jdbc.exceptions.SQLExceptionsMapping;
import javax.transaction.xa.XAException;
import com.mysql.cj.conf.PropertyKey;
import java.sql.SQLException;
import javax.transaction.xa.Xid;
import java.util.Map;
import javax.transaction.xa.XAResource;
import javax.sql.XAConnection;

public class SuspendableXAConnection extends MysqlPooledConnection implements XAConnection, XAResource
{
    private static final Map<Xid, XAConnection> XIDS_TO_PHYSICAL_CONNECTIONS;
    private Xid currentXid;
    private XAConnection currentXAConnection;
    private XAResource currentXAResource;
    private JdbcConnection underlyingConnection;
    
    protected static SuspendableXAConnection getInstance(final JdbcConnection mysqlConnection) throws SQLException {
        return new SuspendableXAConnection(mysqlConnection);
    }
    
    public SuspendableXAConnection(final JdbcConnection connection) {
        super(connection);
        this.underlyingConnection = connection;
    }
    
    private static synchronized XAConnection findConnectionForXid(final JdbcConnection connectionToWrap, final Xid xid) throws SQLException {
        XAConnection conn = SuspendableXAConnection.XIDS_TO_PHYSICAL_CONNECTIONS.get(xid);
        if (conn == null) {
            conn = new MysqlXAConnection(connectionToWrap, connectionToWrap.getPropertySet().getBooleanProperty(PropertyKey.logXaCommands).getValue());
            SuspendableXAConnection.XIDS_TO_PHYSICAL_CONNECTIONS.put(xid, conn);
        }
        return conn;
    }
    
    private static synchronized void removeXAConnectionMapping(final Xid xid) {
        SuspendableXAConnection.XIDS_TO_PHYSICAL_CONNECTIONS.remove(xid);
    }
    
    private synchronized void switchToXid(final Xid xid) throws XAException {
        if (xid == null) {
            throw new XAException();
        }
        try {
            if (!xid.equals(this.currentXid)) {
                final XAConnection toSwitchTo = findConnectionForXid(this.underlyingConnection, xid);
                this.currentXAConnection = toSwitchTo;
                this.currentXid = xid;
                this.currentXAResource = toSwitchTo.getXAResource();
            }
        }
        catch (SQLException sqlEx) {
            throw new XAException();
        }
    }
    
    @Override
    public XAResource getXAResource() throws SQLException {
        try {
            return this;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex);
        }
    }
    
    @Override
    public void commit(final Xid xid, final boolean arg1) throws XAException {
        this.switchToXid(xid);
        this.currentXAResource.commit(xid, arg1);
        removeXAConnectionMapping(xid);
    }
    
    @Override
    public void end(final Xid xid, final int arg1) throws XAException {
        this.switchToXid(xid);
        this.currentXAResource.end(xid, arg1);
    }
    
    @Override
    public void forget(final Xid xid) throws XAException {
        this.switchToXid(xid);
        this.currentXAResource.forget(xid);
        removeXAConnectionMapping(xid);
    }
    
    @Override
    public int getTransactionTimeout() throws XAException {
        return 0;
    }
    
    @Override
    public boolean isSameRM(final XAResource xaRes) throws XAException {
        return xaRes == this;
    }
    
    @Override
    public int prepare(final Xid xid) throws XAException {
        this.switchToXid(xid);
        return this.currentXAResource.prepare(xid);
    }
    
    @Override
    public Xid[] recover(final int flag) throws XAException {
        return MysqlXAConnection.recover(this.underlyingConnection, flag);
    }
    
    @Override
    public void rollback(final Xid xid) throws XAException {
        this.switchToXid(xid);
        this.currentXAResource.rollback(xid);
        removeXAConnectionMapping(xid);
    }
    
    @Override
    public boolean setTransactionTimeout(final int arg0) throws XAException {
        return false;
    }
    
    @Override
    public void start(final Xid xid, final int arg1) throws XAException {
        this.switchToXid(xid);
        if (arg1 != 2097152) {
            this.currentXAResource.start(xid, arg1);
            return;
        }
        this.currentXAResource.start(xid, 134217728);
    }
    
    @Override
    public synchronized Connection getConnection() throws SQLException {
        try {
            if (this.currentXAConnection == null) {
                return this.getConnection(false, true);
            }
            return this.currentXAConnection.getConnection();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex);
        }
    }
    
    @Override
    public void close() throws SQLException {
        try {
            if (this.currentXAConnection == null) {
                super.close();
            }
            else {
                removeXAConnectionMapping(this.currentXid);
                this.currentXAConnection.close();
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex);
        }
    }
    
    static {
        XIDS_TO_PHYSICAL_CONNECTIONS = new HashMap<Xid, XAConnection>();
    }
}
