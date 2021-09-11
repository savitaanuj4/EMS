
package com.mysql.cj.xdevapi;

public interface FilterParams
{
    Object getCollection();
    
    Object getOrder();
    
    void setOrder(final String... p0);
    
    Long getLimit();
    
    void setLimit(final Long p0);
    
    Long getOffset();
    
    void setOffset(final Long p0);
    
    Object getCriteria();
    
    void setCriteria(final String p0);
    
    Object getArgs();
    
    void addArg(final String p0, final Object p1);
    
    void verifyAllArgsBound();
    
    void clearArgs();
    
    boolean isRelational();
    
    void setFields(final String... p0);
    
    Object getFields();
    
    void setGrouping(final String... p0);
    
    Object getGrouping();
    
    void setGroupingCriteria(final String p0);
    
    Object getGroupingCriteria();
    
    RowLock getLock();
    
    void setLock(final RowLock p0);
    
    RowLockOptions getLockOption();
    
    void setLockOption(final RowLockOptions p0);
    
    public enum RowLock
    {
        SHARED_LOCK(1), 
        EXCLUSIVE_LOCK(2);
        
        private int rowLock;
        
        private RowLock(final int rowLock) {
            this.rowLock = rowLock;
        }
        
        public int asNumber() {
            return this.rowLock;
        }
    }
    
    public enum RowLockOptions
    {
        NOWAIT(1), 
        SKIP_LOCKED(2);
        
        private int rowLockOption;
        
        private RowLockOptions(final int rowLockOption) {
            this.rowLockOption = rowLockOption;
        }
        
        public int asNumber() {
            return this.rowLockOption;
        }
    }
}
