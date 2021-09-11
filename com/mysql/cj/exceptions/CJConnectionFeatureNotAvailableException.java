
package com.mysql.cj.exceptions;

import com.mysql.cj.Messages;
import com.mysql.cj.protocol.PacketReceivedTimeHolder;
import com.mysql.cj.protocol.PacketSentTimeHolder;
import com.mysql.cj.protocol.ServerSession;
import com.mysql.cj.conf.PropertySet;

public class CJConnectionFeatureNotAvailableException extends CJCommunicationsException
{
    private static final long serialVersionUID = -4129847384681995107L;
    
    public CJConnectionFeatureNotAvailableException() {
    }
    
    public CJConnectionFeatureNotAvailableException(final PropertySet propertySet, final ServerSession serverSession, final PacketSentTimeHolder packetSentTimeHolder, final Exception underlyingException) {
        super(underlyingException);
        this.init(propertySet, serverSession, packetSentTimeHolder, null);
    }
    
    @Override
    public String getMessage() {
        return Messages.getString("ConnectionFeatureNotAvailableException.0");
    }
}
