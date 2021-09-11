
package com.mysql.cj.exceptions;

import com.mysql.cj.util.Util;
import java.net.BindException;
import com.mysql.cj.Messages;
import com.mysql.cj.conf.PropertyKey;
import com.mysql.cj.protocol.PacketReceivedTimeHolder;
import com.mysql.cj.protocol.PacketSentTimeHolder;
import com.mysql.cj.protocol.ServerSession;
import com.mysql.cj.conf.PropertySet;

public class ExceptionFactory
{
    private static final long DEFAULT_WAIT_TIMEOUT_SECONDS = 28800L;
    private static final int DUE_TO_TIMEOUT_FALSE = 0;
    private static final int DUE_TO_TIMEOUT_MAYBE = 2;
    private static final int DUE_TO_TIMEOUT_TRUE = 1;
    
    public static CJException createException(final String message) {
        return createException(CJException.class, message);
    }
    
    public static <T extends CJException> T createException(final Class<T> clazz, final String message) {
        T sqlEx;
        try {
            sqlEx = clazz.getConstructor(String.class).newInstance(message);
        }
        catch (Throwable e) {
            sqlEx = (T)new CJException(message);
        }
        return sqlEx;
    }
    
    public static CJException createException(final String message, final ExceptionInterceptor interceptor) {
        return createException(CJException.class, message, interceptor);
    }
    
    public static <T extends CJException> T createException(final Class<T> clazz, final String message, final ExceptionInterceptor interceptor) {
        final T sqlEx = createException(clazz, message);
        return sqlEx;
    }
    
    public static CJException createException(final String message, final Throwable cause) {
        return createException(CJException.class, message, cause);
    }
    
    public static <T extends CJException> T createException(final Class<T> clazz, final String message, final Throwable cause) {
        final T sqlEx = createException(clazz, message);
        if (cause != null) {
            try {
                sqlEx.initCause(cause);
            }
            catch (Throwable t) {}
            if (cause instanceof CJException) {
                sqlEx.setSQLState(((CJException)cause).getSQLState());
                sqlEx.setVendorCode(((CJException)cause).getVendorCode());
                sqlEx.setTransient(((CJException)cause).isTransient());
            }
        }
        return sqlEx;
    }
    
    public static CJException createException(final String message, final Throwable cause, final ExceptionInterceptor interceptor) {
        return createException(CJException.class, message, cause, interceptor);
    }
    
    public static CJException createException(final String message, final String sqlState, final int vendorErrorCode, final boolean isTransient, final Throwable cause, final ExceptionInterceptor interceptor) {
        final CJException ex = createException(CJException.class, message, cause, interceptor);
        ex.setSQLState(sqlState);
        ex.setVendorCode(vendorErrorCode);
        ex.setTransient(isTransient);
        return ex;
    }
    
    public static <T extends CJException> T createException(final Class<T> clazz, final String message, final Throwable cause, final ExceptionInterceptor interceptor) {
        final T sqlEx = createException(clazz, message, cause);
        return sqlEx;
    }
    
    public static CJCommunicationsException createCommunicationsException(final PropertySet propertySet, final ServerSession serverSession, final PacketSentTimeHolder packetSentTimeHolder, final PacketReceivedTimeHolder packetReceivedTimeHolder, final Throwable cause, final ExceptionInterceptor interceptor) {
        final CJCommunicationsException sqlEx = createException(CJCommunicationsException.class, null, cause, interceptor);
        sqlEx.init(propertySet, serverSession, packetSentTimeHolder, packetReceivedTimeHolder);
        return sqlEx;
    }
    
    public static String createLinkFailureMessageBasedOnHeuristics(final PropertySet propertySet, final ServerSession serverSession, final PacketSentTimeHolder packetSentTimeHolder, final PacketReceivedTimeHolder packetReceivedTimeHolder, final Throwable underlyingException) {
        long serverTimeoutSeconds = 0L;
        boolean isInteractiveClient = false;
        final long lastPacketReceivedTimeMs = (packetReceivedTimeHolder == null) ? 0L : packetReceivedTimeHolder.getLastPacketReceivedTime();
        long lastPacketSentTimeMs = packetSentTimeHolder.getLastPacketSentTime();
        if (lastPacketSentTimeMs > lastPacketReceivedTimeMs) {
            lastPacketSentTimeMs = packetSentTimeHolder.getPreviousPacketSentTime();
        }
        if (propertySet != null) {
            isInteractiveClient = propertySet.getBooleanProperty(PropertyKey.interactiveClient).getValue();
            String serverTimeoutSecondsStr = null;
            if (serverSession != null) {
                serverTimeoutSecondsStr = (isInteractiveClient ? serverSession.getServerVariable("interactive_timeout") : serverSession.getServerVariable("wait_timeout"));
            }
            if (serverTimeoutSecondsStr != null) {
                try {
                    serverTimeoutSeconds = Long.parseLong(serverTimeoutSecondsStr);
                }
                catch (NumberFormatException nfe) {
                    serverTimeoutSeconds = 0L;
                }
            }
        }
        final StringBuilder exceptionMessageBuf = new StringBuilder();
        final long nowMs = System.currentTimeMillis();
        if (lastPacketSentTimeMs == 0L) {
            lastPacketSentTimeMs = nowMs;
        }
        final long timeSinceLastPacketSentMs = nowMs - lastPacketSentTimeMs;
        final long timeSinceLastPacketSeconds = timeSinceLastPacketSentMs / 1000L;
        final long timeSinceLastPacketReceivedMs = nowMs - lastPacketReceivedTimeMs;
        int dueToTimeout = 0;
        StringBuilder timeoutMessageBuf = null;
        if (serverTimeoutSeconds != 0L) {
            if (timeSinceLastPacketSeconds > serverTimeoutSeconds) {
                dueToTimeout = 1;
                timeoutMessageBuf = new StringBuilder();
                timeoutMessageBuf.append(Messages.getString("CommunicationsException.2"));
                timeoutMessageBuf.append(Messages.getString(isInteractiveClient ? "CommunicationsException.4" : "CommunicationsException.3"));
            }
        }
        else if (timeSinceLastPacketSeconds > 28800L) {
            dueToTimeout = 2;
            timeoutMessageBuf = new StringBuilder();
            timeoutMessageBuf.append(Messages.getString("CommunicationsException.5"));
            timeoutMessageBuf.append(Messages.getString("CommunicationsException.6"));
            timeoutMessageBuf.append(Messages.getString("CommunicationsException.7"));
            timeoutMessageBuf.append(Messages.getString("CommunicationsException.8"));
        }
        if (dueToTimeout == 1 || dueToTimeout == 2) {
            if (lastPacketReceivedTimeMs != 0L) {
                final Object[] timingInfo = { timeSinceLastPacketReceivedMs, timeSinceLastPacketSentMs };
                exceptionMessageBuf.append(Messages.getString("CommunicationsException.ServerPacketTimingInfo", timingInfo));
            }
            else {
                exceptionMessageBuf.append(Messages.getString("CommunicationsException.ServerPacketTimingInfoNoRecv", new Object[] { timeSinceLastPacketSentMs }));
            }
            if (timeoutMessageBuf != null) {
                exceptionMessageBuf.append((CharSequence)timeoutMessageBuf);
            }
            exceptionMessageBuf.append(Messages.getString("CommunicationsException.11"));
            exceptionMessageBuf.append(Messages.getString("CommunicationsException.12"));
            exceptionMessageBuf.append(Messages.getString("CommunicationsException.13"));
        }
        else if (underlyingException instanceof BindException) {
            final String localSocketAddress = propertySet.getStringProperty(PropertyKey.localSocketAddress).getValue();
            if (localSocketAddress != null && !Util.interfaceExists(localSocketAddress)) {
                exceptionMessageBuf.append(Messages.getString("CommunicationsException.LocalSocketAddressNotAvailable"));
            }
            else {
                exceptionMessageBuf.append(Messages.getString("CommunicationsException.TooManyClientConnections"));
            }
        }
        if (exceptionMessageBuf.length() == 0) {
            exceptionMessageBuf.append(Messages.getString("CommunicationsException.20"));
            if (propertySet.getBooleanProperty(PropertyKey.maintainTimeStats).getValue() && !propertySet.getBooleanProperty(PropertyKey.paranoid).getValue()) {
                exceptionMessageBuf.append("\n\n");
                if (lastPacketReceivedTimeMs != 0L) {
                    final Object[] timingInfo = { timeSinceLastPacketReceivedMs, timeSinceLastPacketSentMs };
                    exceptionMessageBuf.append(Messages.getString("CommunicationsException.ServerPacketTimingInfo", timingInfo));
                }
                else {
                    exceptionMessageBuf.append(Messages.getString("CommunicationsException.ServerPacketTimingInfoNoRecv", new Object[] { timeSinceLastPacketSentMs }));
                }
            }
        }
        return exceptionMessageBuf.toString();
    }
}
