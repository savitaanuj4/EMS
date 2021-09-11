
package com.mysql.cj;

public interface CancelQueryTask
{
    boolean cancel();
    
    Throwable getCaughtWhileCancelling();
    
    void setCaughtWhileCancelling(final Throwable p0);
    
    Query getQueryToCancel();
    
    void setQueryToCancel(final Query p0);
}
