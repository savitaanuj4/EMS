
package com.mysql.cj.result;

import com.mysql.cj.exceptions.ExceptionFactory;
import com.mysql.cj.exceptions.WrongArgumentException;
import com.mysql.cj.exceptions.DataReadException;
import com.mysql.cj.Messages;
import java.util.Locale;
import java.util.TimeZone;
import java.util.Calendar;
import com.mysql.cj.WarningListener;
import java.sql.Time;

public class SqlTimeValueFactory extends DefaultValueFactory<Time>
{
    private WarningListener warningListener;
    private Calendar cal;
    
    public SqlTimeValueFactory(final Calendar calendar, final TimeZone tz) {
        if (calendar != null) {
            this.cal = (Calendar)calendar.clone();
        }
        else {
            (this.cal = Calendar.getInstance(tz, Locale.US)).setLenient(false);
        }
    }
    
    public SqlTimeValueFactory(final Calendar calendar, final TimeZone tz, final WarningListener warningListener) {
        this(calendar, tz);
        this.warningListener = warningListener;
    }
    
    @Override
    public Time createFromTime(final int hours, final int minutes, final int seconds, final int nanos) {
        if (hours < 0 || hours >= 24) {
            throw new DataReadException(Messages.getString("ResultSet.InvalidTimeValue", new Object[] { "" + hours + ":" + minutes + ":" + seconds }));
        }
        synchronized (this.cal) {
            try {
                this.cal.set(1970, 0, 1, hours, minutes, seconds);
                this.cal.set(14, 0);
                final long ms = nanos / 1000000 + this.cal.getTimeInMillis();
                return new Time(ms);
            }
            catch (IllegalArgumentException e) {
                throw ExceptionFactory.createException(WrongArgumentException.class, e.getMessage(), e);
            }
        }
    }
    
    @Override
    public Time createFromTimestamp(final int year, final int month, final int day, final int hours, final int minutes, final int seconds, final int nanos) {
        if (this.warningListener != null) {
            this.warningListener.warningEncountered(Messages.getString("ResultSet.PrecisionLostWarning", new Object[] { "java.sql.Time" }));
        }
        return this.createFromTime(hours, minutes, seconds, nanos);
    }
    
    @Override
    public String getTargetTypeName() {
        return Time.class.getName();
    }
}
