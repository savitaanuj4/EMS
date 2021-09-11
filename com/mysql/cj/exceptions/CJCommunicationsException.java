
package com.mysql.cj.exceptions;

import com.mysql.cj.protocol.PacketReceivedTimeHolder;
import com.mysql.cj.protocol.PacketSentTimeHolder;
import com.mysql.cj.protocol.ServerSession;
import com.mysql.cj.conf.PropertySet;

public class CJCommunicationsException extends CJException
{
    private static final long serialVersionUID = 344035358493554245L;
    
    public CJCommunicationsException() {
    }
    
    public CJCommunicationsException(final String message) {
        super(message);
    }
    
    public CJCommunicationsException(final String message, final Throwable cause) {
        super(message, cause);
    }
    
    public CJCommunicationsException(final Throwable cause) {
        super(cause);
    }
    
    protected CJCommunicationsException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
    public void init(final PropertySet propertySet, final ServerSession serverSession, final PacketSentTimeHolder packetSentTimeHolder, final PacketReceivedTimeHolder packetReceivedTimeHolder) {
        this.exceptionMessage = ExceptionFactory.createLinkFailureMessageBasedOnHeuristics(propertySet, serverSession, packetSentTimeHolder, packetReceivedTimeHolder, this.getCause());
    }
}
