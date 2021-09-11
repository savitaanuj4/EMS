
package com.mysql.cj.jdbc.result;

import java.io.UnsupportedEncodingException;
import com.mysql.cj.jdbc.MysqlSQLXML;
import java.sql.SQLXML;
import java.sql.NClob;
import java.sql.Timestamp;
import java.sql.Time;
import com.mysql.cj.exceptions.FeatureNotAvailableException;
import java.sql.JDBCType;
import java.sql.Date;
import java.sql.Clob;
import java.io.Reader;
import java.sql.Blob;
import java.math.BigDecimal;
import java.io.InputStream;
import java.sql.SQLType;
import com.mysql.cj.protocol.ResultsetRow;
import com.mysql.cj.log.ProfilerEvent;
import com.mysql.cj.log.ProfilerEventImpl;
import com.mysql.cj.Constants;
import com.mysql.cj.Session;
import com.mysql.cj.log.ProfilerEventHandlerFactory;
import com.mysql.cj.protocol.a.result.ByteArrayRow;
import com.mysql.cj.util.StringUtils;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeMap;
import java.util.Iterator;
import java.util.Calendar;
import com.mysql.cj.MysqlType;
import com.mysql.cj.jdbc.exceptions.NotUpdatable;
import java.sql.ResultSet;
import java.sql.DatabaseMetaData;
import com.mysql.cj.result.Field;
import com.mysql.cj.exceptions.AssertionFailedException;
import java.util.HashMap;
import com.mysql.cj.jdbc.exceptions.SQLError;
import com.mysql.cj.Messages;
import com.mysql.cj.exceptions.CJException;
import com.mysql.cj.jdbc.exceptions.SQLExceptionsMapping;
import java.sql.SQLException;
import com.mysql.cj.conf.PropertyKey;
import com.mysql.cj.jdbc.StatementImpl;
import com.mysql.cj.jdbc.JdbcConnection;
import com.mysql.cj.protocol.ResultsetRows;
import java.util.Map;
import com.mysql.cj.result.Row;
import java.util.List;
import com.mysql.cj.jdbc.ClientPreparedStatement;

public class UpdatableResultSet extends ResultSetImpl
{
    static final byte[] STREAM_DATA_MARKER;
    private String charEncoding;
    private byte[][] defaultColumnValue;
    private ClientPreparedStatement deleter;
    private String deleteSQL;
    protected ClientPreparedStatement inserter;
    private String insertSQL;
    private boolean isUpdatable;
    private String notUpdatableReason;
    private List<Integer> primaryKeyIndicies;
    private String qualifiedAndQuotedTableName;
    private String quotedIdChar;
    private ClientPreparedStatement refresher;
    private String refreshSQL;
    private Row savedCurrentRow;
    protected ClientPreparedStatement updater;
    private String updateSQL;
    private boolean populateInserterWithDefaultValues;
    private boolean pedantic;
    private boolean hasLongColumnInfo;
    private Map<String, Map<String, Map<String, Integer>>> databasesUsedToTablesUsed;
    private boolean onInsertRow;
    protected boolean doingUpdates;
    
    public UpdatableResultSet(final ResultsetRows tuples, final JdbcConnection conn, final StatementImpl creatorStmt) throws SQLException {
        super(tuples, conn, creatorStmt);
        this.deleter = null;
        this.deleteSQL = null;
        this.inserter = null;
        this.insertSQL = null;
        this.isUpdatable = false;
        this.notUpdatableReason = null;
        this.primaryKeyIndicies = null;
        this.quotedIdChar = null;
        this.refreshSQL = null;
        this.updater = null;
        this.updateSQL = null;
        this.populateInserterWithDefaultValues = false;
        this.hasLongColumnInfo = false;
        this.databasesUsedToTablesUsed = null;
        this.onInsertRow = false;
        this.doingUpdates = false;
        this.checkUpdatability();
        this.populateInserterWithDefaultValues = this.getSession().getPropertySet().getBooleanProperty(PropertyKey.populateInsertRowWithDefaultValues).getValue();
        this.pedantic = this.getSession().getPropertySet().getBooleanProperty(PropertyKey.pedantic).getValue();
        this.hasLongColumnInfo = this.getSession().getServerSession().hasLongColumnInfo();
    }
    
    @Override
    public boolean absolute(final int row) throws SQLException {
        try {
            if (this.onInsertRow) {
                this.onInsertRow = false;
            }
            if (this.doingUpdates) {
                this.doingUpdates = false;
            }
            return super.absolute(row);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void afterLast() throws SQLException {
        try {
            if (this.onInsertRow) {
                this.onInsertRow = false;
            }
            if (this.doingUpdates) {
                this.doingUpdates = false;
            }
            super.afterLast();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void beforeFirst() throws SQLException {
        try {
            if (this.onInsertRow) {
                this.onInsertRow = false;
            }
            if (this.doingUpdates) {
                this.doingUpdates = false;
            }
            super.beforeFirst();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void cancelRowUpdates() throws SQLException {
        try {
            if (this.doingUpdates) {
                this.doingUpdates = false;
                this.updater.clearParameters();
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    protected void checkRowPos() throws SQLException {
        if (!this.onInsertRow) {
            super.checkRowPos();
        }
    }
    
    public void checkUpdatability() throws SQLException {
        try {
            if (this.getMetadata() == null) {
                return;
            }
            String singleTableName = null;
            String catalogName = null;
            int primaryKeyCount = 0;
            final Field[] fields = this.getMetadata().getFields();
            if (this.catalog == null || this.catalog.length() == 0) {
                this.catalog = fields[0].getDatabaseName();
                if (this.catalog == null || this.catalog.length() == 0) {
                    throw SQLError.createSQLException(Messages.getString("UpdatableResultSet.43"), "S1009", this.getExceptionInterceptor());
                }
            }
            if (fields.length <= 0) {
                this.isUpdatable = false;
                this.notUpdatableReason = Messages.getString("NotUpdatableReason.3");
                return;
            }
            singleTableName = fields[0].getOriginalTableName();
            catalogName = fields[0].getDatabaseName();
            if (singleTableName == null) {
                singleTableName = fields[0].getTableName();
                catalogName = this.catalog;
            }
            if (singleTableName == null) {
                this.isUpdatable = false;
                this.notUpdatableReason = Messages.getString("NotUpdatableReason.3");
                return;
            }
            if (fields[0].isPrimaryKey()) {
                ++primaryKeyCount;
            }
            for (int i = 1; i < fields.length; ++i) {
                String otherTableName = fields[i].getOriginalTableName();
                String otherCatalogName = fields[i].getDatabaseName();
                if (otherTableName == null) {
                    otherTableName = fields[i].getTableName();
                    otherCatalogName = this.catalog;
                }
                if (otherTableName == null) {
                    this.isUpdatable = false;
                    this.notUpdatableReason = Messages.getString("NotUpdatableReason.3");
                    return;
                }
                if (!otherTableName.equals(singleTableName)) {
                    this.isUpdatable = false;
                    this.notUpdatableReason = Messages.getString("NotUpdatableReason.0");
                    return;
                }
                if (catalogName == null || !otherCatalogName.equals(catalogName)) {
                    this.isUpdatable = false;
                    this.notUpdatableReason = Messages.getString("NotUpdatableReason.1");
                    return;
                }
                if (fields[i].isPrimaryKey()) {
                    ++primaryKeyCount;
                }
            }
            if (this.getSession().getPropertySet().getBooleanProperty(PropertyKey.strictUpdates).getValue()) {
                final DatabaseMetaData dbmd = this.getConnection().getMetaData();
                ResultSet rs = null;
                final HashMap<String, String> primaryKeyNames = new HashMap<String, String>();
                try {
                    rs = dbmd.getPrimaryKeys(catalogName, null, singleTableName);
                    while (rs.next()) {
                        String keyName = rs.getString(4);
                        keyName = keyName.toUpperCase();
                        primaryKeyNames.put(keyName, keyName);
                    }
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
                }
                final int existingPrimaryKeysCount = primaryKeyNames.size();
                if (existingPrimaryKeysCount == 0) {
                    this.isUpdatable = false;
                    this.notUpdatableReason = Messages.getString("NotUpdatableReason.5");
                    return;
                }
                for (int j = 0; j < fields.length; ++j) {
                    if (fields[j].isPrimaryKey()) {
                        final String columnNameUC = fields[j].getName().toUpperCase();
                        if (primaryKeyNames.remove(columnNameUC) == null) {
                            final String originalName = fields[j].getOriginalName();
                            if (originalName != null && primaryKeyNames.remove(originalName.toUpperCase()) == null) {
                                this.isUpdatable = false;
                                this.notUpdatableReason = Messages.getString("NotUpdatableReason.6", new Object[] { originalName });
                                return;
                            }
                        }
                    }
                }
                if (!(this.isUpdatable = primaryKeyNames.isEmpty())) {
                    this.notUpdatableReason = ((existingPrimaryKeysCount > 1) ? Messages.getString("NotUpdatableReason.7") : Messages.getString("NotUpdatableReason.4"));
                    return;
                }
            }
            if (primaryKeyCount == 0) {
                this.isUpdatable = false;
                this.notUpdatableReason = Messages.getString("NotUpdatableReason.4");
                return;
            }
            this.isUpdatable = true;
            this.notUpdatableReason = null;
        }
        catch (SQLException sqlEx) {
            this.isUpdatable = false;
            this.notUpdatableReason = sqlEx.getMessage();
        }
    }
    
    @Override
    public void deleteRow() throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                if (!this.isUpdatable) {
                    throw new NotUpdatable(this.notUpdatableReason);
                }
                if (this.onInsertRow) {
                    throw SQLError.createSQLException(Messages.getString("UpdatableResultSet.1"), this.getExceptionInterceptor());
                }
                if (this.rowData.size() == 0) {
                    throw SQLError.createSQLException(Messages.getString("UpdatableResultSet.2"), this.getExceptionInterceptor());
                }
                if (this.isBeforeFirst()) {
                    throw SQLError.createSQLException(Messages.getString("UpdatableResultSet.3"), this.getExceptionInterceptor());
                }
                if (this.isAfterLast()) {
                    throw SQLError.createSQLException(Messages.getString("UpdatableResultSet.4"), this.getExceptionInterceptor());
                }
                if (this.deleter == null) {
                    if (this.deleteSQL == null) {
                        this.generateStatements();
                    }
                    this.deleter = (ClientPreparedStatement)this.connection.clientPrepareStatement(this.deleteSQL);
                }
                this.deleter.clearParameters();
                for (int numKeys = this.primaryKeyIndicies.size(), i = 0; i < numKeys; ++i) {
                    final int index = this.primaryKeyIndicies.get(i);
                    this.setParamValue(this.deleter, i + 1, this.thisRow, index, this.getMetadata().getFields()[index]);
                }
                this.deleter.executeUpdate();
                this.rowData.remove();
                this.previous();
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    private void setParamValue(final ClientPreparedStatement ps, final int psIdx, final Row row, final int rsIdx, final Field field) throws SQLException {
        final byte[] val = row.getBytes(rsIdx);
        if (val == null) {
            ps.setNull(psIdx, MysqlType.NULL);
            return;
        }
        switch (field.getMysqlType()) {
            case NULL: {
                ps.setNull(psIdx, MysqlType.NULL);
                break;
            }
            case TINYINT:
            case TINYINT_UNSIGNED:
            case SMALLINT:
            case SMALLINT_UNSIGNED:
            case MEDIUMINT:
            case MEDIUMINT_UNSIGNED:
            case INT:
            case INT_UNSIGNED:
            case YEAR: {
                ps.setInt(psIdx, this.getInt(rsIdx + 1));
                break;
            }
            case BIGINT: {
                ps.setLong(psIdx, this.getLong(rsIdx + 1));
                break;
            }
            case BIGINT_UNSIGNED: {
                ps.setBigInteger(psIdx, this.getBigInteger(rsIdx + 1));
                break;
            }
            case CHAR:
            case ENUM:
            case SET:
            case VARCHAR:
            case JSON:
            case TINYTEXT:
            case TEXT:
            case MEDIUMTEXT:
            case LONGTEXT:
            case DECIMAL:
            case DECIMAL_UNSIGNED: {
                ps.setString(psIdx, this.getString(rsIdx + 1));
                break;
            }
            case DATE: {
                ps.setDate(psIdx, this.getDate(rsIdx + 1));
                break;
            }
            case TIMESTAMP:
            case DATETIME: {
                ps.setTimestamp(psIdx, this.getTimestamp(rsIdx + 1), null, field.getDecimals());
                break;
            }
            case TIME: {
                ps.setTime(psIdx, this.getTime(rsIdx + 1));
                break;
            }
            case DOUBLE:
            case DOUBLE_UNSIGNED:
            case FLOAT:
            case FLOAT_UNSIGNED:
            case BOOLEAN:
            case BIT: {
                ps.setBytesNoEscapeNoQuotes(psIdx, val);
                break;
            }
            default: {
                ps.setBytes(psIdx, val);
                break;
            }
        }
    }
    
    private void extractDefaultValues() throws SQLException {
        final DatabaseMetaData dbmd = this.getConnection().getMetaData();
        this.defaultColumnValue = new byte[this.getMetadata().getFields().length][];
        ResultSet columnsResultSet = null;
        for (final Map.Entry<String, Map<String, Map<String, Integer>>> dbEntry : this.databasesUsedToTablesUsed.entrySet()) {
            for (final Map.Entry<String, Map<String, Integer>> tableEntry : dbEntry.getValue().entrySet()) {
                final String tableName = tableEntry.getKey();
                final Map<String, Integer> columnNamesToIndices = tableEntry.getValue();
                try {
                    columnsResultSet = dbmd.getColumns(this.catalog, null, tableName, "%");
                    while (columnsResultSet.next()) {
                        final String columnName = columnsResultSet.getString("COLUMN_NAME");
                        final byte[] defaultValue = columnsResultSet.getBytes("COLUMN_DEF");
                        if (columnNamesToIndices.containsKey(columnName)) {
                            final int localColumnIndex = columnNamesToIndices.get(columnName);
                            this.defaultColumnValue[localColumnIndex] = defaultValue;
                        }
                    }
                }
                finally {
                    if (columnsResultSet != null) {
                        columnsResultSet.close();
                        columnsResultSet = null;
                    }
                }
            }
        }
    }
    
    @Override
    public boolean first() throws SQLException {
        try {
            if (this.onInsertRow) {
                this.onInsertRow = false;
            }
            if (this.doingUpdates) {
                this.doingUpdates = false;
            }
            return super.first();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    protected void generateStatements() throws SQLException {
        try {
            if (!this.isUpdatable) {
                this.doingUpdates = false;
                this.onInsertRow = false;
                throw new NotUpdatable(this.notUpdatableReason);
            }
            final String quotedId = this.getQuotedIdChar();
            Map<String, String> tableNamesSoFar = null;
            if (this.session.getServerSession().isLowerCaseTableNames()) {
                tableNamesSoFar = new TreeMap<String, String>(String.CASE_INSENSITIVE_ORDER);
                this.databasesUsedToTablesUsed = new TreeMap<String, Map<String, Map<String, Integer>>>(String.CASE_INSENSITIVE_ORDER);
            }
            else {
                tableNamesSoFar = new TreeMap<String, String>();
                this.databasesUsedToTablesUsed = new TreeMap<String, Map<String, Map<String, Integer>>>();
            }
            this.primaryKeyIndicies = new ArrayList<Integer>();
            final StringBuilder fieldValues = new StringBuilder();
            final StringBuilder keyValues = new StringBuilder();
            final StringBuilder columnNames = new StringBuilder();
            final StringBuilder insertPlaceHolders = new StringBuilder();
            final StringBuilder allTablesBuf = new StringBuilder();
            final Map<Integer, String> columnIndicesToTable = new HashMap<Integer, String>();
            final Field[] fields = this.getMetadata().getFields();
            for (int i = 0; i < fields.length; ++i) {
                Map<String, Integer> updColumnNameToIndex = null;
                if (fields[i].getOriginalTableName() != null) {
                    final String databaseName = fields[i].getDatabaseName();
                    final String tableOnlyName = fields[i].getOriginalTableName();
                    final String fqTableName = StringUtils.getFullyQualifiedName(databaseName, tableOnlyName, quotedId, this.pedantic);
                    if (!tableNamesSoFar.containsKey(fqTableName)) {
                        if (!tableNamesSoFar.isEmpty()) {
                            allTablesBuf.append(',');
                        }
                        allTablesBuf.append(fqTableName);
                        tableNamesSoFar.put(fqTableName, fqTableName);
                    }
                    columnIndicesToTable.put(i, fqTableName);
                    updColumnNameToIndex = this.getColumnsToIndexMapForTableAndDB(databaseName, tableOnlyName);
                }
                else {
                    final String tableOnlyName2 = fields[i].getTableName();
                    if (tableOnlyName2 != null) {
                        final String fqTableName2 = StringUtils.quoteIdentifier(tableOnlyName2, quotedId, this.pedantic);
                        if (!tableNamesSoFar.containsKey(fqTableName2)) {
                            if (!tableNamesSoFar.isEmpty()) {
                                allTablesBuf.append(',');
                            }
                            allTablesBuf.append(fqTableName2);
                            tableNamesSoFar.put(fqTableName2, fqTableName2);
                        }
                        columnIndicesToTable.put(i, fqTableName2);
                        updColumnNameToIndex = this.getColumnsToIndexMapForTableAndDB(this.catalog, tableOnlyName2);
                    }
                }
                final String originalColumnName = fields[i].getOriginalName();
                final String columnName = (this.hasLongColumnInfo && originalColumnName != null && originalColumnName.length() > 0) ? originalColumnName : fields[i].getName();
                if (updColumnNameToIndex != null && columnName != null) {
                    updColumnNameToIndex.put(columnName, i);
                }
                final String originalTableName = fields[i].getOriginalTableName();
                final String tableName = (this.hasLongColumnInfo && originalTableName != null && originalTableName.length() > 0) ? originalTableName : fields[i].getTableName();
                final String databaseName2 = fields[i].getDatabaseName();
                final String qualifiedColumnName = StringUtils.getFullyQualifiedName(databaseName2, tableName, quotedId, this.pedantic) + '.' + StringUtils.quoteIdentifier(columnName, quotedId, this.pedantic);
                if (fields[i].isPrimaryKey()) {
                    this.primaryKeyIndicies.add(i);
                    if (keyValues.length() > 0) {
                        keyValues.append(" AND ");
                    }
                    keyValues.append(qualifiedColumnName);
                    keyValues.append("<=>");
                    keyValues.append("?");
                }
                if (fieldValues.length() == 0) {
                    fieldValues.append("SET ");
                }
                else {
                    fieldValues.append(",");
                    columnNames.append(",");
                    insertPlaceHolders.append(",");
                }
                insertPlaceHolders.append("?");
                columnNames.append(qualifiedColumnName);
                fieldValues.append(qualifiedColumnName);
                fieldValues.append("=?");
            }
            this.qualifiedAndQuotedTableName = allTablesBuf.toString();
            this.updateSQL = "UPDATE " + this.qualifiedAndQuotedTableName + " " + fieldValues.toString() + " WHERE " + keyValues.toString();
            this.insertSQL = "INSERT INTO " + this.qualifiedAndQuotedTableName + " (" + columnNames.toString() + ") VALUES (" + insertPlaceHolders.toString() + ")";
            this.refreshSQL = "SELECT " + columnNames.toString() + " FROM " + this.qualifiedAndQuotedTableName + " WHERE " + keyValues.toString();
            this.deleteSQL = "DELETE FROM " + this.qualifiedAndQuotedTableName + " WHERE " + keyValues.toString();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    private Map<String, Integer> getColumnsToIndexMapForTableAndDB(final String databaseName, final String tableName) {
        Map<String, Map<String, Integer>> tablesUsedToColumnsMap = this.databasesUsedToTablesUsed.get(databaseName);
        if (tablesUsedToColumnsMap == null) {
            tablesUsedToColumnsMap = (this.session.getServerSession().isLowerCaseTableNames() ? new TreeMap<String, Map<String, Integer>>(String.CASE_INSENSITIVE_ORDER) : new TreeMap<String, Map<String, Integer>>());
            this.databasesUsedToTablesUsed.put(databaseName, tablesUsedToColumnsMap);
        }
        Map<String, Integer> nameToIndex = tablesUsedToColumnsMap.get(tableName);
        if (nameToIndex == null) {
            nameToIndex = new HashMap<String, Integer>();
            tablesUsedToColumnsMap.put(tableName, nameToIndex);
        }
        return nameToIndex;
    }
    
    @Override
    public int getConcurrency() throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                return this.isUpdatable ? 1008 : 1007;
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    private String getQuotedIdChar() throws SQLException {
        if (this.quotedIdChar == null) {
            this.quotedIdChar = this.session.getIdentifierQuoteString();
        }
        return this.quotedIdChar;
    }
    
    @Override
    public void insertRow() throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                if (!this.onInsertRow) {
                    throw SQLError.createSQLException(Messages.getString("UpdatableResultSet.7"), this.getExceptionInterceptor());
                }
                this.inserter.executeUpdate();
                final long autoIncrementId = this.inserter.getLastInsertID();
                final Field[] fields = this.getMetadata().getFields();
                final byte[][] newRow = new byte[fields.length][];
                for (int i = 0; i < fields.length; ++i) {
                    newRow[i] = (byte[])(this.inserter.isNull(i) ? null : this.inserter.getBytesRepresentation(i));
                    if (fields[i].isAutoIncrement() && autoIncrementId > 0L) {
                        newRow[i] = StringUtils.getBytes(String.valueOf(autoIncrementId));
                        this.inserter.setBytesNoEscapeNoQuotes(i + 1, newRow[i]);
                    }
                }
                final Row resultSetRow = new ByteArrayRow(newRow, this.getExceptionInterceptor());
                this.refreshRow(this.inserter, resultSetRow);
                this.rowData.addRow(resultSetRow);
                this.resetInserter();
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean isAfterLast() throws SQLException {
        try {
            return super.isAfterLast();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean isBeforeFirst() throws SQLException {
        try {
            return super.isBeforeFirst();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean isFirst() throws SQLException {
        try {
            return super.isFirst();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean isLast() throws SQLException {
        try {
            return super.isLast();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    boolean isUpdatable() {
        return this.isUpdatable;
    }
    
    @Override
    public boolean last() throws SQLException {
        try {
            if (this.onInsertRow) {
                this.onInsertRow = false;
            }
            if (this.doingUpdates) {
                this.doingUpdates = false;
            }
            return super.last();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void moveToCurrentRow() throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                if (!this.isUpdatable) {
                    throw new NotUpdatable(this.notUpdatableReason);
                }
                if (this.onInsertRow) {
                    this.onInsertRow = false;
                    this.thisRow = this.savedCurrentRow;
                }
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void moveToInsertRow() throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                if (!this.isUpdatable) {
                    throw new NotUpdatable(this.notUpdatableReason);
                }
                if (this.inserter == null) {
                    if (this.insertSQL == null) {
                        this.generateStatements();
                    }
                    this.inserter = (ClientPreparedStatement)this.getConnection().clientPrepareStatement(this.insertSQL);
                    this.inserter.getQueryBindings().setColumnDefinition(this.getMetadata());
                    if (this.populateInserterWithDefaultValues) {
                        this.extractDefaultValues();
                    }
                }
                this.resetInserter();
                final Field[] fields = this.getMetadata().getFields();
                final int numFields = fields.length;
                this.onInsertRow = true;
                this.doingUpdates = false;
                this.savedCurrentRow = this.thisRow;
                byte[][] newRowData = new byte[numFields][];
                (this.thisRow = new ByteArrayRow(newRowData, this.getExceptionInterceptor())).setMetadata(this.getMetadata());
                for (int i = 0; i < numFields; ++i) {
                    if (!this.populateInserterWithDefaultValues) {
                        this.inserter.setBytesNoEscapeNoQuotes(i + 1, StringUtils.getBytes("DEFAULT"));
                        newRowData = null;
                    }
                    else if (this.defaultColumnValue[i] != null) {
                        final Field f = fields[i];
                        switch (f.getMysqlTypeId()) {
                            case 7:
                            case 10:
                            case 11:
                            case 12: {
                                if (this.defaultColumnValue[i].length > 7 && this.defaultColumnValue[i][0] == 67 && this.defaultColumnValue[i][1] == 85 && this.defaultColumnValue[i][2] == 82 && this.defaultColumnValue[i][3] == 82 && this.defaultColumnValue[i][4] == 69 && this.defaultColumnValue[i][5] == 78 && this.defaultColumnValue[i][6] == 84 && this.defaultColumnValue[i][7] == 95) {
                                    this.inserter.setBytesNoEscapeNoQuotes(i + 1, this.defaultColumnValue[i]);
                                    break;
                                }
                                this.inserter.setBytes(i + 1, this.defaultColumnValue[i], false, false);
                                break;
                            }
                            default: {
                                this.inserter.setBytes(i + 1, this.defaultColumnValue[i], false, false);
                                break;
                            }
                        }
                        final byte[] defaultValueCopy = new byte[this.defaultColumnValue[i].length];
                        System.arraycopy(this.defaultColumnValue[i], 0, defaultValueCopy, 0, defaultValueCopy.length);
                        newRowData[i] = defaultValueCopy;
                    }
                    else {
                        this.inserter.setNull(i + 1, MysqlType.NULL);
                        newRowData[i] = null;
                    }
                }
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean next() throws SQLException {
        try {
            if (this.onInsertRow) {
                this.onInsertRow = false;
            }
            if (this.doingUpdates) {
                this.doingUpdates = false;
            }
            return super.next();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean prev() throws SQLException {
        return super.prev();
    }
    
    @Override
    public boolean previous() throws SQLException {
        try {
            if (this.onInsertRow) {
                this.onInsertRow = false;
            }
            if (this.doingUpdates) {
                this.doingUpdates = false;
            }
            return super.previous();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void realClose(final boolean calledExplicitly) throws SQLException {
        try {
            if (this.isClosed) {
                return;
            }
            synchronized (this.checkClosed().getConnectionMutex()) {
                SQLException sqlEx = null;
                if (this.useUsageAdvisor && this.deleter == null && this.inserter == null && this.refresher == null && this.updater == null) {
                    this.eventSink = ProfilerEventHandlerFactory.getInstance(this.session);
                    final String message = Messages.getString("UpdatableResultSet.34");
                    this.eventSink.consumeEvent(new ProfilerEventImpl((byte)0, "", (this.getOwningStatement() == null) ? "N/A" : this.getOwningStatement().getCurrentCatalog(), this.getConnectionId(), (this.getOwningStatement() == null) ? -1 : this.getOwningStatement().getId(), this.resultId, System.currentTimeMillis(), 0L, Constants.MILLIS_I18N, null, this.getPointOfOrigin(), message));
                }
                try {
                    if (this.deleter != null) {
                        this.deleter.close();
                    }
                }
                catch (SQLException ex) {
                    sqlEx = ex;
                }
                try {
                    if (this.inserter != null) {
                        this.inserter.close();
                    }
                }
                catch (SQLException ex) {
                    sqlEx = ex;
                }
                try {
                    if (this.refresher != null) {
                        this.refresher.close();
                    }
                }
                catch (SQLException ex) {
                    sqlEx = ex;
                }
                try {
                    if (this.updater != null) {
                        this.updater.close();
                    }
                }
                catch (SQLException ex) {
                    sqlEx = ex;
                }
                super.realClose(calledExplicitly);
                if (sqlEx != null) {
                    throw sqlEx;
                }
            }
        }
        catch (CJException ex2) {
            throw SQLExceptionsMapping.translateException(ex2, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void refreshRow() throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                if (!this.isUpdatable) {
                    throw new NotUpdatable(Messages.getString("NotUpdatable.0"));
                }
                if (this.onInsertRow) {
                    throw SQLError.createSQLException(Messages.getString("UpdatableResultSet.8"), this.getExceptionInterceptor());
                }
                if (this.rowData.size() == 0) {
                    throw SQLError.createSQLException(Messages.getString("UpdatableResultSet.9"), this.getExceptionInterceptor());
                }
                if (this.isBeforeFirst()) {
                    throw SQLError.createSQLException(Messages.getString("UpdatableResultSet.10"), this.getExceptionInterceptor());
                }
                if (this.isAfterLast()) {
                    throw SQLError.createSQLException(Messages.getString("UpdatableResultSet.11"), this.getExceptionInterceptor());
                }
                this.refreshRow(this.updater, this.thisRow);
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    private void refreshRow(final ClientPreparedStatement updateInsertStmt, final Row rowToRefresh) throws SQLException {
        if (this.refresher == null) {
            if (this.refreshSQL == null) {
                this.generateStatements();
            }
            this.refresher = (ClientPreparedStatement)(((ResultsetRow)this.thisRow).isBinaryEncoded() ? this.getConnection().serverPrepareStatement(this.refreshSQL) : ((ClientPreparedStatement)this.getConnection().clientPrepareStatement(this.refreshSQL)));
            this.refresher.getQueryBindings().setColumnDefinition(this.getMetadata());
        }
        this.refresher.clearParameters();
        for (int numKeys = this.primaryKeyIndicies.size(), i = 0; i < numKeys; ++i) {
            byte[] dataFrom = null;
            final int index = this.primaryKeyIndicies.get(i);
            if (!this.doingUpdates && !this.onInsertRow) {
                this.setParamValue(this.refresher, i + 1, this.thisRow, index, this.getMetadata().getFields()[index]);
            }
            else {
                dataFrom = updateInsertStmt.getBytesRepresentation(index);
                if (updateInsertStmt.isNull(index) || dataFrom.length == 0) {
                    this.setParamValue(this.refresher, i + 1, this.thisRow, index, this.getMetadata().getFields()[index]);
                }
                else {
                    dataFrom = this.stripBinaryPrefix(dataFrom);
                    this.refresher.setBytesNoEscape(i + 1, dataFrom);
                }
            }
        }
        ResultSet rs = null;
        try {
            rs = this.refresher.executeQuery();
            final int numCols = rs.getMetaData().getColumnCount();
            if (!rs.next()) {
                throw SQLError.createSQLException(Messages.getString("UpdatableResultSet.12"), "S1000", this.getExceptionInterceptor());
            }
            for (int j = 0; j < numCols; ++j) {
                final byte[] val = rs.getBytes(j + 1);
                rowToRefresh.setBytes(j, (byte[])((val == null || rs.wasNull()) ? null : val));
            }
        }
        finally {
            if (rs != null) {
                try {
                    rs.close();
                }
                catch (SQLException ex) {}
            }
        }
    }
    
    @Override
    public boolean relative(final int rows) throws SQLException {
        try {
            return super.relative(rows);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    private void resetInserter() throws SQLException {
        this.inserter.clearParameters();
        for (int i = 0; i < this.getMetadata().getFields().length; ++i) {
            this.inserter.setNull(i + 1, 0);
        }
    }
    
    @Override
    public boolean rowDeleted() throws SQLException {
        try {
            throw SQLError.createSQLFeatureNotSupportedException();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean rowInserted() throws SQLException {
        try {
            throw SQLError.createSQLFeatureNotSupportedException();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean rowUpdated() throws SQLException {
        try {
            throw SQLError.createSQLFeatureNotSupportedException();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setResultSetConcurrency(final int concurrencyFlag) {
        super.setResultSetConcurrency(concurrencyFlag);
    }
    
    private byte[] stripBinaryPrefix(final byte[] dataFrom) {
        return StringUtils.stripEnclosure(dataFrom, "_binary'", "'");
    }
    
    protected void syncUpdate() throws SQLException {
        if (this.updater == null) {
            if (this.updateSQL == null) {
                this.generateStatements();
            }
            this.updater = (ClientPreparedStatement)this.getConnection().clientPrepareStatement(this.updateSQL);
            this.updater.getQueryBindings().setColumnDefinition(this.getMetadata());
        }
        final Field[] fields = this.getMetadata().getFields();
        final int numFields = fields.length;
        this.updater.clearParameters();
        for (int i = 0; i < numFields; ++i) {
            if (this.thisRow.getBytes(i) != null) {
                switch (fields[i].getMysqlType()) {
                    case DATE:
                    case TIMESTAMP:
                    case DATETIME:
                    case TIME: {
                        this.updater.setString(i + 1, this.getString(i + 1));
                        break;
                    }
                    default: {
                        this.updater.setObject(i + 1, this.getObject(i + 1), fields[i].getMysqlType());
                        break;
                    }
                }
            }
            else {
                this.updater.setNull(i + 1, 0);
            }
        }
        for (int numKeys = this.primaryKeyIndicies.size(), j = 0; j < numKeys; ++j) {
            final int idx = this.primaryKeyIndicies.get(j);
            this.setParamValue(this.updater, numFields + j + 1, this.thisRow, idx, fields[idx]);
        }
    }
    
    @Override
    public void updateRow() throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                if (!this.isUpdatable) {
                    throw new NotUpdatable(this.notUpdatableReason);
                }
                if (this.doingUpdates) {
                    this.updater.executeUpdate();
                    this.refreshRow();
                    this.doingUpdates = false;
                }
                else if (this.onInsertRow) {
                    throw SQLError.createSQLException(Messages.getString("UpdatableResultSet.44"), this.getExceptionInterceptor());
                }
                this.syncUpdate();
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public int getHoldability() throws SQLException {
        try {
            throw SQLError.createSQLFeatureNotSupportedException();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateAsciiStream(final String columnLabel, final InputStream x, final int length) throws SQLException {
        try {
            this.updateAsciiStream(this.findColumn(columnLabel), x, length);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateAsciiStream(final int columnIndex, final InputStream x, final int length) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                if (!this.onInsertRow) {
                    if (!this.doingUpdates) {
                        this.doingUpdates = true;
                        this.syncUpdate();
                    }
                    this.updater.setAsciiStream(columnIndex, x, length);
                }
                else {
                    this.inserter.setAsciiStream(columnIndex, x, length);
                    this.thisRow.setBytes(columnIndex - 1, UpdatableResultSet.STREAM_DATA_MARKER);
                }
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateBigDecimal(final String columnLabel, final BigDecimal x) throws SQLException {
        try {
            this.updateBigDecimal(this.findColumn(columnLabel), x);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateBigDecimal(final int columnIndex, final BigDecimal x) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                if (!this.onInsertRow) {
                    if (!this.doingUpdates) {
                        this.doingUpdates = true;
                        this.syncUpdate();
                    }
                    this.updater.setBigDecimal(columnIndex, x);
                }
                else {
                    this.inserter.setBigDecimal(columnIndex, x);
                    this.thisRow.setBytes(columnIndex - 1, (byte[])((x == null) ? null : StringUtils.getBytes(x.toString())));
                }
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateBinaryStream(final String columnLabel, final InputStream x, final int length) throws SQLException {
        try {
            this.updateBinaryStream(this.findColumn(columnLabel), x, length);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateBinaryStream(final int columnIndex, final InputStream x, final int length) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                if (!this.onInsertRow) {
                    if (!this.doingUpdates) {
                        this.doingUpdates = true;
                        this.syncUpdate();
                    }
                    this.updater.setBinaryStream(columnIndex, x, length);
                }
                else {
                    this.inserter.setBinaryStream(columnIndex, x, length);
                    this.thisRow.setBytes(columnIndex - 1, (byte[])((x == null) ? null : UpdatableResultSet.STREAM_DATA_MARKER));
                }
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateBlob(final String columnLabel, final Blob blob) throws SQLException {
        try {
            this.updateBlob(this.findColumn(columnLabel), blob);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateBlob(final int columnIndex, final Blob blob) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                if (!this.onInsertRow) {
                    if (!this.doingUpdates) {
                        this.doingUpdates = true;
                        this.syncUpdate();
                    }
                    this.updater.setBlob(columnIndex, blob);
                }
                else {
                    this.inserter.setBlob(columnIndex, blob);
                    this.thisRow.setBytes(columnIndex - 1, (byte[])((blob == null) ? null : UpdatableResultSet.STREAM_DATA_MARKER));
                }
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateBoolean(final String columnLabel, final boolean x) throws SQLException {
        try {
            this.updateBoolean(this.findColumn(columnLabel), x);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateBoolean(final int columnIndex, final boolean x) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                if (!this.onInsertRow) {
                    if (!this.doingUpdates) {
                        this.doingUpdates = true;
                        this.syncUpdate();
                    }
                    this.updater.setBoolean(columnIndex, x);
                }
                else {
                    this.inserter.setBoolean(columnIndex, x);
                    this.thisRow.setBytes(columnIndex - 1, this.inserter.getBytesRepresentation(columnIndex - 1));
                }
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateByte(final String columnLabel, final byte x) throws SQLException {
        try {
            this.updateByte(this.findColumn(columnLabel), x);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateByte(final int columnIndex, final byte x) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                if (!this.onInsertRow) {
                    if (!this.doingUpdates) {
                        this.doingUpdates = true;
                        this.syncUpdate();
                    }
                    this.updater.setByte(columnIndex, x);
                }
                else {
                    this.inserter.setByte(columnIndex, x);
                    this.thisRow.setBytes(columnIndex - 1, this.inserter.getBytesRepresentation(columnIndex - 1));
                }
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateBytes(final String columnLabel, final byte[] x) throws SQLException {
        try {
            this.updateBytes(this.findColumn(columnLabel), x);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateBytes(final int columnIndex, final byte[] x) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                if (!this.onInsertRow) {
                    if (!this.doingUpdates) {
                        this.doingUpdates = true;
                        this.syncUpdate();
                    }
                    this.updater.setBytes(columnIndex, x);
                }
                else {
                    this.inserter.setBytes(columnIndex, x);
                    this.thisRow.setBytes(columnIndex - 1, x);
                }
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateCharacterStream(final String columnLabel, final Reader reader, final int length) throws SQLException {
        try {
            this.updateCharacterStream(this.findColumn(columnLabel), reader, length);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateCharacterStream(final int columnIndex, final Reader x, final int length) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                if (!this.onInsertRow) {
                    if (!this.doingUpdates) {
                        this.doingUpdates = true;
                        this.syncUpdate();
                    }
                    this.updater.setCharacterStream(columnIndex, x, length);
                }
                else {
                    this.inserter.setCharacterStream(columnIndex, x, length);
                    this.thisRow.setBytes(columnIndex - 1, (byte[])((x == null) ? null : UpdatableResultSet.STREAM_DATA_MARKER));
                }
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateClob(final String columnLabel, final Clob clob) throws SQLException {
        try {
            this.updateClob(this.findColumn(columnLabel), clob);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateClob(final int columnIndex, final Clob clob) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                if (clob == null) {
                    this.updateNull(columnIndex);
                }
                else {
                    this.updateCharacterStream(columnIndex, clob.getCharacterStream(), (int)clob.length());
                }
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateDate(final String columnLabel, final Date x) throws SQLException {
        try {
            this.updateDate(this.findColumn(columnLabel), x);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateDate(final int columnIndex, final Date x) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                if (!this.onInsertRow) {
                    if (!this.doingUpdates) {
                        this.doingUpdates = true;
                        this.syncUpdate();
                    }
                    this.updater.setDate(columnIndex, x);
                }
                else {
                    this.inserter.setDate(columnIndex, x);
                    this.thisRow.setBytes(columnIndex - 1, this.inserter.getBytesRepresentation(columnIndex - 1));
                }
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateDouble(final String columnLabel, final double x) throws SQLException {
        try {
            this.updateDouble(this.findColumn(columnLabel), x);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateDouble(final int columnIndex, final double x) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                if (!this.onInsertRow) {
                    if (!this.doingUpdates) {
                        this.doingUpdates = true;
                        this.syncUpdate();
                    }
                    this.updater.setDouble(columnIndex, x);
                }
                else {
                    this.inserter.setDouble(columnIndex, x);
                    this.thisRow.setBytes(columnIndex - 1, this.inserter.getBytesRepresentation(columnIndex - 1));
                }
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateFloat(final String columnLabel, final float x) throws SQLException {
        try {
            this.updateFloat(this.findColumn(columnLabel), x);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateFloat(final int columnIndex, final float x) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                if (!this.onInsertRow) {
                    if (!this.doingUpdates) {
                        this.doingUpdates = true;
                        this.syncUpdate();
                    }
                    this.updater.setFloat(columnIndex, x);
                }
                else {
                    this.inserter.setFloat(columnIndex, x);
                    this.thisRow.setBytes(columnIndex - 1, this.inserter.getBytesRepresentation(columnIndex - 1));
                }
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateInt(final String columnLabel, final int x) throws SQLException {
        try {
            this.updateInt(this.findColumn(columnLabel), x);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateInt(final int columnIndex, final int x) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                if (!this.onInsertRow) {
                    if (!this.doingUpdates) {
                        this.doingUpdates = true;
                        this.syncUpdate();
                    }
                    this.updater.setInt(columnIndex, x);
                }
                else {
                    this.inserter.setInt(columnIndex, x);
                    this.thisRow.setBytes(columnIndex - 1, this.inserter.getBytesRepresentation(columnIndex - 1));
                }
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateLong(final String columnLabel, final long x) throws SQLException {
        try {
            this.updateLong(this.findColumn(columnLabel), x);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateLong(final int columnIndex, final long x) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                if (!this.onInsertRow) {
                    if (!this.doingUpdates) {
                        this.doingUpdates = true;
                        this.syncUpdate();
                    }
                    this.updater.setLong(columnIndex, x);
                }
                else {
                    this.inserter.setLong(columnIndex, x);
                    this.thisRow.setBytes(columnIndex - 1, this.inserter.getBytesRepresentation(columnIndex - 1));
                }
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateNull(final String columnLabel) throws SQLException {
        try {
            this.updateNull(this.findColumn(columnLabel));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateNull(final int columnIndex) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                if (!this.onInsertRow) {
                    if (!this.doingUpdates) {
                        this.doingUpdates = true;
                        this.syncUpdate();
                    }
                    this.updater.setNull(columnIndex, 0);
                }
                else {
                    this.inserter.setNull(columnIndex, 0);
                    this.thisRow.setBytes(columnIndex - 1, null);
                }
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateObject(final String columnLabel, final Object x) throws SQLException {
        try {
            this.updateObject(this.findColumn(columnLabel), x);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateObject(final int columnIndex, final Object x) throws SQLException {
        try {
            this.updateObjectInternal(columnIndex, x, (Integer)null, 0);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateObject(final String columnLabel, final Object x, final int scale) throws SQLException {
        try {
            this.updateObject(this.findColumn(columnLabel), x, scale);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateObject(final int columnIndex, final Object x, final int scale) throws SQLException {
        try {
            this.updateObjectInternal(columnIndex, x, (Integer)null, scale);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    protected void updateObjectInternal(final int columnIndex, final Object x, final Integer targetType, final int scaleOrLength) throws SQLException {
        try {
            final MysqlType targetMysqlType = (targetType == null) ? null : MysqlType.getByJdbcType(targetType);
            this.updateObjectInternal(columnIndex, x, targetMysqlType, scaleOrLength);
        }
        catch (FeatureNotAvailableException nae) {
            throw SQLError.createSQLFeatureNotSupportedException(Messages.getString("Statement.UnsupportedSQLType") + JDBCType.valueOf(targetType), "S1C00", this.getExceptionInterceptor());
        }
    }
    
    protected void updateObjectInternal(final int columnIndex, final Object x, final SQLType targetType, final int scaleOrLength) throws SQLException {
        synchronized (this.checkClosed().getConnectionMutex()) {
            if (!this.onInsertRow) {
                if (!this.doingUpdates) {
                    this.doingUpdates = true;
                    this.syncUpdate();
                }
                if (targetType == null) {
                    this.updater.setObject(columnIndex, x);
                }
                else {
                    this.updater.setObject(columnIndex, x, targetType);
                }
            }
            else {
                if (targetType == null) {
                    this.inserter.setObject(columnIndex, x);
                }
                else {
                    this.inserter.setObject(columnIndex, x, targetType);
                }
                this.thisRow.setBytes(columnIndex - 1, this.inserter.getBytesRepresentation(columnIndex - 1));
            }
        }
    }
    
    @Override
    public void updateObject(final String columnLabel, final Object x, final SQLType targetSqlType) throws SQLException {
        try {
            this.updateObject(this.findColumn(columnLabel), x, targetSqlType);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateObject(final int columnIndex, final Object x, final SQLType targetSqlType) throws SQLException {
        try {
            this.updateObjectInternal(columnIndex, x, targetSqlType, 0);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateObject(final String columnLabel, final Object x, final SQLType targetSqlType, final int scaleOrLength) throws SQLException {
        try {
            this.updateObject(this.findColumn(columnLabel), x, targetSqlType, scaleOrLength);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateObject(final int columnIndex, final Object x, final SQLType targetSqlType, final int scaleOrLength) throws SQLException {
        try {
            this.updateObjectInternal(columnIndex, x, targetSqlType, scaleOrLength);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateShort(final String columnLabel, final short x) throws SQLException {
        try {
            this.updateShort(this.findColumn(columnLabel), x);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateShort(final int columnIndex, final short x) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                if (!this.onInsertRow) {
                    if (!this.doingUpdates) {
                        this.doingUpdates = true;
                        this.syncUpdate();
                    }
                    this.updater.setShort(columnIndex, x);
                }
                else {
                    this.inserter.setShort(columnIndex, x);
                    this.thisRow.setBytes(columnIndex - 1, this.inserter.getBytesRepresentation(columnIndex - 1));
                }
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateString(final String columnLabel, final String x) throws SQLException {
        try {
            this.updateString(this.findColumn(columnLabel), x);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateString(final int columnIndex, final String x) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                if (!this.onInsertRow) {
                    if (!this.doingUpdates) {
                        this.doingUpdates = true;
                        this.syncUpdate();
                    }
                    this.updater.setString(columnIndex, x);
                }
                else {
                    this.inserter.setString(columnIndex, x);
                    this.thisRow.setBytes(columnIndex - 1, (byte[])((x == null) ? null : StringUtils.getBytes(x, this.charEncoding)));
                }
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateTime(final String columnLabel, final Time x) throws SQLException {
        try {
            this.updateTime(this.findColumn(columnLabel), x);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateTime(final int columnIndex, final Time x) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                if (!this.onInsertRow) {
                    if (!this.doingUpdates) {
                        this.doingUpdates = true;
                        this.syncUpdate();
                    }
                    this.updater.setTime(columnIndex, x);
                }
                else {
                    this.inserter.setTime(columnIndex, x);
                    this.thisRow.setBytes(columnIndex - 1, this.inserter.getBytesRepresentation(columnIndex - 1));
                }
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateTimestamp(final String columnLabel, final Timestamp x) throws SQLException {
        try {
            this.updateTimestamp(this.findColumn(columnLabel), x);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateTimestamp(final int columnIndex, final Timestamp x) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                if (!this.onInsertRow) {
                    if (!this.doingUpdates) {
                        this.doingUpdates = true;
                        this.syncUpdate();
                    }
                    this.updater.setTimestamp(columnIndex, x);
                }
                else {
                    this.inserter.setTimestamp(columnIndex, x);
                    this.thisRow.setBytes(columnIndex - 1, this.inserter.getBytesRepresentation(columnIndex - 1));
                }
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateAsciiStream(final String columnLabel, final InputStream x) throws SQLException {
        try {
            this.updateAsciiStream(this.findColumn(columnLabel), x);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateAsciiStream(final int columnIndex, final InputStream x) throws SQLException {
        try {
            if (!this.onInsertRow) {
                if (!this.doingUpdates) {
                    this.doingUpdates = true;
                    this.syncUpdate();
                }
                this.updater.setAsciiStream(columnIndex, x);
            }
            else {
                this.inserter.setAsciiStream(columnIndex, x);
                this.thisRow.setBytes(columnIndex - 1, UpdatableResultSet.STREAM_DATA_MARKER);
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateAsciiStream(final String columnLabel, final InputStream x, final long length) throws SQLException {
        try {
            this.updateAsciiStream(this.findColumn(columnLabel), x, length);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateAsciiStream(final int columnIndex, final InputStream x, final long length) throws SQLException {
        try {
            if (!this.onInsertRow) {
                if (!this.doingUpdates) {
                    this.doingUpdates = true;
                    this.syncUpdate();
                }
                this.updater.setAsciiStream(columnIndex, x, length);
            }
            else {
                this.inserter.setAsciiStream(columnIndex, x, length);
                this.thisRow.setBytes(columnIndex - 1, UpdatableResultSet.STREAM_DATA_MARKER);
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateBinaryStream(final String columnLabel, final InputStream x) throws SQLException {
        try {
            this.updateBinaryStream(this.findColumn(columnLabel), x);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateBinaryStream(final int columnIndex, final InputStream x) throws SQLException {
        try {
            if (!this.onInsertRow) {
                if (!this.doingUpdates) {
                    this.doingUpdates = true;
                    this.syncUpdate();
                }
                this.updater.setBinaryStream(columnIndex, x);
            }
            else {
                this.inserter.setBinaryStream(columnIndex, x);
                this.thisRow.setBytes(columnIndex - 1, (byte[])((x == null) ? null : UpdatableResultSet.STREAM_DATA_MARKER));
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateBinaryStream(final String columnLabel, final InputStream x, final long length) throws SQLException {
        try {
            this.updateBinaryStream(this.findColumn(columnLabel), x, length);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateBinaryStream(final int columnIndex, final InputStream x, final long length) throws SQLException {
        try {
            if (!this.onInsertRow) {
                if (!this.doingUpdates) {
                    this.doingUpdates = true;
                    this.syncUpdate();
                }
                this.updater.setBinaryStream(columnIndex, x, length);
            }
            else {
                this.inserter.setBinaryStream(columnIndex, x, length);
                this.thisRow.setBytes(columnIndex - 1, (byte[])((x == null) ? null : UpdatableResultSet.STREAM_DATA_MARKER));
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateBlob(final String columnLabel, final InputStream inputStream) throws SQLException {
        try {
            this.updateBlob(this.findColumn(columnLabel), inputStream);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateBlob(final int columnIndex, final InputStream inputStream) throws SQLException {
        try {
            if (!this.onInsertRow) {
                if (!this.doingUpdates) {
                    this.doingUpdates = true;
                    this.syncUpdate();
                }
                this.updater.setBlob(columnIndex, inputStream);
            }
            else {
                this.inserter.setBlob(columnIndex, inputStream);
                this.thisRow.setBytes(columnIndex - 1, (byte[])((inputStream == null) ? null : UpdatableResultSet.STREAM_DATA_MARKER));
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateBlob(final String columnLabel, final InputStream inputStream, final long length) throws SQLException {
        try {
            this.updateBlob(this.findColumn(columnLabel), inputStream, length);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateBlob(final int columnIndex, final InputStream inputStream, final long length) throws SQLException {
        try {
            if (!this.onInsertRow) {
                if (!this.doingUpdates) {
                    this.doingUpdates = true;
                    this.syncUpdate();
                }
                this.updater.setBlob(columnIndex, inputStream, length);
            }
            else {
                this.inserter.setBlob(columnIndex, inputStream, length);
                this.thisRow.setBytes(columnIndex - 1, (byte[])((inputStream == null) ? null : UpdatableResultSet.STREAM_DATA_MARKER));
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateCharacterStream(final String columnLabel, final Reader reader) throws SQLException {
        try {
            this.updateCharacterStream(this.findColumn(columnLabel), reader);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateCharacterStream(final int columnIndex, final Reader x) throws SQLException {
        try {
            if (!this.onInsertRow) {
                if (!this.doingUpdates) {
                    this.doingUpdates = true;
                    this.syncUpdate();
                }
                this.updater.setCharacterStream(columnIndex, x);
            }
            else {
                this.inserter.setCharacterStream(columnIndex, x);
                this.thisRow.setBytes(columnIndex - 1, (byte[])((x == null) ? null : UpdatableResultSet.STREAM_DATA_MARKER));
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateCharacterStream(final String columnLabel, final Reader reader, final long length) throws SQLException {
        try {
            this.updateCharacterStream(this.findColumn(columnLabel), reader, length);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateCharacterStream(final int columnIndex, final Reader x, final long length) throws SQLException {
        try {
            if (!this.onInsertRow) {
                if (!this.doingUpdates) {
                    this.doingUpdates = true;
                    this.syncUpdate();
                }
                this.updater.setCharacterStream(columnIndex, x, length);
            }
            else {
                this.inserter.setCharacterStream(columnIndex, x, length);
                this.thisRow.setBytes(columnIndex - 1, (byte[])((x == null) ? null : UpdatableResultSet.STREAM_DATA_MARKER));
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateClob(final String columnLabel, final Reader reader) throws SQLException {
        try {
            this.updateClob(this.findColumn(columnLabel), reader);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateClob(final int columnIndex, final Reader reader) throws SQLException {
        try {
            this.updateCharacterStream(columnIndex, reader);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateClob(final String columnLabel, final Reader reader, final long length) throws SQLException {
        try {
            this.updateClob(this.findColumn(columnLabel), reader, length);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateClob(final int columnIndex, final Reader reader, final long length) throws SQLException {
        try {
            this.updateCharacterStream(columnIndex, reader, length);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateNCharacterStream(final String columnLabel, final Reader reader) throws SQLException {
        try {
            this.updateNCharacterStream(this.findColumn(columnLabel), reader);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateNCharacterStream(final int columnIndex, final Reader x) throws SQLException {
        try {
            final String fieldEncoding = this.getMetadata().getFields()[columnIndex - 1].getEncoding();
            if (fieldEncoding == null || !fieldEncoding.equals("UTF-8")) {
                throw new SQLException(Messages.getString("ResultSet.16"));
            }
            if (!this.onInsertRow) {
                if (!this.doingUpdates) {
                    this.doingUpdates = true;
                    this.syncUpdate();
                }
                this.updater.setNCharacterStream(columnIndex, x);
            }
            else {
                this.inserter.setNCharacterStream(columnIndex, x);
                this.thisRow.setBytes(columnIndex - 1, (byte[])((x == null) ? null : UpdatableResultSet.STREAM_DATA_MARKER));
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateNCharacterStream(final String columnLabel, final Reader reader, final long length) throws SQLException {
        try {
            this.updateNCharacterStream(this.findColumn(columnLabel), reader, length);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateNCharacterStream(final int columnIndex, final Reader x, final long length) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                final String fieldEncoding = this.getMetadata().getFields()[columnIndex - 1].getEncoding();
                if (fieldEncoding == null || !fieldEncoding.equals("UTF-8")) {
                    throw new SQLException(Messages.getString("ResultSet.16"));
                }
                if (!this.onInsertRow) {
                    if (!this.doingUpdates) {
                        this.doingUpdates = true;
                        this.syncUpdate();
                    }
                    this.updater.setNCharacterStream(columnIndex, x, length);
                }
                else {
                    this.inserter.setNCharacterStream(columnIndex, x, length);
                    this.thisRow.setBytes(columnIndex - 1, (byte[])((x == null) ? null : UpdatableResultSet.STREAM_DATA_MARKER));
                }
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateNClob(final String columnLabel, final Reader reader) throws SQLException {
        try {
            this.updateNClob(this.findColumn(columnLabel), reader);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateNClob(final int columnIndex, final Reader reader) throws SQLException {
        try {
            final String fieldEncoding = this.getMetadata().getFields()[columnIndex - 1].getEncoding();
            if (fieldEncoding == null || !fieldEncoding.equals("UTF-8")) {
                throw new SQLException(Messages.getString("ResultSet.17"));
            }
            this.updateCharacterStream(columnIndex, reader);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateNClob(final String columnLabel, final Reader reader, final long length) throws SQLException {
        try {
            this.updateNClob(this.findColumn(columnLabel), reader, length);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateNClob(final int columnIndex, final Reader reader, final long length) throws SQLException {
        try {
            final String fieldEncoding = this.getMetadata().getFields()[columnIndex - 1].getEncoding();
            if (fieldEncoding == null || !fieldEncoding.equals("UTF-8")) {
                throw new SQLException(Messages.getString("ResultSet.17"));
            }
            this.updateCharacterStream(columnIndex, reader, length);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateNClob(final String columnLabel, final NClob nClob) throws SQLException {
        try {
            this.updateNClob(this.findColumn(columnLabel), nClob);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateNClob(final int columnIndex, final NClob nClob) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                final String fieldEncoding = this.getMetadata().getFields()[columnIndex - 1].getEncoding();
                if (fieldEncoding == null || !fieldEncoding.equals("UTF-8")) {
                    throw new SQLException(Messages.getString("ResultSet.17"));
                }
                if (nClob == null) {
                    this.updateNull(columnIndex);
                }
                else {
                    this.updateNCharacterStream(columnIndex, nClob.getCharacterStream(), (int)nClob.length());
                }
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateSQLXML(final String columnLabel, final SQLXML xmlObject) throws SQLException {
        try {
            this.updateSQLXML(this.findColumn(columnLabel), xmlObject);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateSQLXML(final int columnIndex, final SQLXML xmlObject) throws SQLException {
        try {
            this.updateString(columnIndex, ((MysqlSQLXML)xmlObject).getString());
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateNString(final String columnLabel, final String x) throws SQLException {
        try {
            this.updateNString(this.findColumn(columnLabel), x);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void updateNString(final int columnIndex, final String x) throws SQLException {
        try {
            synchronized (this.checkClosed().getConnectionMutex()) {
                final String fieldEncoding = this.getMetadata().getFields()[columnIndex - 1].getEncoding();
                if (fieldEncoding == null || !fieldEncoding.equals("UTF-8")) {
                    throw new SQLException(Messages.getString("ResultSet.18"));
                }
                if (!this.onInsertRow) {
                    if (!this.doingUpdates) {
                        this.doingUpdates = true;
                        this.syncUpdate();
                    }
                    this.updater.setNString(columnIndex, x);
                }
                else {
                    this.inserter.setNString(columnIndex, x);
                    this.thisRow.setBytes(columnIndex - 1, (byte[])((x == null) ? null : StringUtils.getBytes(x, fieldEncoding)));
                }
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public Reader getNCharacterStream(final String columnLabel) throws SQLException {
        try {
            return this.getNCharacterStream(this.findColumn(columnLabel));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public Reader getNCharacterStream(final int columnIndex) throws SQLException {
        try {
            final String fieldEncoding = this.getMetadata().getFields()[columnIndex - 1].getEncoding();
            if (fieldEncoding == null || !fieldEncoding.equals("UTF-8")) {
                throw new SQLException(Messages.getString("ResultSet.11"));
            }
            return this.getCharacterStream(columnIndex);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public NClob getNClob(final String columnLabel) throws SQLException {
        try {
            return this.getNClob(this.findColumn(columnLabel));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public NClob getNClob(final int columnIndex) throws SQLException {
        try {
            final String fieldEncoding = this.getMetadata().getFields()[columnIndex - 1].getEncoding();
            if (fieldEncoding == null || !fieldEncoding.equals("UTF-8")) {
                throw new SQLException("Can not call getNClob() when field's charset isn't UTF-8");
            }
            final String asString = this.getStringForNClob(columnIndex);
            if (asString == null) {
                return null;
            }
            return new com.mysql.cj.jdbc.NClob(asString, this.getExceptionInterceptor());
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public String getNString(final String columnLabel) throws SQLException {
        try {
            return this.getNString(this.findColumn(columnLabel));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public String getNString(final int columnIndex) throws SQLException {
        try {
            final String fieldEncoding = this.getMetadata().getFields()[columnIndex - 1].getEncoding();
            if (fieldEncoding == null || !fieldEncoding.equals("UTF-8")) {
                throw new SQLException("Can not call getNString() when field's charset isn't UTF-8");
            }
            return this.getString(columnIndex);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public SQLXML getSQLXML(final String columnLabel) throws SQLException {
        try {
            return this.getSQLXML(this.findColumn(columnLabel));
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public SQLXML getSQLXML(final int columnIndex) throws SQLException {
        try {
            return new MysqlSQLXML(this, columnIndex, this.getExceptionInterceptor());
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    private String getStringForNClob(final int columnIndex) throws SQLException {
        String asString = null;
        final String forcedEncoding = "UTF-8";
        try {
            byte[] asBytes = null;
            asBytes = this.getBytes(columnIndex);
            if (asBytes != null) {
                asString = new String(asBytes, forcedEncoding);
            }
        }
        catch (UnsupportedEncodingException uee) {
            throw SQLError.createSQLException("Unsupported character encoding " + forcedEncoding, "S1009", this.getExceptionInterceptor());
        }
        return asString;
    }
    
    @Override
    public boolean isClosed() throws SQLException {
        try {
            return this.isClosed;
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
                throw SQLError.createSQLException("Unable to unwrap to " + iface.toString(), "S1009", this.getExceptionInterceptor());
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    static {
        STREAM_DATA_MARKER = StringUtils.getBytes("** STREAM DATA **");
    }
}
