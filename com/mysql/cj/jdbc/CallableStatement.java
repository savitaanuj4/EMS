
package com.mysql.cj.jdbc;

import java.util.HashMap;
import java.sql.SQLXML;
import java.sql.NClob;
import java.sql.RowId;
import java.sql.PreparedStatement;
import java.io.Reader;
import java.io.InputStream;
import java.sql.Statement;
import java.util.Iterator;
import java.sql.SQLType;
import com.mysql.cj.exceptions.FeatureNotAvailableException;
import java.sql.JDBCType;
import java.net.URL;
import java.sql.Timestamp;
import java.sql.Time;
import java.sql.Ref;
import java.sql.ParameterMetaData;
import com.mysql.cj.jdbc.result.ResultSetImpl;
import java.util.Map;
import java.util.Calendar;
import java.sql.Date;
import java.sql.Clob;
import java.sql.Blob;
import java.math.BigDecimal;
import java.sql.Array;
import com.mysql.cj.util.Util;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import com.mysql.cj.protocol.ResultsetRows;
import com.mysql.cj.protocol.ColumnDefinition;
import com.mysql.cj.protocol.a.result.ResultsetRowsStatic;
import com.mysql.cj.result.DefaultColumnDefinition;
import com.mysql.cj.protocol.a.result.ByteArrayRow;
import com.mysql.cj.result.Row;
import java.util.ArrayList;
import com.mysql.cj.result.Field;
import com.mysql.cj.jdbc.exceptions.SQLError;
import com.mysql.cj.Messages;
import com.mysql.cj.MysqlType;
import java.util.List;
import com.mysql.cj.exceptions.CJException;
import com.mysql.cj.jdbc.exceptions.SQLExceptionsMapping;
import com.mysql.cj.util.StringUtils;
import java.sql.SQLException;
import com.mysql.cj.conf.PropertyKey;
import com.mysql.cj.PreparedQuery;
import com.mysql.cj.jdbc.result.ResultSetInternalMethods;

public class CallableStatement extends ClientPreparedStatement implements java.sql.CallableStatement
{
    private static final int NOT_OUTPUT_PARAMETER_INDICATOR = Integer.MIN_VALUE;
    private static final String PARAMETER_NAMESPACE_PREFIX = "@com_mysql_jdbc_outparam_";
    private boolean callingStoredFunction;
    private ResultSetInternalMethods functionReturnValueResults;
    private boolean hasOutputParams;
    private ResultSetInternalMethods outputParameterResults;
    protected boolean outputParamWasNull;
    private int[] parameterIndexToRsIndex;
    protected CallableStatementParamInfo paramInfo;
    private CallableStatementParam returnValueParam;
    private boolean noAccessToProcedureBodies;
    private int[] placeholderToParameterIndexMap;
    
    private static String mangleParameterName(final String origParameterName) {
        if (origParameterName == null) {
            return null;
        }
        int offset = 0;
        if (origParameterName.length() > 0 && origParameterName.charAt(0) == '@') {
            offset = 1;
        }
        final StringBuilder paramNameBuf = new StringBuilder("@com_mysql_jdbc_outparam_".length() + origParameterName.length());
        paramNameBuf.append("@com_mysql_jdbc_outparam_");
        paramNameBuf.append(origParameterName.substring(offset));
        return paramNameBuf.toString();
    }
    
    public CallableStatement(final JdbcConnection conn, final CallableStatementParamInfo paramInfo) throws SQLException {
        super(conn, paramInfo.nativeSql, paramInfo.catalogInUse);
        this.callingStoredFunction = false;
        this.hasOutputParams = false;
        this.outputParamWasNull = false;
        this.paramInfo = paramInfo;
        this.callingStoredFunction = this.paramInfo.isFunctionCall;
        if (this.callingStoredFunction) {
            ((PreparedQuery)this.query).setParameterCount(((PreparedQuery)this.query).getParameterCount() + 1);
        }
        this.retrieveGeneratedKeys = true;
        this.noAccessToProcedureBodies = conn.getPropertySet().getBooleanProperty(PropertyKey.noAccessToProcedureBodies).getValue();
    }
    
    protected static CallableStatement getInstance(final JdbcConnection conn, final String sql, final String catalog, final boolean isFunctionCall) throws SQLException {
        return new CallableStatement(conn, sql, catalog, isFunctionCall);
    }
    
    protected static CallableStatement getInstance(final JdbcConnection conn, final CallableStatementParamInfo paramInfo) throws SQLException {
        return new CallableStatement(conn, paramInfo);
    }
    
    private void generateParameterMap() throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                if (this.paramInfo == null) {
                    return;
                }
                int parameterCountFromMetaData = this.paramInfo.getParameterCount();
                if (this.callingStoredFunction) {
                    --parameterCountFromMetaData;
                }
                final PreparedQuery<?> q = (PreparedQuery<?>)this.query;
                if (this.paramInfo != null && q.getParameterCount() != parameterCountFromMetaData) {
                    this.placeholderToParameterIndexMap = new int[q.getParameterCount()];
                    final int startPos = this.callingStoredFunction ? StringUtils.indexOfIgnoreCase(q.getOriginalSql(), "SELECT") : StringUtils.indexOfIgnoreCase(q.getOriginalSql(), "CALL");
                    if (startPos != -1) {
                        final int parenOpenPos = q.getOriginalSql().indexOf(40, startPos + 4);
                        if (parenOpenPos != -1) {
                            final int parenClosePos = StringUtils.indexOfIgnoreCase(parenOpenPos, q.getOriginalSql(), ")", "'", "'", StringUtils.SEARCH_MODE__ALL);
                            if (parenClosePos != -1) {
                                final List<?> parsedParameters = StringUtils.split(q.getOriginalSql().substring(parenOpenPos + 1, parenClosePos), ",", "'\"", "'\"", true);
                                final int numParsedParameters = parsedParameters.size();
                                if (numParsedParameters != q.getParameterCount()) {}
                                int placeholderCount = 0;
                                for (int i = 0; i < numParsedParameters; ++i) {
                                    if (((String)parsedParameters.get(i)).equals("?")) {
                                        this.placeholderToParameterIndexMap[placeholderCount++] = i;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    public CallableStatement(final JdbcConnection conn, final String sql, final String catalog, final boolean isFunctionCall) throws SQLException {
        super(conn, sql, catalog);
        this.callingStoredFunction = false;
        this.hasOutputParams = false;
        this.outputParamWasNull = false;
        if (!(this.callingStoredFunction = isFunctionCall)) {
            if (!StringUtils.startsWithIgnoreCaseAndWs(sql, "CALL")) {
                this.fakeParameterTypes(false);
            }
            else {
                this.determineParameterTypes();
            }
            this.generateParameterMap();
        }
        else {
            this.determineParameterTypes();
            this.generateParameterMap();
            ((PreparedQuery)this.query).setParameterCount(((PreparedQuery)this.query).getParameterCount() + 1);
        }
        this.retrieveGeneratedKeys = true;
        this.noAccessToProcedureBodies = conn.getPropertySet().getBooleanProperty(PropertyKey.noAccessToProcedureBodies).getValue();
    }
    
    @Override
    public void addBatch() throws SQLException {
        try {
            this.setOutParams();
            super.addBatch();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    private CallableStatementParam checkIsOutputParam(int paramIndex) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                if (this.callingStoredFunction) {
                    if (paramIndex == 1) {
                        if (this.returnValueParam == null) {
                            this.returnValueParam = new CallableStatementParam("", 0, false, true, MysqlType.VARCHAR.getJdbcType(), "VARCHAR", 0, 0, (short)2, 5);
                        }
                        return this.returnValueParam;
                    }
                    --paramIndex;
                }
                this.checkParameterIndexBounds(paramIndex);
                int localParamIndex = paramIndex - 1;
                if (this.placeholderToParameterIndexMap != null) {
                    localParamIndex = this.placeholderToParameterIndexMap[localParamIndex];
                }
                final CallableStatementParam paramDescriptor = this.paramInfo.getParameter(localParamIndex);
                if (this.noAccessToProcedureBodies) {
                    paramDescriptor.isOut = true;
                    paramDescriptor.isIn = true;
                    paramDescriptor.inOutModifier = 2;
                }
                else if (!paramDescriptor.isOut) {
                    throw SQLError.createSQLException(Messages.getString("CallableStatement.9", new Object[] { paramIndex }), "S1009", this.getExceptionInterceptor());
                }
                this.hasOutputParams = true;
                return paramDescriptor;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    private void checkParameterIndexBounds(final int paramIndex) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                this.paramInfo.checkBounds(paramIndex);
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    private void checkStreamability() throws SQLException {
        if (this.hasOutputParams && this.createStreamingResultSet()) {
            throw SQLError.createSQLException(Messages.getString("CallableStatement.14"), "S1C00", this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void clearParameters() throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                super.clearParameters();
                try {
                    if (this.outputParameterResults != null) {
                        this.outputParameterResults.close();
                    }
                }
                finally {
                    this.outputParameterResults = null;
                }
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    private void fakeParameterTypes(final boolean isReallyProcedure) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                final String encoding = this.connection.getSession().getServerSession().getCharacterSetMetadata();
                final int collationIndex = this.connection.getSession().getServerSession().getMetadataCollationIndex();
                final Field[] fields = { new Field("", "PROCEDURE_CAT", collationIndex, encoding, MysqlType.CHAR, 0), new Field("", "PROCEDURE_SCHEM", collationIndex, encoding, MysqlType.CHAR, 0), new Field("", "PROCEDURE_NAME", collationIndex, encoding, MysqlType.CHAR, 0), new Field("", "COLUMN_NAME", collationIndex, encoding, MysqlType.CHAR, 0), new Field("", "COLUMN_TYPE", collationIndex, encoding, MysqlType.CHAR, 0), new Field("", "DATA_TYPE", collationIndex, encoding, MysqlType.SMALLINT, 0), new Field("", "TYPE_NAME", collationIndex, encoding, MysqlType.CHAR, 0), new Field("", "PRECISION", collationIndex, encoding, MysqlType.INT, 0), new Field("", "LENGTH", collationIndex, encoding, MysqlType.INT, 0), new Field("", "SCALE", collationIndex, encoding, MysqlType.SMALLINT, 0), new Field("", "RADIX", collationIndex, encoding, MysqlType.SMALLINT, 0), new Field("", "NULLABLE", collationIndex, encoding, MysqlType.SMALLINT, 0), new Field("", "REMARKS", collationIndex, encoding, MysqlType.CHAR, 0) };
                final String procName = isReallyProcedure ? this.extractProcedureName() : null;
                byte[] procNameAsBytes = null;
                procNameAsBytes = (byte[])((procName == null) ? null : StringUtils.getBytes(procName, "UTF-8"));
                final ArrayList<Row> resultRows = new ArrayList<Row>();
                for (int i = 0; i < ((PreparedQuery)this.query).getParameterCount(); ++i) {
                    final byte[][] row = { null, null, procNameAsBytes, this.s2b(String.valueOf(i)), this.s2b(String.valueOf(1)), this.s2b(String.valueOf(MysqlType.VARCHAR.getJdbcType())), this.s2b(MysqlType.VARCHAR.getName()), this.s2b(Integer.toString(65535)), this.s2b(Integer.toString(65535)), this.s2b(Integer.toString(0)), this.s2b(Integer.toString(10)), this.s2b(Integer.toString(2)), null };
                    resultRows.add(new ByteArrayRow(row, this.getExceptionInterceptor()));
                }
                final ResultSet paramTypesRs = this.resultSetFactory.createFromResultsetRows(1007, 1004, new ResultsetRowsStatic(resultRows, new DefaultColumnDefinition(fields)));
                this.convertGetProcedureColumnsToInternalDescriptors(paramTypesRs);
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    private void determineParameterTypes() throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                ResultSet paramTypesRs = null;
                try {
                    String procName = this.extractProcedureName();
                    final String quotedId = this.session.getIdentifierQuoteString();
                    final List<?> parseList = StringUtils.splitDBdotName(procName, "", quotedId, this.session.getServerSession().isNoBackslashEscapesSet());
                    String tmpCatalog = "";
                    if (parseList.size() == 2) {
                        tmpCatalog = (String)parseList.get(0);
                        procName = (String)parseList.get(1);
                    }
                    final DatabaseMetaData dbmd = this.connection.getMetaData();
                    boolean useCatalog = false;
                    if (tmpCatalog.length() <= 0) {
                        useCatalog = true;
                    }
                    paramTypesRs = dbmd.getProcedureColumns(useCatalog ? this.getCurrentCatalog() : tmpCatalog, null, procName, "%");
                    boolean hasResults = false;
                    try {
                        if (paramTypesRs.next()) {
                            paramTypesRs.previous();
                            hasResults = true;
                        }
                    }
                    catch (Exception ex2) {}
                    if (hasResults) {
                        this.convertGetProcedureColumnsToInternalDescriptors(paramTypesRs);
                    }
                    else {
                        this.fakeParameterTypes(true);
                    }
                }
                finally {
                    SQLException sqlExRethrow = null;
                    if (paramTypesRs != null) {
                        try {
                            paramTypesRs.close();
                        }
                        catch (SQLException sqlEx) {
                            sqlExRethrow = sqlEx;
                        }
                        paramTypesRs = null;
                    }
                    if (sqlExRethrow != null) {
                        throw sqlExRethrow;
                    }
                }
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    private void convertGetProcedureColumnsToInternalDescriptors(final ResultSet paramTypesRs) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                this.paramInfo = new CallableStatementParamInfo(paramTypesRs);
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
                boolean returnVal = false;
                this.checkStreamability();
                this.setInOutParamsOnServer();
                this.setOutParams();
                returnVal = super.execute();
                if (this.callingStoredFunction) {
                    (this.functionReturnValueResults = this.results).next();
                    this.results = null;
                }
                this.retrieveOutParams();
                return !this.callingStoredFunction && returnVal;
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
                this.checkStreamability();
                ResultSet execResults = null;
                this.setInOutParamsOnServer();
                this.setOutParams();
                execResults = super.executeQuery();
                this.retrieveOutParams();
                return execResults;
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
    
    private String extractProcedureName() throws SQLException {
        final String sanitizedSql = StringUtils.stripComments(((PreparedQuery)this.query).getOriginalSql(), "`\"'", "`\"'", true, false, true, true);
        int endCallIndex = StringUtils.indexOfIgnoreCase(sanitizedSql, "CALL ");
        int offset = 5;
        if (endCallIndex == -1) {
            endCallIndex = StringUtils.indexOfIgnoreCase(sanitizedSql, "SELECT ");
            offset = 7;
        }
        if (endCallIndex != -1) {
            final StringBuilder nameBuf = new StringBuilder();
            final String trimmedStatement = sanitizedSql.substring(endCallIndex + offset).trim();
            for (int statementLength = trimmedStatement.length(), i = 0; i < statementLength; ++i) {
                final char c = trimmedStatement.charAt(i);
                if (Character.isWhitespace(c) || c == '(') {
                    break;
                }
                if (c == '?') {
                    break;
                }
                nameBuf.append(c);
            }
            return nameBuf.toString();
        }
        throw SQLError.createSQLException(Messages.getString("CallableStatement.1"), "S1000", this.getExceptionInterceptor());
    }
    
    protected String fixParameterName(String paramNameIn) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                if (paramNameIn == null) {
                    paramNameIn = "nullpn";
                }
                if (this.noAccessToProcedureBodies) {
                    throw SQLError.createSQLException(Messages.getString("CallableStatement.23"), "S1009", this.getExceptionInterceptor());
                }
                return mangleParameterName(paramNameIn);
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public Array getArray(final int i) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                final ResultSetInternalMethods rs = this.getOutputParameters(i);
                final Array retValue = rs.getArray(this.mapOutputParameterIndexToRsIndex(i));
                this.outputParamWasNull = rs.wasNull();
                return retValue;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public Array getArray(final String parameterName) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                final ResultSetInternalMethods rs = this.getOutputParameters(0);
                final Array retValue = rs.getArray(this.fixParameterName(parameterName));
                this.outputParamWasNull = rs.wasNull();
                return retValue;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public BigDecimal getBigDecimal(final int parameterIndex) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                final ResultSetInternalMethods rs = this.getOutputParameters(parameterIndex);
                final BigDecimal retValue = rs.getBigDecimal(this.mapOutputParameterIndexToRsIndex(parameterIndex));
                this.outputParamWasNull = rs.wasNull();
                return retValue;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Deprecated
    @Override
    public BigDecimal getBigDecimal(final int parameterIndex, final int scale) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                final ResultSetInternalMethods rs = this.getOutputParameters(parameterIndex);
                final BigDecimal retValue = rs.getBigDecimal(this.mapOutputParameterIndexToRsIndex(parameterIndex), scale);
                this.outputParamWasNull = rs.wasNull();
                return retValue;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public BigDecimal getBigDecimal(final String parameterName) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                final ResultSetInternalMethods rs = this.getOutputParameters(0);
                final BigDecimal retValue = rs.getBigDecimal(this.fixParameterName(parameterName));
                this.outputParamWasNull = rs.wasNull();
                return retValue;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public Blob getBlob(final int parameterIndex) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                final ResultSetInternalMethods rs = this.getOutputParameters(parameterIndex);
                final Blob retValue = rs.getBlob(this.mapOutputParameterIndexToRsIndex(parameterIndex));
                this.outputParamWasNull = rs.wasNull();
                return retValue;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public Blob getBlob(final String parameterName) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                final ResultSetInternalMethods rs = this.getOutputParameters(0);
                final Blob retValue = rs.getBlob(this.fixParameterName(parameterName));
                this.outputParamWasNull = rs.wasNull();
                return retValue;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean getBoolean(final int parameterIndex) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                final ResultSetInternalMethods rs = this.getOutputParameters(parameterIndex);
                final boolean retValue = rs.getBoolean(this.mapOutputParameterIndexToRsIndex(parameterIndex));
                this.outputParamWasNull = rs.wasNull();
                return retValue;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean getBoolean(final String parameterName) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                final ResultSetInternalMethods rs = this.getOutputParameters(0);
                final boolean retValue = rs.getBoolean(this.fixParameterName(parameterName));
                this.outputParamWasNull = rs.wasNull();
                return retValue;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public byte getByte(final int parameterIndex) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                final ResultSetInternalMethods rs = this.getOutputParameters(parameterIndex);
                final byte retValue = rs.getByte(this.mapOutputParameterIndexToRsIndex(parameterIndex));
                this.outputParamWasNull = rs.wasNull();
                return retValue;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public byte getByte(final String parameterName) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                final ResultSetInternalMethods rs = this.getOutputParameters(0);
                final byte retValue = rs.getByte(this.fixParameterName(parameterName));
                this.outputParamWasNull = rs.wasNull();
                return retValue;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public byte[] getBytes(final int parameterIndex) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                final ResultSetInternalMethods rs = this.getOutputParameters(parameterIndex);
                final byte[] retValue = rs.getBytes(this.mapOutputParameterIndexToRsIndex(parameterIndex));
                this.outputParamWasNull = rs.wasNull();
                return retValue;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public byte[] getBytes(final String parameterName) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                final ResultSetInternalMethods rs = this.getOutputParameters(0);
                final byte[] retValue = rs.getBytes(this.fixParameterName(parameterName));
                this.outputParamWasNull = rs.wasNull();
                return retValue;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public Clob getClob(final int parameterIndex) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                final ResultSetInternalMethods rs = this.getOutputParameters(parameterIndex);
                final Clob retValue = rs.getClob(this.mapOutputParameterIndexToRsIndex(parameterIndex));
                this.outputParamWasNull = rs.wasNull();
                return retValue;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public Clob getClob(final String parameterName) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                final ResultSetInternalMethods rs = this.getOutputParameters(0);
                final Clob retValue = rs.getClob(this.fixParameterName(parameterName));
                this.outputParamWasNull = rs.wasNull();
                return retValue;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public Date getDate(final int parameterIndex) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                final ResultSetInternalMethods rs = this.getOutputParameters(parameterIndex);
                final Date retValue = rs.getDate(this.mapOutputParameterIndexToRsIndex(parameterIndex));
                this.outputParamWasNull = rs.wasNull();
                return retValue;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public Date getDate(final int parameterIndex, final Calendar cal) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                final ResultSetInternalMethods rs = this.getOutputParameters(parameterIndex);
                final Date retValue = rs.getDate(this.mapOutputParameterIndexToRsIndex(parameterIndex), cal);
                this.outputParamWasNull = rs.wasNull();
                return retValue;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public Date getDate(final String parameterName) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                final ResultSetInternalMethods rs = this.getOutputParameters(0);
                final Date retValue = rs.getDate(this.fixParameterName(parameterName));
                this.outputParamWasNull = rs.wasNull();
                return retValue;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public Date getDate(final String parameterName, final Calendar cal) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                final ResultSetInternalMethods rs = this.getOutputParameters(0);
                final Date retValue = rs.getDate(this.fixParameterName(parameterName), cal);
                this.outputParamWasNull = rs.wasNull();
                return retValue;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public double getDouble(final int parameterIndex) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                final ResultSetInternalMethods rs = this.getOutputParameters(parameterIndex);
                final double retValue = rs.getDouble(this.mapOutputParameterIndexToRsIndex(parameterIndex));
                this.outputParamWasNull = rs.wasNull();
                return retValue;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public double getDouble(final String parameterName) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                final ResultSetInternalMethods rs = this.getOutputParameters(0);
                final double retValue = rs.getDouble(this.fixParameterName(parameterName));
                this.outputParamWasNull = rs.wasNull();
                return retValue;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public float getFloat(final int parameterIndex) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                final ResultSetInternalMethods rs = this.getOutputParameters(parameterIndex);
                final float retValue = rs.getFloat(this.mapOutputParameterIndexToRsIndex(parameterIndex));
                this.outputParamWasNull = rs.wasNull();
                return retValue;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public float getFloat(final String parameterName) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                final ResultSetInternalMethods rs = this.getOutputParameters(0);
                final float retValue = rs.getFloat(this.fixParameterName(parameterName));
                this.outputParamWasNull = rs.wasNull();
                return retValue;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public int getInt(final int parameterIndex) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                final ResultSetInternalMethods rs = this.getOutputParameters(parameterIndex);
                final int retValue = rs.getInt(this.mapOutputParameterIndexToRsIndex(parameterIndex));
                this.outputParamWasNull = rs.wasNull();
                return retValue;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public int getInt(final String parameterName) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                final ResultSetInternalMethods rs = this.getOutputParameters(0);
                final int retValue = rs.getInt(this.fixParameterName(parameterName));
                this.outputParamWasNull = rs.wasNull();
                return retValue;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public long getLong(final int parameterIndex) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                final ResultSetInternalMethods rs = this.getOutputParameters(parameterIndex);
                final long retValue = rs.getLong(this.mapOutputParameterIndexToRsIndex(parameterIndex));
                this.outputParamWasNull = rs.wasNull();
                return retValue;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public long getLong(final String parameterName) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                final ResultSetInternalMethods rs = this.getOutputParameters(0);
                final long retValue = rs.getLong(this.fixParameterName(parameterName));
                this.outputParamWasNull = rs.wasNull();
                return retValue;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    protected int getNamedParamIndex(final String paramName, final boolean forOut) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                if (this.noAccessToProcedureBodies) {
                    throw SQLError.createSQLException("No access to parameters by name when connection has been configured not to access procedure bodies", "S1009", this.getExceptionInterceptor());
                }
                if (paramName == null || paramName.length() == 0) {
                    throw SQLError.createSQLException(Messages.getString("CallableStatement.2"), "S1009", this.getExceptionInterceptor());
                }
                final CallableStatementParam namedParamInfo;
                if (this.paramInfo == null || (namedParamInfo = this.paramInfo.getParameter(paramName)) == null) {
                    throw SQLError.createSQLException(Messages.getString("CallableStatement.3", new Object[] { paramName }), "S1009", this.getExceptionInterceptor());
                }
                if (forOut && !namedParamInfo.isOut) {
                    throw SQLError.createSQLException(Messages.getString("CallableStatement.5", new Object[] { paramName }), "S1009", this.getExceptionInterceptor());
                }
                if (this.placeholderToParameterIndexMap == null) {
                    return namedParamInfo.index + 1;
                }
                for (int i = 0; i < this.placeholderToParameterIndexMap.length; ++i) {
                    if (this.placeholderToParameterIndexMap[i] == namedParamInfo.index) {
                        return i + 1;
                    }
                }
                throw SQLError.createSQLException(Messages.getString("CallableStatement.6", new Object[] { paramName }), "S1009", this.getExceptionInterceptor());
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public Object getObject(final int parameterIndex) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                final CallableStatementParam paramDescriptor = this.checkIsOutputParam(parameterIndex);
                final ResultSetInternalMethods rs = this.getOutputParameters(parameterIndex);
                final Object retVal = rs.getObjectStoredProc(this.mapOutputParameterIndexToRsIndex(parameterIndex), paramDescriptor.desiredMysqlType.getJdbcType());
                this.outputParamWasNull = rs.wasNull();
                return retVal;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public Object getObject(final int parameterIndex, final Map<String, Class<?>> map) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                final ResultSetInternalMethods rs = this.getOutputParameters(parameterIndex);
                final Object retVal = rs.getObject(this.mapOutputParameterIndexToRsIndex(parameterIndex), map);
                this.outputParamWasNull = rs.wasNull();
                return retVal;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public Object getObject(final String parameterName) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                final ResultSetInternalMethods rs = this.getOutputParameters(0);
                final Object retValue = rs.getObject(this.fixParameterName(parameterName));
                this.outputParamWasNull = rs.wasNull();
                return retValue;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public Object getObject(final String parameterName, final Map<String, Class<?>> map) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                final ResultSetInternalMethods rs = this.getOutputParameters(0);
                final Object retValue = rs.getObject(this.fixParameterName(parameterName), map);
                this.outputParamWasNull = rs.wasNull();
                return retValue;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public <T> T getObject(final int parameterIndex, final Class<T> type) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                final ResultSetInternalMethods rs = this.getOutputParameters(parameterIndex);
                final T retVal = ((ResultSetImpl)rs).getObject(this.mapOutputParameterIndexToRsIndex(parameterIndex), type);
                this.outputParamWasNull = rs.wasNull();
                return retVal;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public <T> T getObject(final String parameterName, final Class<T> type) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                final ResultSetInternalMethods rs = this.getOutputParameters(0);
                final T retValue = ((ResultSetImpl)rs).getObject(this.fixParameterName(parameterName), type);
                this.outputParamWasNull = rs.wasNull();
                return retValue;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    protected ResultSetInternalMethods getOutputParameters(final int paramIndex) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                this.outputParamWasNull = false;
                if (paramIndex == 1 && this.callingStoredFunction && this.returnValueParam != null) {
                    return this.functionReturnValueResults;
                }
                if (this.outputParameterResults != null) {
                    return this.outputParameterResults;
                }
                if (this.paramInfo.numberOfParameters() == 0) {
                    throw SQLError.createSQLException(Messages.getString("CallableStatement.7"), "S1009", this.getExceptionInterceptor());
                }
                throw SQLError.createSQLException(Messages.getString("CallableStatement.8"), "S1000", this.getExceptionInterceptor());
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
                if (this.placeholderToParameterIndexMap == null) {
                    return this.paramInfo;
                }
                return new CallableStatementParamInfo(this.paramInfo);
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public Ref getRef(final int parameterIndex) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                final ResultSetInternalMethods rs = this.getOutputParameters(parameterIndex);
                final Ref retValue = rs.getRef(this.mapOutputParameterIndexToRsIndex(parameterIndex));
                this.outputParamWasNull = rs.wasNull();
                return retValue;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public Ref getRef(final String parameterName) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                final ResultSetInternalMethods rs = this.getOutputParameters(0);
                final Ref retValue = rs.getRef(this.fixParameterName(parameterName));
                this.outputParamWasNull = rs.wasNull();
                return retValue;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public short getShort(final int parameterIndex) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                final ResultSetInternalMethods rs = this.getOutputParameters(parameterIndex);
                final short retValue = rs.getShort(this.mapOutputParameterIndexToRsIndex(parameterIndex));
                this.outputParamWasNull = rs.wasNull();
                return retValue;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public short getShort(final String parameterName) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                final ResultSetInternalMethods rs = this.getOutputParameters(0);
                final short retValue = rs.getShort(this.fixParameterName(parameterName));
                this.outputParamWasNull = rs.wasNull();
                return retValue;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public String getString(final int parameterIndex) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                final ResultSetInternalMethods rs = this.getOutputParameters(parameterIndex);
                final String retValue = rs.getString(this.mapOutputParameterIndexToRsIndex(parameterIndex));
                this.outputParamWasNull = rs.wasNull();
                return retValue;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public String getString(final String parameterName) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                final ResultSetInternalMethods rs = this.getOutputParameters(0);
                final String retValue = rs.getString(this.fixParameterName(parameterName));
                this.outputParamWasNull = rs.wasNull();
                return retValue;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public Time getTime(final int parameterIndex) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                final ResultSetInternalMethods rs = this.getOutputParameters(parameterIndex);
                final Time retValue = rs.getTime(this.mapOutputParameterIndexToRsIndex(parameterIndex));
                this.outputParamWasNull = rs.wasNull();
                return retValue;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public Time getTime(final int parameterIndex, final Calendar cal) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                final ResultSetInternalMethods rs = this.getOutputParameters(parameterIndex);
                final Time retValue = rs.getTime(this.mapOutputParameterIndexToRsIndex(parameterIndex), cal);
                this.outputParamWasNull = rs.wasNull();
                return retValue;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public Time getTime(final String parameterName) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                final ResultSetInternalMethods rs = this.getOutputParameters(0);
                final Time retValue = rs.getTime(this.fixParameterName(parameterName));
                this.outputParamWasNull = rs.wasNull();
                return retValue;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public Time getTime(final String parameterName, final Calendar cal) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                final ResultSetInternalMethods rs = this.getOutputParameters(0);
                final Time retValue = rs.getTime(this.fixParameterName(parameterName), cal);
                this.outputParamWasNull = rs.wasNull();
                return retValue;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public Timestamp getTimestamp(final int parameterIndex) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                final ResultSetInternalMethods rs = this.getOutputParameters(parameterIndex);
                final Timestamp retValue = rs.getTimestamp(this.mapOutputParameterIndexToRsIndex(parameterIndex));
                this.outputParamWasNull = rs.wasNull();
                return retValue;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public Timestamp getTimestamp(final int parameterIndex, final Calendar cal) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                final ResultSetInternalMethods rs = this.getOutputParameters(parameterIndex);
                final Timestamp retValue = rs.getTimestamp(this.mapOutputParameterIndexToRsIndex(parameterIndex), cal);
                this.outputParamWasNull = rs.wasNull();
                return retValue;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public Timestamp getTimestamp(final String parameterName) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                final ResultSetInternalMethods rs = this.getOutputParameters(0);
                final Timestamp retValue = rs.getTimestamp(this.fixParameterName(parameterName));
                this.outputParamWasNull = rs.wasNull();
                return retValue;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public Timestamp getTimestamp(final String parameterName, final Calendar cal) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                final ResultSetInternalMethods rs = this.getOutputParameters(0);
                final Timestamp retValue = rs.getTimestamp(this.fixParameterName(parameterName), cal);
                this.outputParamWasNull = rs.wasNull();
                return retValue;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public URL getURL(final int parameterIndex) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                final ResultSetInternalMethods rs = this.getOutputParameters(parameterIndex);
                final URL retValue = rs.getURL(this.mapOutputParameterIndexToRsIndex(parameterIndex));
                this.outputParamWasNull = rs.wasNull();
                return retValue;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public URL getURL(final String parameterName) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                final ResultSetInternalMethods rs = this.getOutputParameters(0);
                final URL retValue = rs.getURL(this.fixParameterName(parameterName));
                this.outputParamWasNull = rs.wasNull();
                return retValue;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    protected int mapOutputParameterIndexToRsIndex(final int paramIndex) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                if (this.returnValueParam != null && paramIndex == 1) {
                    return 1;
                }
                this.checkParameterIndexBounds(paramIndex);
                int localParamIndex = paramIndex - 1;
                if (this.placeholderToParameterIndexMap != null) {
                    localParamIndex = this.placeholderToParameterIndexMap[localParamIndex];
                }
                final int rsIndex = this.parameterIndexToRsIndex[localParamIndex];
                if (rsIndex == Integer.MIN_VALUE) {
                    throw SQLError.createSQLException(Messages.getString("CallableStatement.21", new Object[] { paramIndex }), "S1009", this.getExceptionInterceptor());
                }
                return rsIndex + 1;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    protected void registerOutParameter(final int parameterIndex, final MysqlType mysqlType) throws SQLException {
        final CallableStatementParam paramDescriptor = this.checkIsOutputParam(parameterIndex);
        paramDescriptor.desiredMysqlType = mysqlType;
    }
    
    @Override
    public void registerOutParameter(final int parameterIndex, final int sqlType) throws SQLException {
        try {
            try {
                final MysqlType mt = MysqlType.getByJdbcType(sqlType);
                this.registerOutParameter(parameterIndex, mt);
            }
            catch (FeatureNotAvailableException nae) {
                throw SQLError.createSQLFeatureNotSupportedException(Messages.getString("Statement.UnsupportedSQLType") + JDBCType.valueOf(sqlType), "S1C00", this.getExceptionInterceptor());
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void registerOutParameter(final int parameterIndex, final SQLType sqlType) throws SQLException {
        try {
            if (sqlType instanceof MysqlType) {
                this.registerOutParameter(parameterIndex, (MysqlType)sqlType);
            }
            else {
                this.registerOutParameter(parameterIndex, sqlType.getVendorTypeNumber());
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    protected void registerOutParameter(final int parameterIndex, final MysqlType mysqlType, final int scale) throws SQLException {
        this.registerOutParameter(parameterIndex, mysqlType);
    }
    
    @Override
    public void registerOutParameter(final int parameterIndex, final int sqlType, final int scale) throws SQLException {
        try {
            this.registerOutParameter(parameterIndex, sqlType);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void registerOutParameter(final int parameterIndex, final SQLType sqlType, final int scale) throws SQLException {
        try {
            if (sqlType instanceof MysqlType) {
                this.registerOutParameter(parameterIndex, (MysqlType)sqlType, scale);
            }
            else {
                this.registerOutParameter(parameterIndex, sqlType.getVendorTypeNumber(), scale);
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    protected void registerOutParameter(final int parameterIndex, final MysqlType mysqlType, final String typeName) throws SQLException {
        this.registerOutParameter(parameterIndex, mysqlType);
    }
    
    @Override
    public void registerOutParameter(final int parameterIndex, final int sqlType, final String typeName) throws SQLException {
        try {
            try {
                final MysqlType mt = MysqlType.getByJdbcType(sqlType);
                this.registerOutParameter(parameterIndex, mt, typeName);
            }
            catch (FeatureNotAvailableException nae) {
                throw SQLError.createSQLFeatureNotSupportedException(Messages.getString("Statement.UnsupportedSQLType") + JDBCType.valueOf(sqlType), "S1C00", this.getExceptionInterceptor());
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void registerOutParameter(final int parameterIndex, final SQLType sqlType, final String typeName) throws SQLException {
        try {
            if (sqlType instanceof MysqlType) {
                this.registerOutParameter(parameterIndex, (MysqlType)sqlType, typeName);
            }
            else {
                this.registerOutParameter(parameterIndex, sqlType.getVendorTypeNumber(), typeName);
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void registerOutParameter(final String parameterName, final int sqlType) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                this.registerOutParameter(this.getNamedParamIndex(parameterName, true), sqlType);
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void registerOutParameter(final String parameterName, final SQLType sqlType) throws SQLException {
        try {
            if (sqlType instanceof MysqlType) {
                this.registerOutParameter(this.getNamedParamIndex(parameterName, true), (MysqlType)sqlType);
            }
            else {
                this.registerOutParameter(this.getNamedParamIndex(parameterName, true), sqlType.getVendorTypeNumber());
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void registerOutParameter(final String parameterName, final int sqlType, final int scale) throws SQLException {
        try {
            this.registerOutParameter(this.getNamedParamIndex(parameterName, true), sqlType, scale);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void registerOutParameter(final String parameterName, final SQLType sqlType, final int scale) throws SQLException {
        try {
            if (sqlType instanceof MysqlType) {
                this.registerOutParameter(this.getNamedParamIndex(parameterName, true), (MysqlType)sqlType, scale);
            }
            else {
                this.registerOutParameter(this.getNamedParamIndex(parameterName, true), sqlType.getVendorTypeNumber(), scale);
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void registerOutParameter(final String parameterName, final int sqlType, final String typeName) throws SQLException {
        try {
            this.registerOutParameter(this.getNamedParamIndex(parameterName, true), sqlType, typeName);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void registerOutParameter(final String parameterName, final SQLType sqlType, final String typeName) throws SQLException {
        try {
            if (sqlType instanceof MysqlType) {
                this.registerOutParameter(this.getNamedParamIndex(parameterName, true), (MysqlType)sqlType, typeName);
            }
            else {
                this.registerOutParameter(parameterName, sqlType.getVendorTypeNumber(), typeName);
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    private void retrieveOutParams() throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                final int numParameters = this.paramInfo.numberOfParameters();
                this.parameterIndexToRsIndex = new int[numParameters];
                for (int i = 0; i < numParameters; ++i) {
                    this.parameterIndexToRsIndex[i] = Integer.MIN_VALUE;
                }
                int localParamIndex = 0;
                if (numParameters > 0) {
                    final StringBuilder outParameterQuery = new StringBuilder("SELECT ");
                    boolean firstParam = true;
                    boolean hadOutputParams = false;
                    for (final CallableStatementParam retrParamInfo : this.paramInfo) {
                        if (retrParamInfo.isOut) {
                            hadOutputParams = true;
                            this.parameterIndexToRsIndex[retrParamInfo.index] = localParamIndex++;
                            if (retrParamInfo.paramName == null) {
                                retrParamInfo.paramName = "nullnp" + retrParamInfo.index;
                            }
                            final String outParameterName = mangleParameterName(retrParamInfo.paramName);
                            if (!firstParam) {
                                outParameterQuery.append(",");
                            }
                            else {
                                firstParam = false;
                            }
                            if (!outParameterName.startsWith("@")) {
                                outParameterQuery.append('@');
                            }
                            outParameterQuery.append(outParameterName);
                        }
                    }
                    if (hadOutputParams) {
                        Statement outParameterStmt = null;
                        ResultSet outParamRs = null;
                        try {
                            outParameterStmt = this.connection.createStatement();
                            outParamRs = outParameterStmt.executeQuery(outParameterQuery.toString());
                            this.outputParameterResults = this.resultSetFactory.createFromResultsetRows(outParamRs.getConcurrency(), outParamRs.getType(), ((ResultSetInternalMethods)outParamRs).getRows());
                            if (!this.outputParameterResults.next()) {
                                this.outputParameterResults.close();
                                this.outputParameterResults = null;
                            }
                        }
                        finally {
                            if (outParameterStmt != null) {
                                outParameterStmt.close();
                            }
                        }
                    }
                    else {
                        this.outputParameterResults = null;
                    }
                }
                else {
                    this.outputParameterResults = null;
                }
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setAsciiStream(final String parameterName, final InputStream x, final int length) throws SQLException {
        try {
            this.setAsciiStream(this.getNamedParamIndex(parameterName, false), x, length);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setBigDecimal(final String parameterName, final BigDecimal x) throws SQLException {
        try {
            this.setBigDecimal(this.getNamedParamIndex(parameterName, false), x);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setBinaryStream(final String parameterName, final InputStream x, final int length) throws SQLException {
        try {
            this.setBinaryStream(this.getNamedParamIndex(parameterName, false), x, length);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setBoolean(final String parameterName, final boolean x) throws SQLException {
        try {
            this.setBoolean(this.getNamedParamIndex(parameterName, false), x);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setByte(final String parameterName, final byte x) throws SQLException {
        try {
            this.setByte(this.getNamedParamIndex(parameterName, false), x);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setBytes(final String parameterName, final byte[] x) throws SQLException {
        try {
            this.setBytes(this.getNamedParamIndex(parameterName, false), x);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setCharacterStream(final String parameterName, final Reader reader, final int length) throws SQLException {
        try {
            this.setCharacterStream(this.getNamedParamIndex(parameterName, false), reader, length);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setDate(final String parameterName, final Date x) throws SQLException {
        try {
            this.setDate(this.getNamedParamIndex(parameterName, false), x);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setDate(final String parameterName, final Date x, final Calendar cal) throws SQLException {
        try {
            this.setDate(this.getNamedParamIndex(parameterName, false), x, cal);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setDouble(final String parameterName, final double x) throws SQLException {
        try {
            this.setDouble(this.getNamedParamIndex(parameterName, false), x);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setFloat(final String parameterName, final float x) throws SQLException {
        try {
            this.setFloat(this.getNamedParamIndex(parameterName, false), x);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    private void setInOutParamsOnServer() throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                if (this.paramInfo.numParameters > 0) {
                    for (final CallableStatementParam inParamInfo : this.paramInfo) {
                        if (inParamInfo.isOut && inParamInfo.isIn) {
                            if (inParamInfo.paramName == null) {
                                inParamInfo.paramName = "nullnp" + inParamInfo.index;
                            }
                            final String inOutParameterName = mangleParameterName(inParamInfo.paramName);
                            final StringBuilder queryBuf = new StringBuilder(4 + inOutParameterName.length() + 1 + 1);
                            queryBuf.append("SET ");
                            queryBuf.append(inOutParameterName);
                            queryBuf.append("=?");
                            ClientPreparedStatement setPstmt = null;
                            try {
                                setPstmt = this.connection.clientPrepareStatement(queryBuf.toString()).unwrap(ClientPreparedStatement.class);
                                if (((PreparedQuery)this.query).getQueryBindings().getBindValues()[inParamInfo.index].isNull()) {
                                    setPstmt.setBytesNoEscapeNoQuotes(1, "NULL".getBytes());
                                }
                                else {
                                    final byte[] parameterAsBytes = this.getBytesRepresentation(inParamInfo.index);
                                    if (parameterAsBytes != null) {
                                        if (parameterAsBytes.length > 8 && parameterAsBytes[0] == 95 && parameterAsBytes[1] == 98 && parameterAsBytes[2] == 105 && parameterAsBytes[3] == 110 && parameterAsBytes[4] == 97 && parameterAsBytes[5] == 114 && parameterAsBytes[6] == 121 && parameterAsBytes[7] == 39) {
                                            setPstmt.setBytesNoEscapeNoQuotes(1, parameterAsBytes);
                                        }
                                        else {
                                            switch (inParamInfo.desiredMysqlType) {
                                                case BIT:
                                                case BINARY:
                                                case GEOMETRY:
                                                case TINYBLOB:
                                                case BLOB:
                                                case MEDIUMBLOB:
                                                case LONGBLOB:
                                                case VARBINARY: {
                                                    setPstmt.setBytes(1, parameterAsBytes);
                                                    break;
                                                }
                                                default: {
                                                    setPstmt.setBytesNoEscape(1, parameterAsBytes);
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                    else {
                                        setPstmt.setNull(1, MysqlType.NULL);
                                    }
                                }
                                setPstmt.executeUpdate();
                            }
                            finally {
                                if (setPstmt != null) {
                                    setPstmt.close();
                                }
                            }
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
    public void setInt(final String parameterName, final int x) throws SQLException {
        try {
            this.setInt(this.getNamedParamIndex(parameterName, false), x);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setLong(final String parameterName, final long x) throws SQLException {
        try {
            this.setLong(this.getNamedParamIndex(parameterName, false), x);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setNull(final String parameterName, final int sqlType) throws SQLException {
        try {
            this.setNull(this.getNamedParamIndex(parameterName, false), sqlType);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setNull(final String parameterName, final int sqlType, final String typeName) throws SQLException {
        try {
            this.setNull(this.getNamedParamIndex(parameterName, false), sqlType, typeName);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setObject(final String parameterName, final Object x) throws SQLException {
        try {
            this.setObject(this.getNamedParamIndex(parameterName, false), x);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setObject(final String parameterName, final Object x, final int targetSqlType) throws SQLException {
        try {
            this.setObject(this.getNamedParamIndex(parameterName, false), x, targetSqlType);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setObject(final String parameterName, final Object x, final SQLType targetSqlType) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                this.setObject(this.getNamedParamIndex(parameterName, false), x, targetSqlType);
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setObject(final String parameterName, final Object x, final int targetSqlType, final int scale) throws SQLException {
        try {
            this.setObject(this.getNamedParamIndex(parameterName, false), x, targetSqlType, scale);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setObject(final String parameterName, final Object x, final SQLType targetSqlType, final int scaleOrLength) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                this.setObject(this.getNamedParamIndex(parameterName, false), x, targetSqlType, scaleOrLength);
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    private void setOutParams() throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                if (this.paramInfo.numParameters > 0) {
                    for (final CallableStatementParam outParamInfo : this.paramInfo) {
                        if (!this.callingStoredFunction && outParamInfo.isOut) {
                            if (outParamInfo.paramName == null) {
                                outParamInfo.paramName = "nullnp" + outParamInfo.index;
                            }
                            final String outParameterName = mangleParameterName(outParamInfo.paramName);
                            int outParamIndex = 0;
                            if (this.placeholderToParameterIndexMap == null) {
                                outParamIndex = outParamInfo.index + 1;
                            }
                            else {
                                boolean found = false;
                                for (int i = 0; i < this.placeholderToParameterIndexMap.length; ++i) {
                                    if (this.placeholderToParameterIndexMap[i] == outParamInfo.index) {
                                        outParamIndex = i + 1;
                                        found = true;
                                        break;
                                    }
                                }
                                if (!found) {
                                    throw SQLError.createSQLException(Messages.getString("CallableStatement.21", new Object[] { outParamInfo.paramName }), "S1009", this.getExceptionInterceptor());
                                }
                            }
                            this.setBytesNoEscapeNoQuotes(outParamIndex, StringUtils.getBytes(outParameterName, this.charEncoding));
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
    public void setShort(final String parameterName, final short x) throws SQLException {
        try {
            this.setShort(this.getNamedParamIndex(parameterName, false), x);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setString(final String parameterName, final String x) throws SQLException {
        try {
            this.setString(this.getNamedParamIndex(parameterName, false), x);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setTime(final String parameterName, final Time x) throws SQLException {
        try {
            this.setTime(this.getNamedParamIndex(parameterName, false), x);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setTime(final String parameterName, final Time x, final Calendar cal) throws SQLException {
        try {
            this.setTime(this.getNamedParamIndex(parameterName, false), x, cal);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setTimestamp(final String parameterName, final Timestamp x) throws SQLException {
        try {
            this.setTimestamp(this.getNamedParamIndex(parameterName, false), x);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setTimestamp(final String parameterName, final Timestamp x, final Calendar cal) throws SQLException {
        try {
            this.setTimestamp(this.getNamedParamIndex(parameterName, false), x, cal);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setURL(final String parameterName, final URL val) throws SQLException {
        try {
            this.setURL(this.getNamedParamIndex(parameterName, false), val);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean wasNull() throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                return this.outputParamWasNull;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public int[] executeBatch() throws SQLException {
        try {
            return Util.truncateAndConvertToInt(this.executeLargeBatch());
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    protected int getParameterIndexOffset() {
        if (this.callingStoredFunction) {
            return -1;
        }
        return super.getParameterIndexOffset();
    }
    
    @Override
    public void setAsciiStream(final String parameterName, final InputStream x) throws SQLException {
        try {
            this.setAsciiStream(this.getNamedParamIndex(parameterName, false), x);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setAsciiStream(final String parameterName, final InputStream x, final long length) throws SQLException {
        try {
            this.setAsciiStream(this.getNamedParamIndex(parameterName, false), x, length);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setBinaryStream(final String parameterName, final InputStream x) throws SQLException {
        try {
            this.setBinaryStream(this.getNamedParamIndex(parameterName, false), x);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setBinaryStream(final String parameterName, final InputStream x, final long length) throws SQLException {
        try {
            this.setBinaryStream(this.getNamedParamIndex(parameterName, false), x, length);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setBlob(final String parameterName, final Blob x) throws SQLException {
        try {
            this.setBlob(this.getNamedParamIndex(parameterName, false), x);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setBlob(final String parameterName, final InputStream inputStream) throws SQLException {
        try {
            this.setBlob(this.getNamedParamIndex(parameterName, false), inputStream);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setBlob(final String parameterName, final InputStream inputStream, final long length) throws SQLException {
        try {
            this.setBlob(this.getNamedParamIndex(parameterName, false), inputStream, length);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setCharacterStream(final String parameterName, final Reader reader) throws SQLException {
        try {
            this.setCharacterStream(this.getNamedParamIndex(parameterName, false), reader);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setCharacterStream(final String parameterName, final Reader reader, final long length) throws SQLException {
        try {
            this.setCharacterStream(this.getNamedParamIndex(parameterName, false), reader, length);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setClob(final String parameterName, final Clob x) throws SQLException {
        try {
            this.setClob(this.getNamedParamIndex(parameterName, false), x);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setClob(final String parameterName, final Reader reader) throws SQLException {
        try {
            this.setClob(this.getNamedParamIndex(parameterName, false), reader);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setClob(final String parameterName, final Reader reader, final long length) throws SQLException {
        try {
            this.setClob(this.getNamedParamIndex(parameterName, false), reader, length);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setNCharacterStream(final String parameterName, final Reader value) throws SQLException {
        try {
            this.setNCharacterStream(this.getNamedParamIndex(parameterName, false), value);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setNCharacterStream(final String parameterName, final Reader value, final long length) throws SQLException {
        try {
            this.setNCharacterStream(this.getNamedParamIndex(parameterName, false), value, length);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    private boolean checkReadOnlyProcedure() throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                if (this.noAccessToProcedureBodies) {
                    return false;
                }
                if (this.paramInfo.isReadOnlySafeChecked) {
                    return this.paramInfo.isReadOnlySafeProcedure;
                }
                ResultSet rs = null;
                PreparedStatement ps = null;
                try {
                    String procName = this.extractProcedureName();
                    String catalog = this.getCurrentCatalog();
                    if (procName.indexOf(".") != -1) {
                        catalog = procName.substring(0, procName.indexOf("."));
                        if (StringUtils.startsWithIgnoreCaseAndWs(catalog, "`") && catalog.trim().endsWith("`")) {
                            catalog = catalog.substring(1, catalog.length() - 1);
                        }
                        procName = procName.substring(procName.indexOf(".") + 1);
                        procName = StringUtils.toString(StringUtils.stripEnclosure(StringUtils.getBytes(procName), "`", "`"));
                    }
                    ps = this.connection.prepareStatement("SELECT SQL_DATA_ACCESS FROM information_schema.routines WHERE routine_schema = ? AND routine_name = ?");
                    ps.setMaxRows(0);
                    ps.setFetchSize(0);
                    ps.setString(1, catalog);
                    ps.setString(2, procName);
                    rs = ps.executeQuery();
                    if (rs.next()) {
                        final String sqlDataAccess = rs.getString(1);
                        if ("READS SQL DATA".equalsIgnoreCase(sqlDataAccess) || "NO SQL".equalsIgnoreCase(sqlDataAccess)) {
                            synchronized (this.paramInfo) {
                                this.paramInfo.isReadOnlySafeChecked = true;
                                this.paramInfo.isReadOnlySafeProcedure = true;
                            }
                            return true;
                        }
                    }
                }
                catch (SQLException ex2) {}
                finally {
                    if (rs != null) {
                        rs.close();
                    }
                    if (ps != null) {
                        ps.close();
                    }
                }
                this.paramInfo.isReadOnlySafeChecked = false;
                this.paramInfo.isReadOnlySafeProcedure = false;
            }
            return false;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    protected boolean checkReadOnlySafeStatement() throws SQLException {
        return super.checkReadOnlySafeStatement() || this.checkReadOnlyProcedure();
    }
    
    @Override
    public RowId getRowId(final int parameterIndex) throws SQLException {
        try {
            final ResultSetInternalMethods rs = this.getOutputParameters(parameterIndex);
            final RowId retValue = rs.getRowId(this.mapOutputParameterIndexToRsIndex(parameterIndex));
            this.outputParamWasNull = rs.wasNull();
            return retValue;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public RowId getRowId(final String parameterName) throws SQLException {
        try {
            final ResultSetInternalMethods rs = this.getOutputParameters(0);
            final RowId retValue = rs.getRowId(this.fixParameterName(parameterName));
            this.outputParamWasNull = rs.wasNull();
            return retValue;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setRowId(final String parameterName, final RowId x) throws SQLException {
        try {
            this.setRowId(this.getNamedParamIndex(parameterName, false), x);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setNString(final String parameterName, final String value) throws SQLException {
        try {
            this.setNString(this.getNamedParamIndex(parameterName, false), value);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setNClob(final String parameterName, final NClob value) throws SQLException {
        try {
            this.setNClob(this.getNamedParamIndex(parameterName, false), value);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setNClob(final String parameterName, final Reader reader) throws SQLException {
        try {
            this.setNClob(this.getNamedParamIndex(parameterName, false), reader);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setNClob(final String parameterName, final Reader reader, final long length) throws SQLException {
        try {
            this.setNClob(this.getNamedParamIndex(parameterName, false), reader, length);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setSQLXML(final String parameterName, final SQLXML xmlObject) throws SQLException {
        try {
            this.setSQLXML(this.getNamedParamIndex(parameterName, false), xmlObject);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public SQLXML getSQLXML(final int parameterIndex) throws SQLException {
        try {
            final ResultSetInternalMethods rs = this.getOutputParameters(parameterIndex);
            final SQLXML retValue = rs.getSQLXML(this.mapOutputParameterIndexToRsIndex(parameterIndex));
            this.outputParamWasNull = rs.wasNull();
            return retValue;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public SQLXML getSQLXML(final String parameterName) throws SQLException {
        try {
            final ResultSetInternalMethods rs = this.getOutputParameters(0);
            final SQLXML retValue = rs.getSQLXML(this.fixParameterName(parameterName));
            this.outputParamWasNull = rs.wasNull();
            return retValue;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public String getNString(final int parameterIndex) throws SQLException {
        try {
            final ResultSetInternalMethods rs = this.getOutputParameters(parameterIndex);
            final String retValue = rs.getNString(this.mapOutputParameterIndexToRsIndex(parameterIndex));
            this.outputParamWasNull = rs.wasNull();
            return retValue;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public String getNString(final String parameterName) throws SQLException {
        try {
            final ResultSetInternalMethods rs = this.getOutputParameters(0);
            final String retValue = rs.getNString(this.fixParameterName(parameterName));
            this.outputParamWasNull = rs.wasNull();
            return retValue;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public Reader getNCharacterStream(final int parameterIndex) throws SQLException {
        try {
            final ResultSetInternalMethods rs = this.getOutputParameters(parameterIndex);
            final Reader retValue = rs.getNCharacterStream(this.mapOutputParameterIndexToRsIndex(parameterIndex));
            this.outputParamWasNull = rs.wasNull();
            return retValue;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public Reader getNCharacterStream(final String parameterName) throws SQLException {
        try {
            final ResultSetInternalMethods rs = this.getOutputParameters(0);
            final Reader retValue = rs.getNCharacterStream(this.fixParameterName(parameterName));
            this.outputParamWasNull = rs.wasNull();
            return retValue;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public Reader getCharacterStream(final int parameterIndex) throws SQLException {
        try {
            final ResultSetInternalMethods rs = this.getOutputParameters(parameterIndex);
            final Reader retValue = rs.getCharacterStream(this.mapOutputParameterIndexToRsIndex(parameterIndex));
            this.outputParamWasNull = rs.wasNull();
            return retValue;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public Reader getCharacterStream(final String parameterName) throws SQLException {
        try {
            final ResultSetInternalMethods rs = this.getOutputParameters(0);
            final Reader retValue = rs.getCharacterStream(this.fixParameterName(parameterName));
            this.outputParamWasNull = rs.wasNull();
            return retValue;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public NClob getNClob(final int parameterIndex) throws SQLException {
        try {
            final ResultSetInternalMethods rs = this.getOutputParameters(parameterIndex);
            final NClob retValue = rs.getNClob(this.mapOutputParameterIndexToRsIndex(parameterIndex));
            this.outputParamWasNull = rs.wasNull();
            return retValue;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public NClob getNClob(final String parameterName) throws SQLException {
        try {
            final ResultSetInternalMethods rs = this.getOutputParameters(0);
            final NClob retValue = rs.getNClob(this.fixParameterName(parameterName));
            this.outputParamWasNull = rs.wasNull();
            return retValue;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    protected byte[] s2b(final String s) {
        return (byte[])((s == null) ? null : StringUtils.getBytes(s, this.charEncoding));
    }
    
    @Override
    public long executeLargeUpdate() throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                long returnVal = -1L;
                this.checkStreamability();
                if (this.callingStoredFunction) {
                    this.execute();
                    return -1L;
                }
                this.setInOutParamsOnServer();
                this.setOutParams();
                returnVal = super.executeLargeUpdate();
                this.retrieveOutParams();
                return returnVal;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public long[] executeLargeBatch() throws SQLException {
        try {
            if (this.hasOutputParams) {
                throw SQLError.createSQLException("Can't call executeBatch() on CallableStatement with OUTPUT parameters", "S1009", this.getExceptionInterceptor());
            }
            return super.executeLargeBatch();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    protected static class CallableStatementParam
    {
        int index;
        int inOutModifier;
        boolean isIn;
        boolean isOut;
        int jdbcType;
        short nullability;
        String paramName;
        int precision;
        int scale;
        String typeName;
        MysqlType desiredMysqlType;
        
        CallableStatementParam(final String name, final int idx, final boolean in, final boolean out, final int jdbcType, final String typeName, final int precision, final int scale, final short nullability, final int inOutModifier) {
            this.desiredMysqlType = MysqlType.UNKNOWN;
            this.paramName = name;
            this.isIn = in;
            this.isOut = out;
            this.index = idx;
            this.jdbcType = jdbcType;
            this.typeName = typeName;
            this.precision = precision;
            this.scale = scale;
            this.nullability = nullability;
            this.inOutModifier = inOutModifier;
        }
        
        @Override
        protected Object clone() throws CloneNotSupportedException {
            return super.clone();
        }
    }
    
    public class CallableStatementParamInfo implements ParameterMetaData
    {
        String catalogInUse;
        boolean isFunctionCall;
        String nativeSql;
        int numParameters;
        List<CallableStatementParam> parameterList;
        Map<String, CallableStatementParam> parameterMap;
        boolean isReadOnlySafeProcedure;
        boolean isReadOnlySafeChecked;
        
        CallableStatementParamInfo(final CallableStatementParamInfo fullParamInfo) {
            this.isReadOnlySafeProcedure = false;
            this.isReadOnlySafeChecked = false;
            this.nativeSql = ((PreparedQuery)CallableStatement.this.query).getOriginalSql();
            this.catalogInUse = CallableStatement.this.getCurrentCatalog();
            this.isFunctionCall = fullParamInfo.isFunctionCall;
            final int[] localParameterMap = CallableStatement.this.placeholderToParameterIndexMap;
            final int parameterMapLength = localParameterMap.length;
            this.isReadOnlySafeProcedure = fullParamInfo.isReadOnlySafeProcedure;
            this.isReadOnlySafeChecked = fullParamInfo.isReadOnlySafeChecked;
            this.parameterList = new ArrayList<CallableStatementParam>(fullParamInfo.numParameters);
            this.parameterMap = new HashMap<String, CallableStatementParam>(fullParamInfo.numParameters);
            if (this.isFunctionCall) {
                this.parameterList.add(fullParamInfo.parameterList.get(0));
            }
            final int offset = this.isFunctionCall ? 1 : 0;
            for (int i = 0; i < parameterMapLength; ++i) {
                if (localParameterMap[i] != 0) {
                    final CallableStatementParam param = fullParamInfo.parameterList.get(localParameterMap[i] + offset);
                    this.parameterList.add(param);
                    this.parameterMap.put(param.paramName, param);
                }
            }
            this.numParameters = this.parameterList.size();
        }
        
        CallableStatementParamInfo(final ResultSet paramTypesRs) throws SQLException {
            this.isReadOnlySafeProcedure = false;
            this.isReadOnlySafeChecked = false;
            final boolean hadRows = paramTypesRs.last();
            this.nativeSql = ((PreparedQuery)CallableStatement.this.query).getOriginalSql();
            this.catalogInUse = CallableStatement.this.getCurrentCatalog();
            this.isFunctionCall = CallableStatement.this.callingStoredFunction;
            if (hadRows) {
                this.numParameters = paramTypesRs.getRow();
                this.parameterList = new ArrayList<CallableStatementParam>(this.numParameters);
                this.parameterMap = new HashMap<String, CallableStatementParam>(this.numParameters);
                paramTypesRs.beforeFirst();
                this.addParametersFromDBMD(paramTypesRs);
            }
            else {
                this.numParameters = 0;
            }
            if (this.isFunctionCall) {
                ++this.numParameters;
            }
        }
        
        private void addParametersFromDBMD(final ResultSet paramTypesRs) throws SQLException {
            int i = 0;
            while (paramTypesRs.next()) {
                final String paramName = paramTypesRs.getString(4);
                int inOutModifier = 0;
                switch (paramTypesRs.getInt(5)) {
                    case 1: {
                        inOutModifier = 1;
                        break;
                    }
                    case 2: {
                        inOutModifier = 2;
                        break;
                    }
                    case 4:
                    case 5: {
                        inOutModifier = 4;
                        break;
                    }
                    default: {
                        inOutModifier = 0;
                        break;
                    }
                }
                boolean isOutParameter = false;
                boolean isInParameter = false;
                if (i == 0 && this.isFunctionCall) {
                    isOutParameter = true;
                    isInParameter = false;
                }
                else if (inOutModifier == 2) {
                    isOutParameter = true;
                    isInParameter = true;
                }
                else if (inOutModifier == 1) {
                    isOutParameter = false;
                    isInParameter = true;
                }
                else if (inOutModifier == 4) {
                    isOutParameter = true;
                    isInParameter = false;
                }
                final int jdbcType = paramTypesRs.getInt(6);
                final String typeName = paramTypesRs.getString(7);
                final int precision = paramTypesRs.getInt(8);
                final int scale = paramTypesRs.getInt(10);
                final short nullability = paramTypesRs.getShort(12);
                final CallableStatementParam paramInfoToAdd = new CallableStatementParam(paramName, i++, isInParameter, isOutParameter, jdbcType, typeName, precision, scale, nullability, inOutModifier);
                this.parameterList.add(paramInfoToAdd);
                this.parameterMap.put(paramName, paramInfoToAdd);
            }
        }
        
        protected void checkBounds(final int paramIndex) throws SQLException {
            final int localParamIndex = paramIndex - 1;
            if (paramIndex < 0 || localParamIndex >= this.numParameters) {
                throw SQLError.createSQLException(Messages.getString("CallableStatement.11", new Object[] { paramIndex, this.numParameters }), "S1009", CallableStatement.this.getExceptionInterceptor());
            }
        }
        
        @Override
        protected Object clone() throws CloneNotSupportedException {
            return super.clone();
        }
        
        CallableStatementParam getParameter(final int index) {
            return this.parameterList.get(index);
        }
        
        CallableStatementParam getParameter(final String name) {
            return this.parameterMap.get(name);
        }
        
        @Override
        public String getParameterClassName(final int arg0) throws SQLException {
            try {
                final String mysqlTypeName = this.getParameterTypeName(arg0);
                final MysqlType mysqlType = MysqlType.getByName(mysqlTypeName);
                switch (mysqlType) {
                    case YEAR: {
                        if (!CallableStatement.this.session.getPropertySet().getBooleanProperty(PropertyKey.yearIsDateType).getValue()) {
                            return Short.class.getName();
                        }
                        return mysqlType.getClassName();
                    }
                    default: {
                        return mysqlType.getClassName();
                    }
                }
            }
            catch (CJException ex) {
                throw SQLExceptionsMapping.translateException(ex);
            }
        }
        
        @Override
        public int getParameterCount() throws SQLException {
            try {
                if (this.parameterList == null) {
                    return 0;
                }
                return this.parameterList.size();
            }
            catch (CJException ex) {
                throw SQLExceptionsMapping.translateException(ex);
            }
        }
        
        @Override
        public int getParameterMode(final int arg0) throws SQLException {
            try {
                this.checkBounds(arg0);
                return this.getParameter(arg0 - 1).inOutModifier;
            }
            catch (CJException ex) {
                throw SQLExceptionsMapping.translateException(ex);
            }
        }
        
        @Override
        public int getParameterType(final int arg0) throws SQLException {
            try {
                this.checkBounds(arg0);
                return this.getParameter(arg0 - 1).jdbcType;
            }
            catch (CJException ex) {
                throw SQLExceptionsMapping.translateException(ex);
            }
        }
        
        @Override
        public String getParameterTypeName(final int arg0) throws SQLException {
            try {
                this.checkBounds(arg0);
                return this.getParameter(arg0 - 1).typeName;
            }
            catch (CJException ex) {
                throw SQLExceptionsMapping.translateException(ex);
            }
        }
        
        @Override
        public int getPrecision(final int arg0) throws SQLException {
            try {
                this.checkBounds(arg0);
                return this.getParameter(arg0 - 1).precision;
            }
            catch (CJException ex) {
                throw SQLExceptionsMapping.translateException(ex);
            }
        }
        
        @Override
        public int getScale(final int arg0) throws SQLException {
            try {
                this.checkBounds(arg0);
                return this.getParameter(arg0 - 1).scale;
            }
            catch (CJException ex) {
                throw SQLExceptionsMapping.translateException(ex);
            }
        }
        
        @Override
        public int isNullable(final int arg0) throws SQLException {
            try {
                this.checkBounds(arg0);
                return this.getParameter(arg0 - 1).nullability;
            }
            catch (CJException ex) {
                throw SQLExceptionsMapping.translateException(ex);
            }
        }
        
        @Override
        public boolean isSigned(final int arg0) throws SQLException {
            try {
                this.checkBounds(arg0);
                return false;
            }
            catch (CJException ex) {
                throw SQLExceptionsMapping.translateException(ex);
            }
        }
        
        Iterator<CallableStatementParam> iterator() {
            return this.parameterList.iterator();
        }
        
        int numberOfParameters() {
            return this.numParameters;
        }
        
        @Override
        public boolean isWrapperFor(final Class<?> iface) throws SQLException {
            try {
                CallableStatement.this.checkClosed();
                return iface.isInstance(this);
            }
            catch (CJException ex) {
                throw SQLExceptionsMapping.translateException(ex);
            }
        }
        
        @Override
        public <T> T unwrap(final Class<T> iface) throws SQLException {
            try {
                try {
                    return iface.cast(this);
                }
                catch (ClassCastException cce) {
                    throw SQLError.createSQLException(Messages.getString("Common.UnableToUnwrap", new Object[] { iface.toString() }), "S1009", CallableStatement.this.getExceptionInterceptor());
                }
            }
            catch (CJException ex) {
                throw SQLExceptionsMapping.translateException(ex);
            }
        }
    }
}
