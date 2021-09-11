
package com.mysql.cj.protocol.x;

import com.mysql.cj.ServerVersion;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.List;
import com.mysql.cj.xdevapi.ExprUtil;
import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import java.util.Map;
import com.mysql.cj.protocol.ServerCapabilities;

public class XServerCapabilities implements ServerCapabilities
{
    private Map<String, MysqlxDatatypes.Any> capabilities;
    
    public XServerCapabilities(final Map<String, MysqlxDatatypes.Any> capabilities) {
        this.capabilities = capabilities;
    }
    
    public void setCapability(final String name, final Object value) {
        this.capabilities.put(name, ExprUtil.argObjectToScalarAny(value));
    }
    
    public boolean hasCapability(final String name) {
        return this.capabilities.containsKey(name);
    }
    
    public String getNodeType() {
        return this.capabilities.get("node_type").getScalar().getVString().getValue().toStringUtf8();
    }
    
    public boolean getTls() {
        return this.hasCapability("tls") && this.capabilities.get("tls").getScalar().getVBool();
    }
    
    public boolean getClientPwdExpireOk() {
        return this.capabilities.get("client.pwd_expire_ok").getScalar().getVBool();
    }
    
    public List<String> getAuthenticationMechanisms() {
        return this.capabilities.get("authentication.mechanisms").getArray().getValueList().stream().map(v -> v.getScalar().getVString().getValue().toStringUtf8()).collect((Collector<? super Object, ?, List<String>>)Collectors.toList());
    }
    
    public String getDocFormats() {
        return this.capabilities.get("doc.formats").getScalar().getVString().getValue().toStringUtf8();
    }
    
    @Override
    public int getCapabilityFlags() {
        return 0;
    }
    
    @Override
    public void setCapabilityFlags(final int capabilityFlags) {
    }
    
    @Override
    public ServerVersion getServerVersion() {
        return null;
    }
    
    @Override
    public void setServerVersion(final ServerVersion serverVersion) {
    }
    
    @Override
    public boolean serverSupportsFracSecs() {
        return true;
    }
}
