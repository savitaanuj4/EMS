
package com.mysql.cj.protocol;

public interface WatchableStream
{
    void setWatcher(final OutputStreamWatcher p0);
    
    int size();
    
    byte[] toByteArray();
    
    void write(final byte[] p0, final int p1, final int p2);
}
