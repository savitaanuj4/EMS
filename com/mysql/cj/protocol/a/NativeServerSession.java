
package com.mysql.cj.protocol.a;

import com.mysql.cj.util.StringUtils;
import com.mysql.cj.exceptions.ExceptionFactory;
import com.mysql.cj.Messages;
import com.mysql.cj.exceptions.WrongArgumentException;
import com.mysql.cj.conf.PropertyKey;
import com.mysql.cj.CharsetMapping;
import com.mysql.cj.ServerVersion;
import com.mysql.cj.protocol.ServerCapabilities;
import java.util.HashMap;
import java.util.TimeZone;
import java.util.Map;
import com.mysql.cj.conf.PropertySet;
import com.mysql.cj.protocol.ServerSession;

public class NativeServerSession implements ServerSession
{
    public static final int SERVER_STATUS_IN_TRANS = 1;
    public static final int SERVER_STATUS_AUTOCOMMIT = 2;
    public static final int SERVER_MORE_RESULTS_EXISTS = 8;
    public static final int SERVER_QUERY_NO_GOOD_INDEX_USED = 16;
    public static final int SERVER_QUERY_NO_INDEX_USED = 32;
    public static final int SERVER_STATUS_CURSOR_EXISTS = 64;
    public static final int SERVER_STATUS_LAST_ROW_SENT = 128;
    public static final int SERVER_QUERY_WAS_SLOW = 2048;
    public static final int CLIENT_LONG_PASSWORD = 1;
    public static final int CLIENT_FOUND_ROWS = 2;
    public static final int CLIENT_LONG_FLAG = 4;
    public static final int CLIENT_CONNECT_WITH_DB = 8;
    public static final int CLIENT_COMPRESS = 32;
    public static final int CLIENT_LOCAL_FILES = 128;
    public static final int CLIENT_PROTOCOL_41 = 512;
    public static final int CLIENT_INTERACTIVE = 1024;
    public static final int CLIENT_SSL = 2048;
    public static final int CLIENT_TRANSACTIONS = 8192;
    public static final int CLIENT_RESERVED = 16384;
    public static final int CLIENT_SECURE_CONNECTION = 32768;
    public static final int CLIENT_MULTI_STATEMENTS = 65536;
    public static final int CLIENT_MULTI_RESULTS = 131072;
    public static final int CLIENT_PS_MULTI_RESULTS = 262144;
    public static final int CLIENT_PLUGIN_AUTH = 524288;
    public static final int CLIENT_CONNECT_ATTRS = 1048576;
    public static final int CLIENT_PLUGIN_AUTH_LENENC_CLIENT_DATA = 2097152;
    public static final int CLIENT_CAN_HANDLE_EXPIRED_PASSWORD = 4194304;
    public static final int CLIENT_SESSION_TRACK = 8388608;
    public static final int CLIENT_DEPRECATE_EOF = 16777216;
    private PropertySet propertySet;
    private NativeCapabilities capabilities;
    private int oldStatusFlags;
    private int statusFlags;
    private int serverDefaultCollationIndex;
    private long clientParam;
    private boolean hasLongColumnInfo;
    private Map<String, String> serverVariables;
    public Map<Integer, String> indexToCustomMysqlCharset;
    public Map<String, Integer> mysqlCharsetToCustomMblen;
    private String characterSetMetadata;
    private int metadataCollationIndex;
    private String characterSetResultsOnServer;
    private String errorMessageEncoding;
    private boolean autoCommit;
    private TimeZone serverTimeZone;
    private TimeZone defaultTimeZone;
    
    public NativeServerSession(final PropertySet propertySet) {
        this.oldStatusFlags = 0;
        this.statusFlags = 0;
        this.clientParam = 0L;
        this.hasLongColumnInfo = false;
        this.serverVariables = new HashMap<String, String>();
        this.indexToCustomMysqlCharset = null;
        this.mysqlCharsetToCustomMblen = null;
        this.characterSetMetadata = null;
        this.characterSetResultsOnServer = null;
        this.errorMessageEncoding = "Cp1252";
        this.autoCommit = true;
        this.serverTimeZone = null;
        this.defaultTimeZone = TimeZone.getDefault();
        this.propertySet = propertySet;
        this.serverVariables.put("character_set_server", "utf8");
    }
    
    @Override
    public NativeCapabilities getCapabilities() {
        return this.capabilities;
    }
    
    @Override
    public void setCapabilities(final ServerCapabilities capabilities) {
        this.capabilities = (NativeCapabilities)capabilities;
    }
    
    @Override
    public int getStatusFlags() {
        return this.statusFlags;
    }
    
    @Override
    public void setStatusFlags(final int statusFlags) {
        this.setStatusFlags(statusFlags, false);
    }
    
    @Override
    public void setStatusFlags(final int statusFlags, final boolean saveOldStatus) {
        if (saveOldStatus) {
            this.oldStatusFlags = this.statusFlags;
        }
        this.statusFlags = statusFlags;
    }
    
    @Override
    public int getOldStatusFlags() {
        return this.oldStatusFlags;
    }
    
    @Override
    public void setOldStatusFlags(final int oldStatusFlags) {
        this.oldStatusFlags = oldStatusFlags;
    }
    
    @Override
    public int getTransactionState() {
        if ((this.oldStatusFlags & 0x1) == 0x0) {
            if ((this.statusFlags & 0x1) == 0x0) {
                return 0;
            }
            return 2;
        }
        else {
            if ((this.statusFlags & 0x1) == 0x0) {
                return 3;
            }
            return 1;
        }
    }
    
    @Override
    public boolean inTransactionOnServer() {
        return (this.statusFlags & 0x1) != 0x0;
    }
    
    @Override
    public boolean cursorExists() {
        return (this.statusFlags & 0x40) != 0x0;
    }
    
    @Override
    public boolean isAutocommit() {
        return (this.statusFlags & 0x2) != 0x0;
    }
    
    @Override
    public boolean hasMoreResults() {
        return (this.statusFlags & 0x8) != 0x0;
    }
    
    @Override
    public boolean noGoodIndexUsed() {
        return (this.statusFlags & 0x10) != 0x0;
    }
    
    @Override
    public boolean noIndexUsed() {
        return (this.statusFlags & 0x20) != 0x0;
    }
    
    @Override
    public boolean queryWasSlow() {
        return (this.statusFlags & 0x800) != 0x0;
    }
    
    @Override
    public boolean isLastRowSent() {
        return (this.statusFlags & 0x80) != 0x0;
    }
    
    @Override
    public long getClientParam() {
        return this.clientParam;
    }
    
    @Override
    public void setClientParam(final long clientParam) {
        this.clientParam = clientParam;
    }
    
    @Override
    public boolean useMultiResults() {
        return (this.clientParam & 0x20000L) != 0x0L || (this.clientParam & 0x40000L) != 0x0L;
    }
    
    @Override
    public boolean isEOFDeprecated() {
        return (this.clientParam & 0x1000000L) != 0x0L;
    }
    
    @Override
    public int getServerDefaultCollationIndex() {
        return this.serverDefaultCollationIndex;
    }
    
    @Override
    public void setServerDefaultCollationIndex(final int serverDefaultCollationIndex) {
        this.serverDefaultCollationIndex = serverDefaultCollationIndex;
    }
    
    @Override
    public boolean hasLongColumnInfo() {
        return this.hasLongColumnInfo;
    }
    
    @Override
    public void setHasLongColumnInfo(final boolean hasLongColumnInfo) {
        this.hasLongColumnInfo = hasLongColumnInfo;
    }
    
    @Override
    public Map<String, String> getServerVariables() {
        return this.serverVariables;
    }
    
    @Override
    public String getServerVariable(final String name) {
        return this.serverVariables.get(name);
    }
    
    @Override
    public int getServerVariable(final String variableName, final int fallbackValue) {
        try {
            return Integer.valueOf(this.getServerVariable(variableName));
        }
        catch (NumberFormatException ex) {
            return fallbackValue;
        }
    }
    
    @Override
    public void setServerVariables(final Map<String, String> serverVariables) {
        this.serverVariables = serverVariables;
    }
    
    @Override
    public boolean characterSetNamesMatches(final String mysqlEncodingName) {
        return mysqlEncodingName != null && mysqlEncodingName.equalsIgnoreCase(this.getServerVariable("character_set_client")) && mysqlEncodingName.equalsIgnoreCase(this.getServerVariable("character_set_connection"));
    }
    
    @Override
    public final ServerVersion getServerVersion() {
        return this.capabilities.getServerVersion();
    }
    
    @Override
    public boolean isVersion(final ServerVersion version) {
        return this.getServerVersion().equals(version);
    }
    
    public boolean isSetNeededForAutoCommitMode(final boolean autoCommitFlag, final boolean elideSetAutoCommitsFlag) {
        if (!elideSetAutoCommitsFlag) {
            return true;
        }
        final boolean autoCommitModeOnServer = this.isAutocommit();
        if (autoCommitModeOnServer && !autoCommitFlag) {
            return !this.inTransactionOnServer();
        }
        return autoCommitModeOnServer != autoCommitFlag;
    }
    
    @Override
    public String getErrorMessageEncoding() {
        return this.errorMessageEncoding;
    }
    
    @Override
    public void setErrorMessageEncoding(final String errorMessageEncoding) {
        this.errorMessageEncoding = errorMessageEncoding;
    }
    
    @Override
    public String getServerDefaultCharset() {
        String charset = null;
        if (this.indexToCustomMysqlCharset != null) {
            charset = this.indexToCustomMysqlCharset.get(this.getServerDefaultCollationIndex());
        }
        if (charset == null) {
            charset = CharsetMapping.getMysqlCharsetNameForCollationIndex(this.getServerDefaultCollationIndex());
        }
        return (charset != null) ? charset : this.getServerVariable("character_set_server");
    }
    
    @Override
    public int getMaxBytesPerChar(final String javaCharsetName) {
        return this.getMaxBytesPerChar(null, javaCharsetName);
    }
    
    @Override
    public int getMaxBytesPerChar(final Integer charsetIndex, final String javaCharsetName) {
        String charset = null;
        int res = 1;
        if (this.indexToCustomMysqlCharset != null) {
            charset = this.indexToCustomMysqlCharset.get(charsetIndex);
        }
        if (charset == null) {
            charset = CharsetMapping.getMysqlCharsetNameForCollationIndex(charsetIndex);
        }
        if (charset == null) {
            charset = CharsetMapping.getMysqlCharsetForJavaEncoding(javaCharsetName, this.getServerVersion());
        }
        Integer mblen = null;
        if (this.mysqlCharsetToCustomMblen != null) {
            mblen = this.mysqlCharsetToCustomMblen.get(charset);
        }
        if (mblen == null) {
            mblen = CharsetMapping.getMblen(charset);
        }
        if (mblen != null) {
            res = mblen;
        }
        return res;
    }
    
    @Override
    public String getEncodingForIndex(final int charsetIndex) {
        String javaEncoding = null;
        final String characterEncoding = this.propertySet.getStringProperty(PropertyKey.characterEncoding).getValue();
        if (charsetIndex != -1) {
            try {
                if (this.indexToCustomMysqlCharset != null) {
                    final String cs = this.indexToCustomMysqlCharset.get(charsetIndex);
                    if (cs != null) {
                        javaEncoding = CharsetMapping.getJavaEncodingForMysqlCharset(cs, characterEncoding);
                    }
                }
                if (javaEncoding == null) {
                    javaEncoding = CharsetMapping.getJavaEncodingForCollationIndex(charsetIndex, characterEncoding);
                }
            }
            catch (ArrayIndexOutOfBoundsException outOfBoundsEx) {
                throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("Connection.11", new Object[] { charsetIndex }));
            }
            if (javaEncoding == null) {
                javaEncoding = characterEncoding;
            }
        }
        else {
            javaEncoding = characterEncoding;
        }
        return javaEncoding;
    }
    
    @Override
    public void configureCharacterSets() {
        final String characterSetResultsOnServerMysql = this.getServerVariable("local.character_set_results");
        if (characterSetResultsOnServerMysql == null || StringUtils.startsWithIgnoreCaseAndWs(characterSetResultsOnServerMysql, "NULL") || characterSetResultsOnServerMysql.length() == 0) {
            final String defaultMetadataCharsetMysql = this.getServerVariable("character_set_system");
            String defaultMetadataCharset = null;
            if (defaultMetadataCharsetMysql != null) {
                defaultMetadataCharset = CharsetMapping.getJavaEncodingForMysqlCharset(defaultMetadataCharsetMysql);
            }
            else {
                defaultMetadataCharset = "UTF-8";
            }
            this.characterSetMetadata = defaultMetadataCharset;
            this.setErrorMessageEncoding("UTF-8");
        }
        else {
            this.characterSetResultsOnServer = CharsetMapping.getJavaEncodingForMysqlCharset(characterSetResultsOnServerMysql);
            this.characterSetMetadata = this.characterSetResultsOnServer;
            this.setErrorMessageEncoding(this.characterSetResultsOnServer);
        }
        this.metadataCollationIndex = CharsetMapping.getCollationIndexForJavaEncoding(this.characterSetMetadata, this.getServerVersion());
    }
    
    @Override
    public String getCharacterSetMetadata() {
        return this.characterSetMetadata;
    }
    
    @Override
    public void setCharacterSetMetadata(final String characterSetMetadata) {
        this.characterSetMetadata = characterSetMetadata;
    }
    
    @Override
    public int getMetadataCollationIndex() {
        return this.metadataCollationIndex;
    }
    
    @Override
    public void setMetadataCollationIndex(final int metadataCollationIndex) {
        this.metadataCollationIndex = metadataCollationIndex;
    }
    
    @Override
    public String getCharacterSetResultsOnServer() {
        return this.characterSetResultsOnServer;
    }
    
    @Override
    public void setCharacterSetResultsOnServer(final String characterSetResultsOnServer) {
        this.characterSetResultsOnServer = characterSetResultsOnServer;
    }
    
    public void preserveOldTransactionState() {
        this.statusFlags |= (this.oldStatusFlags & 0x1);
    }
    
    @Override
    public boolean isLowerCaseTableNames() {
        final String lowerCaseTables = this.serverVariables.get("lower_case_table_names");
        return "on".equalsIgnoreCase(lowerCaseTables) || "1".equalsIgnoreCase(lowerCaseTables) || "2".equalsIgnoreCase(lowerCaseTables);
    }
    
    @Override
    public boolean storesLowerCaseTableNames() {
        final String lowerCaseTables = this.serverVariables.get("lower_case_table_names");
        return "1".equalsIgnoreCase(lowerCaseTables) || "on".equalsIgnoreCase(lowerCaseTables);
    }
    
    @Override
    public boolean isQueryCacheEnabled() {
        return "ON".equalsIgnoreCase(this.serverVariables.get("query_cache_type")) && !"0".equalsIgnoreCase(this.serverVariables.get("query_cache_size"));
    }
    
    @Override
    public boolean isNoBackslashEscapesSet() {
        final String sqlModeAsString = this.serverVariables.get("sql_mode");
        return sqlModeAsString != null && sqlModeAsString.indexOf("NO_BACKSLASH_ESCAPES") != -1;
    }
    
    @Override
    public boolean useAnsiQuotedIdentifiers() {
        final String sqlModeAsString = this.serverVariables.get("sql_mode");
        return sqlModeAsString != null && sqlModeAsString.indexOf("ANSI_QUOTES") != -1;
    }
    
    @Override
    public boolean isServerTruncatesFracSecs() {
        final String sqlModeAsString = this.serverVariables.get("sql_mode");
        return sqlModeAsString != null && sqlModeAsString.indexOf("TIME_TRUNCATE_FRACTIONAL") != -1;
    }
    
    @Override
    public long getThreadId() {
        return this.capabilities.getThreadId();
    }
    
    @Override
    public void setThreadId(final long threadId) {
        this.capabilities.setThreadId(threadId);
    }
    
    @Override
    public boolean isAutoCommit() {
        return this.autoCommit;
    }
    
    @Override
    public void setAutoCommit(final boolean autoCommit) {
        this.autoCommit = autoCommit;
    }
    
    @Override
    public TimeZone getServerTimeZone() {
        return this.serverTimeZone;
    }
    
    @Override
    public void setServerTimeZone(final TimeZone serverTimeZone) {
        this.serverTimeZone = serverTimeZone;
    }
    
    @Override
    public TimeZone getDefaultTimeZone() {
        return this.defaultTimeZone;
    }
    
    @Override
    public void setDefaultTimeZone(final TimeZone defaultTimeZone) {
        this.defaultTimeZone = defaultTimeZone;
    }
}
