
package com.mysql.cj.xdevapi;

import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.Arrays;

public class TableFilterParams extends AbstractFilterParams
{
    public TableFilterParams(final String schemaName, final String collectionName) {
        super(schemaName, collectionName, true);
    }
    
    @Override
    public void setFields(final String... projection) {
        this.projection = projection;
        this.fields = new ExprParser(Arrays.stream(projection).collect(Collectors.joining(", ")), true).parseTableSelectProjection();
    }
}
