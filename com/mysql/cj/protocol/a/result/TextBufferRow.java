
package com.mysql.cj.protocol.a.result;

import com.mysql.cj.result.ValueFactory;
import com.mysql.cj.result.Row;
import com.mysql.cj.protocol.a.NativeConstants;
import com.mysql.cj.protocol.ValueDecoder;
import com.mysql.cj.exceptions.ExceptionInterceptor;
import com.mysql.cj.protocol.ColumnDefinition;
import com.mysql.cj.protocol.a.NativePacketPayload;

public class TextBufferRow extends AbstractBufferRow
{
    public TextBufferRow(final NativePacketPayload buf, final ColumnDefinition cd, final ExceptionInterceptor exceptionInterceptor, final ValueDecoder valueDecoder) {
        super(exceptionInterceptor);
        this.rowFromServer = buf;
        this.homePosition = this.rowFromServer.getPosition();
        this.valueDecoder = valueDecoder;
        if (cd.getFields() != null) {
            this.setMetadata(cd);
        }
    }
    
    protected int findAndSeekToOffset(final int index) {
        if (index == 0) {
            this.lastRequestedIndex = 0;
            this.lastRequestedPos = this.homePosition;
            this.rowFromServer.setPosition(this.homePosition);
            return 0;
        }
        if (index == this.lastRequestedIndex) {
            this.rowFromServer.setPosition(this.lastRequestedPos);
            return this.lastRequestedPos;
        }
        int startingIndex = 0;
        if (index > this.lastRequestedIndex) {
            if (this.lastRequestedIndex >= 0) {
                startingIndex = this.lastRequestedIndex;
            }
            else {
                startingIndex = 0;
            }
            this.rowFromServer.setPosition(this.lastRequestedPos);
        }
        else {
            this.rowFromServer.setPosition(this.homePosition);
        }
        for (int i = startingIndex; i < index; ++i) {
            this.rowFromServer.skipBytes(NativeConstants.StringSelfDataType.STRING_LENENC);
        }
        this.lastRequestedIndex = index;
        return this.lastRequestedPos = this.rowFromServer.getPosition();
    }
    
    @Override
    public byte[] getBytes(final int index) {
        if (this.getNull(index)) {
            return null;
        }
        this.findAndSeekToOffset(index);
        return this.rowFromServer.readBytes(NativeConstants.StringSelfDataType.STRING_LENENC);
    }
    
    @Override
    public boolean getNull(final int columnIndex) {
        this.findAndSeekToOffset(columnIndex);
        return this.wasNull = (this.rowFromServer.readInteger(NativeConstants.IntegerDataType.INT_LENENC) == -1L);
    }
    
    @Override
    public Row setMetadata(final ColumnDefinition f) {
        super.setMetadata(f);
        return this;
    }
    
    @Override
    public <T> T getValue(final int columnIndex, final ValueFactory<T> vf) {
        this.findAndSeekToOffset(columnIndex);
        final int length = (int)this.rowFromServer.readInteger(NativeConstants.IntegerDataType.INT_LENENC);
        return this.getValueFromBytes(columnIndex, this.rowFromServer.getByteBuffer(), this.rowFromServer.getPosition(), length, vf);
    }
}
