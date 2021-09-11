
package com.mysql.cj.xdevapi;

import com.mysql.cj.conf.RuntimeProperty;
import java.util.Iterator;
import com.mysql.cj.conf.PropertyKey;
import com.mysql.cj.conf.PropertyDefinitions;
import com.mysql.cj.conf.ConnectionUrl;
import com.mysql.cj.Messages;
import com.mysql.cj.protocol.x.XProtocolError;
import com.mysql.cj.protocol.Message;
import com.mysql.cj.util.StringUtils;
import java.util.function.Function;
import com.mysql.cj.result.Row;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import com.mysql.cj.result.ValueFactory;
import com.mysql.cj.result.StringValueFactory;
import java.util.List;
import com.mysql.cj.protocol.x.XProtocol;
import com.mysql.cj.conf.PropertySet;
import com.mysql.cj.conf.DefaultPropertySet;
import com.mysql.cj.conf.HostInfo;
import com.mysql.cj.protocol.x.XMessageBuilder;
import com.mysql.cj.MysqlxSession;

public class SessionImpl implements Session
{
    protected MysqlxSession session;
    protected String defaultSchemaName;
    private XMessageBuilder xbuilder;
    
    public SessionImpl(final HostInfo hostInfo) {
        final PropertySet pset = new DefaultPropertySet();
        pset.initializeProperties(hostInfo.exposeAsProperties());
        this.session = new MysqlxSession(hostInfo, pset);
        this.defaultSchemaName = hostInfo.getDatabase();
        this.xbuilder = (XMessageBuilder)this.session.getMessageBuilder();
    }
    
    public SessionImpl(final XProtocol prot) {
        this.session = new MysqlxSession(prot);
        this.defaultSchemaName = prot.defaultSchemaName;
        this.xbuilder = (XMessageBuilder)this.session.getMessageBuilder();
    }
    
    protected SessionImpl() {
    }
    
    @Override
    public List<Schema> getSchemas() {
        final Function<Row, String> rowToName = (Function<Row, String>)(r -> r.getValue(0, (ValueFactory<String>)new StringValueFactory()));
        final Function<Row, Schema> rowToSchema = rowToName.andThen(n -> new SchemaImpl(this.session, this, n));
        return this.session.query(this.xbuilder.buildSqlStatement("select schema_name from information_schema.schemata"), null, rowToSchema, Collectors.toList());
    }
    
    @Override
    public Schema getSchema(final String schemaName) {
        return new SchemaImpl(this.session, this, schemaName);
    }
    
    @Override
    public String getDefaultSchemaName() {
        return this.defaultSchemaName;
    }
    
    @Override
    public Schema getDefaultSchema() {
        if (this.defaultSchemaName == null || this.defaultSchemaName.length() == 0) {
            return null;
        }
        return new SchemaImpl(this.session, this, this.defaultSchemaName);
    }
    
    @Override
    public Schema createSchema(final String schemaName) {
        final StringBuilder stmtString = new StringBuilder("CREATE DATABASE ");
        stmtString.append(StringUtils.quoteIdentifier(schemaName, true));
        this.session.sendMessage(this.xbuilder.buildSqlStatement(stmtString.toString()));
        return this.getSchema(schemaName);
    }
    
    @Override
    public Schema createSchema(final String schemaName, final boolean reuseExistingObject) {
        try {
            return this.createSchema(schemaName);
        }
        catch (XProtocolError ex) {
            if (ex.getErrorCode() == 1007) {
                return this.getSchema(schemaName);
            }
            throw ex;
        }
    }
    
    @Override
    public void dropSchema(final String schemaName) {
        final StringBuilder stmtString = new StringBuilder("DROP DATABASE ");
        stmtString.append(StringUtils.quoteIdentifier(schemaName, true));
        this.session.sendMessage(this.xbuilder.buildSqlStatement(stmtString.toString()));
    }
    
    @Override
    public void startTransaction() {
        this.session.sendMessage(this.xbuilder.buildSqlStatement("START TRANSACTION"));
    }
    
    @Override
    public void commit() {
        this.session.sendMessage(this.xbuilder.buildSqlStatement("COMMIT"));
    }
    
    @Override
    public void rollback() {
        this.session.sendMessage(this.xbuilder.buildSqlStatement("ROLLBACK"));
    }
    
    @Override
    public String setSavepoint() {
        return this.setSavepoint(StringUtils.getUniqueSavepointId());
    }
    
    @Override
    public String setSavepoint(final String name) {
        if (name == null || name.trim().length() == 0) {
            throw new XDevAPIError(Messages.getString("XSession.0", new String[] { "name" }));
        }
        this.session.sendMessage(this.xbuilder.buildSqlStatement("SAVEPOINT " + StringUtils.quoteIdentifier(name, true)));
        return name;
    }
    
    @Override
    public void rollbackTo(final String name) {
        if (name == null || name.trim().length() == 0) {
            throw new XDevAPIError(Messages.getString("XSession.0", new String[] { "name" }));
        }
        this.session.sendMessage(this.xbuilder.buildSqlStatement("ROLLBACK TO " + StringUtils.quoteIdentifier(name, true)));
    }
    
    @Override
    public void releaseSavepoint(final String name) {
        if (name == null || name.trim().length() == 0) {
            throw new XDevAPIError(Messages.getString("XSession.0", new String[] { "name" }));
        }
        this.session.sendMessage(this.xbuilder.buildSqlStatement("RELEASE SAVEPOINT " + StringUtils.quoteIdentifier(name, true)));
    }
    
    @Override
    public String getUri() {
        final PropertySet pset = this.session.getPropertySet();
        final StringBuilder sb = new StringBuilder(ConnectionUrl.Type.XDEVAPI_SESSION.getScheme());
        sb.append("//").append(this.session.getProcessHost()).append(":").append(this.session.getPort()).append("/").append(this.defaultSchemaName).append("?");
        boolean isFirstParam = true;
        for (final PropertyKey propKey : PropertyDefinitions.PROPERTY_KEY_TO_PROPERTY_DEFINITION.keySet()) {
            final RuntimeProperty<?> propToGet = pset.getProperty(propKey);
            if (propToGet.isExplicitlySet()) {
                final String propValue = propToGet.getStringValue();
                final Object defaultValue = propToGet.getPropertyDefinition().getDefaultValue();
                if ((defaultValue != null || StringUtils.isNullOrEmpty(propValue)) && (defaultValue == null || propValue != null) && (defaultValue == null || propValue == null || propValue.equals(defaultValue.toString()))) {
                    continue;
                }
                if (isFirstParam) {
                    isFirstParam = false;
                }
                else {
                    sb.append("&");
                }
                sb.append(propKey.getKeyName());
                sb.append("=");
                sb.append(propValue);
            }
        }
        return sb.toString();
    }
    
    @Override
    public boolean isOpen() {
        return !this.session.isClosed();
    }
    
    @Override
    public void close() {
        this.session.quit();
    }
    
    @Override
    public SqlStatementImpl sql(final String sql) {
        return new SqlStatementImpl(this.session, sql);
    }
    
    public MysqlxSession getSession() {
        return this.session;
    }
}
