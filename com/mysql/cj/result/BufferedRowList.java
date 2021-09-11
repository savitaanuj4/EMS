
package com.mysql.cj.result;

import java.util.NoSuchElementException;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import java.util.Spliterators;
import java.util.Iterator;
import java.util.List;

public class BufferedRowList implements RowList
{
    private List<Row> rowList;
    private int position;
    
    public BufferedRowList(final List<Row> rowList) {
        this.position = -1;
        this.rowList = rowList;
    }
    
    public BufferedRowList(final Iterator<Row> ris) {
        this.position = -1;
        this.rowList = StreamSupport.stream(Spliterators.spliteratorUnknownSize((Iterator<?>)ris, 0), false).collect((Collector<? super Object, ?, List<Row>>)Collectors.toList());
    }
    
    @Override
    public Row next() {
        if (this.position + 1 == this.rowList.size()) {
            throw new NoSuchElementException("Can't next() when position=" + this.position + " and size=" + this.rowList.size());
        }
        return this.rowList.get(++this.position);
    }
    
    @Override
    public Row previous() {
        if (this.position < 1) {
            throw new NoSuchElementException("Can't previous() when position=" + this.position);
        }
        final List<Row> rowList = this.rowList;
        final int position = this.position - 1;
        this.position = position;
        return rowList.get(position);
    }
    
    @Override
    public Row get(final int n) {
        if (n < 0 || n >= this.rowList.size()) {
            throw new NoSuchElementException("Can't get(" + n + ") when size=" + this.rowList.size());
        }
        return this.rowList.get(n);
    }
    
    @Override
    public int getPosition() {
        return this.position;
    }
    
    @Override
    public int size() {
        return this.rowList.size();
    }
    
    @Override
    public boolean hasNext() {
        return this.position + 1 < this.rowList.size();
    }
}
