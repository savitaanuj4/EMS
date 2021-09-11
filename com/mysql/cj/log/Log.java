
package com.mysql.cj.log;

public interface Log
{
    public static final String LOGGER_INSTANCE_NAME = "MySQL";
    
    boolean isDebugEnabled();
    
    boolean isErrorEnabled();
    
    boolean isFatalEnabled();
    
    boolean isInfoEnabled();
    
    boolean isTraceEnabled();
    
    boolean isWarnEnabled();
    
    void logDebug(final Object p0);
    
    void logDebug(final Object p0, final Throwable p1);
    
    void logError(final Object p0);
    
    void logError(final Object p0, final Throwable p1);
    
    void logFatal(final Object p0);
    
    void logFatal(final Object p0, final Throwable p1);
    
    void logInfo(final Object p0);
    
    void logInfo(final Object p0, final Throwable p1);
    
    void logTrace(final Object p0);
    
    void logTrace(final Object p0, final Throwable p1);
    
    void logWarn(final Object p0);
    
    void logWarn(final Object p0, final Throwable p1);
}
