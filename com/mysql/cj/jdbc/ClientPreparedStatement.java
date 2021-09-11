
package com.mysql.cj.jdbc;

import com.mysql.cj.protocol.ResultsetRows;
import java.util.List;
import com.mysql.cj.protocol.a.result.ResultsetRowsStatic;
import com.mysql.cj.result.DefaultColumnDefinition;
import com.mysql.cj.protocol.a.result.ByteArrayRow;
import com.mysql.cj.exceptions.ExceptionInterceptor;
import com.mysql.cj.CharsetMapping;
import com.mysql.cj.ClientPreparedQueryBindValue;
import com.mysql.cj.jdbc.result.ResultSetImpl;
import java.net.URL;
import java.sql.Timestamp;
import java.sql.Time;
import java.sql.SQLXML;
import java.sql.RowId;
import java.sql.Ref;
import java.sql.SQLType;
import com.mysql.cj.exceptions.FeatureNotAvailableException;
import java.sql.JDBCType;
import java.sql.NClob;
import java.math.BigInteger;
import java.util.Calendar;
import java.sql.Date;
import java.sql.Clob;
import java.io.Reader;
import java.sql.Blob;
import java.math.BigDecimal;
import java.io.InputStream;
import java.sql.Array;
import java.io.UnsupportedEncodingException;
import com.mysql.cj.log.ProfilerEvent;
import com.mysql.cj.log.ProfilerEventImpl;
import com.mysql.cj.Constants;
import com.mysql.cj.ClientPreparedQueryBindings;
import java.sql.ParameterMetaData;
import com.mysql.cj.util.StringUtils;
import com.mysql.cj.result.Field;
import com.mysql.cj.util.Util;
import java.sql.ResultSet;
import com.mysql.cj.protocol.a.NativePacketPayload;
import com.mysql.cj.exceptions.StatementIsClosedException;
import com.mysql.cj.jdbc.exceptions.MySQLStatementCancelledException;
import com.mysql.cj.jdbc.exceptions.MySQLTimeoutException;
import java.sql.Statement;
import com.mysql.cj.MysqlType;
import com.mysql.cj.CancelQueryTask;
import com.mysql.cj.Query;
import java.sql.PreparedStatement;
import com.mysql.cj.NativeSession;
import com.mysql.cj.result.Row;
import java.util.ArrayList;
import com.mysql.cj.protocol.Message;
import com.mysql.cj.jdbc.result.ResultSetInternalMethods;
import com.mysql.cj.jdbc.result.CachedResultSetMetaData;
import com.mysql.cj.protocol.ColumnDefinition;
import com.mysql.cj.jdbc.exceptions.SQLError;
import com.mysql.cj.Messages;
import com.mysql.cj.BindValue;
import com.mysql.cj.QueryBindings;
import com.mysql.cj.exceptions.CJException;
import com.mysql.cj.jdbc.exceptions.SQLExceptionsMapping;
import com.mysql.cj.Session;
import com.mysql.cj.PreparedQuery;
import com.mysql.cj.conf.PropertyKey;
import com.mysql.cj.ClientPreparedQuery;
import com.mysql.cj.ParseInfo;
import java.sql.SQLException;
import com.mysql.cj.conf.RuntimeProperty;
import java.sql.ResultSetMetaData;

public class ClientPreparedStatement extends StatementImpl implements JdbcPreparedStatement
{
    protected boolean batchHasPlainStatements;
    protected MysqlParameterMetadata parameterMetaData;
    private ResultSetMetaData pstmtResultMetaData;
    protected String batchedValuesClause;
    private boolean doPingInstead;
    private boolean compensateForOnDuplicateKeyUpdate;
    protected RuntimeProperty<Boolean> useStreamLengthsInPrepStmts;
    protected RuntimeProperty<Boolean> autoClosePStmtStreams;
    protected int rewrittenBatchSize;
    
    protected static ClientPreparedStatement getInstance(final JdbcConnection conn, final String sql, final String catalog) throws SQLException {
        return new ClientPreparedStatement(conn, sql, catalog);
    }
    
    protected static ClientPreparedStatement getInstance(final JdbcConnection conn, final String sql, final String catalog, final ParseInfo cachedParseInfo) throws SQLException {
        return new ClientPreparedStatement(conn, sql, catalog, cachedParseInfo);
    }
    
    @Override
    protected void initQuery() {
        this.query = new ClientPreparedQuery(this.session);
    }
    
    protected ClientPreparedStatement(final JdbcConnection conn, final String catalog) throws SQLException {
        super(conn, catalog);
        this.batchHasPlainStatements = false;
        this.compensateForOnDuplicateKeyUpdate = false;
        this.rewrittenBatchSize = 0;
        this.compensateForOnDuplicateKeyUpdate = this.session.getPropertySet().getBooleanProperty(PropertyKey.compensateOnDuplicateKeyUpdateCounts).getValue();
        this.useStreamLengthsInPrepStmts = this.session.getPropertySet().getBooleanProperty(PropertyKey.useStreamLengthsInPrepStmts);
        this.autoClosePStmtStreams = this.session.getPropertySet().getBooleanProperty(PropertyKey.autoClosePStmtStreams);
    }
    
    public ClientPreparedStatement(final JdbcConnection conn, final String sql, final String catalog) throws SQLException {
        this(conn, sql, catalog, null);
    }
    
    public ClientPreparedStatement(final JdbcConnection conn, final String sql, final String catalog, final ParseInfo cachedParseInfo) throws SQLException {
        this(conn, catalog);
        try {
            ((PreparedQuery)this.query).checkNullOrEmptyQuery(sql);
            ((PreparedQuery)this.query).setOriginalSql(sql);
            ((PreparedQuery)this.query).setParseInfo((cachedParseInfo != null) ? cachedParseInfo : new ParseInfo(sql, this.session, this.charEncoding));
        }
        catch (CJException e) {
            throw SQLExceptionsMapping.translateException(e, this.exceptionInterceptor);
        }
        this.doPingInstead = sql.startsWith("/* ping */");
        this.initializeFromParseInfo();
    }
    
    @Override
    public QueryBindings<?> getQueryBindings() {
        return ((PreparedQuery)this.query).getQueryBindings();
    }
    
    @Override
    public String toString() {
        final StringBuilder buf = new StringBuilder();
        buf.append(this.getClass().getName());
        buf.append(": ");
        try {
            buf.append(this.asSql());
        }
        catch (SQLException sqlEx) {
            buf.append("EXCEPTION: " + sqlEx.toString());
        }
        return buf.toString();
    }
    
    @Override
    public void addBatch() throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                final QueryBindings<?> queryBindings = ((PreparedQuery)this.query).getQueryBindings();
                queryBindings.checkAllParametersSet();
                this.query.addBatch(queryBindings.clone());
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void addBatch(final String sql) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                this.batchHasPlainStatements = true;
                super.addBatch(sql);
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    public String asSql() throws SQLException {
        return ((PreparedQuery)this.query).asSql(false);
    }
    
    public String asSql(final boolean quoteStreamsAndUnknowns) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                return ((PreparedQuery)this.query).asSql(quoteStreamsAndUnknowns);
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void clearBatch() throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                this.batchHasPlainStatements = false;
                super.clearBatch();
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void clearParameters() throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                for (final BindValue bv : ((PreparedQuery)this.query).getQueryBindings().getBindValues()) {
                    bv.reset();
                }
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    protected boolean checkReadOnlySafeStatement() throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                return ((PreparedQuery)this.query).getParseInfo().getFirstStmtChar() == 'S' || !this.connection.isReadOnly();
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean execute() throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                final JdbcConnection locallyScopedConn = this.connection;
                if (!this.doPingInstead && !this.checkReadOnlySafeStatement()) {
                    throw SQLError.createSQLException(Messages.getString("PreparedStatement.20") + Messages.getString("PreparedStatement.21"), "S1009", this.exceptionInterceptor);
                }
                ResultSetInternalMethods rs = null;
                this.lastQueryIsOnDupKeyUpdate = false;
                if (this.retrieveGeneratedKeys) {
                    this.lastQueryIsOnDupKeyUpdate = this.containsOnDuplicateKeyUpdateInSQL();
                }
                this.batchedGeneratedKeys = null;
                this.resetCancelledState();
                this.implicitlyCloseAllOpenResults();
                this.clearWarnings();
                if (this.doPingInstead) {
                    this.doPingInstead();
                    return true;
                }
                this.setupStreamingTimeout(locallyScopedConn);
                final Message sendPacket = ((PreparedQuery)this.query).fillSendPacket();
                String oldCatalog = null;
                if (!locallyScopedConn.getCatalog().equals(this.getCurrentCatalog())) {
                    oldCatalog = locallyScopedConn.getCatalog();
                    locallyScopedConn.setCatalog(this.getCurrentCatalog());
                }
                CachedResultSetMetaData cachedMetadata = null;
                final boolean cacheResultSetMetadata = locallyScopedConn.getPropertySet().getBooleanProperty(PropertyKey.cacheResultSetMetadata).getValue();
                if (cacheResultSetMetadata) {
                    cachedMetadata = locallyScopedConn.getCachedMetaData(((PreparedQuery)this.query).getOriginalSql());
                }
                locallyScopedConn.setSessionMaxRows((((PreparedQuery)this.query).getParseInfo().getFirstStmtChar() == 'S') ? this.maxRows : -1);
                rs = this.executeInternal(this.maxRows, sendPacket, this.createStreamingResultSet(), ((PreparedQuery)this.query).getParseInfo().getFirstStmtChar() == 'S', cachedMetadata, false);
                if (cachedMetadata != null) {
                    locallyScopedConn.initializeResultsMetadataFromCache(((PreparedQuery)this.query).getOriginalSql(), cachedMetadata, rs);
                }
                else if (rs.hasRows() && cacheResultSetMetadata) {
                    locallyScopedConn.initializeResultsMetadataFromCache(((PreparedQuery)this.query).getOriginalSql(), null, rs);
                }
                if (this.retrieveGeneratedKeys) {
                    rs.setFirstCharOfQuery(((PreparedQuery)this.query).getParseInfo().getFirstStmtChar());
                }
                if (oldCatalog != null) {
                    locallyScopedConn.setCatalog(oldCatalog);
                }
                if (rs != null) {
                    this.lastInsertId = rs.getUpdateID();
                    this.results = rs;
                }
                return rs != null && rs.hasRows();
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    protected long[] executeBatchInternal() throws SQLException {
        synchronized (this.checkClosed().getConnectionMutex()) {
            if (this.connection.isReadOnly()) {
                throw new SQLException(Messages.getString("PreparedStatement.25") + Messages.getString("PreparedStatement.26"), "S1009");
            }
            if (this.query.getBatchedArgs() == null || this.query.getBatchedArgs().size() == 0) {
                return new long[0];
            }
            final int batchTimeout = this.getTimeoutInMillis();
            this.setTimeoutInMillis(0);
            this.resetCancelledState();
            try {
                this.statementBegins();
                this.clearWarnings();
                if (!this.batchHasPlainStatements && this.rewriteBatchedStatements.getValue()) {
                    if (((PreparedQuery)this.query).getParseInfo().canRewriteAsMultiValueInsertAtSqlLevel()) {
                        return this.executeBatchedInserts(batchTimeout);
                    }
                    if (!this.batchHasPlainStatements && this.query.getBatchedArgs() != null && this.query.getBatchedArgs().size() > 3) {
                        return this.executePreparedBatchAsMultiStatement(batchTimeout);
                    }
                }
                return this.executeBatchSerially(batchTimeout);
            }
            finally {
                this.query.getStatementExecuting().set(false);
                this.clearBatch();
            }
        }
    }
    
    protected long[] executePreparedBatchAsMultiStatement(final int batchTimeout) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                if (this.batchedValuesClause == null) {
                    this.batchedValuesClause = ((PreparedQuery)this.query).getOriginalSql() + ";";
                }
                final JdbcConnection locallyScopedConn = this.connection;
                final boolean multiQueriesEnabled = locallyScopedConn.getPropertySet().getBooleanProperty(PropertyKey.allowMultiQueries).getValue();
                CancelQueryTask timeoutTask = null;
                try {
                    this.clearWarnings();
                    final int numBatchedArgs = this.query.getBatchedArgs().size();
                    if (this.retrieveGeneratedKeys) {
                        this.batchedGeneratedKeys = new ArrayList<Row>(numBatchedArgs);
                    }
                    int numValuesPerBatch = ((PreparedQuery)this.query).computeBatchSize(numBatchedArgs);
                    if (numBatchedArgs < numValuesPerBatch) {
                        numValuesPerBatch = numBatchedArgs;
                    }
                    PreparedStatement batchedStatement = null;
                    int batchedParamIndex = 1;
                    int numberToExecuteAsMultiValue = 0;
                    int batchCounter = 0;
                    int updateCountCounter = 0;
                    final long[] updateCounts = new long[numBatchedArgs * ((PreparedQuery)this.query).getParseInfo().numberOfQueries];
                    SQLException sqlEx = null;
                    try {
                        if (!multiQueriesEnabled) {
                            ((NativeSession)locallyScopedConn.getSession()).enableMultiQueries();
                        }
                        batchedStatement = (this.retrieveGeneratedKeys ? locallyScopedConn.prepareStatement(this.generateMultiStatementForBatch(numValuesPerBatch), 1).unwrap(PreparedStatement.class) : locallyScopedConn.prepareStatement(this.generateMultiStatementForBatch(numValuesPerBatch)).unwrap(PreparedStatement.class));
                        timeoutTask = this.startQueryTimer((Query)batchedStatement, batchTimeout);
                        numberToExecuteAsMultiValue = ((numBatchedArgs < numValuesPerBatch) ? numBatchedArgs : (numBatchedArgs / numValuesPerBatch));
                        for (int numberArgsToExecute = numberToExecuteAsMultiValue * numValuesPerBatch, i = 0; i < numberArgsToExecute; ++i) {
                            if (i != 0 && i % numValuesPerBatch == 0) {
                                try {
                                    batchedStatement.execute();
                                }
                                catch (SQLException ex) {
                                    sqlEx = this.handleExceptionForBatch(batchCounter, numValuesPerBatch, updateCounts, ex);
                                }
                                updateCountCounter = this.processMultiCountsAndKeys((StatementImpl)batchedStatement, updateCountCounter, updateCounts);
                                batchedStatement.clearParameters();
                                batchedParamIndex = 1;
                            }
                            batchedParamIndex = this.setOneBatchedParameterSet(batchedStatement, batchedParamIndex, this.query.getBatchedArgs().get(batchCounter++));
                        }
                        try {
                            batchedStatement.execute();
                        }
                        catch (SQLException ex2) {
                            sqlEx = this.handleExceptionForBatch(batchCounter - 1, numValuesPerBatch, updateCounts, ex2);
                        }
                        updateCountCounter = this.processMultiCountsAndKeys((StatementImpl)batchedStatement, updateCountCounter, updateCounts);
                        batchedStatement.clearParameters();
                        numValuesPerBatch = numBatchedArgs - batchCounter;
                        if (timeoutTask != null) {
                            ((JdbcPreparedStatement)batchedStatement).checkCancelTimeout();
                        }
                    }
                    finally {
                        if (batchedStatement != null) {
                            batchedStatement.close();
                            batchedStatement = null;
                        }
                    }
                    try {
                        if (numValuesPerBatch > 0) {
                            batchedStatement = (this.retrieveGeneratedKeys ? locallyScopedConn.prepareStatement(this.generateMultiStatementForBatch(numValuesPerBatch), 1) : locallyScopedConn.prepareStatement(this.generateMultiStatementForBatch(numValuesPerBatch)));
                            if (timeoutTask != null) {
                                timeoutTask.setQueryToCancel((Query)batchedStatement);
                            }
                            for (batchedParamIndex = 1; batchCounter < numBatchedArgs; batchedParamIndex = this.setOneBatchedParameterSet(batchedStatement, batchedParamIndex, this.query.getBatchedArgs().get(batchCounter++))) {}
                            try {
                                batchedStatement.execute();
                            }
                            catch (SQLException ex3) {
                                sqlEx = this.handleExceptionForBatch(batchCounter - 1, numValuesPerBatch, updateCounts, ex3);
                            }
                            updateCountCounter = this.processMultiCountsAndKeys((StatementImpl)batchedStatement, updateCountCounter, updateCounts);
                            batchedStatement.clearParameters();
                        }
                        if (timeoutTask != null) {
                            this.stopQueryTimer(timeoutTask, true, true);
                            timeoutTask = null;
                        }
                        if (sqlEx != null) {
                            throw SQLError.createBatchUpdateException(sqlEx, updateCounts, this.exceptionInterceptor);
                        }
                        return updateCounts;
                    }
                    finally {
                        if (batchedStatement != null) {
                            batchedStatement.close();
                        }
                    }
                }
                finally {
                    this.stopQueryTimer(timeoutTask, false, false);
                    this.resetCancelledState();
                    if (!multiQueriesEnabled) {
                        ((NativeSession)locallyScopedConn.getSession()).disableMultiQueries();
                    }
                    this.clearBatch();
                }
            }
        }
        catch (CJException ex4) {
            throw SQLExceptionsMapping.translateException(ex4, this.getExceptionInterceptor());
        }
    }
    
    protected int setOneBatchedParameterSet(final PreparedStatement batchedStatement, int batchedParamIndex, final Object paramSet) throws SQLException {
        final QueryBindings<?> paramArg = (QueryBindings<?>)paramSet;
        final BindValue[] bindValues = (BindValue[])paramArg.getBindValues();
        for (int j = 0; j < bindValues.length; ++j) {
            if (bindValues[j].isNull()) {
                batchedStatement.setNull(batchedParamIndex++, MysqlType.NULL.getJdbcType());
            }
            else if (bindValues[j].isStream()) {
                batchedStatement.setBinaryStream(batchedParamIndex++, bindValues[j].getStreamValue(), bindValues[j].getStreamLength());
            }
            else {
                ((JdbcPreparedStatement)batchedStatement).setBytesNoEscapeNoQuotes(batchedParamIndex++, bindValues[j].getByteValue());
            }
        }
        return batchedParamIndex;
    }
    
    private String generateMultiStatementForBatch(final int numBatches) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                final String origSql = ((PreparedQuery)this.query).getOriginalSql();
                final StringBuilder newStatementSql = new StringBuilder((origSql.length() + 1) * numBatches);
                newStatementSql.append(origSql);
                for (int i = 0; i < numBatches - 1; ++i) {
                    newStatementSql.append(';');
                    newStatementSql.append(origSql);
                }
                return newStatementSql.toString();
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    protected long[] executeBatchedInserts(final int batchTimeout) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                final String valuesClause = ((PreparedQuery)this.query).getParseInfo().getValuesClause();
                final JdbcConnection locallyScopedConn = this.connection;
                if (valuesClause == null) {
                    return this.executeBatchSerially(batchTimeout);
                }
                final int numBatchedArgs = this.query.getBatchedArgs().size();
                if (this.retrieveGeneratedKeys) {
                    this.batchedGeneratedKeys = new ArrayList<Row>(numBatchedArgs);
                }
                int numValuesPerBatch = ((PreparedQuery)this.query).computeBatchSize(numBatchedArgs);
                if (numBatchedArgs < numValuesPerBatch) {
                    numValuesPerBatch = numBatchedArgs;
                }
                JdbcPreparedStatement batchedStatement = null;
                int batchedParamIndex = 1;
                long updateCountRunningTotal = 0L;
                int numberToExecuteAsMultiValue = 0;
                int batchCounter = 0;
                CancelQueryTask timeoutTask = null;
                SQLException sqlEx = null;
                final long[] updateCounts = new long[numBatchedArgs];
                try {
                    try {
                        batchedStatement = this.prepareBatchedInsertSQL(locallyScopedConn, numValuesPerBatch);
                        timeoutTask = this.startQueryTimer(batchedStatement, batchTimeout);
                        numberToExecuteAsMultiValue = ((numBatchedArgs < numValuesPerBatch) ? numBatchedArgs : (numBatchedArgs / numValuesPerBatch));
                        for (int numberArgsToExecute = numberToExecuteAsMultiValue * numValuesPerBatch, i = 0; i < numberArgsToExecute; ++i) {
                            if (i != 0 && i % numValuesPerBatch == 0) {
                                try {
                                    updateCountRunningTotal += batchedStatement.executeLargeUpdate();
                                }
                                catch (SQLException ex) {
                                    sqlEx = this.handleExceptionForBatch(batchCounter - 1, numValuesPerBatch, updateCounts, ex);
                                }
                                this.getBatchedGeneratedKeys(batchedStatement);
                                batchedStatement.clearParameters();
                                batchedParamIndex = 1;
                            }
                            batchedParamIndex = this.setOneBatchedParameterSet(batchedStatement, batchedParamIndex, this.query.getBatchedArgs().get(batchCounter++));
                        }
                        try {
                            updateCountRunningTotal += batchedStatement.executeLargeUpdate();
                        }
                        catch (SQLException ex2) {
                            sqlEx = this.handleExceptionForBatch(batchCounter - 1, numValuesPerBatch, updateCounts, ex2);
                        }
                        this.getBatchedGeneratedKeys(batchedStatement);
                        numValuesPerBatch = numBatchedArgs - batchCounter;
                    }
                    finally {
                        if (batchedStatement != null) {
                            batchedStatement.close();
                            batchedStatement = null;
                        }
                    }
                    try {
                        if (numValuesPerBatch > 0) {
                            batchedStatement = this.prepareBatchedInsertSQL(locallyScopedConn, numValuesPerBatch);
                            if (timeoutTask != null) {
                                timeoutTask.setQueryToCancel(batchedStatement);
                            }
                            for (batchedParamIndex = 1; batchCounter < numBatchedArgs; batchedParamIndex = this.setOneBatchedParameterSet(batchedStatement, batchedParamIndex, this.query.getBatchedArgs().get(batchCounter++))) {}
                            try {
                                updateCountRunningTotal += batchedStatement.executeLargeUpdate();
                            }
                            catch (SQLException ex3) {
                                sqlEx = this.handleExceptionForBatch(batchCounter - 1, numValuesPerBatch, updateCounts, ex3);
                            }
                            this.getBatchedGeneratedKeys(batchedStatement);
                        }
                        if (sqlEx != null) {
                            throw SQLError.createBatchUpdateException(sqlEx, updateCounts, this.exceptionInterceptor);
                        }
                        if (numBatchedArgs > 1) {
                            final long updCount = (updateCountRunningTotal > 0L) ? -2L : 0L;
                            for (int j = 0; j < numBatchedArgs; ++j) {
                                updateCounts[j] = updCount;
                            }
                        }
                        else {
                            updateCounts[0] = updateCountRunningTotal;
                        }
                        return updateCounts;
                    }
                    finally {
                        if (batchedStatement != null) {
                            batchedStatement.close();
                        }
                    }
                }
                finally {
                    this.stopQueryTimer(timeoutTask, false, false);
                    this.resetCancelledState();
                }
            }
        }
        catch (CJException ex4) {
            throw SQLExceptionsMapping.translateException(ex4, this.getExceptionInterceptor());
        }
    }
    
    protected long[] executeBatchSerially(final int batchTimeout) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                if (this.connection == null) {
                    this.checkClosed();
                }
                long[] updateCounts = null;
                if (this.query.getBatchedArgs() != null) {
                    final int nbrCommands = this.query.getBatchedArgs().size();
                    updateCounts = new long[nbrCommands];
                    for (int i = 0; i < nbrCommands; ++i) {
                        updateCounts[i] = -3L;
                    }
                    SQLException sqlEx = null;
                    CancelQueryTask timeoutTask = null;
                    try {
                        timeoutTask = this.startQueryTimer(this, batchTimeout);
                        if (this.retrieveGeneratedKeys) {
                            this.batchedGeneratedKeys = new ArrayList<Row>(nbrCommands);
                        }
                        int batchCommandIndex;
                        Object arg;
                        QueryBindings<?> queryBindings;
                        long[] newUpdateCounts;
                        for (batchCommandIndex = ((PreparedQuery)this.query).getBatchCommandIndex(), batchCommandIndex = 0; batchCommandIndex < nbrCommands; ++batchCommandIndex) {
                            ((PreparedQuery)this.query).setBatchCommandIndex(batchCommandIndex);
                            arg = this.query.getBatchedArgs().get(batchCommandIndex);
                            try {
                                if (arg instanceof String) {
                                    updateCounts[batchCommandIndex] = this.executeUpdateInternal((String)arg, true, this.retrieveGeneratedKeys);
                                    this.getBatchedGeneratedKeys((this.results.getFirstCharOfQuery() == 'I' && this.containsOnDuplicateKeyInString((String)arg)) ? 1 : 0);
                                }
                                else {
                                    queryBindings = (QueryBindings<?>)arg;
                                    updateCounts[batchCommandIndex] = this.executeUpdateInternal(queryBindings, true);
                                    this.getBatchedGeneratedKeys(this.containsOnDuplicateKeyUpdateInSQL() ? 1 : 0);
                                }
                            }
                            catch (SQLException ex) {
                                updateCounts[batchCommandIndex] = -3L;
                                if (!this.continueBatchOnError || ex instanceof MySQLTimeoutException || ex instanceof MySQLStatementCancelledException || this.hasDeadlockOrTimeoutRolledBackTx(ex)) {
                                    newUpdateCounts = new long[batchCommandIndex];
                                    System.arraycopy(updateCounts, 0, newUpdateCounts, 0, batchCommandIndex);
                                    throw SQLError.createBatchUpdateException(ex, newUpdateCounts, this.exceptionInterceptor);
                                }
                                sqlEx = ex;
                            }
                        }
                        if (sqlEx != null) {
                            throw SQLError.createBatchUpdateException(sqlEx, updateCounts, this.exceptionInterceptor);
                        }
                    }
                    catch (NullPointerException npe) {
                        try {
                            this.checkClosed();
                        }
                        catch (StatementIsClosedException connectionClosedEx) {
                            final int batchCommandIndex2 = ((PreparedQuery)this.query).getBatchCommandIndex();
                            updateCounts[batchCommandIndex2] = -3L;
                            final long[] newUpdateCounts = new long[batchCommandIndex2];
                            System.arraycopy(updateCounts, 0, newUpdateCounts, 0, batchCommandIndex2);
                            throw SQLError.createBatchUpdateException(SQLExceptionsMapping.translateException(connectionClosedEx), newUpdateCounts, this.exceptionInterceptor);
                        }
                        throw npe;
                    }
                    finally {
                        ((PreparedQuery)this.query).setBatchCommandIndex(-1);
                        this.stopQueryTimer(timeoutTask, false, false);
                        this.resetCancelledState();
                    }
                }
                return (updateCounts != null) ? updateCounts : new long[0];
            }
        }
        catch (CJException ex2) {
            throw SQLExceptionsMapping.translateException(ex2, this.getExceptionInterceptor());
        }
    }
    
    protected <M extends Message> ResultSetInternalMethods executeInternal(final int maxRowsToRetrieve, final M sendPacket, final boolean createStreamingResultSet, final boolean queryIsSelectOnly, final ColumnDefinition metadata, final boolean isBatch) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                try {
                    final JdbcConnection locallyScopedConnection = this.connection;
                    ((PreparedQuery)this.query).getQueryBindings().setNumberOfExecutions(((PreparedQuery)this.query).getQueryBindings().getNumberOfExecutions() + 1);
                    CancelQueryTask timeoutTask = null;
                    ResultSetInternalMethods rs;
                    try {
                        timeoutTask = this.startQueryTimer(this, this.getTimeoutInMillis());
                        if (!isBatch) {
                            this.statementBegins();
                        }
                        rs = ((NativeSession)locallyScopedConnection.getSession()).execSQL(this, null, maxRowsToRetrieve, (NativePacketPayload)sendPacket, createStreamingResultSet, this.getResultSetFactory(), this.getCurrentCatalog(), metadata, isBatch);
                        if (timeoutTask != null) {
                            this.stopQueryTimer(timeoutTask, true, true);
                            timeoutTask = null;
                        }
                    }
                    finally {
                        if (!isBatch) {
                            this.query.getStatementExecuting().set(false);
                        }
                        this.stopQueryTimer(timeoutTask, false, false);
                    }
                    return rs;
                }
                catch (NullPointerException npe) {
                    this.checkClosed();
                    throw npe;
                }
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public ResultSet executeQuery() throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                final JdbcConnection locallyScopedConn = this.connection;
                this.checkForDml(((PreparedQuery)this.query).getOriginalSql(), ((PreparedQuery)this.query).getParseInfo().getFirstStmtChar());
                this.batchedGeneratedKeys = null;
                this.resetCancelledState();
                this.implicitlyCloseAllOpenResults();
                this.clearWarnings();
                if (this.doPingInstead) {
                    this.doPingInstead();
                    return this.results;
                }
                this.setupStreamingTimeout(locallyScopedConn);
                final Message sendPacket = ((PreparedQuery)this.query).fillSendPacket();
                String oldCatalog = null;
                if (!locallyScopedConn.getCatalog().equals(this.getCurrentCatalog())) {
                    oldCatalog = locallyScopedConn.getCatalog();
                    locallyScopedConn.setCatalog(this.getCurrentCatalog());
                }
                CachedResultSetMetaData cachedMetadata = null;
                final boolean cacheResultSetMetadata = locallyScopedConn.getPropertySet().getBooleanProperty(PropertyKey.cacheResultSetMetadata).getValue();
                final String origSql = ((PreparedQuery)this.query).getOriginalSql();
                if (cacheResultSetMetadata) {
                    cachedMetadata = locallyScopedConn.getCachedMetaData(origSql);
                }
                locallyScopedConn.setSessionMaxRows(this.maxRows);
                this.results = this.executeInternal(this.maxRows, sendPacket, this.createStreamingResultSet(), true, cachedMetadata, false);
                if (oldCatalog != null) {
                    locallyScopedConn.setCatalog(oldCatalog);
                }
                if (cachedMetadata != null) {
                    locallyScopedConn.initializeResultsMetadataFromCache(origSql, cachedMetadata, this.results);
                }
                else if (cacheResultSetMetadata) {
                    locallyScopedConn.initializeResultsMetadataFromCache(origSql, null, this.results);
                }
                this.lastInsertId = this.results.getUpdateID();
                return this.results;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public int executeUpdate() throws SQLException {
        try {
            return Util.truncateAndConvertToInt(this.executeLargeUpdate());
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    protected long executeUpdateInternal(final boolean clearBatchedGeneratedKeysAndWarnings, final boolean isBatch) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                if (clearBatchedGeneratedKeysAndWarnings) {
                    this.clearWarnings();
                    this.batchedGeneratedKeys = null;
                }
                return this.executeUpdateInternal(((PreparedQuery)this.query).getQueryBindings(), isBatch);
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    protected long executeUpdateInternal(final QueryBindings<?> bindings, final boolean isReallyBatch) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                final JdbcConnection locallyScopedConn = this.connection;
                if (locallyScopedConn.isReadOnly(false)) {
                    throw SQLError.createSQLException(Messages.getString("PreparedStatement.34") + Messages.getString("PreparedStatement.35"), "S1009", this.exceptionInterceptor);
                }
                if (((PreparedQuery)this.query).getParseInfo().getFirstStmtChar() == 'S' && this.isSelectQuery()) {
                    throw SQLError.createSQLException(Messages.getString("PreparedStatement.37"), "01S03", this.exceptionInterceptor);
                }
                this.resetCancelledState();
                this.implicitlyCloseAllOpenResults();
                ResultSetInternalMethods rs = null;
                final Message sendPacket = ((PreparedQuery)this.query).fillSendPacket(bindings);
                String oldCatalog = null;
                if (!locallyScopedConn.getCatalog().equals(this.getCurrentCatalog())) {
                    oldCatalog = locallyScopedConn.getCatalog();
                    locallyScopedConn.setCatalog(this.getCurrentCatalog());
                }
                locallyScopedConn.setSessionMaxRows(-1);
                rs = this.executeInternal(-1, sendPacket, false, false, null, isReallyBatch);
                if (this.retrieveGeneratedKeys) {
                    rs.setFirstCharOfQuery(((PreparedQuery)this.query).getParseInfo().getFirstStmtChar());
                }
                if (oldCatalog != null) {
                    locallyScopedConn.setCatalog(oldCatalog);
                }
                this.results = rs;
                this.updateCount = rs.getUpdateCount();
                if (this.containsOnDuplicateKeyUpdateInSQL() && this.compensateForOnDuplicateKeyUpdate && (this.updateCount == 2L || this.updateCount == 0L)) {
                    this.updateCount = 1L;
                }
                this.lastInsertId = rs.getUpdateID();
                return this.updateCount;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    protected boolean containsOnDuplicateKeyUpdateInSQL() {
        return ((PreparedQuery)this.query).getParseInfo().containsOnDuplicateKeyUpdateInSQL();
    }
    
    protected ClientPreparedStatement prepareBatchedInsertSQL(final JdbcConnection localConn, final int numBatches) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                final ClientPreparedStatement pstmt = new ClientPreparedStatement(localConn, "Rewritten batch of: " + ((PreparedQuery)this.query).getOriginalSql(), this.getCurrentCatalog(), ((PreparedQuery)this.query).getParseInfo().getParseInfoForBatch(numBatches));
                pstmt.setRetrieveGeneratedKeys(this.retrieveGeneratedKeys);
                pstmt.rewrittenBatchSize = numBatches;
                return pstmt;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    protected void setRetrieveGeneratedKeys(final boolean flag) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                this.retrieveGeneratedKeys = flag;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public byte[] getBytesRepresentation(final int parameterIndex) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                return ((ClientPreparedQuery)this.query).getBytesRepresentation(parameterIndex);
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    protected byte[] getBytesRepresentationForBatch(final int parameterIndex, final int commandIndex) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                return ((ClientPreparedQuery)this.query).getBytesRepresentationForBatch(parameterIndex, commandIndex);
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public ResultSetMetaData getMetaData() throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                if (!this.isSelectQuery()) {
                    return null;
                }
                JdbcPreparedStatement mdStmt = null;
                ResultSet mdRs = null;
                if (this.pstmtResultMetaData == null) {
                    try {
                        mdStmt = new ClientPreparedStatement(this.connection, ((PreparedQuery)this.query).getOriginalSql(), this.getCurrentCatalog(), ((PreparedQuery)this.query).getParseInfo());
                        mdStmt.setMaxRows(1);
                        for (int paramCount = ((PreparedQuery)this.query).getParameterCount(), i = 1; i <= paramCount; ++i) {
                            mdStmt.setString(i, "");
                        }
                        final boolean hadResults = mdStmt.execute();
                        if (hadResults) {
                            mdRs = mdStmt.getResultSet();
                            this.pstmtResultMetaData = mdRs.getMetaData();
                        }
                        else {
                            this.pstmtResultMetaData = new com.mysql.cj.jdbc.result.ResultSetMetaData(this.session, new Field[0], this.session.getPropertySet().getBooleanProperty(PropertyKey.useOldAliasMetadataBehavior).getValue(), this.session.getPropertySet().getBooleanProperty(PropertyKey.yearIsDateType).getValue(), this.exceptionInterceptor);
                        }
                    }
                    finally {
                        SQLException sqlExRethrow = null;
                        if (mdRs != null) {
                            try {
                                mdRs.close();
                            }
                            catch (SQLException sqlEx) {
                                sqlExRethrow = sqlEx;
                            }
                            mdRs = null;
                        }
                        if (mdStmt != null) {
                            try {
                                mdStmt.close();
                            }
                            catch (SQLException sqlEx) {
                                sqlExRethrow = sqlEx;
                            }
                            mdStmt = null;
                        }
                        if (sqlExRethrow != null) {
                            throw sqlExRethrow;
                        }
                    }
                }
                return this.pstmtResultMetaData;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    protected boolean isSelectQuery() throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                return StringUtils.startsWithIgnoreCaseAndWs(StringUtils.stripComments(((PreparedQuery)this.query).getOriginalSql(), "'\"", "'\"", true, false, true, true), "SELECT");
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public ParameterMetaData getParameterMetaData() throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                if (this.parameterMetaData == null) {
                    if (this.session.getPropertySet().getBooleanProperty(PropertyKey.generateSimpleParameterMetadata).getValue()) {
                        this.parameterMetaData = new MysqlParameterMetadata(((PreparedQuery)this.query).getParameterCount());
                    }
                    else {
                        this.parameterMetaData = new MysqlParameterMetadata(this.session, null, ((PreparedQuery)this.query).getParameterCount(), this.exceptionInterceptor);
                    }
                }
                return this.parameterMetaData;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public ParseInfo getParseInfo() {
        return ((PreparedQuery)this.query).getParseInfo();
    }
    
    private void initializeFromParseInfo() throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                final int parameterCount = ((PreparedQuery)this.query).getParseInfo().getStaticSql().length - 1;
                ((PreparedQuery)this.query).setParameterCount(parameterCount);
                ((PreparedQuery)this.query).setQueryBindings(new ClientPreparedQueryBindings(parameterCount, this.session));
                ((ClientPreparedQuery)this.query).getQueryBindings().setLoadDataQuery(((PreparedQuery)this.query).getParseInfo().isFoundLoadData());
                this.clearParameters();
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean isNull(final int paramIndex) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                return ((PreparedQuery)this.query).getQueryBindings().getBindValues()[paramIndex].isNull();
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void realClose(final boolean calledExplicitly, final boolean closeOpenResults) throws SQLException {
        final JdbcConnection locallyScopedConn = this.connection;
        if (locallyScopedConn == null) {
            return;
        }
        synchronized (locallyScopedConn.getConnectionMutex()) {
            if (this.isClosed) {
                return;
            }
            if (this.useUsageAdvisor && ((PreparedQuery)this.query).getQueryBindings().getNumberOfExecutions() <= 1) {
                final String message = Messages.getString("PreparedStatement.43");
                this.query.getEventSink().consumeEvent(new ProfilerEventImpl((byte)0, "", this.getCurrentCatalog(), this.session.getThreadId(), this.getId(), -1, System.currentTimeMillis(), 0L, Constants.MILLIS_I18N, null, this.pointOfOrigin, message));
            }
            super.realClose(calledExplicitly, closeOpenResults);
            ((PreparedQuery)this.query).setOriginalSql(null);
            ((PreparedQuery)this.query).setQueryBindings(null);
        }
    }
    
    @Override
    public String getPreparedSql() {
        synchronized (this.checkClosed().getConnectionMutex()) {
            if (this.rewrittenBatchSize == 0) {
                return ((PreparedQuery)this.query).getOriginalSql();
            }
            try {
                return ((PreparedQuery)this.query).getParseInfo().getSqlForBatch();
            }
            catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }
    }
    
    @Override
    public int getUpdateCount() throws SQLException {
        try {
            int count = super.getUpdateCount();
            if (this.containsOnDuplicateKeyUpdateInSQL() && this.compensateForOnDuplicateKeyUpdate && (count == 2 || count == 0)) {
                count = 1;
            }
            return count;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public long executeLargeUpdate() throws SQLException {
        try {
            return this.executeUpdateInternal(true, false);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    public ParameterBindings getParameterBindings() throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                return new EmulatedPreparedStatementBindings();
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    protected int getParameterIndexOffset() {
        return 0;
    }
    
    protected void checkBounds(final int paramIndex, final int parameterIndexOffset) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                if (paramIndex < 1) {
                    throw SQLError.createSQLException(Messages.getString("PreparedStatement.49") + paramIndex + Messages.getString("PreparedStatement.50"), "S1009", this.exceptionInterceptor);
                }
                if (paramIndex > ((PreparedQuery)this.query).getParameterCount()) {
                    throw SQLError.createSQLException(Messages.getString("PreparedStatement.51") + paramIndex + Messages.getString("PreparedStatement.52") + ((PreparedQuery)this.query).getParameterCount() + Messages.getString("PreparedStatement.53"), "S1009", this.exceptionInterceptor);
                }
                if (parameterIndexOffset == -1 && paramIndex == 1) {
                    throw SQLError.createSQLException(Messages.getString("PreparedStatement.63"), "S1009", this.exceptionInterceptor);
                }
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    protected final int getCoreParameterIndex(final int paramIndex) throws SQLException {
        final int parameterIndexOffset = this.getParameterIndexOffset();
        this.checkBounds(paramIndex, parameterIndexOffset);
        return paramIndex - 1 + parameterIndexOffset;
    }
    
    @Override
    public void setArray(final int i, final Array x) throws SQLException {
        try {
            throw SQLError.createSQLFeatureNotSupportedException();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setAsciiStream(final int parameterIndex, final InputStream x) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                ((PreparedQuery)this.query).getQueryBindings().setAsciiStream(this.getCoreParameterIndex(parameterIndex), x);
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setAsciiStream(final int parameterIndex, final InputStream x, final int length) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                ((PreparedQuery)this.query).getQueryBindings().setAsciiStream(this.getCoreParameterIndex(parameterIndex), x, length);
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setAsciiStream(final int parameterIndex, final InputStream x, final long length) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                ((PreparedQuery)this.query).getQueryBindings().setAsciiStream(this.getCoreParameterIndex(parameterIndex), x, length);
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setBigDecimal(final int parameterIndex, final BigDecimal x) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                ((PreparedQuery)this.query).getQueryBindings().setBigDecimal(this.getCoreParameterIndex(parameterIndex), x);
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setBinaryStream(final int parameterIndex, final InputStream x) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                ((PreparedQuery)this.query).getQueryBindings().setBinaryStream(this.getCoreParameterIndex(parameterIndex), x);
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setBinaryStream(final int parameterIndex, final InputStream x, final int length) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                ((PreparedQuery)this.query).getQueryBindings().setBinaryStream(this.getCoreParameterIndex(parameterIndex), x, length);
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setBinaryStream(final int parameterIndex, final InputStream x, final long length) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                ((PreparedQuery)this.query).getQueryBindings().setBinaryStream(this.getCoreParameterIndex(parameterIndex), x, length);
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setBlob(final int i, final Blob x) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                ((PreparedQuery)this.query).getQueryBindings().setBlob(this.getCoreParameterIndex(i), x);
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setBlob(final int parameterIndex, final InputStream inputStream) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                ((PreparedQuery)this.query).getQueryBindings().setBlob(this.getCoreParameterIndex(parameterIndex), inputStream);
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setBlob(final int parameterIndex, final InputStream inputStream, final long length) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                ((PreparedQuery)this.query).getQueryBindings().setBlob(this.getCoreParameterIndex(parameterIndex), inputStream, length);
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setBoolean(final int parameterIndex, final boolean x) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                ((PreparedQuery)this.query).getQueryBindings().setBoolean(this.getCoreParameterIndex(parameterIndex), x);
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setByte(final int parameterIndex, final byte x) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                ((PreparedQuery)this.query).getQueryBindings().setByte(this.getCoreParameterIndex(parameterIndex), x);
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setBytes(final int parameterIndex, final byte[] x) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                ((PreparedQuery)this.query).getQueryBindings().setBytes(this.getCoreParameterIndex(parameterIndex), x);
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setBytes(final int parameterIndex, final byte[] x, final boolean checkForIntroducer, final boolean escapeForMBChars) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                ((PreparedQuery)this.query).getQueryBindings().setBytes(this.getCoreParameterIndex(parameterIndex), x, checkForIntroducer, escapeForMBChars);
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setBytesNoEscape(final int parameterIndex, final byte[] parameterAsBytes) throws SQLException {
        ((PreparedQuery)this.query).getQueryBindings().setBytesNoEscape(this.getCoreParameterIndex(parameterIndex), parameterAsBytes);
    }
    
    @Override
    public void setBytesNoEscapeNoQuotes(final int parameterIndex, final byte[] parameterAsBytes) throws SQLException {
        ((PreparedQuery)this.query).getQueryBindings().setBytesNoEscapeNoQuotes(this.getCoreParameterIndex(parameterIndex), parameterAsBytes);
    }
    
    @Override
    public void setCharacterStream(final int parameterIndex, final Reader reader) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                ((PreparedQuery)this.query).getQueryBindings().setCharacterStream(this.getCoreParameterIndex(parameterIndex), reader);
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setCharacterStream(final int parameterIndex, final Reader reader, final int length) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                ((PreparedQuery)this.query).getQueryBindings().setCharacterStream(this.getCoreParameterIndex(parameterIndex), reader, length);
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setCharacterStream(final int parameterIndex, final Reader reader, final long length) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                ((PreparedQuery)this.query).getQueryBindings().setCharacterStream(this.getCoreParameterIndex(parameterIndex), reader, length);
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setClob(final int parameterIndex, final Reader reader) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                ((PreparedQuery)this.query).getQueryBindings().setCharacterStream(this.getCoreParameterIndex(parameterIndex), reader);
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setClob(final int parameterIndex, final Reader reader, final long length) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                ((PreparedQuery)this.query).getQueryBindings().setCharacterStream(this.getCoreParameterIndex(parameterIndex), reader, length);
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setClob(final int i, final Clob x) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                ((PreparedQuery)this.query).getQueryBindings().setClob(this.getCoreParameterIndex(i), x);
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setDate(final int parameterIndex, final Date x) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                ((PreparedQuery)this.query).getQueryBindings().setDate(this.getCoreParameterIndex(parameterIndex), x);
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setDate(final int parameterIndex, final Date x, final Calendar cal) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                ((PreparedQuery)this.query).getQueryBindings().setDate(this.getCoreParameterIndex(parameterIndex), x, cal);
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setDouble(final int parameterIndex, final double x) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                ((PreparedQuery)this.query).getQueryBindings().setDouble(this.getCoreParameterIndex(parameterIndex), x);
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setFloat(final int parameterIndex, final float x) throws SQLException {
        try {
            ((PreparedQuery)this.query).getQueryBindings().setFloat(this.getCoreParameterIndex(parameterIndex), x);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setInt(final int parameterIndex, final int x) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                ((PreparedQuery)this.query).getQueryBindings().setInt(this.getCoreParameterIndex(parameterIndex), x);
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setLong(final int parameterIndex, final long x) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                ((PreparedQuery)this.query).getQueryBindings().setLong(this.getCoreParameterIndex(parameterIndex), x);
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setBigInteger(final int parameterIndex, final BigInteger x) throws SQLException {
        synchronized (this.checkClosed().getConnectionMutex()) {
            ((PreparedQuery)this.query).getQueryBindings().setBigInteger(this.getCoreParameterIndex(parameterIndex), x);
        }
    }
    
    @Override
    public void setNCharacterStream(final int parameterIndex, final Reader value) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                ((PreparedQuery)this.query).getQueryBindings().setNCharacterStream(this.getCoreParameterIndex(parameterIndex), value);
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setNCharacterStream(final int parameterIndex, final Reader reader, final long length) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                ((PreparedQuery)this.query).getQueryBindings().setNCharacterStream(this.getCoreParameterIndex(parameterIndex), reader, length);
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setNClob(final int parameterIndex, final Reader reader) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                ((PreparedQuery)this.query).getQueryBindings().setNClob(this.getCoreParameterIndex(parameterIndex), reader);
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setNClob(final int parameterIndex, final Reader reader, final long length) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                ((PreparedQuery)this.query).getQueryBindings().setNClob(this.getCoreParameterIndex(parameterIndex), reader, length);
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setNClob(final int parameterIndex, final NClob value) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                ((PreparedQuery)this.query).getQueryBindings().setNClob(this.getCoreParameterIndex(parameterIndex), value);
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setNString(final int parameterIndex, final String x) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                ((PreparedQuery)this.query).getQueryBindings().setNString(this.getCoreParameterIndex(parameterIndex), x);
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setNull(final int parameterIndex, final int sqlType) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                ((PreparedQuery)this.query).getQueryBindings().setNull(this.getCoreParameterIndex(parameterIndex));
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setNull(final int parameterIndex, final int sqlType, final String typeName) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                ((PreparedQuery)this.query).getQueryBindings().setNull(this.getCoreParameterIndex(parameterIndex));
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setNull(final int parameterIndex, final MysqlType mysqlType) throws SQLException {
        this.setNull(parameterIndex, mysqlType.getJdbcType());
    }
    
    @Override
    public void setObject(final int parameterIndex, final Object parameterObj) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                ((PreparedQuery)this.query).getQueryBindings().setObject(this.getCoreParameterIndex(parameterIndex), parameterObj);
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setObject(final int parameterIndex, final Object parameterObj, final int targetSqlType) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                try {
                    ((PreparedQuery)this.query).getQueryBindings().setObject(this.getCoreParameterIndex(parameterIndex), parameterObj, MysqlType.getByJdbcType(targetSqlType));
                }
                catch (FeatureNotAvailableException nae) {
                    throw SQLError.createSQLFeatureNotSupportedException(Messages.getString("Statement.UnsupportedSQLType") + JDBCType.valueOf(targetSqlType), "S1C00", this.exceptionInterceptor);
                }
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setObject(final int parameterIndex, final Object parameterObj, final SQLType targetSqlType) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                if (targetSqlType instanceof MysqlType) {
                    ((PreparedQuery)this.query).getQueryBindings().setObject(this.getCoreParameterIndex(parameterIndex), parameterObj, (MysqlType)targetSqlType);
                }
                else {
                    this.setObject(parameterIndex, parameterObj, targetSqlType.getVendorTypeNumber());
                }
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setObject(final int parameterIndex, final Object parameterObj, final int targetSqlType, final int scale) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                try {
                    ((PreparedQuery)this.query).getQueryBindings().setObject(this.getCoreParameterIndex(parameterIndex), parameterObj, MysqlType.getByJdbcType(targetSqlType), scale);
                }
                catch (FeatureNotAvailableException nae) {
                    throw SQLError.createSQLFeatureNotSupportedException(Messages.getString("Statement.UnsupportedSQLType") + JDBCType.valueOf(targetSqlType), "S1C00", this.exceptionInterceptor);
                }
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setObject(final int parameterIndex, final Object x, final SQLType targetSqlType, final int scaleOrLength) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                if (targetSqlType instanceof MysqlType) {
                    ((PreparedQuery)this.query).getQueryBindings().setObject(this.getCoreParameterIndex(parameterIndex), x, (MysqlType)targetSqlType, scaleOrLength);
                }
                else {
                    this.setObject(parameterIndex, x, targetSqlType.getVendorTypeNumber(), scaleOrLength);
                }
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setRef(final int i, final Ref x) throws SQLException {
        try {
            throw SQLError.createSQLFeatureNotSupportedException();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setRowId(final int parameterIndex, final RowId x) throws SQLException {
        try {
            throw SQLError.createSQLFeatureNotSupportedException();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setShort(final int parameterIndex, final short x) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                ((PreparedQuery)this.query).getQueryBindings().setShort(this.getCoreParameterIndex(parameterIndex), x);
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setSQLXML(final int parameterIndex, final SQLXML xmlObject) throws SQLException {
        try {
            if (xmlObject == null) {
                this.setNull(parameterIndex, MysqlType.VARCHAR);
            }
            else {
                this.setCharacterStream(parameterIndex, ((MysqlSQLXML)xmlObject).serializeAsCharacterStream());
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setString(final int parameterIndex, final String x) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                ((PreparedQuery)this.query).getQueryBindings().setString(this.getCoreParameterIndex(parameterIndex), x);
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setTime(final int parameterIndex, final Time x) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                ((PreparedQuery)this.query).getQueryBindings().setTime(this.getCoreParameterIndex(parameterIndex), x);
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setTime(final int parameterIndex, final Time x, final Calendar cal) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                ((PreparedQuery)this.query).getQueryBindings().setTime(this.getCoreParameterIndex(parameterIndex), x, cal);
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setTimestamp(final int parameterIndex, final Timestamp x) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                ((PreparedQuery)this.query).getQueryBindings().setTimestamp(this.getCoreParameterIndex(parameterIndex), x);
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setTimestamp(final int parameterIndex, final Timestamp x, final Calendar cal) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                ((PreparedQuery)this.query).getQueryBindings().setTimestamp(this.getCoreParameterIndex(parameterIndex), x, cal);
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    public void setTimestamp(final int parameterIndex, final Timestamp x, final Calendar targetCalendar, final int fractionalLength) throws SQLException {
        synchronized (this.checkClosed().getConnectionMutex()) {
            ((PreparedQuery)this.query).getQueryBindings().setTimestamp(this.getCoreParameterIndex(parameterIndex), x, targetCalendar, fractionalLength);
        }
    }
    
    @Deprecated
    @Override
    public void setUnicodeStream(final int parameterIndex, final InputStream x, final int length) throws SQLException {
        try {
            this.setBinaryStream(parameterIndex, x, length);
            ((PreparedQuery)this.query).getQueryBindings().getBindValues()[this.getCoreParameterIndex(parameterIndex)].setMysqlType(MysqlType.TEXT);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setURL(final int parameterIndex, final URL arg) throws SQLException {
        try {
            if (arg == null) {
                this.setNull(parameterIndex, MysqlType.VARCHAR);
            }
            else {
                this.setString(parameterIndex, arg.toString());
                ((PreparedQuery)this.query).getQueryBindings().getBindValues()[this.getCoreParameterIndex(parameterIndex)].setMysqlType(MysqlType.VARCHAR);
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    class EmulatedPreparedStatementBindings implements ParameterBindings
    {
        private ResultSetImpl bindingsAsRs;
        private ClientPreparedQueryBindValue[] bindValues;
        
        EmulatedPreparedStatementBindings() throws SQLException {
            final List<Row> rows = new ArrayList<Row>();
            final int paramCount = ((PreparedQuery)ClientPreparedStatement.this.query).getParameterCount();
            this.bindValues = new ClientPreparedQueryBindValue[paramCount];
            for (int i = 0; i < paramCount; ++i) {
                this.bindValues[i] = ((PreparedQuery)ClientPreparedStatement.this.query).getQueryBindings().getBindValues()[i].clone();
            }
            final byte[][] rowData = new byte[paramCount][];
            final Field[] typeMetadata = new Field[paramCount];
            for (int j = 0; j < paramCount; ++j) {
                final int batchCommandIndex = ((PreparedQuery)ClientPreparedStatement.this.query).getBatchCommandIndex();
                rowData[j] = ((batchCommandIndex == -1) ? ClientPreparedStatement.this.getBytesRepresentation(j) : ClientPreparedStatement.this.getBytesRepresentationForBatch(j, batchCommandIndex));
                int charsetIndex = 0;
                switch (((PreparedQuery)ClientPreparedStatement.this.query).getQueryBindings().getBindValues()[j].getMysqlType()) {
                    case BINARY:
                    case BLOB:
                    case GEOMETRY:
                    case LONGBLOB:
                    case MEDIUMBLOB:
                    case TINYBLOB:
                    case UNKNOWN:
                    case VARBINARY: {
                        charsetIndex = 63;
                        break;
                    }
                    default: {
                        try {
                            charsetIndex = CharsetMapping.getCollationIndexForJavaEncoding(ClientPreparedStatement.this.session.getPropertySet().getStringProperty(PropertyKey.characterEncoding).getValue(), ClientPreparedStatement.this.session.getServerSession().getServerVersion());
                        }
                        catch (RuntimeException ex) {
                            throw SQLError.createSQLException(ex.toString(), "S1009", ex, null);
                        }
                        break;
                    }
                }
                final Field parameterMetadata = new Field(null, "parameter_" + (j + 1), charsetIndex, ClientPreparedStatement.this.charEncoding, ((PreparedQuery)ClientPreparedStatement.this.query).getQueryBindings().getBindValues()[j].getMysqlType(), rowData[j].length);
                typeMetadata[j] = parameterMetadata;
            }
            rows.add(new ByteArrayRow(rowData, ClientPreparedStatement.this.exceptionInterceptor));
            (this.bindingsAsRs = ClientPreparedStatement.this.resultSetFactory.createFromResultsetRows(1007, 1004, new ResultsetRowsStatic(rows, new DefaultColumnDefinition(typeMetadata)))).next();
        }
        
        @Override
        public Array getArray(final int parameterIndex) throws SQLException {
            return this.bindingsAsRs.getArray(parameterIndex);
        }
        
        @Override
        public InputStream getAsciiStream(final int parameterIndex) throws SQLException {
            return this.bindingsAsRs.getAsciiStream(parameterIndex);
        }
        
        @Override
        public BigDecimal getBigDecimal(final int parameterIndex) throws SQLException {
            return this.bindingsAsRs.getBigDecimal(parameterIndex);
        }
        
        @Override
        public InputStream getBinaryStream(final int parameterIndex) throws SQLException {
            return this.bindingsAsRs.getBinaryStream(parameterIndex);
        }
        
        @Override
        public Blob getBlob(final int parameterIndex) throws SQLException {
            return this.bindingsAsRs.getBlob(parameterIndex);
        }
        
        @Override
        public boolean getBoolean(final int parameterIndex) throws SQLException {
            return this.bindingsAsRs.getBoolean(parameterIndex);
        }
        
        @Override
        public byte getByte(final int parameterIndex) throws SQLException {
            return this.bindingsAsRs.getByte(parameterIndex);
        }
        
        @Override
        public byte[] getBytes(final int parameterIndex) throws SQLException {
            return this.bindingsAsRs.getBytes(parameterIndex);
        }
        
        @Override
        public Reader getCharacterStream(final int parameterIndex) throws SQLException {
            return this.bindingsAsRs.getCharacterStream(parameterIndex);
        }
        
        @Override
        public Clob getClob(final int parameterIndex) throws SQLException {
            return this.bindingsAsRs.getClob(parameterIndex);
        }
        
        @Override
        public Date getDate(final int parameterIndex) throws SQLException {
            return this.bindingsAsRs.getDate(parameterIndex);
        }
        
        @Override
        public double getDouble(final int parameterIndex) throws SQLException {
            return this.bindingsAsRs.getDouble(parameterIndex);
        }
        
        @Override
        public float getFloat(final int parameterIndex) throws SQLException {
            return this.bindingsAsRs.getFloat(parameterIndex);
        }
        
        @Override
        public int getInt(final int parameterIndex) throws SQLException {
            return this.bindingsAsRs.getInt(parameterIndex);
        }
        
        @Override
        public BigInteger getBigInteger(final int parameterIndex) throws SQLException {
            return this.bindingsAsRs.getBigInteger(parameterIndex);
        }
        
        @Override
        public long getLong(final int parameterIndex) throws SQLException {
            return this.bindingsAsRs.getLong(parameterIndex);
        }
        
        @Override
        public Reader getNCharacterStream(final int parameterIndex) throws SQLException {
            return this.bindingsAsRs.getCharacterStream(parameterIndex);
        }
        
        @Override
        public Reader getNClob(final int parameterIndex) throws SQLException {
            return this.bindingsAsRs.getCharacterStream(parameterIndex);
        }
        
        @Override
        public Object getObject(final int parameterIndex) throws SQLException {
            ClientPreparedStatement.this.checkBounds(parameterIndex, 0);
            if (this.bindValues[parameterIndex - 1].isNull()) {
                return null;
            }
            switch (((PreparedQuery)ClientPreparedStatement.this.query).getQueryBindings().getBindValues()[parameterIndex - 1].getMysqlType()) {
                case TINYINT:
                case TINYINT_UNSIGNED: {
                    return this.getByte(parameterIndex);
                }
                case SMALLINT:
                case SMALLINT_UNSIGNED: {
                    return this.getShort(parameterIndex);
                }
                case INT:
                case INT_UNSIGNED: {
                    return this.getInt(parameterIndex);
                }
                case BIGINT: {
                    return this.getLong(parameterIndex);
                }
                case BIGINT_UNSIGNED: {
                    return this.getBigInteger(parameterIndex);
                }
                case FLOAT:
                case FLOAT_UNSIGNED: {
                    return this.getFloat(parameterIndex);
                }
                case DOUBLE:
                case DOUBLE_UNSIGNED: {
                    return this.getDouble(parameterIndex);
                }
                default: {
                    return this.bindingsAsRs.getObject(parameterIndex);
                }
            }
        }
        
        @Override
        public Ref getRef(final int parameterIndex) throws SQLException {
            return this.bindingsAsRs.getRef(parameterIndex);
        }
        
        @Override
        public short getShort(final int parameterIndex) throws SQLException {
            return this.bindingsAsRs.getShort(parameterIndex);
        }
        
        @Override
        public String getString(final int parameterIndex) throws SQLException {
            return this.bindingsAsRs.getString(parameterIndex);
        }
        
        @Override
        public Time getTime(final int parameterIndex) throws SQLException {
            return this.bindingsAsRs.getTime(parameterIndex);
        }
        
        @Override
        public Timestamp getTimestamp(final int parameterIndex) throws SQLException {
            return this.bindingsAsRs.getTimestamp(parameterIndex);
        }
        
        @Override
        public URL getURL(final int parameterIndex) throws SQLException {
            return this.bindingsAsRs.getURL(parameterIndex);
        }
        
        @Override
        public boolean isNull(final int parameterIndex) throws SQLException {
            ClientPreparedStatement.this.checkBounds(parameterIndex, 0);
            return this.bindValues[parameterIndex - 1].isNull();
        }
    }
}
