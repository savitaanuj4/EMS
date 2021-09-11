
package com.mysql.cj.protocol;

import com.mysql.cj.exceptions.ExceptionFactory;
import com.mysql.cj.Messages;
import com.mysql.cj.CharsetMapping;
import com.mysql.cj.ServerVersion;
import com.mysql.cj.exceptions.ExceptionInterceptor;
import com.mysql.cj.conf.PropertySet;

public interface AuthenticationProvider<M extends Message>
{
    void init(final Protocol<M> p0, final PropertySet p1, final ExceptionInterceptor p2);
    
    void connect(final ServerSession p0, final String p1, final String p2, final String p3);
    
    void changeUser(final ServerSession p0, final String p1, final String p2, final String p3);
    
    String getEncodingForHandshake();
    
    default byte getCharsetForHandshake(final String enc, final ServerVersion sv) {
        int charsetIndex = 0;
        if (enc != null) {
            charsetIndex = CharsetMapping.getCollationIndexForJavaEncoding(enc, sv);
        }
        if (charsetIndex == 0) {
            charsetIndex = 33;
        }
        if (charsetIndex > 255) {
            throw ExceptionFactory.createException(Messages.getString("MysqlIO.113", new Object[] { enc }));
        }
        return (byte)charsetIndex;
    }
}
