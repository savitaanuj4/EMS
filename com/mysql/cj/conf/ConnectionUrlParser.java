
package com.mysql.cj.conf;

import java.util.Collections;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.ArrayList;
import java.net.URI;
import java.util.Collection;
import java.util.Iterator;
import com.mysql.cj.util.StringUtils;
import java.util.regex.Matcher;
import com.mysql.cj.exceptions.UnsupportedConnectionStringException;
import com.mysql.cj.exceptions.ExceptionFactory;
import com.mysql.cj.Messages;
import com.mysql.cj.exceptions.WrongArgumentException;
import java.util.Map;
import java.util.List;
import java.util.regex.Pattern;

public class ConnectionUrlParser implements DatabaseUrlContainer
{
    private static final String DUMMY_SCHEMA = "cj://";
    private static final String USER_PASS_SEPARATOR = ":";
    private static final String USER_HOST_SEPARATOR = "@";
    private static final String HOSTS_SEPARATOR = ",";
    private static final String KEY_VALUE_HOST_INFO_OPENING_MARKER = "(";
    private static final String KEY_VALUE_HOST_INFO_CLOSING_MARKER = ")";
    private static final String HOSTS_LIST_OPENING_MARKERS = "[(";
    private static final String HOSTS_LIST_CLOSING_MARKERS = "])";
    private static final String ADDRESS_EQUALS_HOST_INFO_PREFIX = "ADDRESS=";
    private static final Pattern CONNECTION_STRING_PTRN;
    private static final Pattern SCHEME_PTRN;
    private static final Pattern HOST_LIST_PTRN;
    private static final Pattern GENERIC_HOST_PTRN;
    private static final Pattern KEY_VALUE_HOST_PTRN;
    private static final Pattern ADDRESS_EQUALS_HOST_PTRN;
    private static final Pattern PROPERTIES_PTRN;
    private final String baseConnectionString;
    private String scheme;
    private String authority;
    private String path;
    private String query;
    private List<HostInfo> parsedHosts;
    private Map<String, String> parsedProperties;
    
    public static ConnectionUrlParser parseConnectionString(final String connString) {
        return new ConnectionUrlParser(connString);
    }
    
    private ConnectionUrlParser(final String connString) {
        this.parsedHosts = null;
        this.parsedProperties = null;
        if (connString == null) {
            throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("ConnectionString.0"));
        }
        if (!isConnectionStringSupported(connString)) {
            throw ExceptionFactory.createException(UnsupportedConnectionStringException.class, Messages.getString("ConnectionString.17", new String[] { connString }));
        }
        this.baseConnectionString = connString;
        this.parseConnectionString();
    }
    
    public static boolean isConnectionStringSupported(final String connString) {
        if (connString == null) {
            throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("ConnectionString.0"));
        }
        final Matcher matcher = ConnectionUrlParser.SCHEME_PTRN.matcher(connString);
        return matcher.matches() && ConnectionUrl.Type.isSupported(decode(matcher.group("scheme")));
    }
    
    private void parseConnectionString() {
        final String connString = this.baseConnectionString;
        final Matcher matcher = ConnectionUrlParser.CONNECTION_STRING_PTRN.matcher(connString);
        if (!matcher.matches()) {
            throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("ConnectionString.1"));
        }
        this.scheme = decode(matcher.group("scheme"));
        this.authority = matcher.group("authority");
        this.path = ((matcher.group("path") == null) ? null : decode(matcher.group("path")).trim());
        this.query = matcher.group("query");
    }
    
    private void parseAuthoritySection() {
        if (StringUtils.isNullOrEmpty(this.authority)) {
            this.parsedHosts.add(new HostInfo());
            return;
        }
        final List<String> authoritySegments = StringUtils.split(this.authority, ",", "[(", "])", true, StringUtils.SEARCH_MODE__MRK_WS);
        for (final String hi : authoritySegments) {
            this.parseAuthoritySegment(hi);
        }
    }
    
    private void parseAuthoritySegment(final String authSegment) {
        final Pair<String, String> userHostInfoSplit = this.splitByUserInfoAndHostInfo(authSegment);
        final String userInfo = StringUtils.safeTrim(userHostInfoSplit.left);
        String user = null;
        String password = null;
        if (!StringUtils.isNullOrEmpty(userInfo)) {
            final Pair<String, String> userInfoPair = parseUserInfo(userInfo);
            user = decode(StringUtils.safeTrim(userInfoPair.left));
            password = decode(StringUtils.safeTrim(userInfoPair.right));
        }
        final String hostInfo = StringUtils.safeTrim(userHostInfoSplit.right);
        HostInfo hi = this.buildHostInfoForEmptyHost(user, password, hostInfo);
        if (hi != null) {
            this.parsedHosts.add(hi);
            return;
        }
        hi = this.buildHostInfoResortingToUriParser(user, password, authSegment);
        if (hi != null) {
            this.parsedHosts.add(hi);
            return;
        }
        final List<HostInfo> hiList = this.buildHostInfoResortingToSubHostsListParser(user, password, hostInfo);
        if (hiList != null) {
            this.parsedHosts.addAll(hiList);
            return;
        }
        hi = this.buildHostInfoResortingToKeyValueSyntaxParser(user, password, hostInfo);
        if (hi != null) {
            this.parsedHosts.add(hi);
            return;
        }
        hi = this.buildHostInfoResortingToAddressEqualsSyntaxParser(user, password, hostInfo);
        if (hi != null) {
            this.parsedHosts.add(hi);
            return;
        }
        hi = this.buildHostInfoResortingToGenericSyntaxParser(user, password, hostInfo);
        if (hi != null) {
            this.parsedHosts.add(hi);
            return;
        }
        throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("ConnectionString.2", new Object[] { authSegment }));
    }
    
    private HostInfo buildHostInfoForEmptyHost(final String user, final String password, final String hostInfo) {
        if (!StringUtils.isNullOrEmpty(hostInfo)) {
            return null;
        }
        if (StringUtils.isNullOrEmpty(user) && StringUtils.isNullOrEmpty(password)) {
            return new HostInfo();
        }
        return new HostInfo(this, null, -1, user, password);
    }
    
    private HostInfo buildHostInfoResortingToUriParser(final String user, final String password, final String hostInfo) {
        String host = null;
        int port = -1;
        try {
            final URI uri = URI.create("cj://" + hostInfo);
            if (uri.getHost() != null) {
                host = decode(uri.getHost());
            }
            if (uri.getPort() != -1) {
                port = uri.getPort();
            }
            if (uri.getUserInfo() != null) {
                return null;
            }
        }
        catch (IllegalArgumentException e) {
            return null;
        }
        if (host != null || port != -1) {
            return new HostInfo(this, host, port, user, password);
        }
        return null;
    }
    
    private List<HostInfo> buildHostInfoResortingToSubHostsListParser(final String user, final String password, final String hostInfo) {
        final Matcher matcher = ConnectionUrlParser.HOST_LIST_PTRN.matcher(hostInfo);
        if (matcher.matches()) {
            final String hosts = matcher.group("hosts");
            final List<String> hostsList = StringUtils.split(hosts, ",", "[(", "])", true, StringUtils.SEARCH_MODE__MRK_WS);
            final boolean maybeIPv6 = hostsList.size() == 1 && hostsList.get(0).matches("(?i)^[\\dabcdef:]+$");
            final List<HostInfo> hostInfoList = new ArrayList<HostInfo>();
            for (final String h : hostsList) {
                HostInfo hi;
                if ((hi = this.buildHostInfoForEmptyHost(user, password, h)) != null) {
                    hostInfoList.add(hi);
                }
                else if ((hi = this.buildHostInfoResortingToUriParser(user, password, h)) != null || (maybeIPv6 && (hi = this.buildHostInfoResortingToUriParser(user, password, "[" + h + "]")) != null)) {
                    hostInfoList.add(hi);
                }
                else if ((hi = this.buildHostInfoResortingToKeyValueSyntaxParser(user, password, h)) != null) {
                    hostInfoList.add(hi);
                }
                else if ((hi = this.buildHostInfoResortingToAddressEqualsSyntaxParser(user, password, h)) != null) {
                    hostInfoList.add(hi);
                }
                else {
                    if ((hi = this.buildHostInfoResortingToGenericSyntaxParser(user, password, h)) == null) {
                        return null;
                    }
                    hostInfoList.add(hi);
                }
            }
            return hostInfoList;
        }
        return null;
    }
    
    private HostInfo buildHostInfoResortingToKeyValueSyntaxParser(final String user, final String password, String hostInfo) {
        if (!hostInfo.startsWith("(") || !hostInfo.endsWith(")")) {
            return null;
        }
        hostInfo = hostInfo.substring("(".length(), hostInfo.length() - ")".length());
        return new HostInfo(this, null, -1, user, password, this.processKeyValuePattern(ConnectionUrlParser.KEY_VALUE_HOST_PTRN, hostInfo));
    }
    
    private HostInfo buildHostInfoResortingToAddressEqualsSyntaxParser(final String user, final String password, String hostInfo) {
        final int p = StringUtils.indexOfIgnoreCase(hostInfo, "ADDRESS=");
        if (p != 0) {
            return null;
        }
        hostInfo = hostInfo.substring(p + "ADDRESS=".length()).trim();
        return new HostInfo(this, null, -1, user, password, this.processKeyValuePattern(ConnectionUrlParser.ADDRESS_EQUALS_HOST_PTRN, hostInfo));
    }
    
    private HostInfo buildHostInfoResortingToGenericSyntaxParser(final String user, final String password, final String hostInfo) {
        if (this.splitByUserInfoAndHostInfo(hostInfo).left != null) {
            return null;
        }
        final Pair<String, Integer> hostPortPair = parseHostPortPair(hostInfo);
        final String host = decode(StringUtils.safeTrim(hostPortPair.left));
        final Integer port = hostPortPair.right;
        return new HostInfo(this, StringUtils.isNullOrEmpty(host) ? null : host, port, user, password);
    }
    
    private Pair<String, String> splitByUserInfoAndHostInfo(final String authSegment) {
        String userInfoPart = null;
        String hostInfoPart = authSegment;
        final int p = authSegment.indexOf("@");
        if (p >= 0) {
            userInfoPart = authSegment.substring(0, p);
            hostInfoPart = authSegment.substring(p + "@".length());
        }
        return new Pair<String, String>(userInfoPart, hostInfoPart);
    }
    
    public static Pair<String, String> parseUserInfo(final String userInfo) {
        if (StringUtils.isNullOrEmpty(userInfo)) {
            return null;
        }
        final String[] userInfoParts = userInfo.split(":", 2);
        final String userName = userInfoParts[0];
        final String password = (userInfoParts.length > 1) ? userInfoParts[1] : null;
        return new Pair<String, String>(userName, password);
    }
    
    public static Pair<String, Integer> parseHostPortPair(final String hostInfo) {
        if (StringUtils.isNullOrEmpty(hostInfo)) {
            return null;
        }
        final Matcher matcher = ConnectionUrlParser.GENERIC_HOST_PTRN.matcher(hostInfo);
        if (matcher.matches()) {
            final String host = matcher.group("host");
            final String portAsString = decode(StringUtils.safeTrim(matcher.group("port")));
            Integer portAsInteger = -1;
            if (!StringUtils.isNullOrEmpty(portAsString)) {
                try {
                    portAsInteger = Integer.parseInt(portAsString);
                }
                catch (NumberFormatException e) {
                    throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("ConnectionString.3", new Object[] { hostInfo }), e);
                }
            }
            return new Pair<String, Integer>(host, portAsInteger);
        }
        throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("ConnectionString.3", new Object[] { hostInfo }));
    }
    
    private void parseQuerySection() {
        if (StringUtils.isNullOrEmpty(this.query)) {
            this.parsedProperties = new HashMap<String, String>();
            return;
        }
        this.parsedProperties = this.processKeyValuePattern(ConnectionUrlParser.PROPERTIES_PTRN, this.query);
    }
    
    private Map<String, String> processKeyValuePattern(final Pattern pattern, final String input) {
        final Matcher matcher = pattern.matcher(input);
        int p = 0;
        final Map<String, String> kvMap = new HashMap<String, String>();
        while (matcher.find()) {
            if (matcher.start() != p) {
                throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("ConnectionString.4", new Object[] { input.substring(p) }));
            }
            final String key = decode(StringUtils.safeTrim(matcher.group("key")));
            final String value = decode(StringUtils.safeTrim(matcher.group("value")));
            if (!StringUtils.isNullOrEmpty(key)) {
                kvMap.put(key, value);
            }
            else if (!StringUtils.isNullOrEmpty(value)) {
                throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("ConnectionString.4", new Object[] { input.substring(p) }));
            }
            p = matcher.end();
        }
        if (p != input.length()) {
            throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("ConnectionString.4", new Object[] { input.substring(p) }));
        }
        return kvMap;
    }
    
    private static String decode(final String text) {
        if (StringUtils.isNullOrEmpty(text)) {
            return text;
        }
        try {
            return URLDecoder.decode(text, StandardCharsets.UTF_8.name());
        }
        catch (UnsupportedEncodingException ex) {
            return "";
        }
    }
    
    @Override
    public String getDatabaseUrl() {
        return this.baseConnectionString;
    }
    
    public String getScheme() {
        return this.scheme;
    }
    
    public String getAuthority() {
        return this.authority;
    }
    
    public String getPath() {
        return this.path;
    }
    
    public String getQuery() {
        return this.query;
    }
    
    public List<HostInfo> getHosts() {
        if (this.parsedHosts == null) {
            this.parsedHosts = new ArrayList<HostInfo>();
            this.parseAuthoritySection();
        }
        return this.parsedHosts;
    }
    
    public Map<String, String> getProperties() {
        if (this.parsedProperties == null) {
            this.parseQuerySection();
        }
        return Collections.unmodifiableMap((Map<? extends String, ? extends String>)this.parsedProperties);
    }
    
    @Override
    public String toString() {
        final StringBuilder asStr = new StringBuilder(super.toString());
        asStr.append(String.format(" :: {scheme: \"%s\", authority: \"%s\", path: \"%s\", query: \"%s\", parsedHosts: %s, parsedProperties: %s}", this.scheme, this.authority, this.path, this.query, this.parsedHosts, this.parsedProperties));
        return asStr.toString();
    }
    
    static {
        CONNECTION_STRING_PTRN = Pattern.compile("(?<scheme>[\\w:%]+)\\s*(?://(?<authority>[^/?#]*))?\\s*(?:/(?!\\s*/)(?<path>[^?#]*))?(?:\\?(?!\\s*\\?)(?<query>[^#]*))?(?:\\s*#(?<fragment>.*))?");
        SCHEME_PTRN = Pattern.compile("(?<scheme>[\\w:%]+).*");
        HOST_LIST_PTRN = Pattern.compile("^\\[(?<hosts>.*)\\]$");
        GENERIC_HOST_PTRN = Pattern.compile("^(?<host>.*?)(?::(?<port>[^:]*))?$");
        KEY_VALUE_HOST_PTRN = Pattern.compile("[,\\s]*(?<key>[\\w\\.\\-\\s%]*)(?:=(?<value>[^,]*))?");
        ADDRESS_EQUALS_HOST_PTRN = Pattern.compile("\\s*\\(\\s*(?<key>[\\w\\.\\-%]+)?\\s*(?:=(?<value>[^)]*))?\\)\\s*");
        PROPERTIES_PTRN = Pattern.compile("[&\\s]*(?<key>[\\w\\.\\-\\s%]*)(?:=(?<value>[^&]*))?");
    }
    
    public static class Pair<T, U>
    {
        public final T left;
        public final U right;
        
        public Pair(final T left, final U right) {
            this.left = left;
            this.right = right;
        }
        
        @Override
        public String toString() {
            final StringBuilder asStr = new StringBuilder(super.toString());
            asStr.append(String.format(" :: { left: %s, right: %s }", this.left, this.right));
            return asStr.toString();
        }
    }
}
