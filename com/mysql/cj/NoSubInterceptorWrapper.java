
package com.mysql.cj;

import com.mysql.cj.protocol.Message;
import com.mysql.cj.protocol.Resultset;
import com.mysql.cj.protocol.ServerSession;
import java.util.function.Supplier;
import com.mysql.cj.log.Log;
import java.util.Properties;
import com.mysql.cj.interceptors.QueryInterceptor;

public class NoSubInterceptorWrapper implements QueryInterceptor
{
    private final QueryInterceptor underlyingInterceptor;
    
    public NoSubInterceptorWrapper(final QueryInterceptor underlyingInterceptor) {
        if (underlyingInterceptor == null) {
            throw new RuntimeException(Messages.getString("NoSubInterceptorWrapper.0"));
        }
        this.underlyingInterceptor = underlyingInterceptor;
    }
    
    @Override
    public void destroy() {
        this.underlyingInterceptor.destroy();
    }
    
    @Override
    public boolean executeTopLevelOnly() {
        return this.underlyingInterceptor.executeTopLevelOnly();
    }
    
    @Override
    public QueryInterceptor init(final MysqlConnection conn, final Properties props, final Log log) {
        this.underlyingInterceptor.init(conn, props, log);
        return this;
    }
    
    @Override
    public <T extends Resultset> T postProcess(final Supplier<String> sql, final Query interceptedQuery, final T originalResultSet, final ServerSession serverSession) {
        this.underlyingInterceptor.postProcess(sql, interceptedQuery, originalResultSet, serverSession);
        return null;
    }
    
    @Override
    public <T extends Resultset> T preProcess(final Supplier<String> sql, final Query interceptedQuery) {
        this.underlyingInterceptor.preProcess(sql, interceptedQuery);
        return null;
    }
    
    @Override
    public <M extends Message> M preProcess(final M queryPacket) {
        this.underlyingInterceptor.preProcess(queryPacket);
        return null;
    }
    
    @Override
    public <M extends Message> M postProcess(final M queryPacket, final M originalResponsePacket) {
        this.underlyingInterceptor.postProcess(queryPacket, originalResponsePacket);
        return null;
    }
    
    public QueryInterceptor getUnderlyingInterceptor() {
        return this.underlyingInterceptor;
    }
}
