
package com.mysql.cj.result;

import com.mysql.cj.exceptions.DataReadException;
import com.mysql.cj.Messages;
import com.mysql.cj.WarningListener;
import java.time.LocalTime;

public class LocalTimeValueFactory extends DefaultValueFactory<LocalTime>
{
    private WarningListener warningListener;
    
    public LocalTimeValueFactory() {
    }
    
    public LocalTimeValueFactory(final WarningListener warningListener) {
        this();
        this.warningListener = warningListener;
    }
    
    @Override
    public LocalTime createFromTime(final int hours, final int minutes, final int seconds, final int nanos) {
        if (hours < 0 || hours >= 24) {
            throw new DataReadException(Messages.getString("ResultSet.InvalidTimeValue", new Object[] { "" + hours + ":" + minutes + ":" + seconds }));
        }
        return LocalTime.of(hours, minutes, seconds, nanos);
    }
    
    @Override
    public LocalTime createFromTimestamp(final int year, final int month, final int day, final int hours, final int minutes, final int seconds, final int nanos) {
        if (this.warningListener != null) {
            this.warningListener.warningEncountered(Messages.getString("ResultSet.PrecisionLostWarning", new Object[] { this.getTargetTypeName() }));
        }
        return this.createFromTime(hours, minutes, seconds, nanos);
    }
    
    @Override
    public String getTargetTypeName() {
        return LocalTime.class.getName();
    }
}
