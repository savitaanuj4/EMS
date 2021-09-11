
package com.mysql.cj.protocol.x;

import java.math.BigDecimal;
import com.mysql.cj.exceptions.AssertionFailedException;
import java.nio.CharBuffer;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.io.IOException;
import com.mysql.cj.exceptions.DataReadException;
import com.google.protobuf.CodedInputStream;
import com.mysql.cj.result.ValueFactory;
import com.mysql.cj.protocol.ValueDecoder;

public class XProtocolDecoder implements ValueDecoder
{
    public static XProtocolDecoder instance;
    
    @Override
    public <T> T decodeDate(final byte[] bytes, final int offset, final int length, final ValueFactory<T> vf) {
        return (T)this.decodeTimestamp(bytes, offset, length, (ValueFactory<Object>)vf);
    }
    
    @Override
    public <T> T decodeTime(final byte[] bytes, final int offset, final int length, final ValueFactory<T> vf) {
        try {
            final CodedInputStream inputStream = CodedInputStream.newInstance(bytes, offset, length);
            final boolean negative = inputStream.readRawByte() > 0;
            int hours = 0;
            int minutes = 0;
            int seconds = 0;
            int nanos = 0;
            if (!inputStream.isAtEnd()) {
                hours = (int)inputStream.readInt64();
                if (!inputStream.isAtEnd()) {
                    minutes = (int)inputStream.readInt64();
                    if (!inputStream.isAtEnd()) {
                        seconds = (int)inputStream.readInt64();
                        if (!inputStream.isAtEnd()) {
                            nanos = 1000 * (int)inputStream.readInt64();
                        }
                    }
                }
            }
            return vf.createFromTime(negative ? (-1 * hours) : hours, minutes, seconds, nanos);
        }
        catch (IOException e) {
            throw new DataReadException(e);
        }
    }
    
    @Override
    public <T> T decodeTimestamp(final byte[] bytes, final int offset, final int length, final ValueFactory<T> vf) {
        try {
            final CodedInputStream inputStream = CodedInputStream.newInstance(bytes, offset, length);
            final int year = (int)inputStream.readUInt64();
            final int month = (int)inputStream.readUInt64();
            final int day = (int)inputStream.readUInt64();
            if (inputStream.getBytesUntilLimit() > 0) {
                int hours = 0;
                int minutes = 0;
                int seconds = 0;
                int nanos = 0;
                if (!inputStream.isAtEnd()) {
                    hours = (int)inputStream.readInt64();
                    if (!inputStream.isAtEnd()) {
                        minutes = (int)inputStream.readInt64();
                        if (!inputStream.isAtEnd()) {
                            seconds = (int)inputStream.readInt64();
                            if (!inputStream.isAtEnd()) {
                                nanos = 1000 * (int)inputStream.readInt64();
                            }
                        }
                    }
                }
                return vf.createFromTimestamp(year, month, day, hours, minutes, seconds, nanos);
            }
            return vf.createFromDate(year, month, day);
        }
        catch (IOException e) {
            throw new DataReadException(e);
        }
    }
    
    @Override
    public <T> T decodeInt1(final byte[] bytes, final int offset, final int length, final ValueFactory<T> vf) {
        return null;
    }
    
    @Override
    public <T> T decodeUInt1(final byte[] bytes, final int offset, final int length, final ValueFactory<T> vf) {
        return null;
    }
    
    @Override
    public <T> T decodeInt2(final byte[] bytes, final int offset, final int length, final ValueFactory<T> vf) {
        return null;
    }
    
    @Override
    public <T> T decodeUInt2(final byte[] bytes, final int offset, final int length, final ValueFactory<T> vf) {
        return null;
    }
    
    @Override
    public <T> T decodeInt4(final byte[] bytes, final int offset, final int length, final ValueFactory<T> vf) {
        return null;
    }
    
    @Override
    public <T> T decodeUInt4(final byte[] bytes, final int offset, final int length, final ValueFactory<T> vf) {
        return null;
    }
    
    @Override
    public <T> T decodeInt8(final byte[] bytes, final int offset, final int length, final ValueFactory<T> vf) {
        try {
            return vf.createFromLong(CodedInputStream.newInstance(bytes, offset, length).readSInt64());
        }
        catch (IOException e) {
            throw new DataReadException(e);
        }
    }
    
    @Override
    public <T> T decodeUInt8(final byte[] bytes, final int offset, final int length, final ValueFactory<T> vf) {
        try {
            final BigInteger v = new BigInteger(ByteBuffer.allocate(9).put((byte)0).putLong(CodedInputStream.newInstance(bytes, offset, length).readUInt64()).array());
            return vf.createFromBigInteger(v);
        }
        catch (IOException e) {
            throw new DataReadException(e);
        }
    }
    
    @Override
    public <T> T decodeFloat(final byte[] bytes, final int offset, final int length, final ValueFactory<T> vf) {
        try {
            return vf.createFromDouble(CodedInputStream.newInstance(bytes, offset, length).readFloat());
        }
        catch (IOException e) {
            throw new DataReadException(e);
        }
    }
    
    @Override
    public <T> T decodeDouble(final byte[] bytes, final int offset, final int length, final ValueFactory<T> vf) {
        try {
            return vf.createFromDouble(CodedInputStream.newInstance(bytes, offset, length).readDouble());
        }
        catch (IOException e) {
            throw new DataReadException(e);
        }
    }
    
    @Override
    public <T> T decodeDecimal(final byte[] bytes, final int offset, final int length, final ValueFactory<T> vf) {
        try {
            final CodedInputStream inputStream = CodedInputStream.newInstance(bytes, offset, length);
            final byte scale = inputStream.readRawByte();
            final CharBuffer unscaledString = CharBuffer.allocate(2 * inputStream.getBytesUntilLimit());
            unscaledString.position(1);
            byte sign = 0;
            while (true) {
                final int b = 0xFF & inputStream.readRawByte();
                if (b >> 4 > 9) {
                    sign = (byte)(b >> 4);
                    break;
                }
                unscaledString.append((char)((b >> 4) + 48));
                if ((b & 0xF) > 9) {
                    sign = (byte)(b & 0xF);
                    break;
                }
                unscaledString.append((char)((b & 0xF) + 48));
            }
            if (inputStream.getBytesUntilLimit() > 0) {
                throw AssertionFailedException.shouldNotHappen("Did not read all bytes while decoding decimal. Bytes left: " + inputStream.getBytesUntilLimit());
            }
            switch (sign) {
                case 10:
                case 12:
                case 14:
                case 15: {
                    unscaledString.put(0, '+');
                    break;
                }
                case 11:
                case 13: {
                    unscaledString.put(0, '-');
                    break;
                }
            }
            final int characters = unscaledString.position();
            unscaledString.clear();
            final BigInteger unscaled = new BigInteger(unscaledString.subSequence(0, characters).toString());
            return vf.createFromBigDecimal(new BigDecimal(unscaled, scale));
        }
        catch (IOException e) {
            throw new DataReadException(e);
        }
    }
    
    @Override
    public <T> T decodeByteArray(final byte[] bytes, final int offset, final int length, final ValueFactory<T> vf) {
        try {
            final CodedInputStream inputStream = CodedInputStream.newInstance(bytes, offset, length);
            int size = inputStream.getBytesUntilLimit();
            --size;
            return vf.createFromBytes(inputStream.readRawBytes(size), 0, size);
        }
        catch (IOException e) {
            throw new DataReadException(e);
        }
    }
    
    @Override
    public <T> T decodeBit(final byte[] bytes, final int offset, final int length, final ValueFactory<T> vf) {
        try {
            final byte[] buf = ByteBuffer.allocate(9).put((byte)0).putLong(CodedInputStream.newInstance(bytes, offset, length).readUInt64()).array();
            return vf.createFromBit(buf, 0, 9);
        }
        catch (IOException e) {
            throw new DataReadException(e);
        }
    }
    
    @Override
    public <T> T decodeSet(final byte[] bytes, final int offset, final int length, final ValueFactory<T> vf) {
        try {
            final CodedInputStream inputStream = CodedInputStream.newInstance(bytes, offset, length);
            final StringBuilder vals = new StringBuilder();
            while (inputStream.getBytesUntilLimit() > 0) {
                if (vals.length() > 0) {
                    vals.append(",");
                }
                final long valLen = inputStream.readUInt64();
                vals.append(new String(inputStream.readRawBytes((int)valLen)));
            }
            final byte[] buf = vals.toString().getBytes();
            return vf.createFromBytes(buf, 0, buf.length);
        }
        catch (IOException e) {
            throw new DataReadException(e);
        }
    }
    
    static {
        XProtocolDecoder.instance = new XProtocolDecoder();
    }
}
