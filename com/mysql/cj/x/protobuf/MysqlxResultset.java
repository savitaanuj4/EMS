
package com.mysql.cj.x.protobuf;

import com.google.protobuf.MessageOrBuilder;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.google.protobuf.AbstractMessageLite;
import com.google.protobuf.AbstractMessage;
import com.google.protobuf.AbstractParser;
import com.google.protobuf.Message;
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
import com.google.protobuf.Internal;
import com.google.protobuf.ProtocolMessageEnum;
import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.Descriptors;

public final class MysqlxResultset
{
    private static final Descriptors.Descriptor internal_static_Mysqlx_Resultset_FetchDoneMoreOutParams_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_Mysqlx_Resultset_FetchDoneMoreOutParams_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_Mysqlx_Resultset_FetchDoneMoreResultsets_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_Mysqlx_Resultset_FetchDoneMoreResultsets_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_Mysqlx_Resultset_FetchDone_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_Mysqlx_Resultset_FetchDone_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_Mysqlx_Resultset_ColumnMetaData_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_Mysqlx_Resultset_ColumnMetaData_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_Mysqlx_Resultset_Row_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_Mysqlx_Resultset_Row_fieldAccessorTable;
    private static Descriptors.FileDescriptor descriptor;
    
    private MysqlxResultset() {
    }
    
    public static void registerAllExtensions(final ExtensionRegistryLite registry) {
    }
    
    public static void registerAllExtensions(final ExtensionRegistry registry) {
        registerAllExtensions((ExtensionRegistryLite)registry);
    }
    
    public static Descriptors.FileDescriptor getDescriptor() {
        return MysqlxResultset.descriptor;
    }
    
    static {
        final String[] descriptorData = { "\n\u0016mysqlx_resultset.proto\u0012\u0010Mysqlx.Resultset\u001a\fmysqlx.proto\"\u001e\n\u0016FetchDoneMoreOutParams:\u0004\u0090\u00ea0\u0012\"\u001f\n\u0017FetchDoneMoreResultsets:\u0004\u0090\u00ea0\u0010\"\u0011\n\tFetchDone:\u0004\u0090\u00ea0\u000e\"¥\u0003\n\u000eColumnMetaData\u00128\n\u0004type\u0018\u0001 \u0002(\u000e2*.Mysqlx.Resultset.ColumnMetaData.FieldType\u0012\f\n\u0004name\u0018\u0002 \u0001(\f\u0012\u0015\n\roriginal_name\u0018\u0003 \u0001(\f\u0012\r\n\u0005table\u0018\u0004 \u0001(\f\u0012\u0016\n\u000eoriginal_table\u0018\u0005 \u0001(\f\u0012\u000e\n\u0006schema\u0018\u0006 \u0001(\f\u0012\u000f\n\u0007catalog\u0018\u0007 \u0001(\f\u0012\u0011\n\tcollation\u0018\b \u0001(\u0004\u0012\u0019\n\u0011fractional_digits\u0018\t \u0001(\r\u0012\u000e\n\u0006length\u0018\n \u0001(\r\u0012\r\n\u0005flags\u0018\u000b \u0001(\r\u0012\u0014\n\fcontent_type\u0018\f \u0001(\r\"\u0082\u0001\n\tFieldType\u0012\b\n\u0004SINT\u0010\u0001\u0012\b\n\u0004UINT\u0010\u0002\u0012\n\n\u0006DOUBLE\u0010\u0005\u0012\t\n\u0005FLOAT\u0010\u0006\u0012\t\n\u0005BYTES\u0010\u0007\u0012\b\n\u0004TIME\u0010\n\u0012\f\n\bDATETIME\u0010\f\u0012\u0007\n\u0003SET\u0010\u000f\u0012\b\n\u0004ENUM\u0010\u0010\u0012\u0007\n\u0003BIT\u0010\u0011\u0012\u000b\n\u0007DECIMAL\u0010\u0012:\u0004\u0090\u00ea0\f\"\u001a\n\u0003Row\u0012\r\n\u0005field\u0018\u0001 \u0003(\f:\u0004\u0090\u00ea0\r*4\n\u0011ContentType_BYTES\u0012\f\n\bGEOMETRY\u0010\u0001\u0012\b\n\u0004JSON\u0010\u0002\u0012\u0007\n\u0003XML\u0010\u0003*.\n\u0014ContentType_DATETIME\u0012\b\n\u0004DATE\u0010\u0001\u0012\f\n\bDATETIME\u0010\u0002B\u0019\n\u0017com.mysql.cj.x.protobuf" };
        final Descriptors.FileDescriptor.InternalDescriptorAssigner assigner = (Descriptors.FileDescriptor.InternalDescriptorAssigner)new Descriptors.FileDescriptor.InternalDescriptorAssigner() {
            public ExtensionRegistry assignDescriptors(final Descriptors.FileDescriptor root) {
                MysqlxResultset.descriptor = root;
                return null;
            }
        };
        Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new Descriptors.FileDescriptor[] { Mysqlx.getDescriptor() }, assigner);
        internal_static_Mysqlx_Resultset_FetchDoneMoreOutParams_descriptor = getDescriptor().getMessageTypes().get(0);
        internal_static_Mysqlx_Resultset_FetchDoneMoreOutParams_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(MysqlxResultset.internal_static_Mysqlx_Resultset_FetchDoneMoreOutParams_descriptor, new String[0]);
        internal_static_Mysqlx_Resultset_FetchDoneMoreResultsets_descriptor = getDescriptor().getMessageTypes().get(1);
        internal_static_Mysqlx_Resultset_FetchDoneMoreResultsets_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(MysqlxResultset.internal_static_Mysqlx_Resultset_FetchDoneMoreResultsets_descriptor, new String[0]);
        internal_static_Mysqlx_Resultset_FetchDone_descriptor = getDescriptor().getMessageTypes().get(2);
        internal_static_Mysqlx_Resultset_FetchDone_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(MysqlxResultset.internal_static_Mysqlx_Resultset_FetchDone_descriptor, new String[0]);
        internal_static_Mysqlx_Resultset_ColumnMetaData_descriptor = getDescriptor().getMessageTypes().get(3);
        internal_static_Mysqlx_Resultset_ColumnMetaData_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(MysqlxResultset.internal_static_Mysqlx_Resultset_ColumnMetaData_descriptor, new String[] { "Type", "Name", "OriginalName", "Table", "OriginalTable", "Schema", "Catalog", "Collation", "FractionalDigits", "Length", "Flags", "ContentType" });
        internal_static_Mysqlx_Resultset_Row_descriptor = getDescriptor().getMessageTypes().get(4);
        internal_static_Mysqlx_Resultset_Row_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(MysqlxResultset.internal_static_Mysqlx_Resultset_Row_descriptor, new String[] { "Field" });
        final ExtensionRegistry registry = ExtensionRegistry.newInstance();
        registry.add((GeneratedMessage.GeneratedExtension)Mysqlx.serverMessageId);
        Descriptors.FileDescriptor.internalUpdateFileDescriptor(MysqlxResultset.descriptor, registry);
        Mysqlx.getDescriptor();
    }
    
    public enum ContentType_BYTES implements ProtocolMessageEnum
    {
        GEOMETRY(1), 
        JSON(2), 
        XML(3);
        
        public static final int GEOMETRY_VALUE = 1;
        public static final int JSON_VALUE = 2;
        public static final int XML_VALUE = 3;
        private static final Internal.EnumLiteMap<ContentType_BYTES> internalValueMap;
        private static final ContentType_BYTES[] VALUES;
        private final int value;
        
        public final int getNumber() {
            return this.value;
        }
        
        @Deprecated
        public static ContentType_BYTES valueOf(final int value) {
            return forNumber(value);
        }
        
        public static ContentType_BYTES forNumber(final int value) {
            switch (value) {
                case 1: {
                    return ContentType_BYTES.GEOMETRY;
                }
                case 2: {
                    return ContentType_BYTES.JSON;
                }
                case 3: {
                    return ContentType_BYTES.XML;
                }
                default: {
                    return null;
                }
            }
        }
        
        public static Internal.EnumLiteMap<ContentType_BYTES> internalGetValueMap() {
            return ContentType_BYTES.internalValueMap;
        }
        
        public final Descriptors.EnumValueDescriptor getValueDescriptor() {
            return getDescriptor().getValues().get(this.ordinal());
        }
        
        public final Descriptors.EnumDescriptor getDescriptorForType() {
            return getDescriptor();
        }
        
        public static final Descriptors.EnumDescriptor getDescriptor() {
            return MysqlxResultset.getDescriptor().getEnumTypes().get(0);
        }
        
        public static ContentType_BYTES valueOf(final Descriptors.EnumValueDescriptor desc) {
            if (desc.getType() != getDescriptor()) {
                throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
            }
            return ContentType_BYTES.VALUES[desc.getIndex()];
        }
        
        private ContentType_BYTES(final int value) {
            this.value = value;
        }
        
        static {
            internalValueMap = (Internal.EnumLiteMap)new Internal.EnumLiteMap<ContentType_BYTES>() {
                public ContentType_BYTES findValueByNumber(final int number) {
                    return ContentType_BYTES.forNumber(number);
                }
            };
            VALUES = values();
        }
    }
    
    public enum ContentType_DATETIME implements ProtocolMessageEnum
    {
        DATE(1), 
        DATETIME(2);
        
        public static final int DATE_VALUE = 1;
        public static final int DATETIME_VALUE = 2;
        private static final Internal.EnumLiteMap<ContentType_DATETIME> internalValueMap;
        private static final ContentType_DATETIME[] VALUES;
        private final int value;
        
        public final int getNumber() {
            return this.value;
        }
        
        @Deprecated
        public static ContentType_DATETIME valueOf(final int value) {
            return forNumber(value);
        }
        
        public static ContentType_DATETIME forNumber(final int value) {
            switch (value) {
                case 1: {
                    return ContentType_DATETIME.DATE;
                }
                case 2: {
                    return ContentType_DATETIME.DATETIME;
                }
                default: {
                    return null;
                }
            }
        }
        
        public static Internal.EnumLiteMap<ContentType_DATETIME> internalGetValueMap() {
            return ContentType_DATETIME.internalValueMap;
        }
        
        public final Descriptors.EnumValueDescriptor getValueDescriptor() {
            return getDescriptor().getValues().get(this.ordinal());
        }
        
        public final Descriptors.EnumDescriptor getDescriptorForType() {
            return getDescriptor();
        }
        
        public static final Descriptors.EnumDescriptor getDescriptor() {
            return MysqlxResultset.getDescriptor().getEnumTypes().get(1);
        }
        
        public static ContentType_DATETIME valueOf(final Descriptors.EnumValueDescriptor desc) {
            if (desc.getType() != getDescriptor()) {
                throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
            }
            return ContentType_DATETIME.VALUES[desc.getIndex()];
        }
        
        private ContentType_DATETIME(final int value) {
            this.value = value;
        }
        
        static {
            internalValueMap = (Internal.EnumLiteMap)new Internal.EnumLiteMap<ContentType_DATETIME>() {
                public ContentType_DATETIME findValueByNumber(final int number) {
                    return ContentType_DATETIME.forNumber(number);
                }
            };
            VALUES = values();
        }
    }
    
    public static final class FetchDoneMoreOutParams extends GeneratedMessageV3 implements FetchDoneMoreOutParamsOrBuilder
    {
        private static final long serialVersionUID = 0L;
        private byte memoizedIsInitialized;
        private static final FetchDoneMoreOutParams DEFAULT_INSTANCE;
        @Deprecated
        public static final Parser<FetchDoneMoreOutParams> PARSER;
        
        private FetchDoneMoreOutParams(final GeneratedMessageV3.Builder<?> builder) {
            super((GeneratedMessageV3.Builder)builder);
            this.memoizedIsInitialized = -1;
        }
        
        private FetchDoneMoreOutParams() {
            this.memoizedIsInitialized = -1;
        }
        
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }
        
        private FetchDoneMoreOutParams(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
            return MysqlxResultset.internal_static_Mysqlx_Resultset_FetchDoneMoreOutParams_descriptor;
        }
        
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return MysqlxResultset.internal_static_Mysqlx_Resultset_FetchDoneMoreOutParams_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)FetchDoneMoreOutParams.class, (Class)Builder.class);
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
            if (!(obj instanceof FetchDoneMoreOutParams)) {
                return super.equals(obj);
            }
            final FetchDoneMoreOutParams other = (FetchDoneMoreOutParams)obj;
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
        
        public static FetchDoneMoreOutParams parseFrom(final ByteBuffer data) throws InvalidProtocolBufferException {
            return (FetchDoneMoreOutParams)FetchDoneMoreOutParams.PARSER.parseFrom(data);
        }
        
        public static FetchDoneMoreOutParams parseFrom(final ByteBuffer data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (FetchDoneMoreOutParams)FetchDoneMoreOutParams.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static FetchDoneMoreOutParams parseFrom(final ByteString data) throws InvalidProtocolBufferException {
            return (FetchDoneMoreOutParams)FetchDoneMoreOutParams.PARSER.parseFrom(data);
        }
        
        public static FetchDoneMoreOutParams parseFrom(final ByteString data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (FetchDoneMoreOutParams)FetchDoneMoreOutParams.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static FetchDoneMoreOutParams parseFrom(final byte[] data) throws InvalidProtocolBufferException {
            return (FetchDoneMoreOutParams)FetchDoneMoreOutParams.PARSER.parseFrom(data);
        }
        
        public static FetchDoneMoreOutParams parseFrom(final byte[] data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (FetchDoneMoreOutParams)FetchDoneMoreOutParams.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static FetchDoneMoreOutParams parseFrom(final InputStream input) throws IOException {
            return (FetchDoneMoreOutParams)GeneratedMessageV3.parseWithIOException((Parser)FetchDoneMoreOutParams.PARSER, input);
        }
        
        public static FetchDoneMoreOutParams parseFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (FetchDoneMoreOutParams)GeneratedMessageV3.parseWithIOException((Parser)FetchDoneMoreOutParams.PARSER, input, extensionRegistry);
        }
        
        public static FetchDoneMoreOutParams parseDelimitedFrom(final InputStream input) throws IOException {
            return (FetchDoneMoreOutParams)GeneratedMessageV3.parseDelimitedWithIOException((Parser)FetchDoneMoreOutParams.PARSER, input);
        }
        
        public static FetchDoneMoreOutParams parseDelimitedFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (FetchDoneMoreOutParams)GeneratedMessageV3.parseDelimitedWithIOException((Parser)FetchDoneMoreOutParams.PARSER, input, extensionRegistry);
        }
        
        public static FetchDoneMoreOutParams parseFrom(final CodedInputStream input) throws IOException {
            return (FetchDoneMoreOutParams)GeneratedMessageV3.parseWithIOException((Parser)FetchDoneMoreOutParams.PARSER, input);
        }
        
        public static FetchDoneMoreOutParams parseFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (FetchDoneMoreOutParams)GeneratedMessageV3.parseWithIOException((Parser)FetchDoneMoreOutParams.PARSER, input, extensionRegistry);
        }
        
        public Builder newBuilderForType() {
            return newBuilder();
        }
        
        public static Builder newBuilder() {
            return FetchDoneMoreOutParams.DEFAULT_INSTANCE.toBuilder();
        }
        
        public static Builder newBuilder(final FetchDoneMoreOutParams prototype) {
            return FetchDoneMoreOutParams.DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }
        
        public Builder toBuilder() {
            return (this == FetchDoneMoreOutParams.DEFAULT_INSTANCE) ? new Builder() : new Builder().mergeFrom(this);
        }
        
        protected Builder newBuilderForType(final GeneratedMessageV3.BuilderParent parent) {
            final Builder builder = new Builder(parent);
            return builder;
        }
        
        public static FetchDoneMoreOutParams getDefaultInstance() {
            return FetchDoneMoreOutParams.DEFAULT_INSTANCE;
        }
        
        public static Parser<FetchDoneMoreOutParams> parser() {
            return FetchDoneMoreOutParams.PARSER;
        }
        
        public Parser<FetchDoneMoreOutParams> getParserForType() {
            return FetchDoneMoreOutParams.PARSER;
        }
        
        public FetchDoneMoreOutParams getDefaultInstanceForType() {
            return FetchDoneMoreOutParams.DEFAULT_INSTANCE;
        }
        
        static {
            DEFAULT_INSTANCE = new FetchDoneMoreOutParams();
            PARSER = (Parser)new AbstractParser<FetchDoneMoreOutParams>() {
                public FetchDoneMoreOutParams parsePartialFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new FetchDoneMoreOutParams(input, extensionRegistry);
                }
            };
        }
        
        public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements FetchDoneMoreOutParamsOrBuilder
        {
            public static final Descriptors.Descriptor getDescriptor() {
                return MysqlxResultset.internal_static_Mysqlx_Resultset_FetchDoneMoreOutParams_descriptor;
            }
            
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return MysqlxResultset.internal_static_Mysqlx_Resultset_FetchDoneMoreOutParams_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)FetchDoneMoreOutParams.class, (Class)Builder.class);
            }
            
            private Builder() {
                this.maybeForceBuilderInitialization();
            }
            
            private Builder(final GeneratedMessageV3.BuilderParent parent) {
                super(parent);
                this.maybeForceBuilderInitialization();
            }
            
            private void maybeForceBuilderInitialization() {
                if (FetchDoneMoreOutParams.alwaysUseFieldBuilders) {}
            }
            
            public Builder clear() {
                super.clear();
                return this;
            }
            
            public Descriptors.Descriptor getDescriptorForType() {
                return MysqlxResultset.internal_static_Mysqlx_Resultset_FetchDoneMoreOutParams_descriptor;
            }
            
            public FetchDoneMoreOutParams getDefaultInstanceForType() {
                return FetchDoneMoreOutParams.getDefaultInstance();
            }
            
            public FetchDoneMoreOutParams build() {
                final FetchDoneMoreOutParams result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw newUninitializedMessageException((Message)result);
                }
                return result;
            }
            
            public FetchDoneMoreOutParams buildPartial() {
                final FetchDoneMoreOutParams result = new FetchDoneMoreOutParams((GeneratedMessageV3.Builder)this);
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
                if (other instanceof FetchDoneMoreOutParams) {
                    return this.mergeFrom((FetchDoneMoreOutParams)other);
                }
                super.mergeFrom(other);
                return this;
            }
            
            public Builder mergeFrom(final FetchDoneMoreOutParams other) {
                if (other == FetchDoneMoreOutParams.getDefaultInstance()) {
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
                FetchDoneMoreOutParams parsedMessage = null;
                try {
                    parsedMessage = (FetchDoneMoreOutParams)FetchDoneMoreOutParams.PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (FetchDoneMoreOutParams)e.getUnfinishedMessage();
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
    
    public static final class FetchDoneMoreResultsets extends GeneratedMessageV3 implements FetchDoneMoreResultsetsOrBuilder
    {
        private static final long serialVersionUID = 0L;
        private byte memoizedIsInitialized;
        private static final FetchDoneMoreResultsets DEFAULT_INSTANCE;
        @Deprecated
        public static final Parser<FetchDoneMoreResultsets> PARSER;
        
        private FetchDoneMoreResultsets(final GeneratedMessageV3.Builder<?> builder) {
            super((GeneratedMessageV3.Builder)builder);
            this.memoizedIsInitialized = -1;
        }
        
        private FetchDoneMoreResultsets() {
            this.memoizedIsInitialized = -1;
        }
        
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }
        
        private FetchDoneMoreResultsets(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
            return MysqlxResultset.internal_static_Mysqlx_Resultset_FetchDoneMoreResultsets_descriptor;
        }
        
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return MysqlxResultset.internal_static_Mysqlx_Resultset_FetchDoneMoreResultsets_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)FetchDoneMoreResultsets.class, (Class)Builder.class);
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
            if (!(obj instanceof FetchDoneMoreResultsets)) {
                return super.equals(obj);
            }
            final FetchDoneMoreResultsets other = (FetchDoneMoreResultsets)obj;
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
        
        public static FetchDoneMoreResultsets parseFrom(final ByteBuffer data) throws InvalidProtocolBufferException {
            return (FetchDoneMoreResultsets)FetchDoneMoreResultsets.PARSER.parseFrom(data);
        }
        
        public static FetchDoneMoreResultsets parseFrom(final ByteBuffer data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (FetchDoneMoreResultsets)FetchDoneMoreResultsets.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static FetchDoneMoreResultsets parseFrom(final ByteString data) throws InvalidProtocolBufferException {
            return (FetchDoneMoreResultsets)FetchDoneMoreResultsets.PARSER.parseFrom(data);
        }
        
        public static FetchDoneMoreResultsets parseFrom(final ByteString data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (FetchDoneMoreResultsets)FetchDoneMoreResultsets.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static FetchDoneMoreResultsets parseFrom(final byte[] data) throws InvalidProtocolBufferException {
            return (FetchDoneMoreResultsets)FetchDoneMoreResultsets.PARSER.parseFrom(data);
        }
        
        public static FetchDoneMoreResultsets parseFrom(final byte[] data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (FetchDoneMoreResultsets)FetchDoneMoreResultsets.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static FetchDoneMoreResultsets parseFrom(final InputStream input) throws IOException {
            return (FetchDoneMoreResultsets)GeneratedMessageV3.parseWithIOException((Parser)FetchDoneMoreResultsets.PARSER, input);
        }
        
        public static FetchDoneMoreResultsets parseFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (FetchDoneMoreResultsets)GeneratedMessageV3.parseWithIOException((Parser)FetchDoneMoreResultsets.PARSER, input, extensionRegistry);
        }
        
        public static FetchDoneMoreResultsets parseDelimitedFrom(final InputStream input) throws IOException {
            return (FetchDoneMoreResultsets)GeneratedMessageV3.parseDelimitedWithIOException((Parser)FetchDoneMoreResultsets.PARSER, input);
        }
        
        public static FetchDoneMoreResultsets parseDelimitedFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (FetchDoneMoreResultsets)GeneratedMessageV3.parseDelimitedWithIOException((Parser)FetchDoneMoreResultsets.PARSER, input, extensionRegistry);
        }
        
        public static FetchDoneMoreResultsets parseFrom(final CodedInputStream input) throws IOException {
            return (FetchDoneMoreResultsets)GeneratedMessageV3.parseWithIOException((Parser)FetchDoneMoreResultsets.PARSER, input);
        }
        
        public static FetchDoneMoreResultsets parseFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (FetchDoneMoreResultsets)GeneratedMessageV3.parseWithIOException((Parser)FetchDoneMoreResultsets.PARSER, input, extensionRegistry);
        }
        
        public Builder newBuilderForType() {
            return newBuilder();
        }
        
        public static Builder newBuilder() {
            return FetchDoneMoreResultsets.DEFAULT_INSTANCE.toBuilder();
        }
        
        public static Builder newBuilder(final FetchDoneMoreResultsets prototype) {
            return FetchDoneMoreResultsets.DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }
        
        public Builder toBuilder() {
            return (this == FetchDoneMoreResultsets.DEFAULT_INSTANCE) ? new Builder() : new Builder().mergeFrom(this);
        }
        
        protected Builder newBuilderForType(final GeneratedMessageV3.BuilderParent parent) {
            final Builder builder = new Builder(parent);
            return builder;
        }
        
        public static FetchDoneMoreResultsets getDefaultInstance() {
            return FetchDoneMoreResultsets.DEFAULT_INSTANCE;
        }
        
        public static Parser<FetchDoneMoreResultsets> parser() {
            return FetchDoneMoreResultsets.PARSER;
        }
        
        public Parser<FetchDoneMoreResultsets> getParserForType() {
            return FetchDoneMoreResultsets.PARSER;
        }
        
        public FetchDoneMoreResultsets getDefaultInstanceForType() {
            return FetchDoneMoreResultsets.DEFAULT_INSTANCE;
        }
        
        static {
            DEFAULT_INSTANCE = new FetchDoneMoreResultsets();
            PARSER = (Parser)new AbstractParser<FetchDoneMoreResultsets>() {
                public FetchDoneMoreResultsets parsePartialFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new FetchDoneMoreResultsets(input, extensionRegistry);
                }
            };
        }
        
        public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements FetchDoneMoreResultsetsOrBuilder
        {
            public static final Descriptors.Descriptor getDescriptor() {
                return MysqlxResultset.internal_static_Mysqlx_Resultset_FetchDoneMoreResultsets_descriptor;
            }
            
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return MysqlxResultset.internal_static_Mysqlx_Resultset_FetchDoneMoreResultsets_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)FetchDoneMoreResultsets.class, (Class)Builder.class);
            }
            
            private Builder() {
                this.maybeForceBuilderInitialization();
            }
            
            private Builder(final GeneratedMessageV3.BuilderParent parent) {
                super(parent);
                this.maybeForceBuilderInitialization();
            }
            
            private void maybeForceBuilderInitialization() {
                if (FetchDoneMoreResultsets.alwaysUseFieldBuilders) {}
            }
            
            public Builder clear() {
                super.clear();
                return this;
            }
            
            public Descriptors.Descriptor getDescriptorForType() {
                return MysqlxResultset.internal_static_Mysqlx_Resultset_FetchDoneMoreResultsets_descriptor;
            }
            
            public FetchDoneMoreResultsets getDefaultInstanceForType() {
                return FetchDoneMoreResultsets.getDefaultInstance();
            }
            
            public FetchDoneMoreResultsets build() {
                final FetchDoneMoreResultsets result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw newUninitializedMessageException((Message)result);
                }
                return result;
            }
            
            public FetchDoneMoreResultsets buildPartial() {
                final FetchDoneMoreResultsets result = new FetchDoneMoreResultsets((GeneratedMessageV3.Builder)this);
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
                if (other instanceof FetchDoneMoreResultsets) {
                    return this.mergeFrom((FetchDoneMoreResultsets)other);
                }
                super.mergeFrom(other);
                return this;
            }
            
            public Builder mergeFrom(final FetchDoneMoreResultsets other) {
                if (other == FetchDoneMoreResultsets.getDefaultInstance()) {
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
                FetchDoneMoreResultsets parsedMessage = null;
                try {
                    parsedMessage = (FetchDoneMoreResultsets)FetchDoneMoreResultsets.PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (FetchDoneMoreResultsets)e.getUnfinishedMessage();
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
    
    public static final class FetchDone extends GeneratedMessageV3 implements FetchDoneOrBuilder
    {
        private static final long serialVersionUID = 0L;
        private byte memoizedIsInitialized;
        private static final FetchDone DEFAULT_INSTANCE;
        @Deprecated
        public static final Parser<FetchDone> PARSER;
        
        private FetchDone(final GeneratedMessageV3.Builder<?> builder) {
            super((GeneratedMessageV3.Builder)builder);
            this.memoizedIsInitialized = -1;
        }
        
        private FetchDone() {
            this.memoizedIsInitialized = -1;
        }
        
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }
        
        private FetchDone(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
            return MysqlxResultset.internal_static_Mysqlx_Resultset_FetchDone_descriptor;
        }
        
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return MysqlxResultset.internal_static_Mysqlx_Resultset_FetchDone_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)FetchDone.class, (Class)Builder.class);
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
            if (!(obj instanceof FetchDone)) {
                return super.equals(obj);
            }
            final FetchDone other = (FetchDone)obj;
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
        
        public static FetchDone parseFrom(final ByteBuffer data) throws InvalidProtocolBufferException {
            return (FetchDone)FetchDone.PARSER.parseFrom(data);
        }
        
        public static FetchDone parseFrom(final ByteBuffer data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (FetchDone)FetchDone.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static FetchDone parseFrom(final ByteString data) throws InvalidProtocolBufferException {
            return (FetchDone)FetchDone.PARSER.parseFrom(data);
        }
        
        public static FetchDone parseFrom(final ByteString data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (FetchDone)FetchDone.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static FetchDone parseFrom(final byte[] data) throws InvalidProtocolBufferException {
            return (FetchDone)FetchDone.PARSER.parseFrom(data);
        }
        
        public static FetchDone parseFrom(final byte[] data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (FetchDone)FetchDone.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static FetchDone parseFrom(final InputStream input) throws IOException {
            return (FetchDone)GeneratedMessageV3.parseWithIOException((Parser)FetchDone.PARSER, input);
        }
        
        public static FetchDone parseFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (FetchDone)GeneratedMessageV3.parseWithIOException((Parser)FetchDone.PARSER, input, extensionRegistry);
        }
        
        public static FetchDone parseDelimitedFrom(final InputStream input) throws IOException {
            return (FetchDone)GeneratedMessageV3.parseDelimitedWithIOException((Parser)FetchDone.PARSER, input);
        }
        
        public static FetchDone parseDelimitedFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (FetchDone)GeneratedMessageV3.parseDelimitedWithIOException((Parser)FetchDone.PARSER, input, extensionRegistry);
        }
        
        public static FetchDone parseFrom(final CodedInputStream input) throws IOException {
            return (FetchDone)GeneratedMessageV3.parseWithIOException((Parser)FetchDone.PARSER, input);
        }
        
        public static FetchDone parseFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (FetchDone)GeneratedMessageV3.parseWithIOException((Parser)FetchDone.PARSER, input, extensionRegistry);
        }
        
        public Builder newBuilderForType() {
            return newBuilder();
        }
        
        public static Builder newBuilder() {
            return FetchDone.DEFAULT_INSTANCE.toBuilder();
        }
        
        public static Builder newBuilder(final FetchDone prototype) {
            return FetchDone.DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }
        
        public Builder toBuilder() {
            return (this == FetchDone.DEFAULT_INSTANCE) ? new Builder() : new Builder().mergeFrom(this);
        }
        
        protected Builder newBuilderForType(final GeneratedMessageV3.BuilderParent parent) {
            final Builder builder = new Builder(parent);
            return builder;
        }
        
        public static FetchDone getDefaultInstance() {
            return FetchDone.DEFAULT_INSTANCE;
        }
        
        public static Parser<FetchDone> parser() {
            return FetchDone.PARSER;
        }
        
        public Parser<FetchDone> getParserForType() {
            return FetchDone.PARSER;
        }
        
        public FetchDone getDefaultInstanceForType() {
            return FetchDone.DEFAULT_INSTANCE;
        }
        
        static {
            DEFAULT_INSTANCE = new FetchDone();
            PARSER = (Parser)new AbstractParser<FetchDone>() {
                public FetchDone parsePartialFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new FetchDone(input, extensionRegistry);
                }
            };
        }
        
        public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements FetchDoneOrBuilder
        {
            public static final Descriptors.Descriptor getDescriptor() {
                return MysqlxResultset.internal_static_Mysqlx_Resultset_FetchDone_descriptor;
            }
            
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return MysqlxResultset.internal_static_Mysqlx_Resultset_FetchDone_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)FetchDone.class, (Class)Builder.class);
            }
            
            private Builder() {
                this.maybeForceBuilderInitialization();
            }
            
            private Builder(final GeneratedMessageV3.BuilderParent parent) {
                super(parent);
                this.maybeForceBuilderInitialization();
            }
            
            private void maybeForceBuilderInitialization() {
                if (FetchDone.alwaysUseFieldBuilders) {}
            }
            
            public Builder clear() {
                super.clear();
                return this;
            }
            
            public Descriptors.Descriptor getDescriptorForType() {
                return MysqlxResultset.internal_static_Mysqlx_Resultset_FetchDone_descriptor;
            }
            
            public FetchDone getDefaultInstanceForType() {
                return FetchDone.getDefaultInstance();
            }
            
            public FetchDone build() {
                final FetchDone result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw newUninitializedMessageException((Message)result);
                }
                return result;
            }
            
            public FetchDone buildPartial() {
                final FetchDone result = new FetchDone((GeneratedMessageV3.Builder)this);
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
                if (other instanceof FetchDone) {
                    return this.mergeFrom((FetchDone)other);
                }
                super.mergeFrom(other);
                return this;
            }
            
            public Builder mergeFrom(final FetchDone other) {
                if (other == FetchDone.getDefaultInstance()) {
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
                FetchDone parsedMessage = null;
                try {
                    parsedMessage = (FetchDone)FetchDone.PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (FetchDone)e.getUnfinishedMessage();
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
    
    public static final class ColumnMetaData extends GeneratedMessageV3 implements ColumnMetaDataOrBuilder
    {
        private static final long serialVersionUID = 0L;
        private int bitField0_;
        public static final int TYPE_FIELD_NUMBER = 1;
        private int type_;
        public static final int NAME_FIELD_NUMBER = 2;
        private ByteString name_;
        public static final int ORIGINAL_NAME_FIELD_NUMBER = 3;
        private ByteString originalName_;
        public static final int TABLE_FIELD_NUMBER = 4;
        private ByteString table_;
        public static final int ORIGINAL_TABLE_FIELD_NUMBER = 5;
        private ByteString originalTable_;
        public static final int SCHEMA_FIELD_NUMBER = 6;
        private ByteString schema_;
        public static final int CATALOG_FIELD_NUMBER = 7;
        private ByteString catalog_;
        public static final int COLLATION_FIELD_NUMBER = 8;
        private long collation_;
        public static final int FRACTIONAL_DIGITS_FIELD_NUMBER = 9;
        private int fractionalDigits_;
        public static final int LENGTH_FIELD_NUMBER = 10;
        private int length_;
        public static final int FLAGS_FIELD_NUMBER = 11;
        private int flags_;
        public static final int CONTENT_TYPE_FIELD_NUMBER = 12;
        private int contentType_;
        private byte memoizedIsInitialized;
        private static final ColumnMetaData DEFAULT_INSTANCE;
        @Deprecated
        public static final Parser<ColumnMetaData> PARSER;
        
        private ColumnMetaData(final GeneratedMessageV3.Builder<?> builder) {
            super((GeneratedMessageV3.Builder)builder);
            this.memoizedIsInitialized = -1;
        }
        
        private ColumnMetaData() {
            this.memoizedIsInitialized = -1;
            this.type_ = 1;
            this.name_ = ByteString.EMPTY;
            this.originalName_ = ByteString.EMPTY;
            this.table_ = ByteString.EMPTY;
            this.originalTable_ = ByteString.EMPTY;
            this.schema_ = ByteString.EMPTY;
            this.catalog_ = ByteString.EMPTY;
            this.collation_ = 0L;
            this.fractionalDigits_ = 0;
            this.length_ = 0;
            this.flags_ = 0;
            this.contentType_ = 0;
        }
        
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }
        
        private ColumnMetaData(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            final FieldType value = FieldType.valueOf(rawValue);
                            if (value == null) {
                                unknownFields.mergeVarintField(1, rawValue);
                                continue;
                            }
                            this.bitField0_ |= 0x1;
                            this.type_ = rawValue;
                            continue;
                        }
                        case 18: {
                            this.bitField0_ |= 0x2;
                            this.name_ = input.readBytes();
                            continue;
                        }
                        case 26: {
                            this.bitField0_ |= 0x4;
                            this.originalName_ = input.readBytes();
                            continue;
                        }
                        case 34: {
                            this.bitField0_ |= 0x8;
                            this.table_ = input.readBytes();
                            continue;
                        }
                        case 42: {
                            this.bitField0_ |= 0x10;
                            this.originalTable_ = input.readBytes();
                            continue;
                        }
                        case 50: {
                            this.bitField0_ |= 0x20;
                            this.schema_ = input.readBytes();
                            continue;
                        }
                        case 58: {
                            this.bitField0_ |= 0x40;
                            this.catalog_ = input.readBytes();
                            continue;
                        }
                        case 64: {
                            this.bitField0_ |= 0x80;
                            this.collation_ = input.readUInt64();
                            continue;
                        }
                        case 72: {
                            this.bitField0_ |= 0x100;
                            this.fractionalDigits_ = input.readUInt32();
                            continue;
                        }
                        case 80: {
                            this.bitField0_ |= 0x200;
                            this.length_ = input.readUInt32();
                            continue;
                        }
                        case 88: {
                            this.bitField0_ |= 0x400;
                            this.flags_ = input.readUInt32();
                            continue;
                        }
                        case 96: {
                            this.bitField0_ |= 0x800;
                            this.contentType_ = input.readUInt32();
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
            return MysqlxResultset.internal_static_Mysqlx_Resultset_ColumnMetaData_descriptor;
        }
        
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return MysqlxResultset.internal_static_Mysqlx_Resultset_ColumnMetaData_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)ColumnMetaData.class, (Class)Builder.class);
        }
        
        public boolean hasType() {
            return (this.bitField0_ & 0x1) == 0x1;
        }
        
        public FieldType getType() {
            final FieldType result = FieldType.valueOf(this.type_);
            return (result == null) ? FieldType.SINT : result;
        }
        
        public boolean hasName() {
            return (this.bitField0_ & 0x2) == 0x2;
        }
        
        public ByteString getName() {
            return this.name_;
        }
        
        public boolean hasOriginalName() {
            return (this.bitField0_ & 0x4) == 0x4;
        }
        
        public ByteString getOriginalName() {
            return this.originalName_;
        }
        
        public boolean hasTable() {
            return (this.bitField0_ & 0x8) == 0x8;
        }
        
        public ByteString getTable() {
            return this.table_;
        }
        
        public boolean hasOriginalTable() {
            return (this.bitField0_ & 0x10) == 0x10;
        }
        
        public ByteString getOriginalTable() {
            return this.originalTable_;
        }
        
        public boolean hasSchema() {
            return (this.bitField0_ & 0x20) == 0x20;
        }
        
        public ByteString getSchema() {
            return this.schema_;
        }
        
        public boolean hasCatalog() {
            return (this.bitField0_ & 0x40) == 0x40;
        }
        
        public ByteString getCatalog() {
            return this.catalog_;
        }
        
        public boolean hasCollation() {
            return (this.bitField0_ & 0x80) == 0x80;
        }
        
        public long getCollation() {
            return this.collation_;
        }
        
        public boolean hasFractionalDigits() {
            return (this.bitField0_ & 0x100) == 0x100;
        }
        
        public int getFractionalDigits() {
            return this.fractionalDigits_;
        }
        
        public boolean hasLength() {
            return (this.bitField0_ & 0x200) == 0x200;
        }
        
        public int getLength() {
            return this.length_;
        }
        
        public boolean hasFlags() {
            return (this.bitField0_ & 0x400) == 0x400;
        }
        
        public int getFlags() {
            return this.flags_;
        }
        
        public boolean hasContentType() {
            return (this.bitField0_ & 0x800) == 0x800;
        }
        
        public int getContentType() {
            return this.contentType_;
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
                output.writeEnum(1, this.type_);
            }
            if ((this.bitField0_ & 0x2) == 0x2) {
                output.writeBytes(2, this.name_);
            }
            if ((this.bitField0_ & 0x4) == 0x4) {
                output.writeBytes(3, this.originalName_);
            }
            if ((this.bitField0_ & 0x8) == 0x8) {
                output.writeBytes(4, this.table_);
            }
            if ((this.bitField0_ & 0x10) == 0x10) {
                output.writeBytes(5, this.originalTable_);
            }
            if ((this.bitField0_ & 0x20) == 0x20) {
                output.writeBytes(6, this.schema_);
            }
            if ((this.bitField0_ & 0x40) == 0x40) {
                output.writeBytes(7, this.catalog_);
            }
            if ((this.bitField0_ & 0x80) == 0x80) {
                output.writeUInt64(8, this.collation_);
            }
            if ((this.bitField0_ & 0x100) == 0x100) {
                output.writeUInt32(9, this.fractionalDigits_);
            }
            if ((this.bitField0_ & 0x200) == 0x200) {
                output.writeUInt32(10, this.length_);
            }
            if ((this.bitField0_ & 0x400) == 0x400) {
                output.writeUInt32(11, this.flags_);
            }
            if ((this.bitField0_ & 0x800) == 0x800) {
                output.writeUInt32(12, this.contentType_);
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
                size += CodedOutputStream.computeEnumSize(1, this.type_);
            }
            if ((this.bitField0_ & 0x2) == 0x2) {
                size += CodedOutputStream.computeBytesSize(2, this.name_);
            }
            if ((this.bitField0_ & 0x4) == 0x4) {
                size += CodedOutputStream.computeBytesSize(3, this.originalName_);
            }
            if ((this.bitField0_ & 0x8) == 0x8) {
                size += CodedOutputStream.computeBytesSize(4, this.table_);
            }
            if ((this.bitField0_ & 0x10) == 0x10) {
                size += CodedOutputStream.computeBytesSize(5, this.originalTable_);
            }
            if ((this.bitField0_ & 0x20) == 0x20) {
                size += CodedOutputStream.computeBytesSize(6, this.schema_);
            }
            if ((this.bitField0_ & 0x40) == 0x40) {
                size += CodedOutputStream.computeBytesSize(7, this.catalog_);
            }
            if ((this.bitField0_ & 0x80) == 0x80) {
                size += CodedOutputStream.computeUInt64Size(8, this.collation_);
            }
            if ((this.bitField0_ & 0x100) == 0x100) {
                size += CodedOutputStream.computeUInt32Size(9, this.fractionalDigits_);
            }
            if ((this.bitField0_ & 0x200) == 0x200) {
                size += CodedOutputStream.computeUInt32Size(10, this.length_);
            }
            if ((this.bitField0_ & 0x400) == 0x400) {
                size += CodedOutputStream.computeUInt32Size(11, this.flags_);
            }
            if ((this.bitField0_ & 0x800) == 0x800) {
                size += CodedOutputStream.computeUInt32Size(12, this.contentType_);
            }
            size += this.unknownFields.getSerializedSize();
            return this.memoizedSize = size;
        }
        
        public boolean equals(final Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof ColumnMetaData)) {
                return super.equals(obj);
            }
            final ColumnMetaData other = (ColumnMetaData)obj;
            boolean result = true;
            result = (result && this.hasType() == other.hasType());
            if (this.hasType()) {
                result = (result && this.type_ == other.type_);
            }
            result = (result && this.hasName() == other.hasName());
            if (this.hasName()) {
                result = (result && this.getName().equals((Object)other.getName()));
            }
            result = (result && this.hasOriginalName() == other.hasOriginalName());
            if (this.hasOriginalName()) {
                result = (result && this.getOriginalName().equals((Object)other.getOriginalName()));
            }
            result = (result && this.hasTable() == other.hasTable());
            if (this.hasTable()) {
                result = (result && this.getTable().equals((Object)other.getTable()));
            }
            result = (result && this.hasOriginalTable() == other.hasOriginalTable());
            if (this.hasOriginalTable()) {
                result = (result && this.getOriginalTable().equals((Object)other.getOriginalTable()));
            }
            result = (result && this.hasSchema() == other.hasSchema());
            if (this.hasSchema()) {
                result = (result && this.getSchema().equals((Object)other.getSchema()));
            }
            result = (result && this.hasCatalog() == other.hasCatalog());
            if (this.hasCatalog()) {
                result = (result && this.getCatalog().equals((Object)other.getCatalog()));
            }
            result = (result && this.hasCollation() == other.hasCollation());
            if (this.hasCollation()) {
                result = (result && this.getCollation() == other.getCollation());
            }
            result = (result && this.hasFractionalDigits() == other.hasFractionalDigits());
            if (this.hasFractionalDigits()) {
                result = (result && this.getFractionalDigits() == other.getFractionalDigits());
            }
            result = (result && this.hasLength() == other.hasLength());
            if (this.hasLength()) {
                result = (result && this.getLength() == other.getLength());
            }
            result = (result && this.hasFlags() == other.hasFlags());
            if (this.hasFlags()) {
                result = (result && this.getFlags() == other.getFlags());
            }
            result = (result && this.hasContentType() == other.hasContentType());
            if (this.hasContentType()) {
                result = (result && this.getContentType() == other.getContentType());
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
                hash = 53 * hash + this.type_;
            }
            if (this.hasName()) {
                hash = 37 * hash + 2;
                hash = 53 * hash + this.getName().hashCode();
            }
            if (this.hasOriginalName()) {
                hash = 37 * hash + 3;
                hash = 53 * hash + this.getOriginalName().hashCode();
            }
            if (this.hasTable()) {
                hash = 37 * hash + 4;
                hash = 53 * hash + this.getTable().hashCode();
            }
            if (this.hasOriginalTable()) {
                hash = 37 * hash + 5;
                hash = 53 * hash + this.getOriginalTable().hashCode();
            }
            if (this.hasSchema()) {
                hash = 37 * hash + 6;
                hash = 53 * hash + this.getSchema().hashCode();
            }
            if (this.hasCatalog()) {
                hash = 37 * hash + 7;
                hash = 53 * hash + this.getCatalog().hashCode();
            }
            if (this.hasCollation()) {
                hash = 37 * hash + 8;
                hash = 53 * hash + Internal.hashLong(this.getCollation());
            }
            if (this.hasFractionalDigits()) {
                hash = 37 * hash + 9;
                hash = 53 * hash + this.getFractionalDigits();
            }
            if (this.hasLength()) {
                hash = 37 * hash + 10;
                hash = 53 * hash + this.getLength();
            }
            if (this.hasFlags()) {
                hash = 37 * hash + 11;
                hash = 53 * hash + this.getFlags();
            }
            if (this.hasContentType()) {
                hash = 37 * hash + 12;
                hash = 53 * hash + this.getContentType();
            }
            hash = 29 * hash + this.unknownFields.hashCode();
            return this.memoizedHashCode = hash;
        }
        
        public static ColumnMetaData parseFrom(final ByteBuffer data) throws InvalidProtocolBufferException {
            return (ColumnMetaData)ColumnMetaData.PARSER.parseFrom(data);
        }
        
        public static ColumnMetaData parseFrom(final ByteBuffer data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ColumnMetaData)ColumnMetaData.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static ColumnMetaData parseFrom(final ByteString data) throws InvalidProtocolBufferException {
            return (ColumnMetaData)ColumnMetaData.PARSER.parseFrom(data);
        }
        
        public static ColumnMetaData parseFrom(final ByteString data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ColumnMetaData)ColumnMetaData.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static ColumnMetaData parseFrom(final byte[] data) throws InvalidProtocolBufferException {
            return (ColumnMetaData)ColumnMetaData.PARSER.parseFrom(data);
        }
        
        public static ColumnMetaData parseFrom(final byte[] data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ColumnMetaData)ColumnMetaData.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static ColumnMetaData parseFrom(final InputStream input) throws IOException {
            return (ColumnMetaData)GeneratedMessageV3.parseWithIOException((Parser)ColumnMetaData.PARSER, input);
        }
        
        public static ColumnMetaData parseFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ColumnMetaData)GeneratedMessageV3.parseWithIOException((Parser)ColumnMetaData.PARSER, input, extensionRegistry);
        }
        
        public static ColumnMetaData parseDelimitedFrom(final InputStream input) throws IOException {
            return (ColumnMetaData)GeneratedMessageV3.parseDelimitedWithIOException((Parser)ColumnMetaData.PARSER, input);
        }
        
        public static ColumnMetaData parseDelimitedFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ColumnMetaData)GeneratedMessageV3.parseDelimitedWithIOException((Parser)ColumnMetaData.PARSER, input, extensionRegistry);
        }
        
        public static ColumnMetaData parseFrom(final CodedInputStream input) throws IOException {
            return (ColumnMetaData)GeneratedMessageV3.parseWithIOException((Parser)ColumnMetaData.PARSER, input);
        }
        
        public static ColumnMetaData parseFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ColumnMetaData)GeneratedMessageV3.parseWithIOException((Parser)ColumnMetaData.PARSER, input, extensionRegistry);
        }
        
        public Builder newBuilderForType() {
            return newBuilder();
        }
        
        public static Builder newBuilder() {
            return ColumnMetaData.DEFAULT_INSTANCE.toBuilder();
        }
        
        public static Builder newBuilder(final ColumnMetaData prototype) {
            return ColumnMetaData.DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }
        
        public Builder toBuilder() {
            return (this == ColumnMetaData.DEFAULT_INSTANCE) ? new Builder() : new Builder().mergeFrom(this);
        }
        
        protected Builder newBuilderForType(final GeneratedMessageV3.BuilderParent parent) {
            final Builder builder = new Builder(parent);
            return builder;
        }
        
        public static ColumnMetaData getDefaultInstance() {
            return ColumnMetaData.DEFAULT_INSTANCE;
        }
        
        public static Parser<ColumnMetaData> parser() {
            return ColumnMetaData.PARSER;
        }
        
        public Parser<ColumnMetaData> getParserForType() {
            return ColumnMetaData.PARSER;
        }
        
        public ColumnMetaData getDefaultInstanceForType() {
            return ColumnMetaData.DEFAULT_INSTANCE;
        }
        
        static {
            DEFAULT_INSTANCE = new ColumnMetaData();
            PARSER = (Parser)new AbstractParser<ColumnMetaData>() {
                public ColumnMetaData parsePartialFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new ColumnMetaData(input, extensionRegistry);
                }
            };
        }
        
        public enum FieldType implements ProtocolMessageEnum
        {
            SINT(1), 
            UINT(2), 
            DOUBLE(5), 
            FLOAT(6), 
            BYTES(7), 
            TIME(10), 
            DATETIME(12), 
            SET(15), 
            ENUM(16), 
            BIT(17), 
            DECIMAL(18);
            
            public static final int SINT_VALUE = 1;
            public static final int UINT_VALUE = 2;
            public static final int DOUBLE_VALUE = 5;
            public static final int FLOAT_VALUE = 6;
            public static final int BYTES_VALUE = 7;
            public static final int TIME_VALUE = 10;
            public static final int DATETIME_VALUE = 12;
            public static final int SET_VALUE = 15;
            public static final int ENUM_VALUE = 16;
            public static final int BIT_VALUE = 17;
            public static final int DECIMAL_VALUE = 18;
            private static final Internal.EnumLiteMap<FieldType> internalValueMap;
            private static final FieldType[] VALUES;
            private final int value;
            
            public final int getNumber() {
                return this.value;
            }
            
            @Deprecated
            public static FieldType valueOf(final int value) {
                return forNumber(value);
            }
            
            public static FieldType forNumber(final int value) {
                switch (value) {
                    case 1: {
                        return FieldType.SINT;
                    }
                    case 2: {
                        return FieldType.UINT;
                    }
                    case 5: {
                        return FieldType.DOUBLE;
                    }
                    case 6: {
                        return FieldType.FLOAT;
                    }
                    case 7: {
                        return FieldType.BYTES;
                    }
                    case 10: {
                        return FieldType.TIME;
                    }
                    case 12: {
                        return FieldType.DATETIME;
                    }
                    case 15: {
                        return FieldType.SET;
                    }
                    case 16: {
                        return FieldType.ENUM;
                    }
                    case 17: {
                        return FieldType.BIT;
                    }
                    case 18: {
                        return FieldType.DECIMAL;
                    }
                    default: {
                        return null;
                    }
                }
            }
            
            public static Internal.EnumLiteMap<FieldType> internalGetValueMap() {
                return FieldType.internalValueMap;
            }
            
            public final Descriptors.EnumValueDescriptor getValueDescriptor() {
                return getDescriptor().getValues().get(this.ordinal());
            }
            
            public final Descriptors.EnumDescriptor getDescriptorForType() {
                return getDescriptor();
            }
            
            public static final Descriptors.EnumDescriptor getDescriptor() {
                return ColumnMetaData.getDescriptor().getEnumTypes().get(0);
            }
            
            public static FieldType valueOf(final Descriptors.EnumValueDescriptor desc) {
                if (desc.getType() != getDescriptor()) {
                    throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
                }
                return FieldType.VALUES[desc.getIndex()];
            }
            
            private FieldType(final int value) {
                this.value = value;
            }
            
            static {
                internalValueMap = (Internal.EnumLiteMap)new Internal.EnumLiteMap<FieldType>() {
                    public FieldType findValueByNumber(final int number) {
                        return FieldType.forNumber(number);
                    }
                };
                VALUES = values();
            }
        }
        
        public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements ColumnMetaDataOrBuilder
        {
            private int bitField0_;
            private int type_;
            private ByteString name_;
            private ByteString originalName_;
            private ByteString table_;
            private ByteString originalTable_;
            private ByteString schema_;
            private ByteString catalog_;
            private long collation_;
            private int fractionalDigits_;
            private int length_;
            private int flags_;
            private int contentType_;
            
            public static final Descriptors.Descriptor getDescriptor() {
                return MysqlxResultset.internal_static_Mysqlx_Resultset_ColumnMetaData_descriptor;
            }
            
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return MysqlxResultset.internal_static_Mysqlx_Resultset_ColumnMetaData_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)ColumnMetaData.class, (Class)Builder.class);
            }
            
            private Builder() {
                this.type_ = 1;
                this.name_ = ByteString.EMPTY;
                this.originalName_ = ByteString.EMPTY;
                this.table_ = ByteString.EMPTY;
                this.originalTable_ = ByteString.EMPTY;
                this.schema_ = ByteString.EMPTY;
                this.catalog_ = ByteString.EMPTY;
                this.maybeForceBuilderInitialization();
            }
            
            private Builder(final GeneratedMessageV3.BuilderParent parent) {
                super(parent);
                this.type_ = 1;
                this.name_ = ByteString.EMPTY;
                this.originalName_ = ByteString.EMPTY;
                this.table_ = ByteString.EMPTY;
                this.originalTable_ = ByteString.EMPTY;
                this.schema_ = ByteString.EMPTY;
                this.catalog_ = ByteString.EMPTY;
                this.maybeForceBuilderInitialization();
            }
            
            private void maybeForceBuilderInitialization() {
                if (ColumnMetaData.alwaysUseFieldBuilders) {}
            }
            
            public Builder clear() {
                super.clear();
                this.type_ = 1;
                this.bitField0_ &= 0xFFFFFFFE;
                this.name_ = ByteString.EMPTY;
                this.bitField0_ &= 0xFFFFFFFD;
                this.originalName_ = ByteString.EMPTY;
                this.bitField0_ &= 0xFFFFFFFB;
                this.table_ = ByteString.EMPTY;
                this.bitField0_ &= 0xFFFFFFF7;
                this.originalTable_ = ByteString.EMPTY;
                this.bitField0_ &= 0xFFFFFFEF;
                this.schema_ = ByteString.EMPTY;
                this.bitField0_ &= 0xFFFFFFDF;
                this.catalog_ = ByteString.EMPTY;
                this.bitField0_ &= 0xFFFFFFBF;
                this.collation_ = 0L;
                this.bitField0_ &= 0xFFFFFF7F;
                this.fractionalDigits_ = 0;
                this.bitField0_ &= 0xFFFFFEFF;
                this.length_ = 0;
                this.bitField0_ &= 0xFFFFFDFF;
                this.flags_ = 0;
                this.bitField0_ &= 0xFFFFFBFF;
                this.contentType_ = 0;
                this.bitField0_ &= 0xFFFFF7FF;
                return this;
            }
            
            public Descriptors.Descriptor getDescriptorForType() {
                return MysqlxResultset.internal_static_Mysqlx_Resultset_ColumnMetaData_descriptor;
            }
            
            public ColumnMetaData getDefaultInstanceForType() {
                return ColumnMetaData.getDefaultInstance();
            }
            
            public ColumnMetaData build() {
                final ColumnMetaData result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw newUninitializedMessageException((Message)result);
                }
                return result;
            }
            
            public ColumnMetaData buildPartial() {
                final ColumnMetaData result = new ColumnMetaData((GeneratedMessageV3.Builder)this);
                final int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 0x1) == 0x1) {
                    to_bitField0_ |= 0x1;
                }
                result.type_ = this.type_;
                if ((from_bitField0_ & 0x2) == 0x2) {
                    to_bitField0_ |= 0x2;
                }
                result.name_ = this.name_;
                if ((from_bitField0_ & 0x4) == 0x4) {
                    to_bitField0_ |= 0x4;
                }
                result.originalName_ = this.originalName_;
                if ((from_bitField0_ & 0x8) == 0x8) {
                    to_bitField0_ |= 0x8;
                }
                result.table_ = this.table_;
                if ((from_bitField0_ & 0x10) == 0x10) {
                    to_bitField0_ |= 0x10;
                }
                result.originalTable_ = this.originalTable_;
                if ((from_bitField0_ & 0x20) == 0x20) {
                    to_bitField0_ |= 0x20;
                }
                result.schema_ = this.schema_;
                if ((from_bitField0_ & 0x40) == 0x40) {
                    to_bitField0_ |= 0x40;
                }
                result.catalog_ = this.catalog_;
                if ((from_bitField0_ & 0x80) == 0x80) {
                    to_bitField0_ |= 0x80;
                }
                result.collation_ = this.collation_;
                if ((from_bitField0_ & 0x100) == 0x100) {
                    to_bitField0_ |= 0x100;
                }
                result.fractionalDigits_ = this.fractionalDigits_;
                if ((from_bitField0_ & 0x200) == 0x200) {
                    to_bitField0_ |= 0x200;
                }
                result.length_ = this.length_;
                if ((from_bitField0_ & 0x400) == 0x400) {
                    to_bitField0_ |= 0x400;
                }
                result.flags_ = this.flags_;
                if ((from_bitField0_ & 0x800) == 0x800) {
                    to_bitField0_ |= 0x800;
                }
                result.contentType_ = this.contentType_;
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
                if (other instanceof ColumnMetaData) {
                    return this.mergeFrom((ColumnMetaData)other);
                }
                super.mergeFrom(other);
                return this;
            }
            
            public Builder mergeFrom(final ColumnMetaData other) {
                if (other == ColumnMetaData.getDefaultInstance()) {
                    return this;
                }
                if (other.hasType()) {
                    this.setType(other.getType());
                }
                if (other.hasName()) {
                    this.setName(other.getName());
                }
                if (other.hasOriginalName()) {
                    this.setOriginalName(other.getOriginalName());
                }
                if (other.hasTable()) {
                    this.setTable(other.getTable());
                }
                if (other.hasOriginalTable()) {
                    this.setOriginalTable(other.getOriginalTable());
                }
                if (other.hasSchema()) {
                    this.setSchema(other.getSchema());
                }
                if (other.hasCatalog()) {
                    this.setCatalog(other.getCatalog());
                }
                if (other.hasCollation()) {
                    this.setCollation(other.getCollation());
                }
                if (other.hasFractionalDigits()) {
                    this.setFractionalDigits(other.getFractionalDigits());
                }
                if (other.hasLength()) {
                    this.setLength(other.getLength());
                }
                if (other.hasFlags()) {
                    this.setFlags(other.getFlags());
                }
                if (other.hasContentType()) {
                    this.setContentType(other.getContentType());
                }
                this.mergeUnknownFields(other.unknownFields);
                this.onChanged();
                return this;
            }
            
            public final boolean isInitialized() {
                return this.hasType();
            }
            
            public Builder mergeFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
                ColumnMetaData parsedMessage = null;
                try {
                    parsedMessage = (ColumnMetaData)ColumnMetaData.PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (ColumnMetaData)e.getUnfinishedMessage();
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
            
            public FieldType getType() {
                final FieldType result = FieldType.valueOf(this.type_);
                return (result == null) ? FieldType.SINT : result;
            }
            
            public Builder setType(final FieldType value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 0x1;
                this.type_ = value.getNumber();
                this.onChanged();
                return this;
            }
            
            public Builder clearType() {
                this.bitField0_ &= 0xFFFFFFFE;
                this.type_ = 1;
                this.onChanged();
                return this;
            }
            
            public boolean hasName() {
                return (this.bitField0_ & 0x2) == 0x2;
            }
            
            public ByteString getName() {
                return this.name_;
            }
            
            public Builder setName(final ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 0x2;
                this.name_ = value;
                this.onChanged();
                return this;
            }
            
            public Builder clearName() {
                this.bitField0_ &= 0xFFFFFFFD;
                this.name_ = ColumnMetaData.getDefaultInstance().getName();
                this.onChanged();
                return this;
            }
            
            public boolean hasOriginalName() {
                return (this.bitField0_ & 0x4) == 0x4;
            }
            
            public ByteString getOriginalName() {
                return this.originalName_;
            }
            
            public Builder setOriginalName(final ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 0x4;
                this.originalName_ = value;
                this.onChanged();
                return this;
            }
            
            public Builder clearOriginalName() {
                this.bitField0_ &= 0xFFFFFFFB;
                this.originalName_ = ColumnMetaData.getDefaultInstance().getOriginalName();
                this.onChanged();
                return this;
            }
            
            public boolean hasTable() {
                return (this.bitField0_ & 0x8) == 0x8;
            }
            
            public ByteString getTable() {
                return this.table_;
            }
            
            public Builder setTable(final ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 0x8;
                this.table_ = value;
                this.onChanged();
                return this;
            }
            
            public Builder clearTable() {
                this.bitField0_ &= 0xFFFFFFF7;
                this.table_ = ColumnMetaData.getDefaultInstance().getTable();
                this.onChanged();
                return this;
            }
            
            public boolean hasOriginalTable() {
                return (this.bitField0_ & 0x10) == 0x10;
            }
            
            public ByteString getOriginalTable() {
                return this.originalTable_;
            }
            
            public Builder setOriginalTable(final ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 0x10;
                this.originalTable_ = value;
                this.onChanged();
                return this;
            }
            
            public Builder clearOriginalTable() {
                this.bitField0_ &= 0xFFFFFFEF;
                this.originalTable_ = ColumnMetaData.getDefaultInstance().getOriginalTable();
                this.onChanged();
                return this;
            }
            
            public boolean hasSchema() {
                return (this.bitField0_ & 0x20) == 0x20;
            }
            
            public ByteString getSchema() {
                return this.schema_;
            }
            
            public Builder setSchema(final ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 0x20;
                this.schema_ = value;
                this.onChanged();
                return this;
            }
            
            public Builder clearSchema() {
                this.bitField0_ &= 0xFFFFFFDF;
                this.schema_ = ColumnMetaData.getDefaultInstance().getSchema();
                this.onChanged();
                return this;
            }
            
            public boolean hasCatalog() {
                return (this.bitField0_ & 0x40) == 0x40;
            }
            
            public ByteString getCatalog() {
                return this.catalog_;
            }
            
            public Builder setCatalog(final ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 0x40;
                this.catalog_ = value;
                this.onChanged();
                return this;
            }
            
            public Builder clearCatalog() {
                this.bitField0_ &= 0xFFFFFFBF;
                this.catalog_ = ColumnMetaData.getDefaultInstance().getCatalog();
                this.onChanged();
                return this;
            }
            
            public boolean hasCollation() {
                return (this.bitField0_ & 0x80) == 0x80;
            }
            
            public long getCollation() {
                return this.collation_;
            }
            
            public Builder setCollation(final long value) {
                this.bitField0_ |= 0x80;
                this.collation_ = value;
                this.onChanged();
                return this;
            }
            
            public Builder clearCollation() {
                this.bitField0_ &= 0xFFFFFF7F;
                this.collation_ = 0L;
                this.onChanged();
                return this;
            }
            
            public boolean hasFractionalDigits() {
                return (this.bitField0_ & 0x100) == 0x100;
            }
            
            public int getFractionalDigits() {
                return this.fractionalDigits_;
            }
            
            public Builder setFractionalDigits(final int value) {
                this.bitField0_ |= 0x100;
                this.fractionalDigits_ = value;
                this.onChanged();
                return this;
            }
            
            public Builder clearFractionalDigits() {
                this.bitField0_ &= 0xFFFFFEFF;
                this.fractionalDigits_ = 0;
                this.onChanged();
                return this;
            }
            
            public boolean hasLength() {
                return (this.bitField0_ & 0x200) == 0x200;
            }
            
            public int getLength() {
                return this.length_;
            }
            
            public Builder setLength(final int value) {
                this.bitField0_ |= 0x200;
                this.length_ = value;
                this.onChanged();
                return this;
            }
            
            public Builder clearLength() {
                this.bitField0_ &= 0xFFFFFDFF;
                this.length_ = 0;
                this.onChanged();
                return this;
            }
            
            public boolean hasFlags() {
                return (this.bitField0_ & 0x400) == 0x400;
            }
            
            public int getFlags() {
                return this.flags_;
            }
            
            public Builder setFlags(final int value) {
                this.bitField0_ |= 0x400;
                this.flags_ = value;
                this.onChanged();
                return this;
            }
            
            public Builder clearFlags() {
                this.bitField0_ &= 0xFFFFFBFF;
                this.flags_ = 0;
                this.onChanged();
                return this;
            }
            
            public boolean hasContentType() {
                return (this.bitField0_ & 0x800) == 0x800;
            }
            
            public int getContentType() {
                return this.contentType_;
            }
            
            public Builder setContentType(final int value) {
                this.bitField0_ |= 0x800;
                this.contentType_ = value;
                this.onChanged();
                return this;
            }
            
            public Builder clearContentType() {
                this.bitField0_ &= 0xFFFFF7FF;
                this.contentType_ = 0;
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
    
    public static final class Row extends GeneratedMessageV3 implements RowOrBuilder
    {
        private static final long serialVersionUID = 0L;
        public static final int FIELD_FIELD_NUMBER = 1;
        private List<ByteString> field_;
        private byte memoizedIsInitialized;
        private static final Row DEFAULT_INSTANCE;
        @Deprecated
        public static final Parser<Row> PARSER;
        
        private Row(final GeneratedMessageV3.Builder<?> builder) {
            super((GeneratedMessageV3.Builder)builder);
            this.memoizedIsInitialized = -1;
        }
        
        private Row() {
            this.memoizedIsInitialized = -1;
            this.field_ = Collections.emptyList();
        }
        
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }
        
        private Row(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                                this.field_ = new ArrayList<ByteString>();
                                mutable_bitField0_ |= 0x1;
                            }
                            this.field_.add(input.readBytes());
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
                    this.field_ = Collections.unmodifiableList((List<? extends ByteString>)this.field_);
                }
                this.unknownFields = unknownFields.build();
                this.makeExtensionsImmutable();
            }
        }
        
        public static final Descriptors.Descriptor getDescriptor() {
            return MysqlxResultset.internal_static_Mysqlx_Resultset_Row_descriptor;
        }
        
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return MysqlxResultset.internal_static_Mysqlx_Resultset_Row_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)Row.class, (Class)Builder.class);
        }
        
        public List<ByteString> getFieldList() {
            return this.field_;
        }
        
        public int getFieldCount() {
            return this.field_.size();
        }
        
        public ByteString getField(final int index) {
            return this.field_.get(index);
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
            for (int i = 0; i < this.field_.size(); ++i) {
                output.writeBytes(1, (ByteString)this.field_.get(i));
            }
            this.unknownFields.writeTo(output);
        }
        
        public int getSerializedSize() {
            int size = this.memoizedSize;
            if (size != -1) {
                return size;
            }
            size = 0;
            int dataSize = 0;
            for (int i = 0; i < this.field_.size(); ++i) {
                dataSize += CodedOutputStream.computeBytesSizeNoTag((ByteString)this.field_.get(i));
            }
            size += dataSize;
            size += 1 * this.getFieldList().size();
            size += this.unknownFields.getSerializedSize();
            return this.memoizedSize = size;
        }
        
        public boolean equals(final Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Row)) {
                return super.equals(obj);
            }
            final Row other = (Row)obj;
            boolean result = true;
            result = (result && this.getFieldList().equals(other.getFieldList()));
            result = (result && this.unknownFields.equals((Object)other.unknownFields));
            return result;
        }
        
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + getDescriptor().hashCode();
            if (this.getFieldCount() > 0) {
                hash = 37 * hash + 1;
                hash = 53 * hash + this.getFieldList().hashCode();
            }
            hash = 29 * hash + this.unknownFields.hashCode();
            return this.memoizedHashCode = hash;
        }
        
        public static Row parseFrom(final ByteBuffer data) throws InvalidProtocolBufferException {
            return (Row)Row.PARSER.parseFrom(data);
        }
        
        public static Row parseFrom(final ByteBuffer data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Row)Row.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Row parseFrom(final ByteString data) throws InvalidProtocolBufferException {
            return (Row)Row.PARSER.parseFrom(data);
        }
        
        public static Row parseFrom(final ByteString data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Row)Row.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Row parseFrom(final byte[] data) throws InvalidProtocolBufferException {
            return (Row)Row.PARSER.parseFrom(data);
        }
        
        public static Row parseFrom(final byte[] data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Row)Row.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Row parseFrom(final InputStream input) throws IOException {
            return (Row)GeneratedMessageV3.parseWithIOException((Parser)Row.PARSER, input);
        }
        
        public static Row parseFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Row)GeneratedMessageV3.parseWithIOException((Parser)Row.PARSER, input, extensionRegistry);
        }
        
        public static Row parseDelimitedFrom(final InputStream input) throws IOException {
            return (Row)GeneratedMessageV3.parseDelimitedWithIOException((Parser)Row.PARSER, input);
        }
        
        public static Row parseDelimitedFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Row)GeneratedMessageV3.parseDelimitedWithIOException((Parser)Row.PARSER, input, extensionRegistry);
        }
        
        public static Row parseFrom(final CodedInputStream input) throws IOException {
            return (Row)GeneratedMessageV3.parseWithIOException((Parser)Row.PARSER, input);
        }
        
        public static Row parseFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Row)GeneratedMessageV3.parseWithIOException((Parser)Row.PARSER, input, extensionRegistry);
        }
        
        public Builder newBuilderForType() {
            return newBuilder();
        }
        
        public static Builder newBuilder() {
            return Row.DEFAULT_INSTANCE.toBuilder();
        }
        
        public static Builder newBuilder(final Row prototype) {
            return Row.DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }
        
        public Builder toBuilder() {
            return (this == Row.DEFAULT_INSTANCE) ? new Builder() : new Builder().mergeFrom(this);
        }
        
        protected Builder newBuilderForType(final GeneratedMessageV3.BuilderParent parent) {
            final Builder builder = new Builder(parent);
            return builder;
        }
        
        public static Row getDefaultInstance() {
            return Row.DEFAULT_INSTANCE;
        }
        
        public static Parser<Row> parser() {
            return Row.PARSER;
        }
        
        public Parser<Row> getParserForType() {
            return Row.PARSER;
        }
        
        public Row getDefaultInstanceForType() {
            return Row.DEFAULT_INSTANCE;
        }
        
        static {
            DEFAULT_INSTANCE = new Row();
            PARSER = (Parser)new AbstractParser<Row>() {
                public Row parsePartialFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new Row(input, extensionRegistry);
                }
            };
        }
        
        public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements RowOrBuilder
        {
            private int bitField0_;
            private List<ByteString> field_;
            
            public static final Descriptors.Descriptor getDescriptor() {
                return MysqlxResultset.internal_static_Mysqlx_Resultset_Row_descriptor;
            }
            
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return MysqlxResultset.internal_static_Mysqlx_Resultset_Row_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)Row.class, (Class)Builder.class);
            }
            
            private Builder() {
                this.field_ = Collections.emptyList();
                this.maybeForceBuilderInitialization();
            }
            
            private Builder(final GeneratedMessageV3.BuilderParent parent) {
                super(parent);
                this.field_ = Collections.emptyList();
                this.maybeForceBuilderInitialization();
            }
            
            private void maybeForceBuilderInitialization() {
                if (Row.alwaysUseFieldBuilders) {}
            }
            
            public Builder clear() {
                super.clear();
                this.field_ = Collections.emptyList();
                this.bitField0_ &= 0xFFFFFFFE;
                return this;
            }
            
            public Descriptors.Descriptor getDescriptorForType() {
                return MysqlxResultset.internal_static_Mysqlx_Resultset_Row_descriptor;
            }
            
            public Row getDefaultInstanceForType() {
                return Row.getDefaultInstance();
            }
            
            public Row build() {
                final Row result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw newUninitializedMessageException((Message)result);
                }
                return result;
            }
            
            public Row buildPartial() {
                final Row result = new Row((GeneratedMessageV3.Builder)this);
                final int from_bitField0_ = this.bitField0_;
                if ((this.bitField0_ & 0x1) == 0x1) {
                    this.field_ = Collections.unmodifiableList((List<? extends ByteString>)this.field_);
                    this.bitField0_ &= 0xFFFFFFFE;
                }
                result.field_ = this.field_;
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
                if (other instanceof Row) {
                    return this.mergeFrom((Row)other);
                }
                super.mergeFrom(other);
                return this;
            }
            
            public Builder mergeFrom(final Row other) {
                if (other == Row.getDefaultInstance()) {
                    return this;
                }
                if (!other.field_.isEmpty()) {
                    if (this.field_.isEmpty()) {
                        this.field_ = other.field_;
                        this.bitField0_ &= 0xFFFFFFFE;
                    }
                    else {
                        this.ensureFieldIsMutable();
                        this.field_.addAll(other.field_);
                    }
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
                Row parsedMessage = null;
                try {
                    parsedMessage = (Row)Row.PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (Row)e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                }
                finally {
                    if (parsedMessage != null) {
                        this.mergeFrom(parsedMessage);
                    }
                }
                return this;
            }
            
            private void ensureFieldIsMutable() {
                if ((this.bitField0_ & 0x1) != 0x1) {
                    this.field_ = new ArrayList<ByteString>(this.field_);
                    this.bitField0_ |= 0x1;
                }
            }
            
            public List<ByteString> getFieldList() {
                return Collections.unmodifiableList((List<? extends ByteString>)this.field_);
            }
            
            public int getFieldCount() {
                return this.field_.size();
            }
            
            public ByteString getField(final int index) {
                return this.field_.get(index);
            }
            
            public Builder setField(final int index, final ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureFieldIsMutable();
                this.field_.set(index, value);
                this.onChanged();
                return this;
            }
            
            public Builder addField(final ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureFieldIsMutable();
                this.field_.add(value);
                this.onChanged();
                return this;
            }
            
            public Builder addAllField(final Iterable<? extends ByteString> values) {
                this.ensureFieldIsMutable();
                AbstractMessageLite.Builder.addAll((Iterable)values, (List)this.field_);
                this.onChanged();
                return this;
            }
            
            public Builder clearField() {
                this.field_ = Collections.emptyList();
                this.bitField0_ &= 0xFFFFFFFE;
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
    
    public interface RowOrBuilder extends MessageOrBuilder
    {
        List<ByteString> getFieldList();
        
        int getFieldCount();
        
        ByteString getField(final int p0);
    }
    
    public interface ColumnMetaDataOrBuilder extends MessageOrBuilder
    {
        boolean hasType();
        
        ColumnMetaData.FieldType getType();
        
        boolean hasName();
        
        ByteString getName();
        
        boolean hasOriginalName();
        
        ByteString getOriginalName();
        
        boolean hasTable();
        
        ByteString getTable();
        
        boolean hasOriginalTable();
        
        ByteString getOriginalTable();
        
        boolean hasSchema();
        
        ByteString getSchema();
        
        boolean hasCatalog();
        
        ByteString getCatalog();
        
        boolean hasCollation();
        
        long getCollation();
        
        boolean hasFractionalDigits();
        
        int getFractionalDigits();
        
        boolean hasLength();
        
        int getLength();
        
        boolean hasFlags();
        
        int getFlags();
        
        boolean hasContentType();
        
        int getContentType();
    }
    
    public interface FetchDoneOrBuilder extends MessageOrBuilder
    {
    }
    
    public interface FetchDoneMoreResultsetsOrBuilder extends MessageOrBuilder
    {
    }
    
    public interface FetchDoneMoreOutParamsOrBuilder extends MessageOrBuilder
    {
    }
}
