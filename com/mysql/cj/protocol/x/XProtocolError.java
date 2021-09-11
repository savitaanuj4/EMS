
package com.mysql.cj.protocol.x;

import com.mysql.cj.x.protobuf.Mysqlx;
import com.mysql.cj.exceptions.CJException;

public class XProtocolError extends CJException
{
    private static final long serialVersionUID = 6991120628391138584L;
    private Mysqlx.Error msg;
    
    public XProtocolError(final String message) {
        super(message);
    }
    
    public XProtocolError(final Mysqlx.Error msg) {
        super(getFullErrorDescription(msg));
        this.msg = msg;
    }
    
    public XProtocolError(final XProtocolError fromOtherThread) {
        super(getFullErrorDescription(fromOtherThread.msg), fromOtherThread);
        this.msg = fromOtherThread.msg;
    }
    
    public XProtocolError(final String message, final Throwable t) {
        super(message, t);
    }
    
    private static String getFullErrorDescription(final Mysqlx.Error msg) {
        final StringBuilder stringMessage = new StringBuilder("ERROR ");
        stringMessage.append(msg.getCode());
        stringMessage.append(" (");
        stringMessage.append(msg.getSqlState());
        stringMessage.append(") ");
        stringMessage.append(msg.getMsg());
        return stringMessage.toString();
    }
    
    public int getErrorCode() {
        return (this.msg == null) ? super.getVendorCode() : this.msg.getCode();
    }
    
    @Override
    public String getSQLState() {
        return (this.msg == null) ? super.getSQLState() : this.msg.getSqlState();
    }
}
