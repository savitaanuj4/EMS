
package com.mysql.cj.protocol.a.result;

import com.mysql.cj.result.ValueFactory;
import com.mysql.cj.protocol.a.MysqlBinaryValueDecoder;
import com.mysql.cj.protocol.a.MysqlTextValueDecoder;
import com.mysql.cj.protocol.ValueDecoder;
import com.mysql.cj.exceptions.ExceptionInterceptor;
import com.mysql.cj.protocol.result.AbstractResultsetRow;

public class ByteArrayRow extends AbstractResultsetRow
{
    byte[][] internalRowData;
    
    public ByteArrayRow(final byte[][] internalRowData, final ExceptionInterceptor exceptionInterceptor, final ValueDecoder valueDecoder) {
        super(exceptionInterceptor);
        this.internalRowData = internalRowData;
        this.valueDecoder = valueDecoder;
    }
    
    public ByteArrayRow(final byte[][] internalRowData, final ExceptionInterceptor exceptionInterceptor) {
        super(exceptionInterceptor);
        this.internalRowData = internalRowData;
        this.valueDecoder = new MysqlTextValueDecoder();
    }
    
    @Override
    public boolean isBinaryEncoded() {
        return this.valueDecoder instanceof MysqlBinaryValueDecoder;
    }
    
    @Override
    public byte[] getBytes(final int index) {
        if (this.getNull(index)) {
            return null;
        }
        return this.internalRowData[index];
    }
    
    @Override
    public void setBytes(final int index, final byte[] value) {
        this.internalRowData[index] = value;
    }
    
    @Override
    public boolean getNull(final int columnIndex) {
        return this.wasNull = (this.internalRowData[columnIndex] == null);
    }
    
    @Override
    public <T> T getValue(final int columnIndex, final ValueFactory<T> vf) {
        final byte[] columnData = this.internalRowData[columnIndex];
        final int length = (columnData == null) ? 0 : columnData.length;
        return this.getValueFromBytes(columnIndex, columnData, 0, length, vf);
    }
}
