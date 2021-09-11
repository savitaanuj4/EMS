
package com.mysql.cj.protocol.x;

import javax.security.sasl.SaslClient;
import javax.security.sasl.SaslException;
import javax.security.sasl.Sasl;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import java.math.BigInteger;
import java.security.DigestException;
import com.mysql.cj.protocol.Security;
import com.mysql.cj.util.StringUtils;
import java.util.function.Function;
import java.util.ArrayList;
import java.util.Arrays;
import com.google.protobuf.ByteString;
import com.mysql.cj.x.protobuf.MysqlxSql;
import java.util.Iterator;
import com.mysql.cj.xdevapi.CreateIndexParams;
import com.mysql.cj.Messages;
import com.mysql.cj.x.protobuf.MysqlxSession;
import java.util.Map;
import com.mysql.cj.xdevapi.UpdateParams;
import com.mysql.cj.x.protobuf.MysqlxExpr;
import com.mysql.cj.xdevapi.UpdateSpec;
import com.mysql.cj.xdevapi.FilterParams;
import com.mysql.cj.xdevapi.InsertParams;
import java.util.function.Consumer;
import com.mysql.cj.x.protobuf.MysqlxCrud;
import java.util.List;
import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import com.mysql.cj.xdevapi.ExprUtil;
import com.google.protobuf.Message;
import com.mysql.cj.x.protobuf.MysqlxConnection;
import com.mysql.cj.MessageBuilder;

public class XMessageBuilder implements MessageBuilder<XMessage>
{
    private static final String XPLUGIN_NAMESPACE = "mysqlx";
    
    public XMessage buildCapabilitiesGet() {
        return new XMessage((Message)MysqlxConnection.CapabilitiesGet.getDefaultInstance());
    }
    
    public XMessage buildCapabilitiesSet(final String name, final Object value) {
        final MysqlxDatatypes.Any v = ExprUtil.argObjectToScalarAny(value);
        final MysqlxConnection.Capability cap = MysqlxConnection.Capability.newBuilder().setName(name).setValue(v).build();
        final MysqlxConnection.Capabilities caps = MysqlxConnection.Capabilities.newBuilder().addCapabilities(cap).build();
        return new XMessage((Message)MysqlxConnection.CapabilitiesSet.newBuilder().setCapabilities(caps).build());
    }
    
    public XMessage buildDocInsert(final String schemaName, final String collectionName, final List<String> json, final boolean upsert) {
        final MysqlxCrud.Insert.Builder builder = MysqlxCrud.Insert.newBuilder().setCollection(ExprUtil.buildCollection(schemaName, collectionName));
        if (upsert != builder.getUpsert()) {
            builder.setUpsert(upsert);
        }
        json.stream().map(str -> MysqlxCrud.Insert.TypedRow.newBuilder().addField(ExprUtil.argObjectToExpr(str, false)).build()).forEach((Consumer<? super Object>)builder::addRow);
        return new XMessage((Message)builder.build());
    }
    
    public XMessage buildRowInsert(final String schemaName, final String tableName, final InsertParams insertParams) {
        final MysqlxCrud.Insert.Builder builder = MysqlxCrud.Insert.newBuilder().setDataModel(MysqlxCrud.DataModel.TABLE).setCollection(ExprUtil.buildCollection(schemaName, tableName));
        if (insertParams.getProjection() != null) {
            builder.addAllProjection((Iterable<? extends MysqlxCrud.Column>)insertParams.getProjection());
        }
        builder.addAllRow((Iterable<? extends MysqlxCrud.Insert.TypedRow>)insertParams.getRows());
        return new XMessage((Message)builder.build());
    }
    
    public XMessage buildDocUpdate(final FilterParams filterParams, final List<UpdateSpec> updates) {
        final MysqlxCrud.Update.Builder builder = MysqlxCrud.Update.newBuilder().setCollection((MysqlxCrud.Collection)filterParams.getCollection());
        final MysqlxCrud.UpdateOperation.Builder opBuilder;
        final MysqlxCrud.Update.Builder builder2;
        updates.forEach(u -> {
            opBuilder = MysqlxCrud.UpdateOperation.newBuilder();
            opBuilder.setOperation((MysqlxCrud.UpdateOperation.UpdateType)u.getUpdateType());
            opBuilder.setSource((MysqlxExpr.ColumnIdentifier)u.getSource());
            if (u.getValue() != null) {
                opBuilder.setValue((MysqlxExpr.Expr)u.getValue());
            }
            builder2.addOperation(opBuilder.build());
            return;
        });
        applyFilterParams(filterParams, builder::addAllOrder, builder::setLimit, builder::setCriteria, builder::addAllArgs);
        return new XMessage((Message)builder.build());
    }
    
    public XMessage buildRowUpdate(final FilterParams filterParams, final UpdateParams updateParams) {
        final MysqlxCrud.Update.Builder builder = MysqlxCrud.Update.newBuilder().setDataModel(MysqlxCrud.DataModel.TABLE).setCollection((MysqlxCrud.Collection)filterParams.getCollection());
        ((Map)updateParams.getUpdates()).entrySet().stream().map(e -> MysqlxCrud.UpdateOperation.newBuilder().setOperation(MysqlxCrud.UpdateOperation.UpdateType.SET).setSource(e.getKey()).setValue((MysqlxExpr.Expr)e.getValue()).build()).forEach(builder::addOperation);
        applyFilterParams(filterParams, builder::addAllOrder, builder::setLimit, builder::setCriteria, builder::addAllArgs);
        return new XMessage((Message)builder.build());
    }
    
    public XMessage buildFind(final FilterParams filterParams) {
        final MysqlxCrud.Find.Builder builder = MysqlxCrud.Find.newBuilder().setCollection((MysqlxCrud.Collection)filterParams.getCollection());
        builder.setDataModel(filterParams.isRelational() ? MysqlxCrud.DataModel.TABLE : MysqlxCrud.DataModel.DOCUMENT);
        if (filterParams.getFields() != null) {
            builder.addAllProjection((Iterable<? extends MysqlxCrud.Projection>)filterParams.getFields());
        }
        if (filterParams.getGrouping() != null) {
            builder.addAllGrouping((Iterable<? extends MysqlxExpr.Expr>)filterParams.getGrouping());
        }
        if (filterParams.getGroupingCriteria() != null) {
            builder.setGroupingCriteria((MysqlxExpr.Expr)filterParams.getGroupingCriteria());
        }
        if (filterParams.getLock() != null) {
            builder.setLocking(MysqlxCrud.Find.RowLock.forNumber(filterParams.getLock().asNumber()));
        }
        if (filterParams.getLockOption() != null) {
            builder.setLockingOptions(MysqlxCrud.Find.RowLockOptions.forNumber(filterParams.getLockOption().asNumber()));
        }
        applyFilterParams(filterParams, builder::addAllOrder, builder::setLimit, builder::setCriteria, builder::addAllArgs);
        return new XMessage((Message)builder.build());
    }
    
    public XMessage buildDelete(final FilterParams filterParams) {
        final MysqlxCrud.Delete.Builder builder = MysqlxCrud.Delete.newBuilder().setCollection((MysqlxCrud.Collection)filterParams.getCollection());
        applyFilterParams(filterParams, builder::addAllOrder, builder::setLimit, builder::setCriteria, builder::addAllArgs);
        return new XMessage((Message)builder.build());
    }
    
    @Override
    public XMessage buildClose() {
        return new XMessage((Message)MysqlxSession.Close.getDefaultInstance());
    }
    
    public XMessage buildCreateCollection(final String schemaName, final String collectionName) {
        if (schemaName == null) {
            throw new XProtocolError(Messages.getString("CreateTableStatement.0", new String[] { "schemaName" }));
        }
        if (collectionName == null) {
            throw new XProtocolError(Messages.getString("CreateTableStatement.0", new String[] { "collectionName" }));
        }
        return new XMessage((Message)this.buildXpluginCommand(XpluginStatementCommand.XPLUGIN_STMT_CREATE_COLLECTION, MysqlxDatatypes.Any.newBuilder().setType(MysqlxDatatypes.Any.Type.OBJECT).setObj(MysqlxDatatypes.Object.newBuilder().addFld(MysqlxDatatypes.Object.ObjectField.newBuilder().setKey("name").setValue(ExprUtil.buildAny(collectionName))).addFld(MysqlxDatatypes.Object.ObjectField.newBuilder().setKey("schema").setValue(ExprUtil.buildAny(schemaName)))).build()));
    }
    
    public XMessage buildDropCollection(final String schemaName, final String collectionName) {
        if (schemaName == null) {
            throw new XProtocolError(Messages.getString("CreateTableStatement.0", new String[] { "schemaName" }));
        }
        if (collectionName == null) {
            throw new XProtocolError(Messages.getString("CreateTableStatement.0", new String[] { "collectionName" }));
        }
        return new XMessage((Message)this.buildXpluginCommand(XpluginStatementCommand.XPLUGIN_STMT_DROP_COLLECTION, MysqlxDatatypes.Any.newBuilder().setType(MysqlxDatatypes.Any.Type.OBJECT).setObj(MysqlxDatatypes.Object.newBuilder().addFld(MysqlxDatatypes.Object.ObjectField.newBuilder().setKey("name").setValue(ExprUtil.buildAny(collectionName))).addFld(MysqlxDatatypes.Object.ObjectField.newBuilder().setKey("schema").setValue(ExprUtil.buildAny(schemaName)))).build()));
    }
    
    public XMessage buildListObjects(final String schemaName, final String pattern) {
        if (schemaName == null) {
            throw new XProtocolError(Messages.getString("CreateTableStatement.0", new String[] { "schemaName" }));
        }
        final MysqlxDatatypes.Object.Builder obj = MysqlxDatatypes.Object.newBuilder().addFld(MysqlxDatatypes.Object.ObjectField.newBuilder().setKey("schema").setValue(ExprUtil.buildAny(schemaName)));
        if (pattern != null) {
            obj.addFld(MysqlxDatatypes.Object.ObjectField.newBuilder().setKey("pattern").setValue(ExprUtil.buildAny(pattern)));
        }
        return new XMessage((Message)this.buildXpluginCommand(XpluginStatementCommand.XPLUGIN_STMT_LIST_OBJECTS, MysqlxDatatypes.Any.newBuilder().setType(MysqlxDatatypes.Any.Type.OBJECT).setObj(obj).build()));
    }
    
    public XMessage buildEnableNotices(final String... notices) {
        final MysqlxDatatypes.Array.Builder abuilder = MysqlxDatatypes.Array.newBuilder();
        for (final String notice : notices) {
            abuilder.addValue(ExprUtil.buildAny(notice));
        }
        return new XMessage((Message)this.buildXpluginCommand(XpluginStatementCommand.XPLUGIN_STMT_ENABLE_NOTICES, MysqlxDatatypes.Any.newBuilder().setType(MysqlxDatatypes.Any.Type.OBJECT).setObj(MysqlxDatatypes.Object.newBuilder().addFld(MysqlxDatatypes.Object.ObjectField.newBuilder().setKey("notice").setValue(MysqlxDatatypes.Any.newBuilder().setType(MysqlxDatatypes.Any.Type.ARRAY).setArray(abuilder)))).build()));
    }
    
    public XMessage buildDisableNotices(final String... notices) {
        final MysqlxDatatypes.Array.Builder abuilder = MysqlxDatatypes.Array.newBuilder();
        for (final String notice : notices) {
            abuilder.addValue(ExprUtil.buildAny(notice));
        }
        return new XMessage((Message)this.buildXpluginCommand(XpluginStatementCommand.XPLUGIN_STMT_DISABLE_NOTICES, MysqlxDatatypes.Any.newBuilder().setType(MysqlxDatatypes.Any.Type.OBJECT).setObj(MysqlxDatatypes.Object.newBuilder().addFld(MysqlxDatatypes.Object.ObjectField.newBuilder().setKey("notice").setValue(MysqlxDatatypes.Any.newBuilder().setType(MysqlxDatatypes.Any.Type.ARRAY).setArray(abuilder)))).build()));
    }
    
    public XMessage buildListNotices() {
        return new XMessage((Message)this.buildXpluginCommand(XpluginStatementCommand.XPLUGIN_STMT_LIST_NOTICES, new MysqlxDatatypes.Any[0]));
    }
    
    public XMessage buildCreateCollectionIndex(final String schemaName, final String collectionName, final CreateIndexParams params) {
        final MysqlxDatatypes.Object.Builder builder = MysqlxDatatypes.Object.newBuilder();
        builder.addFld(MysqlxDatatypes.Object.ObjectField.newBuilder().setKey("name").setValue(ExprUtil.buildAny(params.getIndexName()))).addFld(MysqlxDatatypes.Object.ObjectField.newBuilder().setKey("collection").setValue(ExprUtil.buildAny(collectionName))).addFld(MysqlxDatatypes.Object.ObjectField.newBuilder().setKey("schema").setValue(ExprUtil.buildAny(schemaName))).addFld(MysqlxDatatypes.Object.ObjectField.newBuilder().setKey("unique").setValue(ExprUtil.buildAny(false)));
        if (params.getIndexType() != null) {
            builder.addFld(MysqlxDatatypes.Object.ObjectField.newBuilder().setKey("type").setValue(ExprUtil.buildAny(params.getIndexType())));
        }
        final MysqlxDatatypes.Array.Builder abuilder = MysqlxDatatypes.Array.newBuilder();
        for (final CreateIndexParams.IndexField indexField : params.getFields()) {
            final MysqlxDatatypes.Object.Builder fld = MysqlxDatatypes.Object.newBuilder().addFld(MysqlxDatatypes.Object.ObjectField.newBuilder().setKey("member").setValue(ExprUtil.buildAny(indexField.getField()))).addFld(MysqlxDatatypes.Object.ObjectField.newBuilder().setKey("type").setValue(ExprUtil.buildAny(indexField.getType()))).addFld(MysqlxDatatypes.Object.ObjectField.newBuilder().setKey("required").setValue(ExprUtil.buildAny(indexField.isRequired())));
            if ("GEOJSON".equalsIgnoreCase(indexField.getType())) {
                if (indexField.getOptions() != null) {
                    fld.addFld(MysqlxDatatypes.Object.ObjectField.newBuilder().setKey("options").setValue(MysqlxDatatypes.Any.newBuilder().setType(MysqlxDatatypes.Any.Type.SCALAR).setScalar(MysqlxDatatypes.Scalar.newBuilder().setType(MysqlxDatatypes.Scalar.Type.V_UINT).setVUnsignedInt(indexField.getOptions())).build()));
                }
                if (indexField.getSrid() != null) {
                    fld.addFld(MysqlxDatatypes.Object.ObjectField.newBuilder().setKey("srid").setValue(MysqlxDatatypes.Any.newBuilder().setType(MysqlxDatatypes.Any.Type.SCALAR).setScalar(MysqlxDatatypes.Scalar.newBuilder().setType(MysqlxDatatypes.Scalar.Type.V_UINT).setVUnsignedInt(indexField.getSrid())).build()));
                }
            }
            abuilder.addValue(MysqlxDatatypes.Any.newBuilder().setType(MysqlxDatatypes.Any.Type.OBJECT).setObj(fld));
        }
        builder.addFld(MysqlxDatatypes.Object.ObjectField.newBuilder().setKey("constraint").setValue(MysqlxDatatypes.Any.newBuilder().setType(MysqlxDatatypes.Any.Type.ARRAY).setArray(abuilder)));
        return new XMessage((Message)this.buildXpluginCommand(XpluginStatementCommand.XPLUGIN_STMT_CREATE_COLLECTION_INDEX, MysqlxDatatypes.Any.newBuilder().setType(MysqlxDatatypes.Any.Type.OBJECT).setObj(builder).build()));
    }
    
    public XMessage buildDropCollectionIndex(final String schemaName, final String collectionName, final String indexName) {
        return new XMessage((Message)this.buildXpluginCommand(XpluginStatementCommand.XPLUGIN_STMT_DROP_COLLECTION_INDEX, MysqlxDatatypes.Any.newBuilder().setType(MysqlxDatatypes.Any.Type.OBJECT).setObj(MysqlxDatatypes.Object.newBuilder().addFld(MysqlxDatatypes.Object.ObjectField.newBuilder().setKey("name").setValue(ExprUtil.buildAny(indexName))).addFld(MysqlxDatatypes.Object.ObjectField.newBuilder().setKey("collection").setValue(ExprUtil.buildAny(collectionName))).addFld(MysqlxDatatypes.Object.ObjectField.newBuilder().setKey("schema").setValue(ExprUtil.buildAny(schemaName)))).build()));
    }
    
    private MysqlxSql.StmtExecute buildXpluginCommand(final XpluginStatementCommand command, final MysqlxDatatypes.Any... args) {
        final MysqlxSql.StmtExecute.Builder builder = MysqlxSql.StmtExecute.newBuilder();
        builder.setNamespace("mysqlx");
        builder.setStmt(ByteString.copyFromUtf8(command.commandName));
        Arrays.stream(args).forEach(a -> builder.addArgs(a));
        return builder.build();
    }
    
    @Override
    public XMessage buildSqlStatement(final String statement) {
        return this.buildSqlStatement(statement, (List<Object>)null);
    }
    
    @Override
    public XMessage buildSqlStatement(final String statement, final List<Object> args) {
        final MysqlxSql.StmtExecute.Builder builder = MysqlxSql.StmtExecute.newBuilder();
        if (args != null) {
            final List<MysqlxDatatypes.Any> anyArgs = new ArrayList<MysqlxDatatypes.Any>();
            args.stream().map((Function<? super Object, ?>)ExprUtil::argObjectToScalarAny).forEach(a -> anyArgs.add(a));
            builder.addAllArgs(anyArgs);
        }
        builder.setStmt(ByteString.copyFromUtf8(statement));
        return new XMessage((Message)builder.build());
    }
    
    private static void applyFilterParams(final FilterParams filterParams, final Consumer<List<MysqlxCrud.Order>> setOrder, final Consumer<MysqlxCrud.Limit> setLimit, final Consumer<MysqlxExpr.Expr> setCriteria, final Consumer<List<MysqlxDatatypes.Scalar>> setArgs) {
        filterParams.verifyAllArgsBound();
        if (filterParams.getOrder() != null) {
            setOrder.accept((List<MysqlxCrud.Order>)filterParams.getOrder());
        }
        if (filterParams.getLimit() != null) {
            final MysqlxCrud.Limit.Builder lb = MysqlxCrud.Limit.newBuilder().setRowCount(filterParams.getLimit());
            if (filterParams.getOffset() != null) {
                lb.setOffset(filterParams.getOffset());
            }
            setLimit.accept(lb.build());
        }
        if (filterParams.getCriteria() != null) {
            setCriteria.accept((MysqlxExpr.Expr)filterParams.getCriteria());
        }
        if (filterParams.getArgs() != null) {
            setArgs.accept((List<MysqlxDatatypes.Scalar>)filterParams.getArgs());
        }
    }
    
    public XMessage buildSha256MemoryAuthStart() {
        return new XMessage((Message)MysqlxSession.AuthenticateStart.newBuilder().setMechName("SHA256_MEMORY").build());
    }
    
    public XMessage buildSha256MemoryAuthContinue(final String user, final String password, final byte[] nonce, final String database) {
        final String encoding = "UTF8";
        final byte[] databaseBytes = (database == null) ? new byte[0] : StringUtils.getBytes(database, encoding);
        final byte[] userBytes = (user == null) ? new byte[0] : StringUtils.getBytes(user, encoding);
        byte[] hashedPassword;
        final byte[] passwordBytes = hashedPassword = ((password == null || password.length() == 0) ? new byte[0] : StringUtils.getBytes(password, encoding));
        try {
            hashedPassword = Security.scrambleCachingSha2(passwordBytes, nonce);
        }
        catch (DigestException e) {
            throw new RuntimeException(e);
        }
        hashedPassword = StringUtils.toHexString(hashedPassword, hashedPassword.length).getBytes();
        final byte[] reply = new byte[databaseBytes.length + userBytes.length + hashedPassword.length + 2];
        System.arraycopy(databaseBytes, 0, reply, 0, databaseBytes.length);
        int pos = databaseBytes.length;
        reply[pos++] = 0;
        System.arraycopy(userBytes, 0, reply, pos, userBytes.length);
        pos += userBytes.length;
        reply[pos++] = 0;
        System.arraycopy(hashedPassword, 0, reply, pos, hashedPassword.length);
        final MysqlxSession.AuthenticateContinue.Builder builder = MysqlxSession.AuthenticateContinue.newBuilder();
        builder.setAuthData(ByteString.copyFrom(reply));
        return new XMessage((Message)builder.build());
    }
    
    public XMessage buildMysql41AuthStart() {
        return new XMessage((Message)MysqlxSession.AuthenticateStart.newBuilder().setMechName("MYSQL41").build());
    }
    
    public XMessage buildMysql41AuthContinue(final String user, final String password, final byte[] salt, final String database) {
        final String encoding = "UTF8";
        final byte[] userBytes = (user == null) ? new byte[0] : StringUtils.getBytes(user, encoding);
        final byte[] passwordBytes = (password == null || password.length() == 0) ? new byte[0] : StringUtils.getBytes(password, encoding);
        final byte[] databaseBytes = (database == null) ? new byte[0] : StringUtils.getBytes(database, encoding);
        byte[] hashedPassword = passwordBytes;
        if (password != null && password.length() > 0) {
            hashedPassword = Security.scramble411(passwordBytes, salt);
            hashedPassword = String.format("*%040x", new BigInteger(1, hashedPassword)).getBytes();
        }
        final byte[] reply = new byte[databaseBytes.length + userBytes.length + hashedPassword.length + 2];
        System.arraycopy(databaseBytes, 0, reply, 0, databaseBytes.length);
        int pos = databaseBytes.length;
        reply[pos++] = 0;
        System.arraycopy(userBytes, 0, reply, pos, userBytes.length);
        pos += userBytes.length;
        reply[pos++] = 0;
        System.arraycopy(hashedPassword, 0, reply, pos, hashedPassword.length);
        final MysqlxSession.AuthenticateContinue.Builder builder = MysqlxSession.AuthenticateContinue.newBuilder();
        builder.setAuthData(ByteString.copyFrom(reply));
        return new XMessage((Message)builder.build());
    }
    
    public XMessage buildPlainAuthStart(final String user, final String password, final String database) {
        final CallbackHandler callbackHandler = new CallbackHandler() {
            @Override
            public void handle(final Callback[] callbacks) throws UnsupportedCallbackException {
                for (final Callback c : callbacks) {
                    if (NameCallback.class.isAssignableFrom(c.getClass())) {
                        ((NameCallback)c).setName(user);
                    }
                    else {
                        if (!PasswordCallback.class.isAssignableFrom(c.getClass())) {
                            throw new UnsupportedCallbackException(c);
                        }
                        ((PasswordCallback)c).setPassword(password.toCharArray());
                    }
                }
            }
        };
        try {
            final String[] mechanisms = { "PLAIN" };
            final String authorizationId = (database == null || database.trim().length() == 0) ? null : database;
            final String protocol = "X Protocol";
            final Map<String, ?> props = null;
            final String serverName = "<unknown>";
            final SaslClient saslClient = Sasl.createSaslClient(mechanisms, authorizationId, protocol, serverName, props, callbackHandler);
            final MysqlxSession.AuthenticateStart.Builder authStartBuilder = MysqlxSession.AuthenticateStart.newBuilder();
            authStartBuilder.setMechName("PLAIN");
            authStartBuilder.setAuthData(ByteString.copyFrom(saslClient.evaluateChallenge(null)));
            return new XMessage((Message)authStartBuilder.build());
        }
        catch (SaslException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public XMessage buildExternalAuthStart(final String database) {
        final CallbackHandler callbackHandler = new CallbackHandler() {
            @Override
            public void handle(final Callback[] callbacks) throws UnsupportedCallbackException {
                final int length = callbacks.length;
                final int n = 0;
                if (n >= length) {
                    return;
                }
                final Callback c = callbacks[n];
                if (NameCallback.class.isAssignableFrom(c.getClass())) {
                    throw new UnsupportedCallbackException(c);
                }
                if (PasswordCallback.class.isAssignableFrom(c.getClass())) {
                    throw new UnsupportedCallbackException(c);
                }
                throw new UnsupportedCallbackException(c);
            }
        };
        try {
            final String[] mechanisms = { "EXTERNAL" };
            final String authorizationId = (database == null || database.trim().length() == 0) ? null : database;
            final String protocol = "X Protocol";
            final Map<String, ?> props = null;
            final String serverName = "<unknown>";
            final SaslClient saslClient = Sasl.createSaslClient(mechanisms, authorizationId, protocol, serverName, props, callbackHandler);
            final MysqlxSession.AuthenticateStart.Builder authStartBuilder = MysqlxSession.AuthenticateStart.newBuilder();
            authStartBuilder.setMechName("EXTERNAL");
            authStartBuilder.setAuthData(ByteString.copyFrom(saslClient.evaluateChallenge(null)));
            return new XMessage((Message)authStartBuilder.build());
        }
        catch (SaslException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public XMessage buildSessionReset() {
        return new XMessage((Message)MysqlxSession.Reset.newBuilder().build());
    }
}
