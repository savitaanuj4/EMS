
package com.mysql.cj.util;

import java.util.ArrayList;
import java.io.StringReader;
import java.util.Locale;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.io.IOException;
import com.mysql.cj.exceptions.WrongArgumentException;
import java.sql.Timestamp;
import com.mysql.cj.exceptions.ExceptionFactory;
import com.mysql.cj.Messages;
import com.mysql.cj.exceptions.InvalidConnectionAttributeException;
import com.mysql.cj.exceptions.ExceptionInterceptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Properties;
import java.util.TimeZone;

public class TimeUtil
{
    static final TimeZone GMT_TIMEZONE;
    private static final String TIME_ZONE_MAPPINGS_RESOURCE = "/com/mysql/cj/util/TimeZoneMapping.properties";
    private static Properties timeZoneMappings;
    protected static final Method systemNanoTimeMethod;
    
    public static boolean nanoTimeAvailable() {
        return TimeUtil.systemNanoTimeMethod != null;
    }
    
    public static long getCurrentTimeNanosOrMillis() {
        if (TimeUtil.systemNanoTimeMethod != null) {
            try {
                return (long)TimeUtil.systemNanoTimeMethod.invoke(null, (Object[])null);
            }
            catch (IllegalArgumentException ex) {}
            catch (IllegalAccessException ex2) {}
            catch (InvocationTargetException ex3) {}
        }
        return System.currentTimeMillis();
    }
    
    public static String getCanonicalTimezone(String timezoneStr, final ExceptionInterceptor exceptionInterceptor) {
        if (timezoneStr == null) {
            return null;
        }
        timezoneStr = timezoneStr.trim();
        if (timezoneStr.length() > 2 && (timezoneStr.charAt(0) == '+' || timezoneStr.charAt(0) == '-') && Character.isDigit(timezoneStr.charAt(1))) {
            return "GMT" + timezoneStr;
        }
        synchronized (TimeUtil.class) {
            if (TimeUtil.timeZoneMappings == null) {
                loadTimeZoneMappings(exceptionInterceptor);
            }
        }
        final String canonicalTz;
        if ((canonicalTz = TimeUtil.timeZoneMappings.getProperty(timezoneStr)) != null) {
            return canonicalTz;
        }
        throw ExceptionFactory.createException(InvalidConnectionAttributeException.class, Messages.getString("TimeUtil.UnrecognizedTimezoneId", new Object[] { timezoneStr }), exceptionInterceptor);
    }
    
    public static Timestamp adjustTimestampNanosPrecision(final Timestamp ts, final int fsp, final boolean serverRoundFracSecs) {
        if (fsp < 0 || fsp > 6) {
            throw ExceptionFactory.createException(WrongArgumentException.class, "fsp value must be in 0 to 6 range.");
        }
        final Timestamp res = (Timestamp)ts.clone();
        int nanos = res.getNanos();
        final double tail = Math.pow(10.0, 9 - fsp);
        if (serverRoundFracSecs) {
            nanos = (int)Math.round(nanos / tail) * (int)tail;
            if (nanos > 999999999) {
                nanos %= 1000000000;
                res.setTime(res.getTime() + 1000L);
            }
        }
        else {
            nanos = (int)(nanos / tail) * (int)tail;
        }
        res.setNanos(nanos);
        return res;
    }
    
    public static String formatNanos(int nanos, final int fsp) {
        if (nanos < 0 || nanos > 999999999) {
            throw ExceptionFactory.createException(WrongArgumentException.class, "nanos value must be in 0 to 999999999 range but was " + nanos);
        }
        if (fsp < 0 || fsp > 6) {
            throw ExceptionFactory.createException(WrongArgumentException.class, "fsp value must be in 0 to 6 range but was " + fsp);
        }
        if (fsp == 0 || nanos == 0) {
            return "0";
        }
        nanos /= (int)Math.pow(10.0, 9 - fsp);
        if (nanos == 0) {
            return "0";
        }
        String nanosString = Integer.toString(nanos);
        final String zeroPadding = "000000000";
        int pos;
        for (nanosString = "000000000".substring(0, fsp - nanosString.length()) + nanosString, pos = fsp - 1; nanosString.charAt(pos) == '0'; --pos) {}
        nanosString = nanosString.substring(0, pos + 1);
        return nanosString;
    }
    
    private static void loadTimeZoneMappings(final ExceptionInterceptor exceptionInterceptor) {
        TimeUtil.timeZoneMappings = new Properties();
        try {
            TimeUtil.timeZoneMappings.load(TimeUtil.class.getResourceAsStream("/com/mysql/cj/util/TimeZoneMapping.properties"));
        }
        catch (IOException e) {
            throw ExceptionFactory.createException(Messages.getString("TimeUtil.LoadTimeZoneMappingError"), exceptionInterceptor);
        }
        for (final String tz : TimeZone.getAvailableIDs()) {
            if (!TimeUtil.timeZoneMappings.containsKey(tz)) {
                TimeUtil.timeZoneMappings.put(tz, tz);
            }
        }
    }
    
    public static Timestamp truncateFractionalSeconds(final Timestamp timestamp) {
        final Timestamp truncatedTimestamp = new Timestamp(timestamp.getTime());
        truncatedTimestamp.setNanos(0);
        return truncatedTimestamp;
    }
    
    public static SimpleDateFormat getSimpleDateFormat(final SimpleDateFormat cachedSimpleDateFormat, final String pattern, final Calendar cal, final TimeZone tz) {
        final SimpleDateFormat sdf = (cachedSimpleDateFormat != null) ? cachedSimpleDateFormat : new SimpleDateFormat(pattern, Locale.US);
        if (cal != null) {
            sdf.setCalendar((Calendar)cal.clone());
        }
        if (tz != null) {
            sdf.setTimeZone(tz);
        }
        return sdf;
    }
    
    public static final String getDateTimePattern(final String dt, final boolean toTime) throws IOException {
        final int dtLength = (dt != null) ? dt.length() : 0;
        if (dtLength >= 8 && dtLength <= 10) {
            int dashCount = 0;
            boolean isDateOnly = true;
            for (int i = 0; i < dtLength; ++i) {
                final char c = dt.charAt(i);
                if (!Character.isDigit(c) && c != '-') {
                    isDateOnly = false;
                    break;
                }
                if (c == '-') {
                    ++dashCount;
                }
            }
            if (isDateOnly && dashCount == 2) {
                return "yyyy-MM-dd";
            }
        }
        boolean colonsOnly = true;
        for (int j = 0; j < dtLength; ++j) {
            final char c2 = dt.charAt(j);
            if (!Character.isDigit(c2) && c2 != ':') {
                colonsOnly = false;
                break;
            }
        }
        if (colonsOnly) {
            return "HH:mm:ss";
        }
        final StringReader reader = new StringReader(dt + " ");
        final ArrayList<Object[]> vec = new ArrayList<Object[]>();
        final ArrayList<Object[]> vecRemovelist = new ArrayList<Object[]>();
        Object[] nv = { 'y', new StringBuilder(), 0 };
        vec.add(nv);
        if (toTime) {
            nv = new Object[] { 'h', new StringBuilder(), 0 };
            vec.add(nv);
        }
        int z;
        while ((z = reader.read()) != -1) {
            final char separator = (char)z;
            for (int maxvecs = vec.size(), count = 0; count < maxvecs; ++count) {
                final Object[] v = vec.get(count);
                final int n = (int)v[2];
                char c3 = getSuccessor((char)v[0], n);
                if (!Character.isLetterOrDigit(separator)) {
                    if (c3 == (char)v[0] && c3 != 'S') {
                        vecRemovelist.add(v);
                    }
                    else {
                        ((StringBuilder)v[1]).append(separator);
                        if (c3 == 'X' || c3 == 'Y') {
                            v[2] = 4;
                        }
                    }
                }
                else {
                    if (c3 == 'X') {
                        c3 = 'y';
                        nv = new Object[] { 'M', new StringBuilder(((StringBuilder)v[1]).toString()).append('M'), 1 };
                        vec.add(nv);
                    }
                    else if (c3 == 'Y') {
                        c3 = 'M';
                        nv = new Object[] { 'd', new StringBuilder(((StringBuilder)v[1]).toString()).append('d'), 1 };
                        vec.add(nv);
                    }
                    ((StringBuilder)v[1]).append(c3);
                    if (c3 == (char)v[0]) {
                        v[2] = n + 1;
                    }
                    else {
                        v[0] = c3;
                        v[2] = 1;
                    }
                }
            }
            for (int size = vecRemovelist.size(), k = 0; k < size; ++k) {
                final Object[] v = vecRemovelist.get(k);
                vec.remove(v);
            }
            vecRemovelist.clear();
        }
        for (int size = vec.size(), k = 0; k < size; ++k) {
            final Object[] v = vec.get(k);
            final char c3 = (char)v[0];
            final int n = (int)v[2];
            final boolean bk = getSuccessor(c3, n) != c3;
            final boolean atEnd = (c3 == 's' || c3 == 'm' || (c3 == 'h' && toTime)) && bk;
            final boolean finishesAtDate = bk && c3 == 'd' && !toTime;
            final boolean containsEnd = ((StringBuilder)v[1]).toString().indexOf(87) != -1;
            if ((!atEnd && !finishesAtDate) || containsEnd) {
                vecRemovelist.add(v);
            }
        }
        for (int size = vecRemovelist.size(), k = 0; k < size; ++k) {
            vec.remove(vecRemovelist.get(k));
        }
        vecRemovelist.clear();
        final Object[] v = vec.get(0);
        final StringBuilder format = (StringBuilder)v[1];
        format.setLength(format.length() - 1);
        return format.toString();
    }
    
    private static final char getSuccessor(final char c, final int n) {
        return (c == 'y' && n == 2) ? 'X' : ((c == 'y' && n < 4) ? 'y' : ((c == 'y') ? 'M' : ((c == 'M' && n == 2) ? 'Y' : ((c == 'M' && n < 3) ? 'M' : ((c == 'M') ? 'd' : ((c == 'd' && n < 2) ? 'd' : ((c == 'd') ? 'H' : ((c == 'H' && n < 2) ? 'H' : ((c == 'H') ? 'm' : ((c == 'm' && n < 2) ? 'm' : ((c == 'm') ? 's' : ((c == 's' && n < 2) ? 's' : 'W'))))))))))));
    }
    
    static {
        GMT_TIMEZONE = TimeZone.getTimeZone("GMT");
        TimeUtil.timeZoneMappings = null;
        Method aMethod;
        try {
            aMethod = System.class.getMethod("nanoTime", (Class<?>[])null);
        }
        catch (SecurityException e) {
            aMethod = null;
        }
        catch (NoSuchMethodException e2) {
            aMethod = null;
        }
        systemNanoTimeMethod = aMethod;
    }
}
