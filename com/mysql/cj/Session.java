
package com.mysql.cj;

import java.util.stream.Collector;
import java.util.function.Function;
import com.mysql.cj.result.Row;
import java.util.function.Predicate;
import java.net.SocketAddress;
import com.mysql.cj.protocol.ServerSession;
import com.mysql.cj.log.ProfilerEventHandler;
import com.mysql.cj.log.Log;
import com.mysql.cj.exceptions.ExceptionInterceptor;
import com.mysql.cj.protocol.Message;
import com.mysql.cj.conf.PropertySet;

public interface Session
{
    PropertySet getPropertySet();
    
     <M extends Message> MessageBuilder<M> getMessageBuilder();
    
    void changeUser(final String p0, final String p1, final String p2);
    
    ExceptionInterceptor getExceptionInterceptor();
    
    void setExceptionInterceptor(final ExceptionInterceptor p0);
    
    void quit();
    
    void forceClose();
    
    boolean versionMeetsMinimum(final int p0, final int p1, final int p2);
    
    long getThreadId();
    
    boolean isSetNeededForAutoCommitMode(final boolean p0);
    
    Log getLog();
    
    ProfilerEventHandler getProfilerEventHandler();
    
    void setProfilerEventHandler(final ProfilerEventHandler p0);
    
    ServerSession getServerSession();
    
    boolean isSSLEstablished();
    
    SocketAddress getRemoteSocketAddress();
    
    String getProcessHost();
    
    void addListener(final SessionEventListener p0);
    
    void removeListener(final SessionEventListener p0);
    
    boolean isClosed();
    
    String getIdentifierQuoteString();
    
    DataStoreMetadata getDataStoreMetadata();
    
     <M extends Message, RES_T, R> RES_T query(final M p0, final Predicate<Row> p1, final Function<Row, R> p2, final Collector<R, ?, RES_T> p3);
    
    public interface SessionEventListener
    {
        void handleNormalClose();
        
        void handleReconnect();
        
        void handleCleanup(final Throwable p0);
    }
}
