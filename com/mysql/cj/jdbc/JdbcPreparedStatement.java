
package com.mysql.cj.jdbc;

import com.mysql.cj.MysqlType;
import java.math.BigInteger;
import com.mysql.cj.ParseInfo;
import com.mysql.cj.QueryBindings;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public interface JdbcPreparedStatement extends PreparedStatement, JdbcStatement
{
    void realClose(final boolean p0, final boolean p1) throws SQLException;
    
    QueryBindings<?> getQueryBindings();
    
    byte[] getBytesRepresentation(final int p0) throws SQLException;
    
    ParseInfo getParseInfo();
    
    boolean isNull(final int p0) throws SQLException;
    
    String getPreparedSql();
    
    void setBytes(final int p0, final byte[] p1, final boolean p2, final boolean p3) throws SQLException;
    
    void setBytesNoEscape(final int p0, final byte[] p1) throws SQLException;
    
    void setBytesNoEscapeNoQuotes(final int p0, final byte[] p1) throws SQLException;
    
    void setBigInteger(final int p0, final BigInteger p1) throws SQLException;
    
    void setNull(final int p0, final MysqlType p1) throws SQLException;
}
