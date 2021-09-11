
package com.mysql.cj.jdbc.ha;

public class NdbLoadBalanceExceptionChecker extends StandardLoadBalanceExceptionChecker
{
    @Override
    public boolean shouldExceptionTriggerFailover(final Throwable ex) {
        return super.shouldExceptionTriggerFailover(ex) || this.checkNdbException(ex);
    }
    
    private boolean checkNdbException(final Throwable ex) {
        return ex.getMessage().startsWith("Lock wait timeout exceeded") || (ex.getMessage().startsWith("Got temporary error") && ex.getMessage().endsWith("from NDB"));
    }
}
