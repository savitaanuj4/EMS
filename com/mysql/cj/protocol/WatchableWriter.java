
package com.mysql.cj.protocol;

import java.io.CharArrayWriter;

public class WatchableWriter extends CharArrayWriter
{
    private WriterWatcher watcher;
    
    @Override
    public void close() {
        super.close();
        if (this.watcher != null) {
            this.watcher.writerClosed(this);
        }
    }
    
    public void setWatcher(final WriterWatcher watcher) {
        this.watcher = watcher;
    }
}
