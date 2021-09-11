
package com.mysql.cj.util;

import java.util.UUID;
import java.io.ByteArrayOutputStream;
import com.mysql.cj.ServerVersion;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.UnsupportedCharsetException;
import java.nio.charset.Charset;
import java.util.Collections;
import java.io.IOException;
import java.io.StringReader;
import java.util.stream.Stream;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.function.Function;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Collection;
import java.util.EnumSet;
import com.mysql.cj.exceptions.NumberOutOfRange;
import java.io.UnsupportedEncodingException;
import com.mysql.cj.exceptions.ExceptionFactory;
import com.mysql.cj.Messages;
import com.mysql.cj.exceptions.WrongArgumentException;
import java.util.Set;

public class StringUtils
{
    public static final Set<SearchMode> SEARCH_MODE__ALL;
    public static final Set<SearchMode> SEARCH_MODE__MRK_COM_WS;
    public static final Set<SearchMode> SEARCH_MODE__BSESC_COM_WS;
    public static final Set<SearchMode> SEARCH_MODE__BSESC_MRK_WS;
    public static final Set<SearchMode> SEARCH_MODE__COM_WS;
    public static final Set<SearchMode> SEARCH_MODE__MRK_WS;
    public static final Set<SearchMode> SEARCH_MODE__NONE;
    private static final int NON_COMMENTS_MYSQL_VERSION_REF_LENGTH = 5;
    private static final int WILD_COMPARE_MATCH = 0;
    private static final int WILD_COMPARE_CONTINUE_WITH_WILD = 1;
    private static final int WILD_COMPARE_NO_MATCH = -1;
    static final char WILDCARD_MANY = '%';
    static final char WILDCARD_ONE = '_';
    static final char WILDCARD_ESCAPE = '\\';
    private static final String VALID_ID_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIGKLMNOPQRSTUVWXYZ0123456789$_#@";
    private static final char[] HEX_DIGITS;
    static final char[] EMPTY_SPACE;
    
    public static String dumpAsHex(final byte[] byteBuffer, int length) {
        length = Math.min(length, byteBuffer.length);
        final StringBuilder fullOutBuilder = new StringBuilder(length * 4);
        final StringBuilder asciiOutBuilder = new StringBuilder(16);
        int p = 0;
        int l = 0;
        while (p < length) {
            while (l < 8 && p < length) {
                final int asInt = byteBuffer[p] & 0xFF;
                if (asInt < 16) {
                    fullOutBuilder.append("0");
                }
                fullOutBuilder.append(Integer.toHexString(asInt)).append(" ");
                asciiOutBuilder.append(" ").append((asInt >= 32 && asInt < 127) ? Character.valueOf((char)asInt) : ".");
                ++p;
                ++l;
            }
            while (l < 8) {
                fullOutBuilder.append("   ");
                ++l;
            }
            fullOutBuilder.append("   ").append((CharSequence)asciiOutBuilder).append(System.lineSeparator());
            asciiOutBuilder.setLength(0);
            l = 0;
        }
        return fullOutBuilder.toString();
    }
    
    public static String toHexString(final byte[] byteBuffer, int length) {
        length = Math.min(length, byteBuffer.length);
        final StringBuilder outputBuilder = new StringBuilder(length * 2);
        for (int i = 0; i < length; ++i) {
            final int asInt = byteBuffer[i] & 0xFF;
            if (asInt < 16) {
                outputBuilder.append("0");
            }
            outputBuilder.append(Integer.toHexString(asInt));
        }
        return outputBuilder.toString();
    }
    
    private static boolean endsWith(final byte[] dataFrom, final String suffix) {
        for (int i = 1; i <= suffix.length(); ++i) {
            final int dfOffset = dataFrom.length - i;
            final int suffixOffset = suffix.length() - i;
            if (dataFrom[dfOffset] != suffix.charAt(suffixOffset)) {
                return false;
            }
        }
        return true;
    }
    
    public static char firstNonWsCharUc(final String searchIn) {
        return firstNonWsCharUc(searchIn, 0);
    }
    
    public static char firstNonWsCharUc(final String searchIn, final int startAt) {
        if (searchIn == null) {
            return '\0';
        }
        for (int length = searchIn.length(), i = startAt; i < length; ++i) {
            final char c = searchIn.charAt(i);
            if (!Character.isWhitespace(c)) {
                return Character.toUpperCase(c);
            }
        }
        return '\0';
    }
    
    public static char firstAlphaCharUc(final String searchIn, final int startAt) {
        if (searchIn == null) {
            return '\0';
        }
        for (int length = searchIn.length(), i = startAt; i < length; ++i) {
            final char c = searchIn.charAt(i);
            if (Character.isLetter(c)) {
                return Character.toUpperCase(c);
            }
        }
        return '\0';
    }
    
    public static String fixDecimalExponent(String dString) {
        int ePos = dString.indexOf(69);
        if (ePos == -1) {
            ePos = dString.indexOf(101);
        }
        if (ePos != -1 && dString.length() > ePos + 1) {
            final char maybeMinusChar = dString.charAt(ePos + 1);
            if (maybeMinusChar != '-' && maybeMinusChar != '+') {
                final StringBuilder strBuilder = new StringBuilder(dString.length() + 1);
                strBuilder.append(dString.substring(0, ePos + 1));
                strBuilder.append('+');
                strBuilder.append(dString.substring(ePos + 1, dString.length()));
                dString = strBuilder.toString();
            }
        }
        return dString;
    }
    
    public static byte[] getBytes(final String s, final String encoding) {
        if (encoding == null) {
            return getBytes(s);
        }
        try {
            return s.getBytes(encoding);
        }
        catch (UnsupportedEncodingException uee) {
            throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("StringUtils.0", new Object[] { encoding }), uee);
        }
    }
    
    public static byte[] getBytesWrapped(String s, final char beginWrap, final char endWrap, final String encoding) {
        byte[] b;
        if (encoding == null) {
            final StringBuilder strBuilder = new StringBuilder(s.length() + 2);
            strBuilder.append(beginWrap);
            strBuilder.append(s);
            strBuilder.append(endWrap);
            b = getBytes(strBuilder.toString());
        }
        else {
            final StringBuilder strBuilder = new StringBuilder(s.length() + 2);
            strBuilder.append(beginWrap);
            strBuilder.append(s);
            strBuilder.append(endWrap);
            s = strBuilder.toString();
            b = getBytes(s, encoding);
        }
        return b;
    }
    
    public static int getInt(final byte[] buf) throws NumberFormatException {
        return getInt(buf, 0, buf.length);
    }
    
    public static int getInt(final byte[] buf, final int offset, final int endpos) throws NumberFormatException {
        final long l = getLong(buf, offset, endpos);
        if (l < -2147483648L || l > 2147483647L) {
            throw new NumberOutOfRange(Messages.getString("StringUtils.badIntFormat", new Object[] { toString(buf, offset, endpos - offset) }));
        }
        return (int)l;
    }
    
    public static long getLong(final byte[] buf) throws NumberFormatException {
        return getLong(buf, 0, buf.length);
    }
    
    public static long getLong(final byte[] buf, final int offset, final int endpos) throws NumberFormatException {
        final int base = 10;
        int s;
        for (s = offset; s < endpos && Character.isWhitespace((char)buf[s]); ++s) {}
        if (s == endpos) {
            throw new NumberFormatException(toString(buf));
        }
        boolean negative = false;
        if ((char)buf[s] == '-') {
            negative = true;
            ++s;
        }
        else if ((char)buf[s] == '+') {
            ++s;
        }
        final int save = s;
        final long cutoff = Long.MAX_VALUE / base;
        long cutlim = (int)(Long.MAX_VALUE % base);
        if (negative) {
            ++cutlim;
        }
        boolean overflow = false;
        long i = 0L;
        while (s < endpos) {
            char c = (char)buf[s];
            if (c >= '0' && c <= '9') {
                c -= '0';
            }
            else {
                if (!Character.isLetter(c)) {
                    break;
                }
                c = (char)(Character.toUpperCase(c) - 'A' + 10);
            }
            if (c >= base) {
                break;
            }
            if (i > cutoff || (i == cutoff && c > cutlim)) {
                overflow = true;
            }
            else {
                i *= base;
                i += c;
            }
            ++s;
        }
        if (s == save) {
            throw new NumberFormatException(Messages.getString("StringUtils.badIntFormat", new Object[] { toString(buf, offset, endpos - offset) }));
        }
        if (overflow) {
            throw new NumberOutOfRange(Messages.getString("StringUtils.badIntFormat", new Object[] { toString(buf, offset, endpos - offset) }));
        }
        return negative ? (-i) : i;
    }
    
    public static int indexOfIgnoreCase(final String searchIn, final String searchFor) {
        return indexOfIgnoreCase(0, searchIn, searchFor);
    }
    
    public static int indexOfIgnoreCase(final int startingPosition, final String searchIn, final String searchFor) {
        if (searchIn == null || searchFor == null) {
            return -1;
        }
        final int searchInLength = searchIn.length();
        final int searchForLength = searchFor.length();
        final int stopSearchingAt = searchInLength - searchForLength;
        if (startingPosition > stopSearchingAt || searchForLength == 0) {
            return -1;
        }
        final char firstCharOfSearchForUc = Character.toUpperCase(searchFor.charAt(0));
        final char firstCharOfSearchForLc = Character.toLowerCase(searchFor.charAt(0));
        for (int i = startingPosition; i <= stopSearchingAt; ++i) {
            if (isCharAtPosNotEqualIgnoreCase(searchIn, i, firstCharOfSearchForUc, firstCharOfSearchForLc)) {
                while (++i <= stopSearchingAt && isCharAtPosNotEqualIgnoreCase(searchIn, i, firstCharOfSearchForUc, firstCharOfSearchForLc)) {}
            }
            if (i <= stopSearchingAt && startsWithIgnoreCase(searchIn, i, searchFor)) {
                return i;
            }
        }
        return -1;
    }
    
    public static int indexOfIgnoreCase(final int startingPosition, final String searchIn, final String[] searchForSequence, final String openingMarkers, final String closingMarkers, Set<SearchMode> searchMode) {
        if (searchIn == null || searchForSequence == null) {
            return -1;
        }
        final int searchInLength = searchIn.length();
        int searchForLength = 0;
        for (final String searchForPart : searchForSequence) {
            searchForLength += searchForPart.length();
        }
        if (searchForLength == 0) {
            return -1;
        }
        final int searchForWordsCount = searchForSequence.length;
        searchForLength += ((searchForWordsCount > 0) ? (searchForWordsCount - 1) : 0);
        final int stopSearchingAt = searchInLength - searchForLength;
        if (startingPosition > stopSearchingAt) {
            return -1;
        }
        if (searchMode.contains(SearchMode.SKIP_BETWEEN_MARKERS) && (openingMarkers == null || closingMarkers == null || openingMarkers.length() != closingMarkers.length())) {
            throw new IllegalArgumentException(Messages.getString("StringUtils.15", new String[] { openingMarkers, closingMarkers }));
        }
        if (Character.isWhitespace(searchForSequence[0].charAt(0)) && searchMode.contains(SearchMode.SKIP_WHITE_SPACE)) {
            searchMode = EnumSet.copyOf(searchMode);
            searchMode.remove(SearchMode.SKIP_WHITE_SPACE);
        }
        final Set<SearchMode> searchMode2 = EnumSet.of(SearchMode.SKIP_WHITE_SPACE);
        searchMode2.addAll(searchMode);
        searchMode2.remove(SearchMode.SKIP_BETWEEN_MARKERS);
        for (int positionOfFirstWord = startingPosition; positionOfFirstWord <= stopSearchingAt; ++positionOfFirstWord) {
            positionOfFirstWord = indexOfIgnoreCase(positionOfFirstWord, searchIn, searchForSequence[0], openingMarkers, closingMarkers, searchMode);
            if (positionOfFirstWord == -1 || positionOfFirstWord > stopSearchingAt) {
                return -1;
            }
            int startingPositionForNextWord = positionOfFirstWord + searchForSequence[0].length();
            int wc = 0;
            boolean match = true;
            while (++wc < searchForWordsCount && match) {
                final int positionOfNextWord = indexOfNextChar(startingPositionForNextWord, searchInLength - 1, searchIn, null, null, null, searchMode2);
                if (startingPositionForNextWord == positionOfNextWord || !startsWithIgnoreCase(searchIn, positionOfNextWord, searchForSequence[wc])) {
                    match = false;
                }
                else {
                    startingPositionForNextWord = positionOfNextWord + searchForSequence[wc].length();
                }
            }
            if (match) {
                return positionOfFirstWord;
            }
        }
        return -1;
    }
    
    public static int indexOfIgnoreCase(final int startingPosition, final String searchIn, final String searchFor, final String openingMarkers, final String closingMarkers, final Set<SearchMode> searchMode) {
        return indexOfIgnoreCase(startingPosition, searchIn, searchFor, openingMarkers, closingMarkers, "", searchMode);
    }
    
    public static int indexOfIgnoreCase(final int startingPosition, final String searchIn, final String searchFor, final String openingMarkers, final String closingMarkers, final String overridingMarkers, Set<SearchMode> searchMode) {
        if (searchIn == null || searchFor == null) {
            return -1;
        }
        final int searchInLength = searchIn.length();
        final int searchForLength = searchFor.length();
        final int stopSearchingAt = searchInLength - searchForLength;
        if (startingPosition > stopSearchingAt || searchForLength == 0) {
            return -1;
        }
        if (searchMode.contains(SearchMode.SKIP_BETWEEN_MARKERS)) {
            if (openingMarkers == null || closingMarkers == null || openingMarkers.length() != closingMarkers.length()) {
                throw new IllegalArgumentException(Messages.getString("StringUtils.15", new String[] { openingMarkers, closingMarkers }));
            }
            if (overridingMarkers == null) {
                throw new IllegalArgumentException(Messages.getString("StringUtils.16", new String[] { overridingMarkers, openingMarkers }));
            }
            for (final char c : overridingMarkers.toCharArray()) {
                if (openingMarkers.indexOf(c) == -1) {
                    throw new IllegalArgumentException(Messages.getString("StringUtils.16", new String[] { overridingMarkers, openingMarkers }));
                }
            }
        }
        final char firstCharOfSearchForUc = Character.toUpperCase(searchFor.charAt(0));
        final char firstCharOfSearchForLc = Character.toLowerCase(searchFor.charAt(0));
        if (Character.isWhitespace(firstCharOfSearchForLc) && searchMode.contains(SearchMode.SKIP_WHITE_SPACE)) {
            searchMode = EnumSet.copyOf(searchMode);
            searchMode.remove(SearchMode.SKIP_WHITE_SPACE);
        }
        for (int i = startingPosition; i <= stopSearchingAt; ++i) {
            i = indexOfNextChar(i, stopSearchingAt, searchIn, openingMarkers, closingMarkers, overridingMarkers, searchMode);
            if (i == -1) {
                return -1;
            }
            final char c = searchIn.charAt(i);
            if (isCharEqualIgnoreCase(c, firstCharOfSearchForUc, firstCharOfSearchForLc) && startsWithIgnoreCase(searchIn, i, searchFor)) {
                return i;
            }
        }
        return -1;
    }
    
    private static int indexOfNextChar(final int startingPosition, final int stopPosition, final String searchIn, final String openingMarkers, final String closingMarkers, final String overridingMarkers, final Set<SearchMode> searchMode) {
        if (searchIn == null) {
            return -1;
        }
        final int searchInLength = searchIn.length();
        if (startingPosition >= searchInLength) {
            return -1;
        }
        char c0 = '\0';
        char c2 = searchIn.charAt(startingPosition);
        char c3 = (startingPosition + 1 < searchInLength) ? searchIn.charAt(startingPosition + 1) : '\0';
        for (int i = startingPosition; i <= stopPosition; ++i) {
            c0 = c2;
            c2 = c3;
            c3 = ((i + 2 < searchInLength) ? searchIn.charAt(i + 2) : '\0');
            boolean dashDashCommentImmediateEnd = false;
            int markerIndex = -1;
            if (searchMode.contains(SearchMode.ALLOW_BACKSLASH_ESCAPE) && c0 == '\\') {
                ++i;
                c2 = c3;
                c3 = ((i + 2 < searchInLength) ? searchIn.charAt(i + 2) : '\0');
            }
            else if (searchMode.contains(SearchMode.SKIP_BETWEEN_MARKERS) && (markerIndex = openingMarkers.indexOf(c0)) != -1) {
                int nestedMarkersCount = 0;
                final char openingMarker = c0;
                final char closingMarker = closingMarkers.charAt(markerIndex);
                final boolean outerIsAnOverridingMarker = overridingMarkers.indexOf(openingMarker) != -1;
                while (++i <= stopPosition && ((c0 = searchIn.charAt(i)) != closingMarker || nestedMarkersCount != 0)) {
                    if (!outerIsAnOverridingMarker && overridingMarkers.indexOf(c0) != -1) {
                        final int overridingMarkerIndex = openingMarkers.indexOf(c0);
                        int overridingNestedMarkersCount = 0;
                        final char overridingOpeningMarker = c0;
                        final char overridingClosingMarker = closingMarkers.charAt(overridingMarkerIndex);
                        while (++i <= stopPosition && ((c0 = searchIn.charAt(i)) != overridingClosingMarker || overridingNestedMarkersCount != 0)) {
                            if (c0 == overridingOpeningMarker) {
                                ++overridingNestedMarkersCount;
                            }
                            else if (c0 == overridingClosingMarker) {
                                --overridingNestedMarkersCount;
                            }
                            else {
                                if (!searchMode.contains(SearchMode.ALLOW_BACKSLASH_ESCAPE) || c0 != '\\') {
                                    continue;
                                }
                                ++i;
                            }
                        }
                    }
                    else if (c0 == openingMarker) {
                        ++nestedMarkersCount;
                    }
                    else if (c0 == closingMarker) {
                        --nestedMarkersCount;
                    }
                    else {
                        if (!searchMode.contains(SearchMode.ALLOW_BACKSLASH_ESCAPE) || c0 != '\\') {
                            continue;
                        }
                        ++i;
                    }
                }
                c2 = ((i + 1 < searchInLength) ? searchIn.charAt(i + 1) : '\0');
                c3 = ((i + 2 < searchInLength) ? searchIn.charAt(i + 2) : '\0');
            }
            else if (searchMode.contains(SearchMode.SKIP_BLOCK_COMMENTS) && c0 == '/' && c2 == '*') {
                if (c3 != '!') {
                    ++i;
                    while (++i <= stopPosition) {
                        if (searchIn.charAt(i) == '*') {
                            if (((i + 1 < searchInLength) ? searchIn.charAt(i + 1) : '\0') != '/') {
                                continue;
                            }
                            break;
                        }
                    }
                    ++i;
                }
                else {
                    ++i;
                    ++i;
                    int j;
                    for (j = 1; j <= 5 && i + j < searchInLength && Character.isDigit(searchIn.charAt(i + j)); ++j) {}
                    if (j == 5) {
                        i += 5;
                    }
                }
                c2 = ((i + 1 < searchInLength) ? searchIn.charAt(i + 1) : '\0');
                c3 = ((i + 2 < searchInLength) ? searchIn.charAt(i + 2) : '\0');
            }
            else if (searchMode.contains(SearchMode.SKIP_BLOCK_COMMENTS) && c0 == '*' && c2 == '/') {
                ++i;
                c2 = c3;
                c3 = ((i + 2 < searchInLength) ? searchIn.charAt(i + 2) : '\0');
            }
            else if (searchMode.contains(SearchMode.SKIP_LINE_COMMENTS) && ((c0 == '-' && c2 == '-' && (Character.isWhitespace(c3) || (dashDashCommentImmediateEnd = (c3 == ';')) || c3 == '\0')) || c0 == '#')) {
                if (dashDashCommentImmediateEnd) {
                    ++i;
                    c2 = ((++i + 1 < searchInLength) ? searchIn.charAt(i + 1) : '\0');
                    c3 = ((i + 2 < searchInLength) ? searchIn.charAt(i + 2) : '\0');
                }
                else {
                    while (++i <= stopPosition && (c0 = searchIn.charAt(i)) != '\n' && c0 != '\r') {}
                    c2 = ((i + 1 < searchInLength) ? searchIn.charAt(i + 1) : '\0');
                    if (c0 == '\r' && c2 == '\n') {
                        c2 = ((++i + 1 < searchInLength) ? searchIn.charAt(i + 1) : '\0');
                    }
                    c3 = ((i + 2 < searchInLength) ? searchIn.charAt(i + 2) : '\0');
                }
            }
            else if (!searchMode.contains(SearchMode.SKIP_WHITE_SPACE) || !Character.isWhitespace(c0)) {
                return i;
            }
        }
        return -1;
    }
    
    private static boolean isCharAtPosNotEqualIgnoreCase(final String searchIn, final int pos, final char firstCharOfSearchForUc, final char firstCharOfSearchForLc) {
        return Character.toLowerCase(searchIn.charAt(pos)) != firstCharOfSearchForLc && Character.toUpperCase(searchIn.charAt(pos)) != firstCharOfSearchForUc;
    }
    
    private static boolean isCharEqualIgnoreCase(final char charToCompare, final char compareToCharUC, final char compareToCharLC) {
        return Character.toLowerCase(charToCompare) == compareToCharLC || Character.toUpperCase(charToCompare) == compareToCharUC;
    }
    
    public static List<String> split(final String stringToSplit, final String delimiter, final boolean trim) {
        if (stringToSplit == null) {
            return new ArrayList<String>();
        }
        if (delimiter == null) {
            throw new IllegalArgumentException();
        }
        final String[] tokens = stringToSplit.split(delimiter, -1);
        Stream<String> tokensStream = Arrays.asList(tokens).stream();
        if (trim) {
            tokensStream = tokensStream.map((Function<? super String, ? extends String>)String::trim);
        }
        return tokensStream.collect((Collector<? super String, ?, List<String>>)Collectors.toList());
    }
    
    public static List<String> split(final String stringToSplit, final String delimiter, final String openingMarkers, final String closingMarkers, final boolean trim) {
        return split(stringToSplit, delimiter, openingMarkers, closingMarkers, "", trim);
    }
    
    public static List<String> split(final String stringToSplit, final String delimiter, final String openingMarkers, final String closingMarkers, final boolean trim, final Set<SearchMode> searchMode) {
        return split(stringToSplit, delimiter, openingMarkers, closingMarkers, "", trim, searchMode);
    }
    
    public static List<String> split(final String stringToSplit, final String delimiter, final String openingMarkers, final String closingMarkers, final String overridingMarkers, final boolean trim) {
        return split(stringToSplit, delimiter, openingMarkers, closingMarkers, overridingMarkers, trim, StringUtils.SEARCH_MODE__MRK_COM_WS);
    }
    
    public static List<String> split(final String stringToSplit, final String delimiter, final String openingMarkers, final String closingMarkers, final String overridingMarkers, final boolean trim, final Set<SearchMode> searchMode) {
        if (stringToSplit == null) {
            return new ArrayList<String>();
        }
        if (delimiter == null) {
            throw new IllegalArgumentException();
        }
        int delimPos = 0;
        int currentPos = 0;
        final List<String> splitTokens = new ArrayList<String>();
        while ((delimPos = indexOfIgnoreCase(currentPos, stringToSplit, delimiter, openingMarkers, closingMarkers, overridingMarkers, searchMode)) != -1) {
            String token = stringToSplit.substring(currentPos, delimPos);
            if (trim) {
                token = token.trim();
            }
            splitTokens.add(token);
            currentPos = delimPos + delimiter.length();
        }
        String token = stringToSplit.substring(currentPos);
        if (trim) {
            token = token.trim();
        }
        splitTokens.add(token);
        return splitTokens;
    }
    
    private static boolean startsWith(final byte[] dataFrom, final String chars) {
        final int charsLength = chars.length();
        if (dataFrom.length < charsLength) {
            return false;
        }
        for (int i = 0; i < charsLength; ++i) {
            if (dataFrom[i] != chars.charAt(i)) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean startsWithIgnoreCase(final String searchIn, final int startAt, final String searchFor) {
        return searchIn.regionMatches(true, startAt, searchFor, 0, searchFor.length());
    }
    
    public static boolean startsWithIgnoreCase(final String searchIn, final String searchFor) {
        return startsWithIgnoreCase(searchIn, 0, searchFor);
    }
    
    public static boolean startsWithIgnoreCaseAndNonAlphaNumeric(final String searchIn, final String searchFor) {
        if (searchIn == null) {
            return searchFor == null;
        }
        int beginPos = 0;
        for (int inLength = searchIn.length(); beginPos < inLength; ++beginPos) {
            final char c = searchIn.charAt(beginPos);
            if (Character.isLetterOrDigit(c)) {
                break;
            }
        }
        return startsWithIgnoreCase(searchIn, beginPos, searchFor);
    }
    
    public static boolean startsWithIgnoreCaseAndWs(final String searchIn, final String searchFor) {
        return startsWithIgnoreCaseAndWs(searchIn, searchFor, 0);
    }
    
    public static boolean startsWithIgnoreCaseAndWs(final String searchIn, final String searchFor, int beginPos) {
        if (searchIn == null) {
            return searchFor == null;
        }
        for (int inLength = searchIn.length(); beginPos < inLength && Character.isWhitespace(searchIn.charAt(beginPos)); ++beginPos) {}
        return startsWithIgnoreCase(searchIn, beginPos, searchFor);
    }
    
    public static int startsWithIgnoreCaseAndWs(final String searchIn, final String[] searchFor) {
        for (int i = 0; i < searchFor.length; ++i) {
            if (startsWithIgnoreCaseAndWs(searchIn, searchFor[i], 0)) {
                return i;
            }
        }
        return -1;
    }
    
    public static byte[] stripEnclosure(final byte[] source, final String prefix, final String suffix) {
        if (source.length >= prefix.length() + suffix.length() && startsWith(source, prefix) && endsWith(source, suffix)) {
            final int totalToStrip = prefix.length() + suffix.length();
            final int enclosedLength = source.length - totalToStrip;
            final byte[] enclosed = new byte[enclosedLength];
            final int startPos = prefix.length();
            final int numToCopy = enclosed.length;
            System.arraycopy(source, startPos, enclosed, 0, numToCopy);
            return enclosed;
        }
        return source;
    }
    
    public static String toAsciiString(final byte[] buffer) {
        return toAsciiString(buffer, 0, buffer.length);
    }
    
    public static String toAsciiString(final byte[] buffer, final int startPos, final int length) {
        final char[] charArray = new char[length];
        int readpoint = startPos;
        for (int i = 0; i < length; ++i) {
            charArray[i] = (char)buffer[readpoint];
            ++readpoint;
        }
        return new String(charArray);
    }
    
    public static boolean wildCompareIgnoreCase(final String searchIn, final String searchFor) {
        return wildCompareInternal(searchIn, searchFor) == 0;
    }
    
    private static int wildCompareInternal(final String searchIn, final String searchFor) {
        if (searchIn == null || searchFor == null) {
            return -1;
        }
        if (searchFor.equals("%")) {
            return 0;
        }
        int searchForPos = 0;
        final int searchForEnd = searchFor.length();
        int searchInPos = 0;
        final int searchInEnd = searchIn.length();
        int result = -1;
    Label_0376:
        while (searchForPos != searchForEnd) {
            while (searchFor.charAt(searchForPos) != '%' && searchFor.charAt(searchForPos) != '_') {
                if (searchFor.charAt(searchForPos) == '\\' && searchForPos + 1 != searchForEnd) {
                    ++searchForPos;
                }
                if (searchInPos == searchInEnd || Character.toUpperCase(searchFor.charAt(searchForPos++)) != Character.toUpperCase(searchIn.charAt(searchInPos++))) {
                    return 1;
                }
                if (searchForPos == searchForEnd) {
                    return (searchInPos != searchInEnd) ? 1 : 0;
                }
                result = 1;
            }
            Label_0193: {
                if (searchFor.charAt(searchForPos) == '_') {
                    while (searchInPos != searchInEnd) {
                        ++searchInPos;
                        if (++searchForPos >= searchForEnd || searchFor.charAt(searchForPos) != '_') {
                            if (searchForPos == searchForEnd) {
                                break Label_0376;
                            }
                            break Label_0193;
                        }
                    }
                    return result;
                }
            }
            if (searchFor.charAt(searchForPos) == '%') {
                ++searchForPos;
                while (searchForPos != searchForEnd) {
                    if (searchFor.charAt(searchForPos) != '%') {
                        if (searchFor.charAt(searchForPos) != '_') {
                            break;
                        }
                        if (searchInPos == searchInEnd) {
                            return -1;
                        }
                        ++searchInPos;
                    }
                    ++searchForPos;
                }
                if (searchForPos == searchForEnd) {
                    return 0;
                }
                if (searchInPos == searchInEnd) {
                    return -1;
                }
                char cmp;
                if ((cmp = searchFor.charAt(searchForPos)) == '\\' && searchForPos + 1 != searchForEnd) {
                    cmp = searchFor.charAt(++searchForPos);
                }
                ++searchForPos;
                while (true) {
                    if (searchInPos != searchInEnd && Character.toUpperCase(searchIn.charAt(searchInPos)) != Character.toUpperCase(cmp)) {
                        ++searchInPos;
                    }
                    else {
                        if (searchInPos++ == searchInEnd) {
                            return -1;
                        }
                        final int tmp = wildCompareInternal(searchIn.substring(searchInPos), searchFor.substring(searchForPos));
                        if (tmp <= 0) {
                            return tmp;
                        }
                        if (searchInPos == searchInEnd) {
                            return -1;
                        }
                        continue;
                    }
                }
            }
        }
        return (searchInPos != searchInEnd) ? 1 : 0;
    }
    
    public static int lastIndexOf(final byte[] s, final char c) {
        if (s == null) {
            return -1;
        }
        for (int i = s.length - 1; i >= 0; --i) {
            if (s[i] == c) {
                return i;
            }
        }
        return -1;
    }
    
    public static int indexOf(final byte[] s, final char c) {
        if (s == null) {
            return -1;
        }
        for (int length = s.length, i = 0; i < length; ++i) {
            if (s[i] == c) {
                return i;
            }
        }
        return -1;
    }
    
    public static boolean isNullOrEmpty(final String toTest) {
        return toTest == null || toTest.isEmpty();
    }
    
    public static String stripComments(final String src, final String stringOpens, final String stringCloses, final boolean slashStarComments, final boolean slashSlashComments, final boolean hashComments, final boolean dashDashComments) {
        if (src == null) {
            return null;
        }
        final StringBuilder strBuilder = new StringBuilder(src.length());
        final StringReader sourceReader = new StringReader(src);
        int contextMarker = 0;
        final boolean escaped = false;
        int markerTypeFound = -1;
        int ind = 0;
        int currentChar = 0;
        try {
            while ((currentChar = sourceReader.read()) != -1) {
                if (markerTypeFound != -1 && currentChar == stringCloses.charAt(markerTypeFound) && !escaped) {
                    contextMarker = 0;
                    markerTypeFound = -1;
                }
                else if ((ind = stringOpens.indexOf(currentChar)) != -1 && !escaped && contextMarker == 0) {
                    markerTypeFound = ind;
                    contextMarker = currentChar;
                }
                if (contextMarker == 0 && currentChar == 47 && (slashSlashComments || slashStarComments)) {
                    currentChar = sourceReader.read();
                    if (currentChar == 42 && slashStarComments) {
                        for (int prevChar = 0; (currentChar = sourceReader.read()) != 47 || prevChar != 42; prevChar = currentChar) {
                            if (currentChar == 13) {
                                currentChar = sourceReader.read();
                                if (currentChar == 10) {
                                    currentChar = sourceReader.read();
                                }
                            }
                            else if (currentChar == 10) {
                                currentChar = sourceReader.read();
                            }
                            if (currentChar < 0) {
                                break;
                            }
                        }
                        continue;
                    }
                    if (currentChar == 47 && slashSlashComments) {
                        while ((currentChar = sourceReader.read()) != 10 && currentChar != 13 && currentChar >= 0) {}
                    }
                }
                else if (contextMarker == 0 && currentChar == 35 && hashComments) {
                    while ((currentChar = sourceReader.read()) != 10 && currentChar != 13 && currentChar >= 0) {}
                }
                else if (contextMarker == 0 && currentChar == 45 && dashDashComments) {
                    currentChar = sourceReader.read();
                    if (currentChar == -1 || currentChar != 45) {
                        strBuilder.append('-');
                        if (currentChar != -1) {
                            strBuilder.append((char)currentChar);
                            continue;
                        }
                        continue;
                    }
                    else {
                        while ((currentChar = sourceReader.read()) != 10 && currentChar != 13 && currentChar >= 0) {}
                    }
                }
                if (currentChar != -1) {
                    strBuilder.append((char)currentChar);
                }
            }
        }
        catch (IOException ex) {}
        return strBuilder.toString();
    }
    
    public static String sanitizeProcOrFuncName(final String src) {
        if (src == null || src.equals("%")) {
            return null;
        }
        return src;
    }
    
    public static List<String> splitDBdotName(final String source, final String catalog, final String quoteId, final boolean isNoBslashEscSet) {
        if (source == null || source.equals("%")) {
            return Collections.emptyList();
        }
        int dotIndex = -1;
        if (" ".equals(quoteId)) {
            dotIndex = source.indexOf(".");
        }
        else {
            dotIndex = indexOfIgnoreCase(0, source, ".", quoteId, quoteId, isNoBslashEscSet ? StringUtils.SEARCH_MODE__MRK_WS : StringUtils.SEARCH_MODE__BSESC_MRK_WS);
        }
        String database = catalog;
        String entityName;
        if (dotIndex != -1) {
            database = unQuoteIdentifier(source.substring(0, dotIndex), quoteId);
            entityName = unQuoteIdentifier(source.substring(dotIndex + 1), quoteId);
        }
        else {
            entityName = unQuoteIdentifier(source, quoteId);
        }
        return Arrays.asList(database, entityName);
    }
    
    public static String getFullyQualifiedName(final String catalog, final String entity, final String quoteId, final boolean isPedantic) {
        final StringBuilder fullyQualifiedName = new StringBuilder(quoteIdentifier((catalog == null) ? "" : catalog, quoteId, isPedantic));
        fullyQualifiedName.append('.');
        fullyQualifiedName.append(quoteIdentifier(entity, quoteId, isPedantic));
        return fullyQualifiedName.toString();
    }
    
    public static boolean isEmptyOrWhitespaceOnly(final String str) {
        if (str == null || str.length() == 0) {
            return true;
        }
        for (int length = str.length(), i = 0; i < length; ++i) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }
    
    public static String escapeQuote(String src, final String quotChar) {
        if (src == null) {
            return null;
        }
        src = toString(stripEnclosure(src.getBytes(), quotChar, quotChar));
        int lastNdx = src.indexOf(quotChar);
        String tmpSrc = src.substring(0, lastNdx);
        tmpSrc = tmpSrc + quotChar + quotChar;
        String tmpRest;
        for (tmpRest = src.substring(lastNdx + 1, src.length()), lastNdx = tmpRest.indexOf(quotChar); lastNdx > -1; lastNdx = tmpRest.indexOf(quotChar)) {
            tmpSrc += tmpRest.substring(0, lastNdx);
            tmpSrc = tmpSrc + quotChar + quotChar;
            tmpRest = tmpRest.substring(lastNdx + 1, tmpRest.length());
        }
        tmpSrc = (src = tmpSrc + tmpRest);
        return src;
    }
    
    public static String quoteIdentifier(String identifier, final String quoteChar, final boolean isPedantic) {
        if (identifier == null) {
            return null;
        }
        identifier = identifier.trim();
        final int quoteCharLength = quoteChar.length();
        if (quoteCharLength == 0) {
            return identifier;
        }
        if (!isPedantic && identifier.startsWith(quoteChar) && identifier.endsWith(quoteChar)) {
            String identifierQuoteTrimmed;
            int quoteCharPos;
            int quoteCharNextExpectedPos;
            int quoteCharNextPosition;
            for (identifierQuoteTrimmed = identifier.substring(quoteCharLength, identifier.length() - quoteCharLength), quoteCharPos = identifierQuoteTrimmed.indexOf(quoteChar); quoteCharPos >= 0; quoteCharPos = identifierQuoteTrimmed.indexOf(quoteChar, quoteCharNextPosition + quoteCharLength)) {
                quoteCharNextExpectedPos = quoteCharPos + quoteCharLength;
                quoteCharNextPosition = identifierQuoteTrimmed.indexOf(quoteChar, quoteCharNextExpectedPos);
                if (quoteCharNextPosition != quoteCharNextExpectedPos) {
                    break;
                }
            }
            if (quoteCharPos < 0) {
                return identifier;
            }
        }
        return quoteChar + identifier.replaceAll(quoteChar, quoteChar + quoteChar) + quoteChar;
    }
    
    public static String quoteIdentifier(final String identifier, final boolean isPedantic) {
        return quoteIdentifier(identifier, "`", isPedantic);
    }
    
    public static String unQuoteIdentifier(String identifier, final String quoteChar) {
        if (identifier == null) {
            return null;
        }
        identifier = identifier.trim();
        final int quoteCharLength = quoteChar.length();
        if (quoteCharLength == 0) {
            return identifier;
        }
        if (identifier.startsWith(quoteChar) && identifier.endsWith(quoteChar)) {
            final String identifierQuoteTrimmed = identifier.substring(quoteCharLength, identifier.length() - quoteCharLength);
            int quoteCharNextPosition;
            for (int quoteCharPos = identifierQuoteTrimmed.indexOf(quoteChar); quoteCharPos >= 0; quoteCharPos = identifierQuoteTrimmed.indexOf(quoteChar, quoteCharNextPosition + quoteCharLength)) {
                final int quoteCharNextExpectedPos = quoteCharPos + quoteCharLength;
                quoteCharNextPosition = identifierQuoteTrimmed.indexOf(quoteChar, quoteCharNextExpectedPos);
                if (quoteCharNextPosition != quoteCharNextExpectedPos) {
                    return identifier;
                }
            }
            return identifier.substring(quoteCharLength, identifier.length() - quoteCharLength).replaceAll(quoteChar + quoteChar, quoteChar);
        }
        return identifier;
    }
    
    public static int indexOfQuoteDoubleAware(final String searchIn, final String quoteChar, final int startFrom) {
        if (searchIn == null || quoteChar == null || quoteChar.length() == 0 || startFrom > searchIn.length()) {
            return -1;
        }
        final int lastIndex = searchIn.length() - 1;
        int beginPos = startFrom;
        int pos = -1;
        boolean next = true;
        while (next) {
            pos = searchIn.indexOf(quoteChar, beginPos);
            if (pos == -1 || pos == lastIndex || !searchIn.startsWith(quoteChar, pos + 1)) {
                next = false;
            }
            else {
                beginPos = pos + 2;
            }
        }
        return pos;
    }
    
    public static String toString(final byte[] value, final int offset, final int length, final String encoding) {
        if (encoding == null || "null".equalsIgnoreCase(encoding)) {
            return new String(value, offset, length);
        }
        try {
            return new String(value, offset, length, encoding);
        }
        catch (UnsupportedEncodingException uee) {
            throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("StringUtils.0", new Object[] { encoding }), uee);
        }
    }
    
    public static String toString(final byte[] value, final String encoding) {
        if (encoding == null) {
            return new String(value);
        }
        try {
            return new String(value, encoding);
        }
        catch (UnsupportedEncodingException uee) {
            throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("StringUtils.0", new Object[] { encoding }), uee);
        }
    }
    
    public static String toString(final byte[] value, final int offset, final int length) {
        return new String(value, offset, length);
    }
    
    public static String toString(final byte[] value) {
        return new String(value);
    }
    
    public static byte[] getBytes(final char[] value) {
        return getBytes(value, 0, value.length);
    }
    
    public static byte[] getBytes(final char[] c, final String encoding) {
        return getBytes(c, 0, c.length, encoding);
    }
    
    public static byte[] getBytes(final char[] value, final int offset, final int length) {
        return getBytes(value, offset, length, null);
    }
    
    public static byte[] getBytes(final char[] value, final int offset, final int length, final String encoding) {
        Charset cs;
        try {
            if (encoding == null) {
                cs = Charset.defaultCharset();
            }
            else {
                cs = Charset.forName(encoding);
            }
        }
        catch (UnsupportedCharsetException ex) {
            throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("StringUtils.0", new Object[] { encoding }), ex);
        }
        final ByteBuffer buf = cs.encode(CharBuffer.wrap(value, offset, length));
        final int encodedLen = buf.limit();
        final byte[] asBytes = new byte[encodedLen];
        buf.get(asBytes, 0, encodedLen);
        return asBytes;
    }
    
    public static byte[] getBytes(final String value) {
        return value.getBytes();
    }
    
    public static byte[] getBytes(final String value, final int offset, final int length) {
        return value.substring(offset, offset + length).getBytes();
    }
    
    public static byte[] getBytes(final String value, final int offset, final int length, final String encoding) {
        if (encoding == null) {
            return getBytes(value, offset, length);
        }
        try {
            return value.substring(offset, offset + length).getBytes(encoding);
        }
        catch (UnsupportedEncodingException uee) {
            throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("StringUtils.0", new Object[] { encoding }), uee);
        }
    }
    
    public static final boolean isValidIdChar(final char c) {
        return "abcdefghijklmnopqrstuvwxyzABCDEFGHIGKLMNOPQRSTUVWXYZ0123456789$_#@".indexOf(c) != -1;
    }
    
    public static void appendAsHex(final StringBuilder builder, final byte[] bytes) {
        builder.append("0x");
        for (final byte b : bytes) {
            builder.append(StringUtils.HEX_DIGITS[b >>> 4 & 0xF]).append(StringUtils.HEX_DIGITS[b & 0xF]);
        }
    }
    
    public static void appendAsHex(final StringBuilder builder, final int value) {
        if (value == 0) {
            builder.append("0x0");
            return;
        }
        int shift = 32;
        boolean nonZeroFound = false;
        builder.append("0x");
        do {
            shift -= 4;
            final byte nibble = (byte)(value >>> shift & 0xF);
            if (nonZeroFound) {
                builder.append(StringUtils.HEX_DIGITS[nibble]);
            }
            else {
                if (nibble == 0) {
                    continue;
                }
                builder.append(StringUtils.HEX_DIGITS[nibble]);
                nonZeroFound = true;
            }
        } while (shift != 0);
    }
    
    public static byte[] getBytesNullTerminated(final String value, final String encoding) {
        final Charset cs = Charset.forName(encoding);
        final ByteBuffer buf = cs.encode(value);
        final int encodedLen = buf.limit();
        final byte[] asBytes = new byte[encodedLen + 1];
        buf.get(asBytes, 0, encodedLen);
        asBytes[encodedLen] = 0;
        return asBytes;
    }
    
    public static boolean canHandleAsServerPreparedStatementNoCache(final String sql, final ServerVersion serverVersion, final boolean allowMultiQueries, final boolean noBackslashEscapes, final boolean useAnsiQuotes) {
        if (startsWithIgnoreCaseAndNonAlphaNumeric(sql, "CALL")) {
            return false;
        }
        boolean canHandleAsStatement = true;
        final boolean allowBackslashEscapes = !noBackslashEscapes;
        final String quoteChar = useAnsiQuotes ? "\"" : "'";
        if (allowMultiQueries) {
            if (indexOfIgnoreCase(0, sql, ";", quoteChar, quoteChar, allowBackslashEscapes ? StringUtils.SEARCH_MODE__ALL : StringUtils.SEARCH_MODE__MRK_COM_WS) != -1) {
                canHandleAsStatement = false;
            }
        }
        else if (startsWithIgnoreCaseAndWs(sql, "XA ")) {
            canHandleAsStatement = false;
        }
        else if (startsWithIgnoreCaseAndWs(sql, "CREATE TABLE")) {
            canHandleAsStatement = false;
        }
        else if (startsWithIgnoreCaseAndWs(sql, "DO")) {
            canHandleAsStatement = false;
        }
        else if (startsWithIgnoreCaseAndWs(sql, "SET")) {
            canHandleAsStatement = false;
        }
        else if (startsWithIgnoreCaseAndWs(sql, "SHOW WARNINGS") && serverVersion.meetsMinimum(ServerVersion.parseVersion("5.7.2"))) {
            canHandleAsStatement = false;
        }
        else if (sql.startsWith("/* ping */")) {
            canHandleAsStatement = false;
        }
        return canHandleAsStatement;
    }
    
    public static String padString(final String stringVal, final int requiredLength) {
        final int currentLength = stringVal.length();
        final int difference = requiredLength - currentLength;
        if (difference > 0) {
            final StringBuilder paddedBuf = new StringBuilder(requiredLength);
            paddedBuf.append(stringVal);
            paddedBuf.append(StringUtils.EMPTY_SPACE, 0, difference);
            return paddedBuf.toString();
        }
        return stringVal;
    }
    
    public static int safeIntParse(final String intAsString) {
        try {
            return Integer.parseInt(intAsString);
        }
        catch (NumberFormatException nfe) {
            return 0;
        }
    }
    
    public static boolean isStrictlyNumeric(final CharSequence cs) {
        if (cs == null || cs.length() == 0) {
            return false;
        }
        for (int i = 0; i < cs.length(); ++i) {
            if (!Character.isDigit(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }
    
    public static String safeTrim(final String toTrim) {
        return isNullOrEmpty(toTrim) ? toTrim : toTrim.trim();
    }
    
    public static String stringArrayToString(final String[] elems, final String prefix, final String midDelimiter, final String lastDelimiter, final String suffix) {
        final StringBuilder valuesString = new StringBuilder();
        if (elems.length > 1) {
            valuesString.append(Arrays.stream(elems).limit(elems.length - 1).collect(Collectors.joining(midDelimiter, prefix, lastDelimiter)));
        }
        else {
            valuesString.append(prefix);
        }
        valuesString.append(elems[elems.length - 1]).append(suffix);
        return valuesString.toString();
    }
    
    public static final void escapeblockFast(final byte[] buf, final ByteArrayOutputStream bytesOut, final int size, final boolean useAnsiMode) {
        int lastwritten = 0;
        for (int i = 0; i < size; ++i) {
            final byte b = buf[i];
            if (b == 0) {
                if (i > lastwritten) {
                    bytesOut.write(buf, lastwritten, i - lastwritten);
                }
                bytesOut.write(92);
                bytesOut.write(48);
                lastwritten = i + 1;
            }
            else if (b == 92 || b == 39 || (!useAnsiMode && b == 34)) {
                if (i > lastwritten) {
                    bytesOut.write(buf, lastwritten, i - lastwritten);
                }
                bytesOut.write(92);
                lastwritten = i;
            }
        }
        if (lastwritten < size) {
            bytesOut.write(buf, lastwritten, size - lastwritten);
        }
    }
    
    public static boolean hasWildcards(final String src) {
        return indexOfIgnoreCase(0, src, "%") > -1 || indexOfIgnoreCase(0, src, "_") > -1;
    }
    
    public static String getUniqueSavepointId() {
        final String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("-", "_");
    }
    
    public static String joinWithSerialComma(final List<?> elements) {
        if (elements == null || elements.size() == 0) {
            return "";
        }
        if (elements.size() == 1) {
            return elements.get(0).toString();
        }
        if (elements.size() == 2) {
            return elements.get(0) + " and " + elements.get(1);
        }
        return elements.subList(0, elements.size() - 1).stream().map((Function<? super Object, ?>)Object::toString).collect((Collector<? super Object, ?, String>)Collectors.joining(", ", "", ", and ")) + elements.get(elements.size() - 1).toString();
    }
    
    static {
        SEARCH_MODE__ALL = Collections.unmodifiableSet((Set<? extends SearchMode>)EnumSet.allOf(SearchMode.class));
        SEARCH_MODE__MRK_COM_WS = Collections.unmodifiableSet((Set<? extends SearchMode>)EnumSet.of(SearchMode.SKIP_BETWEEN_MARKERS, SearchMode.SKIP_BLOCK_COMMENTS, SearchMode.SKIP_LINE_COMMENTS, SearchMode.SKIP_WHITE_SPACE));
        SEARCH_MODE__BSESC_COM_WS = Collections.unmodifiableSet((Set<? extends SearchMode>)EnumSet.of(SearchMode.ALLOW_BACKSLASH_ESCAPE, SearchMode.SKIP_BLOCK_COMMENTS, SearchMode.SKIP_LINE_COMMENTS, SearchMode.SKIP_WHITE_SPACE));
        SEARCH_MODE__BSESC_MRK_WS = Collections.unmodifiableSet((Set<? extends SearchMode>)EnumSet.of(SearchMode.ALLOW_BACKSLASH_ESCAPE, SearchMode.SKIP_BETWEEN_MARKERS, SearchMode.SKIP_WHITE_SPACE));
        SEARCH_MODE__COM_WS = Collections.unmodifiableSet((Set<? extends SearchMode>)EnumSet.of(SearchMode.SKIP_BLOCK_COMMENTS, SearchMode.SKIP_LINE_COMMENTS, SearchMode.SKIP_WHITE_SPACE));
        SEARCH_MODE__MRK_WS = Collections.unmodifiableSet((Set<? extends SearchMode>)EnumSet.of(SearchMode.SKIP_BETWEEN_MARKERS, SearchMode.SKIP_WHITE_SPACE));
        SEARCH_MODE__NONE = Collections.unmodifiableSet((Set<? extends SearchMode>)EnumSet.noneOf(SearchMode.class));
        HEX_DIGITS = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
        EMPTY_SPACE = new char[255];
        for (int i = 0; i < StringUtils.EMPTY_SPACE.length; ++i) {
            StringUtils.EMPTY_SPACE[i] = ' ';
        }
    }
    
    public enum SearchMode
    {
        ALLOW_BACKSLASH_ESCAPE, 
        SKIP_BETWEEN_MARKERS, 
        SKIP_BLOCK_COMMENTS, 
        SKIP_LINE_COMMENTS, 
        SKIP_WHITE_SPACE;
    }
}
