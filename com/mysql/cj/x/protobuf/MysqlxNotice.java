
package com.mysql.cj.x.protobuf;

import com.google.protobuf.MessageOrBuilder;
import java.util.Collection;
import com.google.protobuf.RepeatedFieldBuilderV3;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.google.protobuf.SingleFieldBuilderV3;
import com.google.protobuf.AbstractMessageLite;
import com.google.protobuf.AbstractMessage;
import com.google.protobuf.Internal;
import com.google.protobuf.ProtocolMessageEnum;
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

public final class MysqlxNotice
{
    private static final Descriptors.Descriptor internal_static_Mysqlx_Notice_Frame_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_Mysqlx_Notice_Frame_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_Mysqlx_Notice_Warning_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_Mysqlx_Notice_Warning_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_Mysqlx_Notice_SessionVariableChanged_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_Mysqlx_Notice_SessionVariableChanged_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_Mysqlx_Notice_SessionStateChanged_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_Mysqlx_Notice_SessionStateChanged_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_Mysqlx_Notice_GroupReplicationStateChanged_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_Mysqlx_Notice_GroupReplicationStateChanged_fieldAccessorTable;
    private static Descriptors.FileDescriptor descriptor;
    
    private MysqlxNotice() {
    }
    
    public static void registerAllExtensions(final ExtensionRegistryLite registry) {
    }
    
    public static void registerAllExtensions(final ExtensionRegistry registry) {
        registerAllExtensions((ExtensionRegistryLite)registry);
    }
    
    public static Descriptors.FileDescriptor getDescriptor() {
        return MysqlxNotice.descriptor;
    }
    
    static {
        final String[] descriptorData = { "\n\u0013mysqlx_notice.proto\u0012\rMysqlx.Notice\u001a\fmysqlx.proto\u001a\u0016mysqlx_datatypes.proto\"\u00f2\u0001\n\u0005Frame\u0012\f\n\u0004type\u0018\u0001 \u0002(\r\u00121\n\u0005scope\u0018\u0002 \u0001(\u000e2\u001a.Mysqlx.Notice.Frame.Scope:\u0006GLOBAL\u0012\u000f\n\u0007payload\u0018\u0003 \u0001(\f\"\u001e\n\u0005Scope\u0012\n\n\u0006GLOBAL\u0010\u0001\u0012\t\n\u0005LOCAL\u0010\u0002\"q\n\u0004Type\u0012\u000b\n\u0007WARNING\u0010\u0001\u0012\u001c\n\u0018SESSION_VARIABLE_CHANGED\u0010\u0002\u0012\u0019\n\u0015SESSION_STATE_CHANGED\u0010\u0003\u0012#\n\u001fGROUP_REPLICATION_STATE_CHANGED\u0010\u0004:\u0004\u0090\u00ea0\u000b\"\u0085\u0001\n\u0007Warning\u00124\n\u0005level\u0018\u0001 \u0001(\u000e2\u001c.Mysqlx.Notice.Warning.Level:\u0007WARNING\u0012\f\n\u0004code\u0018\u0002 \u0002(\r\u0012\u000b\n\u0003msg\u0018\u0003 \u0002(\t\")\n\u0005Level\u0012\b\n\u0004NOTE\u0010\u0001\u0012\u000b\n\u0007WARNING\u0010\u0002\u0012\t\n\u0005ERROR\u0010\u0003\"P\n\u0016SessionVariableChanged\u0012\r\n\u0005param\u0018\u0001 \u0002(\t\u0012'\n\u0005value\u0018\u0002 \u0001(\u000b2\u0018.Mysqlx.Datatypes.Scalar\"\u00f1\u0002\n\u0013SessionStateChanged\u0012;\n\u0005param\u0018\u0001 \u0002(\u000e2,.Mysqlx.Notice.SessionStateChanged.Parameter\u0012'\n\u0005value\u0018\u0002 \u0003(\u000b2\u0018.Mysqlx.Datatypes.Scalar\"\u00f3\u0001\n\tParameter\u0012\u0012\n\u000eCURRENT_SCHEMA\u0010\u0001\u0012\u0013\n\u000fACCOUNT_EXPIRED\u0010\u0002\u0012\u0017\n\u0013GENERATED_INSERT_ID\u0010\u0003\u0012\u0011\n\rROWS_AFFECTED\u0010\u0004\u0012\u000e\n\nROWS_FOUND\u0010\u0005\u0012\u0010\n\fROWS_MATCHED\u0010\u0006\u0012\u0011\n\rTRX_COMMITTED\u0010\u0007\u0012\u0012\n\u000eTRX_ROLLEDBACK\u0010\t\u0012\u0014\n\u0010PRODUCED_MESSAGE\u0010\n\u0012\u0016\n\u0012CLIENT_ID_ASSIGNED\u0010\u000b\u0012\u001a\n\u0016GENERATED_DOCUMENT_IDS\u0010\f\"®\u0001\n\u001cGroupReplicationStateChanged\u0012\f\n\u0004type\u0018\u0001 \u0002(\r\u0012\u000f\n\u0007view_id\u0018\u0002 \u0001(\t\"o\n\u0004Type\u0012\u001a\n\u0016MEMBERSHIP_QUORUM_LOSS\u0010\u0001\u0012\u001a\n\u0016MEMBERSHIP_VIEW_CHANGE\u0010\u0002\u0012\u0016\n\u0012MEMBER_ROLE_CHANGE\u0010\u0003\u0012\u0017\n\u0013MEMBER_STATE_CHANGE\u0010\u0004B\u0019\n\u0017com.mysql.cj.x.protobuf" };
        final Descriptors.FileDescriptor.InternalDescriptorAssigner assigner = (Descriptors.FileDescriptor.InternalDescriptorAssigner)new Descriptors.FileDescriptor.InternalDescriptorAssigner() {
            public ExtensionRegistry assignDescriptors(final Descriptors.FileDescriptor root) {
                MysqlxNotice.descriptor = root;
                return null;
            }
        };
        Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new Descriptors.FileDescriptor[] { Mysqlx.getDescriptor(), MysqlxDatatypes.getDescriptor() }, assigner);
        internal_static_Mysqlx_Notice_Frame_descriptor = getDescriptor().getMessageTypes().get(0);
        internal_static_Mysqlx_Notice_Frame_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(MysqlxNotice.internal_static_Mysqlx_Notice_Frame_descriptor, new String[] { "Type", "Scope", "Payload" });
        internal_static_Mysqlx_Notice_Warning_descriptor = getDescriptor().getMessageTypes().get(1);
        internal_static_Mysqlx_Notice_Warning_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(MysqlxNotice.internal_static_Mysqlx_Notice_Warning_descriptor, new String[] { "Level", "Code", "Msg" });
        internal_static_Mysqlx_Notice_SessionVariableChanged_descriptor = getDescriptor().getMessageTypes().get(2);
        internal_static_Mysqlx_Notice_SessionVariableChanged_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(MysqlxNotice.internal_static_Mysqlx_Notice_SessionVariableChanged_descriptor, new String[] { "Param", "Value" });
        internal_static_Mysqlx_Notice_SessionStateChanged_descriptor = getDescriptor().getMessageTypes().get(3);
        internal_static_Mysqlx_Notice_SessionStateChanged_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(MysqlxNotice.internal_static_Mysqlx_Notice_SessionStateChanged_descriptor, new String[] { "Param", "Value" });
        internal_static_Mysqlx_Notice_GroupReplicationStateChanged_descriptor = getDescriptor().getMessageTypes().get(4);
        internal_static_Mysqlx_Notice_GroupReplicationStateChanged_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(MysqlxNotice.internal_static_Mysqlx_Notice_GroupReplicationStateChanged_descriptor, new String[] { "Type", "ViewId" });
        final ExtensionRegistry registry = ExtensionRegistry.newInstance();
        registry.add((GeneratedMessage.GeneratedExtension)Mysqlx.serverMessageId);
        Descriptors.FileDescriptor.internalUpdateFileDescriptor(MysqlxNotice.descriptor, registry);
        Mysqlx.getDescriptor();
        MysqlxDatatypes.getDescriptor();
    }
    
    public static final class Frame extends GeneratedMessageV3 implements FrameOrBuilder
    {
        private static final long serialVersionUID = 0L;
        private int bitField0_;
        public static final int TYPE_FIELD_NUMBER = 1;
        private int type_;
        public static final int SCOPE_FIELD_NUMBER = 2;
        private int scope_;
        public static final int PAYLOAD_FIELD_NUMBER = 3;
        private ByteString payload_;
        private byte memoizedIsInitialized;
        private static final Frame DEFAULT_INSTANCE;
        @Deprecated
        public static final Parser<Frame> PARSER;
        
        private Frame(final GeneratedMessageV3.Builder<?> builder) {
            super((GeneratedMessageV3.Builder)builder);
            this.memoizedIsInitialized = -1;
        }
        
        private Frame() {
            this.memoizedIsInitialized = -1;
            this.type_ = 0;
            this.scope_ = 1;
            this.payload_ = ByteString.EMPTY;
        }
        
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }
        
        private Frame(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            this.bitField0_ |= 0x1;
                            this.type_ = input.readUInt32();
                            continue;
                        }
                        case 16: {
                            final int rawValue = input.readEnum();
                            final Scope value = Scope.valueOf(rawValue);
                            if (value == null) {
                                unknownFields.mergeVarintField(2, rawValue);
                                continue;
                            }
                            this.bitField0_ |= 0x2;
                            this.scope_ = rawValue;
                            continue;
                        }
                        case 26: {
                            this.bitField0_ |= 0x4;
                            this.payload_ = input.readBytes();
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
            return MysqlxNotice.internal_static_Mysqlx_Notice_Frame_descriptor;
        }
        
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return MysqlxNotice.internal_static_Mysqlx_Notice_Frame_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)Frame.class, (Class)Builder.class);
        }
        
        public boolean hasType() {
            return (this.bitField0_ & 0x1) == 0x1;
        }
        
        public int getType() {
            return this.type_;
        }
        
        public boolean hasScope() {
            return (this.bitField0_ & 0x2) == 0x2;
        }
        
        public Scope getScope() {
            final Scope result = Scope.valueOf(this.scope_);
            return (result == null) ? Scope.GLOBAL : result;
        }
        
        public boolean hasPayload() {
            return (this.bitField0_ & 0x4) == 0x4;
        }
        
        public ByteString getPayload() {
            return this.payload_;
        }
        
        public final boolean isInitialized() {
            final byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (!this.hasType()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            this.memoizedIsInitialized = 1;
            return true;
        }
        
        public void writeTo(final CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 0x1) == 0x1) {
                output.writeUInt32(1, this.type_);
            }
            if ((this.bitField0_ & 0x2) == 0x2) {
                output.writeEnum(2, this.scope_);
            }
            if ((this.bitField0_ & 0x4) == 0x4) {
                output.writeBytes(3, this.payload_);
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
                size += CodedOutputStream.computeUInt32Size(1, this.type_);
            }
            if ((this.bitField0_ & 0x2) == 0x2) {
                size += CodedOutputStream.computeEnumSize(2, this.scope_);
            }
            if ((this.bitField0_ & 0x4) == 0x4) {
                size += CodedOutputStream.computeBytesSize(3, this.payload_);
            }
            size += this.unknownFields.getSerializedSize();
            return this.memoizedSize = size;
        }
        
        public boolean equals(final Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Frame)) {
                return super.equals(obj);
            }
            final Frame other = (Frame)obj;
            boolean result = true;
            result = (result && this.hasType() == other.hasType());
            if (this.hasType()) {
                result = (result && this.getType() == other.getType());
            }
            result = (result && this.hasScope() == other.hasScope());
            if (this.hasScope()) {
                result = (result && this.scope_ == other.scope_);
            }
            result = (result && this.hasPayload() == other.hasPayload());
            if (this.hasPayload()) {
                result = (result && this.getPayload().equals((Object)other.getPayload()));
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
            if (this.hasType()) {
                hash = 37 * hash + 1;
                hash = 53 * hash + this.getType();
            }
            if (this.hasScope()) {
                hash = 37 * hash + 2;
                hash = 53 * hash + this.scope_;
            }
            if (this.hasPayload()) {
                hash = 37 * hash + 3;
                hash = 53 * hash + this.getPayload().hashCode();
            }
            hash = 29 * hash + this.unknownFields.hashCode();
            return this.memoizedHashCode = hash;
        }
        
        public static Frame parseFrom(final ByteBuffer data) throws InvalidProtocolBufferException {
            return (Frame)Frame.PARSER.parseFrom(data);
        }
        
        public static Frame parseFrom(final ByteBuffer data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Frame)Frame.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Frame parseFrom(final ByteString data) throws InvalidProtocolBufferException {
            return (Frame)Frame.PARSER.parseFrom(data);
        }
        
        public static Frame parseFrom(final ByteString data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Frame)Frame.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Frame parseFrom(final byte[] data) throws InvalidProtocolBufferException {
            return (Frame)Frame.PARSER.parseFrom(data);
        }
        
        public static Frame parseFrom(final byte[] data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Frame)Frame.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Frame parseFrom(final InputStream input) throws IOException {
            return (Frame)GeneratedMessageV3.parseWithIOException((Parser)Frame.PARSER, input);
        }
        
        public static Frame parseFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Frame)GeneratedMessageV3.parseWithIOException((Parser)Frame.PARSER, input, extensionRegistry);
        }
        
        public static Frame parseDelimitedFrom(final InputStream input) throws IOException {
            return (Frame)GeneratedMessageV3.parseDelimitedWithIOException((Parser)Frame.PARSER, input);
        }
        
        public static Frame parseDelimitedFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Frame)GeneratedMessageV3.parseDelimitedWithIOException((Parser)Frame.PARSER, input, extensionRegistry);
        }
        
        public static Frame parseFrom(final CodedInputStream input) throws IOException {
            return (Frame)GeneratedMessageV3.parseWithIOException((Parser)Frame.PARSER, input);
        }
        
        public static Frame parseFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Frame)GeneratedMessageV3.parseWithIOException((Parser)Frame.PARSER, input, extensionRegistry);
        }
        
        public Builder newBuilderForType() {
            return newBuilder();
        }
        
        public static Builder newBuilder() {
            return Frame.DEFAULT_INSTANCE.toBuilder();
        }
        
        public static Builder newBuilder(final Frame prototype) {
            return Frame.DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }
        
        public Builder toBuilder() {
            return (this == Frame.DEFAULT_INSTANCE) ? new Builder() : new Builder().mergeFrom(this);
        }
        
        protected Builder newBuilderForType(final GeneratedMessageV3.BuilderParent parent) {
            final Builder builder = new Builder(parent);
            return builder;
        }
        
        public static Frame getDefaultInstance() {
            return Frame.DEFAULT_INSTANCE;
        }
        
        public static Parser<Frame> parser() {
            return Frame.PARSER;
        }
        
        public Parser<Frame> getParserForType() {
            return Frame.PARSER;
        }
        
        public Frame getDefaultInstanceForType() {
            return Frame.DEFAULT_INSTANCE;
        }
        
        static {
            DEFAULT_INSTANCE = new Frame();
            PARSER = (Parser)new AbstractParser<Frame>() {
                public Frame parsePartialFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new Frame(input, extensionRegistry);
                }
            };
        }
        
        public enum Scope implements ProtocolMessageEnum
        {
            GLOBAL(1), 
            LOCAL(2);
            
            public static final int GLOBAL_VALUE = 1;
            public static final int LOCAL_VALUE = 2;
            private static final Internal.EnumLiteMap<Scope> internalValueMap;
            private static final Scope[] VALUES;
            private final int value;
            
            public final int getNumber() {
                return this.value;
            }
            
            @Deprecated
            public static Scope valueOf(final int value) {
                return forNumber(value);
            }
            
            public static Scope forNumber(final int value) {
                switch (value) {
                    case 1: {
                        return Scope.GLOBAL;
                    }
                    case 2: {
                        return Scope.LOCAL;
                    }
                    default: {
                        return null;
                    }
                }
            }
            
            public static Internal.EnumLiteMap<Scope> internalGetValueMap() {
                return Scope.internalValueMap;
            }
            
            public final Descriptors.EnumValueDescriptor getValueDescriptor() {
                return getDescriptor().getValues().get(this.ordinal());
            }
            
            public final Descriptors.EnumDescriptor getDescriptorForType() {
                return getDescriptor();
            }
            
            public static final Descriptors.EnumDescriptor getDescriptor() {
                return Frame.getDescriptor().getEnumTypes().get(0);
            }
            
            public static Scope valueOf(final Descriptors.EnumValueDescriptor desc) {
                if (desc.getType() != getDescriptor()) {
                    throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
                }
                return Scope.VALUES[desc.getIndex()];
            }
            
            private Scope(final int value) {
                this.value = value;
            }
            
            static {
                internalValueMap = (Internal.EnumLiteMap)new Internal.EnumLiteMap<Scope>() {
                    public Scope findValueByNumber(final int number) {
                        return Scope.forNumber(number);
                    }
                };
                VALUES = values();
            }
        }
        
        public enum Type implements ProtocolMessageEnum
        {
            WARNING(1), 
            SESSION_VARIABLE_CHANGED(2), 
            SESSION_STATE_CHANGED(3), 
            GROUP_REPLICATION_STATE_CHANGED(4);
            
            public static final int WARNING_VALUE = 1;
            public static final int SESSION_VARIABLE_CHANGED_VALUE = 2;
            public static final int SESSION_STATE_CHANGED_VALUE = 3;
            public static final int GROUP_REPLICATION_STATE_CHANGED_VALUE = 4;
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
                        return Type.WARNING;
                    }
                    case 2: {
                        return Type.SESSION_VARIABLE_CHANGED;
                    }
                    case 3: {
                        return Type.SESSION_STATE_CHANGED;
                    }
                    case 4: {
                        return Type.GROUP_REPLICATION_STATE_CHANGED;
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
                return Frame.getDescriptor().getEnumTypes().get(1);
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
        
        public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements FrameOrBuilder
        {
            private int bitField0_;
            private int type_;
            private int scope_;
            private ByteString payload_;
            
            public static final Descriptors.Descriptor getDescriptor() {
                return MysqlxNotice.internal_static_Mysqlx_Notice_Frame_descriptor;
            }
            
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return MysqlxNotice.internal_static_Mysqlx_Notice_Frame_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)Frame.class, (Class)Builder.class);
            }
            
            private Builder() {
                this.scope_ = 1;
                this.payload_ = ByteString.EMPTY;
                this.maybeForceBuilderInitialization();
            }
            
            private Builder(final GeneratedMessageV3.BuilderParent parent) {
                super(parent);
                this.scope_ = 1;
                this.payload_ = ByteString.EMPTY;
                this.maybeForceBuilderInitialization();
            }
            
            private void maybeForceBuilderInitialization() {
                if (Frame.alwaysUseFieldBuilders) {}
            }
            
            public Builder clear() {
                super.clear();
                this.type_ = 0;
                this.bitField0_ &= 0xFFFFFFFE;
                this.scope_ = 1;
                this.bitField0_ &= 0xFFFFFFFD;
                this.payload_ = ByteString.EMPTY;
                this.bitField0_ &= 0xFFFFFFFB;
                return this;
            }
            
            public Descriptors.Descriptor getDescriptorForType() {
                return MysqlxNotice.internal_static_Mysqlx_Notice_Frame_descriptor;
            }
            
            public Frame getDefaultInstanceForType() {
                return Frame.getDefaultInstance();
            }
            
            public Frame build() {
                final Frame result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw newUninitializedMessageException((Message)result);
                }
                return result;
            }
            
            public Frame buildPartial() {
                final Frame result = new Frame((GeneratedMessageV3.Builder)this);
                final int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 0x1) == 0x1) {
                    to_bitField0_ |= 0x1;
                }
                result.type_ = this.type_;
                if ((from_bitField0_ & 0x2) == 0x2) {
                    to_bitField0_ |= 0x2;
                }
                result.scope_ = this.scope_;
                if ((from_bitField0_ & 0x4) == 0x4) {
                    to_bitField0_ |= 0x4;
                }
                result.payload_ = this.payload_;
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
                if (other instanceof Frame) {
                    return this.mergeFrom((Frame)other);
                }
                super.mergeFrom(other);
                return this;
            }
            
            public Builder mergeFrom(final Frame other) {
                if (other == Frame.getDefaultInstance()) {
                    return this;
                }
                if (other.hasType()) {
                    this.setType(other.getType());
                }
                if (other.hasScope()) {
                    this.setScope(other.getScope());
                }
                if (other.hasPayload()) {
                    this.setPayload(other.getPayload());
                }
                this.mergeUnknownFields(other.unknownFields);
                this.onChanged();
                return this;
            }
            
            public final boolean isInitialized() {
                return this.hasType();
            }
            
            public Builder mergeFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
                Frame parsedMessage = null;
                try {
                    parsedMessage = (Frame)Frame.PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (Frame)e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                }
                finally {
                    if (parsedMessage != null) {
                        this.mergeFrom(parsedMessage);
                    }
                }
                return this;
            }
            
            public boolean hasType() {
                return (this.bitField0_ & 0x1) == 0x1;
            }
            
            public int getType() {
                return this.type_;
            }
            
            public Builder setType(final int value) {
                this.bitField0_ |= 0x1;
                this.type_ = value;
                this.onChanged();
                return this;
            }
            
            public Builder clearType() {
                this.bitField0_ &= 0xFFFFFFFE;
                this.type_ = 0;
                this.onChanged();
                return this;
            }
            
            public boolean hasScope() {
                return (this.bitField0_ & 0x2) == 0x2;
            }
            
            public Scope getScope() {
                final Scope result = Scope.valueOf(this.scope_);
                return (result == null) ? Scope.GLOBAL : result;
            }
            
            public Builder setScope(final Scope value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 0x2;
                this.scope_ = value.getNumber();
                this.onChanged();
                return this;
            }
            
            public Builder clearScope() {
                this.bitField0_ &= 0xFFFFFFFD;
                this.scope_ = 1;
                this.onChanged();
                return this;
            }
            
            public boolean hasPayload() {
                return (this.bitField0_ & 0x4) == 0x4;
            }
            
            public ByteString getPayload() {
                return this.payload_;
            }
            
            public Builder setPayload(final ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 0x4;
                this.payload_ = value;
                this.onChanged();
                return this;
            }
            
            public Builder clearPayload() {
                this.bitField0_ &= 0xFFFFFFFB;
                this.payload_ = Frame.getDefaultInstance().getPayload();
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
    
    public static final class Warning extends GeneratedMessageV3 implements WarningOrBuilder
    {
        private static final long serialVersionUID = 0L;
        private int bitField0_;
        public static final int LEVEL_FIELD_NUMBER = 1;
        private int level_;
        public static final int CODE_FIELD_NUMBER = 2;
        private int code_;
        public static final int MSG_FIELD_NUMBER = 3;
        private volatile Object msg_;
        private byte memoizedIsInitialized;
        private static final Warning DEFAULT_INSTANCE;
        @Deprecated
        public static final Parser<Warning> PARSER;
        
        private Warning(final GeneratedMessageV3.Builder<?> builder) {
            super((GeneratedMessageV3.Builder)builder);
            this.memoizedIsInitialized = -1;
        }
        
        private Warning() {
            this.memoizedIsInitialized = -1;
            this.level_ = 2;
            this.code_ = 0;
            this.msg_ = "";
        }
        
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }
        
        private Warning(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            final Level value = Level.valueOf(rawValue);
                            if (value == null) {
                                unknownFields.mergeVarintField(1, rawValue);
                                continue;
                            }
                            this.bitField0_ |= 0x1;
                            this.level_ = rawValue;
                            continue;
                        }
                        case 16: {
                            this.bitField0_ |= 0x2;
                            this.code_ = input.readUInt32();
                            continue;
                        }
                        case 26: {
                            final ByteString bs = input.readBytes();
                            this.bitField0_ |= 0x4;
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
            return MysqlxNotice.internal_static_Mysqlx_Notice_Warning_descriptor;
        }
        
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return MysqlxNotice.internal_static_Mysqlx_Notice_Warning_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)Warning.class, (Class)Builder.class);
        }
        
        public boolean hasLevel() {
            return (this.bitField0_ & 0x1) == 0x1;
        }
        
        public Level getLevel() {
            final Level result = Level.valueOf(this.level_);
            return (result == null) ? Level.WARNING : result;
        }
        
        public boolean hasCode() {
            return (this.bitField0_ & 0x2) == 0x2;
        }
        
        public int getCode() {
            return this.code_;
        }
        
        public boolean hasMsg() {
            return (this.bitField0_ & 0x4) == 0x4;
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
            if (!this.hasMsg()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            this.memoizedIsInitialized = 1;
            return true;
        }
        
        public void writeTo(final CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 0x1) == 0x1) {
                output.writeEnum(1, this.level_);
            }
            if ((this.bitField0_ & 0x2) == 0x2) {
                output.writeUInt32(2, this.code_);
            }
            if ((this.bitField0_ & 0x4) == 0x4) {
                GeneratedMessageV3.writeString(output, 3, this.msg_);
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
                size += CodedOutputStream.computeEnumSize(1, this.level_);
            }
            if ((this.bitField0_ & 0x2) == 0x2) {
                size += CodedOutputStream.computeUInt32Size(2, this.code_);
            }
            if ((this.bitField0_ & 0x4) == 0x4) {
                size += GeneratedMessageV3.computeStringSize(3, this.msg_);
            }
            size += this.unknownFields.getSerializedSize();
            return this.memoizedSize = size;
        }
        
        public boolean equals(final Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Warning)) {
                return super.equals(obj);
            }
            final Warning other = (Warning)obj;
            boolean result = true;
            result = (result && this.hasLevel() == other.hasLevel());
            if (this.hasLevel()) {
                result = (result && this.level_ == other.level_);
            }
            result = (result && this.hasCode() == other.hasCode());
            if (this.hasCode()) {
                result = (result && this.getCode() == other.getCode());
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
            if (this.hasLevel()) {
                hash = 37 * hash + 1;
                hash = 53 * hash + this.level_;
            }
            if (this.hasCode()) {
                hash = 37 * hash + 2;
                hash = 53 * hash + this.getCode();
            }
            if (this.hasMsg()) {
                hash = 37 * hash + 3;
                hash = 53 * hash + this.getMsg().hashCode();
            }
            hash = 29 * hash + this.unknownFields.hashCode();
            return this.memoizedHashCode = hash;
        }
        
        public static Warning parseFrom(final ByteBuffer data) throws InvalidProtocolBufferException {
            return (Warning)Warning.PARSER.parseFrom(data);
        }
        
        public static Warning parseFrom(final ByteBuffer data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Warning)Warning.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Warning parseFrom(final ByteString data) throws InvalidProtocolBufferException {
            return (Warning)Warning.PARSER.parseFrom(data);
        }
        
        public static Warning parseFrom(final ByteString data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Warning)Warning.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Warning parseFrom(final byte[] data) throws InvalidProtocolBufferException {
            return (Warning)Warning.PARSER.parseFrom(data);
        }
        
        public static Warning parseFrom(final byte[] data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Warning)Warning.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Warning parseFrom(final InputStream input) throws IOException {
            return (Warning)GeneratedMessageV3.parseWithIOException((Parser)Warning.PARSER, input);
        }
        
        public static Warning parseFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Warning)GeneratedMessageV3.parseWithIOException((Parser)Warning.PARSER, input, extensionRegistry);
        }
        
        public static Warning parseDelimitedFrom(final InputStream input) throws IOException {
            return (Warning)GeneratedMessageV3.parseDelimitedWithIOException((Parser)Warning.PARSER, input);
        }
        
        public static Warning parseDelimitedFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Warning)GeneratedMessageV3.parseDelimitedWithIOException((Parser)Warning.PARSER, input, extensionRegistry);
        }
        
        public static Warning parseFrom(final CodedInputStream input) throws IOException {
            return (Warning)GeneratedMessageV3.parseWithIOException((Parser)Warning.PARSER, input);
        }
        
        public static Warning parseFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Warning)GeneratedMessageV3.parseWithIOException((Parser)Warning.PARSER, input, extensionRegistry);
        }
        
        public Builder newBuilderForType() {
            return newBuilder();
        }
        
        public static Builder newBuilder() {
            return Warning.DEFAULT_INSTANCE.toBuilder();
        }
        
        public static Builder newBuilder(final Warning prototype) {
            return Warning.DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }
        
        public Builder toBuilder() {
            return (this == Warning.DEFAULT_INSTANCE) ? new Builder() : new Builder().mergeFrom(this);
        }
        
        protected Builder newBuilderForType(final GeneratedMessageV3.BuilderParent parent) {
            final Builder builder = new Builder(parent);
            return builder;
        }
        
        public static Warning getDefaultInstance() {
            return Warning.DEFAULT_INSTANCE;
        }
        
        public static Parser<Warning> parser() {
            return Warning.PARSER;
        }
        
        public Parser<Warning> getParserForType() {
            return Warning.PARSER;
        }
        
        public Warning getDefaultInstanceForType() {
            return Warning.DEFAULT_INSTANCE;
        }
        
        static {
            DEFAULT_INSTANCE = new Warning();
            PARSER = (Parser)new AbstractParser<Warning>() {
                public Warning parsePartialFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new Warning(input, extensionRegistry);
                }
            };
        }
        
        public enum Level implements ProtocolMessageEnum
        {
            NOTE(1), 
            WARNING(2), 
            ERROR(3);
            
            public static final int NOTE_VALUE = 1;
            public static final int WARNING_VALUE = 2;
            public static final int ERROR_VALUE = 3;
            private static final Internal.EnumLiteMap<Level> internalValueMap;
            private static final Level[] VALUES;
            private final int value;
            
            public final int getNumber() {
                return this.value;
            }
            
            @Deprecated
            public static Level valueOf(final int value) {
                return forNumber(value);
            }
            
            public static Level forNumber(final int value) {
                switch (value) {
                    case 1: {
                        return Level.NOTE;
                    }
                    case 2: {
                        return Level.WARNING;
                    }
                    case 3: {
                        return Level.ERROR;
                    }
                    default: {
                        return null;
                    }
                }
            }
            
            public static Internal.EnumLiteMap<Level> internalGetValueMap() {
                return Level.internalValueMap;
            }
            
            public final Descriptors.EnumValueDescriptor getValueDescriptor() {
                return getDescriptor().getValues().get(this.ordinal());
            }
            
            public final Descriptors.EnumDescriptor getDescriptorForType() {
                return getDescriptor();
            }
            
            public static final Descriptors.EnumDescriptor getDescriptor() {
                return Warning.getDescriptor().getEnumTypes().get(0);
            }
            
            public static Level valueOf(final Descriptors.EnumValueDescriptor desc) {
                if (desc.getType() != getDescriptor()) {
                    throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
                }
                return Level.VALUES[desc.getIndex()];
            }
            
            private Level(final int value) {
                this.value = value;
            }
            
            static {
                internalValueMap = (Internal.EnumLiteMap)new Internal.EnumLiteMap<Level>() {
                    public Level findValueByNumber(final int number) {
                        return Level.forNumber(number);
                    }
                };
                VALUES = values();
            }
        }
        
        public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements WarningOrBuilder
        {
            private int bitField0_;
            private int level_;
            private int code_;
            private Object msg_;
            
            public static final Descriptors.Descriptor getDescriptor() {
                return MysqlxNotice.internal_static_Mysqlx_Notice_Warning_descriptor;
            }
            
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return MysqlxNotice.internal_static_Mysqlx_Notice_Warning_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)Warning.class, (Class)Builder.class);
            }
            
            private Builder() {
                this.level_ = 2;
                this.msg_ = "";
                this.maybeForceBuilderInitialization();
            }
            
            private Builder(final GeneratedMessageV3.BuilderParent parent) {
                super(parent);
                this.level_ = 2;
                this.msg_ = "";
                this.maybeForceBuilderInitialization();
            }
            
            private void maybeForceBuilderInitialization() {
                if (Warning.alwaysUseFieldBuilders) {}
            }
            
            public Builder clear() {
                super.clear();
                this.level_ = 2;
                this.bitField0_ &= 0xFFFFFFFE;
                this.code_ = 0;
                this.bitField0_ &= 0xFFFFFFFD;
                this.msg_ = "";
                this.bitField0_ &= 0xFFFFFFFB;
                return this;
            }
            
            public Descriptors.Descriptor getDescriptorForType() {
                return MysqlxNotice.internal_static_Mysqlx_Notice_Warning_descriptor;
            }
            
            public Warning getDefaultInstanceForType() {
                return Warning.getDefaultInstance();
            }
            
            public Warning build() {
                final Warning result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw newUninitializedMessageException((Message)result);
                }
                return result;
            }
            
            public Warning buildPartial() {
                final Warning result = new Warning((GeneratedMessageV3.Builder)this);
                final int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 0x1) == 0x1) {
                    to_bitField0_ |= 0x1;
                }
                result.level_ = this.level_;
                if ((from_bitField0_ & 0x2) == 0x2) {
                    to_bitField0_ |= 0x2;
                }
                result.code_ = this.code_;
                if ((from_bitField0_ & 0x4) == 0x4) {
                    to_bitField0_ |= 0x4;
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
                if (other instanceof Warning) {
                    return this.mergeFrom((Warning)other);
                }
                super.mergeFrom(other);
                return this;
            }
            
            public Builder mergeFrom(final Warning other) {
                if (other == Warning.getDefaultInstance()) {
                    return this;
                }
                if (other.hasLevel()) {
                    this.setLevel(other.getLevel());
                }
                if (other.hasCode()) {
                    this.setCode(other.getCode());
                }
                if (other.hasMsg()) {
                    this.bitField0_ |= 0x4;
                    this.msg_ = other.msg_;
                    this.onChanged();
                }
                this.mergeUnknownFields(other.unknownFields);
                this.onChanged();
                return this;
            }
            
            public final boolean isInitialized() {
                return this.hasCode() && this.hasMsg();
            }
            
            public Builder mergeFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
                Warning parsedMessage = null;
                try {
                    parsedMessage = (Warning)Warning.PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (Warning)e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                }
                finally {
                    if (parsedMessage != null) {
                        this.mergeFrom(parsedMessage);
                    }
                }
                return this;
            }
            
            public boolean hasLevel() {
                return (this.bitField0_ & 0x1) == 0x1;
            }
            
            public Level getLevel() {
                final Level result = Level.valueOf(this.level_);
                return (result == null) ? Level.WARNING : result;
            }
            
            public Builder setLevel(final Level value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 0x1;
                this.level_ = value.getNumber();
                this.onChanged();
                return this;
            }
            
            public Builder clearLevel() {
                this.bitField0_ &= 0xFFFFFFFE;
                this.level_ = 2;
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
            
            public boolean hasMsg() {
                return (this.bitField0_ & 0x4) == 0x4;
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
                this.bitField0_ |= 0x4;
                this.msg_ = value;
                this.onChanged();
                return this;
            }
            
            public Builder clearMsg() {
                this.bitField0_ &= 0xFFFFFFFB;
                this.msg_ = Warning.getDefaultInstance().getMsg();
                this.onChanged();
                return this;
            }
            
            public Builder setMsgBytes(final ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 0x4;
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
    
    public static final class SessionVariableChanged extends GeneratedMessageV3 implements SessionVariableChangedOrBuilder
    {
        private static final long serialVersionUID = 0L;
        private int bitField0_;
        public static final int PARAM_FIELD_NUMBER = 1;
        private volatile Object param_;
        public static final int VALUE_FIELD_NUMBER = 2;
        private MysqlxDatatypes.Scalar value_;
        private byte memoizedIsInitialized;
        private static final SessionVariableChanged DEFAULT_INSTANCE;
        @Deprecated
        public static final Parser<SessionVariableChanged> PARSER;
        
        private SessionVariableChanged(final GeneratedMessageV3.Builder<?> builder) {
            super((GeneratedMessageV3.Builder)builder);
            this.memoizedIsInitialized = -1;
        }
        
        private SessionVariableChanged() {
            this.memoizedIsInitialized = -1;
            this.param_ = "";
        }
        
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }
        
        private SessionVariableChanged(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            this.param_ = bs;
                            continue;
                        }
                        case 18: {
                            MysqlxDatatypes.Scalar.Builder subBuilder = null;
                            if ((this.bitField0_ & 0x2) == 0x2) {
                                subBuilder = this.value_.toBuilder();
                            }
                            this.value_ = (MysqlxDatatypes.Scalar)input.readMessage((Parser)MysqlxDatatypes.Scalar.PARSER, extensionRegistry);
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
            return MysqlxNotice.internal_static_Mysqlx_Notice_SessionVariableChanged_descriptor;
        }
        
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return MysqlxNotice.internal_static_Mysqlx_Notice_SessionVariableChanged_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)SessionVariableChanged.class, (Class)Builder.class);
        }
        
        public boolean hasParam() {
            return (this.bitField0_ & 0x1) == 0x1;
        }
        
        public String getParam() {
            final Object ref = this.param_;
            if (ref instanceof String) {
                return (String)ref;
            }
            final ByteString bs = (ByteString)ref;
            final String s = bs.toStringUtf8();
            if (bs.isValidUtf8()) {
                this.param_ = s;
            }
            return s;
        }
        
        public ByteString getParamBytes() {
            final Object ref = this.param_;
            if (ref instanceof String) {
                final ByteString b = ByteString.copyFromUtf8((String)ref);
                return (ByteString)(this.param_ = b);
            }
            return (ByteString)ref;
        }
        
        public boolean hasValue() {
            return (this.bitField0_ & 0x2) == 0x2;
        }
        
        public MysqlxDatatypes.Scalar getValue() {
            return (this.value_ == null) ? MysqlxDatatypes.Scalar.getDefaultInstance() : this.value_;
        }
        
        public MysqlxDatatypes.ScalarOrBuilder getValueOrBuilder() {
            return (this.value_ == null) ? MysqlxDatatypes.Scalar.getDefaultInstance() : this.value_;
        }
        
        public final boolean isInitialized() {
            final byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (!this.hasParam()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            if (this.hasValue() && !this.getValue().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            this.memoizedIsInitialized = 1;
            return true;
        }
        
        public void writeTo(final CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 0x1) == 0x1) {
                GeneratedMessageV3.writeString(output, 1, this.param_);
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
                size += GeneratedMessageV3.computeStringSize(1, this.param_);
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
            if (!(obj instanceof SessionVariableChanged)) {
                return super.equals(obj);
            }
            final SessionVariableChanged other = (SessionVariableChanged)obj;
            boolean result = true;
            result = (result && this.hasParam() == other.hasParam());
            if (this.hasParam()) {
                result = (result && this.getParam().equals(other.getParam()));
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
            if (this.hasParam()) {
                hash = 37 * hash + 1;
                hash = 53 * hash + this.getParam().hashCode();
            }
            if (this.hasValue()) {
                hash = 37 * hash + 2;
                hash = 53 * hash + this.getValue().hashCode();
            }
            hash = 29 * hash + this.unknownFields.hashCode();
            return this.memoizedHashCode = hash;
        }
        
        public static SessionVariableChanged parseFrom(final ByteBuffer data) throws InvalidProtocolBufferException {
            return (SessionVariableChanged)SessionVariableChanged.PARSER.parseFrom(data);
        }
        
        public static SessionVariableChanged parseFrom(final ByteBuffer data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (SessionVariableChanged)SessionVariableChanged.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static SessionVariableChanged parseFrom(final ByteString data) throws InvalidProtocolBufferException {
            return (SessionVariableChanged)SessionVariableChanged.PARSER.parseFrom(data);
        }
        
        public static SessionVariableChanged parseFrom(final ByteString data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (SessionVariableChanged)SessionVariableChanged.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static SessionVariableChanged parseFrom(final byte[] data) throws InvalidProtocolBufferException {
            return (SessionVariableChanged)SessionVariableChanged.PARSER.parseFrom(data);
        }
        
        public static SessionVariableChanged parseFrom(final byte[] data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (SessionVariableChanged)SessionVariableChanged.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static SessionVariableChanged parseFrom(final InputStream input) throws IOException {
            return (SessionVariableChanged)GeneratedMessageV3.parseWithIOException((Parser)SessionVariableChanged.PARSER, input);
        }
        
        public static SessionVariableChanged parseFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (SessionVariableChanged)GeneratedMessageV3.parseWithIOException((Parser)SessionVariableChanged.PARSER, input, extensionRegistry);
        }
        
        public static SessionVariableChanged parseDelimitedFrom(final InputStream input) throws IOException {
            return (SessionVariableChanged)GeneratedMessageV3.parseDelimitedWithIOException((Parser)SessionVariableChanged.PARSER, input);
        }
        
        public static SessionVariableChanged parseDelimitedFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (SessionVariableChanged)GeneratedMessageV3.parseDelimitedWithIOException((Parser)SessionVariableChanged.PARSER, input, extensionRegistry);
        }
        
        public static SessionVariableChanged parseFrom(final CodedInputStream input) throws IOException {
            return (SessionVariableChanged)GeneratedMessageV3.parseWithIOException((Parser)SessionVariableChanged.PARSER, input);
        }
        
        public static SessionVariableChanged parseFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (SessionVariableChanged)GeneratedMessageV3.parseWithIOException((Parser)SessionVariableChanged.PARSER, input, extensionRegistry);
        }
        
        public Builder newBuilderForType() {
            return newBuilder();
        }
        
        public static Builder newBuilder() {
            return SessionVariableChanged.DEFAULT_INSTANCE.toBuilder();
        }
        
        public static Builder newBuilder(final SessionVariableChanged prototype) {
            return SessionVariableChanged.DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }
        
        public Builder toBuilder() {
            return (this == SessionVariableChanged.DEFAULT_INSTANCE) ? new Builder() : new Builder().mergeFrom(this);
        }
        
        protected Builder newBuilderForType(final GeneratedMessageV3.BuilderParent parent) {
            final Builder builder = new Builder(parent);
            return builder;
        }
        
        public static SessionVariableChanged getDefaultInstance() {
            return SessionVariableChanged.DEFAULT_INSTANCE;
        }
        
        public static Parser<SessionVariableChanged> parser() {
            return SessionVariableChanged.PARSER;
        }
        
        public Parser<SessionVariableChanged> getParserForType() {
            return SessionVariableChanged.PARSER;
        }
        
        public SessionVariableChanged getDefaultInstanceForType() {
            return SessionVariableChanged.DEFAULT_INSTANCE;
        }
        
        static {
            DEFAULT_INSTANCE = new SessionVariableChanged();
            PARSER = (Parser)new AbstractParser<SessionVariableChanged>() {
                public SessionVariableChanged parsePartialFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new SessionVariableChanged(input, extensionRegistry);
                }
            };
        }
        
        public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements SessionVariableChangedOrBuilder
        {
            private int bitField0_;
            private Object param_;
            private MysqlxDatatypes.Scalar value_;
            private SingleFieldBuilderV3<MysqlxDatatypes.Scalar, MysqlxDatatypes.Scalar.Builder, MysqlxDatatypes.ScalarOrBuilder> valueBuilder_;
            
            public static final Descriptors.Descriptor getDescriptor() {
                return MysqlxNotice.internal_static_Mysqlx_Notice_SessionVariableChanged_descriptor;
            }
            
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return MysqlxNotice.internal_static_Mysqlx_Notice_SessionVariableChanged_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)SessionVariableChanged.class, (Class)Builder.class);
            }
            
            private Builder() {
                this.param_ = "";
                this.value_ = null;
                this.maybeForceBuilderInitialization();
            }
            
            private Builder(final GeneratedMessageV3.BuilderParent parent) {
                super(parent);
                this.param_ = "";
                this.value_ = null;
                this.maybeForceBuilderInitialization();
            }
            
            private void maybeForceBuilderInitialization() {
                if (SessionVariableChanged.alwaysUseFieldBuilders) {
                    this.getValueFieldBuilder();
                }
            }
            
            public Builder clear() {
                super.clear();
                this.param_ = "";
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
                return MysqlxNotice.internal_static_Mysqlx_Notice_SessionVariableChanged_descriptor;
            }
            
            public SessionVariableChanged getDefaultInstanceForType() {
                return SessionVariableChanged.getDefaultInstance();
            }
            
            public SessionVariableChanged build() {
                final SessionVariableChanged result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw newUninitializedMessageException((Message)result);
                }
                return result;
            }
            
            public SessionVariableChanged buildPartial() {
                final SessionVariableChanged result = new SessionVariableChanged((GeneratedMessageV3.Builder)this);
                final int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 0x1) == 0x1) {
                    to_bitField0_ |= 0x1;
                }
                result.param_ = this.param_;
                if ((from_bitField0_ & 0x2) == 0x2) {
                    to_bitField0_ |= 0x2;
                }
                if (this.valueBuilder_ == null) {
                    result.value_ = this.value_;
                }
                else {
                    result.value_ = (MysqlxDatatypes.Scalar)this.valueBuilder_.build();
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
                if (other instanceof SessionVariableChanged) {
                    return this.mergeFrom((SessionVariableChanged)other);
                }
                super.mergeFrom(other);
                return this;
            }
            
            public Builder mergeFrom(final SessionVariableChanged other) {
                if (other == SessionVariableChanged.getDefaultInstance()) {
                    return this;
                }
                if (other.hasParam()) {
                    this.bitField0_ |= 0x1;
                    this.param_ = other.param_;
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
                return this.hasParam() && (!this.hasValue() || this.getValue().isInitialized());
            }
            
            public Builder mergeFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
                SessionVariableChanged parsedMessage = null;
                try {
                    parsedMessage = (SessionVariableChanged)SessionVariableChanged.PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (SessionVariableChanged)e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                }
                finally {
                    if (parsedMessage != null) {
                        this.mergeFrom(parsedMessage);
                    }
                }
                return this;
            }
            
            public boolean hasParam() {
                return (this.bitField0_ & 0x1) == 0x1;
            }
            
            public String getParam() {
                final Object ref = this.param_;
                if (!(ref instanceof String)) {
                    final ByteString bs = (ByteString)ref;
                    final String s = bs.toStringUtf8();
                    if (bs.isValidUtf8()) {
                        this.param_ = s;
                    }
                    return s;
                }
                return (String)ref;
            }
            
            public ByteString getParamBytes() {
                final Object ref = this.param_;
                if (ref instanceof String) {
                    final ByteString b = ByteString.copyFromUtf8((String)ref);
                    return (ByteString)(this.param_ = b);
                }
                return (ByteString)ref;
            }
            
            public Builder setParam(final String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 0x1;
                this.param_ = value;
                this.onChanged();
                return this;
            }
            
            public Builder clearParam() {
                this.bitField0_ &= 0xFFFFFFFE;
                this.param_ = SessionVariableChanged.getDefaultInstance().getParam();
                this.onChanged();
                return this;
            }
            
            public Builder setParamBytes(final ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 0x1;
                this.param_ = value;
                this.onChanged();
                return this;
            }
            
            public boolean hasValue() {
                return (this.bitField0_ & 0x2) == 0x2;
            }
            
            public MysqlxDatatypes.Scalar getValue() {
                if (this.valueBuilder_ == null) {
                    return (this.value_ == null) ? MysqlxDatatypes.Scalar.getDefaultInstance() : this.value_;
                }
                return (MysqlxDatatypes.Scalar)this.valueBuilder_.getMessage();
            }
            
            public Builder setValue(final MysqlxDatatypes.Scalar value) {
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
            
            public Builder setValue(final MysqlxDatatypes.Scalar.Builder builderForValue) {
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
            
            public Builder mergeValue(final MysqlxDatatypes.Scalar value) {
                if (this.valueBuilder_ == null) {
                    if ((this.bitField0_ & 0x2) == 0x2 && this.value_ != null && this.value_ != MysqlxDatatypes.Scalar.getDefaultInstance()) {
                        this.value_ = MysqlxDatatypes.Scalar.newBuilder(this.value_).mergeFrom(value).buildPartial();
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
            
            public MysqlxDatatypes.Scalar.Builder getValueBuilder() {
                this.bitField0_ |= 0x2;
                this.onChanged();
                return (MysqlxDatatypes.Scalar.Builder)this.getValueFieldBuilder().getBuilder();
            }
            
            public MysqlxDatatypes.ScalarOrBuilder getValueOrBuilder() {
                if (this.valueBuilder_ != null) {
                    return (MysqlxDatatypes.ScalarOrBuilder)this.valueBuilder_.getMessageOrBuilder();
                }
                return (this.value_ == null) ? MysqlxDatatypes.Scalar.getDefaultInstance() : this.value_;
            }
            
            private SingleFieldBuilderV3<MysqlxDatatypes.Scalar, MysqlxDatatypes.Scalar.Builder, MysqlxDatatypes.ScalarOrBuilder> getValueFieldBuilder() {
                if (this.valueBuilder_ == null) {
                    this.valueBuilder_ = (SingleFieldBuilderV3<MysqlxDatatypes.Scalar, MysqlxDatatypes.Scalar.Builder, MysqlxDatatypes.ScalarOrBuilder>)new SingleFieldBuilderV3((AbstractMessage)this.getValue(), (AbstractMessage.BuilderParent)this.getParentForChildren(), this.isClean());
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
    
    public static final class SessionStateChanged extends GeneratedMessageV3 implements SessionStateChangedOrBuilder
    {
        private static final long serialVersionUID = 0L;
        private int bitField0_;
        public static final int PARAM_FIELD_NUMBER = 1;
        private int param_;
        public static final int VALUE_FIELD_NUMBER = 2;
        private List<MysqlxDatatypes.Scalar> value_;
        private byte memoizedIsInitialized;
        private static final SessionStateChanged DEFAULT_INSTANCE;
        @Deprecated
        public static final Parser<SessionStateChanged> PARSER;
        
        private SessionStateChanged(final GeneratedMessageV3.Builder<?> builder) {
            super((GeneratedMessageV3.Builder)builder);
            this.memoizedIsInitialized = -1;
        }
        
        private SessionStateChanged() {
            this.memoizedIsInitialized = -1;
            this.param_ = 1;
            this.value_ = Collections.emptyList();
        }
        
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }
        
        private SessionStateChanged(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                        case 8: {
                            final int rawValue = input.readEnum();
                            final Parameter value = Parameter.valueOf(rawValue);
                            if (value == null) {
                                unknownFields.mergeVarintField(1, rawValue);
                                continue;
                            }
                            this.bitField0_ |= 0x1;
                            this.param_ = rawValue;
                            continue;
                        }
                        case 18: {
                            if ((mutable_bitField0_ & 0x2) != 0x2) {
                                this.value_ = new ArrayList<MysqlxDatatypes.Scalar>();
                                mutable_bitField0_ |= 0x2;
                            }
                            this.value_.add((MysqlxDatatypes.Scalar)input.readMessage((Parser)MysqlxDatatypes.Scalar.PARSER, extensionRegistry));
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
                if ((mutable_bitField0_ & 0x2) == 0x2) {
                    this.value_ = Collections.unmodifiableList((List<? extends MysqlxDatatypes.Scalar>)this.value_);
                }
                this.unknownFields = unknownFields.build();
                this.makeExtensionsImmutable();
            }
        }
        
        public static final Descriptors.Descriptor getDescriptor() {
            return MysqlxNotice.internal_static_Mysqlx_Notice_SessionStateChanged_descriptor;
        }
        
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return MysqlxNotice.internal_static_Mysqlx_Notice_SessionStateChanged_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)SessionStateChanged.class, (Class)Builder.class);
        }
        
        public boolean hasParam() {
            return (this.bitField0_ & 0x1) == 0x1;
        }
        
        public Parameter getParam() {
            final Parameter result = Parameter.valueOf(this.param_);
            return (result == null) ? Parameter.CURRENT_SCHEMA : result;
        }
        
        public List<MysqlxDatatypes.Scalar> getValueList() {
            return this.value_;
        }
        
        public List<? extends MysqlxDatatypes.ScalarOrBuilder> getValueOrBuilderList() {
            return this.value_;
        }
        
        public int getValueCount() {
            return this.value_.size();
        }
        
        public MysqlxDatatypes.Scalar getValue(final int index) {
            return this.value_.get(index);
        }
        
        public MysqlxDatatypes.ScalarOrBuilder getValueOrBuilder(final int index) {
            return this.value_.get(index);
        }
        
        public final boolean isInitialized() {
            final byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (!this.hasParam()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            for (int i = 0; i < this.getValueCount(); ++i) {
                if (!this.getValue(i).isInitialized()) {
                    this.memoizedIsInitialized = 0;
                    return false;
                }
            }
            this.memoizedIsInitialized = 1;
            return true;
        }
        
        public void writeTo(final CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 0x1) == 0x1) {
                output.writeEnum(1, this.param_);
            }
            for (int i = 0; i < this.value_.size(); ++i) {
                output.writeMessage(2, (MessageLite)this.value_.get(i));
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
                size += CodedOutputStream.computeEnumSize(1, this.param_);
            }
            for (int i = 0; i < this.value_.size(); ++i) {
                size += CodedOutputStream.computeMessageSize(2, (MessageLite)this.value_.get(i));
            }
            size += this.unknownFields.getSerializedSize();
            return this.memoizedSize = size;
        }
        
        public boolean equals(final Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof SessionStateChanged)) {
                return super.equals(obj);
            }
            final SessionStateChanged other = (SessionStateChanged)obj;
            boolean result = true;
            result = (result && this.hasParam() == other.hasParam());
            if (this.hasParam()) {
                result = (result && this.param_ == other.param_);
            }
            result = (result && this.getValueList().equals(other.getValueList()));
            result = (result && this.unknownFields.equals((Object)other.unknownFields));
            return result;
        }
        
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + getDescriptor().hashCode();
            if (this.hasParam()) {
                hash = 37 * hash + 1;
                hash = 53 * hash + this.param_;
            }
            if (this.getValueCount() > 0) {
                hash = 37 * hash + 2;
                hash = 53 * hash + this.getValueList().hashCode();
            }
            hash = 29 * hash + this.unknownFields.hashCode();
            return this.memoizedHashCode = hash;
        }
        
        public static SessionStateChanged parseFrom(final ByteBuffer data) throws InvalidProtocolBufferException {
            return (SessionStateChanged)SessionStateChanged.PARSER.parseFrom(data);
        }
        
        public static SessionStateChanged parseFrom(final ByteBuffer data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (SessionStateChanged)SessionStateChanged.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static SessionStateChanged parseFrom(final ByteString data) throws InvalidProtocolBufferException {
            return (SessionStateChanged)SessionStateChanged.PARSER.parseFrom(data);
        }
        
        public static SessionStateChanged parseFrom(final ByteString data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (SessionStateChanged)SessionStateChanged.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static SessionStateChanged parseFrom(final byte[] data) throws InvalidProtocolBufferException {
            return (SessionStateChanged)SessionStateChanged.PARSER.parseFrom(data);
        }
        
        public static SessionStateChanged parseFrom(final byte[] data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (SessionStateChanged)SessionStateChanged.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static SessionStateChanged parseFrom(final InputStream input) throws IOException {
            return (SessionStateChanged)GeneratedMessageV3.parseWithIOException((Parser)SessionStateChanged.PARSER, input);
        }
        
        public static SessionStateChanged parseFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (SessionStateChanged)GeneratedMessageV3.parseWithIOException((Parser)SessionStateChanged.PARSER, input, extensionRegistry);
        }
        
        public static SessionStateChanged parseDelimitedFrom(final InputStream input) throws IOException {
            return (SessionStateChanged)GeneratedMessageV3.parseDelimitedWithIOException((Parser)SessionStateChanged.PARSER, input);
        }
        
        public static SessionStateChanged parseDelimitedFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (SessionStateChanged)GeneratedMessageV3.parseDelimitedWithIOException((Parser)SessionStateChanged.PARSER, input, extensionRegistry);
        }
        
        public static SessionStateChanged parseFrom(final CodedInputStream input) throws IOException {
            return (SessionStateChanged)GeneratedMessageV3.parseWithIOException((Parser)SessionStateChanged.PARSER, input);
        }
        
        public static SessionStateChanged parseFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (SessionStateChanged)GeneratedMessageV3.parseWithIOException((Parser)SessionStateChanged.PARSER, input, extensionRegistry);
        }
        
        public Builder newBuilderForType() {
            return newBuilder();
        }
        
        public static Builder newBuilder() {
            return SessionStateChanged.DEFAULT_INSTANCE.toBuilder();
        }
        
        public static Builder newBuilder(final SessionStateChanged prototype) {
            return SessionStateChanged.DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }
        
        public Builder toBuilder() {
            return (this == SessionStateChanged.DEFAULT_INSTANCE) ? new Builder() : new Builder().mergeFrom(this);
        }
        
        protected Builder newBuilderForType(final GeneratedMessageV3.BuilderParent parent) {
            final Builder builder = new Builder(parent);
            return builder;
        }
        
        public static SessionStateChanged getDefaultInstance() {
            return SessionStateChanged.DEFAULT_INSTANCE;
        }
        
        public static Parser<SessionStateChanged> parser() {
            return SessionStateChanged.PARSER;
        }
        
        public Parser<SessionStateChanged> getParserForType() {
            return SessionStateChanged.PARSER;
        }
        
        public SessionStateChanged getDefaultInstanceForType() {
            return SessionStateChanged.DEFAULT_INSTANCE;
        }
        
        static {
            DEFAULT_INSTANCE = new SessionStateChanged();
            PARSER = (Parser)new AbstractParser<SessionStateChanged>() {
                public SessionStateChanged parsePartialFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new SessionStateChanged(input, extensionRegistry);
                }
            };
        }
        
        public enum Parameter implements ProtocolMessageEnum
        {
            CURRENT_SCHEMA(1), 
            ACCOUNT_EXPIRED(2), 
            GENERATED_INSERT_ID(3), 
            ROWS_AFFECTED(4), 
            ROWS_FOUND(5), 
            ROWS_MATCHED(6), 
            TRX_COMMITTED(7), 
            TRX_ROLLEDBACK(9), 
            PRODUCED_MESSAGE(10), 
            CLIENT_ID_ASSIGNED(11), 
            GENERATED_DOCUMENT_IDS(12);
            
            public static final int CURRENT_SCHEMA_VALUE = 1;
            public static final int ACCOUNT_EXPIRED_VALUE = 2;
            public static final int GENERATED_INSERT_ID_VALUE = 3;
            public static final int ROWS_AFFECTED_VALUE = 4;
            public static final int ROWS_FOUND_VALUE = 5;
            public static final int ROWS_MATCHED_VALUE = 6;
            public static final int TRX_COMMITTED_VALUE = 7;
            public static final int TRX_ROLLEDBACK_VALUE = 9;
            public static final int PRODUCED_MESSAGE_VALUE = 10;
            public static final int CLIENT_ID_ASSIGNED_VALUE = 11;
            public static final int GENERATED_DOCUMENT_IDS_VALUE = 12;
            private static final Internal.EnumLiteMap<Parameter> internalValueMap;
            private static final Parameter[] VALUES;
            private final int value;
            
            public final int getNumber() {
                return this.value;
            }
            
            @Deprecated
            public static Parameter valueOf(final int value) {
                return forNumber(value);
            }
            
            public static Parameter forNumber(final int value) {
                switch (value) {
                    case 1: {
                        return Parameter.CURRENT_SCHEMA;
                    }
                    case 2: {
                        return Parameter.ACCOUNT_EXPIRED;
                    }
                    case 3: {
                        return Parameter.GENERATED_INSERT_ID;
                    }
                    case 4: {
                        return Parameter.ROWS_AFFECTED;
                    }
                    case 5: {
                        return Parameter.ROWS_FOUND;
                    }
                    case 6: {
                        return Parameter.ROWS_MATCHED;
                    }
                    case 7: {
                        return Parameter.TRX_COMMITTED;
                    }
                    case 9: {
                        return Parameter.TRX_ROLLEDBACK;
                    }
                    case 10: {
                        return Parameter.PRODUCED_MESSAGE;
                    }
                    case 11: {
                        return Parameter.CLIENT_ID_ASSIGNED;
                    }
                    case 12: {
                        return Parameter.GENERATED_DOCUMENT_IDS;
                    }
                    default: {
                        return null;
                    }
                }
            }
            
            public static Internal.EnumLiteMap<Parameter> internalGetValueMap() {
                return Parameter.internalValueMap;
            }
            
            public final Descriptors.EnumValueDescriptor getValueDescriptor() {
                return getDescriptor().getValues().get(this.ordinal());
            }
            
            public final Descriptors.EnumDescriptor getDescriptorForType() {
                return getDescriptor();
            }
            
            public static final Descriptors.EnumDescriptor getDescriptor() {
                return SessionStateChanged.getDescriptor().getEnumTypes().get(0);
            }
            
            public static Parameter valueOf(final Descriptors.EnumValueDescriptor desc) {
                if (desc.getType() != getDescriptor()) {
                    throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
                }
                return Parameter.VALUES[desc.getIndex()];
            }
            
            private Parameter(final int value) {
                this.value = value;
            }
            
            static {
                internalValueMap = (Internal.EnumLiteMap)new Internal.EnumLiteMap<Parameter>() {
                    public Parameter findValueByNumber(final int number) {
                        return Parameter.forNumber(number);
                    }
                };
                VALUES = values();
            }
        }
        
        public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements SessionStateChangedOrBuilder
        {
            private int bitField0_;
            private int param_;
            private List<MysqlxDatatypes.Scalar> value_;
            private RepeatedFieldBuilderV3<MysqlxDatatypes.Scalar, MysqlxDatatypes.Scalar.Builder, MysqlxDatatypes.ScalarOrBuilder> valueBuilder_;
            
            public static final Descriptors.Descriptor getDescriptor() {
                return MysqlxNotice.internal_static_Mysqlx_Notice_SessionStateChanged_descriptor;
            }
            
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return MysqlxNotice.internal_static_Mysqlx_Notice_SessionStateChanged_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)SessionStateChanged.class, (Class)Builder.class);
            }
            
            private Builder() {
                this.param_ = 1;
                this.value_ = Collections.emptyList();
                this.maybeForceBuilderInitialization();
            }
            
            private Builder(final GeneratedMessageV3.BuilderParent parent) {
                super(parent);
                this.param_ = 1;
                this.value_ = Collections.emptyList();
                this.maybeForceBuilderInitialization();
            }
            
            private void maybeForceBuilderInitialization() {
                if (SessionStateChanged.alwaysUseFieldBuilders) {
                    this.getValueFieldBuilder();
                }
            }
            
            public Builder clear() {
                super.clear();
                this.param_ = 1;
                this.bitField0_ &= 0xFFFFFFFE;
                if (this.valueBuilder_ == null) {
                    this.value_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFFD;
                }
                else {
                    this.valueBuilder_.clear();
                }
                return this;
            }
            
            public Descriptors.Descriptor getDescriptorForType() {
                return MysqlxNotice.internal_static_Mysqlx_Notice_SessionStateChanged_descriptor;
            }
            
            public SessionStateChanged getDefaultInstanceForType() {
                return SessionStateChanged.getDefaultInstance();
            }
            
            public SessionStateChanged build() {
                final SessionStateChanged result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw newUninitializedMessageException((Message)result);
                }
                return result;
            }
            
            public SessionStateChanged buildPartial() {
                final SessionStateChanged result = new SessionStateChanged((GeneratedMessageV3.Builder)this);
                final int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 0x1) == 0x1) {
                    to_bitField0_ |= 0x1;
                }
                result.param_ = this.param_;
                if (this.valueBuilder_ == null) {
                    if ((this.bitField0_ & 0x2) == 0x2) {
                        this.value_ = Collections.unmodifiableList((List<? extends MysqlxDatatypes.Scalar>)this.value_);
                        this.bitField0_ &= 0xFFFFFFFD;
                    }
                    result.value_ = this.value_;
                }
                else {
                    result.value_ = (List<MysqlxDatatypes.Scalar>)this.valueBuilder_.build();
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
                if (other instanceof SessionStateChanged) {
                    return this.mergeFrom((SessionStateChanged)other);
                }
                super.mergeFrom(other);
                return this;
            }
            
            public Builder mergeFrom(final SessionStateChanged other) {
                if (other == SessionStateChanged.getDefaultInstance()) {
                    return this;
                }
                if (other.hasParam()) {
                    this.setParam(other.getParam());
                }
                if (this.valueBuilder_ == null) {
                    if (!other.value_.isEmpty()) {
                        if (this.value_.isEmpty()) {
                            this.value_ = other.value_;
                            this.bitField0_ &= 0xFFFFFFFD;
                        }
                        else {
                            this.ensureValueIsMutable();
                            this.value_.addAll(other.value_);
                        }
                        this.onChanged();
                    }
                }
                else if (!other.value_.isEmpty()) {
                    if (this.valueBuilder_.isEmpty()) {
                        this.valueBuilder_.dispose();
                        this.valueBuilder_ = null;
                        this.value_ = other.value_;
                        this.bitField0_ &= 0xFFFFFFFD;
                        this.valueBuilder_ = (SessionStateChanged.alwaysUseFieldBuilders ? this.getValueFieldBuilder() : null);
                    }
                    else {
                        this.valueBuilder_.addAllMessages((Iterable)other.value_);
                    }
                }
                this.mergeUnknownFields(other.unknownFields);
                this.onChanged();
                return this;
            }
            
            public final boolean isInitialized() {
                if (!this.hasParam()) {
                    return false;
                }
                for (int i = 0; i < this.getValueCount(); ++i) {
                    if (!this.getValue(i).isInitialized()) {
                        return false;
                    }
                }
                return true;
            }
            
            public Builder mergeFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
                SessionStateChanged parsedMessage = null;
                try {
                    parsedMessage = (SessionStateChanged)SessionStateChanged.PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (SessionStateChanged)e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                }
                finally {
                    if (parsedMessage != null) {
                        this.mergeFrom(parsedMessage);
                    }
                }
                return this;
            }
            
            public boolean hasParam() {
                return (this.bitField0_ & 0x1) == 0x1;
            }
            
            public Parameter getParam() {
                final Parameter result = Parameter.valueOf(this.param_);
                return (result == null) ? Parameter.CURRENT_SCHEMA : result;
            }
            
            public Builder setParam(final Parameter value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 0x1;
                this.param_ = value.getNumber();
                this.onChanged();
                return this;
            }
            
            public Builder clearParam() {
                this.bitField0_ &= 0xFFFFFFFE;
                this.param_ = 1;
                this.onChanged();
                return this;
            }
            
            private void ensureValueIsMutable() {
                if ((this.bitField0_ & 0x2) != 0x2) {
                    this.value_ = new ArrayList<MysqlxDatatypes.Scalar>(this.value_);
                    this.bitField0_ |= 0x2;
                }
            }
            
            public List<MysqlxDatatypes.Scalar> getValueList() {
                if (this.valueBuilder_ == null) {
                    return Collections.unmodifiableList((List<? extends MysqlxDatatypes.Scalar>)this.value_);
                }
                return (List<MysqlxDatatypes.Scalar>)this.valueBuilder_.getMessageList();
            }
            
            public int getValueCount() {
                if (this.valueBuilder_ == null) {
                    return this.value_.size();
                }
                return this.valueBuilder_.getCount();
            }
            
            public MysqlxDatatypes.Scalar getValue(final int index) {
                if (this.valueBuilder_ == null) {
                    return this.value_.get(index);
                }
                return (MysqlxDatatypes.Scalar)this.valueBuilder_.getMessage(index);
            }
            
            public Builder setValue(final int index, final MysqlxDatatypes.Scalar value) {
                if (this.valueBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureValueIsMutable();
                    this.value_.set(index, value);
                    this.onChanged();
                }
                else {
                    this.valueBuilder_.setMessage(index, (AbstractMessage)value);
                }
                return this;
            }
            
            public Builder setValue(final int index, final MysqlxDatatypes.Scalar.Builder builderForValue) {
                if (this.valueBuilder_ == null) {
                    this.ensureValueIsMutable();
                    this.value_.set(index, builderForValue.build());
                    this.onChanged();
                }
                else {
                    this.valueBuilder_.setMessage(index, (AbstractMessage)builderForValue.build());
                }
                return this;
            }
            
            public Builder addValue(final MysqlxDatatypes.Scalar value) {
                if (this.valueBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureValueIsMutable();
                    this.value_.add(value);
                    this.onChanged();
                }
                else {
                    this.valueBuilder_.addMessage((AbstractMessage)value);
                }
                return this;
            }
            
            public Builder addValue(final int index, final MysqlxDatatypes.Scalar value) {
                if (this.valueBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureValueIsMutable();
                    this.value_.add(index, value);
                    this.onChanged();
                }
                else {
                    this.valueBuilder_.addMessage(index, (AbstractMessage)value);
                }
                return this;
            }
            
            public Builder addValue(final MysqlxDatatypes.Scalar.Builder builderForValue) {
                if (this.valueBuilder_ == null) {
                    this.ensureValueIsMutable();
                    this.value_.add(builderForValue.build());
                    this.onChanged();
                }
                else {
                    this.valueBuilder_.addMessage((AbstractMessage)builderForValue.build());
                }
                return this;
            }
            
            public Builder addValue(final int index, final MysqlxDatatypes.Scalar.Builder builderForValue) {
                if (this.valueBuilder_ == null) {
                    this.ensureValueIsMutable();
                    this.value_.add(index, builderForValue.build());
                    this.onChanged();
                }
                else {
                    this.valueBuilder_.addMessage(index, (AbstractMessage)builderForValue.build());
                }
                return this;
            }
            
            public Builder addAllValue(final Iterable<? extends MysqlxDatatypes.Scalar> values) {
                if (this.valueBuilder_ == null) {
                    this.ensureValueIsMutable();
                    AbstractMessageLite.Builder.addAll((Iterable)values, (List)this.value_);
                    this.onChanged();
                }
                else {
                    this.valueBuilder_.addAllMessages((Iterable)values);
                }
                return this;
            }
            
            public Builder clearValue() {
                if (this.valueBuilder_ == null) {
                    this.value_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFFD;
                    this.onChanged();
                }
                else {
                    this.valueBuilder_.clear();
                }
                return this;
            }
            
            public Builder removeValue(final int index) {
                if (this.valueBuilder_ == null) {
                    this.ensureValueIsMutable();
                    this.value_.remove(index);
                    this.onChanged();
                }
                else {
                    this.valueBuilder_.remove(index);
                }
                return this;
            }
            
            public MysqlxDatatypes.Scalar.Builder getValueBuilder(final int index) {
                return (MysqlxDatatypes.Scalar.Builder)this.getValueFieldBuilder().getBuilder(index);
            }
            
            public MysqlxDatatypes.ScalarOrBuilder getValueOrBuilder(final int index) {
                if (this.valueBuilder_ == null) {
                    return this.value_.get(index);
                }
                return (MysqlxDatatypes.ScalarOrBuilder)this.valueBuilder_.getMessageOrBuilder(index);
            }
            
            public List<? extends MysqlxDatatypes.ScalarOrBuilder> getValueOrBuilderList() {
                if (this.valueBuilder_ != null) {
                    return (List<? extends MysqlxDatatypes.ScalarOrBuilder>)this.valueBuilder_.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList((List<? extends MysqlxDatatypes.ScalarOrBuilder>)this.value_);
            }
            
            public MysqlxDatatypes.Scalar.Builder addValueBuilder() {
                return (MysqlxDatatypes.Scalar.Builder)this.getValueFieldBuilder().addBuilder((AbstractMessage)MysqlxDatatypes.Scalar.getDefaultInstance());
            }
            
            public MysqlxDatatypes.Scalar.Builder addValueBuilder(final int index) {
                return (MysqlxDatatypes.Scalar.Builder)this.getValueFieldBuilder().addBuilder(index, (AbstractMessage)MysqlxDatatypes.Scalar.getDefaultInstance());
            }
            
            public List<MysqlxDatatypes.Scalar.Builder> getValueBuilderList() {
                return (List<MysqlxDatatypes.Scalar.Builder>)this.getValueFieldBuilder().getBuilderList();
            }
            
            private RepeatedFieldBuilderV3<MysqlxDatatypes.Scalar, MysqlxDatatypes.Scalar.Builder, MysqlxDatatypes.ScalarOrBuilder> getValueFieldBuilder() {
                if (this.valueBuilder_ == null) {
                    this.valueBuilder_ = (RepeatedFieldBuilderV3<MysqlxDatatypes.Scalar, MysqlxDatatypes.Scalar.Builder, MysqlxDatatypes.ScalarOrBuilder>)new RepeatedFieldBuilderV3((List)this.value_, (this.bitField0_ & 0x2) == 0x2, (AbstractMessage.BuilderParent)this.getParentForChildren(), this.isClean());
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
    
    public static final class GroupReplicationStateChanged extends GeneratedMessageV3 implements GroupReplicationStateChangedOrBuilder
    {
        private static final long serialVersionUID = 0L;
        private int bitField0_;
        public static final int TYPE_FIELD_NUMBER = 1;
        private int type_;
        public static final int VIEW_ID_FIELD_NUMBER = 2;
        private volatile Object viewId_;
        private byte memoizedIsInitialized;
        private static final GroupReplicationStateChanged DEFAULT_INSTANCE;
        @Deprecated
        public static final Parser<GroupReplicationStateChanged> PARSER;
        
        private GroupReplicationStateChanged(final GeneratedMessageV3.Builder<?> builder) {
            super((GeneratedMessageV3.Builder)builder);
            this.memoizedIsInitialized = -1;
        }
        
        private GroupReplicationStateChanged() {
            this.memoizedIsInitialized = -1;
            this.type_ = 0;
            this.viewId_ = "";
        }
        
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }
        
        private GroupReplicationStateChanged(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            this.bitField0_ |= 0x1;
                            this.type_ = input.readUInt32();
                            continue;
                        }
                        case 18: {
                            final ByteString bs = input.readBytes();
                            this.bitField0_ |= 0x2;
                            this.viewId_ = bs;
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
            return MysqlxNotice.internal_static_Mysqlx_Notice_GroupReplicationStateChanged_descriptor;
        }
        
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return MysqlxNotice.internal_static_Mysqlx_Notice_GroupReplicationStateChanged_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)GroupReplicationStateChanged.class, (Class)Builder.class);
        }
        
        public boolean hasType() {
            return (this.bitField0_ & 0x1) == 0x1;
        }
        
        public int getType() {
            return this.type_;
        }
        
        public boolean hasViewId() {
            return (this.bitField0_ & 0x2) == 0x2;
        }
        
        public String getViewId() {
            final Object ref = this.viewId_;
            if (ref instanceof String) {
                return (String)ref;
            }
            final ByteString bs = (ByteString)ref;
            final String s = bs.toStringUtf8();
            if (bs.isValidUtf8()) {
                this.viewId_ = s;
            }
            return s;
        }
        
        public ByteString getViewIdBytes() {
            final Object ref = this.viewId_;
            if (ref instanceof String) {
                final ByteString b = ByteString.copyFromUtf8((String)ref);
                return (ByteString)(this.viewId_ = b);
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
            if (!this.hasType()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            this.memoizedIsInitialized = 1;
            return true;
        }
        
        public void writeTo(final CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 0x1) == 0x1) {
                output.writeUInt32(1, this.type_);
            }
            if ((this.bitField0_ & 0x2) == 0x2) {
                GeneratedMessageV3.writeString(output, 2, this.viewId_);
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
                size += CodedOutputStream.computeUInt32Size(1, this.type_);
            }
            if ((this.bitField0_ & 0x2) == 0x2) {
                size += GeneratedMessageV3.computeStringSize(2, this.viewId_);
            }
            size += this.unknownFields.getSerializedSize();
            return this.memoizedSize = size;
        }
        
        public boolean equals(final Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof GroupReplicationStateChanged)) {
                return super.equals(obj);
            }
            final GroupReplicationStateChanged other = (GroupReplicationStateChanged)obj;
            boolean result = true;
            result = (result && this.hasType() == other.hasType());
            if (this.hasType()) {
                result = (result && this.getType() == other.getType());
            }
            result = (result && this.hasViewId() == other.hasViewId());
            if (this.hasViewId()) {
                result = (result && this.getViewId().equals(other.getViewId()));
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
            if (this.hasType()) {
                hash = 37 * hash + 1;
                hash = 53 * hash + this.getType();
            }
            if (this.hasViewId()) {
                hash = 37 * hash + 2;
                hash = 53 * hash + this.getViewId().hashCode();
            }
            hash = 29 * hash + this.unknownFields.hashCode();
            return this.memoizedHashCode = hash;
        }
        
        public static GroupReplicationStateChanged parseFrom(final ByteBuffer data) throws InvalidProtocolBufferException {
            return (GroupReplicationStateChanged)GroupReplicationStateChanged.PARSER.parseFrom(data);
        }
        
        public static GroupReplicationStateChanged parseFrom(final ByteBuffer data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (GroupReplicationStateChanged)GroupReplicationStateChanged.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static GroupReplicationStateChanged parseFrom(final ByteString data) throws InvalidProtocolBufferException {
            return (GroupReplicationStateChanged)GroupReplicationStateChanged.PARSER.parseFrom(data);
        }
        
        public static GroupReplicationStateChanged parseFrom(final ByteString data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (GroupReplicationStateChanged)GroupReplicationStateChanged.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static GroupReplicationStateChanged parseFrom(final byte[] data) throws InvalidProtocolBufferException {
            return (GroupReplicationStateChanged)GroupReplicationStateChanged.PARSER.parseFrom(data);
        }
        
        public static GroupReplicationStateChanged parseFrom(final byte[] data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (GroupReplicationStateChanged)GroupReplicationStateChanged.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static GroupReplicationStateChanged parseFrom(final InputStream input) throws IOException {
            return (GroupReplicationStateChanged)GeneratedMessageV3.parseWithIOException((Parser)GroupReplicationStateChanged.PARSER, input);
        }
        
        public static GroupReplicationStateChanged parseFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (GroupReplicationStateChanged)GeneratedMessageV3.parseWithIOException((Parser)GroupReplicationStateChanged.PARSER, input, extensionRegistry);
        }
        
        public static GroupReplicationStateChanged parseDelimitedFrom(final InputStream input) throws IOException {
            return (GroupReplicationStateChanged)GeneratedMessageV3.parseDelimitedWithIOException((Parser)GroupReplicationStateChanged.PARSER, input);
        }
        
        public static GroupReplicationStateChanged parseDelimitedFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (GroupReplicationStateChanged)GeneratedMessageV3.parseDelimitedWithIOException((Parser)GroupReplicationStateChanged.PARSER, input, extensionRegistry);
        }
        
        public static GroupReplicationStateChanged parseFrom(final CodedInputStream input) throws IOException {
            return (GroupReplicationStateChanged)GeneratedMessageV3.parseWithIOException((Parser)GroupReplicationStateChanged.PARSER, input);
        }
        
        public static GroupReplicationStateChanged parseFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (GroupReplicationStateChanged)GeneratedMessageV3.parseWithIOException((Parser)GroupReplicationStateChanged.PARSER, input, extensionRegistry);
        }
        
        public Builder newBuilderForType() {
            return newBuilder();
        }
        
        public static Builder newBuilder() {
            return GroupReplicationStateChanged.DEFAULT_INSTANCE.toBuilder();
        }
        
        public static Builder newBuilder(final GroupReplicationStateChanged prototype) {
            return GroupReplicationStateChanged.DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }
        
        public Builder toBuilder() {
            return (this == GroupReplicationStateChanged.DEFAULT_INSTANCE) ? new Builder() : new Builder().mergeFrom(this);
        }
        
        protected Builder newBuilderForType(final GeneratedMessageV3.BuilderParent parent) {
            final Builder builder = new Builder(parent);
            return builder;
        }
        
        public static GroupReplicationStateChanged getDefaultInstance() {
            return GroupReplicationStateChanged.DEFAULT_INSTANCE;
        }
        
        public static Parser<GroupReplicationStateChanged> parser() {
            return GroupReplicationStateChanged.PARSER;
        }
        
        public Parser<GroupReplicationStateChanged> getParserForType() {
            return GroupReplicationStateChanged.PARSER;
        }
        
        public GroupReplicationStateChanged getDefaultInstanceForType() {
            return GroupReplicationStateChanged.DEFAULT_INSTANCE;
        }
        
        static {
            DEFAULT_INSTANCE = new GroupReplicationStateChanged();
            PARSER = (Parser)new AbstractParser<GroupReplicationStateChanged>() {
                public GroupReplicationStateChanged parsePartialFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new GroupReplicationStateChanged(input, extensionRegistry);
                }
            };
        }
        
        public enum Type implements ProtocolMessageEnum
        {
            MEMBERSHIP_QUORUM_LOSS(1), 
            MEMBERSHIP_VIEW_CHANGE(2), 
            MEMBER_ROLE_CHANGE(3), 
            MEMBER_STATE_CHANGE(4);
            
            public static final int MEMBERSHIP_QUORUM_LOSS_VALUE = 1;
            public static final int MEMBERSHIP_VIEW_CHANGE_VALUE = 2;
            public static final int MEMBER_ROLE_CHANGE_VALUE = 3;
            public static final int MEMBER_STATE_CHANGE_VALUE = 4;
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
                        return Type.MEMBERSHIP_QUORUM_LOSS;
                    }
                    case 2: {
                        return Type.MEMBERSHIP_VIEW_CHANGE;
                    }
                    case 3: {
                        return Type.MEMBER_ROLE_CHANGE;
                    }
                    case 4: {
                        return Type.MEMBER_STATE_CHANGE;
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
                return GroupReplicationStateChanged.getDescriptor().getEnumTypes().get(0);
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
        
        public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements GroupReplicationStateChangedOrBuilder
        {
            private int bitField0_;
            private int type_;
            private Object viewId_;
            
            public static final Descriptors.Descriptor getDescriptor() {
                return MysqlxNotice.internal_static_Mysqlx_Notice_GroupReplicationStateChanged_descriptor;
            }
            
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return MysqlxNotice.internal_static_Mysqlx_Notice_GroupReplicationStateChanged_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)GroupReplicationStateChanged.class, (Class)Builder.class);
            }
            
            private Builder() {
                this.viewId_ = "";
                this.maybeForceBuilderInitialization();
            }
            
            private Builder(final GeneratedMessageV3.BuilderParent parent) {
                super(parent);
                this.viewId_ = "";
                this.maybeForceBuilderInitialization();
            }
            
            private void maybeForceBuilderInitialization() {
                if (GroupReplicationStateChanged.alwaysUseFieldBuilders) {}
            }
            
            public Builder clear() {
                super.clear();
                this.type_ = 0;
                this.bitField0_ &= 0xFFFFFFFE;
                this.viewId_ = "";
                this.bitField0_ &= 0xFFFFFFFD;
                return this;
            }
            
            public Descriptors.Descriptor getDescriptorForType() {
                return MysqlxNotice.internal_static_Mysqlx_Notice_GroupReplicationStateChanged_descriptor;
            }
            
            public GroupReplicationStateChanged getDefaultInstanceForType() {
                return GroupReplicationStateChanged.getDefaultInstance();
            }
            
            public GroupReplicationStateChanged build() {
                final GroupReplicationStateChanged result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw newUninitializedMessageException((Message)result);
                }
                return result;
            }
            
            public GroupReplicationStateChanged buildPartial() {
                final GroupReplicationStateChanged result = new GroupReplicationStateChanged((GeneratedMessageV3.Builder)this);
                final int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 0x1) == 0x1) {
                    to_bitField0_ |= 0x1;
                }
                result.type_ = this.type_;
                if ((from_bitField0_ & 0x2) == 0x2) {
                    to_bitField0_ |= 0x2;
                }
                result.viewId_ = this.viewId_;
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
                if (other instanceof GroupReplicationStateChanged) {
                    return this.mergeFrom((GroupReplicationStateChanged)other);
                }
                super.mergeFrom(other);
                return this;
            }
            
            public Builder mergeFrom(final GroupReplicationStateChanged other) {
                if (other == GroupReplicationStateChanged.getDefaultInstance()) {
                    return this;
                }
                if (other.hasType()) {
                    this.setType(other.getType());
                }
                if (other.hasViewId()) {
                    this.bitField0_ |= 0x2;
                    this.viewId_ = other.viewId_;
                    this.onChanged();
                }
                this.mergeUnknownFields(other.unknownFields);
                this.onChanged();
                return this;
            }
            
            public final boolean isInitialized() {
                return this.hasType();
            }
            
            public Builder mergeFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
                GroupReplicationStateChanged parsedMessage = null;
                try {
                    parsedMessage = (GroupReplicationStateChanged)GroupReplicationStateChanged.PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (GroupReplicationStateChanged)e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                }
                finally {
                    if (parsedMessage != null) {
                        this.mergeFrom(parsedMessage);
                    }
                }
                return this;
            }
            
            public boolean hasType() {
                return (this.bitField0_ & 0x1) == 0x1;
            }
            
            public int getType() {
                return this.type_;
            }
            
            public Builder setType(final int value) {
                this.bitField0_ |= 0x1;
                this.type_ = value;
                this.onChanged();
                return this;
            }
            
            public Builder clearType() {
                this.bitField0_ &= 0xFFFFFFFE;
                this.type_ = 0;
                this.onChanged();
                return this;
            }
            
            public boolean hasViewId() {
                return (this.bitField0_ & 0x2) == 0x2;
            }
            
            public String getViewId() {
                final Object ref = this.viewId_;
                if (!(ref instanceof String)) {
                    final ByteString bs = (ByteString)ref;
                    final String s = bs.toStringUtf8();
                    if (bs.isValidUtf8()) {
                        this.viewId_ = s;
                    }
                    return s;
                }
                return (String)ref;
            }
            
            public ByteString getViewIdBytes() {
                final Object ref = this.viewId_;
                if (ref instanceof String) {
                    final ByteString b = ByteString.copyFromUtf8((String)ref);
                    return (ByteString)(this.viewId_ = b);
                }
                return (ByteString)ref;
            }
            
            public Builder setViewId(final String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 0x2;
                this.viewId_ = value;
                this.onChanged();
                return this;
            }
            
            public Builder clearViewId() {
                this.bitField0_ &= 0xFFFFFFFD;
                this.viewId_ = GroupReplicationStateChanged.getDefaultInstance().getViewId();
                this.onChanged();
                return this;
            }
            
            public Builder setViewIdBytes(final ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 0x2;
                this.viewId_ = value;
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
    
    public interface GroupReplicationStateChangedOrBuilder extends MessageOrBuilder
    {
        boolean hasType();
        
        int getType();
        
        boolean hasViewId();
        
        String getViewId();
        
        ByteString getViewIdBytes();
    }
    
    public interface SessionStateChangedOrBuilder extends MessageOrBuilder
    {
        boolean hasParam();
        
        SessionStateChanged.Parameter getParam();
        
        List<MysqlxDatatypes.Scalar> getValueList();
        
        MysqlxDatatypes.Scalar getValue(final int p0);
        
        int getValueCount();
        
        List<? extends MysqlxDatatypes.ScalarOrBuilder> getValueOrBuilderList();
        
        MysqlxDatatypes.ScalarOrBuilder getValueOrBuilder(final int p0);
    }
    
    public interface SessionVariableChangedOrBuilder extends MessageOrBuilder
    {
        boolean hasParam();
        
        String getParam();
        
        ByteString getParamBytes();
        
        boolean hasValue();
        
        MysqlxDatatypes.Scalar getValue();
        
        MysqlxDatatypes.ScalarOrBuilder getValueOrBuilder();
    }
    
    public interface WarningOrBuilder extends MessageOrBuilder
    {
        boolean hasLevel();
        
        Warning.Level getLevel();
        
        boolean hasCode();
        
        int getCode();
        
        boolean hasMsg();
        
        String getMsg();
        
        ByteString getMsgBytes();
    }
    
    public interface FrameOrBuilder extends MessageOrBuilder
    {
        boolean hasType();
        
        int getType();
        
        boolean hasScope();
        
        Frame.Scope getScope();
        
        boolean hasPayload();
        
        ByteString getPayload();
    }
}
