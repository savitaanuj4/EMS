
package com.mysql.cj.protocol;

import com.mysql.cj.ServerVersion;

public interface ServerCapabilities
{
    int getCapabilityFlags();
    
    void setCapabilityFlags(final int p0);
    
    ServerVersion getServerVersion();
    
    void setServerVersion(final ServerVersion p0);
    
    boolean serverSupportsFracSecs();
}
