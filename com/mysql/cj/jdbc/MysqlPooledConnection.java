
package com.mysql.cj.jdbc;

import javax.sql.StatementEvent;
import java.util.Iterator;
import javax.sql.ConnectionEvent;
import com.mysql.cj.jdbc.exceptions.SQLError;
import com.mysql.cj.Messages;
import com.mysql.cj.exceptions.CJException;
import com.mysql.cj.jdbc.exceptions.SQLExceptionsMapping;
import java.util.HashMap;
import java.sql.SQLException;
import javax.sql.StatementEventListener;
import com.mysql.cj.exceptions.ExceptionInterceptor;
import java.sql.Connection;
import javax.sql.ConnectionEventListener;
import java.util.Map;
import javax.sql.PooledConnection;

public class MysqlPooledConnection implements PooledConnection
{
    public static final int CONNECTION_ERROR_EVENT = 1;
    public static final int CONNECTION_CLOSED_EVENT = 2;
    private Map<ConnectionEventListener, ConnectionEventListener> connectionEventListeners;
    private Connection logicalHandle;
    private JdbcConnection physicalConn;
    private ExceptionInterceptor exceptionInterceptor;
    private final Map<StatementEventListener, StatementEventListener> statementEventListeners;
    
    protected static MysqlPooledConnection getInstance(final JdbcConnection connection) throws SQLException {
        return new MysqlPooledConnection(connection);
    }
    
    public MysqlPooledConnection(final JdbcConnection connection) {
        this.statementEventListeners = new HashMap<StatementEventListener, StatementEventListener>();
        this.logicalHandle = null;
        this.physicalConn = connection;
        this.connectionEventListeners = new HashMap<ConnectionEventListener, ConnectionEventListener>();
        this.exceptionInterceptor = this.physicalConn.getExceptionInterceptor();
    }
    
    @Override
    public synchronized void addConnectionEventListener(final ConnectionEventListener connectioneventlistener) {
        if (this.connectionEventListeners != null) {
            this.connectionEventListeners.put(connectioneventlistener, connectioneventlistener);
        }
    }
    
    @Override
    public synchronized void removeConnectionEventListener(final ConnectionEventListener connectioneventlistener) {
        if (this.connectionEventListeners != null) {
            this.connectionEventListeners.remove(connectioneventlistener);
        }
    }
    
    @Override
    public synchronized Connection getConnection() throws SQLException {
        try {
            return this.getConnection(true, false);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.exceptionInterceptor);
        }
    }
    
    protected synchronized Connection getConnection(final boolean resetServerState, final boolean forXa) throws SQLException {
        SQLException sqlException = null;
        if (this.physicalConn == null) {
            sqlException = SQLError.createSQLException(Messages.getString("MysqlPooledConnection.0"), this.exceptionInterceptor);
            this.callConnectionEventListeners(1, sqlException);
            throw sqlException;
        }
        try {
            if (this.logicalHandle != null) {
                ((ConnectionWrapper)this.logicalHandle).close(false);
            }
            if (resetServerState) {
                this.physicalConn.resetServerState();
            }
            this.logicalHandle = ConnectionWrapper.getInstance(this, this.physicalConn, forXa);
        }
        catch (SQLException sqlException) {
            this.callConnectionEventListeners(1, sqlException);
            throw sqlException;
        }
        return this.logicalHandle;
    }
    
    @Override
    public synchronized void close() throws SQLException {
        try {
            if (this.physicalConn != null) {
                this.physicalConn.close();
                this.physicalConn = null;
            }
            if (this.connectionEventListeners != null) {
                this.connectionEventListeners.clear();
                this.connectionEventListeners = null;
            }
            this.statementEventListeners.clear();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.exceptionInterceptor);
        }
    }
    
    protected synchronized void callConnectionEventListeners(final int eventType, final SQLException sqlException) {
        if (this.connectionEventListeners == null) {
            return;
        }
        final Iterator<Map.Entry<ConnectionEventListener, ConnectionEventListener>> iterator = this.connectionEventListeners.entrySet().iterator();
        final ConnectionEvent connectionevent = new ConnectionEvent(this, sqlException);
        while (iterator.hasNext()) {
            final ConnectionEventListener connectioneventlistener = iterator.next().getValue();
            if (eventType == 2) {
                connectioneventlistener.connectionClosed(connectionevent);
            }
            else {
                if (eventType != 1) {
                    continue;
                }
                connectioneventlistener.connectionErrorOccurred(connectionevent);
            }
        }
    }
    
    protected ExceptionInterceptor getExceptionInterceptor() {
        return this.exceptionInterceptor;
    }
    
    @Override
    public void addStatementEventListener(final StatementEventListener listener) {
        synchronized (this.statementEventListeners) {
            this.statementEventListeners.put(listener, listener);
        }
    }
    
    @Override
    public void removeStatementEventListener(final StatementEventListener listener) {
        synchronized (this.statementEventListeners) {
            this.statementEventListeners.remove(listener);
        }
    }
    
    void fireStatementEvent(final StatementEvent event) throws SQLException {
        synchronized (this.statementEventListeners) {
            for (final StatementEventListener listener : this.statementEventListeners.keySet()) {
                listener.statementClosed(event);
            }
        }
    }
}
