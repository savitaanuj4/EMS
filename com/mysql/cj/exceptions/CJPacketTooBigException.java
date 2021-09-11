
package com.mysql.cj.exceptions;

import com.mysql.cj.Messages;

public class CJPacketTooBigException extends CJException
{
    private static final long serialVersionUID = 7186090399276725363L;
    
    public CJPacketTooBigException() {
    }
    
    public CJPacketTooBigException(final String message) {
        super(message);
    }
    
    public CJPacketTooBigException(final Throwable cause) {
        super(cause);
    }
    
    public CJPacketTooBigException(final String message, final Throwable cause) {
        super(message, cause);
    }
    
    public CJPacketTooBigException(final long packetSize, final long maximumPacketSize) {
        super(Messages.getString("PacketTooBigException.0", new Object[] { packetSize, maximumPacketSize }));
    }
}
