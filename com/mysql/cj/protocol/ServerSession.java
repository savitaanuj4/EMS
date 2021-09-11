
package com.mysql.cj.protocol;

import java.util.TimeZone;
import com.mysql.cj.ServerVersion;
import java.util.Map;

public interface ServerSession
{
    public static final int TRANSACTION_NOT_STARTED = 0;
    public static final int TRANSACTION_IN_PROGRESS = 1;
    public static final int TRANSACTION_STARTED = 2;
    public static final int TRANSACTION_COMPLETED = 3;
    public static final String LOCAL_CHARACTER_SET_RESULTS = "local.character_set_results";
    
    ServerCapabilities getCapabilities();
    
    void setCapabilities(final ServerCapabilities p0);
    
    int getStatusFlags();
    
    void setStatusFlags(final int p0);
    
    void setStatusFlags(final int p0, final boolean p1);
    
    int getOldStatusFlags();
    
    void setOldStatusFlags(final int p0);
    
    int getServerDefaultCollationIndex();
    
    void setServerDefaultCollationIndex(final int p0);
    
    int getTransactionState();
    
    boolean inTransactionOnServer();
    
    boolean cursorExists();
    
    boolean isAutocommit();
    
    boolean hasMoreResults();
    
    boolean isLastRowSent();
    
    boolean noGoodIndexUsed();
    
    boolean noIndexUsed();
    
    boolean queryWasSlow();
    
    long getClientParam();
    
    void setClientParam(final long p0);
    
    boolean useMultiResults();
    
    boolean isEOFDeprecated();
    
    boolean hasLongColumnInfo();
    
    void setHasLongColumnInfo(final boolean p0);
    
    Map<String, String> getServerVariables();
    
    String getServerVariable(final String p0);
    
    int getServerVariable(final String p0, final int p1);
    
    void setServerVariables(final Map<String, String> p0);
    
    boolean characterSetNamesMatches(final String p0);
    
    ServerVersion getServerVersion();
    
    boolean isVersion(final ServerVersion p0);
    
    String getServerDefaultCharset();
    
    String getErrorMessageEncoding();
    
    void setErrorMessageEncoding(final String p0);
    
    int getMaxBytesPerChar(final String p0);
    
    int getMaxBytesPerChar(final Integer p0, final String p1);
    
    String getEncodingForIndex(final int p0);
    
    void configureCharacterSets();
    
    String getCharacterSetMetadata();
    
    void setCharacterSetMetadata(final String p0);
    
    int getMetadataCollationIndex();
    
    void setMetadataCollationIndex(final int p0);
    
    String getCharacterSetResultsOnServer();
    
    void setCharacterSetResultsOnServer(final String p0);
    
    boolean isLowerCaseTableNames();
    
    boolean storesLowerCaseTableNames();
    
    boolean isQueryCacheEnabled();
    
    boolean isNoBackslashEscapesSet();
    
    boolean useAnsiQuotedIdentifiers();
    
    boolean isServerTruncatesFracSecs();
    
    long getThreadId();
    
    void setThreadId(final long p0);
    
    boolean isAutoCommit();
    
    void setAutoCommit(final boolean p0);
    
    TimeZone getServerTimeZone();
    
    void setServerTimeZone(final TimeZone p0);
    
    TimeZone getDefaultTimeZone();
    
    void setDefaultTimeZone(final TimeZone p0);
}
