
package com.mysql.cj.log;

public interface ProfilerEventHandler
{
    void init(final Log p0);
    
    void destroy();
    
    void consumeEvent(final ProfilerEvent p0);
}
