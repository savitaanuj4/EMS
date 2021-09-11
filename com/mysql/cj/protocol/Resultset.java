
package com.mysql.cj.protocol;

public interface Resultset extends ProtocolEntity
{
    void setColumnDefinition(final ColumnDefinition p0);
    
    ColumnDefinition getColumnDefinition();
    
    boolean hasRows();
    
    ResultsetRows getRows();
    
    void initRowsWithMetadata();
    
    int getResultId();
    
    void setNextResultset(final Resultset p0);
    
    Resultset getNextResultset();
    
    void clearNextResultset();
    
    long getUpdateCount();
    
    long getUpdateID();
    
    String getServerInfo();
    
    public enum Concurrency
    {
        READ_ONLY(1007), 
        UPDATABLE(1008);
        
        private int value;
        
        private Concurrency(final int jdbcRsConcur) {
            this.value = jdbcRsConcur;
        }
        
        public int getIntValue() {
            return this.value;
        }
        
        public static Concurrency fromValue(final int concurMode, final Concurrency backupValue) {
            for (final Concurrency c : values()) {
                if (c.getIntValue() == concurMode) {
                    return c;
                }
            }
            return backupValue;
        }
    }
    
    public enum Type
    {
        FORWARD_ONLY(1003), 
        SCROLL_INSENSITIVE(1004), 
        SCROLL_SENSITIVE(1005);
        
        private int value;
        
        private Type(final int jdbcRsType) {
            this.value = jdbcRsType;
        }
        
        public int getIntValue() {
            return this.value;
        }
        
        public static Type fromValue(final int rsType, final Type backupValue) {
            for (final Type t : values()) {
                if (t.getIntValue() == rsType) {
                    return t;
                }
            }
            return backupValue;
        }
    }
}
