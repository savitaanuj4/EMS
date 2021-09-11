
package com.mysql.cj.jdbc.ha;

import com.mysql.cj.conf.PropertySet;
import com.mysql.cj.jdbc.JdbcPropertySet;
import com.mysql.cj.jdbc.ClientInfoProvider;
import java.sql.NClob;
import java.sql.Clob;
import java.sql.Blob;
import com.mysql.cj.jdbc.exceptions.SQLError;
import com.mysql.cj.Messages;
import java.sql.SQLClientInfoException;
import java.sql.Struct;
import java.sql.Array;
import java.sql.SQLXML;
import java.util.concurrent.Executor;
import java.sql.Savepoint;
import com.mysql.cj.jdbc.JdbcStatement;
import com.mysql.cj.jdbc.JdbcPreparedStatement;
import java.sql.CallableStatement;
import com.mysql.cj.jdbc.result.ResultSetInternalMethods;
import java.sql.SQLWarning;
import java.util.Map;
import com.mysql.cj.interceptors.QueryInterceptor;
import java.util.List;
import com.mysql.cj.Session;
import com.mysql.cj.ServerVersion;
import java.util.Properties;
import java.sql.DatabaseMetaData;
import com.mysql.cj.exceptions.ExceptionInterceptor;
import com.mysql.cj.jdbc.result.CachedResultSetMetaData;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.mysql.cj.exceptions.CJException;
import com.mysql.cj.jdbc.exceptions.SQLExceptionsMapping;
import com.mysql.cj.jdbc.JdbcConnection;

public class MultiHostMySQLConnection implements JdbcConnection
{
    protected MultiHostConnectionProxy thisAsProxy;
    
    public MultiHostMySQLConnection(final MultiHostConnectionProxy proxy) {
        this.thisAsProxy = proxy;
    }
    
    public MultiHostConnectionProxy getThisAsProxy() {
        return this.thisAsProxy;
    }
    
    @Override
    public JdbcConnection getActiveMySQLConnection() {
        synchronized (this.thisAsProxy) {
            return this.thisAsProxy.currentConnection;
        }
    }
    
    @Override
    public void abortInternal() throws SQLException {
        try {
            this.getActiveMySQLConnection().abortInternal();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void changeUser(final String userName, final String newPassword) throws SQLException {
        try {
            this.getActiveMySQLConnection().changeUser(userName, newPassword);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void checkClosed() {
        this.getActiveMySQLConnection().checkClosed();
    }
    
    @Deprecated
    @Override
    public void clearHasTriedMaster() {
        this.getActiveMySQLConnection().clearHasTriedMaster();
    }
    
    @Override
    public void clearWarnings() throws SQLException {
        try {
            this.getActiveMySQLConnection().clearWarnings();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public PreparedStatement clientPrepareStatement(final String sql, final int resultSetType, final int resultSetConcurrency, final int resultSetHoldability) throws SQLException {
        try {
            return this.getActiveMySQLConnection().clientPrepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public PreparedStatement clientPrepareStatement(final String sql, final int resultSetType, final int resultSetConcurrency) throws SQLException {
        try {
            return this.getActiveMySQLConnection().clientPrepareStatement(sql, resultSetType, resultSetConcurrency);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public PreparedStatement clientPrepareStatement(final String sql, final int autoGenKeyIndex) throws SQLException {
        try {
            return this.getActiveMySQLConnection().clientPrepareStatement(sql, autoGenKeyIndex);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public PreparedStatement clientPrepareStatement(final String sql, final int[] autoGenKeyIndexes) throws SQLException {
        try {
            return this.getActiveMySQLConnection().clientPrepareStatement(sql, autoGenKeyIndexes);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public PreparedStatement clientPrepareStatement(final String sql, final String[] autoGenKeyColNames) throws SQLException {
        try {
            return this.getActiveMySQLConnection().clientPrepareStatement(sql, autoGenKeyColNames);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public PreparedStatement clientPrepareStatement(final String sql) throws SQLException {
        try {
            return this.getActiveMySQLConnection().clientPrepareStatement(sql);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void close() throws SQLException {
        try {
            this.getActiveMySQLConnection().close();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void commit() throws SQLException {
        try {
            this.getActiveMySQLConnection().commit();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void createNewIO(final boolean isForReconnect) {
        this.getActiveMySQLConnection().createNewIO(isForReconnect);
    }
    
    @Override
    public Statement createStatement() throws SQLException {
        try {
            return this.getActiveMySQLConnection().createStatement();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public Statement createStatement(final int resultSetType, final int resultSetConcurrency, final int resultSetHoldability) throws SQLException {
        try {
            return this.getActiveMySQLConnection().createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public Statement createStatement(final int resultSetType, final int resultSetConcurrency) throws SQLException {
        try {
            return this.getActiveMySQLConnection().createStatement(resultSetType, resultSetConcurrency);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public int getActiveStatementCount() {
        return this.getActiveMySQLConnection().getActiveStatementCount();
    }
    
    @Override
    public boolean getAutoCommit() throws SQLException {
        try {
            return this.getActiveMySQLConnection().getAutoCommit();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public int getAutoIncrementIncrement() {
        return this.getActiveMySQLConnection().getAutoIncrementIncrement();
    }
    
    @Override
    public CachedResultSetMetaData getCachedMetaData(final String sql) {
        return this.getActiveMySQLConnection().getCachedMetaData(sql);
    }
    
    @Override
    public String getCatalog() throws SQLException {
        try {
            return this.getActiveMySQLConnection().getCatalog();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public String getCharacterSetMetadata() {
        return this.getActiveMySQLConnection().getCharacterSetMetadata();
    }
    
    @Override
    public ExceptionInterceptor getExceptionInterceptor() {
        return this.getActiveMySQLConnection().getExceptionInterceptor();
    }
    
    @Override
    public int getHoldability() throws SQLException {
        try {
            return this.getActiveMySQLConnection().getHoldability();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public String getHost() {
        return this.getActiveMySQLConnection().getHost();
    }
    
    @Override
    public long getId() {
        return this.getActiveMySQLConnection().getId();
    }
    
    @Override
    public long getIdleFor() {
        return this.getActiveMySQLConnection().getIdleFor();
    }
    
    @Override
    public JdbcConnection getMultiHostSafeProxy() {
        return this.getThisAsProxy().getProxy();
    }
    
    @Override
    public DatabaseMetaData getMetaData() throws SQLException {
        try {
            return this.getActiveMySQLConnection().getMetaData();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public Statement getMetadataSafeStatement() throws SQLException {
        try {
            return this.getActiveMySQLConnection().getMetadataSafeStatement();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public Properties getProperties() {
        return this.getActiveMySQLConnection().getProperties();
    }
    
    @Override
    public ServerVersion getServerVersion() {
        return this.getActiveMySQLConnection().getServerVersion();
    }
    
    @Override
    public Session getSession() {
        return this.getActiveMySQLConnection().getSession();
    }
    
    @Override
    public String getStatementComment() {
        return this.getActiveMySQLConnection().getStatementComment();
    }
    
    @Override
    public List<QueryInterceptor> getQueryInterceptorsInstances() {
        return this.getActiveMySQLConnection().getQueryInterceptorsInstances();
    }
    
    @Override
    public int getTransactionIsolation() throws SQLException {
        try {
            return this.getActiveMySQLConnection().getTransactionIsolation();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public Map<String, Class<?>> getTypeMap() throws SQLException {
        try {
            return this.getActiveMySQLConnection().getTypeMap();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public String getURL() {
        return this.getActiveMySQLConnection().getURL();
    }
    
    @Override
    public String getUser() {
        return this.getActiveMySQLConnection().getUser();
    }
    
    @Override
    public SQLWarning getWarnings() throws SQLException {
        try {
            return this.getActiveMySQLConnection().getWarnings();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean hasSameProperties(final JdbcConnection c) {
        return this.getActiveMySQLConnection().hasSameProperties(c);
    }
    
    @Deprecated
    @Override
    public boolean hasTriedMaster() {
        return this.getActiveMySQLConnection().hasTriedMaster();
    }
    
    @Override
    public void initializeResultsMetadataFromCache(final String sql, final CachedResultSetMetaData cachedMetaData, final ResultSetInternalMethods resultSet) throws SQLException {
        try {
            this.getActiveMySQLConnection().initializeResultsMetadataFromCache(sql, cachedMetaData, resultSet);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void initializeSafeQueryInterceptors() throws SQLException {
        try {
            this.getActiveMySQLConnection().initializeSafeQueryInterceptors();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean isInGlobalTx() {
        return this.getActiveMySQLConnection().isInGlobalTx();
    }
    
    @Override
    public boolean isMasterConnection() {
        return this.getThisAsProxy().isMasterConnection();
    }
    
    @Override
    public boolean isReadOnly() throws SQLException {
        try {
            return this.getActiveMySQLConnection().isReadOnly();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean isReadOnly(final boolean useSessionStatus) throws SQLException {
        try {
            return this.getActiveMySQLConnection().isReadOnly(useSessionStatus);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean isSameResource(final JdbcConnection otherConnection) {
        return this.getActiveMySQLConnection().isSameResource(otherConnection);
    }
    
    @Override
    public boolean lowerCaseTableNames() {
        return this.getActiveMySQLConnection().lowerCaseTableNames();
    }
    
    @Override
    public String nativeSQL(final String sql) throws SQLException {
        try {
            return this.getActiveMySQLConnection().nativeSQL(sql);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void ping() throws SQLException {
        try {
            this.getActiveMySQLConnection().ping();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void pingInternal(final boolean checkForClosedConnection, final int timeoutMillis) throws SQLException {
        try {
            this.getActiveMySQLConnection().pingInternal(checkForClosedConnection, timeoutMillis);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public CallableStatement prepareCall(final String sql, final int resultSetType, final int resultSetConcurrency, final int resultSetHoldability) throws SQLException {
        try {
            return this.getActiveMySQLConnection().prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public CallableStatement prepareCall(final String sql, final int resultSetType, final int resultSetConcurrency) throws SQLException {
        try {
            return this.getActiveMySQLConnection().prepareCall(sql, resultSetType, resultSetConcurrency);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public CallableStatement prepareCall(final String sql) throws SQLException {
        try {
            return this.getActiveMySQLConnection().prepareCall(sql);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public PreparedStatement prepareStatement(final String sql, final int resultSetType, final int resultSetConcurrency, final int resultSetHoldability) throws SQLException {
        try {
            return this.getActiveMySQLConnection().prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public PreparedStatement prepareStatement(final String sql, final int resultSetType, final int resultSetConcurrency) throws SQLException {
        try {
            return this.getActiveMySQLConnection().prepareStatement(sql, resultSetType, resultSetConcurrency);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public PreparedStatement prepareStatement(final String sql, final int autoGenKeyIndex) throws SQLException {
        try {
            return this.getActiveMySQLConnection().prepareStatement(sql, autoGenKeyIndex);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public PreparedStatement prepareStatement(final String sql, final int[] autoGenKeyIndexes) throws SQLException {
        try {
            return this.getActiveMySQLConnection().prepareStatement(sql, autoGenKeyIndexes);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public PreparedStatement prepareStatement(final String sql, final String[] autoGenKeyColNames) throws SQLException {
        try {
            return this.getActiveMySQLConnection().prepareStatement(sql, autoGenKeyColNames);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public PreparedStatement prepareStatement(final String sql) throws SQLException {
        try {
            return this.getActiveMySQLConnection().prepareStatement(sql);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void realClose(final boolean calledExplicitly, final boolean issueRollback, final boolean skipLocalTeardown, final Throwable reason) throws SQLException {
        try {
            this.getActiveMySQLConnection().realClose(calledExplicitly, issueRollback, skipLocalTeardown, reason);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void recachePreparedStatement(final JdbcPreparedStatement pstmt) throws SQLException {
        try {
            this.getActiveMySQLConnection().recachePreparedStatement(pstmt);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void decachePreparedStatement(final JdbcPreparedStatement pstmt) throws SQLException {
        try {
            this.getActiveMySQLConnection().decachePreparedStatement(pstmt);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void registerStatement(final JdbcStatement stmt) {
        this.getActiveMySQLConnection().registerStatement(stmt);
    }
    
    @Override
    public void releaseSavepoint(final Savepoint arg0) throws SQLException {
        try {
            this.getActiveMySQLConnection().releaseSavepoint(arg0);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void resetServerState() throws SQLException {
        try {
            this.getActiveMySQLConnection().resetServerState();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void rollback() throws SQLException {
        try {
            this.getActiveMySQLConnection().rollback();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void rollback(final Savepoint savepoint) throws SQLException {
        try {
            this.getActiveMySQLConnection().rollback(savepoint);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public PreparedStatement serverPrepareStatement(final String sql, final int resultSetType, final int resultSetConcurrency, final int resultSetHoldability) throws SQLException {
        try {
            return this.getActiveMySQLConnection().serverPrepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public PreparedStatement serverPrepareStatement(final String sql, final int resultSetType, final int resultSetConcurrency) throws SQLException {
        try {
            return this.getActiveMySQLConnection().serverPrepareStatement(sql, resultSetType, resultSetConcurrency);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public PreparedStatement serverPrepareStatement(final String sql, final int autoGenKeyIndex) throws SQLException {
        try {
            return this.getActiveMySQLConnection().serverPrepareStatement(sql, autoGenKeyIndex);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public PreparedStatement serverPrepareStatement(final String sql, final int[] autoGenKeyIndexes) throws SQLException {
        try {
            return this.getActiveMySQLConnection().serverPrepareStatement(sql, autoGenKeyIndexes);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public PreparedStatement serverPrepareStatement(final String sql, final String[] autoGenKeyColNames) throws SQLException {
        try {
            return this.getActiveMySQLConnection().serverPrepareStatement(sql, autoGenKeyColNames);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public PreparedStatement serverPrepareStatement(final String sql) throws SQLException {
        try {
            return this.getActiveMySQLConnection().serverPrepareStatement(sql);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setAutoCommit(final boolean autoCommitFlag) throws SQLException {
        try {
            this.getActiveMySQLConnection().setAutoCommit(autoCommitFlag);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setCatalog(final String catalog) throws SQLException {
        try {
            this.getActiveMySQLConnection().setCatalog(catalog);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setFailedOver(final boolean flag) {
        this.getActiveMySQLConnection().setFailedOver(flag);
    }
    
    @Override
    public void setHoldability(final int arg0) throws SQLException {
        try {
            this.getActiveMySQLConnection().setHoldability(arg0);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setInGlobalTx(final boolean flag) {
        this.getActiveMySQLConnection().setInGlobalTx(flag);
    }
    
    @Override
    public void setProxy(final JdbcConnection proxy) {
        this.getThisAsProxy().setProxy(proxy);
    }
    
    @Override
    public void setReadOnly(final boolean readOnlyFlag) throws SQLException {
        try {
            this.getActiveMySQLConnection().setReadOnly(readOnlyFlag);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setReadOnlyInternal(final boolean readOnlyFlag) throws SQLException {
        try {
            this.getActiveMySQLConnection().setReadOnlyInternal(readOnlyFlag);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public Savepoint setSavepoint() throws SQLException {
        try {
            return this.getActiveMySQLConnection().setSavepoint();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public Savepoint setSavepoint(final String name) throws SQLException {
        try {
            return this.getActiveMySQLConnection().setSavepoint(name);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setStatementComment(final String comment) {
        this.getActiveMySQLConnection().setStatementComment(comment);
    }
    
    @Override
    public void setTransactionIsolation(final int level) throws SQLException {
        try {
            this.getActiveMySQLConnection().setTransactionIsolation(level);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void shutdownServer() throws SQLException {
        try {
            this.getActiveMySQLConnection().shutdownServer();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean storesLowerCaseTableName() {
        return this.getActiveMySQLConnection().storesLowerCaseTableName();
    }
    
    @Override
    public void throwConnectionClosedException() throws SQLException {
        try {
            this.getActiveMySQLConnection().throwConnectionClosedException();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void transactionBegun() {
        this.getActiveMySQLConnection().transactionBegun();
    }
    
    @Override
    public void transactionCompleted() {
        this.getActiveMySQLConnection().transactionCompleted();
    }
    
    @Override
    public void unregisterStatement(final JdbcStatement stmt) {
        this.getActiveMySQLConnection().unregisterStatement(stmt);
    }
    
    @Override
    public void unSafeQueryInterceptors() throws SQLException {
        try {
            this.getActiveMySQLConnection().unSafeQueryInterceptors();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean isClosed() throws SQLException {
        try {
            return this.getThisAsProxy().isClosed;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean isProxySet() {
        return this.getActiveMySQLConnection().isProxySet();
    }
    
    @Override
    public void setTypeMap(final Map<String, Class<?>> map) throws SQLException {
        try {
            this.getActiveMySQLConnection().setTypeMap(map);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean isServerLocal() throws SQLException {
        try {
            return this.getActiveMySQLConnection().isServerLocal();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setSchema(final String schema) throws SQLException {
        try {
            this.getActiveMySQLConnection().setSchema(schema);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public String getSchema() throws SQLException {
        try {
            return this.getActiveMySQLConnection().getSchema();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void abort(final Executor executor) throws SQLException {
        try {
            this.getActiveMySQLConnection().abort(executor);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setNetworkTimeout(final Executor executor, final int milliseconds) throws SQLException {
        try {
            this.getActiveMySQLConnection().setNetworkTimeout(executor, milliseconds);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public int getNetworkTimeout() throws SQLException {
        try {
            return this.getActiveMySQLConnection().getNetworkTimeout();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public Object getConnectionMutex() {
        return this.getActiveMySQLConnection().getConnectionMutex();
    }
    
    @Override
    public int getSessionMaxRows() {
        return this.getActiveMySQLConnection().getSessionMaxRows();
    }
    
    @Override
    public void setSessionMaxRows(final int max) throws SQLException {
        try {
            this.getActiveMySQLConnection().setSessionMaxRows(max);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public SQLXML createSQLXML() throws SQLException {
        try {
            return this.getActiveMySQLConnection().createSQLXML();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public Array createArrayOf(final String typeName, final Object[] elements) throws SQLException {
        try {
            return this.getActiveMySQLConnection().createArrayOf(typeName, elements);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public Struct createStruct(final String typeName, final Object[] attributes) throws SQLException {
        try {
            return this.getActiveMySQLConnection().createStruct(typeName, attributes);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public Properties getClientInfo() throws SQLException {
        try {
            return this.getActiveMySQLConnection().getClientInfo();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public String getClientInfo(final String name) throws SQLException {
        try {
            return this.getActiveMySQLConnection().getClientInfo(name);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean isValid(final int timeout) throws SQLException {
        try {
            return this.getActiveMySQLConnection().isValid(timeout);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setClientInfo(final Properties properties) throws SQLClientInfoException {
        this.getActiveMySQLConnection().setClientInfo(properties);
    }
    
    @Override
    public void setClientInfo(final String name, final String value) throws SQLClientInfoException {
        this.getActiveMySQLConnection().setClientInfo(name, value);
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
    
    @Override
    public Blob createBlob() throws SQLException {
        try {
            return this.getActiveMySQLConnection().createBlob();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public Clob createClob() throws SQLException {
        try {
            return this.getActiveMySQLConnection().createClob();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public NClob createNClob() throws SQLException {
        try {
            return this.getActiveMySQLConnection().createNClob();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public ClientInfoProvider getClientInfoProviderImpl() throws SQLException {
        try {
            synchronized (this.getThisAsProxy()) {
                return this.getActiveMySQLConnection().getClientInfoProviderImpl();
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public JdbcPropertySet getPropertySet() {
        return this.getActiveMySQLConnection().getPropertySet();
    }
    
    @Override
    public String getHostPortPair() {
        return this.getActiveMySQLConnection().getHostPortPair();
    }
    
    @Override
    public void normalClose() {
        this.getActiveMySQLConnection().normalClose();
    }
    
    @Override
    public void cleanup(final Throwable whyCleanedUp) {
        this.getActiveMySQLConnection().cleanup(whyCleanedUp);
    }
}
