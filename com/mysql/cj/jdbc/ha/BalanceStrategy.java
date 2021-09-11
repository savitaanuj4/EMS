
package com.mysql.cj.jdbc.ha;

import java.sql.SQLException;
import com.mysql.cj.jdbc.JdbcConnection;
import java.util.Map;
import java.util.List;
import java.lang.reflect.InvocationHandler;

public interface BalanceStrategy
{
    JdbcConnection pickConnection(final InvocationHandler p0, final List<String> p1, final Map<String, JdbcConnection> p2, final long[] p3, final int p4) throws SQLException;
}
