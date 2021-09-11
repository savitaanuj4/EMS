
package com.mysql.cj.result;

public class ZeroDateTimeToNullValueFactory<T> extends BaseDecoratingValueFactory<T>
{
    public ZeroDateTimeToNullValueFactory(final ValueFactory<T> targetVf) {
        super(targetVf);
    }
    
    @Override
    public T createFromDate(final int year, final int month, final int day) {
        if (year + month + day == 0) {
            return null;
        }
        return this.targetVf.createFromDate(year, month, day);
    }
    
    @Override
    public T createFromTimestamp(final int year, final int month, final int day, final int hours, final int minutes, final int seconds, final int nanos) {
        if (year + month + day + hours + minutes + seconds + nanos == 0) {
            return null;
        }
        return this.targetVf.createFromTimestamp(year, month, day, hours, minutes, seconds, nanos);
    }
}
