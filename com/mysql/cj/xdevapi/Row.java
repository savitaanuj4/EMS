
package com.mysql.cj.xdevapi;

import java.sql.Timestamp;
import java.sql.Time;
import java.sql.Date;
import java.math.BigDecimal;

public interface Row
{
    BigDecimal getBigDecimal(final String p0);
    
    BigDecimal getBigDecimal(final int p0);
    
    boolean getBoolean(final String p0);
    
    boolean getBoolean(final int p0);
    
    byte getByte(final String p0);
    
    byte getByte(final int p0);
    
    Date getDate(final String p0);
    
    Date getDate(final int p0);
    
    DbDoc getDbDoc(final String p0);
    
    DbDoc getDbDoc(final int p0);
    
    double getDouble(final String p0);
    
    double getDouble(final int p0);
    
    int getInt(final String p0);
    
    int getInt(final int p0);
    
    long getLong(final String p0);
    
    long getLong(final int p0);
    
    String getString(final String p0);
    
    String getString(final int p0);
    
    Time getTime(final String p0);
    
    Time getTime(final int p0);
    
    Timestamp getTimestamp(final String p0);
    
    Timestamp getTimestamp(final int p0);
}
