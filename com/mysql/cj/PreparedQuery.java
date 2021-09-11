
package com.mysql.cj;

import com.mysql.cj.protocol.Message;

public interface PreparedQuery<T extends QueryBindings<?>> extends Query
{
    ParseInfo getParseInfo();
    
    void setParseInfo(final ParseInfo p0);
    
    void checkNullOrEmptyQuery(final String p0);
    
    String getOriginalSql();
    
    void setOriginalSql(final String p0);
    
    int getParameterCount();
    
    void setParameterCount(final int p0);
    
    T getQueryBindings();
    
    void setQueryBindings(final T p0);
    
    int computeBatchSize(final int p0);
    
    int getBatchCommandIndex();
    
    void setBatchCommandIndex(final int p0);
    
    String asSql();
    
    String asSql(final boolean p0);
    
     <M extends Message> M fillSendPacket();
    
     <M extends Message> M fillSendPacket(final QueryBindings<?> p0);
}
