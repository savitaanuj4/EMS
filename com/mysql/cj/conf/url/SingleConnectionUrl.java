
package com.mysql.cj.conf.url;

import java.util.Properties;
import com.mysql.cj.conf.ConnectionUrlParser;
import com.mysql.cj.conf.ConnectionUrl;

public class SingleConnectionUrl extends ConnectionUrl
{
    public SingleConnectionUrl(final ConnectionUrlParser connStrParser, final Properties info) {
        super(connStrParser, info);
        this.type = Type.SINGLE_CONNECTION;
    }
}
