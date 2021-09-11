
package com.mysql.cj.x.protobuf;

import com.google.protobuf.MessageOrBuilder;
import com.google.protobuf.AbstractMessageLite;
import com.google.protobuf.AbstractMessage;
import java.util.Collection;
import com.google.protobuf.RepeatedFieldBuilderV3;
import com.google.protobuf.AbstractParser;
import com.google.protobuf.Message;
import java.io.InputStream;
import java.nio.ByteBuffer;
import com.google.protobuf.Internal;
import com.google.protobuf.CodedOutputStream;
import java.io.IOException;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.MessageLite;
import java.util.ArrayList;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.UnknownFieldSet;
import java.util.Collections;
import com.google.protobuf.Parser;
import java.util.List;
import com.google.protobuf.ByteString;
import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.Descriptors;

public final class MysqlxSql
{
    private static final Descriptors.Descriptor internal_static_Mysqlx_Sql_StmtExecute_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_Mysqlx_Sql_StmtExecute_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_Mysqlx_Sql_StmtExecuteOk_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_Mysqlx_Sql_StmtExecuteOk_fieldAccessorTable;
    private static Descriptors.FileDescriptor descriptor;
    
    private MysqlxSql() {
    }
    
    public static void registerAllExtensions(final ExtensionRegistryLite registry) {
    }
    
    public static void registerAllExtensions(final ExtensionRegistry registry) {
        registerAllExtensions((ExtensionRegistryLite)registry);
    }
    
    public static Descriptors.FileDescriptor getDescriptor() {
        return MysqlxSql.descriptor;
    }
    
    static {
        final String[] descriptorData = { "\n\u0010mysqlx_sql.proto\u0012\nMysqlx.Sql\u001a\fmysqlx.proto\u001a\u0016mysqlx_datatypes.proto\"\u007f\n\u000bStmtExecute\u0012\u0016\n\tnamespace\u0018\u0003 \u0001(\t:\u0003sql\u0012\f\n\u0004stmt\u0018\u0001 \u0002(\f\u0012#\n\u0004args\u0018\u0002 \u0003(\u000b2\u0015.Mysqlx.Datatypes.Any\u0012\u001f\n\u0010compact_metadata\u0018\u0004 \u0001(\b:\u0005false:\u0004\u0088\u00ea0\f\"\u0015\n\rStmtExecuteOk:\u0004\u0090\u00ea0\u0011B\u0019\n\u0017com.mysql.cj.x.protobuf" };
        final Descriptors.FileDescriptor.InternalDescriptorAssigner assigner = (Descriptors.FileDescriptor.InternalDescriptorAssigner)new Descriptors.FileDescriptor.InternalDescriptorAssigner() {
            public ExtensionRegistry assignDescriptors(final Descriptors.FileDescriptor root) {
                MysqlxSql.descriptor = root;
                return null;
            }
        };
        Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new Descriptors.FileDescriptor[] { Mysqlx.getDescriptor(), MysqlxDatatypes.getDescriptor() }, assigner);
        internal_static_Mysqlx_Sql_StmtExecute_descriptor = getDescriptor().getMessageTypes().get(0);
        internal_static_Mysqlx_Sql_StmtExecute_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(MysqlxSql.internal_static_Mysqlx_Sql_StmtExecute_descriptor, new String[] { "Namespace", "Stmt", "Args", "CompactMetadata" });
        internal_static_Mysqlx_Sql_StmtExecuteOk_descriptor = getDescriptor().getMessageTypes().get(1);
        internal_static_Mysqlx_Sql_StmtExecuteOk_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(MysqlxSql.internal_static_Mysqlx_Sql_StmtExecuteOk_descriptor, new String[0]);
        final ExtensionRegistry registry = ExtensionRegistry.newInstance();
        registry.add((GeneratedMessage.GeneratedExtension)Mysqlx.clientMessageId);
        registry.add((GeneratedMessage.GeneratedExtension)Mysqlx.serverMessageId);
        Descriptors.FileDescriptor.internalUpdateFileDescriptor(MysqlxSql.descriptor, registry);
        Mysqlx.getDescriptor();
        MysqlxDatatypes.getDescriptor();
    }
    
    public static final class StmtExecute extends GeneratedMessageV3 implements StmtExecuteOrBuilder
    {
        private static final long serialVersionUID = 0L;
        private int bitField0_;
        public static final int NAMESPACE_FIELD_NUMBER = 3;
        private volatile Object namespace_;
        public static final int STMT_FIELD_NUMBER = 1;
        private ByteString stmt_;
        public static final int ARGS_FIELD_NUMBER = 2;
        private List<MysqlxDatatypes.Any> args_;
        public static final int COMPACT_METADATA_FIELD_NUMBER = 4;
        private boolean compactMetadata_;
        private byte memoizedIsInitialized;
        private static final StmtExecute DEFAULT_INSTANCE;
        @Deprecated
        public static final Parser<StmtExecute> PARSER;
        
        private StmtExecute(final GeneratedMessageV3.Builder<?> builder) {
            super((GeneratedMessageV3.Builder)builder);
            this.memoizedIsInitialized = -1;
        }
        
        private StmtExecute() {
            this.memoizedIsInitialized = -1;
            this.namespace_ = "sql";
            this.stmt_ = ByteString.EMPTY;
            this.args_ = Collections.emptyList();
            this.compactMetadata_ = false;
        }
        
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }
        
        private StmtExecute(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            this.bitField0_ |= 0x2;
                            this.stmt_ = input.readBytes();
                            continue;
                        }
                        case 18: {
                            if ((mutable_bitField0_ & 0x4) != 0x4) {
                                this.args_ = new ArrayList<MysqlxDatatypes.Any>();
                                mutable_bitField0_ |= 0x4;
                            }
                            this.args_.add((MysqlxDatatypes.Any)input.readMessage((Parser)MysqlxDatatypes.Any.PARSER, extensionRegistry));
                            continue;
                        }
                        case 26: {
                            final ByteString bs = input.readBytes();
                            this.bitField0_ |= 0x1;
                            this.namespace_ = bs;
                            continue;
                        }
                        case 32: {
                            this.bitField0_ |= 0x4;
                            this.compactMetadata_ = input.readBool();
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
                if ((mutable_bitField0_ & 0x4) == 0x4) {
                    this.args_ = Collections.unmodifiableList((List<? extends MysqlxDatatypes.Any>)this.args_);
                }
                this.unknownFields = unknownFields.build();
                this.makeExtensionsImmutable();
            }
        }
        
        public static final Descriptors.Descriptor getDescriptor() {
            return MysqlxSql.internal_static_Mysqlx_Sql_StmtExecute_descriptor;
        }
        
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return MysqlxSql.internal_static_Mysqlx_Sql_StmtExecute_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)StmtExecute.class, (Class)Builder.class);
        }
        
        public boolean hasNamespace() {
            return (this.bitField0_ & 0x1) == 0x1;
        }
        
        public String getNamespace() {
            final Object ref = this.namespace_;
            if (ref instanceof String) {
                return (String)ref;
            }
            final ByteString bs = (ByteString)ref;
            final String s = bs.toStringUtf8();
            if (bs.isValidUtf8()) {
                this.namespace_ = s;
            }
            return s;
        }
        
        public ByteString getNamespaceBytes() {
            final Object ref = this.namespace_;
            if (ref instanceof String) {
                final ByteString b = ByteString.copyFromUtf8((String)ref);
                return (ByteString)(this.namespace_ = b);
            }
            return (ByteString)ref;
        }
        
        public boolean hasStmt() {
            return (this.bitField0_ & 0x2) == 0x2;
        }
        
        public ByteString getStmt() {
            return this.stmt_;
        }
        
        public List<MysqlxDatatypes.Any> getArgsList() {
            return this.args_;
        }
        
        public List<? extends MysqlxDatatypes.AnyOrBuilder> getArgsOrBuilderList() {
            return this.args_;
        }
        
        public int getArgsCount() {
            return this.args_.size();
        }
        
        public MysqlxDatatypes.Any getArgs(final int index) {
            return this.args_.get(index);
        }
        
        public MysqlxDatatypes.AnyOrBuilder getArgsOrBuilder(final int index) {
            return this.args_.get(index);
        }
        
        public boolean hasCompactMetadata() {
            return (this.bitField0_ & 0x4) == 0x4;
        }
        
        public boolean getCompactMetadata() {
            return this.compactMetadata_;
        }
        
        public final boolean isInitialized() {
            final byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (!this.hasStmt()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            for (int i = 0; i < this.getArgsCount(); ++i) {
                if (!this.getArgs(i).isInitialized()) {
                    this.memoizedIsInitialized = 0;
                    return false;
                }
            }
            this.memoizedIsInitialized = 1;
            return true;
        }
        
        public void writeTo(final CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 0x2) == 0x2) {
                output.writeBytes(1, this.stmt_);
            }
            for (int i = 0; i < this.args_.size(); ++i) {
                output.writeMessage(2, (MessageLite)this.args_.get(i));
            }
            if ((this.bitField0_ & 0x1) == 0x1) {
                GeneratedMessageV3.writeString(output, 3, this.namespace_);
            }
            if ((this.bitField0_ & 0x4) == 0x4) {
                output.writeBool(4, this.compactMetadata_);
            }
            this.unknownFields.writeTo(output);
        }
        
        public int getSerializedSize() {
            int size = this.memoizedSize;
            if (size != -1) {
                return size;
            }
            size = 0;
            if ((this.bitField0_ & 0x2) == 0x2) {
                size += CodedOutputStream.computeBytesSize(1, this.stmt_);
            }
            for (int i = 0; i < this.args_.size(); ++i) {
                size += CodedOutputStream.computeMessageSize(2, (MessageLite)this.args_.get(i));
            }
            if ((this.bitField0_ & 0x1) == 0x1) {
                size += GeneratedMessageV3.computeStringSize(3, this.namespace_);
            }
            if ((this.bitField0_ & 0x4) == 0x4) {
                size += CodedOutputStream.computeBoolSize(4, this.compactMetadata_);
            }
            size += this.unknownFields.getSerializedSize();
            return this.memoizedSize = size;
        }
        
        public boolean equals(final Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof StmtExecute)) {
                return super.equals(obj);
            }
            final StmtExecute other = (StmtExecute)obj;
            boolean result = true;
            result = (result && this.hasNamespace() == other.hasNamespace());
            if (this.hasNamespace()) {
                result = (result && this.getNamespace().equals(other.getNamespace()));
            }
            result = (result && this.hasStmt() == other.hasStmt());
            if (this.hasStmt()) {
                result = (result && this.getStmt().equals((Object)other.getStmt()));
            }
            result = (result && this.getArgsList().equals(other.getArgsList()));
            result = (result && this.hasCompactMetadata() == other.hasCompactMetadata());
            if (this.hasCompactMetadata()) {
                result = (result && this.getCompactMetadata() == other.getCompactMetadata());
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
            if (this.hasNamespace()) {
                hash = 37 * hash + 3;
                hash = 53 * hash + this.getNamespace().hashCode();
            }
            if (this.hasStmt()) {
                hash = 37 * hash + 1;
                hash = 53 * hash + this.getStmt().hashCode();
            }
            if (this.getArgsCount() > 0) {
                hash = 37 * hash + 2;
                hash = 53 * hash + this.getArgsList().hashCode();
            }
            if (this.hasCompactMetadata()) {
                hash = 37 * hash + 4;
                hash = 53 * hash + Internal.hashBoolean(this.getCompactMetadata());
            }
            hash = 29 * hash + this.unknownFields.hashCode();
            return this.memoizedHashCode = hash;
        }
        
        public static StmtExecute parseFrom(final ByteBuffer data) throws InvalidProtocolBufferException {
            return (StmtExecute)StmtExecute.PARSER.parseFrom(data);
        }
        
        public static StmtExecute parseFrom(final ByteBuffer data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (StmtExecute)StmtExecute.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static StmtExecute parseFrom(final ByteString data) throws InvalidProtocolBufferException {
            return (StmtExecute)StmtExecute.PARSER.parseFrom(data);
        }
        
        public static StmtExecute parseFrom(final ByteString data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (StmtExecute)StmtExecute.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static StmtExecute parseFrom(final byte[] data) throws InvalidProtocolBufferException {
            return (StmtExecute)StmtExecute.PARSER.parseFrom(data);
        }
        
        public static StmtExecute parseFrom(final byte[] data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (StmtExecute)StmtExecute.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static StmtExecute parseFrom(final InputStream input) throws IOException {
            return (StmtExecute)GeneratedMessageV3.parseWithIOException((Parser)StmtExecute.PARSER, input);
        }
        
        public static StmtExecute parseFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (StmtExecute)GeneratedMessageV3.parseWithIOException((Parser)StmtExecute.PARSER, input, extensionRegistry);
        }
        
        public static StmtExecute parseDelimitedFrom(final InputStream input) throws IOException {
            return (StmtExecute)GeneratedMessageV3.parseDelimitedWithIOException((Parser)StmtExecute.PARSER, input);
        }
        
        public static StmtExecute parseDelimitedFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (StmtExecute)GeneratedMessageV3.parseDelimitedWithIOException((Parser)StmtExecute.PARSER, input, extensionRegistry);
        }
        
        public static StmtExecute parseFrom(final CodedInputStream input) throws IOException {
            return (StmtExecute)GeneratedMessageV3.parseWithIOException((Parser)StmtExecute.PARSER, input);
        }
        
        public static StmtExecute parseFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (StmtExecute)GeneratedMessageV3.parseWithIOException((Parser)StmtExecute.PARSER, input, extensionRegistry);
        }
        
        public Builder newBuilderForType() {
            return newBuilder();
        }
        
        public static Builder newBuilder() {
            return StmtExecute.DEFAULT_INSTANCE.toBuilder();
        }
        
        public static Builder newBuilder(final StmtExecute prototype) {
            return StmtExecute.DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }
        
        public Builder toBuilder() {
            return (this == StmtExecute.DEFAULT_INSTANCE) ? new Builder() : new Builder().mergeFrom(this);
        }
        
        protected Builder newBuilderForType(final GeneratedMessageV3.BuilderParent parent) {
            final Builder builder = new Builder(parent);
            return builder;
        }
        
        public static StmtExecute getDefaultInstance() {
            return StmtExecute.DEFAULT_INSTANCE;
        }
        
        public static Parser<StmtExecute> parser() {
            return StmtExecute.PARSER;
        }
        
        public Parser<StmtExecute> getParserForType() {
            return StmtExecute.PARSER;
        }
        
        public StmtExecute getDefaultInstanceForType() {
            return StmtExecute.DEFAULT_INSTANCE;
        }
        
        static {
            DEFAULT_INSTANCE = new StmtExecute();
            PARSER = (Parser)new AbstractParser<StmtExecute>() {
                public StmtExecute parsePartialFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new StmtExecute(input, extensionRegistry);
                }
            };
        }
        
        public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements StmtExecuteOrBuilder
        {
            private int bitField0_;
            private Object namespace_;
            private ByteString stmt_;
            private List<MysqlxDatatypes.Any> args_;
            private RepeatedFieldBuilderV3<MysqlxDatatypes.Any, MysqlxDatatypes.Any.Builder, MysqlxDatatypes.AnyOrBuilder> argsBuilder_;
            private boolean compactMetadata_;
            
            public static final Descriptors.Descriptor getDescriptor() {
                return MysqlxSql.internal_static_Mysqlx_Sql_StmtExecute_descriptor;
            }
            
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return MysqlxSql.internal_static_Mysqlx_Sql_StmtExecute_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)StmtExecute.class, (Class)Builder.class);
            }
            
            private Builder() {
                this.namespace_ = "sql";
                this.stmt_ = ByteString.EMPTY;
                this.args_ = Collections.emptyList();
                this.maybeForceBuilderInitialization();
            }
            
            private Builder(final GeneratedMessageV3.BuilderParent parent) {
                super(parent);
                this.namespace_ = "sql";
                this.stmt_ = ByteString.EMPTY;
                this.args_ = Collections.emptyList();
                this.maybeForceBuilderInitialization();
            }
            
            private void maybeForceBuilderInitialization() {
                if (StmtExecute.alwaysUseFieldBuilders) {
                    this.getArgsFieldBuilder();
                }
            }
            
            public Builder clear() {
                super.clear();
                this.namespace_ = "sql";
                this.bitField0_ &= 0xFFFFFFFE;
                this.stmt_ = ByteString.EMPTY;
                this.bitField0_ &= 0xFFFFFFFD;
                if (this.argsBuilder_ == null) {
                    this.args_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFFB;
                }
                else {
                    this.argsBuilder_.clear();
                }
                this.compactMetadata_ = false;
                this.bitField0_ &= 0xFFFFFFF7;
                return this;
            }
            
            public Descriptors.Descriptor getDescriptorForType() {
                return MysqlxSql.internal_static_Mysqlx_Sql_StmtExecute_descriptor;
            }
            
            public StmtExecute getDefaultInstanceForType() {
                return StmtExecute.getDefaultInstance();
            }
            
            public StmtExecute build() {
                final StmtExecute result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw newUninitializedMessageException((Message)result);
                }
                return result;
            }
            
            public StmtExecute buildPartial() {
                final StmtExecute result = new StmtExecute((GeneratedMessageV3.Builder)this);
                final int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 0x1) == 0x1) {
                    to_bitField0_ |= 0x1;
                }
                result.namespace_ = this.namespace_;
                if ((from_bitField0_ & 0x2) == 0x2) {
                    to_bitField0_ |= 0x2;
                }
                result.stmt_ = this.stmt_;
                if (this.argsBuilder_ == null) {
                    if ((this.bitField0_ & 0x4) == 0x4) {
                        this.args_ = Collections.unmodifiableList((List<? extends MysqlxDatatypes.Any>)this.args_);
                        this.bitField0_ &= 0xFFFFFFFB;
                    }
                    result.args_ = this.args_;
                }
                else {
                    result.args_ = (List<MysqlxDatatypes.Any>)this.argsBuilder_.build();
                }
                if ((from_bitField0_ & 0x8) == 0x8) {
                    to_bitField0_ |= 0x4;
                }
                result.compactMetadata_ = this.compactMetadata_;
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
                if (other instanceof StmtExecute) {
                    return this.mergeFrom((StmtExecute)other);
                }
                super.mergeFrom(other);
                return this;
            }
            
            public Builder mergeFrom(final StmtExecute other) {
                if (other == StmtExecute.getDefaultInstance()) {
                    return this;
                }
                if (other.hasNamespace()) {
                    this.bitField0_ |= 0x1;
                    this.namespace_ = other.namespace_;
                    this.onChanged();
                }
                if (other.hasStmt()) {
                    this.setStmt(other.getStmt());
                }
                if (this.argsBuilder_ == null) {
                    if (!other.args_.isEmpty()) {
                        if (this.args_.isEmpty()) {
                            this.args_ = other.args_;
                            this.bitField0_ &= 0xFFFFFFFB;
                        }
                        else {
                            this.ensureArgsIsMutable();
                            this.args_.addAll(other.args_);
                        }
                        this.onChanged();
                    }
                }
                else if (!other.args_.isEmpty()) {
                    if (this.argsBuilder_.isEmpty()) {
                        this.argsBuilder_.dispose();
                        this.argsBuilder_ = null;
                        this.args_ = other.args_;
                        this.bitField0_ &= 0xFFFFFFFB;
                        this.argsBuilder_ = (StmtExecute.alwaysUseFieldBuilders ? this.getArgsFieldBuilder() : null);
                    }
                    else {
                        this.argsBuilder_.addAllMessages((Iterable)other.args_);
                    }
                }
                if (other.hasCompactMetadata()) {
                    this.setCompactMetadata(other.getCompactMetadata());
                }
                this.mergeUnknownFields(other.unknownFields);
                this.onChanged();
                return this;
            }
            
            public final boolean isInitialized() {
                if (!this.hasStmt()) {
                    return false;
                }
                for (int i = 0; i < this.getArgsCount(); ++i) {
                    if (!this.getArgs(i).isInitialized()) {
                        return false;
                    }
                }
                return true;
            }
            
            public Builder mergeFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
                StmtExecute parsedMessage = null;
                try {
                    parsedMessage = (StmtExecute)StmtExecute.PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (StmtExecute)e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                }
                finally {
                    if (parsedMessage != null) {
                        this.mergeFrom(parsedMessage);
                    }
                }
                return this;
            }
            
            public boolean hasNamespace() {
                return (this.bitField0_ & 0x1) == 0x1;
            }
            
            public String getNamespace() {
                final Object ref = this.namespace_;
                if (!(ref instanceof String)) {
                    final ByteString bs = (ByteString)ref;
                    final String s = bs.toStringUtf8();
                    if (bs.isValidUtf8()) {
                        this.namespace_ = s;
                    }
                    return s;
                }
                return (String)ref;
            }
            
            public ByteString getNamespaceBytes() {
                final Object ref = this.namespace_;
                if (ref instanceof String) {
                    final ByteString b = ByteString.copyFromUtf8((String)ref);
                    return (ByteString)(this.namespace_ = b);
                }
                return (ByteString)ref;
            }
            
            public Builder setNamespace(final String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 0x1;
                this.namespace_ = value;
                this.onChanged();
                return this;
            }
            
            public Builder clearNamespace() {
                this.bitField0_ &= 0xFFFFFFFE;
                this.namespace_ = StmtExecute.getDefaultInstance().getNamespace();
                this.onChanged();
                return this;
            }
            
            public Builder setNamespaceBytes(final ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 0x1;
                this.namespace_ = value;
                this.onChanged();
                return this;
            }
            
            public boolean hasStmt() {
                return (this.bitField0_ & 0x2) == 0x2;
            }
            
            public ByteString getStmt() {
                return this.stmt_;
            }
            
            public Builder setStmt(final ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 0x2;
                this.stmt_ = value;
                this.onChanged();
                return this;
            }
            
            public Builder clearStmt() {
                this.bitField0_ &= 0xFFFFFFFD;
                this.stmt_ = StmtExecute.getDefaultInstance().getStmt();
                this.onChanged();
                return this;
            }
            
            private void ensureArgsIsMutable() {
                if ((this.bitField0_ & 0x4) != 0x4) {
                    this.args_ = new ArrayList<MysqlxDatatypes.Any>(this.args_);
                    this.bitField0_ |= 0x4;
                }
            }
            
            public List<MysqlxDatatypes.Any> getArgsList() {
                if (this.argsBuilder_ == null) {
                    return Collections.unmodifiableList((List<? extends MysqlxDatatypes.Any>)this.args_);
                }
                return (List<MysqlxDatatypes.Any>)this.argsBuilder_.getMessageList();
            }
            
            public int getArgsCount() {
                if (this.argsBuilder_ == null) {
                    return this.args_.size();
                }
                return this.argsBuilder_.getCount();
            }
            
            public MysqlxDatatypes.Any getArgs(final int index) {
                if (this.argsBuilder_ == null) {
                    return this.args_.get(index);
                }
                return (MysqlxDatatypes.Any)this.argsBuilder_.getMessage(index);
            }
            
            public Builder setArgs(final int index, final MysqlxDatatypes.Any value) {
                if (this.argsBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureArgsIsMutable();
                    this.args_.set(index, value);
                    this.onChanged();
                }
                else {
                    this.argsBuilder_.setMessage(index, (AbstractMessage)value);
                }
                return this;
            }
            
            public Builder setArgs(final int index, final MysqlxDatatypes.Any.Builder builderForValue) {
                if (this.argsBuilder_ == null) {
                    this.ensureArgsIsMutable();
                    this.args_.set(index, builderForValue.build());
                    this.onChanged();
                }
                else {
                    this.argsBuilder_.setMessage(index, (AbstractMessage)builderForValue.build());
                }
                return this;
            }
            
            public Builder addArgs(final MysqlxDatatypes.Any value) {
                if (this.argsBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureArgsIsMutable();
                    this.args_.add(value);
                    this.onChanged();
                }
                else {
                    this.argsBuilder_.addMessage((AbstractMessage)value);
                }
                return this;
            }
            
            public Builder addArgs(final int index, final MysqlxDatatypes.Any value) {
                if (this.argsBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureArgsIsMutable();
                    this.args_.add(index, value);
                    this.onChanged();
                }
                else {
                    this.argsBuilder_.addMessage(index, (AbstractMessage)value);
                }
                return this;
            }
            
            public Builder addArgs(final MysqlxDatatypes.Any.Builder builderForValue) {
                if (this.argsBuilder_ == null) {
                    this.ensureArgsIsMutable();
                    this.args_.add(builderForValue.build());
                    this.onChanged();
                }
                else {
                    this.argsBuilder_.addMessage((AbstractMessage)builderForValue.build());
                }
                return this;
            }
            
            public Builder addArgs(final int index, final MysqlxDatatypes.Any.Builder builderForValue) {
                if (this.argsBuilder_ == null) {
                    this.ensureArgsIsMutable();
                    this.args_.add(index, builderForValue.build());
                    this.onChanged();
                }
                else {
                    this.argsBuilder_.addMessage(index, (AbstractMessage)builderForValue.build());
                }
                return this;
            }
            
            public Builder addAllArgs(final Iterable<? extends MysqlxDatatypes.Any> values) {
                if (this.argsBuilder_ == null) {
                    this.ensureArgsIsMutable();
                    AbstractMessageLite.Builder.addAll((Iterable)values, (List)this.args_);
                    this.onChanged();
                }
                else {
                    this.argsBuilder_.addAllMessages((Iterable)values);
                }
                return this;
            }
            
            public Builder clearArgs() {
                if (this.argsBuilder_ == null) {
                    this.args_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFFB;
                    this.onChanged();
                }
                else {
                    this.argsBuilder_.clear();
                }
                return this;
            }
            
            public Builder removeArgs(final int index) {
                if (this.argsBuilder_ == null) {
                    this.ensureArgsIsMutable();
                    this.args_.remove(index);
                    this.onChanged();
                }
                else {
                    this.argsBuilder_.remove(index);
                }
                return this;
            }
            
            public MysqlxDatatypes.Any.Builder getArgsBuilder(final int index) {
                return (MysqlxDatatypes.Any.Builder)this.getArgsFieldBuilder().getBuilder(index);
            }
            
            public MysqlxDatatypes.AnyOrBuilder getArgsOrBuilder(final int index) {
                if (this.argsBuilder_ == null) {
                    return this.args_.get(index);
                }
                return (MysqlxDatatypes.AnyOrBuilder)this.argsBuilder_.getMessageOrBuilder(index);
            }
            
            public List<? extends MysqlxDatatypes.AnyOrBuilder> getArgsOrBuilderList() {
                if (this.argsBuilder_ != null) {
                    return (List<? extends MysqlxDatatypes.AnyOrBuilder>)this.argsBuilder_.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList((List<? extends MysqlxDatatypes.AnyOrBuilder>)this.args_);
            }
            
            public MysqlxDatatypes.Any.Builder addArgsBuilder() {
                return (MysqlxDatatypes.Any.Builder)this.getArgsFieldBuilder().addBuilder((AbstractMessage)MysqlxDatatypes.Any.getDefaultInstance());
            }
            
            public MysqlxDatatypes.Any.Builder addArgsBuilder(final int index) {
                return (MysqlxDatatypes.Any.Builder)this.getArgsFieldBuilder().addBuilder(index, (AbstractMessage)MysqlxDatatypes.Any.getDefaultInstance());
            }
            
            public List<MysqlxDatatypes.Any.Builder> getArgsBuilderList() {
                return (List<MysqlxDatatypes.Any.Builder>)this.getArgsFieldBuilder().getBuilderList();
            }
            
            private RepeatedFieldBuilderV3<MysqlxDatatypes.Any, MysqlxDatatypes.Any.Builder, MysqlxDatatypes.AnyOrBuilder> getArgsFieldBuilder() {
                if (this.argsBuilder_ == null) {
                    this.argsBuilder_ = (RepeatedFieldBuilderV3<MysqlxDatatypes.Any, MysqlxDatatypes.Any.Builder, MysqlxDatatypes.AnyOrBuilder>)new RepeatedFieldBuilderV3((List)this.args_, (this.bitField0_ & 0x4) == 0x4, (AbstractMessage.BuilderParent)this.getParentForChildren(), this.isClean());
                    this.args_ = null;
                }
                return this.argsBuilder_;
            }
            
            public boolean hasCompactMetadata() {
                return (this.bitField0_ & 0x8) == 0x8;
            }
            
            public boolean getCompactMetadata() {
                return this.compactMetadata_;
            }
            
            public Builder setCompactMetadata(final boolean value) {
                this.bitField0_ |= 0x8;
                this.compactMetadata_ = value;
                this.onChanged();
                return this;
            }
            
            public Builder clearCompactMetadata() {
                this.bitField0_ &= 0xFFFFFFF7;
                this.compactMetadata_ = false;
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
    
    public static final class StmtExecuteOk extends GeneratedMessageV3 implements StmtExecuteOkOrBuilder
    {
        private static final long serialVersionUID = 0L;
        private byte memoizedIsInitialized;
        private static final StmtExecuteOk DEFAULT_INSTANCE;
        @Deprecated
        public static final Parser<StmtExecuteOk> PARSER;
        
        private StmtExecuteOk(final GeneratedMessageV3.Builder<?> builder) {
            super((GeneratedMessageV3.Builder)builder);
            this.memoizedIsInitialized = -1;
        }
        
        private StmtExecuteOk() {
            this.memoizedIsInitialized = -1;
        }
        
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }
        
        private StmtExecuteOk(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
            return MysqlxSql.internal_static_Mysqlx_Sql_StmtExecuteOk_descriptor;
        }
        
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return MysqlxSql.internal_static_Mysqlx_Sql_StmtExecuteOk_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)StmtExecuteOk.class, (Class)Builder.class);
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
            if (!(obj instanceof StmtExecuteOk)) {
                return super.equals(obj);
            }
            final StmtExecuteOk other = (StmtExecuteOk)obj;
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
        
        public static StmtExecuteOk parseFrom(final ByteBuffer data) throws InvalidProtocolBufferException {
            return (StmtExecuteOk)StmtExecuteOk.PARSER.parseFrom(data);
        }
        
        public static StmtExecuteOk parseFrom(final ByteBuffer data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (StmtExecuteOk)StmtExecuteOk.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static StmtExecuteOk parseFrom(final ByteString data) throws InvalidProtocolBufferException {
            return (StmtExecuteOk)StmtExecuteOk.PARSER.parseFrom(data);
        }
        
        public static StmtExecuteOk parseFrom(final ByteString data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (StmtExecuteOk)StmtExecuteOk.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static StmtExecuteOk parseFrom(final byte[] data) throws InvalidProtocolBufferException {
            return (StmtExecuteOk)StmtExecuteOk.PARSER.parseFrom(data);
        }
        
        public static StmtExecuteOk parseFrom(final byte[] data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (StmtExecuteOk)StmtExecuteOk.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static StmtExecuteOk parseFrom(final InputStream input) throws IOException {
            return (StmtExecuteOk)GeneratedMessageV3.parseWithIOException((Parser)StmtExecuteOk.PARSER, input);
        }
        
        public static StmtExecuteOk parseFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (StmtExecuteOk)GeneratedMessageV3.parseWithIOException((Parser)StmtExecuteOk.PARSER, input, extensionRegistry);
        }
        
        public static StmtExecuteOk parseDelimitedFrom(final InputStream input) throws IOException {
            return (StmtExecuteOk)GeneratedMessageV3.parseDelimitedWithIOException((Parser)StmtExecuteOk.PARSER, input);
        }
        
        public static StmtExecuteOk parseDelimitedFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (StmtExecuteOk)GeneratedMessageV3.parseDelimitedWithIOException((Parser)StmtExecuteOk.PARSER, input, extensionRegistry);
        }
        
        public static StmtExecuteOk parseFrom(final CodedInputStream input) throws IOException {
            return (StmtExecuteOk)GeneratedMessageV3.parseWithIOException((Parser)StmtExecuteOk.PARSER, input);
        }
        
        public static StmtExecuteOk parseFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (StmtExecuteOk)GeneratedMessageV3.parseWithIOException((Parser)StmtExecuteOk.PARSER, input, extensionRegistry);
        }
        
        public Builder newBuilderForType() {
            return newBuilder();
        }
        
        public static Builder newBuilder() {
            return StmtExecuteOk.DEFAULT_INSTANCE.toBuilder();
        }
        
        public static Builder newBuilder(final StmtExecuteOk prototype) {
            return StmtExecuteOk.DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }
        
        public Builder toBuilder() {
            return (this == StmtExecuteOk.DEFAULT_INSTANCE) ? new Builder() : new Builder().mergeFrom(this);
        }
        
        protected Builder newBuilderForType(final GeneratedMessageV3.BuilderParent parent) {
            final Builder builder = new Builder(parent);
            return builder;
        }
        
        public static StmtExecuteOk getDefaultInstance() {
            return StmtExecuteOk.DEFAULT_INSTANCE;
        }
        
        public static Parser<StmtExecuteOk> parser() {
            return StmtExecuteOk.PARSER;
        }
        
        public Parser<StmtExecuteOk> getParserForType() {
            return StmtExecuteOk.PARSER;
        }
        
        public StmtExecuteOk getDefaultInstanceForType() {
            return StmtExecuteOk.DEFAULT_INSTANCE;
        }
        
        static {
            DEFAULT_INSTANCE = new StmtExecuteOk();
            PARSER = (Parser)new AbstractParser<StmtExecuteOk>() {
                public StmtExecuteOk parsePartialFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new StmtExecuteOk(input, extensionRegistry);
                }
            };
        }
        
        public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements StmtExecuteOkOrBuilder
        {
            public static final Descriptors.Descriptor getDescriptor() {
                return MysqlxSql.internal_static_Mysqlx_Sql_StmtExecuteOk_descriptor;
            }
            
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return MysqlxSql.internal_static_Mysqlx_Sql_StmtExecuteOk_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)StmtExecuteOk.class, (Class)Builder.class);
            }
            
            private Builder() {
                this.maybeForceBuilderInitialization();
            }
            
            private Builder(final GeneratedMessageV3.BuilderParent parent) {
                super(parent);
                this.maybeForceBuilderInitialization();
            }
            
            private void maybeForceBuilderInitialization() {
                if (StmtExecuteOk.alwaysUseFieldBuilders) {}
            }
            
            public Builder clear() {
                super.clear();
                return this;
            }
            
            public Descriptors.Descriptor getDescriptorForType() {
                return MysqlxSql.internal_static_Mysqlx_Sql_StmtExecuteOk_descriptor;
            }
            
            public StmtExecuteOk getDefaultInstanceForType() {
                return StmtExecuteOk.getDefaultInstance();
            }
            
            public StmtExecuteOk build() {
                final StmtExecuteOk result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw newUninitializedMessageException((Message)result);
                }
                return result;
            }
            
            public StmtExecuteOk buildPartial() {
                final StmtExecuteOk result = new StmtExecuteOk((GeneratedMessageV3.Builder)this);
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
                if (other instanceof StmtExecuteOk) {
                    return this.mergeFrom((StmtExecuteOk)other);
                }
                super.mergeFrom(other);
                return this;
            }
            
            public Builder mergeFrom(final StmtExecuteOk other) {
                if (other == StmtExecuteOk.getDefaultInstance()) {
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
                StmtExecuteOk parsedMessage = null;
                try {
                    parsedMessage = (StmtExecuteOk)StmtExecuteOk.PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (StmtExecuteOk)e.getUnfinishedMessage();
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
    
    public interface StmtExecuteOkOrBuilder extends MessageOrBuilder
    {
    }
    
    public interface StmtExecuteOrBuilder extends MessageOrBuilder
    {
        boolean hasNamespace();
        
        String getNamespace();
        
        ByteString getNamespaceBytes();
        
        boolean hasStmt();
        
        ByteString getStmt();
        
        List<MysqlxDatatypes.Any> getArgsList();
        
        MysqlxDatatypes.Any getArgs(final int p0);
        
        int getArgsCount();
        
        List<? extends MysqlxDatatypes.AnyOrBuilder> getArgsOrBuilderList();
        
        MysqlxDatatypes.AnyOrBuilder getArgsOrBuilder(final int p0);
        
        boolean hasCompactMetadata();
        
        boolean getCompactMetadata();
    }
}
