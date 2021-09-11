
package com.mysql.cj.protocol.x;

import java.util.NoSuchElementException;
import com.mysql.cj.protocol.ColumnDefinition;
import com.mysql.cj.result.RowList;

public class XProtocolRowInputStream implements RowList
{
    private ColumnDefinition metadata;
    private XProtocol protocol;
    private boolean isDone;
    private int position;
    private XProtocolRow next;
    
    public XProtocolRowInputStream(final ColumnDefinition metadata, final XProtocol protocol) {
        this.isDone = false;
        this.position = -1;
        this.metadata = metadata;
        this.protocol = protocol;
    }
    
    public XProtocolRow readRow() {
        if (!this.hasNext()) {
            this.isDone = true;
            return null;
        }
        ++this.position;
        final XProtocolRow r = this.next;
        this.next = null;
        return r;
    }
    
    @Override
    public XProtocolRow next() {
        if (!this.hasNext()) {
            throw new NoSuchElementException();
        }
        return this.readRow();
    }
    
    @Override
    public boolean hasNext() {
        if (this.isDone) {
            return false;
        }
        if (this.next == null) {
            this.next = this.protocol.readRowOrNull(this.metadata);
        }
        return this.next != null;
    }
    
    @Override
    public int getPosition() {
        return this.position;
    }
}
