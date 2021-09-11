
package com.mysql.cj;

import java.util.List;
import com.mysql.cj.protocol.Message;

public interface MessageBuilder<M extends Message>
{
    M buildSqlStatement(final String p0);
    
    M buildSqlStatement(final String p0, final List<Object> p1);
    
    M buildClose();
}
