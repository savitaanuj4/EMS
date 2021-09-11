
package com.mysql.cj.jdbc;

import com.mysql.cj.MysqlType;
import com.mysql.cj.jdbc.exceptions.SQLError;
import com.mysql.cj.Messages;
import java.sql.SQLException;
import com.mysql.cj.exceptions.CJException;
import com.mysql.cj.jdbc.exceptions.SQLExceptionsMapping;
import com.mysql.cj.result.Field;
import com.mysql.cj.Session;
import com.mysql.cj.exceptions.ExceptionInterceptor;
import com.mysql.cj.jdbc.result.ResultSetMetaData;
import java.sql.ParameterMetaData;

public class MysqlParameterMetadata implements ParameterMetaData
{
    boolean returnSimpleMetadata;
    ResultSetMetaData metadata;
    int parameterCount;
    private ExceptionInterceptor exceptionInterceptor;
    
    public MysqlParameterMetadata(final Session session, final Field[] fieldInfo, final int parameterCount, final ExceptionInterceptor exceptionInterceptor) {
        this.returnSimpleMetadata = false;
        this.metadata = null;
        this.parameterCount = 0;
        this.metadata = new ResultSetMetaData(session, fieldInfo, false, true, exceptionInterceptor);
        this.parameterCount = parameterCount;
        this.exceptionInterceptor = exceptionInterceptor;
    }
    
    MysqlParameterMetadata(final int count) {
        this.returnSimpleMetadata = false;
        this.metadata = null;
        this.parameterCount = 0;
        this.parameterCount = count;
        this.returnSimpleMetadata = true;
    }
    
    @Override
    public int getParameterCount() throws SQLException {
        try {
            return this.parameterCount;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.exceptionInterceptor);
        }
    }
    
    @Override
    public int isNullable(final int arg0) throws SQLException {
        try {
            this.checkAvailable();
            return this.metadata.isNullable(arg0);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.exceptionInterceptor);
        }
    }
    
    private void checkAvailable() throws SQLException {
        if (this.metadata == null || this.metadata.getFields() == null) {
            throw SQLError.createSQLException(Messages.getString("MysqlParameterMetadata.0"), "S1C00", this.exceptionInterceptor);
        }
    }
    
    @Override
    public boolean isSigned(final int arg0) throws SQLException {
        try {
            if (this.returnSimpleMetadata) {
                this.checkBounds(arg0);
                return false;
            }
            this.checkAvailable();
            return this.metadata.isSigned(arg0);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.exceptionInterceptor);
        }
    }
    
    @Override
    public int getPrecision(final int arg0) throws SQLException {
        try {
            if (this.returnSimpleMetadata) {
                this.checkBounds(arg0);
                return 0;
            }
            this.checkAvailable();
            return this.metadata.getPrecision(arg0);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.exceptionInterceptor);
        }
    }
    
    @Override
    public int getScale(final int arg0) throws SQLException {
        try {
            if (this.returnSimpleMetadata) {
                this.checkBounds(arg0);
                return 0;
            }
            this.checkAvailable();
            return this.metadata.getScale(arg0);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.exceptionInterceptor);
        }
    }
    
    @Override
    public int getParameterType(final int arg0) throws SQLException {
        try {
            if (this.returnSimpleMetadata) {
                this.checkBounds(arg0);
                return MysqlType.VARCHAR.getJdbcType();
            }
            this.checkAvailable();
            return this.metadata.getColumnType(arg0);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.exceptionInterceptor);
        }
    }
    
    @Override
    public String getParameterTypeName(final int arg0) throws SQLException {
        try {
            if (this.returnSimpleMetadata) {
                this.checkBounds(arg0);
                return MysqlType.VARCHAR.getName();
            }
            this.checkAvailable();
            return this.metadata.getColumnTypeName(arg0);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.exceptionInterceptor);
        }
    }
    
    @Override
    public String getParameterClassName(final int arg0) throws SQLException {
        try {
            if (this.returnSimpleMetadata) {
                this.checkBounds(arg0);
                return "java.lang.String";
            }
            this.checkAvailable();
            return this.metadata.getColumnClassName(arg0);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.exceptionInterceptor);
        }
    }
    
    @Override
    public int getParameterMode(final int arg0) throws SQLException {
        try {
            return 1;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.exceptionInterceptor);
        }
    }
    
    private void checkBounds(final int paramNumber) throws SQLException {
        if (paramNumber < 1) {
            throw SQLError.createSQLException(Messages.getString("MysqlParameterMetadata.1", new Object[] { paramNumber }), "S1009", this.exceptionInterceptor);
        }
        if (paramNumber > this.parameterCount) {
            throw SQLError.createSQLException(Messages.getString("MysqlParameterMetadata.2", new Object[] { paramNumber, this.parameterCount }), "S1009", this.exceptionInterceptor);
        }
    }
    
    @Override
    public boolean isWrapperFor(final Class<?> iface) throws SQLException {
        try {
            return iface.isInstance(this);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.exceptionInterceptor);
        }
    }
    
    @Override
    public <T> T unwrap(final Class<T> iface) throws SQLException {
        try {
            try {
                return iface.cast(this);
            }
            catch (ClassCastException cce) {
                throw SQLError.createSQLException(Messages.getString("Common.UnableToUnwrap", new Object[] { iface.toString() }), "S1009", this.exceptionInterceptor);
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.exceptionInterceptor);
        }
    }
}
