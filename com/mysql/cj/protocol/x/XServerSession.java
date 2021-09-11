
package com.mysql.cj.protocol.x;

import com.mysql.cj.ServerVersion;
import java.util.Map;
import com.mysql.cj.exceptions.ExceptionFactory;
import com.mysql.cj.exceptions.CJOperationNotSupportedException;
import com.mysql.cj.protocol.ServerCapabilities;
import java.util.TimeZone;
import com.mysql.cj.protocol.ServerSession;

public class XServerSession implements ServerSession
{
    XServerCapabilities serverCapabilities;
    private long clientId;
    private TimeZone defaultTimeZone;
    
    public XServerSession() {
        this.serverCapabilities = null;
        this.clientId = -1L;
        this.defaultTimeZone = TimeZone.getDefault();
    }
    
    @Override
    public ServerCapabilities getCapabilities() {
        return this.serverCapabilities;
    }
    
    @Override
    public void setCapabilities(final ServerCapabilities capabilities) {
        this.serverCapabilities = (XServerCapabilities)capabilities;
    }
    
    @Override
    public int getStatusFlags() {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, "Not supported");
    }
    
    @Override
    public void setStatusFlags(final int statusFlags) {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, "Not supported");
    }
    
    @Override
    public void setStatusFlags(final int statusFlags, final boolean saveOldStatusFlags) {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, "Not supported");
    }
    
    @Override
    public int getOldStatusFlags() {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, "Not supported");
    }
    
    @Override
    public void setOldStatusFlags(final int statusFlags) {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, "Not supported");
    }
    
    @Override
    public int getServerDefaultCollationIndex() {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, "Not supported");
    }
    
    @Override
    public void setServerDefaultCollationIndex(final int serverDefaultCollationIndex) {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, "Not supported");
    }
    
    @Override
    public int getTransactionState() {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, "Not supported");
    }
    
    @Override
    public boolean inTransactionOnServer() {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, "Not supported");
    }
    
    @Override
    public boolean cursorExists() {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, "Not supported");
    }
    
    @Override
    public boolean isAutocommit() {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, "Not supported");
    }
    
    @Override
    public boolean hasMoreResults() {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, "Not supported");
    }
    
    @Override
    public boolean isLastRowSent() {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, "Not supported");
    }
    
    @Override
    public boolean noGoodIndexUsed() {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, "Not supported");
    }
    
    @Override
    public boolean noIndexUsed() {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, "Not supported");
    }
    
    @Override
    public boolean queryWasSlow() {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, "Not supported");
    }
    
    @Override
    public long getClientParam() {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, "Not supported");
    }
    
    @Override
    public void setClientParam(final long clientParam) {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, "Not supported");
    }
    
    @Override
    public boolean useMultiResults() {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, "Not supported");
    }
    
    @Override
    public boolean isEOFDeprecated() {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, "Not supported");
    }
    
    @Override
    public boolean hasLongColumnInfo() {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, "Not supported");
    }
    
    @Override
    public void setHasLongColumnInfo(final boolean hasLongColumnInfo) {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, "Not supported");
    }
    
    @Override
    public Map<String, String> getServerVariables() {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, "Not supported");
    }
    
    @Override
    public String getServerVariable(final String name) {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, "Not supported");
    }
    
    @Override
    public int getServerVariable(final String variableName, final int fallbackValue) {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, "Not supported");
    }
    
    @Override
    public void setServerVariables(final Map<String, String> serverVariables) {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, "Not supported");
    }
    
    @Override
    public boolean characterSetNamesMatches(final String mysqlEncodingName) {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, "Not supported");
    }
    
    @Override
    public ServerVersion getServerVersion() {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, "Not supported");
    }
    
    @Override
    public boolean isVersion(final ServerVersion version) {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, "Not supported");
    }
    
    @Override
    public String getServerDefaultCharset() {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, "Not supported");
    }
    
    @Override
    public String getErrorMessageEncoding() {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, "Not supported");
    }
    
    @Override
    public void setErrorMessageEncoding(final String errorMessageEncoding) {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, "Not supported");
    }
    
    @Override
    public int getMaxBytesPerChar(final String javaCharsetName) {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, "Not supported");
    }
    
    @Override
    public int getMaxBytesPerChar(final Integer charsetIndex, final String javaCharsetName) {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, "Not supported");
    }
    
    @Override
    public String getEncodingForIndex(final int collationIndex) {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, "Not supported");
    }
    
    @Override
    public void configureCharacterSets() {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, "Not supported");
    }
    
    @Override
    public String getCharacterSetMetadata() {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, "Not supported");
    }
    
    @Override
    public void setCharacterSetMetadata(final String characterSetMetadata) {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, "Not supported");
    }
    
    @Override
    public int getMetadataCollationIndex() {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, "Not supported");
    }
    
    @Override
    public void setMetadataCollationIndex(final int metadataCollationIndex) {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, "Not supported");
    }
    
    @Override
    public String getCharacterSetResultsOnServer() {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, "Not supported");
    }
    
    @Override
    public void setCharacterSetResultsOnServer(final String characterSetResultsOnServer) {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, "Not supported");
    }
    
    @Override
    public boolean isLowerCaseTableNames() {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, "Not supported");
    }
    
    @Override
    public boolean storesLowerCaseTableNames() {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, "Not supported");
    }
    
    @Override
    public boolean isQueryCacheEnabled() {
        return false;
    }
    
    @Override
    public boolean isNoBackslashEscapesSet() {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, "Not supported");
    }
    
    @Override
    public boolean useAnsiQuotedIdentifiers() {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, "Not supported");
    }
    
    @Override
    public boolean isServerTruncatesFracSecs() {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, "Not supported");
    }
    
    @Override
    public long getThreadId() {
        return this.clientId;
    }
    
    @Override
    public void setThreadId(final long threadId) {
        this.clientId = threadId;
    }
    
    @Override
    public boolean isAutoCommit() {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, "Not supported");
    }
    
    @Override
    public void setAutoCommit(final boolean autoCommit) {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, "Not supported");
    }
    
    @Override
    public TimeZone getServerTimeZone() {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, "Not supported");
    }
    
    @Override
    public void setServerTimeZone(final TimeZone serverTimeZone) {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, "Not supported");
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
