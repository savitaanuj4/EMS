
package com.mysql.cj.result;

import java.util.Comparator;
import java.util.TreeMap;
import java.util.HashMap;
import java.util.Map;
import com.mysql.cj.protocol.ColumnDefinition;

public class DefaultColumnDefinition implements ColumnDefinition
{
    protected Field[] fields;
    private Map<String, Integer> columnLabelToIndex;
    private Map<String, Integer> columnToIndexCache;
    private Map<String, Integer> fullColumnNameToIndex;
    private Map<String, Integer> columnNameToIndex;
    private boolean builtIndexMapping;
    
    public DefaultColumnDefinition() {
        this.columnLabelToIndex = null;
        this.columnToIndexCache = new HashMap<String, Integer>();
        this.fullColumnNameToIndex = null;
        this.columnNameToIndex = null;
        this.builtIndexMapping = false;
    }
    
    public DefaultColumnDefinition(final Field[] fields) {
        this.columnLabelToIndex = null;
        this.columnToIndexCache = new HashMap<String, Integer>();
        this.fullColumnNameToIndex = null;
        this.columnNameToIndex = null;
        this.builtIndexMapping = false;
        this.fields = fields;
    }
    
    @Override
    public Field[] getFields() {
        return this.fields;
    }
    
    @Override
    public void setFields(final Field[] fields) {
        this.fields = fields;
    }
    
    @Override
    public void buildIndexMapping() {
        final int numFields = this.fields.length;
        this.columnLabelToIndex = new TreeMap<String, Integer>(String.CASE_INSENSITIVE_ORDER);
        this.fullColumnNameToIndex = new TreeMap<String, Integer>(String.CASE_INSENSITIVE_ORDER);
        this.columnNameToIndex = new TreeMap<String, Integer>(String.CASE_INSENSITIVE_ORDER);
        for (int i = numFields - 1; i >= 0; --i) {
            final Integer index = i;
            final String columnName = this.fields[i].getOriginalName();
            final String columnLabel = this.fields[i].getName();
            final String fullColumnName = this.fields[i].getFullName();
            if (columnLabel != null) {
                this.columnLabelToIndex.put(columnLabel, index);
            }
            if (fullColumnName != null) {
                this.fullColumnNameToIndex.put(fullColumnName, index);
            }
            if (columnName != null) {
                this.columnNameToIndex.put(columnName, index);
            }
        }
        this.builtIndexMapping = true;
    }
    
    @Override
    public boolean hasBuiltIndexMapping() {
        return this.builtIndexMapping;
    }
    
    @Override
    public Map<String, Integer> getColumnLabelToIndex() {
        return this.columnLabelToIndex;
    }
    
    @Override
    public void setColumnLabelToIndex(final Map<String, Integer> columnLabelToIndex) {
        this.columnLabelToIndex = columnLabelToIndex;
    }
    
    @Override
    public Map<String, Integer> getFullColumnNameToIndex() {
        return this.fullColumnNameToIndex;
    }
    
    @Override
    public void setFullColumnNameToIndex(final Map<String, Integer> fullColNameToIndex) {
        this.fullColumnNameToIndex = fullColNameToIndex;
    }
    
    @Override
    public Map<String, Integer> getColumnNameToIndex() {
        return this.columnNameToIndex;
    }
    
    @Override
    public void setColumnNameToIndex(final Map<String, Integer> colNameToIndex) {
        this.columnNameToIndex = colNameToIndex;
    }
    
    @Override
    public Map<String, Integer> getColumnToIndexCache() {
        return this.columnToIndexCache;
    }
    
    @Override
    public void setColumnToIndexCache(final Map<String, Integer> columnToIndexCache) {
        this.columnToIndexCache = columnToIndexCache;
    }
    
    @Override
    public void initializeFrom(final ColumnDefinition columnDefinition) {
        this.fields = columnDefinition.getFields();
        this.columnLabelToIndex = columnDefinition.getColumnNameToIndex();
        this.fullColumnNameToIndex = columnDefinition.getFullColumnNameToIndex();
        this.builtIndexMapping = true;
    }
    
    @Override
    public void exportTo(final ColumnDefinition columnDefinition) {
        columnDefinition.setFields(this.fields);
        columnDefinition.setColumnNameToIndex(this.columnLabelToIndex);
        columnDefinition.setFullColumnNameToIndex(this.fullColumnNameToIndex);
    }
    
    @Override
    public int findColumn(final String columnName, final boolean useColumnNamesInFindColumn, final int indexBase) {
        if (!this.hasBuiltIndexMapping()) {
            this.buildIndexMapping();
        }
        Integer index = this.columnToIndexCache.get(columnName);
        if (index != null) {
            return index + indexBase;
        }
        index = this.columnLabelToIndex.get(columnName);
        if (index == null && useColumnNamesInFindColumn) {
            index = this.columnNameToIndex.get(columnName);
        }
        if (index == null) {
            index = this.fullColumnNameToIndex.get(columnName);
        }
        if (index != null) {
            this.columnToIndexCache.put(columnName, index);
            return index + indexBase;
        }
        for (int i = 0; i < this.fields.length; ++i) {
            if (this.fields[i].getName().equalsIgnoreCase(columnName)) {
                return i + indexBase;
            }
            if (this.fields[i].getFullName().equalsIgnoreCase(columnName)) {
                return i + indexBase;
            }
        }
        return -1;
    }
    
    @Override
    public boolean hasLargeFields() {
        if (this.fields != null) {
            int i = 0;
            while (i < this.fields.length) {
                switch (this.fields[i].getMysqlType()) {
                    case BLOB:
                    case MEDIUMBLOB:
                    case LONGBLOB:
                    case TEXT:
                    case MEDIUMTEXT:
                    case LONGTEXT:
                    case JSON: {
                        return true;
                    }
                    default: {
                        ++i;
                        continue;
                    }
                }
            }
        }
        return false;
    }
}
