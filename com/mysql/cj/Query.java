
package com.mysql.cj;

import java.util.concurrent.atomic.AtomicBoolean;
import com.mysql.cj.log.ProfilerEventHandler;
import java.util.List;
import com.mysql.cj.protocol.ProtocolEntityFactory;
import com.mysql.cj.protocol.Message;
import com.mysql.cj.protocol.Resultset;

public interface Query
{
    int getId();
    
    void setCancelStatus(final CancelStatus p0);
    
    void checkCancelTimeout();
    
     <T extends Resultset, M extends Message> ProtocolEntityFactory<T, M> getResultSetFactory();
    
    Session getSession();
    
    Object getCancelTimeoutMutex();
    
    void resetCancelledState();
    
    void closeQuery();
    
    void addBatch(final Object p0);
    
    List<Object> getBatchedArgs();
    
    void clearBatchedArgs();
    
    int getResultFetchSize();
    
    void setResultFetchSize(final int p0);
    
    Resultset.Type getResultType();
    
    void setResultType(final Resultset.Type p0);
    
    int getTimeoutInMillis();
    
    void setTimeoutInMillis(final int p0);
    
    CancelQueryTask startQueryTimer(final Query p0, final int p1);
    
    ProfilerEventHandler getEventSink();
    
    void setEventSink(final ProfilerEventHandler p0);
    
    AtomicBoolean getStatementExecuting();
    
    String getCurrentCatalog();
    
    void setCurrentCatalog(final String p0);
    
    boolean isClearWarningsCalled();
    
    void setClearWarningsCalled(final boolean p0);
    
    void statementBegins();
    
    void stopQueryTimer(final CancelQueryTask p0, final boolean p1, final boolean p2);
    
    public enum CancelStatus
    {
        NOT_CANCELED, 
        CANCELED_BY_USER, 
        CANCELED_BY_TIMEOUT;
    }
}
