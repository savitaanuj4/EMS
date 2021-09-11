
package com.mysql.cj.x.protobuf;

import com.google.protobuf.MessageOrBuilder;
import com.google.protobuf.AbstractMessageLite;
import com.google.protobuf.AbstractMessage;
import com.google.protobuf.Internal;
import com.google.protobuf.ProtocolMessageEnum;
import com.google.protobuf.AbstractParser;
import java.io.InputStream;
import com.google.protobuf.ByteString;
import java.nio.ByteBuffer;
import com.google.protobuf.CodedOutputStream;
import java.io.IOException;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.MessageLite;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.UnknownFieldSet;
import com.google.protobuf.Parser;
import com.google.protobuf.Message;
import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.ExtensionLite;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.Descriptors;
import com.google.protobuf.DescriptorProtos;
import com.google.protobuf.GeneratedMessage;

public final class Mysqlx
{
    public static final int CLIENT_MESSAGE_ID_FIELD_NUMBER = 100001;
    public static final GeneratedMessage.GeneratedExtension<DescriptorProtos.MessageOptions, ClientMessages.Type> clientMessageId;
    public static final int SERVER_MESSAGE_ID_FIELD_NUMBER = 100002;
    public static final GeneratedMessage.GeneratedExtension<DescriptorProtos.MessageOptions, ServerMessages.Type> serverMessageId;
    private static final Descriptors.Descriptor internal_static_Mysqlx_ClientMessages_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_Mysqlx_ClientMessages_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_Mysqlx_ServerMessages_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_Mysqlx_ServerMessages_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_Mysqlx_Ok_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_Mysqlx_Ok_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_Mysqlx_Error_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_Mysqlx_Error_fieldAccessorTable;
    private static Descriptors.FileDescriptor descriptor;
    
    private Mysqlx() {
    }
    
    public static void registerAllExtensions(final ExtensionRegistryLite registry) {
        registry.add((ExtensionLite)Mysqlx.clientMessageId);
        registry.add((ExtensionLite)Mysqlx.serverMessageId);
    }
    
    public static void registerAllExtensions(final ExtensionRegistry registry) {
        registerAllExtensions((ExtensionRegistryLite)registry);
    }
    
    public static Descriptors.FileDescriptor getDescriptor() {
        return Mysqlx.descriptor;
    }
    
    static {
        clientMessageId = GeneratedMessage.newFileScopedGeneratedExtension((Class)ClientMessages.Type.class, (Message)null);
        serverMessageId = GeneratedMessage.newFileScopedGeneratedExtension((Class)ServerMessages.Type.class, (Message)null);
        final String[] descriptorData = { "\n\fmysqlx.proto\u0012\u0006Mysqlx\u001a google/protobuf/descriptor.proto\"\u00f4\u0002\n\u000eClientMessages\"\u00e1\u0002\n\u0004Type\u0012\u0018\n\u0014CON_CAPABILITIES_GET\u0010\u0001\u0012\u0018\n\u0014CON_CAPABILITIES_SET\u0010\u0002\u0012\r\n\tCON_CLOSE\u0010\u0003\u0012\u001b\n\u0017SESS_AUTHENTICATE_START\u0010\u0004\u0012\u001e\n\u001aSESS_AUTHENTICATE_CONTINUE\u0010\u0005\u0012\u000e\n\nSESS_RESET\u0010\u0006\u0012\u000e\n\nSESS_CLOSE\u0010\u0007\u0012\u0014\n\u0010SQL_STMT_EXECUTE\u0010\f\u0012\r\n\tCRUD_FIND\u0010\u0011\u0012\u000f\n\u000bCRUD_INSERT\u0010\u0012\u0012\u000f\n\u000bCRUD_UPDATE\u0010\u0013\u0012\u000f\n\u000bCRUD_DELETE\u0010\u0014\u0012\u000f\n\u000bEXPECT_OPEN\u0010\u0018\u0012\u0010\n\fEXPECT_CLOSE\u0010\u0019\u0012\u0014\n\u0010CRUD_CREATE_VIEW\u0010\u001e\u0012\u0014\n\u0010CRUD_MODIFY_VIEW\u0010\u001f\u0012\u0012\n\u000eCRUD_DROP_VIEW\u0010 \"\u00e2\u0002\n\u000eServerMessages\"\u00cf\u0002\n\u0004Type\u0012\u0006\n\u0002OK\u0010\u0000\u0012\t\n\u0005ERROR\u0010\u0001\u0012\u0015\n\u0011CONN_CAPABILITIES\u0010\u0002\u0012\u001e\n\u001aSESS_AUTHENTICATE_CONTINUE\u0010\u0003\u0012\u0018\n\u0014SESS_AUTHENTICATE_OK\u0010\u0004\u0012\n\n\u0006NOTICE\u0010\u000b\u0012\u001e\n\u001aRESULTSET_COLUMN_META_DATA\u0010\f\u0012\u0011\n\rRESULTSET_ROW\u0010\r\u0012\u0018\n\u0014RESULTSET_FETCH_DONE\u0010\u000e\u0012\u001d\n\u0019RESULTSET_FETCH_SUSPENDED\u0010\u000f\u0012(\n$RESULTSET_FETCH_DONE_MORE_RESULTSETS\u0010\u0010\u0012\u0017\n\u0013SQL_STMT_EXECUTE_OK\u0010\u0011\u0012(\n$RESULTSET_FETCH_DONE_MORE_OUT_PARAMS\u0010\u0012\"\u0017\n\u0002Ok\u0012\u000b\n\u0003msg\u0018\u0001 \u0001(\t:\u0004\u0090\u00ea0\u0000\"\u008e\u0001\n\u0005Error\u0012/\n\bseverity\u0018\u0001 \u0001(\u000e2\u0016.Mysqlx.Error.Severity:\u0005ERROR\u0012\f\n\u0004code\u0018\u0002 \u0002(\r\u0012\u0011\n\tsql_state\u0018\u0004 \u0002(\t\u0012\u000b\n\u0003msg\u0018\u0003 \u0002(\t\" \n\bSeverity\u0012\t\n\u0005ERROR\u0010\u0000\u0012\t\n\u0005FATAL\u0010\u0001:\u0004\u0090\u00ea0\u0001:Y\n\u0011client_message_id\u0012\u001f.google.protobuf.MessageOptions\u0018¡\u008d\u0006 \u0001(\u000e2\u001b.Mysqlx.ClientMessages.Type:Y\n\u0011server_message_id\u0012\u001f.google.protobuf.MessageOptions\u0018¢\u008d\u0006 \u0001(\u000e2\u001b.Mysqlx.ServerMessages.TypeB\u0019\n\u0017com.mysql.cj.x.protobuf" };
        final Descriptors.FileDescriptor.InternalDescriptorAssigner assigner = (Descriptors.FileDescriptor.InternalDescriptorAssigner)new Descriptors.FileDescriptor.InternalDescriptorAssigner() {
            public ExtensionRegistry assignDescriptors(final Descriptors.FileDescriptor root) {
                Mysqlx.descriptor = root;
                return null;
            }
        };
        Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new Descriptors.FileDescriptor[] { DescriptorProtos.getDescriptor() }, assigner);
        internal_static_Mysqlx_ClientMessages_descriptor = getDescriptor().getMessageTypes().get(0);
        internal_static_Mysqlx_ClientMessages_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(Mysqlx.internal_static_Mysqlx_ClientMessages_descriptor, new String[0]);
        internal_static_Mysqlx_ServerMessages_descriptor = getDescriptor().getMessageTypes().get(1);
        internal_static_Mysqlx_ServerMessages_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(Mysqlx.internal_static_Mysqlx_ServerMessages_descriptor, new String[0]);
        internal_static_Mysqlx_Ok_descriptor = getDescriptor().getMessageTypes().get(2);
        internal_static_Mysqlx_Ok_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(Mysqlx.internal_static_Mysqlx_Ok_descriptor, new String[] { "Msg" });
        internal_static_Mysqlx_Error_descriptor = getDescriptor().getMessageTypes().get(3);
        internal_static_Mysqlx_Error_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(Mysqlx.internal_static_Mysqlx_Error_descriptor, new String[] { "Severity", "Code", "SqlState", "Msg" });
        Mysqlx.clientMessageId.internalInit((Descriptors.FieldDescriptor)Mysqlx.descriptor.getExtensions().get(0));
        Mysqlx.serverMessageId.internalInit((Descriptors.FieldDescriptor)Mysqlx.descriptor.getExtensions().get(1));
        final ExtensionRegistry registry = ExtensionRegistry.newInstance();
        registry.add((GeneratedMessage.GeneratedExtension)Mysqlx.serverMessageId);
        Descriptors.FileDescriptor.internalUpdateFileDescriptor(Mysqlx.descriptor, registry);
        DescriptorProtos.getDescriptor();
    }
    
    public static final class ClientMessages extends GeneratedMessageV3 implements ClientMessagesOrBuilder
    {
        private static final long serialVersionUID = 0L;
        private byte memoizedIsInitialized;
        private static final ClientMessages DEFAULT_INSTANCE;
        @Deprecated
        public static final Parser<ClientMessages> PARSER;
        
        private ClientMessages(final GeneratedMessageV3.Builder<?> builder) {
            super((GeneratedMessageV3.Builder)builder);
            this.memoizedIsInitialized = -1;
        }
        
        private ClientMessages() {
            this.memoizedIsInitialized = -1;
        }
        
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }
        
        private ClientMessages(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this();
            if (extensionRegistry == null) {
                throw new NullPointerException();
            }
            final UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
            try {
                boolean done = false;
                while (!done) {
                    final int tag = input.readTag();
                    switch (tag) {
                        case 0: {
                            done = true;
                            continue;
                        }
                        default: {
                            if (!this.parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                                done = true;
                                continue;
                            }
                            continue;
                        }
                    }
                }
            }
            catch (InvalidProtocolBufferException e) {
                throw e.setUnfinishedMessage((MessageLite)this);
            }
            catch (IOException e2) {
                throw new InvalidProtocolBufferException(e2).setUnfinishedMessage((MessageLite)this);
            }
            finally {
                this.unknownFields = unknownFields.build();
                this.makeExtensionsImmutable();
            }
        }
        
        public static final Descriptors.Descriptor getDescriptor() {
            return Mysqlx.internal_static_Mysqlx_ClientMessages_descriptor;
        }
        
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return Mysqlx.internal_static_Mysqlx_ClientMessages_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)ClientMessages.class, (Class)Builder.class);
        }
        
        public final boolean isInitialized() {
            final byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            this.memoizedIsInitialized = 1;
            return true;
        }
        
        public void writeTo(final CodedOutputStream output) throws IOException {
            this.unknownFields.writeTo(output);
        }
        
        public int getSerializedSize() {
            int size = this.memoizedSize;
            if (size != -1) {
                return size;
            }
            size = 0;
            size += this.unknownFields.getSerializedSize();
            return this.memoizedSize = size;
        }
        
        public boolean equals(final Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof ClientMessages)) {
                return super.equals(obj);
            }
            final ClientMessages other = (ClientMessages)obj;
            boolean result = true;
            result = (result && this.unknownFields.equals((Object)other.unknownFields));
            return result;
        }
        
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + getDescriptor().hashCode();
            hash = 29 * hash + this.unknownFields.hashCode();
            return this.memoizedHashCode = hash;
        }
        
        public static ClientMessages parseFrom(final ByteBuffer data) throws InvalidProtocolBufferException {
            return (ClientMessages)ClientMessages.PARSER.parseFrom(data);
        }
        
        public static ClientMessages parseFrom(final ByteBuffer data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ClientMessages)ClientMessages.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static ClientMessages parseFrom(final ByteString data) throws InvalidProtocolBufferException {
            return (ClientMessages)ClientMessages.PARSER.parseFrom(data);
        }
        
        public static ClientMessages parseFrom(final ByteString data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ClientMessages)ClientMessages.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static ClientMessages parseFrom(final byte[] data) throws InvalidProtocolBufferException {
            return (ClientMessages)ClientMessages.PARSER.parseFrom(data);
        }
        
        public static ClientMessages parseFrom(final byte[] data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ClientMessages)ClientMessages.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static ClientMessages parseFrom(final InputStream input) throws IOException {
            return (ClientMessages)GeneratedMessageV3.parseWithIOException((Parser)ClientMessages.PARSER, input);
        }
        
        public static ClientMessages parseFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ClientMessages)GeneratedMessageV3.parseWithIOException((Parser)ClientMessages.PARSER, input, extensionRegistry);
        }
        
        public static ClientMessages parseDelimitedFrom(final InputStream input) throws IOException {
            return (ClientMessages)GeneratedMessageV3.parseDelimitedWithIOException((Parser)ClientMessages.PARSER, input);
        }
        
        public static ClientMessages parseDelimitedFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ClientMessages)GeneratedMessageV3.parseDelimitedWithIOException((Parser)ClientMessages.PARSER, input, extensionRegistry);
        }
        
        public static ClientMessages parseFrom(final CodedInputStream input) throws IOException {
            return (ClientMessages)GeneratedMessageV3.parseWithIOException((Parser)ClientMessages.PARSER, input);
        }
        
        public static ClientMessages parseFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ClientMessages)GeneratedMessageV3.parseWithIOException((Parser)ClientMessages.PARSER, input, extensionRegistry);
        }
        
        public Builder newBuilderForType() {
            return newBuilder();
        }
        
        public static Builder newBuilder() {
            return ClientMessages.DEFAULT_INSTANCE.toBuilder();
        }
        
        public static Builder newBuilder(final ClientMessages prototype) {
            return ClientMessages.DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }
        
        public Builder toBuilder() {
            return (this == ClientMessages.DEFAULT_INSTANCE) ? new Builder() : new Builder().mergeFrom(this);
        }
        
        protected Builder newBuilderForType(final GeneratedMessageV3.BuilderParent parent) {
            final Builder builder = new Builder(parent);
            return builder;
        }
        
        public static ClientMessages getDefaultInstance() {
            return ClientMessages.DEFAULT_INSTANCE;
        }
        
        public static Parser<ClientMessages> parser() {
            return ClientMessages.PARSER;
        }
        
        public Parser<ClientMessages> getParserForType() {
            return ClientMessages.PARSER;
        }
        
        public ClientMessages getDefaultInstanceForType() {
            return ClientMessages.DEFAULT_INSTANCE;
        }
        
        static {
            DEFAULT_INSTANCE = new ClientMessages();
            PARSER = (Parser)new AbstractParser<ClientMessages>() {
                public ClientMessages parsePartialFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new ClientMessages(input, extensionRegistry);
                }
            };
        }
        
        public enum Type implements ProtocolMessageEnum
        {
            CON_CAPABILITIES_GET(1), 
            CON_CAPABILITIES_SET(2), 
            CON_CLOSE(3), 
            SESS_AUTHENTICATE_START(4), 
            SESS_AUTHENTICATE_CONTINUE(5), 
            SESS_RESET(6), 
            SESS_CLOSE(7), 
            SQL_STMT_EXECUTE(12), 
            CRUD_FIND(17), 
            CRUD_INSERT(18), 
            CRUD_UPDATE(19), 
            CRUD_DELETE(20), 
            EXPECT_OPEN(24), 
            EXPECT_CLOSE(25), 
            CRUD_CREATE_VIEW(30), 
            CRUD_MODIFY_VIEW(31), 
            CRUD_DROP_VIEW(32);
            
            public static final int CON_CAPABILITIES_GET_VALUE = 1;
            public static final int CON_CAPABILITIES_SET_VALUE = 2;
            public static final int CON_CLOSE_VALUE = 3;
            public static final int SESS_AUTHENTICATE_START_VALUE = 4;
            public static final int SESS_AUTHENTICATE_CONTINUE_VALUE = 5;
            public static final int SESS_RESET_VALUE = 6;
            public static final int SESS_CLOSE_VALUE = 7;
            public static final int SQL_STMT_EXECUTE_VALUE = 12;
            public static final int CRUD_FIND_VALUE = 17;
            public static final int CRUD_INSERT_VALUE = 18;
            public static final int CRUD_UPDATE_VALUE = 19;
            public static final int CRUD_DELETE_VALUE = 20;
            public static final int EXPECT_OPEN_VALUE = 24;
            public static final int EXPECT_CLOSE_VALUE = 25;
            public static final int CRUD_CREATE_VIEW_VALUE = 30;
            public static final int CRUD_MODIFY_VIEW_VALUE = 31;
            public static final int CRUD_DROP_VIEW_VALUE = 32;
            private static final Internal.EnumLiteMap<Type> internalValueMap;
            private static final Type[] VALUES;
            private final int value;
            
            public final int getNumber() {
                return this.value;
            }
            
            @Deprecated
            public static Type valueOf(final int value) {
                return forNumber(value);
            }
            
            public static Type forNumber(final int value) {
                switch (value) {
                    case 1: {
                        return Type.CON_CAPABILITIES_GET;
                    }
                    case 2: {
                        return Type.CON_CAPABILITIES_SET;
                    }
                    case 3: {
                        return Type.CON_CLOSE;
                    }
                    case 4: {
                        return Type.SESS_AUTHENTICATE_START;
                    }
                    case 5: {
                        return Type.SESS_AUTHENTICATE_CONTINUE;
                    }
                    case 6: {
                        return Type.SESS_RESET;
                    }
                    case 7: {
                        return Type.SESS_CLOSE;
                    }
                    case 12: {
                        return Type.SQL_STMT_EXECUTE;
                    }
                    case 17: {
                        return Type.CRUD_FIND;
                    }
                    case 18: {
                        return Type.CRUD_INSERT;
                    }
                    case 19: {
                        return Type.CRUD_UPDATE;
                    }
                    case 20: {
                        return Type.CRUD_DELETE;
                    }
                    case 24: {
                        return Type.EXPECT_OPEN;
                    }
                    case 25: {
                        return Type.EXPECT_CLOSE;
                    }
                    case 30: {
                        return Type.CRUD_CREATE_VIEW;
                    }
                    case 31: {
                        return Type.CRUD_MODIFY_VIEW;
                    }
                    case 32: {
                        return Type.CRUD_DROP_VIEW;
                    }
                    default: {
                        return null;
                    }
                }
            }
            
            public static Internal.EnumLiteMap<Type> internalGetValueMap() {
                return Type.internalValueMap;
            }
            
            public final Descriptors.EnumValueDescriptor getValueDescriptor() {
                return getDescriptor().getValues().get(this.ordinal());
            }
            
            public final Descriptors.EnumDescriptor getDescriptorForType() {
                return getDescriptor();
            }
            
            public static final Descriptors.EnumDescriptor getDescriptor() {
                return ClientMessages.getDescriptor().getEnumTypes().get(0);
            }
            
            public static Type valueOf(final Descriptors.EnumValueDescriptor desc) {
                if (desc.getType() != getDescriptor()) {
                    throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
                }
                return Type.VALUES[desc.getIndex()];
            }
            
            private Type(final int value) {
                this.value = value;
            }
            
            static {
                internalValueMap = (Internal.EnumLiteMap)new Internal.EnumLiteMap<Type>() {
                    public Type findValueByNumber(final int number) {
                        return Type.forNumber(number);
                    }
                };
                VALUES = values();
            }
        }
        
        public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements ClientMessagesOrBuilder
        {
            public static final Descriptors.Descriptor getDescriptor() {
                return Mysqlx.internal_static_Mysqlx_ClientMessages_descriptor;
            }
            
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return Mysqlx.internal_static_Mysqlx_ClientMessages_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)ClientMessages.class, (Class)Builder.class);
            }
            
            private Builder() {
                this.maybeForceBuilderInitialization();
            }
            
            private Builder(final GeneratedMessageV3.BuilderParent parent) {
                super(parent);
                this.maybeForceBuilderInitialization();
            }
            
            private void maybeForceBuilderInitialization() {
                if (ClientMessages.alwaysUseFieldBuilders) {}
            }
            
            public Builder clear() {
                super.clear();
                return this;
            }
            
            public Descriptors.Descriptor getDescriptorForType() {
                return Mysqlx.internal_static_Mysqlx_ClientMessages_descriptor;
            }
            
            public ClientMessages getDefaultInstanceForType() {
                return ClientMessages.getDefaultInstance();
            }
            
            public ClientMessages build() {
                final ClientMessages result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw newUninitializedMessageException((Message)result);
                }
                return result;
            }
            
            public ClientMessages buildPartial() {
                final ClientMessages result = new ClientMessages((GeneratedMessageV3.Builder)this);
                this.onBuilt();
                return result;
            }
            
            public Builder clone() {
                return (Builder)super.clone();
            }
            
            public Builder setField(final Descriptors.FieldDescriptor field, final Object value) {
                return (Builder)super.setField(field, value);
            }
            
            public Builder clearField(final Descriptors.FieldDescriptor field) {
                return (Builder)super.clearField(field);
            }
            
            public Builder clearOneof(final Descriptors.OneofDescriptor oneof) {
                return (Builder)super.clearOneof(oneof);
            }
            
            public Builder setRepeatedField(final Descriptors.FieldDescriptor field, final int index, final Object value) {
                return (Builder)super.setRepeatedField(field, index, value);
            }
            
            public Builder addRepeatedField(final Descriptors.FieldDescriptor field, final Object value) {
                return (Builder)super.addRepeatedField(field, value);
            }
            
            public Builder mergeFrom(final Message other) {
                if (other instanceof ClientMessages) {
                    return this.mergeFrom((ClientMessages)other);
                }
                super.mergeFrom(other);
                return this;
            }
            
            public Builder mergeFrom(final ClientMessages other) {
                if (other == ClientMessages.getDefaultInstance()) {
                    return this;
                }
                this.mergeUnknownFields(other.unknownFields);
                this.onChanged();
                return this;
            }
            
            public final boolean isInitialized() {
                return true;
            }
            
            public Builder mergeFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
                ClientMessages parsedMessage = null;
                try {
                    parsedMessage = (ClientMessages)ClientMessages.PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (ClientMessages)e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                }
                finally {
                    if (parsedMessage != null) {
                        this.mergeFrom(parsedMessage);
                    }
                }
                return this;
            }
            
            public final Builder setUnknownFields(final UnknownFieldSet unknownFields) {
                return (Builder)super.setUnknownFields(unknownFields);
            }
            
            public final Builder mergeUnknownFields(final UnknownFieldSet unknownFields) {
                return (Builder)super.mergeUnknownFields(unknownFields);
            }
        }
    }
    
    public static final class ServerMessages extends GeneratedMessageV3 implements ServerMessagesOrBuilder
    {
        private static final long serialVersionUID = 0L;
        private byte memoizedIsInitialized;
        private static final ServerMessages DEFAULT_INSTANCE;
        @Deprecated
        public static final Parser<ServerMessages> PARSER;
        
        private ServerMessages(final GeneratedMessageV3.Builder<?> builder) {
            super((GeneratedMessageV3.Builder)builder);
            this.memoizedIsInitialized = -1;
        }
        
        private ServerMessages() {
            this.memoizedIsInitialized = -1;
        }
        
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }
        
        private ServerMessages(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this();
            if (extensionRegistry == null) {
                throw new NullPointerException();
            }
            final UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
            try {
                boolean done = false;
                while (!done) {
                    final int tag = input.readTag();
                    switch (tag) {
                        case 0: {
                            done = true;
                            continue;
                        }
                        default: {
                            if (!this.parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                                done = true;
                                continue;
                            }
                            continue;
                        }
                    }
                }
            }
            catch (InvalidProtocolBufferException e) {
                throw e.setUnfinishedMessage((MessageLite)this);
            }
            catch (IOException e2) {
                throw new InvalidProtocolBufferException(e2).setUnfinishedMessage((MessageLite)this);
            }
            finally {
                this.unknownFields = unknownFields.build();
                this.makeExtensionsImmutable();
            }
        }
        
        public static final Descriptors.Descriptor getDescriptor() {
            return Mysqlx.internal_static_Mysqlx_ServerMessages_descriptor;
        }
        
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return Mysqlx.internal_static_Mysqlx_ServerMessages_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)ServerMessages.class, (Class)Builder.class);
        }
        
        public final boolean isInitialized() {
            final byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            this.memoizedIsInitialized = 1;
            return true;
        }
        
        public void writeTo(final CodedOutputStream output) throws IOException {
            this.unknownFields.writeTo(output);
        }
        
        public int getSerializedSize() {
            int size = this.memoizedSize;
            if (size != -1) {
                return size;
            }
            size = 0;
            size += this.unknownFields.getSerializedSize();
            return this.memoizedSize = size;
        }
        
        public boolean equals(final Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof ServerMessages)) {
                return super.equals(obj);
            }
            final ServerMessages other = (ServerMessages)obj;
            boolean result = true;
            result = (result && this.unknownFields.equals((Object)other.unknownFields));
            return result;
        }
        
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + getDescriptor().hashCode();
            hash = 29 * hash + this.unknownFields.hashCode();
            return this.memoizedHashCode = hash;
        }
        
        public static ServerMessages parseFrom(final ByteBuffer data) throws InvalidProtocolBufferException {
            return (ServerMessages)ServerMessages.PARSER.parseFrom(data);
        }
        
        public static ServerMessages parseFrom(final ByteBuffer data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ServerMessages)ServerMessages.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static ServerMessages parseFrom(final ByteString data) throws InvalidProtocolBufferException {
            return (ServerMessages)ServerMessages.PARSER.parseFrom(data);
        }
        
        public static ServerMessages parseFrom(final ByteString data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ServerMessages)ServerMessages.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static ServerMessages parseFrom(final byte[] data) throws InvalidProtocolBufferException {
            return (ServerMessages)ServerMessages.PARSER.parseFrom(data);
        }
        
        public static ServerMessages parseFrom(final byte[] data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ServerMessages)ServerMessages.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static ServerMessages parseFrom(final InputStream input) throws IOException {
            return (ServerMessages)GeneratedMessageV3.parseWithIOException((Parser)ServerMessages.PARSER, input);
        }
        
        public static ServerMessages parseFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ServerMessages)GeneratedMessageV3.parseWithIOException((Parser)ServerMessages.PARSER, input, extensionRegistry);
        }
        
        public static ServerMessages parseDelimitedFrom(final InputStream input) throws IOException {
            return (ServerMessages)GeneratedMessageV3.parseDelimitedWithIOException((Parser)ServerMessages.PARSER, input);
        }
        
        public static ServerMessages parseDelimitedFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ServerMessages)GeneratedMessageV3.parseDelimitedWithIOException((Parser)ServerMessages.PARSER, input, extensionRegistry);
        }
        
        public static ServerMessages parseFrom(final CodedInputStream input) throws IOException {
            return (ServerMessages)GeneratedMessageV3.parseWithIOException((Parser)ServerMessages.PARSER, input);
        }
        
        public static ServerMessages parseFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ServerMessages)GeneratedMessageV3.parseWithIOException((Parser)ServerMessages.PARSER, input, extensionRegistry);
        }
        
        public Builder newBuilderForType() {
            return newBuilder();
        }
        
        public static Builder newBuilder() {
            return ServerMessages.DEFAULT_INSTANCE.toBuilder();
        }
        
        public static Builder newBuilder(final ServerMessages prototype) {
            return ServerMessages.DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }
        
        public Builder toBuilder() {
            return (this == ServerMessages.DEFAULT_INSTANCE) ? new Builder() : new Builder().mergeFrom(this);
        }
        
        protected Builder newBuilderForType(final GeneratedMessageV3.BuilderParent parent) {
            final Builder builder = new Builder(parent);
            return builder;
        }
        
        public static ServerMessages getDefaultInstance() {
            return ServerMessages.DEFAULT_INSTANCE;
        }
        
        public static Parser<ServerMessages> parser() {
            return ServerMessages.PARSER;
        }
        
        public Parser<ServerMessages> getParserForType() {
            return ServerMessages.PARSER;
        }
        
        public ServerMessages getDefaultInstanceForType() {
            return ServerMessages.DEFAULT_INSTANCE;
        }
        
        static {
            DEFAULT_INSTANCE = new ServerMessages();
            PARSER = (Parser)new AbstractParser<ServerMessages>() {
                public ServerMessages parsePartialFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new ServerMessages(input, extensionRegistry);
                }
            };
        }
        
        public enum Type implements ProtocolMessageEnum
        {
            OK(0), 
            ERROR(1), 
            CONN_CAPABILITIES(2), 
            SESS_AUTHENTICATE_CONTINUE(3), 
            SESS_AUTHENTICATE_OK(4), 
            NOTICE(11), 
            RESULTSET_COLUMN_META_DATA(12), 
            RESULTSET_ROW(13), 
            RESULTSET_FETCH_DONE(14), 
            RESULTSET_FETCH_SUSPENDED(15), 
            RESULTSET_FETCH_DONE_MORE_RESULTSETS(16), 
            SQL_STMT_EXECUTE_OK(17), 
            RESULTSET_FETCH_DONE_MORE_OUT_PARAMS(18);
            
            public static final int OK_VALUE = 0;
            public static final int ERROR_VALUE = 1;
            public static final int CONN_CAPABILITIES_VALUE = 2;
            public static final int SESS_AUTHENTICATE_CONTINUE_VALUE = 3;
            public static final int SESS_AUTHENTICATE_OK_VALUE = 4;
            public static final int NOTICE_VALUE = 11;
            public static final int RESULTSET_COLUMN_META_DATA_VALUE = 12;
            public static final int RESULTSET_ROW_VALUE = 13;
            public static final int RESULTSET_FETCH_DONE_VALUE = 14;
            public static final int RESULTSET_FETCH_SUSPENDED_VALUE = 15;
            public static final int RESULTSET_FETCH_DONE_MORE_RESULTSETS_VALUE = 16;
            public static final int SQL_STMT_EXECUTE_OK_VALUE = 17;
            public static final int RESULTSET_FETCH_DONE_MORE_OUT_PARAMS_VALUE = 18;
            private static final Internal.EnumLiteMap<Type> internalValueMap;
            private static final Type[] VALUES;
            private final int value;
            
            public final int getNumber() {
                return this.value;
            }
            
            @Deprecated
            public static Type valueOf(final int value) {
                return forNumber(value);
            }
            
            public static Type forNumber(final int value) {
                switch (value) {
                    case 0: {
                        return Type.OK;
                    }
                    case 1: {
                        return Type.ERROR;
                    }
                    case 2: {
                        return Type.CONN_CAPABILITIES;
                    }
                    case 3: {
                        return Type.SESS_AUTHENTICATE_CONTINUE;
                    }
                    case 4: {
                        return Type.SESS_AUTHENTICATE_OK;
                    }
                    case 11: {
                        return Type.NOTICE;
                    }
                    case 12: {
                        return Type.RESULTSET_COLUMN_META_DATA;
                    }
                    case 13: {
                        return Type.RESULTSET_ROW;
                    }
                    case 14: {
                        return Type.RESULTSET_FETCH_DONE;
                    }
                    case 15: {
                        return Type.RESULTSET_FETCH_SUSPENDED;
                    }
                    case 16: {
                        return Type.RESULTSET_FETCH_DONE_MORE_RESULTSETS;
                    }
                    case 17: {
                        return Type.SQL_STMT_EXECUTE_OK;
                    }
                    case 18: {
                        return Type.RESULTSET_FETCH_DONE_MORE_OUT_PARAMS;
                    }
                    default: {
                        return null;
                    }
                }
            }
            
            public static Internal.EnumLiteMap<Type> internalGetValueMap() {
                return Type.internalValueMap;
            }
            
            public final Descriptors.EnumValueDescriptor getValueDescriptor() {
                return getDescriptor().getValues().get(this.ordinal());
            }
            
            public final Descriptors.EnumDescriptor getDescriptorForType() {
                return getDescriptor();
            }
            
            public static final Descriptors.EnumDescriptor getDescriptor() {
                return ServerMessages.getDescriptor().getEnumTypes().get(0);
            }
            
            public static Type valueOf(final Descriptors.EnumValueDescriptor desc) {
                if (desc.getType() != getDescriptor()) {
                    throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
                }
                return Type.VALUES[desc.getIndex()];
            }
            
            private Type(final int value) {
                this.value = value;
            }
            
            static {
                internalValueMap = (Internal.EnumLiteMap)new Internal.EnumLiteMap<Type>() {
                    public Type findValueByNumber(final int number) {
                        return Type.forNumber(number);
                    }
                };
                VALUES = values();
            }
        }
        
        public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements ServerMessagesOrBuilder
        {
            public static final Descriptors.Descriptor getDescriptor() {
                return Mysqlx.internal_static_Mysqlx_ServerMessages_descriptor;
            }
            
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return Mysqlx.internal_static_Mysqlx_ServerMessages_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)ServerMessages.class, (Class)Builder.class);
            }
            
            private Builder() {
                this.maybeForceBuilderInitialization();
            }
            
            private Builder(final GeneratedMessageV3.BuilderParent parent) {
                super(parent);
                this.maybeForceBuilderInitialization();
            }
            
            private void maybeForceBuilderInitialization() {
                if (ServerMessages.alwaysUseFieldBuilders) {}
            }
            
            public Builder clear() {
                super.clear();
                return this;
            }
            
            public Descriptors.Descriptor getDescriptorForType() {
                return Mysqlx.internal_static_Mysqlx_ServerMessages_descriptor;
            }
            
            public ServerMessages getDefaultInstanceForType() {
                return ServerMessages.getDefaultInstance();
            }
            
            public ServerMessages build() {
                final ServerMessages result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw newUninitializedMessageException((Message)result);
                }
                return result;
            }
            
            public ServerMessages buildPartial() {
                final ServerMessages result = new ServerMessages((GeneratedMessageV3.Builder)this);
                this.onBuilt();
                return result;
            }
            
            public Builder clone() {
                return (Builder)super.clone();
            }
            
            public Builder setField(final Descriptors.FieldDescriptor field, final Object value) {
                return (Builder)super.setField(field, value);
            }
            
            public Builder clearField(final Descriptors.FieldDescriptor field) {
                return (Builder)super.clearField(field);
            }
            
            public Builder clearOneof(final Descriptors.OneofDescriptor oneof) {
                return (Builder)super.clearOneof(oneof);
            }
            
            public Builder setRepeatedField(final Descriptors.FieldDescriptor field, final int index, final Object value) {
                return (Builder)super.setRepeatedField(field, index, value);
            }
            
            public Builder addRepeatedField(final Descriptors.FieldDescriptor field, final Object value) {
                return (Builder)super.addRepeatedField(field, value);
            }
            
            public Builder mergeFrom(final Message other) {
                if (other instanceof ServerMessages) {
                    return this.mergeFrom((ServerMessages)other);
                }
                super.mergeFrom(other);
                return this;
            }
            
            public Builder mergeFrom(final ServerMessages other) {
                if (other == ServerMessages.getDefaultInstance()) {
                    return this;
                }
                this.mergeUnknownFields(other.unknownFields);
                this.onChanged();
                return this;
            }
            
            public final boolean isInitialized() {
                return true;
            }
            
            public Builder mergeFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
                ServerMessages parsedMessage = null;
                try {
                    parsedMessage = (ServerMessages)ServerMessages.PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (ServerMessages)e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                }
                finally {
                    if (parsedMessage != null) {
                        this.mergeFrom(parsedMessage);
                    }
                }
                return this;
            }
            
            public final Builder setUnknownFields(final UnknownFieldSet unknownFields) {
                return (Builder)super.setUnknownFields(unknownFields);
            }
            
            public final Builder mergeUnknownFields(final UnknownFieldSet unknownFields) {
                return (Builder)super.mergeUnknownFields(unknownFields);
            }
        }
    }
    
    public static final class Ok extends GeneratedMessageV3 implements OkOrBuilder
    {
        private static final long serialVersionUID = 0L;
        private int bitField0_;
        public static final int MSG_FIELD_NUMBER = 1;
        private volatile Object msg_;
        private byte memoizedIsInitialized;
        private static final Ok DEFAULT_INSTANCE;
        @Deprecated
        public static final Parser<Ok> PARSER;
        
        private Ok(final GeneratedMessageV3.Builder<?> builder) {
            super((GeneratedMessageV3.Builder)builder);
            this.memoizedIsInitialized = -1;
        }
        
        private Ok() {
            this.memoizedIsInitialized = -1;
            this.msg_ = "";
        }
        
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }
        
        private Ok(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this();
            if (extensionRegistry == null) {
                throw new NullPointerException();
            }
            final int mutable_bitField0_ = 0;
            final UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
            try {
                boolean done = false;
                while (!done) {
                    final int tag = input.readTag();
                    switch (tag) {
                        case 0: {
                            done = true;
                            continue;
                        }
                        case 10: {
                            final ByteString bs = input.readBytes();
                            this.bitField0_ |= 0x1;
                            this.msg_ = bs;
                            continue;
                        }
                        default: {
                            if (!this.parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                                done = true;
                                continue;
                            }
                            continue;
                        }
                    }
                }
            }
            catch (InvalidProtocolBufferException e) {
                throw e.setUnfinishedMessage((MessageLite)this);
            }
            catch (IOException e2) {
                throw new InvalidProtocolBufferException(e2).setUnfinishedMessage((MessageLite)this);
            }
            finally {
                this.unknownFields = unknownFields.build();
                this.makeExtensionsImmutable();
            }
        }
        
        public static final Descriptors.Descriptor getDescriptor() {
            return Mysqlx.internal_static_Mysqlx_Ok_descriptor;
        }
        
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return Mysqlx.internal_static_Mysqlx_Ok_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)Ok.class, (Class)Builder.class);
        }
        
        public boolean hasMsg() {
            return (this.bitField0_ & 0x1) == 0x1;
        }
        
        public String getMsg() {
            final Object ref = this.msg_;
            if (ref instanceof String) {
                return (String)ref;
            }
            final ByteString bs = (ByteString)ref;
            final String s = bs.toStringUtf8();
            if (bs.isValidUtf8()) {
                this.msg_ = s;
            }
            return s;
        }
        
        public ByteString getMsgBytes() {
            final Object ref = this.msg_;
            if (ref instanceof String) {
                final ByteString b = ByteString.copyFromUtf8((String)ref);
                return (ByteString)(this.msg_ = b);
            }
            return (ByteString)ref;
        }
        
        public final boolean isInitialized() {
            final byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            this.memoizedIsInitialized = 1;
            return true;
        }
        
        public void writeTo(final CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 0x1) == 0x1) {
                GeneratedMessageV3.writeString(output, 1, this.msg_);
            }
            this.unknownFields.writeTo(output);
        }
        
        public int getSerializedSize() {
            int size = this.memoizedSize;
            if (size != -1) {
                return size;
            }
            size = 0;
            if ((this.bitField0_ & 0x1) == 0x1) {
                size += GeneratedMessageV3.computeStringSize(1, this.msg_);
            }
            size += this.unknownFields.getSerializedSize();
            return this.memoizedSize = size;
        }
        
        public boolean equals(final Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Ok)) {
                return super.equals(obj);
            }
            final Ok other = (Ok)obj;
            boolean result = true;
            result = (result && this.hasMsg() == other.hasMsg());
            if (this.hasMsg()) {
                result = (result && this.getMsg().equals(other.getMsg()));
            }
            result = (result && this.unknownFields.equals((Object)other.unknownFields));
            return result;
        }
        
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + getDescriptor().hashCode();
            if (this.hasMsg()) {
                hash = 37 * hash + 1;
                hash = 53 * hash + this.getMsg().hashCode();
            }
            hash = 29 * hash + this.unknownFields.hashCode();
            return this.memoizedHashCode = hash;
        }
        
        public static Ok parseFrom(final ByteBuffer data) throws InvalidProtocolBufferException {
            return (Ok)Ok.PARSER.parseFrom(data);
        }
        
        public static Ok parseFrom(final ByteBuffer data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Ok)Ok.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Ok parseFrom(final ByteString data) throws InvalidProtocolBufferException {
            return (Ok)Ok.PARSER.parseFrom(data);
        }
        
        public static Ok parseFrom(final ByteString data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Ok)Ok.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Ok parseFrom(final byte[] data) throws InvalidProtocolBufferException {
            return (Ok)Ok.PARSER.parseFrom(data);
        }
        
        public static Ok parseFrom(final byte[] data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Ok)Ok.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Ok parseFrom(final InputStream input) throws IOException {
            return (Ok)GeneratedMessageV3.parseWithIOException((Parser)Ok.PARSER, input);
        }
        
        public static Ok parseFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Ok)GeneratedMessageV3.parseWithIOException((Parser)Ok.PARSER, input, extensionRegistry);
        }
        
        public static Ok parseDelimitedFrom(final InputStream input) throws IOException {
            return (Ok)GeneratedMessageV3.parseDelimitedWithIOException((Parser)Ok.PARSER, input);
        }
        
        public static Ok parseDelimitedFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Ok)GeneratedMessageV3.parseDelimitedWithIOException((Parser)Ok.PARSER, input, extensionRegistry);
        }
        
        public static Ok parseFrom(final CodedInputStream input) throws IOException {
            return (Ok)GeneratedMessageV3.parseWithIOException((Parser)Ok.PARSER, input);
        }
        
        public static Ok parseFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Ok)GeneratedMessageV3.parseWithIOException((Parser)Ok.PARSER, input, extensionRegistry);
        }
        
        public Builder newBuilderForType() {
            return newBuilder();
        }
        
        public static Builder newBuilder() {
            return Ok.DEFAULT_INSTANCE.toBuilder();
        }
        
        public static Builder newBuilder(final Ok prototype) {
            return Ok.DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }
        
        public Builder toBuilder() {
            return (this == Ok.DEFAULT_INSTANCE) ? new Builder() : new Builder().mergeFrom(this);
        }
        
        protected Builder newBuilderForType(final GeneratedMessageV3.BuilderParent parent) {
            final Builder builder = new Builder(parent);
            return builder;
        }
        
        public static Ok getDefaultInstance() {
            return Ok.DEFAULT_INSTANCE;
        }
        
        public static Parser<Ok> parser() {
            return Ok.PARSER;
        }
        
        public Parser<Ok> getParserForType() {
            return Ok.PARSER;
        }
        
        public Ok getDefaultInstanceForType() {
            return Ok.DEFAULT_INSTANCE;
        }
        
        static {
            DEFAULT_INSTANCE = new Ok();
            PARSER = (Parser)new AbstractParser<Ok>() {
                public Ok parsePartialFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new Ok(input, extensionRegistry);
                }
            };
        }
        
        public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements OkOrBuilder
        {
            private int bitField0_;
            private Object msg_;
            
            public static final Descriptors.Descriptor getDescriptor() {
                return Mysqlx.internal_static_Mysqlx_Ok_descriptor;
            }
            
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return Mysqlx.internal_static_Mysqlx_Ok_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)Ok.class, (Class)Builder.class);
            }
            
            private Builder() {
                this.msg_ = "";
                this.maybeForceBuilderInitialization();
            }
            
            private Builder(final GeneratedMessageV3.BuilderParent parent) {
                super(parent);
                this.msg_ = "";
                this.maybeForceBuilderInitialization();
            }
            
            private void maybeForceBuilderInitialization() {
                if (Ok.alwaysUseFieldBuilders) {}
            }
            
            public Builder clear() {
                super.clear();
                this.msg_ = "";
                this.bitField0_ &= 0xFFFFFFFE;
                return this;
            }
            
            public Descriptors.Descriptor getDescriptorForType() {
                return Mysqlx.internal_static_Mysqlx_Ok_descriptor;
            }
            
            public Ok getDefaultInstanceForType() {
                return Ok.getDefaultInstance();
            }
            
            public Ok build() {
                final Ok result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw newUninitializedMessageException((Message)result);
                }
                return result;
            }
            
            public Ok buildPartial() {
                final Ok result = new Ok((GeneratedMessageV3.Builder)this);
                final int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 0x1) == 0x1) {
                    to_bitField0_ |= 0x1;
                }
                result.msg_ = this.msg_;
                result.bitField0_ = to_bitField0_;
                this.onBuilt();
                return result;
            }
            
            public Builder clone() {
                return (Builder)super.clone();
            }
            
            public Builder setField(final Descriptors.FieldDescriptor field, final Object value) {
                return (Builder)super.setField(field, value);
            }
            
            public Builder clearField(final Descriptors.FieldDescriptor field) {
                return (Builder)super.clearField(field);
            }
            
            public Builder clearOneof(final Descriptors.OneofDescriptor oneof) {
                return (Builder)super.clearOneof(oneof);
            }
            
            public Builder setRepeatedField(final Descriptors.FieldDescriptor field, final int index, final Object value) {
                return (Builder)super.setRepeatedField(field, index, value);
            }
            
            public Builder addRepeatedField(final Descriptors.FieldDescriptor field, final Object value) {
                return (Builder)super.addRepeatedField(field, value);
            }
            
            public Builder mergeFrom(final Message other) {
                if (other instanceof Ok) {
                    return this.mergeFrom((Ok)other);
                }
                super.mergeFrom(other);
                return this;
            }
            
            public Builder mergeFrom(final Ok other) {
                if (other == Ok.getDefaultInstance()) {
                    return this;
                }
                if (other.hasMsg()) {
                    this.bitField0_ |= 0x1;
                    this.msg_ = other.msg_;
                    this.onChanged();
                }
                this.mergeUnknownFields(other.unknownFields);
                this.onChanged();
                return this;
            }
            
            public final boolean isInitialized() {
                return true;
            }
            
            public Builder mergeFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
                Ok parsedMessage = null;
                try {
                    parsedMessage = (Ok)Ok.PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (Ok)e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                }
                finally {
                    if (parsedMessage != null) {
                        this.mergeFrom(parsedMessage);
                    }
                }
                return this;
            }
            
            public boolean hasMsg() {
                return (this.bitField0_ & 0x1) == 0x1;
            }
            
            public String getMsg() {
                final Object ref = this.msg_;
                if (!(ref instanceof String)) {
                    final ByteString bs = (ByteString)ref;
                    final String s = bs.toStringUtf8();
                    if (bs.isValidUtf8()) {
                        this.msg_ = s;
                    }
                    return s;
                }
                return (String)ref;
            }
            
            public ByteString getMsgBytes() {
                final Object ref = this.msg_;
                if (ref instanceof String) {
                    final ByteString b = ByteString.copyFromUtf8((String)ref);
                    return (ByteString)(this.msg_ = b);
                }
                return (ByteString)ref;
            }
            
            public Builder setMsg(final String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 0x1;
                this.msg_ = value;
                this.onChanged();
                return this;
            }
            
            public Builder clearMsg() {
                this.bitField0_ &= 0xFFFFFFFE;
                this.msg_ = Ok.getDefaultInstance().getMsg();
                this.onChanged();
                return this;
            }
            
            public Builder setMsgBytes(final ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 0x1;
                this.msg_ = value;
                this.onChanged();
                return this;
            }
            
            public final Builder setUnknownFields(final UnknownFieldSet unknownFields) {
                return (Builder)super.setUnknownFields(unknownFields);
            }
            
            public final Builder mergeUnknownFields(final UnknownFieldSet unknownFields) {
                return (Builder)super.mergeUnknownFields(unknownFields);
            }
        }
    }
    
    public static final class Error extends GeneratedMessageV3 implements ErrorOrBuilder
    {
        private static final long serialVersionUID = 0L;
        private int bitField0_;
        public static final int SEVERITY_FIELD_NUMBER = 1;
        private int severity_;
        public static final int CODE_FIELD_NUMBER = 2;
        private int code_;
        public static final int SQL_STATE_FIELD_NUMBER = 4;
        private volatile Object sqlState_;
        public static final int MSG_FIELD_NUMBER = 3;
        private volatile Object msg_;
        private byte memoizedIsInitialized;
        private static final Error DEFAULT_INSTANCE;
        @Deprecated
        public static final Parser<Error> PARSER;
        
        private Error(final GeneratedMessageV3.Builder<?> builder) {
            super((GeneratedMessageV3.Builder)builder);
            this.memoizedIsInitialized = -1;
        }
        
        private Error() {
            this.memoizedIsInitialized = -1;
            this.severity_ = 0;
            this.code_ = 0;
            this.sqlState_ = "";
            this.msg_ = "";
        }
        
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }
        
        private Error(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this();
            if (extensionRegistry == null) {
                throw new NullPointerException();
            }
            final int mutable_bitField0_ = 0;
            final UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
            try {
                boolean done = false;
                while (!done) {
                    final int tag = input.readTag();
                    switch (tag) {
                        case 0: {
                            done = true;
                            continue;
                        }
                        case 8: {
                            final int rawValue = input.readEnum();
                            final Severity value = Severity.valueOf(rawValue);
                            if (value == null) {
                                unknownFields.mergeVarintField(1, rawValue);
                                continue;
                            }
                            this.bitField0_ |= 0x1;
                            this.severity_ = rawValue;
                            continue;
                        }
                        case 16: {
                            this.bitField0_ |= 0x2;
                            this.code_ = input.readUInt32();
                            continue;
                        }
                        case 26: {
                            final ByteString bs = input.readBytes();
                            this.bitField0_ |= 0x8;
                            this.msg_ = bs;
                            continue;
                        }
                        case 34: {
                            final ByteString bs = input.readBytes();
                            this.bitField0_ |= 0x4;
                            this.sqlState_ = bs;
                            continue;
                        }
                        default: {
                            if (!this.parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                                done = true;
                                continue;
                            }
                            continue;
                        }
                    }
                }
            }
            catch (InvalidProtocolBufferException e) {
                throw e.setUnfinishedMessage((MessageLite)this);
            }
            catch (IOException e2) {
                throw new InvalidProtocolBufferException(e2).setUnfinishedMessage((MessageLite)this);
            }
            finally {
                this.unknownFields = unknownFields.build();
                this.makeExtensionsImmutable();
            }
        }
        
        public static final Descriptors.Descriptor getDescriptor() {
            return Mysqlx.internal_static_Mysqlx_Error_descriptor;
        }
        
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return Mysqlx.internal_static_Mysqlx_Error_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)Error.class, (Class)Builder.class);
        }
        
        public boolean hasSeverity() {
            return (this.bitField0_ & 0x1) == 0x1;
        }
        
        public Severity getSeverity() {
            final Severity result = Severity.valueOf(this.severity_);
            return (result == null) ? Severity.ERROR : result;
        }
        
        public boolean hasCode() {
            return (this.bitField0_ & 0x2) == 0x2;
        }
        
        public int getCode() {
            return this.code_;
        }
        
        public boolean hasSqlState() {
            return (this.bitField0_ & 0x4) == 0x4;
        }
        
        public String getSqlState() {
            final Object ref = this.sqlState_;
            if (ref instanceof String) {
                return (String)ref;
            }
            final ByteString bs = (ByteString)ref;
            final String s = bs.toStringUtf8();
            if (bs.isValidUtf8()) {
                this.sqlState_ = s;
            }
            return s;
        }
        
        public ByteString getSqlStateBytes() {
            final Object ref = this.sqlState_;
            if (ref instanceof String) {
                final ByteString b = ByteString.copyFromUtf8((String)ref);
                return (ByteString)(this.sqlState_ = b);
            }
            return (ByteString)ref;
        }
        
        public boolean hasMsg() {
            return (this.bitField0_ & 0x8) == 0x8;
        }
        
        public String getMsg() {
            final Object ref = this.msg_;
            if (ref instanceof String) {
                return (String)ref;
            }
            final ByteString bs = (ByteString)ref;
            final String s = bs.toStringUtf8();
            if (bs.isValidUtf8()) {
                this.msg_ = s;
            }
            return s;
        }
        
        public ByteString getMsgBytes() {
            final Object ref = this.msg_;
            if (ref instanceof String) {
                final ByteString b = ByteString.copyFromUtf8((String)ref);
                return (ByteString)(this.msg_ = b);
            }
            return (ByteString)ref;
        }
        
        public final boolean isInitialized() {
            final byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (!this.hasCode()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            if (!this.hasSqlState()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            if (!this.hasMsg()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            this.memoizedIsInitialized = 1;
            return true;
        }
        
        public void writeTo(final CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 0x1) == 0x1) {
                output.writeEnum(1, this.severity_);
            }
            if ((this.bitField0_ & 0x2) == 0x2) {
                output.writeUInt32(2, this.code_);
            }
            if ((this.bitField0_ & 0x8) == 0x8) {
                GeneratedMessageV3.writeString(output, 3, this.msg_);
            }
            if ((this.bitField0_ & 0x4) == 0x4) {
                GeneratedMessageV3.writeString(output, 4, this.sqlState_);
            }
            this.unknownFields.writeTo(output);
        }
        
        public int getSerializedSize() {
            int size = this.memoizedSize;
            if (size != -1) {
                return size;
            }
            size = 0;
            if ((this.bitField0_ & 0x1) == 0x1) {
                size += CodedOutputStream.computeEnumSize(1, this.severity_);
            }
            if ((this.bitField0_ & 0x2) == 0x2) {
                size += CodedOutputStream.computeUInt32Size(2, this.code_);
            }
            if ((this.bitField0_ & 0x8) == 0x8) {
                size += GeneratedMessageV3.computeStringSize(3, this.msg_);
            }
            if ((this.bitField0_ & 0x4) == 0x4) {
                size += GeneratedMessageV3.computeStringSize(4, this.sqlState_);
            }
            size += this.unknownFields.getSerializedSize();
            return this.memoizedSize = size;
        }
        
        public boolean equals(final Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Error)) {
                return super.equals(obj);
            }
            final Error other = (Error)obj;
            boolean result = true;
            result = (result && this.hasSeverity() == other.hasSeverity());
            if (this.hasSeverity()) {
                result = (result && this.severity_ == other.severity_);
            }
            result = (result && this.hasCode() == other.hasCode());
            if (this.hasCode()) {
                result = (result && this.getCode() == other.getCode());
            }
            result = (result && this.hasSqlState() == other.hasSqlState());
            if (this.hasSqlState()) {
                result = (result && this.getSqlState().equals(other.getSqlState()));
            }
            result = (result && this.hasMsg() == other.hasMsg());
            if (this.hasMsg()) {
                result = (result && this.getMsg().equals(other.getMsg()));
            }
            result = (result && this.unknownFields.equals((Object)other.unknownFields));
            return result;
        }
        
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + getDescriptor().hashCode();
            if (this.hasSeverity()) {
                hash = 37 * hash + 1;
                hash = 53 * hash + this.severity_;
            }
            if (this.hasCode()) {
                hash = 37 * hash + 2;
                hash = 53 * hash + this.getCode();
            }
            if (this.hasSqlState()) {
                hash = 37 * hash + 4;
                hash = 53 * hash + this.getSqlState().hashCode();
            }
            if (this.hasMsg()) {
                hash = 37 * hash + 3;
                hash = 53 * hash + this.getMsg().hashCode();
            }
            hash = 29 * hash + this.unknownFields.hashCode();
            return this.memoizedHashCode = hash;
        }
        
        public static Error parseFrom(final ByteBuffer data) throws InvalidProtocolBufferException {
            return (Error)Error.PARSER.parseFrom(data);
        }
        
        public static Error parseFrom(final ByteBuffer data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Error)Error.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Error parseFrom(final ByteString data) throws InvalidProtocolBufferException {
            return (Error)Error.PARSER.parseFrom(data);
        }
        
        public static Error parseFrom(final ByteString data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Error)Error.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Error parseFrom(final byte[] data) throws InvalidProtocolBufferException {
            return (Error)Error.PARSER.parseFrom(data);
        }
        
        public static Error parseFrom(final byte[] data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Error)Error.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Error parseFrom(final InputStream input) throws IOException {
            return (Error)GeneratedMessageV3.parseWithIOException((Parser)Error.PARSER, input);
        }
        
        public static Error parseFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Error)GeneratedMessageV3.parseWithIOException((Parser)Error.PARSER, input, extensionRegistry);
        }
        
        public static Error parseDelimitedFrom(final InputStream input) throws IOException {
            return (Error)GeneratedMessageV3.parseDelimitedWithIOException((Parser)Error.PARSER, input);
        }
        
        public static Error parseDelimitedFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Error)GeneratedMessageV3.parseDelimitedWithIOException((Parser)Error.PARSER, input, extensionRegistry);
        }
        
        public static Error parseFrom(final CodedInputStream input) throws IOException {
            return (Error)GeneratedMessageV3.parseWithIOException((Parser)Error.PARSER, input);
        }
        
        public static Error parseFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Error)GeneratedMessageV3.parseWithIOException((Parser)Error.PARSER, input, extensionRegistry);
        }
        
        public Builder newBuilderForType() {
            return newBuilder();
        }
        
        public static Builder newBuilder() {
            return Error.DEFAULT_INSTANCE.toBuilder();
        }
        
        public static Builder newBuilder(final Error prototype) {
            return Error.DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }
        
        public Builder toBuilder() {
            return (this == Error.DEFAULT_INSTANCE) ? new Builder() : new Builder().mergeFrom(this);
        }
        
        protected Builder newBuilderForType(final GeneratedMessageV3.BuilderParent parent) {
            final Builder builder = new Builder(parent);
            return builder;
        }
        
        public static Error getDefaultInstance() {
            return Error.DEFAULT_INSTANCE;
        }
        
        public static Parser<Error> parser() {
            return Error.PARSER;
        }
        
        public Parser<Error> getParserForType() {
            return Error.PARSER;
        }
        
        public Error getDefaultInstanceForType() {
            return Error.DEFAULT_INSTANCE;
        }
        
        static {
            DEFAULT_INSTANCE = new Error();
            PARSER = (Parser)new AbstractParser<Error>() {
                public Error parsePartialFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new Error(input, extensionRegistry);
                }
            };
        }
        
        public enum Severity implements ProtocolMessageEnum
        {
            ERROR(0), 
            FATAL(1);
            
            public static final int ERROR_VALUE = 0;
            public static final int FATAL_VALUE = 1;
            private static final Internal.EnumLiteMap<Severity> internalValueMap;
            private static final Severity[] VALUES;
            private final int value;
            
            public final int getNumber() {
                return this.value;
            }
            
            @Deprecated
            public static Severity valueOf(final int value) {
                return forNumber(value);
            }
            
            public static Severity forNumber(final int value) {
                switch (value) {
                    case 0: {
                        return Severity.ERROR;
                    }
                    case 1: {
                        return Severity.FATAL;
                    }
                    default: {
                        return null;
                    }
                }
            }
            
            public static Internal.EnumLiteMap<Severity> internalGetValueMap() {
                return Severity.internalValueMap;
            }
            
            public final Descriptors.EnumValueDescriptor getValueDescriptor() {
                return getDescriptor().getValues().get(this.ordinal());
            }
            
            public final Descriptors.EnumDescriptor getDescriptorForType() {
                return getDescriptor();
            }
            
            public static final Descriptors.EnumDescriptor getDescriptor() {
                return Error.getDescriptor().getEnumTypes().get(0);
            }
            
            public static Severity valueOf(final Descriptors.EnumValueDescriptor desc) {
                if (desc.getType() != getDescriptor()) {
                    throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
                }
                return Severity.VALUES[desc.getIndex()];
            }
            
            private Severity(final int value) {
                this.value = value;
            }
            
            static {
                internalValueMap = (Internal.EnumLiteMap)new Internal.EnumLiteMap<Severity>() {
                    public Severity findValueByNumber(final int number) {
                        return Severity.forNumber(number);
                    }
                };
                VALUES = values();
            }
        }
        
        public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements ErrorOrBuilder
        {
            private int bitField0_;
            private int severity_;
            private int code_;
            private Object sqlState_;
            private Object msg_;
            
            public static final Descriptors.Descriptor getDescriptor() {
                return Mysqlx.internal_static_Mysqlx_Error_descriptor;
            }
            
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return Mysqlx.internal_static_Mysqlx_Error_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)Error.class, (Class)Builder.class);
            }
            
            private Builder() {
                this.severity_ = 0;
                this.sqlState_ = "";
                this.msg_ = "";
                this.maybeForceBuilderInitialization();
            }
            
            private Builder(final GeneratedMessageV3.BuilderParent parent) {
                super(parent);
                this.severity_ = 0;
                this.sqlState_ = "";
                this.msg_ = "";
                this.maybeForceBuilderInitialization();
            }
            
            private void maybeForceBuilderInitialization() {
                if (Error.alwaysUseFieldBuilders) {}
            }
            
            public Builder clear() {
                super.clear();
                this.severity_ = 0;
                this.bitField0_ &= 0xFFFFFFFE;
                this.code_ = 0;
                this.bitField0_ &= 0xFFFFFFFD;
                this.sqlState_ = "";
                this.bitField0_ &= 0xFFFFFFFB;
                this.msg_ = "";
                this.bitField0_ &= 0xFFFFFFF7;
                return this;
            }
            
            public Descriptors.Descriptor getDescriptorForType() {
                return Mysqlx.internal_static_Mysqlx_Error_descriptor;
            }
            
            public Error getDefaultInstanceForType() {
                return Error.getDefaultInstance();
            }
            
            public Error build() {
                final Error result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw newUninitializedMessageException((Message)result);
                }
                return result;
            }
            
            public Error buildPartial() {
                final Error result = new Error((GeneratedMessageV3.Builder)this);
                final int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 0x1) == 0x1) {
                    to_bitField0_ |= 0x1;
                }
                result.severity_ = this.severity_;
                if ((from_bitField0_ & 0x2) == 0x2) {
                    to_bitField0_ |= 0x2;
                }
                result.code_ = this.code_;
                if ((from_bitField0_ & 0x4) == 0x4) {
                    to_bitField0_ |= 0x4;
                }
                result.sqlState_ = this.sqlState_;
                if ((from_bitField0_ & 0x8) == 0x8) {
                    to_bitField0_ |= 0x8;
                }
                result.msg_ = this.msg_;
                result.bitField0_ = to_bitField0_;
                this.onBuilt();
                return result;
            }
            
            public Builder clone() {
                return (Builder)super.clone();
            }
            
            public Builder setField(final Descriptors.FieldDescriptor field, final Object value) {
                return (Builder)super.setField(field, value);
            }
            
            public Builder clearField(final Descriptors.FieldDescriptor field) {
                return (Builder)super.clearField(field);
            }
            
            public Builder clearOneof(final Descriptors.OneofDescriptor oneof) {
                return (Builder)super.clearOneof(oneof);
            }
            
            public Builder setRepeatedField(final Descriptors.FieldDescriptor field, final int index, final Object value) {
                return (Builder)super.setRepeatedField(field, index, value);
            }
            
            public Builder addRepeatedField(final Descriptors.FieldDescriptor field, final Object value) {
                return (Builder)super.addRepeatedField(field, value);
            }
            
            public Builder mergeFrom(final Message other) {
                if (other instanceof Error) {
                    return this.mergeFrom((Error)other);
                }
                super.mergeFrom(other);
                return this;
            }
            
            public Builder mergeFrom(final Error other) {
                if (other == Error.getDefaultInstance()) {
                    return this;
                }
                if (other.hasSeverity()) {
                    this.setSeverity(other.getSeverity());
                }
                if (other.hasCode()) {
                    this.setCode(other.getCode());
                }
                if (other.hasSqlState()) {
                    this.bitField0_ |= 0x4;
                    this.sqlState_ = other.sqlState_;
                    this.onChanged();
                }
                if (other.hasMsg()) {
                    this.bitField0_ |= 0x8;
                    this.msg_ = other.msg_;
                    this.onChanged();
                }
                this.mergeUnknownFields(other.unknownFields);
                this.onChanged();
                return this;
            }
            
            public final boolean isInitialized() {
                return this.hasCode() && this.hasSqlState() && this.hasMsg();
            }
            
            public Builder mergeFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
                Error parsedMessage = null;
                try {
                    parsedMessage = (Error)Error.PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (Error)e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                }
                finally {
                    if (parsedMessage != null) {
                        this.mergeFrom(parsedMessage);
                    }
                }
                return this;
            }
            
            public boolean hasSeverity() {
                return (this.bitField0_ & 0x1) == 0x1;
            }
            
            public Severity getSeverity() {
                final Severity result = Severity.valueOf(this.severity_);
                return (result == null) ? Severity.ERROR : result;
            }
            
            public Builder setSeverity(final Severity value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 0x1;
                this.severity_ = value.getNumber();
                this.onChanged();
                return this;
            }
            
            public Builder clearSeverity() {
                this.bitField0_ &= 0xFFFFFFFE;
                this.severity_ = 0;
                this.onChanged();
                return this;
            }
            
            public boolean hasCode() {
                return (this.bitField0_ & 0x2) == 0x2;
            }
            
            public int getCode() {
                return this.code_;
            }
            
            public Builder setCode(final int value) {
                this.bitField0_ |= 0x2;
                this.code_ = value;
                this.onChanged();
                return this;
            }
            
            public Builder clearCode() {
                this.bitField0_ &= 0xFFFFFFFD;
                this.code_ = 0;
                this.onChanged();
                return this;
            }
            
            public boolean hasSqlState() {
                return (this.bitField0_ & 0x4) == 0x4;
            }
            
            public String getSqlState() {
                final Object ref = this.sqlState_;
                if (!(ref instanceof String)) {
                    final ByteString bs = (ByteString)ref;
                    final String s = bs.toStringUtf8();
                    if (bs.isValidUtf8()) {
                        this.sqlState_ = s;
                    }
                    return s;
                }
                return (String)ref;
            }
            
            public ByteString getSqlStateBytes() {
                final Object ref = this.sqlState_;
                if (ref instanceof String) {
                    final ByteString b = ByteString.copyFromUtf8((String)ref);
                    return (ByteString)(this.sqlState_ = b);
                }
                return (ByteString)ref;
            }
            
            public Builder setSqlState(final String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 0x4;
                this.sqlState_ = value;
                this.onChanged();
                return this;
            }
            
            public Builder clearSqlState() {
                this.bitField0_ &= 0xFFFFFFFB;
                this.sqlState_ = Error.getDefaultInstance().getSqlState();
                this.onChanged();
                return this;
            }
            
            public Builder setSqlStateBytes(final ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 0x4;
                this.sqlState_ = value;
                this.onChanged();
                return this;
            }
            
            public boolean hasMsg() {
                return (this.bitField0_ & 0x8) == 0x8;
            }
            
            public String getMsg() {
                final Object ref = this.msg_;
                if (!(ref instanceof String)) {
                    final ByteString bs = (ByteString)ref;
                    final String s = bs.toStringUtf8();
                    if (bs.isValidUtf8()) {
                        this.msg_ = s;
                    }
                    return s;
                }
                return (String)ref;
            }
            
            public ByteString getMsgBytes() {
                final Object ref = this.msg_;
                if (ref instanceof String) {
                    final ByteString b = ByteString.copyFromUtf8((String)ref);
                    return (ByteString)(this.msg_ = b);
                }
                return (ByteString)ref;
            }
            
            public Builder setMsg(final String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 0x8;
                this.msg_ = value;
                this.onChanged();
                return this;
            }
            
            public Builder clearMsg() {
                this.bitField0_ &= 0xFFFFFFF7;
                this.msg_ = Error.getDefaultInstance().getMsg();
                this.onChanged();
                return this;
            }
            
            public Builder setMsgBytes(final ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 0x8;
                this.msg_ = value;
                this.onChanged();
                return this;
            }
            
            public final Builder setUnknownFields(final UnknownFieldSet unknownFields) {
                return (Builder)super.setUnknownFields(unknownFields);
            }
            
            public final Builder mergeUnknownFields(final UnknownFieldSet unknownFields) {
                return (Builder)super.mergeUnknownFields(unknownFields);
            }
        }
    }
    
    public interface ErrorOrBuilder extends MessageOrBuilder
    {
        boolean hasSeverity();
        
        Error.Severity getSeverity();
        
        boolean hasCode();
        
        int getCode();
        
        boolean hasSqlState();
        
        String getSqlState();
        
        ByteString getSqlStateBytes();
        
        boolean hasMsg();
        
        String getMsg();
        
        ByteString getMsgBytes();
    }
    
    public interface OkOrBuilder extends MessageOrBuilder
    {
        boolean hasMsg();
        
        String getMsg();
        
        ByteString getMsgBytes();
    }
    
    public interface ServerMessagesOrBuilder extends MessageOrBuilder
    {
    }
    
    public interface ClientMessagesOrBuilder extends MessageOrBuilder
    {
    }
}
