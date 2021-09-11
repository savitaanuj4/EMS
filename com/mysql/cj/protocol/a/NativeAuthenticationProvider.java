
package com.mysql.cj.protocol.a;

import com.mysql.cj.Constants;
import com.mysql.cj.protocol.Message;
import com.mysql.cj.protocol.a.result.OkPacket;
import java.util.Iterator;
import com.mysql.cj.protocol.a.authentication.MysqlOldPasswordPlugin;
import com.mysql.cj.protocol.a.authentication.CachingSha2PasswordPlugin;
import com.mysql.cj.protocol.a.authentication.Sha256PasswordPlugin;
import com.mysql.cj.protocol.a.authentication.MysqlClearPasswordPlugin;
import com.mysql.cj.protocol.a.authentication.MysqlNativePasswordPlugin;
import java.util.LinkedList;
import java.util.HashMap;
import com.mysql.cj.util.StringUtils;
import java.util.ArrayList;
import com.mysql.cj.exceptions.WrongArgumentException;
import com.mysql.cj.conf.RuntimeProperty;
import com.mysql.cj.Messages;
import com.mysql.cj.conf.PropertyDefinitions;
import com.mysql.cj.conf.PropertyKey;
import com.mysql.cj.exceptions.ExceptionFactory;
import com.mysql.cj.exceptions.UnableToConnectException;
import com.mysql.cj.protocol.ServerSession;
import java.util.List;
import com.mysql.cj.protocol.AuthenticationPlugin;
import java.util.Map;
import com.mysql.cj.protocol.Protocol;
import com.mysql.cj.conf.PropertySet;
import com.mysql.cj.exceptions.ExceptionInterceptor;
import com.mysql.cj.protocol.AuthenticationProvider;

public class NativeAuthenticationProvider implements AuthenticationProvider<NativePacketPayload>
{
    protected static final int AUTH_411_OVERHEAD = 33;
    private static final String NONE = "none";
    protected String seed;
    private boolean useConnectWithDb;
    private ExceptionInterceptor exceptionInterceptor;
    private PropertySet propertySet;
    private Protocol<NativePacketPayload> protocol;
    private Map<String, AuthenticationPlugin<NativePacketPayload>> authenticationPlugins;
    private List<String> disabledAuthenticationPlugins;
    private String clientDefaultAuthenticationPlugin;
    private String clientDefaultAuthenticationPluginName;
    private String serverDefaultAuthenticationPluginName;
    
    public NativeAuthenticationProvider() {
        this.authenticationPlugins = null;
        this.disabledAuthenticationPlugins = null;
        this.clientDefaultAuthenticationPlugin = null;
        this.clientDefaultAuthenticationPluginName = null;
        this.serverDefaultAuthenticationPluginName = null;
    }
    
    @Override
    public void init(final Protocol<NativePacketPayload> prot, final PropertySet propSet, final ExceptionInterceptor excInterceptor) {
        this.protocol = prot;
        this.propertySet = propSet;
        this.exceptionInterceptor = excInterceptor;
    }
    
    @Override
    public void connect(final ServerSession sessState, final String user, final String password, final String database) {
        long clientParam = sessState.getClientParam();
        final NativeCapabilities capabilities = (NativeCapabilities)sessState.getCapabilities();
        final NativePacketPayload buf = capabilities.getInitialHandshakePacket();
        this.seed = capabilities.getSeed();
        sessState.setServerDefaultCollationIndex(capabilities.getServerDefaultCollationIndex());
        sessState.setStatusFlags(capabilities.getStatusFlags());
        final int capabilityFlags = capabilities.getCapabilityFlags();
        if ((capabilityFlags & 0x8000) == 0x0) {
            throw ExceptionFactory.createException(UnableToConnectException.class, "CLIENT_SECURE_CONNECTION is required", this.getExceptionInterceptor());
        }
        clientParam |= 0x8000L;
        final int authPluginDataLength = capabilities.getAuthPluginDataLength();
        String seedPart2;
        StringBuilder newSeed;
        if (authPluginDataLength > 0) {
            seedPart2 = buf.readString(NativeConstants.StringLengthDataType.STRING_FIXED, "ASCII", authPluginDataLength - 8);
            newSeed = new StringBuilder(authPluginDataLength);
        }
        else {
            seedPart2 = buf.readString(NativeConstants.StringSelfDataType.STRING_TERM, "ASCII");
            newSeed = new StringBuilder(20);
        }
        newSeed.append(this.seed);
        newSeed.append(seedPart2);
        this.seed = newSeed.toString();
        if ((capabilityFlags & 0x20) != 0x0 && this.propertySet.getBooleanProperty(PropertyKey.useCompression).getValue()) {
            clientParam |= 0x20L;
        }
        this.useConnectWithDb = (database != null && database.length() > 0 && !this.propertySet.getBooleanProperty(PropertyKey.createDatabaseIfNotExist).getValue());
        if (this.useConnectWithDb) {
            clientParam |= 0x8L;
        }
        final RuntimeProperty<Boolean> useInformationSchema = this.propertySet.getProperty(PropertyKey.useInformationSchema);
        if (this.protocol.versionMeetsMinimum(8, 0, 3) && !useInformationSchema.getValue() && !useInformationSchema.isExplicitlySet()) {
            useInformationSchema.setValue(true);
        }
        final PropertyDefinitions.SslMode sslMode = this.propertySet.getEnumProperty(PropertyKey.sslMode).getValue();
        if ((capabilityFlags & 0x800) == 0x0 && sslMode != PropertyDefinitions.SslMode.DISABLED && sslMode != PropertyDefinitions.SslMode.PREFERRED) {
            throw ExceptionFactory.createException(UnableToConnectException.class, Messages.getString("MysqlIO.15"), this.getExceptionInterceptor());
        }
        if ((capabilityFlags & 0x4) != 0x0) {
            clientParam |= 0x4L;
            sessState.setHasLongColumnInfo(true);
        }
        if (!this.propertySet.getBooleanProperty(PropertyKey.useAffectedRows).getValue()) {
            clientParam |= 0x2L;
        }
        if (this.propertySet.getBooleanProperty(PropertyKey.allowLoadLocalInfile).getValue()) {
            clientParam |= 0x80L;
        }
        if (this.propertySet.getBooleanProperty(PropertyKey.interactiveClient).getValue()) {
            clientParam |= 0x400L;
        }
        if ((capabilityFlags & 0x800000) != 0x0) {}
        if ((capabilityFlags & 0x1000000) != 0x0) {
            clientParam |= 0x1000000L;
        }
        if ((capabilityFlags & 0x80000) != 0x0) {
            sessState.setClientParam(clientParam);
            this.proceedHandshakeWithPluggableAuthentication(sessState, user, password, database, buf);
            return;
        }
        throw ExceptionFactory.createException(UnableToConnectException.class, "CLIENT_PLUGIN_AUTH is required", this.getExceptionInterceptor());
    }
    
    private void loadAuthenticationPlugins() {
        this.clientDefaultAuthenticationPlugin = this.propertySet.getStringProperty(PropertyKey.defaultAuthenticationPlugin).getValue();
        if (this.clientDefaultAuthenticationPlugin == null || "".equals(this.clientDefaultAuthenticationPlugin.trim())) {
            throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("AuthenticationProvider.BadDefaultAuthenticationPlugin", new Object[] { this.clientDefaultAuthenticationPlugin }), this.getExceptionInterceptor());
        }
        final String disabledPlugins = this.propertySet.getStringProperty(PropertyKey.disabledAuthenticationPlugins).getValue();
        if (disabledPlugins != null && !"".equals(disabledPlugins)) {
            this.disabledAuthenticationPlugins = new ArrayList<String>();
            final List<String> pluginsToDisable = StringUtils.split(disabledPlugins, ",", true);
            final Iterator<String> iter = pluginsToDisable.iterator();
            while (iter.hasNext()) {
                this.disabledAuthenticationPlugins.add(iter.next());
            }
        }
        this.authenticationPlugins = new HashMap<String, AuthenticationPlugin<NativePacketPayload>>();
        boolean defaultIsFound = false;
        final List<AuthenticationPlugin<NativePacketPayload>> pluginsToInit = new LinkedList<AuthenticationPlugin<NativePacketPayload>>();
        pluginsToInit.add(new MysqlNativePasswordPlugin());
        pluginsToInit.add(new MysqlClearPasswordPlugin());
        pluginsToInit.add(new Sha256PasswordPlugin());
        pluginsToInit.add(new CachingSha2PasswordPlugin());
        pluginsToInit.add(new MysqlOldPasswordPlugin());
        final String authenticationPluginClasses = this.propertySet.getStringProperty(PropertyKey.authenticationPlugins).getValue();
        if (authenticationPluginClasses != null && !"".equals(authenticationPluginClasses)) {
            final List<String> pluginsToCreate = StringUtils.split(authenticationPluginClasses, ",", true);
            String className = null;
            try {
                for (int i = 0, s = pluginsToCreate.size(); i < s; ++i) {
                    className = pluginsToCreate.get(i);
                    pluginsToInit.add((AuthenticationPlugin<NativePacketPayload>)Class.forName(className).newInstance());
                }
            }
            catch (Throwable t) {
                throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("AuthenticationProvider.BadAuthenticationPlugin", new Object[] { className }), t, this.exceptionInterceptor);
            }
        }
        for (final AuthenticationPlugin<NativePacketPayload> plugin : pluginsToInit) {
            plugin.init(this.protocol);
            if (this.addAuthenticationPlugin(plugin)) {
                defaultIsFound = true;
            }
        }
        if (!defaultIsFound) {
            throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("AuthenticationProvider.DefaultAuthenticationPluginIsNotListed", new Object[] { this.clientDefaultAuthenticationPlugin }), this.getExceptionInterceptor());
        }
    }
    
    private boolean addAuthenticationPlugin(final AuthenticationPlugin<NativePacketPayload> plugin) {
        boolean isDefault = false;
        final String pluginClassName = plugin.getClass().getName();
        final String pluginProtocolName = plugin.getProtocolPluginName();
        final boolean disabledByClassName = this.disabledAuthenticationPlugins != null && this.disabledAuthenticationPlugins.contains(pluginClassName);
        final boolean disabledByMechanism = this.disabledAuthenticationPlugins != null && this.disabledAuthenticationPlugins.contains(pluginProtocolName);
        if (disabledByClassName || disabledByMechanism) {
            if (this.clientDefaultAuthenticationPlugin.equals(pluginClassName)) {
                throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("AuthenticationProvider.BadDisabledAuthenticationPlugin", new Object[] { disabledByClassName ? pluginClassName : pluginProtocolName }), this.getExceptionInterceptor());
            }
        }
        else {
            this.authenticationPlugins.put(pluginProtocolName, plugin);
            if (this.clientDefaultAuthenticationPlugin.equals(pluginClassName)) {
                this.clientDefaultAuthenticationPluginName = pluginProtocolName;
                isDefault = true;
            }
        }
        return isDefault;
    }
    
    private AuthenticationPlugin<NativePacketPayload> getAuthenticationPlugin(final String pluginName) {
        AuthenticationPlugin<NativePacketPayload> plugin = this.authenticationPlugins.get(pluginName);
        if (plugin != null && !plugin.isReusable()) {
            try {
                plugin = (AuthenticationPlugin<NativePacketPayload>)plugin.getClass().newInstance();
                plugin.init(this.protocol);
            }
            catch (Throwable t) {
                throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("AuthenticationProvider.BadAuthenticationPlugin", new Object[] { plugin.getClass().getName() }), t, this.getExceptionInterceptor());
            }
        }
        return plugin;
    }
    
    private void checkConfidentiality(final AuthenticationPlugin<?> plugin) {
        if (plugin.requiresConfidentiality() && !this.protocol.getSocketConnection().isSSLEstablished()) {
            throw ExceptionFactory.createException(Messages.getString("AuthenticationProvider.AuthenticationPluginRequiresSSL", new Object[] { plugin.getProtocolPluginName() }), this.getExceptionInterceptor());
        }
    }
    
    private void proceedHandshakeWithPluggableAuthentication(final ServerSession sessState, final String user, final String password, final String database, NativePacketPayload challenge) {
        if (this.authenticationPlugins == null) {
            this.loadAuthenticationPlugins();
        }
        boolean skipPassword = false;
        final int passwordLength = 16;
        final int userLength = (user != null) ? user.length() : 0;
        final int databaseLength = (database != null) ? database.length() : 0;
        final int packLength = (userLength + passwordLength + databaseLength) * 3 + 7 + 33;
        long clientParam = sessState.getClientParam();
        final int serverCapabilities = sessState.getCapabilities().getCapabilityFlags();
        AuthenticationPlugin<NativePacketPayload> plugin = null;
        NativePacketPayload fromServer = null;
        final ArrayList<NativePacketPayload> toServer = new ArrayList<NativePacketPayload>();
        boolean done = false;
        NativePacketPayload last_sent = null;
        boolean old_raw_challenge = false;
        int counter = 100;
        while (0 < counter--) {
            if (!done) {
                if (challenge != null) {
                    if (challenge.isOKPacket()) {
                        throw ExceptionFactory.createException(Messages.getString("AuthenticationProvider.UnexpectedAuthenticationApproval", new Object[] { plugin.getProtocolPluginName() }), this.getExceptionInterceptor());
                    }
                    clientParam |= 0xEA201L;
                    if (this.propertySet.getBooleanProperty(PropertyKey.allowMultiQueries).getValue()) {
                        clientParam |= 0x10000L;
                    }
                    if ((serverCapabilities & 0x400000) != 0x0 && !this.propertySet.getBooleanProperty(PropertyKey.disconnectOnExpiredPasswords).getValue()) {
                        clientParam |= 0x400000L;
                    }
                    if ((serverCapabilities & 0x100000) != 0x0 && !"none".equals(this.propertySet.getStringProperty(PropertyKey.connectionAttributes).getValue())) {
                        clientParam |= 0x100000L;
                    }
                    if ((serverCapabilities & 0x200000) != 0x0) {
                        clientParam |= 0x200000L;
                    }
                    sessState.setClientParam(clientParam);
                    if ((serverCapabilities & 0x800) != 0x0 && this.propertySet.getEnumProperty(PropertyKey.sslMode).getValue() != PropertyDefinitions.SslMode.DISABLED) {
                        this.negotiateSSLConnection(packLength);
                    }
                    String pluginName = null;
                    if ((serverCapabilities & 0x80000) != 0x0) {
                        if (!this.protocol.versionMeetsMinimum(5, 5, 10) || (this.protocol.versionMeetsMinimum(5, 6, 0) && !this.protocol.versionMeetsMinimum(5, 6, 2))) {
                            pluginName = challenge.readString(NativeConstants.StringLengthDataType.STRING_FIXED, "ASCII", ((NativeCapabilities)sessState.getCapabilities()).getAuthPluginDataLength());
                        }
                        else {
                            pluginName = challenge.readString(NativeConstants.StringSelfDataType.STRING_TERM, "ASCII");
                        }
                    }
                    plugin = this.getAuthenticationPlugin(pluginName);
                    if (plugin == null) {
                        plugin = this.getAuthenticationPlugin(this.clientDefaultAuthenticationPluginName);
                    }
                    else if (pluginName.equals(Sha256PasswordPlugin.PLUGIN_NAME) && !this.protocol.getSocketConnection().isSSLEstablished() && this.propertySet.getStringProperty(PropertyKey.serverRSAPublicKeyFile).getValue() == null && !this.propertySet.getBooleanProperty(PropertyKey.allowPublicKeyRetrieval).getValue()) {
                        plugin = this.getAuthenticationPlugin(this.clientDefaultAuthenticationPluginName);
                        skipPassword = !this.clientDefaultAuthenticationPluginName.equals(pluginName);
                    }
                    this.serverDefaultAuthenticationPluginName = plugin.getProtocolPluginName();
                    this.checkConfidentiality(plugin);
                    fromServer = new NativePacketPayload(StringUtils.getBytes(this.seed));
                }
                else {
                    plugin = this.getAuthenticationPlugin((this.serverDefaultAuthenticationPluginName == null) ? this.clientDefaultAuthenticationPluginName : this.serverDefaultAuthenticationPluginName);
                    this.checkConfidentiality(plugin);
                    fromServer = new NativePacketPayload(StringUtils.getBytes(this.seed));
                }
            }
            else {
                challenge = this.protocol.checkErrorMessage();
                old_raw_challenge = false;
                if (plugin == null) {
                    plugin = this.getAuthenticationPlugin((this.serverDefaultAuthenticationPluginName == null) ? this.clientDefaultAuthenticationPluginName : this.serverDefaultAuthenticationPluginName);
                }
                if (challenge.isOKPacket()) {
                    final OkPacket ok = OkPacket.parse(challenge, null);
                    sessState.setStatusFlags(ok.getStatusFlags(), true);
                    plugin.destroy();
                    break;
                }
                if (challenge.isAuthMethodSwitchRequestPacket()) {
                    skipPassword = false;
                    final String pluginName = challenge.readString(NativeConstants.StringSelfDataType.STRING_TERM, "ASCII");
                    if (!plugin.getProtocolPluginName().equals(pluginName)) {
                        plugin.destroy();
                        plugin = this.getAuthenticationPlugin(pluginName);
                        if (plugin == null) {
                            throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("AuthenticationProvider.BadAuthenticationPlugin", new Object[] { pluginName }), this.getExceptionInterceptor());
                        }
                    }
                    else {
                        plugin.reset();
                    }
                    this.checkConfidentiality(plugin);
                    fromServer = new NativePacketPayload(StringUtils.getBytes(challenge.readString(NativeConstants.StringSelfDataType.STRING_TERM, "ASCII")));
                }
                else {
                    if (!this.protocol.versionMeetsMinimum(5, 5, 16)) {
                        old_raw_challenge = true;
                        challenge.setPosition(challenge.getPosition() - 1);
                    }
                    fromServer = new NativePacketPayload(challenge.readBytes(NativeConstants.StringSelfDataType.STRING_EOF));
                }
            }
            plugin.setAuthenticationParameters(user, skipPassword ? null : password);
            done = plugin.nextAuthenticationStep(fromServer, toServer);
            if (toServer.size() > 0) {
                if (challenge == null) {
                    final String enc = this.getEncodingForHandshake();
                    last_sent = new NativePacketPayload(packLength + 1);
                    last_sent.writeInteger(NativeConstants.IntegerDataType.INT1, 17L);
                    last_sent.writeBytes(NativeConstants.StringSelfDataType.STRING_TERM, StringUtils.getBytes(user, enc));
                    if (toServer.get(0).getPayloadLength() < 256) {
                        last_sent.writeInteger(NativeConstants.IntegerDataType.INT1, toServer.get(0).getPayloadLength());
                        last_sent.writeBytes(NativeConstants.StringSelfDataType.STRING_EOF, toServer.get(0).getByteBuffer(), 0, toServer.get(0).getPayloadLength());
                    }
                    else {
                        last_sent.writeInteger(NativeConstants.IntegerDataType.INT1, 0L);
                    }
                    if (this.useConnectWithDb) {
                        last_sent.writeBytes(NativeConstants.StringSelfDataType.STRING_TERM, StringUtils.getBytes(database, enc));
                    }
                    else {
                        last_sent.writeInteger(NativeConstants.IntegerDataType.INT1, 0L);
                    }
                    last_sent.writeInteger(NativeConstants.IntegerDataType.INT1, AuthenticationProvider.getCharsetForHandshake(enc, sessState.getCapabilities().getServerVersion()));
                    last_sent.writeInteger(NativeConstants.IntegerDataType.INT1, 0L);
                    if ((serverCapabilities & 0x80000) != 0x0) {
                        last_sent.writeBytes(NativeConstants.StringSelfDataType.STRING_TERM, StringUtils.getBytes(plugin.getProtocolPluginName(), enc));
                    }
                    if ((clientParam & 0x100000L) != 0x0L) {
                        this.appendConnectionAttributes(last_sent, this.propertySet.getStringProperty(PropertyKey.connectionAttributes).getValue(), enc);
                    }
                    this.protocol.send(last_sent, last_sent.getPosition());
                }
                else if (challenge.isAuthMethodSwitchRequestPacket()) {
                    this.protocol.send(toServer.get(0), toServer.get(0).getPayloadLength());
                }
                else if (challenge.isAuthMoreData() || old_raw_challenge) {
                    for (final NativePacketPayload buffer : toServer) {
                        this.protocol.send(buffer, buffer.getPayloadLength());
                    }
                }
                else {
                    final String enc = this.getEncodingForHandshake();
                    last_sent = new NativePacketPayload(packLength);
                    last_sent.writeInteger(NativeConstants.IntegerDataType.INT4, clientParam);
                    last_sent.writeInteger(NativeConstants.IntegerDataType.INT4, 16777215L);
                    last_sent.writeInteger(NativeConstants.IntegerDataType.INT1, AuthenticationProvider.getCharsetForHandshake(enc, sessState.getCapabilities().getServerVersion()));
                    last_sent.writeBytes(NativeConstants.StringLengthDataType.STRING_FIXED, new byte[23]);
                    last_sent.writeBytes(NativeConstants.StringSelfDataType.STRING_TERM, StringUtils.getBytes(user, enc));
                    if ((serverCapabilities & 0x200000) != 0x0) {
                        last_sent.writeBytes(NativeConstants.StringSelfDataType.STRING_LENENC, toServer.get(0).readBytes(NativeConstants.StringSelfDataType.STRING_EOF));
                    }
                    else {
                        last_sent.writeInteger(NativeConstants.IntegerDataType.INT1, toServer.get(0).getPayloadLength());
                        last_sent.writeBytes(NativeConstants.StringSelfDataType.STRING_EOF, toServer.get(0).getByteBuffer());
                    }
                    if (this.useConnectWithDb) {
                        last_sent.writeBytes(NativeConstants.StringSelfDataType.STRING_TERM, StringUtils.getBytes(database, enc));
                    }
                    if ((serverCapabilities & 0x80000) != 0x0) {
                        last_sent.writeBytes(NativeConstants.StringSelfDataType.STRING_TERM, StringUtils.getBytes(plugin.getProtocolPluginName(), enc));
                    }
                    if ((clientParam & 0x100000L) != 0x0L) {
                        this.appendConnectionAttributes(last_sent, this.propertySet.getStringProperty(PropertyKey.connectionAttributes).getValue(), enc);
                    }
                    this.protocol.send(last_sent, last_sent.getPosition());
                }
            }
        }
        if (counter == 0) {
            throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("CommunicationsException.TooManyAuthenticationPluginNegotiations"), this.getExceptionInterceptor());
        }
        this.protocol.afterHandshake();
        if (!this.useConnectWithDb) {
            this.protocol.changeDatabase(database);
        }
    }
    
    private Map<String, String> getConnectionAttributesMap(final String attStr) {
        final Map<String, String> attMap = new HashMap<String, String>();
        if (attStr != null) {
            final String[] split;
            final String[] pairs = split = attStr.split(",");
            for (final String pair : split) {
                final int keyEnd = pair.indexOf(":");
                if (keyEnd > 0 && keyEnd + 1 < pair.length()) {
                    attMap.put(pair.substring(0, keyEnd), pair.substring(keyEnd + 1));
                }
            }
        }
        attMap.put("_client_name", "MySQL Connector/J");
        attMap.put("_client_version", "8.0.14");
        attMap.put("_runtime_vendor", Constants.JVM_VENDOR);
        attMap.put("_runtime_version", Constants.JVM_VERSION);
        attMap.put("_client_license", "GPL");
        return attMap;
    }
    
    private void appendConnectionAttributes(final NativePacketPayload buf, final String attributes, final String enc) {
        final NativePacketPayload lb = new NativePacketPayload(100);
        final Map<String, String> attMap = this.getConnectionAttributesMap(attributes);
        for (final String key : attMap.keySet()) {
            lb.writeBytes(NativeConstants.StringSelfDataType.STRING_LENENC, StringUtils.getBytes(key, enc));
            lb.writeBytes(NativeConstants.StringSelfDataType.STRING_LENENC, StringUtils.getBytes(attMap.get(key), enc));
        }
        buf.writeInteger(NativeConstants.IntegerDataType.INT_LENENC, lb.getPosition());
        buf.writeBytes(NativeConstants.StringLengthDataType.STRING_FIXED, lb.getByteBuffer(), 0, lb.getPosition());
    }
    
    @Override
    public String getEncodingForHandshake() {
        String enc = this.propertySet.getStringProperty(PropertyKey.characterEncoding).getValue();
        if (enc == null) {
            enc = "UTF-8";
        }
        return enc;
    }
    
    public ExceptionInterceptor getExceptionInterceptor() {
        return this.exceptionInterceptor;
    }
    
    private void negotiateSSLConnection(final int packLength) {
        this.protocol.negotiateSSLConnection(packLength);
    }
    
    @Override
    public void changeUser(final ServerSession serverSession, final String userName, final String password, final String database) {
        this.proceedHandshakeWithPluggableAuthentication(serverSession, userName, password, database, null);
    }
}
