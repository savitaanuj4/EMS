
package com.mysql.cj;

import com.mysql.cj.conf.HostInfo;
import com.mysql.cj.protocol.a.NativeMessageBuilder;
import com.mysql.cj.util.StringUtils;
import com.mysql.cj.exceptions.OperationCancelledException;
import com.mysql.cj.conf.PropertyKey;
import java.util.TimerTask;

public class CancelQueryTaskImpl extends TimerTask implements CancelQueryTask
{
    Query queryToCancel;
    Throwable caughtWhileCancelling;
    boolean queryTimeoutKillsConnection;
    
    public CancelQueryTaskImpl(final Query cancellee) {
        this.caughtWhileCancelling = null;
        this.queryTimeoutKillsConnection = false;
        this.queryToCancel = cancellee;
        final NativeSession session = (NativeSession)cancellee.getSession();
        this.queryTimeoutKillsConnection = session.getPropertySet().getBooleanProperty(PropertyKey.queryTimeoutKillsConnection).getValue();
    }
    
    @Override
    public boolean cancel() {
        final boolean res = super.cancel();
        this.queryToCancel = null;
        return res;
    }
    
    @Override
    public void run() {
        final Thread cancelThread = new Thread() {
            @Override
            public void run() {
                final Query localQueryToCancel = CancelQueryTaskImpl.this.queryToCancel;
                if (localQueryToCancel == null) {
                    return;
                }
                final NativeSession session = (NativeSession)localQueryToCancel.getSession();
                if (session == null) {
                    return;
                }
                try {
                    if (CancelQueryTaskImpl.this.queryTimeoutKillsConnection) {
                        localQueryToCancel.setCancelStatus(Query.CancelStatus.CANCELED_BY_TIMEOUT);
                        session.invokeCleanupListeners(new OperationCancelledException(Messages.getString("Statement.ConnectionKilledDueToTimeout")));
                    }
                    else {
                        synchronized (localQueryToCancel.getCancelTimeoutMutex()) {
                            final long origConnId = session.getThreadId();
                            final HostInfo hostInfo = session.getHostInfo();
                            final String database = hostInfo.getDatabase();
                            final String user = StringUtils.isNullOrEmpty(hostInfo.getUser()) ? "" : hostInfo.getUser();
                            final String password = StringUtils.isNullOrEmpty(hostInfo.getPassword()) ? "" : hostInfo.getPassword();
                            final NativeSession newSession = new NativeSession(hostInfo, session.getPropertySet());
                            newSession.connect(hostInfo, user, password, database, 30000, new TransactionEventHandler() {
                                @Override
                                public void transactionCompleted() {
                                }
                                
                                @Override
                                public void transactionBegun() {
                                }
                            });
                            newSession.sendCommand(new NativeMessageBuilder().buildComQuery(newSession.getSharedSendPacket(), "KILL QUERY " + origConnId), false, 0);
                            localQueryToCancel.setCancelStatus(Query.CancelStatus.CANCELED_BY_TIMEOUT);
                        }
                    }
                }
                catch (Throwable t) {
                    CancelQueryTaskImpl.this.caughtWhileCancelling = t;
                }
                finally {
                    CancelQueryTaskImpl.this.setQueryToCancel(null);
                }
            }
        };
        cancelThread.start();
    }
    
    @Override
    public Throwable getCaughtWhileCancelling() {
        return this.caughtWhileCancelling;
    }
    
    @Override
    public void setCaughtWhileCancelling(final Throwable caughtWhileCancelling) {
        this.caughtWhileCancelling = caughtWhileCancelling;
    }
    
    @Override
    public Query getQueryToCancel() {
        return this.queryToCancel;
    }
    
    @Override
    public void setQueryToCancel(final Query queryToCancel) {
        this.queryToCancel = queryToCancel;
    }
}
