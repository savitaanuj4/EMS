
package com.mysql.cj;

import java.io.UnsupportedEncodingException;
import com.mysql.cj.util.StringUtils;
import com.mysql.cj.conf.PropertyKey;
import java.util.ArrayList;
import com.mysql.cj.exceptions.ExceptionFactory;
import com.mysql.cj.exceptions.WrongArgumentException;

public class ParseInfo
{
    protected static final String[] ON_DUPLICATE_KEY_UPDATE_CLAUSE;
    private char firstStmtChar;
    private boolean foundLoadData;
    long lastUsed;
    int statementLength;
    int statementStartPos;
    boolean canRewriteAsMultiValueInsert;
    byte[][] staticSql;
    boolean hasPlaceholders;
    public int numberOfQueries;
    boolean isOnDuplicateKeyUpdate;
    int locationOfOnDuplicateKeyUpdate;
    String valuesClause;
    boolean parametersInDuplicateKeyClause;
    String charEncoding;
    private ParseInfo batchHead;
    private ParseInfo batchValues;
    private ParseInfo batchODKUClause;
    
    private ParseInfo(final byte[][] staticSql, final char firstStmtChar, final boolean foundLoadData, final boolean isOnDuplicateKeyUpdate, final int locationOfOnDuplicateKeyUpdate, final int statementLength, final int statementStartPos) {
        this.firstStmtChar = '\0';
        this.foundLoadData = false;
        this.lastUsed = 0L;
        this.statementLength = 0;
        this.statementStartPos = 0;
        this.canRewriteAsMultiValueInsert = false;
        this.staticSql = null;
        this.hasPlaceholders = false;
        this.numberOfQueries = 1;
        this.isOnDuplicateKeyUpdate = false;
        this.locationOfOnDuplicateKeyUpdate = -1;
        this.parametersInDuplicateKeyClause = false;
        this.firstStmtChar = firstStmtChar;
        this.foundLoadData = foundLoadData;
        this.isOnDuplicateKeyUpdate = isOnDuplicateKeyUpdate;
        this.locationOfOnDuplicateKeyUpdate = locationOfOnDuplicateKeyUpdate;
        this.statementLength = statementLength;
        this.statementStartPos = statementStartPos;
        this.staticSql = staticSql;
    }
    
    public ParseInfo(final String sql, final Session session, final String encoding) {
        this(sql, session, encoding, true);
    }
    
    public ParseInfo(final String sql, final Session session, final String encoding, final boolean buildRewriteInfo) {
        this.firstStmtChar = '\0';
        this.foundLoadData = false;
        this.lastUsed = 0L;
        this.statementLength = 0;
        this.statementStartPos = 0;
        this.canRewriteAsMultiValueInsert = false;
        this.staticSql = null;
        this.hasPlaceholders = false;
        this.numberOfQueries = 1;
        this.isOnDuplicateKeyUpdate = false;
        this.locationOfOnDuplicateKeyUpdate = -1;
        this.parametersInDuplicateKeyClause = false;
        try {
            if (sql == null) {
                throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("PreparedStatement.61"), session.getExceptionInterceptor());
            }
            this.charEncoding = encoding;
            this.lastUsed = System.currentTimeMillis();
            final String quotedIdentifierString = session.getIdentifierQuoteString();
            char quotedIdentifierChar = '\0';
            if (quotedIdentifierString != null && !quotedIdentifierString.equals(" ") && quotedIdentifierString.length() > 0) {
                quotedIdentifierChar = quotedIdentifierString.charAt(0);
            }
            this.statementLength = sql.length();
            final ArrayList<int[]> endpointList = new ArrayList<int[]>();
            boolean inQuotes = false;
            char quoteChar = '\0';
            boolean inQuotedId = false;
            int lastParmEnd = 0;
            final boolean noBackslashEscapes = session.getServerSession().isNoBackslashEscapesSet();
            this.statementStartPos = findStartOfStatement(sql);
            for (int i = this.statementStartPos; i < this.statementLength; ++i) {
                char c = sql.charAt(i);
                if (this.firstStmtChar == '\0' && Character.isLetter(c)) {
                    this.firstStmtChar = Character.toUpperCase(c);
                    if (this.firstStmtChar == 'I') {
                        this.locationOfOnDuplicateKeyUpdate = getOnDuplicateKeyLocation(sql, session.getPropertySet().getBooleanProperty(PropertyKey.dontCheckOnDuplicateKeyUpdateInSQL).getValue(), session.getPropertySet().getBooleanProperty(PropertyKey.rewriteBatchedStatements).getValue(), session.getServerSession().isNoBackslashEscapesSet());
                        this.isOnDuplicateKeyUpdate = (this.locationOfOnDuplicateKeyUpdate != -1);
                    }
                }
                if (!noBackslashEscapes && c == '\\' && i < this.statementLength - 1) {
                    ++i;
                }
                else {
                    if (!inQuotes && quotedIdentifierChar != '\0' && c == quotedIdentifierChar) {
                        inQuotedId = !inQuotedId;
                    }
                    else if (!inQuotedId) {
                        if (inQuotes) {
                            if ((c == '\'' || c == '\"') && c == quoteChar) {
                                if (i < this.statementLength - 1 && sql.charAt(i + 1) == quoteChar) {
                                    ++i;
                                    continue;
                                }
                                inQuotes = !inQuotes;
                                quoteChar = '\0';
                            }
                            else if ((c == '\'' || c == '\"') && c == quoteChar) {
                                inQuotes = !inQuotes;
                                quoteChar = '\0';
                            }
                        }
                        else {
                            if (c == '#' || (c == '-' && i + 1 < this.statementLength && sql.charAt(i + 1) == '-')) {
                                for (int endOfStmt = this.statementLength - 1; i < endOfStmt; ++i) {
                                    c = sql.charAt(i);
                                    if (c == '\r') {
                                        break;
                                    }
                                    if (c == '\n') {
                                        break;
                                    }
                                }
                                continue;
                            }
                            if (c == '/' && i + 1 < this.statementLength) {
                                char cNext = sql.charAt(i + 1);
                                if (cNext == '*') {
                                    i += 2;
                                    int j = i;
                                    while (j < this.statementLength) {
                                        ++i;
                                        cNext = sql.charAt(j);
                                        if (cNext == '*' && j + 1 < this.statementLength && sql.charAt(j + 1) == '/') {
                                            if (++i < this.statementLength) {
                                                c = sql.charAt(i);
                                                break;
                                            }
                                            break;
                                        }
                                        else {
                                            ++j;
                                        }
                                    }
                                }
                            }
                            else if (c == '\'' || c == '\"') {
                                inQuotes = true;
                                quoteChar = c;
                            }
                        }
                    }
                    if (!inQuotes && !inQuotedId) {
                        if (c == '?') {
                            endpointList.add(new int[] { lastParmEnd, i });
                            lastParmEnd = i + 1;
                            if (this.isOnDuplicateKeyUpdate && i > this.locationOfOnDuplicateKeyUpdate) {
                                this.parametersInDuplicateKeyClause = true;
                            }
                        }
                        else if (c == ';') {
                            int k = i + 1;
                            if (k < this.statementLength) {
                                while (k < this.statementLength && Character.isWhitespace(sql.charAt(k))) {
                                    ++k;
                                }
                                if (k < this.statementLength) {
                                    ++this.numberOfQueries;
                                }
                                i = k - 1;
                            }
                        }
                    }
                }
            }
            if (this.firstStmtChar == 'L') {
                if (StringUtils.startsWithIgnoreCaseAndWs(sql, "LOAD DATA")) {
                    this.foundLoadData = true;
                }
                else {
                    this.foundLoadData = false;
                }
            }
            else {
                this.foundLoadData = false;
            }
            endpointList.add(new int[] { lastParmEnd, this.statementLength });
            this.staticSql = new byte[endpointList.size()][];
            this.hasPlaceholders = (this.staticSql.length > 1);
            for (int i = 0; i < this.staticSql.length; ++i) {
                final int[] ep = endpointList.get(i);
                final int end = ep[1];
                final int begin = ep[0];
                final int len = end - begin;
                if (this.foundLoadData) {
                    this.staticSql[i] = StringUtils.getBytes(sql, begin, len);
                }
                else if (encoding == null) {
                    final byte[] buf = new byte[len];
                    for (int l = 0; l < len; ++l) {
                        buf[l] = (byte)sql.charAt(begin + l);
                    }
                    this.staticSql[i] = buf;
                }
                else {
                    this.staticSql[i] = StringUtils.getBytes(sql, begin, len, encoding);
                }
            }
        }
        catch (StringIndexOutOfBoundsException oobEx) {
            throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("PreparedStatement.62", new Object[] { sql }), oobEx, session.getExceptionInterceptor());
        }
        if (buildRewriteInfo) {
            this.canRewriteAsMultiValueInsert = (this.numberOfQueries == 1 && !this.parametersInDuplicateKeyClause && canRewrite(sql, this.isOnDuplicateKeyUpdate, this.locationOfOnDuplicateKeyUpdate, this.statementStartPos));
            if (this.canRewriteAsMultiValueInsert && session.getPropertySet().getBooleanProperty(PropertyKey.rewriteBatchedStatements).getValue()) {
                this.buildRewriteBatchedParams(sql, session, encoding);
            }
        }
    }
    
    public byte[][] getStaticSql() {
        return this.staticSql;
    }
    
    public String getValuesClause() {
        return this.valuesClause;
    }
    
    public int getLocationOfOnDuplicateKeyUpdate() {
        return this.locationOfOnDuplicateKeyUpdate;
    }
    
    public boolean canRewriteAsMultiValueInsertAtSqlLevel() {
        return this.canRewriteAsMultiValueInsert;
    }
    
    public boolean containsOnDuplicateKeyUpdateInSQL() {
        return this.isOnDuplicateKeyUpdate;
    }
    
    private void buildRewriteBatchedParams(final String sql, final Session session, final String encoding) {
        this.valuesClause = this.extractValuesClause(sql, session.getIdentifierQuoteString());
        final String odkuClause = this.isOnDuplicateKeyUpdate ? sql.substring(this.locationOfOnDuplicateKeyUpdate) : null;
        String headSql = null;
        if (this.isOnDuplicateKeyUpdate) {
            headSql = sql.substring(0, this.locationOfOnDuplicateKeyUpdate);
        }
        else {
            headSql = sql;
        }
        this.batchHead = new ParseInfo(headSql, session, encoding, false);
        this.batchValues = new ParseInfo("," + this.valuesClause, session, encoding, false);
        this.batchODKUClause = null;
        if (odkuClause != null && odkuClause.length() > 0) {
            this.batchODKUClause = new ParseInfo("," + this.valuesClause + " " + odkuClause, session, encoding, false);
        }
    }
    
    private String extractValuesClause(final String sql, final String quoteCharStr) {
        int indexOfValues = -1;
        int valuesSearchStart = this.statementStartPos;
        while (indexOfValues == -1) {
            if (quoteCharStr.length() > 0) {
                indexOfValues = StringUtils.indexOfIgnoreCase(valuesSearchStart, sql, "VALUES", quoteCharStr, quoteCharStr, StringUtils.SEARCH_MODE__MRK_COM_WS);
            }
            else {
                indexOfValues = StringUtils.indexOfIgnoreCase(valuesSearchStart, sql, "VALUES");
            }
            if (indexOfValues <= 0) {
                break;
            }
            char c = sql.charAt(indexOfValues - 1);
            if (!Character.isWhitespace(c) && c != ')' && c != '`') {
                valuesSearchStart = indexOfValues + 6;
                indexOfValues = -1;
            }
            else {
                c = sql.charAt(indexOfValues + 6);
                if (Character.isWhitespace(c) || c == '(') {
                    continue;
                }
                valuesSearchStart = indexOfValues + 6;
                indexOfValues = -1;
            }
        }
        if (indexOfValues == -1) {
            return null;
        }
        final int indexOfFirstParen = sql.indexOf(40, indexOfValues + 6);
        if (indexOfFirstParen == -1) {
            return null;
        }
        final int endOfValuesClause = this.isOnDuplicateKeyUpdate ? this.locationOfOnDuplicateKeyUpdate : sql.length();
        return sql.substring(indexOfFirstParen, endOfValuesClause);
    }
    
    public synchronized ParseInfo getParseInfoForBatch(final int numBatch) {
        final AppendingBatchVisitor apv = new AppendingBatchVisitor();
        this.buildInfoForBatch(numBatch, apv);
        final ParseInfo batchParseInfo = new ParseInfo(apv.getStaticSqlStrings(), this.firstStmtChar, this.foundLoadData, this.isOnDuplicateKeyUpdate, this.locationOfOnDuplicateKeyUpdate, this.statementLength, this.statementStartPos);
        return batchParseInfo;
    }
    
    public String getSqlForBatch(final int numBatch) throws UnsupportedEncodingException {
        final ParseInfo batchInfo = this.getParseInfoForBatch(numBatch);
        return batchInfo.getSqlForBatch();
    }
    
    public String getSqlForBatch() throws UnsupportedEncodingException {
        int size = 0;
        final byte[][] sqlStrings = this.staticSql;
        final int sqlStringsLength = sqlStrings.length;
        for (int i = 0; i < sqlStringsLength; ++i) {
            size += sqlStrings[i].length;
            ++size;
        }
        final StringBuilder buf = new StringBuilder(size);
        for (int j = 0; j < sqlStringsLength - 1; ++j) {
            buf.append(StringUtils.toString(sqlStrings[j], this.charEncoding));
            buf.append("?");
        }
        buf.append(StringUtils.toString(sqlStrings[sqlStringsLength - 1]));
        return buf.toString();
    }
    
    private void buildInfoForBatch(final int numBatch, final BatchVisitor visitor) {
        if (this.hasPlaceholders) {
            final byte[][] headStaticSql = this.batchHead.staticSql;
            final int headStaticSqlLength = headStaticSql.length;
            final byte[] endOfHead = headStaticSql[headStaticSqlLength - 1];
            for (int i = 0; i < headStaticSqlLength - 1; ++i) {
                visitor.append(headStaticSql[i]).increment();
            }
            int numValueRepeats = numBatch - 1;
            if (this.batchODKUClause != null) {
                --numValueRepeats;
            }
            final byte[][] valuesStaticSql = this.batchValues.staticSql;
            final int valuesStaticSqlLength = valuesStaticSql.length;
            final byte[] beginOfValues = valuesStaticSql[0];
            final byte[] endOfValues = valuesStaticSql[valuesStaticSqlLength - 1];
            for (int j = 0; j < numValueRepeats; ++j) {
                visitor.merge(endOfValues, beginOfValues).increment();
                for (int k = 1; k < valuesStaticSqlLength - 1; ++k) {
                    visitor.append(valuesStaticSql[k]).increment();
                }
            }
            if (this.batchODKUClause != null) {
                final byte[][] batchOdkuStaticSql = this.batchODKUClause.staticSql;
                final int batchOdkuStaticSqlLength = batchOdkuStaticSql.length;
                final byte[] beginOfOdku = batchOdkuStaticSql[0];
                final byte[] endOfOdku = batchOdkuStaticSql[batchOdkuStaticSqlLength - 1];
                if (numBatch > 1) {
                    visitor.merge((numValueRepeats > 0) ? endOfValues : endOfHead, beginOfOdku).increment();
                    for (int l = 1; l < batchOdkuStaticSqlLength; ++l) {
                        visitor.append(batchOdkuStaticSql[l]).increment();
                    }
                }
                else {
                    visitor.append(endOfOdku).increment();
                }
            }
            else {
                visitor.append(endOfHead);
            }
            return;
        }
        if (numBatch == 1) {
            visitor.append(this.staticSql[0]);
            return;
        }
        final byte[] headStaticSql2 = this.batchHead.staticSql[0];
        visitor.append(headStaticSql2).increment();
        int numValueRepeats2 = numBatch - 1;
        if (this.batchODKUClause != null) {
            --numValueRepeats2;
        }
        final byte[] valuesStaticSql2 = this.batchValues.staticSql[0];
        for (int i = 0; i < numValueRepeats2; ++i) {
            visitor.mergeWithLast(valuesStaticSql2).increment();
        }
        if (this.batchODKUClause != null) {
            final byte[] batchOdkuStaticSql2 = this.batchODKUClause.staticSql[0];
            visitor.mergeWithLast(batchOdkuStaticSql2).increment();
        }
    }
    
    protected static int findStartOfStatement(final String sql) {
        int statementStartPos = 0;
        if (StringUtils.startsWithIgnoreCaseAndWs(sql, "/*")) {
            statementStartPos = sql.indexOf("*/");
            if (statementStartPos == -1) {
                statementStartPos = 0;
            }
            else {
                statementStartPos += 2;
            }
        }
        else if (StringUtils.startsWithIgnoreCaseAndWs(sql, "--") || StringUtils.startsWithIgnoreCaseAndWs(sql, "#")) {
            statementStartPos = sql.indexOf(10);
            if (statementStartPos == -1) {
                statementStartPos = sql.indexOf(13);
                if (statementStartPos == -1) {
                    statementStartPos = 0;
                }
            }
        }
        return statementStartPos;
    }
    
    public static int getOnDuplicateKeyLocation(final String sql, final boolean dontCheckOnDuplicateKeyUpdateInSQL, final boolean rewriteBatchedStatements, final boolean noBackslashEscapes) {
        return (dontCheckOnDuplicateKeyUpdateInSQL && !rewriteBatchedStatements) ? -1 : StringUtils.indexOfIgnoreCase(0, sql, ParseInfo.ON_DUPLICATE_KEY_UPDATE_CLAUSE, "\"'`", "\"'`", noBackslashEscapes ? StringUtils.SEARCH_MODE__MRK_COM_WS : StringUtils.SEARCH_MODE__ALL);
    }
    
    protected static boolean canRewrite(final String sql, final boolean isOnDuplicateKeyUpdate, final int locationOfOnDuplicateKeyUpdate, final int statementStartPos) {
        if (!StringUtils.startsWithIgnoreCaseAndWs(sql, "INSERT", statementStartPos)) {
            return StringUtils.startsWithIgnoreCaseAndWs(sql, "REPLACE", statementStartPos) && StringUtils.indexOfIgnoreCase(statementStartPos, sql, "SELECT", "\"'`", "\"'`", StringUtils.SEARCH_MODE__MRK_COM_WS) == -1;
        }
        if (StringUtils.indexOfIgnoreCase(statementStartPos, sql, "SELECT", "\"'`", "\"'`", StringUtils.SEARCH_MODE__MRK_COM_WS) != -1) {
            return false;
        }
        if (isOnDuplicateKeyUpdate) {
            final int updateClausePos = StringUtils.indexOfIgnoreCase(locationOfOnDuplicateKeyUpdate, sql, " UPDATE ");
            if (updateClausePos != -1) {
                return StringUtils.indexOfIgnoreCase(updateClausePos, sql, "LAST_INSERT_ID", "\"'`", "\"'`", StringUtils.SEARCH_MODE__MRK_COM_WS) == -1;
            }
        }
        return true;
    }
    
    public boolean isFoundLoadData() {
        return this.foundLoadData;
    }
    
    public char getFirstStmtChar() {
        return this.firstStmtChar;
    }
    
    static {
        ON_DUPLICATE_KEY_UPDATE_CLAUSE = new String[] { "ON", "DUPLICATE", "KEY", "UPDATE" };
    }
}
