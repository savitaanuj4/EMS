
package com.mysql.cj.xdevapi;

import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.Collections;
import com.mysql.cj.x.protobuf.MysqlxCrud;

public class DocFilterParams extends AbstractFilterParams
{
    public DocFilterParams(final String schemaName, final String collectionName) {
        super(schemaName, collectionName, false);
    }
    
    public void setFields(final Expression docProjection) {
        this.fields = Collections.singletonList(MysqlxCrud.Projection.newBuilder().setSource(new ExprParser(docProjection.getExpressionString(), false).parse()).build());
    }
    
    @Override
    public void setFields(final String... projection) {
        this.fields = new ExprParser(Arrays.stream(projection).collect(Collectors.joining(", ")), false).parseDocumentProjection();
    }
}
