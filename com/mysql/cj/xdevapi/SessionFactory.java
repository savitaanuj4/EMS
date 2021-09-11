
package com.mysql.cj.xdevapi;

import java.util.Iterator;
import com.mysql.cj.exceptions.CJCommunicationsException;
import com.mysql.cj.conf.HostInfo;
import com.mysql.cj.exceptions.ExceptionFactory;
import com.mysql.cj.exceptions.InvalidConnectionAttributeException;
import java.util.Properties;
import com.mysql.cj.conf.ConnectionUrl;

public class SessionFactory
{
    protected ConnectionUrl parseUrl(final String url) {
        final ConnectionUrl connUrl = ConnectionUrl.getConnectionUrlInstance(url, null);
        if (connUrl == null || connUrl.getType() != ConnectionUrl.Type.XDEVAPI_SESSION) {
            throw ExceptionFactory.createException(InvalidConnectionAttributeException.class, "Initialization via URL failed for \"" + url + "\"");
        }
        return connUrl;
    }
    
    protected Session getSession(final ConnectionUrl connUrl) {
        CJCommunicationsException latestException = null;
        for (final HostInfo hi : connUrl.getHostsList()) {
            try {
                return new SessionImpl(hi);
            }
            catch (CJCommunicationsException e) {
                latestException = e;
                continue;
            }
            break;
        }
        if (latestException != null) {
            throw latestException;
        }
        return null;
    }
    
    public Session getSession(final String url) {
        return this.getSession(this.parseUrl(url));
    }
    
    public Session getSession(final Properties properties) {
        final ConnectionUrl connUrl = ConnectionUrl.getConnectionUrlInstance(ConnectionUrl.Type.XDEVAPI_SESSION.getScheme(), properties);
        return new SessionImpl(connUrl.getMainHost());
    }
}
