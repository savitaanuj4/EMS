
package com.mysql.cj;

import java.util.Iterator;
import com.mysql.cj.util.StringUtils;
import java.util.LinkedList;

public class AppendingBatchVisitor implements BatchVisitor
{
    LinkedList<byte[]> statementComponents;
    
    public AppendingBatchVisitor() {
        this.statementComponents = new LinkedList<byte[]>();
    }
    
    @Override
    public BatchVisitor append(final byte[] values) {
        this.statementComponents.addLast(values);
        return this;
    }
    
    @Override
    public BatchVisitor increment() {
        return this;
    }
    
    @Override
    public BatchVisitor decrement() {
        this.statementComponents.removeLast();
        return this;
    }
    
    @Override
    public BatchVisitor merge(final byte[] front, final byte[] back) {
        final int mergedLength = front.length + back.length;
        final byte[] merged = new byte[mergedLength];
        System.arraycopy(front, 0, merged, 0, front.length);
        System.arraycopy(back, 0, merged, front.length, back.length);
        this.statementComponents.addLast(merged);
        return this;
    }
    
    @Override
    public BatchVisitor mergeWithLast(final byte[] values) {
        if (this.statementComponents.isEmpty()) {
            return this.append(values);
        }
        return this.merge(this.statementComponents.removeLast(), values);
    }
    
    public byte[][] getStaticSqlStrings() {
        final byte[][] asBytes = new byte[this.statementComponents.size()][];
        this.statementComponents.toArray(asBytes);
        return asBytes;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        for (final byte[] comp : this.statementComponents) {
            sb.append(StringUtils.toString(comp));
        }
        return sb.toString();
    }
}
