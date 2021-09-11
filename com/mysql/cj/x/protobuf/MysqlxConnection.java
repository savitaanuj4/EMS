
package com.mysql.cj.x.protobuf;

import com.google.protobuf.MessageOrBuilder;
import java.util.Collection;
import com.google.protobuf.RepeatedFieldBuilderV3;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.google.protobuf.AbstractMessageLite;
import com.google.protobuf.AbstractMessage;
import com.google.protobuf.SingleFieldBuilderV3;
import com.google.protobuf.AbstractParser;
import com.google.protobuf.Message;
import java.io.InputStream;
import java.nio.ByteBuffer;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.ByteString;
import java.io.IOException;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.MessageLite;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.UnknownFieldSet;
import com.google.protobuf.Parser;
import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.Descriptors;

public final class MysqlxConnection
{
    private static final Descriptors.Descriptor internal_static_Mysqlx_Connection_Capability_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_Mysqlx_Connection_Capability_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_Mysqlx_Connection_Capabilities_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_Mysqlx_Connection_Capabilities_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_Mysqlx_Connection_CapabilitiesGet_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_Mysqlx_Connection_CapabilitiesGet_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_Mysqlx_Connection_CapabilitiesSet_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_Mysqlx_Connection_CapabilitiesSet_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_Mysqlx_Connection_Close_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_Mysqlx_Connection_Close_fieldAccessorTable;
    private static Descriptors.FileDescriptor descriptor;
    
    private MysqlxConnection() {
    }
    
    public static void registerAllExtensions(final ExtensionRegistryLite registry) {
    }
    
    public static void registerAllExtensions(final ExtensionRegistry registry) {
        registerAllExtensions((ExtensionRegistryLite)registry);
    }
    
    public static Descriptors.FileDescriptor getDescriptor() {
        return MysqlxConnection.descriptor;
    }
    
    static {
        final String[] descriptorData = { "\n\u0017mysqlx_connection.proto\u0012\u0011Mysqlx.Connection\u001a\u0016mysqlx_datatypes.proto\u001a\fmysqlx.proto\"@\n\nCapability\u0012\f\n\u0004name\u0018\u0001 \u0002(\t\u0012$\n\u0005value\u0018\u0002 \u0002(\u000b2\u0015.Mysqlx.Datatypes.Any\"I\n\fCapabilities\u00123\n\fcapabilities\u0018\u0001 \u0003(\u000b2\u001d.Mysqlx.Connection.Capability:\u0004\u0090\u00ea0\u0002\"\u0017\n\u000fCapabilitiesGet:\u0004\u0088\u00ea0\u0001\"N\n\u000fCapabilitiesSet\u00125\n\fcapabilities\u0018\u0001 \u0002(\u000b2\u001f.Mysqlx.Connection.Capabilities:\u0004\u0088\u00ea0\u0002\"\r\n\u0005Close:\u0004\u0088\u00ea0\u0003B\u0019\n\u0017com.mysql.cj.x.protobuf" };
        final Descriptors.FileDescriptor.InternalDescriptorAssigner assigner = (Descriptors.FileDescriptor.InternalDescriptorAssigner)new Descriptors.FileDescriptor.InternalDescriptorAssigner() {
            public ExtensionRegistry assignDescriptors(final Descriptors.FileDescriptor root) {
                MysqlxConnection.descriptor = root;
                return null;
            }
        };
        Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new Descriptors.FileDescriptor[] { MysqlxDatatypes.getDescriptor(), Mysqlx.getDescriptor() }, assigner);
        internal_static_Mysqlx_Connection_Capability_descriptor = getDescriptor().getMessageTypes().get(0);
        internal_static_Mysqlx_Connection_Capability_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(MysqlxConnection.internal_static_Mysqlx_Connection_Capability_descriptor, new String[] { "Name", "Value" });
        internal_static_Mysqlx_Connection_Capabilities_descriptor = getDescriptor().getMessageTypes().get(1);
        internal_static_Mysqlx_Connection_Capabilities_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(MysqlxConnection.internal_static_Mysqlx_Connection_Capabilities_descriptor, new String[] { "Capabilities" });
        internal_static_Mysqlx_Connection_CapabilitiesGet_descriptor = getDescriptor().getMessageTypes().get(2);
        internal_static_Mysqlx_Connection_CapabilitiesGet_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(MysqlxConnection.internal_static_Mysqlx_Connection_CapabilitiesGet_descriptor, new String[0]);
        internal_static_Mysqlx_Connection_CapabilitiesSet_descriptor = getDescriptor().getMessageTypes().get(3);
        internal_static_Mysqlx_Connection_CapabilitiesSet_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(MysqlxConnection.internal_static_Mysqlx_Connection_CapabilitiesSet_descriptor, new String[] { "Capabilities" });
        internal_static_Mysqlx_Connection_Close_descriptor = getDescriptor().getMessageTypes().get(4);
        internal_static_Mysqlx_Connection_Close_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(MysqlxConnection.internal_static_Mysqlx_Connection_Close_descriptor, new String[0]);
        final ExtensionRegistry registry = ExtensionRegistry.newInstance();
        registry.add((GeneratedMessage.GeneratedExtension)Mysqlx.clientMessageId);
        registry.add((GeneratedMessage.GeneratedExtension)Mysqlx.serverMessageId);
        Descriptors.FileDescriptor.internalUpdateFileDescriptor(MysqlxConnection.descriptor, registry);
        MysqlxDatatypes.getDescriptor();
        Mysqlx.getDescriptor();
    }
    
    public static final class Capability extends GeneratedMessageV3 implements CapabilityOrBuilder
    {
        private static final long serialVersionUID = 0L;
        private int bitField0_;
        public static final int NAME_FIELD_NUMBER = 1;
        private volatile Object name_;
        public static final int VALUE_FIELD_NUMBER = 2;
        private MysqlxDatatypes.Any value_;
        private byte memoizedIsInitialized;
        private static final Capability DEFAULT_INSTANCE;
        @Deprecated
        public static final Parser<Capability> PARSER;
        
        private Capability(final GeneratedMessageV3.Builder<?> builder) {
            super((GeneratedMessageV3.Builder)builder);
            this.memoizedIsInitialized = -1;
        }
        
        private Capability() {
            this.memoizedIsInitialized = -1;
            this.name_ = "";
        }
        
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }
        
        private Capability(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            this.name_ = bs;
                            continue;
                        }
                        case 18: {
                            MysqlxDatatypes.Any.Builder subBuilder = null;
                            if ((this.bitField0_ & 0x2) == 0x2) {
                                subBuilder = this.value_.toBuilder();
                            }
                            this.value_ = (MysqlxDatatypes.Any)input.readMessage((Parser)MysqlxDatatypes.Any.PARSER, extensionRegistry);
                            if (subBuilder != null) {
                                subBuilder.mergeFrom(this.value_);
                                this.value_ = subBuilder.buildPartial();
                            }
                            this.bitField0_ |= 0x2;
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
            return MysqlxConnection.internal_static_Mysqlx_Connection_Capability_descriptor;
        }
        
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return MysqlxConnection.internal_static_Mysqlx_Connection_Capability_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)Capability.class, (Class)Builder.class);
        }
        
        public boolean hasName() {
            return (this.bitField0_ & 0x1) == 0x1;
        }
        
        public String getName() {
            final Object ref = this.name_;
            if (ref instanceof String) {
                return (String)ref;
            }
            final ByteString bs = (ByteString)ref;
            final String s = bs.toStringUtf8();
            if (bs.isValidUtf8()) {
                this.name_ = s;
            }
            return s;
        }
        
        public ByteString getNameBytes() {
            final Object ref = this.name_;
            if (ref instanceof String) {
                final ByteString b = ByteString.copyFromUtf8((String)ref);
                return (ByteString)(this.name_ = b);
            }
            return (ByteString)ref;
        }
        
        public boolean hasValue() {
            return (this.bitField0_ & 0x2) == 0x2;
        }
        
        public MysqlxDatatypes.Any getValue() {
            return (this.value_ == null) ? MysqlxDatatypes.Any.getDefaultInstance() : this.value_;
        }
        
        public MysqlxDatatypes.AnyOrBuilder getValueOrBuilder() {
            return (this.value_ == null) ? MysqlxDatatypes.Any.getDefaultInstance() : this.value_;
        }
        
        public final boolean isInitialized() {
            final byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (!this.hasName()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            if (!this.hasValue()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            if (!this.getValue().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            this.memoizedIsInitialized = 1;
            return true;
        }
        
        public void writeTo(final CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 0x1) == 0x1) {
                GeneratedMessageV3.writeString(output, 1, this.name_);
            }
            if ((this.bitField0_ & 0x2) == 0x2) {
                output.writeMessage(2, (MessageLite)this.getValue());
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
                size += GeneratedMessageV3.computeStringSize(1, this.name_);
            }
            if ((this.bitField0_ & 0x2) == 0x2) {
                size += CodedOutputStream.computeMessageSize(2, (MessageLite)this.getValue());
            }
            size += this.unknownFields.getSerializedSize();
            return this.memoizedSize = size;
        }
        
        public boolean equals(final Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Capability)) {
                return super.equals(obj);
            }
            final Capability other = (Capability)obj;
            boolean result = true;
            result = (result && this.hasName() == other.hasName());
            if (this.hasName()) {
                result = (result && this.getName().equals(other.getName()));
            }
            result = (result && this.hasValue() == other.hasValue());
            if (this.hasValue()) {
                result = (result && this.getValue().equals(other.getValue()));
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
            if (this.hasName()) {
                hash = 37 * hash + 1;
                hash = 53 * hash + this.getName().hashCode();
            }
            if (this.hasValue()) {
                hash = 37 * hash + 2;
                hash = 53 * hash + this.getValue().hashCode();
            }
            hash = 29 * hash + this.unknownFields.hashCode();
            return this.memoizedHashCode = hash;
        }
        
        public static Capability parseFrom(final ByteBuffer data) throws InvalidProtocolBufferException {
            return (Capability)Capability.PARSER.parseFrom(data);
        }
        
        public static Capability parseFrom(final ByteBuffer data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Capability)Capability.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Capability parseFrom(final ByteString data) throws InvalidProtocolBufferException {
            return (Capability)Capability.PARSER.parseFrom(data);
        }
        
        public static Capability parseFrom(final ByteString data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Capability)Capability.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Capability parseFrom(final byte[] data) throws InvalidProtocolBufferException {
            return (Capability)Capability.PARSER.parseFrom(data);
        }
        
        public static Capability parseFrom(final byte[] data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Capability)Capability.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Capability parseFrom(final InputStream input) throws IOException {
            return (Capability)GeneratedMessageV3.parseWithIOException((Parser)Capability.PARSER, input);
        }
        
        public static Capability parseFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Capability)GeneratedMessageV3.parseWithIOException((Parser)Capability.PARSER, input, extensionRegistry);
        }
        
        public static Capability parseDelimitedFrom(final InputStream input) throws IOException {
            return (Capability)GeneratedMessageV3.parseDelimitedWithIOException((Parser)Capability.PARSER, input);
        }
        
        public static Capability parseDelimitedFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Capability)GeneratedMessageV3.parseDelimitedWithIOException((Parser)Capability.PARSER, input, extensionRegistry);
        }
        
        public static Capability parseFrom(final CodedInputStream input) throws IOException {
            return (Capability)GeneratedMessageV3.parseWithIOException((Parser)Capability.PARSER, input);
        }
        
        public static Capability parseFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Capability)GeneratedMessageV3.parseWithIOException((Parser)Capability.PARSER, input, extensionRegistry);
        }
        
        public Builder newBuilderForType() {
            return newBuilder();
        }
        
        public static Builder newBuilder() {
            return Capability.DEFAULT_INSTANCE.toBuilder();
        }
        
        public static Builder newBuilder(final Capability prototype) {
            return Capability.DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }
        
        public Builder toBuilder() {
            return (this == Capability.DEFAULT_INSTANCE) ? new Builder() : new Builder().mergeFrom(this);
        }
        
        protected Builder newBuilderForType(final GeneratedMessageV3.BuilderParent parent) {
            final Builder builder = new Builder(parent);
            return builder;
        }
        
        public static Capability getDefaultInstance() {
            return Capability.DEFAULT_INSTANCE;
        }
        
        public static Parser<Capability> parser() {
            return Capability.PARSER;
        }
        
        public Parser<Capability> getParserForType() {
            return Capability.PARSER;
        }
        
        public Capability getDefaultInstanceForType() {
            return Capability.DEFAULT_INSTANCE;
        }
        
        static {
            DEFAULT_INSTANCE = new Capability();
            PARSER = (Parser)new AbstractParser<Capability>() {
                public Capability parsePartialFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new Capability(input, extensionRegistry);
                }
            };
        }
        
        public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements CapabilityOrBuilder
        {
            private int bitField0_;
            private Object name_;
            private MysqlxDatatypes.Any value_;
            private SingleFieldBuilderV3<MysqlxDatatypes.Any, MysqlxDatatypes.Any.Builder, MysqlxDatatypes.AnyOrBuilder> valueBuilder_;
            
            public static final Descriptors.Descriptor getDescriptor() {
                return MysqlxConnection.internal_static_Mysqlx_Connection_Capability_descriptor;
            }
            
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return MysqlxConnection.internal_static_Mysqlx_Connection_Capability_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)Capability.class, (Class)Builder.class);
            }
            
            private Builder() {
                this.name_ = "";
                this.value_ = null;
                this.maybeForceBuilderInitialization();
            }
            
            private Builder(final GeneratedMessageV3.BuilderParent parent) {
                super(parent);
                this.name_ = "";
                this.value_ = null;
                this.maybeForceBuilderInitialization();
            }
            
            private void maybeForceBuilderInitialization() {
                if (Capability.alwaysUseFieldBuilders) {
                    this.getValueFieldBuilder();
                }
            }
            
            public Builder clear() {
                super.clear();
                this.name_ = "";
                this.bitField0_ &= 0xFFFFFFFE;
                if (this.valueBuilder_ == null) {
                    this.value_ = null;
                }
                else {
                    this.valueBuilder_.clear();
                }
                this.bitField0_ &= 0xFFFFFFFD;
                return this;
            }
            
            public Descriptors.Descriptor getDescriptorForType() {
                return MysqlxConnection.internal_static_Mysqlx_Connection_Capability_descriptor;
            }
            
            public Capability getDefaultInstanceForType() {
                return Capability.getDefaultInstance();
            }
            
            public Capability build() {
                final Capability result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw newUninitializedMessageException((Message)result);
                }
                return result;
            }
            
            public Capability buildPartial() {
                final Capability result = new Capability((GeneratedMessageV3.Builder)this);
                final int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 0x1) == 0x1) {
                    to_bitField0_ |= 0x1;
                }
                result.name_ = this.name_;
                if ((from_bitField0_ & 0x2) == 0x2) {
                    to_bitField0_ |= 0x2;
                }
                if (this.valueBuilder_ == null) {
                    result.value_ = this.value_;
                }
                else {
                    result.value_ = (MysqlxDatatypes.Any)this.valueBuilder_.build();
                }
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
                if (other instanceof Capability) {
                    return this.mergeFrom((Capability)other);
                }
                super.mergeFrom(other);
                return this;
            }
            
            public Builder mergeFrom(final Capability other) {
                if (other == Capability.getDefaultInstance()) {
                    return this;
                }
                if (other.hasName()) {
                    this.bitField0_ |= 0x1;
                    this.name_ = other.name_;
                    this.onChanged();
                }
                if (other.hasValue()) {
                    this.mergeValue(other.getValue());
                }
                this.mergeUnknownFields(other.unknownFields);
                this.onChanged();
                return this;
            }
            
            public final boolean isInitialized() {
                return this.hasName() && this.hasValue() && this.getValue().isInitialized();
            }
            
            public Builder mergeFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
                Capability parsedMessage = null;
                try {
                    parsedMessage = (Capability)Capability.PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (Capability)e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                }
                finally {
                    if (parsedMessage != null) {
                        this.mergeFrom(parsedMessage);
                    }
                }
                return this;
            }
            
            public boolean hasName() {
                return (this.bitField0_ & 0x1) == 0x1;
            }
            
            public String getName() {
                final Object ref = this.name_;
                if (!(ref instanceof String)) {
                    final ByteString bs = (ByteString)ref;
                    final String s = bs.toStringUtf8();
                    if (bs.isValidUtf8()) {
                        this.name_ = s;
                    }
                    return s;
                }
                return (String)ref;
            }
            
            public ByteString getNameBytes() {
                final Object ref = this.name_;
                if (ref instanceof String) {
                    final ByteString b = ByteString.copyFromUtf8((String)ref);
                    return (ByteString)(this.name_ = b);
                }
                return (ByteString)ref;
            }
            
            public Builder setName(final String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 0x1;
                this.name_ = value;
                this.onChanged();
                return this;
            }
            
            public Builder clearName() {
                this.bitField0_ &= 0xFFFFFFFE;
                this.name_ = Capability.getDefaultInstance().getName();
                this.onChanged();
                return this;
            }
            
            public Builder setNameBytes(final ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 0x1;
                this.name_ = value;
                this.onChanged();
                return this;
            }
            
            public boolean hasValue() {
                return (this.bitField0_ & 0x2) == 0x2;
            }
            
            public MysqlxDatatypes.Any getValue() {
                if (this.valueBuilder_ == null) {
                    return (this.value_ == null) ? MysqlxDatatypes.Any.getDefaultInstance() : this.value_;
                }
                return (MysqlxDatatypes.Any)this.valueBuilder_.getMessage();
            }
            
            public Builder setValue(final MysqlxDatatypes.Any value) {
                if (this.valueBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.value_ = value;
                    this.onChanged();
                }
                else {
                    this.valueBuilder_.setMessage((AbstractMessage)value);
                }
                this.bitField0_ |= 0x2;
                return this;
            }
            
            public Builder setValue(final MysqlxDatatypes.Any.Builder builderForValue) {
                if (this.valueBuilder_ == null) {
                    this.value_ = builderForValue.build();
                    this.onChanged();
                }
                else {
                    this.valueBuilder_.setMessage((AbstractMessage)builderForValue.build());
                }
                this.bitField0_ |= 0x2;
                return this;
            }
            
            public Builder mergeValue(final MysqlxDatatypes.Any value) {
                if (this.valueBuilder_ == null) {
                    if ((this.bitField0_ & 0x2) == 0x2 && this.value_ != null && this.value_ != MysqlxDatatypes.Any.getDefaultInstance()) {
                        this.value_ = MysqlxDatatypes.Any.newBuilder(this.value_).mergeFrom(value).buildPartial();
                    }
                    else {
                        this.value_ = value;
                    }
                    this.onChanged();
                }
                else {
                    this.valueBuilder_.mergeFrom((AbstractMessage)value);
                }
                this.bitField0_ |= 0x2;
                return this;
            }
            
            public Builder clearValue() {
                if (this.valueBuilder_ == null) {
                    this.value_ = null;
                    this.onChanged();
                }
                else {
                    this.valueBuilder_.clear();
                }
                this.bitField0_ &= 0xFFFFFFFD;
                return this;
            }
            
            public MysqlxDatatypes.Any.Builder getValueBuilder() {
                this.bitField0_ |= 0x2;
                this.onChanged();
                return (MysqlxDatatypes.Any.Builder)this.getValueFieldBuilder().getBuilder();
            }
            
            public MysqlxDatatypes.AnyOrBuilder getValueOrBuilder() {
                if (this.valueBuilder_ != null) {
                    return (MysqlxDatatypes.AnyOrBuilder)this.valueBuilder_.getMessageOrBuilder();
                }
                return (this.value_ == null) ? MysqlxDatatypes.Any.getDefaultInstance() : this.value_;
            }
            
            private SingleFieldBuilderV3<MysqlxDatatypes.Any, MysqlxDatatypes.Any.Builder, MysqlxDatatypes.AnyOrBuilder> getValueFieldBuilder() {
                if (this.valueBuilder_ == null) {
                    this.valueBuilder_ = (SingleFieldBuilderV3<MysqlxDatatypes.Any, MysqlxDatatypes.Any.Builder, MysqlxDatatypes.AnyOrBuilder>)new SingleFieldBuilderV3((AbstractMessage)this.getValue(), (AbstractMessage.BuilderParent)this.getParentForChildren(), this.isClean());
                    this.value_ = null;
                }
                return this.valueBuilder_;
            }
            
            public final Builder setUnknownFields(final UnknownFieldSet unknownFields) {
                return (Builder)super.setUnknownFields(unknownFields);
            }
            
            public final Builder mergeUnknownFields(final UnknownFieldSet unknownFields) {
                return (Builder)super.mergeUnknownFields(unknownFields);
            }
        }
    }
    
    public static final class Capabilities extends GeneratedMessageV3 implements CapabilitiesOrBuilder
    {
        private static final long serialVersionUID = 0L;
        public static final int CAPABILITIES_FIELD_NUMBER = 1;
        private List<Capability> capabilities_;
        private byte memoizedIsInitialized;
        private static final Capabilities DEFAULT_INSTANCE;
        @Deprecated
        public static final Parser<Capabilities> PARSER;
        
        private Capabilities(final GeneratedMessageV3.Builder<?> builder) {
            super((GeneratedMessageV3.Builder)builder);
            this.memoizedIsInitialized = -1;
        }
        
        private Capabilities() {
            this.memoizedIsInitialized = -1;
            this.capabilities_ = Collections.emptyList();
        }
        
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }
        
        private Capabilities(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this();
            if (extensionRegistry == null) {
                throw new NullPointerException();
            }
            int mutable_bitField0_ = 0;
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
                            if ((mutable_bitField0_ & 0x1) != 0x1) {
                                this.capabilities_ = new ArrayList<Capability>();
                                mutable_bitField0_ |= 0x1;
                            }
                            this.capabilities_.add((Capability)input.readMessage((Parser)Capability.PARSER, extensionRegistry));
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
                if ((mutable_bitField0_ & 0x1) == 0x1) {
                    this.capabilities_ = Collections.unmodifiableList((List<? extends Capability>)this.capabilities_);
                }
                this.unknownFields = unknownFields.build();
                this.makeExtensionsImmutable();
            }
        }
        
        public static final Descriptors.Descriptor getDescriptor() {
            return MysqlxConnection.internal_static_Mysqlx_Connection_Capabilities_descriptor;
        }
        
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return MysqlxConnection.internal_static_Mysqlx_Connection_Capabilities_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)Capabilities.class, (Class)Builder.class);
        }
        
        public List<Capability> getCapabilitiesList() {
            return this.capabilities_;
        }
        
        public List<? extends CapabilityOrBuilder> getCapabilitiesOrBuilderList() {
            return this.capabilities_;
        }
        
        public int getCapabilitiesCount() {
            return this.capabilities_.size();
        }
        
        public Capability getCapabilities(final int index) {
            return this.capabilities_.get(index);
        }
        
        public CapabilityOrBuilder getCapabilitiesOrBuilder(final int index) {
            return this.capabilities_.get(index);
        }
        
        public final boolean isInitialized() {
            final byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            for (int i = 0; i < this.getCapabilitiesCount(); ++i) {
                if (!this.getCapabilities(i).isInitialized()) {
                    this.memoizedIsInitialized = 0;
                    return false;
                }
            }
            this.memoizedIsInitialized = 1;
            return true;
        }
        
        public void writeTo(final CodedOutputStream output) throws IOException {
            for (int i = 0; i < this.capabilities_.size(); ++i) {
                output.writeMessage(1, (MessageLite)this.capabilities_.get(i));
            }
            this.unknownFields.writeTo(output);
        }
        
        public int getSerializedSize() {
            int size = this.memoizedSize;
            if (size != -1) {
                return size;
            }
            size = 0;
            for (int i = 0; i < this.capabilities_.size(); ++i) {
                size += CodedOutputStream.computeMessageSize(1, (MessageLite)this.capabilities_.get(i));
            }
            size += this.unknownFields.getSerializedSize();
            return this.memoizedSize = size;
        }
        
        public boolean equals(final Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Capabilities)) {
                return super.equals(obj);
            }
            final Capabilities other = (Capabilities)obj;
            boolean result = true;
            result = (result && this.getCapabilitiesList().equals(other.getCapabilitiesList()));
            result = (result && this.unknownFields.equals((Object)other.unknownFields));
            return result;
        }
        
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + getDescriptor().hashCode();
            if (this.getCapabilitiesCount() > 0) {
                hash = 37 * hash + 1;
                hash = 53 * hash + this.getCapabilitiesList().hashCode();
            }
            hash = 29 * hash + this.unknownFields.hashCode();
            return this.memoizedHashCode = hash;
        }
        
        public static Capabilities parseFrom(final ByteBuffer data) throws InvalidProtocolBufferException {
            return (Capabilities)Capabilities.PARSER.parseFrom(data);
        }
        
        public static Capabilities parseFrom(final ByteBuffer data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Capabilities)Capabilities.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Capabilities parseFrom(final ByteString data) throws InvalidProtocolBufferException {
            return (Capabilities)Capabilities.PARSER.parseFrom(data);
        }
        
        public static Capabilities parseFrom(final ByteString data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Capabilities)Capabilities.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Capabilities parseFrom(final byte[] data) throws InvalidProtocolBufferException {
            return (Capabilities)Capabilities.PARSER.parseFrom(data);
        }
        
        public static Capabilities parseFrom(final byte[] data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Capabilities)Capabilities.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Capabilities parseFrom(final InputStream input) throws IOException {
            return (Capabilities)GeneratedMessageV3.parseWithIOException((Parser)Capabilities.PARSER, input);
        }
        
        public static Capabilities parseFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Capabilities)GeneratedMessageV3.parseWithIOException((Parser)Capabilities.PARSER, input, extensionRegistry);
        }
        
        public static Capabilities parseDelimitedFrom(final InputStream input) throws IOException {
            return (Capabilities)GeneratedMessageV3.parseDelimitedWithIOException((Parser)Capabilities.PARSER, input);
        }
        
        public static Capabilities parseDelimitedFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Capabilities)GeneratedMessageV3.parseDelimitedWithIOException((Parser)Capabilities.PARSER, input, extensionRegistry);
        }
        
        public static Capabilities parseFrom(final CodedInputStream input) throws IOException {
            return (Capabilities)GeneratedMessageV3.parseWithIOException((Parser)Capabilities.PARSER, input);
        }
        
        public static Capabilities parseFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Capabilities)GeneratedMessageV3.parseWithIOException((Parser)Capabilities.PARSER, input, extensionRegistry);
        }
        
        public Builder newBuilderForType() {
            return newBuilder();
        }
        
        public static Builder newBuilder() {
            return Capabilities.DEFAULT_INSTANCE.toBuilder();
        }
        
        public static Builder newBuilder(final Capabilities prototype) {
            return Capabilities.DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }
        
        public Builder toBuilder() {
            return (this == Capabilities.DEFAULT_INSTANCE) ? new Builder() : new Builder().mergeFrom(this);
        }
        
        protected Builder newBuilderForType(final GeneratedMessageV3.BuilderParent parent) {
            final Builder builder = new Builder(parent);
            return builder;
        }
        
        public static Capabilities getDefaultInstance() {
            return Capabilities.DEFAULT_INSTANCE;
        }
        
        public static Parser<Capabilities> parser() {
            return Capabilities.PARSER;
        }
        
        public Parser<Capabilities> getParserForType() {
            return Capabilities.PARSER;
        }
        
        public Capabilities getDefaultInstanceForType() {
            return Capabilities.DEFAULT_INSTANCE;
        }
        
        static {
            DEFAULT_INSTANCE = new Capabilities();
            PARSER = (Parser)new AbstractParser<Capabilities>() {
                public Capabilities parsePartialFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new Capabilities(input, extensionRegistry);
                }
            };
        }
        
        public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements CapabilitiesOrBuilder
        {
            private int bitField0_;
            private List<Capability> capabilities_;
            private RepeatedFieldBuilderV3<Capability, Capability.Builder, CapabilityOrBuilder> capabilitiesBuilder_;
            
            public static final Descriptors.Descriptor getDescriptor() {
                return MysqlxConnection.internal_static_Mysqlx_Connection_Capabilities_descriptor;
            }
            
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return MysqlxConnection.internal_static_Mysqlx_Connection_Capabilities_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)Capabilities.class, (Class)Builder.class);
            }
            
            private Builder() {
                this.capabilities_ = Collections.emptyList();
                this.maybeForceBuilderInitialization();
            }
            
            private Builder(final GeneratedMessageV3.BuilderParent parent) {
                super(parent);
                this.capabilities_ = Collections.emptyList();
                this.maybeForceBuilderInitialization();
            }
            
            private void maybeForceBuilderInitialization() {
                if (Capabilities.alwaysUseFieldBuilders) {
                    this.getCapabilitiesFieldBuilder();
                }
            }
            
            public Builder clear() {
                super.clear();
                if (this.capabilitiesBuilder_ == null) {
                    this.capabilities_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFFE;
                }
                else {
                    this.capabilitiesBuilder_.clear();
                }
                return this;
            }
            
            public Descriptors.Descriptor getDescriptorForType() {
                return MysqlxConnection.internal_static_Mysqlx_Connection_Capabilities_descriptor;
            }
            
            public Capabilities getDefaultInstanceForType() {
                return Capabilities.getDefaultInstance();
            }
            
            public Capabilities build() {
                final Capabilities result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw newUninitializedMessageException((Message)result);
                }
                return result;
            }
            
            public Capabilities buildPartial() {
                final Capabilities result = new Capabilities((GeneratedMessageV3.Builder)this);
                final int from_bitField0_ = this.bitField0_;
                if (this.capabilitiesBuilder_ == null) {
                    if ((this.bitField0_ & 0x1) == 0x1) {
                        this.capabilities_ = Collections.unmodifiableList((List<? extends Capability>)this.capabilities_);
                        this.bitField0_ &= 0xFFFFFFFE;
                    }
                    result.capabilities_ = this.capabilities_;
                }
                else {
                    result.capabilities_ = (List<Capability>)this.capabilitiesBuilder_.build();
                }
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
                if (other instanceof Capabilities) {
                    return this.mergeFrom((Capabilities)other);
                }
                super.mergeFrom(other);
                return this;
            }
            
            public Builder mergeFrom(final Capabilities other) {
                if (other == Capabilities.getDefaultInstance()) {
                    return this;
                }
                if (this.capabilitiesBuilder_ == null) {
                    if (!other.capabilities_.isEmpty()) {
                        if (this.capabilities_.isEmpty()) {
                            this.capabilities_ = other.capabilities_;
                            this.bitField0_ &= 0xFFFFFFFE;
                        }
                        else {
                            this.ensureCapabilitiesIsMutable();
                            this.capabilities_.addAll(other.capabilities_);
                        }
                        this.onChanged();
                    }
                }
                else if (!other.capabilities_.isEmpty()) {
                    if (this.capabilitiesBuilder_.isEmpty()) {
                        this.capabilitiesBuilder_.dispose();
                        this.capabilitiesBuilder_ = null;
                        this.capabilities_ = other.capabilities_;
                        this.bitField0_ &= 0xFFFFFFFE;
                        this.capabilitiesBuilder_ = (Capabilities.alwaysUseFieldBuilders ? this.getCapabilitiesFieldBuilder() : null);
                    }
                    else {
                        this.capabilitiesBuilder_.addAllMessages((Iterable)other.capabilities_);
                    }
                }
                this.mergeUnknownFields(other.unknownFields);
                this.onChanged();
                return this;
            }
            
            public final boolean isInitialized() {
                for (int i = 0; i < this.getCapabilitiesCount(); ++i) {
                    if (!this.getCapabilities(i).isInitialized()) {
                        return false;
                    }
                }
                return true;
            }
            
            public Builder mergeFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
                Capabilities parsedMessage = null;
                try {
                    parsedMessage = (Capabilities)Capabilities.PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (Capabilities)e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                }
                finally {
                    if (parsedMessage != null) {
                        this.mergeFrom(parsedMessage);
                    }
                }
                return this;
            }
            
            private void ensureCapabilitiesIsMutable() {
                if ((this.bitField0_ & 0x1) != 0x1) {
                    this.capabilities_ = new ArrayList<Capability>(this.capabilities_);
                    this.bitField0_ |= 0x1;
                }
            }
            
            public List<Capability> getCapabilitiesList() {
                if (this.capabilitiesBuilder_ == null) {
                    return Collections.unmodifiableList((List<? extends Capability>)this.capabilities_);
                }
                return (List<Capability>)this.capabilitiesBuilder_.getMessageList();
            }
            
            public int getCapabilitiesCount() {
                if (this.capabilitiesBuilder_ == null) {
                    return this.capabilities_.size();
                }
                return this.capabilitiesBuilder_.getCount();
            }
            
            public Capability getCapabilities(final int index) {
                if (this.capabilitiesBuilder_ == null) {
                    return this.capabilities_.get(index);
                }
                return (Capability)this.capabilitiesBuilder_.getMessage(index);
            }
            
            public Builder setCapabilities(final int index, final Capability value) {
                if (this.capabilitiesBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureCapabilitiesIsMutable();
                    this.capabilities_.set(index, value);
                    this.onChanged();
                }
                else {
                    this.capabilitiesBuilder_.setMessage(index, (AbstractMessage)value);
                }
                return this;
            }
            
            public Builder setCapabilities(final int index, final Capability.Builder builderForValue) {
                if (this.capabilitiesBuilder_ == null) {
                    this.ensureCapabilitiesIsMutable();
                    this.capabilities_.set(index, builderForValue.build());
                    this.onChanged();
                }
                else {
                    this.capabilitiesBuilder_.setMessage(index, (AbstractMessage)builderForValue.build());
                }
                return this;
            }
            
            public Builder addCapabilities(final Capability value) {
                if (this.capabilitiesBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureCapabilitiesIsMutable();
                    this.capabilities_.add(value);
                    this.onChanged();
                }
                else {
                    this.capabilitiesBuilder_.addMessage((AbstractMessage)value);
                }
                return this;
            }
            
            public Builder addCapabilities(final int index, final Capability value) {
                if (this.capabilitiesBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureCapabilitiesIsMutable();
                    this.capabilities_.add(index, value);
                    this.onChanged();
                }
                else {
                    this.capabilitiesBuilder_.addMessage(index, (AbstractMessage)value);
                }
                return this;
            }
            
            public Builder addCapabilities(final Capability.Builder builderForValue) {
                if (this.capabilitiesBuilder_ == null) {
                    this.ensureCapabilitiesIsMutable();
                    this.capabilities_.add(builderForValue.build());
                    this.onChanged();
                }
                else {
                    this.capabilitiesBuilder_.addMessage((AbstractMessage)builderForValue.build());
                }
                return this;
            }
            
            public Builder addCapabilities(final int index, final Capability.Builder builderForValue) {
                if (this.capabilitiesBuilder_ == null) {
                    this.ensureCapabilitiesIsMutable();
                    this.capabilities_.add(index, builderForValue.build());
                    this.onChanged();
                }
                else {
                    this.capabilitiesBuilder_.addMessage(index, (AbstractMessage)builderForValue.build());
                }
                return this;
            }
            
            public Builder addAllCapabilities(final Iterable<? extends Capability> values) {
                if (this.capabilitiesBuilder_ == null) {
                    this.ensureCapabilitiesIsMutable();
                    AbstractMessageLite.Builder.addAll((Iterable)values, (List)this.capabilities_);
                    this.onChanged();
                }
                else {
                    this.capabilitiesBuilder_.addAllMessages((Iterable)values);
                }
                return this;
            }
            
            public Builder clearCapabilities() {
                if (this.capabilitiesBuilder_ == null) {
                    this.capabilities_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFFE;
                    this.onChanged();
                }
                else {
                    this.capabilitiesBuilder_.clear();
                }
                return this;
            }
            
            public Builder removeCapabilities(final int index) {
                if (this.capabilitiesBuilder_ == null) {
                    this.ensureCapabilitiesIsMutable();
                    this.capabilities_.remove(index);
                    this.onChanged();
                }
                else {
                    this.capabilitiesBuilder_.remove(index);
                }
                return this;
            }
            
            public Capability.Builder getCapabilitiesBuilder(final int index) {
                return (Capability.Builder)this.getCapabilitiesFieldBuilder().getBuilder(index);
            }
            
            public CapabilityOrBuilder getCapabilitiesOrBuilder(final int index) {
                if (this.capabilitiesBuilder_ == null) {
                    return this.capabilities_.get(index);
                }
                return (CapabilityOrBuilder)this.capabilitiesBuilder_.getMessageOrBuilder(index);
            }
            
            public List<? extends CapabilityOrBuilder> getCapabilitiesOrBuilderList() {
                if (this.capabilitiesBuilder_ != null) {
                    return (List<? extends CapabilityOrBuilder>)this.capabilitiesBuilder_.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList((List<? extends CapabilityOrBuilder>)this.capabilities_);
            }
            
            public Capability.Builder addCapabilitiesBuilder() {
                return (Capability.Builder)this.getCapabilitiesFieldBuilder().addBuilder((AbstractMessage)Capability.getDefaultInstance());
            }
            
            public Capability.Builder addCapabilitiesBuilder(final int index) {
                return (Capability.Builder)this.getCapabilitiesFieldBuilder().addBuilder(index, (AbstractMessage)Capability.getDefaultInstance());
            }
            
            public List<Capability.Builder> getCapabilitiesBuilderList() {
                return (List<Capability.Builder>)this.getCapabilitiesFieldBuilder().getBuilderList();
            }
            
            private RepeatedFieldBuilderV3<Capability, Capability.Builder, CapabilityOrBuilder> getCapabilitiesFieldBuilder() {
                if (this.capabilitiesBuilder_ == null) {
                    this.capabilitiesBuilder_ = (RepeatedFieldBuilderV3<Capability, Capability.Builder, CapabilityOrBuilder>)new RepeatedFieldBuilderV3((List)this.capabilities_, (this.bitField0_ & 0x1) == 0x1, (AbstractMessage.BuilderParent)this.getParentForChildren(), this.isClean());
                    this.capabilities_ = null;
                }
                return this.capabilitiesBuilder_;
            }
            
            public final Builder setUnknownFields(final UnknownFieldSet unknownFields) {
                return (Builder)super.setUnknownFields(unknownFields);
            }
            
            public final Builder mergeUnknownFields(final UnknownFieldSet unknownFields) {
                return (Builder)super.mergeUnknownFields(unknownFields);
            }
        }
    }
    
    public static final class CapabilitiesGet extends GeneratedMessageV3 implements CapabilitiesGetOrBuilder
    {
        private static final long serialVersionUID = 0L;
        private byte memoizedIsInitialized;
        private static final CapabilitiesGet DEFAULT_INSTANCE;
        @Deprecated
        public static final Parser<CapabilitiesGet> PARSER;
        
        private CapabilitiesGet(final GeneratedMessageV3.Builder<?> builder) {
            super((GeneratedMessageV3.Builder)builder);
            this.memoizedIsInitialized = -1;
        }
        
        private CapabilitiesGet() {
            this.memoizedIsInitialized = -1;
        }
        
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }
        
        private CapabilitiesGet(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
            return MysqlxConnection.internal_static_Mysqlx_Connection_CapabilitiesGet_descriptor;
        }
        
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return MysqlxConnection.internal_static_Mysqlx_Connection_CapabilitiesGet_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)CapabilitiesGet.class, (Class)Builder.class);
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
            if (!(obj instanceof CapabilitiesGet)) {
                return super.equals(obj);
            }
            final CapabilitiesGet other = (CapabilitiesGet)obj;
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
        
        public static CapabilitiesGet parseFrom(final ByteBuffer data) throws InvalidProtocolBufferException {
            return (CapabilitiesGet)CapabilitiesGet.PARSER.parseFrom(data);
        }
        
        public static CapabilitiesGet parseFrom(final ByteBuffer data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (CapabilitiesGet)CapabilitiesGet.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static CapabilitiesGet parseFrom(final ByteString data) throws InvalidProtocolBufferException {
            return (CapabilitiesGet)CapabilitiesGet.PARSER.parseFrom(data);
        }
        
        public static CapabilitiesGet parseFrom(final ByteString data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (CapabilitiesGet)CapabilitiesGet.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static CapabilitiesGet parseFrom(final byte[] data) throws InvalidProtocolBufferException {
            return (CapabilitiesGet)CapabilitiesGet.PARSER.parseFrom(data);
        }
        
        public static CapabilitiesGet parseFrom(final byte[] data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (CapabilitiesGet)CapabilitiesGet.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static CapabilitiesGet parseFrom(final InputStream input) throws IOException {
            return (CapabilitiesGet)GeneratedMessageV3.parseWithIOException((Parser)CapabilitiesGet.PARSER, input);
        }
        
        public static CapabilitiesGet parseFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (CapabilitiesGet)GeneratedMessageV3.parseWithIOException((Parser)CapabilitiesGet.PARSER, input, extensionRegistry);
        }
        
        public static CapabilitiesGet parseDelimitedFrom(final InputStream input) throws IOException {
            return (CapabilitiesGet)GeneratedMessageV3.parseDelimitedWithIOException((Parser)CapabilitiesGet.PARSER, input);
        }
        
        public static CapabilitiesGet parseDelimitedFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (CapabilitiesGet)GeneratedMessageV3.parseDelimitedWithIOException((Parser)CapabilitiesGet.PARSER, input, extensionRegistry);
        }
        
        public static CapabilitiesGet parseFrom(final CodedInputStream input) throws IOException {
            return (CapabilitiesGet)GeneratedMessageV3.parseWithIOException((Parser)CapabilitiesGet.PARSER, input);
        }
        
        public static CapabilitiesGet parseFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (CapabilitiesGet)GeneratedMessageV3.parseWithIOException((Parser)CapabilitiesGet.PARSER, input, extensionRegistry);
        }
        
        public Builder newBuilderForType() {
            return newBuilder();
        }
        
        public static Builder newBuilder() {
            return CapabilitiesGet.DEFAULT_INSTANCE.toBuilder();
        }
        
        public static Builder newBuilder(final CapabilitiesGet prototype) {
            return CapabilitiesGet.DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }
        
        public Builder toBuilder() {
            return (this == CapabilitiesGet.DEFAULT_INSTANCE) ? new Builder() : new Builder().mergeFrom(this);
        }
        
        protected Builder newBuilderForType(final GeneratedMessageV3.BuilderParent parent) {
            final Builder builder = new Builder(parent);
            return builder;
        }
        
        public static CapabilitiesGet getDefaultInstance() {
            return CapabilitiesGet.DEFAULT_INSTANCE;
        }
        
        public static Parser<CapabilitiesGet> parser() {
            return CapabilitiesGet.PARSER;
        }
        
        public Parser<CapabilitiesGet> getParserForType() {
            return CapabilitiesGet.PARSER;
        }
        
        public CapabilitiesGet getDefaultInstanceForType() {
            return CapabilitiesGet.DEFAULT_INSTANCE;
        }
        
        static {
            DEFAULT_INSTANCE = new CapabilitiesGet();
            PARSER = (Parser)new AbstractParser<CapabilitiesGet>() {
                public CapabilitiesGet parsePartialFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new CapabilitiesGet(input, extensionRegistry);
                }
            };
        }
        
        public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements CapabilitiesGetOrBuilder
        {
            public static final Descriptors.Descriptor getDescriptor() {
                return MysqlxConnection.internal_static_Mysqlx_Connection_CapabilitiesGet_descriptor;
            }
            
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return MysqlxConnection.internal_static_Mysqlx_Connection_CapabilitiesGet_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)CapabilitiesGet.class, (Class)Builder.class);
            }
            
            private Builder() {
                this.maybeForceBuilderInitialization();
            }
            
            private Builder(final GeneratedMessageV3.BuilderParent parent) {
                super(parent);
                this.maybeForceBuilderInitialization();
            }
            
            private void maybeForceBuilderInitialization() {
                if (CapabilitiesGet.alwaysUseFieldBuilders) {}
            }
            
            public Builder clear() {
                super.clear();
                return this;
            }
            
            public Descriptors.Descriptor getDescriptorForType() {
                return MysqlxConnection.internal_static_Mysqlx_Connection_CapabilitiesGet_descriptor;
            }
            
            public CapabilitiesGet getDefaultInstanceForType() {
                return CapabilitiesGet.getDefaultInstance();
            }
            
            public CapabilitiesGet build() {
                final CapabilitiesGet result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw newUninitializedMessageException((Message)result);
                }
                return result;
            }
            
            public CapabilitiesGet buildPartial() {
                final CapabilitiesGet result = new CapabilitiesGet((GeneratedMessageV3.Builder)this);
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
                if (other instanceof CapabilitiesGet) {
                    return this.mergeFrom((CapabilitiesGet)other);
                }
                super.mergeFrom(other);
                return this;
            }
            
            public Builder mergeFrom(final CapabilitiesGet other) {
                if (other == CapabilitiesGet.getDefaultInstance()) {
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
                CapabilitiesGet parsedMessage = null;
                try {
                    parsedMessage = (CapabilitiesGet)CapabilitiesGet.PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (CapabilitiesGet)e.getUnfinishedMessage();
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
    
    public static final class CapabilitiesSet extends GeneratedMessageV3 implements CapabilitiesSetOrBuilder
    {
        private static final long serialVersionUID = 0L;
        private int bitField0_;
        public static final int CAPABILITIES_FIELD_NUMBER = 1;
        private Capabilities capabilities_;
        private byte memoizedIsInitialized;
        private static final CapabilitiesSet DEFAULT_INSTANCE;
        @Deprecated
        public static final Parser<CapabilitiesSet> PARSER;
        
        private CapabilitiesSet(final GeneratedMessageV3.Builder<?> builder) {
            super((GeneratedMessageV3.Builder)builder);
            this.memoizedIsInitialized = -1;
        }
        
        private CapabilitiesSet() {
            this.memoizedIsInitialized = -1;
        }
        
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }
        
        private CapabilitiesSet(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            Capabilities.Builder subBuilder = null;
                            if ((this.bitField0_ & 0x1) == 0x1) {
                                subBuilder = this.capabilities_.toBuilder();
                            }
                            this.capabilities_ = (Capabilities)input.readMessage((Parser)Capabilities.PARSER, extensionRegistry);
                            if (subBuilder != null) {
                                subBuilder.mergeFrom(this.capabilities_);
                                this.capabilities_ = subBuilder.buildPartial();
                            }
                            this.bitField0_ |= 0x1;
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
            return MysqlxConnection.internal_static_Mysqlx_Connection_CapabilitiesSet_descriptor;
        }
        
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return MysqlxConnection.internal_static_Mysqlx_Connection_CapabilitiesSet_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)CapabilitiesSet.class, (Class)Builder.class);
        }
        
        public boolean hasCapabilities() {
            return (this.bitField0_ & 0x1) == 0x1;
        }
        
        public Capabilities getCapabilities() {
            return (this.capabilities_ == null) ? Capabilities.getDefaultInstance() : this.capabilities_;
        }
        
        public CapabilitiesOrBuilder getCapabilitiesOrBuilder() {
            return (this.capabilities_ == null) ? Capabilities.getDefaultInstance() : this.capabilities_;
        }
        
        public final boolean isInitialized() {
            final byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (!this.hasCapabilities()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            if (!this.getCapabilities().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            this.memoizedIsInitialized = 1;
            return true;
        }
        
        public void writeTo(final CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 0x1) == 0x1) {
                output.writeMessage(1, (MessageLite)this.getCapabilities());
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
                size += CodedOutputStream.computeMessageSize(1, (MessageLite)this.getCapabilities());
            }
            size += this.unknownFields.getSerializedSize();
            return this.memoizedSize = size;
        }
        
        public boolean equals(final Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof CapabilitiesSet)) {
                return super.equals(obj);
            }
            final CapabilitiesSet other = (CapabilitiesSet)obj;
            boolean result = true;
            result = (result && this.hasCapabilities() == other.hasCapabilities());
            if (this.hasCapabilities()) {
                result = (result && this.getCapabilities().equals(other.getCapabilities()));
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
            if (this.hasCapabilities()) {
                hash = 37 * hash + 1;
                hash = 53 * hash + this.getCapabilities().hashCode();
            }
            hash = 29 * hash + this.unknownFields.hashCode();
            return this.memoizedHashCode = hash;
        }
        
        public static CapabilitiesSet parseFrom(final ByteBuffer data) throws InvalidProtocolBufferException {
            return (CapabilitiesSet)CapabilitiesSet.PARSER.parseFrom(data);
        }
        
        public static CapabilitiesSet parseFrom(final ByteBuffer data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (CapabilitiesSet)CapabilitiesSet.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static CapabilitiesSet parseFrom(final ByteString data) throws InvalidProtocolBufferException {
            return (CapabilitiesSet)CapabilitiesSet.PARSER.parseFrom(data);
        }
        
        public static CapabilitiesSet parseFrom(final ByteString data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (CapabilitiesSet)CapabilitiesSet.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static CapabilitiesSet parseFrom(final byte[] data) throws InvalidProtocolBufferException {
            return (CapabilitiesSet)CapabilitiesSet.PARSER.parseFrom(data);
        }
        
        public static CapabilitiesSet parseFrom(final byte[] data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (CapabilitiesSet)CapabilitiesSet.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static CapabilitiesSet parseFrom(final InputStream input) throws IOException {
            return (CapabilitiesSet)GeneratedMessageV3.parseWithIOException((Parser)CapabilitiesSet.PARSER, input);
        }
        
        public static CapabilitiesSet parseFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (CapabilitiesSet)GeneratedMessageV3.parseWithIOException((Parser)CapabilitiesSet.PARSER, input, extensionRegistry);
        }
        
        public static CapabilitiesSet parseDelimitedFrom(final InputStream input) throws IOException {
            return (CapabilitiesSet)GeneratedMessageV3.parseDelimitedWithIOException((Parser)CapabilitiesSet.PARSER, input);
        }
        
        public static CapabilitiesSet parseDelimitedFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (CapabilitiesSet)GeneratedMessageV3.parseDelimitedWithIOException((Parser)CapabilitiesSet.PARSER, input, extensionRegistry);
        }
        
        public static CapabilitiesSet parseFrom(final CodedInputStream input) throws IOException {
            return (CapabilitiesSet)GeneratedMessageV3.parseWithIOException((Parser)CapabilitiesSet.PARSER, input);
        }
        
        public static CapabilitiesSet parseFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (CapabilitiesSet)GeneratedMessageV3.parseWithIOException((Parser)CapabilitiesSet.PARSER, input, extensionRegistry);
        }
        
        public Builder newBuilderForType() {
            return newBuilder();
        }
        
        public static Builder newBuilder() {
            return CapabilitiesSet.DEFAULT_INSTANCE.toBuilder();
        }
        
        public static Builder newBuilder(final CapabilitiesSet prototype) {
            return CapabilitiesSet.DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }
        
        public Builder toBuilder() {
            return (this == CapabilitiesSet.DEFAULT_INSTANCE) ? new Builder() : new Builder().mergeFrom(this);
        }
        
        protected Builder newBuilderForType(final GeneratedMessageV3.BuilderParent parent) {
            final Builder builder = new Builder(parent);
            return builder;
        }
        
        public static CapabilitiesSet getDefaultInstance() {
            return CapabilitiesSet.DEFAULT_INSTANCE;
        }
        
        public static Parser<CapabilitiesSet> parser() {
            return CapabilitiesSet.PARSER;
        }
        
        public Parser<CapabilitiesSet> getParserForType() {
            return CapabilitiesSet.PARSER;
        }
        
        public CapabilitiesSet getDefaultInstanceForType() {
            return CapabilitiesSet.DEFAULT_INSTANCE;
        }
        
        static {
            DEFAULT_INSTANCE = new CapabilitiesSet();
            PARSER = (Parser)new AbstractParser<CapabilitiesSet>() {
                public CapabilitiesSet parsePartialFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new CapabilitiesSet(input, extensionRegistry);
                }
            };
        }
        
        public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements CapabilitiesSetOrBuilder
        {
            private int bitField0_;
            private Capabilities capabilities_;
            private SingleFieldBuilderV3<Capabilities, Capabilities.Builder, CapabilitiesOrBuilder> capabilitiesBuilder_;
            
            public static final Descriptors.Descriptor getDescriptor() {
                return MysqlxConnection.internal_static_Mysqlx_Connection_CapabilitiesSet_descriptor;
            }
            
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return MysqlxConnection.internal_static_Mysqlx_Connection_CapabilitiesSet_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)CapabilitiesSet.class, (Class)Builder.class);
            }
            
            private Builder() {
                this.capabilities_ = null;
                this.maybeForceBuilderInitialization();
            }
            
            private Builder(final GeneratedMessageV3.BuilderParent parent) {
                super(parent);
                this.capabilities_ = null;
                this.maybeForceBuilderInitialization();
            }
            
            private void maybeForceBuilderInitialization() {
                if (CapabilitiesSet.alwaysUseFieldBuilders) {
                    this.getCapabilitiesFieldBuilder();
                }
            }
            
            public Builder clear() {
                super.clear();
                if (this.capabilitiesBuilder_ == null) {
                    this.capabilities_ = null;
                }
                else {
                    this.capabilitiesBuilder_.clear();
                }
                this.bitField0_ &= 0xFFFFFFFE;
                return this;
            }
            
            public Descriptors.Descriptor getDescriptorForType() {
                return MysqlxConnection.internal_static_Mysqlx_Connection_CapabilitiesSet_descriptor;
            }
            
            public CapabilitiesSet getDefaultInstanceForType() {
                return CapabilitiesSet.getDefaultInstance();
            }
            
            public CapabilitiesSet build() {
                final CapabilitiesSet result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw newUninitializedMessageException((Message)result);
                }
                return result;
            }
            
            public CapabilitiesSet buildPartial() {
                final CapabilitiesSet result = new CapabilitiesSet((GeneratedMessageV3.Builder)this);
                final int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 0x1) == 0x1) {
                    to_bitField0_ |= 0x1;
                }
                if (this.capabilitiesBuilder_ == null) {
                    result.capabilities_ = this.capabilities_;
                }
                else {
                    result.capabilities_ = (Capabilities)this.capabilitiesBuilder_.build();
                }
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
                if (other instanceof CapabilitiesSet) {
                    return this.mergeFrom((CapabilitiesSet)other);
                }
                super.mergeFrom(other);
                return this;
            }
            
            public Builder mergeFrom(final CapabilitiesSet other) {
                if (other == CapabilitiesSet.getDefaultInstance()) {
                    return this;
                }
                if (other.hasCapabilities()) {
                    this.mergeCapabilities(other.getCapabilities());
                }
                this.mergeUnknownFields(other.unknownFields);
                this.onChanged();
                return this;
            }
            
            public final boolean isInitialized() {
                return this.hasCapabilities() && this.getCapabilities().isInitialized();
            }
            
            public Builder mergeFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
                CapabilitiesSet parsedMessage = null;
                try {
                    parsedMessage = (CapabilitiesSet)CapabilitiesSet.PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (CapabilitiesSet)e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                }
                finally {
                    if (parsedMessage != null) {
                        this.mergeFrom(parsedMessage);
                    }
                }
                return this;
            }
            
            public boolean hasCapabilities() {
                return (this.bitField0_ & 0x1) == 0x1;
            }
            
            public Capabilities getCapabilities() {
                if (this.capabilitiesBuilder_ == null) {
                    return (this.capabilities_ == null) ? Capabilities.getDefaultInstance() : this.capabilities_;
                }
                return (Capabilities)this.capabilitiesBuilder_.getMessage();
            }
            
            public Builder setCapabilities(final Capabilities value) {
                if (this.capabilitiesBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.capabilities_ = value;
                    this.onChanged();
                }
                else {
                    this.capabilitiesBuilder_.setMessage((AbstractMessage)value);
                }
                this.bitField0_ |= 0x1;
                return this;
            }
            
            public Builder setCapabilities(final Capabilities.Builder builderForValue) {
                if (this.capabilitiesBuilder_ == null) {
                    this.capabilities_ = builderForValue.build();
                    this.onChanged();
                }
                else {
                    this.capabilitiesBuilder_.setMessage((AbstractMessage)builderForValue.build());
                }
                this.bitField0_ |= 0x1;
                return this;
            }
            
            public Builder mergeCapabilities(final Capabilities value) {
                if (this.capabilitiesBuilder_ == null) {
                    if ((this.bitField0_ & 0x1) == 0x1 && this.capabilities_ != null && this.capabilities_ != Capabilities.getDefaultInstance()) {
                        this.capabilities_ = Capabilities.newBuilder(this.capabilities_).mergeFrom(value).buildPartial();
                    }
                    else {
                        this.capabilities_ = value;
                    }
                    this.onChanged();
                }
                else {
                    this.capabilitiesBuilder_.mergeFrom((AbstractMessage)value);
                }
                this.bitField0_ |= 0x1;
                return this;
            }
            
            public Builder clearCapabilities() {
                if (this.capabilitiesBuilder_ == null) {
                    this.capabilities_ = null;
                    this.onChanged();
                }
                else {
                    this.capabilitiesBuilder_.clear();
                }
                this.bitField0_ &= 0xFFFFFFFE;
                return this;
            }
            
            public Capabilities.Builder getCapabilitiesBuilder() {
                this.bitField0_ |= 0x1;
                this.onChanged();
                return (Capabilities.Builder)this.getCapabilitiesFieldBuilder().getBuilder();
            }
            
            public CapabilitiesOrBuilder getCapabilitiesOrBuilder() {
                if (this.capabilitiesBuilder_ != null) {
                    return (CapabilitiesOrBuilder)this.capabilitiesBuilder_.getMessageOrBuilder();
                }
                return (this.capabilities_ == null) ? Capabilities.getDefaultInstance() : this.capabilities_;
            }
            
            private SingleFieldBuilderV3<Capabilities, Capabilities.Builder, CapabilitiesOrBuilder> getCapabilitiesFieldBuilder() {
                if (this.capabilitiesBuilder_ == null) {
                    this.capabilitiesBuilder_ = (SingleFieldBuilderV3<Capabilities, Capabilities.Builder, CapabilitiesOrBuilder>)new SingleFieldBuilderV3((AbstractMessage)this.getCapabilities(), (AbstractMessage.BuilderParent)this.getParentForChildren(), this.isClean());
                    this.capabilities_ = null;
                }
                return this.capabilitiesBuilder_;
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
            return MysqlxConnection.internal_static_Mysqlx_Connection_Close_descriptor;
        }
        
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return MysqlxConnection.internal_static_Mysqlx_Connection_Close_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)Close.class, (Class)Builder.class);
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
                return MysqlxConnection.internal_static_Mysqlx_Connection_Close_descriptor;
            }
            
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return MysqlxConnection.internal_static_Mysqlx_Connection_Close_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)Close.class, (Class)Builder.class);
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
                return MysqlxConnection.internal_static_Mysqlx_Connection_Close_descriptor;
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
    
    public interface CapabilityOrBuilder extends MessageOrBuilder
    {
        boolean hasName();
        
        String getName();
        
        ByteString getNameBytes();
        
        boolean hasValue();
        
        MysqlxDatatypes.Any getValue();
        
        MysqlxDatatypes.AnyOrBuilder getValueOrBuilder();
    }
    
    public interface CapabilitiesOrBuilder extends MessageOrBuilder
    {
        List<Capability> getCapabilitiesList();
        
        Capability getCapabilities(final int p0);
        
        int getCapabilitiesCount();
        
        List<? extends CapabilityOrBuilder> getCapabilitiesOrBuilderList();
        
        CapabilityOrBuilder getCapabilitiesOrBuilder(final int p0);
    }
    
    public interface CapabilitiesSetOrBuilder extends MessageOrBuilder
    {
        boolean hasCapabilities();
        
        Capabilities getCapabilities();
        
        CapabilitiesOrBuilder getCapabilitiesOrBuilder();
    }
    
    public interface CapabilitiesGetOrBuilder extends MessageOrBuilder
    {
    }
}
