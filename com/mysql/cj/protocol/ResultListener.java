
package com.mysql.cj.protocol;

import com.mysql.cj.result.Row;

public interface ResultListener<OK extends ProtocolEntity>
{
    void onMetadata(final ColumnDefinition p0);
    
    void onRow(final Row p0);
    
    void onComplete(final OK p0);
    
    void onException(final Throwable p0);
}
