
package com.mysql.cj.jdbc;

import com.mysql.cj.conf.PropertySet;
import com.mysql.cj.jdbc.result.ResultSetInternalMethods;
import com.mysql.cj.interceptors.QueryInterceptor;
import java.util.List;
import com.mysql.cj.ServerVersion;
import com.mysql.cj.jdbc.result.CachedResultSetMetaData;
import com.mysql.cj.Session;
import java.sql.Wrapper;
import java.sql.Connection;
import com.mysql.cj.MysqlConnection;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.sql.Struct;
import java.sql.Array;
import java.sql.SQLClientInfoException;
import java.sql.SQLXML;
import java.sql.NClob;
import java.sql.Blob;
import java.sql.Clob;
import java.util.concurrent.Executor;
import java.util.Properties;
import com.mysql.cj.exceptions.ExceptionInterceptor;
import com.mysql.cj.exceptions.ExceptionFactory;
import com.mysql.cj.exceptions.ConnectionIsClosedException;
import com.mysql.cj.conf.PropertyKey;
import java.sql.PreparedStatement;
import java.sql.CallableStatement;
import java.sql.Statement;
import java.sql.SQLWarning;
import java.util.Map;
import java.sql.Savepoint;
import java.sql.DatabaseMetaData;
import com.mysql.cj.exceptions.CJException;
import com.mysql.cj.jdbc.exceptions.SQLExceptionsMapping;
import com.mysql.cj.jdbc.exceptions.SQLError;
import com.mysql.cj.Messages;
import java.sql.SQLException;

public class ConnectionWrapper extends WrapperBase implements JdbcConnection
{
    protected JdbcConnection mc;
    private String invalidHandleStr;
    private boolean closed;
    private boolean isForXa;
    
    protected static ConnectionWrapper getInstance(final MysqlPooledConnection mysqlPooledConnection, final JdbcConnection mysqlConnection, final boolean forXa) throws SQLException {
        return new ConnectionWrapper(mysqlPooledConnection, mysqlConnection, forXa);
    }
    
    public ConnectionWrapper(final MysqlPooledConnection mysqlPooledConnection, final JdbcConnection mysqlConnection, final boolean forXa) throws SQLException {
        super(mysqlPooledConnection);
        this.mc = null;
        this.invalidHandleStr = "Logical handle no longer valid";
        this.mc = mysqlConnection;
        this.closed = false;
        this.isForXa = forXa;
        if (this.isForXa) {
            this.setInGlobalTx(false);
        }
    }
    
    @Override
    public void setAutoCommit(final boolean autoCommit) throws SQLException {
        try {
            this.checkClosed();
            if (autoCommit && this.isInGlobalTx()) {
                throw SQLError.createSQLException(Messages.getString("ConnectionWrapper.0"), "2D000", 1401, this.exceptionInterceptor);
            }
            try {
                this.mc.setAutoCommit(autoCommit);
            }
            catch (SQLException sqlException) {
                this.checkAndFireConnectionError(sqlException);
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, super.exceptionInterceptor);
        }
    }
    
    @Override
    public boolean getAutoCommit() throws SQLException {
        try {
            this.checkClosed();
            try {
                return this.mc.getAutoCommit();
            }
            catch (SQLException sqlException) {
                this.checkAndFireConnectionError(sqlException);
                return false;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, super.exceptionInterceptor);
        }
    }
    
    @Override
    public void setCatalog(final String catalog) throws SQLException {
        try {
            this.checkClosed();
            try {
                this.mc.setCatalog(catalog);
            }
            catch (SQLException sqlException) {
                this.checkAndFireConnectionError(sqlException);
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, super.exceptionInterceptor);
        }
    }
    
    @Override
    public String getCatalog() throws SQLException {
        try {
            this.checkClosed();
            try {
                return this.mc.getCatalog();
            }
            catch (SQLException sqlException) {
                this.checkAndFireConnectionError(sqlException);
                return null;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, super.exceptionInterceptor);
        }
    }
    
    @Override
    public boolean isClosed() throws SQLException {
        try {
            return this.closed || this.mc.isClosed();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, super.exceptionInterceptor);
        }
    }
    
    @Override
    public boolean isMasterConnection() {
        return this.mc.isMasterConnection();
    }
    
    @Override
    public void setHoldability(final int arg0) throws SQLException {
        try {
            this.checkClosed();
            try {
                this.mc.setHoldability(arg0);
            }
            catch (SQLException sqlException) {
                this.checkAndFireConnectionError(sqlException);
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, super.exceptionInterceptor);
        }
    }
    
    @Override
    public int getHoldability() throws SQLException {
        try {
            this.checkClosed();
            try {
                return this.mc.getHoldability();
            }
            catch (SQLException sqlException) {
                this.checkAndFireConnectionError(sqlException);
                return 1;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, super.exceptionInterceptor);
        }
    }
    
    @Override
    public long getIdleFor() {
        return this.mc.getIdleFor();
    }
    
    @Override
    public DatabaseMetaData getMetaData() throws SQLException {
        try {
            this.checkClosed();
            try {
                return this.mc.getMetaData();
            }
            catch (SQLException sqlException) {
                this.checkAndFireConnectionError(sqlException);
                return null;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, super.exceptionInterceptor);
        }
    }
    
    @Override
    public void setReadOnly(final boolean readOnly) throws SQLException {
        try {
            this.checkClosed();
            try {
                this.mc.setReadOnly(readOnly);
            }
            catch (SQLException sqlException) {
                this.checkAndFireConnectionError(sqlException);
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, super.exceptionInterceptor);
        }
    }
    
    @Override
    public boolean isReadOnly() throws SQLException {
        try {
            this.checkClosed();
            try {
                return this.mc.isReadOnly();
            }
            catch (SQLException sqlException) {
                this.checkAndFireConnectionError(sqlException);
                return false;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, super.exceptionInterceptor);
        }
    }
    
    @Override
    public Savepoint setSavepoint() throws SQLException {
        try {
            this.checkClosed();
            if (this.isInGlobalTx()) {
                throw SQLError.createSQLException(Messages.getString("ConnectionWrapper.0"), "2D000", 1401, this.exceptionInterceptor);
            }
            try {
                return this.mc.setSavepoint();
            }
            catch (SQLException sqlException) {
                this.checkAndFireConnectionError(sqlException);
                return null;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, super.exceptionInterceptor);
        }
    }
    
    @Override
    public Savepoint setSavepoint(final String arg0) throws SQLException {
        try {
            this.checkClosed();
            if (this.isInGlobalTx()) {
                throw SQLError.createSQLException(Messages.getString("ConnectionWrapper.0"), "2D000", 1401, this.exceptionInterceptor);
            }
            try {
                return this.mc.setSavepoint(arg0);
            }
            catch (SQLException sqlException) {
                this.checkAndFireConnectionError(sqlException);
                return null;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, super.exceptionInterceptor);
        }
    }
    
    @Override
    public void setTransactionIsolation(final int level) throws SQLException {
        try {
            this.checkClosed();
            try {
                this.mc.setTransactionIsolation(level);
            }
            catch (SQLException sqlException) {
                this.checkAndFireConnectionError(sqlException);
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, super.exceptionInterceptor);
        }
    }
    
    @Override
    public int getTransactionIsolation() throws SQLException {
        try {
            this.checkClosed();
            try {
                return this.mc.getTransactionIsolation();
            }
            catch (SQLException sqlException) {
                this.checkAndFireConnectionError(sqlException);
                return 4;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, super.exceptionInterceptor);
        }
    }
    
    @Override
    public Map<String, Class<?>> getTypeMap() throws SQLException {
        try {
            this.checkClosed();
            try {
                return this.mc.getTypeMap();
            }
            catch (SQLException sqlException) {
                this.checkAndFireConnectionError(sqlException);
                return null;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, super.exceptionInterceptor);
        }
    }
    
    @Override
    public SQLWarning getWarnings() throws SQLException {
        try {
            this.checkClosed();
            try {
                return this.mc.getWarnings();
            }
            catch (SQLException sqlException) {
                this.checkAndFireConnectionError(sqlException);
                return null;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, super.exceptionInterceptor);
        }
    }
    
    @Override
    public void clearWarnings() throws SQLException {
        try {
            this.checkClosed();
            try {
                this.mc.clearWarnings();
            }
            catch (SQLException sqlException) {
                this.checkAndFireConnectionError(sqlException);
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, super.exceptionInterceptor);
        }
    }
    
    @Override
    public void close() throws SQLException {
        try {
            try {
                this.close(true);
            }
            finally {
                this.unwrappedInterfaces = null;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, super.exceptionInterceptor);
        }
    }
    
    @Override
    public void commit() throws SQLException {
        try {
            this.checkClosed();
            if (this.isInGlobalTx()) {
                throw SQLError.createSQLException(Messages.getString("ConnectionWrapper.1"), "2D000", 1401, this.exceptionInterceptor);
            }
            try {
                this.mc.commit();
            }
            catch (SQLException sqlException) {
                this.checkAndFireConnectionError(sqlException);
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, super.exceptionInterceptor);
        }
    }
    
    @Override
    public Statement createStatement() throws SQLException {
        try {
            this.checkClosed();
            try {
                return StatementWrapper.getInstance(this, this.pooledConnection, this.mc.createStatement());
            }
            catch (SQLException sqlException) {
                this.checkAndFireConnectionError(sqlException);
                return null;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, super.exceptionInterceptor);
        }
    }
    
    @Override
    public Statement createStatement(final int resultSetType, final int resultSetConcurrency) throws SQLException {
        try {
            this.checkClosed();
            try {
                return StatementWrapper.getInstance(this, this.pooledConnection, this.mc.createStatement(resultSetType, resultSetConcurrency));
            }
            catch (SQLException sqlException) {
                this.checkAndFireConnectionError(sqlException);
                return null;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, super.exceptionInterceptor);
        }
    }
    
    @Override
    public Statement createStatement(final int arg0, final int arg1, final int arg2) throws SQLException {
        try {
            this.checkClosed();
            try {
                return StatementWrapper.getInstance(this, this.pooledConnection, this.mc.createStatement(arg0, arg1, arg2));
            }
            catch (SQLException sqlException) {
                this.checkAndFireConnectionError(sqlException);
                return null;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, super.exceptionInterceptor);
        }
    }
    
    @Override
    public String nativeSQL(final String sql) throws SQLException {
        try {
            this.checkClosed();
            try {
                return this.mc.nativeSQL(sql);
            }
            catch (SQLException sqlException) {
                this.checkAndFireConnectionError(sqlException);
                return null;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, super.exceptionInterceptor);
        }
    }
    
    @Override
    public CallableStatement prepareCall(final String sql) throws SQLException {
        try {
            this.checkClosed();
            try {
                return CallableStatementWrapper.getInstance(this, this.pooledConnection, this.mc.prepareCall(sql));
            }
            catch (SQLException sqlException) {
                this.checkAndFireConnectionError(sqlException);
                return null;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, super.exceptionInterceptor);
        }
    }
    
    @Override
    public CallableStatement prepareCall(final String sql, final int resultSetType, final int resultSetConcurrency) throws SQLException {
        try {
            this.checkClosed();
            try {
                return CallableStatementWrapper.getInstance(this, this.pooledConnection, this.mc.prepareCall(sql, resultSetType, resultSetConcurrency));
            }
            catch (SQLException sqlException) {
                this.checkAndFireConnectionError(sqlException);
                return null;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, super.exceptionInterceptor);
        }
    }
    
    @Override
    public CallableStatement prepareCall(final String arg0, final int arg1, final int arg2, final int arg3) throws SQLException {
        try {
            this.checkClosed();
            try {
                return CallableStatementWrapper.getInstance(this, this.pooledConnection, this.mc.prepareCall(arg0, arg1, arg2, arg3));
            }
            catch (SQLException sqlException) {
                this.checkAndFireConnectionError(sqlException);
                return null;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, super.exceptionInterceptor);
        }
    }
    
    public PreparedStatement clientPrepare(final String sql) throws SQLException {
        try {
            this.checkClosed();
            try {
                return new PreparedStatementWrapper(this, this.pooledConnection, this.mc.clientPrepareStatement(sql));
            }
            catch (SQLException sqlException) {
                this.checkAndFireConnectionError(sqlException);
                return null;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, super.exceptionInterceptor);
        }
    }
    
    public PreparedStatement clientPrepare(final String sql, final int resultSetType, final int resultSetConcurrency) throws SQLException {
        try {
            this.checkClosed();
            try {
                return new PreparedStatementWrapper(this, this.pooledConnection, this.mc.clientPrepareStatement(sql, resultSetType, resultSetConcurrency));
            }
            catch (SQLException sqlException) {
                this.checkAndFireConnectionError(sqlException);
                return null;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, super.exceptionInterceptor);
        }
    }
    
    @Override
    public PreparedStatement prepareStatement(final String sql) throws SQLException {
        try {
            this.checkClosed();
            PreparedStatement res = null;
            try {
                res = PreparedStatementWrapper.getInstance(this, this.pooledConnection, this.mc.prepareStatement(sql));
            }
            catch (SQLException sqlException) {
                this.checkAndFireConnectionError(sqlException);
            }
            return res;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, super.exceptionInterceptor);
        }
    }
    
    @Override
    public PreparedStatement prepareStatement(final String sql, final int resultSetType, final int resultSetConcurrency) throws SQLException {
        try {
            this.checkClosed();
            try {
                return PreparedStatementWrapper.getInstance(this, this.pooledConnection, this.mc.prepareStatement(sql, resultSetType, resultSetConcurrency));
            }
            catch (SQLException sqlException) {
                this.checkAndFireConnectionError(sqlException);
                return null;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, super.exceptionInterceptor);
        }
    }
    
    @Override
    public PreparedStatement prepareStatement(final String arg0, final int arg1, final int arg2, final int arg3) throws SQLException {
        try {
            this.checkClosed();
            try {
                return PreparedStatementWrapper.getInstance(this, this.pooledConnection, this.mc.prepareStatement(arg0, arg1, arg2, arg3));
            }
            catch (SQLException sqlException) {
                this.checkAndFireConnectionError(sqlException);
                return null;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, super.exceptionInterceptor);
        }
    }
    
    @Override
    public PreparedStatement prepareStatement(final String arg0, final int arg1) throws SQLException {
        try {
            this.checkClosed();
            try {
                return PreparedStatementWrapper.getInstance(this, this.pooledConnection, this.mc.prepareStatement(arg0, arg1));
            }
            catch (SQLException sqlException) {
                this.checkAndFireConnectionError(sqlException);
                return null;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, super.exceptionInterceptor);
        }
    }
    
    @Override
    public PreparedStatement prepareStatement(final String arg0, final int[] arg1) throws SQLException {
        try {
            this.checkClosed();
            try {
                return PreparedStatementWrapper.getInstance(this, this.pooledConnection, this.mc.prepareStatement(arg0, arg1));
            }
            catch (SQLException sqlException) {
                this.checkAndFireConnectionError(sqlException);
                return null;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, super.exceptionInterceptor);
        }
    }
    
    @Override
    public PreparedStatement prepareStatement(final String arg0, final String[] arg1) throws SQLException {
        try {
            this.checkClosed();
            try {
                return PreparedStatementWrapper.getInstance(this, this.pooledConnection, this.mc.prepareStatement(arg0, arg1));
            }
            catch (SQLException sqlException) {
                this.checkAndFireConnectionError(sqlException);
                return null;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, super.exceptionInterceptor);
        }
    }
    
    @Override
    public void releaseSavepoint(final Savepoint arg0) throws SQLException {
        try {
            this.checkClosed();
            try {
                this.mc.releaseSavepoint(arg0);
            }
            catch (SQLException sqlException) {
                this.checkAndFireConnectionError(sqlException);
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, super.exceptionInterceptor);
        }
    }
    
    @Override
    public void rollback() throws SQLException {
        try {
            this.checkClosed();
            if (this.isInGlobalTx()) {
                throw SQLError.createSQLException(Messages.getString("ConnectionWrapper.2"), "2D000", 1401, this.exceptionInterceptor);
            }
            try {
                this.mc.rollback();
            }
            catch (SQLException sqlException) {
                this.checkAndFireConnectionError(sqlException);
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, super.exceptionInterceptor);
        }
    }
    
    @Override
    public void rollback(final Savepoint arg0) throws SQLException {
        try {
            this.checkClosed();
            if (this.isInGlobalTx()) {
                throw SQLError.createSQLException(Messages.getString("ConnectionWrapper.2"), "2D000", 1401, this.exceptionInterceptor);
            }
            try {
                this.mc.rollback(arg0);
            }
            catch (SQLException sqlException) {
                this.checkAndFireConnectionError(sqlException);
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, super.exceptionInterceptor);
        }
    }
    
    @Override
    public boolean isSameResource(final JdbcConnection c) {
        if (c instanceof ConnectionWrapper) {
            return this.mc.isSameResource(((ConnectionWrapper)c).mc);
        }
        return this.mc.isSameResource(c);
    }
    
    protected void close(final boolean fireClosedEvent) throws SQLException {
        synchronized (this.pooledConnection) {
            if (this.closed) {
                return;
            }
            if (!this.isInGlobalTx() && this.mc.getPropertySet().getBooleanProperty(PropertyKey.rollbackOnPooledClose).getValue() && !this.getAutoCommit()) {
                this.rollback();
            }
            if (fireClosedEvent) {
                this.pooledConnection.callConnectionEventListeners(2, null);
            }
            this.closed = true;
        }
    }
    
    @Override
    public void checkClosed() {
        if (this.closed) {
            throw ExceptionFactory.createException(ConnectionIsClosedException.class, this.invalidHandleStr, this.exceptionInterceptor);
        }
    }
    
    @Override
    public boolean isInGlobalTx() {
        return this.mc.isInGlobalTx();
    }
    
    @Override
    public void setInGlobalTx(final boolean flag) {
        this.mc.setInGlobalTx(flag);
    }
    
    @Override
    public void ping() throws SQLException {
        try {
            if (this.mc != null) {
                this.mc.ping();
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, super.exceptionInterceptor);
        }
    }
    
    @Override
    public void changeUser(final String userName, final String newPassword) throws SQLException {
        try {
            this.checkClosed();
            try {
                this.mc.changeUser(userName, newPassword);
            }
            catch (SQLException sqlException) {
                this.checkAndFireConnectionError(sqlException);
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, super.exceptionInterceptor);
        }
    }
    
    @Deprecated
    @Override
    public void clearHasTriedMaster() {
        this.mc.clearHasTriedMaster();
    }
    
    @Override
    public PreparedStatement clientPrepareStatement(final String sql) throws SQLException {
        try {
            this.checkClosed();
            try {
                return PreparedStatementWrapper.getInstance(this, this.pooledConnection, this.mc.clientPrepareStatement(sql));
            }
            catch (SQLException sqlException) {
                this.checkAndFireConnectionError(sqlException);
                return null;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, super.exceptionInterceptor);
        }
    }
    
    @Override
    public PreparedStatement clientPrepareStatement(final String sql, final int autoGenKeyIndex) throws SQLException {
        try {
            this.checkClosed();
            try {
                return PreparedStatementWrapper.getInstance(this, this.pooledConnection, this.mc.clientPrepareStatement(sql, autoGenKeyIndex));
            }
            catch (SQLException sqlException) {
                this.checkAndFireConnectionError(sqlException);
                return null;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, super.exceptionInterceptor);
        }
    }
    
    @Override
    public PreparedStatement clientPrepareStatement(final String sql, final int resultSetType, final int resultSetConcurrency) throws SQLException {
        try {
            this.checkClosed();
            try {
                return PreparedStatementWrapper.getInstance(this, this.pooledConnection, this.mc.clientPrepareStatement(sql, resultSetType, resultSetConcurrency));
            }
            catch (SQLException sqlException) {
                this.checkAndFireConnectionError(sqlException);
                return null;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, super.exceptionInterceptor);
        }
    }
    
    @Override
    public PreparedStatement clientPrepareStatement(final String sql, final int resultSetType, final int resultSetConcurrency, final int resultSetHoldability) throws SQLException {
        try {
            this.checkClosed();
            try {
                return PreparedStatementWrapper.getInstance(this, this.pooledConnection, this.mc.clientPrepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability));
            }
            catch (SQLException sqlException) {
                this.checkAndFireConnectionError(sqlException);
                return null;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, super.exceptionInterceptor);
        }
    }
    
    @Override
    public PreparedStatement clientPrepareStatement(final String sql, final int[] autoGenKeyIndexes) throws SQLException {
        try {
            this.checkClosed();
            try {
                return PreparedStatementWrapper.getInstance(this, this.pooledConnection, this.mc.clientPrepareStatement(sql, autoGenKeyIndexes));
            }
            catch (SQLException sqlException) {
                this.checkAndFireConnectionError(sqlException);
                return null;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, super.exceptionInterceptor);
        }
    }
    
    @Override
    public PreparedStatement clientPrepareStatement(final String sql, final String[] autoGenKeyColNames) throws SQLException {
        try {
            this.checkClosed();
            try {
                return PreparedStatementWrapper.getInstance(this, this.pooledConnection, this.mc.clientPrepareStatement(sql, autoGenKeyColNames));
            }
            catch (SQLException sqlException) {
                this.checkAndFireConnectionError(sqlException);
                return null;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, super.exceptionInterceptor);
        }
    }
    
    @Override
    public int getActiveStatementCount() {
        return this.mc.getActiveStatementCount();
    }
    
    @Override
    public String getStatementComment() {
        return this.mc.getStatementComment();
    }
    
    @Deprecated
    @Override
    public boolean hasTriedMaster() {
        return this.mc.hasTriedMaster();
    }
    
    @Override
    public boolean lowerCaseTableNames() {
        return this.mc.lowerCaseTableNames();
    }
    
    @Override
    public void resetServerState() throws SQLException {
        try {
            this.checkClosed();
            try {
                this.mc.resetServerState();
            }
            catch (SQLException sqlException) {
                this.checkAndFireConnectionError(sqlException);
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, super.exceptionInterceptor);
        }
    }
    
    @Override
    public PreparedStatement serverPrepareStatement(final String sql) throws SQLException {
        try {
            this.checkClosed();
            try {
                return PreparedStatementWrapper.getInstance(this, this.pooledConnection, this.mc.serverPrepareStatement(sql));
            }
            catch (SQLException sqlException) {
                this.checkAndFireConnectionError(sqlException);
                return null;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, super.exceptionInterceptor);
        }
    }
    
    @Override
    public PreparedStatement serverPrepareStatement(final String sql, final int autoGenKeyIndex) throws SQLException {
        try {
            this.checkClosed();
            try {
                return PreparedStatementWrapper.getInstance(this, this.pooledConnection, this.mc.serverPrepareStatement(sql, autoGenKeyIndex));
            }
            catch (SQLException sqlException) {
                this.checkAndFireConnectionError(sqlException);
                return null;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, super.exceptionInterceptor);
        }
    }
    
    @Override
    public PreparedStatement serverPrepareStatement(final String sql, final int resultSetType, final int resultSetConcurrency) throws SQLException {
        try {
            this.checkClosed();
            try {
                return PreparedStatementWrapper.getInstance(this, this.pooledConnection, this.mc.serverPrepareStatement(sql, resultSetType, resultSetConcurrency));
            }
            catch (SQLException sqlException) {
                this.checkAndFireConnectionError(sqlException);
                return null;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, super.exceptionInterceptor);
        }
    }
    
    @Override
    public PreparedStatement serverPrepareStatement(final String sql, final int resultSetType, final int resultSetConcurrency, final int resultSetHoldability) throws SQLException {
        try {
            this.checkClosed();
            try {
                return PreparedStatementWrapper.getInstance(this, this.pooledConnection, this.mc.serverPrepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability));
            }
            catch (SQLException sqlException) {
                this.checkAndFireConnectionError(sqlException);
                return null;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, super.exceptionInterceptor);
        }
    }
    
    @Override
    public PreparedStatement serverPrepareStatement(final String sql, final int[] autoGenKeyIndexes) throws SQLException {
        try {
            this.checkClosed();
            try {
                return PreparedStatementWrapper.getInstance(this, this.pooledConnection, this.mc.serverPrepareStatement(sql, autoGenKeyIndexes));
            }
            catch (SQLException sqlException) {
                this.checkAndFireConnectionError(sqlException);
                return null;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, super.exceptionInterceptor);
        }
    }
    
    @Override
    public PreparedStatement serverPrepareStatement(final String sql, final String[] autoGenKeyColNames) throws SQLException {
        try {
            this.checkClosed();
            try {
                return PreparedStatementWrapper.getInstance(this, this.pooledConnection, this.mc.serverPrepareStatement(sql, autoGenKeyColNames));
            }
            catch (SQLException sqlException) {
                this.checkAndFireConnectionError(sqlException);
                return null;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, super.exceptionInterceptor);
        }
    }
    
    @Override
    public void setFailedOver(final boolean flag) {
        this.mc.setFailedOver(flag);
    }
    
    @Override
    public void setStatementComment(final String comment) {
        this.mc.setStatementComment(comment);
    }
    
    @Override
    public void shutdownServer() throws SQLException {
        try {
            this.checkClosed();
            try {
                this.mc.shutdownServer();
            }
            catch (SQLException sqlException) {
                this.checkAndFireConnectionError(sqlException);
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, super.exceptionInterceptor);
        }
    }
    
    @Override
    public int getAutoIncrementIncrement() {
        return this.mc.getAutoIncrementIncrement();
    }
    
    @Override
    public ExceptionInterceptor getExceptionInterceptor() {
        return this.pooledConnection.getExceptionInterceptor();
    }
    
    @Override
    public boolean hasSameProperties(final JdbcConnection c) {
        return this.mc.hasSameProperties(c);
    }
    
    @Override
    public Properties getProperties() {
        return this.mc.getProperties();
    }
    
    @Override
    public String getHost() {
        return this.mc.getHost();
    }
    
    @Override
    public void setProxy(final JdbcConnection conn) {
        this.mc.setProxy(conn);
    }
    
    @Override
    public void setTypeMap(final Map<String, Class<?>> map) throws SQLException {
        try {
            this.checkClosed();
            try {
                this.mc.setTypeMap(map);
            }
            catch (SQLException sqlException) {
                this.checkAndFireConnectionError(sqlException);
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, super.exceptionInterceptor);
        }
    }
    
    @Override
    public boolean isServerLocal() throws SQLException {
        try {
            return this.mc.isServerLocal();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, super.exceptionInterceptor);
        }
    }
    
    @Override
    public void setSchema(final String schema) throws SQLException {
        try {
            this.mc.setSchema(schema);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, super.exceptionInterceptor);
        }
    }
    
    @Override
    public String getSchema() throws SQLException {
        try {
            return this.mc.getSchema();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, super.exceptionInterceptor);
        }
    }
    
    @Override
    public void abort(final Executor executor) throws SQLException {
        try {
            this.mc.abort(executor);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, super.exceptionInterceptor);
        }
    }
    
    @Override
    public void setNetworkTimeout(final Executor executor, final int milliseconds) throws SQLException {
        try {
            this.mc.setNetworkTimeout(executor, milliseconds);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, super.exceptionInterceptor);
        }
    }
    
    @Override
    public int getNetworkTimeout() throws SQLException {
        try {
            return this.mc.getNetworkTimeout();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, super.exceptionInterceptor);
        }
    }
    
    @Override
    public void abortInternal() throws SQLException {
        try {
            this.mc.abortInternal();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, super.exceptionInterceptor);
        }
    }
    
    @Override
    public Object getConnectionMutex() {
        return this.mc.getConnectionMutex();
    }
    
    @Override
    public int getSessionMaxRows() {
        return this.mc.getSessionMaxRows();
    }
    
    @Override
    public void setSessionMaxRows(final int max) throws SQLException {
        try {
            this.mc.setSessionMaxRows(max);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, super.exceptionInterceptor);
        }
    }
    
    @Override
    public Clob createClob() throws SQLException {
        try {
            this.checkClosed();
            try {
                return this.mc.createClob();
            }
            catch (SQLException sqlException) {
                this.checkAndFireConnectionError(sqlException);
                return null;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, super.exceptionInterceptor);
        }
    }
    
    @Override
    public Blob createBlob() throws SQLException {
        try {
            this.checkClosed();
            try {
                return this.mc.createBlob();
            }
            catch (SQLException sqlException) {
                this.checkAndFireConnectionError(sqlException);
                return null;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, super.exceptionInterceptor);
        }
    }
    
    @Override
    public NClob createNClob() throws SQLException {
        try {
            this.checkClosed();
            try {
                return this.mc.createNClob();
            }
            catch (SQLException sqlException) {
                this.checkAndFireConnectionError(sqlException);
                return null;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, super.exceptionInterceptor);
        }
    }
    
    @Override
    public SQLXML createSQLXML() throws SQLException {
        try {
            this.checkClosed();
            try {
                return this.mc.createSQLXML();
            }
            catch (SQLException sqlException) {
                this.checkAndFireConnectionError(sqlException);
                return null;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, super.exceptionInterceptor);
        }
    }
    
    @Override
    public synchronized boolean isValid(final int timeout) throws SQLException {
        try {
            try {
                return this.mc.isValid(timeout);
            }
            catch (SQLException sqlException) {
                this.checkAndFireConnectionError(sqlException);
                return false;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, super.exceptionInterceptor);
        }
    }
    
    @Override
    public void setClientInfo(final String name, final String value) throws SQLClientInfoException {
        try {
            try {
                this.checkClosed();
                this.mc.setClientInfo(name, value);
            }
            catch (SQLException sqlException) {
                try {
                    this.checkAndFireConnectionError(sqlException);
                }
                catch (SQLException sqlEx2) {
                    final SQLClientInfoException clientEx = new SQLClientInfoException();
                    clientEx.initCause(sqlEx2);
                    throw clientEx;
                }
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, super.exceptionInterceptor);
        }
    }
    
    @Override
    public void setClientInfo(final Properties properties) throws SQLClientInfoException {
        try {
            try {
                this.checkClosed();
                this.mc.setClientInfo(properties);
            }
            catch (SQLException sqlException) {
                try {
                    this.checkAndFireConnectionError(sqlException);
                }
                catch (SQLException sqlEx2) {
                    final SQLClientInfoException clientEx = new SQLClientInfoException();
                    clientEx.initCause(sqlEx2);
                    throw clientEx;
                }
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, super.exceptionInterceptor);
        }
    }
    
    @Override
    public String getClientInfo(final String name) throws SQLException {
        try {
            this.checkClosed();
            try {
                return this.mc.getClientInfo(name);
            }
            catch (SQLException sqlException) {
                this.checkAndFireConnectionError(sqlException);
                return null;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, super.exceptionInterceptor);
        }
    }
    
    @Override
    public Properties getClientInfo() throws SQLException {
        try {
            this.checkClosed();
            try {
                return this.mc.getClientInfo();
            }
            catch (SQLException sqlException) {
                this.checkAndFireConnectionError(sqlException);
                return null;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, super.exceptionInterceptor);
        }
    }
    
    @Override
    public Array createArrayOf(final String typeName, final Object[] elements) throws SQLException {
        try {
            this.checkClosed();
            try {
                return this.mc.createArrayOf(typeName, elements);
            }
            catch (SQLException sqlException) {
                this.checkAndFireConnectionError(sqlException);
                return null;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, super.exceptionInterceptor);
        }
    }
    
    @Override
    public Struct createStruct(final String typeName, final Object[] attributes) throws SQLException {
        try {
            this.checkClosed();
            try {
                return this.mc.createStruct(typeName, attributes);
            }
            catch (SQLException sqlException) {
                this.checkAndFireConnectionError(sqlException);
                return null;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, super.exceptionInterceptor);
        }
    }
    
    @Override
    public synchronized <T> T unwrap(final Class<T> iface) throws SQLException {
        try {
            try {
                if ("java.sql.Connection".equals(iface.getName()) || "java.sql.Wrapper.class".equals(iface.getName())) {
                    return iface.cast(this);
                }
                if (this.unwrappedInterfaces == null) {
                    this.unwrappedInterfaces = new HashMap<Class<?>, Object>();
                }
                Object cachedUnwrapped = this.unwrappedInterfaces.get(iface);
                if (cachedUnwrapped == null) {
                    cachedUnwrapped = Proxy.newProxyInstance(this.mc.getClass().getClassLoader(), new Class[] { iface }, new ConnectionErrorFiringInvocationHandler(this.mc));
                    this.unwrappedInterfaces.put(iface, cachedUnwrapped);
                }
                return iface.cast(cachedUnwrapped);
            }
            catch (ClassCastException cce) {
                throw SQLError.createSQLException(Messages.getString("Common.UnableToUnwrap", new Object[] { iface.toString() }), "S1009", this.exceptionInterceptor);
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, super.exceptionInterceptor);
        }
    }
    
    @Override
    public boolean isWrapperFor(final Class<?> iface) throws SQLException {
        try {
            final boolean isInstance = iface.isInstance(this);
            return isInstance || iface.getName().equals(JdbcConnection.class.getName()) || iface.getName().equals(MysqlConnection.class.getName()) || iface.getName().equals(Connection.class.getName()) || iface.getName().equals(Wrapper.class.getName()) || iface.getName().equals(AutoCloseable.class.getName());
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, super.exceptionInterceptor);
        }
    }
    
    @Override
    public Session getSession() {
        return this.mc.getSession();
    }
    
    @Override
    public long getId() {
        return this.mc.getId();
    }
    
    @Override
    public String getURL() {
        return this.mc.getURL();
    }
    
    @Override
    public String getUser() {
        return this.mc.getUser();
    }
    
    @Override
    public void createNewIO(final boolean isForReconnect) {
        this.mc.createNewIO(isForReconnect);
    }
    
    @Override
    public boolean isProxySet() {
        return this.mc.isProxySet();
    }
    
    @Override
    public JdbcPropertySet getPropertySet() {
        return this.mc.getPropertySet();
    }
    
    @Override
    public CachedResultSetMetaData getCachedMetaData(final String sql) {
        return this.mc.getCachedMetaData(sql);
    }
    
    @Override
    public String getCharacterSetMetadata() {
        return this.mc.getCharacterSetMetadata();
    }
    
    @Override
    public Statement getMetadataSafeStatement() throws SQLException {
        try {
            return this.mc.getMetadataSafeStatement();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, super.exceptionInterceptor);
        }
    }
    
    @Override
    public ServerVersion getServerVersion() {
        return this.mc.getServerVersion();
    }
    
    @Override
    public List<QueryInterceptor> getQueryInterceptorsInstances() {
        return this.mc.getQueryInterceptorsInstances();
    }
    
    @Override
    public void initializeResultsMetadataFromCache(final String sql, final CachedResultSetMetaData cachedMetaData, final ResultSetInternalMethods resultSet) throws SQLException {
        try {
            this.mc.initializeResultsMetadataFromCache(sql, cachedMetaData, resultSet);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, super.exceptionInterceptor);
        }
    }
    
    @Override
    public void initializeSafeQueryInterceptors() throws SQLException {
        try {
            this.mc.initializeSafeQueryInterceptors();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, super.exceptionInterceptor);
        }
    }
    
    @Override
    public boolean isReadOnly(final boolean useSessionStatus) throws SQLException {
        try {
            this.checkClosed();
            return this.mc.isReadOnly(useSessionStatus);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, super.exceptionInterceptor);
        }
    }
    
    @Override
    public void pingInternal(final boolean checkForClosedConnection, final int timeoutMillis) throws SQLException {
        try {
            this.mc.pingInternal(checkForClosedConnection, timeoutMillis);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, super.exceptionInterceptor);
        }
    }
    
    @Override
    public void realClose(final boolean calledExplicitly, final boolean issueRollback, final boolean skipLocalTeardown, final Throwable reason) throws SQLException {
        try {
            this.mc.realClose(calledExplicitly, issueRollback, skipLocalTeardown, reason);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, super.exceptionInterceptor);
        }
    }
    
    @Override
    public void recachePreparedStatement(final JdbcPreparedStatement pstmt) throws SQLException {
        try {
            this.mc.recachePreparedStatement(pstmt);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, super.exceptionInterceptor);
        }
    }
    
    @Override
    public void decachePreparedStatement(final JdbcPreparedStatement pstmt) throws SQLException {
        try {
            this.mc.decachePreparedStatement(pstmt);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, super.exceptionInterceptor);
        }
    }
    
    @Override
    public void registerStatement(final JdbcStatement stmt) {
        this.mc.registerStatement(stmt);
    }
    
    @Override
    public void setReadOnlyInternal(final boolean readOnlyFlag) throws SQLException {
        try {
            this.mc.setReadOnlyInternal(readOnlyFlag);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, super.exceptionInterceptor);
        }
    }
    
    @Override
    public boolean storesLowerCaseTableName() {
        return this.mc.storesLowerCaseTableName();
    }
    
    @Override
    public void throwConnectionClosedException() throws SQLException {
        try {
            this.mc.throwConnectionClosedException();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, super.exceptionInterceptor);
        }
    }
    
    @Override
    public void transactionBegun() {
        this.mc.transactionBegun();
    }
    
    @Override
    public void transactionCompleted() {
        this.mc.transactionCompleted();
    }
    
    @Override
    public void unregisterStatement(final JdbcStatement stmt) {
        this.mc.unregisterStatement(stmt);
    }
    
    @Override
    public void unSafeQueryInterceptors() throws SQLException {
        try {
            this.mc.unSafeQueryInterceptors();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, super.exceptionInterceptor);
        }
    }
    
    @Override
    public JdbcConnection getMultiHostSafeProxy() {
        return this.mc.getMultiHostSafeProxy();
    }
    
    @Override
    public JdbcConnection getActiveMySQLConnection() {
        return this.mc.getActiveMySQLConnection();
    }
    
    @Override
    public ClientInfoProvider getClientInfoProviderImpl() throws SQLException {
        try {
            return this.mc.getClientInfoProviderImpl();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, super.exceptionInterceptor);
        }
    }
    
    @Override
    public String getHostPortPair() {
        return this.mc.getHostPortPair();
    }
    
    @Override
    public void normalClose() {
        this.mc.normalClose();
    }
    
    @Override
    public void cleanup(final Throwable whyCleanedUp) {
        this.mc.cleanup(whyCleanedUp);
    }
}
