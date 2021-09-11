
package com.mysql.cj.jdbc.exceptions;

import com.mysql.cj.Messages;
import com.mysql.cj.protocol.PacketReceivedTimeHolder;
import com.mysql.cj.protocol.PacketSentTimeHolder;
import com.mysql.cj.jdbc.JdbcConnection;

public class ConnectionFeatureNotAvailableException extends CommunicationsException
{
    private static final long serialVersionUID = 8315412078945570018L;
    
    public ConnectionFeatureNotAvailableException(final JdbcConnection conn, final PacketSentTimeHolder packetSentTimeHolder, final Exception underlyingException) {
        super(conn, packetSentTimeHolder, null, underlyingException);
    }
    
    public ConnectionFeatureNotAvailableException(final String message, final Throwable underlyingException) {
        super(message, underlyingException);
    }
    
    @Override
    public String getMessage() {
        return Messages.getString("ConnectionFeatureNotAvailableException.0");
    }
    
    @Override
    public String getSQLState() {
        return "01S00";
    }
}
