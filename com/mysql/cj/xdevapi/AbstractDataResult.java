
package com.mysql.cj.xdevapi;

import java.util.stream.Collector;
import java.util.stream.Collectors;
import com.mysql.cj.result.BufferedRowList;
import java.util.Collections;
import java.util.ArrayList;
import com.mysql.cj.protocol.ProtocolEntity;
import java.util.NoSuchElementException;
import com.mysql.cj.result.Row;
import com.mysql.cj.exceptions.WrongArgumentException;
import java.util.List;
import com.mysql.cj.protocol.x.XMessage;
import com.mysql.cj.protocol.ProtocolEntityFactory;
import com.mysql.cj.protocol.x.StatementExecuteOk;
import java.util.function.Supplier;
import com.mysql.cj.result.RowList;
import java.util.Iterator;
import com.mysql.cj.protocol.ResultStreamer;

public abstract class AbstractDataResult<T> implements ResultStreamer, Iterator<T>
{
    protected int position;
    protected int count;
    protected RowList rows;
    protected Supplier<StatementExecuteOk> completer;
    protected StatementExecuteOk ok;
    protected ProtocolEntityFactory<T, XMessage> rowToData;
    protected List<T> all;
    
    public AbstractDataResult(final RowList rows, final Supplier<StatementExecuteOk> completer, final ProtocolEntityFactory<T, XMessage> rowToData) {
        this.position = -1;
        this.count = -1;
        this.rows = rows;
        this.completer = completer;
        this.rowToData = rowToData;
    }
    
    @Override
    public T next() {
        if (this.all != null) {
            throw new WrongArgumentException("Cannot iterate after fetchAll()");
        }
        final Row r = this.rows.next();
        if (r == null) {
            throw new NoSuchElementException();
        }
        ++this.position;
        return this.rowToData.createFromProtocolEntity(r);
    }
    
    public List<T> fetchAll() {
        if (this.position > -1) {
            throw new WrongArgumentException("Cannot fetchAll() after starting iteration");
        }
        if (this.all == null) {
            this.all = new ArrayList<T>((int)this.count());
            this.rows.forEachRemaining(r -> this.all.add(this.rowToData.createFromProtocolEntity(r)));
            this.all = Collections.unmodifiableList((List<? extends T>)this.all);
        }
        return this.all;
    }
    
    public long count() {
        this.finishStreaming();
        return this.count;
    }
    
    @Override
    public boolean hasNext() {
        return this.rows.hasNext();
    }
    
    public StatementExecuteOk getStatementExecuteOk() {
        this.finishStreaming();
        return this.ok;
    }
    
    @Override
    public void finishStreaming() {
        if (this.ok == null) {
            final BufferedRowList remainingRows = new BufferedRowList(this.rows);
            this.count = 1 + this.position + remainingRows.size();
            this.rows = remainingRows;
            this.ok = this.completer.get();
        }
    }
    
    public int getWarningsCount() {
        return this.getStatementExecuteOk().getWarnings().size();
    }
    
    public Iterator<Warning> getWarnings() {
        return this.getStatementExecuteOk().getWarnings().stream().map(w -> new WarningImpl(w)).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList()).iterator();
    }
}
