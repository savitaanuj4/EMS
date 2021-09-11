
package com.mysql.cj.x.protobuf;

import com.google.protobuf.MessageOrBuilder;
import com.google.protobuf.AbstractMessageLite;
import com.google.protobuf.AbstractMessage;
import com.google.protobuf.AbstractParser;
import com.google.protobuf.Message;
import java.io.InputStream;
import java.nio.ByteBuffer;
import com.google.protobuf.CodedOutputStream;
import java.io.IOException;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.MessageLite;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.UnknownFieldSet;
import com.google.protobuf.Parser;
import com.google.protobuf.ByteString;
import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.Descriptors;

public final class MysqlxSession
{
    private static final Descriptors.Descriptor internal_static_Mysqlx_Session_AuthenticateStart_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_Mysqlx_Session_AuthenticateStart_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_Mysqlx_Session_AuthenticateContinue_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_Mysqlx_Session_AuthenticateContinue_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_Mysqlx_Session_AuthenticateOk_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_Mysqlx_Session_AuthenticateOk_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_Mysqlx_Session_Reset_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_Mysqlx_Session_Reset_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_Mysqlx_Session_Close_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_Mysqlx_Session_Close_fieldAccessorTable;
    private static Descriptors.FileDescriptor descriptor;
    
    private MysqlxSession() {
    }
    
    public static void registerAllExtensions(final ExtensionRegistryLite registry) {
    }
    
    public static void registerAllExtensions(final ExtensionRegistry registry) {
        registerAllExtensions((ExtensionRegistryLite)registry);
    }
    
    public static Descriptors.FileDescriptor getDescriptor() {
        return MysqlxSession.descriptor;
    }
    
    static {
        final String[] descriptorData = { "\n\u0014mysqlx_session.proto\u0012\u000eMysqlx.Session\u001a\fmysqlx.proto\"Y\n\u0011AuthenticateStart\u0012\u0011\n\tmech_name\u0018\u0001 \u0002(\t\u0012\u0011\n\tauth_data\u0018\u0002 \u0001(\f\u0012\u0018\n\u0010initial_response\u0018\u0003 \u0001(\f:\u0004\u0088\u00ea0\u0004\"3\n\u0014AuthenticateContinue\u0012\u0011\n\tauth_data\u0018\u0001 \u0002(\f:\b\u0090\u00ea0\u0003\u0088\u00ea0\u0005\")\n\u000eAuthenticateOk\u0012\u0011\n\tauth_data\u0018\u0001 \u0001(\f:\u0004\u0090\u00ea0\u0004\"\r\n\u0005Reset:\u0004\u0088\u00ea0\u0006\"\r\n\u0005Close:\u0004\u0088\u00ea0\u0007B\u0019\n\u0017com.mysql.cj.x.protobuf" };
        final Descriptors.FileDescriptor.InternalDescriptorAssigner assigner = (Descriptors.FileDescriptor.InternalDescriptorAssigner)new Descriptors.FileDescriptor.InternalDescriptorAssigner() {
            public ExtensionRegistry assignDescriptors(final Descriptors.FileDescriptor root) {
                MysqlxSession.descriptor = root;
                return null;
            }
        };
        Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new Descriptors.FileDescriptor[] { Mysqlx.getDescriptor() }, assigner);
        internal_static_Mysqlx_Session_AuthenticateStart_descriptor = getDescriptor().getMessageTypes().get(0);
        internal_static_Mysqlx_Session_AuthenticateStart_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(MysqlxSession.internal_static_Mysqlx_Session_AuthenticateStart_descriptor, new String[] { "MechName", "AuthData", "InitialResponse" });
        internal_static_Mysqlx_Session_AuthenticateContinue_descriptor = getDescriptor().getMessageTypes().get(1);
        internal_static_Mysqlx_Session_AuthenticateContinue_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(MysqlxSession.internal_static_Mysqlx_Session_AuthenticateContinue_descriptor, new String[] { "AuthData" });
        internal_static_Mysqlx_Session_AuthenticateOk_descriptor = getDescriptor().getMessageTypes().get(2);
        internal_static_Mysqlx_Session_AuthenticateOk_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(MysqlxSession.internal_static_Mysqlx_Session_AuthenticateOk_descriptor, new String[] { "AuthData" });
        internal_static_Mysqlx_Session_Reset_descriptor = getDescriptor().getMessageTypes().get(3);
        internal_static_Mysqlx_Session_Reset_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(MysqlxSession.internal_static_Mysqlx_Session_Reset_descriptor, new String[0]);
        internal_static_Mysqlx_Session_Close_descriptor = getDescriptor().getMessageTypes().get(4);
        internal_static_Mysqlx_Session_Close_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(MysqlxSession.internal_static_Mysqlx_Session_Close_descriptor, new String[0]);
        final ExtensionRegistry registry = ExtensionRegistry.newInstance();
        registry.add((GeneratedMessage.GeneratedExtension)Mysqlx.clientMessageId);
        registry.add((GeneratedMessage.GeneratedExtension)Mysqlx.serverMessageId);
        Descriptors.FileDescriptor.internalUpdateFileDescriptor(MysqlxSession.descriptor, registry);
        Mysqlx.getDescriptor();
    }
    
    public static final class AuthenticateStart extends GeneratedMessageV3 implements AuthenticateStartOrBuilder
    {
        private static final long serialVersionUID = 0L;
        private int bitField0_;
        public static final int MECH_NAME_FIELD_NUMBER = 1;
        private volatile Object mechName_;
        public static final int AUTH_DATA_FIELD_NUMBER = 2;
        private ByteString authData_;
        public static final int INITIAL_RESPONSE_FIELD_NUMBER = 3;
        private ByteString initialResponse_;
        private byte memoizedIsInitialized;
        private static final AuthenticateStart DEFAULT_INSTANCE;
        @Deprecated
        public static final Parser<AuthenticateStart> PARSER;
        
        private AuthenticateStart(final GeneratedMessageV3.Builder<?> builder) {
            super((GeneratedMessageV3.Builder)builder);
            this.memoizedIsInitialized = -1;
        }
        
        private AuthenticateStart() {
            this.memoizedIsInitialized = -1;
            this.mechName_ = "";
            this.authData_ = ByteString.EMPTY;
            this.initialResponse_ = ByteString.EMPTY;
        }
        
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }
        
        private AuthenticateStart(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            this.mechName_ = bs;
                            continue;
                        }
                        case 18: {
                            this.bitField0_ |= 0x2;
                            this.authData_ = input.readBytes();
                            continue;
                        }
                        case 26: {
                            this.bitField0_ |= 0x4;
                            this.initialResponse_ = input.readBytes();
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
            return MysqlxSession.internal_static_Mysqlx_Session_AuthenticateStart_descriptor;
        }
        
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return MysqlxSession.internal_static_Mysqlx_Session_AuthenticateStart_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)AuthenticateStart.class, (Class)Builder.class);
        }
        
        public boolean hasMechName() {
            return (this.bitField0_ & 0x1) == 0x1;
        }
        
        public String getMechName() {
            final Object ref = this.mechName_;
            if (ref instanceof String) {
                return (String)ref;
            }
            final ByteString bs = (ByteString)ref;
            final String s = bs.toStringUtf8();
            if (bs.isValidUtf8()) {
                this.mechName_ = s;
            }
            return s;
        }
        
        public ByteString getMechNameBytes() {
            final Object ref = this.mechName_;
            if (ref instanceof String) {
                final ByteString b = ByteString.copyFromUtf8((String)ref);
                return (ByteString)(this.mechName_ = b);
            }
            return (ByteString)ref;
        }
        
        public boolean hasAuthData() {
            return (this.bitField0_ & 0x2) == 0x2;
        }
        
        public ByteString getAuthData() {
            return this.authData_;
        }
        
        public boolean hasInitialResponse() {
            return (this.bitField0_ & 0x4) == 0x4;
        }
        
        public ByteString getInitialResponse() {
            return this.initialResponse_;
        }
        
        public final boolean isInitialized() {
            final byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (!this.hasMechName()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            this.memoizedIsInitialized = 1;
            return true;
        }
        
        public void writeTo(final CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 0x1) == 0x1) {
                GeneratedMessageV3.writeString(output, 1, this.mechName_);
            }
            if ((this.bitField0_ & 0x2) == 0x2) {
                output.writeBytes(2, this.authData_);
            }
            if ((this.bitField0_ & 0x4) == 0x4) {
                output.writeBytes(3, this.initialResponse_);
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
                size += GeneratedMessageV3.computeStringSize(1, this.mechName_);
            }
            if ((this.bitField0_ & 0x2) == 0x2) {
                size += CodedOutputStream.computeBytesSize(2, this.authData_);
            }
            if ((this.bitField0_ & 0x4) == 0x4) {
                size += CodedOutputStream.computeBytesSize(3, this.initialResponse_);
            }
            size += this.unknownFields.getSerializedSize();
            return this.memoizedSize = size;
        }
        
        public boolean equals(final Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof AuthenticateStart)) {
                return super.equals(obj);
            }
            final AuthenticateStart other = (AuthenticateStart)obj;
            boolean result = true;
            result = (result && this.hasMechName() == other.hasMechName());
            if (this.hasMechName()) {
                result = (result && this.getMechName().equals(other.getMechName()));
            }
            result = (result && this.hasAuthData() == other.hasAuthData());
            if (this.hasAuthData()) {
                result = (result && this.getAuthData().equals((Object)other.getAuthData()));
            }
            result = (result && this.hasInitialResponse() == other.hasInitialResponse());
            if (this.hasInitialResponse()) {
                result = (result && this.getInitialResponse().equals((Object)other.getInitialResponse()));
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
            if (this.hasMechName()) {
                hash = 37 * hash + 1;
                hash = 53 * hash + this.getMechName().hashCode();
            }
            if (this.hasAuthData()) {
                hash = 37 * hash + 2;
                hash = 53 * hash + this.getAuthData().hashCode();
            }
            if (this.hasInitialResponse()) {
                hash = 37 * hash + 3;
                hash = 53 * hash + this.getInitialResponse().hashCode();
            }
            hash = 29 * hash + this.unknownFields.hashCode();
            return this.memoizedHashCode = hash;
        }
        
        public static AuthenticateStart parseFrom(final ByteBuffer data) throws InvalidProtocolBufferException {
            return (AuthenticateStart)AuthenticateStart.PARSER.parseFrom(data);
        }
        
        public static AuthenticateStart parseFrom(final ByteBuffer data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (AuthenticateStart)AuthenticateStart.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static AuthenticateStart parseFrom(final ByteString data) throws InvalidProtocolBufferException {
            return (AuthenticateStart)AuthenticateStart.PARSER.parseFrom(data);
        }
        
        public static AuthenticateStart parseFrom(final ByteString data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (AuthenticateStart)AuthenticateStart.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static AuthenticateStart parseFrom(final byte[] data) throws InvalidProtocolBufferException {
            return (AuthenticateStart)AuthenticateStart.PARSER.parseFrom(data);
        }
        
        public static AuthenticateStart parseFrom(final byte[] data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (AuthenticateStart)AuthenticateStart.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static AuthenticateStart parseFrom(final InputStream input) throws IOException {
            return (AuthenticateStart)GeneratedMessageV3.parseWithIOException((Parser)AuthenticateStart.PARSER, input);
        }
        
        public static AuthenticateStart parseFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (AuthenticateStart)GeneratedMessageV3.parseWithIOException((Parser)AuthenticateStart.PARSER, input, extensionRegistry);
        }
        
        public static AuthenticateStart parseDelimitedFrom(final InputStream input) throws IOException {
            return (AuthenticateStart)GeneratedMessageV3.parseDelimitedWithIOException((Parser)AuthenticateStart.PARSER, input);
        }
        
        public static AuthenticateStart parseDelimitedFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (AuthenticateStart)GeneratedMessageV3.parseDelimitedWithIOException((Parser)AuthenticateStart.PARSER, input, extensionRegistry);
        }
        
        public static AuthenticateStart parseFrom(final CodedInputStream input) throws IOException {
            return (AuthenticateStart)GeneratedMessageV3.parseWithIOException((Parser)AuthenticateStart.PARSER, input);
        }
        
        public static AuthenticateStart parseFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (AuthenticateStart)GeneratedMessageV3.parseWithIOException((Parser)AuthenticateStart.PARSER, input, extensionRegistry);
        }
        
        public Builder newBuilderForType() {
            return newBuilder();
        }
        
        public static Builder newBuilder() {
            return AuthenticateStart.DEFAULT_INSTANCE.toBuilder();
        }
        
        public static Builder newBuilder(final AuthenticateStart prototype) {
            return AuthenticateStart.DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }
        
        public Builder toBuilder() {
            return (this == AuthenticateStart.DEFAULT_INSTANCE) ? new Builder() : new Builder().mergeFrom(this);
        }
        
        protected Builder newBuilderForType(final GeneratedMessageV3.BuilderParent parent) {
            final Builder builder = new Builder(parent);
            return builder;
        }
        
        public static AuthenticateStart getDefaultInstance() {
            return AuthenticateStart.DEFAULT_INSTANCE;
        }
        
        public static Parser<AuthenticateStart> parser() {
            return AuthenticateStart.PARSER;
        }
        
        public Parser<AuthenticateStart> getParserForType() {
            return AuthenticateStart.PARSER;
        }
        
        public AuthenticateStart getDefaultInstanceForType() {
            return AuthenticateStart.DEFAULT_INSTANCE;
        }
        
        static {
            DEFAULT_INSTANCE = new AuthenticateStart();
            PARSER = (Parser)new AbstractParser<AuthenticateStart>() {
                public AuthenticateStart parsePartialFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new AuthenticateStart(input, extensionRegistry);
                }
            };
        }
        
        public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements AuthenticateStartOrBuilder
        {
            private int bitField0_;
            private Object mechName_;
            private ByteString authData_;
            private ByteString initialResponse_;
            
            public static final Descriptors.Descriptor getDescriptor() {
                return MysqlxSession.internal_static_Mysqlx_Session_AuthenticateStart_descriptor;
            }
            
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return MysqlxSession.internal_static_Mysqlx_Session_AuthenticateStart_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)AuthenticateStart.class, (Class)Builder.class);
            }
            
            private Builder() {
                this.mechName_ = "";
                this.authData_ = ByteString.EMPTY;
                this.initialResponse_ = ByteString.EMPTY;
                this.maybeForceBuilderInitialization();
            }
            
            private Builder(final GeneratedMessageV3.BuilderParent parent) {
                super(parent);
                this.mechName_ = "";
                this.authData_ = ByteString.EMPTY;
                this.initialResponse_ = ByteString.EMPTY;
                this.maybeForceBuilderInitialization();
            }
            
            private void maybeForceBuilderInitialization() {
                if (AuthenticateStart.alwaysUseFieldBuilders) {}
            }
            
            public Builder clear() {
                super.clear();
                this.mechName_ = "";
                this.bitField0_ &= 0xFFFFFFFE;
                this.authData_ = ByteString.EMPTY;
                this.bitField0_ &= 0xFFFFFFFD;
                this.initialResponse_ = ByteString.EMPTY;
                this.bitField0_ &= 0xFFFFFFFB;
                return this;
            }
            
            public Descriptors.Descriptor getDescriptorForType() {
                return MysqlxSession.internal_static_Mysqlx_Session_AuthenticateStart_descriptor;
            }
            
            public AuthenticateStart getDefaultInstanceForType() {
                return AuthenticateStart.getDefaultInstance();
            }
            
            public AuthenticateStart build() {
                final AuthenticateStart result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw newUninitializedMessageException((Message)result);
                }
                return result;
            }
            
            public AuthenticateStart buildPartial() {
                final AuthenticateStart result = new AuthenticateStart((GeneratedMessageV3.Builder)this);
                final int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 0x1) == 0x1) {
                    to_bitField0_ |= 0x1;
                }
                result.mechName_ = this.mechName_;
                if ((from_bitField0_ & 0x2) == 0x2) {
                    to_bitField0_ |= 0x2;
                }
                result.authData_ = this.authData_;
                if ((from_bitField0_ & 0x4) == 0x4) {
                    to_bitField0_ |= 0x4;
                }
                result.initialResponse_ = this.initialResponse_;
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
                if (other instanceof AuthenticateStart) {
                    return this.mergeFrom((AuthenticateStart)other);
                }
                super.mergeFrom(other);
                return this;
            }
            
            public Builder mergeFrom(final AuthenticateStart other) {
                if (other == AuthenticateStart.getDefaultInstance()) {
                    return this;
                }
                if (other.hasMechName()) {
                    this.bitField0_ |= 0x1;
                    this.mechName_ = other.mechName_;
                    this.onChanged();
                }
                if (other.hasAuthData()) {
                    this.setAuthData(other.getAuthData());
                }
                if (other.hasInitialResponse()) {
                    this.setInitialResponse(other.getInitialResponse());
                }
                this.mergeUnknownFields(other.unknownFields);
                this.onChanged();
                return this;
            }
            
            public final boolean isInitialized() {
                return this.hasMechName();
            }
            
            public Builder mergeFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
                AuthenticateStart parsedMessage = null;
                try {
                    parsedMessage = (AuthenticateStart)AuthenticateStart.PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (AuthenticateStart)e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                }
                finally {
                    if (parsedMessage != null) {
                        this.mergeFrom(parsedMessage);
                    }
                }
                return this;
            }
            
            public boolean hasMechName() {
                return (this.bitField0_ & 0x1) == 0x1;
            }
            
            public String getMechName() {
                final Object ref = this.mechName_;
                if (!(ref instanceof String)) {
                    final ByteString bs = (ByteString)ref;
                    final String s = bs.toStringUtf8();
                    if (bs.isValidUtf8()) {
                        this.mechName_ = s;
                    }
                    return s;
                }
                return (String)ref;
            }
            
            public ByteString getMechNameBytes() {
                final Object ref = this.mechName_;
                if (ref instanceof String) {
                    final ByteString b = ByteString.copyFromUtf8((String)ref);
                    return (ByteString)(this.mechName_ = b);
                }
                return (ByteString)ref;
            }
            
            public Builder setMechName(final String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 0x1;
                this.mechName_ = value;
                this.onChanged();
                return this;
            }
            
            public Builder clearMechName() {
                this.bitField0_ &= 0xFFFFFFFE;
                this.mechName_ = AuthenticateStart.getDefaultInstance().getMechName();
                this.onChanged();
                return this;
            }
            
            public Builder setMechNameBytes(final ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 0x1;
                this.mechName_ = value;
                this.onChanged();
                return this;
            }
            
            public boolean hasAuthData() {
                return (this.bitField0_ & 0x2) == 0x2;
            }
            
            public ByteString getAuthData() {
                return this.authData_;
            }
            
            public Builder setAuthData(final ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 0x2;
                this.authData_ = value;
                this.onChanged();
                return this;
            }
            
            public Builder clearAuthData() {
                this.bitField0_ &= 0xFFFFFFFD;
                this.authData_ = AuthenticateStart.getDefaultInstance().getAuthData();
                this.onChanged();
                return this;
            }
            
            public boolean hasInitialResponse() {
                return (this.bitField0_ & 0x4) == 0x4;
            }
            
            public ByteString getInitialResponse() {
                return this.initialResponse_;
            }
            
            public Builder setInitialResponse(final ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 0x4;
                this.initialResponse_ = value;
                this.onChanged();
                return this;
            }
            
            public Builder clearInitialResponse() {
                this.bitField0_ &= 0xFFFFFFFB;
                this.initialResponse_ = AuthenticateStart.getDefaultInstance().getInitialResponse();
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
    
    public static final class AuthenticateContinue extends GeneratedMessageV3 implements AuthenticateContinueOrBuilder
    {
        private static final long serialVersionUID = 0L;
        private int bitField0_;
        public static final int AUTH_DATA_FIELD_NUMBER = 1;
        private ByteString authData_;
        private byte memoizedIsInitialized;
        private static final AuthenticateContinue DEFAULT_INSTANCE;
        @Deprecated
        public static final Parser<AuthenticateContinue> PARSER;
        
        private AuthenticateContinue(final GeneratedMessageV3.Builder<?> builder) {
            super((GeneratedMessageV3.Builder)builder);
            this.memoizedIsInitialized = -1;
        }
        
        private AuthenticateContinue() {
            this.memoizedIsInitialized = -1;
            this.authData_ = ByteString.EMPTY;
        }
        
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }
        
        private AuthenticateContinue(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            this.bitField0_ |= 0x1;
                            this.authData_ = input.readBytes();
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
            return MysqlxSession.internal_static_Mysqlx_Session_AuthenticateContinue_descriptor;
        }
        
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return MysqlxSession.internal_static_Mysqlx_Session_AuthenticateContinue_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)AuthenticateContinue.class, (Class)Builder.class);
        }
        
        public boolean hasAuthData() {
            return (this.bitField0_ & 0x1) == 0x1;
        }
        
        public ByteString getAuthData() {
            return this.authData_;
        }
        
        public final boolean isInitialized() {
            final byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (!this.hasAuthData()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            this.memoizedIsInitialized = 1;
            return true;
        }
        
        public void writeTo(final CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 0x1) == 0x1) {
                output.writeBytes(1, this.authData_);
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
                size += CodedOutputStream.computeBytesSize(1, this.authData_);
            }
            size += this.unknownFields.getSerializedSize();
            return this.memoizedSize = size;
        }
        
        public boolean equals(final Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof AuthenticateContinue)) {
                return super.equals(obj);
            }
            final AuthenticateContinue other = (AuthenticateContinue)obj;
            boolean result = true;
            result = (result && this.hasAuthData() == other.hasAuthData());
            if (this.hasAuthData()) {
                result = (result && this.getAuthData().equals((Object)other.getAuthData()));
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
            if (this.hasAuthData()) {
                hash = 37 * hash + 1;
                hash = 53 * hash + this.getAuthData().hashCode();
            }
            hash = 29 * hash + this.unknownFields.hashCode();
            return this.memoizedHashCode = hash;
        }
        
        public static AuthenticateContinue parseFrom(final ByteBuffer data) throws InvalidProtocolBufferException {
            return (AuthenticateContinue)AuthenticateContinue.PARSER.parseFrom(data);
        }
        
        public static AuthenticateContinue parseFrom(final ByteBuffer data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (AuthenticateContinue)AuthenticateContinue.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static AuthenticateContinue parseFrom(final ByteString data) throws InvalidProtocolBufferException {
            return (AuthenticateContinue)AuthenticateContinue.PARSER.parseFrom(data);
        }
        
        public static AuthenticateContinue parseFrom(final ByteString data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (AuthenticateContinue)AuthenticateContinue.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static AuthenticateContinue parseFrom(final byte[] data) throws InvalidProtocolBufferException {
            return (AuthenticateContinue)AuthenticateContinue.PARSER.parseFrom(data);
        }
        
        public static AuthenticateContinue parseFrom(final byte[] data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (AuthenticateContinue)AuthenticateContinue.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static AuthenticateContinue parseFrom(final InputStream input) throws IOException {
            return (AuthenticateContinue)GeneratedMessageV3.parseWithIOException((Parser)AuthenticateContinue.PARSER, input);
        }
        
        public static AuthenticateContinue parseFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (AuthenticateContinue)GeneratedMessageV3.parseWithIOException((Parser)AuthenticateContinue.PARSER, input, extensionRegistry);
        }
        
        public static AuthenticateContinue parseDelimitedFrom(final InputStream input) throws IOException {
            return (AuthenticateContinue)GeneratedMessageV3.parseDelimitedWithIOException((Parser)AuthenticateContinue.PARSER, input);
        }
        
        public static AuthenticateContinue parseDelimitedFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (AuthenticateContinue)GeneratedMessageV3.parseDelimitedWithIOException((Parser)AuthenticateContinue.PARSER, input, extensionRegistry);
        }
        
        public static AuthenticateContinue parseFrom(final CodedInputStream input) throws IOException {
            return (AuthenticateContinue)GeneratedMessageV3.parseWithIOException((Parser)AuthenticateContinue.PARSER, input);
        }
        
        public static AuthenticateContinue parseFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (AuthenticateContinue)GeneratedMessageV3.parseWithIOException((Parser)AuthenticateContinue.PARSER, input, extensionRegistry);
        }
        
        public Builder newBuilderForType() {
            return newBuilder();
        }
        
        public static Builder newBuilder() {
            return AuthenticateContinue.DEFAULT_INSTANCE.toBuilder();
        }
        
        public static Builder newBuilder(final AuthenticateContinue prototype) {
            return AuthenticateContinue.DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }
        
        public Builder toBuilder() {
            return (this == AuthenticateContinue.DEFAULT_INSTANCE) ? new Builder() : new Builder().mergeFrom(this);
        }
        
        protected Builder newBuilderForType(final GeneratedMessageV3.BuilderParent parent) {
            final Builder builder = new Builder(parent);
            return builder;
        }
        
        public static AuthenticateContinue getDefaultInstance() {
            return AuthenticateContinue.DEFAULT_INSTANCE;
        }
        
        public static Parser<AuthenticateContinue> parser() {
            return AuthenticateContinue.PARSER;
        }
        
        public Parser<AuthenticateContinue> getParserForType() {
            return AuthenticateContinue.PARSER;
        }
        
        public AuthenticateContinue getDefaultInstanceForType() {
            return AuthenticateContinue.DEFAULT_INSTANCE;
        }
        
        static {
            DEFAULT_INSTANCE = new AuthenticateContinue();
            PARSER = (Parser)new AbstractParser<AuthenticateContinue>() {
                public AuthenticateContinue parsePartialFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new AuthenticateContinue(input, extensionRegistry);
                }
            };
        }
        
        public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements AuthenticateContinueOrBuilder
        {
            private int bitField0_;
            private ByteString authData_;
            
            public static final Descriptors.Descriptor getDescriptor() {
                return MysqlxSession.internal_static_Mysqlx_Session_AuthenticateContinue_descriptor;
            }
            
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return MysqlxSession.internal_static_Mysqlx_Session_AuthenticateContinue_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)AuthenticateContinue.class, (Class)Builder.class);
            }
            
            private Builder() {
                this.authData_ = ByteString.EMPTY;
                this.maybeForceBuilderInitialization();
            }
            
            private Builder(final GeneratedMessageV3.BuilderParent parent) {
                super(parent);
                this.authData_ = ByteString.EMPTY;
                this.maybeForceBuilderInitialization();
            }
            
            private void maybeForceBuilderInitialization() {
                if (AuthenticateContinue.alwaysUseFieldBuilders) {}
            }
            
            public Builder clear() {
                super.clear();
                this.authData_ = ByteString.EMPTY;
                this.bitField0_ &= 0xFFFFFFFE;
                return this;
            }
            
            public Descriptors.Descriptor getDescriptorForType() {
                return MysqlxSession.internal_static_Mysqlx_Session_AuthenticateContinue_descriptor;
            }
            
            public AuthenticateContinue getDefaultInstanceForType() {
                return AuthenticateContinue.getDefaultInstance();
            }
            
            public AuthenticateContinue build() {
                final AuthenticateContinue result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw newUninitializedMessageException((Message)result);
                }
                return result;
            }
            
            public AuthenticateContinue buildPartial() {
                final AuthenticateContinue result = new AuthenticateContinue((GeneratedMessageV3.Builder)this);
                final int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 0x1) == 0x1) {
                    to_bitField0_ |= 0x1;
                }
                result.authData_ = this.authData_;
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
                if (other instanceof AuthenticateContinue) {
                    return this.mergeFrom((AuthenticateContinue)other);
                }
                super.mergeFrom(other);
                return this;
            }
            
            public Builder mergeFrom(final AuthenticateContinue other) {
                if (other == AuthenticateContinue.getDefaultInstance()) {
                    return this;
                }
                if (other.hasAuthData()) {
                    this.setAuthData(other.getAuthData());
                }
                this.mergeUnknownFields(other.unknownFields);
                this.onChanged();
                return this;
            }
            
            public final boolean isInitialized() {
                return this.hasAuthData();
            }
            
            public Builder mergeFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
                AuthenticateContinue parsedMessage = null;
                try {
                    parsedMessage = (AuthenticateContinue)AuthenticateContinue.PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (AuthenticateContinue)e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                }
                finally {
                    if (parsedMessage != null) {
                        this.mergeFrom(parsedMessage);
                    }
                }
                return this;
            }
            
            public boolean hasAuthData() {
                return (this.bitField0_ & 0x1) == 0x1;
            }
            
            public ByteString getAuthData() {
                return this.authData_;
            }
            
            public Builder setAuthData(final ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 0x1;
                this.authData_ = value;
                this.onChanged();
                return this;
            }
            
            public Builder clearAuthData() {
                this.bitField0_ &= 0xFFFFFFFE;
                this.authData_ = AuthenticateContinue.getDefaultInstance().getAuthData();
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
    
    public static final class AuthenticateOk extends GeneratedMessageV3 implements AuthenticateOkOrBuilder
    {
        private static final long serialVersionUID = 0L;
        private int bitField0_;
        public static final int AUTH_DATA_FIELD_NUMBER = 1;
        private ByteString authData_;
        private byte memoizedIsInitialized;
        private static final AuthenticateOk DEFAULT_INSTANCE;
        @Deprecated
        public static final Parser<AuthenticateOk> PARSER;
        
        private AuthenticateOk(final GeneratedMessageV3.Builder<?> builder) {
            super((GeneratedMessageV3.Builder)builder);
            this.memoizedIsInitialized = -1;
        }
        
        private AuthenticateOk() {
            this.memoizedIsInitialized = -1;
            this.authData_ = ByteString.EMPTY;
        }
        
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }
        
        private AuthenticateOk(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            this.bitField0_ |= 0x1;
                            this.authData_ = input.readBytes();
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
            return MysqlxSession.internal_static_Mysqlx_Session_AuthenticateOk_descriptor;
        }
        
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return MysqlxSession.internal_static_Mysqlx_Session_AuthenticateOk_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)AuthenticateOk.class, (Class)Builder.class);
        }
        
        public boolean hasAuthData() {
            return (this.bitField0_ & 0x1) == 0x1;
        }
        
        public ByteString getAuthData() {
            return this.authData_;
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
                output.writeBytes(1, this.authData_);
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
                size += CodedOutputStream.computeBytesSize(1, this.authData_);
            }
            size += this.unknownFields.getSerializedSize();
            return this.memoizedSize = size;
        }
        
        public boolean equals(final Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof AuthenticateOk)) {
                return super.equals(obj);
            }
            final AuthenticateOk other = (AuthenticateOk)obj;
            boolean result = true;
            result = (result && this.hasAuthData() == other.hasAuthData());
            if (this.hasAuthData()) {
                result = (result && this.getAuthData().equals((Object)other.getAuthData()));
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
            if (this.hasAuthData()) {
                hash = 37 * hash + 1;
                hash = 53 * hash + this.getAuthData().hashCode();
            }
            hash = 29 * hash + this.unknownFields.hashCode();
            return this.memoizedHashCode = hash;
        }
        
        public static AuthenticateOk parseFrom(final ByteBuffer data) throws InvalidProtocolBufferException {
            return (AuthenticateOk)AuthenticateOk.PARSER.parseFrom(data);
        }
        
        public static AuthenticateOk parseFrom(final ByteBuffer data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (AuthenticateOk)AuthenticateOk.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static AuthenticateOk parseFrom(final ByteString data) throws InvalidProtocolBufferException {
            return (AuthenticateOk)AuthenticateOk.PARSER.parseFrom(data);
        }
        
        public static AuthenticateOk parseFrom(final ByteString data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (AuthenticateOk)AuthenticateOk.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static AuthenticateOk parseFrom(final byte[] data) throws InvalidProtocolBufferException {
            return (AuthenticateOk)AuthenticateOk.PARSER.parseFrom(data);
        }
        
        public static AuthenticateOk parseFrom(final byte[] data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (AuthenticateOk)AuthenticateOk.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static AuthenticateOk parseFrom(final InputStream input) throws IOException {
            return (AuthenticateOk)GeneratedMessageV3.parseWithIOException((Parser)AuthenticateOk.PARSER, input);
        }
        
        public static AuthenticateOk parseFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (AuthenticateOk)GeneratedMessageV3.parseWithIOException((Parser)AuthenticateOk.PARSER, input, extensionRegistry);
        }
        
        public static AuthenticateOk parseDelimitedFrom(final InputStream input) throws IOException {
            return (AuthenticateOk)GeneratedMessageV3.parseDelimitedWithIOException((Parser)AuthenticateOk.PARSER, input);
        }
        
        public static AuthenticateOk parseDelimitedFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (AuthenticateOk)GeneratedMessageV3.parseDelimitedWithIOException((Parser)AuthenticateOk.PARSER, input, extensionRegistry);
        }
        
        public static AuthenticateOk parseFrom(final CodedInputStream input) throws IOException {
            return (AuthenticateOk)GeneratedMessageV3.parseWithIOException((Parser)AuthenticateOk.PARSER, input);
        }
        
        public static AuthenticateOk parseFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (AuthenticateOk)GeneratedMessageV3.parseWithIOException((Parser)AuthenticateOk.PARSER, input, extensionRegistry);
        }
        
        public Builder newBuilderForType() {
            return newBuilder();
        }
        
        public static Builder newBuilder() {
            return AuthenticateOk.DEFAULT_INSTANCE.toBuilder();
        }
        
        public static Builder newBuilder(final AuthenticateOk prototype) {
            return AuthenticateOk.DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }
        
        public Builder toBuilder() {
            return (this == AuthenticateOk.DEFAULT_INSTANCE) ? new Builder() : new Builder().mergeFrom(this);
        }
        
        protected Builder newBuilderForType(final GeneratedMessageV3.BuilderParent parent) {
            final Builder builder = new Builder(parent);
            return builder;
        }
        
        public static AuthenticateOk getDefaultInstance() {
            return AuthenticateOk.DEFAULT_INSTANCE;
        }
        
        public static Parser<AuthenticateOk> parser() {
            return AuthenticateOk.PARSER;
        }
        
        public Parser<AuthenticateOk> getParserForType() {
            return AuthenticateOk.PARSER;
        }
        
        public AuthenticateOk getDefaultInstanceForType() {
            return AuthenticateOk.DEFAULT_INSTANCE;
        }
        
        static {
            DEFAULT_INSTANCE = new AuthenticateOk();
            PARSER = (Parser)new AbstractParser<AuthenticateOk>() {
                public AuthenticateOk parsePartialFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new AuthenticateOk(input, extensionRegistry);
                }
            };
        }
        
        public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements AuthenticateOkOrBuilder
        {
            private int bitField0_;
            private ByteString authData_;
            
            public static final Descriptors.Descriptor getDescriptor() {
                return MysqlxSession.internal_static_Mysqlx_Session_AuthenticateOk_descriptor;
            }
            
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return MysqlxSession.internal_static_Mysqlx_Session_AuthenticateOk_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)AuthenticateOk.class, (Class)Builder.class);
            }
            
            private Builder() {
                this.authData_ = ByteString.EMPTY;
                this.maybeForceBuilderInitialization();
            }
            
            private Builder(final GeneratedMessageV3.BuilderParent parent) {
                super(parent);
                this.authData_ = ByteString.EMPTY;
                this.maybeForceBuilderInitialization();
            }
            
            private void maybeForceBuilderInitialization() {
                if (AuthenticateOk.alwaysUseFieldBuilders) {}
            }
            
            public Builder clear() {
                super.clear();
                this.authData_ = ByteString.EMPTY;
                this.bitField0_ &= 0xFFFFFFFE;
                return this;
            }
            
            public Descriptors.Descriptor getDescriptorForType() {
                return MysqlxSession.internal_static_Mysqlx_Session_AuthenticateOk_descriptor;
            }
            
            public AuthenticateOk getDefaultInstanceForType() {
                return AuthenticateOk.getDefaultInstance();
            }
            
            public AuthenticateOk build() {
                final AuthenticateOk result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw newUninitializedMessageException((Message)result);
                }
                return result;
            }
            
            public AuthenticateOk buildPartial() {
                final AuthenticateOk result = new AuthenticateOk((GeneratedMessageV3.Builder)this);
                final int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 0x1) == 0x1) {
                    to_bitField0_ |= 0x1;
                }
                result.authData_ = this.authData_;
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
                if (other instanceof AuthenticateOk) {
                    return this.mergeFrom((AuthenticateOk)other);
                }
                super.mergeFrom(other);
                return this;
            }
            
            public Builder mergeFrom(final AuthenticateOk other) {
                if (other == AuthenticateOk.getDefaultInstance()) {
                    return this;
                }
                if (other.hasAuthData()) {
                    this.setAuthData(other.getAuthData());
                }
                this.mergeUnknownFields(other.unknownFields);
                this.onChanged();
                return this;
            }
            
            public final boolean isInitialized() {
                return true;
            }
            
            public Builder mergeFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
                AuthenticateOk parsedMessage = null;
                try {
                    parsedMessage = (AuthenticateOk)AuthenticateOk.PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (AuthenticateOk)e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                }
                finally {
                    if (parsedMessage != null) {
                        this.mergeFrom(parsedMessage);
                    }
                }
                return this;
            }
            
            public boolean hasAuthData() {
                return (this.bitField0_ & 0x1) == 0x1;
            }
            
            public ByteString getAuthData() {
                return this.authData_;
            }
            
            public Builder setAuthData(final ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 0x1;
                this.authData_ = value;
                this.onChanged();
                return this;
            }
            
            public Builder clearAuthData() {
                this.bitField0_ &= 0xFFFFFFFE;
                this.authData_ = AuthenticateOk.getDefaultInstance().getAuthData();
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
    
    public static final class Reset extends GeneratedMessageV3 implements ResetOrBuilder
    {
        private static final long serialVersionUID = 0L;
        private byte memoizedIsInitialized;
        private static final Reset DEFAULT_INSTANCE;
        @Deprecated
        public static final Parser<Reset> PARSER;
        
        private Reset(final GeneratedMessageV3.Builder<?> builder) {
            super((GeneratedMessageV3.Builder)builder);
            this.memoizedIsInitialized = -1;
        }
        
        private Reset() {
            this.memoizedIsInitialized = -1;
        }
        
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }
        
        private Reset(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
            return MysqlxSession.internal_static_Mysqlx_Session_Reset_descriptor;
        }
        
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return MysqlxSession.internal_static_Mysqlx_Session_Reset_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)Reset.class, (Class)Builder.class);
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
            if (!(obj instanceof Reset)) {
                return super.equals(obj);
            }
            final Reset other = (Reset)obj;
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
        
        public static Reset parseFrom(final ByteBuffer data) throws InvalidProtocolBufferException {
            return (Reset)Reset.PARSER.parseFrom(data);
        }
        
        public static Reset parseFrom(final ByteBuffer data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Reset)Reset.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Reset parseFrom(final ByteString data) throws InvalidProtocolBufferException {
            return (Reset)Reset.PARSER.parseFrom(data);
        }
        
        public static Reset parseFrom(final ByteString data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Reset)Reset.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Reset parseFrom(final byte[] data) throws InvalidProtocolBufferException {
            return (Reset)Reset.PARSER.parseFrom(data);
        }
        
        public static Reset parseFrom(final byte[] data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Reset)Reset.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Reset parseFrom(final InputStream input) throws IOException {
            return (Reset)GeneratedMessageV3.parseWithIOException((Parser)Reset.PARSER, input);
        }
        
        public static Reset parseFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Reset)GeneratedMessageV3.parseWithIOException((Parser)Reset.PARSER, input, extensionRegistry);
        }
        
        public static Reset parseDelimitedFrom(final InputStream input) throws IOException {
            return (Reset)GeneratedMessageV3.parseDelimitedWithIOException((Parser)Reset.PARSER, input);
        }
        
        public static Reset parseDelimitedFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Reset)GeneratedMessageV3.parseDelimitedWithIOException((Parser)Reset.PARSER, input, extensionRegistry);
        }
        
        public static Reset parseFrom(final CodedInputStream input) throws IOException {
            return (Reset)GeneratedMessageV3.parseWithIOException((Parser)Reset.PARSER, input);
        }
        
        public static Reset parseFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Reset)GeneratedMessageV3.parseWithIOException((Parser)Reset.PARSER, input, extensionRegistry);
        }
        
        public Builder newBuilderForType() {
            return newBuilder();
        }
        
        public static Builder newBuilder() {
            return Reset.DEFAULT_INSTANCE.toBuilder();
        }
        
        public static Builder newBuilder(final Reset prototype) {
            return Reset.DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }
        
        public Builder toBuilder() {
            return (this == Reset.DEFAULT_INSTANCE) ? new Builder() : new Builder().mergeFrom(this);
        }
        
        protected Builder newBuilderForType(final GeneratedMessageV3.BuilderParent parent) {
            final Builder builder = new Builder(parent);
            return builder;
        }
        
        public static Reset getDefaultInstance() {
            return Reset.DEFAULT_INSTANCE;
        }
        
        public static Parser<Reset> parser() {
            return Reset.PARSER;
        }
        
        public Parser<Reset> getParserForType() {
            return Reset.PARSER;
        }
        
        public Reset getDefaultInstanceForType() {
            return Reset.DEFAULT_INSTANCE;
        }
        
        static {
            DEFAULT_INSTANCE = new Reset();
            PARSER = (Parser)new AbstractParser<Reset>() {
                public Reset parsePartialFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new Reset(input, extensionRegistry);
                }
            };
        }
        
        public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements ResetOrBuilder
        {
            public static final Descriptors.Descriptor getDescriptor() {
                return MysqlxSession.internal_static_Mysqlx_Session_Reset_descriptor;
            }
            
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return MysqlxSession.internal_static_Mysqlx_Session_Reset_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)Reset.class, (Class)Builder.class);
            }
            
            private Builder() {
                this.maybeForceBuilderInitialization();
            }
            
            private Builder(final GeneratedMessageV3.BuilderParent parent) {
                super(parent);
                this.maybeForceBuilderInitialization();
            }
            
            private void maybeForceBuilderInitialization() {
                if (Reset.alwaysUseFieldBuilders) {}
            }
            
            public Builder clear() {
                super.clear();
                return this;
            }
            
            public Descriptors.Descriptor getDescriptorForType() {
                return MysqlxSession.internal_static_Mysqlx_Session_Reset_descriptor;
            }
            
            public Reset getDefaultInstanceForType() {
                return Reset.getDefaultInstance();
            }
            
            public Reset build() {
                final Reset result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw newUninitializedMessageException((Message)result);
                }
                return result;
            }
            
            public Reset buildPartial() {
                final Reset result = new Reset((GeneratedMessageV3.Builder)this);
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
                if (other instanceof Reset) {
                    return this.mergeFrom((Reset)other);
                }
                super.mergeFrom(other);
                return this;
            }
            
            public Builder mergeFrom(final Reset other) {
                if (other == Reset.getDefaultInstance()) {
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
                Reset parsedMessage = null;
                try {
                    parsedMessage = (Reset)Reset.PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (Reset)e.getUnfinishedMessage();
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
    
    public static final class Close extends GeneratedMessageV3 implements CloseOrBuilder
    {
        private static final long serialVersionUID = 0L;
        private byte memoizedIsInitialized;
        private static final Close DEFAULT_INSTANCE;
        @Deprecated
        public static final Parser<Close> PARSER;
        
        private Close(final GeneratedMessageV3.Builder<?> builder) {
            super((GeneratedMessageV3.Builder)builder);
            this.memoizedIsInitialized = -1;
        }
        
        private Close() {
            this.memoizedIsInitialized = -1;
        }
        
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }
        
        private Close(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
            return MysqlxSession.internal_static_Mysqlx_Session_Close_descriptor;
        }
        
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return MysqlxSession.internal_static_Mysqlx_Session_Close_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)Close.class, (Class)Builder.class);
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
            if (!(obj instanceof Close)) {
                return super.equals(obj);
            }
            final Close other = (Close)obj;
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
        
        public static Close parseFrom(final ByteBuffer data) throws InvalidProtocolBufferException {
            return (Close)Close.PARSER.parseFrom(data);
        }
        
        public static Close parseFrom(final ByteBuffer data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Close)Close.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Close parseFrom(final ByteString data) throws InvalidProtocolBufferException {
            return (Close)Close.PARSER.parseFrom(data);
        }
        
        public static Close parseFrom(final ByteString data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Close)Close.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Close parseFrom(final byte[] data) throws InvalidProtocolBufferException {
            return (Close)Close.PARSER.parseFrom(data);
        }
        
        public static Close parseFrom(final byte[] data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Close)Close.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Close parseFrom(final InputStream input) throws IOException {
            return (Close)GeneratedMessageV3.parseWithIOException((Parser)Close.PARSER, input);
        }
        
        public static Close parseFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Close)GeneratedMessageV3.parseWithIOException((Parser)Close.PARSER, input, extensionRegistry);
        }
        
        public static Close parseDelimitedFrom(final InputStream input) throws IOException {
            return (Close)GeneratedMessageV3.parseDelimitedWithIOException((Parser)Close.PARSER, input);
        }
        
        public static Close parseDelimitedFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Close)GeneratedMessageV3.parseDelimitedWithIOException((Parser)Close.PARSER, input, extensionRegistry);
        }
        
        public static Close parseFrom(final CodedInputStream input) throws IOException {
            return (Close)GeneratedMessageV3.parseWithIOException((Parser)Close.PARSER, input);
        }
        
        public static Close parseFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Close)GeneratedMessageV3.parseWithIOException((Parser)Close.PARSER, input, extensionRegistry);
        }
        
        public Builder newBuilderForType() {
            return newBuilder();
        }
        
        public static Builder newBuilder() {
            return Close.DEFAULT_INSTANCE.toBuilder();
        }
        
        public static Builder newBuilder(final Close prototype) {
            return Close.DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }
        
        public Builder toBuilder() {
            return (this == Close.DEFAULT_INSTANCE) ? new Builder() : new Builder().mergeFrom(this);
        }
        
        protected Builder newBuilderForType(final GeneratedMessageV3.BuilderParent parent) {
            final Builder builder = new Builder(parent);
            return builder;
        }
        
        public static Close getDefaultInstance() {
            return Close.DEFAULT_INSTANCE;
        }
        
        public static Parser<Close> parser() {
            return Close.PARSER;
        }
        
        public Parser<Close> getParserForType() {
            return Close.PARSER;
        }
        
        public Close getDefaultInstanceForType() {
            return Close.DEFAULT_INSTANCE;
        }
        
        static {
            DEFAULT_INSTANCE = new Close();
            PARSER = (Parser)new AbstractParser<Close>() {
                public Close parsePartialFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new Close(input, extensionRegistry);
                }
            };
        }
        
        public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements CloseOrBuilder
        {
            public static final Descriptors.Descriptor getDescriptor() {
                return MysqlxSession.internal_static_Mysqlx_Session_Close_descriptor;
            }
            
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return MysqlxSession.internal_static_Mysqlx_Session_Close_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)Close.class, (Class)Builder.class);
            }
            
            private Builder() {
                this.maybeForceBuilderInitialization();
            }
            
            private Builder(final GeneratedMessageV3.BuilderParent parent) {
                super(parent);
                this.maybeForceBuilderInitialization();
            }
            
            private void maybeForceBuilderInitialization() {
                if (Close.alwaysUseFieldBuilders) {}
            }
            
            public Builder clear() {
                super.clear();
                return this;
            }
            
            public Descriptors.Descriptor getDescriptorForType() {
                return MysqlxSession.internal_static_Mysqlx_Session_Close_descriptor;
            }
            
            public Close getDefaultInstanceForType() {
                return Close.getDefaultInstance();
            }
            
            public Close build() {
                final Close result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw newUninitializedMessageException((Message)result);
                }
                return result;
            }
            
            public Close buildPartial() {
                final Close result = new Close((GeneratedMessageV3.Builder)this);
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
                if (other instanceof Close) {
                    return this.mergeFrom((Close)other);
                }
                super.mergeFrom(other);
                return this;
            }
            
            public Builder mergeFrom(final Close other) {
                if (other == Close.getDefaultInstance()) {
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
                Close parsedMessage = null;
                try {
                    parsedMessage = (Close)Close.PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (Close)e.getUnfinishedMessage();
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
    
    public interface CloseOrBuilder extends MessageOrBuilder
    {
    }
    
    public interface ResetOrBuilder extends MessageOrBuilder
    {
    }
    
    public interface AuthenticateOkOrBuilder extends MessageOrBuilder
    {
        boolean hasAuthData();
        
        ByteString getAuthData();
    }
    
    public interface AuthenticateContinueOrBuilder extends MessageOrBuilder
    {
        boolean hasAuthData();
        
        ByteString getAuthData();
    }
    
    public interface AuthenticateStartOrBuilder extends MessageOrBuilder
    {
        boolean hasMechName();
        
        String getMechName();
        
        ByteString getMechNameBytes();
        
        boolean hasAuthData();
        
        ByteString getAuthData();
        
        boolean hasInitialResponse();
        
        ByteString getInitialResponse();
    }
}
