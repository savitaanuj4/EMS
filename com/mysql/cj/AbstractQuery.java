
package com.mysql.cj;

import com.mysql.cj.exceptions.ExceptionFactory;
import java.util.TimerTask;
import java.util.Collections;
import java.util.ArrayList;
import com.mysql.cj.protocol.ProtocolEntityFactory;
import com.mysql.cj.protocol.Message;
import com.mysql.cj.exceptions.CJException;
import com.mysql.cj.exceptions.OperationCancelledException;
import com.mysql.cj.exceptions.CJTimeoutException;
import com.mysql.cj.conf.PropertyKey;
import java.util.concurrent.atomic.AtomicBoolean;
import com.mysql.cj.log.ProfilerEventHandler;
import com.mysql.cj.protocol.Resultset;
import java.util.List;
import com.mysql.cj.conf.RuntimeProperty;

public abstract class AbstractQuery implements Query
{
    static int statementCounter;
    public NativeSession session;
    protected int statementId;
    protected boolean profileSQL;
    protected RuntimeProperty<Integer> maxAllowedPacket;
    protected String charEncoding;
    protected Object cancelTimeoutMutex;
    private CancelStatus cancelStatus;
    protected int timeoutInMillis;
    protected List<Object> batchedArgs;
    protected boolean useCursorFetch;
    protected Resultset.Type resultSetType;
    protected int fetchSize;
    protected ProfilerEventHandler eventSink;
    protected final AtomicBoolean statementExecuting;
    protected String currentCatalog;
    protected boolean clearWarningsCalled;
    
    public AbstractQuery(final NativeSession sess) {
        this.session = null;
        this.profileSQL = false;
        this.charEncoding = null;
        this.cancelTimeoutMutex = new Object();
        this.cancelStatus = CancelStatus.NOT_CANCELED;
        this.timeoutInMillis = 0;
        this.useCursorFetch = false;
        this.resultSetType = Resultset.Type.FORWARD_ONLY;
        this.fetchSize = 0;
        this.eventSink = null;
        this.statementExecuting = new AtomicBoolean(false);
        this.currentCatalog = null;
        this.clearWarningsCalled = false;
        ++AbstractQuery.statementCounter;
        this.session = sess;
        this.profileSQL = sess.getPropertySet().getBooleanProperty(PropertyKey.profileSQL).getValue();
        this.maxAllowedPacket = sess.getPropertySet().getIntegerProperty(PropertyKey.maxAllowedPacket);
        this.charEncoding = sess.getPropertySet().getStringProperty(PropertyKey.characterEncoding).getValue();
        this.useCursorFetch = sess.getPropertySet().getBooleanProperty(PropertyKey.useCursorFetch).getValue();
    }
    
    @Override
    public int getId() {
        return this.statementId;
    }
    
    @Override
    public void setCancelStatus(final CancelStatus cs) {
        this.cancelStatus = cs;
    }
    
    @Override
    public void checkCancelTimeout() {
        synchronized (this.cancelTimeoutMutex) {
            if (this.cancelStatus != CancelStatus.NOT_CANCELED) {
                final CJException cause = (this.cancelStatus == CancelStatus.CANCELED_BY_TIMEOUT) ? new CJTimeoutException() : new OperationCancelledException();
                this.resetCancelledState();
                throw cause;
            }
        }
    }
    
    @Override
    public void resetCancelledState() {
        synchronized (this.cancelTimeoutMutex) {
            this.cancelStatus = CancelStatus.NOT_CANCELED;
        }
    }
    
    @Override
    public <T extends Resultset, M extends Message> ProtocolEntityFactory<T, M> getResultSetFactory() {
        return null;
    }
    
    @Override
    public NativeSession getSession() {
        return this.session;
    }
    
    @Override
    public Object getCancelTimeoutMutex() {
        return this.cancelTimeoutMutex;
    }
    
    @Override
    public void closeQuery() {
        this.session = null;
    }
    
    @Override
    public void addBatch(final Object batch) {
        if (this.batchedArgs == null) {
            this.batchedArgs = new ArrayList<Object>();
        }
        this.batchedArgs.add(batch);
    }
    
    @Override
    public List<Object> getBatchedArgs() {
        return (this.batchedArgs == null) ? null : Collections.unmodifiableList((List<?>)this.batchedArgs);
    }
    
    @Override
    public void clearBatchedArgs() {
        if (this.batchedArgs != null) {
            this.batchedArgs.clear();
        }
    }
    
    @Override
    public int getResultFetchSize() {
        return this.fetchSize;
    }
    
    @Override
    public void setResultFetchSize(final int fetchSize) {
        this.fetchSize = fetchSize;
    }
    
    @Override
    public Resultset.Type getResultType() {
        return this.resultSetType;
    }
    
    @Override
    public void setResultType(final Resultset.Type resultSetType) {
        this.resultSetType = resultSetType;
    }
    
    @Override
    public int getTimeoutInMillis() {
        return this.timeoutInMillis;
    }
    
    @Override
    public void setTimeoutInMillis(final int timeoutInMillis) {
        this.timeoutInMillis = timeoutInMillis;
    }
    
    @Override
    public CancelQueryTask startQueryTimer(final Query stmtToCancel, final int timeout) {
        if (this.session.getPropertySet().getBooleanProperty(PropertyKey.enableQueryTimeouts).getValue() && timeout != 0) {
            final CancelQueryTaskImpl timeoutTask = new CancelQueryTaskImpl(stmtToCancel);
            this.session.getCancelTimer().schedule(timeoutTask, timeout);
            return timeoutTask;
        }
        return null;
    }
    
    @Override
    public void stopQueryTimer(final CancelQueryTask timeoutTask, final boolean rethrowCancelReason, final boolean checkCancelTimeout) {
        if (timeoutTask != null) {
            timeoutTask.cancel();
            if (rethrowCancelReason && timeoutTask.getCaughtWhileCancelling() != null) {
                final Throwable t = timeoutTask.getCaughtWhileCancelling();
                throw ExceptionFactory.createException(t.getMessage(), t);
            }
            this.session.getCancelTimer().purge();
            if (checkCancelTimeout) {
                this.checkCancelTimeout();
            }
        }
    }
    
    @Override
    public ProfilerEventHandler getEventSink() {
        return this.eventSink;
    }
    
    @Override
    public void setEventSink(final ProfilerEventHandler eventSink) {
        this.eventSink = eventSink;
    }
    
    @Override
    public AtomicBoolean getStatementExecuting() {
        return this.statementExecuting;
    }
    
    @Override
    public String getCurrentCatalog() {
        return this.currentCatalog;
    }
    
    @Override
    public void setCurrentCatalog(final String currentCatalog) {
        this.currentCatalog = currentCatalog;
    }
    
    @Override
    public boolean isClearWarningsCalled() {
        return this.clearWarningsCalled;
    }
    
    @Override
    public void setClearWarningsCalled(final boolean clearWarningsCalled) {
        this.clearWarningsCalled = clearWarningsCalled;
    }
    
    @Override
    public void statementBegins() {
        this.clearWarningsCalled = false;
        this.statementExecuting.set(true);
    }
    
    static {
        AbstractQuery.statementCounter = 1;
    }
}
