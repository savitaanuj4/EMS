
package com.mysql.cj.jdbc;

import java.util.Iterator;
import java.util.Map;
import java.sql.SQLClientInfoException;
import java.util.Enumeration;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.Properties;

public class CommentClientInfoProvider implements ClientInfoProvider
{
    private Properties clientInfo;
    
    @Override
    public synchronized void initialize(final Connection conn, final Properties configurationProps) throws SQLException {
        this.clientInfo = new Properties();
    }
    
    @Override
    public synchronized void destroy() throws SQLException {
        this.clientInfo = null;
    }
    
    @Override
    public synchronized Properties getClientInfo(final Connection conn) throws SQLException {
        return this.clientInfo;
    }
    
    @Override
    public synchronized String getClientInfo(final Connection conn, final String name) throws SQLException {
        return this.clientInfo.getProperty(name);
    }
    
    @Override
    public synchronized void setClientInfo(final Connection conn, final Properties properties) throws SQLClientInfoException {
        this.clientInfo = new Properties();
        final Enumeration<?> propNames = properties.propertyNames();
        while (propNames.hasMoreElements()) {
            final String name = (String)propNames.nextElement();
            this.clientInfo.put(name, properties.getProperty(name));
        }
        this.setComment(conn);
    }
    
    @Override
    public synchronized void setClientInfo(final Connection conn, final String name, final String value) throws SQLClientInfoException {
        this.clientInfo.setProperty(name, value);
        this.setComment(conn);
    }
    
    private synchronized void setComment(final Connection conn) {
        final StringBuilder commentBuf = new StringBuilder();
        final Iterator<Map.Entry<Object, Object>> elements = this.clientInfo.entrySet().iterator();
        while (elements.hasNext()) {
            if (commentBuf.length() > 0) {
                commentBuf.append(", ");
            }
            final Map.Entry<?, ?> entry = elements.next();
            commentBuf.append("" + entry.getKey());
            commentBuf.append("=");
            commentBuf.append("" + entry.getValue());
        }
        ((JdbcConnection)conn).setStatementComment(commentBuf.toString());
    }
}
