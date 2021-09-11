
package com.mysql.cj.protocol;

import java.util.LinkedList;
import com.mysql.cj.MessageBuilder;
import com.mysql.cj.exceptions.ExceptionInterceptor;
import com.mysql.cj.log.Log;
import com.mysql.cj.conf.PropertySet;
import com.mysql.cj.Session;

public abstract class AbstractProtocol<M extends Message> implements Protocol<M>
{
    protected Session session;
    protected SocketConnection socketConnection;
    protected PropertySet propertySet;
    protected transient Log log;
    protected ExceptionInterceptor exceptionInterceptor;
    protected AuthenticationProvider<M> authProvider;
    protected MessageBuilder<M> messageBuilder;
    private PacketSentTimeHolder packetSentTimeHolder;
    private PacketReceivedTimeHolder packetReceivedTimeHolder;
    protected LinkedList<StringBuilder> packetDebugRingBuffer;
    
    public AbstractProtocol() {
        this.packetSentTimeHolder = new PacketSentTimeHolder() {};
        this.packetReceivedTimeHolder = new PacketReceivedTimeHolder() {};
        this.packetDebugRingBuffer = null;
    }
    
    @Override
    public SocketConnection getSocketConnection() {
        return this.socketConnection;
    }
    
    @Override
    public AuthenticationProvider<M> getAuthenticationProvider() {
        return this.authProvider;
    }
    
    @Override
    public ExceptionInterceptor getExceptionInterceptor() {
        return this.exceptionInterceptor;
    }
    
    @Override
    public PacketSentTimeHolder getPacketSentTimeHolder() {
        return this.packetSentTimeHolder;
    }
    
    @Override
    public void setPacketSentTimeHolder(final PacketSentTimeHolder packetSentTimeHolder) {
        this.packetSentTimeHolder = packetSentTimeHolder;
    }
    
    @Override
    public PacketReceivedTimeHolder getPacketReceivedTimeHolder() {
        return this.packetReceivedTimeHolder;
    }
    
    @Override
    public void setPacketReceivedTimeHolder(final PacketReceivedTimeHolder packetReceivedTimeHolder) {
        this.packetReceivedTimeHolder = packetReceivedTimeHolder;
    }
    
    @Override
    public PropertySet getPropertySet() {
        return this.propertySet;
    }
    
    @Override
    public void setPropertySet(final PropertySet propertySet) {
        this.propertySet = propertySet;
    }
    
    @Override
    public MessageBuilder<M> getMessageBuilder() {
        return this.messageBuilder;
    }
    
    @Override
    public void reset() {
    }
}
