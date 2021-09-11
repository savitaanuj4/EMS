
package com.mysql.cj.protocol.a.authentication;

import com.mysql.cj.protocol.Message;
import com.mysql.cj.protocol.a.NativeConstants;
import com.mysql.cj.util.StringUtils;
import java.util.List;
import com.mysql.cj.protocol.Protocol;
import com.mysql.cj.protocol.a.NativePacketPayload;
import com.mysql.cj.protocol.AuthenticationPlugin;

public class MysqlClearPasswordPlugin implements AuthenticationPlugin<NativePacketPayload>
{
    private Protocol<NativePacketPayload> protocol;
    private String password;
    
    public MysqlClearPasswordPlugin() {
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
        return "mysql_clear_password";
    }
    
    @Override
    public boolean requiresConfidentiality() {
        return true;
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
        final String encoding = this.protocol.versionMeetsMinimum(5, 7, 6) ? this.protocol.getPasswordCharacterEncoding() : "UTF-8";
        final NativePacketPayload bresp = new NativePacketPayload(StringUtils.getBytes((this.password != null) ? this.password : "", encoding));
        bresp.setPosition(bresp.getPayloadLength());
        bresp.writeInteger(NativeConstants.IntegerDataType.INT1, 0L);
        bresp.setPosition(0);
        toServer.add(bresp);
        return true;
    }
}
