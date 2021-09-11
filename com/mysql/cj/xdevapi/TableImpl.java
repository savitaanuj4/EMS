
package com.mysql.cj.xdevapi;

import java.util.function.Function;
import com.mysql.cj.result.ValueFactory;
import com.mysql.cj.result.Row;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.List;
import com.mysql.cj.result.StringValueFactory;
import com.mysql.cj.protocol.x.XProtocolError;
import java.util.Map;
import com.mysql.cj.Messages;
import com.mysql.cj.protocol.x.XMessageBuilder;
import com.mysql.cj.MysqlxSession;

public class TableImpl implements Table
{
    private MysqlxSession mysqlxSession;
    private SchemaImpl schema;
    private String name;
    private Boolean isView;
    private XMessageBuilder xbuilder;
    
    TableImpl(final MysqlxSession mysqlxSession, final SchemaImpl schema, final String name) {
        this.isView = null;
        if (mysqlxSession == null) {
            throw new XDevAPIError(Messages.getString("CreateTableStatement.0", new String[] { "mysqlxSession" }));
        }
        if (schema == null) {
            throw new XDevAPIError(Messages.getString("CreateTableStatement.0", new String[] { "schema" }));
        }
        if (name == null) {
            throw new XDevAPIError(Messages.getString("CreateTableStatement.0", new String[] { "name" }));
        }
        this.mysqlxSession = mysqlxSession;
        this.xbuilder = (XMessageBuilder)this.mysqlxSession.getMessageBuilder();
        this.schema = schema;
        this.name = name;
    }
    
    @Override
    public Session getSession() {
        return this.schema.getSession();
    }
    
    @Override
    public Schema getSchema() {
        return this.schema;
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    @Override
    public DatabaseObject.DbObjectStatus existsInDatabase() {
        if (this.mysqlxSession.getDataStoreMetadata().tableExists(this.schema.getName(), this.name)) {
            return DatabaseObject.DbObjectStatus.EXISTS;
        }
        return DatabaseObject.DbObjectStatus.NOT_EXISTS;
    }
    
    @Override
    public InsertStatement insert() {
        return new InsertStatementImpl(this.mysqlxSession, this.schema.getName(), this.name, new String[0]);
    }
    
    @Override
    public InsertStatement insert(final String... fields) {
        return new InsertStatementImpl(this.mysqlxSession, this.schema.getName(), this.name, fields);
    }
    
    @Override
    public InsertStatement insert(final Map<String, Object> fieldsAndValues) {
        return new InsertStatementImpl(this.mysqlxSession, this.schema.getName(), this.name, fieldsAndValues);
    }
    
    @Override
    public SelectStatement select(final String... projection) {
        return new SelectStatementImpl(this.mysqlxSession, this.schema.getName(), this.name, projection);
    }
    
    @Override
    public UpdateStatement update() {
        return new UpdateStatementImpl(this.mysqlxSession, this.schema.getName(), this.name);
    }
    
    @Override
    public DeleteStatement delete() {
        return new DeleteStatementImpl(this.mysqlxSession, this.schema.getName(), this.name);
    }
    
    @Override
    public long count() {
        try {
            return this.mysqlxSession.getDataStoreMetadata().getTableRowCount(this.schema.getName(), this.name);
        }
        catch (XProtocolError e) {
            if (e.getErrorCode() == 1146) {
                throw new XProtocolError("Table '" + this.name + "' does not exist in schema '" + this.schema.getName() + "'", e);
            }
            throw e;
        }
    }
    
    @Override
    public boolean equals(final Object other) {
        return other != null && other.getClass() == TableImpl.class && ((TableImpl)other).schema.equals(this.schema) && ((TableImpl)other).mysqlxSession == this.mysqlxSession && this.name.equals(((TableImpl)other).name);
    }
    
    @Override
    public int hashCode() {
        assert false : "hashCode not designed";
        return 0;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Table(");
        sb.append(ExprUnparser.quoteIdentifier(this.schema.getName()));
        sb.append(".");
        sb.append(ExprUnparser.quoteIdentifier(this.name));
        sb.append(")");
        return sb.toString();
    }
    
    @Override
    public boolean isView() {
        if (this.isView == null) {
            final ValueFactory<String> svf = new StringValueFactory();
            final ValueFactory<String> valueFactory;
            final Function<Row, DatabaseObjectDescription> rowToDatabaseObjectDescription = (Function<Row, DatabaseObjectDescription>)(r -> new DatabaseObjectDescription(r.getValue(0, valueFactory), r.getValue(1, valueFactory)));
            final List<DatabaseObjectDescription> objects = this.mysqlxSession.query(this.xbuilder.buildListObjects(this.schema.getName(), this.name), null, rowToDatabaseObjectDescription, Collectors.toList());
            if (objects.isEmpty()) {
                return false;
            }
            this.isView = (objects.get(0).getObjectType() == DatabaseObject.DbObjectType.VIEW || objects.get(0).getObjectType() == DatabaseObject.DbObjectType.COLLECTION_VIEW);
        }
        return this.isView;
    }
    
    public void setView(final boolean isView) {
        this.isView = isView;
    }
}
