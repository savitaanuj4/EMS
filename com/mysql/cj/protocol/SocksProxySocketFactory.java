
package com.mysql.cj.protocol;

import java.net.SocketAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import com.mysql.cj.conf.PropertyKey;
import java.net.Socket;
import com.mysql.cj.conf.PropertySet;

public class SocksProxySocketFactory extends StandardSocketFactory
{
    @Override
    protected Socket createSocket(final PropertySet props) {
        final String socksProxyHost = props.getStringProperty(PropertyKey.socksProxyHost).getValue();
        final int socksProxyPort = props.getIntegerProperty(PropertyKey.socksProxyPort).getValue();
        return new Socket(new Proxy(Proxy.Type.SOCKS, new InetSocketAddress(socksProxyHost, socksProxyPort)));
    }
}
