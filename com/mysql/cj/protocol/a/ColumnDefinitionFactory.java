
package com.mysql.cj.protocol.a;

import com.mysql.cj.protocol.Message;
import com.mysql.cj.result.DefaultColumnDefinition;
import com.mysql.cj.result.Field;
import com.mysql.cj.protocol.ColumnDefinition;
import com.mysql.cj.protocol.ProtocolEntityFactory;

public class ColumnDefinitionFactory implements ProtocolEntityFactory<ColumnDefinition, NativePacketPayload>
{
    protected long columnCount;
    protected ColumnDefinition columnDefinitionFromCache;
    
    public ColumnDefinitionFactory(final long columnCount, final ColumnDefinition columnDefinitionFromCache) {
        this.columnCount = columnCount;
        this.columnDefinitionFromCache = columnDefinitionFromCache;
    }
    
    public long getColumnCount() {
        return this.columnCount;
    }
    
    public ColumnDefinition getColumnDefinitionFromCache() {
        return this.columnDefinitionFromCache;
    }
    
    @Override
    public ColumnDefinition createFromMessage(final NativePacketPayload packetPayload) {
        return null;
    }
    
    public boolean mergeColumnDefinitions() {
        return false;
    }
    
    public ColumnDefinition createFromFields(final Field[] fields) {
        return new DefaultColumnDefinition(fields);
    }
}
