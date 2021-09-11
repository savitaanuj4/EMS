
package com.mysql.cj.protocol;

import java.io.EOFException;
import com.mysql.cj.Messages;
import java.io.IOException;
import java.io.InputStream;
import java.io.FilterInputStream;

public class FullReadInputStream extends FilterInputStream
{
    public FullReadInputStream(final InputStream underlyingStream) {
        super(underlyingStream);
    }
    
    public InputStream getUnderlyingStream() {
        return this.in;
    }
    
    public int readFully(final byte[] b) throws IOException {
        return this.readFully(b, 0, b.length);
    }
    
    public int readFully(final byte[] b, final int off, final int len) throws IOException {
        if (len < 0) {
            throw new IndexOutOfBoundsException();
        }
        int n;
        int count;
        for (n = 0; n < len; n += count) {
            count = this.read(b, off + n, len - n);
            if (count < 0) {
                throw new EOFException(Messages.getString("MysqlIO.EOF", new Object[] { len, n }));
            }
        }
        return n;
    }
    
    public long skipFully(final long len) throws IOException {
        if (len < 0L) {
            throw new IOException(Messages.getString("MysqlIO.105"));
        }
        long n;
        long count;
        for (n = 0L; n < len; n += count) {
            count = this.skip(len - n);
            if (count < 0L) {
                throw new EOFException(Messages.getString("MysqlIO.EOF", new Object[] { len, n }));
            }
        }
        return n;
    }
    
    public int skipLengthEncodedInteger() throws IOException {
        final int sw = this.read() & 0xFF;
        switch (sw) {
            case 252: {
                return (int)this.skipFully(2L) + 1;
            }
            case 253: {
                return (int)this.skipFully(3L) + 1;
            }
            case 254: {
                return (int)this.skipFully(8L) + 1;
            }
            default: {
                return 1;
            }
        }
    }
}
