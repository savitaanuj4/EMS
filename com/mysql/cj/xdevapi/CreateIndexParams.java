
package com.mysql.cj.xdevapi;

import java.util.Map;
import java.util.Iterator;
import java.io.IOException;
import com.mysql.cj.exceptions.AssertionFailedException;
import java.io.StringReader;
import com.mysql.cj.Messages;
import java.util.ArrayList;
import java.util.List;

public class CreateIndexParams
{
    private String indexName;
    private String indexType;
    private List<IndexField> fields;
    
    public CreateIndexParams(final String indexName, final DbDoc indexDefinition) {
        this.indexType = "INDEX";
        this.fields = new ArrayList<IndexField>();
        this.init(indexName, indexDefinition);
    }
    
    public CreateIndexParams(final String indexName, final String jsonIndexDefinition) {
        this.indexType = "INDEX";
        this.fields = new ArrayList<IndexField>();
        if (jsonIndexDefinition == null || jsonIndexDefinition.trim().length() == 0) {
            throw new XDevAPIError(Messages.getString("CreateIndexParams.0", new String[] { "jsonIndexDefinition" }));
        }
        try {
            this.init(indexName, JsonParser.parseDoc(new StringReader(jsonIndexDefinition)));
        }
        catch (IOException ex) {
            throw AssertionFailedException.shouldNotHappen(ex);
        }
    }
    
    private void init(final String idxName, final DbDoc indexDefinition) {
        if (idxName == null || idxName.trim().length() == 0) {
            throw new XDevAPIError(Messages.getString("CreateIndexParams.0", new String[] { "indexName" }));
        }
        if (indexDefinition == null) {
            throw new XDevAPIError(Messages.getString("CreateIndexParams.0", new String[] { "indexDefinition" }));
        }
        this.indexName = idxName;
        for (final String key : ((Map<String, V>)indexDefinition).keySet()) {
            if (!"type".equals(key) && !"fields".equals(key)) {
                throw new XDevAPIError("The '" + key + "' field is not allowed in indexDefinition.");
            }
        }
        JsonValue val = ((Map<K, JsonValue>)indexDefinition).get("type");
        if (val != null) {
            if (!(val instanceof JsonString)) {
                throw new XDevAPIError("Index type must be a string.");
            }
            final String type = ((JsonString)val).getString();
            if (!"INDEX".equalsIgnoreCase(type) && !"SPATIAL".equalsIgnoreCase(type)) {
                throw new XDevAPIError("Wrong index type '" + type + "'. Must be 'INDEX' or 'SPATIAL'.");
            }
            this.indexType = type;
        }
        val = ((Map<K, JsonValue>)indexDefinition).get("fields");
        if (val == null) {
            throw new XDevAPIError("Index definition does not contain fields.");
        }
        if (val instanceof JsonArray) {
            for (final JsonValue field : (JsonArray)val) {
                if (!(field instanceof DbDoc)) {
                    throw new XDevAPIError("Index field definition must be a JSON document.");
                }
                this.fields.add(new IndexField((DbDoc)field));
            }
            return;
        }
        throw new XDevAPIError("Index definition 'fields' member must be an array of index fields.");
    }
    
    public String getIndexName() {
        return this.indexName;
    }
    
    public String getIndexType() {
        return this.indexType;
    }
    
    public List<IndexField> getFields() {
        return this.fields;
    }
    
    public static class IndexField
    {
        private String field;
        private String type;
        private boolean required;
        private Integer options;
        private Integer srid;
        
        public IndexField(final DbDoc indexField) {
            this.required = false;
            this.options = null;
            this.srid = null;
            for (final String key : ((Map<String, V>)indexField).keySet()) {
                if (!"type".equals(key) && !"field".equals(key) && !"required".equals(key) && !"options".equals(key) && !"srid".equals(key)) {
                    throw new XDevAPIError("The '" + key + "' field is not allowed in indexField.");
                }
            }
            JsonValue val = ((Map<K, JsonValue>)indexField).get("field");
            if (val == null) {
                throw new XDevAPIError("Index field definition has no document path.");
            }
            if (!(val instanceof JsonString)) {
                throw new XDevAPIError("Index field 'field' member must be a string.");
            }
            this.field = ((JsonString)val).getString();
            val = ((Map<K, JsonValue>)indexField).get("type");
            if (val == null) {
                throw new XDevAPIError("Index field definition has no field type.");
            }
            if (val instanceof JsonString) {
                this.type = ((JsonString)val).getString();
                val = ((Map<K, JsonValue>)indexField).get("required");
                if (val != null) {
                    if (!(val instanceof JsonLiteral) || JsonLiteral.NULL.equals(val)) {
                        throw new XDevAPIError("Index field 'required' member must be boolean.");
                    }
                    this.required = Boolean.valueOf(((JsonLiteral)val).value);
                }
                else if (this.type.equalsIgnoreCase("GEOJSON")) {
                    this.required = true;
                }
                val = ((Map<K, JsonValue>)indexField).get("options");
                if (val != null) {
                    if (!this.type.equalsIgnoreCase("GEOJSON")) {
                        throw new XDevAPIError("Index field 'options' member should not be used for field types other than GEOJSON.");
                    }
                    if (!(val instanceof JsonNumber)) {
                        throw new XDevAPIError("Index field 'options' member must be integer.");
                    }
                    this.options = ((JsonNumber)val).getInteger();
                }
                val = ((Map<K, JsonValue>)indexField).get("srid");
                if (val != null) {
                    if (!this.type.equalsIgnoreCase("GEOJSON")) {
                        throw new XDevAPIError("Index field 'srid' member should not be used for field types other than GEOJSON.");
                    }
                    if (!(val instanceof JsonNumber)) {
                        throw new XDevAPIError("Index field 'srid' member must be integer.");
                    }
                    this.srid = ((JsonNumber)val).getInteger();
                }
                return;
            }
            throw new XDevAPIError("Index type must be a string.");
        }
        
        public String getField() {
            return this.field;
        }
        
        public String getType() {
            return this.type;
        }
        
        public boolean isRequired() {
            return this.required;
        }
        
        public Integer getOptions() {
            return this.options;
        }
        
        public Integer getSrid() {
            return this.srid;
        }
    }
}
