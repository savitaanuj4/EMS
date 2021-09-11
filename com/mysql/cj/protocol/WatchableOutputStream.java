
package com.mysql.cj.protocol;

import java.io.IOException;
import java.io.ByteArrayOutputStream;

public class WatchableOutputStream extends ByteArrayOutputStream implements WatchableStream
{
    private OutputStreamWatcher watcher;
    
    @Override
    public void close() throws IOException {
        super.close();
        if (this.watcher != null) {
            this.watcher.streamClosed(this);
        }
    }
    
    @Override
    public void setWatcher(final OutputStreamWatcher watcher) {
        this.watcher = watcher;
    }
}
