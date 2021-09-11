
package com.mysql.cj.conf;

import java.util.Comparator;
import java.util.TreeMap;
import java.util.Map;

public enum PropertyKey
{
    USER("user", false), 
    PASSWORD("password", false), 
    HOST("host", false), 
    PORT("port", false), 
    PROTOCOL("protocol", false), 
    PATH("path", "namedPipePath", false), 
    TYPE("type", false), 
    ADDRESS("address", false), 
    PRIORITY("priority", false), 
    DBNAME("dbname", false), 
    allowLoadLocalInfile("allowLoadLocalInfile", true), 
    allowMasterDownConnections("allowMasterDownConnections", true), 
    allowMultiQueries("allowMultiQueries", true), 
    allowNanAndInf("allowNanAndInf", true), 
    allowPublicKeyRetrieval("allowPublicKeyRetrieval", true), 
    allowSlaveDownConnections("allowSlaveDownConnections", true), 
    allowUrlInLocalInfile("allowUrlInLocalInfile", true), 
    alwaysSendSetIsolation("alwaysSendSetIsolation", true), 
    authenticationPlugins("authenticationPlugins", true), 
    autoClosePStmtStreams("autoClosePStmtStreams", true), 
    autoDeserialize("autoDeserialize", true), 
    autoGenerateTestcaseScript("autoGenerateTestcaseScript", true), 
    autoReconnect("autoReconnect", true), 
    autoReconnectForPools("autoReconnectForPools", true), 
    autoSlowLog("autoSlowLog", true), 
    blobsAreStrings("blobsAreStrings", true), 
    blobSendChunkSize("blobSendChunkSize", true), 
    cacheCallableStmts("cacheCallableStmts", true), 
    cachePrepStmts("cachePrepStmts", true), 
    cacheResultSetMetadata("cacheResultSetMetadata", true), 
    cacheServerConfiguration("cacheServerConfiguration", true), 
    callableStmtCacheSize("callableStmtCacheSize", true), 
    characterEncoding("characterEncoding", true), 
    characterSetResults("characterSetResults", true), 
    clientCertificateKeyStorePassword("clientCertificateKeyStorePassword", true), 
    clientCertificateKeyStoreType("clientCertificateKeyStoreType", true), 
    clientCertificateKeyStoreUrl("clientCertificateKeyStoreUrl", true), 
    clientInfoProvider("clientInfoProvider", true), 
    clobberStreamingResults("clobberStreamingResults", true), 
    clobCharacterEncoding("clobCharacterEncoding", true), 
    compensateOnDuplicateKeyUpdateCounts("compensateOnDuplicateKeyUpdateCounts", true), 
    connectionAttributes("connectionAttributes", true), 
    connectionCollation("connectionCollation", true), 
    connectionLifecycleInterceptors("connectionLifecycleInterceptors", true), 
    connectTimeout("connectTimeout", true), 
    continueBatchOnError("continueBatchOnError", true), 
    createDatabaseIfNotExist("createDatabaseIfNotExist", true), 
    defaultAuthenticationPlugin("defaultAuthenticationPlugin", true), 
    defaultFetchSize("defaultFetchSize", true), 
    detectCustomCollations("detectCustomCollations", true), 
    disabledAuthenticationPlugins("disabledAuthenticationPlugins", true), 
    disconnectOnExpiredPasswords("disconnectOnExpiredPasswords", true), 
    dontCheckOnDuplicateKeyUpdateInSQL("dontCheckOnDuplicateKeyUpdateInSQL", true), 
    dontTrackOpenResources("dontTrackOpenResources", true), 
    dumpQueriesOnException("dumpQueriesOnException", true), 
    elideSetAutoCommits("elideSetAutoCommits", true), 
    emptyStringsConvertToZero("emptyStringsConvertToZero", true), 
    emulateLocators("emulateLocators", true), 
    emulateUnsupportedPstmts("emulateUnsupportedPstmts", true), 
    enabledSSLCipherSuites("enabledSSLCipherSuites", true), 
    enabledTLSProtocols("enabledTLSProtocols", true), 
    enableEscapeProcessing("enableEscapeProcessing", true), 
    enablePacketDebug("enablePacketDebug", true), 
    enableQueryTimeouts("enableQueryTimeouts", true), 
    exceptionInterceptors("exceptionInterceptors", true), 
    explainSlowQueries("explainSlowQueries", true), 
    failOverReadOnly("failOverReadOnly", true), 
    functionsNeverReturnBlobs("functionsNeverReturnBlobs", true), 
    gatherPerfMetrics("gatherPerfMetrics", true), 
    generateSimpleParameterMetadata("generateSimpleParameterMetadata", true), 
    getProceduresReturnsFunctions("getProceduresReturnsFunctions", true), 
    holdResultsOpenOverStatementClose("holdResultsOpenOverStatementClose", true), 
    ha_enableJMX("ha.enableJMX", "haEnableJMX", true), 
    ha_loadBalanceStrategy("ha.loadBalanceStrategy", "haLoadBalanceStrategy", true), 
    ignoreNonTxTables("ignoreNonTxTables", true), 
    includeInnodbStatusInDeadlockExceptions("includeInnodbStatusInDeadlockExceptions", true), 
    includeThreadDumpInDeadlockExceptions("includeThreadDumpInDeadlockExceptions", true), 
    includeThreadNamesAsStatementComment("includeThreadNamesAsStatementComment", true), 
    initialTimeout("initialTimeout", true), 
    interactiveClient("interactiveClient", true), 
    jdbcCompliantTruncation("jdbcCompliantTruncation", true), 
    largeRowSizeThreshold("largeRowSizeThreshold", true), 
    loadBalanceAutoCommitStatementRegex("loadBalanceAutoCommitStatementRegex", true), 
    loadBalanceAutoCommitStatementThreshold("loadBalanceAutoCommitStatementThreshold", true), 
    loadBalanceBlacklistTimeout("loadBalanceBlacklistTimeout", true), 
    loadBalanceConnectionGroup("loadBalanceConnectionGroup", true), 
    loadBalanceExceptionChecker("loadBalanceExceptionChecker", true), 
    loadBalanceHostRemovalGracePeriod("loadBalanceHostRemovalGracePeriod", true), 
    loadBalancePingTimeout("loadBalancePingTimeout", true), 
    loadBalanceSQLStateFailover("loadBalanceSQLStateFailover", true), 
    loadBalanceSQLExceptionSubclassFailover("loadBalanceSQLExceptionSubclassFailover", true), 
    loadBalanceValidateConnectionOnSwapServer("loadBalanceValidateConnectionOnSwapServer", true), 
    localSocketAddress("localSocketAddress", true), 
    locatorFetchBufferSize("locatorFetchBufferSize", true), 
    logger("logger", true), 
    logSlowQueries("logSlowQueries", true), 
    logXaCommands("logXaCommands", true), 
    maintainTimeStats("maintainTimeStats", true), 
    maxAllowedPacket("maxAllowedPacket", true), 
    maxQuerySizeToLog("maxQuerySizeToLog", true), 
    maxReconnects("maxReconnects", true), 
    maxRows("maxRows", true), 
    metadataCacheSize("metadataCacheSize", true), 
    netTimeoutForStreamingResults("netTimeoutForStreamingResults", true), 
    noAccessToProcedureBodies("noAccessToProcedureBodies", true), 
    noDatetimeStringSync("noDatetimeStringSync", true), 
    nullCatalogMeansCurrent("nullCatalogMeansCurrent", true), 
    overrideSupportsIntegrityEnhancementFacility("overrideSupportsIntegrityEnhancementFacility", true), 
    packetDebugBufferSize("packetDebugBufferSize", true), 
    padCharsWithSpace("padCharsWithSpace", true), 
    paranoid("paranoid", false), 
    parseInfoCacheFactory("parseInfoCacheFactory", true), 
    passwordCharacterEncoding("passwordCharacterEncoding", true), 
    pedantic("pedantic", true), 
    pinGlobalTxToPhysicalConnection("pinGlobalTxToPhysicalConnection", true), 
    populateInsertRowWithDefaultValues("populateInsertRowWithDefaultValues", true), 
    prepStmtCacheSize("prepStmtCacheSize", true), 
    prepStmtCacheSqlLimit("prepStmtCacheSqlLimit", true), 
    processEscapeCodesForPrepStmts("processEscapeCodesForPrepStmts", true), 
    profilerEventHandler("profilerEventHandler", true), 
    profileSQL("profileSQL", true), 
    propertiesTransform("propertiesTransform", true), 
    queriesBeforeRetryMaster("queriesBeforeRetryMaster", true), 
    queryInterceptors("queryInterceptors", true), 
    queryTimeoutKillsConnection("queryTimeoutKillsConnection", true), 
    readFromMasterWhenNoSlaves("readFromMasterWhenNoSlaves", true), 
    readOnlyPropagatesToServer("readOnlyPropagatesToServer", true), 
    reconnectAtTxEnd("reconnectAtTxEnd", true), 
    replicationConnectionGroup("replicationConnectionGroup", true), 
    reportMetricsIntervalMillis("reportMetricsIntervalMillis", true), 
    requireSSL("requireSSL", true), 
    resourceId("resourceId", true), 
    resultSetSizeThreshold("resultSetSizeThreshold", true), 
    retriesAllDown("retriesAllDown", true), 
    rewriteBatchedStatements("rewriteBatchedStatements", true), 
    rollbackOnPooledClose("rollbackOnPooledClose", true), 
    secondsBeforeRetryMaster("secondsBeforeRetryMaster", true), 
    selfDestructOnPingMaxOperations("selfDestructOnPingMaxOperations", true), 
    selfDestructOnPingSecondsLifetime("selfDestructOnPingSecondsLifetime", true), 
    sendFractionalSeconds("sendFractionalSeconds", true), 
    serverAffinityOrder("serverAffinityOrder", true), 
    serverConfigCacheFactory("serverConfigCacheFactory", true), 
    serverRSAPublicKeyFile("serverRSAPublicKeyFile", true), 
    serverTimezone("serverTimezone", true), 
    sessionVariables("sessionVariables", true), 
    slowQueryThresholdMillis("slowQueryThresholdMillis", true), 
    slowQueryThresholdNanos("slowQueryThresholdNanos", true), 
    socketFactory("socketFactory", true), 
    socketTimeout("socketTimeout", true), 
    socksProxyHost("socksProxyHost", true), 
    socksProxyPort("socksProxyPort", true), 
    sslMode("sslMode", true), 
    strictUpdates("strictUpdates", true), 
    tcpKeepAlive("tcpKeepAlive", true), 
    tcpNoDelay("tcpNoDelay", true), 
    tcpRcvBuf("tcpRcvBuf", true), 
    tcpSndBuf("tcpSndBuf", true), 
    tcpTrafficClass("tcpTrafficClass", true), 
    tinyInt1isBit("tinyInt1isBit", true), 
    traceProtocol("traceProtocol", true), 
    transformedBitIsBoolean("transformedBitIsBoolean", true), 
    treatUtilDateAsTimestamp("treatUtilDateAsTimestamp", true), 
    trustCertificateKeyStorePassword("trustCertificateKeyStorePassword", true), 
    trustCertificateKeyStoreType("trustCertificateKeyStoreType", true), 
    trustCertificateKeyStoreUrl("trustCertificateKeyStoreUrl", true), 
    ultraDevHack("ultraDevHack", true), 
    useAffectedRows("useAffectedRows", true), 
    useColumnNamesInFindColumn("useColumnNamesInFindColumn", true), 
    useCompression("useCompression", true), 
    useConfigs("useConfigs", true), 
    useCursorFetch("useCursorFetch", true), 
    useHostsInPrivileges("useHostsInPrivileges", true), 
    useInformationSchema("useInformationSchema", true), 
    useLocalSessionState("useLocalSessionState", true), 
    useLocalTransactionState("useLocalTransactionState", true), 
    useNanosForElapsedTime("useNanosForElapsedTime", true), 
    useOldAliasMetadataBehavior("useOldAliasMetadataBehavior", true), 
    useOnlyServerErrorMessages("useOnlyServerErrorMessages", true), 
    useReadAheadInput("useReadAheadInput", true), 
    useServerPrepStmts("useServerPrepStmts", true), 
    useSSL("useSSL", true), 
    useStreamLengthsInPrepStmts("useStreamLengthsInPrepStmts", true), 
    useUnbufferedInput("useUnbufferedInput", true), 
    useUsageAdvisor("useUsageAdvisor", true), 
    verifyServerCertificate("verifyServerCertificate", true), 
    xdevapiAsyncResponseTimeout("xdevapi.asyncResponseTimeout", "xdevapiAsyncResponseTimeout", true), 
    xdevapiAuth("xdevapi.auth", "xdevapiAuth", true), 
    xdevapiConnectTimeout("xdevapi.connect-timeout", "xdevapiConnectTimeout", true), 
    xdevapiSSLMode("xdevapi.ssl-mode", "xdevapiSSLMode", true), 
    xdevapiSSLTrustStoreUrl("xdevapi.ssl-truststore", "xdevapiSSLTruststore", true), 
    xdevapiSSLTrustStoreType("xdevapi.ssl-truststore-type", "xdevapiSSLTruststoreType", true), 
    xdevapiSSLTrustStorePassword("xdevapi.ssl-truststore-password", "xdevapiSSLTruststorePassword", true), 
    xdevapiUseAsyncProtocol("xdevapi.useAsyncProtocol", "xdevapiUseAsyncProtocol", true), 
    yearIsDateType("yearIsDateType", true), 
    zeroDateTimeBehavior("zeroDateTimeBehavior", true);
    
    private String keyName;
    private String ccAlias;
    private boolean isCaseSensitive;
    private static Map<String, PropertyKey> caseInsensitiveValues;
    
    private PropertyKey(final String keyName, final boolean isCaseSensitive) {
        this.ccAlias = null;
        this.isCaseSensitive = false;
        this.keyName = keyName;
        this.isCaseSensitive = isCaseSensitive;
    }
    
    private PropertyKey(final String keyName, final String alias, final boolean isCaseSensitive) {
        this(keyName, isCaseSensitive);
        this.ccAlias = alias;
    }
    
    @Override
    public String toString() {
        return this.keyName;
    }
    
    public String getKeyName() {
        return this.keyName;
    }
    
    public String getCcAlias() {
        return this.ccAlias;
    }
    
    public static PropertyKey fromValue(final String value) {
        for (final PropertyKey k : values()) {
            if (k.isCaseSensitive) {
                if (k.getKeyName().equals(value) || (k.getCcAlias() != null && k.getCcAlias().equals(value))) {
                    return k;
                }
            }
            else if (k.getKeyName().equalsIgnoreCase(value) || (k.getCcAlias() != null && k.getCcAlias().equalsIgnoreCase(value))) {
                return k;
            }
        }
        return null;
    }
    
    public static String normalizeCase(final String keyName) {
        final PropertyKey pk = PropertyKey.caseInsensitiveValues.get(keyName);
        return (pk == null) ? keyName : pk.getKeyName();
    }
    
    static {
        PropertyKey.caseInsensitiveValues = new TreeMap<String, PropertyKey>(String.CASE_INSENSITIVE_ORDER);
        for (final PropertyKey pk : values()) {
            if (!pk.isCaseSensitive) {
                PropertyKey.caseInsensitiveValues.put(pk.getKeyName(), pk);
                if (pk.getCcAlias() != null) {
                    PropertyKey.caseInsensitiveValues.put(pk.getCcAlias(), pk);
                }
            }
        }
    }
}
