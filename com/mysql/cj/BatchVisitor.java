
package com.mysql.cj;

public interface BatchVisitor
{
    BatchVisitor increment();
    
    BatchVisitor decrement();
    
    BatchVisitor append(final byte[] p0);
    
    BatchVisitor merge(final byte[] p0, final byte[] p1);
    
    BatchVisitor mergeWithLast(final byte[] p0);
}
