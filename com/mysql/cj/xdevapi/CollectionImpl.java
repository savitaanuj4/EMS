
package com.mysql.cj.xdevapi;

import com.mysql.cj.Messages;
import com.mysql.cj.protocol.x.XProtocolError;
import com.mysql.cj.protocol.Message;
import com.mysql.cj.protocol.x.StatementExecuteOk;
import java.io.IOException;
import com.mysql.cj.exceptions.AssertionFailedException;
import java.io.StringReader;
import com.mysql.cj.exceptions.FeatureNotAvailableException;
import java.util.Map;
import com.mysql.cj.protocol.x.XMessageBuilder;
import com.mysql.cj.MysqlxSession;

public class CollectionImpl implements Collection
{
    private MysqlxSession mysqlxSession;
    private XMessageBuilder xbuilder;
    private SchemaImpl schema;
    private String name;
    
    CollectionImpl(final MysqlxSession mysqlxSession, final SchemaImpl schema, final String name) {
        this.mysqlxSession = mysqlxSession;
        this.schema = schema;
        this.name = name;
        this.xbuilder = (XMessageBuilder)this.mysqlxSession.getMessageBuilder();
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
    public AddStatement add(final Map<String, ?> doc) {
        throw new FeatureNotAvailableException("TODO: ");
    }
    
    @Override
    public AddStatement add(final String... jsonString) {
        try {
            final DbDoc[] docs = new DbDoc[jsonString.length];
            for (int i = 0; i < jsonString.length; ++i) {
                docs[i] = JsonParser.parseDoc(new StringReader(jsonString[i]));
            }
            return this.add(docs);
        }
        catch (IOException ex) {
            throw AssertionFailedException.shouldNotHappen(ex);
        }
    }
    
    @Override
    public AddStatement add(final DbDoc doc) {
        return new AddStatementImpl(this.mysqlxSession, this.schema.getName(), this.name, doc);
    }
    
    @Override
    public AddStatement add(final DbDoc... docs) {
        return new AddStatementImpl(this.mysqlxSession, this.schema.getName(), this.name, docs);
    }
    
    @Override
    public FindStatement find() {
        return this.find(null);
    }
    
    @Override
    public FindStatement find(final String searchCondition) {
        return new FindStatementImpl(this.mysqlxSession, this.schema.getName(), this.name, searchCondition);
    }
    
    @Override
    public ModifyStatement modify(final String searchCondition) {
        return new ModifyStatementImpl(this.mysqlxSession, this.schema.getName(), this.name, searchCondition);
    }
    
    @Override
    public RemoveStatement remove(final String searchCondition) {
        return new RemoveStatementImpl(this.mysqlxSession, this.schema.getName(), this.name, searchCondition);
    }
    
    @Override
    public Result createIndex(final String indexName, final DbDoc indexDefinition) {
        final StatementExecuteOk ok = this.mysqlxSession.sendMessage(this.xbuilder.buildCreateCollectionIndex(this.schema.getName(), this.name, new CreateIndexParams(indexName, indexDefinition)));
        return new UpdateResult(ok);
    }
    
    @Override
    public Result createIndex(final String indexName, final String jsonIndexDefinition) {
        final StatementExecuteOk ok = this.mysqlxSession.sendMessage(this.xbuilder.buildCreateCollectionIndex(this.schema.getName(), this.name, new CreateIndexParams(indexName, jsonIndexDefinition)));
        return new UpdateResult(ok);
    }
    
    @Override
    public void dropIndex(final String indexName) {
        try {
            this.mysqlxSession.sendMessage(this.xbuilder.buildDropCollectionIndex(this.schema.getName(), this.name, indexName));
        }
        catch (XProtocolError e) {
            if (e.getErrorCode() != 1091) {
                throw e;
            }
        }
    }
    
    @Override
    public long count() {
        try {
            return this.mysqlxSession.getDataStoreMetadata().getTableRowCount(this.schema.getName(), this.name);
        }
        catch (XProtocolError e) {
            if (e.getErrorCode() == 1146) {
                throw new XProtocolError("Collection '" + this.name + "' does not exist in schema '" + this.schema.getName() + "'", e);
            }
            throw e;
        }
    }
    
    @Override
    public DbDoc newDoc() {
        return new DbDocImpl();
    }
    
    @Override
    public boolean equals(final Object other) {
        return other != null && other.getClass() == CollectionImpl.class && ((CollectionImpl)other).schema.equals(this.schema) && ((CollectionImpl)other).mysqlxSession == this.mysqlxSession && this.name.equals(((CollectionImpl)other).name);
    }
    
    @Override
    public int hashCode() {
        assert false : "hashCode not designed";
        return 0;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Collection(");
        sb.append(ExprUnparser.quoteIdentifier(this.schema.getName()));
        sb.append(".");
        sb.append(ExprUnparser.quoteIdentifier(this.name));
        sb.append(")");
        return sb.toString();
    }
    
    @Override
    public Result replaceOne(final String id, final DbDoc doc) {
        if (id == null) {
            throw new XDevAPIError(Messages.getString("CreateTableStatement.0", new String[] { "id" }));
        }
        if (doc == null) {
            throw new XDevAPIError(Messages.getString("CreateTableStatement.0", new String[] { "doc" }));
        }
        return ((Statement<STMT_T, Result>)((Statement<ModifyStatement, RES_T>)this.modify("_id = :id").set("$", doc)).bind("id", id)).execute();
    }
    
    @Override
    public Result replaceOne(final String id, final String jsonString) {
        if (id == null) {
            throw new XDevAPIError(Messages.getString("CreateTableStatement.0", new String[] { "id" }));
        }
        if (jsonString == null) {
            throw new XDevAPIError(Messages.getString("CreateTableStatement.0", new String[] { "jsonString" }));
        }
        try {
            return this.replaceOne(id, JsonParser.parseDoc(new StringReader(jsonString)));
        }
        catch (IOException e) {
            throw AssertionFailedException.shouldNotHappen(e);
        }
    }
    
    @Override
    public Result addOrReplaceOne(final String id, final DbDoc doc) {
        if (id == null) {
            throw new XDevAPIError(Messages.getString("CreateTableStatement.0", new String[] { "id" }));
        }
        if (doc == null) {
            throw new XDevAPIError(Messages.getString("CreateTableStatement.0", new String[] { "doc" }));
        }
        if (doc.get("_id") == null) {
            doc.add("_id", new JsonString().setValue(id));
        }
        else if (!id.equals(((Map<K, JsonString>)doc).get("_id").getString())) {
            throw new XDevAPIError("Document already has an _id that doesn't match to id parameter");
        }
        return ((Statement<STMT_T, Result>)this.add(doc).setUpsert(true)).execute();
    }
    
    @Override
    public Result addOrReplaceOne(final String id, final String jsonString) {
        if (id == null) {
            throw new XDevAPIError(Messages.getString("CreateTableStatement.0", new String[] { "id" }));
        }
        if (jsonString == null) {
            throw new XDevAPIError(Messages.getString("CreateTableStatement.0", new String[] { "jsonString" }));
        }
        try {
            return this.addOrReplaceOne(id, JsonParser.parseDoc(new StringReader(jsonString)));
        }
        catch (IOException e) {
            throw AssertionFailedException.shouldNotHappen(e);
        }
    }
    
    @Override
    public DbDoc getOne(final String id) {
        return ((Statement<STMT_T, DocResult>)((Statement<FindStatement, RES_T>)this.find("_id = :id")).bind("id", id)).execute().fetchOne();
    }
    
    @Override
    public Result removeOne(final String id) {
        return ((Statement<STMT_T, Result>)((Statement<RemoveStatement, RES_T>)this.remove("_id = :id")).bind("id", id)).execute();
    }
}
