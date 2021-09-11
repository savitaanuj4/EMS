
package com.mysql.cj.xdevapi;

import com.mysql.cj.result.ValueFactory;
import com.mysql.cj.result.Row;
import com.mysql.cj.protocol.ProtocolEntity;
import com.mysql.cj.protocol.x.XMessage;
import com.mysql.cj.protocol.ProtocolEntityFactory;

public class DbDocFactory implements ProtocolEntityFactory<DbDoc, XMessage>
{
    @Override
    public DbDoc createFromProtocolEntity(final ProtocolEntity internalRow) {
        return ((Row)internalRow).getValue(0, (ValueFactory<DbDoc>)new DbDocValueFactory());
    }
}
