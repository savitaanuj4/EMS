
package com.mysql.cj.conf;

import java.util.Properties;
import com.mysql.cj.util.StringUtils;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class HostInfo implements DatabaseUrlContainer
{
    private static final String HOST_PORT_SEPARATOR = ":";
    private final DatabaseUrlContainer originalUrl;
    private final String host;
    private final int port;
    private final String user;
    private final String password;
    private final boolean isPasswordless;
    private final Map<String, String> hostProperties;
    
    public HostInfo() {
        this(null, null, -1, null, null, true, null);
    }
    
    public HostInfo(final DatabaseUrlContainer url, final String host, final int port, final String user, final String password) {
        this(url, host, port, user, password, password == null, null);
    }
    
    public HostInfo(final DatabaseUrlContainer url, final String host, final int port, final String user, final String password, final Map<String, String> properties) {
        this(url, host, port, user, password, password == null, properties);
    }
    
    public HostInfo(final DatabaseUrlContainer url, final String host, final int port, final String user, final String password, final boolean isPasswordless, final Map<String, String> properties) {
        this.hostProperties = new HashMap<String, String>();
        this.originalUrl = url;
        this.host = host;
        this.port = port;
        this.user = user;
        this.password = password;
        this.isPasswordless = isPasswordless;
        if (properties != null) {
            this.hostProperties.putAll(properties);
        }
    }
    
    public String getHost() {
        return this.host;
    }
    
    public int getPort() {
        return this.port;
    }
    
    public String getHostPortPair() {
        return this.host + ":" + this.port;
    }
    
    public String getUser() {
        return this.user;
    }
    
    public String getPassword() {
        return this.password;
    }
    
    public boolean isPasswordless() {
        return this.isPasswordless;
    }
    
    public Map<String, String> getHostProperties() {
        return Collections.unmodifiableMap((Map<? extends String, ? extends String>)this.hostProperties);
    }
    
    public String getProperty(final String key) {
        return this.hostProperties.get(key);
    }
    
    public String getDatabase() {
        final String database = this.hostProperties.get(PropertyKey.DBNAME.getKeyName());
        return StringUtils.isNullOrEmpty(database) ? "" : database;
    }
    
    public Properties exposeAsProperties() {
        final Properties props = new Properties();
        this.hostProperties.entrySet().stream().forEach(e -> props.setProperty(e.getKey(), (e.getValue() == null) ? "" : ((String)e.getValue())));
        props.setProperty(PropertyKey.HOST.getKeyName(), this.getHost());
        props.setProperty(PropertyKey.PORT.getKeyName(), String.valueOf(this.getPort()));
        props.setProperty(PropertyKey.USER.getKeyName(), this.getUser());
        props.setProperty(PropertyKey.PASSWORD.getKeyName(), this.getPassword());
        return props;
    }
    
    @Override
    public String getDatabaseUrl() {
        return (this.originalUrl != null) ? this.originalUrl.getDatabaseUrl() : "";
    }
    
    @Override
    public String toString() {
        final StringBuilder asStr = new StringBuilder(super.toString());
        asStr.append(String.format(" :: {host: \"%s\", port: %d, user: %s, password: %s, hostProperties: %s}", this.host, this.port, this.user, this.password, this.hostProperties));
        return asStr.toString();
    }
}
