
package com.mysql.cj;

import com.mysql.cj.log.NullLogger;
import java.net.SocketAddress;
import com.mysql.cj.exceptions.ExceptionFactory;
import com.mysql.cj.exceptions.CJOperationNotSupportedException;
import com.mysql.cj.protocol.ServerSession;
import java.util.stream.Stream;
import com.mysql.cj.protocol.ColumnDefinition;
import java.util.stream.StreamSupport;
import java.util.Iterator;
import java.util.Spliterators;
import java.util.stream.Collector;
import java.util.function.Function;
import com.mysql.cj.result.Row;
import java.util.function.Predicate;
import java.util.concurrent.CompletableFuture;
import com.mysql.cj.log.ProfilerEventHandlerFactory;
import com.mysql.cj.log.LogFactory;
import com.mysql.cj.conf.PropertyKey;
import com.mysql.cj.log.ProfilerEventHandler;
import com.mysql.cj.conf.RuntimeProperty;
import com.mysql.cj.conf.HostInfo;
import com.mysql.cj.protocol.Message;
import com.mysql.cj.protocol.Protocol;
import com.mysql.cj.log.Log;
import com.mysql.cj.exceptions.ExceptionInterceptor;
import com.mysql.cj.conf.PropertySet;

public abstract class CoreSession implements Session
{
    protected PropertySet propertySet;
    protected ExceptionInterceptor exceptionInterceptor;
    protected transient Log log;
    protected static final Log NULL_LOGGER;
    protected transient Protocol<? extends Message> protocol;
    protected MessageBuilder<? extends Message> messageBuilder;
    protected long connectionCreationTimeMillis;
    protected HostInfo hostInfo;
    protected RuntimeProperty<Boolean> gatherPerfMetrics;
    protected RuntimeProperty<String> characterEncoding;
    protected RuntimeProperty<Boolean> disconnectOnExpiredPasswords;
    protected RuntimeProperty<Boolean> cacheServerConfiguration;
    protected RuntimeProperty<Boolean> autoReconnect;
    protected RuntimeProperty<Boolean> autoReconnectForPools;
    protected RuntimeProperty<Boolean> maintainTimeStats;
    protected int sessionMaxRows;
    private ProfilerEventHandler eventSink;
    
    public CoreSession(final HostInfo hostInfo, final PropertySet propSet) {
        this.connectionCreationTimeMillis = 0L;
        this.hostInfo = null;
        this.sessionMaxRows = -1;
        this.connectionCreationTimeMillis = System.currentTimeMillis();
        this.hostInfo = hostInfo;
        this.propertySet = propSet;
        this.gatherPerfMetrics = this.getPropertySet().getBooleanProperty(PropertyKey.gatherPerfMetrics);
        this.characterEncoding = this.getPropertySet().getStringProperty(PropertyKey.characterEncoding);
        this.disconnectOnExpiredPasswords = this.getPropertySet().getBooleanProperty(PropertyKey.disconnectOnExpiredPasswords);
        this.cacheServerConfiguration = this.getPropertySet().getBooleanProperty(PropertyKey.cacheServerConfiguration);
        this.autoReconnect = this.getPropertySet().getBooleanProperty(PropertyKey.autoReconnect);
        this.autoReconnectForPools = this.getPropertySet().getBooleanProperty(PropertyKey.autoReconnectForPools);
        this.maintainTimeStats = this.getPropertySet().getBooleanProperty(PropertyKey.maintainTimeStats);
        this.log = LogFactory.getLogger(this.getPropertySet().getStringProperty(PropertyKey.logger).getStringValue(), "MySQL");
        if (this.getPropertySet().getBooleanProperty(PropertyKey.profileSQL).getValue() || this.getPropertySet().getBooleanProperty(PropertyKey.useUsageAdvisor).getValue()) {
            ProfilerEventHandlerFactory.getInstance(this);
        }
    }
    
    @Override
    public void changeUser(final String user, final String password, final String database) {
        this.sessionMaxRows = -1;
        this.protocol.changeUser(user, password, database);
    }
    
    @Override
    public PropertySet getPropertySet() {
        return this.propertySet;
    }
    
    @Override
    public ExceptionInterceptor getExceptionInterceptor() {
        return this.exceptionInterceptor;
    }
    
    @Override
    public void setExceptionInterceptor(final ExceptionInterceptor exceptionInterceptor) {
        this.exceptionInterceptor = exceptionInterceptor;
    }
    
    @Override
    public Log getLog() {
        return this.log;
    }
    
    @Override
    public <M extends Message> MessageBuilder<M> getMessageBuilder() {
        return (MessageBuilder<M>)this.messageBuilder;
    }
    
    public <QR extends QueryResult> QR sendMessage(final Message message) {
        this.protocol.send(message, 0);
        return this.protocol.readQueryResult();
    }
    
    public <QR extends QueryResult> CompletableFuture<QR> asyncSendMessage(final Message message) {
        return this.protocol.sendAsync(message);
    }
    
    @Override
    public <M extends Message, RES_T, R> RES_T query(final M message, final Predicate<Row> filterRow, final Function<Row, R> mapRow, final Collector<R, ?, RES_T> collector) {
        this.protocol.send(message, 0);
        final ColumnDefinition metadata = this.protocol.readMetadata();
        final Iterator<Row> ris = this.protocol.getRowInputStream(metadata);
        Stream<Row> stream = StreamSupport.stream(Spliterators.spliteratorUnknownSize((Iterator<? extends Row>)ris, 0), false);
        if (filterRow != null) {
            stream = stream.filter(filterRow);
        }
        final RES_T result = stream.map((Function<? super Row, ?>)mapRow).collect((Collector<? super Object, ?, RES_T>)collector);
        this.protocol.readQueryResult();
        return result;
    }
    
    @Override
    public ServerSession getServerSession() {
        return this.protocol.getServerSession();
    }
    
    @Override
    public boolean versionMeetsMinimum(final int major, final int minor, final int subminor) {
        return this.protocol.versionMeetsMinimum(major, minor, subminor);
    }
    
    @Override
    public long getThreadId() {
        return this.protocol.getServerSession().getThreadId();
    }
    
    @Override
    public void forceClose() {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, "Not supported");
    }
    
    @Override
    public boolean isSetNeededForAutoCommitMode(final boolean autoCommitFlag) {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, "Not supported");
    }
    
    @Override
    public ProfilerEventHandler getProfilerEventHandler() {
        return this.eventSink;
    }
    
    @Override
    public void setProfilerEventHandler(final ProfilerEventHandler h) {
        this.eventSink = h;
    }
    
    @Override
    public boolean isSSLEstablished() {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, "Not supported");
    }
    
    @Override
    public SocketAddress getRemoteSocketAddress() {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, "Not supported");
    }
    
    @Override
    public void addListener(final SessionEventListener l) {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, "Not supported");
    }
    
    @Override
    public void removeListener(final SessionEventListener l) {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, "Not supported");
    }
    
    @Override
    public String getIdentifierQuoteString() {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, "Not supported");
    }
    
    @Override
    public DataStoreMetadata getDataStoreMetadata() {
        return new DataStoreMetadataImpl(this);
    }
    
    static {
        NULL_LOGGER = new NullLogger("MySQL");
    }
}
