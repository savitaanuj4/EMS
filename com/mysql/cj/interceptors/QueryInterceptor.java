
package com.mysql.cj.interceptors;

import com.mysql.cj.protocol.ServerSession;
import com.mysql.cj.protocol.Message;
import com.mysql.cj.protocol.Resultset;
import com.mysql.cj.Query;
import java.util.function.Supplier;
import com.mysql.cj.log.Log;
import java.util.Properties;
import com.mysql.cj.MysqlConnection;

public interface QueryInterceptor
{
    QueryInterceptor init(final MysqlConnection p0, final Properties p1, final Log p2);
    
     <T extends Resultset> T preProcess(final Supplier<String> p0, final Query p1);
    
    default <M extends Message> M preProcess(final M queryPacket) {
        return null;
    }
    
    boolean executeTopLevelOnly();
    
    void destroy();
    
     <T extends Resultset> T postProcess(final Supplier<String> p0, final Query p1, final T p2, final ServerSession p3);
    
    default <M extends Message> M postProcess(final M queryPacket, final M originalResponsePacket) {
        return null;
    }
}
