
package com.mysql.cj.xdevapi;

import java.util.Properties;

public class ClientFactory
{
    public Client getClient(final String url, final String clientPropsJson) {
        return new ClientImpl(url, clientPropsJson);
    }
    
    public Client getClient(final String url, final Properties clientProps) {
        return new ClientImpl(url, clientProps);
    }
}
