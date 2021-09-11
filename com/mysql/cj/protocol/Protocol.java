
package com.mysql.cj.protocol;

import com.mysql.cj.log.ProfilerEventHandler;
import java.io.InputStream;
import java.io.IOException;
import com.mysql.cj.result.RowList;
import java.util.concurrent.CompletableFuture;
import com.mysql.cj.QueryResult;
import com.mysql.cj.exceptions.ExceptionInterceptor;
import com.mysql.cj.MessageBuilder;
import com.mysql.cj.TransactionEventHandler;
import com.mysql.cj.conf.PropertySet;
import com.mysql.cj.Session;

public interface Protocol<M extends Message>
{
    void init(final Session p0, final SocketConnection p1, final PropertySet p2, final TransactionEventHandler p3);
    
    PropertySet getPropertySet();
    
    void setPropertySet(final PropertySet p0);
    
    MessageBuilder<M> getMessageBuilder();
    
    ServerCapabilities readServerCapabilities();
    
    ServerSession getServerSession();
    
    SocketConnection getSocketConnection();
    
    AuthenticationProvider<M> getAuthenticationProvider();
    
    ExceptionInterceptor getExceptionInterceptor();
    
    PacketSentTimeHolder getPacketSentTimeHolder();
    
    void setPacketSentTimeHolder(final PacketSentTimeHolder p0);
    
    PacketReceivedTimeHolder getPacketReceivedTimeHolder();
    
    void setPacketReceivedTimeHolder(final PacketReceivedTimeHolder p0);
    
    void connect(final String p0, final String p1, final String p2);
    
    void negotiateSSLConnection(final int p0);
    
    void beforeHandshake();
    
    void afterHandshake();
    
    void changeDatabase(final String p0);
    
    void changeUser(final String p0, final String p1, final String p2);
    
    String getPasswordCharacterEncoding();
    
    boolean versionMeetsMinimum(final int p0, final int p1, final int p2);
    
    M readMessage(final M p0);
    
    M checkErrorMessage();
    
    void send(final Message p0, final int p1);
    
     <RES extends QueryResult> CompletableFuture<RES> sendAsync(final Message p0);
    
    ColumnDefinition readMetadata();
    
    RowList getRowInputStream(final ColumnDefinition p0);
    
    M sendCommand(final Message p0, final boolean p1, final int p2);
    
     <T extends ProtocolEntity> T read(final Class<T> p0, final ProtocolEntityFactory<T, M> p1) throws IOException;
    
     <T extends ProtocolEntity> T read(final Class<Resultset> p0, final int p1, final boolean p2, final M p3, final boolean p4, final ColumnDefinition p5, final ProtocolEntityFactory<T, M> p6) throws IOException;
    
    void setLocalInfileInputStream(final InputStream p0);
    
    InputStream getLocalInfileInputStream();
    
    String getQueryComment();
    
    void setQueryComment(final String p0);
    
     <QR extends QueryResult> QR readQueryResult();
    
    void close() throws IOException;
    
    void setCurrentResultStreamer(final ResultStreamer p0);
    
    void configureTimezone();
    
    void initServerSession();
    
    void reset();
    
    @FunctionalInterface
    public interface GetProfilerEventHandlerInstanceFunction
    {
        ProfilerEventHandler apply();
    }
}
