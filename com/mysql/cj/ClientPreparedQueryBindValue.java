
package com.mysql.cj;

import java.io.InputStream;

public class ClientPreparedQueryBindValue implements BindValue
{
    protected boolean isNull;
    protected boolean isStream;
    protected MysqlType parameterType;
    public Object value;
    protected long streamLength;
    protected boolean isSet;
    
    public ClientPreparedQueryBindValue() {
        this.isStream = false;
        this.parameterType = MysqlType.NULL;
        this.isSet = false;
    }
    
    @Override
    public ClientPreparedQueryBindValue clone() {
        return new ClientPreparedQueryBindValue(this);
    }
    
    protected ClientPreparedQueryBindValue(final ClientPreparedQueryBindValue copyMe) {
        this.isStream = false;
        this.parameterType = MysqlType.NULL;
        this.isSet = false;
        this.isNull = copyMe.isNull;
        this.isStream = copyMe.isStream;
        this.parameterType = copyMe.parameterType;
        if (copyMe.value != null && copyMe.value instanceof byte[]) {
            this.value = new byte[((byte[])copyMe.value).length];
            System.arraycopy(copyMe.value, 0, this.value, 0, ((byte[])copyMe.value).length);
        }
        else {
            this.value = copyMe.value;
        }
        this.streamLength = copyMe.streamLength;
        this.isSet = copyMe.isSet;
    }
    
    @Override
    public void reset() {
        this.isNull = false;
        this.isStream = false;
        this.parameterType = MysqlType.NULL;
        this.value = null;
        this.streamLength = 0L;
        this.isSet = false;
    }
    
    @Override
    public boolean isNull() {
        return this.isNull;
    }
    
    @Override
    public void setNull(final boolean isNull) {
        this.isNull = isNull;
        if (isNull) {
            this.parameterType = MysqlType.NULL;
        }
        this.isSet = true;
    }
    
    @Override
    public boolean isStream() {
        return this.isStream;
    }
    
    @Override
    public void setIsStream(final boolean isStream) {
        this.isStream = isStream;
    }
    
    @Override
    public MysqlType getMysqlType() {
        return this.parameterType;
    }
    
    @Override
    public void setMysqlType(final MysqlType type) {
        this.parameterType = type;
    }
    
    @Override
    public byte[] getByteValue() {
        if (this.value instanceof byte[]) {
            return (byte[])this.value;
        }
        return null;
    }
    
    @Override
    public void setByteValue(final byte[] parameterValue) {
        this.isNull = false;
        this.isStream = false;
        this.value = parameterValue;
        this.streamLength = 0L;
        this.isSet = true;
    }
    
    @Override
    public InputStream getStreamValue() {
        if (this.value instanceof InputStream) {
            return (InputStream)this.value;
        }
        return null;
    }
    
    @Override
    public void setStreamValue(final InputStream parameterStream, final long streamLength) {
        this.value = parameterStream;
        this.streamLength = streamLength;
        this.isSet = true;
    }
    
    @Override
    public long getStreamLength() {
        return this.streamLength;
    }
    
    @Override
    public void setStreamLength(final long length) {
        this.streamLength = length;
    }
    
    @Override
    public boolean isSet() {
        return this.isSet;
    }
}
