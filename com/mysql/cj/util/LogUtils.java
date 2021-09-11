
package com.mysql.cj.util;

import com.mysql.cj.log.ProfilerEvent;

public class LogUtils
{
    public static final String CALLER_INFORMATION_NOT_AVAILABLE = "Caller information not available";
    private static final String LINE_SEPARATOR;
    private static final int LINE_SEPARATOR_LENGTH;
    
    public static Object expandProfilerEventIfNecessary(final Object possibleProfilerEvent) {
        if (possibleProfilerEvent instanceof ProfilerEvent) {
            final StringBuilder msgBuf = new StringBuilder();
            final ProfilerEvent evt = (ProfilerEvent)possibleProfilerEvent;
            String locationInformation = evt.getEventCreationPointAsString();
            if (locationInformation == null) {
                locationInformation = Util.stackTraceToString(new Throwable());
            }
            msgBuf.append("Profiler Event: [");
            switch (evt.getEventType()) {
                case 4: {
                    msgBuf.append("EXECUTE");
                    break;
                }
                case 5: {
                    msgBuf.append("FETCH");
                    break;
                }
                case 1: {
                    msgBuf.append("CONSTRUCT");
                    break;
                }
                case 2: {
                    msgBuf.append("PREPARE");
                    break;
                }
                case 3: {
                    msgBuf.append("QUERY");
                    break;
                }
                case 0: {
                    msgBuf.append("WARN");
                    break;
                }
                case 6: {
                    msgBuf.append("SLOW QUERY");
                    break;
                }
                default: {
                    msgBuf.append("UNKNOWN");
                    break;
                }
            }
            msgBuf.append("] ");
            msgBuf.append(locationInformation);
            msgBuf.append(" duration: ");
            msgBuf.append(evt.getEventDuration());
            msgBuf.append(" ");
            msgBuf.append(evt.getDurationUnits());
            msgBuf.append(", connection-id: ");
            msgBuf.append(evt.getConnectionId());
            msgBuf.append(", statement-id: ");
            msgBuf.append(evt.getStatementId());
            msgBuf.append(", resultset-id: ");
            msgBuf.append(evt.getResultSetId());
            final String evtMessage = evt.getMessage();
            if (evtMessage != null) {
                msgBuf.append(", message: ");
                msgBuf.append(evtMessage);
            }
            return msgBuf;
        }
        return possibleProfilerEvent;
    }
    
    public static String findCallingClassAndMethod(final Throwable t) {
        final String stackTraceAsString = Util.stackTraceToString(t);
        String callingClassAndMethod = "Caller information not available";
        final int endInternalMethods = Math.max(Math.max(stackTraceAsString.lastIndexOf("com.mysql.cj"), stackTraceAsString.lastIndexOf("com.mysql.cj.core")), stackTraceAsString.lastIndexOf("com.mysql.cj.jdbc"));
        if (endInternalMethods != -1) {
            final int endOfLine = stackTraceAsString.indexOf(LogUtils.LINE_SEPARATOR, endInternalMethods);
            if (endOfLine != -1) {
                final int nextEndOfLine = stackTraceAsString.indexOf(LogUtils.LINE_SEPARATOR, endOfLine + LogUtils.LINE_SEPARATOR_LENGTH);
                callingClassAndMethod = ((nextEndOfLine != -1) ? stackTraceAsString.substring(endOfLine + LogUtils.LINE_SEPARATOR_LENGTH, nextEndOfLine) : stackTraceAsString.substring(endOfLine + LogUtils.LINE_SEPARATOR_LENGTH));
            }
        }
        if (!callingClassAndMethod.startsWith("\tat ") && !callingClassAndMethod.startsWith("at ")) {
            return "at " + callingClassAndMethod;
        }
        return callingClassAndMethod;
    }
    
    static {
        LINE_SEPARATOR = System.getProperty("line.separator");
        LINE_SEPARATOR_LENGTH = LogUtils.LINE_SEPARATOR.length();
    }
}
