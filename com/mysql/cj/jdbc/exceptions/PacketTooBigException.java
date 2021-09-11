
package com.mysql.cj.jdbc.exceptions;

import com.mysql.cj.Messages;
import java.sql.SQLException;

public class PacketTooBigException extends SQLException
{
    static final long serialVersionUID = 7248633977685452174L;
    
    public PacketTooBigException(final long packetSize, final long maximumPacketSize) {
        super(Messages.getString("PacketTooBigException.0", new Object[] { packetSize, maximumPacketSize }), "S1000");
    }
    
    public PacketTooBigException(final String message) {
        super(message, "S1000");
    }
}
