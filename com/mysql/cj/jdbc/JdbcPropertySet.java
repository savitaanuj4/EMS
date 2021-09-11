
package com.mysql.cj.jdbc;

import java.sql.SQLException;
import java.sql.DriverPropertyInfo;
import java.util.Properties;
import com.mysql.cj.conf.PropertySet;

public interface JdbcPropertySet extends PropertySet
{
    DriverPropertyInfo[] exposeAsDriverPropertyInfo(final Properties p0, final int p1) throws SQLException;
}
