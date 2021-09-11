
package com.mysql.cj.protocol.a.result;

import com.mysql.cj.protocol.ColumnDefinition;
import com.mysql.cj.result.Row;
import java.util.List;
import com.mysql.cj.protocol.ResultsetRows;

public class ResultsetRowsStatic extends AbstractResultsetRows implements ResultsetRows
{
    private List<Row> rows;
    
    public ResultsetRowsStatic(final List<? extends Row> rows, final ColumnDefinition columnDefinition) {
        this.currentPositionInFetchedRows = -1;
        this.rows = (List<Row>)rows;
        this.metadata = columnDefinition;
    }
    
    @Override
    public void addRow(final Row row) {
        this.rows.add(row);
    }
    
    @Override
    public void afterLast() {
        if (this.rows.size() > 0) {
            this.currentPositionInFetchedRows = this.rows.size();
        }
    }
    
    @Override
    public void beforeFirst() {
        if (this.rows.size() > 0) {
            this.currentPositionInFetchedRows = -1;
        }
    }
    
    @Override
    public void beforeLast() {
        if (this.rows.size() > 0) {
            this.currentPositionInFetchedRows = this.rows.size() - 2;
        }
    }
    
    @Override
    public Row get(final int atIndex) {
        if (atIndex < 0 || atIndex >= this.rows.size()) {
            return null;
        }
        return this.rows.get(atIndex).setMetadata(this.metadata);
    }
    
    @Override
    public int getPosition() {
        return this.currentPositionInFetchedRows;
    }
    
    @Override
    public boolean hasNext() {
        final boolean hasMore = this.currentPositionInFetchedRows + 1 < this.rows.size();
        return hasMore;
    }
    
    @Override
    public boolean isAfterLast() {
        return this.currentPositionInFetchedRows >= this.rows.size() && this.rows.size() != 0;
    }
    
    @Override
    public boolean isBeforeFirst() {
        return this.currentPositionInFetchedRows == -1 && this.rows.size() != 0;
    }
    
    @Override
    public boolean isDynamic() {
        return false;
    }
    
    @Override
    public boolean isEmpty() {
        return this.rows.size() == 0;
    }
    
    @Override
    public boolean isFirst() {
        return this.currentPositionInFetchedRows == 0;
    }
    
    @Override
    public boolean isLast() {
        return this.rows.size() != 0 && this.currentPositionInFetchedRows == this.rows.size() - 1;
    }
    
    @Override
    public void moveRowRelative(final int rowsToMove) {
        if (this.rows.size() > 0) {
            this.currentPositionInFetchedRows += rowsToMove;
            if (this.currentPositionInFetchedRows < -1) {
                this.beforeFirst();
            }
            else if (this.currentPositionInFetchedRows > this.rows.size()) {
                this.afterLast();
            }
        }
    }
    
    @Override
    public Row next() {
        ++this.currentPositionInFetchedRows;
        if (this.currentPositionInFetchedRows > this.rows.size()) {
            this.afterLast();
        }
        else if (this.currentPositionInFetchedRows < this.rows.size()) {
            final Row row = this.rows.get(this.currentPositionInFetchedRows);
            return row.setMetadata(this.metadata);
        }
        return null;
    }
    
    @Override
    public void remove() {
        this.rows.remove(this.getPosition());
    }
    
    @Override
    public void setCurrentRow(final int newIndex) {
        this.currentPositionInFetchedRows = newIndex;
    }
    
    @Override
    public int size() {
        return this.rows.size();
    }
    
    @Override
    public boolean wasEmpty() {
        return this.rows != null && this.rows.size() == 0;
    }
}
