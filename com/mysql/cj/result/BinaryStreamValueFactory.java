
package com.mysql.cj.result;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class BinaryStreamValueFactory extends DefaultValueFactory<InputStream>
{
    @Override
    public InputStream createFromBytes(final byte[] bytes, final int offset, final int length) {
        return new ByteArrayInputStream(bytes, offset, length);
    }
    
    @Override
    public String getTargetTypeName() {
        return InputStream.class.getName();
    }
}
