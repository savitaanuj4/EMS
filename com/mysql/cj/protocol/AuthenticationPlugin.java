
package com.mysql.cj.protocol;

import java.util.List;

public interface AuthenticationPlugin<M extends Message>
{
    default void init(final Protocol<M> protocol) {
    }
    
    default void reset() {
    }
    
    default void destroy() {
    }
    
    String getProtocolPluginName();
    
    boolean requiresConfidentiality();
    
    boolean isReusable();
    
    void setAuthenticationParameters(final String p0, final String p1);
    
    boolean nextAuthenticationStep(final M p0, final List<M> p1);
}
