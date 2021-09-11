
package com.mysql.cj.xdevapi;

import com.mysql.cj.result.SqlTimestampValueFactory;
import java.sql.Timestamp;
import com.mysql.cj.result.SqlTimeValueFactory;
import java.sql.Time;
import com.mysql.cj.result.StringValueFactory;
import com.mysql.cj.result.LongValueFactory;
import com.mysql.cj.result.IntegerValueFactory;
import com.mysql.cj.result.DoubleValueFactory;
import java.util.Calendar;
import com.mysql.cj.result.SqlDateValueFactory;
import java.sql.Date;
import com.mysql.cj.result.ByteValueFactory;
import com.mysql.cj.result.BooleanValueFactory;
import com.mysql.cj.result.ValueFactory;
import com.mysql.cj.result.BigDecimalValueFactory;
import java.math.BigDecimal;
import com.mysql.cj.exceptions.DataReadException;
import java.util.TimeZone;
import com.mysql.cj.protocol.ColumnDefinition;

public class RowImpl implements Row
{
    private com.mysql.cj.result.Row row;
    private ColumnDefinition metadata;
    private TimeZone defaultTimeZone;
    
    public RowImpl(final com.mysql.cj.result.Row row, final ColumnDefinition metadata, final TimeZone defaultTimeZone) {
        this.row = row;
        this.metadata = metadata;
        this.defaultTimeZone = defaultTimeZone;
    }
    
    private int fieldNameToIndex(final String fieldName) {
        final int idx = this.metadata.findColumn(fieldName, true, 0);
        if (idx == -1) {
            throw new DataReadException("Invalid column");
        }
        return idx;
    }
    
    @Override
    public BigDecimal getBigDecimal(final String fieldName) {
        return this.getBigDecimal(this.fieldNameToIndex(fieldName));
    }
    
    @Override
    public BigDecimal getBigDecimal(final int pos) {
        return this.row.getValue(pos, (ValueFactory<BigDecimal>)new BigDecimalValueFactory());
    }
    
    @Override
    public boolean getBoolean(final String fieldName) {
        return this.getBoolean(this.fieldNameToIndex(fieldName));
    }
    
    @Override
    public boolean getBoolean(final int pos) {
        return this.row.getValue(pos, (ValueFactory<Boolean>)new BooleanValueFactory());
    }
    
    @Override
    public byte getByte(final String fieldName) {
        return this.getByte(this.fieldNameToIndex(fieldName));
    }
    
    @Override
    public byte getByte(final int pos) {
        return this.row.getValue(pos, (ValueFactory<Byte>)new ByteValueFactory());
    }
    
    @Override
    public Date getDate(final String fieldName) {
        return this.getDate(this.fieldNameToIndex(fieldName));
    }
    
    @Override
    public Date getDate(final int pos) {
        return this.row.getValue(pos, (ValueFactory<Date>)new SqlDateValueFactory(null, this.defaultTimeZone));
    }
    
    @Override
    public DbDoc getDbDoc(final String fieldName) {
        return this.getDbDoc(this.fieldNameToIndex(fieldName));
    }
    
    @Override
    public DbDoc getDbDoc(final int pos) {
        return this.row.getValue(pos, (ValueFactory<DbDoc>)new DbDocValueFactory());
    }
    
    @Override
    public double getDouble(final String fieldName) {
        return this.getDouble(this.fieldNameToIndex(fieldName));
    }
    
    @Override
    public double getDouble(final int pos) {
        return this.row.getValue(pos, (ValueFactory<Double>)new DoubleValueFactory());
    }
    
    @Override
    public int getInt(final String fieldName) {
        return this.getInt(this.fieldNameToIndex(fieldName));
    }
    
    @Override
    public int getInt(final int pos) {
        return this.row.getValue(pos, (ValueFactory<Integer>)new IntegerValueFactory());
    }
    
    @Override
    public long getLong(final String fieldName) {
        return this.getLong(this.fieldNameToIndex(fieldName));
    }
    
    @Override
    public long getLong(final int pos) {
        return this.row.getValue(pos, (ValueFactory<Long>)new LongValueFactory());
    }
    
    @Override
    public String getString(final String fieldName) {
        return this.getString(this.fieldNameToIndex(fieldName));
    }
    
    @Override
    public String getString(final int pos) {
        return this.row.getValue(pos, (ValueFactory<String>)new StringValueFactory());
    }
    
    @Override
    public Time getTime(final String fieldName) {
        return this.getTime(this.fieldNameToIndex(fieldName));
    }
    
    @Override
    public Time getTime(final int pos) {
        return this.row.getValue(pos, (ValueFactory<Time>)new SqlTimeValueFactory(null, this.defaultTimeZone));
    }
    
    @Override
    public Timestamp getTimestamp(final String fieldName) {
        return this.getTimestamp(this.fieldNameToIndex(fieldName));
    }
    
    @Override
    public Timestamp getTimestamp(final int pos) {
        return this.row.getValue(pos, (ValueFactory<Timestamp>)new SqlTimestampValueFactory(null, this.defaultTimeZone));
    }
}
