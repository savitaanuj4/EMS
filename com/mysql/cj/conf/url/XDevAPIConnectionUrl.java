
package com.mysql.cj.conf.url;

import com.mysql.cj.util.StringUtils;
import java.util.Map;
import java.util.Iterator;
import java.util.Comparator;
import com.mysql.cj.exceptions.ExceptionFactory;
import com.mysql.cj.Messages;
import com.mysql.cj.exceptions.WrongArgumentException;
import com.mysql.cj.conf.PropertyKey;
import com.mysql.cj.conf.HostInfo;
import java.util.Properties;
import com.mysql.cj.conf.ConnectionUrlParser;
import com.mysql.cj.conf.ConnectionUrl;

public class XDevAPIConnectionUrl extends ConnectionUrl
{
    private static final int DEFAULT_PORT = 33060;
    
    public XDevAPIConnectionUrl(final ConnectionUrlParser connStrParser, final Properties info) {
        super(connStrParser, info);
        this.type = Type.XDEVAPI_SESSION;
        boolean first = true;
        String user = null;
        String password = null;
        boolean hasPriority = false;
        for (final HostInfo hi2 : this.hosts) {
            if (first) {
                first = false;
                user = hi2.getUser();
                password = hi2.getPassword();
                hasPriority = hi2.getHostProperties().containsKey(PropertyKey.PRIORITY.getKeyName());
            }
            else {
                if (!user.equals(hi2.getUser()) || !password.equals(hi2.getPassword())) {
                    throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("ConnectionString.14", new Object[] { Type.XDEVAPI_SESSION.getScheme() }));
                }
                if (hasPriority ^ hi2.getHostProperties().containsKey(PropertyKey.PRIORITY.getKeyName())) {
                    throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("ConnectionString.15", new Object[] { Type.XDEVAPI_SESSION.getScheme() }));
                }
            }
            if (hasPriority) {
                try {
                    final int priority = Integer.parseInt(hi2.getProperty(PropertyKey.PRIORITY.getKeyName()));
                    if (priority < 0 || priority > 100) {
                        throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("ConnectionString.16", new Object[] { Type.XDEVAPI_SESSION.getScheme() }));
                    }
                    continue;
                }
                catch (NumberFormatException e) {
                    throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("ConnectionString.16", new Object[] { Type.XDEVAPI_SESSION.getScheme() }));
                }
            }
        }
        if (hasPriority) {
            this.hosts.sort(Comparator.comparing(hi -> Integer.parseInt(hi.getHostProperties().get(PropertyKey.PRIORITY.getKeyName()))).reversed());
        }
    }
    
    @Override
    protected void preprocessPerTypeHostProperties(final Map<String, String> hostProps) {
        if (hostProps.containsKey(PropertyKey.ADDRESS.getKeyName())) {
            final String address = hostProps.get(PropertyKey.ADDRESS.getKeyName());
            final ConnectionUrlParser.Pair<String, Integer> hostPortPair = ConnectionUrlParser.parseHostPortPair(address);
            final String host = StringUtils.safeTrim(hostPortPair.left);
            final Integer port = hostPortPair.right;
            if (!StringUtils.isNullOrEmpty(host) && !hostProps.containsKey(PropertyKey.HOST.getKeyName())) {
                hostProps.put(PropertyKey.HOST.getKeyName(), host);
            }
            if (port != -1 && !hostProps.containsKey(PropertyKey.PORT.getKeyName())) {
                hostProps.put(PropertyKey.PORT.getKeyName(), port.toString());
            }
        }
    }
    
    @Override
    public int getDefaultPort() {
        return 33060;
    }
    
    @Override
    protected void fixProtocolDependencies(final Map<String, String> hostProps) {
    }
}
