
package com.mysql.cj.result;

public class ZeroDateTimeToDefaultValueFactory<T> extends BaseDecoratingValueFactory<T>
{
    public ZeroDateTimeToDefaultValueFactory(final ValueFactory<T> targetVf) {
        super(targetVf);
    }
    
    @Override
    public T createFromDate(final int year, final int month, final int day) {
        if (year + month + day == 0) {
            return this.targetVf.createFromDate(1, 1, 1);
        }
        return this.targetVf.createFromDate(year, month, day);
    }
    
    @Override
    public T createFromTime(final int hours, final int minutes, final int seconds, final int nanos) {
        if (hours + minutes + seconds + nanos == 0) {
            return this.targetVf.createFromTime(0, 0, 0, 0);
        }
        return this.targetVf.createFromTime(hours, minutes, seconds, nanos);
    }
    
    @Override
    public T createFromTimestamp(final int year, final int month, final int day, final int hours, final int minutes, final int seconds, final int nanos) {
        if (year + month + day + hours + minutes + seconds + nanos == 0) {
            return this.targetVf.createFromTimestamp(1, 1, 1, 0, 0, 0, 0);
        }
        return this.targetVf.createFromTimestamp(year, month, day, hours, minutes, seconds, nanos);
    }
}
