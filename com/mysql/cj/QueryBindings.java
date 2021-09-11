
package com.mysql.cj;

import java.sql.Timestamp;
import java.sql.Time;
import java.sql.NClob;
import java.util.Calendar;
import java.sql.Date;
import java.sql.Clob;
import java.io.Reader;
import java.sql.Blob;
import java.math.BigInteger;
import java.math.BigDecimal;
import java.io.InputStream;
import com.mysql.cj.protocol.ColumnDefinition;

public interface QueryBindings<T extends BindValue>
{
    QueryBindings<T> clone();
    
    void setColumnDefinition(final ColumnDefinition p0);
    
    boolean isLoadDataQuery();
    
    void setLoadDataQuery(final boolean p0);
    
    T[] getBindValues();
    
    void setBindValues(final T[] p0);
    
    boolean clearBindValues();
    
    void checkParameterSet(final int p0);
    
    void checkAllParametersSet();
    
    int getNumberOfExecutions();
    
    void setNumberOfExecutions(final int p0);
    
    void setValue(final int p0, final byte[] p1);
    
    void setValue(final int p0, final byte[] p1, final MysqlType p2);
    
    void setValue(final int p0, final String p1);
    
    void setValue(final int p0, final String p1, final MysqlType p2);
    
    void setAsciiStream(final int p0, final InputStream p1);
    
    void setAsciiStream(final int p0, final InputStream p1, final int p2);
    
    void setAsciiStream(final int p0, final InputStream p1, final long p2);
    
    void setBigDecimal(final int p0, final BigDecimal p1);
    
    void setBigInteger(final int p0, final BigInteger p1);
    
    void setBinaryStream(final int p0, final InputStream p1);
    
    void setBinaryStream(final int p0, final InputStream p1, final int p2);
    
    void setBinaryStream(final int p0, final InputStream p1, final long p2);
    
    void setBlob(final int p0, final Blob p1);
    
    void setBlob(final int p0, final InputStream p1);
    
    void setBlob(final int p0, final InputStream p1, final long p2);
    
    void setBoolean(final int p0, final boolean p1);
    
    void setByte(final int p0, final byte p1);
    
    void setBytes(final int p0, final byte[] p1);
    
    void setBytes(final int p0, final byte[] p1, final boolean p2, final boolean p3);
    
    void setBytesNoEscape(final int p0, final byte[] p1);
    
    void setBytesNoEscapeNoQuotes(final int p0, final byte[] p1);
    
    void setCharacterStream(final int p0, final Reader p1);
    
    void setCharacterStream(final int p0, final Reader p1, final int p2);
    
    void setCharacterStream(final int p0, final Reader p1, final long p2);
    
    void setClob(final int p0, final Clob p1);
    
    void setClob(final int p0, final Reader p1);
    
    void setClob(final int p0, final Reader p1, final long p2);
    
    void setDate(final int p0, final Date p1);
    
    void setDate(final int p0, final Date p1, final Calendar p2);
    
    void setDouble(final int p0, final double p1);
    
    void setFloat(final int p0, final float p1);
    
    void setInt(final int p0, final int p1);
    
    void setLong(final int p0, final long p1);
    
    void setNCharacterStream(final int p0, final Reader p1);
    
    void setNCharacterStream(final int p0, final Reader p1, final long p2);
    
    void setNClob(final int p0, final Reader p1);
    
    void setNClob(final int p0, final Reader p1, final long p2);
    
    void setNClob(final int p0, final NClob p1);
    
    void setNString(final int p0, final String p1);
    
    void setNull(final int p0);
    
    void setObject(final int p0, final Object p1);
    
    void setObject(final int p0, final Object p1, final MysqlType p2);
    
    void setObject(final int p0, final Object p1, final MysqlType p2, final int p3);
    
    void setShort(final int p0, final short p1);
    
    void setString(final int p0, final String p1);
    
    void setTime(final int p0, final Time p1);
    
    void setTime(final int p0, final Time p1, final Calendar p2);
    
    void setTimestamp(final int p0, final Timestamp p1, final Calendar p2);
    
    void setTimestamp(final int p0, final Timestamp p1);
    
    void setTimestamp(final int p0, final Timestamp p1, final Calendar p2, final int p3);
}
