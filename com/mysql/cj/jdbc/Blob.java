
package com.mysql.cj.jdbc;

import com.mysql.cj.protocol.WatchableStream;
import java.io.IOException;
import com.mysql.cj.protocol.WatchableOutputStream;
import java.io.OutputStream;
import com.mysql.cj.jdbc.exceptions.SQLError;
import com.mysql.cj.Messages;
import java.sql.SQLException;
import com.mysql.cj.exceptions.CJException;
import com.mysql.cj.jdbc.exceptions.SQLExceptionsMapping;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import com.mysql.cj.jdbc.result.ResultSetInternalMethods;
import com.mysql.cj.Constants;
import com.mysql.cj.exceptions.ExceptionInterceptor;
import com.mysql.cj.protocol.OutputStreamWatcher;

public class Blob implements java.sql.Blob, OutputStreamWatcher
{
    private byte[] binaryData;
    private boolean isClosed;
    private ExceptionInterceptor exceptionInterceptor;
    
    Blob(final ExceptionInterceptor exceptionInterceptor) {
        this.binaryData = null;
        this.isClosed = false;
        this.setBinaryData(Constants.EMPTY_BYTE_ARRAY);
        this.exceptionInterceptor = exceptionInterceptor;
    }
    
    public Blob(final byte[] data, final ExceptionInterceptor exceptionInterceptor) {
        this.binaryData = null;
        this.isClosed = false;
        this.setBinaryData(data);
        this.exceptionInterceptor = exceptionInterceptor;
    }
    
    Blob(final byte[] data, final ResultSetInternalMethods creatorResultSetToSet, final int columnIndexToSet) {
        this.binaryData = null;
        this.isClosed = false;
        this.setBinaryData(data);
    }
    
    private synchronized byte[] getBinaryData() {
        return this.binaryData;
    }
    
    @Override
    public synchronized InputStream getBinaryStream() throws SQLException {
        try {
            this.checkClosed();
            return new ByteArrayInputStream(this.getBinaryData());
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.exceptionInterceptor);
        }
    }
    
    @Override
    public synchronized byte[] getBytes(long pos, final int length) throws SQLException {
        try {
            this.checkClosed();
            if (pos < 1L) {
                throw SQLError.createSQLException(Messages.getString("Blob.2"), "S1009", this.exceptionInterceptor);
            }
            --pos;
            if (pos > this.binaryData.length) {
                throw SQLError.createSQLException(Messages.getString("Blob.3"), "S1009", this.exceptionInterceptor);
            }
            if (pos + length > this.binaryData.length) {
                throw SQLError.createSQLException(Messages.getString("Blob.4"), "S1009", this.exceptionInterceptor);
            }
            final byte[] newData = new byte[length];
            System.arraycopy(this.getBinaryData(), (int)pos, newData, 0, length);
            return newData;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.exceptionInterceptor);
        }
    }
    
    @Override
    public synchronized long length() throws SQLException {
        try {
            this.checkClosed();
            return this.getBinaryData().length;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.exceptionInterceptor);
        }
    }
    
    @Override
    public synchronized long position(final byte[] pattern, final long start) throws SQLException {
        try {
            throw SQLError.createSQLFeatureNotSupportedException();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.exceptionInterceptor);
        }
    }
    
    @Override
    public synchronized long position(final java.sql.Blob pattern, final long start) throws SQLException {
        try {
            this.checkClosed();
            return this.position(pattern.getBytes(0L, (int)pattern.length()), start);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.exceptionInterceptor);
        }
    }
    
    private synchronized void setBinaryData(final byte[] newBinaryData) {
        this.binaryData = newBinaryData;
    }
    
    @Override
    public synchronized OutputStream setBinaryStream(final long indexToWriteAt) throws SQLException {
        try {
            this.checkClosed();
            if (indexToWriteAt < 1L) {
                throw SQLError.createSQLException(Messages.getString("Blob.0"), "S1009", this.exceptionInterceptor);
            }
            final WatchableOutputStream bytesOut = new WatchableOutputStream();
            bytesOut.setWatcher(this);
            if (indexToWriteAt > 0L) {
                bytesOut.write(this.binaryData, 0, (int)(indexToWriteAt - 1L));
            }
            return bytesOut;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.exceptionInterceptor);
        }
    }
    
    @Override
    public synchronized int setBytes(final long writeAt, final byte[] bytes) throws SQLException {
        try {
            this.checkClosed();
            return this.setBytes(writeAt, bytes, 0, bytes.length);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.exceptionInterceptor);
        }
    }
    
    @Override
    public synchronized int setBytes(final long writeAt, final byte[] bytes, final int offset, final int length) throws SQLException {
        try {
            this.checkClosed();
            final OutputStream bytesOut = this.setBinaryStream(writeAt);
            try {
                bytesOut.write(bytes, offset, length);
            }
            catch (IOException ioEx) {
                final SQLException sqlEx = SQLError.createSQLException(Messages.getString("Blob.1"), "S1000", this.exceptionInterceptor);
                sqlEx.initCause(ioEx);
                throw sqlEx;
            }
            finally {
                try {
                    bytesOut.close();
                }
                catch (IOException ex2) {}
            }
            return length;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.exceptionInterceptor);
        }
    }
    
    public synchronized void streamClosed(final byte[] byteData) {
        this.binaryData = byteData;
    }
    
    @Override
    public synchronized void streamClosed(final WatchableStream out) {
        final int streamSize = out.size();
        if (streamSize < this.binaryData.length) {
            out.write(this.binaryData, streamSize, this.binaryData.length - streamSize);
        }
        this.binaryData = out.toByteArray();
    }
    
    @Override
    public synchronized void truncate(final long len) throws SQLException {
        try {
            this.checkClosed();
            if (len < 0L) {
                throw SQLError.createSQLException(Messages.getString("Blob.5"), "S1009", this.exceptionInterceptor);
            }
            if (len > this.binaryData.length) {
                throw SQLError.createSQLException(Messages.getString("Blob.6"), "S1009", this.exceptionInterceptor);
            }
            final byte[] newData = new byte[(int)len];
            System.arraycopy(this.getBinaryData(), 0, newData, 0, (int)len);
            this.binaryData = newData;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.exceptionInterceptor);
        }
    }
    
    @Override
    public synchronized void free() throws SQLException {
        try {
            this.binaryData = null;
            this.isClosed = true;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.exceptionInterceptor);
        }
    }
    
    @Override
    public synchronized InputStream getBinaryStream(long pos, final long length) throws SQLException {
        try {
            this.checkClosed();
            if (pos < 1L) {
                throw SQLError.createSQLException(Messages.getString("Blob.2"), "S1009", this.exceptionInterceptor);
            }
            --pos;
            if (pos > this.binaryData.length) {
                throw SQLError.createSQLException(Messages.getString("Blob.6"), "S1009", this.exceptionInterceptor);
            }
            if (pos + length > this.binaryData.length) {
                throw SQLError.createSQLException(Messages.getString("Blob.4"), "S1009", this.exceptionInterceptor);
            }
            return new ByteArrayInputStream(this.getBinaryData(), (int)pos, (int)length);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.exceptionInterceptor);
        }
    }
    
    private synchronized void checkClosed() throws SQLException {
        if (this.isClosed) {
            throw SQLError.createSQLException(Messages.getString("Blob.7"), "S1009", this.exceptionInterceptor);
        }
    }
}
