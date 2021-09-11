
package com.mysql.cj.jdbc;

import com.mysql.cj.Messages;
import com.mysql.cj.conf.AbstractRuntimeProperty;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;
import com.mysql.cj.conf.ConnectionUrl;
import javax.naming.NamingException;
import javax.naming.RefAddr;
import javax.naming.StringRefAddr;
import com.mysql.cj.conf.RuntimeProperty;
import java.util.Iterator;
import com.mysql.cj.exceptions.ExceptionInterceptor;
import com.mysql.cj.conf.PropertyDefinitions;
import javax.naming.Reference;
import java.util.Properties;
import com.mysql.cj.conf.PropertyKey;
import java.sql.SQLException;
import com.mysql.cj.exceptions.CJException;
import com.mysql.cj.jdbc.exceptions.SQLExceptionsMapping;
import java.sql.Connection;
import java.io.PrintWriter;
import java.io.Serializable;
import javax.naming.Referenceable;
import javax.sql.DataSource;

public class MysqlDataSource extends JdbcPropertySetImpl implements DataSource, Referenceable, Serializable, JdbcPropertySet
{
    static final long serialVersionUID = -5515846944416881264L;
    protected static final NonRegisteringDriver mysqlDriver;
    protected transient PrintWriter logWriter;
    protected String databaseName;
    protected String encoding;
    protected String hostName;
    protected String password;
    protected String profileSQLString;
    protected String url;
    protected String user;
    protected boolean explicitUrl;
    protected int port;
    protected String description;
    
    public MysqlDataSource() {
        this.logWriter = null;
        this.databaseName = null;
        this.encoding = null;
        this.hostName = null;
        this.password = null;
        this.profileSQLString = "false";
        this.url = null;
        this.user = null;
        this.explicitUrl = false;
        this.port = 3306;
        this.description = "MySQL Connector/J Data Source";
    }
    
    @Override
    public Connection getConnection() throws SQLException {
        try {
            return this.getConnection(this.user, this.password);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex);
        }
    }
    
    @Override
    public Connection getConnection(final String userID, final String pass) throws SQLException {
        try {
            final Properties props = this.exposeAsProperties();
            if (userID != null) {
                props.setProperty(PropertyKey.USER.getKeyName(), userID);
            }
            if (pass != null) {
                props.setProperty(PropertyKey.PASSWORD.getKeyName(), pass);
            }
            return this.getConnection(props);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex);
        }
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(final String value) {
        this.description = value;
    }
    
    public void setDatabaseName(final String dbName) {
        this.databaseName = dbName;
    }
    
    public String getDatabaseName() {
        return (this.databaseName != null) ? this.databaseName : "";
    }
    
    @Override
    public void setLogWriter(final PrintWriter output) throws SQLException {
        try {
            this.logWriter = output;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex);
        }
    }
    
    @Override
    public PrintWriter getLogWriter() {
        try {
            return this.logWriter;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex);
        }
    }
    
    @Override
    public void setLoginTimeout(final int seconds) throws SQLException {
    }
    
    @Override
    public int getLoginTimeout() {
        try {
            return 0;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex);
        }
    }
    
    public void setPassword(final String pass) {
        this.password = pass;
    }
    
    public String getPassword() {
        return this.password;
    }
    
    public void setPort(final int p) {
        this.port = p;
    }
    
    public int getPort() {
        return this.port;
    }
    
    public void setPortNumber(final int p) {
        this.setPort(p);
    }
    
    public int getPortNumber() {
        return this.getPort();
    }
    
    public void setPropertiesViaRef(final Reference ref) throws SQLException {
        for (final PropertyKey propKey : PropertyDefinitions.PROPERTY_KEY_TO_PROPERTY_DEFINITION.keySet()) {
            final RuntimeProperty<?> propToSet = this.getProperty(propKey);
            if (ref != null) {
                propToSet.initializeFrom(ref, null);
            }
        }
        this.postInitialization();
    }
    
    @Override
    public Reference getReference() throws NamingException {
        final String factoryName = MysqlDataSourceFactory.class.getName();
        final Reference ref = new Reference(this.getClass().getName(), factoryName, null);
        ref.add(new StringRefAddr(PropertyKey.USER.getKeyName(), this.getUser()));
        ref.add(new StringRefAddr(PropertyKey.PASSWORD.getKeyName(), this.password));
        ref.add(new StringRefAddr("serverName", this.getServerName()));
        ref.add(new StringRefAddr("port", "" + this.getPort()));
        ref.add(new StringRefAddr("databaseName", this.getDatabaseName()));
        ref.add(new StringRefAddr("url", this.getUrl()));
        ref.add(new StringRefAddr("explicitUrl", String.valueOf(this.explicitUrl)));
        for (final PropertyKey propKey : PropertyDefinitions.PROPERTY_KEY_TO_PROPERTY_DEFINITION.keySet()) {
            final RuntimeProperty<?> propToStore = this.getProperty(propKey);
            final String val = propToStore.getStringValue();
            if (val != null) {
                ref.add(new StringRefAddr(propToStore.getPropertyDefinition().getName(), val));
            }
        }
        return ref;
    }
    
    public void setServerName(final String serverName) {
        this.hostName = serverName;
    }
    
    public String getServerName() {
        return (this.hostName != null) ? this.hostName : "";
    }
    
    public void setURL(final String url) {
        this.setUrl(url);
    }
    
    public String getURL() {
        return this.getUrl();
    }
    
    public void setUrl(final String url) {
        this.url = url;
        this.explicitUrl = true;
    }
    
    public String getUrl() {
        if (!this.explicitUrl) {
            final StringBuilder sbUrl = new StringBuilder(ConnectionUrl.Type.SINGLE_CONNECTION.getScheme());
            sbUrl.append("//").append(this.getServerName()).append(":").append(this.getPort()).append("/").append(this.getDatabaseName());
            return sbUrl.toString();
        }
        return this.url;
    }
    
    public void setUser(final String userID) {
        this.user = userID;
    }
    
    public String getUser() {
        return this.user;
    }
    
    protected Connection getConnection(final Properties props) throws SQLException {
        final String jdbcUrlToUse = this.explicitUrl ? this.url : this.getUrl();
        final ConnectionUrl connUrl = ConnectionUrl.getConnectionUrlInstance(jdbcUrlToUse, null);
        final Properties urlProps = connUrl.getConnectionArgumentsAsProperties();
        urlProps.remove(PropertyKey.HOST.getKeyName());
        urlProps.remove(PropertyKey.PORT.getKeyName());
        urlProps.remove(PropertyKey.DBNAME.getKeyName());
        urlProps.stringPropertyNames().stream().forEach(k -> props.setProperty(k, urlProps.getProperty(k)));
        return MysqlDataSource.mysqlDriver.connect(jdbcUrlToUse, props);
    }
    
    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }
    
    @Override
    public <T> T unwrap(final Class<T> iface) throws SQLException {
        try {
            return null;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex);
        }
    }
    
    @Override
    public boolean isWrapperFor(final Class<?> iface) throws SQLException {
        try {
            return false;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex);
        }
    }
    
    protected String getStringRuntimeProperty(final String name) throws SQLException {
        try {
            return this.getStringProperty(name).getValue();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex);
        }
    }
    
    protected void setStringRuntimeProperty(final String name, final String value) throws SQLException {
        try {
            ((AbstractRuntimeProperty)this.getStringProperty(name)).setValueInternal(value, null, null);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex);
        }
    }
    
    protected boolean getBooleanRuntimeProperty(final String name) throws SQLException {
        try {
            return this.getBooleanProperty(name).getValue();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex);
        }
    }
    
    protected void setBooleanRuntimeProperty(final String name, final boolean value) throws SQLException {
        try {
            ((AbstractRuntimeProperty)this.getBooleanProperty(name)).setValueInternal(value, null, null);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex);
        }
    }
    
    protected int getIntegerRuntimeProperty(final String name) throws SQLException {
        try {
            return this.getIntegerProperty(name).getValue();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex);
        }
    }
    
    protected void setIntegerRuntimeProperty(final String name, final int value) throws SQLException {
        try {
            ((AbstractRuntimeProperty)this.getIntegerProperty(name)).setValueInternal(value, null, null);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex);
        }
    }
    
    protected long getLongRuntimeProperty(final String name) throws SQLException {
        try {
            return this.getLongProperty(name).getValue();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex);
        }
    }
    
    protected void setLongRuntimeProperty(final String name, final long value) throws SQLException {
        try {
            ((AbstractRuntimeProperty)this.getLongProperty(name)).setValueInternal(value, null, null);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex);
        }
    }
    
    protected int getMemorySizeRuntimeProperty(final String name) throws SQLException {
        try {
            return this.getMemorySizeProperty(name).getValue();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex);
        }
    }
    
    protected void setMemorySizeRuntimeProperty(final String name, final int value) throws SQLException {
        try {
            ((AbstractRuntimeProperty)this.getMemorySizeProperty(name)).setValueInternal(value, null, null);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex);
        }
    }
    
    protected String getEnumRuntimeProperty(final String name) throws SQLException {
        try {
            return this.getEnumProperty(name).getStringValue();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex);
        }
    }
    
    protected void setEnumRuntimeProperty(final String name, final String value) throws SQLException {
        try {
            ((AbstractRuntimeProperty)this.getEnumProperty(name)).setValueInternal(value, null);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex);
        }
    }
    
    @Override
    public Properties exposeAsProperties() {
        final Properties props = new Properties();
        for (final PropertyKey propKey : PropertyDefinitions.PROPERTY_KEY_TO_PROPERTY_DEFINITION.keySet()) {
            final RuntimeProperty<?> propToGet = this.getProperty(propKey);
            final String propValue = propToGet.getStringValue();
            if (propValue != null && propToGet.isExplicitlySet()) {
                props.setProperty(propToGet.getPropertyDefinition().getName(), propValue);
            }
        }
        return props;
    }
    
    static {
        try {
            mysqlDriver = new NonRegisteringDriver();
        }
        catch (Exception E) {
            throw new RuntimeException(Messages.getString("MysqlDataSource.0"));
        }
    }
    
    public boolean getParanoid() throws SQLException {
        return this.getBooleanRuntimeProperty("paranoid");
    }
    
    public void setParanoid(final boolean value) throws SQLException {
        this.setBooleanRuntimeProperty("paranoid", value);
    }
    
    public boolean getAllowMasterDownConnections() throws SQLException {
        return this.getBooleanRuntimeProperty("allowMasterDownConnections");
    }
    
    public void setAllowMasterDownConnections(final boolean value) throws SQLException {
        this.setBooleanRuntimeProperty("allowMasterDownConnections", value);
    }
    
    public String getLoadBalanceAutoCommitStatementRegex() throws SQLException {
        return this.getStringRuntimeProperty("loadBalanceAutoCommitStatementRegex");
    }
    
    public void setLoadBalanceAutoCommitStatementRegex(final String value) throws SQLException {
        this.setStringRuntimeProperty("loadBalanceAutoCommitStatementRegex", value);
    }
    
    public String getLoadBalanceExceptionChecker() throws SQLException {
        return this.getStringRuntimeProperty("loadBalanceExceptionChecker");
    }
    
    public void setLoadBalanceExceptionChecker(final String value) throws SQLException {
        this.setStringRuntimeProperty("loadBalanceExceptionChecker", value);
    }
    
    public boolean getIncludeThreadDumpInDeadlockExceptions() throws SQLException {
        return this.getBooleanRuntimeProperty("includeThreadDumpInDeadlockExceptions");
    }
    
    public void setIncludeThreadDumpInDeadlockExceptions(final boolean value) throws SQLException {
        this.setBooleanRuntimeProperty("includeThreadDumpInDeadlockExceptions", value);
    }
    
    public String getServerConfigCacheFactory() throws SQLException {
        return this.getStringRuntimeProperty("serverConfigCacheFactory");
    }
    
    public void setServerConfigCacheFactory(final String value) throws SQLException {
        this.setStringRuntimeProperty("serverConfigCacheFactory", value);
    }
    
    public String getPasswordCharacterEncoding() throws SQLException {
        return this.getStringRuntimeProperty("passwordCharacterEncoding");
    }
    
    public void setPasswordCharacterEncoding(final String value) throws SQLException {
        this.setStringRuntimeProperty("passwordCharacterEncoding", value);
    }
    
    public boolean getReadFromMasterWhenNoSlaves() throws SQLException {
        return this.getBooleanRuntimeProperty("readFromMasterWhenNoSlaves");
    }
    
    public void setReadFromMasterWhenNoSlaves(final boolean value) throws SQLException {
        this.setBooleanRuntimeProperty("readFromMasterWhenNoSlaves", value);
    }
    
    public boolean getHaEnableJMX() throws SQLException {
        return this.getBooleanRuntimeProperty("haEnableJMX");
    }
    
    public void setHaEnableJMX(final boolean value) throws SQLException {
        this.setBooleanRuntimeProperty("haEnableJMX", value);
    }
    
    public boolean getClobberStreamingResults() throws SQLException {
        return this.getBooleanRuntimeProperty("clobberStreamingResults");
    }
    
    public void setClobberStreamingResults(final boolean value) throws SQLException {
        this.setBooleanRuntimeProperty("clobberStreamingResults", value);
    }
    
    public String getCharacterSetResults() throws SQLException {
        return this.getStringRuntimeProperty("characterSetResults");
    }
    
    public void setCharacterSetResults(final String value) throws SQLException {
        this.setStringRuntimeProperty("characterSetResults", value);
    }
    
    public boolean getProcessEscapeCodesForPrepStmts() throws SQLException {
        return this.getBooleanRuntimeProperty("processEscapeCodesForPrepStmts");
    }
    
    public void setProcessEscapeCodesForPrepStmts(final boolean value) throws SQLException {
        this.setBooleanRuntimeProperty("processEscapeCodesForPrepStmts", value);
    }
    
    public String getSocksProxyHost() throws SQLException {
        return this.getStringRuntimeProperty("socksProxyHost");
    }
    
    public void setSocksProxyHost(final String value) throws SQLException {
        this.setStringRuntimeProperty("socksProxyHost", value);
    }
    
    public int getMaxAllowedPacket() throws SQLException {
        return this.getIntegerRuntimeProperty("maxAllowedPacket");
    }
    
    public void setMaxAllowedPacket(final int value) throws SQLException {
        this.setIntegerRuntimeProperty("maxAllowedPacket", value);
    }
    
    public String getTrustCertificateKeyStoreType() throws SQLException {
        return this.getStringRuntimeProperty("trustCertificateKeyStoreType");
    }
    
    public void setTrustCertificateKeyStoreType(final String value) throws SQLException {
        this.setStringRuntimeProperty("trustCertificateKeyStoreType", value);
    }
    
    public boolean getAllowPublicKeyRetrieval() throws SQLException {
        return this.getBooleanRuntimeProperty("allowPublicKeyRetrieval");
    }
    
    public void setAllowPublicKeyRetrieval(final boolean value) throws SQLException {
        this.setBooleanRuntimeProperty("allowPublicKeyRetrieval", value);
    }
    
    public boolean getCachePrepStmts() throws SQLException {
        return this.getBooleanRuntimeProperty("cachePrepStmts");
    }
    
    public void setCachePrepStmts(final boolean value) throws SQLException {
        this.setBooleanRuntimeProperty("cachePrepStmts", value);
    }
    
    public String getExceptionInterceptors() throws SQLException {
        return this.getStringRuntimeProperty("exceptionInterceptors");
    }
    
    public void setExceptionInterceptors(final String value) throws SQLException {
        this.setStringRuntimeProperty("exceptionInterceptors", value);
    }
    
    public int getLoadBalanceBlacklistTimeout() throws SQLException {
        return this.getIntegerRuntimeProperty("loadBalanceBlacklistTimeout");
    }
    
    public void setLoadBalanceBlacklistTimeout(final int value) throws SQLException {
        this.setIntegerRuntimeProperty("loadBalanceBlacklistTimeout", value);
    }
    
    public boolean getTransformedBitIsBoolean() throws SQLException {
        return this.getBooleanRuntimeProperty("transformedBitIsBoolean");
    }
    
    public void setTransformedBitIsBoolean(final boolean value) throws SQLException {
        this.setBooleanRuntimeProperty("transformedBitIsBoolean", value);
    }
    
    public int getConnectTimeout() throws SQLException {
        return this.getIntegerRuntimeProperty("connectTimeout");
    }
    
    public void setConnectTimeout(final int value) throws SQLException {
        this.setIntegerRuntimeProperty("connectTimeout", value);
    }
    
    public String getDisabledAuthenticationPlugins() throws SQLException {
        return this.getStringRuntimeProperty("disabledAuthenticationPlugins");
    }
    
    public void setDisabledAuthenticationPlugins(final String value) throws SQLException {
        this.setStringRuntimeProperty("disabledAuthenticationPlugins", value);
    }
    
    public boolean getAutoClosePStmtStreams() throws SQLException {
        return this.getBooleanRuntimeProperty("autoClosePStmtStreams");
    }
    
    public void setAutoClosePStmtStreams(final boolean value) throws SQLException {
        this.setBooleanRuntimeProperty("autoClosePStmtStreams", value);
    }
    
    public boolean getPadCharsWithSpace() throws SQLException {
        return this.getBooleanRuntimeProperty("padCharsWithSpace");
    }
    
    public void setPadCharsWithSpace(final boolean value) throws SQLException {
        this.setBooleanRuntimeProperty("padCharsWithSpace", value);
    }
    
    public String getEnabledSSLCipherSuites() throws SQLException {
        return this.getStringRuntimeProperty("enabledSSLCipherSuites");
    }
    
    public void setEnabledSSLCipherSuites(final String value) throws SQLException {
        this.setStringRuntimeProperty("enabledSSLCipherSuites", value);
    }
    
    public boolean getCacheResultSetMetadata() throws SQLException {
        return this.getBooleanRuntimeProperty("cacheResultSetMetadata");
    }
    
    public void setCacheResultSetMetadata(final boolean value) throws SQLException {
        this.setBooleanRuntimeProperty("cacheResultSetMetadata", value);
    }
    
    public boolean getUseServerPrepStmts() throws SQLException {
        return this.getBooleanRuntimeProperty("useServerPrepStmts");
    }
    
    public void setUseServerPrepStmts(final boolean value) throws SQLException {
        this.setBooleanRuntimeProperty("useServerPrepStmts", value);
    }
    
    public int getQueriesBeforeRetryMaster() throws SQLException {
        return this.getIntegerRuntimeProperty("queriesBeforeRetryMaster");
    }
    
    public void setQueriesBeforeRetryMaster(final int value) throws SQLException {
        this.setIntegerRuntimeProperty("queriesBeforeRetryMaster", value);
    }
    
    public boolean getInteractiveClient() throws SQLException {
        return this.getBooleanRuntimeProperty("interactiveClient");
    }
    
    public void setInteractiveClient(final boolean value) throws SQLException {
        this.setBooleanRuntimeProperty("interactiveClient", value);
    }
    
    public boolean getBlobsAreStrings() throws SQLException {
        return this.getBooleanRuntimeProperty("blobsAreStrings");
    }
    
    public void setBlobsAreStrings(final boolean value) throws SQLException {
        this.setBooleanRuntimeProperty("blobsAreStrings", value);
    }
    
    public boolean getExplainSlowQueries() throws SQLException {
        return this.getBooleanRuntimeProperty("explainSlowQueries");
    }
    
    public void setExplainSlowQueries(final boolean value) throws SQLException {
        this.setBooleanRuntimeProperty("explainSlowQueries", value);
    }
    
    public String getClientCertificateKeyStorePassword() throws SQLException {
        return this.getStringRuntimeProperty("clientCertificateKeyStorePassword");
    }
    
    public void setClientCertificateKeyStorePassword(final String value) throws SQLException {
        this.setStringRuntimeProperty("clientCertificateKeyStorePassword", value);
    }
    
    public String getServerTimezone() throws SQLException {
        return this.getStringRuntimeProperty("serverTimezone");
    }
    
    public void setServerTimezone(final String value) throws SQLException {
        this.setStringRuntimeProperty("serverTimezone", value);
    }
    
    public boolean getUseAffectedRows() throws SQLException {
        return this.getBooleanRuntimeProperty("useAffectedRows");
    }
    
    public void setUseAffectedRows(final boolean value) throws SQLException {
        this.setBooleanRuntimeProperty("useAffectedRows", value);
    }
    
    public boolean getIgnoreNonTxTables() throws SQLException {
        return this.getBooleanRuntimeProperty("ignoreNonTxTables");
    }
    
    public void setIgnoreNonTxTables(final boolean value) throws SQLException {
        this.setBooleanRuntimeProperty("ignoreNonTxTables", value);
    }
    
    public boolean getNoDatetimeStringSync() throws SQLException {
        return this.getBooleanRuntimeProperty("noDatetimeStringSync");
    }
    
    public void setNoDatetimeStringSync(final boolean value) throws SQLException {
        this.setBooleanRuntimeProperty("noDatetimeStringSync", value);
    }
    
    public int getSocketTimeout() throws SQLException {
        return this.getIntegerRuntimeProperty("socketTimeout");
    }
    
    public void setSocketTimeout(final int value) throws SQLException {
        this.setIntegerRuntimeProperty("socketTimeout", value);
    }
    
    public boolean getUseLocalSessionState() throws SQLException {
        return this.getBooleanRuntimeProperty("useLocalSessionState");
    }
    
    public void setUseLocalSessionState(final boolean value) throws SQLException {
        this.setBooleanRuntimeProperty("useLocalSessionState", value);
    }
    
    public String getHaLoadBalanceStrategy() throws SQLException {
        return this.getStringRuntimeProperty("haLoadBalanceStrategy");
    }
    
    public void setHaLoadBalanceStrategy(final String value) throws SQLException {
        this.setStringRuntimeProperty("haLoadBalanceStrategy", value);
    }
    
    public String getEnabledTLSProtocols() throws SQLException {
        return this.getStringRuntimeProperty("enabledTLSProtocols");
    }
    
    public void setEnabledTLSProtocols(final String value) throws SQLException {
        this.setStringRuntimeProperty("enabledTLSProtocols", value);
    }
    
    public boolean getAlwaysSendSetIsolation() throws SQLException {
        return this.getBooleanRuntimeProperty("alwaysSendSetIsolation");
    }
    
    public void setAlwaysSendSetIsolation(final boolean value) throws SQLException {
        this.setBooleanRuntimeProperty("alwaysSendSetIsolation", value);
    }
    
    public int getSelfDestructOnPingSecondsLifetime() throws SQLException {
        return this.getIntegerRuntimeProperty("selfDestructOnPingSecondsLifetime");
    }
    
    public void setSelfDestructOnPingSecondsLifetime(final int value) throws SQLException {
        this.setIntegerRuntimeProperty("selfDestructOnPingSecondsLifetime", value);
    }
    
    public String getTrustCertificateKeyStorePassword() throws SQLException {
        return this.getStringRuntimeProperty("trustCertificateKeyStorePassword");
    }
    
    public void setTrustCertificateKeyStorePassword(final String value) throws SQLException {
        this.setStringRuntimeProperty("trustCertificateKeyStorePassword", value);
    }
    
    public boolean getEmulateUnsupportedPstmts() throws SQLException {
        return this.getBooleanRuntimeProperty("emulateUnsupportedPstmts");
    }
    
    public void setEmulateUnsupportedPstmts(final boolean value) throws SQLException {
        this.setBooleanRuntimeProperty("emulateUnsupportedPstmts", value);
    }
    
    public boolean getUseColumnNamesInFindColumn() throws SQLException {
        return this.getBooleanRuntimeProperty("useColumnNamesInFindColumn");
    }
    
    public void setUseColumnNamesInFindColumn(final boolean value) throws SQLException {
        this.setBooleanRuntimeProperty("useColumnNamesInFindColumn", value);
    }
    
    public boolean getUseReadAheadInput() throws SQLException {
        return this.getBooleanRuntimeProperty("useReadAheadInput");
    }
    
    public void setUseReadAheadInput(final boolean value) throws SQLException {
        this.setBooleanRuntimeProperty("useReadAheadInput", value);
    }
    
    public int getPacketDebugBufferSize() throws SQLException {
        return this.getIntegerRuntimeProperty("packetDebugBufferSize");
    }
    
    public void setPacketDebugBufferSize(final int value) throws SQLException {
        this.setIntegerRuntimeProperty("packetDebugBufferSize", value);
    }
    
    public boolean getTinyInt1isBit() throws SQLException {
        return this.getBooleanRuntimeProperty("tinyInt1isBit");
    }
    
    public void setTinyInt1isBit(final boolean value) throws SQLException {
        this.setBooleanRuntimeProperty("tinyInt1isBit", value);
    }
    
    public boolean getUseStreamLengthsInPrepStmts() throws SQLException {
        return this.getBooleanRuntimeProperty("useStreamLengthsInPrepStmts");
    }
    
    public void setUseStreamLengthsInPrepStmts(final boolean value) throws SQLException {
        this.setBooleanRuntimeProperty("useStreamLengthsInPrepStmts", value);
    }
    
    public boolean getFunctionsNeverReturnBlobs() throws SQLException {
        return this.getBooleanRuntimeProperty("functionsNeverReturnBlobs");
    }
    
    public void setFunctionsNeverReturnBlobs(final boolean value) throws SQLException {
        this.setBooleanRuntimeProperty("functionsNeverReturnBlobs", value);
    }
    
    public int getLoadBalancePingTimeout() throws SQLException {
        return this.getIntegerRuntimeProperty("loadBalancePingTimeout");
    }
    
    public void setLoadBalancePingTimeout(final int value) throws SQLException {
        this.setIntegerRuntimeProperty("loadBalancePingTimeout", value);
    }
    
    public boolean getXdevapiUseAsyncProtocol() throws SQLException {
        return this.getBooleanRuntimeProperty("xdevapiUseAsyncProtocol");
    }
    
    public void setXdevapiUseAsyncProtocol(final boolean value) throws SQLException {
        this.setBooleanRuntimeProperty("xdevapiUseAsyncProtocol", value);
    }
    
    public boolean getUseCursorFetch() throws SQLException {
        return this.getBooleanRuntimeProperty("useCursorFetch");
    }
    
    public void setUseCursorFetch(final boolean value) throws SQLException {
        this.setBooleanRuntimeProperty("useCursorFetch", value);
    }
    
    public int getCallableStmtCacheSize() throws SQLException {
        return this.getIntegerRuntimeProperty("callableStmtCacheSize");
    }
    
    public void setCallableStmtCacheSize(final int value) throws SQLException {
        this.setIntegerRuntimeProperty("callableStmtCacheSize", value);
    }
    
    public boolean getYearIsDateType() throws SQLException {
        return this.getBooleanRuntimeProperty("yearIsDateType");
    }
    
    public void setYearIsDateType(final boolean value) throws SQLException {
        this.setBooleanRuntimeProperty("yearIsDateType", value);
    }
    
    public String getReplicationConnectionGroup() throws SQLException {
        return this.getStringRuntimeProperty("replicationConnectionGroup");
    }
    
    public void setReplicationConnectionGroup(final String value) throws SQLException {
        this.setStringRuntimeProperty("replicationConnectionGroup", value);
    }
    
    public String getLoadBalanceSQLExceptionSubclassFailover() throws SQLException {
        return this.getStringRuntimeProperty("loadBalanceSQLExceptionSubclassFailover");
    }
    
    public void setLoadBalanceSQLExceptionSubclassFailover(final String value) throws SQLException {
        this.setStringRuntimeProperty("loadBalanceSQLExceptionSubclassFailover", value);
    }
    
    public boolean getPedantic() throws SQLException {
        return this.getBooleanRuntimeProperty("pedantic");
    }
    
    public void setPedantic(final boolean value) throws SQLException {
        this.setBooleanRuntimeProperty("pedantic", value);
    }
    
    public int getSecondsBeforeRetryMaster() throws SQLException {
        return this.getIntegerRuntimeProperty("secondsBeforeRetryMaster");
    }
    
    public void setSecondsBeforeRetryMaster(final int value) throws SQLException {
        this.setIntegerRuntimeProperty("secondsBeforeRetryMaster", value);
    }
    
    public boolean getJdbcCompliantTruncation() throws SQLException {
        return this.getBooleanRuntimeProperty("jdbcCompliantTruncation");
    }
    
    public void setJdbcCompliantTruncation(final boolean value) throws SQLException {
        this.setBooleanRuntimeProperty("jdbcCompliantTruncation", value);
    }
    
    public boolean getUseNanosForElapsedTime() throws SQLException {
        return this.getBooleanRuntimeProperty("useNanosForElapsedTime");
    }
    
    public void setUseNanosForElapsedTime(final boolean value) throws SQLException {
        this.setBooleanRuntimeProperty("useNanosForElapsedTime", value);
    }
    
    public int getXdevapiConnectTimeout() throws SQLException {
        return this.getIntegerRuntimeProperty("xdevapiConnectTimeout");
    }
    
    public void setXdevapiConnectTimeout(final int value) throws SQLException {
        this.setIntegerRuntimeProperty("xdevapiConnectTimeout", value);
    }
    
    public boolean getCompensateOnDuplicateKeyUpdateCounts() throws SQLException {
        return this.getBooleanRuntimeProperty("compensateOnDuplicateKeyUpdateCounts");
    }
    
    public void setCompensateOnDuplicateKeyUpdateCounts(final boolean value) throws SQLException {
        this.setBooleanRuntimeProperty("compensateOnDuplicateKeyUpdateCounts", value);
    }
    
    public boolean getRequireSSL() throws SQLException {
        return this.getBooleanRuntimeProperty("requireSSL");
    }
    
    public void setRequireSSL(final boolean value) throws SQLException {
        this.setBooleanRuntimeProperty("requireSSL", value);
    }
    
    public boolean getElideSetAutoCommits() throws SQLException {
        return this.getBooleanRuntimeProperty("elideSetAutoCommits");
    }
    
    public void setElideSetAutoCommits(final boolean value) throws SQLException {
        this.setBooleanRuntimeProperty("elideSetAutoCommits", value);
    }
    
    public int getXdevapiAsyncResponseTimeout() throws SQLException {
        return this.getIntegerRuntimeProperty("xdevapiAsyncResponseTimeout");
    }
    
    public void setXdevapiAsyncResponseTimeout(final int value) throws SQLException {
        this.setIntegerRuntimeProperty("xdevapiAsyncResponseTimeout", value);
    }
    
    public String getXdevapiSSLTruststorePassword() throws SQLException {
        return this.getStringRuntimeProperty("xdevapiSSLTruststorePassword");
    }
    
    public void setXdevapiSSLTruststorePassword(final String value) throws SQLException {
        this.setStringRuntimeProperty("xdevapiSSLTruststorePassword", value);
    }
    
    public String getLoadBalanceConnectionGroup() throws SQLException {
        return this.getStringRuntimeProperty("loadBalanceConnectionGroup");
    }
    
    public void setLoadBalanceConnectionGroup(final String value) throws SQLException {
        this.setStringRuntimeProperty("loadBalanceConnectionGroup", value);
    }
    
    public String getLoadBalanceSQLStateFailover() throws SQLException {
        return this.getStringRuntimeProperty("loadBalanceSQLStateFailover");
    }
    
    public void setLoadBalanceSQLStateFailover(final String value) throws SQLException {
        this.setStringRuntimeProperty("loadBalanceSQLStateFailover", value);
    }
    
    public boolean getLogSlowQueries() throws SQLException {
        return this.getBooleanRuntimeProperty("logSlowQueries");
    }
    
    public void setLogSlowQueries(final boolean value) throws SQLException {
        this.setBooleanRuntimeProperty("logSlowQueries", value);
    }
    
    public int getPrepStmtCacheSqlLimit() throws SQLException {
        return this.getIntegerRuntimeProperty("prepStmtCacheSqlLimit");
    }
    
    public void setPrepStmtCacheSqlLimit(final int value) throws SQLException {
        this.setIntegerRuntimeProperty("prepStmtCacheSqlLimit", value);
    }
    
    public boolean getStrictUpdates() throws SQLException {
        return this.getBooleanRuntimeProperty("strictUpdates");
    }
    
    public void setStrictUpdates(final boolean value) throws SQLException {
        this.setBooleanRuntimeProperty("strictUpdates", value);
    }
    
    public boolean getEnableEscapeProcessing() throws SQLException {
        return this.getBooleanRuntimeProperty("enableEscapeProcessing");
    }
    
    public void setEnableEscapeProcessing(final boolean value) throws SQLException {
        this.setBooleanRuntimeProperty("enableEscapeProcessing", value);
    }
    
    public boolean getGatherPerfMetrics() throws SQLException {
        return this.getBooleanRuntimeProperty("gatherPerfMetrics");
    }
    
    public void setGatherPerfMetrics(final boolean value) throws SQLException {
        this.setBooleanRuntimeProperty("gatherPerfMetrics", value);
    }
    
    public boolean getGetProceduresReturnsFunctions() throws SQLException {
        return this.getBooleanRuntimeProperty("getProceduresReturnsFunctions");
    }
    
    public void setGetProceduresReturnsFunctions(final boolean value) throws SQLException {
        this.setBooleanRuntimeProperty("getProceduresReturnsFunctions", value);
    }
    
    public int getLargeRowSizeThreshold() throws SQLException {
        return this.getIntegerRuntimeProperty("largeRowSizeThreshold");
    }
    
    public void setLargeRowSizeThreshold(final int value) throws SQLException {
        this.setIntegerRuntimeProperty("largeRowSizeThreshold", value);
    }
    
    public boolean getUseLocalTransactionState() throws SQLException {
        return this.getBooleanRuntimeProperty("useLocalTransactionState");
    }
    
    public void setUseLocalTransactionState(final boolean value) throws SQLException {
        this.setBooleanRuntimeProperty("useLocalTransactionState", value);
    }
    
    public boolean getEnableQueryTimeouts() throws SQLException {
        return this.getBooleanRuntimeProperty("enableQueryTimeouts");
    }
    
    public void setEnableQueryTimeouts(final boolean value) throws SQLException {
        this.setBooleanRuntimeProperty("enableQueryTimeouts", value);
    }
    
    public String getSslMode() throws SQLException {
        return this.getEnumRuntimeProperty("sslMode");
    }
    
    public void setSslMode(final String value) throws SQLException {
        this.setEnumRuntimeProperty("sslMode", value);
    }
    
    public int getMaxRows() throws SQLException {
        return this.getIntegerRuntimeProperty("maxRows");
    }
    
    public void setMaxRows(final int value) throws SQLException {
        this.setIntegerRuntimeProperty("maxRows", value);
    }
    
    public String getDefaultAuthenticationPlugin() throws SQLException {
        return this.getStringRuntimeProperty("defaultAuthenticationPlugin");
    }
    
    public void setDefaultAuthenticationPlugin(final String value) throws SQLException {
        this.setStringRuntimeProperty("defaultAuthenticationPlugin", value);
    }
    
    public int getNetTimeoutForStreamingResults() throws SQLException {
        return this.getIntegerRuntimeProperty("netTimeoutForStreamingResults");
    }
    
    public void setNetTimeoutForStreamingResults(final int value) throws SQLException {
        this.setIntegerRuntimeProperty("netTimeoutForStreamingResults", value);
    }
    
    public String getXdevapiSSLTruststore() throws SQLException {
        return this.getStringRuntimeProperty("xdevapiSSLTruststore");
    }
    
    public void setXdevapiSSLTruststore(final String value) throws SQLException {
        this.setStringRuntimeProperty("xdevapiSSLTruststore", value);
    }
    
    public int getSocksProxyPort() throws SQLException {
        return this.getIntegerRuntimeProperty("socksProxyPort");
    }
    
    public void setSocksProxyPort(final int value) throws SQLException {
        this.setIntegerRuntimeProperty("socksProxyPort", value);
    }
    
    public String getClientCertificateKeyStoreUrl() throws SQLException {
        return this.getStringRuntimeProperty("clientCertificateKeyStoreUrl");
    }
    
    public void setClientCertificateKeyStoreUrl(final String value) throws SQLException {
        this.setStringRuntimeProperty("clientCertificateKeyStoreUrl", value);
    }
    
    public int getPrepStmtCacheSize() throws SQLException {
        return this.getIntegerRuntimeProperty("prepStmtCacheSize");
    }
    
    public void setPrepStmtCacheSize(final int value) throws SQLException {
        this.setIntegerRuntimeProperty("prepStmtCacheSize", value);
    }
    
    public String getQueryInterceptors() throws SQLException {
        return this.getStringRuntimeProperty("queryInterceptors");
    }
    
    public void setQueryInterceptors(final String value) throws SQLException {
        this.setStringRuntimeProperty("queryInterceptors", value);
    }
    
    public boolean getDontTrackOpenResources() throws SQLException {
        return this.getBooleanRuntimeProperty("dontTrackOpenResources");
    }
    
    public void setDontTrackOpenResources(final boolean value) throws SQLException {
        this.setBooleanRuntimeProperty("dontTrackOpenResources", value);
    }
    
    public String getPropertiesTransform() throws SQLException {
        return this.getStringRuntimeProperty("propertiesTransform");
    }
    
    public void setPropertiesTransform(final String value) throws SQLException {
        this.setStringRuntimeProperty("propertiesTransform", value);
    }
    
    public boolean getCacheServerConfiguration() throws SQLException {
        return this.getBooleanRuntimeProperty("cacheServerConfiguration");
    }
    
    public void setCacheServerConfiguration(final boolean value) throws SQLException {
        this.setBooleanRuntimeProperty("cacheServerConfiguration", value);
    }
    
    public String getClientInfoProvider() throws SQLException {
        return this.getStringRuntimeProperty("clientInfoProvider");
    }
    
    public void setClientInfoProvider(final String value) throws SQLException {
        this.setStringRuntimeProperty("clientInfoProvider", value);
    }
    
    public String getZeroDateTimeBehavior() throws SQLException {
        return this.getEnumRuntimeProperty("zeroDateTimeBehavior");
    }
    
    public void setZeroDateTimeBehavior(final String value) throws SQLException {
        this.setEnumRuntimeProperty("zeroDateTimeBehavior", value);
    }
    
    public boolean getAllowLoadLocalInfile() throws SQLException {
        return this.getBooleanRuntimeProperty("allowLoadLocalInfile");
    }
    
    public void setAllowLoadLocalInfile(final boolean value) throws SQLException {
        this.setBooleanRuntimeProperty("allowLoadLocalInfile", value);
    }
    
    public boolean getCacheCallableStmts() throws SQLException {
        return this.getBooleanRuntimeProperty("cacheCallableStmts");
    }
    
    public void setCacheCallableStmts(final boolean value) throws SQLException {
        this.setBooleanRuntimeProperty("cacheCallableStmts", value);
    }
    
    public boolean getLogXaCommands() throws SQLException {
        return this.getBooleanRuntimeProperty("logXaCommands");
    }
    
    public void setLogXaCommands(final boolean value) throws SQLException {
        this.setBooleanRuntimeProperty("logXaCommands", value);
    }
    
    public boolean getUseUnbufferedInput() throws SQLException {
        return this.getBooleanRuntimeProperty("useUnbufferedInput");
    }
    
    public void setUseUnbufferedInput(final boolean value) throws SQLException {
        this.setBooleanRuntimeProperty("useUnbufferedInput", value);
    }
    
    public String getLogger() throws SQLException {
        return this.getStringRuntimeProperty("logger");
    }
    
    public void setLogger(final String value) throws SQLException {
        this.setStringRuntimeProperty("logger", value);
    }
    
    public String getTrustCertificateKeyStoreUrl() throws SQLException {
        return this.getStringRuntimeProperty("trustCertificateKeyStoreUrl");
    }
    
    public void setTrustCertificateKeyStoreUrl(final String value) throws SQLException {
        this.setStringRuntimeProperty("trustCertificateKeyStoreUrl", value);
    }
    
    public String getProfilerEventHandler() throws SQLException {
        return this.getStringRuntimeProperty("profilerEventHandler");
    }
    
    public void setProfilerEventHandler(final String value) throws SQLException {
        this.setStringRuntimeProperty("profilerEventHandler", value);
    }
    
    public String getConnectionAttributes() throws SQLException {
        return this.getStringRuntimeProperty("connectionAttributes");
    }
    
    public void setConnectionAttributes(final String value) throws SQLException {
        this.setStringRuntimeProperty("connectionAttributes", value);
    }
    
    public String getClobCharacterEncoding() throws SQLException {
        return this.getStringRuntimeProperty("clobCharacterEncoding");
    }
    
    public void setClobCharacterEncoding(final String value) throws SQLException {
        this.setStringRuntimeProperty("clobCharacterEncoding", value);
    }
    
    public boolean getLoadBalanceValidateConnectionOnSwapServer() throws SQLException {
        return this.getBooleanRuntimeProperty("loadBalanceValidateConnectionOnSwapServer");
    }
    
    public void setLoadBalanceValidateConnectionOnSwapServer(final boolean value) throws SQLException {
        this.setBooleanRuntimeProperty("loadBalanceValidateConnectionOnSwapServer", value);
    }
    
    public String getResourceId() throws SQLException {
        return this.getStringRuntimeProperty("resourceId");
    }
    
    public void setResourceId(final String value) throws SQLException {
        this.setStringRuntimeProperty("resourceId", value);
    }
    
    public boolean getAutoGenerateTestcaseScript() throws SQLException {
        return this.getBooleanRuntimeProperty("autoGenerateTestcaseScript");
    }
    
    public void setAutoGenerateTestcaseScript(final boolean value) throws SQLException {
        this.setBooleanRuntimeProperty("autoGenerateTestcaseScript", value);
    }
    
    public boolean getEmptyStringsConvertToZero() throws SQLException {
        return this.getBooleanRuntimeProperty("emptyStringsConvertToZero");
    }
    
    public void setEmptyStringsConvertToZero(final boolean value) throws SQLException {
        this.setBooleanRuntimeProperty("emptyStringsConvertToZero", value);
    }
    
    public boolean getFailOverReadOnly() throws SQLException {
        return this.getBooleanRuntimeProperty("failOverReadOnly");
    }
    
    public void setFailOverReadOnly(final boolean value) throws SQLException {
        this.setBooleanRuntimeProperty("failOverReadOnly", value);
    }
    
    public boolean getVerifyServerCertificate() throws SQLException {
        return this.getBooleanRuntimeProperty("verifyServerCertificate");
    }
    
    public void setVerifyServerCertificate(final boolean value) throws SQLException {
        this.setBooleanRuntimeProperty("verifyServerCertificate", value);
    }
    
    public String getAuthenticationPlugins() throws SQLException {
        return this.getStringRuntimeProperty("authenticationPlugins");
    }
    
    public void setAuthenticationPlugins(final String value) throws SQLException {
        this.setStringRuntimeProperty("authenticationPlugins", value);
    }
    
    public long getSlowQueryThresholdNanos() throws SQLException {
        return this.getLongRuntimeProperty("slowQueryThresholdNanos");
    }
    
    public void setSlowQueryThresholdNanos(final long value) throws SQLException {
        this.setLongRuntimeProperty("slowQueryThresholdNanos", value);
    }
    
    public boolean getUseOldAliasMetadataBehavior() throws SQLException {
        return this.getBooleanRuntimeProperty("useOldAliasMetadataBehavior");
    }
    
    public void setUseOldAliasMetadataBehavior(final boolean value) throws SQLException {
        this.setBooleanRuntimeProperty("useOldAliasMetadataBehavior", value);
    }
    
    public boolean getIncludeThreadNamesAsStatementComment() throws SQLException {
        return this.getBooleanRuntimeProperty("includeThreadNamesAsStatementComment");
    }
    
    public void setIncludeThreadNamesAsStatementComment(final boolean value) throws SQLException {
        this.setBooleanRuntimeProperty("includeThreadNamesAsStatementComment", value);
    }
    
    public int getMetadataCacheSize() throws SQLException {
        return this.getIntegerRuntimeProperty("metadataCacheSize");
    }
    
    public void setMetadataCacheSize(final int value) throws SQLException {
        this.setIntegerRuntimeProperty("metadataCacheSize", value);
    }
    
    public int getSlowQueryThresholdMillis() throws SQLException {
        return this.getIntegerRuntimeProperty("slowQueryThresholdMillis");
    }
    
    public void setSlowQueryThresholdMillis(final int value) throws SQLException {
        this.setIntegerRuntimeProperty("slowQueryThresholdMillis", value);
    }
    
    public boolean getUseHostsInPrivileges() throws SQLException {
        return this.getBooleanRuntimeProperty("useHostsInPrivileges");
    }
    
    public void setUseHostsInPrivileges(final boolean value) throws SQLException {
        this.setBooleanRuntimeProperty("useHostsInPrivileges", value);
    }
    
    public boolean getAllowSlaveDownConnections() throws SQLException {
        return this.getBooleanRuntimeProperty("allowSlaveDownConnections");
    }
    
    public void setAllowSlaveDownConnections(final boolean value) throws SQLException {
        this.setBooleanRuntimeProperty("allowSlaveDownConnections", value);
    }
    
    public boolean getNullCatalogMeansCurrent() throws SQLException {
        return this.getBooleanRuntimeProperty("nullCatalogMeansCurrent");
    }
    
    public void setNullCatalogMeansCurrent(final boolean value) throws SQLException {
        this.setBooleanRuntimeProperty("nullCatalogMeansCurrent", value);
    }
    
    public int getTcpSndBuf() throws SQLException {
        return this.getIntegerRuntimeProperty("tcpSndBuf");
    }
    
    public void setTcpSndBuf(final int value) throws SQLException {
        this.setIntegerRuntimeProperty("tcpSndBuf", value);
    }
    
    public boolean getDontCheckOnDuplicateKeyUpdateInSQL() throws SQLException {
        return this.getBooleanRuntimeProperty("dontCheckOnDuplicateKeyUpdateInSQL");
    }
    
    public void setDontCheckOnDuplicateKeyUpdateInSQL(final boolean value) throws SQLException {
        this.setBooleanRuntimeProperty("dontCheckOnDuplicateKeyUpdateInSQL", value);
    }
    
    public String getConnectionCollation() throws SQLException {
        return this.getStringRuntimeProperty("connectionCollation");
    }
    
    public void setConnectionCollation(final String value) throws SQLException {
        this.setStringRuntimeProperty("connectionCollation", value);
    }
    
    public int getTcpTrafficClass() throws SQLException {
        return this.getIntegerRuntimeProperty("tcpTrafficClass");
    }
    
    public void setTcpTrafficClass(final int value) throws SQLException {
        this.setIntegerRuntimeProperty("tcpTrafficClass", value);
    }
    
    public boolean getReadOnlyPropagatesToServer() throws SQLException {
        return this.getBooleanRuntimeProperty("readOnlyPropagatesToServer");
    }
    
    public void setReadOnlyPropagatesToServer(final boolean value) throws SQLException {
        this.setBooleanRuntimeProperty("readOnlyPropagatesToServer", value);
    }
    
    public boolean getAllowMultiQueries() throws SQLException {
        return this.getBooleanRuntimeProperty("allowMultiQueries");
    }
    
    public void setAllowMultiQueries(final boolean value) throws SQLException {
        this.setBooleanRuntimeProperty("allowMultiQueries", value);
    }
    
    public int getLoadBalanceHostRemovalGracePeriod() throws SQLException {
        return this.getIntegerRuntimeProperty("loadBalanceHostRemovalGracePeriod");
    }
    
    public void setLoadBalanceHostRemovalGracePeriod(final int value) throws SQLException {
        this.setIntegerRuntimeProperty("loadBalanceHostRemovalGracePeriod", value);
    }
    
    public String getSocketFactory() throws SQLException {
        return this.getStringRuntimeProperty("socketFactory");
    }
    
    public void setSocketFactory(final String value) throws SQLException {
        this.setStringRuntimeProperty("socketFactory", value);
    }
    
    public boolean getAutoSlowLog() throws SQLException {
        return this.getBooleanRuntimeProperty("autoSlowLog");
    }
    
    public void setAutoSlowLog(final boolean value) throws SQLException {
        this.setBooleanRuntimeProperty("autoSlowLog", value);
    }
    
    public int getSelfDestructOnPingMaxOperations() throws SQLException {
        return this.getIntegerRuntimeProperty("selfDestructOnPingMaxOperations");
    }
    
    public void setSelfDestructOnPingMaxOperations(final int value) throws SQLException {
        this.setIntegerRuntimeProperty("selfDestructOnPingMaxOperations", value);
    }
    
    public boolean getAutoReconnect() throws SQLException {
        return this.getBooleanRuntimeProperty("autoReconnect");
    }
    
    public void setAutoReconnect(final boolean value) throws SQLException {
        this.setBooleanRuntimeProperty("autoReconnect", value);
    }
    
    public String getXdevapiAuth() throws SQLException {
        return this.getEnumRuntimeProperty("xdevapiAuth");
    }
    
    public void setXdevapiAuth(final String value) throws SQLException {
        this.setEnumRuntimeProperty("xdevapiAuth", value);
    }
    
    public boolean getUseOnlyServerErrorMessages() throws SQLException {
        return this.getBooleanRuntimeProperty("useOnlyServerErrorMessages");
    }
    
    public void setUseOnlyServerErrorMessages(final boolean value) throws SQLException {
        this.setBooleanRuntimeProperty("useOnlyServerErrorMessages", value);
    }
    
    public boolean getRewriteBatchedStatements() throws SQLException {
        return this.getBooleanRuntimeProperty("rewriteBatchedStatements");
    }
    
    public void setRewriteBatchedStatements(final boolean value) throws SQLException {
        this.setBooleanRuntimeProperty("rewriteBatchedStatements", value);
    }
    
    public boolean getNoAccessToProcedureBodies() throws SQLException {
        return this.getBooleanRuntimeProperty("noAccessToProcedureBodies");
    }
    
    public void setNoAccessToProcedureBodies(final boolean value) throws SQLException {
        this.setBooleanRuntimeProperty("noAccessToProcedureBodies", value);
    }
    
    public String getXdevapiSSLMode() throws SQLException {
        return this.getEnumRuntimeProperty("xdevapiSSLMode");
    }
    
    public void setXdevapiSSLMode(final String value) throws SQLException {
        this.setEnumRuntimeProperty("xdevapiSSLMode", value);
    }
    
    public int getRetriesAllDown() throws SQLException {
        return this.getIntegerRuntimeProperty("retriesAllDown");
    }
    
    public void setRetriesAllDown(final int value) throws SQLException {
        this.setIntegerRuntimeProperty("retriesAllDown", value);
    }
    
    public int getTcpRcvBuf() throws SQLException {
        return this.getIntegerRuntimeProperty("tcpRcvBuf");
    }
    
    public void setTcpRcvBuf(final int value) throws SQLException {
        this.setIntegerRuntimeProperty("tcpRcvBuf", value);
    }
    
    public int getLoadBalanceAutoCommitStatementThreshold() throws SQLException {
        return this.getIntegerRuntimeProperty("loadBalanceAutoCommitStatementThreshold");
    }
    
    public void setLoadBalanceAutoCommitStatementThreshold(final int value) throws SQLException {
        this.setIntegerRuntimeProperty("loadBalanceAutoCommitStatementThreshold", value);
    }
    
    public int getMaxReconnects() throws SQLException {
        return this.getIntegerRuntimeProperty("maxReconnects");
    }
    
    public void setMaxReconnects(final int value) throws SQLException {
        this.setIntegerRuntimeProperty("maxReconnects", value);
    }
    
    public boolean getGenerateSimpleParameterMetadata() throws SQLException {
        return this.getBooleanRuntimeProperty("generateSimpleParameterMetadata");
    }
    
    public void setGenerateSimpleParameterMetadata(final boolean value) throws SQLException {
        this.setBooleanRuntimeProperty("generateSimpleParameterMetadata", value);
    }
    
    public int getLocatorFetchBufferSize() throws SQLException {
        return this.getIntegerRuntimeProperty("locatorFetchBufferSize");
    }
    
    public void setLocatorFetchBufferSize(final int value) throws SQLException {
        this.setIntegerRuntimeProperty("locatorFetchBufferSize", value);
    }
    
    public String getXdevapiSSLTruststoreType() throws SQLException {
        return this.getStringRuntimeProperty("xdevapiSSLTruststoreType");
    }
    
    public void setXdevapiSSLTruststoreType(final String value) throws SQLException {
        this.setStringRuntimeProperty("xdevapiSSLTruststoreType", value);
    }
    
    public boolean getSendFractionalSeconds() throws SQLException {
        return this.getBooleanRuntimeProperty("sendFractionalSeconds");
    }
    
    public void setSendFractionalSeconds(final boolean value) throws SQLException {
        this.setBooleanRuntimeProperty("sendFractionalSeconds", value);
    }
    
    public int getMaxQuerySizeToLog() throws SQLException {
        return this.getIntegerRuntimeProperty("maxQuerySizeToLog");
    }
    
    public void setMaxQuerySizeToLog(final int value) throws SQLException {
        this.setIntegerRuntimeProperty("maxQuerySizeToLog", value);
    }
    
    public int getInitialTimeout() throws SQLException {
        return this.getIntegerRuntimeProperty("initialTimeout");
    }
    
    public void setInitialTimeout(final int value) throws SQLException {
        this.setIntegerRuntimeProperty("initialTimeout", value);
    }
    
    public boolean getProfileSQL() throws SQLException {
        return this.getBooleanRuntimeProperty("profileSQL");
    }
    
    public void setProfileSQL(final boolean value) throws SQLException {
        this.setBooleanRuntimeProperty("profileSQL", value);
    }
    
    public boolean getTreatUtilDateAsTimestamp() throws SQLException {
        return this.getBooleanRuntimeProperty("treatUtilDateAsTimestamp");
    }
    
    public void setTreatUtilDateAsTimestamp(final boolean value) throws SQLException {
        this.setBooleanRuntimeProperty("treatUtilDateAsTimestamp", value);
    }
    
    public boolean getQueryTimeoutKillsConnection() throws SQLException {
        return this.getBooleanRuntimeProperty("queryTimeoutKillsConnection");
    }
    
    public void setQueryTimeoutKillsConnection(final boolean value) throws SQLException {
        this.setBooleanRuntimeProperty("queryTimeoutKillsConnection", value);
    }
    
    public boolean getReconnectAtTxEnd() throws SQLException {
        return this.getBooleanRuntimeProperty("reconnectAtTxEnd");
    }
    
    public void setReconnectAtTxEnd(final boolean value) throws SQLException {
        this.setBooleanRuntimeProperty("reconnectAtTxEnd", value);
    }
    
    public String getServerRSAPublicKeyFile() throws SQLException {
        return this.getStringRuntimeProperty("serverRSAPublicKeyFile");
    }
    
    public void setServerRSAPublicKeyFile(final String value) throws SQLException {
        this.setStringRuntimeProperty("serverRSAPublicKeyFile", value);
    }
    
    public boolean getAllowUrlInLocalInfile() throws SQLException {
        return this.getBooleanRuntimeProperty("allowUrlInLocalInfile");
    }
    
    public void setAllowUrlInLocalInfile(final boolean value) throws SQLException {
        this.setBooleanRuntimeProperty("allowUrlInLocalInfile", value);
    }
    
    public String getSessionVariables() throws SQLException {
        return this.getStringRuntimeProperty("sessionVariables");
    }
    
    public void setSessionVariables(final String value) throws SQLException {
        this.setStringRuntimeProperty("sessionVariables", value);
    }
    
    public String getParseInfoCacheFactory() throws SQLException {
        return this.getStringRuntimeProperty("parseInfoCacheFactory");
    }
    
    public void setParseInfoCacheFactory(final String value) throws SQLException {
        this.setStringRuntimeProperty("parseInfoCacheFactory", value);
    }
    
    public boolean getDetectCustomCollations() throws SQLException {
        return this.getBooleanRuntimeProperty("detectCustomCollations");
    }
    
    public void setDetectCustomCollations(final boolean value) throws SQLException {
        this.setBooleanRuntimeProperty("detectCustomCollations", value);
    }
    
    public String getCharacterEncoding() throws SQLException {
        return this.getStringRuntimeProperty("characterEncoding");
    }
    
    public void setCharacterEncoding(final String value) throws SQLException {
        this.setStringRuntimeProperty("characterEncoding", value);
    }
    
    public boolean getUseSSL() throws SQLException {
        return this.getBooleanRuntimeProperty("useSSL");
    }
    
    public void setUseSSL(final boolean value) throws SQLException {
        this.setBooleanRuntimeProperty("useSSL", value);
    }
    
    public boolean getIncludeInnodbStatusInDeadlockExceptions() throws SQLException {
        return this.getBooleanRuntimeProperty("includeInnodbStatusInDeadlockExceptions");
    }
    
    public void setIncludeInnodbStatusInDeadlockExceptions(final boolean value) throws SQLException {
        this.setBooleanRuntimeProperty("includeInnodbStatusInDeadlockExceptions", value);
    }
    
    public boolean getCreateDatabaseIfNotExist() throws SQLException {
        return this.getBooleanRuntimeProperty("createDatabaseIfNotExist");
    }
    
    public void setCreateDatabaseIfNotExist(final boolean value) throws SQLException {
        this.setBooleanRuntimeProperty("createDatabaseIfNotExist", value);
    }
    
    public String getServerAffinityOrder() throws SQLException {
        return this.getStringRuntimeProperty("serverAffinityOrder");
    }
    
    public void setServerAffinityOrder(final String value) throws SQLException {
        this.setStringRuntimeProperty("serverAffinityOrder", value);
    }
    
    public boolean getAutoReconnectForPools() throws SQLException {
        return this.getBooleanRuntimeProperty("autoReconnectForPools");
    }
    
    public void setAutoReconnectForPools(final boolean value) throws SQLException {
        this.setBooleanRuntimeProperty("autoReconnectForPools", value);
    }
    
    public String getLocalSocketAddress() throws SQLException {
        return this.getStringRuntimeProperty("localSocketAddress");
    }
    
    public void setLocalSocketAddress(final String value) throws SQLException {
        this.setStringRuntimeProperty("localSocketAddress", value);
    }
    
    public boolean getUseCompression() throws SQLException {
        return this.getBooleanRuntimeProperty("useCompression");
    }
    
    public void setUseCompression(final boolean value) throws SQLException {
        this.setBooleanRuntimeProperty("useCompression", value);
    }
    
    public boolean getEnablePacketDebug() throws SQLException {
        return this.getBooleanRuntimeProperty("enablePacketDebug");
    }
    
    public void setEnablePacketDebug(final boolean value) throws SQLException {
        this.setBooleanRuntimeProperty("enablePacketDebug", value);
    }
    
    public boolean getPinGlobalTxToPhysicalConnection() throws SQLException {
        return this.getBooleanRuntimeProperty("pinGlobalTxToPhysicalConnection");
    }
    
    public void setPinGlobalTxToPhysicalConnection(final boolean value) throws SQLException {
        this.setBooleanRuntimeProperty("pinGlobalTxToPhysicalConnection", value);
    }
    
    public int getReportMetricsIntervalMillis() throws SQLException {
        return this.getIntegerRuntimeProperty("reportMetricsIntervalMillis");
    }
    
    public void setReportMetricsIntervalMillis(final int value) throws SQLException {
        this.setIntegerRuntimeProperty("reportMetricsIntervalMillis", value);
    }
    
    public String getConnectionLifecycleInterceptors() throws SQLException {
        return this.getStringRuntimeProperty("connectionLifecycleInterceptors");
    }
    
    public void setConnectionLifecycleInterceptors(final String value) throws SQLException {
        this.setStringRuntimeProperty("connectionLifecycleInterceptors", value);
    }
    
    public boolean getAutoDeserialize() throws SQLException {
        return this.getBooleanRuntimeProperty("autoDeserialize");
    }
    
    public void setAutoDeserialize(final boolean value) throws SQLException {
        this.setBooleanRuntimeProperty("autoDeserialize", value);
    }
    
    public boolean getUseUsageAdvisor() throws SQLException {
        return this.getBooleanRuntimeProperty("useUsageAdvisor");
    }
    
    public void setUseUsageAdvisor(final boolean value) throws SQLException {
        this.setBooleanRuntimeProperty("useUsageAdvisor", value);
    }
    
    public boolean getUltraDevHack() throws SQLException {
        return this.getBooleanRuntimeProperty("ultraDevHack");
    }
    
    public void setUltraDevHack(final boolean value) throws SQLException {
        this.setBooleanRuntimeProperty("ultraDevHack", value);
    }
    
    public boolean getContinueBatchOnError() throws SQLException {
        return this.getBooleanRuntimeProperty("continueBatchOnError");
    }
    
    public void setContinueBatchOnError(final boolean value) throws SQLException {
        this.setBooleanRuntimeProperty("continueBatchOnError", value);
    }
    
    public boolean getPopulateInsertRowWithDefaultValues() throws SQLException {
        return this.getBooleanRuntimeProperty("populateInsertRowWithDefaultValues");
    }
    
    public void setPopulateInsertRowWithDefaultValues(final boolean value) throws SQLException {
        this.setBooleanRuntimeProperty("populateInsertRowWithDefaultValues", value);
    }
    
    public boolean getUseInformationSchema() throws SQLException {
        return this.getBooleanRuntimeProperty("useInformationSchema");
    }
    
    public void setUseInformationSchema(final boolean value) throws SQLException {
        this.setBooleanRuntimeProperty("useInformationSchema", value);
    }
    
    public String getClientCertificateKeyStoreType() throws SQLException {
        return this.getStringRuntimeProperty("clientCertificateKeyStoreType");
    }
    
    public void setClientCertificateKeyStoreType(final String value) throws SQLException {
        this.setStringRuntimeProperty("clientCertificateKeyStoreType", value);
    }
    
    public boolean getDisconnectOnExpiredPasswords() throws SQLException {
        return this.getBooleanRuntimeProperty("disconnectOnExpiredPasswords");
    }
    
    public void setDisconnectOnExpiredPasswords(final boolean value) throws SQLException {
        this.setBooleanRuntimeProperty("disconnectOnExpiredPasswords", value);
    }
    
    public boolean getEmulateLocators() throws SQLException {
        return this.getBooleanRuntimeProperty("emulateLocators");
    }
    
    public void setEmulateLocators(final boolean value) throws SQLException {
        this.setBooleanRuntimeProperty("emulateLocators", value);
    }
    
    public boolean getAllowNanAndInf() throws SQLException {
        return this.getBooleanRuntimeProperty("allowNanAndInf");
    }
    
    public void setAllowNanAndInf(final boolean value) throws SQLException {
        this.setBooleanRuntimeProperty("allowNanAndInf", value);
    }
    
    public boolean getTcpNoDelay() throws SQLException {
        return this.getBooleanRuntimeProperty("tcpNoDelay");
    }
    
    public void setTcpNoDelay(final boolean value) throws SQLException {
        this.setBooleanRuntimeProperty("tcpNoDelay", value);
    }
    
    public boolean getMaintainTimeStats() throws SQLException {
        return this.getBooleanRuntimeProperty("maintainTimeStats");
    }
    
    public void setMaintainTimeStats(final boolean value) throws SQLException {
        this.setBooleanRuntimeProperty("maintainTimeStats", value);
    }
    
    public boolean getOverrideSupportsIntegrityEnhancementFacility() throws SQLException {
        return this.getBooleanRuntimeProperty("overrideSupportsIntegrityEnhancementFacility");
    }
    
    public void setOverrideSupportsIntegrityEnhancementFacility(final boolean value) throws SQLException {
        this.setBooleanRuntimeProperty("overrideSupportsIntegrityEnhancementFacility", value);
    }
    
    public boolean getTraceProtocol() throws SQLException {
        return this.getBooleanRuntimeProperty("traceProtocol");
    }
    
    public void setTraceProtocol(final boolean value) throws SQLException {
        this.setBooleanRuntimeProperty("traceProtocol", value);
    }
    
    public boolean getRollbackOnPooledClose() throws SQLException {
        return this.getBooleanRuntimeProperty("rollbackOnPooledClose");
    }
    
    public void setRollbackOnPooledClose(final boolean value) throws SQLException {
        this.setBooleanRuntimeProperty("rollbackOnPooledClose", value);
    }
    
    public int getDefaultFetchSize() throws SQLException {
        return this.getIntegerRuntimeProperty("defaultFetchSize");
    }
    
    public void setDefaultFetchSize(final int value) throws SQLException {
        this.setIntegerRuntimeProperty("defaultFetchSize", value);
    }
    
    public boolean getDumpQueriesOnException() throws SQLException {
        return this.getBooleanRuntimeProperty("dumpQueriesOnException");
    }
    
    public void setDumpQueriesOnException(final boolean value) throws SQLException {
        this.setBooleanRuntimeProperty("dumpQueriesOnException", value);
    }
    
    public int getResultSetSizeThreshold() throws SQLException {
        return this.getIntegerRuntimeProperty("resultSetSizeThreshold");
    }
    
    public void setResultSetSizeThreshold(final int value) throws SQLException {
        this.setIntegerRuntimeProperty("resultSetSizeThreshold", value);
    }
    
    public boolean getHoldResultsOpenOverStatementClose() throws SQLException {
        return this.getBooleanRuntimeProperty("holdResultsOpenOverStatementClose");
    }
    
    public void setHoldResultsOpenOverStatementClose(final boolean value) throws SQLException {
        this.setBooleanRuntimeProperty("holdResultsOpenOverStatementClose", value);
    }
    
    public boolean getTcpKeepAlive() throws SQLException {
        return this.getBooleanRuntimeProperty("tcpKeepAlive");
    }
    
    public void setTcpKeepAlive(final boolean value) throws SQLException {
        this.setBooleanRuntimeProperty("tcpKeepAlive", value);
    }
    
    public String getUseConfigs() throws SQLException {
        return this.getStringRuntimeProperty("useConfigs");
    }
    
    public void setUseConfigs(final String value) throws SQLException {
        this.setStringRuntimeProperty("useConfigs", value);
    }
    
    public int getBlobSendChunkSize() throws SQLException {
        return this.getIntegerRuntimeProperty("blobSendChunkSize");
    }
    
    public void setBlobSendChunkSize(final int value) throws SQLException {
        this.setIntegerRuntimeProperty("blobSendChunkSize", value);
    }
}
