
package com.mysql.cj.jdbc;

import java.lang.ref.WeakReference;
import com.mysql.cj.log.StandardLogger;
import java.sql.Struct;
import java.sql.Array;
import java.sql.SQLClientInfoException;
import java.sql.Connection;
import java.sql.SQLXML;
import java.sql.NClob;
import java.sql.Blob;
import java.sql.Clob;
import java.security.Permission;
import java.util.concurrent.Executor;
import com.mysql.cj.jdbc.result.UpdatableResultSet;
import com.mysql.cj.jdbc.result.CachedResultSetMetaDataImpl;
import com.mysql.cj.jdbc.result.ResultSetInternalMethods;
import java.sql.Savepoint;
import com.mysql.cj.PreparedQuery;
import com.mysql.cj.log.ProfilerEventHandlerFactory;
import com.mysql.cj.log.ProfilerEvent;
import com.mysql.cj.log.ProfilerEventImpl;
import com.mysql.cj.Constants;
import com.mysql.cj.LicenseConfiguration;
import java.sql.SQLWarning;
import java.util.HashMap;
import com.mysql.cj.ServerVersion;
import com.mysql.cj.CacheAdapterFactory;
import com.mysql.cj.exceptions.PasswordExpiredException;
import java.util.Stack;
import com.mysql.cj.TransactionEventHandler;
import java.sql.DriverManager;
import com.mysql.cj.exceptions.UnableToConnectException;
import com.mysql.cj.protocol.ColumnDefinition;
import com.mysql.cj.protocol.Resultset;
import com.mysql.cj.protocol.ProtocolEntityFactory;
import com.mysql.cj.protocol.a.NativePacketPayload;
import com.mysql.cj.Query;
import java.sql.Statement;
import com.mysql.cj.exceptions.ExceptionFactory;
import java.util.Iterator;
import java.sql.PreparedStatement;
import com.mysql.cj.util.Util;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import com.mysql.cj.NoSubInterceptorWrapper;
import com.mysql.cj.jdbc.exceptions.SQLError;
import com.mysql.cj.Messages;
import com.mysql.cj.MysqlConnection;
import com.mysql.cj.exceptions.CJException;
import com.mysql.cj.jdbc.exceptions.SQLExceptionsMapping;
import com.mysql.cj.util.LogUtils;
import com.mysql.cj.protocol.SocksProxySocketFactory;
import com.mysql.cj.exceptions.ExceptionInterceptorChain;
import com.mysql.cj.conf.PropertyKey;
import com.mysql.cj.conf.PropertySet;
import com.mysql.cj.util.StringUtils;
import java.sql.SQLException;
import com.mysql.cj.jdbc.ha.MultiHostMySQLConnection;
import com.mysql.cj.exceptions.ExceptionInterceptor;
import com.mysql.cj.jdbc.result.ResultSetFactory;
import com.mysql.cj.conf.RuntimeProperty;
import com.mysql.cj.interceptors.QueryInterceptor;
import com.mysql.cj.conf.HostInfo;
import com.mysql.cj.jdbc.result.CachedResultSetMetaData;
import java.util.Properties;
import com.mysql.cj.util.LRUCache;
import java.util.concurrent.CopyOnWriteArrayList;
import com.mysql.cj.NativeSession;
import java.sql.DatabaseMetaData;
import com.mysql.cj.ParseInfo;
import com.mysql.cj.CacheAdapter;
import java.util.Random;
import com.mysql.cj.jdbc.interceptors.ConnectionLifecycleInterceptor;
import java.util.List;
import java.util.Map;
import java.lang.reflect.InvocationHandler;
import java.sql.SQLPermission;
import java.io.Serializable;
import com.mysql.cj.Session;

public class ConnectionImpl implements JdbcConnection, Session.SessionEventListener, Serializable
{
    private static final long serialVersionUID = 4009476458425101761L;
    private static final SQLPermission SET_NETWORK_TIMEOUT_PERM;
    private static final SQLPermission ABORT_PERM;
    private JdbcConnection proxy;
    private InvocationHandler realProxy;
    public static Map<?, ?> charsetMap;
    protected static final String DEFAULT_LOGGER_CLASS;
    private static Map<String, Integer> mapTransIsolationNameToValue;
    protected static Map<?, ?> roundRobinStatsMap;
    private List<ConnectionLifecycleInterceptor> connectionLifecycleInterceptors;
    private static final int DEFAULT_RESULT_SET_TYPE = 1003;
    private static final int DEFAULT_RESULT_SET_CONCURRENCY = 1007;
    private static final Random random;
    private CacheAdapter<String, ParseInfo> cachedPreparedStatementParams;
    private String database;
    private DatabaseMetaData dbmd;
    private NativeSession session;
    private boolean isInGlobalTx;
    private int isolationLevel;
    private final CopyOnWriteArrayList<JdbcStatement> openStatements;
    private LRUCache<CompoundCacheKey, CallableStatement.CallableStatementParamInfo> parsedCallableStatementCache;
    private String password;
    private String pointOfOrigin;
    protected Properties props;
    private boolean readOnly;
    protected LRUCache<String, CachedResultSetMetaData> resultSetMetadataCache;
    private Map<String, Class<?>> typeMap;
    private String user;
    private LRUCache<String, Boolean> serverSideStatementCheckCache;
    private LRUCache<CompoundCacheKey, ServerPreparedStatement> serverSideStatementCache;
    private HostInfo origHostInfo;
    private String origHostToConnectTo;
    private int origPortToConnectTo;
    private boolean hasTriedMasterFlag;
    private List<QueryInterceptor> queryInterceptors;
    protected JdbcPropertySet propertySet;
    private RuntimeProperty<Boolean> autoReconnectForPools;
    private RuntimeProperty<Boolean> cachePrepStmts;
    private RuntimeProperty<Boolean> autoReconnect;
    private RuntimeProperty<Boolean> useUsageAdvisor;
    private RuntimeProperty<Boolean> reconnectAtTxEnd;
    private RuntimeProperty<Boolean> emulateUnsupportedPstmts;
    private RuntimeProperty<Boolean> ignoreNonTxTables;
    private RuntimeProperty<Boolean> pedantic;
    private RuntimeProperty<Integer> prepStmtCacheSqlLimit;
    private RuntimeProperty<Boolean> useLocalSessionState;
    private RuntimeProperty<Boolean> useServerPrepStmts;
    private RuntimeProperty<Boolean> processEscapeCodesForPrepStmts;
    private RuntimeProperty<Boolean> useLocalTransactionState;
    private RuntimeProperty<Boolean> disconnectOnExpiredPasswords;
    private RuntimeProperty<Boolean> readOnlyPropagatesToServer;
    protected ResultSetFactory nullStatementResultSetFactory;
    private int autoIncrementIncrement;
    private ExceptionInterceptor exceptionInterceptor;
    private ClientInfoProvider infoProvider;
    
    @Override
    public String getHost() {
        return this.session.getHostInfo().getHost();
    }
    
    @Override
    public boolean isProxySet() {
        return this.proxy != null;
    }
    
    @Override
    public void setProxy(final JdbcConnection proxy) {
        this.proxy = proxy;
        this.realProxy = ((this.proxy instanceof MultiHostMySQLConnection) ? ((MultiHostMySQLConnection)proxy).getThisAsProxy() : null);
    }
    
    private JdbcConnection getProxy() {
        return (this.proxy != null) ? this.proxy : this;
    }
    
    @Override
    public JdbcConnection getMultiHostSafeProxy() {
        return this.getProxy();
    }
    
    @Override
    public JdbcConnection getActiveMySQLConnection() {
        return this;
    }
    
    @Override
    public Object getConnectionMutex() {
        return (this.realProxy != null) ? this.realProxy : this.getProxy();
    }
    
    public static JdbcConnection getInstance(final HostInfo hostInfo) throws SQLException {
        return new ConnectionImpl(hostInfo);
    }
    
    protected static synchronized int getNextRoundRobinHostIndex(final String url, final List<?> hostList) {
        final int indexRange = hostList.size();
        final int index = ConnectionImpl.random.nextInt(indexRange);
        return index;
    }
    
    private static boolean nullSafeCompare(final String s1, final String s2) {
        return (s1 == null && s2 == null) || ((s1 != null || s2 == null) && s1 != null && s1.equals(s2));
    }
    
    protected ConnectionImpl() {
        this.proxy = null;
        this.realProxy = null;
        this.database = null;
        this.dbmd = null;
        this.session = null;
        this.isInGlobalTx = false;
        this.isolationLevel = 2;
        this.openStatements = new CopyOnWriteArrayList<JdbcStatement>();
        this.password = null;
        this.props = null;
        this.readOnly = false;
        this.user = null;
        this.hasTriedMasterFlag = false;
        this.autoIncrementIncrement = 0;
    }
    
    public ConnectionImpl(final HostInfo hostInfo) throws SQLException {
        this.proxy = null;
        this.realProxy = null;
        this.database = null;
        this.dbmd = null;
        this.session = null;
        this.isInGlobalTx = false;
        this.isolationLevel = 2;
        this.openStatements = new CopyOnWriteArrayList<JdbcStatement>();
        this.password = null;
        this.props = null;
        this.readOnly = false;
        this.user = null;
        this.hasTriedMasterFlag = false;
        this.autoIncrementIncrement = 0;
        try {
            this.origHostInfo = hostInfo;
            this.origHostToConnectTo = hostInfo.getHost();
            this.origPortToConnectTo = hostInfo.getPort();
            this.database = hostInfo.getDatabase();
            this.user = (StringUtils.isNullOrEmpty(hostInfo.getUser()) ? "" : hostInfo.getUser());
            this.password = (StringUtils.isNullOrEmpty(hostInfo.getPassword()) ? "" : hostInfo.getPassword());
            this.props = hostInfo.exposeAsProperties();
            (this.propertySet = new JdbcPropertySetImpl()).initializeProperties(this.props);
            this.nullStatementResultSetFactory = new ResultSetFactory(this, null);
            (this.session = new NativeSession(hostInfo, this.propertySet)).addListener(this);
            this.autoReconnectForPools = this.propertySet.getBooleanProperty(PropertyKey.autoReconnectForPools);
            this.cachePrepStmts = this.propertySet.getBooleanProperty(PropertyKey.cachePrepStmts);
            this.autoReconnect = this.propertySet.getBooleanProperty(PropertyKey.autoReconnect);
            this.useUsageAdvisor = this.propertySet.getBooleanProperty(PropertyKey.useUsageAdvisor);
            this.reconnectAtTxEnd = this.propertySet.getBooleanProperty(PropertyKey.reconnectAtTxEnd);
            this.emulateUnsupportedPstmts = this.propertySet.getBooleanProperty(PropertyKey.emulateUnsupportedPstmts);
            this.ignoreNonTxTables = this.propertySet.getBooleanProperty(PropertyKey.ignoreNonTxTables);
            this.pedantic = this.propertySet.getBooleanProperty(PropertyKey.pedantic);
            this.prepStmtCacheSqlLimit = this.propertySet.getIntegerProperty(PropertyKey.prepStmtCacheSqlLimit);
            this.useLocalSessionState = this.propertySet.getBooleanProperty(PropertyKey.useLocalSessionState);
            this.useServerPrepStmts = this.propertySet.getBooleanProperty(PropertyKey.useServerPrepStmts);
            this.processEscapeCodesForPrepStmts = this.propertySet.getBooleanProperty(PropertyKey.processEscapeCodesForPrepStmts);
            this.useLocalTransactionState = this.propertySet.getBooleanProperty(PropertyKey.useLocalTransactionState);
            this.disconnectOnExpiredPasswords = this.propertySet.getBooleanProperty(PropertyKey.disconnectOnExpiredPasswords);
            this.readOnlyPropagatesToServer = this.propertySet.getBooleanProperty(PropertyKey.readOnlyPropagatesToServer);
            final String exceptionInterceptorClasses = this.propertySet.getStringProperty(PropertyKey.exceptionInterceptors).getStringValue();
            if (exceptionInterceptorClasses != null && !"".equals(exceptionInterceptorClasses)) {
                this.exceptionInterceptor = new ExceptionInterceptorChain(exceptionInterceptorClasses, this.props, this.session.getLog());
            }
            if (this.cachePrepStmts.getValue()) {
                this.createPreparedStatementCaches();
            }
            if (this.propertySet.getBooleanProperty(PropertyKey.cacheCallableStmts).getValue()) {
                this.parsedCallableStatementCache = new LRUCache<CompoundCacheKey, CallableStatement.CallableStatementParamInfo>(this.propertySet.getIntegerProperty(PropertyKey.callableStmtCacheSize).getValue());
            }
            if (this.propertySet.getBooleanProperty(PropertyKey.allowMultiQueries).getValue()) {
                this.propertySet.getProperty(PropertyKey.cacheResultSetMetadata).setValue(false);
            }
            if (this.propertySet.getBooleanProperty(PropertyKey.cacheResultSetMetadata).getValue()) {
                this.resultSetMetadataCache = new LRUCache<String, CachedResultSetMetaData>(this.propertySet.getIntegerProperty(PropertyKey.metadataCacheSize).getValue());
            }
            if (this.propertySet.getStringProperty(PropertyKey.socksProxyHost).getStringValue() != null) {
                this.propertySet.getProperty(PropertyKey.socketFactory).setValue(SocksProxySocketFactory.class.getName());
            }
            this.pointOfOrigin = (this.useUsageAdvisor.getValue() ? LogUtils.findCallingClassAndMethod(new Throwable()) : "");
            this.dbmd = this.getMetaData(false, false);
            this.initializeSafeQueryInterceptors();
        }
        catch (CJException e1) {
            throw SQLExceptionsMapping.translateException(e1, this.getExceptionInterceptor());
        }
        try {
            this.createNewIO(false);
            this.unSafeQueryInterceptors();
            AbandonedConnectionCleanupThread.trackConnection(this, this.getSession().getNetworkResources());
        }
        catch (SQLException ex) {
            this.cleanup(ex);
            throw ex;
        }
        catch (Exception ex2) {
            this.cleanup(ex2);
            throw SQLError.createSQLException(((boolean)this.propertySet.getBooleanProperty(PropertyKey.paranoid).getValue()) ? Messages.getString("Connection.0") : Messages.getString("Connection.1", new Object[] { this.session.getHostInfo().getHost(), this.session.getHostInfo().getPort() }), "08S01", ex2, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public JdbcPropertySet getPropertySet() {
        return this.propertySet;
    }
    
    @Override
    public void unSafeQueryInterceptors() throws SQLException {
        try {
            this.queryInterceptors = this.queryInterceptors.stream().map(u -> u.getUnderlyingInterceptor()).collect((Collector<? super Object, ?, List<QueryInterceptor>>)Collectors.toList());
            if (this.session != null) {
                this.session.setQueryInterceptors(this.queryInterceptors);
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void initializeSafeQueryInterceptors() throws SQLException {
        try {
            this.queryInterceptors = Util.loadClasses(this.propertySet.getStringProperty(PropertyKey.queryInterceptors).getStringValue(), "MysqlIo.BadQueryInterceptor", this.getExceptionInterceptor()).stream().map(o -> new NoSubInterceptorWrapper(o.init(this, this.props, this.session.getLog()))).collect((Collector<? super Object, ?, List<QueryInterceptor>>)Collectors.toList());
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public List<QueryInterceptor> getQueryInterceptorsInstances() {
        return this.queryInterceptors;
    }
    
    private boolean canHandleAsServerPreparedStatement(final String sql) throws SQLException {
        if (sql == null || sql.length() == 0) {
            return true;
        }
        if (!this.useServerPrepStmts.getValue()) {
            return false;
        }
        final boolean allowMultiQueries = this.propertySet.getBooleanProperty(PropertyKey.allowMultiQueries).getValue();
        if (this.cachePrepStmts.getValue()) {
            synchronized (this.serverSideStatementCheckCache) {
                final Boolean flag = this.serverSideStatementCheckCache.get(sql);
                if (flag != null) {
                    return flag;
                }
                final boolean canHandle = StringUtils.canHandleAsServerPreparedStatementNoCache(sql, this.getServerVersion(), allowMultiQueries, this.session.getServerSession().isNoBackslashEscapesSet(), this.session.getServerSession().useAnsiQuotedIdentifiers());
                if (sql.length() < this.prepStmtCacheSqlLimit.getValue()) {
                    this.serverSideStatementCheckCache.put(sql, canHandle ? Boolean.TRUE : Boolean.FALSE);
                }
                return canHandle;
            }
        }
        return StringUtils.canHandleAsServerPreparedStatementNoCache(sql, this.getServerVersion(), allowMultiQueries, this.session.getServerSession().isNoBackslashEscapesSet(), this.session.getServerSession().useAnsiQuotedIdentifiers());
    }
    
    @Override
    public void changeUser(String userName, String newPassword) throws SQLException {
        try {
            synchronized (this.getConnectionMutex()) {
                this.checkClosed();
                if (userName == null || userName.equals("")) {
                    userName = "";
                }
                if (newPassword == null) {
                    newPassword = "";
                }
                try {
                    this.session.changeUser(userName, newPassword, this.database);
                }
                catch (CJException ex) {
                    if ("28000".equals(ex.getSQLState())) {
                        this.cleanup(ex);
                    }
                    throw ex;
                }
                this.user = userName;
                this.password = newPassword;
                this.session.configureClientCharacterSet(true);
                this.session.setSessionVariables();
                this.setupServerForTruncationChecks();
            }
        }
        catch (CJException ex2) {
            throw SQLExceptionsMapping.translateException(ex2, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void checkClosed() {
        this.session.checkClosed();
    }
    
    @Override
    public void throwConnectionClosedException() throws SQLException {
        try {
            final SQLException ex = SQLError.createSQLException(Messages.getString("Connection.2"), "08003", this.getExceptionInterceptor());
            if (this.session.getForceClosedReason() != null) {
                ex.initCause(this.session.getForceClosedReason());
            }
            throw ex;
        }
        catch (CJException ex2) {
            throw SQLExceptionsMapping.translateException(ex2, this.getExceptionInterceptor());
        }
    }
    
    private void checkTransactionIsolationLevel() {
        String s = this.session.getServerSession().getServerVariable("transaction_isolation");
        if (s == null) {
            s = this.session.getServerSession().getServerVariable("tx_isolation");
        }
        if (s != null) {
            final Integer intTI = ConnectionImpl.mapTransIsolationNameToValue.get(s);
            if (intTI != null) {
                this.isolationLevel = intTI;
            }
        }
    }
    
    @Override
    public void abortInternal() throws SQLException {
        try {
            this.session.forceClose();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void cleanup(final Throwable whyCleanedUp) {
        try {
            if (this.session != null) {
                if (this.isClosed()) {
                    this.session.forceClose();
                }
                else {
                    this.realClose(false, false, false, whyCleanedUp);
                }
            }
        }
        catch (SQLException ex) {}
        catch (CJException ex2) {}
    }
    
    @Deprecated
    @Override
    public void clearHasTriedMaster() {
        this.hasTriedMasterFlag = false;
    }
    
    @Override
    public void clearWarnings() throws SQLException {
    }
    
    @Override
    public PreparedStatement clientPrepareStatement(final String sql) throws SQLException {
        try {
            return this.clientPrepareStatement(sql, 1003, 1007);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public PreparedStatement clientPrepareStatement(final String sql, final int autoGenKeyIndex) throws SQLException {
        try {
            final PreparedStatement pStmt = this.clientPrepareStatement(sql);
            ((ClientPreparedStatement)pStmt).setRetrieveGeneratedKeys(autoGenKeyIndex == 1);
            return pStmt;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public PreparedStatement clientPrepareStatement(final String sql, final int resultSetType, final int resultSetConcurrency) throws SQLException {
        try {
            return this.clientPrepareStatement(sql, resultSetType, resultSetConcurrency, true);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    public PreparedStatement clientPrepareStatement(final String sql, final int resultSetType, final int resultSetConcurrency, final boolean processEscapeCodesIfNeeded) throws SQLException {
        try {
            this.checkClosed();
            final String nativeSql = (processEscapeCodesIfNeeded && this.processEscapeCodesForPrepStmts.getValue()) ? this.nativeSQL(sql) : sql;
            ClientPreparedStatement pStmt = null;
            if (this.cachePrepStmts.getValue()) {
                final ParseInfo pStmtInfo = this.cachedPreparedStatementParams.get(nativeSql);
                if (pStmtInfo == null) {
                    pStmt = ClientPreparedStatement.getInstance(this.getMultiHostSafeProxy(), nativeSql, this.database);
                    this.cachedPreparedStatementParams.put(nativeSql, pStmt.getParseInfo());
                }
                else {
                    pStmt = ClientPreparedStatement.getInstance(this.getMultiHostSafeProxy(), nativeSql, this.database, pStmtInfo);
                }
            }
            else {
                pStmt = ClientPreparedStatement.getInstance(this.getMultiHostSafeProxy(), nativeSql, this.database);
            }
            pStmt.setResultSetType(resultSetType);
            pStmt.setResultSetConcurrency(resultSetConcurrency);
            return pStmt;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public PreparedStatement clientPrepareStatement(final String sql, final int[] autoGenKeyIndexes) throws SQLException {
        try {
            final ClientPreparedStatement pStmt = (ClientPreparedStatement)this.clientPrepareStatement(sql);
            pStmt.setRetrieveGeneratedKeys(autoGenKeyIndexes != null && autoGenKeyIndexes.length > 0);
            return pStmt;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public PreparedStatement clientPrepareStatement(final String sql, final String[] autoGenKeyColNames) throws SQLException {
        try {
            final ClientPreparedStatement pStmt = (ClientPreparedStatement)this.clientPrepareStatement(sql);
            pStmt.setRetrieveGeneratedKeys(autoGenKeyColNames != null && autoGenKeyColNames.length > 0);
            return pStmt;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public PreparedStatement clientPrepareStatement(final String sql, final int resultSetType, final int resultSetConcurrency, final int resultSetHoldability) throws SQLException {
        try {
            return this.clientPrepareStatement(sql, resultSetType, resultSetConcurrency, true);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void close() throws SQLException {
        try {
            synchronized (this.getConnectionMutex()) {
                if (this.connectionLifecycleInterceptors != null) {
                    for (final ConnectionLifecycleInterceptor cli : this.connectionLifecycleInterceptors) {
                        cli.close();
                    }
                }
                this.realClose(true, true, false, null);
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void normalClose() {
        try {
            this.close();
        }
        catch (SQLException e) {
            ExceptionFactory.createException(e.getMessage(), e);
        }
    }
    
    private void closeAllOpenStatements() throws SQLException {
        SQLException postponedException = null;
        for (final JdbcStatement stmt : this.openStatements) {
            try {
                ((StatementImpl)stmt).realClose(false, true);
            }
            catch (SQLException sqlEx) {
                postponedException = sqlEx;
            }
        }
        if (postponedException != null) {
            throw postponedException;
        }
    }
    
    private void closeStatement(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            }
            catch (SQLException ex) {}
            stmt = null;
        }
    }
    
    @Override
    public void commit() throws SQLException {
        try {
            synchronized (this.getConnectionMutex()) {
                this.checkClosed();
                try {
                    if (this.connectionLifecycleInterceptors != null) {
                        final IterateBlock<ConnectionLifecycleInterceptor> iter = new IterateBlock<ConnectionLifecycleInterceptor>(this.connectionLifecycleInterceptors.iterator()) {
                            @Override
                            void forEach(final ConnectionLifecycleInterceptor each) throws SQLException {
                                if (!each.commit()) {
                                    this.stopIterating = true;
                                }
                            }
                        };
                        iter.doForAll();
                        if (!iter.fullIteration()) {
                            return;
                        }
                    }
                    if (this.session.getServerSession().isAutoCommit()) {
                        throw SQLError.createSQLException(Messages.getString("Connection.3"), this.getExceptionInterceptor());
                    }
                    if (this.useLocalTransactionState.getValue() && !this.session.getServerSession().inTransactionOnServer()) {
                        return;
                    }
                    this.session.execSQL(null, "commit", -1, null, false, (ProtocolEntityFactory<Resultset, NativePacketPayload>)this.nullStatementResultSetFactory, this.database, null, false);
                }
                catch (SQLException sqlException) {
                    if ("08S01".equals(sqlException.getSQLState())) {
                        throw SQLError.createSQLException(Messages.getString("Connection.4"), "08007", this.getExceptionInterceptor());
                    }
                    throw sqlException;
                }
                finally {
                    this.session.setNeedsPing(this.reconnectAtTxEnd.getValue());
                }
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void createNewIO(final boolean isForReconnect) {
        try {
            synchronized (this.getConnectionMutex()) {
                try {
                    if (!this.autoReconnect.getValue()) {
                        this.connectOneTryOnly(isForReconnect);
                        return;
                    }
                    this.connectWithRetries(isForReconnect);
                }
                catch (SQLException ex) {
                    throw ExceptionFactory.createException(UnableToConnectException.class, ex.getMessage(), ex);
                }
            }
        }
        catch (CJException ex2) {
            throw SQLExceptionsMapping.translateException(ex2, this.getExceptionInterceptor());
        }
    }
    
    private void connectWithRetries(final boolean isForReconnect) throws SQLException {
        final double timeout = this.propertySet.getIntegerProperty(PropertyKey.initialTimeout).getValue();
        boolean connectionGood = false;
        Exception connectionException = null;
        for (int attemptCount = 0; attemptCount < this.propertySet.getIntegerProperty(PropertyKey.maxReconnects).getValue() && !connectionGood; ++attemptCount) {
            try {
                this.session.forceClose();
                final JdbcConnection c = this.getProxy();
                this.session.connect(this.origHostInfo, this.user, this.password, this.database, DriverManager.getLoginTimeout() * 1000, c);
                this.pingInternal(false, 0);
                final boolean oldAutoCommit;
                final int oldIsolationLevel;
                final boolean oldReadOnly;
                final String oldCatalog;
                synchronized (this.getConnectionMutex()) {
                    oldAutoCommit = this.getAutoCommit();
                    oldIsolationLevel = this.isolationLevel;
                    oldReadOnly = this.isReadOnly(false);
                    oldCatalog = this.getCatalog();
                    this.session.setQueryInterceptors(this.queryInterceptors);
                }
                this.initializePropsFromServer();
                if (isForReconnect) {
                    this.setAutoCommit(oldAutoCommit);
                    this.setTransactionIsolation(oldIsolationLevel);
                    this.setCatalog(oldCatalog);
                    this.setReadOnly(oldReadOnly);
                }
                connectionGood = true;
                break;
            }
            catch (UnableToConnectException rejEx) {
                this.close();
                this.session.getProtocol().getSocketConnection().forceClose();
            }
            catch (Exception EEE) {
                connectionException = EEE;
                connectionGood = false;
            }
            if (connectionGood) {
                break;
            }
            if (attemptCount > 0) {
                try {
                    Thread.sleep((long)timeout * 1000L);
                }
                catch (InterruptedException ex) {}
            }
        }
        if (!connectionGood) {
            final SQLException chainedEx = SQLError.createSQLException(Messages.getString("Connection.UnableToConnectWithRetries", new Object[] { this.propertySet.getIntegerProperty(PropertyKey.maxReconnects).getValue() }), "08001", connectionException, this.getExceptionInterceptor());
            throw chainedEx;
        }
        if (this.propertySet.getBooleanProperty(PropertyKey.paranoid).getValue() && !this.autoReconnect.getValue()) {
            this.password = null;
            this.user = null;
        }
        if (isForReconnect) {
            final Iterator<JdbcStatement> statementIter = this.openStatements.iterator();
            Stack<JdbcStatement> serverPreparedStatements = null;
            while (statementIter.hasNext()) {
                final JdbcStatement statementObj = statementIter.next();
                if (statementObj instanceof ServerPreparedStatement) {
                    if (serverPreparedStatements == null) {
                        serverPreparedStatements = new Stack<JdbcStatement>();
                    }
                    serverPreparedStatements.add(statementObj);
                }
            }
            if (serverPreparedStatements != null) {
                while (!serverPreparedStatements.isEmpty()) {
                    serverPreparedStatements.pop().rePrepare();
                }
            }
        }
    }
    
    private void connectOneTryOnly(final boolean isForReconnect) throws SQLException {
        Exception connectionNotEstablishedBecause = null;
        try {
            final JdbcConnection c = this.getProxy();
            this.session.connect(this.origHostInfo, this.user, this.password, this.database, DriverManager.getLoginTimeout() * 1000, c);
            final boolean oldAutoCommit = this.getAutoCommit();
            final int oldIsolationLevel = this.isolationLevel;
            final boolean oldReadOnly = this.isReadOnly(false);
            final String oldCatalog = this.getCatalog();
            this.session.setQueryInterceptors(this.queryInterceptors);
            this.initializePropsFromServer();
            if (isForReconnect) {
                this.setAutoCommit(oldAutoCommit);
                this.setTransactionIsolation(oldIsolationLevel);
                this.setCatalog(oldCatalog);
                this.setReadOnly(oldReadOnly);
            }
        }
        catch (UnableToConnectException rejEx) {
            this.close();
            this.session.getProtocol().getSocketConnection().forceClose();
            throw rejEx;
        }
        catch (Exception EEE) {
            if ((EEE instanceof PasswordExpiredException || (EEE instanceof SQLException && ((SQLException)EEE).getErrorCode() == 1820)) && !this.disconnectOnExpiredPasswords.getValue()) {
                return;
            }
            if (this.session != null) {
                this.session.forceClose();
            }
            connectionNotEstablishedBecause = EEE;
            if (EEE instanceof SQLException) {
                throw (SQLException)EEE;
            }
            if (EEE.getCause() != null && EEE.getCause() instanceof SQLException) {
                throw (SQLException)EEE.getCause();
            }
            if (EEE instanceof CJException) {
                throw (CJException)EEE;
            }
            final SQLException chainedEx = SQLError.createSQLException(Messages.getString("Connection.UnableToConnect"), "08001", this.getExceptionInterceptor());
            chainedEx.initCause(connectionNotEstablishedBecause);
            throw chainedEx;
        }
    }
    
    private void createPreparedStatementCaches() throws SQLException {
        synchronized (this.getConnectionMutex()) {
            final int cacheSize = this.propertySet.getIntegerProperty(PropertyKey.prepStmtCacheSize).getValue();
            final String parseInfoCacheFactory = this.propertySet.getStringProperty(PropertyKey.parseInfoCacheFactory).getValue();
            try {
                final Class<?> factoryClass = Class.forName(parseInfoCacheFactory);
                final CacheAdapterFactory<String, ParseInfo> cacheFactory = (CacheAdapterFactory<String, ParseInfo>)factoryClass.newInstance();
                this.cachedPreparedStatementParams = cacheFactory.getInstance(this, this.origHostInfo.getDatabaseUrl(), cacheSize, this.prepStmtCacheSqlLimit.getValue());
            }
            catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex2) {
                final ReflectiveOperationException ex;
                final ReflectiveOperationException e = ex;
                final SQLException sqlEx = SQLError.createSQLException(Messages.getString("Connection.CantFindCacheFactory", new Object[] { parseInfoCacheFactory, PropertyKey.parseInfoCacheFactory }), this.getExceptionInterceptor());
                sqlEx.initCause(e);
                throw sqlEx;
            }
            catch (Exception e2) {
                final SQLException sqlEx = SQLError.createSQLException(Messages.getString("Connection.CantLoadCacheFactory", new Object[] { parseInfoCacheFactory, PropertyKey.parseInfoCacheFactory }), this.getExceptionInterceptor());
                sqlEx.initCause(e2);
                throw sqlEx;
            }
            if (this.useServerPrepStmts.getValue()) {
                this.serverSideStatementCheckCache = new LRUCache<String, Boolean>(cacheSize);
                this.serverSideStatementCache = new LRUCache<CompoundCacheKey, ServerPreparedStatement>(cacheSize) {
                    private static final long serialVersionUID = 7692318650375988114L;
                    
                    @Override
                    protected boolean removeEldestEntry(final Map.Entry<CompoundCacheKey, ServerPreparedStatement> eldest) {
                        if (this.maxElements <= 1) {
                            return false;
                        }
                        final boolean removeIt = super.removeEldestEntry(eldest);
                        if (removeIt) {
                            final ServerPreparedStatement ps = eldest.getValue();
                            ps.setClosed(ps.isCached = false);
                            try {
                                ps.close();
                            }
                            catch (SQLException ex) {}
                        }
                        return removeIt;
                    }
                };
            }
        }
    }
    
    @Override
    public Statement createStatement() throws SQLException {
        try {
            return this.createStatement(1003, 1007);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public Statement createStatement(final int resultSetType, final int resultSetConcurrency) throws SQLException {
        try {
            this.checkClosed();
            final StatementImpl stmt = new StatementImpl(this.getMultiHostSafeProxy(), this.database);
            stmt.setResultSetType(resultSetType);
            stmt.setResultSetConcurrency(resultSetConcurrency);
            return stmt;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public Statement createStatement(final int resultSetType, final int resultSetConcurrency, final int resultSetHoldability) throws SQLException {
        try {
            if (this.pedantic.getValue() && resultSetHoldability != 1) {
                throw SQLError.createSQLException("HOLD_CUSRORS_OVER_COMMIT is only supported holdability level", "S1009", this.getExceptionInterceptor());
            }
            return this.createStatement(resultSetType, resultSetConcurrency);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public int getActiveStatementCount() {
        return this.openStatements.size();
    }
    
    @Override
    public boolean getAutoCommit() throws SQLException {
        try {
            synchronized (this.getConnectionMutex()) {
                return this.session.getServerSession().isAutoCommit();
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public String getCatalog() throws SQLException {
        try {
            synchronized (this.getConnectionMutex()) {
                return this.database;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public String getCharacterSetMetadata() {
        synchronized (this.getConnectionMutex()) {
            return this.session.getServerSession().getCharacterSetMetadata();
        }
    }
    
    @Override
    public int getHoldability() throws SQLException {
        try {
            return 2;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public long getId() {
        return this.session.getThreadId();
    }
    
    @Override
    public long getIdleFor() {
        synchronized (this.getConnectionMutex()) {
            return this.session.getIdleFor();
        }
    }
    
    @Override
    public DatabaseMetaData getMetaData() throws SQLException {
        try {
            return this.getMetaData(true, true);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    private DatabaseMetaData getMetaData(final boolean checkClosed, final boolean checkForInfoSchema) throws SQLException {
        try {
            if (checkClosed) {
                this.checkClosed();
            }
            final com.mysql.cj.jdbc.DatabaseMetaData dbmeta = com.mysql.cj.jdbc.DatabaseMetaData.getInstance(this.getMultiHostSafeProxy(), this.database, checkForInfoSchema, this.nullStatementResultSetFactory);
            if (this.getSession() != null && this.getSession().getProtocol() != null) {
                dbmeta.setMetadataEncoding(this.getSession().getServerSession().getCharacterSetMetadata());
                dbmeta.setMetadataCollationIndex(this.getSession().getServerSession().getMetadataCollationIndex());
            }
            return dbmeta;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public Statement getMetadataSafeStatement() throws SQLException {
        try {
            return this.getMetadataSafeStatement(0);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    public Statement getMetadataSafeStatement(final int maxRows) throws SQLException {
        final Statement stmt = this.createStatement();
        stmt.setMaxRows((maxRows == -1) ? 0 : maxRows);
        stmt.setEscapeProcessing(false);
        if (stmt.getFetchSize() != 0) {
            stmt.setFetchSize(0);
        }
        return stmt;
    }
    
    @Override
    public ServerVersion getServerVersion() {
        return this.session.getServerSession().getServerVersion();
    }
    
    @Override
    public int getTransactionIsolation() throws SQLException {
        try {
            synchronized (this.getConnectionMutex()) {
                if (this.useLocalSessionState.getValue()) {
                    return this.isolationLevel;
                }
                final String s = this.session.queryServerVariable((this.versionMeetsMinimum(8, 0, 3) || (this.versionMeetsMinimum(5, 7, 20) && !this.versionMeetsMinimum(8, 0, 0))) ? "@@session.transaction_isolation" : "@@session.tx_isolation");
                if (s == null) {
                    throw SQLError.createSQLException(Messages.getString("Connection.13"), "S1000", this.getExceptionInterceptor());
                }
                final Integer intTI = ConnectionImpl.mapTransIsolationNameToValue.get(s);
                if (intTI != null) {
                    return this.isolationLevel = intTI;
                }
                throw SQLError.createSQLException(Messages.getString("Connection.12", new Object[] { s }), "S1000", this.getExceptionInterceptor());
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public Map<String, Class<?>> getTypeMap() throws SQLException {
        try {
            synchronized (this.getConnectionMutex()) {
                if (this.typeMap == null) {
                    this.typeMap = new HashMap<String, Class<?>>();
                }
                return this.typeMap;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public String getURL() {
        return this.origHostInfo.getDatabaseUrl();
    }
    
    @Override
    public String getUser() {
        return this.user;
    }
    
    @Override
    public SQLWarning getWarnings() throws SQLException {
        try {
            return null;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean hasSameProperties(final JdbcConnection c) {
        return this.props.equals(c.getProperties());
    }
    
    @Override
    public Properties getProperties() {
        return this.props;
    }
    
    @Deprecated
    @Override
    public boolean hasTriedMaster() {
        return this.hasTriedMasterFlag;
    }
    
    private void initializePropsFromServer() throws SQLException {
        final String connectionInterceptorClasses = this.propertySet.getStringProperty(PropertyKey.connectionLifecycleInterceptors).getStringValue();
        this.connectionLifecycleInterceptors = null;
        if (connectionInterceptorClasses != null) {
            try {
                this.connectionLifecycleInterceptors = Util.loadClasses(this.propertySet.getStringProperty(PropertyKey.connectionLifecycleInterceptors).getStringValue(), "Connection.badLifecycleInterceptor", this.getExceptionInterceptor()).stream().map(o -> o.init(this, this.props, this.session.getLog())).collect((Collector<? super Object, ?, List<ConnectionLifecycleInterceptor>>)Collectors.toList());
            }
            catch (CJException e) {
                throw SQLExceptionsMapping.translateException(e, this.getExceptionInterceptor());
            }
        }
        this.session.setSessionVariables();
        this.session.loadServerVariables(this.getConnectionMutex(), this.dbmd.getDriverVersion());
        this.autoIncrementIncrement = this.session.getServerSession().getServerVariable("auto_increment_increment", 1);
        this.session.buildCollationMapping();
        try {
            LicenseConfiguration.checkLicenseType(this.session.getServerSession().getServerVariables());
        }
        catch (CJException e) {
            throw SQLError.createSQLException(e.getMessage(), "08001", this.getExceptionInterceptor());
        }
        this.session.getProtocol().initServerSession();
        this.checkTransactionIsolationLevel();
        this.session.checkForCharsetMismatch();
        this.session.configureClientCharacterSet(false);
        this.handleAutoCommitDefaults();
        this.session.getServerSession().configureCharacterSets();
        ((com.mysql.cj.jdbc.DatabaseMetaData)this.dbmd).setMetadataEncoding(this.getSession().getServerSession().getCharacterSetMetadata());
        ((com.mysql.cj.jdbc.DatabaseMetaData)this.dbmd).setMetadataCollationIndex(this.getSession().getServerSession().getMetadataCollationIndex());
        this.setupServerForTruncationChecks();
    }
    
    private void handleAutoCommitDefaults() throws SQLException {
        try {
            boolean resetAutoCommitDefault = false;
            final String initConnectValue = this.session.getServerSession().getServerVariable("init_connect");
            if (initConnectValue != null && initConnectValue.length() > 0) {
                final String s = this.session.queryServerVariable("@@session.autocommit");
                if (s != null) {
                    this.session.getServerSession().setAutoCommit(Boolean.parseBoolean(s));
                    if (!this.session.getServerSession().isAutoCommit()) {
                        resetAutoCommitDefault = true;
                    }
                }
            }
            else {
                resetAutoCommitDefault = true;
            }
            if (resetAutoCommitDefault) {
                try {
                    this.setAutoCommit(true);
                }
                catch (SQLException ex) {
                    if (ex.getErrorCode() != 1820 || this.disconnectOnExpiredPasswords.getValue()) {
                        throw ex;
                    }
                }
            }
        }
        catch (CJException ex2) {
            throw SQLExceptionsMapping.translateException(ex2, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean isClosed() {
        try {
            return this.session.isClosed();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean isInGlobalTx() {
        return this.isInGlobalTx;
    }
    
    @Override
    public boolean isMasterConnection() {
        return false;
    }
    
    @Override
    public boolean isReadOnly() throws SQLException {
        try {
            return this.isReadOnly(true);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean isReadOnly(final boolean useSessionStatus) throws SQLException {
        try {
            if (useSessionStatus && !this.session.isClosed() && this.versionMeetsMinimum(5, 6, 5) && !this.useLocalSessionState.getValue() && this.readOnlyPropagatesToServer.getValue()) {
                try {
                    final String s = this.session.queryServerVariable((this.versionMeetsMinimum(8, 0, 3) || (this.versionMeetsMinimum(5, 7, 20) && !this.versionMeetsMinimum(8, 0, 0))) ? "@@session.transaction_read_only" : "@@session.tx_read_only");
                    if (s != null) {
                        return Integer.parseInt(s) != 0;
                    }
                }
                catch (PasswordExpiredException ex) {
                    if (this.disconnectOnExpiredPasswords.getValue()) {
                        throw SQLError.createSQLException(Messages.getString("Connection.16"), "S1000", ex, this.getExceptionInterceptor());
                    }
                }
            }
            return this.readOnly;
        }
        catch (CJException ex2) {
            throw SQLExceptionsMapping.translateException(ex2, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean isSameResource(final JdbcConnection otherConnection) {
        synchronized (this.getConnectionMutex()) {
            if (otherConnection == null) {
                return false;
            }
            boolean directCompare = true;
            final String otherHost = ((ConnectionImpl)otherConnection).origHostToConnectTo;
            final String otherOrigDatabase = ((ConnectionImpl)otherConnection).origHostInfo.getDatabase();
            final String otherCurrentCatalog = ((ConnectionImpl)otherConnection).database;
            if (!nullSafeCompare(otherHost, this.origHostToConnectTo)) {
                directCompare = false;
            }
            else if (otherHost != null && otherHost.indexOf(44) == -1 && otherHost.indexOf(58) == -1) {
                directCompare = (((ConnectionImpl)otherConnection).origPortToConnectTo == this.origPortToConnectTo);
            }
            if (directCompare && (!nullSafeCompare(otherOrigDatabase, this.origHostInfo.getDatabase()) || !nullSafeCompare(otherCurrentCatalog, this.database))) {
                directCompare = false;
            }
            if (directCompare) {
                return true;
            }
            final String otherResourceId = ((ConnectionImpl)otherConnection).getPropertySet().getStringProperty(PropertyKey.resourceId).getValue();
            final String myResourceId = this.propertySet.getStringProperty(PropertyKey.resourceId).getValue();
            if (otherResourceId != null || myResourceId != null) {
                directCompare = nullSafeCompare(otherResourceId, myResourceId);
                if (directCompare) {
                    return true;
                }
            }
            return false;
        }
    }
    
    @Override
    public int getAutoIncrementIncrement() {
        return this.autoIncrementIncrement;
    }
    
    @Override
    public boolean lowerCaseTableNames() {
        return this.session.getServerSession().isLowerCaseTableNames();
    }
    
    @Override
    public String nativeSQL(final String sql) throws SQLException {
        try {
            if (sql == null) {
                return null;
            }
            final Object escapedSqlResult = EscapeProcessor.escapeSQL(sql, this.getMultiHostSafeProxy().getSession().getServerSession().getDefaultTimeZone(), this.getMultiHostSafeProxy().getSession().getServerSession().getCapabilities().serverSupportsFracSecs(), this.getMultiHostSafeProxy().getSession().getServerSession().isServerTruncatesFracSecs(), this.getExceptionInterceptor());
            if (escapedSqlResult instanceof String) {
                return (String)escapedSqlResult;
            }
            return ((EscapeProcessorResult)escapedSqlResult).escapedSql;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    private CallableStatement parseCallableStatement(final String sql) throws SQLException {
        final Object escapedSqlResult = EscapeProcessor.escapeSQL(sql, this.getMultiHostSafeProxy().getSession().getServerSession().getDefaultTimeZone(), this.getMultiHostSafeProxy().getSession().getServerSession().getCapabilities().serverSupportsFracSecs(), this.getMultiHostSafeProxy().getSession().getServerSession().isServerTruncatesFracSecs(), this.getExceptionInterceptor());
        boolean isFunctionCall = false;
        String parsedSql = null;
        if (escapedSqlResult instanceof EscapeProcessorResult) {
            parsedSql = ((EscapeProcessorResult)escapedSqlResult).escapedSql;
            isFunctionCall = ((EscapeProcessorResult)escapedSqlResult).callingStoredFunction;
        }
        else {
            parsedSql = (String)escapedSqlResult;
            isFunctionCall = false;
        }
        return CallableStatement.getInstance(this.getMultiHostSafeProxy(), parsedSql, this.database, isFunctionCall);
    }
    
    @Override
    public void ping() throws SQLException {
        try {
            this.pingInternal(true, 0);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void pingInternal(final boolean checkForClosedConnection, final int timeoutMillis) throws SQLException {
        try {
            this.session.ping(checkForClosedConnection, timeoutMillis);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public java.sql.CallableStatement prepareCall(final String sql) throws SQLException {
        try {
            return this.prepareCall(sql, 1003, 1007);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public java.sql.CallableStatement prepareCall(final String sql, final int resultSetType, final int resultSetConcurrency) throws SQLException {
        try {
            CallableStatement cStmt = null;
            if (!this.propertySet.getBooleanProperty(PropertyKey.cacheCallableStmts).getValue()) {
                cStmt = this.parseCallableStatement(sql);
            }
            else {
                synchronized (this.parsedCallableStatementCache) {
                    final CompoundCacheKey key = new CompoundCacheKey(this.getCatalog(), sql);
                    CallableStatement.CallableStatementParamInfo cachedParamInfo = this.parsedCallableStatementCache.get(key);
                    if (cachedParamInfo != null) {
                        cStmt = CallableStatement.getInstance(this.getMultiHostSafeProxy(), cachedParamInfo);
                    }
                    else {
                        cStmt = this.parseCallableStatement(sql);
                        synchronized (cStmt) {
                            cachedParamInfo = cStmt.paramInfo;
                        }
                        this.parsedCallableStatementCache.put(key, cachedParamInfo);
                    }
                }
            }
            cStmt.setResultSetType(resultSetType);
            cStmt.setResultSetConcurrency(resultSetConcurrency);
            return cStmt;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public java.sql.CallableStatement prepareCall(final String sql, final int resultSetType, final int resultSetConcurrency, final int resultSetHoldability) throws SQLException {
        try {
            if (this.pedantic.getValue() && resultSetHoldability != 1) {
                throw SQLError.createSQLException(Messages.getString("Connection.17"), "S1009", this.getExceptionInterceptor());
            }
            final CallableStatement cStmt = (CallableStatement)this.prepareCall(sql, resultSetType, resultSetConcurrency);
            return cStmt;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public PreparedStatement prepareStatement(final String sql) throws SQLException {
        try {
            return this.prepareStatement(sql, 1003, 1007);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public PreparedStatement prepareStatement(final String sql, final int autoGenKeyIndex) throws SQLException {
        try {
            final PreparedStatement pStmt = this.prepareStatement(sql);
            ((ClientPreparedStatement)pStmt).setRetrieveGeneratedKeys(autoGenKeyIndex == 1);
            return pStmt;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public PreparedStatement prepareStatement(final String sql, final int resultSetType, final int resultSetConcurrency) throws SQLException {
        try {
            synchronized (this.getConnectionMutex()) {
                this.checkClosed();
                ClientPreparedStatement pStmt = null;
                boolean canServerPrepare = true;
                final String nativeSql = this.processEscapeCodesForPrepStmts.getValue() ? this.nativeSQL(sql) : sql;
                if (this.useServerPrepStmts.getValue() && this.emulateUnsupportedPstmts.getValue()) {
                    canServerPrepare = this.canHandleAsServerPreparedStatement(nativeSql);
                }
                if (this.useServerPrepStmts.getValue() && canServerPrepare) {
                    if (this.cachePrepStmts.getValue()) {
                        synchronized (this.serverSideStatementCache) {
                            pStmt = this.serverSideStatementCache.remove(new CompoundCacheKey(this.database, sql));
                            if (pStmt != null) {
                                ((ServerPreparedStatement)pStmt).setClosed(false);
                                pStmt.clearParameters();
                            }
                            if (pStmt == null) {
                                try {
                                    pStmt = ServerPreparedStatement.getInstance(this.getMultiHostSafeProxy(), nativeSql, this.database, resultSetType, resultSetConcurrency);
                                    if (sql.length() < this.prepStmtCacheSqlLimit.getValue()) {
                                        ((ServerPreparedStatement)pStmt).isCached = true;
                                    }
                                    pStmt.setResultSetType(resultSetType);
                                    pStmt.setResultSetConcurrency(resultSetConcurrency);
                                }
                                catch (SQLException sqlEx) {
                                    if (!this.emulateUnsupportedPstmts.getValue()) {
                                        throw sqlEx;
                                    }
                                    pStmt = (ClientPreparedStatement)this.clientPrepareStatement(nativeSql, resultSetType, resultSetConcurrency, false);
                                    if (sql.length() < this.prepStmtCacheSqlLimit.getValue()) {
                                        this.serverSideStatementCheckCache.put(sql, Boolean.FALSE);
                                    }
                                }
                            }
                        }
                    }
                    else {
                        try {
                            pStmt = ServerPreparedStatement.getInstance(this.getMultiHostSafeProxy(), nativeSql, this.database, resultSetType, resultSetConcurrency);
                            pStmt.setResultSetType(resultSetType);
                            pStmt.setResultSetConcurrency(resultSetConcurrency);
                        }
                        catch (SQLException sqlEx2) {
                            if (!this.emulateUnsupportedPstmts.getValue()) {
                                throw sqlEx2;
                            }
                            pStmt = (ClientPreparedStatement)this.clientPrepareStatement(nativeSql, resultSetType, resultSetConcurrency, false);
                        }
                    }
                }
                else {
                    pStmt = (ClientPreparedStatement)this.clientPrepareStatement(nativeSql, resultSetType, resultSetConcurrency, false);
                }
                return pStmt;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public PreparedStatement prepareStatement(final String sql, final int resultSetType, final int resultSetConcurrency, final int resultSetHoldability) throws SQLException {
        try {
            if (this.pedantic.getValue() && resultSetHoldability != 1) {
                throw SQLError.createSQLException(Messages.getString("Connection.17"), "S1009", this.getExceptionInterceptor());
            }
            return this.prepareStatement(sql, resultSetType, resultSetConcurrency);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public PreparedStatement prepareStatement(final String sql, final int[] autoGenKeyIndexes) throws SQLException {
        try {
            final PreparedStatement pStmt = this.prepareStatement(sql);
            ((ClientPreparedStatement)pStmt).setRetrieveGeneratedKeys(autoGenKeyIndexes != null && autoGenKeyIndexes.length > 0);
            return pStmt;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public PreparedStatement prepareStatement(final String sql, final String[] autoGenKeyColNames) throws SQLException {
        try {
            final PreparedStatement pStmt = this.prepareStatement(sql);
            ((ClientPreparedStatement)pStmt).setRetrieveGeneratedKeys(autoGenKeyColNames != null && autoGenKeyColNames.length > 0);
            return pStmt;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void realClose(final boolean calledExplicitly, final boolean issueRollback, final boolean skipLocalTeardown, final Throwable reason) throws SQLException {
        try {
            SQLException sqlEx = null;
            if (this.isClosed()) {
                return;
            }
            this.session.setForceClosedReason(reason);
            try {
                if (!skipLocalTeardown) {
                    if (!this.getAutoCommit() && issueRollback) {
                        try {
                            this.rollback();
                        }
                        catch (SQLException ex) {
                            sqlEx = ex;
                        }
                    }
                    this.session.reportMetrics();
                    if (this.useUsageAdvisor.getValue()) {
                        if (!calledExplicitly) {
                            this.session.getProfilerEventHandler().consumeEvent(new ProfilerEventImpl((byte)0, "", this.getCatalog(), this.session.getThreadId(), -1, -1, System.currentTimeMillis(), 0L, Constants.MILLIS_I18N, null, this.pointOfOrigin, Messages.getString("Connection.18")));
                        }
                        final long connectionLifeTime = System.currentTimeMillis() - this.session.getConnectionCreationTimeMillis();
                        if (connectionLifeTime < 500L) {
                            this.session.getProfilerEventHandler().consumeEvent(new ProfilerEventImpl((byte)0, "", this.getCatalog(), this.session.getThreadId(), -1, -1, System.currentTimeMillis(), 0L, Constants.MILLIS_I18N, null, this.pointOfOrigin, Messages.getString("Connection.19")));
                        }
                    }
                    try {
                        this.closeAllOpenStatements();
                    }
                    catch (SQLException ex) {
                        sqlEx = ex;
                    }
                    this.session.quit();
                }
                else {
                    this.session.forceClose();
                }
                if (this.queryInterceptors != null) {
                    for (int i = 0; i < this.queryInterceptors.size(); ++i) {
                        this.queryInterceptors.get(i).destroy();
                    }
                }
                if (this.exceptionInterceptor != null) {
                    this.exceptionInterceptor.destroy();
                }
            }
            finally {
                ProfilerEventHandlerFactory.removeInstance(this.session);
                this.openStatements.clear();
                this.queryInterceptors = null;
                this.exceptionInterceptor = null;
                this.nullStatementResultSetFactory = null;
            }
            if (sqlEx != null) {
                throw sqlEx;
            }
        }
        catch (CJException ex2) {
            throw SQLExceptionsMapping.translateException(ex2, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void recachePreparedStatement(final JdbcPreparedStatement pstmt) throws SQLException {
        try {
            synchronized (this.getConnectionMutex()) {
                if (this.cachePrepStmts.getValue() && pstmt.isPoolable()) {
                    synchronized (this.serverSideStatementCache) {
                        final Object oldServerPrepStmt = this.serverSideStatementCache.put(new CompoundCacheKey(pstmt.getCurrentCatalog(), ((PreparedQuery)pstmt.getQuery()).getOriginalSql()), (ServerPreparedStatement)pstmt);
                        if (oldServerPrepStmt != null && oldServerPrepStmt != pstmt) {
                            ((ServerPreparedStatement)oldServerPrepStmt).isCached = false;
                            ((ServerPreparedStatement)oldServerPrepStmt).setClosed(false);
                            ((ServerPreparedStatement)oldServerPrepStmt).realClose(true, true);
                        }
                    }
                }
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void decachePreparedStatement(final JdbcPreparedStatement pstmt) throws SQLException {
        try {
            synchronized (this.getConnectionMutex()) {
                if (this.cachePrepStmts.getValue() && pstmt.isPoolable()) {
                    synchronized (this.serverSideStatementCache) {
                        this.serverSideStatementCache.remove(new CompoundCacheKey(pstmt.getCurrentCatalog(), ((PreparedQuery)pstmt.getQuery()).getOriginalSql()));
                    }
                }
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void registerStatement(final JdbcStatement stmt) {
        this.openStatements.addIfAbsent(stmt);
    }
    
    @Override
    public void releaseSavepoint(final Savepoint arg0) throws SQLException {
    }
    
    @Override
    public void resetServerState() throws SQLException {
        try {
            if (!this.propertySet.getBooleanProperty(PropertyKey.paranoid).getValue() && this.session != null) {
                this.changeUser(this.user, this.password);
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void rollback() throws SQLException {
        try {
            synchronized (this.getConnectionMutex()) {
                this.checkClosed();
                try {
                    if (this.connectionLifecycleInterceptors != null) {
                        final IterateBlock<ConnectionLifecycleInterceptor> iter = new IterateBlock<ConnectionLifecycleInterceptor>(this.connectionLifecycleInterceptors.iterator()) {
                            @Override
                            void forEach(final ConnectionLifecycleInterceptor each) throws SQLException {
                                if (!each.rollback()) {
                                    this.stopIterating = true;
                                }
                            }
                        };
                        iter.doForAll();
                        if (!iter.fullIteration()) {
                            return;
                        }
                    }
                    if (this.session.getServerSession().isAutoCommit()) {
                        throw SQLError.createSQLException(Messages.getString("Connection.20"), "08003", this.getExceptionInterceptor());
                    }
                    try {
                        this.rollbackNoChecks();
                    }
                    catch (SQLException sqlEx) {
                        if (this.ignoreNonTxTables.getInitialValue() && sqlEx.getErrorCode() == 1196) {
                            return;
                        }
                        throw sqlEx;
                    }
                }
                catch (SQLException sqlException) {
                    if ("08S01".equals(sqlException.getSQLState())) {
                        throw SQLError.createSQLException(Messages.getString("Connection.21"), "08007", this.getExceptionInterceptor());
                    }
                    throw sqlException;
                }
                finally {
                    this.session.setNeedsPing(this.reconnectAtTxEnd.getValue());
                }
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void rollback(final Savepoint savepoint) throws SQLException {
        try {
            synchronized (this.getConnectionMutex()) {
                this.checkClosed();
                try {
                    if (this.connectionLifecycleInterceptors != null) {
                        final IterateBlock<ConnectionLifecycleInterceptor> iter = new IterateBlock<ConnectionLifecycleInterceptor>(this.connectionLifecycleInterceptors.iterator()) {
                            @Override
                            void forEach(final ConnectionLifecycleInterceptor each) throws SQLException {
                                if (!each.rollback(savepoint)) {
                                    this.stopIterating = true;
                                }
                            }
                        };
                        iter.doForAll();
                        if (!iter.fullIteration()) {
                            return;
                        }
                    }
                    final StringBuilder rollbackQuery = new StringBuilder("ROLLBACK TO SAVEPOINT ");
                    rollbackQuery.append('`');
                    rollbackQuery.append(savepoint.getSavepointName());
                    rollbackQuery.append('`');
                    Statement stmt = null;
                    try {
                        stmt = this.getMetadataSafeStatement();
                        stmt.executeUpdate(rollbackQuery.toString());
                    }
                    catch (SQLException sqlEx) {
                        final int errno = sqlEx.getErrorCode();
                        if (errno == 1181) {
                            final String msg = sqlEx.getMessage();
                            if (msg != null) {
                                final int indexOfError153 = msg.indexOf("153");
                                if (indexOfError153 != -1) {
                                    throw SQLError.createSQLException(Messages.getString("Connection.22", new Object[] { savepoint.getSavepointName() }), "S1009", errno, this.getExceptionInterceptor());
                                }
                            }
                        }
                        if (this.ignoreNonTxTables.getValue() && sqlEx.getErrorCode() != 1196) {
                            throw sqlEx;
                        }
                        if ("08S01".equals(sqlEx.getSQLState())) {
                            throw SQLError.createSQLException(Messages.getString("Connection.23"), "08007", this.getExceptionInterceptor());
                        }
                        throw sqlEx;
                    }
                    finally {
                        this.closeStatement(stmt);
                    }
                }
                finally {
                    this.session.setNeedsPing(this.reconnectAtTxEnd.getValue());
                }
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    private void rollbackNoChecks() throws SQLException {
        try {
            synchronized (this.getConnectionMutex()) {
                if (this.useLocalTransactionState.getValue() && !this.session.getServerSession().inTransactionOnServer()) {
                    return;
                }
                this.session.execSQL(null, "rollback", -1, null, false, (ProtocolEntityFactory<Resultset, NativePacketPayload>)this.nullStatementResultSetFactory, this.database, null, false);
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public PreparedStatement serverPrepareStatement(final String sql) throws SQLException {
        try {
            final String nativeSql = this.processEscapeCodesForPrepStmts.getValue() ? this.nativeSQL(sql) : sql;
            return ServerPreparedStatement.getInstance(this.getMultiHostSafeProxy(), nativeSql, this.getCatalog(), 1003, 1007);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public PreparedStatement serverPrepareStatement(final String sql, final int autoGenKeyIndex) throws SQLException {
        try {
            final String nativeSql = this.processEscapeCodesForPrepStmts.getValue() ? this.nativeSQL(sql) : sql;
            final ClientPreparedStatement pStmt = ServerPreparedStatement.getInstance(this.getMultiHostSafeProxy(), nativeSql, this.getCatalog(), 1003, 1007);
            pStmt.setRetrieveGeneratedKeys(autoGenKeyIndex == 1);
            return pStmt;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public PreparedStatement serverPrepareStatement(final String sql, final int resultSetType, final int resultSetConcurrency) throws SQLException {
        try {
            final String nativeSql = this.processEscapeCodesForPrepStmts.getValue() ? this.nativeSQL(sql) : sql;
            return ServerPreparedStatement.getInstance(this.getMultiHostSafeProxy(), nativeSql, this.getCatalog(), resultSetType, resultSetConcurrency);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public PreparedStatement serverPrepareStatement(final String sql, final int resultSetType, final int resultSetConcurrency, final int resultSetHoldability) throws SQLException {
        try {
            if (this.pedantic.getValue() && resultSetHoldability != 1) {
                throw SQLError.createSQLException(Messages.getString("Connection.17"), "S1009", this.getExceptionInterceptor());
            }
            return this.serverPrepareStatement(sql, resultSetType, resultSetConcurrency);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public PreparedStatement serverPrepareStatement(final String sql, final int[] autoGenKeyIndexes) throws SQLException {
        try {
            final ClientPreparedStatement pStmt = (ClientPreparedStatement)this.serverPrepareStatement(sql);
            pStmt.setRetrieveGeneratedKeys(autoGenKeyIndexes != null && autoGenKeyIndexes.length > 0);
            return pStmt;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public PreparedStatement serverPrepareStatement(final String sql, final String[] autoGenKeyColNames) throws SQLException {
        try {
            final ClientPreparedStatement pStmt = (ClientPreparedStatement)this.serverPrepareStatement(sql);
            pStmt.setRetrieveGeneratedKeys(autoGenKeyColNames != null && autoGenKeyColNames.length > 0);
            return pStmt;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setAutoCommit(final boolean autoCommitFlag) throws SQLException {
        try {
            synchronized (this.getConnectionMutex()) {
                this.checkClosed();
                if (this.connectionLifecycleInterceptors != null) {
                    final IterateBlock<ConnectionLifecycleInterceptor> iter = new IterateBlock<ConnectionLifecycleInterceptor>(this.connectionLifecycleInterceptors.iterator()) {
                        @Override
                        void forEach(final ConnectionLifecycleInterceptor each) throws SQLException {
                            if (!each.setAutoCommit(autoCommitFlag)) {
                                this.stopIterating = true;
                            }
                        }
                    };
                    iter.doForAll();
                    if (!iter.fullIteration()) {
                        return;
                    }
                }
                if (this.autoReconnectForPools.getValue()) {
                    this.autoReconnect.setValue(true);
                }
                try {
                    boolean needsSetOnServer = true;
                    if (this.useLocalSessionState.getValue() && this.session.getServerSession().isAutoCommit() == autoCommitFlag) {
                        needsSetOnServer = false;
                    }
                    else if (!this.autoReconnect.getValue()) {
                        needsSetOnServer = this.getSession().isSetNeededForAutoCommitMode(autoCommitFlag);
                    }
                    this.session.getServerSession().setAutoCommit(autoCommitFlag);
                    if (needsSetOnServer) {
                        this.session.execSQL(null, autoCommitFlag ? "SET autocommit=1" : "SET autocommit=0", -1, null, false, (ProtocolEntityFactory<Resultset, NativePacketPayload>)this.nullStatementResultSetFactory, this.database, null, false);
                    }
                }
                finally {
                    if (this.autoReconnectForPools.getValue()) {
                        this.autoReconnect.setValue(false);
                    }
                }
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setCatalog(final String catalog) throws SQLException {
        try {
            synchronized (this.getConnectionMutex()) {
                this.checkClosed();
                if (catalog == null) {
                    throw SQLError.createSQLException("Catalog can not be null", "S1009", this.getExceptionInterceptor());
                }
                if (this.connectionLifecycleInterceptors != null) {
                    final IterateBlock<ConnectionLifecycleInterceptor> iter = new IterateBlock<ConnectionLifecycleInterceptor>(this.connectionLifecycleInterceptors.iterator()) {
                        @Override
                        void forEach(final ConnectionLifecycleInterceptor each) throws SQLException {
                            if (!each.setCatalog(catalog)) {
                                this.stopIterating = true;
                            }
                        }
                    };
                    iter.doForAll();
                    if (!iter.fullIteration()) {
                        return;
                    }
                }
                if (this.useLocalSessionState.getValue()) {
                    if (this.session.getServerSession().isLowerCaseTableNames()) {
                        if (this.database.equalsIgnoreCase(catalog)) {
                            return;
                        }
                    }
                    else if (this.database.equals(catalog)) {
                        return;
                    }
                }
                String quotedId = this.session.getIdentifierQuoteString();
                if (quotedId == null || quotedId.equals(" ")) {
                    quotedId = "";
                }
                final StringBuilder query = new StringBuilder("USE ");
                query.append(StringUtils.quoteIdentifier(catalog, quotedId, this.pedantic.getValue()));
                this.session.execSQL(null, query.toString(), -1, null, false, (ProtocolEntityFactory<Resultset, NativePacketPayload>)this.nullStatementResultSetFactory, this.database, null, false);
                this.database = catalog;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setFailedOver(final boolean flag) {
    }
    
    @Override
    public void setHoldability(final int arg0) throws SQLException {
    }
    
    @Override
    public void setInGlobalTx(final boolean flag) {
        this.isInGlobalTx = flag;
    }
    
    @Override
    public void setReadOnly(final boolean readOnlyFlag) throws SQLException {
        try {
            this.checkClosed();
            this.setReadOnlyInternal(readOnlyFlag);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setReadOnlyInternal(final boolean readOnlyFlag) throws SQLException {
        try {
            synchronized (this.getConnectionMutex()) {
                if (this.readOnlyPropagatesToServer.getValue() && this.versionMeetsMinimum(5, 6, 5) && (!this.useLocalSessionState.getValue() || readOnlyFlag != this.readOnly)) {
                    this.session.execSQL(null, "set session transaction " + (readOnlyFlag ? "read only" : "read write"), -1, null, false, (ProtocolEntityFactory<Resultset, NativePacketPayload>)this.nullStatementResultSetFactory, this.database, null, false);
                }
                this.readOnly = readOnlyFlag;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public Savepoint setSavepoint() throws SQLException {
        try {
            final MysqlSavepoint savepoint = new MysqlSavepoint(this.getExceptionInterceptor());
            this.setSavepoint(savepoint);
            return savepoint;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    private void setSavepoint(final MysqlSavepoint savepoint) throws SQLException {
        try {
            synchronized (this.getConnectionMutex()) {
                this.checkClosed();
                final StringBuilder savePointQuery = new StringBuilder("SAVEPOINT ");
                savePointQuery.append('`');
                savePointQuery.append(savepoint.getSavepointName());
                savePointQuery.append('`');
                Statement stmt = null;
                try {
                    stmt = this.getMetadataSafeStatement();
                    stmt.executeUpdate(savePointQuery.toString());
                }
                finally {
                    this.closeStatement(stmt);
                }
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public Savepoint setSavepoint(final String name) throws SQLException {
        try {
            synchronized (this.getConnectionMutex()) {
                final MysqlSavepoint savepoint = new MysqlSavepoint(name, this.getExceptionInterceptor());
                this.setSavepoint(savepoint);
                return savepoint;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setTransactionIsolation(final int level) throws SQLException {
        try {
            synchronized (this.getConnectionMutex()) {
                this.checkClosed();
                String sql = null;
                boolean shouldSendSet = false;
                if (this.propertySet.getBooleanProperty(PropertyKey.alwaysSendSetIsolation).getValue()) {
                    shouldSendSet = true;
                }
                else if (level != this.isolationLevel) {
                    shouldSendSet = true;
                }
                if (this.useLocalSessionState.getValue()) {
                    shouldSendSet = (this.isolationLevel != level);
                }
                if (shouldSendSet) {
                    switch (level) {
                        case 0: {
                            throw SQLError.createSQLException(Messages.getString("Connection.24"), this.getExceptionInterceptor());
                        }
                        case 2: {
                            sql = "SET SESSION TRANSACTION ISOLATION LEVEL READ COMMITTED";
                            break;
                        }
                        case 1: {
                            sql = "SET SESSION TRANSACTION ISOLATION LEVEL READ UNCOMMITTED";
                            break;
                        }
                        case 4: {
                            sql = "SET SESSION TRANSACTION ISOLATION LEVEL REPEATABLE READ";
                            break;
                        }
                        case 8: {
                            sql = "SET SESSION TRANSACTION ISOLATION LEVEL SERIALIZABLE";
                            break;
                        }
                        default: {
                            throw SQLError.createSQLException(Messages.getString("Connection.25", new Object[] { level }), "S1C00", this.getExceptionInterceptor());
                        }
                    }
                    this.session.execSQL(null, sql, -1, null, false, (ProtocolEntityFactory<Resultset, NativePacketPayload>)this.nullStatementResultSetFactory, this.database, null, false);
                    this.isolationLevel = level;
                }
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setTypeMap(final Map<String, Class<?>> map) throws SQLException {
        try {
            synchronized (this.getConnectionMutex()) {
                this.typeMap = map;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    private void setupServerForTruncationChecks() throws SQLException {
        try {
            synchronized (this.getConnectionMutex()) {
                final RuntimeProperty<Boolean> jdbcCompliantTruncation = this.propertySet.getProperty(PropertyKey.jdbcCompliantTruncation);
                if (jdbcCompliantTruncation.getValue()) {
                    final String currentSqlMode = this.session.getServerSession().getServerVariable("sql_mode");
                    final boolean strictTransTablesIsSet = StringUtils.indexOfIgnoreCase(currentSqlMode, "STRICT_TRANS_TABLES") != -1;
                    if (currentSqlMode == null || currentSqlMode.length() == 0 || !strictTransTablesIsSet) {
                        final StringBuilder commandBuf = new StringBuilder("SET sql_mode='");
                        if (currentSqlMode != null && currentSqlMode.length() > 0) {
                            commandBuf.append(currentSqlMode);
                            commandBuf.append(",");
                        }
                        commandBuf.append("STRICT_TRANS_TABLES'");
                        this.session.execSQL(null, commandBuf.toString(), -1, null, false, (ProtocolEntityFactory<Resultset, NativePacketPayload>)this.nullStatementResultSetFactory, this.database, null, false);
                        jdbcCompliantTruncation.setValue(false);
                    }
                    else if (strictTransTablesIsSet) {
                        jdbcCompliantTruncation.setValue(false);
                    }
                }
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void shutdownServer() throws SQLException {
        try {
            try {
                this.session.shutdownServer();
            }
            catch (CJException ex) {
                final SQLException sqlEx = SQLError.createSQLException(Messages.getString("Connection.UnhandledExceptionDuringShutdown"), "S1000", this.getExceptionInterceptor());
                sqlEx.initCause(ex);
                throw sqlEx;
            }
        }
        catch (CJException ex2) {
            throw SQLExceptionsMapping.translateException(ex2, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void unregisterStatement(final JdbcStatement stmt) {
        this.openStatements.remove(stmt);
    }
    
    public boolean versionMeetsMinimum(final int major, final int minor, final int subminor) {
        try {
            this.checkClosed();
            return this.session.versionMeetsMinimum(major, minor, subminor);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public CachedResultSetMetaData getCachedMetaData(final String sql) {
        if (this.resultSetMetadataCache != null) {
            synchronized (this.resultSetMetadataCache) {
                return this.resultSetMetadataCache.get(sql);
            }
        }
        return null;
    }
    
    @Override
    public void initializeResultsMetadataFromCache(final String sql, CachedResultSetMetaData cachedMetaData, final ResultSetInternalMethods resultSet) throws SQLException {
        try {
            if (cachedMetaData == null) {
                cachedMetaData = new CachedResultSetMetaDataImpl();
                resultSet.getColumnDefinition().buildIndexMapping();
                resultSet.initializeWithMetadata();
                if (resultSet instanceof UpdatableResultSet) {
                    ((UpdatableResultSet)resultSet).checkUpdatability();
                }
                resultSet.populateCachedMetaData(cachedMetaData);
                this.resultSetMetadataCache.put(sql, cachedMetaData);
            }
            else {
                resultSet.getColumnDefinition().initializeFrom(cachedMetaData);
                resultSet.initializeWithMetadata();
                if (resultSet instanceof UpdatableResultSet) {
                    ((UpdatableResultSet)resultSet).checkUpdatability();
                }
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public String getStatementComment() {
        return this.session.getProtocol().getQueryComment();
    }
    
    @Override
    public void setStatementComment(final String comment) {
        this.session.getProtocol().setQueryComment(comment);
    }
    
    @Override
    public void transactionBegun() {
        synchronized (this.getConnectionMutex()) {
            if (this.connectionLifecycleInterceptors != null) {
                this.connectionLifecycleInterceptors.stream().forEach(ConnectionLifecycleInterceptor::transactionBegun);
            }
        }
    }
    
    @Override
    public void transactionCompleted() {
        synchronized (this.getConnectionMutex()) {
            if (this.connectionLifecycleInterceptors != null) {
                this.connectionLifecycleInterceptors.stream().forEach(ConnectionLifecycleInterceptor::transactionCompleted);
            }
        }
    }
    
    @Override
    public boolean storesLowerCaseTableName() {
        return this.session.getServerSession().storesLowerCaseTableNames();
    }
    
    @Override
    public ExceptionInterceptor getExceptionInterceptor() {
        return this.exceptionInterceptor;
    }
    
    @Override
    public boolean isServerLocal() throws SQLException {
        try {
            synchronized (this.getConnectionMutex()) {
                try {
                    return this.session.isServerLocal(this.getSession());
                }
                catch (CJException ex) {
                    final SQLException sqlEx = SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
                    throw sqlEx;
                }
            }
        }
        catch (CJException ex2) {
            throw SQLExceptionsMapping.translateException(ex2, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public int getSessionMaxRows() {
        synchronized (this.getConnectionMutex()) {
            return this.session.getSessionMaxRows();
        }
    }
    
    @Override
    public void setSessionMaxRows(final int max) throws SQLException {
        try {
            synchronized (this.getConnectionMutex()) {
                if (this.session.getSessionMaxRows() != max) {
                    this.session.setSessionMaxRows(max);
                    this.session.execSQL(null, "SET SQL_SELECT_LIMIT=" + ((this.session.getSessionMaxRows() == -1) ? "DEFAULT" : Integer.valueOf(this.session.getSessionMaxRows())), -1, null, false, (ProtocolEntityFactory<Resultset, NativePacketPayload>)this.nullStatementResultSetFactory, this.database, null, false);
                }
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setSchema(final String schema) throws SQLException {
        try {
            synchronized (this.getConnectionMutex()) {
                this.checkClosed();
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public String getSchema() throws SQLException {
        try {
            synchronized (this.getConnectionMutex()) {
                this.checkClosed();
                return null;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void abort(final Executor executor) throws SQLException {
        try {
            final SecurityManager sec = System.getSecurityManager();
            if (sec != null) {
                sec.checkPermission(ConnectionImpl.ABORT_PERM);
            }
            if (executor == null) {
                throw SQLError.createSQLException(Messages.getString("Connection.26"), "S1009", this.getExceptionInterceptor());
            }
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        ConnectionImpl.this.abortInternal();
                    }
                    catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setNetworkTimeout(final Executor executor, final int milliseconds) throws SQLException {
        try {
            synchronized (this.getConnectionMutex()) {
                final SecurityManager sec = System.getSecurityManager();
                if (sec != null) {
                    sec.checkPermission(ConnectionImpl.SET_NETWORK_TIMEOUT_PERM);
                }
                if (executor == null) {
                    throw SQLError.createSQLException(Messages.getString("Connection.26"), "S1009", this.getExceptionInterceptor());
                }
                this.checkClosed();
                executor.execute(new NetworkTimeoutSetter(this, milliseconds));
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public int getNetworkTimeout() throws SQLException {
        try {
            synchronized (this.getConnectionMutex()) {
                this.checkClosed();
                return this.session.getSocketTimeout();
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public Clob createClob() {
        try {
            return new com.mysql.cj.jdbc.Clob(this.getExceptionInterceptor());
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public Blob createBlob() {
        try {
            return new com.mysql.cj.jdbc.Blob(this.getExceptionInterceptor());
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public NClob createNClob() {
        try {
            return new com.mysql.cj.jdbc.NClob(this.getExceptionInterceptor());
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public SQLXML createSQLXML() throws SQLException {
        try {
            return new MysqlSQLXML(this.getExceptionInterceptor());
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean isValid(final int timeout) throws SQLException {
        try {
            synchronized (this.getConnectionMutex()) {
                if (this.isClosed()) {
                    return false;
                }
                try {
                    try {
                        this.pingInternal(false, timeout * 1000);
                    }
                    catch (Throwable t) {
                        try {
                            this.abortInternal();
                        }
                        catch (Throwable t2) {}
                        return false;
                    }
                }
                catch (Throwable t) {
                    return false;
                }
                return true;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public ClientInfoProvider getClientInfoProviderImpl() throws SQLException {
        try {
            synchronized (this.getConnectionMutex()) {
                if (this.infoProvider == null) {
                    final String clientInfoProvider = this.propertySet.getStringProperty(PropertyKey.clientInfoProvider).getStringValue();
                    try {
                        try {
                            this.infoProvider = (ClientInfoProvider)Util.getInstance(clientInfoProvider, new Class[0], new Object[0], this.getExceptionInterceptor());
                        }
                        catch (CJException ex) {
                            if (ex.getCause() instanceof ClassCastException) {
                                try {
                                    this.infoProvider = (ClientInfoProvider)Util.getInstance("com.mysql.cj.jdbc." + clientInfoProvider, new Class[0], new Object[0], this.getExceptionInterceptor());
                                }
                                catch (CJException e) {
                                    throw SQLExceptionsMapping.translateException(e, this.getExceptionInterceptor());
                                }
                            }
                        }
                    }
                    catch (ClassCastException cce) {
                        throw SQLError.createSQLException(Messages.getString("Connection.ClientInfoNotImplemented", new Object[] { clientInfoProvider }), "S1009", this.getExceptionInterceptor());
                    }
                    this.infoProvider.initialize(this, this.props);
                }
                return this.infoProvider;
            }
        }
        catch (CJException ex2) {
            throw SQLExceptionsMapping.translateException(ex2, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setClientInfo(final String name, final String value) throws SQLClientInfoException {
        try {
            this.getClientInfoProviderImpl().setClientInfo(this, name, value);
        }
        catch (SQLClientInfoException ciEx) {
            throw ciEx;
        }
        catch (SQLException | CJException ex2) {
            final Exception ex;
            final Exception sqlEx = ex;
            final SQLClientInfoException clientInfoEx = new SQLClientInfoException();
            clientInfoEx.initCause(sqlEx);
            throw clientInfoEx;
        }
    }
    
    @Override
    public void setClientInfo(final Properties properties) throws SQLClientInfoException {
        try {
            this.getClientInfoProviderImpl().setClientInfo(this, properties);
        }
        catch (SQLClientInfoException ciEx) {
            throw ciEx;
        }
        catch (SQLException | CJException ex2) {
            final Exception ex;
            final Exception sqlEx = ex;
            final SQLClientInfoException clientInfoEx = new SQLClientInfoException();
            clientInfoEx.initCause(sqlEx);
            throw clientInfoEx;
        }
    }
    
    @Override
    public String getClientInfo(final String name) throws SQLException {
        try {
            return this.getClientInfoProviderImpl().getClientInfo(this, name);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public Properties getClientInfo() throws SQLException {
        try {
            return this.getClientInfoProviderImpl().getClientInfo(this);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public Array createArrayOf(final String typeName, final Object[] elements) throws SQLException {
        try {
            throw SQLError.createSQLFeatureNotSupportedException();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public Struct createStruct(final String typeName, final Object[] attributes) throws SQLException {
        try {
            throw SQLError.createSQLFeatureNotSupportedException();
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
                throw SQLError.createSQLException("Unable to unwrap to " + iface.toString(), "S1009", this.getExceptionInterceptor());
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean isWrapperFor(final Class<?> iface) throws SQLException {
        try {
            this.checkClosed();
            return iface.isInstance(this);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public NativeSession getSession() {
        return this.session;
    }
    
    @Override
    public String getHostPortPair() {
        return this.origHostInfo.getHostPortPair();
    }
    
    @Override
    public void handleNormalClose() {
        try {
            this.close();
        }
        catch (SQLException e) {
            ExceptionFactory.createException(e.getMessage(), e);
        }
    }
    
    @Override
    public void handleReconnect() {
        this.createNewIO(true);
    }
    
    @Override
    public void handleCleanup(final Throwable whyCleanedUp) {
        this.cleanup(whyCleanedUp);
    }
    
    static {
        SET_NETWORK_TIMEOUT_PERM = new SQLPermission("setNetworkTimeout");
        ABORT_PERM = new SQLPermission("abort");
        DEFAULT_LOGGER_CLASS = StandardLogger.class.getName();
        ConnectionImpl.mapTransIsolationNameToValue = null;
        (ConnectionImpl.mapTransIsolationNameToValue = new HashMap<String, Integer>(8)).put("READ-UNCOMMITED", 1);
        ConnectionImpl.mapTransIsolationNameToValue.put("READ-UNCOMMITTED", 1);
        ConnectionImpl.mapTransIsolationNameToValue.put("READ-COMMITTED", 2);
        ConnectionImpl.mapTransIsolationNameToValue.put("REPEATABLE-READ", 4);
        ConnectionImpl.mapTransIsolationNameToValue.put("SERIALIZABLE", 8);
        random = new Random();
    }
    
    static class CompoundCacheKey
    {
        final String componentOne;
        final String componentTwo;
        final int hashCode;
        
        CompoundCacheKey(final String partOne, final String partTwo) {
            this.componentOne = partOne;
            this.componentTwo = partTwo;
            int hc = 17;
            hc = 31 * hc + ((this.componentOne != null) ? this.componentOne.hashCode() : 0);
            hc = 31 * hc + ((this.componentTwo != null) ? this.componentTwo.hashCode() : 0);
            this.hashCode = hc;
        }
        
        @Override
        public boolean equals(final Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj != null && CompoundCacheKey.class.isAssignableFrom(obj.getClass())) {
                final CompoundCacheKey another = (CompoundCacheKey)obj;
                if (this.componentOne == null) {
                    if (another.componentOne != null) {
                        return false;
                    }
                }
                else if (!this.componentOne.equals(another.componentOne)) {
                    return false;
                }
                return (this.componentTwo == null) ? (another.componentTwo == null) : this.componentTwo.equals(another.componentTwo);
            }
            return false;
        }
        
        @Override
        public int hashCode() {
            return this.hashCode;
        }
    }
    
    private static class NetworkTimeoutSetter implements Runnable
    {
        private final WeakReference<JdbcConnection> connRef;
        private final int milliseconds;
        
        public NetworkTimeoutSetter(final JdbcConnection conn, final int milliseconds) {
            this.connRef = new WeakReference<JdbcConnection>(conn);
            this.milliseconds = milliseconds;
        }
        
        @Override
        public void run() {
            final JdbcConnection conn = this.connRef.get();
            if (conn != null) {
                synchronized (conn.getConnectionMutex()) {
                    ((NativeSession)conn.getSession()).setSocketTimeout(this.milliseconds);
                }
            }
        }
    }
}
