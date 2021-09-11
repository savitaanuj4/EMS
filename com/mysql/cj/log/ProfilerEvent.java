
package com.mysql.cj.log;

public interface ProfilerEvent
{
    public static final byte TYPE_WARN = 0;
    public static final byte TYPE_OBJECT_CREATION = 1;
    public static final byte TYPE_PREPARE = 2;
    public static final byte TYPE_QUERY = 3;
    public static final byte TYPE_EXECUTE = 4;
    public static final byte TYPE_FETCH = 5;
    public static final byte TYPE_SLOW_QUERY = 6;
    
    byte getEventType();
    
    void setEventType(final byte p0);
    
    long getEventDuration();
    
    String getDurationUnits();
    
    long getConnectionId();
    
    int getResultSetId();
    
    int getStatementId();
    
    String getMessage();
    
    long getEventCreationTime();
    
    String getCatalog();
    
    String getEventCreationPointAsString();
    
    byte[] pack();
}
