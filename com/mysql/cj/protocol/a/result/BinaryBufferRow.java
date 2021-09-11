
package com.mysql.cj.protocol.a.result;

import com.mysql.cj.exceptions.WrongArgumentException;
import com.mysql.cj.result.ValueFactory;
import com.mysql.cj.result.Row;
import com.mysql.cj.exceptions.ExceptionFactory;
import com.mysql.cj.Messages;
import com.mysql.cj.protocol.a.NativeConstants;
import com.mysql.cj.protocol.a.NativeUtils;
import com.mysql.cj.protocol.ValueDecoder;
import com.mysql.cj.exceptions.ExceptionInterceptor;
import com.mysql.cj.protocol.ColumnDefinition;
import com.mysql.cj.protocol.a.NativePacketPayload;

public class BinaryBufferRow extends AbstractBufferRow
{
    private int preNullBitmaskHomePosition;
    private boolean[] isNull;
    
    public BinaryBufferRow(final NativePacketPayload buf, final ColumnDefinition cd, final ExceptionInterceptor exceptionInterceptor, final ValueDecoder valueDecoder) {
        super(exceptionInterceptor);
        this.preNullBitmaskHomePosition = 0;
        this.rowFromServer = buf;
        this.homePosition = this.rowFromServer.getPosition();
        this.preNullBitmaskHomePosition = this.homePosition;
        this.valueDecoder = valueDecoder;
        if (cd.getFields() != null) {
            this.setMetadata(cd);
        }
    }
    
    @Override
    public boolean isBinaryEncoded() {
        return true;
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
                this.lastRequestedPos = this.homePosition;
            }
            this.rowFromServer.setPosition(this.lastRequestedPos);
        }
        else {
            this.rowFromServer.setPosition(this.homePosition);
        }
        for (int i = startingIndex; i < index; ++i) {
            if (!this.isNull[i]) {
                final int type = this.metadata.getFields()[i].getMysqlTypeId();
                if (type != 6) {
                    final int length = NativeUtils.getBinaryEncodedLength(this.metadata.getFields()[i].getMysqlTypeId());
                    if (length == 0) {
                        this.rowFromServer.skipBytes(NativeConstants.StringSelfDataType.STRING_LENENC);
                    }
                    else {
                        if (length == -1) {
                            throw ExceptionFactory.createException(Messages.getString("MysqlIO.97", new Object[] { type, i + 1, this.metadata.getFields().length }), this.exceptionInterceptor);
                        }
                        final int curPosition = this.rowFromServer.getPosition();
                        this.rowFromServer.setPosition(curPosition + length);
                    }
                }
            }
        }
        this.lastRequestedIndex = index;
        return this.lastRequestedPos = this.rowFromServer.getPosition();
    }
    
    @Override
    public byte[] getBytes(final int index) {
        this.findAndSeekToOffset(index);
        if (this.getNull(index)) {
            return null;
        }
        final int type = this.metadata.getFields()[index].getMysqlTypeId();
        switch (type) {
            case 6: {
                return null;
            }
            case 1: {
                return this.rowFromServer.readBytes(NativeConstants.StringLengthDataType.STRING_FIXED, 1);
            }
            default: {
                final int length = NativeUtils.getBinaryEncodedLength(type);
                if (length == 0) {
                    return this.rowFromServer.readBytes(NativeConstants.StringSelfDataType.STRING_LENENC);
                }
                if (length == -1) {
                    throw ExceptionFactory.createException(Messages.getString("MysqlIO.97", new Object[] { type, index + 1, this.metadata.getFields().length }), this.exceptionInterceptor);
                }
                return this.rowFromServer.readBytes(NativeConstants.StringLengthDataType.STRING_FIXED, length);
            }
        }
    }
    
    @Override
    public boolean getNull(final int columnIndex) {
        return this.wasNull = this.isNull[columnIndex];
    }
    
    @Override
    public Row setMetadata(final ColumnDefinition f) {
        super.setMetadata(f);
        this.setupIsNullBitmask();
        return this;
    }
    
    private void setupIsNullBitmask() {
        if (this.isNull != null) {
            return;
        }
        this.rowFromServer.setPosition(this.preNullBitmaskHomePosition);
        final int len = this.metadata.getFields().length;
        final int nullCount = (len + 9) / 8;
        final byte[] nullBitMask = this.rowFromServer.readBytes(NativeConstants.StringLengthDataType.STRING_FIXED, nullCount);
        this.homePosition = this.rowFromServer.getPosition();
        this.isNull = new boolean[len];
        int nullMaskPos = 0;
        int bit = 4;
        for (int i = 0; i < len; ++i) {
            this.isNull[i] = ((nullBitMask[nullMaskPos] & bit) != 0x0);
            if (((bit <<= 1) & 0xFF) == 0x0) {
                bit = 1;
                ++nullMaskPos;
            }
        }
    }
    
    @Override
    public <T> T getValue(final int columnIndex, final ValueFactory<T> vf) {
        this.findAndSeekToOffset(columnIndex);
        final int type = this.metadata.getFields()[columnIndex].getMysqlTypeId();
        int length = NativeUtils.getBinaryEncodedLength(type);
        if (!this.getNull(columnIndex)) {
            if (length == 0) {
                length = (int)this.rowFromServer.readInteger(NativeConstants.IntegerDataType.INT_LENENC);
            }
            else if (length == -1) {
                throw ExceptionFactory.createException(Messages.getString("MysqlIO.97", new Object[] { type, columnIndex + 1, this.metadata.getFields().length }), this.exceptionInterceptor);
            }
        }
        return this.getValueFromBytes(columnIndex, this.rowFromServer.getByteBuffer(), this.rowFromServer.getPosition(), length, vf);
    }
    
    @Override
    public void setBytes(final int columnIndex, final byte[] value) {
        byte[] backup = null;
        int backupLength = 0;
        if (columnIndex + 1 < this.metadata.getFields().length) {
            this.findAndSeekToOffset(columnIndex + 1);
            backupLength = this.rowFromServer.getPayloadLength() - this.rowFromServer.getPosition();
            backup = new byte[backupLength];
            System.arraycopy(this.rowFromServer.getByteBuffer(), this.rowFromServer.getPosition(), backup, 0, backupLength);
        }
        this.findAndSeekToOffset(columnIndex);
        this.rowFromServer.setPayloadLength(this.rowFromServer.getPosition());
        if (value == null) {
            this.metadata.getFields()[columnIndex].setMysqlTypeId(6);
        }
        else {
            final int type = this.metadata.getFields()[columnIndex].getMysqlTypeId();
            final int length = NativeUtils.getBinaryEncodedLength(type);
            if (length == 0) {
                this.rowFromServer.writeBytes(NativeConstants.StringSelfDataType.STRING_LENENC, value);
            }
            else {
                if (length == -1) {
                    throw ExceptionFactory.createException(Messages.getString("MysqlIO.97", new Object[] { type, columnIndex + 1, this.metadata.getFields().length }), this.exceptionInterceptor);
                }
                if (length != value.length) {
                    throw ExceptionFactory.createException(WrongArgumentException.class, "Value length doesn't match the expected one for type " + type, this.exceptionInterceptor);
                }
                this.rowFromServer.writeBytes(NativeConstants.StringLengthDataType.STRING_FIXED, value);
            }
        }
        if (backup != null) {
            this.rowFromServer.writeBytes(NativeConstants.StringLengthDataType.STRING_FIXED, backup);
        }
    }
}
