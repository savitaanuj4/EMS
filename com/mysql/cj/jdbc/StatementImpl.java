
package com.mysql.cj.jdbc;

import java.util.concurrent.atomic.AtomicBoolean;
import com.mysql.cj.log.ProfilerEventHandler;
import com.mysql.cj.protocol.ProtocolEntityFactory;
import com.mysql.cj.protocol.Message;
import com.mysql.cj.exceptions.CJOperationNotSupportedException;
import com.mysql.cj.ParseInfo;
import java.io.InputStream;
import com.mysql.cj.log.ProfilerEvent;
import com.mysql.cj.log.ProfilerEventImpl;
import com.mysql.cj.Constants;
import java.math.BigInteger;
import java.sql.Connection;
import com.mysql.cj.jdbc.result.ResultSetImpl;
import com.mysql.cj.protocol.ResultsetRows;
import com.mysql.cj.protocol.a.result.ResultsetRowsStatic;
import com.mysql.cj.result.DefaultColumnDefinition;
import com.mysql.cj.MysqlType;
import com.mysql.cj.result.Field;
import java.sql.ResultSet;
import java.sql.BatchUpdateException;
import com.mysql.cj.protocol.a.result.ByteArrayRow;
import com.mysql.cj.CharsetMapping;
import com.mysql.cj.jdbc.exceptions.MySQLStatementCancelledException;
import com.mysql.cj.jdbc.exceptions.MySQLTimeoutException;
import com.mysql.cj.util.Util;
import com.mysql.cj.jdbc.result.CachedResultSetMetaData;
import com.mysql.cj.exceptions.OperationCancelledException;
import com.mysql.cj.exceptions.CJTimeoutException;
import com.mysql.cj.protocol.ColumnDefinition;
import com.mysql.cj.protocol.a.NativePacketPayload;
import com.mysql.cj.CancelQueryTask;
import java.sql.PreparedStatement;
import java.util.Iterator;
import com.mysql.cj.exceptions.AssertionFailedException;
import com.mysql.cj.exceptions.ExceptionFactory;
import com.mysql.cj.exceptions.StatementIsClosedException;
import com.mysql.cj.conf.HostInfo;
import java.sql.Statement;
import java.io.IOException;
import com.mysql.cj.TransactionEventHandler;
import com.mysql.cj.util.StringUtils;
import java.util.List;
import com.mysql.cj.SimpleQuery;
import java.sql.SQLException;
import com.mysql.cj.exceptions.CJException;
import com.mysql.cj.jdbc.exceptions.SQLExceptionsMapping;
import com.mysql.cj.Session;
import com.mysql.cj.log.ProfilerEventHandlerFactory;
import com.mysql.cj.util.LogUtils;
import com.mysql.cj.jdbc.exceptions.SQLError;
import com.mysql.cj.Messages;
import java.util.HashSet;
import com.mysql.cj.conf.PropertyDefinitions;
import com.mysql.cj.conf.PropertyKey;
import com.mysql.cj.protocol.Resultset;
import com.mysql.cj.NativeSession;
import com.mysql.cj.Query;
import com.mysql.cj.jdbc.result.ResultSetFactory;
import com.mysql.cj.conf.RuntimeProperty;
import com.mysql.cj.exceptions.ExceptionInterceptor;
import com.mysql.cj.PingTarget;
import com.mysql.cj.result.Row;
import java.util.ArrayList;
import java.sql.SQLWarning;
import com.mysql.cj.jdbc.result.ResultSetInternalMethods;
import java.util.Set;
import com.mysql.cj.protocol.a.NativeMessageBuilder;

public class StatementImpl implements JdbcStatement
{
    protected static final String PING_MARKER = "/* ping */";
    protected NativeMessageBuilder commandBuilder;
    public static final byte USES_VARIABLES_FALSE = 0;
    public static final byte USES_VARIABLES_TRUE = 1;
    public static final byte USES_VARIABLES_UNKNOWN = -1;
    protected String charEncoding;
    protected volatile JdbcConnection connection;
    protected boolean doEscapeProcessing;
    protected boolean isClosed;
    protected long lastInsertId;
    protected int maxFieldSize;
    public int maxRows;
    protected Set<ResultSetInternalMethods> openResults;
    protected boolean pedantic;
    protected String pointOfOrigin;
    protected boolean profileSQL;
    protected ResultSetInternalMethods results;
    protected ResultSetInternalMethods generatedKeysResults;
    protected int resultSetConcurrency;
    protected long updateCount;
    protected boolean useUsageAdvisor;
    protected SQLWarning warningChain;
    protected boolean holdResultsOpenOverClose;
    protected ArrayList<Row> batchedGeneratedKeys;
    protected boolean retrieveGeneratedKeys;
    protected boolean continueBatchOnError;
    protected PingTarget pingTarget;
    protected ExceptionInterceptor exceptionInterceptor;
    protected boolean lastQueryIsOnDupKeyUpdate;
    private boolean isImplicitlyClosingResults;
    protected RuntimeProperty<Boolean> dontTrackOpenResources;
    protected RuntimeProperty<Boolean> dumpQueriesOnException;
    protected boolean logSlowQueries;
    protected RuntimeProperty<Boolean> rewriteBatchedStatements;
    protected RuntimeProperty<Integer> maxAllowedPacket;
    protected boolean dontCheckOnDuplicateKeyUpdateInSQL;
    protected RuntimeProperty<Boolean> sendFractionalSeconds;
    protected ResultSetFactory resultSetFactory;
    protected Query query;
    protected NativeSession session;
    private Resultset.Type originalResultSetType;
    private int originalFetchSize;
    private boolean isPoolable;
    private boolean closeOnCompletion;
    
    public StatementImpl(final JdbcConnection c, final String catalog) throws SQLException {
        this.commandBuilder = new NativeMessageBuilder();
        this.charEncoding = null;
        this.connection = null;
        this.doEscapeProcessing = true;
        this.isClosed = false;
        this.lastInsertId = -1L;
        this.maxFieldSize = (int)PropertyDefinitions.getPropertyDefinition(PropertyKey.maxAllowedPacket).getDefaultValue();
        this.maxRows = -1;
        this.openResults = new HashSet<ResultSetInternalMethods>();
        this.pedantic = false;
        this.profileSQL = false;
        this.results = null;
        this.generatedKeysResults = null;
        this.resultSetConcurrency = 0;
        this.updateCount = -1L;
        this.useUsageAdvisor = false;
        this.warningChain = null;
        this.holdResultsOpenOverClose = false;
        this.batchedGeneratedKeys = null;
        this.retrieveGeneratedKeys = false;
        this.continueBatchOnError = false;
        this.pingTarget = null;
        this.lastQueryIsOnDupKeyUpdate = false;
        this.isImplicitlyClosingResults = false;
        this.logSlowQueries = false;
        this.session = null;
        this.originalResultSetType = Resultset.Type.FORWARD_ONLY;
        this.originalFetchSize = 0;
        this.isPoolable = true;
        this.closeOnCompletion = false;
        if (c == null || c.isClosed()) {
            throw SQLError.createSQLException(Messages.getString("Statement.0"), "08003", null);
        }
        this.connection = c;
        this.session = (NativeSession)c.getSession();
        this.exceptionInterceptor = c.getExceptionInterceptor();
        this.initQuery();
        this.query.setCurrentCatalog(catalog);
        final JdbcPropertySet pset = c.getPropertySet();
        this.dontTrackOpenResources = pset.getBooleanProperty(PropertyKey.dontTrackOpenResources);
        this.dumpQueriesOnException = pset.getBooleanProperty(PropertyKey.dumpQueriesOnException);
        this.continueBatchOnError = pset.getBooleanProperty(PropertyKey.continueBatchOnError).getValue();
        this.pedantic = pset.getBooleanProperty(PropertyKey.pedantic).getValue();
        this.rewriteBatchedStatements = pset.getBooleanProperty(PropertyKey.rewriteBatchedStatements);
        this.charEncoding = pset.getStringProperty(PropertyKey.characterEncoding).getValue();
        this.profileSQL = pset.getBooleanProperty(PropertyKey.profileSQL).getValue();
        this.useUsageAdvisor = pset.getBooleanProperty(PropertyKey.useUsageAdvisor).getValue();
        this.logSlowQueries = pset.getBooleanProperty(PropertyKey.logSlowQueries).getValue();
        this.maxAllowedPacket = pset.getIntegerProperty(PropertyKey.maxAllowedPacket);
        this.dontCheckOnDuplicateKeyUpdateInSQL = pset.getBooleanProperty(PropertyKey.dontCheckOnDuplicateKeyUpdateInSQL).getValue();
        this.sendFractionalSeconds = pset.getBooleanProperty(PropertyKey.sendFractionalSeconds);
        this.doEscapeProcessing = pset.getBooleanProperty(PropertyKey.enableEscapeProcessing).getValue();
        this.maxFieldSize = this.maxAllowedPacket.getValue();
        if (!this.dontTrackOpenResources.getValue()) {
            c.registerStatement(this);
        }
        final int defaultFetchSize = pset.getIntegerProperty(PropertyKey.defaultFetchSize).getValue();
        if (defaultFetchSize != 0) {
            this.setFetchSize(defaultFetchSize);
        }
        final boolean profiling = this.profileSQL || this.useUsageAdvisor || this.logSlowQueries;
        if (profiling) {
            this.pointOfOrigin = LogUtils.findCallingClassAndMethod(new Throwable());
            try {
                this.query.setEventSink(ProfilerEventHandlerFactory.getInstance(this.session));
            }
            catch (CJException e) {
                throw SQLExceptionsMapping.translateException(e, this.getExceptionInterceptor());
            }
        }
        final int maxRowsConn = pset.getIntegerProperty(PropertyKey.maxRows).getValue();
        if (maxRowsConn != -1) {
            this.setMaxRows(maxRowsConn);
        }
        this.holdResultsOpenOverClose = pset.getBooleanProperty(PropertyKey.holdResultsOpenOverStatementClose).getValue();
        this.resultSetFactory = new ResultSetFactory(this.connection, this);
    }
    
    protected void initQuery() {
        this.query = new SimpleQuery(this.session);
    }
    
    @Override
    public void addBatch(final String sql) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                if (sql != null) {
                    this.query.addBatch(sql);
                }
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void addBatch(final Object batch) {
        this.query.addBatch(batch);
    }
    
    @Override
    public List<Object> getBatchedArgs() {
        return this.query.getBatchedArgs();
    }
    
    @Override
    public void cancel() throws SQLException {
        try {
            if (!this.query.getStatementExecuting().get()) {
                return;
            }
            if (!this.isClosed && this.connection != null) {
                final JdbcConnection cancelConn = null;
                final Statement cancelStmt = null;
                try {
                    final HostInfo hostInfo = this.session.getHostInfo();
                    final String database = hostInfo.getDatabase();
                    final String user = StringUtils.isNullOrEmpty(hostInfo.getUser()) ? "" : hostInfo.getUser();
                    final String password = StringUtils.isNullOrEmpty(hostInfo.getPassword()) ? "" : hostInfo.getPassword();
                    final NativeSession newSession = new NativeSession(this.session.getHostInfo(), this.session.getPropertySet());
                    newSession.connect(hostInfo, user, password, database, 30000, new TransactionEventHandler() {
                        @Override
                        public void transactionCompleted() {
                        }
                        
                        @Override
                        public void transactionBegun() {
                        }
                    });
                    newSession.sendCommand(new NativeMessageBuilder().buildComQuery(newSession.getSharedSendPacket(), "KILL QUERY " + this.session.getThreadId()), false, 0);
                    this.setCancelStatus(Query.CancelStatus.CANCELED_BY_USER);
                }
                catch (IOException e) {
                    throw SQLExceptionsMapping.translateException(e, this.exceptionInterceptor);
                }
                finally {
                    if (cancelStmt != null) {
                        cancelStmt.close();
                    }
                    if (cancelConn != null) {
                        cancelConn.close();
                    }
                }
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    protected JdbcConnection checkClosed() {
        final JdbcConnection c = this.connection;
        if (c == null) {
            throw ExceptionFactory.createException(StatementIsClosedException.class, Messages.getString("Statement.AlreadyClosed"), this.getExceptionInterceptor());
        }
        return c;
    }
    
    protected void checkForDml(final String sql, final char firstStatementChar) throws SQLException {
        if (firstStatementChar == 'I' || firstStatementChar == 'U' || firstStatementChar == 'D' || firstStatementChar == 'A' || firstStatementChar == 'C' || firstStatementChar == 'T' || firstStatementChar == 'R') {
            final String noCommentSql = StringUtils.stripComments(sql, "'\"", "'\"", true, false, true, true);
            if (StringUtils.startsWithIgnoreCaseAndWs(noCommentSql, "INSERT") || StringUtils.startsWithIgnoreCaseAndWs(noCommentSql, "UPDATE") || StringUtils.startsWithIgnoreCaseAndWs(noCommentSql, "DELETE") || StringUtils.startsWithIgnoreCaseAndWs(noCommentSql, "DROP") || StringUtils.startsWithIgnoreCaseAndWs(noCommentSql, "CREATE") || StringUtils.startsWithIgnoreCaseAndWs(noCommentSql, "ALTER") || StringUtils.startsWithIgnoreCaseAndWs(noCommentSql, "TRUNCATE") || StringUtils.startsWithIgnoreCaseAndWs(noCommentSql, "RENAME")) {
                throw SQLError.createSQLException(Messages.getString("Statement.57"), "S1009", this.getExceptionInterceptor());
            }
        }
    }
    
    protected void checkNullOrEmptyQuery(final String sql) throws SQLException {
        if (sql == null) {
            throw SQLError.createSQLException(Messages.getString("Statement.59"), "S1009", this.getExceptionInterceptor());
        }
        if (sql.length() == 0) {
            throw SQLError.createSQLException(Messages.getString("Statement.61"), "S1009", this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void clearBatch() throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                this.query.clearBatchedArgs();
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void clearBatchedArgs() {
        this.query.clearBatchedArgs();
    }
    
    @Override
    public void clearWarnings() throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                this.setClearWarningsCalled(true);
                this.warningChain = null;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void close() throws SQLException {
        try {
            this.realClose(true, true);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    protected void closeAllOpenResults() throws SQLException {
        final JdbcConnection locallyScopedConn = this.connection;
        if (locallyScopedConn == null) {
            return;
        }
        synchronized (locallyScopedConn.getConnectionMutex()) {
            if (this.openResults != null) {
                for (final ResultSetInternalMethods element : this.openResults) {
                    try {
                        element.realClose(false);
                    }
                    catch (SQLException sqlEx) {
                        AssertionFailedException.shouldNotHappen(sqlEx);
                    }
                }
                this.openResults.clear();
            }
        }
    }
    
    protected void implicitlyCloseAllOpenResults() throws SQLException {
        this.isImplicitlyClosingResults = true;
        try {
            if (!this.holdResultsOpenOverClose && !this.dontTrackOpenResources.getValue()) {
                if (this.results != null) {
                    this.results.realClose(false);
                }
                if (this.generatedKeysResults != null) {
                    this.generatedKeysResults.realClose(false);
                }
                this.closeAllOpenResults();
            }
        }
        finally {
            this.isImplicitlyClosingResults = false;
        }
    }
    
    @Override
    public void removeOpenResultSet(final ResultSetInternalMethods rs) {
        try {
            try {
                synchronized (this.checkClosed().getConnectionMutex()) {
                    if (this.openResults != null) {
                        this.openResults.remove(rs);
                    }
                    final boolean hasMoreResults = rs.getNextResultset() != null;
                    if (this.results == rs && !hasMoreResults) {
                        this.results = null;
                    }
                    if (this.generatedKeysResults == rs) {
                        this.generatedKeysResults = null;
                    }
                    if (!this.isImplicitlyClosingResults && !hasMoreResults) {
                        this.checkAndPerformCloseOnCompletionAction();
                    }
                }
            }
            catch (StatementIsClosedException ex2) {}
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public int getOpenResultSetCount() {
        try {
            try {
                synchronized (this.checkClosed().getConnectionMutex()) {
                    if (this.openResults != null) {
                        return this.openResults.size();
                    }
                    return 0;
                }
            }
            catch (StatementIsClosedException e) {
                return 0;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    private void checkAndPerformCloseOnCompletionAction() {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                if (this.isCloseOnCompletion() && !this.dontTrackOpenResources.getValue() && this.getOpenResultSetCount() == 0 && (this.results == null || !this.results.hasRows() || this.results.isClosed()) && (this.generatedKeysResults == null || !this.generatedKeysResults.hasRows() || this.generatedKeysResults.isClosed())) {
                    this.realClose(false, false);
                }
            }
        }
        catch (SQLException ex) {}
    }
    
    private ResultSetInternalMethods createResultSetUsingServerFetch(final String sql) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                final PreparedStatement pStmt = this.connection.prepareStatement(sql, this.query.getResultType().getIntValue(), this.resultSetConcurrency);
                pStmt.setFetchSize(this.query.getResultFetchSize());
                if (this.maxRows > -1) {
                    pStmt.setMaxRows(this.maxRows);
                }
                this.statementBegins();
                pStmt.execute();
                final ResultSetInternalMethods rs = ((StatementImpl)pStmt).getResultSetInternal();
                rs.setStatementUsedForFetchingRows((JdbcPreparedStatement)pStmt);
                return this.results = rs;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    protected boolean createStreamingResultSet() {
        return this.query.getResultType() == Resultset.Type.FORWARD_ONLY && this.resultSetConcurrency == 1007 && this.query.getResultFetchSize() == Integer.MIN_VALUE;
    }
    
    @Override
    public void enableStreamingResults() throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                this.originalResultSetType = this.query.getResultType();
                this.originalFetchSize = this.query.getResultFetchSize();
                this.setFetchSize(Integer.MIN_VALUE);
                this.setResultSetType(Resultset.Type.FORWARD_ONLY);
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void disableStreamingResults() throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                if (this.query.getResultFetchSize() == Integer.MIN_VALUE && this.query.getResultType() == Resultset.Type.FORWARD_ONLY) {
                    this.setFetchSize(this.originalFetchSize);
                    this.setResultSetType(this.originalResultSetType);
                }
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    protected void setupStreamingTimeout(final JdbcConnection con) throws SQLException {
        final int netTimeoutForStreamingResults = this.session.getPropertySet().getIntegerProperty(PropertyKey.netTimeoutForStreamingResults).getValue();
        if (this.createStreamingResultSet() && netTimeoutForStreamingResults > 0) {
            this.executeSimpleNonQuery(con, "SET net_write_timeout=" + netTimeoutForStreamingResults);
        }
    }
    
    @Override
    public CancelQueryTask startQueryTimer(final Query stmtToCancel, final int timeout) {
        return this.query.startQueryTimer(stmtToCancel, timeout);
    }
    
    @Override
    public void stopQueryTimer(final CancelQueryTask timeoutTask, final boolean rethrowCancelReason, final boolean checkCancelTimeout) {
        this.query.stopQueryTimer(timeoutTask, rethrowCancelReason, checkCancelTimeout);
    }
    
    @Override
    public boolean execute(final String sql) throws SQLException {
        try {
            return this.executeInternal(sql, false);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    private boolean executeInternal(String sql, final boolean returnGeneratedKeys) throws SQLException {
        try {
            final JdbcConnection locallyScopedConn = this.checkClosed();
            synchronized (locallyScopedConn.getConnectionMutex()) {
                this.checkClosed();
                this.checkNullOrEmptyQuery(sql);
                this.resetCancelledState();
                this.implicitlyCloseAllOpenResults();
                if (sql.charAt(0) == '/' && sql.startsWith("/* ping */")) {
                    this.doPingInstead();
                    return true;
                }
                final char firstNonWsChar = StringUtils.firstAlphaCharUc(sql, findStartOfStatement(sql));
                final boolean maybeSelect = firstNonWsChar == 'S';
                this.retrieveGeneratedKeys = returnGeneratedKeys;
                this.lastQueryIsOnDupKeyUpdate = (returnGeneratedKeys && firstNonWsChar == 'I' && this.containsOnDuplicateKeyInString(sql));
                if (!maybeSelect && locallyScopedConn.isReadOnly()) {
                    throw SQLError.createSQLException(Messages.getString("Statement.27") + Messages.getString("Statement.28"), "S1009", this.getExceptionInterceptor());
                }
                try {
                    this.setupStreamingTimeout(locallyScopedConn);
                    if (this.doEscapeProcessing) {
                        final Object escapedSqlResult = EscapeProcessor.escapeSQL(sql, this.session.getServerSession().getDefaultTimeZone(), this.session.getServerSession().getCapabilities().serverSupportsFracSecs(), this.session.getServerSession().isServerTruncatesFracSecs(), this.getExceptionInterceptor());
                        sql = (String)((escapedSqlResult instanceof String) ? escapedSqlResult : ((EscapeProcessorResult)escapedSqlResult).escapedSql);
                    }
                    CachedResultSetMetaData cachedMetaData = null;
                    ResultSetInternalMethods rs = null;
                    this.batchedGeneratedKeys = null;
                    if (this.useServerFetch()) {
                        rs = this.createResultSetUsingServerFetch(sql);
                    }
                    else {
                        CancelQueryTask timeoutTask = null;
                        String oldCatalog = null;
                        try {
                            timeoutTask = this.startQueryTimer(this, this.getTimeoutInMillis());
                            if (!locallyScopedConn.getCatalog().equals(this.getCurrentCatalog())) {
                                oldCatalog = locallyScopedConn.getCatalog();
                                locallyScopedConn.setCatalog(this.getCurrentCatalog());
                            }
                            if (locallyScopedConn.getPropertySet().getBooleanProperty(PropertyKey.cacheResultSetMetadata).getValue()) {
                                cachedMetaData = locallyScopedConn.getCachedMetaData(sql);
                            }
                            locallyScopedConn.setSessionMaxRows(maybeSelect ? this.maxRows : -1);
                            this.statementBegins();
                            rs = ((NativeSession)locallyScopedConn.getSession()).execSQL(this, sql, this.maxRows, null, this.createStreamingResultSet(), this.getResultSetFactory(), this.getCurrentCatalog(), cachedMetaData, false);
                            if (timeoutTask != null) {
                                this.stopQueryTimer(timeoutTask, true, true);
                                timeoutTask = null;
                            }
                        }
                        catch (CJTimeoutException ex2) {}
                        catch (OperationCancelledException e) {
                            throw SQLExceptionsMapping.translateException(e, this.exceptionInterceptor);
                        }
                        finally {
                            this.stopQueryTimer(timeoutTask, false, false);
                            if (oldCatalog != null) {
                                locallyScopedConn.setCatalog(oldCatalog);
                            }
                        }
                    }
                    if (rs != null) {
                        this.lastInsertId = rs.getUpdateID();
                        (this.results = rs).setFirstCharOfQuery(firstNonWsChar);
                        if (rs.hasRows()) {
                            if (cachedMetaData != null) {
                                locallyScopedConn.initializeResultsMetadataFromCache(sql, cachedMetaData, this.results);
                            }
                            else if (this.session.getPropertySet().getBooleanProperty(PropertyKey.cacheResultSetMetadata).getValue()) {
                                locallyScopedConn.initializeResultsMetadataFromCache(sql, null, this.results);
                            }
                        }
                    }
                    return rs != null && rs.hasRows();
                }
                finally {
                    this.query.getStatementExecuting().set(false);
                }
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void statementBegins() {
        this.query.statementBegins();
    }
    
    @Override
    public void resetCancelledState() {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                this.query.resetCancelledState();
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean execute(final String sql, final int returnGeneratedKeys) throws SQLException {
        try {
            return this.executeInternal(sql, returnGeneratedKeys == 1);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean execute(final String sql, final int[] generatedKeyIndices) throws SQLException {
        try {
            return this.executeInternal(sql, generatedKeyIndices != null && generatedKeyIndices.length > 0);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean execute(final String sql, final String[] generatedKeyNames) throws SQLException {
        try {
            return this.executeInternal(sql, generatedKeyNames != null && generatedKeyNames.length > 0);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public int[] executeBatch() throws SQLException {
        try {
            return Util.truncateAndConvertToInt(this.executeBatchInternal());
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    protected long[] executeBatchInternal() throws SQLException {
        final JdbcConnection locallyScopedConn = this.checkClosed();
        synchronized (locallyScopedConn.getConnectionMutex()) {
            if (locallyScopedConn.isReadOnly()) {
                throw SQLError.createSQLException(Messages.getString("Statement.34") + Messages.getString("Statement.35"), "S1009", this.getExceptionInterceptor());
            }
            this.implicitlyCloseAllOpenResults();
            final List<Object> batchedArgs = this.query.getBatchedArgs();
            if (batchedArgs == null || batchedArgs.size() == 0) {
                return new long[0];
            }
            final int individualStatementTimeout = this.getTimeoutInMillis();
            this.setTimeoutInMillis(0);
            CancelQueryTask timeoutTask = null;
            try {
                this.resetCancelledState();
                this.statementBegins();
                try {
                    this.retrieveGeneratedKeys = true;
                    long[] updateCounts = null;
                    if (batchedArgs != null) {
                        final int nbrCommands = batchedArgs.size();
                        this.batchedGeneratedKeys = new ArrayList<Row>(batchedArgs.size());
                        final boolean multiQueriesEnabled = locallyScopedConn.getPropertySet().getBooleanProperty(PropertyKey.allowMultiQueries).getValue();
                        if (multiQueriesEnabled || (locallyScopedConn.getPropertySet().getBooleanProperty(PropertyKey.rewriteBatchedStatements).getValue() && nbrCommands > 4)) {
                            return this.executeBatchUsingMultiQueries(multiQueriesEnabled, nbrCommands, individualStatementTimeout);
                        }
                        timeoutTask = this.startQueryTimer(this, individualStatementTimeout);
                        updateCounts = new long[nbrCommands];
                        for (int i = 0; i < nbrCommands; ++i) {
                            updateCounts[i] = -3L;
                        }
                        SQLException sqlEx = null;
                        int commandIndex;
                        String sql;
                        long[] newUpdateCounts;
                        int j;
                        for (commandIndex = 0, commandIndex = 0; commandIndex < nbrCommands; ++commandIndex) {
                            try {
                                sql = batchedArgs.get(commandIndex);
                                updateCounts[commandIndex] = this.executeUpdateInternal(sql, true, true);
                                if (timeoutTask != null) {
                                    this.checkCancelTimeout();
                                }
                                this.getBatchedGeneratedKeys((this.results.getFirstCharOfQuery() == 'I' && this.containsOnDuplicateKeyInString(sql)) ? 1 : 0);
                            }
                            catch (SQLException ex) {
                                updateCounts[commandIndex] = -3L;
                                if (!this.continueBatchOnError || ex instanceof MySQLTimeoutException || ex instanceof MySQLStatementCancelledException || this.hasDeadlockOrTimeoutRolledBackTx(ex)) {
                                    newUpdateCounts = new long[commandIndex];
                                    if (this.hasDeadlockOrTimeoutRolledBackTx(ex)) {
                                        for (j = 0; j < newUpdateCounts.length; ++j) {
                                            newUpdateCounts[j] = -3L;
                                        }
                                    }
                                    else {
                                        System.arraycopy(updateCounts, 0, newUpdateCounts, 0, commandIndex);
                                    }
                                    sqlEx = ex;
                                    break;
                                }
                                sqlEx = ex;
                            }
                        }
                        if (sqlEx != null) {
                            throw SQLError.createBatchUpdateException(sqlEx, updateCounts, this.getExceptionInterceptor());
                        }
                    }
                    if (timeoutTask != null) {
                        this.stopQueryTimer(timeoutTask, true, true);
                        timeoutTask = null;
                    }
                    return (updateCounts != null) ? updateCounts : new long[0];
                }
                finally {
                    this.query.getStatementExecuting().set(false);
                }
            }
            finally {
                this.stopQueryTimer(timeoutTask, false, false);
                this.resetCancelledState();
                this.setTimeoutInMillis(individualStatementTimeout);
                this.clearBatch();
            }
        }
    }
    
    protected final boolean hasDeadlockOrTimeoutRolledBackTx(final SQLException ex) {
        final int vendorCode = ex.getErrorCode();
        switch (vendorCode) {
            case 1206:
            case 1213: {
                return true;
            }
            case 1205: {
                return false;
            }
            default: {
                return false;
            }
        }
    }
    
    private long[] executeBatchUsingMultiQueries(final boolean multiQueriesEnabled, final int nbrCommands, final int individualStatementTimeout) throws SQLException {
        try {
            final JdbcConnection locallyScopedConn = this.checkClosed();
            synchronized (locallyScopedConn.getConnectionMutex()) {
                if (!multiQueriesEnabled) {
                    this.session.enableMultiQueries();
                }
                Statement batchStmt = null;
                CancelQueryTask timeoutTask = null;
                try {
                    final long[] updateCounts = new long[nbrCommands];
                    for (int i = 0; i < nbrCommands; ++i) {
                        updateCounts[i] = -3L;
                    }
                    int commandIndex = 0;
                    StringBuilder queryBuf = new StringBuilder();
                    batchStmt = locallyScopedConn.createStatement();
                    timeoutTask = this.startQueryTimer((Query)batchStmt, individualStatementTimeout);
                    int counter = 0;
                    final String connectionEncoding = locallyScopedConn.getPropertySet().getStringProperty(PropertyKey.characterEncoding).getValue();
                    final int numberOfBytesPerChar = StringUtils.startsWithIgnoreCase(connectionEncoding, "utf") ? 3 : (CharsetMapping.isMultibyteCharset(connectionEncoding) ? 2 : 1);
                    int escapeAdjust = 1;
                    batchStmt.setEscapeProcessing(this.doEscapeProcessing);
                    if (this.doEscapeProcessing) {
                        escapeAdjust = 2;
                    }
                    SQLException sqlEx = null;
                    int argumentSetsInBatchSoFar = 0;
                    for (commandIndex = 0; commandIndex < nbrCommands; ++commandIndex) {
                        final String nextQuery = this.query.getBatchedArgs().get(commandIndex);
                        if (((queryBuf.length() + nextQuery.length()) * numberOfBytesPerChar + 1 + 4) * escapeAdjust + 32 > this.maxAllowedPacket.getValue()) {
                            try {
                                batchStmt.execute(queryBuf.toString(), 1);
                            }
                            catch (SQLException ex) {
                                sqlEx = this.handleExceptionForBatch(commandIndex, argumentSetsInBatchSoFar, updateCounts, ex);
                            }
                            counter = this.processMultiCountsAndKeys((StatementImpl)batchStmt, counter, updateCounts);
                            queryBuf = new StringBuilder();
                            argumentSetsInBatchSoFar = 0;
                        }
                        queryBuf.append(nextQuery);
                        queryBuf.append(";");
                        ++argumentSetsInBatchSoFar;
                    }
                    if (queryBuf.length() > 0) {
                        try {
                            batchStmt.execute(queryBuf.toString(), 1);
                        }
                        catch (SQLException ex2) {
                            sqlEx = this.handleExceptionForBatch(commandIndex - 1, argumentSetsInBatchSoFar, updateCounts, ex2);
                        }
                        counter = this.processMultiCountsAndKeys((StatementImpl)batchStmt, counter, updateCounts);
                    }
                    if (timeoutTask != null) {
                        this.stopQueryTimer(timeoutTask, true, true);
                        timeoutTask = null;
                    }
                    if (sqlEx != null) {
                        throw SQLError.createBatchUpdateException(sqlEx, updateCounts, this.getExceptionInterceptor());
                    }
                    return (updateCounts != null) ? updateCounts : new long[0];
                }
                finally {
                    this.stopQueryTimer(timeoutTask, false, false);
                    this.resetCancelledState();
                    try {
                        if (batchStmt != null) {
                            batchStmt.close();
                        }
                    }
                    finally {
                        if (!multiQueriesEnabled) {
                            this.session.disableMultiQueries();
                        }
                    }
                }
            }
        }
        catch (CJException ex3) {
            throw SQLExceptionsMapping.translateException(ex3, this.getExceptionInterceptor());
        }
    }
    
    protected int processMultiCountsAndKeys(final StatementImpl batchedStatement, int updateCountCounter, final long[] updateCounts) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                updateCounts[updateCountCounter++] = batchedStatement.getLargeUpdateCount();
                final boolean doGenKeys = this.batchedGeneratedKeys != null;
                byte[][] row = null;
                if (doGenKeys) {
                    final long generatedKey = batchedStatement.getLastInsertID();
                    row = new byte[][] { StringUtils.getBytes(Long.toString(generatedKey)) };
                    this.batchedGeneratedKeys.add(new ByteArrayRow(row, this.getExceptionInterceptor()));
                }
                while (batchedStatement.getMoreResults() || batchedStatement.getLargeUpdateCount() != -1L) {
                    updateCounts[updateCountCounter++] = batchedStatement.getLargeUpdateCount();
                    if (doGenKeys) {
                        final long generatedKey = batchedStatement.getLastInsertID();
                        row = new byte[][] { StringUtils.getBytes(Long.toString(generatedKey)) };
                        this.batchedGeneratedKeys.add(new ByteArrayRow(row, this.getExceptionInterceptor()));
                    }
                }
                return updateCountCounter;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    protected SQLException handleExceptionForBatch(final int endOfBatchIndex, final int numValuesPerBatch, final long[] updateCounts, final SQLException ex) throws BatchUpdateException, SQLException {
        for (int j = endOfBatchIndex; j > endOfBatchIndex - numValuesPerBatch; --j) {
            updateCounts[j] = -3L;
        }
        if (this.continueBatchOnError && !(ex instanceof MySQLTimeoutException) && !(ex instanceof MySQLStatementCancelledException) && !this.hasDeadlockOrTimeoutRolledBackTx(ex)) {
            return ex;
        }
        final long[] newUpdateCounts = new long[endOfBatchIndex];
        System.arraycopy(updateCounts, 0, newUpdateCounts, 0, endOfBatchIndex);
        throw SQLError.createBatchUpdateException(ex, newUpdateCounts, this.getExceptionInterceptor());
    }
    
    @Override
    public ResultSet executeQuery(String sql) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                final JdbcConnection locallyScopedConn = this.connection;
                this.retrieveGeneratedKeys = false;
                this.checkNullOrEmptyQuery(sql);
                this.resetCancelledState();
                this.implicitlyCloseAllOpenResults();
                if (sql.charAt(0) == '/' && sql.startsWith("/* ping */")) {
                    this.doPingInstead();
                    return this.results;
                }
                this.setupStreamingTimeout(locallyScopedConn);
                if (this.doEscapeProcessing) {
                    final Object escapedSqlResult = EscapeProcessor.escapeSQL(sql, this.session.getServerSession().getDefaultTimeZone(), this.session.getServerSession().getCapabilities().serverSupportsFracSecs(), this.session.getServerSession().isServerTruncatesFracSecs(), this.getExceptionInterceptor());
                    sql = (String)((escapedSqlResult instanceof String) ? escapedSqlResult : ((EscapeProcessorResult)escapedSqlResult).escapedSql);
                }
                final char firstStatementChar = StringUtils.firstAlphaCharUc(sql, findStartOfStatement(sql));
                this.checkForDml(sql, firstStatementChar);
                CachedResultSetMetaData cachedMetaData = null;
                if (this.useServerFetch()) {
                    return this.results = this.createResultSetUsingServerFetch(sql);
                }
                CancelQueryTask timeoutTask = null;
                String oldCatalog = null;
                try {
                    timeoutTask = this.startQueryTimer(this, this.getTimeoutInMillis());
                    if (!locallyScopedConn.getCatalog().equals(this.getCurrentCatalog())) {
                        oldCatalog = locallyScopedConn.getCatalog();
                        locallyScopedConn.setCatalog(this.getCurrentCatalog());
                    }
                    if (locallyScopedConn.getPropertySet().getBooleanProperty(PropertyKey.cacheResultSetMetadata).getValue()) {
                        cachedMetaData = locallyScopedConn.getCachedMetaData(sql);
                    }
                    locallyScopedConn.setSessionMaxRows(this.maxRows);
                    this.statementBegins();
                    this.results = ((NativeSession)locallyScopedConn.getSession()).execSQL(this, sql, this.maxRows, null, this.createStreamingResultSet(), this.getResultSetFactory(), this.getCurrentCatalog(), cachedMetaData, false);
                    if (timeoutTask != null) {
                        this.stopQueryTimer(timeoutTask, true, true);
                        timeoutTask = null;
                    }
                }
                catch (CJTimeoutException ex2) {}
                catch (OperationCancelledException e) {
                    throw SQLExceptionsMapping.translateException(e, this.exceptionInterceptor);
                }
                finally {
                    this.query.getStatementExecuting().set(false);
                    this.stopQueryTimer(timeoutTask, false, false);
                    if (oldCatalog != null) {
                        locallyScopedConn.setCatalog(oldCatalog);
                    }
                }
                this.lastInsertId = this.results.getUpdateID();
                if (cachedMetaData != null) {
                    locallyScopedConn.initializeResultsMetadataFromCache(sql, cachedMetaData, this.results);
                }
                else if (this.connection.getPropertySet().getBooleanProperty(PropertyKey.cacheResultSetMetadata).getValue()) {
                    locallyScopedConn.initializeResultsMetadataFromCache(sql, null, this.results);
                }
                return this.results;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    protected void doPingInstead() throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                Label_0060: {
                    if (this.pingTarget != null) {
                        try {
                            this.pingTarget.doPing();
                            break Label_0060;
                        }
                        catch (SQLException e) {
                            throw e;
                        }
                        catch (Exception e2) {
                            throw SQLError.createSQLException(e2.getMessage(), "08S01", e2, this.getExceptionInterceptor());
                        }
                    }
                    this.connection.ping();
                }
                final ResultSetInternalMethods fakeSelectOneResultSet = this.generatePingResultSet();
                this.results = fakeSelectOneResultSet;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    protected ResultSetInternalMethods generatePingResultSet() throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                final String encoding = this.session.getServerSession().getCharacterSetMetadata();
                final int collationIndex = this.session.getServerSession().getMetadataCollationIndex();
                final Field[] fields = { new Field(null, "1", collationIndex, encoding, MysqlType.BIGINT, 1) };
                final ArrayList<Row> rows = new ArrayList<Row>();
                final byte[] colVal = { 49 };
                rows.add(new ByteArrayRow(new byte[][] { colVal }, this.getExceptionInterceptor()));
                return this.resultSetFactory.createFromResultsetRows(1007, 1004, new ResultsetRowsStatic(rows, new DefaultColumnDefinition(fields)));
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    public void executeSimpleNonQuery(final JdbcConnection c, final String nonQuery) throws SQLException {
        try {
            synchronized (c.getConnectionMutex()) {
                ((NativeSession)c.getSession()).execSQL(this, nonQuery, -1, null, false, this.getResultSetFactory(), this.getCurrentCatalog(), null, false).close();
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public int executeUpdate(final String sql) throws SQLException {
        try {
            return Util.truncateAndConvertToInt(this.executeLargeUpdate(sql));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    protected long executeUpdateInternal(String sql, final boolean isBatch, final boolean returnGeneratedKeys) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                final JdbcConnection locallyScopedConn = this.connection;
                this.checkNullOrEmptyQuery(sql);
                this.resetCancelledState();
                final char firstStatementChar = StringUtils.firstAlphaCharUc(sql, findStartOfStatement(sql));
                this.retrieveGeneratedKeys = returnGeneratedKeys;
                this.lastQueryIsOnDupKeyUpdate = (returnGeneratedKeys && firstStatementChar == 'I' && this.containsOnDuplicateKeyInString(sql));
                ResultSetInternalMethods rs = null;
                if (this.doEscapeProcessing) {
                    final Object escapedSqlResult = EscapeProcessor.escapeSQL(sql, this.session.getServerSession().getDefaultTimeZone(), this.session.getServerSession().getCapabilities().serverSupportsFracSecs(), this.session.getServerSession().isServerTruncatesFracSecs(), this.getExceptionInterceptor());
                    sql = (String)((escapedSqlResult instanceof String) ? escapedSqlResult : ((EscapeProcessorResult)escapedSqlResult).escapedSql);
                }
                if (locallyScopedConn.isReadOnly(false)) {
                    throw SQLError.createSQLException(Messages.getString("Statement.42") + Messages.getString("Statement.43"), "S1009", this.getExceptionInterceptor());
                }
                if (StringUtils.startsWithIgnoreCaseAndWs(sql, "select")) {
                    throw SQLError.createSQLException(Messages.getString("Statement.46"), "01S03", this.getExceptionInterceptor());
                }
                this.implicitlyCloseAllOpenResults();
                CancelQueryTask timeoutTask = null;
                String oldCatalog = null;
                try {
                    timeoutTask = this.startQueryTimer(this, this.getTimeoutInMillis());
                    if (!locallyScopedConn.getCatalog().equals(this.getCurrentCatalog())) {
                        oldCatalog = locallyScopedConn.getCatalog();
                        locallyScopedConn.setCatalog(this.getCurrentCatalog());
                    }
                    locallyScopedConn.setSessionMaxRows(-1);
                    this.statementBegins();
                    rs = ((NativeSession)locallyScopedConn.getSession()).execSQL(this, sql, -1, null, false, this.getResultSetFactory(), this.getCurrentCatalog(), null, isBatch);
                    if (timeoutTask != null) {
                        this.stopQueryTimer(timeoutTask, true, true);
                        timeoutTask = null;
                    }
                }
                catch (CJTimeoutException ex2) {}
                catch (OperationCancelledException e) {
                    throw SQLExceptionsMapping.translateException(e, this.exceptionInterceptor);
                }
                finally {
                    this.stopQueryTimer(timeoutTask, false, false);
                    if (oldCatalog != null) {
                        locallyScopedConn.setCatalog(oldCatalog);
                    }
                    if (!isBatch) {
                        this.query.getStatementExecuting().set(false);
                    }
                }
                (this.results = rs).setFirstCharOfQuery(firstStatementChar);
                this.updateCount = rs.getUpdateCount();
                this.lastInsertId = rs.getUpdateID();
                return this.updateCount;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public int executeUpdate(final String sql, final int autoGeneratedKeys) throws SQLException {
        try {
            return Util.truncateAndConvertToInt(this.executeLargeUpdate(sql, autoGeneratedKeys));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public int executeUpdate(final String sql, final int[] columnIndexes) throws SQLException {
        try {
            return Util.truncateAndConvertToInt(this.executeLargeUpdate(sql, columnIndexes));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public int executeUpdate(final String sql, final String[] columnNames) throws SQLException {
        try {
            return Util.truncateAndConvertToInt(this.executeLargeUpdate(sql, columnNames));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public Connection getConnection() throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                return this.connection;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public int getFetchDirection() throws SQLException {
        try {
            return 1000;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public int getFetchSize() throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                return this.query.getResultFetchSize();
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public ResultSet getGeneratedKeys() throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                if (!this.retrieveGeneratedKeys) {
                    throw SQLError.createSQLException(Messages.getString("Statement.GeneratedKeysNotRequested"), "S1009", this.getExceptionInterceptor());
                }
                if (this.batchedGeneratedKeys != null) {
                    final String encoding = this.session.getServerSession().getCharacterSetMetadata();
                    final int collationIndex = this.session.getServerSession().getMetadataCollationIndex();
                    final Field[] fields = { new Field("", "GENERATED_KEY", collationIndex, encoding, MysqlType.BIGINT_UNSIGNED, 20) };
                    return this.generatedKeysResults = this.resultSetFactory.createFromResultsetRows(1007, 1004, new ResultsetRowsStatic(this.batchedGeneratedKeys, new DefaultColumnDefinition(fields)));
                }
                if (this.lastQueryIsOnDupKeyUpdate) {
                    return this.generatedKeysResults = this.getGeneratedKeysInternal(1L);
                }
                return this.generatedKeysResults = this.getGeneratedKeysInternal();
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    protected ResultSetInternalMethods getGeneratedKeysInternal() throws SQLException {
        final long numKeys = this.getLargeUpdateCount();
        return this.getGeneratedKeysInternal(numKeys);
    }
    
    protected ResultSetInternalMethods getGeneratedKeysInternal(long numKeys) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                final String encoding = this.session.getServerSession().getCharacterSetMetadata();
                final int collationIndex = this.session.getServerSession().getMetadataCollationIndex();
                final Field[] fields = { new Field("", "GENERATED_KEY", collationIndex, encoding, MysqlType.BIGINT_UNSIGNED, 20) };
                final ArrayList<Row> rowSet = new ArrayList<Row>();
                long beginAt = this.getLastInsertID();
                if (this.results != null) {
                    final String serverInfo = this.results.getServerInfo();
                    if (numKeys > 0L && this.results.getFirstCharOfQuery() == 'R' && serverInfo != null && serverInfo.length() > 0) {
                        numKeys = this.getRecordCountFromInfo(serverInfo);
                    }
                    if (beginAt != 0L && numKeys > 0L) {
                        for (int i = 0; i < numKeys; ++i) {
                            final byte[][] row = { null };
                            if (beginAt > 0L) {
                                row[0] = StringUtils.getBytes(Long.toString(beginAt));
                            }
                            else {
                                final byte[] asBytes = { (byte)(beginAt >>> 56), (byte)(beginAt >>> 48), (byte)(beginAt >>> 40), (byte)(beginAt >>> 32), (byte)(beginAt >>> 24), (byte)(beginAt >>> 16), (byte)(beginAt >>> 8), (byte)(beginAt & 0xFFL) };
                                final BigInteger val = new BigInteger(1, asBytes);
                                row[0] = val.toString().getBytes();
                            }
                            rowSet.add(new ByteArrayRow(row, this.getExceptionInterceptor()));
                            beginAt += this.connection.getAutoIncrementIncrement();
                        }
                    }
                }
                final ResultSetImpl gkRs = this.resultSetFactory.createFromResultsetRows(1007, 1004, new ResultsetRowsStatic(rowSet, new DefaultColumnDefinition(fields)));
                return gkRs;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    public long getLastInsertID() {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                return this.lastInsertId;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    public long getLongUpdateCount() {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                if (this.results == null) {
                    return -1L;
                }
                if (this.results.hasRows()) {
                    return -1L;
                }
                return this.updateCount;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public int getMaxFieldSize() throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                return this.maxFieldSize;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public int getMaxRows() throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                if (this.maxRows <= 0) {
                    return 0;
                }
                return this.maxRows;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean getMoreResults() throws SQLException {
        try {
            return this.getMoreResults(1);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean getMoreResults(final int current) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                if (this.results == null) {
                    return false;
                }
                final boolean streamingMode = this.createStreamingResultSet();
                if (streamingMode && this.results.hasRows()) {
                    while (this.results.next()) {}
                }
                final ResultSetInternalMethods nextResultSet = (ResultSetInternalMethods)this.results.getNextResultset();
                switch (current) {
                    case 1: {
                        if (this.results != null) {
                            if (!streamingMode && !this.dontTrackOpenResources.getValue()) {
                                this.results.realClose(false);
                            }
                            this.results.clearNextResultset();
                            break;
                        }
                        break;
                    }
                    case 3: {
                        if (this.results != null) {
                            if (!streamingMode && !this.dontTrackOpenResources.getValue()) {
                                this.results.realClose(false);
                            }
                            this.results.clearNextResultset();
                        }
                        this.closeAllOpenResults();
                        break;
                    }
                    case 2: {
                        if (!this.dontTrackOpenResources.getValue()) {
                            this.openResults.add(this.results);
                        }
                        this.results.clearNextResultset();
                        break;
                    }
                    default: {
                        throw SQLError.createSQLException(Messages.getString("Statement.19"), "S1009", this.getExceptionInterceptor());
                    }
                }
                this.results = nextResultSet;
                if (this.results == null) {
                    this.updateCount = -1L;
                    this.lastInsertId = -1L;
                }
                else if (this.results.hasRows()) {
                    this.updateCount = -1L;
                    this.lastInsertId = -1L;
                }
                else {
                    this.updateCount = this.results.getUpdateCount();
                    this.lastInsertId = this.results.getUpdateID();
                }
                final boolean moreResults = this.results != null && this.results.hasRows();
                if (!moreResults) {
                    this.checkAndPerformCloseOnCompletionAction();
                }
                return moreResults;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public int getQueryTimeout() throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                return this.getTimeoutInMillis() / 1000;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    private long getRecordCountFromInfo(final String serverInfo) {
        final StringBuilder recordsBuf = new StringBuilder();
        long recordsCount = 0L;
        long duplicatesCount = 0L;
        char c = '\0';
        int length;
        int i;
        for (length = serverInfo.length(), i = 0; i < length; ++i) {
            c = serverInfo.charAt(i);
            if (Character.isDigit(c)) {
                break;
            }
        }
        recordsBuf.append(c);
        ++i;
        while (i < length) {
            c = serverInfo.charAt(i);
            if (!Character.isDigit(c)) {
                break;
            }
            recordsBuf.append(c);
            ++i;
        }
        recordsCount = Long.parseLong(recordsBuf.toString());
        final StringBuilder duplicatesBuf = new StringBuilder();
        while (i < length) {
            c = serverInfo.charAt(i);
            if (Character.isDigit(c)) {
                break;
            }
            ++i;
        }
        duplicatesBuf.append(c);
        ++i;
        while (i < length) {
            c = serverInfo.charAt(i);
            if (!Character.isDigit(c)) {
                break;
            }
            duplicatesBuf.append(c);
            ++i;
        }
        duplicatesCount = Long.parseLong(duplicatesBuf.toString());
        return recordsCount - duplicatesCount;
    }
    
    @Override
    public ResultSet getResultSet() throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                return (this.results != null && this.results.hasRows()) ? this.results : null;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public int getResultSetConcurrency() throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                return this.resultSetConcurrency;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public int getResultSetHoldability() throws SQLException {
        try {
            return 1;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    protected ResultSetInternalMethods getResultSetInternal() {
        try {
            try {
                synchronized (this.checkClosed().getConnectionMutex()) {
                    return this.results;
                }
            }
            catch (StatementIsClosedException e) {
                return this.results;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public int getResultSetType() throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                return this.query.getResultType().getIntValue();
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public int getUpdateCount() throws SQLException {
        try {
            return Util.truncateAndConvertToInt(this.getLargeUpdateCount());
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public SQLWarning getWarnings() throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                if (this.isClearWarningsCalled()) {
                    return null;
                }
                final SQLWarning pendingWarningsFromServer = this.session.getProtocol().convertShowWarningsToSQLWarnings(0, false);
                if (this.warningChain != null) {
                    this.warningChain.setNextWarning(pendingWarningsFromServer);
                }
                else {
                    this.warningChain = pendingWarningsFromServer;
                }
                return this.warningChain;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    protected void realClose(final boolean calledExplicitly, boolean closeOpenResults) throws SQLException {
        final JdbcConnection locallyScopedConn = this.connection;
        if (locallyScopedConn == null || this.isClosed) {
            return;
        }
        if (!this.dontTrackOpenResources.getValue()) {
            locallyScopedConn.unregisterStatement(this);
        }
        if (this.useUsageAdvisor && !calledExplicitly) {
            final String message = Messages.getString("Statement.63") + Messages.getString("Statement.64");
            this.query.getEventSink().consumeEvent(new ProfilerEventImpl((byte)0, "", this.getCurrentCatalog(), this.session.getThreadId(), this.getId(), -1, System.currentTimeMillis(), 0L, Constants.MILLIS_I18N, null, this.pointOfOrigin, message));
        }
        if (closeOpenResults) {
            closeOpenResults = (!this.holdResultsOpenOverClose && !this.dontTrackOpenResources.getValue());
        }
        if (closeOpenResults) {
            if (this.results != null) {
                try {
                    this.results.close();
                }
                catch (Exception ex) {}
            }
            if (this.generatedKeysResults != null) {
                try {
                    this.generatedKeysResults.close();
                }
                catch (Exception ex2) {}
            }
            this.closeAllOpenResults();
        }
        this.isClosed = true;
        this.closeQuery();
        this.results = null;
        this.generatedKeysResults = null;
        this.connection = null;
        this.session = null;
        this.warningChain = null;
        this.openResults = null;
        this.batchedGeneratedKeys = null;
        this.pingTarget = null;
        this.resultSetFactory = null;
    }
    
    @Override
    public void setCursorName(final String name) throws SQLException {
    }
    
    @Override
    public void setEscapeProcessing(final boolean enable) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                this.doEscapeProcessing = enable;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setFetchDirection(final int direction) throws SQLException {
        try {
            switch (direction) {
                case 1000:
                case 1001:
                case 1002: {}
                default: {
                    throw SQLError.createSQLException(Messages.getString("Statement.5"), "S1009", this.getExceptionInterceptor());
                }
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setFetchSize(final int rows) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                if ((rows < 0 && rows != Integer.MIN_VALUE) || (this.maxRows > 0 && rows > this.getMaxRows())) {
                    throw SQLError.createSQLException(Messages.getString("Statement.7"), "S1009", this.getExceptionInterceptor());
                }
                this.query.setResultFetchSize(rows);
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setHoldResultsOpenOverClose(final boolean holdResultsOpenOverClose) {
        try {
            try {
                synchronized (this.checkClosed().getConnectionMutex()) {
                    this.holdResultsOpenOverClose = holdResultsOpenOverClose;
                }
            }
            catch (StatementIsClosedException ex2) {}
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setMaxFieldSize(final int max) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                if (max < 0) {
                    throw SQLError.createSQLException(Messages.getString("Statement.11"), "S1009", this.getExceptionInterceptor());
                }
                final int maxBuf = this.maxAllowedPacket.getValue();
                if (max > maxBuf) {
                    throw SQLError.createSQLException(Messages.getString("Statement.13", new Object[] { maxBuf }), "S1009", this.getExceptionInterceptor());
                }
                this.maxFieldSize = max;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setMaxRows(final int max) throws SQLException {
        try {
            this.setLargeMaxRows(max);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setQueryTimeout(final int seconds) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                if (seconds < 0) {
                    throw SQLError.createSQLException(Messages.getString("Statement.21"), "S1009", this.getExceptionInterceptor());
                }
                this.setTimeoutInMillis(seconds * 1000);
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    void setResultSetConcurrency(final int concurrencyFlag) throws SQLException {
        try {
            try {
                synchronized (this.checkClosed().getConnectionMutex()) {
                    this.resultSetConcurrency = concurrencyFlag;
                    this.resultSetFactory = new ResultSetFactory(this.connection, this);
                }
            }
            catch (StatementIsClosedException ex2) {}
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    void setResultSetType(final Resultset.Type typeFlag) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                this.query.setResultType(typeFlag);
                this.resultSetFactory = new ResultSetFactory(this.connection, this);
            }
        }
        catch (StatementIsClosedException ex) {}
    }
    
    void setResultSetType(final int typeFlag) throws SQLException {
        Resultset.Type.fromValue(typeFlag, Resultset.Type.FORWARD_ONLY);
    }
    
    protected void getBatchedGeneratedKeys(final Statement batchedStatement) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                if (this.retrieveGeneratedKeys) {
                    ResultSet rs = null;
                    try {
                        rs = batchedStatement.getGeneratedKeys();
                        while (rs.next()) {
                            this.batchedGeneratedKeys.add(new ByteArrayRow(new byte[][] { rs.getBytes(1) }, this.getExceptionInterceptor()));
                        }
                    }
                    finally {
                        if (rs != null) {
                            rs.close();
                        }
                    }
                }
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    protected void getBatchedGeneratedKeys(final int maxKeys) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                if (this.retrieveGeneratedKeys) {
                    ResultSet rs = null;
                    try {
                        rs = ((maxKeys == 0) ? this.getGeneratedKeysInternal() : this.getGeneratedKeysInternal(maxKeys));
                        while (rs.next()) {
                            this.batchedGeneratedKeys.add(new ByteArrayRow(new byte[][] { rs.getBytes(1) }, this.getExceptionInterceptor()));
                        }
                    }
                    finally {
                        this.isImplicitlyClosingResults = true;
                        try {
                            if (rs != null) {
                                rs.close();
                            }
                        }
                        finally {
                            this.isImplicitlyClosingResults = false;
                        }
                    }
                }
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    private boolean useServerFetch() throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                return this.session.getPropertySet().getBooleanProperty(PropertyKey.useCursorFetch).getValue() && this.query.getResultFetchSize() > 0 && this.query.getResultType() == Resultset.Type.FORWARD_ONLY;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean isClosed() throws SQLException {
        try {
            final JdbcConnection locallyScopedConn = this.connection;
            if (locallyScopedConn == null) {
                return true;
            }
            synchronized (locallyScopedConn.getConnectionMutex()) {
                return this.isClosed;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean isPoolable() throws SQLException {
        try {
            return this.isPoolable;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setPoolable(final boolean poolable) throws SQLException {
        try {
            this.isPoolable = poolable;
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
    
    protected static int findStartOfStatement(final String sql) {
        int statementStartPos = 0;
        if (StringUtils.startsWithIgnoreCaseAndWs(sql, "/*")) {
            statementStartPos = sql.indexOf("*/");
            if (statementStartPos == -1) {
                statementStartPos = 0;
            }
            else {
                statementStartPos += 2;
            }
        }
        else if (StringUtils.startsWithIgnoreCaseAndWs(sql, "--") || StringUtils.startsWithIgnoreCaseAndWs(sql, "#")) {
            statementStartPos = sql.indexOf(10);
            if (statementStartPos == -1) {
                statementStartPos = sql.indexOf(13);
                if (statementStartPos == -1) {
                    statementStartPos = 0;
                }
            }
        }
        return statementStartPos;
    }
    
    @Override
    public InputStream getLocalInfileInputStream() {
        return this.session.getLocalInfileInputStream();
    }
    
    @Override
    public void setLocalInfileInputStream(final InputStream stream) {
        this.session.setLocalInfileInputStream(stream);
    }
    
    @Override
    public void setPingTarget(final PingTarget pingTarget) {
        this.pingTarget = pingTarget;
    }
    
    @Override
    public ExceptionInterceptor getExceptionInterceptor() {
        return this.exceptionInterceptor;
    }
    
    protected boolean containsOnDuplicateKeyInString(final String sql) {
        return ParseInfo.getOnDuplicateKeyLocation(sql, this.dontCheckOnDuplicateKeyUpdateInSQL, this.rewriteBatchedStatements.getValue(), this.session.getServerSession().isNoBackslashEscapesSet()) != -1;
    }
    
    @Override
    public void closeOnCompletion() throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                this.closeOnCompletion = true;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean isCloseOnCompletion() throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                return this.closeOnCompletion;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public long[] executeLargeBatch() throws SQLException {
        try {
            return this.executeBatchInternal();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public long executeLargeUpdate(final String sql) throws SQLException {
        try {
            return this.executeUpdateInternal(sql, false, false);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public long executeLargeUpdate(final String sql, final int autoGeneratedKeys) throws SQLException {
        try {
            return this.executeUpdateInternal(sql, false, autoGeneratedKeys == 1);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public long executeLargeUpdate(final String sql, final int[] columnIndexes) throws SQLException {
        try {
            return this.executeUpdateInternal(sql, false, columnIndexes != null && columnIndexes.length > 0);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public long executeLargeUpdate(final String sql, final String[] columnNames) throws SQLException {
        try {
            return this.executeUpdateInternal(sql, false, columnNames != null && columnNames.length > 0);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public long getLargeMaxRows() throws SQLException {
        try {
            return this.getMaxRows();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public long getLargeUpdateCount() throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                if (this.results == null) {
                    return -1L;
                }
                if (this.results.hasRows()) {
                    return -1L;
                }
                return this.results.getUpdateCount();
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setLargeMaxRows(long max) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                if (max > 50000000L || max < 0L) {
                    throw SQLError.createSQLException(Messages.getString("Statement.15") + max + " > " + 50000000 + ".", "S1009", this.getExceptionInterceptor());
                }
                if (max == 0L) {
                    max = -1L;
                }
                this.maxRows = (int)max;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public String getCurrentCatalog() {
        return this.query.getCurrentCatalog();
    }
    
    public long getServerStatementId() {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, Messages.getString("Statement.65"));
    }
    
    @Override
    public <T extends Resultset, M extends Message> ProtocolEntityFactory<T, M> getResultSetFactory() {
        return (ProtocolEntityFactory<T, M>)this.resultSetFactory;
    }
    
    @Override
    public int getId() {
        return this.query.getId();
    }
    
    @Override
    public void setCancelStatus(final Query.CancelStatus cs) {
        this.query.setCancelStatus(cs);
    }
    
    @Override
    public void checkCancelTimeout() {
        try {
            this.query.checkCancelTimeout();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public Session getSession() {
        return this.session;
    }
    
    @Override
    public Object getCancelTimeoutMutex() {
        return this.query.getCancelTimeoutMutex();
    }
    
    @Override
    public void closeQuery() {
        if (this.query != null) {
            this.query.closeQuery();
        }
    }
    
    @Override
    public int getResultFetchSize() {
        return this.query.getResultFetchSize();
    }
    
    @Override
    public void setResultFetchSize(final int fetchSize) {
        this.query.setResultFetchSize(fetchSize);
    }
    
    @Override
    public Resultset.Type getResultType() {
        return this.query.getResultType();
    }
    
    @Override
    public void setResultType(final Resultset.Type resultSetType) {
        this.query.setResultType(resultSetType);
    }
    
    @Override
    public int getTimeoutInMillis() {
        return this.query.getTimeoutInMillis();
    }
    
    @Override
    public void setTimeoutInMillis(final int timeoutInMillis) {
        this.query.setTimeoutInMillis(timeoutInMillis);
    }
    
    @Override
    public ProfilerEventHandler getEventSink() {
        return this.query.getEventSink();
    }
    
    @Override
    public void setEventSink(final ProfilerEventHandler eventSink) {
        this.query.setEventSink(eventSink);
    }
    
    @Override
    public AtomicBoolean getStatementExecuting() {
        return this.query.getStatementExecuting();
    }
    
    @Override
    public void setCurrentCatalog(final String currentCatalog) {
        this.query.setCurrentCatalog(currentCatalog);
    }
    
    @Override
    public boolean isClearWarningsCalled() {
        return this.query.isClearWarningsCalled();
    }
    
    @Override
    public void setClearWarningsCalled(final boolean clearWarningsCalled) {
        this.query.setClearWarningsCalled(clearWarningsCalled);
    }
    
    @Override
    public Query getQuery() {
        return this.query;
    }
}
