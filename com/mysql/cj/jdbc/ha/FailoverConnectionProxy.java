
package com.mysql.cj.jdbc.ha;

import java.lang.reflect.InvocationTargetException;
import com.mysql.cj.exceptions.ExceptionInterceptor;
import com.mysql.cj.jdbc.exceptions.SQLError;
import java.lang.reflect.Method;
import java.util.concurrent.Executor;
import com.mysql.cj.util.Util;
import com.mysql.cj.jdbc.exceptions.SQLExceptionsMapping;
import com.mysql.cj.conf.HostInfo;
import com.mysql.cj.jdbc.ConnectionImpl;
import com.mysql.cj.exceptions.CJException;
import com.mysql.cj.exceptions.CJCommunicationsException;
import com.mysql.cj.jdbc.exceptions.CommunicationsException;
import com.mysql.cj.conf.PropertyKey;
import com.mysql.cj.jdbc.JdbcPropertySetImpl;
import java.sql.SQLException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import com.mysql.cj.jdbc.JdbcConnection;
import com.mysql.cj.conf.ConnectionUrl;

public class FailoverConnectionProxy extends MultiHostConnectionProxy
{
    private static final String METHOD_SET_READ_ONLY = "setReadOnly";
    private static final String METHOD_SET_AUTO_COMMIT = "setAutoCommit";
    private static final String METHOD_COMMIT = "commit";
    private static final String METHOD_ROLLBACK = "rollback";
    private static final int NO_CONNECTION_INDEX = -1;
    private static final int DEFAULT_PRIMARY_HOST_INDEX = 0;
    private int secondsBeforeRetryPrimaryHost;
    private long queriesBeforeRetryPrimaryHost;
    private boolean failoverReadOnly;
    private int retriesAllDown;
    private int currentHostIndex;
    private int primaryHostIndex;
    private Boolean explicitlyReadOnly;
    private boolean explicitlyAutoCommit;
    private boolean enableFallBackToPrimaryHost;
    private long primaryHostFailTimeMillis;
    private long queriesIssuedSinceFailover;
    
    public static JdbcConnection createProxyInstance(final ConnectionUrl connectionUrl) throws SQLException {
        final FailoverConnectionProxy connProxy = new FailoverConnectionProxy(connectionUrl);
        return (JdbcConnection)Proxy.newProxyInstance(JdbcConnection.class.getClassLoader(), new Class[] { JdbcConnection.class }, connProxy);
    }
    
    private FailoverConnectionProxy(final ConnectionUrl connectionUrl) throws SQLException {
        super(connectionUrl);
        this.currentHostIndex = -1;
        this.primaryHostIndex = 0;
        this.explicitlyReadOnly = null;
        this.explicitlyAutoCommit = true;
        this.enableFallBackToPrimaryHost = true;
        this.primaryHostFailTimeMillis = 0L;
        this.queriesIssuedSinceFailover = 0L;
        final JdbcPropertySetImpl connProps = new JdbcPropertySetImpl();
        connProps.initializeProperties(connectionUrl.getConnectionArgumentsAsProperties());
        this.secondsBeforeRetryPrimaryHost = connProps.getIntegerProperty(PropertyKey.secondsBeforeRetryMaster).getValue();
        this.queriesBeforeRetryPrimaryHost = connProps.getIntegerProperty(PropertyKey.queriesBeforeRetryMaster).getValue();
        this.failoverReadOnly = connProps.getBooleanProperty(PropertyKey.failOverReadOnly).getValue();
        this.retriesAllDown = connProps.getIntegerProperty(PropertyKey.retriesAllDown).getValue();
        this.enableFallBackToPrimaryHost = (this.secondsBeforeRetryPrimaryHost > 0 || this.queriesBeforeRetryPrimaryHost > 0L);
        this.pickNewConnection();
        this.explicitlyAutoCommit = this.currentConnection.getAutoCommit();
    }
    
    @Override
    JdbcInterfaceProxy getNewJdbcInterfaceProxy(final Object toProxy) {
        return new FailoverJdbcInterfaceProxy(toProxy);
    }
    
    @Override
    boolean shouldExceptionTriggerConnectionSwitch(final Throwable t) {
        String sqlState = null;
        if (t instanceof CommunicationsException || t instanceof CJCommunicationsException) {
            return true;
        }
        if (t instanceof SQLException) {
            sqlState = ((SQLException)t).getSQLState();
        }
        else if (t instanceof CJException) {
            sqlState = ((CJException)t).getSQLState();
        }
        return sqlState != null && sqlState.startsWith("08");
    }
    
    @Override
    boolean isMasterConnection() {
        return this.connectedToPrimaryHost();
    }
    
    @Override
    synchronized void pickNewConnection() throws SQLException {
        if (this.isClosed && this.closedExplicitly) {
            return;
        }
        if (this.isConnected()) {
            if (!this.readyToFallBackToPrimaryHost()) {
                this.failOver();
                return;
            }
        }
        try {
            this.connectTo(this.primaryHostIndex);
        }
        catch (SQLException e) {
            this.resetAutoFallBackCounters();
            this.failOver(this.primaryHostIndex);
        }
    }
    
    synchronized ConnectionImpl createConnectionForHostIndex(final int hostIndex) throws SQLException {
        return this.createConnectionForHost(this.hostsList.get(hostIndex));
    }
    
    private synchronized void connectTo(final int hostIndex) throws SQLException {
        try {
            this.switchCurrentConnectionTo(hostIndex, this.createConnectionForHostIndex(hostIndex));
        }
        catch (SQLException e) {
            if (this.currentConnection != null) {
                final StringBuilder msg = new StringBuilder("Connection to ").append(this.isPrimaryHostIndex(hostIndex) ? "primary" : "secondary").append(" host '").append(this.hostsList.get(hostIndex)).append("' failed");
                try {
                    this.currentConnection.getSession().getLog().logWarn(msg.toString(), e);
                }
                catch (CJException ex) {
                    throw SQLExceptionsMapping.translateException(e, this.currentConnection.getExceptionInterceptor());
                }
            }
            throw e;
        }
    }
    
    private synchronized void switchCurrentConnectionTo(final int hostIndex, final JdbcConnection connection) throws SQLException {
        this.invalidateCurrentConnection();
        boolean readOnly;
        if (this.isPrimaryHostIndex(hostIndex)) {
            readOnly = (this.explicitlyReadOnly != null && this.explicitlyReadOnly);
        }
        else if (this.failoverReadOnly) {
            readOnly = true;
        }
        else if (this.explicitlyReadOnly != null) {
            readOnly = this.explicitlyReadOnly;
        }
        else {
            readOnly = (this.currentConnection != null && this.currentConnection.isReadOnly());
        }
        this.syncSessionState(this.currentConnection, connection, readOnly);
        this.currentConnection = connection;
        this.currentHostIndex = hostIndex;
    }
    
    private synchronized void failOver() throws SQLException {
        this.failOver(this.currentHostIndex);
    }
    
    private synchronized void failOver(final int failedHostIdx) throws SQLException {
        final int prevHostIndex = this.currentHostIndex;
        final int firstHostIndexTried;
        int nextHostIndex = firstHostIndexTried = this.nextHost(failedHostIdx, false);
        SQLException lastExceptionCaught = null;
        int attempts = 0;
        boolean gotConnection = false;
        boolean firstConnOrPassedByPrimaryHost = prevHostIndex == -1 || this.isPrimaryHostIndex(prevHostIndex);
        do {
            try {
                firstConnOrPassedByPrimaryHost = (firstConnOrPassedByPrimaryHost || this.isPrimaryHostIndex(nextHostIndex));
                this.connectTo(nextHostIndex);
                if (firstConnOrPassedByPrimaryHost && this.connectedToSecondaryHost()) {
                    this.resetAutoFallBackCounters();
                }
                gotConnection = true;
            }
            catch (SQLException e) {
                lastExceptionCaught = e;
                if (!this.shouldExceptionTriggerConnectionSwitch(e)) {
                    throw e;
                }
                int newNextHostIndex = this.nextHost(nextHostIndex, attempts > 0);
                if (newNextHostIndex == firstHostIndexTried && newNextHostIndex == (newNextHostIndex = this.nextHost(nextHostIndex, true))) {
                    ++attempts;
                    try {
                        Thread.sleep(250L);
                    }
                    catch (InterruptedException ex) {}
                }
                nextHostIndex = newNextHostIndex;
            }
        } while (attempts < this.retriesAllDown && !gotConnection);
        if (!gotConnection) {
            throw lastExceptionCaught;
        }
    }
    
    synchronized void fallBackToPrimaryIfAvailable() {
        JdbcConnection connection = null;
        try {
            connection = this.createConnectionForHostIndex(this.primaryHostIndex);
            this.switchCurrentConnectionTo(this.primaryHostIndex, connection);
        }
        catch (SQLException e1) {
            if (connection != null) {
                try {
                    connection.close();
                }
                catch (SQLException ex) {}
            }
            this.resetAutoFallBackCounters();
        }
    }
    
    private int nextHost(final int currHostIdx, final boolean vouchForPrimaryHost) {
        int nextHostIdx = (currHostIdx + 1) % this.hostsList.size();
        if (this.isPrimaryHostIndex(nextHostIdx) && this.isConnected() && !vouchForPrimaryHost && this.enableFallBackToPrimaryHost && !this.readyToFallBackToPrimaryHost()) {
            nextHostIdx = this.nextHost(nextHostIdx, vouchForPrimaryHost);
        }
        return nextHostIdx;
    }
    
    synchronized void incrementQueriesIssuedSinceFailover() {
        ++this.queriesIssuedSinceFailover;
    }
    
    synchronized boolean readyToFallBackToPrimaryHost() {
        return this.enableFallBackToPrimaryHost && this.connectedToSecondaryHost() && (this.secondsBeforeRetryPrimaryHostIsMet() || this.queriesBeforeRetryPrimaryHostIsMet());
    }
    
    synchronized boolean isConnected() {
        return this.currentHostIndex != -1;
    }
    
    synchronized boolean isPrimaryHostIndex(final int hostIndex) {
        return hostIndex == this.primaryHostIndex;
    }
    
    synchronized boolean connectedToPrimaryHost() {
        return this.isPrimaryHostIndex(this.currentHostIndex);
    }
    
    synchronized boolean connectedToSecondaryHost() {
        return this.currentHostIndex >= 0 && !this.isPrimaryHostIndex(this.currentHostIndex);
    }
    
    private synchronized boolean secondsBeforeRetryPrimaryHostIsMet() {
        return this.secondsBeforeRetryPrimaryHost > 0 && Util.secondsSinceMillis(this.primaryHostFailTimeMillis) >= this.secondsBeforeRetryPrimaryHost;
    }
    
    private synchronized boolean queriesBeforeRetryPrimaryHostIsMet() {
        return this.queriesBeforeRetryPrimaryHost > 0L && this.queriesIssuedSinceFailover >= this.queriesBeforeRetryPrimaryHost;
    }
    
    private synchronized void resetAutoFallBackCounters() {
        this.primaryHostFailTimeMillis = System.currentTimeMillis();
        this.queriesIssuedSinceFailover = 0L;
    }
    
    @Override
    synchronized void doClose() throws SQLException {
        this.currentConnection.close();
    }
    
    @Override
    synchronized void doAbortInternal() throws SQLException {
        this.currentConnection.abortInternal();
    }
    
    @Override
    synchronized void doAbort(final Executor executor) throws SQLException {
        this.currentConnection.abort(executor);
    }
    
    public synchronized Object invokeMore(final Object proxy, final Method method, final Object[] args) throws Throwable {
        final String methodName = method.getName();
        if ("setReadOnly".equals(methodName)) {
            this.explicitlyReadOnly = (Boolean)args[0];
            if (this.failoverReadOnly && this.connectedToSecondaryHost()) {
                return null;
            }
        }
        if (this.isClosed && !this.allowedOnClosedConnection(method)) {
            if (!this.autoReconnect || this.closedExplicitly) {
                String reason = "No operations allowed after connection closed.";
                if (this.closedReason != null) {
                    reason = reason + "  " + this.closedReason;
                }
                throw SQLError.createSQLException(reason, "08003", null);
            }
            this.currentHostIndex = -1;
            this.pickNewConnection();
            this.isClosed = false;
            this.closedReason = null;
        }
        Object result = null;
        try {
            result = method.invoke(this.thisAsConnection, args);
            result = this.proxyIfReturnTypeIsJdbcInterface(method.getReturnType(), result);
        }
        catch (InvocationTargetException e) {
            this.dealWithInvocationException(e);
        }
        if ("setAutoCommit".equals(methodName)) {
            this.explicitlyAutoCommit = (boolean)args[0];
        }
        if ((this.explicitlyAutoCommit || "commit".equals(methodName) || "rollback".equals(methodName)) && this.readyToFallBackToPrimaryHost()) {
            this.fallBackToPrimaryIfAvailable();
        }
        return result;
    }
    
    class FailoverJdbcInterfaceProxy extends JdbcInterfaceProxy
    {
        FailoverJdbcInterfaceProxy(final Object toInvokeOn) {
            super(toInvokeOn);
        }
        
        @Override
        public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
            final String methodName = method.getName();
            final boolean isExecute = methodName.startsWith("execute");
            if (FailoverConnectionProxy.this.connectedToSecondaryHost() && isExecute) {
                FailoverConnectionProxy.this.incrementQueriesIssuedSinceFailover();
            }
            final Object result = super.invoke(proxy, method, args);
            if (FailoverConnectionProxy.this.explicitlyAutoCommit && isExecute && FailoverConnectionProxy.this.readyToFallBackToPrimaryHost()) {
                FailoverConnectionProxy.this.fallBackToPrimaryIfAvailable();
            }
            return result;
        }
    }
}
