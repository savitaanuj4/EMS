
package com.mysql.cj.xdevapi;

import java.util.stream.Collector;
import java.util.stream.Collectors;
import com.mysql.cj.result.Field;
import java.util.function.Function;
import java.util.Arrays;
import java.util.List;
import com.mysql.cj.protocol.ProtocolEntityFactory;
import com.mysql.cj.protocol.x.StatementExecuteOk;
import java.util.function.Supplier;
import com.mysql.cj.result.RowList;
import java.util.TimeZone;
import com.mysql.cj.protocol.ColumnDefinition;

public class RowResultImpl extends AbstractDataResult<Row> implements RowResult
{
    private ColumnDefinition metadata;
    
    public RowResultImpl(final ColumnDefinition metadata, final TimeZone defaultTimeZone, final RowList rows, final Supplier<StatementExecuteOk> completer) {
        super(rows, completer, new RowFactory(metadata, defaultTimeZone));
        this.metadata = metadata;
    }
    
    @Override
    public int getColumnCount() {
        return this.metadata.getFields().length;
    }
    
    @Override
    public List<Column> getColumns() {
        return Arrays.stream(this.metadata.getFields()).map((Function<? super Field, ?>)ColumnImpl::new).collect((Collector<? super Object, ?, List<Column>>)Collectors.toList());
    }
    
    @Override
    public List<String> getColumnNames() {
        return Arrays.stream(this.metadata.getFields()).map((Function<? super Field, ?>)Field::getColumnLabel).collect((Collector<? super Object, ?, List<String>>)Collectors.toList());
    }
}
