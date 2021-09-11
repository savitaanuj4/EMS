
package com.mysql.cj.jdbc;

import java.util.Arrays;
import java.sql.RowIdLifetime;
import java.util.Set;
import java.util.Collection;
import java.util.TreeSet;
import java.util.SortedMap;
import java.util.TreeMap;
import java.sql.Connection;
import java.util.Map;
import java.util.HashMap;
import java.sql.PreparedStatement;
import java.util.Locale;
import java.util.Iterator;
import com.mysql.cj.exceptions.AssertionFailedException;
import java.util.Collections;
import java.sql.Statement;
import com.mysql.cj.protocol.ResultsetRows;
import com.mysql.cj.protocol.ColumnDefinition;
import com.mysql.cj.protocol.a.result.ResultsetRowsStatic;
import com.mysql.cj.result.DefaultColumnDefinition;
import com.mysql.cj.MysqlType;
import java.util.StringTokenizer;
import java.util.ArrayList;
import com.mysql.cj.jdbc.exceptions.SQLError;
import com.mysql.cj.Messages;
import com.mysql.cj.protocol.a.result.ByteArrayRow;
import com.mysql.cj.util.StringUtils;
import com.mysql.cj.result.Field;
import com.mysql.cj.result.Row;
import java.sql.ResultSet;
import com.mysql.cj.exceptions.CJException;
import com.mysql.cj.jdbc.exceptions.SQLExceptionsMapping;
import java.sql.SQLException;
import com.mysql.cj.conf.PropertyKey;
import com.mysql.cj.exceptions.ExceptionInterceptor;
import com.mysql.cj.jdbc.result.ResultSetFactory;
import com.mysql.cj.NativeSession;
import java.util.List;

public class DatabaseMetaData implements java.sql.DatabaseMetaData
{
    protected static int maxBufferSize;
    protected static final int MAX_IDENTIFIER_LENGTH = 64;
    private static final int DEFERRABILITY = 13;
    private static final int DELETE_RULE = 10;
    private static final int FK_NAME = 11;
    private static final int FKCOLUMN_NAME = 7;
    private static final int FKTABLE_CAT = 4;
    private static final int FKTABLE_NAME = 6;
    private static final int FKTABLE_SCHEM = 5;
    private static final int KEY_SEQ = 8;
    private static final int PK_NAME = 12;
    private static final int PKCOLUMN_NAME = 3;
    private static final int PKTABLE_CAT = 0;
    private static final int PKTABLE_NAME = 2;
    private static final int PKTABLE_SCHEM = 1;
    private static final String SUPPORTS_FK = "SUPPORTS_FK";
    protected static final byte[] TABLE_AS_BYTES;
    protected static final byte[] SYSTEM_TABLE_AS_BYTES;
    private static final int UPDATE_RULE = 9;
    protected static final byte[] VIEW_AS_BYTES;
    private static final String[] MYSQL_KEYWORDS;
    static final List<String> SQL2003_KEYWORDS;
    private static volatile String mysqlKeywords;
    protected JdbcConnection conn;
    protected NativeSession session;
    protected String database;
    protected final String quotedId;
    protected boolean nullCatalogMeansCurrent;
    protected boolean pedantic;
    protected boolean tinyInt1isBit;
    protected boolean transformedBitIsBoolean;
    protected boolean useHostsInPrivileges;
    protected ResultSetFactory resultSetFactory;
    private String metadataEncoding;
    private int metadataCollationIndex;
    private ExceptionInterceptor exceptionInterceptor;
    
    protected static DatabaseMetaData getInstance(final JdbcConnection connToSet, final String databaseToSet, final boolean checkForInfoSchema, final ResultSetFactory resultSetFactory) throws SQLException {
        if (checkForInfoSchema && connToSet.getPropertySet().getBooleanProperty(PropertyKey.useInformationSchema).getValue()) {
            return new DatabaseMetaDataUsingInfoSchema(connToSet, databaseToSet, resultSetFactory);
        }
        return new DatabaseMetaData(connToSet, databaseToSet, resultSetFactory);
    }
    
    protected DatabaseMetaData(final JdbcConnection connToSet, final String databaseToSet, final ResultSetFactory resultSetFactory) {
        this.database = null;
        this.conn = connToSet;
        this.session = (NativeSession)connToSet.getSession();
        this.database = databaseToSet;
        this.resultSetFactory = resultSetFactory;
        this.exceptionInterceptor = this.conn.getExceptionInterceptor();
        this.nullCatalogMeansCurrent = this.conn.getPropertySet().getBooleanProperty(PropertyKey.nullCatalogMeansCurrent).getValue();
        this.pedantic = this.conn.getPropertySet().getBooleanProperty(PropertyKey.pedantic).getValue();
        this.tinyInt1isBit = this.conn.getPropertySet().getBooleanProperty(PropertyKey.tinyInt1isBit).getValue();
        this.transformedBitIsBoolean = this.conn.getPropertySet().getBooleanProperty(PropertyKey.transformedBitIsBoolean).getValue();
        this.useHostsInPrivileges = this.conn.getPropertySet().getBooleanProperty(PropertyKey.useHostsInPrivileges).getValue();
        this.quotedId = this.session.getIdentifierQuoteString();
    }
    
    @Override
    public boolean allProceduresAreCallable() throws SQLException {
        try {
            return false;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean allTablesAreSelectable() throws SQLException {
        try {
            return false;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    protected void convertToJdbcFunctionList(final String catalog, final ResultSet proceduresRs, final boolean needsClientFiltering, final String db, final List<ComparableWrapper<String, Row>> procedureRows, final int nameIndex, final Field[] fields) throws SQLException {
        while (proceduresRs.next()) {
            boolean shouldAdd = true;
            if (needsClientFiltering) {
                shouldAdd = false;
                final String procDb = proceduresRs.getString(1);
                if (db == null && procDb == null) {
                    shouldAdd = true;
                }
                else if (db != null && db.equals(procDb)) {
                    shouldAdd = true;
                }
            }
            if (shouldAdd) {
                final String functionName = proceduresRs.getString(nameIndex);
                byte[][] rowData = null;
                if (fields != null && fields.length == 9) {
                    rowData = new byte[][] { (catalog == null) ? null : this.s2b(catalog), null, this.s2b(functionName), null, null, null, this.s2b(proceduresRs.getString("comment")), this.s2b(Integer.toString(2)), this.s2b(functionName) };
                }
                else {
                    rowData = new byte[][] { (catalog == null) ? null : this.s2b(catalog), null, this.s2b(functionName), this.s2b(proceduresRs.getString("comment")), this.s2b(Integer.toString(this.getFunctionNoTableConstant())), this.s2b(functionName) };
                }
                procedureRows.add(new ComparableWrapper<String, Row>(StringUtils.getFullyQualifiedName(catalog, functionName, this.quotedId, this.pedantic), new ByteArrayRow(rowData, this.getExceptionInterceptor())));
            }
        }
    }
    
    protected int getFunctionNoTableConstant() {
        return 1;
    }
    
    protected void convertToJdbcProcedureList(final boolean fromSelect, final String catalog, final ResultSet proceduresRs, final boolean needsClientFiltering, final String db, final List<ComparableWrapper<String, Row>> procedureRows, final int nameIndex) throws SQLException {
        while (proceduresRs.next()) {
            boolean shouldAdd = true;
            if (needsClientFiltering) {
                shouldAdd = false;
                final String procDb = proceduresRs.getString(1);
                if (db == null && procDb == null) {
                    shouldAdd = true;
                }
                else if (db != null && db.equals(procDb)) {
                    shouldAdd = true;
                }
            }
            if (shouldAdd) {
                final String procedureName = proceduresRs.getString(nameIndex);
                final byte[][] rowData = { (catalog == null) ? null : this.s2b(catalog), null, this.s2b(procedureName), null, null, null, this.s2b(proceduresRs.getString("comment")), null, null };
                final boolean isFunction = fromSelect && "FUNCTION".equalsIgnoreCase(proceduresRs.getString("type"));
                rowData[7] = this.s2b(isFunction ? Integer.toString(2) : Integer.toString(1));
                rowData[8] = this.s2b(procedureName);
                procedureRows.add(new ComparableWrapper<String, Row>(StringUtils.getFullyQualifiedName(catalog, procedureName, this.quotedId, this.pedantic), new ByteArrayRow(rowData, this.getExceptionInterceptor())));
            }
        }
    }
    
    private Row convertTypeDescriptorToProcedureRow(final byte[] procNameAsBytes, final byte[] procCatAsBytes, final String paramName, final boolean isOutParam, final boolean isInParam, final boolean isReturnParam, final TypeDescriptor typeDesc, final boolean forGetFunctionColumns, final int ordinal) throws SQLException {
        final byte[][] row = forGetFunctionColumns ? new byte[17][] : new byte[20][];
        row[0] = procCatAsBytes;
        row[1] = null;
        row[2] = procNameAsBytes;
        row[3] = this.s2b(paramName);
        row[4] = this.s2b(String.valueOf(this.getColumnType(isOutParam, isInParam, isReturnParam, forGetFunctionColumns)));
        row[5] = this.s2b(Short.toString((short)typeDesc.mysqlType.getJdbcType()));
        row[6] = this.s2b(typeDesc.mysqlType.getName());
        row[8] = (row[7] = (byte[])((typeDesc.columnSize == null) ? null : this.s2b(typeDesc.columnSize.toString())));
        row[9] = (byte[])((typeDesc.decimalDigits == null) ? null : this.s2b(typeDesc.decimalDigits.toString()));
        row[10] = this.s2b(Integer.toString(typeDesc.numPrecRadix));
        switch (typeDesc.nullability) {
            case 0: {
                row[11] = this.s2b(String.valueOf(0));
                break;
            }
            case 1: {
                row[11] = this.s2b(String.valueOf(1));
                break;
            }
            case 2: {
                row[11] = this.s2b(String.valueOf(2));
                break;
            }
            default: {
                throw SQLError.createSQLException(Messages.getString("DatabaseMetaData.1"), "S1000", this.getExceptionInterceptor());
            }
        }
        row[12] = null;
        if (forGetFunctionColumns) {
            row[13] = null;
            row[14] = this.s2b(String.valueOf(ordinal));
            row[15] = this.s2b(typeDesc.isNullable);
            row[16] = procNameAsBytes;
        }
        else {
            row[14] = (row[13] = null);
            row[16] = (row[15] = null);
            row[17] = this.s2b(String.valueOf(ordinal));
            row[18] = this.s2b(typeDesc.isNullable);
            row[19] = procNameAsBytes;
        }
        return new ByteArrayRow(row, this.getExceptionInterceptor());
    }
    
    protected int getColumnType(final boolean isOutParam, final boolean isInParam, final boolean isReturnParam, final boolean forGetFunctionColumns) {
        return getProcedureOrFunctionColumnType(isOutParam, isInParam, isReturnParam, forGetFunctionColumns);
    }
    
    protected static int getProcedureOrFunctionColumnType(final boolean isOutParam, final boolean isInParam, final boolean isReturnParam, final boolean forGetFunctionColumns) {
        if (isInParam && isOutParam) {
            return forGetFunctionColumns ? 2 : 2;
        }
        if (isInParam) {
            return (!forGetFunctionColumns || true) ? 1 : 0;
        }
        if (isOutParam) {
            return forGetFunctionColumns ? 3 : 4;
        }
        if (isReturnParam) {
            return forGetFunctionColumns ? 4 : 5;
        }
        return (forGetFunctionColumns && false) ? 1 : 0;
    }
    
    protected ExceptionInterceptor getExceptionInterceptor() {
        return this.exceptionInterceptor;
    }
    
    @Override
    public boolean dataDefinitionCausesTransactionCommit() throws SQLException {
        try {
            return true;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean dataDefinitionIgnoredInTransactions() throws SQLException {
        try {
            return false;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean deletesAreDetected(final int type) throws SQLException {
        try {
            return false;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean doesMaxRowSizeIncludeBlobs() throws SQLException {
        try {
            return true;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    public List<Row> extractForeignKeyForTable(final ArrayList<Row> rows, final ResultSet rs, final String catalog) throws SQLException {
        final byte[][] row = { rs.getBytes(1), this.s2b("SUPPORTS_FK"), null };
        final String createTableString = rs.getString(2);
        final StringTokenizer lineTokenizer = new StringTokenizer(createTableString, "\n");
        final StringBuilder commentBuf = new StringBuilder("comment; ");
        boolean firstTime = true;
        while (lineTokenizer.hasMoreTokens()) {
            String line = lineTokenizer.nextToken().trim();
            String constraintName = null;
            if (StringUtils.startsWithIgnoreCase(line, "CONSTRAINT")) {
                boolean usingBackTicks = true;
                int beginPos = StringUtils.indexOfQuoteDoubleAware(line, this.quotedId, 0);
                if (beginPos == -1) {
                    beginPos = line.indexOf("\"");
                    usingBackTicks = false;
                }
                if (beginPos != -1) {
                    int endPos = -1;
                    if (usingBackTicks) {
                        endPos = StringUtils.indexOfQuoteDoubleAware(line, this.quotedId, beginPos + 1);
                    }
                    else {
                        endPos = StringUtils.indexOfQuoteDoubleAware(line, "\"", beginPos + 1);
                    }
                    if (endPos != -1) {
                        constraintName = line.substring(beginPos + 1, endPos);
                        line = line.substring(endPos + 1, line.length()).trim();
                    }
                }
            }
            if (line.startsWith("FOREIGN KEY")) {
                if (line.endsWith(",")) {
                    line = line.substring(0, line.length() - 1);
                }
                final int indexOfFK = line.indexOf("FOREIGN KEY");
                String localColumnName = null;
                String referencedCatalogName = StringUtils.quoteIdentifier(catalog, this.quotedId, this.pedantic);
                String referencedTableName = null;
                String referencedColumnName = null;
                if (indexOfFK != -1) {
                    final int afterFk = indexOfFK + "FOREIGN KEY".length();
                    final int indexOfRef = StringUtils.indexOfIgnoreCase(afterFk, line, "REFERENCES", this.quotedId, this.quotedId, StringUtils.SEARCH_MODE__ALL);
                    if (indexOfRef != -1) {
                        final int indexOfParenOpen = line.indexOf(40, afterFk);
                        final int indexOfParenClose = StringUtils.indexOfIgnoreCase(indexOfParenOpen, line, ")", this.quotedId, this.quotedId, StringUtils.SEARCH_MODE__ALL);
                        if (indexOfParenOpen == -1 || indexOfParenClose == -1) {}
                        localColumnName = line.substring(indexOfParenOpen + 1, indexOfParenClose);
                        final int afterRef = indexOfRef + "REFERENCES".length();
                        final int referencedColumnBegin = StringUtils.indexOfIgnoreCase(afterRef, line, "(", this.quotedId, this.quotedId, StringUtils.SEARCH_MODE__ALL);
                        if (referencedColumnBegin != -1) {
                            referencedTableName = line.substring(afterRef, referencedColumnBegin);
                            final int referencedColumnEnd = StringUtils.indexOfIgnoreCase(referencedColumnBegin + 1, line, ")", this.quotedId, this.quotedId, StringUtils.SEARCH_MODE__ALL);
                            if (referencedColumnEnd != -1) {
                                referencedColumnName = line.substring(referencedColumnBegin + 1, referencedColumnEnd);
                            }
                            final int indexOfCatalogSep = StringUtils.indexOfIgnoreCase(0, referencedTableName, ".", this.quotedId, this.quotedId, StringUtils.SEARCH_MODE__ALL);
                            if (indexOfCatalogSep != -1) {
                                referencedCatalogName = referencedTableName.substring(0, indexOfCatalogSep);
                                referencedTableName = referencedTableName.substring(indexOfCatalogSep + 1);
                            }
                        }
                    }
                }
                if (!firstTime) {
                    commentBuf.append("; ");
                }
                else {
                    firstTime = false;
                }
                if (constraintName != null) {
                    commentBuf.append(constraintName);
                }
                else {
                    commentBuf.append("not_available");
                }
                commentBuf.append("(");
                commentBuf.append(localColumnName);
                commentBuf.append(") REFER ");
                commentBuf.append(referencedCatalogName);
                commentBuf.append("/");
                commentBuf.append(referencedTableName);
                commentBuf.append("(");
                commentBuf.append(referencedColumnName);
                commentBuf.append(")");
                final int lastParenIndex = line.lastIndexOf(")");
                if (lastParenIndex == line.length() - 1) {
                    continue;
                }
                final String cascadeOptions = line.substring(lastParenIndex + 1);
                commentBuf.append(" ");
                commentBuf.append(cascadeOptions);
            }
        }
        row[2] = this.s2b(commentBuf.toString());
        rows.add(new ByteArrayRow(row, this.getExceptionInterceptor()));
        return rows;
    }
    
    public ResultSet extractForeignKeyFromCreateTable(final String catalog, final String tableName) throws SQLException {
        final ArrayList<String> tableList = new ArrayList<String>();
        ResultSet rs = null;
        Statement stmt = null;
        if (tableName != null) {
            tableList.add(tableName);
        }
        else {
            try {
                rs = this.getTables(catalog, null, null, new String[] { "TABLE" });
                while (rs.next()) {
                    tableList.add(rs.getString("TABLE_NAME"));
                }
            }
            finally {
                if (rs != null) {
                    rs.close();
                }
                rs = null;
            }
        }
        final ArrayList<Row> rows = new ArrayList<Row>();
        final Field[] fields = { new Field("", "Name", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, Integer.MAX_VALUE), new Field("", "Type", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 255), new Field("", "Comment", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, Integer.MAX_VALUE) };
        final int numTables = tableList.size();
        stmt = this.conn.getMetadataSafeStatement();
        try {
            for (int i = 0; i < numTables; ++i) {
                final String tableToExtract = tableList.get(i);
                final String query = "SHOW CREATE TABLE " + StringUtils.getFullyQualifiedName(catalog, tableToExtract, this.quotedId, this.pedantic);
                try {
                    rs = stmt.executeQuery(query);
                }
                catch (SQLException sqlEx) {
                    final String sqlState = sqlEx.getSQLState();
                    if (!"42S02".equals(sqlState) && sqlEx.getErrorCode() != 1146) {
                        throw sqlEx;
                    }
                    continue;
                }
                while (rs.next()) {
                    this.extractForeignKeyForTable(rows, rs, catalog);
                }
            }
        }
        finally {
            if (rs != null) {
                rs.close();
            }
            rs = null;
            if (stmt != null) {
                stmt.close();
            }
            stmt = null;
        }
        return this.resultSetFactory.createFromResultsetRows(1007, 1004, new ResultsetRowsStatic(rows, new DefaultColumnDefinition(fields)));
    }
    
    @Override
    public ResultSet getAttributes(final String arg0, final String arg1, final String arg2, final String arg3) throws SQLException {
        try {
            final Field[] fields = { new Field("", "TYPE_CAT", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 32), new Field("", "TYPE_SCHEM", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 32), new Field("", "TYPE_NAME", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 32), new Field("", "ATTR_NAME", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 32), new Field("", "DATA_TYPE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.SMALLINT, 32), new Field("", "ATTR_TYPE_NAME", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 32), new Field("", "ATTR_SIZE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.INT, 32), new Field("", "DECIMAL_DIGITS", this.metadataCollationIndex, this.metadataEncoding, MysqlType.INT, 32), new Field("", "NUM_PREC_RADIX", this.metadataCollationIndex, this.metadataEncoding, MysqlType.INT, 32), new Field("", "NULLABLE ", this.metadataCollationIndex, this.metadataEncoding, MysqlType.INT, 32), new Field("", "REMARKS", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 32), new Field("", "ATTR_DEF", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 32), new Field("", "SQL_DATA_TYPE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.INT, 32), new Field("", "SQL_DATETIME_SUB", this.metadataCollationIndex, this.metadataEncoding, MysqlType.INT, 32), new Field("", "CHAR_OCTET_LENGTH", this.metadataCollationIndex, this.metadataEncoding, MysqlType.INT, 32), new Field("", "ORDINAL_POSITION", this.metadataCollationIndex, this.metadataEncoding, MysqlType.INT, 32), new Field("", "IS_NULLABLE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 32), new Field("", "SCOPE_CATALOG", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 32), new Field("", "SCOPE_SCHEMA", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 32), new Field("", "SCOPE_TABLE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 32), new Field("", "SOURCE_DATA_TYPE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.SMALLINT, 32) };
            return this.resultSetFactory.createFromResultsetRows(1007, 1004, new ResultsetRowsStatic(new ArrayList<Row>(), new DefaultColumnDefinition(fields)));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public ResultSet getBestRowIdentifier(final String catalog, final String schema, final String table, final int scope, final boolean nullable) throws SQLException {
        try {
            if (table == null) {
                throw SQLError.createSQLException(Messages.getString("DatabaseMetaData.2"), "S1009", this.getExceptionInterceptor());
            }
            final Field[] fields = { new Field("", "SCOPE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.SMALLINT, 5), new Field("", "COLUMN_NAME", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 32), new Field("", "DATA_TYPE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.INT, 32), new Field("", "TYPE_NAME", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 32), new Field("", "COLUMN_SIZE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.INT, 10), new Field("", "BUFFER_LENGTH", this.metadataCollationIndex, this.metadataEncoding, MysqlType.INT, 10), new Field("", "DECIMAL_DIGITS", this.metadataCollationIndex, this.metadataEncoding, MysqlType.SMALLINT, 10), new Field("", "PSEUDO_COLUMN", this.metadataCollationIndex, this.metadataEncoding, MysqlType.SMALLINT, 5) };
            final ArrayList<Row> rows = new ArrayList<Row>();
            final Statement stmt = this.conn.getMetadataSafeStatement();
            try {
                new IterateBlock<String>(this.getCatalogIterator(catalog)) {
                    @Override
                    void forEach(final String catalogStr) throws SQLException {
                        ResultSet results = null;
                        try {
                            final StringBuilder queryBuf = new StringBuilder("SHOW COLUMNS FROM ");
                            queryBuf.append(StringUtils.quoteIdentifier(table, DatabaseMetaData.this.quotedId, DatabaseMetaData.this.pedantic));
                            queryBuf.append(" FROM ");
                            queryBuf.append(StringUtils.quoteIdentifier(catalogStr, DatabaseMetaData.this.quotedId, DatabaseMetaData.this.pedantic));
                            results = stmt.executeQuery(queryBuf.toString());
                            while (results.next()) {
                                final String keyType = results.getString("Key");
                                if (keyType != null && StringUtils.startsWithIgnoreCase(keyType, "PRI")) {
                                    final byte[][] rowVal = new byte[8][];
                                    rowVal[0] = Integer.toString(2).getBytes();
                                    rowVal[1] = results.getBytes("Field");
                                    String type = results.getString("Type");
                                    int size = stmt.getMaxFieldSize();
                                    int decimals = 0;
                                    if (type.indexOf("enum") != -1) {
                                        final String temp = type.substring(type.indexOf("("), type.indexOf(")"));
                                        final StringTokenizer tokenizer = new StringTokenizer(temp, ",");
                                        int maxLength = 0;
                                        while (tokenizer.hasMoreTokens()) {
                                            maxLength = Math.max(maxLength, tokenizer.nextToken().length() - 2);
                                        }
                                        size = maxLength;
                                        decimals = 0;
                                        type = "enum";
                                    }
                                    else if (type.indexOf("(") != -1) {
                                        if (type.indexOf(",") != -1) {
                                            size = Integer.parseInt(type.substring(type.indexOf("(") + 1, type.indexOf(",")));
                                            decimals = Integer.parseInt(type.substring(type.indexOf(",") + 1, type.indexOf(")")));
                                        }
                                        else {
                                            size = Integer.parseInt(type.substring(type.indexOf("(") + 1, type.indexOf(")")));
                                        }
                                        type = type.substring(0, type.indexOf("("));
                                    }
                                    final MysqlType ft = MysqlType.getByName(type.toUpperCase());
                                    rowVal[2] = DatabaseMetaData.this.s2b(String.valueOf(ft.getJdbcType()));
                                    rowVal[3] = DatabaseMetaData.this.s2b(type);
                                    rowVal[4] = Integer.toString(size + decimals).getBytes();
                                    rowVal[5] = Integer.toString(size + decimals).getBytes();
                                    rowVal[6] = Integer.toString(decimals).getBytes();
                                    rowVal[7] = Integer.toString(1).getBytes();
                                    rows.add(new ByteArrayRow(rowVal, DatabaseMetaData.this.getExceptionInterceptor()));
                                }
                            }
                        }
                        catch (SQLException sqlEx) {
                            if (!"42S02".equals(sqlEx.getSQLState())) {
                                throw sqlEx;
                            }
                        }
                        finally {
                            if (results != null) {
                                try {
                                    results.close();
                                }
                                catch (Exception ex) {}
                                results = null;
                            }
                        }
                    }
                }.doForAll();
            }
            finally {
                if (stmt != null) {
                    stmt.close();
                }
            }
            final ResultSet results = this.resultSetFactory.createFromResultsetRows(1007, 1004, new ResultsetRowsStatic(rows, new DefaultColumnDefinition(fields)));
            return results;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    private void getCallStmtParameterTypes(String catalog, String quotedProcName, final ProcedureType procType, final String parameterNamePattern, final List<Row> resultRows, final boolean forGetFunctionColumns) throws SQLException {
        Statement paramRetrievalStmt = null;
        ResultSet paramRetrievalRs = null;
        String parameterDef = null;
        byte[] procNameAsBytes = null;
        byte[] procCatAsBytes = null;
        boolean isProcedureInAnsiMode = false;
        String storageDefnDelims = null;
        String storageDefnClosures = null;
        try {
            paramRetrievalStmt = this.conn.getMetadataSafeStatement();
            final String oldCatalog = this.conn.getCatalog();
            if (this.conn.lowerCaseTableNames() && catalog != null && catalog.length() != 0 && oldCatalog != null && oldCatalog.length() != 0) {
                ResultSet rs = null;
                try {
                    this.conn.setCatalog(StringUtils.unQuoteIdentifier(catalog, this.quotedId));
                    rs = paramRetrievalStmt.executeQuery("SELECT DATABASE()");
                    rs.next();
                    catalog = rs.getString(1);
                }
                finally {
                    this.conn.setCatalog(oldCatalog);
                    if (rs != null) {
                        rs.close();
                    }
                }
            }
            if (paramRetrievalStmt.getMaxRows() != 0) {
                paramRetrievalStmt.setMaxRows(0);
            }
            int dotIndex = -1;
            if (!" ".equals(this.quotedId)) {
                dotIndex = StringUtils.indexOfIgnoreCase(0, quotedProcName, ".", this.quotedId, this.quotedId, this.session.getServerSession().isNoBackslashEscapesSet() ? StringUtils.SEARCH_MODE__MRK_COM_WS : StringUtils.SEARCH_MODE__ALL);
            }
            else {
                dotIndex = quotedProcName.indexOf(".");
            }
            String dbName = null;
            if (dotIndex != -1 && dotIndex + 1 < quotedProcName.length()) {
                dbName = quotedProcName.substring(0, dotIndex);
                quotedProcName = quotedProcName.substring(dotIndex + 1);
            }
            else {
                dbName = StringUtils.quoteIdentifier(catalog, this.quotedId, this.pedantic);
            }
            String tmpProcName = StringUtils.unQuoteIdentifier(quotedProcName, this.quotedId);
            procNameAsBytes = StringUtils.getBytes(tmpProcName, "UTF-8");
            tmpProcName = StringUtils.unQuoteIdentifier(dbName, this.quotedId);
            procCatAsBytes = StringUtils.getBytes(tmpProcName, "UTF-8");
            final StringBuilder procNameBuf = new StringBuilder();
            procNameBuf.append(dbName);
            procNameBuf.append('.');
            procNameBuf.append(quotedProcName);
            String fieldName = null;
            if (procType == ProcedureType.PROCEDURE) {
                paramRetrievalRs = paramRetrievalStmt.executeQuery("SHOW CREATE PROCEDURE " + procNameBuf.toString());
                fieldName = "Create Procedure";
            }
            else {
                paramRetrievalRs = paramRetrievalStmt.executeQuery("SHOW CREATE FUNCTION " + procNameBuf.toString());
                fieldName = "Create Function";
            }
            if (paramRetrievalRs.next()) {
                String procedureDef = paramRetrievalRs.getString(fieldName);
                if (!this.conn.getPropertySet().getBooleanProperty(PropertyKey.noAccessToProcedureBodies).getValue() && (procedureDef == null || procedureDef.length() == 0)) {
                    throw SQLError.createSQLException(Messages.getString("DatabaseMetaData.4"), "S1000", this.getExceptionInterceptor());
                }
                try {
                    final String sqlMode = paramRetrievalRs.getString("sql_mode");
                    if (StringUtils.indexOfIgnoreCase(sqlMode, "ANSI") != -1) {
                        isProcedureInAnsiMode = true;
                    }
                }
                catch (SQLException ex) {}
                final String identifierMarkers = isProcedureInAnsiMode ? "`\"" : "`";
                final String identifierAndStringMarkers = "'" + identifierMarkers;
                storageDefnDelims = "(" + identifierMarkers;
                storageDefnClosures = ")" + identifierMarkers;
                if (procedureDef != null && procedureDef.length() != 0) {
                    procedureDef = StringUtils.stripComments(procedureDef, identifierAndStringMarkers, identifierAndStringMarkers, true, false, true, true);
                    final int openParenIndex = StringUtils.indexOfIgnoreCase(0, procedureDef, "(", this.quotedId, this.quotedId, this.session.getServerSession().isNoBackslashEscapesSet() ? StringUtils.SEARCH_MODE__MRK_COM_WS : StringUtils.SEARCH_MODE__ALL);
                    int endOfParamDeclarationIndex = 0;
                    endOfParamDeclarationIndex = this.endPositionOfParameterDeclaration(openParenIndex, procedureDef, this.quotedId);
                    if (procType == ProcedureType.FUNCTION) {
                        final int returnsIndex = StringUtils.indexOfIgnoreCase(0, procedureDef, " RETURNS ", this.quotedId, this.quotedId, this.session.getServerSession().isNoBackslashEscapesSet() ? StringUtils.SEARCH_MODE__MRK_COM_WS : StringUtils.SEARCH_MODE__ALL);
                        final int endReturnsDef = this.findEndOfReturnsClause(procedureDef, returnsIndex);
                        int declarationStart;
                        for (declarationStart = returnsIndex + "RETURNS ".length(); declarationStart < procedureDef.length() && Character.isWhitespace(procedureDef.charAt(declarationStart)); ++declarationStart) {}
                        final String returnsDefn = procedureDef.substring(declarationStart, endReturnsDef).trim();
                        final TypeDescriptor returnDescriptor = new TypeDescriptor(returnsDefn, "YES");
                        resultRows.add(this.convertTypeDescriptorToProcedureRow(procNameAsBytes, procCatAsBytes, "", false, false, true, returnDescriptor, forGetFunctionColumns, 0));
                    }
                    if (openParenIndex == -1 || endOfParamDeclarationIndex == -1) {
                        throw SQLError.createSQLException(Messages.getString("DatabaseMetaData.5"), "S1000", this.getExceptionInterceptor());
                    }
                    parameterDef = procedureDef.substring(openParenIndex + 1, endOfParamDeclarationIndex);
                }
            }
        }
        finally {
            SQLException sqlExRethrow = null;
            if (paramRetrievalRs != null) {
                try {
                    paramRetrievalRs.close();
                }
                catch (SQLException sqlEx) {
                    sqlExRethrow = sqlEx;
                }
                paramRetrievalRs = null;
            }
            if (paramRetrievalStmt != null) {
                try {
                    paramRetrievalStmt.close();
                }
                catch (SQLException sqlEx) {
                    sqlExRethrow = sqlEx;
                }
                paramRetrievalStmt = null;
            }
            if (sqlExRethrow != null) {
                throw sqlExRethrow;
            }
        }
        if (parameterDef != null) {
            int ordinal = 1;
            final List<String> parseList = StringUtils.split(parameterDef, ",", storageDefnDelims, storageDefnClosures, true);
            for (int parseListLen = parseList.size(), i = 0; i < parseListLen; ++i) {
                String declaration = parseList.get(i);
                if (declaration.trim().length() == 0) {
                    break;
                }
                declaration = declaration.replaceAll("[\\t\\n\\x0B\\f\\r]", " ");
                final StringTokenizer declarationTok = new StringTokenizer(declaration, " \t");
                String paramName = null;
                boolean isOutParam = false;
                boolean isInParam = false;
                if (!declarationTok.hasMoreTokens()) {
                    throw SQLError.createSQLException(Messages.getString("DatabaseMetaData.8"), "S1000", this.getExceptionInterceptor());
                }
                final String possibleParamName = declarationTok.nextToken();
                if (possibleParamName.equalsIgnoreCase("OUT")) {
                    isOutParam = true;
                    if (!declarationTok.hasMoreTokens()) {
                        throw SQLError.createSQLException(Messages.getString("DatabaseMetaData.6"), "S1000", this.getExceptionInterceptor());
                    }
                    paramName = declarationTok.nextToken();
                }
                else if (possibleParamName.equalsIgnoreCase("INOUT")) {
                    isOutParam = true;
                    isInParam = true;
                    if (!declarationTok.hasMoreTokens()) {
                        throw SQLError.createSQLException(Messages.getString("DatabaseMetaData.6"), "S1000", this.getExceptionInterceptor());
                    }
                    paramName = declarationTok.nextToken();
                }
                else if (possibleParamName.equalsIgnoreCase("IN")) {
                    isOutParam = false;
                    isInParam = true;
                    if (!declarationTok.hasMoreTokens()) {
                        throw SQLError.createSQLException(Messages.getString("DatabaseMetaData.6"), "S1000", this.getExceptionInterceptor());
                    }
                    paramName = declarationTok.nextToken();
                }
                else {
                    isOutParam = false;
                    isInParam = true;
                    paramName = possibleParamName;
                }
                TypeDescriptor typeDesc = null;
                if (!declarationTok.hasMoreTokens()) {
                    throw SQLError.createSQLException(Messages.getString("DatabaseMetaData.7"), "S1000", this.getExceptionInterceptor());
                }
                final StringBuilder typeInfoBuf = new StringBuilder(declarationTok.nextToken());
                while (declarationTok.hasMoreTokens()) {
                    typeInfoBuf.append(" ");
                    typeInfoBuf.append(declarationTok.nextToken());
                }
                final String typeInfo = typeInfoBuf.toString();
                typeDesc = new TypeDescriptor(typeInfo, "YES");
                if ((paramName.startsWith("`") && paramName.endsWith("`")) || (isProcedureInAnsiMode && paramName.startsWith("\"") && paramName.endsWith("\""))) {
                    paramName = paramName.substring(1, paramName.length() - 1);
                }
                if (parameterNamePattern == null || StringUtils.wildCompareIgnoreCase(paramName, parameterNamePattern)) {
                    final Row row = this.convertTypeDescriptorToProcedureRow(procNameAsBytes, procCatAsBytes, paramName, isOutParam, isInParam, false, typeDesc, forGetFunctionColumns, ordinal++);
                    resultRows.add(row);
                }
            }
        }
    }
    
    private int endPositionOfParameterDeclaration(final int beginIndex, final String procedureDef, final String quoteChar) throws SQLException {
        int currentPos = beginIndex + 1;
        int parenDepth = 1;
        while (parenDepth > 0 && currentPos < procedureDef.length()) {
            final int closedParenIndex = StringUtils.indexOfIgnoreCase(currentPos, procedureDef, ")", quoteChar, quoteChar, this.session.getServerSession().isNoBackslashEscapesSet() ? StringUtils.SEARCH_MODE__MRK_COM_WS : StringUtils.SEARCH_MODE__ALL);
            if (closedParenIndex == -1) {
                throw SQLError.createSQLException(Messages.getString("DatabaseMetaData.5"), "S1000", this.getExceptionInterceptor());
            }
            final int nextOpenParenIndex = StringUtils.indexOfIgnoreCase(currentPos, procedureDef, "(", quoteChar, quoteChar, this.session.getServerSession().isNoBackslashEscapesSet() ? StringUtils.SEARCH_MODE__MRK_COM_WS : StringUtils.SEARCH_MODE__ALL);
            if (nextOpenParenIndex != -1 && nextOpenParenIndex < closedParenIndex) {
                ++parenDepth;
                currentPos = closedParenIndex + 1;
            }
            else {
                --parenDepth;
                currentPos = closedParenIndex;
            }
        }
        return currentPos;
    }
    
    private int findEndOfReturnsClause(final String procedureDefn, final int positionOfReturnKeyword) throws SQLException {
        final String openingMarkers = this.quotedId + "(";
        final String closingMarkers = this.quotedId + ")";
        final String[] tokens = { "LANGUAGE", "NOT", "DETERMINISTIC", "CONTAINS", "NO", "READ", "MODIFIES", "SQL", "COMMENT", "BEGIN", "RETURN" };
        final int startLookingAt = positionOfReturnKeyword + "RETURNS".length() + 1;
        int endOfReturn = -1;
        for (int i = 0; i < tokens.length; ++i) {
            final int nextEndOfReturn = StringUtils.indexOfIgnoreCase(startLookingAt, procedureDefn, tokens[i], openingMarkers, closingMarkers, this.session.getServerSession().isNoBackslashEscapesSet() ? StringUtils.SEARCH_MODE__MRK_COM_WS : StringUtils.SEARCH_MODE__ALL);
            if (nextEndOfReturn != -1 && (endOfReturn == -1 || nextEndOfReturn < endOfReturn)) {
                endOfReturn = nextEndOfReturn;
            }
        }
        if (endOfReturn != -1) {
            return endOfReturn;
        }
        endOfReturn = StringUtils.indexOfIgnoreCase(startLookingAt, procedureDefn, ":", openingMarkers, closingMarkers, this.session.getServerSession().isNoBackslashEscapesSet() ? StringUtils.SEARCH_MODE__MRK_COM_WS : StringUtils.SEARCH_MODE__ALL);
        if (endOfReturn != -1) {
            for (int i = endOfReturn; i > 0; --i) {
                if (Character.isWhitespace(procedureDefn.charAt(i))) {
                    return i;
                }
            }
        }
        throw SQLError.createSQLException(Messages.getString("DatabaseMetaData.5"), "S1000", this.getExceptionInterceptor());
    }
    
    private int getCascadeDeleteOption(final String cascadeOptions) {
        final int onDeletePos = cascadeOptions.indexOf("ON DELETE");
        if (onDeletePos != -1) {
            final String deleteOptions = cascadeOptions.substring(onDeletePos, cascadeOptions.length());
            if (deleteOptions.startsWith("ON DELETE CASCADE")) {
                return 0;
            }
            if (deleteOptions.startsWith("ON DELETE SET NULL")) {
                return 2;
            }
            if (deleteOptions.startsWith("ON DELETE RESTRICT")) {
                return 1;
            }
            if (deleteOptions.startsWith("ON DELETE NO ACTION")) {
                return 3;
            }
        }
        return 3;
    }
    
    private int getCascadeUpdateOption(final String cascadeOptions) {
        final int onUpdatePos = cascadeOptions.indexOf("ON UPDATE");
        if (onUpdatePos != -1) {
            final String updateOptions = cascadeOptions.substring(onUpdatePos, cascadeOptions.length());
            if (updateOptions.startsWith("ON UPDATE CASCADE")) {
                return 0;
            }
            if (updateOptions.startsWith("ON UPDATE SET NULL")) {
                return 2;
            }
            if (updateOptions.startsWith("ON UPDATE RESTRICT")) {
                return 1;
            }
            if (updateOptions.startsWith("ON UPDATE NO ACTION")) {
                return 3;
            }
        }
        return 3;
    }
    
    protected IteratorWithCleanup<String> getCatalogIterator(final String catalogSpec) throws SQLException {
        IteratorWithCleanup<String> allCatalogsIter;
        if (catalogSpec != null) {
            allCatalogsIter = new SingleStringIterator(this.pedantic ? catalogSpec : StringUtils.unQuoteIdentifier(catalogSpec, this.quotedId));
        }
        else if (this.nullCatalogMeansCurrent) {
            allCatalogsIter = new SingleStringIterator(this.database);
        }
        else {
            allCatalogsIter = new ResultSetIterator(this.getCatalogs(), 1);
        }
        return allCatalogsIter;
    }
    
    @Override
    public ResultSet getCatalogs() throws SQLException {
        try {
            ResultSet results = null;
            Statement stmt = null;
            try {
                stmt = this.conn.getMetadataSafeStatement();
                results = stmt.executeQuery("SHOW DATABASES");
                int catalogsCount = 0;
                if (results.last()) {
                    catalogsCount = results.getRow();
                    results.beforeFirst();
                }
                final List<String> resultsAsList = new ArrayList<String>(catalogsCount);
                while (results.next()) {
                    resultsAsList.add(results.getString(1));
                }
                Collections.sort(resultsAsList);
                final Field[] fields = { new Field("", "TABLE_CAT", this.metadataCollationIndex, this.metadataEncoding, MysqlType.VARCHAR, results.getMetaData().getColumnDisplaySize(1)) };
                final ArrayList<Row> tuples = new ArrayList<Row>(catalogsCount);
                for (final String cat : resultsAsList) {
                    final byte[][] rowVal = { this.s2b(cat) };
                    tuples.add(new ByteArrayRow(rowVal, this.getExceptionInterceptor()));
                }
                return this.resultSetFactory.createFromResultsetRows(1007, 1004, new ResultsetRowsStatic(tuples, new DefaultColumnDefinition(fields)));
            }
            finally {
                if (results != null) {
                    try {
                        results.close();
                    }
                    catch (SQLException sqlEx) {
                        AssertionFailedException.shouldNotHappen(sqlEx);
                    }
                    results = null;
                }
                if (stmt != null) {
                    try {
                        stmt.close();
                    }
                    catch (SQLException sqlEx) {
                        AssertionFailedException.shouldNotHappen(sqlEx);
                    }
                    stmt = null;
                }
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public String getCatalogSeparator() throws SQLException {
        try {
            return ".";
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public String getCatalogTerm() throws SQLException {
        try {
            return "database";
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public ResultSet getColumnPrivileges(final String catalog, final String schema, final String table, final String columnNamePattern) throws SQLException {
        try {
            final Field[] fields = { new Field("", "TABLE_CAT", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 64), new Field("", "TABLE_SCHEM", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 1), new Field("", "TABLE_NAME", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 64), new Field("", "COLUMN_NAME", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 64), new Field("", "GRANTOR", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 77), new Field("", "GRANTEE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 77), new Field("", "PRIVILEGE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 64), new Field("", "IS_GRANTABLE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 3) };
            final StringBuilder grantQueryBuf = new StringBuilder("SELECT c.host, c.db, t.grantor, c.user, c.table_name, c.column_name, c.column_priv");
            grantQueryBuf.append(" FROM mysql.columns_priv c, mysql.tables_priv t");
            grantQueryBuf.append(" WHERE c.host = t.host AND c.db = t.db AND c.table_name = t.table_name");
            if (catalog != null) {
                grantQueryBuf.append(" AND c.db LIKE ?");
            }
            grantQueryBuf.append(" AND c.table_name = ?");
            if (columnNamePattern != null) {
                grantQueryBuf.append(" AND c.column_name LIKE ?");
            }
            PreparedStatement pStmt = null;
            ResultSet results = null;
            final ArrayList<Row> grantRows = new ArrayList<Row>();
            try {
                pStmt = this.prepareMetaDataSafeStatement(grantQueryBuf.toString());
                int nextId = 1;
                if (catalog != null) {
                    pStmt.setString(nextId++, catalog);
                }
                pStmt.setString(nextId++, table);
                if (columnNamePattern != null) {
                    pStmt.setString(nextId, columnNamePattern);
                }
                results = pStmt.executeQuery();
                while (results.next()) {
                    final String host = results.getString(1);
                    final String db = results.getString(2);
                    final String grantor = results.getString(3);
                    String user = results.getString(4);
                    if (user == null || user.length() == 0) {
                        user = "%";
                    }
                    final StringBuilder fullUser = new StringBuilder(user);
                    if (host != null && this.useHostsInPrivileges) {
                        fullUser.append("@");
                        fullUser.append(host);
                    }
                    final String columnName = results.getString(6);
                    String allPrivileges = results.getString(7);
                    if (allPrivileges != null) {
                        allPrivileges = allPrivileges.toUpperCase(Locale.ENGLISH);
                        final StringTokenizer st = new StringTokenizer(allPrivileges, ",");
                        while (st.hasMoreTokens()) {
                            final String privilege = st.nextToken().trim();
                            final byte[][] tuple = { this.s2b(db), null, this.s2b(table), this.s2b(columnName), (grantor != null) ? this.s2b(grantor) : null, this.s2b(fullUser.toString()), this.s2b(privilege), null };
                            grantRows.add(new ByteArrayRow(tuple, this.getExceptionInterceptor()));
                        }
                    }
                }
            }
            finally {
                if (results != null) {
                    try {
                        results.close();
                    }
                    catch (Exception ex2) {}
                    results = null;
                }
                if (pStmt != null) {
                    try {
                        pStmt.close();
                    }
                    catch (Exception ex3) {}
                    pStmt = null;
                }
            }
            return this.resultSetFactory.createFromResultsetRows(1007, 1004, new ResultsetRowsStatic(grantRows, new DefaultColumnDefinition(fields)));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public ResultSet getColumns(final String catalog, final String schemaPattern, final String tableNamePattern, final String columnNamePattern) throws SQLException {
        try {
            final String colPattern = columnNamePattern;
            final Field[] fields = this.createColumnsFields();
            final ArrayList<Row> rows = new ArrayList<Row>();
            final Statement stmt = this.conn.getMetadataSafeStatement();
            try {
                new IterateBlock<String>(this.getCatalogIterator(catalog)) {
                    @Override
                    void forEach(final String catalogStr) throws SQLException {
                        final ArrayList<String> tableNameList = new ArrayList<String>();
                        ResultSet tables = null;
                        try {
                            tables = DatabaseMetaData.this.getTables(catalogStr, schemaPattern, tableNamePattern, new String[0]);
                            while (tables.next()) {
                                final String tableNameFromList = tables.getString("TABLE_NAME");
                                tableNameList.add(tableNameFromList);
                            }
                        }
                        finally {
                            if (tables != null) {
                                try {
                                    tables.close();
                                }
                                catch (Exception sqlEx) {
                                    AssertionFailedException.shouldNotHappen(sqlEx);
                                }
                                tables = null;
                            }
                        }
                        for (final String tableName : tableNameList) {
                            ResultSet results = null;
                            try {
                                final StringBuilder queryBuf = new StringBuilder("SHOW FULL COLUMNS FROM ");
                                queryBuf.append(StringUtils.quoteIdentifier(tableName, DatabaseMetaData.this.quotedId, DatabaseMetaData.this.pedantic));
                                queryBuf.append(" FROM ");
                                queryBuf.append(StringUtils.quoteIdentifier(catalogStr, DatabaseMetaData.this.quotedId, DatabaseMetaData.this.pedantic));
                                if (colPattern != null) {
                                    queryBuf.append(" LIKE ");
                                    queryBuf.append(StringUtils.quoteIdentifier(colPattern, "'", true));
                                }
                                boolean fixUpOrdinalsRequired = false;
                                Map<String, Integer> ordinalFixUpMap = null;
                                if (colPattern != null && !colPattern.equals("%")) {
                                    fixUpOrdinalsRequired = true;
                                    final StringBuilder fullColumnQueryBuf = new StringBuilder("SHOW FULL COLUMNS FROM ");
                                    fullColumnQueryBuf.append(StringUtils.quoteIdentifier(tableName, DatabaseMetaData.this.quotedId, DatabaseMetaData.this.pedantic));
                                    fullColumnQueryBuf.append(" FROM ");
                                    fullColumnQueryBuf.append(StringUtils.quoteIdentifier(catalogStr, DatabaseMetaData.this.quotedId, DatabaseMetaData.this.pedantic));
                                    results = stmt.executeQuery(fullColumnQueryBuf.toString());
                                    ordinalFixUpMap = new HashMap<String, Integer>();
                                    int fullOrdinalPos = 1;
                                    while (results.next()) {
                                        final String fullOrdColName = results.getString("Field");
                                        ordinalFixUpMap.put(fullOrdColName, fullOrdinalPos++);
                                    }
                                    results.close();
                                }
                                results = stmt.executeQuery(queryBuf.toString());
                                int ordPos = 1;
                                while (results.next()) {
                                    final TypeDescriptor typeDesc = new TypeDescriptor(results.getString("Type"), results.getString("Null"));
                                    final byte[][] rowVal = new byte[24][];
                                    rowVal[0] = DatabaseMetaData.this.s2b(catalogStr);
                                    rowVal[1] = null;
                                    rowVal[2] = DatabaseMetaData.this.s2b(tableName);
                                    rowVal[3] = results.getBytes("Field");
                                    rowVal[4] = Short.toString((short)typeDesc.mysqlType.getJdbcType()).getBytes();
                                    rowVal[5] = DatabaseMetaData.this.s2b(typeDesc.mysqlType.getName());
                                    if (typeDesc.columnSize == null) {
                                        rowVal[6] = null;
                                    }
                                    else {
                                        final String collation = results.getString("Collation");
                                        int mbminlen = 1;
                                        if (collation != null) {
                                            if (collation.indexOf("ucs2") > -1 || collation.indexOf("utf16") > -1) {
                                                mbminlen = 2;
                                            }
                                            else if (collation.indexOf("utf32") > -1) {
                                                mbminlen = 4;
                                            }
                                        }
                                        rowVal[6] = ((mbminlen == 1) ? DatabaseMetaData.this.s2b(typeDesc.columnSize.toString()) : DatabaseMetaData.this.s2b(Integer.valueOf(typeDesc.columnSize / mbminlen).toString()));
                                    }
                                    rowVal[7] = DatabaseMetaData.this.s2b(Integer.toString(typeDesc.bufferLength));
                                    rowVal[8] = (byte[])((typeDesc.decimalDigits == null) ? null : DatabaseMetaData.this.s2b(typeDesc.decimalDigits.toString()));
                                    rowVal[9] = DatabaseMetaData.this.s2b(Integer.toString(typeDesc.numPrecRadix));
                                    rowVal[10] = DatabaseMetaData.this.s2b(Integer.toString(typeDesc.nullability));
                                    try {
                                        rowVal[11] = results.getBytes("Comment");
                                    }
                                    catch (Exception E) {
                                        rowVal[11] = new byte[0];
                                    }
                                    rowVal[12] = results.getBytes("Default");
                                    rowVal[13] = new byte[] { 48 };
                                    rowVal[14] = new byte[] { 48 };
                                    if (StringUtils.indexOfIgnoreCase(typeDesc.mysqlType.getName(), "CHAR") != -1 || StringUtils.indexOfIgnoreCase(typeDesc.mysqlType.getName(), "BLOB") != -1 || StringUtils.indexOfIgnoreCase(typeDesc.mysqlType.getName(), "TEXT") != -1 || StringUtils.indexOfIgnoreCase(typeDesc.mysqlType.getName(), "ENUM") != -1 || StringUtils.indexOfIgnoreCase(typeDesc.mysqlType.getName(), "SET") != -1 || StringUtils.indexOfIgnoreCase(typeDesc.mysqlType.getName(), "BINARY") != -1) {
                                        rowVal[15] = rowVal[6];
                                    }
                                    else {
                                        rowVal[15] = null;
                                    }
                                    if (!fixUpOrdinalsRequired) {
                                        rowVal[16] = Integer.toString(ordPos++).getBytes();
                                    }
                                    else {
                                        final String origColName = results.getString("Field");
                                        final Integer realOrdinal = ordinalFixUpMap.get(origColName);
                                        if (realOrdinal == null) {
                                            throw SQLError.createSQLException(Messages.getString("DatabaseMetaData.10"), "S1000", DatabaseMetaData.this.getExceptionInterceptor());
                                        }
                                        rowVal[16] = realOrdinal.toString().getBytes();
                                    }
                                    rowVal[17] = DatabaseMetaData.this.s2b(typeDesc.isNullable);
                                    rowVal[19] = (rowVal[18] = null);
                                    rowVal[21] = (rowVal[20] = null);
                                    rowVal[22] = DatabaseMetaData.this.s2b("");
                                    final String extra = results.getString("Extra");
                                    if (extra != null) {
                                        rowVal[22] = DatabaseMetaData.this.s2b((StringUtils.indexOfIgnoreCase(extra, "auto_increment") != -1) ? "YES" : "NO");
                                        rowVal[23] = DatabaseMetaData.this.s2b((StringUtils.indexOfIgnoreCase(extra, "generated") != -1) ? "YES" : "NO");
                                    }
                                    rows.add(new ByteArrayRow(rowVal, DatabaseMetaData.this.getExceptionInterceptor()));
                                }
                            }
                            finally {
                                if (results != null) {
                                    try {
                                        results.close();
                                    }
                                    catch (Exception ex) {}
                                    results = null;
                                }
                            }
                        }
                    }
                }.doForAll();
            }
            finally {
                if (stmt != null) {
                    stmt.close();
                }
            }
            final ResultSet results = this.resultSetFactory.createFromResultsetRows(1007, 1004, new ResultsetRowsStatic(rows, new DefaultColumnDefinition(fields)));
            return results;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    protected Field[] createColumnsFields() {
        final Field[] fields = { new Field("", "TABLE_CAT", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 255), new Field("", "TABLE_SCHEM", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 0), new Field("", "TABLE_NAME", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 255), new Field("", "COLUMN_NAME", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 32), new Field("", "DATA_TYPE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.INT, 5), new Field("", "TYPE_NAME", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 16), new Field("", "COLUMN_SIZE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.INT, Integer.toString(Integer.MAX_VALUE).length()), new Field("", "BUFFER_LENGTH", this.metadataCollationIndex, this.metadataEncoding, MysqlType.INT, 10), new Field("", "DECIMAL_DIGITS", this.metadataCollationIndex, this.metadataEncoding, MysqlType.INT, 10), new Field("", "NUM_PREC_RADIX", this.metadataCollationIndex, this.metadataEncoding, MysqlType.INT, 10), new Field("", "NULLABLE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.INT, 10), new Field("", "REMARKS", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 0), new Field("", "COLUMN_DEF", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 0), new Field("", "SQL_DATA_TYPE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.INT, 10), new Field("", "SQL_DATETIME_SUB", this.metadataCollationIndex, this.metadataEncoding, MysqlType.INT, 10), new Field("", "CHAR_OCTET_LENGTH", this.metadataCollationIndex, this.metadataEncoding, MysqlType.INT, Integer.toString(Integer.MAX_VALUE).length()), new Field("", "ORDINAL_POSITION", this.metadataCollationIndex, this.metadataEncoding, MysqlType.INT, 10), new Field("", "IS_NULLABLE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 3), new Field("", "SCOPE_CATALOG", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 255), new Field("", "SCOPE_SCHEMA", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 255), new Field("", "SCOPE_TABLE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 255), new Field("", "SOURCE_DATA_TYPE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.SMALLINT, 10), new Field("", "IS_AUTOINCREMENT", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 3), new Field("", "IS_GENERATEDCOLUMN", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 3) };
        return fields;
    }
    
    @Override
    public Connection getConnection() throws SQLException {
        try {
            return this.conn;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public ResultSet getCrossReference(final String primaryCatalog, final String primarySchema, final String primaryTable, final String foreignCatalog, final String foreignSchema, final String foreignTable) throws SQLException {
        try {
            if (primaryTable == null) {
                throw SQLError.createSQLException(Messages.getString("DatabaseMetaData.2"), "S1009", this.getExceptionInterceptor());
            }
            final Field[] fields = this.createFkMetadataFields();
            final ArrayList<Row> tuples = new ArrayList<Row>();
            final Statement stmt = this.conn.getMetadataSafeStatement();
            try {
                new IterateBlock<String>(this.getCatalogIterator(foreignCatalog)) {
                    @Override
                    void forEach(final String catalogStr) throws SQLException {
                        ResultSet fkresults = null;
                        try {
                            fkresults = DatabaseMetaData.this.extractForeignKeyFromCreateTable(catalogStr, null);
                            final String foreignTableWithCase = DatabaseMetaData.this.getTableNameWithCase(foreignTable);
                            final String primaryTableWithCase = DatabaseMetaData.this.getTableNameWithCase(primaryTable);
                            while (fkresults.next()) {
                                final String tableType = fkresults.getString("Type");
                                if (tableType != null && (tableType.equalsIgnoreCase("innodb") || tableType.equalsIgnoreCase("SUPPORTS_FK"))) {
                                    final String comment = fkresults.getString("Comment").trim();
                                    if (comment == null) {
                                        continue;
                                    }
                                    final StringTokenizer commentTokens = new StringTokenizer(comment, ";", false);
                                    if (commentTokens.hasMoreTokens()) {
                                        commentTokens.nextToken();
                                    }
                                    while (commentTokens.hasMoreTokens()) {
                                        final String keys = commentTokens.nextToken();
                                        final LocalAndReferencedColumns parsedInfo = DatabaseMetaData.this.parseTableStatusIntoLocalAndReferencedColumns(keys);
                                        int keySeq = 0;
                                        final Iterator<String> referencingColumns = parsedInfo.localColumnsList.iterator();
                                        final Iterator<String> referencedColumns = parsedInfo.referencedColumnsList.iterator();
                                        while (referencingColumns.hasNext()) {
                                            final String referencingColumn = StringUtils.unQuoteIdentifier(referencingColumns.next(), DatabaseMetaData.this.quotedId);
                                            final byte[][] tuple = new byte[14][];
                                            tuple[4] = (byte[])((foreignCatalog == null) ? null : DatabaseMetaData.this.s2b(foreignCatalog));
                                            tuple[5] = (byte[])((foreignSchema == null) ? null : DatabaseMetaData.this.s2b(foreignSchema));
                                            final String dummy = fkresults.getString("Name");
                                            if (dummy.compareTo(foreignTableWithCase) != 0) {
                                                continue;
                                            }
                                            tuple[6] = DatabaseMetaData.this.s2b(dummy);
                                            tuple[7] = DatabaseMetaData.this.s2b(referencingColumn);
                                            tuple[0] = (byte[])((primaryCatalog == null) ? null : DatabaseMetaData.this.s2b(primaryCatalog));
                                            tuple[1] = (byte[])((primarySchema == null) ? null : DatabaseMetaData.this.s2b(primarySchema));
                                            if (parsedInfo.referencedTable.compareTo(primaryTableWithCase) != 0) {
                                                continue;
                                            }
                                            tuple[2] = DatabaseMetaData.this.s2b(parsedInfo.referencedTable);
                                            tuple[3] = DatabaseMetaData.this.s2b(StringUtils.unQuoteIdentifier(referencedColumns.next(), DatabaseMetaData.this.quotedId));
                                            tuple[8] = Integer.toString(keySeq).getBytes();
                                            final int[] actions = DatabaseMetaData.this.getForeignKeyActions(keys);
                                            tuple[9] = Integer.toString(actions[1]).getBytes();
                                            tuple[10] = Integer.toString(actions[0]).getBytes();
                                            tuple[12] = (tuple[11] = null);
                                            tuple[13] = Integer.toString(7).getBytes();
                                            tuples.add(new ByteArrayRow(tuple, DatabaseMetaData.this.getExceptionInterceptor()));
                                            ++keySeq;
                                        }
                                    }
                                }
                            }
                        }
                        finally {
                            if (fkresults != null) {
                                try {
                                    fkresults.close();
                                }
                                catch (Exception sqlEx) {
                                    AssertionFailedException.shouldNotHappen(sqlEx);
                                }
                                fkresults = null;
                            }
                        }
                    }
                }.doForAll();
            }
            finally {
                if (stmt != null) {
                    stmt.close();
                }
            }
            final ResultSet results = this.resultSetFactory.createFromResultsetRows(1007, 1004, new ResultsetRowsStatic(tuples, new DefaultColumnDefinition(fields)));
            return results;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    protected Field[] createFkMetadataFields() {
        final Field[] fields = { new Field("", "PKTABLE_CAT", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 255), new Field("", "PKTABLE_SCHEM", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 0), new Field("", "PKTABLE_NAME", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 255), new Field("", "PKCOLUMN_NAME", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 32), new Field("", "FKTABLE_CAT", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 255), new Field("", "FKTABLE_SCHEM", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 0), new Field("", "FKTABLE_NAME", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 255), new Field("", "FKCOLUMN_NAME", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 32), new Field("", "KEY_SEQ", this.metadataCollationIndex, this.metadataEncoding, MysqlType.SMALLINT, 2), new Field("", "UPDATE_RULE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.SMALLINT, 2), new Field("", "DELETE_RULE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.SMALLINT, 2), new Field("", "FK_NAME", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 0), new Field("", "PK_NAME", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 0), new Field("", "DEFERRABILITY", this.metadataCollationIndex, this.metadataEncoding, MysqlType.SMALLINT, 2) };
        return fields;
    }
    
    @Override
    public int getDatabaseMajorVersion() throws SQLException {
        try {
            return this.conn.getServerVersion().getMajor();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public int getDatabaseMinorVersion() throws SQLException {
        try {
            return this.conn.getServerVersion().getMinor();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public String getDatabaseProductName() throws SQLException {
        try {
            return "MySQL";
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public String getDatabaseProductVersion() throws SQLException {
        try {
            return this.conn.getServerVersion().toString();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public int getDefaultTransactionIsolation() throws SQLException {
        try {
            return 2;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public int getDriverMajorVersion() {
        return NonRegisteringDriver.getMajorVersionInternal();
    }
    
    @Override
    public int getDriverMinorVersion() {
        return NonRegisteringDriver.getMinorVersionInternal();
    }
    
    @Override
    public String getDriverName() throws SQLException {
        try {
            return "MySQL Connector/J";
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public String getDriverVersion() throws SQLException {
        try {
            return "mysql-connector-java-8.0.14 (Revision: 36534fa273b4d7824a8668ca685465cf8eaeadd9)";
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public ResultSet getExportedKeys(final String catalog, final String schema, final String table) throws SQLException {
        try {
            if (table == null) {
                throw SQLError.createSQLException(Messages.getString("DatabaseMetaData.2"), "S1009", this.getExceptionInterceptor());
            }
            final Field[] fields = this.createFkMetadataFields();
            final ArrayList<Row> rows = new ArrayList<Row>();
            final Statement stmt = this.conn.getMetadataSafeStatement();
            try {
                new IterateBlock<String>(this.getCatalogIterator(catalog)) {
                    @Override
                    void forEach(final String catalogStr) throws SQLException {
                        ResultSet fkresults = null;
                        try {
                            fkresults = DatabaseMetaData.this.extractForeignKeyFromCreateTable(catalogStr, null);
                            final String tableNameWithCase = DatabaseMetaData.this.getTableNameWithCase(table);
                            while (fkresults.next()) {
                                final String tableType = fkresults.getString("Type");
                                if (tableType != null && (tableType.equalsIgnoreCase("innodb") || tableType.equalsIgnoreCase("SUPPORTS_FK"))) {
                                    final String comment = fkresults.getString("Comment").trim();
                                    if (comment == null) {
                                        continue;
                                    }
                                    final StringTokenizer commentTokens = new StringTokenizer(comment, ";", false);
                                    if (!commentTokens.hasMoreTokens()) {
                                        continue;
                                    }
                                    commentTokens.nextToken();
                                    while (commentTokens.hasMoreTokens()) {
                                        final String keys = commentTokens.nextToken();
                                        DatabaseMetaData.this.getExportKeyResults(catalogStr, tableNameWithCase, keys, rows, fkresults.getString("Name"));
                                    }
                                }
                            }
                        }
                        finally {
                            if (fkresults != null) {
                                try {
                                    fkresults.close();
                                }
                                catch (SQLException sqlEx) {
                                    AssertionFailedException.shouldNotHappen(sqlEx);
                                }
                                fkresults = null;
                            }
                        }
                    }
                }.doForAll();
            }
            finally {
                if (stmt != null) {
                    stmt.close();
                }
            }
            final ResultSet results = this.resultSetFactory.createFromResultsetRows(1007, 1004, new ResultsetRowsStatic(rows, new DefaultColumnDefinition(fields)));
            return results;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    protected void getExportKeyResults(final String catalog, final String exportingTable, final String keysComment, final List<Row> tuples, final String fkTableName) throws SQLException {
        this.getResultsImpl(catalog, exportingTable, keysComment, tuples, fkTableName, true);
    }
    
    @Override
    public String getExtraNameCharacters() throws SQLException {
        try {
            return "#@";
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    protected int[] getForeignKeyActions(final String commentString) {
        final int[] actions = { 3, 3 };
        final int lastParenIndex = commentString.lastIndexOf(")");
        if (lastParenIndex != commentString.length() - 1) {
            final String cascadeOptions = commentString.substring(lastParenIndex + 1).trim().toUpperCase(Locale.ENGLISH);
            actions[0] = this.getCascadeDeleteOption(cascadeOptions);
            actions[1] = this.getCascadeUpdateOption(cascadeOptions);
        }
        return actions;
    }
    
    @Override
    public String getIdentifierQuoteString() throws SQLException {
        try {
            return this.session.getIdentifierQuoteString();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public ResultSet getImportedKeys(final String catalog, final String schema, final String table) throws SQLException {
        try {
            if (table == null) {
                throw SQLError.createSQLException(Messages.getString("DatabaseMetaData.2"), "S1009", this.getExceptionInterceptor());
            }
            final Field[] fields = this.createFkMetadataFields();
            final ArrayList<Row> rows = new ArrayList<Row>();
            final Statement stmt = this.conn.getMetadataSafeStatement();
            try {
                new IterateBlock<String>(this.getCatalogIterator(catalog)) {
                    @Override
                    void forEach(final String catalogStr) throws SQLException {
                        ResultSet fkresults = null;
                        try {
                            fkresults = DatabaseMetaData.this.extractForeignKeyFromCreateTable(catalogStr, table);
                            while (fkresults.next()) {
                                final String tableType = fkresults.getString("Type");
                                if (tableType != null && (tableType.equalsIgnoreCase("innodb") || tableType.equalsIgnoreCase("SUPPORTS_FK"))) {
                                    final String comment = fkresults.getString("Comment").trim();
                                    if (comment == null) {
                                        continue;
                                    }
                                    final StringTokenizer commentTokens = new StringTokenizer(comment, ";", false);
                                    if (!commentTokens.hasMoreTokens()) {
                                        continue;
                                    }
                                    commentTokens.nextToken();
                                    while (commentTokens.hasMoreTokens()) {
                                        final String keys = commentTokens.nextToken();
                                        DatabaseMetaData.this.getImportKeyResults(catalogStr, table, keys, rows);
                                    }
                                }
                            }
                        }
                        finally {
                            if (fkresults != null) {
                                try {
                                    fkresults.close();
                                }
                                catch (SQLException sqlEx) {
                                    AssertionFailedException.shouldNotHappen(sqlEx);
                                }
                                fkresults = null;
                            }
                        }
                    }
                }.doForAll();
            }
            finally {
                if (stmt != null) {
                    stmt.close();
                }
            }
            final ResultSet results = this.resultSetFactory.createFromResultsetRows(1007, 1004, new ResultsetRowsStatic(rows, new DefaultColumnDefinition(fields)));
            return results;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    protected void getImportKeyResults(final String catalog, final String importingTable, final String keysComment, final List<Row> tuples) throws SQLException {
        this.getResultsImpl(catalog, importingTable, keysComment, tuples, null, false);
    }
    
    @Override
    public ResultSet getIndexInfo(final String catalog, final String schema, final String table, final boolean unique, final boolean approximate) throws SQLException {
        try {
            final Field[] fields = this.createIndexInfoFields();
            final SortedMap<IndexMetaDataKey, Row> sortedRows = new TreeMap<IndexMetaDataKey, Row>();
            final ArrayList<Row> rows = new ArrayList<Row>();
            final Statement stmt = this.conn.getMetadataSafeStatement();
            try {
                new IterateBlock<String>(this.getCatalogIterator(catalog)) {
                    @Override
                    void forEach(final String catalogStr) throws SQLException {
                        ResultSet results = null;
                        try {
                            final StringBuilder queryBuf = new StringBuilder("SHOW INDEX FROM ");
                            queryBuf.append(StringUtils.quoteIdentifier(table, DatabaseMetaData.this.quotedId, DatabaseMetaData.this.pedantic));
                            queryBuf.append(" FROM ");
                            queryBuf.append(StringUtils.quoteIdentifier(catalogStr, DatabaseMetaData.this.quotedId, DatabaseMetaData.this.pedantic));
                            try {
                                results = stmt.executeQuery(queryBuf.toString());
                            }
                            catch (SQLException sqlEx) {
                                final int errorCode = sqlEx.getErrorCode();
                                if (!"42S02".equals(sqlEx.getSQLState()) && errorCode != 1146) {
                                    throw sqlEx;
                                }
                            }
                            while (results != null && results.next()) {
                                final byte[][] row = new byte[14][];
                                row[0] = ((catalogStr == null) ? new byte[0] : DatabaseMetaData.this.s2b(catalogStr));
                                row[1] = null;
                                row[2] = results.getBytes("Table");
                                final boolean indexIsUnique = results.getInt("Non_unique") == 0;
                                row[3] = (indexIsUnique ? DatabaseMetaData.this.s2b("false") : DatabaseMetaData.this.s2b("true"));
                                row[4] = new byte[0];
                                row[5] = results.getBytes("Key_name");
                                final short indexType = 3;
                                row[6] = Integer.toString(indexType).getBytes();
                                row[7] = results.getBytes("Seq_in_index");
                                row[8] = results.getBytes("Column_name");
                                row[9] = results.getBytes("Collation");
                                final long cardinality = results.getLong("Cardinality");
                                row[10] = DatabaseMetaData.this.s2b(String.valueOf(cardinality));
                                row[11] = DatabaseMetaData.this.s2b("0");
                                row[12] = null;
                                final IndexMetaDataKey indexInfoKey = new IndexMetaDataKey(!indexIsUnique, indexType, results.getString("Key_name").toLowerCase(), results.getShort("Seq_in_index"));
                                if (unique) {
                                    if (!indexIsUnique) {
                                        continue;
                                    }
                                    sortedRows.put(indexInfoKey, new ByteArrayRow(row, DatabaseMetaData.this.getExceptionInterceptor()));
                                }
                                else {
                                    sortedRows.put(indexInfoKey, new ByteArrayRow(row, DatabaseMetaData.this.getExceptionInterceptor()));
                                }
                            }
                        }
                        finally {
                            if (results != null) {
                                try {
                                    results.close();
                                }
                                catch (Exception ex) {}
                                results = null;
                            }
                        }
                    }
                }.doForAll();
                final Iterator<Row> sortedRowsIterator = sortedRows.values().iterator();
                while (sortedRowsIterator.hasNext()) {
                    rows.add(sortedRowsIterator.next());
                }
                final ResultSet indexInfo = this.resultSetFactory.createFromResultsetRows(1007, 1004, new ResultsetRowsStatic(rows, new DefaultColumnDefinition(fields)));
                return indexInfo;
            }
            finally {
                if (stmt != null) {
                    stmt.close();
                }
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    protected Field[] createIndexInfoFields() {
        final Field[] fields = { new Field("", "TABLE_CAT", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 255), new Field("", "TABLE_SCHEM", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 0), new Field("", "TABLE_NAME", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 255), new Field("", "NON_UNIQUE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.BOOLEAN, 4), new Field("", "INDEX_QUALIFIER", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 1), new Field("", "INDEX_NAME", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 32), new Field("", "TYPE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.SMALLINT, 32), new Field("", "ORDINAL_POSITION", this.metadataCollationIndex, this.metadataEncoding, MysqlType.SMALLINT, 5), new Field("", "COLUMN_NAME", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 32), new Field("", "ASC_OR_DESC", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 1), new Field("", "CARDINALITY", this.metadataCollationIndex, this.metadataEncoding, MysqlType.BIGINT, 20), new Field("", "PAGES", this.metadataCollationIndex, this.metadataEncoding, MysqlType.BIGINT, 20), new Field("", "FILTER_CONDITION", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 32) };
        return fields;
    }
    
    @Override
    public int getJDBCMajorVersion() throws SQLException {
        try {
            return 4;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public int getJDBCMinorVersion() throws SQLException {
        try {
            return 2;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public int getMaxBinaryLiteralLength() throws SQLException {
        try {
            return 16777208;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public int getMaxCatalogNameLength() throws SQLException {
        try {
            return 32;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public int getMaxCharLiteralLength() throws SQLException {
        try {
            return 16777208;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public int getMaxColumnNameLength() throws SQLException {
        try {
            return 64;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public int getMaxColumnsInGroupBy() throws SQLException {
        try {
            return 64;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public int getMaxColumnsInIndex() throws SQLException {
        try {
            return 16;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public int getMaxColumnsInOrderBy() throws SQLException {
        try {
            return 64;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public int getMaxColumnsInSelect() throws SQLException {
        try {
            return 256;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public int getMaxColumnsInTable() throws SQLException {
        try {
            return 512;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public int getMaxConnections() throws SQLException {
        try {
            return 0;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public int getMaxCursorNameLength() throws SQLException {
        try {
            return 64;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public int getMaxIndexLength() throws SQLException {
        try {
            return 256;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public int getMaxProcedureNameLength() throws SQLException {
        try {
            return 0;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public int getMaxRowSize() throws SQLException {
        try {
            return 2147483639;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public int getMaxSchemaNameLength() throws SQLException {
        try {
            return 0;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public int getMaxStatementLength() throws SQLException {
        try {
            return DatabaseMetaData.maxBufferSize - 4;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public int getMaxStatements() throws SQLException {
        try {
            return 0;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public int getMaxTableNameLength() throws SQLException {
        try {
            return 64;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public int getMaxTablesInSelect() throws SQLException {
        try {
            return 256;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public int getMaxUserNameLength() throws SQLException {
        try {
            return 16;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public String getNumericFunctions() throws SQLException {
        try {
            return "ABS,ACOS,ASIN,ATAN,ATAN2,BIT_COUNT,CEILING,COS,COT,DEGREES,EXP,FLOOR,LOG,LOG10,MAX,MIN,MOD,PI,POW,POWER,RADIANS,RAND,ROUND,SIN,SQRT,TAN,TRUNCATE";
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public ResultSet getPrimaryKeys(final String catalog, final String schema, final String table) throws SQLException {
        try {
            final Field[] fields = { new Field("", "TABLE_CAT", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 255), new Field("", "TABLE_SCHEM", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 0), new Field("", "TABLE_NAME", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 255), new Field("", "COLUMN_NAME", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 32), new Field("", "KEY_SEQ", this.metadataCollationIndex, this.metadataEncoding, MysqlType.SMALLINT, 5), new Field("", "PK_NAME", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 32) };
            if (table == null) {
                throw SQLError.createSQLException(Messages.getString("DatabaseMetaData.2"), "S1009", this.getExceptionInterceptor());
            }
            final ArrayList<Row> rows = new ArrayList<Row>();
            final Statement stmt = this.conn.getMetadataSafeStatement();
            try {
                new IterateBlock<String>(this.getCatalogIterator(catalog)) {
                    @Override
                    void forEach(final String catalogStr) throws SQLException {
                        ResultSet rs = null;
                        try {
                            final StringBuilder queryBuf = new StringBuilder("SHOW KEYS FROM ");
                            queryBuf.append(StringUtils.quoteIdentifier(table, DatabaseMetaData.this.quotedId, DatabaseMetaData.this.pedantic));
                            queryBuf.append(" FROM ");
                            queryBuf.append(StringUtils.quoteIdentifier(catalogStr, DatabaseMetaData.this.quotedId, DatabaseMetaData.this.pedantic));
                            rs = stmt.executeQuery(queryBuf.toString());
                            final TreeMap<String, byte[][]> sortMap = new TreeMap<String, byte[][]>();
                            while (rs.next()) {
                                final String keyType = rs.getString("Key_name");
                                if (keyType != null && (keyType.equalsIgnoreCase("PRIMARY") || keyType.equalsIgnoreCase("PRI"))) {
                                    final byte[][] tuple = { (catalogStr == null) ? new byte[0] : DatabaseMetaData.this.s2b(catalogStr), null, DatabaseMetaData.this.s2b(table), null, null, null };
                                    final String columnName = rs.getString("Column_name");
                                    tuple[3] = DatabaseMetaData.this.s2b(columnName);
                                    tuple[4] = DatabaseMetaData.this.s2b(rs.getString("Seq_in_index"));
                                    tuple[5] = DatabaseMetaData.this.s2b(keyType);
                                    sortMap.put(columnName, tuple);
                                }
                            }
                            final Iterator<byte[][]> sortedIterator = sortMap.values().iterator();
                            while (sortedIterator.hasNext()) {
                                rows.add(new ByteArrayRow(sortedIterator.next(), DatabaseMetaData.this.getExceptionInterceptor()));
                            }
                        }
                        finally {
                            if (rs != null) {
                                try {
                                    rs.close();
                                }
                                catch (Exception ex) {}
                                rs = null;
                            }
                        }
                    }
                }.doForAll();
            }
            finally {
                if (stmt != null) {
                    stmt.close();
                }
            }
            final ResultSet results = this.resultSetFactory.createFromResultsetRows(1007, 1004, new ResultsetRowsStatic(rows, new DefaultColumnDefinition(fields)));
            return results;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public ResultSet getProcedureColumns(final String catalog, final String schemaPattern, final String procedureNamePattern, final String columnNamePattern) throws SQLException {
        try {
            final Field[] fields = this.createProcedureColumnsFields();
            return this.getProcedureOrFunctionColumns(fields, catalog, schemaPattern, procedureNamePattern, columnNamePattern, true, this.conn.getPropertySet().getBooleanProperty(PropertyKey.getProceduresReturnsFunctions).getValue());
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    protected Field[] createProcedureColumnsFields() {
        final Field[] fields = { new Field("", "PROCEDURE_CAT", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 512), new Field("", "PROCEDURE_SCHEM", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 512), new Field("", "PROCEDURE_NAME", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 512), new Field("", "COLUMN_NAME", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 512), new Field("", "COLUMN_TYPE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 64), new Field("", "DATA_TYPE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.SMALLINT, 6), new Field("", "TYPE_NAME", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 64), new Field("", "PRECISION", this.metadataCollationIndex, this.metadataEncoding, MysqlType.INT, 12), new Field("", "LENGTH", this.metadataCollationIndex, this.metadataEncoding, MysqlType.INT, 12), new Field("", "SCALE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.SMALLINT, 12), new Field("", "RADIX", this.metadataCollationIndex, this.metadataEncoding, MysqlType.SMALLINT, 6), new Field("", "NULLABLE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.SMALLINT, 6), new Field("", "REMARKS", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 512), new Field("", "COLUMN_DEF", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 512), new Field("", "SQL_DATA_TYPE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.INT, 12), new Field("", "SQL_DATETIME_SUB", this.metadataCollationIndex, this.metadataEncoding, MysqlType.INT, 12), new Field("", "CHAR_OCTET_LENGTH", this.metadataCollationIndex, this.metadataEncoding, MysqlType.INT, 12), new Field("", "ORDINAL_POSITION", this.metadataCollationIndex, this.metadataEncoding, MysqlType.INT, 12), new Field("", "IS_NULLABLE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 512), new Field("", "SPECIFIC_NAME", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 512) };
        return fields;
    }
    
    protected ResultSet getProcedureOrFunctionColumns(final Field[] fields, String catalog, final String schemaPattern, final String procedureOrFunctionNamePattern, final String columnNamePattern, final boolean returnProcedures, final boolean returnFunctions) throws SQLException {
        final List<ComparableWrapper<String, ProcedureType>> procsOrFuncsToExtractList = new ArrayList<ComparableWrapper<String, ProcedureType>>();
        ResultSet procsAndOrFuncsRs = null;
        try {
            String tmpProcedureOrFunctionNamePattern = null;
            if (procedureOrFunctionNamePattern != null && !procedureOrFunctionNamePattern.equals("%")) {
                tmpProcedureOrFunctionNamePattern = StringUtils.sanitizeProcOrFuncName(procedureOrFunctionNamePattern);
            }
            if (tmpProcedureOrFunctionNamePattern == null) {
                tmpProcedureOrFunctionNamePattern = procedureOrFunctionNamePattern;
            }
            else {
                String tmpCatalog = catalog;
                final List<String> parseList = StringUtils.splitDBdotName(tmpProcedureOrFunctionNamePattern, tmpCatalog, this.quotedId, this.session.getServerSession().isNoBackslashEscapesSet());
                if (parseList.size() == 2) {
                    tmpCatalog = parseList.get(0);
                    tmpProcedureOrFunctionNamePattern = parseList.get(1);
                }
            }
            procsAndOrFuncsRs = this.getProceduresAndOrFunctions(this.createFieldMetadataForGetProcedures(), catalog, schemaPattern, tmpProcedureOrFunctionNamePattern, returnProcedures, returnFunctions);
            boolean hasResults = false;
            while (procsAndOrFuncsRs.next()) {
                procsOrFuncsToExtractList.add(new ComparableWrapper<String, ProcedureType>(StringUtils.getFullyQualifiedName(procsAndOrFuncsRs.getString(1), procsAndOrFuncsRs.getString(3), this.quotedId, this.pedantic), (procsAndOrFuncsRs.getShort(8) == 1) ? ProcedureType.PROCEDURE : ProcedureType.FUNCTION));
                hasResults = true;
            }
            if (hasResults) {
                Collections.sort(procsOrFuncsToExtractList);
            }
        }
        finally {
            SQLException rethrowSqlEx = null;
            if (procsAndOrFuncsRs != null) {
                try {
                    procsAndOrFuncsRs.close();
                }
                catch (SQLException sqlEx) {
                    rethrowSqlEx = sqlEx;
                }
            }
            if (rethrowSqlEx != null) {
                throw rethrowSqlEx;
            }
        }
        final ArrayList<Row> resultRows = new ArrayList<Row>();
        int idx = 0;
        String procNameToCall = "";
        for (final ComparableWrapper<String, ProcedureType> procOrFunc : procsOrFuncsToExtractList) {
            final String procName = procOrFunc.getKey();
            final ProcedureType procType = procOrFunc.getValue();
            if (!" ".equals(this.quotedId)) {
                idx = StringUtils.indexOfIgnoreCase(0, procName, ".", this.quotedId, this.quotedId, this.session.getServerSession().isNoBackslashEscapesSet() ? StringUtils.SEARCH_MODE__MRK_COM_WS : StringUtils.SEARCH_MODE__ALL);
            }
            else {
                idx = procName.indexOf(".");
            }
            if (idx > 0) {
                catalog = StringUtils.unQuoteIdentifier(procName.substring(0, idx), this.quotedId);
                procNameToCall = procName;
            }
            else {
                procNameToCall = procName;
            }
            this.getCallStmtParameterTypes(catalog, procNameToCall, procType, columnNamePattern, resultRows, fields.length == 17);
        }
        return this.resultSetFactory.createFromResultsetRows(1007, 1004, new ResultsetRowsStatic(resultRows, new DefaultColumnDefinition(fields)));
    }
    
    @Override
    public ResultSet getProcedures(final String catalog, final String schemaPattern, final String procedureNamePattern) throws SQLException {
        try {
            final Field[] fields = this.createFieldMetadataForGetProcedures();
            return this.getProceduresAndOrFunctions(fields, catalog, schemaPattern, procedureNamePattern, true, this.conn.getPropertySet().getBooleanProperty(PropertyKey.getProceduresReturnsFunctions).getValue());
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    protected Field[] createFieldMetadataForGetProcedures() {
        final Field[] fields = { new Field("", "PROCEDURE_CAT", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 255), new Field("", "PROCEDURE_SCHEM", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 255), new Field("", "PROCEDURE_NAME", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 255), new Field("", "reserved1", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 0), new Field("", "reserved2", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 0), new Field("", "reserved3", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 0), new Field("", "REMARKS", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 255), new Field("", "PROCEDURE_TYPE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.SMALLINT, 6), new Field("", "SPECIFIC_NAME", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 255) };
        return fields;
    }
    
    protected ResultSet getProceduresAndOrFunctions(final Field[] fields, final String catalog, final String schemaPattern, final String procedureNamePattern, final boolean returnProcedures, final boolean returnFunctions) throws SQLException {
        final ArrayList<Row> procedureRows = new ArrayList<Row>();
        final String procNamePattern = procedureNamePattern;
        final List<ComparableWrapper<String, Row>> procedureRowsToSort = new ArrayList<ComparableWrapper<String, Row>>();
        new IterateBlock<String>(this.getCatalogIterator(catalog)) {
            @Override
            void forEach(final String catalogStr) throws SQLException {
                String db = catalogStr;
                ResultSet proceduresRs = null;
                boolean needsClientFiltering = true;
                final StringBuilder selectFromMySQLProcSQL = new StringBuilder();
                selectFromMySQLProcSQL.append("SELECT name, type, comment FROM mysql.proc WHERE");
                if (returnProcedures && !returnFunctions) {
                    selectFromMySQLProcSQL.append(" type = 'PROCEDURE' AND ");
                }
                else if (!returnProcedures && returnFunctions) {
                    selectFromMySQLProcSQL.append(" type = 'FUNCTION' AND ");
                }
                selectFromMySQLProcSQL.append(" db <=> ?");
                if (procNamePattern != null && procNamePattern.length() > 0) {
                    selectFromMySQLProcSQL.append(" AND name LIKE ?");
                }
                selectFromMySQLProcSQL.append(" ORDER BY name, type");
                PreparedStatement proceduresStmt = DatabaseMetaData.this.prepareMetaDataSafeStatement(selectFromMySQLProcSQL.toString());
                try {
                    if (db != null) {
                        if (DatabaseMetaData.this.conn.lowerCaseTableNames()) {
                            db = db.toLowerCase();
                        }
                        proceduresStmt.setString(1, db);
                    }
                    else {
                        proceduresStmt.setNull(1, MysqlType.VARCHAR.getJdbcType());
                    }
                    int nameIndex = 1;
                    if (procNamePattern != null && procNamePattern.length() > 0) {
                        proceduresStmt.setString(2, procNamePattern);
                    }
                    try {
                        proceduresRs = proceduresStmt.executeQuery();
                        needsClientFiltering = false;
                        if (returnProcedures) {
                            DatabaseMetaData.this.convertToJdbcProcedureList(true, db, proceduresRs, needsClientFiltering, db, procedureRowsToSort, nameIndex);
                        }
                        if (returnFunctions) {
                            DatabaseMetaData.this.convertToJdbcFunctionList(db, proceduresRs, needsClientFiltering, db, procedureRowsToSort, nameIndex, fields);
                        }
                    }
                    catch (SQLException sqlEx2) {
                        nameIndex = 2;
                        if (returnFunctions) {
                            proceduresStmt.close();
                            final String sql = (procNamePattern != null && procNamePattern.length() > 0) ? "SHOW FUNCTION STATUS LIKE ?" : "SHOW FUNCTION STATUS";
                            proceduresStmt = DatabaseMetaData.this.prepareMetaDataSafeStatement(sql);
                            if (procNamePattern != null && procNamePattern.length() > 0) {
                                proceduresStmt.setString(1, procNamePattern);
                            }
                            proceduresRs = proceduresStmt.executeQuery();
                            DatabaseMetaData.this.convertToJdbcFunctionList(db, proceduresRs, needsClientFiltering, db, procedureRowsToSort, nameIndex, fields);
                        }
                        if (returnProcedures) {
                            proceduresStmt.close();
                            final String sql = (procNamePattern != null && procNamePattern.length() > 0) ? "SHOW PROCEDURE STATUS LIKE ?" : "SHOW PROCEDURE STATUS";
                            proceduresStmt = DatabaseMetaData.this.prepareMetaDataSafeStatement(sql);
                            if (procNamePattern != null && procNamePattern.length() > 0) {
                                proceduresStmt.setString(1, procNamePattern);
                            }
                            proceduresRs = proceduresStmt.executeQuery();
                            DatabaseMetaData.this.convertToJdbcProcedureList(false, db, proceduresRs, needsClientFiltering, db, procedureRowsToSort, nameIndex);
                        }
                    }
                }
                finally {
                    SQLException rethrowSqlEx = null;
                    if (proceduresRs != null) {
                        try {
                            proceduresRs.close();
                        }
                        catch (SQLException sqlEx) {
                            rethrowSqlEx = sqlEx;
                        }
                    }
                    if (proceduresStmt != null) {
                        try {
                            proceduresStmt.close();
                        }
                        catch (SQLException sqlEx) {
                            rethrowSqlEx = sqlEx;
                        }
                    }
                    if (rethrowSqlEx != null) {
                        throw rethrowSqlEx;
                    }
                }
            }
        }.doForAll();
        Collections.sort(procedureRowsToSort);
        for (final ComparableWrapper<String, Row> procRow : procedureRowsToSort) {
            procedureRows.add(procRow.getValue());
        }
        return this.resultSetFactory.createFromResultsetRows(1007, 1004, new ResultsetRowsStatic(procedureRows, new DefaultColumnDefinition(fields)));
    }
    
    @Override
    public String getProcedureTerm() throws SQLException {
        try {
            return "PROCEDURE";
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
    
    private void getResultsImpl(final String catalog, final String table, final String keysComment, final List<Row> tuples, final String fkTableName, final boolean isExport) throws SQLException {
        final LocalAndReferencedColumns parsedInfo = this.parseTableStatusIntoLocalAndReferencedColumns(keysComment);
        if (isExport && !parsedInfo.referencedTable.equals(table)) {
            return;
        }
        if (parsedInfo.localColumnsList.size() != parsedInfo.referencedColumnsList.size()) {
            throw SQLError.createSQLException(Messages.getString("DatabaseMetaData.12"), "S1000", this.getExceptionInterceptor());
        }
        final Iterator<String> localColumnNames = parsedInfo.localColumnsList.iterator();
        final Iterator<String> referColumnNames = parsedInfo.referencedColumnsList.iterator();
        int keySeqIndex = 1;
        while (localColumnNames.hasNext()) {
            final byte[][] tuple = new byte[14][];
            final String lColumnName = StringUtils.unQuoteIdentifier(localColumnNames.next(), this.quotedId);
            final String rColumnName = StringUtils.unQuoteIdentifier(referColumnNames.next(), this.quotedId);
            tuple[4] = ((catalog == null) ? new byte[0] : this.s2b(catalog));
            tuple[5] = null;
            tuple[6] = this.s2b(isExport ? fkTableName : table);
            tuple[7] = this.s2b(lColumnName);
            tuple[0] = this.s2b(parsedInfo.referencedCatalog);
            tuple[1] = null;
            tuple[2] = this.s2b(isExport ? table : parsedInfo.referencedTable);
            tuple[3] = this.s2b(rColumnName);
            tuple[8] = this.s2b(Integer.toString(keySeqIndex++));
            final int[] actions = this.getForeignKeyActions(keysComment);
            tuple[9] = this.s2b(Integer.toString(actions[1]));
            tuple[10] = this.s2b(Integer.toString(actions[0]));
            tuple[11] = this.s2b(parsedInfo.constraintName);
            tuple[12] = null;
            tuple[13] = this.s2b(Integer.toString(7));
            tuples.add(new ByteArrayRow(tuple, this.getExceptionInterceptor()));
        }
    }
    
    @Override
    public ResultSet getSchemas() throws SQLException {
        try {
            final Field[] fields = { new Field("", "TABLE_SCHEM", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 0), new Field("", "TABLE_CATALOG", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 0) };
            final ArrayList<Row> tuples = new ArrayList<Row>();
            final ResultSet results = this.resultSetFactory.createFromResultsetRows(1007, 1004, new ResultsetRowsStatic(tuples, new DefaultColumnDefinition(fields)));
            return results;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public String getSchemaTerm() throws SQLException {
        try {
            return "";
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public String getSearchStringEscape() throws SQLException {
        try {
            return "\\";
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public String getSQLKeywords() throws SQLException {
        try {
            if (DatabaseMetaData.mysqlKeywords != null) {
                return DatabaseMetaData.mysqlKeywords;
            }
            synchronized (DatabaseMetaData.class) {
                if (DatabaseMetaData.mysqlKeywords != null) {
                    return DatabaseMetaData.mysqlKeywords;
                }
                final Set<String> mysqlKeywordSet = new TreeSet<String>();
                final StringBuilder mysqlKeywordsBuffer = new StringBuilder();
                Collections.addAll(mysqlKeywordSet, DatabaseMetaData.MYSQL_KEYWORDS);
                mysqlKeywordSet.removeAll(DatabaseMetaData.SQL2003_KEYWORDS);
                for (final String keyword : mysqlKeywordSet) {
                    mysqlKeywordsBuffer.append(",").append(keyword);
                }
                return DatabaseMetaData.mysqlKeywords = mysqlKeywordsBuffer.substring(1);
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public int getSQLStateType() throws SQLException {
        try {
            return 2;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public String getStringFunctions() throws SQLException {
        try {
            return "ASCII,BIN,BIT_LENGTH,CHAR,CHARACTER_LENGTH,CHAR_LENGTH,CONCAT,CONCAT_WS,CONV,ELT,EXPORT_SET,FIELD,FIND_IN_SET,HEX,INSERT,INSTR,LCASE,LEFT,LENGTH,LOAD_FILE,LOCATE,LOCATE,LOWER,LPAD,LTRIM,MAKE_SET,MATCH,MID,OCT,OCTET_LENGTH,ORD,POSITION,QUOTE,REPEAT,REPLACE,REVERSE,RIGHT,RPAD,RTRIM,SOUNDEX,SPACE,STRCMP,SUBSTRING,SUBSTRING,SUBSTRING,SUBSTRING,SUBSTRING_INDEX,TRIM,UCASE,UPPER";
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public ResultSet getSuperTables(final String arg0, final String arg1, final String arg2) throws SQLException {
        try {
            final Field[] fields = { new Field("", "TABLE_CAT", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 32), new Field("", "TABLE_SCHEM", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 32), new Field("", "TABLE_NAME", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 32), new Field("", "SUPERTABLE_NAME", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 32) };
            return this.resultSetFactory.createFromResultsetRows(1007, 1004, new ResultsetRowsStatic(new ArrayList<Row>(), new DefaultColumnDefinition(fields)));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public ResultSet getSuperTypes(final String arg0, final String arg1, final String arg2) throws SQLException {
        try {
            final Field[] fields = { new Field("", "TYPE_CAT", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 32), new Field("", "TYPE_SCHEM", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 32), new Field("", "TYPE_NAME", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 32), new Field("", "SUPERTYPE_CAT", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 32), new Field("", "SUPERTYPE_SCHEM", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 32), new Field("", "SUPERTYPE_NAME", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 32) };
            return this.resultSetFactory.createFromResultsetRows(1007, 1004, new ResultsetRowsStatic(new ArrayList<Row>(), new DefaultColumnDefinition(fields)));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public String getSystemFunctions() throws SQLException {
        try {
            return "DATABASE,USER,SYSTEM_USER,SESSION_USER,PASSWORD,ENCRYPT,LAST_INSERT_ID,VERSION";
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    protected String getTableNameWithCase(final String table) {
        final String tableNameWithCase = this.conn.lowerCaseTableNames() ? table.toLowerCase() : table;
        return tableNameWithCase;
    }
    
    @Override
    public ResultSet getTablePrivileges(final String catalog, final String schemaPattern, final String tableNamePattern) throws SQLException {
        try {
            final Field[] fields = { new Field("", "TABLE_CAT", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 64), new Field("", "TABLE_SCHEM", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 1), new Field("", "TABLE_NAME", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 64), new Field("", "GRANTOR", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 77), new Field("", "GRANTEE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 77), new Field("", "PRIVILEGE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 64), new Field("", "IS_GRANTABLE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 3) };
            final StringBuilder grantQueryBuf = new StringBuilder("SELECT host,db,table_name,grantor,user,table_priv FROM mysql.tables_priv");
            final StringBuilder conditionBuf = new StringBuilder();
            if (catalog != null) {
                conditionBuf.append(" db LIKE ?");
            }
            if (tableNamePattern != null) {
                if (conditionBuf.length() > 0) {
                    conditionBuf.append(" AND");
                }
                conditionBuf.append(" table_name LIKE ?");
            }
            if (conditionBuf.length() > 0) {
                grantQueryBuf.append(" WHERE");
                grantQueryBuf.append((CharSequence)conditionBuf);
            }
            ResultSet results = null;
            final ArrayList<Row> grantRows = new ArrayList<Row>();
            PreparedStatement pStmt = null;
            try {
                pStmt = this.prepareMetaDataSafeStatement(grantQueryBuf.toString());
                int nextId = 1;
                if (catalog != null) {
                    pStmt.setString(nextId++, catalog);
                }
                if (tableNamePattern != null) {
                    pStmt.setString(nextId, tableNamePattern);
                }
                results = pStmt.executeQuery();
                while (results.next()) {
                    final String host = results.getString(1);
                    final String db = results.getString(2);
                    final String table = results.getString(3);
                    final String grantor = results.getString(4);
                    String user = results.getString(5);
                    if (user == null || user.length() == 0) {
                        user = "%";
                    }
                    final StringBuilder fullUser = new StringBuilder(user);
                    if (host != null && this.useHostsInPrivileges) {
                        fullUser.append("@");
                        fullUser.append(host);
                    }
                    String allPrivileges = results.getString(6);
                    if (allPrivileges != null) {
                        allPrivileges = allPrivileges.toUpperCase(Locale.ENGLISH);
                        final StringTokenizer st = new StringTokenizer(allPrivileges, ",");
                        while (st.hasMoreTokens()) {
                            final String privilege = st.nextToken().trim();
                            ResultSet columnResults = null;
                            try {
                                columnResults = this.getColumns(catalog, schemaPattern, table, null);
                                while (columnResults.next()) {
                                    final byte[][] tuple = { this.s2b(db), null, this.s2b(table), (grantor != null) ? this.s2b(grantor) : null, this.s2b(fullUser.toString()), this.s2b(privilege), null, null };
                                    grantRows.add(new ByteArrayRow(tuple, this.getExceptionInterceptor()));
                                }
                            }
                            finally {
                                if (columnResults != null) {
                                    try {
                                        columnResults.close();
                                    }
                                    catch (Exception ex2) {}
                                }
                            }
                        }
                    }
                }
            }
            finally {
                if (results != null) {
                    try {
                        results.close();
                    }
                    catch (Exception ex3) {}
                    results = null;
                }
                if (pStmt != null) {
                    try {
                        pStmt.close();
                    }
                    catch (Exception ex4) {}
                    pStmt = null;
                }
            }
            return this.resultSetFactory.createFromResultsetRows(1007, 1004, new ResultsetRowsStatic(grantRows, new DefaultColumnDefinition(fields)));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public ResultSet getTables(String catalog, final String schemaPattern, String tableNamePattern, final String[] types) throws SQLException {
        try {
            final SortedMap<TableMetaDataKey, Row> sortedRows = new TreeMap<TableMetaDataKey, Row>();
            final ArrayList<Row> tuples = new ArrayList<Row>();
            final Statement stmt = this.conn.getMetadataSafeStatement();
            if (catalog == null && this.nullCatalogMeansCurrent) {
                catalog = this.database;
            }
            if (tableNamePattern != null) {
                final List<String> parseList = StringUtils.splitDBdotName(tableNamePattern, catalog, this.quotedId, this.session.getServerSession().isNoBackslashEscapesSet());
                if (parseList.size() == 2) {
                    tableNamePattern = parseList.get(1);
                }
            }
            final String tableNamePat = tableNamePattern;
            try {
                new IterateBlock<String>(this.getCatalogIterator(catalog)) {
                    @Override
                    void forEach(final String catalogStr) throws SQLException {
                        final boolean operatingOnSystemDB = "information_schema".equalsIgnoreCase(catalogStr) || "mysql".equalsIgnoreCase(catalogStr) || "performance_schema".equalsIgnoreCase(catalogStr);
                        ResultSet results = null;
                        try {
                            try {
                                final StringBuilder sqlBuf = new StringBuilder("SHOW FULL TABLES FROM ");
                                sqlBuf.append(StringUtils.quoteIdentifier(catalogStr, DatabaseMetaData.this.quotedId, DatabaseMetaData.this.pedantic));
                                if (tableNamePat != null) {
                                    sqlBuf.append(" LIKE ");
                                    sqlBuf.append(StringUtils.quoteIdentifier(tableNamePat, "'", true));
                                }
                                results = stmt.executeQuery(sqlBuf.toString());
                            }
                            catch (SQLException sqlEx) {
                                if ("08S01".equals(sqlEx.getSQLState())) {
                                    throw sqlEx;
                                }
                                return;
                            }
                            boolean shouldReportTables = false;
                            boolean shouldReportViews = false;
                            boolean shouldReportSystemTables = false;
                            boolean shouldReportSystemViews = false;
                            boolean shouldReportLocalTemporaries = false;
                            if (types == null || types.length == 0) {
                                shouldReportTables = true;
                                shouldReportViews = true;
                                shouldReportSystemTables = true;
                                shouldReportSystemViews = true;
                                shouldReportLocalTemporaries = true;
                            }
                            else {
                                for (int i = 0; i < types.length; ++i) {
                                    if (TableType.TABLE.equalsTo(types[i])) {
                                        shouldReportTables = true;
                                    }
                                    else if (TableType.VIEW.equalsTo(types[i])) {
                                        shouldReportViews = true;
                                    }
                                    else if (TableType.SYSTEM_TABLE.equalsTo(types[i])) {
                                        shouldReportSystemTables = true;
                                    }
                                    else if (TableType.SYSTEM_VIEW.equalsTo(types[i])) {
                                        shouldReportSystemViews = true;
                                    }
                                    else if (TableType.LOCAL_TEMPORARY.equalsTo(types[i])) {
                                        shouldReportLocalTemporaries = true;
                                    }
                                }
                            }
                            int typeColumnIndex = 0;
                            boolean hasTableTypes = false;
                            try {
                                typeColumnIndex = results.findColumn("table_type");
                                hasTableTypes = true;
                            }
                            catch (SQLException sqlEx2) {
                                try {
                                    typeColumnIndex = results.findColumn("Type");
                                    hasTableTypes = true;
                                }
                                catch (SQLException sqlEx3) {
                                    hasTableTypes = false;
                                }
                            }
                            while (results.next()) {
                                final byte[][] row = { (catalogStr == null) ? null : DatabaseMetaData.this.s2b(catalogStr), null, results.getBytes(1), null, new byte[0], null, null, null, null, null };
                                if (hasTableTypes) {
                                    final String tableType = results.getString(typeColumnIndex);
                                    switch (TableType.getTableTypeCompliantWith(tableType)) {
                                        case TABLE: {
                                            boolean reportTable = false;
                                            TableMetaDataKey tablesKey = null;
                                            if (operatingOnSystemDB && shouldReportSystemTables) {
                                                row[3] = TableType.SYSTEM_TABLE.asBytes();
                                                tablesKey = new TableMetaDataKey(TableType.SYSTEM_TABLE.getName(), catalogStr, null, results.getString(1));
                                                reportTable = true;
                                            }
                                            else if (!operatingOnSystemDB && shouldReportTables) {
                                                row[3] = TableType.TABLE.asBytes();
                                                tablesKey = new TableMetaDataKey(TableType.TABLE.getName(), catalogStr, null, results.getString(1));
                                                reportTable = true;
                                            }
                                            if (reportTable) {
                                                sortedRows.put(tablesKey, new ByteArrayRow(row, DatabaseMetaData.this.getExceptionInterceptor()));
                                                continue;
                                            }
                                            continue;
                                        }
                                        case VIEW: {
                                            if (shouldReportViews) {
                                                row[3] = TableType.VIEW.asBytes();
                                                sortedRows.put(new TableMetaDataKey(TableType.VIEW.getName(), catalogStr, null, results.getString(1)), new ByteArrayRow(row, DatabaseMetaData.this.getExceptionInterceptor()));
                                                continue;
                                            }
                                            continue;
                                        }
                                        case SYSTEM_TABLE: {
                                            if (shouldReportSystemTables) {
                                                row[3] = TableType.SYSTEM_TABLE.asBytes();
                                                sortedRows.put(new TableMetaDataKey(TableType.SYSTEM_TABLE.getName(), catalogStr, null, results.getString(1)), new ByteArrayRow(row, DatabaseMetaData.this.getExceptionInterceptor()));
                                                continue;
                                            }
                                            continue;
                                        }
                                        case SYSTEM_VIEW: {
                                            if (shouldReportSystemViews) {
                                                row[3] = TableType.SYSTEM_VIEW.asBytes();
                                                sortedRows.put(new TableMetaDataKey(TableType.SYSTEM_VIEW.getName(), catalogStr, null, results.getString(1)), new ByteArrayRow(row, DatabaseMetaData.this.getExceptionInterceptor()));
                                                continue;
                                            }
                                            continue;
                                        }
                                        case LOCAL_TEMPORARY: {
                                            if (shouldReportLocalTemporaries) {
                                                row[3] = TableType.LOCAL_TEMPORARY.asBytes();
                                                sortedRows.put(new TableMetaDataKey(TableType.LOCAL_TEMPORARY.getName(), catalogStr, null, results.getString(1)), new ByteArrayRow(row, DatabaseMetaData.this.getExceptionInterceptor()));
                                                continue;
                                            }
                                            continue;
                                        }
                                        default: {
                                            row[3] = TableType.TABLE.asBytes();
                                            sortedRows.put(new TableMetaDataKey(TableType.TABLE.getName(), catalogStr, null, results.getString(1)), new ByteArrayRow(row, DatabaseMetaData.this.getExceptionInterceptor()));
                                            continue;
                                        }
                                    }
                                }
                                else {
                                    if (!shouldReportTables) {
                                        continue;
                                    }
                                    row[3] = TableType.TABLE.asBytes();
                                    sortedRows.put(new TableMetaDataKey(TableType.TABLE.getName(), catalogStr, null, results.getString(1)), new ByteArrayRow(row, DatabaseMetaData.this.getExceptionInterceptor()));
                                }
                            }
                        }
                        finally {
                            if (results != null) {
                                try {
                                    results.close();
                                }
                                catch (Exception ex) {}
                                results = null;
                            }
                        }
                    }
                }.doForAll();
            }
            finally {
                if (stmt != null) {
                    stmt.close();
                }
            }
            tuples.addAll(sortedRows.values());
            final ResultSet tables = this.resultSetFactory.createFromResultsetRows(1007, 1004, new ResultsetRowsStatic(tuples, this.createTablesFields()));
            return tables;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    protected ColumnDefinition createTablesFields() {
        final Field[] fields = { new Field("", "TABLE_CAT", this.metadataCollationIndex, this.metadataEncoding, MysqlType.VARCHAR, 255), new Field("", "TABLE_SCHEM", this.metadataCollationIndex, this.metadataEncoding, MysqlType.VARCHAR, 0), new Field("", "TABLE_NAME", this.metadataCollationIndex, this.metadataEncoding, MysqlType.VARCHAR, 255), new Field("", "TABLE_TYPE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.VARCHAR, 5), new Field("", "REMARKS", this.metadataCollationIndex, this.metadataEncoding, MysqlType.VARCHAR, 0), new Field("", "TYPE_CAT", this.metadataCollationIndex, this.metadataEncoding, MysqlType.VARCHAR, 0), new Field("", "TYPE_SCHEM", this.metadataCollationIndex, this.metadataEncoding, MysqlType.VARCHAR, 0), new Field("", "TYPE_NAME", this.metadataCollationIndex, this.metadataEncoding, MysqlType.VARCHAR, 0), new Field("", "SELF_REFERENCING_COL_NAME", this.metadataCollationIndex, this.metadataEncoding, MysqlType.VARCHAR, 0), new Field("", "REF_GENERATION", this.metadataCollationIndex, this.metadataEncoding, MysqlType.VARCHAR, 0) };
        return new DefaultColumnDefinition(fields);
    }
    
    @Override
    public ResultSet getTableTypes() throws SQLException {
        try {
            final ArrayList<Row> tuples = new ArrayList<Row>();
            final Field[] fields = { new Field("", "TABLE_TYPE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.VARCHAR, 256) };
            tuples.add(new ByteArrayRow(new byte[][] { TableType.LOCAL_TEMPORARY.asBytes() }, this.getExceptionInterceptor()));
            tuples.add(new ByteArrayRow(new byte[][] { TableType.SYSTEM_TABLE.asBytes() }, this.getExceptionInterceptor()));
            tuples.add(new ByteArrayRow(new byte[][] { TableType.SYSTEM_VIEW.asBytes() }, this.getExceptionInterceptor()));
            tuples.add(new ByteArrayRow(new byte[][] { TableType.TABLE.asBytes() }, this.getExceptionInterceptor()));
            tuples.add(new ByteArrayRow(new byte[][] { TableType.VIEW.asBytes() }, this.getExceptionInterceptor()));
            return this.resultSetFactory.createFromResultsetRows(1007, 1004, new ResultsetRowsStatic(tuples, new DefaultColumnDefinition(fields)));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public String getTimeDateFunctions() throws SQLException {
        try {
            return "DAYOFWEEK,WEEKDAY,DAYOFMONTH,DAYOFYEAR,MONTH,DAYNAME,MONTHNAME,QUARTER,WEEK,YEAR,HOUR,MINUTE,SECOND,PERIOD_ADD,PERIOD_DIFF,TO_DAYS,FROM_DAYS,DATE_FORMAT,TIME_FORMAT,CURDATE,CURRENT_DATE,CURTIME,CURRENT_TIME,NOW,SYSDATE,CURRENT_TIMESTAMP,UNIX_TIMESTAMP,FROM_UNIXTIME,SEC_TO_TIME,TIME_TO_SEC";
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    private byte[][] getTypeInfo(final String mysqlTypeName) throws SQLException {
        final MysqlType mt = MysqlType.getByName(mysqlTypeName);
        final byte[][] rowVal = new byte[18][];
        rowVal[0] = this.s2b(mysqlTypeName);
        rowVal[1] = Integer.toString(mt.getJdbcType()).getBytes();
        rowVal[2] = Integer.toString((mt.getPrecision() > 2147483647L) ? Integer.MAX_VALUE : mt.getPrecision().intValue()).getBytes();
        switch (mt) {
            case ENUM:
            case SET:
            case CHAR:
            case VARCHAR:
            case TINYTEXT:
            case MEDIUMTEXT:
            case LONGTEXT:
            case JSON:
            case TEXT:
            case TINYBLOB:
            case MEDIUMBLOB:
            case LONGBLOB:
            case BLOB:
            case BINARY:
            case VARBINARY:
            case GEOMETRY:
            case UNKNOWN:
            case DATE:
            case TIME:
            case DATETIME:
            case TIMESTAMP: {
                rowVal[3] = this.s2b("'");
                rowVal[4] = this.s2b("'");
                break;
            }
            default: {
                rowVal[3] = this.s2b("");
                rowVal[4] = this.s2b("");
                break;
            }
        }
        rowVal[5] = this.s2b(mt.getCreateParams());
        rowVal[6] = Integer.toString(1).getBytes();
        rowVal[7] = this.s2b("true");
        rowVal[8] = Integer.toString(3).getBytes();
        rowVal[9] = this.s2b(mt.isAllowed(32) ? "true" : "false");
        rowVal[10] = this.s2b("false");
        rowVal[11] = this.s2b("false");
        rowVal[12] = this.s2b(mt.getName());
        switch (mt) {
            case DECIMAL:
            case DECIMAL_UNSIGNED:
            case DOUBLE:
            case DOUBLE_UNSIGNED: {
                rowVal[13] = this.s2b("-308");
                rowVal[14] = this.s2b("308");
                break;
            }
            case FLOAT:
            case FLOAT_UNSIGNED: {
                rowVal[13] = this.s2b("-38");
                rowVal[14] = this.s2b("38");
                break;
            }
            default: {
                rowVal[13] = this.s2b("0");
                rowVal[14] = this.s2b("0");
                break;
            }
        }
        rowVal[15] = this.s2b("0");
        rowVal[16] = this.s2b("0");
        rowVal[17] = this.s2b("10");
        return rowVal;
    }
    
    @Override
    public ResultSet getTypeInfo() throws SQLException {
        try {
            final Field[] fields = { new Field("", "TYPE_NAME", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 32), new Field("", "DATA_TYPE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.INT, 5), new Field("", "PRECISION", this.metadataCollationIndex, this.metadataEncoding, MysqlType.INT, 10), new Field("", "LITERAL_PREFIX", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 4), new Field("", "LITERAL_SUFFIX", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 4), new Field("", "CREATE_PARAMS", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 32), new Field("", "NULLABLE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.SMALLINT, 5), new Field("", "CASE_SENSITIVE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.BOOLEAN, 3), new Field("", "SEARCHABLE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.SMALLINT, 3), new Field("", "UNSIGNED_ATTRIBUTE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.BOOLEAN, 3), new Field("", "FIXED_PREC_SCALE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.BOOLEAN, 3), new Field("", "AUTO_INCREMENT", this.metadataCollationIndex, this.metadataEncoding, MysqlType.BOOLEAN, 3), new Field("", "LOCAL_TYPE_NAME", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 32), new Field("", "MINIMUM_SCALE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.SMALLINT, 5), new Field("", "MAXIMUM_SCALE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.SMALLINT, 5), new Field("", "SQL_DATA_TYPE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.INT, 10), new Field("", "SQL_DATETIME_SUB", this.metadataCollationIndex, this.metadataEncoding, MysqlType.INT, 10), new Field("", "NUM_PREC_RADIX", this.metadataCollationIndex, this.metadataEncoding, MysqlType.INT, 10) };
            final ArrayList<Row> tuples = new ArrayList<Row>();
            tuples.add(new ByteArrayRow(this.getTypeInfo("BIT"), this.getExceptionInterceptor()));
            tuples.add(new ByteArrayRow(this.getTypeInfo("BOOL"), this.getExceptionInterceptor()));
            tuples.add(new ByteArrayRow(this.getTypeInfo("TINYINT"), this.getExceptionInterceptor()));
            tuples.add(new ByteArrayRow(this.getTypeInfo("TINYINT UNSIGNED"), this.getExceptionInterceptor()));
            tuples.add(new ByteArrayRow(this.getTypeInfo("BIGINT"), this.getExceptionInterceptor()));
            tuples.add(new ByteArrayRow(this.getTypeInfo("BIGINT UNSIGNED"), this.getExceptionInterceptor()));
            tuples.add(new ByteArrayRow(this.getTypeInfo("LONG VARBINARY"), this.getExceptionInterceptor()));
            tuples.add(new ByteArrayRow(this.getTypeInfo("MEDIUMBLOB"), this.getExceptionInterceptor()));
            tuples.add(new ByteArrayRow(this.getTypeInfo("LONGBLOB"), this.getExceptionInterceptor()));
            tuples.add(new ByteArrayRow(this.getTypeInfo("BLOB"), this.getExceptionInterceptor()));
            tuples.add(new ByteArrayRow(this.getTypeInfo("VARBINARY"), this.getExceptionInterceptor()));
            tuples.add(new ByteArrayRow(this.getTypeInfo("TINYBLOB"), this.getExceptionInterceptor()));
            tuples.add(new ByteArrayRow(this.getTypeInfo("BINARY"), this.getExceptionInterceptor()));
            tuples.add(new ByteArrayRow(this.getTypeInfo("LONG VARCHAR"), this.getExceptionInterceptor()));
            tuples.add(new ByteArrayRow(this.getTypeInfo("MEDIUMTEXT"), this.getExceptionInterceptor()));
            tuples.add(new ByteArrayRow(this.getTypeInfo("LONGTEXT"), this.getExceptionInterceptor()));
            tuples.add(new ByteArrayRow(this.getTypeInfo("TEXT"), this.getExceptionInterceptor()));
            tuples.add(new ByteArrayRow(this.getTypeInfo("CHAR"), this.getExceptionInterceptor()));
            tuples.add(new ByteArrayRow(this.getTypeInfo("ENUM"), this.getExceptionInterceptor()));
            tuples.add(new ByteArrayRow(this.getTypeInfo("SET"), this.getExceptionInterceptor()));
            tuples.add(new ByteArrayRow(this.getTypeInfo("DECIMAL"), this.getExceptionInterceptor()));
            tuples.add(new ByteArrayRow(this.getTypeInfo("NUMERIC"), this.getExceptionInterceptor()));
            tuples.add(new ByteArrayRow(this.getTypeInfo("INTEGER"), this.getExceptionInterceptor()));
            tuples.add(new ByteArrayRow(this.getTypeInfo("INTEGER UNSIGNED"), this.getExceptionInterceptor()));
            tuples.add(new ByteArrayRow(this.getTypeInfo("INT"), this.getExceptionInterceptor()));
            tuples.add(new ByteArrayRow(this.getTypeInfo("INT UNSIGNED"), this.getExceptionInterceptor()));
            tuples.add(new ByteArrayRow(this.getTypeInfo("MEDIUMINT"), this.getExceptionInterceptor()));
            tuples.add(new ByteArrayRow(this.getTypeInfo("MEDIUMINT UNSIGNED"), this.getExceptionInterceptor()));
            tuples.add(new ByteArrayRow(this.getTypeInfo("SMALLINT"), this.getExceptionInterceptor()));
            tuples.add(new ByteArrayRow(this.getTypeInfo("SMALLINT UNSIGNED"), this.getExceptionInterceptor()));
            tuples.add(new ByteArrayRow(this.getTypeInfo("FLOAT"), this.getExceptionInterceptor()));
            tuples.add(new ByteArrayRow(this.getTypeInfo("DOUBLE"), this.getExceptionInterceptor()));
            tuples.add(new ByteArrayRow(this.getTypeInfo("DOUBLE PRECISION"), this.getExceptionInterceptor()));
            tuples.add(new ByteArrayRow(this.getTypeInfo("REAL"), this.getExceptionInterceptor()));
            tuples.add(new ByteArrayRow(this.getTypeInfo("VARCHAR"), this.getExceptionInterceptor()));
            tuples.add(new ByteArrayRow(this.getTypeInfo("TINYTEXT"), this.getExceptionInterceptor()));
            tuples.add(new ByteArrayRow(this.getTypeInfo("DATE"), this.getExceptionInterceptor()));
            tuples.add(new ByteArrayRow(this.getTypeInfo("YEAR"), this.getExceptionInterceptor()));
            tuples.add(new ByteArrayRow(this.getTypeInfo("TIME"), this.getExceptionInterceptor()));
            tuples.add(new ByteArrayRow(this.getTypeInfo("DATETIME"), this.getExceptionInterceptor()));
            tuples.add(new ByteArrayRow(this.getTypeInfo("TIMESTAMP"), this.getExceptionInterceptor()));
            return this.resultSetFactory.createFromResultsetRows(1007, 1004, new ResultsetRowsStatic(tuples, new DefaultColumnDefinition(fields)));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public ResultSet getUDTs(final String catalog, final String schemaPattern, final String typeNamePattern, final int[] types) throws SQLException {
        try {
            final Field[] fields = { new Field("", "TYPE_CAT", this.metadataCollationIndex, this.metadataEncoding, MysqlType.VARCHAR, 32), new Field("", "TYPE_SCHEM", this.metadataCollationIndex, this.metadataEncoding, MysqlType.VARCHAR, 32), new Field("", "TYPE_NAME", this.metadataCollationIndex, this.metadataEncoding, MysqlType.VARCHAR, 32), new Field("", "CLASS_NAME", this.metadataCollationIndex, this.metadataEncoding, MysqlType.VARCHAR, 32), new Field("", "DATA_TYPE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.INT, 10), new Field("", "REMARKS", this.metadataCollationIndex, this.metadataEncoding, MysqlType.VARCHAR, 32), new Field("", "BASE_TYPE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.SMALLINT, 10) };
            final ArrayList<Row> tuples = new ArrayList<Row>();
            return this.resultSetFactory.createFromResultsetRows(1007, 1004, new ResultsetRowsStatic(tuples, new DefaultColumnDefinition(fields)));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public String getURL() throws SQLException {
        try {
            return this.conn.getURL();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public String getUserName() throws SQLException {
        try {
            if (this.useHostsInPrivileges) {
                Statement stmt = null;
                ResultSet rs = null;
                try {
                    stmt = this.conn.getMetadataSafeStatement();
                    rs = stmt.executeQuery("SELECT USER()");
                    rs.next();
                    return rs.getString(1);
                }
                finally {
                    if (rs != null) {
                        try {
                            rs.close();
                        }
                        catch (Exception ex) {
                            AssertionFailedException.shouldNotHappen(ex);
                        }
                        rs = null;
                    }
                    if (stmt != null) {
                        try {
                            stmt.close();
                        }
                        catch (Exception ex) {
                            AssertionFailedException.shouldNotHappen(ex);
                        }
                        stmt = null;
                    }
                }
            }
            return this.conn.getUser();
        }
        catch (CJException ex2) {
            throw SQLExceptionsMapping.translateException(ex2, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public ResultSet getVersionColumns(final String catalog, final String schema, final String table) throws SQLException {
        try {
            if (table == null) {
                throw SQLError.createSQLException(Messages.getString("DatabaseMetaData.2"), "S1009", this.getExceptionInterceptor());
            }
            final Field[] fields = { new Field("", "SCOPE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.SMALLINT, 5), new Field("", "COLUMN_NAME", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 32), new Field("", "DATA_TYPE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.INT, 5), new Field("", "TYPE_NAME", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 16), new Field("", "COLUMN_SIZE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.INT, 16), new Field("", "BUFFER_LENGTH", this.metadataCollationIndex, this.metadataEncoding, MysqlType.INT, 16), new Field("", "DECIMAL_DIGITS", this.metadataCollationIndex, this.metadataEncoding, MysqlType.SMALLINT, 16), new Field("", "PSEUDO_COLUMN", this.metadataCollationIndex, this.metadataEncoding, MysqlType.SMALLINT, 5) };
            final ArrayList<Row> rows = new ArrayList<Row>();
            final Statement stmt = this.conn.getMetadataSafeStatement();
            try {
                new IterateBlock<String>(this.getCatalogIterator(catalog)) {
                    @Override
                    void forEach(final String catalogStr) throws SQLException {
                        ResultSet results = null;
                        try {
                            final StringBuilder whereBuf = new StringBuilder(" Extra LIKE '%on update CURRENT_TIMESTAMP%'");
                            final List<String> rsFields = new ArrayList<String>();
                            if (whereBuf.length() > 0 || rsFields.size() > 0) {
                                final StringBuilder queryBuf = new StringBuilder("SHOW COLUMNS FROM ");
                                queryBuf.append(StringUtils.quoteIdentifier(table, DatabaseMetaData.this.quotedId, DatabaseMetaData.this.pedantic));
                                queryBuf.append(" FROM ");
                                queryBuf.append(StringUtils.quoteIdentifier(catalogStr, DatabaseMetaData.this.quotedId, DatabaseMetaData.this.pedantic));
                                queryBuf.append(" WHERE");
                                queryBuf.append(whereBuf.toString());
                                results = stmt.executeQuery(queryBuf.toString());
                                while (results.next()) {
                                    final TypeDescriptor typeDesc = new TypeDescriptor(results.getString("Type"), results.getString("Null"));
                                    final byte[][] rowVal = { null, results.getBytes("Field"), Short.toString((short)typeDesc.mysqlType.getJdbcType()).getBytes(), DatabaseMetaData.this.s2b(typeDesc.mysqlType.getName()), (typeDesc.columnSize == null) ? null : DatabaseMetaData.this.s2b(typeDesc.columnSize.toString()), DatabaseMetaData.this.s2b(Integer.toString(typeDesc.bufferLength)), (typeDesc.decimalDigits == null) ? null : DatabaseMetaData.this.s2b(typeDesc.decimalDigits.toString()), Integer.toString(1).getBytes() };
                                    rows.add(new ByteArrayRow(rowVal, DatabaseMetaData.this.getExceptionInterceptor()));
                                }
                            }
                        }
                        catch (SQLException sqlEx) {
                            if (!"42S02".equals(sqlEx.getSQLState())) {
                                throw sqlEx;
                            }
                        }
                        finally {
                            if (results != null) {
                                try {
                                    results.close();
                                }
                                catch (Exception ex) {}
                                results = null;
                            }
                        }
                    }
                }.doForAll();
            }
            finally {
                if (stmt != null) {
                    stmt.close();
                }
            }
            return this.resultSetFactory.createFromResultsetRows(1007, 1004, new ResultsetRowsStatic(rows, new DefaultColumnDefinition(fields)));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean insertsAreDetected(final int type) throws SQLException {
        try {
            return false;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean isCatalogAtStart() throws SQLException {
        try {
            return true;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean isReadOnly() throws SQLException {
        try {
            return false;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean locatorsUpdateCopy() throws SQLException {
        try {
            return !this.conn.getPropertySet().getBooleanProperty(PropertyKey.emulateLocators).getValue();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean nullPlusNonNullIsNull() throws SQLException {
        try {
            return true;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean nullsAreSortedAtEnd() throws SQLException {
        try {
            return false;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean nullsAreSortedAtStart() throws SQLException {
        try {
            return false;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean nullsAreSortedHigh() throws SQLException {
        try {
            return false;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean nullsAreSortedLow() throws SQLException {
        try {
            return !this.nullsAreSortedHigh();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean othersDeletesAreVisible(final int type) throws SQLException {
        try {
            return false;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean othersInsertsAreVisible(final int type) throws SQLException {
        try {
            return false;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean othersUpdatesAreVisible(final int type) throws SQLException {
        try {
            return false;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean ownDeletesAreVisible(final int type) throws SQLException {
        try {
            return false;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean ownInsertsAreVisible(final int type) throws SQLException {
        try {
            return false;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean ownUpdatesAreVisible(final int type) throws SQLException {
        try {
            return false;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    protected LocalAndReferencedColumns parseTableStatusIntoLocalAndReferencedColumns(String keysComment) throws SQLException {
        final String columnsDelimitter = ",";
        final int indexOfOpenParenLocalColumns = StringUtils.indexOfIgnoreCase(0, keysComment, "(", this.quotedId, this.quotedId, StringUtils.SEARCH_MODE__ALL);
        if (indexOfOpenParenLocalColumns == -1) {
            throw SQLError.createSQLException(Messages.getString("DatabaseMetaData.14"), "S1000", this.getExceptionInterceptor());
        }
        final String constraintName = StringUtils.unQuoteIdentifier(keysComment.substring(0, indexOfOpenParenLocalColumns).trim(), this.quotedId);
        keysComment = keysComment.substring(indexOfOpenParenLocalColumns, keysComment.length());
        final String keysCommentTrimmed = keysComment.trim();
        final int indexOfCloseParenLocalColumns = StringUtils.indexOfIgnoreCase(0, keysCommentTrimmed, ")", this.quotedId, this.quotedId, StringUtils.SEARCH_MODE__ALL);
        if (indexOfCloseParenLocalColumns == -1) {
            throw SQLError.createSQLException(Messages.getString("DatabaseMetaData.15"), "S1000", this.getExceptionInterceptor());
        }
        final String localColumnNamesString = keysCommentTrimmed.substring(1, indexOfCloseParenLocalColumns);
        final int indexOfRefer = StringUtils.indexOfIgnoreCase(0, keysCommentTrimmed, "REFER ", this.quotedId, this.quotedId, StringUtils.SEARCH_MODE__ALL);
        if (indexOfRefer == -1) {
            throw SQLError.createSQLException(Messages.getString("DatabaseMetaData.16"), "S1000", this.getExceptionInterceptor());
        }
        final int indexOfOpenParenReferCol = StringUtils.indexOfIgnoreCase(indexOfRefer, keysCommentTrimmed, "(", this.quotedId, this.quotedId, StringUtils.SEARCH_MODE__MRK_COM_WS);
        if (indexOfOpenParenReferCol == -1) {
            throw SQLError.createSQLException(Messages.getString("DatabaseMetaData.17"), "S1000", this.getExceptionInterceptor());
        }
        final String referCatalogTableString = keysCommentTrimmed.substring(indexOfRefer + "REFER ".length(), indexOfOpenParenReferCol);
        final int indexOfSlash = StringUtils.indexOfIgnoreCase(0, referCatalogTableString, "/", this.quotedId, this.quotedId, StringUtils.SEARCH_MODE__MRK_COM_WS);
        if (indexOfSlash == -1) {
            throw SQLError.createSQLException(Messages.getString("DatabaseMetaData.18"), "S1000", this.getExceptionInterceptor());
        }
        final String referCatalog = StringUtils.unQuoteIdentifier(referCatalogTableString.substring(0, indexOfSlash), this.quotedId);
        final String referTable = StringUtils.unQuoteIdentifier(referCatalogTableString.substring(indexOfSlash + 1).trim(), this.quotedId);
        final int indexOfCloseParenRefer = StringUtils.indexOfIgnoreCase(indexOfOpenParenReferCol, keysCommentTrimmed, ")", this.quotedId, this.quotedId, StringUtils.SEARCH_MODE__ALL);
        if (indexOfCloseParenRefer == -1) {
            throw SQLError.createSQLException(Messages.getString("DatabaseMetaData.19"), "S1000", this.getExceptionInterceptor());
        }
        final String referColumnNamesString = keysCommentTrimmed.substring(indexOfOpenParenReferCol + 1, indexOfCloseParenRefer);
        final List<String> referColumnsList = StringUtils.split(referColumnNamesString, columnsDelimitter, this.quotedId, this.quotedId, false);
        final List<String> localColumnsList = StringUtils.split(localColumnNamesString, columnsDelimitter, this.quotedId, this.quotedId, false);
        return new LocalAndReferencedColumns(localColumnsList, referColumnsList, constraintName, referCatalog, referTable);
    }
    
    protected byte[] s2b(final String s) throws SQLException {
        if (s == null) {
            return null;
        }
        try {
            return StringUtils.getBytes(s, this.conn.getCharacterSetMetadata());
        }
        catch (CJException e) {
            throw SQLExceptionsMapping.translateException(e, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean storesLowerCaseIdentifiers() throws SQLException {
        try {
            return this.conn.storesLowerCaseTableName();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean storesLowerCaseQuotedIdentifiers() throws SQLException {
        try {
            return this.conn.storesLowerCaseTableName();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean storesMixedCaseIdentifiers() throws SQLException {
        try {
            return !this.conn.storesLowerCaseTableName();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean storesMixedCaseQuotedIdentifiers() throws SQLException {
        try {
            return !this.conn.storesLowerCaseTableName();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean storesUpperCaseIdentifiers() throws SQLException {
        try {
            return false;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean storesUpperCaseQuotedIdentifiers() throws SQLException {
        try {
            return true;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean supportsAlterTableWithAddColumn() throws SQLException {
        try {
            return true;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean supportsAlterTableWithDropColumn() throws SQLException {
        try {
            return true;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean supportsANSI92EntryLevelSQL() throws SQLException {
        try {
            return true;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean supportsANSI92FullSQL() throws SQLException {
        try {
            return false;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean supportsANSI92IntermediateSQL() throws SQLException {
        try {
            return false;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean supportsBatchUpdates() throws SQLException {
        try {
            return true;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean supportsCatalogsInDataManipulation() throws SQLException {
        try {
            return true;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean supportsCatalogsInIndexDefinitions() throws SQLException {
        try {
            return true;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean supportsCatalogsInPrivilegeDefinitions() throws SQLException {
        try {
            return true;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean supportsCatalogsInProcedureCalls() throws SQLException {
        try {
            return true;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean supportsCatalogsInTableDefinitions() throws SQLException {
        try {
            return true;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean supportsColumnAliasing() throws SQLException {
        try {
            return true;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean supportsConvert() throws SQLException {
        try {
            return false;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean supportsConvert(final int fromType, final int toType) throws SQLException {
        try {
            return MysqlType.supportsConvert(fromType, toType);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean supportsCoreSQLGrammar() throws SQLException {
        try {
            return true;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean supportsCorrelatedSubqueries() throws SQLException {
        try {
            return true;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean supportsDataDefinitionAndDataManipulationTransactions() throws SQLException {
        try {
            return false;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean supportsDataManipulationTransactionsOnly() throws SQLException {
        try {
            return false;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean supportsDifferentTableCorrelationNames() throws SQLException {
        try {
            return true;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean supportsExpressionsInOrderBy() throws SQLException {
        try {
            return true;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean supportsExtendedSQLGrammar() throws SQLException {
        try {
            return false;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean supportsFullOuterJoins() throws SQLException {
        try {
            return false;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean supportsGetGeneratedKeys() {
        try {
            return true;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean supportsGroupBy() throws SQLException {
        try {
            return true;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean supportsGroupByBeyondSelect() throws SQLException {
        try {
            return true;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean supportsGroupByUnrelated() throws SQLException {
        try {
            return true;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean supportsIntegrityEnhancementFacility() throws SQLException {
        try {
            return this.conn.getPropertySet().getBooleanProperty(PropertyKey.overrideSupportsIntegrityEnhancementFacility).getValue();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean supportsLikeEscapeClause() throws SQLException {
        try {
            return true;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean supportsLimitedOuterJoins() throws SQLException {
        try {
            return true;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean supportsMinimumSQLGrammar() throws SQLException {
        try {
            return true;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean supportsMixedCaseIdentifiers() throws SQLException {
        try {
            return !this.conn.lowerCaseTableNames();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean supportsMixedCaseQuotedIdentifiers() throws SQLException {
        try {
            return !this.conn.lowerCaseTableNames();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean supportsMultipleOpenResults() throws SQLException {
        try {
            return true;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean supportsMultipleResultSets() throws SQLException {
        try {
            return true;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean supportsMultipleTransactions() throws SQLException {
        try {
            return true;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean supportsNamedParameters() throws SQLException {
        try {
            return false;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean supportsNonNullableColumns() throws SQLException {
        try {
            return true;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean supportsOpenCursorsAcrossCommit() throws SQLException {
        try {
            return false;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean supportsOpenCursorsAcrossRollback() throws SQLException {
        try {
            return false;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean supportsOpenStatementsAcrossCommit() throws SQLException {
        try {
            return false;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean supportsOpenStatementsAcrossRollback() throws SQLException {
        try {
            return false;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean supportsOrderByUnrelated() throws SQLException {
        try {
            return false;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean supportsOuterJoins() throws SQLException {
        try {
            return true;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean supportsPositionedDelete() throws SQLException {
        try {
            return false;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean supportsPositionedUpdate() throws SQLException {
        try {
            return false;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean supportsResultSetConcurrency(final int type, final int concurrency) throws SQLException {
        try {
            switch (type) {
                case 1004: {
                    if (concurrency == 1007 || concurrency == 1008) {
                        return true;
                    }
                    throw SQLError.createSQLException(Messages.getString("DatabaseMetaData.20"), "S1009", this.getExceptionInterceptor());
                }
                case 1003: {
                    if (concurrency == 1007 || concurrency == 1008) {
                        return true;
                    }
                    throw SQLError.createSQLException(Messages.getString("DatabaseMetaData.20"), "S1009", this.getExceptionInterceptor());
                }
                case 1005: {
                    return false;
                }
                default: {
                    throw SQLError.createSQLException(Messages.getString("DatabaseMetaData.20"), "S1009", this.getExceptionInterceptor());
                }
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean supportsResultSetHoldability(final int holdability) throws SQLException {
        try {
            return holdability == 1;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean supportsResultSetType(final int type) throws SQLException {
        try {
            return type == 1004;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean supportsSavepoints() throws SQLException {
        try {
            return true;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean supportsSchemasInDataManipulation() throws SQLException {
        try {
            return false;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean supportsSchemasInIndexDefinitions() throws SQLException {
        try {
            return false;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean supportsSchemasInPrivilegeDefinitions() throws SQLException {
        try {
            return false;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean supportsSchemasInProcedureCalls() throws SQLException {
        try {
            return false;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean supportsSchemasInTableDefinitions() throws SQLException {
        try {
            return false;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean supportsSelectForUpdate() throws SQLException {
        try {
            return true;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean supportsStatementPooling() throws SQLException {
        try {
            return false;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean supportsStoredProcedures() throws SQLException {
        try {
            return true;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean supportsSubqueriesInComparisons() throws SQLException {
        try {
            return true;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean supportsSubqueriesInExists() throws SQLException {
        try {
            return true;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean supportsSubqueriesInIns() throws SQLException {
        try {
            return true;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean supportsSubqueriesInQuantifieds() throws SQLException {
        try {
            return true;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean supportsTableCorrelationNames() throws SQLException {
        try {
            return true;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean supportsTransactionIsolationLevel(final int level) throws SQLException {
        try {
            switch (level) {
                case 1:
                case 2:
                case 4:
                case 8: {
                    return true;
                }
                default: {
                    return false;
                }
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean supportsTransactions() throws SQLException {
        try {
            return true;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean supportsUnion() throws SQLException {
        try {
            return true;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean supportsUnionAll() throws SQLException {
        try {
            return true;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean updatesAreDetected(final int type) throws SQLException {
        try {
            return false;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean usesLocalFilePerTable() throws SQLException {
        try {
            return false;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean usesLocalFiles() throws SQLException {
        try {
            return false;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public ResultSet getClientInfoProperties() throws SQLException {
        try {
            final Field[] fields = { new Field("", "NAME", this.metadataCollationIndex, this.metadataEncoding, MysqlType.VARCHAR, 255), new Field("", "MAX_LEN", this.metadataCollationIndex, this.metadataEncoding, MysqlType.INT, 10), new Field("", "DEFAULT_VALUE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.VARCHAR, 255), new Field("", "DESCRIPTION", this.metadataCollationIndex, this.metadataEncoding, MysqlType.VARCHAR, 255) };
            return this.resultSetFactory.createFromResultsetRows(1007, 1004, new ResultsetRowsStatic(new ArrayList<Row>(), new DefaultColumnDefinition(fields)));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public ResultSet getFunctionColumns(final String catalog, final String schemaPattern, final String functionNamePattern, final String columnNamePattern) throws SQLException {
        try {
            final Field[] fields = this.createFunctionColumnsFields();
            return this.getProcedureOrFunctionColumns(fields, catalog, schemaPattern, functionNamePattern, columnNamePattern, false, true);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    protected Field[] createFunctionColumnsFields() {
        final Field[] fields = { new Field("", "FUNCTION_CAT", this.metadataCollationIndex, this.metadataEncoding, MysqlType.VARCHAR, 512), new Field("", "FUNCTION_SCHEM", this.metadataCollationIndex, this.metadataEncoding, MysqlType.VARCHAR, 512), new Field("", "FUNCTION_NAME", this.metadataCollationIndex, this.metadataEncoding, MysqlType.VARCHAR, 512), new Field("", "COLUMN_NAME", this.metadataCollationIndex, this.metadataEncoding, MysqlType.VARCHAR, 512), new Field("", "COLUMN_TYPE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.VARCHAR, 64), new Field("", "DATA_TYPE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.SMALLINT, 6), new Field("", "TYPE_NAME", this.metadataCollationIndex, this.metadataEncoding, MysqlType.VARCHAR, 64), new Field("", "PRECISION", this.metadataCollationIndex, this.metadataEncoding, MysqlType.INT, 12), new Field("", "LENGTH", this.metadataCollationIndex, this.metadataEncoding, MysqlType.INT, 12), new Field("", "SCALE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.SMALLINT, 12), new Field("", "RADIX", this.metadataCollationIndex, this.metadataEncoding, MysqlType.SMALLINT, 6), new Field("", "NULLABLE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.SMALLINT, 6), new Field("", "REMARKS", this.metadataCollationIndex, this.metadataEncoding, MysqlType.VARCHAR, 512), new Field("", "CHAR_OCTET_LENGTH", this.metadataCollationIndex, this.metadataEncoding, MysqlType.INT, 32), new Field("", "ORDINAL_POSITION", this.metadataCollationIndex, this.metadataEncoding, MysqlType.INT, 32), new Field("", "IS_NULLABLE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.VARCHAR, 12), new Field("", "SPECIFIC_NAME", this.metadataCollationIndex, this.metadataEncoding, MysqlType.VARCHAR, 64) };
        return fields;
    }
    
    @Override
    public ResultSet getFunctions(final String catalog, final String schemaPattern, final String functionNamePattern) throws SQLException {
        try {
            final Field[] fields = { new Field("", "FUNCTION_CAT", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 255), new Field("", "FUNCTION_SCHEM", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 255), new Field("", "FUNCTION_NAME", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 255), new Field("", "REMARKS", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 255), new Field("", "FUNCTION_TYPE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.SMALLINT, 6), new Field("", "SPECIFIC_NAME", this.metadataCollationIndex, this.metadataEncoding, MysqlType.CHAR, 255) };
            return this.getProceduresAndOrFunctions(fields, catalog, schemaPattern, functionNamePattern, false, true);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    public boolean providesQueryObjectGenerator() throws SQLException {
        return false;
    }
    
    @Override
    public ResultSet getSchemas(final String catalog, final String schemaPattern) throws SQLException {
        try {
            final Field[] fields = { new Field("", "TABLE_SCHEM", this.metadataCollationIndex, this.metadataEncoding, MysqlType.VARCHAR, 255), new Field("", "TABLE_CATALOG", this.metadataCollationIndex, this.metadataEncoding, MysqlType.VARCHAR, 255) };
            return this.resultSetFactory.createFromResultsetRows(1007, 1004, new ResultsetRowsStatic(new ArrayList<Row>(), new DefaultColumnDefinition(fields)));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean supportsStoredFunctionsUsingCallSyntax() throws SQLException {
        try {
            return true;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    protected PreparedStatement prepareMetaDataSafeStatement(final String sql) throws SQLException {
        final PreparedStatement pStmt = this.conn.clientPrepareStatement(sql);
        if (pStmt.getMaxRows() != 0) {
            pStmt.setMaxRows(0);
        }
        ((JdbcStatement)pStmt).setHoldResultsOpenOverClose(true);
        return pStmt;
    }
    
    @Override
    public ResultSet getPseudoColumns(final String catalog, final String schemaPattern, final String tableNamePattern, final String columnNamePattern) throws SQLException {
        try {
            final Field[] fields = { new Field("", "TABLE_CAT", this.metadataCollationIndex, this.metadataEncoding, MysqlType.VARCHAR, 512), new Field("", "TABLE_SCHEM", this.metadataCollationIndex, this.metadataEncoding, MysqlType.VARCHAR, 512), new Field("", "TABLE_NAME", this.metadataCollationIndex, this.metadataEncoding, MysqlType.VARCHAR, 512), new Field("", "COLUMN_NAME", this.metadataCollationIndex, this.metadataEncoding, MysqlType.VARCHAR, 512), new Field("", "DATA_TYPE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.INT, 12), new Field("", "COLUMN_SIZE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.INT, 12), new Field("", "DECIMAL_DIGITS", this.metadataCollationIndex, this.metadataEncoding, MysqlType.INT, 12), new Field("", "NUM_PREC_RADIX", this.metadataCollationIndex, this.metadataEncoding, MysqlType.INT, 12), new Field("", "COLUMN_USAGE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.VARCHAR, 512), new Field("", "REMARKS", this.metadataCollationIndex, this.metadataEncoding, MysqlType.VARCHAR, 512), new Field("", "CHAR_OCTET_LENGTH", this.metadataCollationIndex, this.metadataEncoding, MysqlType.INT, 12), new Field("", "IS_NULLABLE", this.metadataCollationIndex, this.metadataEncoding, MysqlType.VARCHAR, 512) };
            return this.resultSetFactory.createFromResultsetRows(1007, 1004, new ResultsetRowsStatic(new ArrayList<Row>(), new DefaultColumnDefinition(fields)));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean generatedKeyAlwaysReturned() throws SQLException {
        try {
            return true;
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
                throw SQLError.createSQLException(Messages.getString("Common.UnableToUnwrap", new Object[] { iface.toString() }), "S1009", this.conn.getExceptionInterceptor());
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
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
    public RowIdLifetime getRowIdLifetime() throws SQLException {
        try {
            return RowIdLifetime.ROWID_UNSUPPORTED;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean autoCommitFailureClosesAllResultSets() throws SQLException {
        try {
            return false;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    public String getMetadataEncoding() {
        return this.metadataEncoding;
    }
    
    public void setMetadataEncoding(final String metadataEncoding) {
        this.metadataEncoding = metadataEncoding;
    }
    
    public int getMetadataCollationIndex() {
        return this.metadataCollationIndex;
    }
    
    public void setMetadataCollationIndex(final int metadataCollationIndex) {
        this.metadataCollationIndex = metadataCollationIndex;
    }
    
    static {
        DatabaseMetaData.maxBufferSize = 65535;
        TABLE_AS_BYTES = "TABLE".getBytes();
        SYSTEM_TABLE_AS_BYTES = "SYSTEM TABLE".getBytes();
        VIEW_AS_BYTES = "VIEW".getBytes();
        MYSQL_KEYWORDS = new String[] { "ACCESSIBLE", "ADD", "ALL", "ALTER", "ANALYZE", "AND", "AS", "ASC", "ASENSITIVE", "BEFORE", "BETWEEN", "BIGINT", "BINARY", "BLOB", "BOTH", "BY", "CALL", "CASCADE", "CASE", "CHANGE", "CHAR", "CHARACTER", "CHECK", "COLLATE", "COLUMN", "CONDITION", "CONSTRAINT", "CONTINUE", "CONVERT", "CREATE", "CROSS", "CUBE", "CUME_DIST", "CURRENT_DATE", "CURRENT_TIME", "CURRENT_TIMESTAMP", "CURRENT_USER", "CURSOR", "DATABASE", "DATABASES", "DAY_HOUR", "DAY_MICROSECOND", "DAY_MINUTE", "DAY_SECOND", "DEC", "DECIMAL", "DECLARE", "DEFAULT", "DELAYED", "DELETE", "DENSE_RANK", "DESC", "DESCRIBE", "DETERMINISTIC", "DISTINCT", "DISTINCTROW", "DIV", "DOUBLE", "DROP", "DUAL", "EACH", "ELSE", "ELSEIF", "EMPTY", "ENCLOSED", "ESCAPED", "EXCEPT", "EXISTS", "EXIT", "EXPLAIN", "FALSE", "FETCH", "FIRST_VALUE", "FLOAT", "FLOAT4", "FLOAT8", "FOR", "FORCE", "FOREIGN", "FROM", "FULLTEXT", "FUNCTION", "GENERATED", "GET", "GRANT", "GROUP", "GROUPING", "GROUPS", "HAVING", "HIGH_PRIORITY", "HOUR_MICROSECOND", "HOUR_MINUTE", "HOUR_SECOND", "IF", "IGNORE", "IN", "INDEX", "INFILE", "INNER", "INOUT", "INSENSITIVE", "INSERT", "INT", "INT1", "INT2", "INT3", "INT4", "INT8", "INTEGER", "INTERVAL", "INTO", "IO_AFTER_GTIDS", "IO_BEFORE_GTIDS", "IS", "ITERATE", "JOIN", "JSON_TABLE", "KEY", "KEYS", "KILL", "LAG", "LAST_VALUE", "LEAD", "LEADING", "LEAVE", "LEFT", "LIKE", "LIMIT", "LINEAR", "LINES", "LOAD", "LOCALTIME", "LOCALTIMESTAMP", "LOCK", "LONG", "LONGBLOB", "LONGTEXT", "LOOP", "LOW_PRIORITY", "MASTER_BIND", "MASTER_SSL_VERIFY_SERVER_CERT", "MATCH", "MAXVALUE", "MEDIUMBLOB", "MEDIUMINT", "MEDIUMTEXT", "MIDDLEINT", "MINUTE_MICROSECOND", "MINUTE_SECOND", "MOD", "MODIFIES", "NATURAL", "NOT", "NO_WRITE_TO_BINLOG", "NTH_VALUE", "NTILE", "NULL", "NUMERIC", "OF", "ON", "OPTIMIZE", "OPTIMIZER_COSTS", "OPTION", "OPTIONALLY", "OR", "ORDER", "OUT", "OUTER", "OUTFILE", "OVER", "PARTITION", "PERCENT_RANK", "PERSIST", "PERSIST_ONLY", "PRECISION", "PRIMARY", "PROCEDURE", "PURGE", "RANGE", "RANK", "READ", "READS", "READ_WRITE", "REAL", "RECURSIVE", "REFERENCES", "REGEXP", "RELEASE", "RENAME", "REPEAT", "REPLACE", "REQUIRE", "RESIGNAL", "RESTRICT", "RETURN", "REVOKE", "RIGHT", "RLIKE", "ROW", "ROWS", "ROW_NUMBER", "SCHEMA", "SCHEMAS", "SECOND_MICROSECOND", "SELECT", "SENSITIVE", "SEPARATOR", "SET", "SHOW", "SIGNAL", "SMALLINT", "SPATIAL", "SPECIFIC", "SQL", "SQLEXCEPTION", "SQLSTATE", "SQLWARNING", "SQL_BIG_RESULT", "SQL_CALC_FOUND_ROWS", "SQL_SMALL_RESULT", "SSL", "STARTING", "STORED", "STRAIGHT_JOIN", "SYSTEM", "TABLE", "TERMINATED", "THEN", "TINYBLOB", "TINYINT", "TINYTEXT", "TO", "TRAILING", "TRIGGER", "TRUE", "UNDO", "UNION", "UNIQUE", "UNLOCK", "UNSIGNED", "UPDATE", "USAGE", "USE", "USING", "UTC_DATE", "UTC_TIME", "UTC_TIMESTAMP", "VALUES", "VARBINARY", "VARCHAR", "VARCHARACTER", "VARYING", "VIRTUAL", "WHEN", "WHERE", "WHILE", "WINDOW", "WITH", "WRITE", "XOR", "YEAR_MONTH", "ZEROFILL" };
        SQL2003_KEYWORDS = Arrays.asList("ABS", "ALL", "ALLOCATE", "ALTER", "AND", "ANY", "ARE", "ARRAY", "AS", "ASENSITIVE", "ASYMMETRIC", "AT", "ATOMIC", "AUTHORIZATION", "AVG", "BEGIN", "BETWEEN", "BIGINT", "BINARY", "BLOB", "BOOLEAN", "BOTH", "BY", "CALL", "CALLED", "CARDINALITY", "CASCADED", "CASE", "CAST", "CEIL", "CEILING", "CHAR", "CHARACTER", "CHARACTER_LENGTH", "CHAR_LENGTH", "CHECK", "CLOB", "CLOSE", "COALESCE", "COLLATE", "COLLECT", "COLUMN", "COMMIT", "CONDITION", "CONNECT", "CONSTRAINT", "CONVERT", "CORR", "CORRESPONDING", "COUNT", "COVAR_POP", "COVAR_SAMP", "CREATE", "CROSS", "CUBE", "CUME_DIST", "CURRENT", "CURRENT_DATE", "CURRENT_DEFAULT_TRANSFORM_GROUP", "CURRENT_PATH", "CURRENT_ROLE", "CURRENT_TIME", "CURRENT_TIMESTAMP", "CURRENT_TRANSFORM_GROUP_FOR_TYPE", "CURRENT_USER", "CURSOR", "CYCLE", "DATE", "DAY", "DEALLOCATE", "DEC", "DECIMAL", "DECLARE", "DEFAULT", "DELETE", "DENSE_RANK", "DEREF", "DESCRIBE", "DETERMINISTIC", "DISCONNECT", "DISTINCT", "DOUBLE", "DROP", "DYNAMIC", "EACH", "ELEMENT", "ELSE", "END", "END-EXEC", "ESCAPE", "EVERY", "EXCEPT", "EXEC", "EXECUTE", "EXISTS", "EXP", "EXTERNAL", "EXTRACT", "FALSE", "FETCH", "FILTER", "FLOAT", "FLOOR", "FOR", "FOREIGN", "FREE", "FROM", "FULL", "FUNCTION", "FUSION", "GET", "GLOBAL", "GRANT", "GROUP", "GROUPING", "HAVING", "HOLD", "HOUR", "IDENTITY", "IN", "INDICATOR", "INNER", "INOUT", "INSENSITIVE", "INSERT", "INT", "INTEGER", "INTERSECT", "INTERSECTION", "INTERVAL", "INTO", "IS", "JOIN", "LANGUAGE", "LARGE", "LATERAL", "LEADING", "LEFT", "LIKE", "LN", "LOCAL", "LOCALTIME", "LOCALTIMESTAMP", "LOWER", "MATCH", "MAX", "MEMBER", "MERGE", "METHOD", "MIN", "MINUTE", "MOD", "MODIFIES", "MODULE", "MONTH", "MULTISET", "NATIONAL", "NATURAL", "NCHAR", "NCLOB", "NEW", "NO", "NONE", "NORMALIZE", "NOT", "NULL", "NULLIF", "NUMERIC", "OCTET_LENGTH", "OF", "OLD", "ON", "ONLY", "OPEN", "OR", "ORDER", "OUT", "OUTER", "OVER", "OVERLAPS", "OVERLAY", "PARAMETER", "PARTITION", "PERCENTILE_CONT", "PERCENTILE_DISC", "PERCENT_RANK", "POSITION", "POWER", "PRECISION", "PREPARE", "PRIMARY", "PROCEDURE", "RANGE", "RANK", "READS", "REAL", "RECURSIVE", "REF", "REFERENCES", "REFERENCING", "REGR_AVGX", "REGR_AVGY", "REGR_COUNT", "REGR_INTERCEPT", "REGR_R2", "REGR_SLOPE", "REGR_SXX", "REGR_SXY", "REGR_SYY", "RELEASE", "RESULT", "RETURN", "RETURNS", "REVOKE", "RIGHT", "ROLLBACK", "ROLLUP", "ROW", "ROWS", "ROW_NUMBER", "SAVEPOINT", "SCOPE", "SCROLL", "SEARCH", "SECOND", "SELECT", "SENSITIVE", "SESSION_USER", "SET", "SIMILAR", "SMALLINT", "SOME", "SPECIFIC", "SPECIFICTYPE", "SQL", "SQLEXCEPTION", "SQLSTATE", "SQLWARNING", "SQRT", "START", "STATIC", "STDDEV_POP", "STDDEV_SAMP", "SUBMULTISET", "SUBSTRING", "SUM", "SYMMETRIC", "SYSTEM", "SYSTEM_USER", "TABLE", "TABLESAMPLE", "THEN", "TIME", "TIMESTAMP", "TIMEZONE_HOUR", "TIMEZONE_MINUTE", "TO", "TRAILING", "TRANSLATE", "TRANSLATION", "TREAT", "TRIGGER", "TRIM", "TRUE", "UESCAPE", "UNION", "UNIQUE", "UNKNOWN", "UNNEST", "UPDATE", "UPPER", "USER", "USING", "VALUE", "VALUES", "VARCHAR", "VARYING", "VAR_POP", "VAR_SAMP", "WHEN", "WHENEVER", "WHERE", "WIDTH_BUCKET", "WINDOW", "WITH", "WITHIN", "WITHOUT", "YEAR");
        DatabaseMetaData.mysqlKeywords = null;
    }
    
    protected abstract class IteratorWithCleanup<T>
    {
        abstract void close() throws SQLException;
        
        abstract boolean hasNext() throws SQLException;
        
        abstract T next() throws SQLException;
    }
    
    class LocalAndReferencedColumns
    {
        String constraintName;
        List<String> localColumnsList;
        String referencedCatalog;
        List<String> referencedColumnsList;
        String referencedTable;
        
        LocalAndReferencedColumns(final List<String> localColumns, final List<String> refColumns, final String constName, final String refCatalog, final String refTable) {
            this.localColumnsList = localColumns;
            this.referencedColumnsList = refColumns;
            this.constraintName = constName;
            this.referencedTable = refTable;
            this.referencedCatalog = refCatalog;
        }
    }
    
    protected class ResultSetIterator extends IteratorWithCleanup<String>
    {
        int colIndex;
        ResultSet resultSet;
        
        ResultSetIterator(final ResultSet rs, final int index) {
            this.resultSet = rs;
            this.colIndex = index;
        }
        
        @Override
        void close() throws SQLException {
            this.resultSet.close();
        }
        
        @Override
        boolean hasNext() throws SQLException {
            return this.resultSet.next();
        }
        
        @Override
        String next() throws SQLException {
            return this.resultSet.getObject(this.colIndex).toString();
        }
    }
    
    protected class SingleStringIterator extends IteratorWithCleanup<String>
    {
        boolean onFirst;
        String value;
        
        SingleStringIterator(final String s) {
            this.onFirst = true;
            this.value = s;
        }
        
        @Override
        void close() throws SQLException {
        }
        
        @Override
        boolean hasNext() throws SQLException {
            return this.onFirst;
        }
        
        @Override
        String next() throws SQLException {
            this.onFirst = false;
            return this.value;
        }
    }
    
    class TypeDescriptor
    {
        int bufferLength;
        int charOctetLength;
        Integer columnSize;
        Integer decimalDigits;
        String isNullable;
        int nullability;
        int numPrecRadix;
        String mysqlTypeName;
        MysqlType mysqlType;
        
        TypeDescriptor(final String typeInfo, final String nullabilityInfo) throws SQLException {
            this.columnSize = null;
            this.decimalDigits = null;
            this.numPrecRadix = 10;
            if (typeInfo == null) {
                throw SQLError.createSQLException(Messages.getString("DatabaseMetaData.0"), "S1009", DatabaseMetaData.this.getExceptionInterceptor());
            }
            this.mysqlType = MysqlType.getByName(typeInfo);
            int maxLength = 0;
            switch (this.mysqlType) {
                case ENUM: {
                    final String temp = typeInfo.substring(typeInfo.indexOf("(") + 1, typeInfo.lastIndexOf(")"));
                    final StringTokenizer tokenizer = new StringTokenizer(temp, ",");
                    while (tokenizer.hasMoreTokens()) {
                        final String nextToken = tokenizer.nextToken();
                        maxLength = Math.max(maxLength, nextToken.length() - 2);
                    }
                    this.columnSize = maxLength;
                    break;
                }
                case SET: {
                    final String temp = typeInfo.substring(typeInfo.indexOf("(") + 1, typeInfo.lastIndexOf(")"));
                    final StringTokenizer tokenizer = new StringTokenizer(temp, ",");
                    final int numElements = tokenizer.countTokens();
                    if (numElements > 0) {
                        maxLength += numElements - 1;
                    }
                    while (tokenizer.hasMoreTokens()) {
                        final String setMember = tokenizer.nextToken().trim();
                        if (setMember.startsWith("'") && setMember.endsWith("'")) {
                            maxLength += setMember.length() - 2;
                        }
                        else {
                            maxLength += setMember.length();
                        }
                    }
                    this.columnSize = maxLength;
                    break;
                }
                case DECIMAL:
                case DECIMAL_UNSIGNED:
                case FLOAT:
                case FLOAT_UNSIGNED:
                case DOUBLE:
                case DOUBLE_UNSIGNED: {
                    if (typeInfo.indexOf(",") != -1) {
                        this.columnSize = Integer.valueOf(typeInfo.substring(typeInfo.indexOf("(") + 1, typeInfo.indexOf(",")).trim());
                        this.decimalDigits = Integer.valueOf(typeInfo.substring(typeInfo.indexOf(",") + 1, typeInfo.indexOf(")")).trim());
                        break;
                    }
                    switch (this.mysqlType) {
                        case DECIMAL:
                        case DECIMAL_UNSIGNED: {
                            this.columnSize = 65;
                            break;
                        }
                        case FLOAT:
                        case FLOAT_UNSIGNED: {
                            this.columnSize = 12;
                            break;
                        }
                        case DOUBLE:
                        case DOUBLE_UNSIGNED: {
                            this.columnSize = 22;
                            break;
                        }
                    }
                    this.decimalDigits = 0;
                    break;
                }
                case CHAR:
                case VARCHAR:
                case TINYTEXT:
                case MEDIUMTEXT:
                case LONGTEXT:
                case JSON:
                case TEXT:
                case TINYBLOB:
                case MEDIUMBLOB:
                case LONGBLOB:
                case BLOB:
                case BINARY:
                case VARBINARY:
                case BIT: {
                    if (this.mysqlType == MysqlType.CHAR) {
                        this.columnSize = 1;
                    }
                    if (typeInfo.indexOf("(") != -1) {
                        int endParenIndex = typeInfo.indexOf(")");
                        if (endParenIndex == -1) {
                            endParenIndex = typeInfo.length();
                        }
                        this.columnSize = Integer.valueOf(typeInfo.substring(typeInfo.indexOf("(") + 1, endParenIndex).trim());
                        if (DatabaseMetaData.this.tinyInt1isBit && this.columnSize == 1 && StringUtils.startsWithIgnoreCase(typeInfo, 0, "tinyint")) {
                            if (DatabaseMetaData.this.transformedBitIsBoolean) {
                                this.mysqlType = MysqlType.BOOLEAN;
                            }
                            else {
                                this.mysqlType = MysqlType.BIT;
                            }
                        }
                        break;
                    }
                    break;
                }
                case TINYINT:
                case TINYINT_UNSIGNED: {
                    if (!DatabaseMetaData.this.tinyInt1isBit || typeInfo.indexOf("(1)") == -1) {
                        this.columnSize = 3;
                        break;
                    }
                    if (DatabaseMetaData.this.transformedBitIsBoolean) {
                        this.mysqlType = MysqlType.BOOLEAN;
                        break;
                    }
                    this.mysqlType = MysqlType.BIT;
                    break;
                }
            }
            if (this.columnSize == null) {
                this.columnSize = ((this.mysqlType.getPrecision() > 2147483647L) ? Integer.MAX_VALUE : this.mysqlType.getPrecision().intValue());
            }
            this.bufferLength = DatabaseMetaData.maxBufferSize;
            this.numPrecRadix = 10;
            if (nullabilityInfo != null) {
                if (nullabilityInfo.equals("YES")) {
                    this.nullability = 1;
                    this.isNullable = "YES";
                }
                else if (nullabilityInfo.equals("UNKNOWN")) {
                    this.nullability = 2;
                    this.isNullable = "";
                }
                else {
                    this.nullability = 0;
                    this.isNullable = "NO";
                }
            }
            else {
                this.nullability = 0;
                this.isNullable = "NO";
            }
        }
    }
    
    protected class IndexMetaDataKey implements Comparable<IndexMetaDataKey>
    {
        Boolean columnNonUnique;
        Short columnType;
        String columnIndexName;
        Short columnOrdinalPosition;
        
        IndexMetaDataKey(final boolean columnNonUnique, final short columnType, final String columnIndexName, final short columnOrdinalPosition) {
            this.columnNonUnique = columnNonUnique;
            this.columnType = columnType;
            this.columnIndexName = columnIndexName;
            this.columnOrdinalPosition = columnOrdinalPosition;
        }
        
        @Override
        public int compareTo(final IndexMetaDataKey indexInfoKey) {
            int compareResult;
            if ((compareResult = this.columnNonUnique.compareTo(indexInfoKey.columnNonUnique)) != 0) {
                return compareResult;
            }
            if ((compareResult = this.columnType.compareTo(indexInfoKey.columnType)) != 0) {
                return compareResult;
            }
            if ((compareResult = this.columnIndexName.compareTo(indexInfoKey.columnIndexName)) != 0) {
                return compareResult;
            }
            return this.columnOrdinalPosition.compareTo(indexInfoKey.columnOrdinalPosition);
        }
        
        @Override
        public boolean equals(final Object obj) {
            return obj != null && (obj == this || (obj instanceof IndexMetaDataKey && this.compareTo((IndexMetaDataKey)obj) == 0));
        }
        
        @Override
        public int hashCode() {
            assert false : "hashCode not designed";
            return 0;
        }
    }
    
    protected class TableMetaDataKey implements Comparable<TableMetaDataKey>
    {
        String tableType;
        String tableCat;
        String tableSchem;
        String tableName;
        
        TableMetaDataKey(final String tableType, final String tableCat, final String tableSchem, final String tableName) {
            this.tableType = ((tableType == null) ? "" : tableType);
            this.tableCat = ((tableCat == null) ? "" : tableCat);
            this.tableSchem = ((tableSchem == null) ? "" : tableSchem);
            this.tableName = ((tableName == null) ? "" : tableName);
        }
        
        @Override
        public int compareTo(final TableMetaDataKey tablesKey) {
            int compareResult;
            if ((compareResult = this.tableType.compareTo(tablesKey.tableType)) != 0) {
                return compareResult;
            }
            if ((compareResult = this.tableCat.compareTo(tablesKey.tableCat)) != 0) {
                return compareResult;
            }
            if ((compareResult = this.tableSchem.compareTo(tablesKey.tableSchem)) != 0) {
                return compareResult;
            }
            return this.tableName.compareTo(tablesKey.tableName);
        }
        
        @Override
        public boolean equals(final Object obj) {
            return obj != null && (obj == this || (obj instanceof TableMetaDataKey && this.compareTo((TableMetaDataKey)obj) == 0));
        }
        
        @Override
        public int hashCode() {
            assert false : "hashCode not designed";
            return 0;
        }
    }
    
    protected class ComparableWrapper<K extends java.lang.Object, V> implements Comparable<ComparableWrapper<K, V>>
    {
        K key;
        V value;
        
        public ComparableWrapper(final K key, final V value) {
            this.key = key;
            this.value = value;
        }
        
        public K getKey() {
            return this.key;
        }
        
        public V getValue() {
            return this.value;
        }
        
        @Override
        public int compareTo(final ComparableWrapper<K, V> other) {
            return this.getKey().compareTo(other.getKey());
        }
        
        @Override
        public boolean equals(final Object obj) {
            if (obj == null) {
                return false;
            }
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof ComparableWrapper)) {
                return false;
            }
            final Object otherKey = ((ComparableWrapper)obj).getKey();
            return this.key.equals(otherKey);
        }
        
        @Override
        public int hashCode() {
            assert false : "hashCode not designed";
            return 0;
        }
        
        @Override
        public String toString() {
            return "{KEY:" + this.key + "; VALUE:" + this.value + "}";
        }
    }
    
    protected enum TableType
    {
        LOCAL_TEMPORARY("LOCAL TEMPORARY"), 
        SYSTEM_TABLE("SYSTEM TABLE"), 
        SYSTEM_VIEW("SYSTEM VIEW"), 
        TABLE("TABLE", new String[] { "BASE TABLE" }), 
        VIEW("VIEW"), 
        UNKNOWN("UNKNOWN");
        
        private String name;
        private byte[] nameAsBytes;
        private String[] synonyms;
        
        private TableType(final String tableTypeName) {
            this(tableTypeName, null);
        }
        
        private TableType(final String tableTypeName, final String[] tableTypeSynonyms) {
            this.name = tableTypeName;
            this.nameAsBytes = tableTypeName.getBytes();
            this.synonyms = tableTypeSynonyms;
        }
        
        String getName() {
            return this.name;
        }
        
        byte[] asBytes() {
            return this.nameAsBytes;
        }
        
        boolean equalsTo(final String tableTypeName) {
            return this.name.equalsIgnoreCase(tableTypeName);
        }
        
        static TableType getTableTypeEqualTo(final String tableTypeName) {
            for (final TableType tableType : values()) {
                if (tableType.equalsTo(tableTypeName)) {
                    return tableType;
                }
            }
            return TableType.UNKNOWN;
        }
        
        boolean compliesWith(final String tableTypeName) {
            if (this.equalsTo(tableTypeName)) {
                return true;
            }
            if (this.synonyms != null) {
                for (final String synonym : this.synonyms) {
                    if (synonym.equalsIgnoreCase(tableTypeName)) {
                        return true;
                    }
                }
            }
            return false;
        }
        
        static TableType getTableTypeCompliantWith(final String tableTypeName) {
            for (final TableType tableType : values()) {
                if (tableType.compliesWith(tableTypeName)) {
                    return tableType;
                }
            }
            return TableType.UNKNOWN;
        }
    }
    
    protected enum ProcedureType
    {
        PROCEDURE, 
        FUNCTION;
    }
}
