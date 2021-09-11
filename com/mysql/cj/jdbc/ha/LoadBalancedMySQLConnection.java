
package com.mysql.cj.jdbc.ha;

import com.mysql.cj.jdbc.exceptions.SQLError;
import com.mysql.cj.Messages;
import java.sql.SQLException;
import com.mysql.cj.exceptions.CJException;
import com.mysql.cj.jdbc.exceptions.SQLExceptionsMapping;

public class LoadBalancedMySQLConnection extends MultiHostMySQLConnection implements LoadBalancedConnection
{
    public LoadBalancedMySQLConnection(final LoadBalancedConnectionProxy proxy) {
        super(proxy);
    }
    
    @Override
    public LoadBalancedConnectionProxy getThisAsProxy() {
        return (LoadBalancedConnectionProxy)super.getThisAsProxy();
    }
    
    @Override
    public void close() throws SQLException {
        try {
            this.getThisAsProxy().doClose();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void ping() throws SQLException {
        try {
            this.ping(true);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void ping(final boolean allConnections) throws SQLException {
        try {
            if (allConnections) {
                this.getThisAsProxy().doPing();
            }
            else {
                this.getActiveMySQLConnection().ping();
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean addHost(final String host) throws SQLException {
        try {
            return this.getThisAsProxy().addHost(host);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void removeHost(final String host) throws SQLException {
        try {
            this.getThisAsProxy().removeHost(host);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public void removeHostWhenNotInUse(final String host) throws SQLException {
        try {
            this.getThisAsProxy().removeHostWhenNotInUse(host);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public boolean isWrapperFor(final Class<?> iface) throws SQLException {
        try {
            return iface.isInstance(this);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public <T> T unwrap(final Class<T> iface) throws SQLException {
        try {
            try {
                return iface.cast(this);
            }
            catch (ClassCastException cce) {
                throw SQLError.createSQLException(Messages.getString("Common.UnableToUnwrap", new Object[] { iface.toString() }), "S1009", this.getExceptionInterceptor());
            }
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.getExceptionInterceptor());
        }
    }
}
