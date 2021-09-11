
package com.mysql.cj.xdevapi;

import com.mysql.cj.protocol.x.XProtocolError;
import com.mysql.cj.protocol.Message;
import com.mysql.cj.exceptions.WrongArgumentException;
import com.mysql.cj.result.Row;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.function.Function;
import java.util.Arrays;
import java.util.Set;
import java.util.List;
import com.mysql.cj.result.StringValueFactory;
import com.mysql.cj.result.ValueFactory;
import com.mysql.cj.protocol.x.XMessageBuilder;
import com.mysql.cj.MysqlxSession;

public class SchemaImpl implements Schema
{
    private MysqlxSession mysqlxSession;
    private XMessageBuilder xbuilder;
    private Session session;
    private String name;
    private ValueFactory<String> svf;
    
    SchemaImpl(final MysqlxSession mysqlxSession, final Session session, final String name) {
        this.svf = new StringValueFactory();
        this.mysqlxSession = mysqlxSession;
        this.session = session;
        this.name = name;
        this.xbuilder = (XMessageBuilder)this.mysqlxSession.getMessageBuilder();
    }
    
    @Override
    public Session getSession() {
        return this.session;
    }
    
    @Override
    public Schema getSchema() {
        return this;
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    @Override
    public DatabaseObject.DbObjectStatus existsInDatabase() {
        final StringBuilder stmt = new StringBuilder("select count(*) from information_schema.schemata where schema_name = '");
        stmt.append(this.name.replaceAll("'", "\\'"));
        stmt.append("'");
        return this.mysqlxSession.getDataStoreMetadata().schemaExists(this.name) ? DatabaseObject.DbObjectStatus.EXISTS : DatabaseObject.DbObjectStatus.NOT_EXISTS;
    }
    
    @Override
    public List<Collection> getCollections() {
        return this.getCollections(null);
    }
    
    @Override
    public List<Collection> getCollections(final String pattern) {
        final Set<String> strTypes = Arrays.stream(new DatabaseObject.DbObjectType[] { DatabaseObject.DbObjectType.COLLECTION }).map((Function<? super DatabaseObject.DbObjectType, ?>)Enum::toString).collect((Collector<? super Object, ?, Set<String>>)Collectors.toSet());
        final Predicate<Row> rowFiler = r -> strTypes.contains(r.getValue(1, this.svf));
        final Function<Row, String> rowToName = (Function<Row, String>)(r -> r.getValue(0, this.svf));
        final List<String> objectNames = this.mysqlxSession.query(this.xbuilder.buildListObjects(this.name, pattern), rowFiler, rowToName, Collectors.toList());
        return objectNames.stream().map((Function<? super Object, ?>)this::getCollection).collect((Collector<? super Object, ?, List<Collection>>)Collectors.toList());
    }
    
    @Override
    public List<Table> getTables() {
        return this.getTables(null);
    }
    
    @Override
    public List<Table> getTables(final String pattern) {
        final Set<String> strTypes = Arrays.stream(new DatabaseObject.DbObjectType[] { DatabaseObject.DbObjectType.TABLE, DatabaseObject.DbObjectType.VIEW, DatabaseObject.DbObjectType.COLLECTION_VIEW }).map((Function<? super DatabaseObject.DbObjectType, ?>)Enum::toString).collect((Collector<? super Object, ?, Set<String>>)Collectors.toSet());
        final Predicate<Row> rowFiler = r -> strTypes.contains(r.getValue(1, this.svf));
        final Function<Row, String> rowToName = (Function<Row, String>)(r -> r.getValue(0, this.svf));
        final List<String> objectNames = this.mysqlxSession.query(this.xbuilder.buildListObjects(this.name, pattern), rowFiler, rowToName, Collectors.toList());
        return objectNames.stream().map((Function<? super Object, ?>)this::getTable).collect((Collector<? super Object, ?, List<Table>>)Collectors.toList());
    }
    
    @Override
    public Collection getCollection(final String collectionName) {
        return new CollectionImpl(this.mysqlxSession, this, collectionName);
    }
    
    @Override
    public Collection getCollection(final String collectionName, final boolean requireExists) {
        final CollectionImpl coll = new CollectionImpl(this.mysqlxSession, this, collectionName);
        if (requireExists && coll.existsInDatabase() != DatabaseObject.DbObjectStatus.EXISTS) {
            throw new WrongArgumentException(coll.toString() + " doesn't exist");
        }
        return coll;
    }
    
    @Override
    public Table getCollectionAsTable(final String collectionName) {
        return this.getTable(collectionName);
    }
    
    @Override
    public Table getTable(final String tableName) {
        return new TableImpl(this.mysqlxSession, this, tableName);
    }
    
    @Override
    public Table getTable(final String tableName, final boolean requireExists) {
        final TableImpl table = new TableImpl(this.mysqlxSession, this, tableName);
        if (requireExists && table.existsInDatabase() != DatabaseObject.DbObjectStatus.EXISTS) {
            throw new WrongArgumentException(table.toString() + " doesn't exist");
        }
        return table;
    }
    
    @Override
    public Collection createCollection(final String collectionName) {
        this.mysqlxSession.sendMessage(this.xbuilder.buildCreateCollection(this.name, collectionName));
        return new CollectionImpl(this.mysqlxSession, this, collectionName);
    }
    
    @Override
    public Collection createCollection(final String collectionName, final boolean reuseExistingObject) {
        try {
            return this.createCollection(collectionName);
        }
        catch (XProtocolError ex) {
            if (reuseExistingObject && ex.getErrorCode() == 1050) {
                return this.getCollection(collectionName);
            }
            throw ex;
        }
    }
    
    @Override
    public boolean equals(final Object other) {
        return other != null && other.getClass() == SchemaImpl.class && ((SchemaImpl)other).session == this.session && ((SchemaImpl)other).mysqlxSession == this.mysqlxSession && this.name.equals(((SchemaImpl)other).name);
    }
    
    @Override
    public int hashCode() {
        assert false : "hashCode not designed";
        return 0;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Schema(");
        sb.append(ExprUnparser.quoteIdentifier(this.name));
        sb.append(")");
        return sb.toString();
    }
    
    @Override
    public void dropCollection(final String collectionName) {
        try {
            this.mysqlxSession.sendMessage(this.xbuilder.buildDropCollection(this.name, collectionName));
        }
        catch (XProtocolError e) {
            if (e.getErrorCode() != 1051) {
                throw e;
            }
        }
    }
}
