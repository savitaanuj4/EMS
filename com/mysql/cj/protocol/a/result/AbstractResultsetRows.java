
package com.mysql.cj.protocol.a.result;

import com.mysql.cj.protocol.a.NativePacketPayload;
import com.mysql.cj.protocol.ResultsetRow;
import com.mysql.cj.protocol.ProtocolEntityFactory;
import com.mysql.cj.protocol.ResultsetRowsOwner;
import com.mysql.cj.protocol.ColumnDefinition;
import com.mysql.cj.protocol.ResultsetRows;

public abstract class AbstractResultsetRows implements ResultsetRows
{
    protected static final int BEFORE_START_OF_ROWS = -1;
    protected ColumnDefinition metadata;
    protected int currentPositionInFetchedRows;
    protected boolean wasEmpty;
    protected ResultsetRowsOwner owner;
    protected ProtocolEntityFactory<ResultsetRow, NativePacketPayload> rowFactory;
    
    public AbstractResultsetRows() {
        this.currentPositionInFetchedRows = -1;
        this.wasEmpty = false;
    }
    
    @Override
    public void setOwner(final ResultsetRowsOwner rs) {
        this.owner = rs;
    }
    
    @Override
    public ResultsetRowsOwner getOwner() {
        return this.owner;
    }
    
    @Override
    public void setMetadata(final ColumnDefinition columnDefinition) {
        this.metadata = columnDefinition;
    }
    
    @Override
    public ColumnDefinition getMetadata() {
        return this.metadata;
    }
    
    @Override
    public boolean wasEmpty() {
        return this.wasEmpty;
    }
}
