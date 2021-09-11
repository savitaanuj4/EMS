
package com.mysql.cj.result;

import com.mysql.cj.exceptions.DataReadException;
import com.mysql.cj.Messages;
import com.mysql.cj.WarningListener;
import java.time.LocalDate;

public class LocalDateValueFactory extends DefaultValueFactory<LocalDate>
{
    private WarningListener warningListener;
    
    public LocalDateValueFactory() {
    }
    
    public LocalDateValueFactory(final WarningListener warningListener) {
        this();
        this.warningListener = warningListener;
    }
    
    @Override
    public LocalDate createFromDate(final int year, final int month, final int day) {
        if (year == 0 && month == 0 && day == 0) {
            throw new DataReadException(Messages.getString("ResultSet.InvalidZeroDate"));
        }
        return LocalDate.of(year, month, day);
    }
    
    @Override
    public LocalDate createFromTimestamp(final int year, final int month, final int day, final int hours, final int minutes, final int seconds, final int nanos) {
        if (this.warningListener != null) {
            this.warningListener.warningEncountered(Messages.getString("ResultSet.PrecisionLostWarning", new Object[] { this.getTargetTypeName() }));
        }
        return this.createFromDate(year, month, day);
    }
    
    @Override
    public String getTargetTypeName() {
        return LocalDate.class.getName();
    }
}
