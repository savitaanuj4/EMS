
package com.mysql.cj.util;

public class DataTypeUtil
{
    public static long bitToLong(final byte[] bytes, final int offset, final int length) {
        long valueAsLong = 0L;
        for (int i = 0; i < length; ++i) {
            valueAsLong = (valueAsLong << 8 | (long)(bytes[offset + i] & 0xFF));
        }
        return valueAsLong;
    }
}
