
package com.mysql.cj.protocol;

import com.mysql.cj.result.ValueFactory;

public interface ValueDecoder
{
     <T> T decodeDate(final byte[] p0, final int p1, final int p2, final ValueFactory<T> p3);
    
     <T> T decodeTime(final byte[] p0, final int p1, final int p2, final ValueFactory<T> p3);
    
     <T> T decodeTimestamp(final byte[] p0, final int p1, final int p2, final ValueFactory<T> p3);
    
     <T> T decodeInt1(final byte[] p0, final int p1, final int p2, final ValueFactory<T> p3);
    
     <T> T decodeUInt1(final byte[] p0, final int p1, final int p2, final ValueFactory<T> p3);
    
     <T> T decodeInt2(final byte[] p0, final int p1, final int p2, final ValueFactory<T> p3);
    
     <T> T decodeUInt2(final byte[] p0, final int p1, final int p2, final ValueFactory<T> p3);
    
     <T> T decodeInt4(final byte[] p0, final int p1, final int p2, final ValueFactory<T> p3);
    
     <T> T decodeUInt4(final byte[] p0, final int p1, final int p2, final ValueFactory<T> p3);
    
     <T> T decodeInt8(final byte[] p0, final int p1, final int p2, final ValueFactory<T> p3);
    
     <T> T decodeUInt8(final byte[] p0, final int p1, final int p2, final ValueFactory<T> p3);
    
     <T> T decodeFloat(final byte[] p0, final int p1, final int p2, final ValueFactory<T> p3);
    
     <T> T decodeDouble(final byte[] p0, final int p1, final int p2, final ValueFactory<T> p3);
    
     <T> T decodeDecimal(final byte[] p0, final int p1, final int p2, final ValueFactory<T> p3);
    
     <T> T decodeByteArray(final byte[] p0, final int p1, final int p2, final ValueFactory<T> p3);
    
     <T> T decodeBit(final byte[] p0, final int p1, final int p2, final ValueFactory<T> p3);
    
     <T> T decodeSet(final byte[] p0, final int p1, final int p2, final ValueFactory<T> p3);
}
