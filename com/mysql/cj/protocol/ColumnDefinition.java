
package com.mysql.cj.protocol;

import java.util.Map;
import com.mysql.cj.result.Field;

public interface ColumnDefinition extends ProtocolEntity
{
    Field[] getFields();
    
    void setFields(final Field[] p0);
    
    void buildIndexMapping();
    
    boolean hasBuiltIndexMapping();
    
    Map<String, Integer> getColumnLabelToIndex();
    
    void setColumnLabelToIndex(final Map<String, Integer> p0);
    
    Map<String, Integer> getFullColumnNameToIndex();
    
    void setFullColumnNameToIndex(final Map<String, Integer> p0);
    
    Map<String, Integer> getColumnNameToIndex();
    
    void setColumnNameToIndex(final Map<String, Integer> p0);
    
    Map<String, Integer> getColumnToIndexCache();
    
    void setColumnToIndexCache(final Map<String, Integer> p0);
    
    void initializeFrom(final ColumnDefinition p0);
    
    void exportTo(final ColumnDefinition p0);
    
    int findColumn(final String p0, final boolean p1, final int p2);
    
    boolean hasLargeFields();
}
