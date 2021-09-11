
package com.mysql.cj.protocol.a.result;

import java.util.Map;
import java.util.HashMap;
import com.mysql.cj.result.DefaultColumnDefinition;
import com.mysql.cj.result.Field;
import com.mysql.cj.result.Row;
import com.mysql.cj.protocol.ResultsetRows;
import com.mysql.cj.protocol.ColumnDefinition;
import com.mysql.cj.protocol.Resultset;

public class NativeResultset implements Resultset
{
    protected ColumnDefinition columnDefinition;
    protected ResultsetRows rowData;
    protected Resultset nextResultset;
    protected int resultId;
    protected long updateCount;
    protected long updateId;
    protected String serverInfo;
    protected Row thisRow;
    
    public NativeResultset() {
        this.nextResultset = null;
        this.updateId = -1L;
        this.serverInfo = null;
        this.thisRow = null;
    }
    
    public NativeResultset(final OkPacket ok) {
        this.nextResultset = null;
        this.updateId = -1L;
        this.serverInfo = null;
        this.thisRow = null;
        this.updateCount = ok.getUpdateCount();
        this.updateId = ok.getUpdateID();
        this.serverInfo = ok.getInfo();
        this.columnDefinition = new DefaultColumnDefinition(new Field[0]);
    }
    
    public NativeResultset(final ResultsetRows rows) {
        this.nextResultset = null;
        this.updateId = -1L;
        this.serverInfo = null;
        this.thisRow = null;
        this.columnDefinition = rows.getMetadata();
        this.rowData = rows;
        this.updateCount = this.rowData.size();
        if (this.rowData.size() > 0) {
            if (this.updateCount == 1L && this.thisRow == null) {
                this.rowData.close();
                this.updateCount = -1L;
            }
        }
        else {
            this.thisRow = null;
        }
    }
    
    @Override
    public void setColumnDefinition(final ColumnDefinition metadata) {
        this.columnDefinition = metadata;
    }
    
    @Override
    public ColumnDefinition getColumnDefinition() {
        return this.columnDefinition;
    }
    
    @Override
    public boolean hasRows() {
        return this.rowData != null;
    }
    
    @Override
    public int getResultId() {
        return this.resultId;
    }
    
    @Override
    public void initRowsWithMetadata() {
        this.rowData.setMetadata(this.columnDefinition);
        this.columnDefinition.setColumnToIndexCache(new HashMap<String, Integer>());
    }
    
    @Override
    public synchronized void setNextResultset(final Resultset nextResultset) {
        this.nextResultset = nextResultset;
    }
    
    @Override
    public synchronized Resultset getNextResultset() {
        return this.nextResultset;
    }
    
    @Override
    public synchronized void clearNextResultset() {
        this.nextResultset = null;
    }
    
    @Override
    public long getUpdateCount() {
        return this.updateCount;
    }
    
    @Override
    public long getUpdateID() {
        return this.updateId;
    }
    
    @Override
    public String getServerInfo() {
        return this.serverInfo;
    }
    
    @Override
    public ResultsetRows getRows() {
        return this.rowData;
    }
}
