
package com.mysql.cj.jdbc;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.io.BufferedInputStream;
import com.mysql.cj.conf.PropertyKey;
import java.io.InputStream;
import com.mysql.cj.exceptions.CJException;
import com.mysql.cj.jdbc.exceptions.SQLExceptionsMapping;
import java.io.OutputStream;
import com.mysql.cj.jdbc.exceptions.SQLError;
import com.mysql.cj.Messages;
import java.sql.SQLException;
import com.mysql.cj.result.Field;
import java.util.ArrayList;
import com.mysql.cj.exceptions.ExceptionInterceptor;
import com.mysql.cj.jdbc.result.ResultSetImpl;
import java.util.List;
import java.sql.Blob;

public class BlobFromLocator implements Blob
{
    private List<String> primaryKeyColumns;
    private List<String> primaryKeyValues;
    private ResultSetImpl creatorResultSet;
    private String blobColumnName;
    private String tableName;
    private int numColsInResultSet;
    private int numPrimaryKeys;
    private String quotedId;
    private ExceptionInterceptor exceptionInterceptor;
    
    public BlobFromLocator(final ResultSetImpl creatorResultSetToSet, final int blobColumnIndex, final ExceptionInterceptor exceptionInterceptor) throws SQLException {
        this.primaryKeyColumns = null;
        this.primaryKeyValues = null;
        this.blobColumnName = null;
        this.tableName = null;
        this.numColsInResultSet = 0;
        this.numPrimaryKeys = 0;
        this.exceptionInterceptor = exceptionInterceptor;
        this.creatorResultSet = creatorResultSetToSet;
        final Field[] fields = this.creatorResultSet.getMetadata().getFields();
        this.numColsInResultSet = fields.length;
        this.quotedId = this.creatorResultSet.getSession().getIdentifierQuoteString();
        if (this.numColsInResultSet > 1) {
            this.primaryKeyColumns = new ArrayList<String>();
            this.primaryKeyValues = new ArrayList<String>();
            for (int i = 0; i < this.numColsInResultSet; ++i) {
                if (fields[i].isPrimaryKey()) {
                    final StringBuilder keyName = new StringBuilder();
                    keyName.append(this.quotedId);
                    final String originalColumnName = fields[i].getOriginalName();
                    if (originalColumnName != null && originalColumnName.length() > 0) {
                        keyName.append(originalColumnName);
                    }
                    else {
                        keyName.append(fields[i].getName());
                    }
                    keyName.append(this.quotedId);
                    this.primaryKeyColumns.add(keyName.toString());
                    this.primaryKeyValues.add(this.creatorResultSet.getString(i + 1));
                }
            }
        }
        else {
            this.notEnoughInformationInQuery();
        }
        this.numPrimaryKeys = this.primaryKeyColumns.size();
        if (this.numPrimaryKeys == 0) {
            this.notEnoughInformationInQuery();
        }
        if (fields[0].getOriginalTableName() != null) {
            final StringBuilder tableNameBuffer = new StringBuilder();
            final String databaseName = fields[0].getDatabaseName();
            if (databaseName != null && databaseName.length() > 0) {
                tableNameBuffer.append(this.quotedId);
                tableNameBuffer.append(databaseName);
                tableNameBuffer.append(this.quotedId);
                tableNameBuffer.append('.');
            }
            tableNameBuffer.append(this.quotedId);
            tableNameBuffer.append(fields[0].getOriginalTableName());
            tableNameBuffer.append(this.quotedId);
            this.tableName = tableNameBuffer.toString();
        }
        else {
            final StringBuilder tableNameBuffer = new StringBuilder();
            tableNameBuffer.append(this.quotedId);
            tableNameBuffer.append(fields[0].getTableName());
            tableNameBuffer.append(this.quotedId);
            this.tableName = tableNameBuffer.toString();
        }
        this.blobColumnName = this.quotedId + this.creatorResultSet.getString(blobColumnIndex) + this.quotedId;
    }
    
    private void notEnoughInformationInQuery() throws SQLException {
        throw SQLError.createSQLException(Messages.getString("Blob.8"), "S1000", this.exceptionInterceptor);
    }
    
    @Override
    public OutputStream setBinaryStream(final long indexToWriteAt) throws SQLException {
        try {
            throw SQLError.createSQLFeatureNotSupportedException();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.exceptionInterceptor);
        }
    }
    
    @Override
    public InputStream getBinaryStream() throws SQLException {
        try {
            return new BufferedInputStream(new LocatorInputStream(), this.creatorResultSet.getSession().getPropertySet().getMemorySizeProperty(PropertyKey.locatorFetchBufferSize).getValue());
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.exceptionInterceptor);
        }
    }
    
    @Override
    public int setBytes(final long writeAt, final byte[] bytes, final int offset, int length) throws SQLException {
        try {
            PreparedStatement pStmt = null;
            if (offset + length > bytes.length) {
                length = bytes.length - offset;
            }
            final byte[] bytesToWrite = new byte[length];
            System.arraycopy(bytes, offset, bytesToWrite, 0, length);
            final StringBuilder query = new StringBuilder("UPDATE ");
            query.append(this.tableName);
            query.append(" SET ");
            query.append(this.blobColumnName);
            query.append(" = INSERT(");
            query.append(this.blobColumnName);
            query.append(", ");
            query.append(writeAt);
            query.append(", ");
            query.append(length);
            query.append(", ?) WHERE ");
            query.append(this.primaryKeyColumns.get(0));
            query.append(" = ?");
            for (int i = 1; i < this.numPrimaryKeys; ++i) {
                query.append(" AND ");
                query.append(this.primaryKeyColumns.get(i));
                query.append(" = ?");
            }
            try {
                pStmt = this.creatorResultSet.getConnection().prepareStatement(query.toString());
                pStmt.setBytes(1, bytesToWrite);
                for (int i = 0; i < this.numPrimaryKeys; ++i) {
                    pStmt.setString(i + 2, this.primaryKeyValues.get(i));
                }
                final int rowsUpdated = pStmt.executeUpdate();
                if (rowsUpdated != 1) {
                    throw SQLError.createSQLException(Messages.getString("Blob.9"), "S1000", this.exceptionInterceptor);
                }
            }
            finally {
                if (pStmt != null) {
                    try {
                        pStmt.close();
                    }
                    catch (SQLException ex2) {}
                    pStmt = null;
                }
            }
            return (int)this.length();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.exceptionInterceptor);
        }
    }
    
    @Override
    public int setBytes(final long writeAt, final byte[] bytes) throws SQLException {
        try {
            return this.setBytes(writeAt, bytes, 0, bytes.length);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.exceptionInterceptor);
        }
    }
    
    @Override
    public byte[] getBytes(final long pos, final int length) throws SQLException {
        try {
            PreparedStatement pStmt = null;
            try {
                pStmt = this.createGetBytesStatement();
                return this.getBytesInternal(pStmt, pos, length);
            }
            finally {
                if (pStmt != null) {
                    try {
                        pStmt.close();
                    }
                    catch (SQLException ex2) {}
                    pStmt = null;
                }
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.exceptionInterceptor);
        }
    }
    
    @Override
    public long length() throws SQLException {
        try {
            ResultSet blobRs = null;
            PreparedStatement pStmt = null;
            final StringBuilder query = new StringBuilder("SELECT LENGTH(");
            query.append(this.blobColumnName);
            query.append(") FROM ");
            query.append(this.tableName);
            query.append(" WHERE ");
            query.append(this.primaryKeyColumns.get(0));
            query.append(" = ?");
            for (int i = 1; i < this.numPrimaryKeys; ++i) {
                query.append(" AND ");
                query.append(this.primaryKeyColumns.get(i));
                query.append(" = ?");
            }
            try {
                pStmt = this.creatorResultSet.getConnection().prepareStatement(query.toString());
                for (int i = 0; i < this.numPrimaryKeys; ++i) {
                    pStmt.setString(i + 1, this.primaryKeyValues.get(i));
                }
                blobRs = pStmt.executeQuery();
                if (blobRs.next()) {
                    return blobRs.getLong(1);
                }
                throw SQLError.createSQLException(Messages.getString("Blob.9"), "S1000", this.exceptionInterceptor);
            }
            finally {
                if (blobRs != null) {
                    try {
                        blobRs.close();
                    }
                    catch (SQLException ex2) {}
                    blobRs = null;
                }
                if (pStmt != null) {
                    try {
                        pStmt.close();
                    }
                    catch (SQLException ex3) {}
                    pStmt = null;
                }
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.exceptionInterceptor);
        }
    }
    
    @Override
    public long position(final Blob pattern, final long start) throws SQLException {
        try {
            return this.position(pattern.getBytes(0L, (int)pattern.length()), start);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.exceptionInterceptor);
        }
    }
    
    @Override
    public long position(final byte[] pattern, final long start) throws SQLException {
        try {
            ResultSet blobRs = null;
            PreparedStatement pStmt = null;
            final StringBuilder query = new StringBuilder("SELECT LOCATE(");
            query.append("?, ");
            query.append(this.blobColumnName);
            query.append(", ");
            query.append(start);
            query.append(") FROM ");
            query.append(this.tableName);
            query.append(" WHERE ");
            query.append(this.primaryKeyColumns.get(0));
            query.append(" = ?");
            for (int i = 1; i < this.numPrimaryKeys; ++i) {
                query.append(" AND ");
                query.append(this.primaryKeyColumns.get(i));
                query.append(" = ?");
            }
            try {
                pStmt = this.creatorResultSet.getConnection().prepareStatement(query.toString());
                pStmt.setBytes(1, pattern);
                for (int i = 0; i < this.numPrimaryKeys; ++i) {
                    pStmt.setString(i + 2, this.primaryKeyValues.get(i));
                }
                blobRs = pStmt.executeQuery();
                if (blobRs.next()) {
                    return blobRs.getLong(1);
                }
                throw SQLError.createSQLException(Messages.getString("Blob.9"), "S1000", this.exceptionInterceptor);
            }
            finally {
                if (blobRs != null) {
                    try {
                        blobRs.close();
                    }
                    catch (SQLException ex2) {}
                    blobRs = null;
                }
                if (pStmt != null) {
                    try {
                        pStmt.close();
                    }
                    catch (SQLException ex3) {}
                    pStmt = null;
                }
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.exceptionInterceptor);
        }
    }
    
    @Override
    public void truncate(final long length) throws SQLException {
        try {
            PreparedStatement pStmt = null;
            final StringBuilder query = new StringBuilder("UPDATE ");
            query.append(this.tableName);
            query.append(" SET ");
            query.append(this.blobColumnName);
            query.append(" = LEFT(");
            query.append(this.blobColumnName);
            query.append(", ");
            query.append(length);
            query.append(") WHERE ");
            query.append(this.primaryKeyColumns.get(0));
            query.append(" = ?");
            for (int i = 1; i < this.numPrimaryKeys; ++i) {
                query.append(" AND ");
                query.append(this.primaryKeyColumns.get(i));
                query.append(" = ?");
            }
            try {
                pStmt = this.creatorResultSet.getConnection().prepareStatement(query.toString());
                for (int i = 0; i < this.numPrimaryKeys; ++i) {
                    pStmt.setString(i + 1, this.primaryKeyValues.get(i));
                }
                final int rowsUpdated = pStmt.executeUpdate();
                if (rowsUpdated != 1) {
                    throw SQLError.createSQLException(Messages.getString("Blob.9"), "S1000", this.exceptionInterceptor);
                }
            }
            finally {
                if (pStmt != null) {
                    try {
                        pStmt.close();
                    }
                    catch (SQLException ex2) {}
                    pStmt = null;
                }
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.exceptionInterceptor);
        }
    }
    
    PreparedStatement createGetBytesStatement() throws SQLException {
        final StringBuilder query = new StringBuilder("SELECT SUBSTRING(");
        query.append(this.blobColumnName);
        query.append(", ");
        query.append("?");
        query.append(", ");
        query.append("?");
        query.append(") FROM ");
        query.append(this.tableName);
        query.append(" WHERE ");
        query.append(this.primaryKeyColumns.get(0));
        query.append(" = ?");
        for (int i = 1; i < this.numPrimaryKeys; ++i) {
            query.append(" AND ");
            query.append(this.primaryKeyColumns.get(i));
            query.append(" = ?");
        }
        return this.creatorResultSet.getConnection().prepareStatement(query.toString());
    }
    
    byte[] getBytesInternal(final PreparedStatement pStmt, final long pos, final int length) throws SQLException {
        ResultSet blobRs = null;
        try {
            pStmt.setLong(1, pos);
            pStmt.setInt(2, length);
            for (int i = 0; i < this.numPrimaryKeys; ++i) {
                pStmt.setString(i + 3, this.primaryKeyValues.get(i));
            }
            blobRs = pStmt.executeQuery();
            if (blobRs.next()) {
                return ((ResultSetImpl)blobRs).getBytes(1);
            }
            throw SQLError.createSQLException(Messages.getString("Blob.9"), "S1000", this.exceptionInterceptor);
        }
        finally {
            if (blobRs != null) {
                try {
                    blobRs.close();
                }
                catch (SQLException ex) {}
                blobRs = null;
            }
        }
    }
    
    @Override
    public void free() throws SQLException {
        try {
            this.creatorResultSet = null;
            this.primaryKeyColumns = null;
            this.primaryKeyValues = null;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.exceptionInterceptor);
        }
    }
    
    @Override
    public InputStream getBinaryStream(final long pos, final long length) throws SQLException {
        try {
            return new LocatorInputStream(pos, length);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.exceptionInterceptor);
        }
    }
    
    class LocatorInputStream extends InputStream
    {
        long currentPositionInBlob;
        long length;
        PreparedStatement pStmt;
        
        LocatorInputStream() throws SQLException {
            this.currentPositionInBlob = 0L;
            this.length = 0L;
            this.pStmt = null;
            this.length = BlobFromLocator.this.length();
            this.pStmt = BlobFromLocator.this.createGetBytesStatement();
        }
        
        LocatorInputStream(final long pos, final long len) throws SQLException {
            this.currentPositionInBlob = 0L;
            this.length = 0L;
            this.pStmt = null;
            this.length = pos + len;
            this.currentPositionInBlob = pos;
            final long blobLength = BlobFromLocator.this.length();
            if (pos + len > blobLength) {
                throw SQLError.createSQLException(Messages.getString("Blob.invalidStreamLength", new Object[] { blobLength, pos, len }), "S1009", BlobFromLocator.this.exceptionInterceptor);
            }
            if (pos < 1L) {
                throw SQLError.createSQLException(Messages.getString("Blob.invalidStreamPos"), "S1009", BlobFromLocator.this.exceptionInterceptor);
            }
            if (pos > blobLength) {
                throw SQLError.createSQLException(Messages.getString("Blob.invalidStreamPos"), "S1009", BlobFromLocator.this.exceptionInterceptor);
            }
        }
        
        @Override
        public int read() throws IOException {
            if (this.currentPositionInBlob + 1L > this.length) {
                return -1;
            }
            try {
                final byte[] asBytes = BlobFromLocator.this.getBytesInternal(this.pStmt, this.currentPositionInBlob++ + 1L, 1);
                if (asBytes == null) {
                    return -1;
                }
                return asBytes[0];
            }
            catch (SQLException sqlEx) {
                throw new IOException(sqlEx.toString());
            }
        }
        
        @Override
        public int read(final byte[] b, final int off, final int len) throws IOException {
            if (this.currentPositionInBlob + 1L > this.length) {
                return -1;
            }
            try {
                final byte[] asBytes = BlobFromLocator.this.getBytesInternal(this.pStmt, this.currentPositionInBlob + 1L, len);
                if (asBytes == null) {
                    return -1;
                }
                System.arraycopy(asBytes, 0, b, off, asBytes.length);
                this.currentPositionInBlob += asBytes.length;
                return asBytes.length;
            }
            catch (SQLException sqlEx) {
                throw new IOException(sqlEx.toString());
            }
        }
        
        @Override
        public int read(final byte[] b) throws IOException {
            if (this.currentPositionInBlob + 1L > this.length) {
                return -1;
            }
            try {
                final byte[] asBytes = BlobFromLocator.this.getBytesInternal(this.pStmt, this.currentPositionInBlob + 1L, b.length);
                if (asBytes == null) {
                    return -1;
                }
                System.arraycopy(asBytes, 0, b, 0, asBytes.length);
                this.currentPositionInBlob += asBytes.length;
                return asBytes.length;
            }
            catch (SQLException sqlEx) {
                throw new IOException(sqlEx.toString());
            }
        }
        
        @Override
        public void close() throws IOException {
            if (this.pStmt != null) {
                try {
                    this.pStmt.close();
                }
                catch (SQLException sqlEx) {
                    throw new IOException(sqlEx.toString());
                }
            }
            super.close();
        }
    }
}
