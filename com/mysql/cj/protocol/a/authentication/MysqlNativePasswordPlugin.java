
package com.mysql.cj.protocol.a.authentication;

import com.mysql.cj.protocol.Message;
import com.mysql.cj.protocol.Security;
import com.mysql.cj.protocol.a.NativeConstants;
import java.util.List;
import com.mysql.cj.protocol.Protocol;
import com.mysql.cj.protocol.a.NativePacketPayload;
import com.mysql.cj.protocol.AuthenticationPlugin;

public class MysqlNativePasswordPlugin implements AuthenticationPlugin<NativePacketPayload>
{
    private Protocol<NativePacketPayload> protocol;
    private String password;
    
    public MysqlNativePasswordPlugin() {
        this.password = null;
    }
    
    @Override
    public void init(final Protocol<NativePacketPayload> prot) {
        this.protocol = prot;
    }
    
    @Override
    public void destroy() {
        this.password = null;
    }
    
    @Override
    public String getProtocolPluginName() {
        return "mysql_native_password";
    }
    
    @Override
    public boolean requiresConfidentiality() {
        return false;
    }
    
    @Override
    public boolean isReusable() {
        return true;
    }
    
    @Override
    public void setAuthenticationParameters(final String user, final String password) {
        this.password = password;
    }
    
    @Override
    public boolean nextAuthenticationStep(final NativePacketPayload fromServer, final List<NativePacketPayload> toServer) {
        toServer.clear();
        NativePacketPayload bresp = null;
        final String pwd = this.password;
        if (fromServer == null || pwd == null || pwd.length() == 0) {
            bresp = new NativePacketPayload(new byte[0]);
        }
        else {
            bresp = new NativePacketPayload(Security.scramble411(pwd, fromServer.readBytes(NativeConstants.StringSelfDataType.STRING_TERM), this.protocol.getPasswordCharacterEncoding()));
        }
        toServer.add(bresp);
        return true;
    }
}
