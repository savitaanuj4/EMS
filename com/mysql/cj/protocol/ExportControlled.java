
package com.mysql.cj.protocol;

import java.util.Iterator;
import java.security.cert.CertPathValidatorResult;
import java.security.cert.CertPath;
import javax.naming.InvalidNameException;
import javax.naming.ldap.Rdn;
import javax.naming.ldap.LdapName;
import java.security.cert.CertPathValidatorException;
import java.security.InvalidAlgorithmParameterException;
import java.security.cert.PKIXCertPathValidatorResult;
import java.security.cert.CertPathParameters;
import java.security.cert.Certificate;
import java.security.cert.X509CertSelector;
import java.security.cert.X509Certificate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.security.cert.TrustAnchor;
import java.util.Set;
import java.security.cert.CertPathValidator;
import java.security.cert.PKIXParameters;
import java.security.cert.CertificateFactory;
import java.util.concurrent.Future;
import java.util.concurrent.ExecutionException;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CompletableFuture;
import javax.net.ssl.SSLEngineResult;
import java.nio.ByteBuffer;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLEngine;
import java.nio.channels.AsynchronousSocketChannel;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.security.InvalidKeyException;
import javax.crypto.NoSuchPaddingException;
import java.security.Key;
import javax.crypto.Cipher;
import java.security.GeneralSecurityException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.KeyFactory;
import java.security.spec.X509EncodedKeySpec;
import com.mysql.cj.util.Base64Decoder;
import com.mysql.cj.exceptions.RSAException;
import java.security.interfaces.RSAPublicKey;
import java.io.InputStream;
import javax.net.ssl.KeyManager;
import java.security.KeyManagementException;
import java.security.SecureRandom;
import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import java.security.KeyStoreException;
import java.security.UnrecoverableKeyException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import com.mysql.cj.exceptions.ExceptionFactory;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.SSLContext;
import com.mysql.cj.exceptions.ExceptionInterceptor;
import com.mysql.cj.exceptions.FeatureNotAvailableException;
import com.mysql.cj.exceptions.SSLParamsException;
import java.io.IOException;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.SSLSocket;
import com.mysql.cj.conf.PropertyDefinitions;
import java.net.Socket;
import com.mysql.cj.exceptions.CJCommunicationsException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import com.mysql.cj.util.Util;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import com.mysql.cj.util.StringUtils;
import com.mysql.cj.conf.PropertyKey;
import com.mysql.cj.ServerVersion;
import com.mysql.cj.conf.PropertySet;

public class ExportControlled
{
    private static final String TLSv1 = "TLSv1";
    private static final String TLSv1_1 = "TLSv1.1";
    private static final String TLSv1_2 = "TLSv1.2";
    private static final String[] TLS_PROTOCOLS;
    
    private ExportControlled() {
    }
    
    public static boolean enabled() {
        return true;
    }
    
    private static String[] getAllowedCiphers(final PropertySet pset, final ServerVersion serverVersion, final String[] socketCipherSuites) {
        List<String> allowedCiphers = null;
        final String enabledSSLCipherSuites = pset.getStringProperty(PropertyKey.enabledSSLCipherSuites).getValue();
        if (!StringUtils.isNullOrEmpty(enabledSSLCipherSuites)) {
            allowedCiphers = new ArrayList<String>();
            final List<String> availableCiphers = Arrays.asList(socketCipherSuites);
            for (final String cipher : enabledSSLCipherSuites.split("\\s*,\\s*")) {
                if (availableCiphers.contains(cipher)) {
                    allowedCiphers.add(cipher);
                }
            }
        }
        else if (serverVersion != null && !serverVersion.meetsMinimum(ServerVersion.parseVersion("5.7.6")) && (!serverVersion.meetsMinimum(ServerVersion.parseVersion("5.6.26")) || serverVersion.meetsMinimum(ServerVersion.parseVersion("5.7.0"))) && (!serverVersion.meetsMinimum(ServerVersion.parseVersion("5.5.45")) || serverVersion.meetsMinimum(ServerVersion.parseVersion("5.6.0")))) {
            allowedCiphers = new ArrayList<String>();
            for (final String cipher2 : socketCipherSuites) {
                if (cipher2.indexOf("_DHE_") == -1 && cipher2.indexOf("_DH_") == -1) {
                    allowedCiphers.add(cipher2);
                }
            }
        }
        return (String[])((allowedCiphers == null) ? null : ((String[])allowedCiphers.toArray(new String[0])));
    }
    
    private static String[] getAllowedProtocols(final PropertySet pset, final ServerVersion serverVersion, final String[] socketProtocols) {
        final String enabledTLSProtocols = pset.getStringProperty(PropertyKey.enabledTLSProtocols).getValue();
        String[] tryProtocols = null;
        if (enabledTLSProtocols != null && enabledTLSProtocols.length() > 0) {
            tryProtocols = enabledTLSProtocols.split("\\s*,\\s*");
        }
        else if (serverVersion != null && (serverVersion.meetsMinimum(ServerVersion.parseVersion("8.0.4")) || (serverVersion.meetsMinimum(ServerVersion.parseVersion("5.6.0")) && Util.isEnterpriseEdition(serverVersion.toString())))) {
            tryProtocols = ExportControlled.TLS_PROTOCOLS;
        }
        else {
            tryProtocols = new String[] { "TLSv1.1", "TLSv1" };
        }
        final List<String> configuredProtocols = new ArrayList<String>(Arrays.asList(tryProtocols));
        final List<String> jvmSupportedProtocols = Arrays.asList(socketProtocols);
        final List<String> allowedProtocols = new ArrayList<String>();
        for (final String protocol : ExportControlled.TLS_PROTOCOLS) {
            if (jvmSupportedProtocols.contains(protocol) && configuredProtocols.contains(protocol)) {
                allowedProtocols.add(protocol);
            }
        }
        return allowedProtocols.toArray(new String[0]);
    }
    
    private static KeyStoreConf getTrustStoreConf(final PropertySet propertySet, final PropertyKey keyStoreUrlPropertyKey, final PropertyKey keyStorePasswordPropertyKey, final PropertyKey keyStoreTypePropertyKey, final boolean required) {
        String trustStoreUrl = propertySet.getStringProperty(keyStoreUrlPropertyKey).getValue();
        String trustStorePassword = propertySet.getStringProperty(keyStorePasswordPropertyKey).getValue();
        String trustStoreType = propertySet.getStringProperty(keyStoreTypePropertyKey).getValue();
        if (StringUtils.isNullOrEmpty(trustStoreUrl)) {
            trustStoreUrl = System.getProperty("javax.net.ssl.trustStore");
            trustStorePassword = System.getProperty("javax.net.ssl.trustStorePassword");
            trustStoreType = System.getProperty("javax.net.ssl.trustStoreType");
            if (StringUtils.isNullOrEmpty(trustStoreType)) {
                trustStoreType = propertySet.getStringProperty(keyStoreTypePropertyKey).getInitialValue();
            }
            if (!StringUtils.isNullOrEmpty(trustStoreUrl)) {
                try {
                    new URL(trustStoreUrl);
                }
                catch (MalformedURLException e) {
                    trustStoreUrl = "file:" + trustStoreUrl;
                }
            }
        }
        if (required && StringUtils.isNullOrEmpty(trustStoreUrl)) {
            throw new CJCommunicationsException("No truststore provided to verify the Server certificate.");
        }
        return new KeyStoreConf(trustStoreUrl, trustStorePassword, trustStoreType);
    }
    
    private static KeyStoreConf getKeyStoreConf(final PropertySet propertySet, final PropertyKey keyStoreUrlPropertyKey, final PropertyKey keyStorePasswordPropertyKey, final PropertyKey keyStoreTypePropertyKey) {
        String keyStoreUrl = propertySet.getStringProperty(keyStoreUrlPropertyKey).getValue();
        String keyStorePassword = propertySet.getStringProperty(keyStorePasswordPropertyKey).getValue();
        String keyStoreType = propertySet.getStringProperty(keyStoreTypePropertyKey).getValue();
        if (StringUtils.isNullOrEmpty(keyStoreUrl)) {
            keyStoreUrl = System.getProperty("javax.net.ssl.keyStore");
            keyStorePassword = System.getProperty("javax.net.ssl.keyStorePassword");
            keyStoreType = System.getProperty("javax.net.ssl.keyStoreType");
            if (StringUtils.isNullOrEmpty(keyStoreType)) {
                keyStoreType = propertySet.getStringProperty(keyStoreTypePropertyKey).getInitialValue();
            }
            if (!StringUtils.isNullOrEmpty(keyStoreUrl)) {
                try {
                    new URL(keyStoreUrl);
                }
                catch (MalformedURLException e) {
                    keyStoreUrl = "file:" + keyStoreUrl;
                }
            }
        }
        return new KeyStoreConf(keyStoreUrl, keyStorePassword, keyStoreType);
    }
    
    public static Socket performTlsHandshake(final Socket rawSocket, final SocketConnection socketConnection, final ServerVersion serverVersion) throws IOException, SSLParamsException, FeatureNotAvailableException {
        final PropertySet pset = socketConnection.getPropertySet();
        final PropertyDefinitions.SslMode sslMode = pset.getEnumProperty(PropertyKey.sslMode).getValue();
        final boolean verifyServerCert = sslMode == PropertyDefinitions.SslMode.VERIFY_CA || sslMode == PropertyDefinitions.SslMode.VERIFY_IDENTITY;
        final KeyStoreConf trustStore = verifyServerCert ? getTrustStoreConf(pset, PropertyKey.trustCertificateKeyStoreUrl, PropertyKey.trustCertificateKeyStorePassword, PropertyKey.trustCertificateKeyStoreType, verifyServerCert && serverVersion == null) : new KeyStoreConf();
        final KeyStoreConf keyStore = getKeyStoreConf(pset, PropertyKey.clientCertificateKeyStoreUrl, PropertyKey.clientCertificateKeyStorePassword, PropertyKey.clientCertificateKeyStoreType);
        final SSLSocketFactory socketFactory = getSSLContext(keyStore.keyStoreUrl, keyStore.keyStoreType, keyStore.keyStorePassword, trustStore.keyStoreUrl, trustStore.keyStoreType, trustStore.keyStorePassword, serverVersion != null, verifyServerCert, (sslMode == PropertyDefinitions.SslMode.VERIFY_IDENTITY) ? socketConnection.getHost() : null, socketConnection.getExceptionInterceptor()).getSocketFactory();
        final SSLSocket sslSocket = (SSLSocket)socketFactory.createSocket(rawSocket, socketConnection.getHost(), socketConnection.getPort(), true);
        sslSocket.setEnabledProtocols(getAllowedProtocols(pset, serverVersion, sslSocket.getSupportedProtocols()));
        final String[] allowedCiphers = getAllowedCiphers(pset, serverVersion, sslSocket.getEnabledCipherSuites());
        if (allowedCiphers != null) {
            sslSocket.setEnabledCipherSuites(allowedCiphers);
        }
        sslSocket.startHandshake();
        return sslSocket;
    }
    
    public static SSLContext getSSLContext(final String clientCertificateKeyStoreUrl, final String clientCertificateKeyStoreType, final String clientCertificateKeyStorePassword, final String trustCertificateKeyStoreUrl, final String trustCertificateKeyStoreType, final String trustCertificateKeyStorePassword, final boolean fallbackToDefaultTrustStore, final boolean verifyServerCert, final String hostName, final ExceptionInterceptor exceptionInterceptor) throws SSLParamsException {
        TrustManagerFactory tmf = null;
        KeyManagerFactory kmf = null;
        KeyManager[] kms = null;
        final List<TrustManager> tms = new ArrayList<TrustManager>();
        try {
            tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        }
        catch (NoSuchAlgorithmException nsae) {
            throw ExceptionFactory.createException(SSLParamsException.class, "Default algorithm definitions for TrustManager and/or KeyManager are invalid.  Check java security properties file.", nsae, exceptionInterceptor);
        }
        if (!StringUtils.isNullOrEmpty(clientCertificateKeyStoreUrl)) {
            InputStream ksIS = null;
            try {
                if (!StringUtils.isNullOrEmpty(clientCertificateKeyStoreType)) {
                    final KeyStore clientKeyStore = KeyStore.getInstance(clientCertificateKeyStoreType);
                    final URL ksURL = new URL(clientCertificateKeyStoreUrl);
                    final char[] password = (clientCertificateKeyStorePassword == null) ? new char[0] : clientCertificateKeyStorePassword.toCharArray();
                    ksIS = ksURL.openStream();
                    clientKeyStore.load(ksIS, password);
                    kmf.init(clientKeyStore, password);
                    kms = kmf.getKeyManagers();
                }
            }
            catch (UnrecoverableKeyException uke) {
                throw ExceptionFactory.createException(SSLParamsException.class, "Could not recover keys from client keystore.  Check password?", uke, exceptionInterceptor);
            }
            catch (NoSuchAlgorithmException nsae2) {
                throw ExceptionFactory.createException(SSLParamsException.class, "Unsupported keystore algorithm [" + nsae2.getMessage() + "]", nsae2, exceptionInterceptor);
            }
            catch (KeyStoreException kse) {
                throw ExceptionFactory.createException(SSLParamsException.class, "Could not create KeyStore instance [" + kse.getMessage() + "]", kse, exceptionInterceptor);
            }
            catch (CertificateException nsae3) {
                throw ExceptionFactory.createException(SSLParamsException.class, "Could not load client" + clientCertificateKeyStoreType + " keystore from " + clientCertificateKeyStoreUrl, nsae3, exceptionInterceptor);
            }
            catch (MalformedURLException mue) {
                throw ExceptionFactory.createException(SSLParamsException.class, clientCertificateKeyStoreUrl + " does not appear to be a valid URL.", mue, exceptionInterceptor);
            }
            catch (IOException ioe) {
                throw ExceptionFactory.createException(SSLParamsException.class, "Cannot open " + clientCertificateKeyStoreUrl + " [" + ioe.getMessage() + "]", ioe, exceptionInterceptor);
            }
            finally {
                if (ksIS != null) {
                    try {
                        ksIS.close();
                    }
                    catch (IOException ex) {}
                }
            }
        }
        InputStream trustStoreIS = null;
        try {
            String trustStoreType = "";
            char[] trustStorePassword = null;
            KeyStore trustKeyStore = null;
            if (!StringUtils.isNullOrEmpty(trustCertificateKeyStoreUrl) && !StringUtils.isNullOrEmpty(trustCertificateKeyStoreType)) {
                trustStoreType = trustCertificateKeyStoreType;
                trustStorePassword = ((trustCertificateKeyStorePassword == null) ? new char[0] : trustCertificateKeyStorePassword.toCharArray());
                trustStoreIS = new URL(trustCertificateKeyStoreUrl).openStream();
                trustKeyStore = KeyStore.getInstance(trustStoreType);
                trustKeyStore.load(trustStoreIS, trustStorePassword);
            }
            if (trustKeyStore != null || fallbackToDefaultTrustStore) {
                tmf.init(trustKeyStore);
                final TrustManager[] trustManagers;
                final TrustManager[] origTms = trustManagers = tmf.getTrustManagers();
                for (final TrustManager tm : trustManagers) {
                    tms.add((tm instanceof X509TrustManager) ? new X509TrustManagerWrapper((X509TrustManager)tm, verifyServerCert, hostName) : tm);
                }
            }
        }
        catch (MalformedURLException e) {
            throw ExceptionFactory.createException(SSLParamsException.class, trustCertificateKeyStoreUrl + " does not appear to be a valid URL.", e, exceptionInterceptor);
        }
        catch (NoSuchAlgorithmException e2) {
            throw ExceptionFactory.createException(SSLParamsException.class, "Unsupported keystore algorithm [" + e2.getMessage() + "]", e2, exceptionInterceptor);
        }
        catch (KeyStoreException e3) {
            throw ExceptionFactory.createException(SSLParamsException.class, "Could not create KeyStore instance [" + e3.getMessage() + "]", e3, exceptionInterceptor);
        }
        catch (CertificateException e4) {
            throw ExceptionFactory.createException(SSLParamsException.class, "Could not load trust" + trustCertificateKeyStoreType + " keystore from " + trustCertificateKeyStoreUrl, e4, exceptionInterceptor);
        }
        catch (IOException e5) {
            throw ExceptionFactory.createException(SSLParamsException.class, "Cannot open " + trustCertificateKeyStoreUrl + " [" + e5.getMessage() + "]", e5, exceptionInterceptor);
        }
        finally {
            if (trustStoreIS != null) {
                try {
                    trustStoreIS.close();
                }
                catch (IOException ex2) {}
            }
        }
        if (tms.size() == 0) {
            tms.add(new X509TrustManagerWrapper(verifyServerCert, hostName));
        }
        try {
            final SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(kms, tms.toArray(new TrustManager[tms.size()]), null);
            return sslContext;
        }
        catch (NoSuchAlgorithmException nsae2) {
            throw new SSLParamsException("TLS is not a valid SSL protocol.", nsae2);
        }
        catch (KeyManagementException kme) {
            throw new SSLParamsException("KeyManagementException: " + kme.getMessage(), kme);
        }
    }
    
    public static boolean isSSLEstablished(final Socket socket) {
        return SSLSocket.class.isAssignableFrom(socket.getClass());
    }
    
    public static RSAPublicKey decodeRSAPublicKey(final String key) throws RSAException {
        if (key == null) {
            throw ExceptionFactory.createException(RSAException.class, "Key parameter is null");
        }
        final int offset = key.indexOf("\n") + 1;
        final int len = key.indexOf("-----END PUBLIC KEY-----") - offset;
        final byte[] certificateData = Base64Decoder.decode(key.getBytes(), offset, len);
        final X509EncodedKeySpec spec = new X509EncodedKeySpec(certificateData);
        try {
            final KeyFactory kf = KeyFactory.getInstance("RSA");
            return (RSAPublicKey)kf.generatePublic(spec);
        }
        catch (NoSuchAlgorithmException | InvalidKeySpecException ex2) {
            final GeneralSecurityException ex;
            final GeneralSecurityException e = ex;
            throw ExceptionFactory.createException(RSAException.class, "Unable to decode public key", e);
        }
    }
    
    public static byte[] encryptWithRSAPublicKey(final byte[] source, final RSAPublicKey key, final String transformation) throws RSAException {
        try {
            final Cipher cipher = Cipher.getInstance(transformation);
            cipher.init(1, key);
            return cipher.doFinal(source);
        }
        catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex2) {
            final GeneralSecurityException ex;
            final GeneralSecurityException e = ex;
            throw ExceptionFactory.createException(RSAException.class, e.getMessage(), e);
        }
    }
    
    public static byte[] encryptWithRSAPublicKey(final byte[] source, final RSAPublicKey key) throws RSAException {
        return encryptWithRSAPublicKey(source, key, "RSA/ECB/OAEPWithSHA-1AndMGF1Padding");
    }
    
    public static AsynchronousSocketChannel startTlsOnAsynchronousChannel(final AsynchronousSocketChannel channel, final SocketConnection socketConnection) throws SSLException {
        final PropertySet propertySet = socketConnection.getPropertySet();
        final PropertyDefinitions.SslMode sslMode = propertySet.getEnumProperty(PropertyKey.sslMode).getValue();
        final boolean verifyServerCert = sslMode == PropertyDefinitions.SslMode.VERIFY_CA || sslMode == PropertyDefinitions.SslMode.VERIFY_IDENTITY;
        final KeyStoreConf trustStore = verifyServerCert ? getTrustStoreConf(propertySet, PropertyKey.trustCertificateKeyStoreUrl, PropertyKey.trustCertificateKeyStorePassword, PropertyKey.trustCertificateKeyStoreType, true) : new KeyStoreConf();
        final KeyStoreConf keyStore = getKeyStoreConf(propertySet, PropertyKey.clientCertificateKeyStoreUrl, PropertyKey.clientCertificateKeyStorePassword, PropertyKey.clientCertificateKeyStoreType);
        final SSLContext sslContext = getSSLContext(keyStore.keyStoreUrl, keyStore.keyStoreType, keyStore.keyStorePassword, trustStore.keyStoreUrl, trustStore.keyStoreType, trustStore.keyStorePassword, false, verifyServerCert, (sslMode == PropertyDefinitions.SslMode.VERIFY_IDENTITY) ? socketConnection.getHost() : null, null);
        final SSLEngine sslEngine = sslContext.createSSLEngine();
        sslEngine.setUseClientMode(true);
        sslEngine.setEnabledProtocols(getAllowedProtocols(propertySet, null, sslEngine.getSupportedProtocols()));
        final String[] allowedCiphers = getAllowedCiphers(propertySet, null, sslEngine.getEnabledCipherSuites());
        if (allowedCiphers != null) {
            sslEngine.setEnabledCipherSuites(allowedCiphers);
        }
        performTlsHandshake(sslEngine, channel);
        return new TlsAsynchronousSocketChannel(channel, sslEngine);
    }
    
    private static void performTlsHandshake(final SSLEngine sslEngine, final AsynchronousSocketChannel channel) throws SSLException {
        sslEngine.beginHandshake();
        SSLEngineResult.HandshakeStatus handshakeStatus = sslEngine.getHandshakeStatus();
        final int packetBufferSize = sslEngine.getSession().getPacketBufferSize();
        final ByteBuffer myNetData = ByteBuffer.allocate(packetBufferSize);
        ByteBuffer peerNetData = ByteBuffer.allocate(packetBufferSize);
        final int appBufferSize = sslEngine.getSession().getApplicationBufferSize();
        final ByteBuffer myAppData = ByteBuffer.allocate(appBufferSize);
        ByteBuffer peerAppData = ByteBuffer.allocate(appBufferSize);
        SSLEngineResult res = null;
        while (handshakeStatus != SSLEngineResult.HandshakeStatus.FINISHED && handshakeStatus != SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING) {
            switch (handshakeStatus) {
                case NEED_WRAP: {
                    myNetData.clear();
                    res = sslEngine.wrap(myAppData, myNetData);
                    handshakeStatus = res.getHandshakeStatus();
                    switch (res.getStatus()) {
                        case OK: {
                            myNetData.flip();
                            write(channel, myNetData);
                            continue;
                        }
                        case BUFFER_OVERFLOW:
                        case BUFFER_UNDERFLOW:
                        case CLOSED: {
                            throw new CJCommunicationsException("Unacceptable SSLEngine result: " + res);
                        }
                    }
                    continue;
                }
                case NEED_UNWRAP: {
                    peerNetData.flip();
                    res = sslEngine.unwrap(peerNetData, peerAppData);
                    handshakeStatus = res.getHandshakeStatus();
                    switch (res.getStatus()) {
                        case OK: {
                            peerNetData.compact();
                            continue;
                        }
                        case BUFFER_OVERFLOW: {
                            final int newPeerAppDataSize = sslEngine.getSession().getApplicationBufferSize();
                            if (newPeerAppDataSize > peerAppData.capacity()) {
                                final ByteBuffer newPeerAppData = ByteBuffer.allocate(newPeerAppDataSize);
                                newPeerAppData.put(peerAppData);
                                newPeerAppData.flip();
                                peerAppData = newPeerAppData;
                                continue;
                            }
                            peerAppData.compact();
                            continue;
                        }
                        case BUFFER_UNDERFLOW: {
                            final int newPeerNetDataSize = sslEngine.getSession().getPacketBufferSize();
                            if (newPeerNetDataSize > peerNetData.capacity()) {
                                final ByteBuffer newPeerNetData = ByteBuffer.allocate(newPeerNetDataSize);
                                newPeerNetData.put(peerNetData);
                                newPeerNetData.flip();
                                peerNetData = newPeerNetData;
                            }
                            else {
                                peerNetData.compact();
                            }
                            if (read(channel, peerNetData) < 0) {
                                throw new CJCommunicationsException("Server does not provide enough data to proceed with SSL handshake.");
                            }
                            continue;
                        }
                        case CLOSED: {
                            throw new CJCommunicationsException("Unacceptable SSLEngine result: " + res);
                        }
                    }
                    continue;
                }
                case NEED_TASK: {
                    sslEngine.getDelegatedTask().run();
                    handshakeStatus = sslEngine.getHandshakeStatus();
                    continue;
                }
            }
        }
    }
    
    private static void write(final AsynchronousSocketChannel channel, final ByteBuffer data) {
        final CompletableFuture<Void> f = new CompletableFuture<Void>();
        final int bytesToWrite = data.limit();
        final CompletionHandler<Integer, Void> handler = new CompletionHandler<Integer, Void>() {
            @Override
            public void completed(final Integer bytesWritten, final Void nothing) {
                if (bytesWritten < bytesToWrite) {
                    channel.write(data, (Object)null, (CompletionHandler<Integer, ? super Object>)this);
                }
                else {
                    f.complete(null);
                }
            }
            
            @Override
            public void failed(final Throwable exc, final Void nothing) {
                f.completeExceptionally(exc);
            }
        };
        channel.write(data, (Object)null, (CompletionHandler<Integer, ? super Object>)handler);
        try {
            f.get();
        }
        catch (InterruptedException | ExecutionException ex3) {
            final Exception ex2;
            final Exception ex = ex2;
            throw new CJCommunicationsException(ex);
        }
    }
    
    private static Integer read(final AsynchronousSocketChannel channel, final ByteBuffer data) {
        final Future<Integer> f = channel.read(data);
        try {
            return f.get();
        }
        catch (InterruptedException | ExecutionException ex3) {
            final Exception ex2;
            final Exception ex = ex2;
            throw new CJCommunicationsException(ex);
        }
    }
    
    static {
        TLS_PROTOCOLS = new String[] { "TLSv1.2", "TLSv1.1", "TLSv1" };
    }
    
    private static class KeyStoreConf
    {
        public String keyStoreUrl;
        public String keyStorePassword;
        public String keyStoreType;
        
        public KeyStoreConf() {
            this.keyStoreUrl = null;
            this.keyStorePassword = null;
            this.keyStoreType = "JKS";
        }
        
        public KeyStoreConf(final String keyStoreUrl, final String keyStorePassword, final String keyStoreType) {
            this.keyStoreUrl = null;
            this.keyStorePassword = null;
            this.keyStoreType = "JKS";
            this.keyStoreUrl = keyStoreUrl;
            this.keyStorePassword = keyStorePassword;
            this.keyStoreType = keyStoreType;
        }
    }
    
    public static class X509TrustManagerWrapper implements X509TrustManager
    {
        private X509TrustManager origTm;
        private boolean verifyServerCert;
        private String hostName;
        private CertificateFactory certFactory;
        private PKIXParameters validatorParams;
        private CertPathValidator validator;
        
        public X509TrustManagerWrapper(final X509TrustManager tm, final boolean verifyServerCertificate, final String hostName) throws CertificateException {
            this.origTm = null;
            this.verifyServerCert = false;
            this.hostName = null;
            this.certFactory = null;
            this.validatorParams = null;
            this.validator = null;
            this.origTm = tm;
            this.verifyServerCert = verifyServerCertificate;
            this.hostName = hostName;
            if (verifyServerCertificate) {
                try {
                    final Set<TrustAnchor> anch = Arrays.stream(tm.getAcceptedIssuers()).map(c -> new TrustAnchor(c, null)).collect((Collector<? super Object, ?, Set<TrustAnchor>>)Collectors.toSet());
                    (this.validatorParams = new PKIXParameters(anch)).setRevocationEnabled(false);
                    this.validator = CertPathValidator.getInstance("PKIX");
                    this.certFactory = CertificateFactory.getInstance("X.509");
                }
                catch (Exception e) {
                    throw new CertificateException(e);
                }
            }
        }
        
        public X509TrustManagerWrapper(final boolean verifyServerCertificate, final String hostName) {
            this.origTm = null;
            this.verifyServerCert = false;
            this.hostName = null;
            this.certFactory = null;
            this.validatorParams = null;
            this.validator = null;
            this.verifyServerCert = verifyServerCertificate;
            this.hostName = hostName;
        }
        
        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return (this.origTm != null) ? this.origTm.getAcceptedIssuers() : new X509Certificate[0];
        }
        
        @Override
        public void checkServerTrusted(final X509Certificate[] chain, final String authType) throws CertificateException {
            for (int i = 0; i < chain.length; ++i) {
                chain[i].checkValidity();
            }
            if (this.validatorParams != null) {
                final X509CertSelector certSelect = new X509CertSelector();
                certSelect.setSerialNumber(chain[0].getSerialNumber());
                try {
                    final CertPath certPath = this.certFactory.generateCertPath(Arrays.asList(chain));
                    final CertPathValidatorResult result = this.validator.validate(certPath, this.validatorParams);
                    ((PKIXCertPathValidatorResult)result).getTrustAnchor().getTrustedCert().checkValidity();
                }
                catch (InvalidAlgorithmParameterException e) {
                    throw new CertificateException(e);
                }
                catch (CertPathValidatorException e2) {
                    throw new CertificateException(e2);
                }
            }
            if (this.verifyServerCert) {
                if (this.origTm == null) {
                    throw new CertificateException("Can't verify server certificate because no trust manager is found.");
                }
                this.origTm.checkServerTrusted(chain, authType);
                if (this.hostName != null) {
                    final String dn = chain[0].getSubjectX500Principal().getName("RFC2253");
                    String cn = null;
                    try {
                        final LdapName ldapDN = new LdapName(dn);
                        for (final Rdn rdn : ldapDN.getRdns()) {
                            if (rdn.getType().equalsIgnoreCase("CN")) {
                                cn = rdn.getValue().toString();
                                break;
                            }
                        }
                    }
                    catch (InvalidNameException e3) {
                        throw new CertificateException("Failed to retrieve the Common Name (CN) from the server certificate.");
                    }
                    if (!this.hostName.equalsIgnoreCase(cn)) {
                        throw new CertificateException("Server certificate identity check failed. The certificate Common Name '" + cn + "' does not match with '" + this.hostName + "'.");
                    }
                }
            }
        }
        
        @Override
        public void checkClientTrusted(final X509Certificate[] chain, final String authType) throws CertificateException {
            this.origTm.checkClientTrusted(chain, authType);
        }
    }
}
