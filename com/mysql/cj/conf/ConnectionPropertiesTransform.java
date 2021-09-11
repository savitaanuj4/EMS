
package com.mysql.cj.conf;

import java.util.Properties;

public interface ConnectionPropertiesTransform
{
    Properties transformProperties(final Properties p0);
}
