
package com.mysql.cj.protocol.a;

import com.mysql.cj.result.DefaultColumnDefinition;
import com.mysql.cj.exceptions.ExceptionFactory;
import com.mysql.cj.exceptions.WrongArgumentException;
import com.mysql.cj.result.Field;
import com.mysql.cj.protocol.ColumnDefinition;
import com.mysql.cj.protocol.ProtocolEntityFactory;

public class MergingColumnDefinitionFactory extends ColumnDefinitionFactory implements ProtocolEntityFactory<ColumnDefinition, NativePacketPayload>
{
    public MergingColumnDefinitionFactory(final long columnCount, final ColumnDefinition columnDefinitionFromCache) {
        super(columnCount, columnDefinitionFromCache);
    }
    
    @Override
    public boolean mergeColumnDefinitions() {
        return true;
    }
    
    @Override
    public ColumnDefinition createFromFields(final Field[] fields) {
        if (this.columnDefinitionFromCache != null) {
            if (fields.length != this.columnCount) {
                throw ExceptionFactory.createException(WrongArgumentException.class, "Wrong number of ColumnDefinition fields.");
            }
            final Field[] f = this.columnDefinitionFromCache.getFields();
            for (int i = 0; i < fields.length; ++i) {
                fields[i].setFlags(f[i].getFlags());
            }
        }
        return new DefaultColumnDefinition(fields);
    }
}
