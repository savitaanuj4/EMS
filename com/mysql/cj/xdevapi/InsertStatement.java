
package com.mysql.cj.xdevapi;

import java.util.Arrays;
import java.util.List;

public interface InsertStatement extends Statement<InsertStatement, InsertResult>
{
    InsertStatement values(final List<Object> p0);
    
    default InsertStatement values(final Object... values) {
        return this.values(Arrays.asList(values));
    }
}
