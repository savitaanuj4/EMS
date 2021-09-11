
package com.mysql.cj.protocol.a.result;

import com.mysql.cj.protocol.ResultsetRow;
import com.mysql.cj.protocol.Message;
import java.util.ArrayList;
import com.mysql.cj.exceptions.ExceptionFactory;
import com.mysql.cj.Messages;
import com.mysql.cj.protocol.a.BinaryRowFactory;
import com.mysql.cj.protocol.Resultset;
import com.mysql.cj.protocol.ColumnDefinition;
import com.mysql.cj.protocol.a.NativeMessageBuilder;
import com.mysql.cj.protocol.a.NativeProtocol;
import com.mysql.cj.result.Row;
import java.util.List;
import com.mysql.cj.protocol.ResultsetRows;

public class ResultsetRowsCursor extends AbstractResultsetRows implements ResultsetRows
{
    private List<Row> fetchedRows;
    private int currentPositionInEntireResult;
    private boolean lastRowFetched;
    private NativeProtocol protocol;
    private boolean firstFetchCompleted;
    protected NativeMessageBuilder commandBuilder;
    
    public ResultsetRowsCursor(final NativeProtocol ioChannel, final ColumnDefinition columnDefinition) {
        this.currentPositionInEntireResult = -1;
        this.lastRowFetched = false;
        this.firstFetchCompleted = false;
        this.commandBuilder = new NativeMessageBuilder();
        this.currentPositionInEntireResult = -1;
        this.metadata = columnDefinition;
        this.protocol = ioChannel;
        this.rowFactory = new BinaryRowFactory(this.protocol, this.metadata, Resultset.Concurrency.READ_ONLY, false);
    }
    
    @Override
    public boolean isAfterLast() {
        return this.lastRowFetched && this.currentPositionInFetchedRows > this.fetchedRows.size();
    }
    
    @Override
    public boolean isBeforeFirst() {
        return this.currentPositionInEntireResult < 0;
    }
    
    @Override
    public int getPosition() {
        return this.currentPositionInEntireResult + 1;
    }
    
    @Override
    public boolean isEmpty() {
        return this.isBeforeFirst() && this.isAfterLast();
    }
    
    @Override
    public boolean isFirst() {
        return this.currentPositionInEntireResult == 0;
    }
    
    @Override
    public boolean isLast() {
        return this.lastRowFetched && this.currentPositionInFetchedRows == this.fetchedRows.size() - 1;
    }
    
    @Override
    public void close() {
        this.metadata = null;
        this.owner = null;
    }
    
    @Override
    public boolean hasNext() {
        if (this.fetchedRows != null && this.fetchedRows.size() == 0) {
            return false;
        }
        if (this.owner != null) {
            final int maxRows = this.owner.getOwningStatementMaxRows();
            if (maxRows != -1 && this.currentPositionInEntireResult + 1 > maxRows) {
                return false;
            }
        }
        if (this.currentPositionInEntireResult == -1) {
            this.fetchMoreRows();
            return this.fetchedRows.size() > 0;
        }
        if (this.currentPositionInFetchedRows < this.fetchedRows.size() - 1) {
            return true;
        }
        if (this.currentPositionInFetchedRows == this.fetchedRows.size() && this.lastRowFetched) {
            return false;
        }
        this.fetchMoreRows();
        return this.fetchedRows.size() > 0;
    }
    
    @Override
    public Row next() {
        if (this.fetchedRows == null && this.currentPositionInEntireResult != -1) {
            throw ExceptionFactory.createException(Messages.getString("ResultSet.Operation_not_allowed_after_ResultSet_closed_144"), this.protocol.getExceptionInterceptor());
        }
        if (!this.hasNext()) {
            return null;
        }
        ++this.currentPositionInEntireResult;
        ++this.currentPositionInFetchedRows;
        if (this.fetchedRows != null && this.fetchedRows.size() == 0) {
            return null;
        }
        if (this.fetchedRows == null || this.currentPositionInFetchedRows > this.fetchedRows.size() - 1) {
            this.fetchMoreRows();
            this.currentPositionInFetchedRows = 0;
        }
        final Row row = this.fetchedRows.get(this.currentPositionInFetchedRows);
        row.setMetadata(this.metadata);
        return row;
    }
    
    private void fetchMoreRows() {
        if (this.lastRowFetched) {
            this.fetchedRows = new ArrayList<Row>(0);
            return;
        }
        synchronized (this.owner.getSyncMutex()) {
            try {
                final boolean oldFirstFetchCompleted = this.firstFetchCompleted;
                if (!this.firstFetchCompleted) {
                    this.firstFetchCompleted = true;
                }
                int numRowsToFetch = this.owner.getOwnerFetchSize();
                if (numRowsToFetch == 0) {
                    numRowsToFetch = this.owner.getOwningStatementFetchSize();
                }
                if (numRowsToFetch == Integer.MIN_VALUE) {
                    numRowsToFetch = 1;
                }
                if (this.fetchedRows == null) {
                    this.fetchedRows = new ArrayList<Row>(numRowsToFetch);
                }
                else {
                    this.fetchedRows.clear();
                }
                this.protocol.sendCommand(this.commandBuilder.buildComStmtFetch(this.protocol.getSharedSendPacket(), this.owner.getOwningStatementServerId(), numRowsToFetch), true, 0);
                Row row = null;
                while ((row = this.protocol.read(ResultsetRow.class, this.rowFactory)) != null) {
                    this.fetchedRows.add(row);
                }
                this.currentPositionInFetchedRows = -1;
                if (this.protocol.getServerSession().isLastRowSent()) {
                    this.lastRowFetched = true;
                    if (!oldFirstFetchCompleted && this.fetchedRows.size() == 0) {
                        this.wasEmpty = true;
                    }
                }
            }
            catch (Exception ex) {
                throw ExceptionFactory.createException(ex.getMessage(), ex);
            }
        }
    }
    
    @Override
    public void addRow(final Row row) {
    }
    
    @Override
    public void afterLast() {
        throw ExceptionFactory.createException(Messages.getString("ResultSet.ForwardOnly"));
    }
    
    @Override
    public void beforeFirst() {
        throw ExceptionFactory.createException(Messages.getString("ResultSet.ForwardOnly"));
    }
    
    @Override
    public void beforeLast() {
        throw ExceptionFactory.createException(Messages.getString("ResultSet.ForwardOnly"));
    }
    
    @Override
    public void moveRowRelative(final int rows) {
        throw ExceptionFactory.createException(Messages.getString("ResultSet.ForwardOnly"));
    }
    
    @Override
    public void setCurrentRow(final int rowNumber) {
        throw ExceptionFactory.createException(Messages.getString("ResultSet.ForwardOnly"));
    }
}
