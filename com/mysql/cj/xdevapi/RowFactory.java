
package com.mysql.cj.xdevapi;

import com.mysql.cj.protocol.ProtocolEntity;
import java.util.TimeZone;
import com.mysql.cj.protocol.ColumnDefinition;
import com.mysql.cj.protocol.x.XMessage;
import com.mysql.cj.protocol.ProtocolEntityFactory;

public class RowFactory implements ProtocolEntityFactory<Row, XMessage>
{
    private ColumnDefinition metadata;
    private TimeZone defaultTimeZone;
    
    public RowFactory(final ColumnDefinition metadata, final TimeZone defaultTimeZone) {
        this.metadata = metadata;
        this.defaultTimeZone = defaultTimeZone;
    }
    
    @Override
    public Row createFromProtocolEntity(final ProtocolEntity internalRow) {
        return new RowImpl((com.mysql.cj.result.Row)internalRow, this.metadata, this.defaultTimeZone);
    }
}
