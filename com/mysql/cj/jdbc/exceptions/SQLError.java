
package com.mysql.cj.jdbc.exceptions;

import java.sql.SQLFeatureNotSupportedException;
import com.mysql.cj.protocol.PacketReceivedTimeHolder;
import com.mysql.cj.protocol.PacketSentTimeHolder;
import com.mysql.cj.jdbc.JdbcConnection;
import com.mysql.cj.util.Util;
import java.sql.SQLSyntaxErrorException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.SQLDataException;
import java.sql.SQLNonTransientConnectionException;
import java.sql.SQLTransientConnectionException;
import java.sql.SQLException;
import com.mysql.cj.exceptions.ExceptionInterceptor;

public class SQLError
{
    public static SQLException createSQLException(final String message, final String sqlState, final ExceptionInterceptor interceptor) {
        return createSQLException(message, sqlState, 0, interceptor);
    }
    
    public static SQLException createSQLException(final String message, final ExceptionInterceptor interceptor) {
        final SQLException sqlEx = new SQLException(message);
        return runThroughExceptionInterceptor(interceptor, sqlEx);
    }
    
    public static SQLException createSQLException(final String message, final String sqlState, final Throwable cause, final ExceptionInterceptor interceptor) {
        final SQLException sqlEx = createSQLException(message, sqlState, null);
        if (sqlEx.getCause() == null && cause != null) {
            try {
                sqlEx.initCause(cause);
            }
            catch (Throwable t) {}
        }
        return runThroughExceptionInterceptor(interceptor, sqlEx);
    }
    
    public static SQLException createSQLException(final String message, final String sqlState, final int vendorErrorCode, final ExceptionInterceptor interceptor) {
        return createSQLException(message, sqlState, vendorErrorCode, false, interceptor);
    }
    
    public static SQLException createSQLException(final String message, final String sqlState, final int vendorErrorCode, final Throwable cause, final ExceptionInterceptor interceptor) {
        return createSQLException(message, sqlState, vendorErrorCode, false, cause, interceptor);
    }
    
    public static SQLException createSQLException(final String message, final String sqlState, final int vendorErrorCode, final boolean isTransient, final ExceptionInterceptor interceptor) {
        return createSQLException(message, sqlState, vendorErrorCode, isTransient, null, interceptor);
    }
    
    public static SQLException createSQLException(final String message, final String sqlState, final int vendorErrorCode, final boolean isTransient, final Throwable cause, final ExceptionInterceptor interceptor) {
        try {
            SQLException sqlEx = null;
            if (sqlState != null) {
                if (sqlState.startsWith("08")) {
                    if (isTransient) {
                        sqlEx = new SQLTransientConnectionException(message, sqlState, vendorErrorCode);
                    }
                    else {
                        sqlEx = new SQLNonTransientConnectionException(message, sqlState, vendorErrorCode);
                    }
                }
                else if (sqlState.startsWith("22")) {
                    sqlEx = new SQLDataException(message, sqlState, vendorErrorCode);
                }
                else if (sqlState.startsWith("23")) {
                    sqlEx = new SQLIntegrityConstraintViolationException(message, sqlState, vendorErrorCode);
                }
                else if (sqlState.startsWith("42")) {
                    sqlEx = new SQLSyntaxErrorException(message, sqlState, vendorErrorCode);
                }
                else if (sqlState.startsWith("40")) {
                    sqlEx = new MySQLTransactionRollbackException(message, sqlState, vendorErrorCode);
                }
                else if (sqlState.startsWith("70100")) {
                    sqlEx = new MySQLQueryInterruptedException(message, sqlState, vendorErrorCode);
                }
                else {
                    sqlEx = new SQLException(message, sqlState, vendorErrorCode);
                }
            }
            else {
                sqlEx = new SQLException(message, sqlState, vendorErrorCode);
            }
            if (cause != null) {
                try {
                    sqlEx.initCause(cause);
                }
                catch (Throwable t) {}
            }
            return runThroughExceptionInterceptor(interceptor, sqlEx);
        }
        catch (Exception sqlEx2) {
            final SQLException unexpectedEx = new SQLException("Unable to create correct SQLException class instance, error class/codes may be incorrect. Reason: " + Util.stackTraceToString(sqlEx2), "S1000");
            return runThroughExceptionInterceptor(interceptor, unexpectedEx);
        }
    }
    
    public static SQLException createCommunicationsException(final JdbcConnection conn, final PacketSentTimeHolder packetSentTimeHolder, final PacketReceivedTimeHolder packetReceivedTimeHolder, final Exception underlyingException, final ExceptionInterceptor interceptor) {
        final SQLException exToReturn = new CommunicationsException(conn, packetSentTimeHolder, packetReceivedTimeHolder, underlyingException);
        if (underlyingException != null) {
            try {
                exToReturn.initCause(underlyingException);
            }
            catch (Throwable t) {}
        }
        return runThroughExceptionInterceptor(interceptor, exToReturn);
    }
    
    public static SQLException createCommunicationsException(final String message, final Throwable underlyingException, final ExceptionInterceptor interceptor) {
        SQLException exToReturn = null;
        exToReturn = new CommunicationsException(message, underlyingException);
        if (underlyingException != null) {
            try {
                exToReturn.initCause(underlyingException);
            }
            catch (Throwable t) {}
        }
        return runThroughExceptionInterceptor(interceptor, exToReturn);
    }
    
    private static SQLException runThroughExceptionInterceptor(final ExceptionInterceptor exInterceptor, final SQLException sqlEx) {
        if (exInterceptor != null) {
            final SQLException interceptedEx = (SQLException)exInterceptor.interceptException(sqlEx);
            if (interceptedEx != null) {
                return interceptedEx;
            }
        }
        return sqlEx;
    }
    
    public static SQLException createBatchUpdateException(final SQLException underlyingEx, final long[] updateCounts, final ExceptionInterceptor interceptor) throws SQLException {
        final SQLException newEx = (SQLException)Util.getInstance("java.sql.BatchUpdateException", new Class[] { String.class, String.class, Integer.TYPE, long[].class, Throwable.class }, new Object[] { underlyingEx.getMessage(), underlyingEx.getSQLState(), underlyingEx.getErrorCode(), updateCounts, underlyingEx }, interceptor);
        return runThroughExceptionInterceptor(interceptor, newEx);
    }
    
    public static SQLException createSQLFeatureNotSupportedException() {
        return new SQLFeatureNotSupportedException();
    }
    
    public static SQLException createSQLFeatureNotSupportedException(final String message, final String sqlState, final ExceptionInterceptor interceptor) throws SQLException {
        final SQLException newEx = new SQLFeatureNotSupportedException(message, sqlState);
        return runThroughExceptionInterceptor(interceptor, newEx);
    }
}
