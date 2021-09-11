
package com.mysql.cj.protocol.x;

import java.util.Iterator;
import com.mysql.cj.conf.RuntimeProperty;
import com.mysql.cj.xdevapi.XDevAPIError;
import java.util.List;
import com.mysql.cj.util.StringUtils;
import com.mysql.cj.exceptions.CJCommunicationsException;
import java.nio.channels.ClosedChannelException;
import com.mysql.cj.exceptions.WrongArgumentException;
import com.mysql.cj.protocol.Message;
import java.util.Arrays;
import com.mysql.cj.conf.PropertyKey;
import com.mysql.cj.protocol.ServerSession;
import com.mysql.cj.exceptions.ExceptionInterceptor;
import com.mysql.cj.conf.PropertySet;
import com.mysql.cj.protocol.Protocol;
import com.mysql.cj.conf.PropertyDefinitions;
import com.mysql.cj.protocol.AuthenticationProvider;

public class XAuthenticationProvider implements AuthenticationProvider<XMessage>
{
    XProtocol protocol;
    private PropertyDefinitions.AuthMech authMech;
    private XMessageBuilder messageBuilder;
    
    public XAuthenticationProvider() {
        this.authMech = null;
        this.messageBuilder = new XMessageBuilder();
    }
    
    @Override
    public void init(final Protocol<XMessage> prot, final PropertySet propertySet, final ExceptionInterceptor exceptionInterceptor) {
        this.protocol = (XProtocol)prot;
    }
    
    @Override
    public void connect(final ServerSession serverSession, final String userName, final String password, final String database) {
        this.changeUser(serverSession, userName, password, database);
    }
    
    @Override
    public void changeUser(final ServerSession serverSession, final String userName, final String password, final String database) {
        final boolean overTLS = ((XServerCapabilities)this.protocol.getServerSession().getCapabilities()).getTls();
        final RuntimeProperty<PropertyDefinitions.AuthMech> authMechProp = this.protocol.getPropertySet().getEnumProperty(PropertyKey.xdevapiAuth);
        List<PropertyDefinitions.AuthMech> tryAuthMech;
        if (overTLS || authMechProp.isExplicitlySet()) {
            tryAuthMech = Arrays.asList(authMechProp.getValue());
        }
        else {
            tryAuthMech = Arrays.asList(PropertyDefinitions.AuthMech.MYSQL41, PropertyDefinitions.AuthMech.SHA256_MEMORY);
        }
        XProtocolError capturedAuthErr = null;
        for (final PropertyDefinitions.AuthMech am : tryAuthMech) {
            this.authMech = am;
            try {
                switch (this.authMech) {
                    case SHA256_MEMORY: {
                        this.protocol.send(this.messageBuilder.buildSha256MemoryAuthStart(), 0);
                        final byte[] nonce = this.protocol.readAuthenticateContinue();
                        this.protocol.send(this.messageBuilder.buildSha256MemoryAuthContinue(userName, password, nonce, database), 0);
                        break;
                    }
                    case MYSQL41: {
                        this.protocol.send(this.messageBuilder.buildMysql41AuthStart(), 0);
                        final byte[] salt = this.protocol.readAuthenticateContinue();
                        this.protocol.send(this.messageBuilder.buildMysql41AuthContinue(userName, password, salt, database), 0);
                        break;
                    }
                    case PLAIN: {
                        if (overTLS) {
                            this.protocol.send(this.messageBuilder.buildPlainAuthStart(userName, password, database), 0);
                            break;
                        }
                        throw new XProtocolError("PLAIN authentication is not allowed via unencrypted connection.");
                    }
                    case EXTERNAL: {
                        this.protocol.send(this.messageBuilder.buildExternalAuthStart(database), 0);
                        break;
                    }
                    default: {
                        throw new WrongArgumentException("Unknown authentication mechanism '" + this.authMech + "'.");
                    }
                }
            }
            catch (CJCommunicationsException e) {
                if (capturedAuthErr != null && e.getCause() instanceof ClosedChannelException) {
                    throw capturedAuthErr;
                }
                throw e;
            }
            try {
                this.protocol.readAuthenticateOk();
                capturedAuthErr = null;
            }
            catch (XProtocolError e2) {
                if (e2.getErrorCode() != 1045) {
                    throw e2;
                }
                capturedAuthErr = e2;
                continue;
            }
            break;
        }
        if (capturedAuthErr == null) {
            this.protocol.afterHandshake();
            return;
        }
        if (tryAuthMech.size() == 1) {
            throw capturedAuthErr;
        }
        final String errMsg = "Authentication failed using " + StringUtils.joinWithSerialComma(tryAuthMech) + ", check username and password or try a secure connection";
        final XDevAPIError ex = new XDevAPIError(errMsg, capturedAuthErr);
        ex.setVendorCode(capturedAuthErr.getErrorCode());
        ex.setSQLState(capturedAuthErr.getSQLState());
        ex.initCause(capturedAuthErr);
        throw ex;
    }
    
    @Override
    public String getEncodingForHandshake() {
        return null;
    }
}
