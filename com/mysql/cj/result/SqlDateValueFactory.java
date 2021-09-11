
package com.mysql.cj.result;

import com.mysql.cj.exceptions.ExceptionFactory;
import com.mysql.cj.exceptions.WrongArgumentException;
import com.mysql.cj.exceptions.DataReadException;
import com.mysql.cj.Messages;
import java.util.Locale;
import java.util.TimeZone;
import java.util.Calendar;
import com.mysql.cj.WarningListener;
import java.sql.Date;

public class SqlDateValueFactory extends DefaultValueFactory<Date>
{
    private WarningListener warningListener;
    private Calendar cal;
    
    public SqlDateValueFactory(final Calendar calendar, final TimeZone tz) {
        if (calendar != null) {
            this.cal = (Calendar)calendar.clone();
        }
        else {
            (this.cal = Calendar.getInstance(tz, Locale.US)).set(14, 0);
            this.cal.setLenient(false);
        }
    }
    
    public SqlDateValueFactory(final Calendar calendar, final TimeZone tz, final WarningListener warningListener) {
        this(calendar, tz);
        this.warningListener = warningListener;
    }
    
    @Override
    public Date createFromDate(final int year, final int month, final int day) {
        synchronized (this.cal) {
            try {
                if (year == 0 && month == 0 && day == 0) {
                    throw new DataReadException(Messages.getString("ResultSet.InvalidZeroDate"));
                }
                this.cal.clear();
                this.cal.set(year, month - 1, day);
                final long ms = this.cal.getTimeInMillis();
                return new Date(ms);
            }
            catch (IllegalArgumentException e) {
                throw ExceptionFactory.createException(WrongArgumentException.class, e.getMessage(), e);
            }
        }
    }
    
    @Override
    public Date createFromTime(final int hours, final int minutes, final int seconds, final int nanos) {
        if (this.warningListener != null) {
            this.warningListener.warningEncountered(Messages.getString("ResultSet.ImplicitDatePartWarning", new Object[] { "java.sql.Date" }));
        }
        synchronized (this.cal) {
            try {
                final Calendar c1 = Calendar.getInstance(TimeZone.getTimeZone("UTC"), Locale.US);
                c1.set(1970, 0, 1, hours, minutes, seconds);
                c1.set(14, 0);
                final long ms = nanos / 1000000 + c1.getTimeInMillis();
                return new Date(ms);
            }
            catch (IllegalArgumentException e) {
                throw ExceptionFactory.createException(WrongArgumentException.class, e.getMessage(), e);
            }
        }
    }
    
    @Override
    public Date createFromTimestamp(final int year, final int month, final int day, final int hours, final int minutes, final int seconds, final int nanos) {
        if (this.warningListener != null) {
            this.warningListener.warningEncountered(Messages.getString("ResultSet.PrecisionLostWarning", new Object[] { "java.sql.Date" }));
        }
        return this.createFromDate(year, month, day);
    }
    
    @Override
    public String getTargetTypeName() {
        return Date.class.getName();
    }
}
