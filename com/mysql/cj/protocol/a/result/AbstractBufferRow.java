
package com.mysql.cj.protocol.a.result;

import com.mysql.cj.exceptions.ExceptionInterceptor;
import com.mysql.cj.protocol.a.NativePacketPayload;
import com.mysql.cj.protocol.result.AbstractResultsetRow;

public abstract class AbstractBufferRow extends AbstractResultsetRow
{
    protected NativePacketPayload rowFromServer;
    protected int homePosition;
    protected int lastRequestedIndex;
    protected int lastRequestedPos;
    
    protected AbstractBufferRow(final ExceptionInterceptor exceptionInterceptor) {
        super(exceptionInterceptor);
        this.homePosition = 0;
        this.lastRequestedIndex = -1;
    }
    
    abstract int findAndSeekToOffset(final int p0);
}
