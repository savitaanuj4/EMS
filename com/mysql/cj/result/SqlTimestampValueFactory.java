
package com.mysql.cj.result;

import com.mysql.cj.exceptions.ExceptionFactory;
import com.mysql.cj.exceptions.WrongArgumentException;
import com.mysql.cj.exceptions.DataReadException;
import com.mysql.cj.Messages;
import java.util.Locale;
import java.util.TimeZone;
import java.util.Calendar;
import java.sql.Timestamp;

public class SqlTimestampValueFactory extends DefaultValueFactory<Timestamp>
{
    private Calendar cal;
    
    public SqlTimestampValueFactory(final Calendar calendar, final TimeZone tz) {
        if (calendar != null) {
            this.cal = (Calendar)calendar.clone();
        }
        else {
            (this.cal = Calendar.getInstance(tz, Locale.US)).setLenient(false);
        }
    }
    
    @Override
    public Timestamp createFromDate(final int year, final int month, final int day) {
        return this.createFromTimestamp(year, month, day, 0, 0, 0, 0);
    }
    
    @Override
    public Timestamp createFromTime(final int hours, final int minutes, final int seconds, final int nanos) {
        if (hours < 0 || hours >= 24) {
            throw new DataReadException(Messages.getString("ResultSet.InvalidTimeValue", new Object[] { "" + hours + ":" + minutes + ":" + seconds }));
        }
        return this.createFromTimestamp(1970, 1, 1, hours, minutes, seconds, nanos);
    }
    
    @Override
    public Timestamp createFromTimestamp(final int year, final int month, final int day, final int hours, final int minutes, final int seconds, final int nanos) {
        if (year == 0 && month == 0 && day == 0) {
            throw new DataReadException(Messages.getString("ResultSet.InvalidZeroDate"));
        }
        synchronized (this.cal) {
            try {
                this.cal.set(year, month - 1, day, hours, minutes, seconds);
                final Timestamp ts = new Timestamp(this.cal.getTimeInMillis());
                ts.setNanos(nanos);
                return ts;
            }
            catch (IllegalArgumentException e) {
                throw ExceptionFactory.createException(WrongArgumentException.class, e.getMessage(), e);
            }
        }
    }
    
    @Override
    public String getTargetTypeName() {
        return Timestamp.class.getName();
    }
}
