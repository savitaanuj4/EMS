
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
import com.google.protobuf.Internal;
import com.google.protobuf.ProtocolMessageEnum;
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
import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.Descriptors;

public final class MysqlxExpr
{
    private static final Descriptors.Descriptor internal_static_Mysqlx_Expr_Expr_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_Mysqlx_Expr_Expr_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_Mysqlx_Expr_Identifier_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_Mysqlx_Expr_Identifier_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_Mysqlx_Expr_DocumentPathItem_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_Mysqlx_Expr_DocumentPathItem_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_Mysqlx_Expr_ColumnIdentifier_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_Mysqlx_Expr_ColumnIdentifier_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_Mysqlx_Expr_FunctionCall_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_Mysqlx_Expr_FunctionCall_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_Mysqlx_Expr_Operator_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_Mysqlx_Expr_Operator_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_Mysqlx_Expr_Object_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_Mysqlx_Expr_Object_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_Mysqlx_Expr_Object_ObjectField_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_Mysqlx_Expr_Object_ObjectField_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_Mysqlx_Expr_Array_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_Mysqlx_Expr_Array_fieldAccessorTable;
    private static Descriptors.FileDescriptor descriptor;
    
    private MysqlxExpr() {
    }
    
    public static void registerAllExtensions(final ExtensionRegistryLite registry) {
    }
    
    public static void registerAllExtensions(final ExtensionRegistry registry) {
        registerAllExtensions((ExtensionRegistryLite)registry);
    }
    
    public static Descriptors.FileDescriptor getDescriptor() {
        return MysqlxExpr.descriptor;
    }
    
    static {
        final String[] descriptorData = { "\n\u0011mysqlx_expr.proto\u0012\u000bMysqlx.Expr\u001a\u0016mysqlx_datatypes.proto\"\u00c4\u0003\n\u0004Expr\u0012$\n\u0004type\u0018\u0001 \u0002(\u000e2\u0016.Mysqlx.Expr.Expr.Type\u00121\n\nidentifier\u0018\u0002 \u0001(\u000b2\u001d.Mysqlx.Expr.ColumnIdentifier\u0012\u0010\n\bvariable\u0018\u0003 \u0001(\t\u0012)\n\u0007literal\u0018\u0004 \u0001(\u000b2\u0018.Mysqlx.Datatypes.Scalar\u00120\n\rfunction_call\u0018\u0005 \u0001(\u000b2\u0019.Mysqlx.Expr.FunctionCall\u0012'\n\boperator\u0018\u0006 \u0001(\u000b2\u0015.Mysqlx.Expr.Operator\u0012\u0010\n\bposition\u0018\u0007 \u0001(\r\u0012#\n\u0006object\u0018\b \u0001(\u000b2\u0013.Mysqlx.Expr.Object\u0012!\n\u0005array\u0018\t \u0001(\u000b2\u0012.Mysqlx.Expr.Array\"q\n\u0004Type\u0012\t\n\u0005IDENT\u0010\u0001\u0012\u000b\n\u0007LITERAL\u0010\u0002\u0012\f\n\bVARIABLE\u0010\u0003\u0012\r\n\tFUNC_CALL\u0010\u0004\u0012\f\n\bOPERATOR\u0010\u0005\u0012\u000f\n\u000bPLACEHOLDER\u0010\u0006\u0012\n\n\u0006OBJECT\u0010\u0007\u0012\t\n\u0005ARRAY\u0010\b\"/\n\nIdentifier\u0012\f\n\u0004name\u0018\u0001 \u0002(\t\u0012\u0013\n\u000bschema_name\u0018\u0002 \u0001(\t\"\u00cb\u0001\n\u0010DocumentPathItem\u00120\n\u0004type\u0018\u0001 \u0002(\u000e2\".Mysqlx.Expr.DocumentPathItem.Type\u0012\r\n\u0005value\u0018\u0002 \u0001(\t\u0012\r\n\u0005index\u0018\u0003 \u0001(\r\"g\n\u0004Type\u0012\n\n\u0006MEMBER\u0010\u0001\u0012\u0013\n\u000fMEMBER_ASTERISK\u0010\u0002\u0012\u000f\n\u000bARRAY_INDEX\u0010\u0003\u0012\u0018\n\u0014ARRAY_INDEX_ASTERISK\u0010\u0004\u0012\u0013\n\u000fDOUBLE_ASTERISK\u0010\u0005\"\u007f\n\u0010ColumnIdentifier\u00124\n\rdocument_path\u0018\u0001 \u0003(\u000b2\u001d.Mysqlx.Expr.DocumentPathItem\u0012\f\n\u0004name\u0018\u0002 \u0001(\t\u0012\u0012\n\ntable_name\u0018\u0003 \u0001(\t\u0012\u0013\n\u000bschema_name\u0018\u0004 \u0001(\t\"W\n\fFunctionCall\u0012%\n\u0004name\u0018\u0001 \u0002(\u000b2\u0017.Mysqlx.Expr.Identifier\u0012 \n\u0005param\u0018\u0002 \u0003(\u000b2\u0011.Mysqlx.Expr.Expr\":\n\bOperator\u0012\f\n\u0004name\u0018\u0001 \u0002(\t\u0012 \n\u0005param\u0018\u0002 \u0003(\u000b2\u0011.Mysqlx.Expr.Expr\"t\n\u0006Object\u0012,\n\u0003fld\u0018\u0001 \u0003(\u000b2\u001f.Mysqlx.Expr.Object.ObjectField\u001a<\n\u000bObjectField\u0012\u000b\n\u0003key\u0018\u0001 \u0002(\t\u0012 \n\u0005value\u0018\u0002 \u0002(\u000b2\u0011.Mysqlx.Expr.Expr\")\n\u0005Array\u0012 \n\u0005value\u0018\u0001 \u0003(\u000b2\u0011.Mysqlx.Expr.ExprB\u0019\n\u0017com.mysql.cj.x.protobuf" };
        final Descriptors.FileDescriptor.InternalDescriptorAssigner assigner = (Descriptors.FileDescriptor.InternalDescriptorAssigner)new Descriptors.FileDescriptor.InternalDescriptorAssigner() {
            public ExtensionRegistry assignDescriptors(final Descriptors.FileDescriptor root) {
                MysqlxExpr.descriptor = root;
                return null;
            }
        };
        Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new Descriptors.FileDescriptor[] { MysqlxDatatypes.getDescriptor() }, assigner);
        internal_static_Mysqlx_Expr_Expr_descriptor = getDescriptor().getMessageTypes().get(0);
        internal_static_Mysqlx_Expr_Expr_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(MysqlxExpr.internal_static_Mysqlx_Expr_Expr_descriptor, new String[] { "Type", "Identifier", "Variable", "Literal", "FunctionCall", "Operator", "Position", "Object", "Array" });
        internal_static_Mysqlx_Expr_Identifier_descriptor = getDescriptor().getMessageTypes().get(1);
        internal_static_Mysqlx_Expr_Identifier_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(MysqlxExpr.internal_static_Mysqlx_Expr_Identifier_descriptor, new String[] { "Name", "SchemaName" });
        internal_static_Mysqlx_Expr_DocumentPathItem_descriptor = getDescriptor().getMessageTypes().get(2);
        internal_static_Mysqlx_Expr_DocumentPathItem_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(MysqlxExpr.internal_static_Mysqlx_Expr_DocumentPathItem_descriptor, new String[] { "Type", "Value", "Index" });
        internal_static_Mysqlx_Expr_ColumnIdentifier_descriptor = getDescriptor().getMessageTypes().get(3);
        internal_static_Mysqlx_Expr_ColumnIdentifier_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(MysqlxExpr.internal_static_Mysqlx_Expr_ColumnIdentifier_descriptor, new String[] { "DocumentPath", "Name", "TableName", "SchemaName" });
        internal_static_Mysqlx_Expr_FunctionCall_descriptor = getDescriptor().getMessageTypes().get(4);
        internal_static_Mysqlx_Expr_FunctionCall_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(MysqlxExpr.internal_static_Mysqlx_Expr_FunctionCall_descriptor, new String[] { "Name", "Param" });
        internal_static_Mysqlx_Expr_Operator_descriptor = getDescriptor().getMessageTypes().get(5);
        internal_static_Mysqlx_Expr_Operator_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(MysqlxExpr.internal_static_Mysqlx_Expr_Operator_descriptor, new String[] { "Name", "Param" });
        internal_static_Mysqlx_Expr_Object_descriptor = getDescriptor().getMessageTypes().get(6);
        internal_static_Mysqlx_Expr_Object_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(MysqlxExpr.internal_static_Mysqlx_Expr_Object_descriptor, new String[] { "Fld" });
        internal_static_Mysqlx_Expr_Object_ObjectField_descriptor = MysqlxExpr.internal_static_Mysqlx_Expr_Object_descriptor.getNestedTypes().get(0);
        internal_static_Mysqlx_Expr_Object_ObjectField_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(MysqlxExpr.internal_static_Mysqlx_Expr_Object_ObjectField_descriptor, new String[] { "Key", "Value" });
        internal_static_Mysqlx_Expr_Array_descriptor = getDescriptor().getMessageTypes().get(7);
        internal_static_Mysqlx_Expr_Array_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(MysqlxExpr.internal_static_Mysqlx_Expr_Array_descriptor, new String[] { "Value" });
        MysqlxDatatypes.getDescriptor();
    }
    
    public static final class Expr extends GeneratedMessageV3 implements ExprOrBuilder
    {
        private static final long serialVersionUID = 0L;
        private int bitField0_;
        public static final int TYPE_FIELD_NUMBER = 1;
        private int type_;
        public static final int IDENTIFIER_FIELD_NUMBER = 2;
        private ColumnIdentifier identifier_;
        public static final int VARIABLE_FIELD_NUMBER = 3;
        private volatile Object variable_;
        public static final int LITERAL_FIELD_NUMBER = 4;
        private MysqlxDatatypes.Scalar literal_;
        public static final int FUNCTION_CALL_FIELD_NUMBER = 5;
        private FunctionCall functionCall_;
        public static final int OPERATOR_FIELD_NUMBER = 6;
        private Operator operator_;
        public static final int POSITION_FIELD_NUMBER = 7;
        private int position_;
        public static final int OBJECT_FIELD_NUMBER = 8;
        private MysqlxExpr.Object object_;
        public static final int ARRAY_FIELD_NUMBER = 9;
        private Array array_;
        private byte memoizedIsInitialized;
        private static final Expr DEFAULT_INSTANCE;
        @Deprecated
        public static final Parser<Expr> PARSER;
        
        private Expr(final GeneratedMessageV3.Builder<?> builder) {
            super((GeneratedMessageV3.Builder)builder);
            this.memoizedIsInitialized = -1;
        }
        
        private Expr() {
            this.memoizedIsInitialized = -1;
            this.type_ = 1;
            this.variable_ = "";
            this.position_ = 0;
        }
        
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }
        
        private Expr(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            final Type value = Type.valueOf(rawValue);
                            if (value == null) {
                                unknownFields.mergeVarintField(1, rawValue);
                                continue;
                            }
                            this.bitField0_ |= 0x1;
                            this.type_ = rawValue;
                            continue;
                        }
                        case 18: {
                            ColumnIdentifier.Builder subBuilder = null;
                            if ((this.bitField0_ & 0x2) == 0x2) {
                                subBuilder = this.identifier_.toBuilder();
                            }
                            this.identifier_ = (ColumnIdentifier)input.readMessage((Parser)ColumnIdentifier.PARSER, extensionRegistry);
                            if (subBuilder != null) {
                                subBuilder.mergeFrom(this.identifier_);
                                this.identifier_ = subBuilder.buildPartial();
                            }
                            this.bitField0_ |= 0x2;
                            continue;
                        }
                        case 26: {
                            final ByteString bs = input.readBytes();
                            this.bitField0_ |= 0x4;
                            this.variable_ = bs;
                            continue;
                        }
                        case 34: {
                            MysqlxDatatypes.Scalar.Builder subBuilder2 = null;
                            if ((this.bitField0_ & 0x8) == 0x8) {
                                subBuilder2 = this.literal_.toBuilder();
                            }
                            this.literal_ = (MysqlxDatatypes.Scalar)input.readMessage((Parser)MysqlxDatatypes.Scalar.PARSER, extensionRegistry);
                            if (subBuilder2 != null) {
                                subBuilder2.mergeFrom(this.literal_);
                                this.literal_ = subBuilder2.buildPartial();
                            }
                            this.bitField0_ |= 0x8;
                            continue;
                        }
                        case 42: {
                            FunctionCall.Builder subBuilder3 = null;
                            if ((this.bitField0_ & 0x10) == 0x10) {
                                subBuilder3 = this.functionCall_.toBuilder();
                            }
                            this.functionCall_ = (FunctionCall)input.readMessage((Parser)FunctionCall.PARSER, extensionRegistry);
                            if (subBuilder3 != null) {
                                subBuilder3.mergeFrom(this.functionCall_);
                                this.functionCall_ = subBuilder3.buildPartial();
                            }
                            this.bitField0_ |= 0x10;
                            continue;
                        }
                        case 50: {
                            Operator.Builder subBuilder4 = null;
                            if ((this.bitField0_ & 0x20) == 0x20) {
                                subBuilder4 = this.operator_.toBuilder();
                            }
                            this.operator_ = (Operator)input.readMessage((Parser)Operator.PARSER, extensionRegistry);
                            if (subBuilder4 != null) {
                                subBuilder4.mergeFrom(this.operator_);
                                this.operator_ = subBuilder4.buildPartial();
                            }
                            this.bitField0_ |= 0x20;
                            continue;
                        }
                        case 56: {
                            this.bitField0_ |= 0x40;
                            this.position_ = input.readUInt32();
                            continue;
                        }
                        case 66: {
                            MysqlxExpr.Object.Builder subBuilder5 = null;
                            if ((this.bitField0_ & 0x80) == 0x80) {
                                subBuilder5 = this.object_.toBuilder();
                            }
                            this.object_ = (MysqlxExpr.Object)input.readMessage((Parser)MysqlxExpr.Object.PARSER, extensionRegistry);
                            if (subBuilder5 != null) {
                                subBuilder5.mergeFrom(this.object_);
                                this.object_ = subBuilder5.buildPartial();
                            }
                            this.bitField0_ |= 0x80;
                            continue;
                        }
                        case 74: {
                            Array.Builder subBuilder6 = null;
                            if ((this.bitField0_ & 0x100) == 0x100) {
                                subBuilder6 = this.array_.toBuilder();
                            }
                            this.array_ = (Array)input.readMessage((Parser)Array.PARSER, extensionRegistry);
                            if (subBuilder6 != null) {
                                subBuilder6.mergeFrom(this.array_);
                                this.array_ = subBuilder6.buildPartial();
                            }
                            this.bitField0_ |= 0x100;
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
            return MysqlxExpr.internal_static_Mysqlx_Expr_Expr_descriptor;
        }
        
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return MysqlxExpr.internal_static_Mysqlx_Expr_Expr_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)Expr.class, (Class)Builder.class);
        }
        
        public boolean hasType() {
            return (this.bitField0_ & 0x1) == 0x1;
        }
        
        public Type getType() {
            final Type result = Type.valueOf(this.type_);
            return (result == null) ? Type.IDENT : result;
        }
        
        public boolean hasIdentifier() {
            return (this.bitField0_ & 0x2) == 0x2;
        }
        
        public ColumnIdentifier getIdentifier() {
            return (this.identifier_ == null) ? ColumnIdentifier.getDefaultInstance() : this.identifier_;
        }
        
        public ColumnIdentifierOrBuilder getIdentifierOrBuilder() {
            return (this.identifier_ == null) ? ColumnIdentifier.getDefaultInstance() : this.identifier_;
        }
        
        public boolean hasVariable() {
            return (this.bitField0_ & 0x4) == 0x4;
        }
        
        public String getVariable() {
            final Object ref = this.variable_;
            if (ref instanceof String) {
                return (String)ref;
            }
            final ByteString bs = (ByteString)ref;
            final String s = bs.toStringUtf8();
            if (bs.isValidUtf8()) {
                this.variable_ = s;
            }
            return s;
        }
        
        public ByteString getVariableBytes() {
            final Object ref = this.variable_;
            if (ref instanceof String) {
                final ByteString b = ByteString.copyFromUtf8((String)ref);
                return (ByteString)(this.variable_ = b);
            }
            return (ByteString)ref;
        }
        
        public boolean hasLiteral() {
            return (this.bitField0_ & 0x8) == 0x8;
        }
        
        public MysqlxDatatypes.Scalar getLiteral() {
            return (this.literal_ == null) ? MysqlxDatatypes.Scalar.getDefaultInstance() : this.literal_;
        }
        
        public MysqlxDatatypes.ScalarOrBuilder getLiteralOrBuilder() {
            return (this.literal_ == null) ? MysqlxDatatypes.Scalar.getDefaultInstance() : this.literal_;
        }
        
        public boolean hasFunctionCall() {
            return (this.bitField0_ & 0x10) == 0x10;
        }
        
        public FunctionCall getFunctionCall() {
            return (this.functionCall_ == null) ? FunctionCall.getDefaultInstance() : this.functionCall_;
        }
        
        public FunctionCallOrBuilder getFunctionCallOrBuilder() {
            return (this.functionCall_ == null) ? FunctionCall.getDefaultInstance() : this.functionCall_;
        }
        
        public boolean hasOperator() {
            return (this.bitField0_ & 0x20) == 0x20;
        }
        
        public Operator getOperator() {
            return (this.operator_ == null) ? Operator.getDefaultInstance() : this.operator_;
        }
        
        public OperatorOrBuilder getOperatorOrBuilder() {
            return (this.operator_ == null) ? Operator.getDefaultInstance() : this.operator_;
        }
        
        public boolean hasPosition() {
            return (this.bitField0_ & 0x40) == 0x40;
        }
        
        public int getPosition() {
            return this.position_;
        }
        
        public boolean hasObject() {
            return (this.bitField0_ & 0x80) == 0x80;
        }
        
        public MysqlxExpr.Object getObject() {
            return (this.object_ == null) ? MysqlxExpr.Object.getDefaultInstance() : this.object_;
        }
        
        public ObjectOrBuilder getObjectOrBuilder() {
            return (this.object_ == null) ? MysqlxExpr.Object.getDefaultInstance() : this.object_;
        }
        
        public boolean hasArray() {
            return (this.bitField0_ & 0x100) == 0x100;
        }
        
        public Array getArray() {
            return (this.array_ == null) ? Array.getDefaultInstance() : this.array_;
        }
        
        public ArrayOrBuilder getArrayOrBuilder() {
            return (this.array_ == null) ? Array.getDefaultInstance() : this.array_;
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
            if (this.hasIdentifier() && !this.getIdentifier().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            if (this.hasLiteral() && !this.getLiteral().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            if (this.hasFunctionCall() && !this.getFunctionCall().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            if (this.hasOperator() && !this.getOperator().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            if (this.hasObject() && !this.getObject().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            if (this.hasArray() && !this.getArray().isInitialized()) {
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
                output.writeMessage(2, (MessageLite)this.getIdentifier());
            }
            if ((this.bitField0_ & 0x4) == 0x4) {
                GeneratedMessageV3.writeString(output, 3, this.variable_);
            }
            if ((this.bitField0_ & 0x8) == 0x8) {
                output.writeMessage(4, (MessageLite)this.getLiteral());
            }
            if ((this.bitField0_ & 0x10) == 0x10) {
                output.writeMessage(5, (MessageLite)this.getFunctionCall());
            }
            if ((this.bitField0_ & 0x20) == 0x20) {
                output.writeMessage(6, (MessageLite)this.getOperator());
            }
            if ((this.bitField0_ & 0x40) == 0x40) {
                output.writeUInt32(7, this.position_);
            }
            if ((this.bitField0_ & 0x80) == 0x80) {
                output.writeMessage(8, (MessageLite)this.getObject());
            }
            if ((this.bitField0_ & 0x100) == 0x100) {
                output.writeMessage(9, (MessageLite)this.getArray());
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
                size += CodedOutputStream.computeMessageSize(2, (MessageLite)this.getIdentifier());
            }
            if ((this.bitField0_ & 0x4) == 0x4) {
                size += GeneratedMessageV3.computeStringSize(3, this.variable_);
            }
            if ((this.bitField0_ & 0x8) == 0x8) {
                size += CodedOutputStream.computeMessageSize(4, (MessageLite)this.getLiteral());
            }
            if ((this.bitField0_ & 0x10) == 0x10) {
                size += CodedOutputStream.computeMessageSize(5, (MessageLite)this.getFunctionCall());
            }
            if ((this.bitField0_ & 0x20) == 0x20) {
                size += CodedOutputStream.computeMessageSize(6, (MessageLite)this.getOperator());
            }
            if ((this.bitField0_ & 0x40) == 0x40) {
                size += CodedOutputStream.computeUInt32Size(7, this.position_);
            }
            if ((this.bitField0_ & 0x80) == 0x80) {
                size += CodedOutputStream.computeMessageSize(8, (MessageLite)this.getObject());
            }
            if ((this.bitField0_ & 0x100) == 0x100) {
                size += CodedOutputStream.computeMessageSize(9, (MessageLite)this.getArray());
            }
            size += this.unknownFields.getSerializedSize();
            return this.memoizedSize = size;
        }
        
        public boolean equals(final Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Expr)) {
                return super.equals(obj);
            }
            final Expr other = (Expr)obj;
            boolean result = true;
            result = (result && this.hasType() == other.hasType());
            if (this.hasType()) {
                result = (result && this.type_ == other.type_);
            }
            result = (result && this.hasIdentifier() == other.hasIdentifier());
            if (this.hasIdentifier()) {
                result = (result && this.getIdentifier().equals(other.getIdentifier()));
            }
            result = (result && this.hasVariable() == other.hasVariable());
            if (this.hasVariable()) {
                result = (result && this.getVariable().equals(other.getVariable()));
            }
            result = (result && this.hasLiteral() == other.hasLiteral());
            if (this.hasLiteral()) {
                result = (result && this.getLiteral().equals(other.getLiteral()));
            }
            result = (result && this.hasFunctionCall() == other.hasFunctionCall());
            if (this.hasFunctionCall()) {
                result = (result && this.getFunctionCall().equals(other.getFunctionCall()));
            }
            result = (result && this.hasOperator() == other.hasOperator());
            if (this.hasOperator()) {
                result = (result && this.getOperator().equals(other.getOperator()));
            }
            result = (result && this.hasPosition() == other.hasPosition());
            if (this.hasPosition()) {
                result = (result && this.getPosition() == other.getPosition());
            }
            result = (result && this.hasObject() == other.hasObject());
            if (this.hasObject()) {
                result = (result && this.getObject().equals(other.getObject()));
            }
            result = (result && this.hasArray() == other.hasArray());
            if (this.hasArray()) {
                result = (result && this.getArray().equals(other.getArray()));
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
            if (this.hasIdentifier()) {
                hash = 37 * hash + 2;
                hash = 53 * hash + this.getIdentifier().hashCode();
            }
            if (this.hasVariable()) {
                hash = 37 * hash + 3;
                hash = 53 * hash + this.getVariable().hashCode();
            }
            if (this.hasLiteral()) {
                hash = 37 * hash + 4;
                hash = 53 * hash + this.getLiteral().hashCode();
            }
            if (this.hasFunctionCall()) {
                hash = 37 * hash + 5;
                hash = 53 * hash + this.getFunctionCall().hashCode();
            }
            if (this.hasOperator()) {
                hash = 37 * hash + 6;
                hash = 53 * hash + this.getOperator().hashCode();
            }
            if (this.hasPosition()) {
                hash = 37 * hash + 7;
                hash = 53 * hash + this.getPosition();
            }
            if (this.hasObject()) {
                hash = 37 * hash + 8;
                hash = 53 * hash + this.getObject().hashCode();
            }
            if (this.hasArray()) {
                hash = 37 * hash + 9;
                hash = 53 * hash + this.getArray().hashCode();
            }
            hash = 29 * hash + this.unknownFields.hashCode();
            return this.memoizedHashCode = hash;
        }
        
        public static Expr parseFrom(final ByteBuffer data) throws InvalidProtocolBufferException {
            return (Expr)Expr.PARSER.parseFrom(data);
        }
        
        public static Expr parseFrom(final ByteBuffer data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Expr)Expr.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Expr parseFrom(final ByteString data) throws InvalidProtocolBufferException {
            return (Expr)Expr.PARSER.parseFrom(data);
        }
        
        public static Expr parseFrom(final ByteString data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Expr)Expr.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Expr parseFrom(final byte[] data) throws InvalidProtocolBufferException {
            return (Expr)Expr.PARSER.parseFrom(data);
        }
        
        public static Expr parseFrom(final byte[] data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Expr)Expr.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Expr parseFrom(final InputStream input) throws IOException {
            return (Expr)GeneratedMessageV3.parseWithIOException((Parser)Expr.PARSER, input);
        }
        
        public static Expr parseFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Expr)GeneratedMessageV3.parseWithIOException((Parser)Expr.PARSER, input, extensionRegistry);
        }
        
        public static Expr parseDelimitedFrom(final InputStream input) throws IOException {
            return (Expr)GeneratedMessageV3.parseDelimitedWithIOException((Parser)Expr.PARSER, input);
        }
        
        public static Expr parseDelimitedFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Expr)GeneratedMessageV3.parseDelimitedWithIOException((Parser)Expr.PARSER, input, extensionRegistry);
        }
        
        public static Expr parseFrom(final CodedInputStream input) throws IOException {
            return (Expr)GeneratedMessageV3.parseWithIOException((Parser)Expr.PARSER, input);
        }
        
        public static Expr parseFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Expr)GeneratedMessageV3.parseWithIOException((Parser)Expr.PARSER, input, extensionRegistry);
        }
        
        public Builder newBuilderForType() {
            return newBuilder();
        }
        
        public static Builder newBuilder() {
            return Expr.DEFAULT_INSTANCE.toBuilder();
        }
        
        public static Builder newBuilder(final Expr prototype) {
            return Expr.DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }
        
        public Builder toBuilder() {
            return (this == Expr.DEFAULT_INSTANCE) ? new Builder() : new Builder().mergeFrom(this);
        }
        
        protected Builder newBuilderForType(final GeneratedMessageV3.BuilderParent parent) {
            final Builder builder = new Builder(parent);
            return builder;
        }
        
        public static Expr getDefaultInstance() {
            return Expr.DEFAULT_INSTANCE;
        }
        
        public static Parser<Expr> parser() {
            return Expr.PARSER;
        }
        
        public Parser<Expr> getParserForType() {
            return Expr.PARSER;
        }
        
        public Expr getDefaultInstanceForType() {
            return Expr.DEFAULT_INSTANCE;
        }
        
        static {
            DEFAULT_INSTANCE = new Expr();
            PARSER = (Parser)new AbstractParser<Expr>() {
                public Expr parsePartialFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new Expr(input, extensionRegistry);
                }
            };
        }
        
        public enum Type implements ProtocolMessageEnum
        {
            IDENT(1), 
            LITERAL(2), 
            VARIABLE(3), 
            FUNC_CALL(4), 
            OPERATOR(5), 
            PLACEHOLDER(6), 
            OBJECT(7), 
            ARRAY(8);
            
            public static final int IDENT_VALUE = 1;
            public static final int LITERAL_VALUE = 2;
            public static final int VARIABLE_VALUE = 3;
            public static final int FUNC_CALL_VALUE = 4;
            public static final int OPERATOR_VALUE = 5;
            public static final int PLACEHOLDER_VALUE = 6;
            public static final int OBJECT_VALUE = 7;
            public static final int ARRAY_VALUE = 8;
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
                        return Type.IDENT;
                    }
                    case 2: {
                        return Type.LITERAL;
                    }
                    case 3: {
                        return Type.VARIABLE;
                    }
                    case 4: {
                        return Type.FUNC_CALL;
                    }
                    case 5: {
                        return Type.OPERATOR;
                    }
                    case 6: {
                        return Type.PLACEHOLDER;
                    }
                    case 7: {
                        return Type.OBJECT;
                    }
                    case 8: {
                        return Type.ARRAY;
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
                return Expr.getDescriptor().getEnumTypes().get(0);
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
        
        public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements ExprOrBuilder
        {
            private int bitField0_;
            private int type_;
            private ColumnIdentifier identifier_;
            private SingleFieldBuilderV3<ColumnIdentifier, ColumnIdentifier.Builder, ColumnIdentifierOrBuilder> identifierBuilder_;
            private Object variable_;
            private MysqlxDatatypes.Scalar literal_;
            private SingleFieldBuilderV3<MysqlxDatatypes.Scalar, MysqlxDatatypes.Scalar.Builder, MysqlxDatatypes.ScalarOrBuilder> literalBuilder_;
            private FunctionCall functionCall_;
            private SingleFieldBuilderV3<FunctionCall, FunctionCall.Builder, FunctionCallOrBuilder> functionCallBuilder_;
            private Operator operator_;
            private SingleFieldBuilderV3<Operator, Operator.Builder, OperatorOrBuilder> operatorBuilder_;
            private int position_;
            private MysqlxExpr.Object object_;
            private SingleFieldBuilderV3<MysqlxExpr.Object, MysqlxExpr.Object.Builder, ObjectOrBuilder> objectBuilder_;
            private Array array_;
            private SingleFieldBuilderV3<Array, Array.Builder, ArrayOrBuilder> arrayBuilder_;
            
            public static final Descriptors.Descriptor getDescriptor() {
                return MysqlxExpr.internal_static_Mysqlx_Expr_Expr_descriptor;
            }
            
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return MysqlxExpr.internal_static_Mysqlx_Expr_Expr_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)Expr.class, (Class)Builder.class);
            }
            
            private Builder() {
                this.type_ = 1;
                this.identifier_ = null;
                this.variable_ = "";
                this.literal_ = null;
                this.functionCall_ = null;
                this.operator_ = null;
                this.object_ = null;
                this.array_ = null;
                this.maybeForceBuilderInitialization();
            }
            
            private Builder(final GeneratedMessageV3.BuilderParent parent) {
                super(parent);
                this.type_ = 1;
                this.identifier_ = null;
                this.variable_ = "";
                this.literal_ = null;
                this.functionCall_ = null;
                this.operator_ = null;
                this.object_ = null;
                this.array_ = null;
                this.maybeForceBuilderInitialization();
            }
            
            private void maybeForceBuilderInitialization() {
                if (Expr.alwaysUseFieldBuilders) {
                    this.getIdentifierFieldBuilder();
                    this.getLiteralFieldBuilder();
                    this.getFunctionCallFieldBuilder();
                    this.getOperatorFieldBuilder();
                    this.getObjectFieldBuilder();
                    this.getArrayFieldBuilder();
                }
            }
            
            public Builder clear() {
                super.clear();
                this.type_ = 1;
                this.bitField0_ &= 0xFFFFFFFE;
                if (this.identifierBuilder_ == null) {
                    this.identifier_ = null;
                }
                else {
                    this.identifierBuilder_.clear();
                }
                this.bitField0_ &= 0xFFFFFFFD;
                this.variable_ = "";
                this.bitField0_ &= 0xFFFFFFFB;
                if (this.literalBuilder_ == null) {
                    this.literal_ = null;
                }
                else {
                    this.literalBuilder_.clear();
                }
                this.bitField0_ &= 0xFFFFFFF7;
                if (this.functionCallBuilder_ == null) {
                    this.functionCall_ = null;
                }
                else {
                    this.functionCallBuilder_.clear();
                }
                this.bitField0_ &= 0xFFFFFFEF;
                if (this.operatorBuilder_ == null) {
                    this.operator_ = null;
                }
                else {
                    this.operatorBuilder_.clear();
                }
                this.bitField0_ &= 0xFFFFFFDF;
                this.position_ = 0;
                this.bitField0_ &= 0xFFFFFFBF;
                if (this.objectBuilder_ == null) {
                    this.object_ = null;
                }
                else {
                    this.objectBuilder_.clear();
                }
                this.bitField0_ &= 0xFFFFFF7F;
                if (this.arrayBuilder_ == null) {
                    this.array_ = null;
                }
                else {
                    this.arrayBuilder_.clear();
                }
                this.bitField0_ &= 0xFFFFFEFF;
                return this;
            }
            
            public Descriptors.Descriptor getDescriptorForType() {
                return MysqlxExpr.internal_static_Mysqlx_Expr_Expr_descriptor;
            }
            
            public Expr getDefaultInstanceForType() {
                return Expr.getDefaultInstance();
            }
            
            public Expr build() {
                final Expr result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw newUninitializedMessageException((Message)result);
                }
                return result;
            }
            
            public Expr buildPartial() {
                final Expr result = new Expr((GeneratedMessageV3.Builder)this);
                final int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 0x1) == 0x1) {
                    to_bitField0_ |= 0x1;
                }
                result.type_ = this.type_;
                if ((from_bitField0_ & 0x2) == 0x2) {
                    to_bitField0_ |= 0x2;
                }
                if (this.identifierBuilder_ == null) {
                    result.identifier_ = this.identifier_;
                }
                else {
                    result.identifier_ = (ColumnIdentifier)this.identifierBuilder_.build();
                }
                if ((from_bitField0_ & 0x4) == 0x4) {
                    to_bitField0_ |= 0x4;
                }
                result.variable_ = this.variable_;
                if ((from_bitField0_ & 0x8) == 0x8) {
                    to_bitField0_ |= 0x8;
                }
                if (this.literalBuilder_ == null) {
                    result.literal_ = this.literal_;
                }
                else {
                    result.literal_ = (MysqlxDatatypes.Scalar)this.literalBuilder_.build();
                }
                if ((from_bitField0_ & 0x10) == 0x10) {
                    to_bitField0_ |= 0x10;
                }
                if (this.functionCallBuilder_ == null) {
                    result.functionCall_ = this.functionCall_;
                }
                else {
                    result.functionCall_ = (FunctionCall)this.functionCallBuilder_.build();
                }
                if ((from_bitField0_ & 0x20) == 0x20) {
                    to_bitField0_ |= 0x20;
                }
                if (this.operatorBuilder_ == null) {
                    result.operator_ = this.operator_;
                }
                else {
                    result.operator_ = (Operator)this.operatorBuilder_.build();
                }
                if ((from_bitField0_ & 0x40) == 0x40) {
                    to_bitField0_ |= 0x40;
                }
                result.position_ = this.position_;
                if ((from_bitField0_ & 0x80) == 0x80) {
                    to_bitField0_ |= 0x80;
                }
                if (this.objectBuilder_ == null) {
                    result.object_ = this.object_;
                }
                else {
                    result.object_ = (MysqlxExpr.Object)this.objectBuilder_.build();
                }
                if ((from_bitField0_ & 0x100) == 0x100) {
                    to_bitField0_ |= 0x100;
                }
                if (this.arrayBuilder_ == null) {
                    result.array_ = this.array_;
                }
                else {
                    result.array_ = (Array)this.arrayBuilder_.build();
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
                if (other instanceof Expr) {
                    return this.mergeFrom((Expr)other);
                }
                super.mergeFrom(other);
                return this;
            }
            
            public Builder mergeFrom(final Expr other) {
                if (other == Expr.getDefaultInstance()) {
                    return this;
                }
                if (other.hasType()) {
                    this.setType(other.getType());
                }
                if (other.hasIdentifier()) {
                    this.mergeIdentifier(other.getIdentifier());
                }
                if (other.hasVariable()) {
                    this.bitField0_ |= 0x4;
                    this.variable_ = other.variable_;
                    this.onChanged();
                }
                if (other.hasLiteral()) {
                    this.mergeLiteral(other.getLiteral());
                }
                if (other.hasFunctionCall()) {
                    this.mergeFunctionCall(other.getFunctionCall());
                }
                if (other.hasOperator()) {
                    this.mergeOperator(other.getOperator());
                }
                if (other.hasPosition()) {
                    this.setPosition(other.getPosition());
                }
                if (other.hasObject()) {
                    this.mergeObject(other.getObject());
                }
                if (other.hasArray()) {
                    this.mergeArray(other.getArray());
                }
                this.mergeUnknownFields(other.unknownFields);
                this.onChanged();
                return this;
            }
            
            public final boolean isInitialized() {
                return this.hasType() && (!this.hasIdentifier() || this.getIdentifier().isInitialized()) && (!this.hasLiteral() || this.getLiteral().isInitialized()) && (!this.hasFunctionCall() || this.getFunctionCall().isInitialized()) && (!this.hasOperator() || this.getOperator().isInitialized()) && (!this.hasObject() || this.getObject().isInitialized()) && (!this.hasArray() || this.getArray().isInitialized());
            }
            
            public Builder mergeFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
                Expr parsedMessage = null;
                try {
                    parsedMessage = (Expr)Expr.PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (Expr)e.getUnfinishedMessage();
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
            
            public Type getType() {
                final Type result = Type.valueOf(this.type_);
                return (result == null) ? Type.IDENT : result;
            }
            
            public Builder setType(final Type value) {
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
            
            public boolean hasIdentifier() {
                return (this.bitField0_ & 0x2) == 0x2;
            }
            
            public ColumnIdentifier getIdentifier() {
                if (this.identifierBuilder_ == null) {
                    return (this.identifier_ == null) ? ColumnIdentifier.getDefaultInstance() : this.identifier_;
                }
                return (ColumnIdentifier)this.identifierBuilder_.getMessage();
            }
            
            public Builder setIdentifier(final ColumnIdentifier value) {
                if (this.identifierBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.identifier_ = value;
                    this.onChanged();
                }
                else {
                    this.identifierBuilder_.setMessage((AbstractMessage)value);
                }
                this.bitField0_ |= 0x2;
                return this;
            }
            
            public Builder setIdentifier(final ColumnIdentifier.Builder builderForValue) {
                if (this.identifierBuilder_ == null) {
                    this.identifier_ = builderForValue.build();
                    this.onChanged();
                }
                else {
                    this.identifierBuilder_.setMessage((AbstractMessage)builderForValue.build());
                }
                this.bitField0_ |= 0x2;
                return this;
            }
            
            public Builder mergeIdentifier(final ColumnIdentifier value) {
                if (this.identifierBuilder_ == null) {
                    if ((this.bitField0_ & 0x2) == 0x2 && this.identifier_ != null && this.identifier_ != ColumnIdentifier.getDefaultInstance()) {
                        this.identifier_ = ColumnIdentifier.newBuilder(this.identifier_).mergeFrom(value).buildPartial();
                    }
                    else {
                        this.identifier_ = value;
                    }
                    this.onChanged();
                }
                else {
                    this.identifierBuilder_.mergeFrom((AbstractMessage)value);
                }
                this.bitField0_ |= 0x2;
                return this;
            }
            
            public Builder clearIdentifier() {
                if (this.identifierBuilder_ == null) {
                    this.identifier_ = null;
                    this.onChanged();
                }
                else {
                    this.identifierBuilder_.clear();
                }
                this.bitField0_ &= 0xFFFFFFFD;
                return this;
            }
            
            public ColumnIdentifier.Builder getIdentifierBuilder() {
                this.bitField0_ |= 0x2;
                this.onChanged();
                return (ColumnIdentifier.Builder)this.getIdentifierFieldBuilder().getBuilder();
            }
            
            public ColumnIdentifierOrBuilder getIdentifierOrBuilder() {
                if (this.identifierBuilder_ != null) {
                    return (ColumnIdentifierOrBuilder)this.identifierBuilder_.getMessageOrBuilder();
                }
                return (this.identifier_ == null) ? ColumnIdentifier.getDefaultInstance() : this.identifier_;
            }
            
            private SingleFieldBuilderV3<ColumnIdentifier, ColumnIdentifier.Builder, ColumnIdentifierOrBuilder> getIdentifierFieldBuilder() {
                if (this.identifierBuilder_ == null) {
                    this.identifierBuilder_ = (SingleFieldBuilderV3<ColumnIdentifier, ColumnIdentifier.Builder, ColumnIdentifierOrBuilder>)new SingleFieldBuilderV3((AbstractMessage)this.getIdentifier(), (AbstractMessage.BuilderParent)this.getParentForChildren(), this.isClean());
                    this.identifier_ = null;
                }
                return this.identifierBuilder_;
            }
            
            public boolean hasVariable() {
                return (this.bitField0_ & 0x4) == 0x4;
            }
            
            public String getVariable() {
                final Object ref = this.variable_;
                if (!(ref instanceof String)) {
                    final ByteString bs = (ByteString)ref;
                    final String s = bs.toStringUtf8();
                    if (bs.isValidUtf8()) {
                        this.variable_ = s;
                    }
                    return s;
                }
                return (String)ref;
            }
            
            public ByteString getVariableBytes() {
                final Object ref = this.variable_;
                if (ref instanceof String) {
                    final ByteString b = ByteString.copyFromUtf8((String)ref);
                    return (ByteString)(this.variable_ = b);
                }
                return (ByteString)ref;
            }
            
            public Builder setVariable(final String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 0x4;
                this.variable_ = value;
                this.onChanged();
                return this;
            }
            
            public Builder clearVariable() {
                this.bitField0_ &= 0xFFFFFFFB;
                this.variable_ = Expr.getDefaultInstance().getVariable();
                this.onChanged();
                return this;
            }
            
            public Builder setVariableBytes(final ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 0x4;
                this.variable_ = value;
                this.onChanged();
                return this;
            }
            
            public boolean hasLiteral() {
                return (this.bitField0_ & 0x8) == 0x8;
            }
            
            public MysqlxDatatypes.Scalar getLiteral() {
                if (this.literalBuilder_ == null) {
                    return (this.literal_ == null) ? MysqlxDatatypes.Scalar.getDefaultInstance() : this.literal_;
                }
                return (MysqlxDatatypes.Scalar)this.literalBuilder_.getMessage();
            }
            
            public Builder setLiteral(final MysqlxDatatypes.Scalar value) {
                if (this.literalBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.literal_ = value;
                    this.onChanged();
                }
                else {
                    this.literalBuilder_.setMessage((AbstractMessage)value);
                }
                this.bitField0_ |= 0x8;
                return this;
            }
            
            public Builder setLiteral(final MysqlxDatatypes.Scalar.Builder builderForValue) {
                if (this.literalBuilder_ == null) {
                    this.literal_ = builderForValue.build();
                    this.onChanged();
                }
                else {
                    this.literalBuilder_.setMessage((AbstractMessage)builderForValue.build());
                }
                this.bitField0_ |= 0x8;
                return this;
            }
            
            public Builder mergeLiteral(final MysqlxDatatypes.Scalar value) {
                if (this.literalBuilder_ == null) {
                    if ((this.bitField0_ & 0x8) == 0x8 && this.literal_ != null && this.literal_ != MysqlxDatatypes.Scalar.getDefaultInstance()) {
                        this.literal_ = MysqlxDatatypes.Scalar.newBuilder(this.literal_).mergeFrom(value).buildPartial();
                    }
                    else {
                        this.literal_ = value;
                    }
                    this.onChanged();
                }
                else {
                    this.literalBuilder_.mergeFrom((AbstractMessage)value);
                }
                this.bitField0_ |= 0x8;
                return this;
            }
            
            public Builder clearLiteral() {
                if (this.literalBuilder_ == null) {
                    this.literal_ = null;
                    this.onChanged();
                }
                else {
                    this.literalBuilder_.clear();
                }
                this.bitField0_ &= 0xFFFFFFF7;
                return this;
            }
            
            public MysqlxDatatypes.Scalar.Builder getLiteralBuilder() {
                this.bitField0_ |= 0x8;
                this.onChanged();
                return (MysqlxDatatypes.Scalar.Builder)this.getLiteralFieldBuilder().getBuilder();
            }
            
            public MysqlxDatatypes.ScalarOrBuilder getLiteralOrBuilder() {
                if (this.literalBuilder_ != null) {
                    return (MysqlxDatatypes.ScalarOrBuilder)this.literalBuilder_.getMessageOrBuilder();
                }
                return (this.literal_ == null) ? MysqlxDatatypes.Scalar.getDefaultInstance() : this.literal_;
            }
            
            private SingleFieldBuilderV3<MysqlxDatatypes.Scalar, MysqlxDatatypes.Scalar.Builder, MysqlxDatatypes.ScalarOrBuilder> getLiteralFieldBuilder() {
                if (this.literalBuilder_ == null) {
                    this.literalBuilder_ = (SingleFieldBuilderV3<MysqlxDatatypes.Scalar, MysqlxDatatypes.Scalar.Builder, MysqlxDatatypes.ScalarOrBuilder>)new SingleFieldBuilderV3((AbstractMessage)this.getLiteral(), (AbstractMessage.BuilderParent)this.getParentForChildren(), this.isClean());
                    this.literal_ = null;
                }
                return this.literalBuilder_;
            }
            
            public boolean hasFunctionCall() {
                return (this.bitField0_ & 0x10) == 0x10;
            }
            
            public FunctionCall getFunctionCall() {
                if (this.functionCallBuilder_ == null) {
                    return (this.functionCall_ == null) ? FunctionCall.getDefaultInstance() : this.functionCall_;
                }
                return (FunctionCall)this.functionCallBuilder_.getMessage();
            }
            
            public Builder setFunctionCall(final FunctionCall value) {
                if (this.functionCallBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.functionCall_ = value;
                    this.onChanged();
                }
                else {
                    this.functionCallBuilder_.setMessage((AbstractMessage)value);
                }
                this.bitField0_ |= 0x10;
                return this;
            }
            
            public Builder setFunctionCall(final FunctionCall.Builder builderForValue) {
                if (this.functionCallBuilder_ == null) {
                    this.functionCall_ = builderForValue.build();
                    this.onChanged();
                }
                else {
                    this.functionCallBuilder_.setMessage((AbstractMessage)builderForValue.build());
                }
                this.bitField0_ |= 0x10;
                return this;
            }
            
            public Builder mergeFunctionCall(final FunctionCall value) {
                if (this.functionCallBuilder_ == null) {
                    if ((this.bitField0_ & 0x10) == 0x10 && this.functionCall_ != null && this.functionCall_ != FunctionCall.getDefaultInstance()) {
                        this.functionCall_ = FunctionCall.newBuilder(this.functionCall_).mergeFrom(value).buildPartial();
                    }
                    else {
                        this.functionCall_ = value;
                    }
                    this.onChanged();
                }
                else {
                    this.functionCallBuilder_.mergeFrom((AbstractMessage)value);
                }
                this.bitField0_ |= 0x10;
                return this;
            }
            
            public Builder clearFunctionCall() {
                if (this.functionCallBuilder_ == null) {
                    this.functionCall_ = null;
                    this.onChanged();
                }
                else {
                    this.functionCallBuilder_.clear();
                }
                this.bitField0_ &= 0xFFFFFFEF;
                return this;
            }
            
            public FunctionCall.Builder getFunctionCallBuilder() {
                this.bitField0_ |= 0x10;
                this.onChanged();
                return (FunctionCall.Builder)this.getFunctionCallFieldBuilder().getBuilder();
            }
            
            public FunctionCallOrBuilder getFunctionCallOrBuilder() {
                if (this.functionCallBuilder_ != null) {
                    return (FunctionCallOrBuilder)this.functionCallBuilder_.getMessageOrBuilder();
                }
                return (this.functionCall_ == null) ? FunctionCall.getDefaultInstance() : this.functionCall_;
            }
            
            private SingleFieldBuilderV3<FunctionCall, FunctionCall.Builder, FunctionCallOrBuilder> getFunctionCallFieldBuilder() {
                if (this.functionCallBuilder_ == null) {
                    this.functionCallBuilder_ = (SingleFieldBuilderV3<FunctionCall, FunctionCall.Builder, FunctionCallOrBuilder>)new SingleFieldBuilderV3((AbstractMessage)this.getFunctionCall(), (AbstractMessage.BuilderParent)this.getParentForChildren(), this.isClean());
                    this.functionCall_ = null;
                }
                return this.functionCallBuilder_;
            }
            
            public boolean hasOperator() {
                return (this.bitField0_ & 0x20) == 0x20;
            }
            
            public Operator getOperator() {
                if (this.operatorBuilder_ == null) {
                    return (this.operator_ == null) ? Operator.getDefaultInstance() : this.operator_;
                }
                return (Operator)this.operatorBuilder_.getMessage();
            }
            
            public Builder setOperator(final Operator value) {
                if (this.operatorBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.operator_ = value;
                    this.onChanged();
                }
                else {
                    this.operatorBuilder_.setMessage((AbstractMessage)value);
                }
                this.bitField0_ |= 0x20;
                return this;
            }
            
            public Builder setOperator(final Operator.Builder builderForValue) {
                if (this.operatorBuilder_ == null) {
                    this.operator_ = builderForValue.build();
                    this.onChanged();
                }
                else {
                    this.operatorBuilder_.setMessage((AbstractMessage)builderForValue.build());
                }
                this.bitField0_ |= 0x20;
                return this;
            }
            
            public Builder mergeOperator(final Operator value) {
                if (this.operatorBuilder_ == null) {
                    if ((this.bitField0_ & 0x20) == 0x20 && this.operator_ != null && this.operator_ != Operator.getDefaultInstance()) {
                        this.operator_ = Operator.newBuilder(this.operator_).mergeFrom(value).buildPartial();
                    }
                    else {
                        this.operator_ = value;
                    }
                    this.onChanged();
                }
                else {
                    this.operatorBuilder_.mergeFrom((AbstractMessage)value);
                }
                this.bitField0_ |= 0x20;
                return this;
            }
            
            public Builder clearOperator() {
                if (this.operatorBuilder_ == null) {
                    this.operator_ = null;
                    this.onChanged();
                }
                else {
                    this.operatorBuilder_.clear();
                }
                this.bitField0_ &= 0xFFFFFFDF;
                return this;
            }
            
            public Operator.Builder getOperatorBuilder() {
                this.bitField0_ |= 0x20;
                this.onChanged();
                return (Operator.Builder)this.getOperatorFieldBuilder().getBuilder();
            }
            
            public OperatorOrBuilder getOperatorOrBuilder() {
                if (this.operatorBuilder_ != null) {
                    return (OperatorOrBuilder)this.operatorBuilder_.getMessageOrBuilder();
                }
                return (this.operator_ == null) ? Operator.getDefaultInstance() : this.operator_;
            }
            
            private SingleFieldBuilderV3<Operator, Operator.Builder, OperatorOrBuilder> getOperatorFieldBuilder() {
                if (this.operatorBuilder_ == null) {
                    this.operatorBuilder_ = (SingleFieldBuilderV3<Operator, Operator.Builder, OperatorOrBuilder>)new SingleFieldBuilderV3((AbstractMessage)this.getOperator(), (AbstractMessage.BuilderParent)this.getParentForChildren(), this.isClean());
                    this.operator_ = null;
                }
                return this.operatorBuilder_;
            }
            
            public boolean hasPosition() {
                return (this.bitField0_ & 0x40) == 0x40;
            }
            
            public int getPosition() {
                return this.position_;
            }
            
            public Builder setPosition(final int value) {
                this.bitField0_ |= 0x40;
                this.position_ = value;
                this.onChanged();
                return this;
            }
            
            public Builder clearPosition() {
                this.bitField0_ &= 0xFFFFFFBF;
                this.position_ = 0;
                this.onChanged();
                return this;
            }
            
            public boolean hasObject() {
                return (this.bitField0_ & 0x80) == 0x80;
            }
            
            public MysqlxExpr.Object getObject() {
                if (this.objectBuilder_ == null) {
                    return (this.object_ == null) ? MysqlxExpr.Object.getDefaultInstance() : this.object_;
                }
                return (MysqlxExpr.Object)this.objectBuilder_.getMessage();
            }
            
            public Builder setObject(final MysqlxExpr.Object value) {
                if (this.objectBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.object_ = value;
                    this.onChanged();
                }
                else {
                    this.objectBuilder_.setMessage((AbstractMessage)value);
                }
                this.bitField0_ |= 0x80;
                return this;
            }
            
            public Builder setObject(final MysqlxExpr.Object.Builder builderForValue) {
                if (this.objectBuilder_ == null) {
                    this.object_ = builderForValue.build();
                    this.onChanged();
                }
                else {
                    this.objectBuilder_.setMessage((AbstractMessage)builderForValue.build());
                }
                this.bitField0_ |= 0x80;
                return this;
            }
            
            public Builder mergeObject(final MysqlxExpr.Object value) {
                if (this.objectBuilder_ == null) {
                    if ((this.bitField0_ & 0x80) == 0x80 && this.object_ != null && this.object_ != MysqlxExpr.Object.getDefaultInstance()) {
                        this.object_ = MysqlxExpr.Object.newBuilder(this.object_).mergeFrom(value).buildPartial();
                    }
                    else {
                        this.object_ = value;
                    }
                    this.onChanged();
                }
                else {
                    this.objectBuilder_.mergeFrom((AbstractMessage)value);
                }
                this.bitField0_ |= 0x80;
                return this;
            }
            
            public Builder clearObject() {
                if (this.objectBuilder_ == null) {
                    this.object_ = null;
                    this.onChanged();
                }
                else {
                    this.objectBuilder_.clear();
                }
                this.bitField0_ &= 0xFFFFFF7F;
                return this;
            }
            
            public MysqlxExpr.Object.Builder getObjectBuilder() {
                this.bitField0_ |= 0x80;
                this.onChanged();
                return (MysqlxExpr.Object.Builder)this.getObjectFieldBuilder().getBuilder();
            }
            
            public ObjectOrBuilder getObjectOrBuilder() {
                if (this.objectBuilder_ != null) {
                    return (ObjectOrBuilder)this.objectBuilder_.getMessageOrBuilder();
                }
                return (this.object_ == null) ? MysqlxExpr.Object.getDefaultInstance() : this.object_;
            }
            
            private SingleFieldBuilderV3<MysqlxExpr.Object, MysqlxExpr.Object.Builder, ObjectOrBuilder> getObjectFieldBuilder() {
                if (this.objectBuilder_ == null) {
                    this.objectBuilder_ = (SingleFieldBuilderV3<MysqlxExpr.Object, MysqlxExpr.Object.Builder, ObjectOrBuilder>)new SingleFieldBuilderV3((AbstractMessage)this.getObject(), (AbstractMessage.BuilderParent)this.getParentForChildren(), this.isClean());
                    this.object_ = null;
                }
                return this.objectBuilder_;
            }
            
            public boolean hasArray() {
                return (this.bitField0_ & 0x100) == 0x100;
            }
            
            public Array getArray() {
                if (this.arrayBuilder_ == null) {
                    return (this.array_ == null) ? Array.getDefaultInstance() : this.array_;
                }
                return (Array)this.arrayBuilder_.getMessage();
            }
            
            public Builder setArray(final Array value) {
                if (this.arrayBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.array_ = value;
                    this.onChanged();
                }
                else {
                    this.arrayBuilder_.setMessage((AbstractMessage)value);
                }
                this.bitField0_ |= 0x100;
                return this;
            }
            
            public Builder setArray(final Array.Builder builderForValue) {
                if (this.arrayBuilder_ == null) {
                    this.array_ = builderForValue.build();
                    this.onChanged();
                }
                else {
                    this.arrayBuilder_.setMessage((AbstractMessage)builderForValue.build());
                }
                this.bitField0_ |= 0x100;
                return this;
            }
            
            public Builder mergeArray(final Array value) {
                if (this.arrayBuilder_ == null) {
                    if ((this.bitField0_ & 0x100) == 0x100 && this.array_ != null && this.array_ != Array.getDefaultInstance()) {
                        this.array_ = Array.newBuilder(this.array_).mergeFrom(value).buildPartial();
                    }
                    else {
                        this.array_ = value;
                    }
                    this.onChanged();
                }
                else {
                    this.arrayBuilder_.mergeFrom((AbstractMessage)value);
                }
                this.bitField0_ |= 0x100;
                return this;
            }
            
            public Builder clearArray() {
                if (this.arrayBuilder_ == null) {
                    this.array_ = null;
                    this.onChanged();
                }
                else {
                    this.arrayBuilder_.clear();
                }
                this.bitField0_ &= 0xFFFFFEFF;
                return this;
            }
            
            public Array.Builder getArrayBuilder() {
                this.bitField0_ |= 0x100;
                this.onChanged();
                return (Array.Builder)this.getArrayFieldBuilder().getBuilder();
            }
            
            public ArrayOrBuilder getArrayOrBuilder() {
                if (this.arrayBuilder_ != null) {
                    return (ArrayOrBuilder)this.arrayBuilder_.getMessageOrBuilder();
                }
                return (this.array_ == null) ? Array.getDefaultInstance() : this.array_;
            }
            
            private SingleFieldBuilderV3<Array, Array.Builder, ArrayOrBuilder> getArrayFieldBuilder() {
                if (this.arrayBuilder_ == null) {
                    this.arrayBuilder_ = (SingleFieldBuilderV3<Array, Array.Builder, ArrayOrBuilder>)new SingleFieldBuilderV3((AbstractMessage)this.getArray(), (AbstractMessage.BuilderParent)this.getParentForChildren(), this.isClean());
                    this.array_ = null;
                }
                return this.arrayBuilder_;
            }
            
            public final Builder setUnknownFields(final UnknownFieldSet unknownFields) {
                return (Builder)super.setUnknownFields(unknownFields);
            }
            
            public final Builder mergeUnknownFields(final UnknownFieldSet unknownFields) {
                return (Builder)super.mergeUnknownFields(unknownFields);
            }
        }
    }
    
    public static final class Identifier extends GeneratedMessageV3 implements IdentifierOrBuilder
    {
        private static final long serialVersionUID = 0L;
        private int bitField0_;
        public static final int NAME_FIELD_NUMBER = 1;
        private volatile Object name_;
        public static final int SCHEMA_NAME_FIELD_NUMBER = 2;
        private volatile Object schemaName_;
        private byte memoizedIsInitialized;
        private static final Identifier DEFAULT_INSTANCE;
        @Deprecated
        public static final Parser<Identifier> PARSER;
        
        private Identifier(final GeneratedMessageV3.Builder<?> builder) {
            super((GeneratedMessageV3.Builder)builder);
            this.memoizedIsInitialized = -1;
        }
        
        private Identifier() {
            this.memoizedIsInitialized = -1;
            this.name_ = "";
            this.schemaName_ = "";
        }
        
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }
        
        private Identifier(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            final ByteString bs = input.readBytes();
                            this.bitField0_ |= 0x2;
                            this.schemaName_ = bs;
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
            return MysqlxExpr.internal_static_Mysqlx_Expr_Identifier_descriptor;
        }
        
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return MysqlxExpr.internal_static_Mysqlx_Expr_Identifier_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)Identifier.class, (Class)Builder.class);
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
        
        public boolean hasSchemaName() {
            return (this.bitField0_ & 0x2) == 0x2;
        }
        
        public String getSchemaName() {
            final Object ref = this.schemaName_;
            if (ref instanceof String) {
                return (String)ref;
            }
            final ByteString bs = (ByteString)ref;
            final String s = bs.toStringUtf8();
            if (bs.isValidUtf8()) {
                this.schemaName_ = s;
            }
            return s;
        }
        
        public ByteString getSchemaNameBytes() {
            final Object ref = this.schemaName_;
            if (ref instanceof String) {
                final ByteString b = ByteString.copyFromUtf8((String)ref);
                return (ByteString)(this.schemaName_ = b);
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
            if (!this.hasName()) {
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
                GeneratedMessageV3.writeString(output, 2, this.schemaName_);
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
                size += GeneratedMessageV3.computeStringSize(2, this.schemaName_);
            }
            size += this.unknownFields.getSerializedSize();
            return this.memoizedSize = size;
        }
        
        public boolean equals(final Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Identifier)) {
                return super.equals(obj);
            }
            final Identifier other = (Identifier)obj;
            boolean result = true;
            result = (result && this.hasName() == other.hasName());
            if (this.hasName()) {
                result = (result && this.getName().equals(other.getName()));
            }
            result = (result && this.hasSchemaName() == other.hasSchemaName());
            if (this.hasSchemaName()) {
                result = (result && this.getSchemaName().equals(other.getSchemaName()));
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
            if (this.hasSchemaName()) {
                hash = 37 * hash + 2;
                hash = 53 * hash + this.getSchemaName().hashCode();
            }
            hash = 29 * hash + this.unknownFields.hashCode();
            return this.memoizedHashCode = hash;
        }
        
        public static Identifier parseFrom(final ByteBuffer data) throws InvalidProtocolBufferException {
            return (Identifier)Identifier.PARSER.parseFrom(data);
        }
        
        public static Identifier parseFrom(final ByteBuffer data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Identifier)Identifier.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Identifier parseFrom(final ByteString data) throws InvalidProtocolBufferException {
            return (Identifier)Identifier.PARSER.parseFrom(data);
        }
        
        public static Identifier parseFrom(final ByteString data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Identifier)Identifier.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Identifier parseFrom(final byte[] data) throws InvalidProtocolBufferException {
            return (Identifier)Identifier.PARSER.parseFrom(data);
        }
        
        public static Identifier parseFrom(final byte[] data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Identifier)Identifier.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Identifier parseFrom(final InputStream input) throws IOException {
            return (Identifier)GeneratedMessageV3.parseWithIOException((Parser)Identifier.PARSER, input);
        }
        
        public static Identifier parseFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Identifier)GeneratedMessageV3.parseWithIOException((Parser)Identifier.PARSER, input, extensionRegistry);
        }
        
        public static Identifier parseDelimitedFrom(final InputStream input) throws IOException {
            return (Identifier)GeneratedMessageV3.parseDelimitedWithIOException((Parser)Identifier.PARSER, input);
        }
        
        public static Identifier parseDelimitedFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Identifier)GeneratedMessageV3.parseDelimitedWithIOException((Parser)Identifier.PARSER, input, extensionRegistry);
        }
        
        public static Identifier parseFrom(final CodedInputStream input) throws IOException {
            return (Identifier)GeneratedMessageV3.parseWithIOException((Parser)Identifier.PARSER, input);
        }
        
        public static Identifier parseFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Identifier)GeneratedMessageV3.parseWithIOException((Parser)Identifier.PARSER, input, extensionRegistry);
        }
        
        public Builder newBuilderForType() {
            return newBuilder();
        }
        
        public static Builder newBuilder() {
            return Identifier.DEFAULT_INSTANCE.toBuilder();
        }
        
        public static Builder newBuilder(final Identifier prototype) {
            return Identifier.DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }
        
        public Builder toBuilder() {
            return (this == Identifier.DEFAULT_INSTANCE) ? new Builder() : new Builder().mergeFrom(this);
        }
        
        protected Builder newBuilderForType(final GeneratedMessageV3.BuilderParent parent) {
            final Builder builder = new Builder(parent);
            return builder;
        }
        
        public static Identifier getDefaultInstance() {
            return Identifier.DEFAULT_INSTANCE;
        }
        
        public static Parser<Identifier> parser() {
            return Identifier.PARSER;
        }
        
        public Parser<Identifier> getParserForType() {
            return Identifier.PARSER;
        }
        
        public Identifier getDefaultInstanceForType() {
            return Identifier.DEFAULT_INSTANCE;
        }
        
        static {
            DEFAULT_INSTANCE = new Identifier();
            PARSER = (Parser)new AbstractParser<Identifier>() {
                public Identifier parsePartialFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new Identifier(input, extensionRegistry);
                }
            };
        }
        
        public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements IdentifierOrBuilder
        {
            private int bitField0_;
            private Object name_;
            private Object schemaName_;
            
            public static final Descriptors.Descriptor getDescriptor() {
                return MysqlxExpr.internal_static_Mysqlx_Expr_Identifier_descriptor;
            }
            
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return MysqlxExpr.internal_static_Mysqlx_Expr_Identifier_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)Identifier.class, (Class)Builder.class);
            }
            
            private Builder() {
                this.name_ = "";
                this.schemaName_ = "";
                this.maybeForceBuilderInitialization();
            }
            
            private Builder(final GeneratedMessageV3.BuilderParent parent) {
                super(parent);
                this.name_ = "";
                this.schemaName_ = "";
                this.maybeForceBuilderInitialization();
            }
            
            private void maybeForceBuilderInitialization() {
                if (Identifier.alwaysUseFieldBuilders) {}
            }
            
            public Builder clear() {
                super.clear();
                this.name_ = "";
                this.bitField0_ &= 0xFFFFFFFE;
                this.schemaName_ = "";
                this.bitField0_ &= 0xFFFFFFFD;
                return this;
            }
            
            public Descriptors.Descriptor getDescriptorForType() {
                return MysqlxExpr.internal_static_Mysqlx_Expr_Identifier_descriptor;
            }
            
            public Identifier getDefaultInstanceForType() {
                return Identifier.getDefaultInstance();
            }
            
            public Identifier build() {
                final Identifier result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw newUninitializedMessageException((Message)result);
                }
                return result;
            }
            
            public Identifier buildPartial() {
                final Identifier result = new Identifier((GeneratedMessageV3.Builder)this);
                final int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 0x1) == 0x1) {
                    to_bitField0_ |= 0x1;
                }
                result.name_ = this.name_;
                if ((from_bitField0_ & 0x2) == 0x2) {
                    to_bitField0_ |= 0x2;
                }
                result.schemaName_ = this.schemaName_;
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
                if (other instanceof Identifier) {
                    return this.mergeFrom((Identifier)other);
                }
                super.mergeFrom(other);
                return this;
            }
            
            public Builder mergeFrom(final Identifier other) {
                if (other == Identifier.getDefaultInstance()) {
                    return this;
                }
                if (other.hasName()) {
                    this.bitField0_ |= 0x1;
                    this.name_ = other.name_;
                    this.onChanged();
                }
                if (other.hasSchemaName()) {
                    this.bitField0_ |= 0x2;
                    this.schemaName_ = other.schemaName_;
                    this.onChanged();
                }
                this.mergeUnknownFields(other.unknownFields);
                this.onChanged();
                return this;
            }
            
            public final boolean isInitialized() {
                return this.hasName();
            }
            
            public Builder mergeFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
                Identifier parsedMessage = null;
                try {
                    parsedMessage = (Identifier)Identifier.PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (Identifier)e.getUnfinishedMessage();
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
                this.name_ = Identifier.getDefaultInstance().getName();
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
            
            public boolean hasSchemaName() {
                return (this.bitField0_ & 0x2) == 0x2;
            }
            
            public String getSchemaName() {
                final Object ref = this.schemaName_;
                if (!(ref instanceof String)) {
                    final ByteString bs = (ByteString)ref;
                    final String s = bs.toStringUtf8();
                    if (bs.isValidUtf8()) {
                        this.schemaName_ = s;
                    }
                    return s;
                }
                return (String)ref;
            }
            
            public ByteString getSchemaNameBytes() {
                final Object ref = this.schemaName_;
                if (ref instanceof String) {
                    final ByteString b = ByteString.copyFromUtf8((String)ref);
                    return (ByteString)(this.schemaName_ = b);
                }
                return (ByteString)ref;
            }
            
            public Builder setSchemaName(final String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 0x2;
                this.schemaName_ = value;
                this.onChanged();
                return this;
            }
            
            public Builder clearSchemaName() {
                this.bitField0_ &= 0xFFFFFFFD;
                this.schemaName_ = Identifier.getDefaultInstance().getSchemaName();
                this.onChanged();
                return this;
            }
            
            public Builder setSchemaNameBytes(final ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 0x2;
                this.schemaName_ = value;
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
    
    public static final class DocumentPathItem extends GeneratedMessageV3 implements DocumentPathItemOrBuilder
    {
        private static final long serialVersionUID = 0L;
        private int bitField0_;
        public static final int TYPE_FIELD_NUMBER = 1;
        private int type_;
        public static final int VALUE_FIELD_NUMBER = 2;
        private volatile Object value_;
        public static final int INDEX_FIELD_NUMBER = 3;
        private int index_;
        private byte memoizedIsInitialized;
        private static final DocumentPathItem DEFAULT_INSTANCE;
        @Deprecated
        public static final Parser<DocumentPathItem> PARSER;
        
        private DocumentPathItem(final GeneratedMessageV3.Builder<?> builder) {
            super((GeneratedMessageV3.Builder)builder);
            this.memoizedIsInitialized = -1;
        }
        
        private DocumentPathItem() {
            this.memoizedIsInitialized = -1;
            this.type_ = 1;
            this.value_ = "";
            this.index_ = 0;
        }
        
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }
        
        private DocumentPathItem(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            final Type value = Type.valueOf(rawValue);
                            if (value == null) {
                                unknownFields.mergeVarintField(1, rawValue);
                                continue;
                            }
                            this.bitField0_ |= 0x1;
                            this.type_ = rawValue;
                            continue;
                        }
                        case 18: {
                            final ByteString bs = input.readBytes();
                            this.bitField0_ |= 0x2;
                            this.value_ = bs;
                            continue;
                        }
                        case 24: {
                            this.bitField0_ |= 0x4;
                            this.index_ = input.readUInt32();
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
            return MysqlxExpr.internal_static_Mysqlx_Expr_DocumentPathItem_descriptor;
        }
        
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return MysqlxExpr.internal_static_Mysqlx_Expr_DocumentPathItem_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)DocumentPathItem.class, (Class)Builder.class);
        }
        
        public boolean hasType() {
            return (this.bitField0_ & 0x1) == 0x1;
        }
        
        public Type getType() {
            final Type result = Type.valueOf(this.type_);
            return (result == null) ? Type.MEMBER : result;
        }
        
        public boolean hasValue() {
            return (this.bitField0_ & 0x2) == 0x2;
        }
        
        public String getValue() {
            final Object ref = this.value_;
            if (ref instanceof String) {
                return (String)ref;
            }
            final ByteString bs = (ByteString)ref;
            final String s = bs.toStringUtf8();
            if (bs.isValidUtf8()) {
                this.value_ = s;
            }
            return s;
        }
        
        public ByteString getValueBytes() {
            final Object ref = this.value_;
            if (ref instanceof String) {
                final ByteString b = ByteString.copyFromUtf8((String)ref);
                return (ByteString)(this.value_ = b);
            }
            return (ByteString)ref;
        }
        
        public boolean hasIndex() {
            return (this.bitField0_ & 0x4) == 0x4;
        }
        
        public int getIndex() {
            return this.index_;
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
                GeneratedMessageV3.writeString(output, 2, this.value_);
            }
            if ((this.bitField0_ & 0x4) == 0x4) {
                output.writeUInt32(3, this.index_);
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
                size += GeneratedMessageV3.computeStringSize(2, this.value_);
            }
            if ((this.bitField0_ & 0x4) == 0x4) {
                size += CodedOutputStream.computeUInt32Size(3, this.index_);
            }
            size += this.unknownFields.getSerializedSize();
            return this.memoizedSize = size;
        }
        
        public boolean equals(final Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof DocumentPathItem)) {
                return super.equals(obj);
            }
            final DocumentPathItem other = (DocumentPathItem)obj;
            boolean result = true;
            result = (result && this.hasType() == other.hasType());
            if (this.hasType()) {
                result = (result && this.type_ == other.type_);
            }
            result = (result && this.hasValue() == other.hasValue());
            if (this.hasValue()) {
                result = (result && this.getValue().equals(other.getValue()));
            }
            result = (result && this.hasIndex() == other.hasIndex());
            if (this.hasIndex()) {
                result = (result && this.getIndex() == other.getIndex());
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
            if (this.hasValue()) {
                hash = 37 * hash + 2;
                hash = 53 * hash + this.getValue().hashCode();
            }
            if (this.hasIndex()) {
                hash = 37 * hash + 3;
                hash = 53 * hash + this.getIndex();
            }
            hash = 29 * hash + this.unknownFields.hashCode();
            return this.memoizedHashCode = hash;
        }
        
        public static DocumentPathItem parseFrom(final ByteBuffer data) throws InvalidProtocolBufferException {
            return (DocumentPathItem)DocumentPathItem.PARSER.parseFrom(data);
        }
        
        public static DocumentPathItem parseFrom(final ByteBuffer data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (DocumentPathItem)DocumentPathItem.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static DocumentPathItem parseFrom(final ByteString data) throws InvalidProtocolBufferException {
            return (DocumentPathItem)DocumentPathItem.PARSER.parseFrom(data);
        }
        
        public static DocumentPathItem parseFrom(final ByteString data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (DocumentPathItem)DocumentPathItem.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static DocumentPathItem parseFrom(final byte[] data) throws InvalidProtocolBufferException {
            return (DocumentPathItem)DocumentPathItem.PARSER.parseFrom(data);
        }
        
        public static DocumentPathItem parseFrom(final byte[] data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (DocumentPathItem)DocumentPathItem.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static DocumentPathItem parseFrom(final InputStream input) throws IOException {
            return (DocumentPathItem)GeneratedMessageV3.parseWithIOException((Parser)DocumentPathItem.PARSER, input);
        }
        
        public static DocumentPathItem parseFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (DocumentPathItem)GeneratedMessageV3.parseWithIOException((Parser)DocumentPathItem.PARSER, input, extensionRegistry);
        }
        
        public static DocumentPathItem parseDelimitedFrom(final InputStream input) throws IOException {
            return (DocumentPathItem)GeneratedMessageV3.parseDelimitedWithIOException((Parser)DocumentPathItem.PARSER, input);
        }
        
        public static DocumentPathItem parseDelimitedFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (DocumentPathItem)GeneratedMessageV3.parseDelimitedWithIOException((Parser)DocumentPathItem.PARSER, input, extensionRegistry);
        }
        
        public static DocumentPathItem parseFrom(final CodedInputStream input) throws IOException {
            return (DocumentPathItem)GeneratedMessageV3.parseWithIOException((Parser)DocumentPathItem.PARSER, input);
        }
        
        public static DocumentPathItem parseFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (DocumentPathItem)GeneratedMessageV3.parseWithIOException((Parser)DocumentPathItem.PARSER, input, extensionRegistry);
        }
        
        public Builder newBuilderForType() {
            return newBuilder();
        }
        
        public static Builder newBuilder() {
            return DocumentPathItem.DEFAULT_INSTANCE.toBuilder();
        }
        
        public static Builder newBuilder(final DocumentPathItem prototype) {
            return DocumentPathItem.DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }
        
        public Builder toBuilder() {
            return (this == DocumentPathItem.DEFAULT_INSTANCE) ? new Builder() : new Builder().mergeFrom(this);
        }
        
        protected Builder newBuilderForType(final GeneratedMessageV3.BuilderParent parent) {
            final Builder builder = new Builder(parent);
            return builder;
        }
        
        public static DocumentPathItem getDefaultInstance() {
            return DocumentPathItem.DEFAULT_INSTANCE;
        }
        
        public static Parser<DocumentPathItem> parser() {
            return DocumentPathItem.PARSER;
        }
        
        public Parser<DocumentPathItem> getParserForType() {
            return DocumentPathItem.PARSER;
        }
        
        public DocumentPathItem getDefaultInstanceForType() {
            return DocumentPathItem.DEFAULT_INSTANCE;
        }
        
        static {
            DEFAULT_INSTANCE = new DocumentPathItem();
            PARSER = (Parser)new AbstractParser<DocumentPathItem>() {
                public DocumentPathItem parsePartialFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new DocumentPathItem(input, extensionRegistry);
                }
            };
        }
        
        public enum Type implements ProtocolMessageEnum
        {
            MEMBER(1), 
            MEMBER_ASTERISK(2), 
            ARRAY_INDEX(3), 
            ARRAY_INDEX_ASTERISK(4), 
            DOUBLE_ASTERISK(5);
            
            public static final int MEMBER_VALUE = 1;
            public static final int MEMBER_ASTERISK_VALUE = 2;
            public static final int ARRAY_INDEX_VALUE = 3;
            public static final int ARRAY_INDEX_ASTERISK_VALUE = 4;
            public static final int DOUBLE_ASTERISK_VALUE = 5;
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
                        return Type.MEMBER;
                    }
                    case 2: {
                        return Type.MEMBER_ASTERISK;
                    }
                    case 3: {
                        return Type.ARRAY_INDEX;
                    }
                    case 4: {
                        return Type.ARRAY_INDEX_ASTERISK;
                    }
                    case 5: {
                        return Type.DOUBLE_ASTERISK;
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
                return DocumentPathItem.getDescriptor().getEnumTypes().get(0);
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
        
        public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements DocumentPathItemOrBuilder
        {
            private int bitField0_;
            private int type_;
            private Object value_;
            private int index_;
            
            public static final Descriptors.Descriptor getDescriptor() {
                return MysqlxExpr.internal_static_Mysqlx_Expr_DocumentPathItem_descriptor;
            }
            
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return MysqlxExpr.internal_static_Mysqlx_Expr_DocumentPathItem_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)DocumentPathItem.class, (Class)Builder.class);
            }
            
            private Builder() {
                this.type_ = 1;
                this.value_ = "";
                this.maybeForceBuilderInitialization();
            }
            
            private Builder(final GeneratedMessageV3.BuilderParent parent) {
                super(parent);
                this.type_ = 1;
                this.value_ = "";
                this.maybeForceBuilderInitialization();
            }
            
            private void maybeForceBuilderInitialization() {
                if (DocumentPathItem.alwaysUseFieldBuilders) {}
            }
            
            public Builder clear() {
                super.clear();
                this.type_ = 1;
                this.bitField0_ &= 0xFFFFFFFE;
                this.value_ = "";
                this.bitField0_ &= 0xFFFFFFFD;
                this.index_ = 0;
                this.bitField0_ &= 0xFFFFFFFB;
                return this;
            }
            
            public Descriptors.Descriptor getDescriptorForType() {
                return MysqlxExpr.internal_static_Mysqlx_Expr_DocumentPathItem_descriptor;
            }
            
            public DocumentPathItem getDefaultInstanceForType() {
                return DocumentPathItem.getDefaultInstance();
            }
            
            public DocumentPathItem build() {
                final DocumentPathItem result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw newUninitializedMessageException((Message)result);
                }
                return result;
            }
            
            public DocumentPathItem buildPartial() {
                final DocumentPathItem result = new DocumentPathItem((GeneratedMessageV3.Builder)this);
                final int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 0x1) == 0x1) {
                    to_bitField0_ |= 0x1;
                }
                result.type_ = this.type_;
                if ((from_bitField0_ & 0x2) == 0x2) {
                    to_bitField0_ |= 0x2;
                }
                result.value_ = this.value_;
                if ((from_bitField0_ & 0x4) == 0x4) {
                    to_bitField0_ |= 0x4;
                }
                result.index_ = this.index_;
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
                if (other instanceof DocumentPathItem) {
                    return this.mergeFrom((DocumentPathItem)other);
                }
                super.mergeFrom(other);
                return this;
            }
            
            public Builder mergeFrom(final DocumentPathItem other) {
                if (other == DocumentPathItem.getDefaultInstance()) {
                    return this;
                }
                if (other.hasType()) {
                    this.setType(other.getType());
                }
                if (other.hasValue()) {
                    this.bitField0_ |= 0x2;
                    this.value_ = other.value_;
                    this.onChanged();
                }
                if (other.hasIndex()) {
                    this.setIndex(other.getIndex());
                }
                this.mergeUnknownFields(other.unknownFields);
                this.onChanged();
                return this;
            }
            
            public final boolean isInitialized() {
                return this.hasType();
            }
            
            public Builder mergeFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
                DocumentPathItem parsedMessage = null;
                try {
                    parsedMessage = (DocumentPathItem)DocumentPathItem.PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (DocumentPathItem)e.getUnfinishedMessage();
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
            
            public Type getType() {
                final Type result = Type.valueOf(this.type_);
                return (result == null) ? Type.MEMBER : result;
            }
            
            public Builder setType(final Type value) {
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
            
            public boolean hasValue() {
                return (this.bitField0_ & 0x2) == 0x2;
            }
            
            public String getValue() {
                final Object ref = this.value_;
                if (!(ref instanceof String)) {
                    final ByteString bs = (ByteString)ref;
                    final String s = bs.toStringUtf8();
                    if (bs.isValidUtf8()) {
                        this.value_ = s;
                    }
                    return s;
                }
                return (String)ref;
            }
            
            public ByteString getValueBytes() {
                final Object ref = this.value_;
                if (ref instanceof String) {
                    final ByteString b = ByteString.copyFromUtf8((String)ref);
                    return (ByteString)(this.value_ = b);
                }
                return (ByteString)ref;
            }
            
            public Builder setValue(final String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 0x2;
                this.value_ = value;
                this.onChanged();
                return this;
            }
            
            public Builder clearValue() {
                this.bitField0_ &= 0xFFFFFFFD;
                this.value_ = DocumentPathItem.getDefaultInstance().getValue();
                this.onChanged();
                return this;
            }
            
            public Builder setValueBytes(final ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 0x2;
                this.value_ = value;
                this.onChanged();
                return this;
            }
            
            public boolean hasIndex() {
                return (this.bitField0_ & 0x4) == 0x4;
            }
            
            public int getIndex() {
                return this.index_;
            }
            
            public Builder setIndex(final int value) {
                this.bitField0_ |= 0x4;
                this.index_ = value;
                this.onChanged();
                return this;
            }
            
            public Builder clearIndex() {
                this.bitField0_ &= 0xFFFFFFFB;
                this.index_ = 0;
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
    
    public static final class ColumnIdentifier extends GeneratedMessageV3 implements ColumnIdentifierOrBuilder
    {
        private static final long serialVersionUID = 0L;
        private int bitField0_;
        public static final int DOCUMENT_PATH_FIELD_NUMBER = 1;
        private List<DocumentPathItem> documentPath_;
        public static final int NAME_FIELD_NUMBER = 2;
        private volatile Object name_;
        public static final int TABLE_NAME_FIELD_NUMBER = 3;
        private volatile Object tableName_;
        public static final int SCHEMA_NAME_FIELD_NUMBER = 4;
        private volatile Object schemaName_;
        private byte memoizedIsInitialized;
        private static final ColumnIdentifier DEFAULT_INSTANCE;
        @Deprecated
        public static final Parser<ColumnIdentifier> PARSER;
        
        private ColumnIdentifier(final GeneratedMessageV3.Builder<?> builder) {
            super((GeneratedMessageV3.Builder)builder);
            this.memoizedIsInitialized = -1;
        }
        
        private ColumnIdentifier() {
            this.memoizedIsInitialized = -1;
            this.documentPath_ = Collections.emptyList();
            this.name_ = "";
            this.tableName_ = "";
            this.schemaName_ = "";
        }
        
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }
        
        private ColumnIdentifier(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                                this.documentPath_ = new ArrayList<DocumentPathItem>();
                                mutable_bitField0_ |= 0x1;
                            }
                            this.documentPath_.add((DocumentPathItem)input.readMessage((Parser)DocumentPathItem.PARSER, extensionRegistry));
                            continue;
                        }
                        case 18: {
                            final ByteString bs = input.readBytes();
                            this.bitField0_ |= 0x1;
                            this.name_ = bs;
                            continue;
                        }
                        case 26: {
                            final ByteString bs = input.readBytes();
                            this.bitField0_ |= 0x2;
                            this.tableName_ = bs;
                            continue;
                        }
                        case 34: {
                            final ByteString bs = input.readBytes();
                            this.bitField0_ |= 0x4;
                            this.schemaName_ = bs;
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
                    this.documentPath_ = Collections.unmodifiableList((List<? extends DocumentPathItem>)this.documentPath_);
                }
                this.unknownFields = unknownFields.build();
                this.makeExtensionsImmutable();
            }
        }
        
        public static final Descriptors.Descriptor getDescriptor() {
            return MysqlxExpr.internal_static_Mysqlx_Expr_ColumnIdentifier_descriptor;
        }
        
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return MysqlxExpr.internal_static_Mysqlx_Expr_ColumnIdentifier_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)ColumnIdentifier.class, (Class)Builder.class);
        }
        
        public List<DocumentPathItem> getDocumentPathList() {
            return this.documentPath_;
        }
        
        public List<? extends DocumentPathItemOrBuilder> getDocumentPathOrBuilderList() {
            return this.documentPath_;
        }
        
        public int getDocumentPathCount() {
            return this.documentPath_.size();
        }
        
        public DocumentPathItem getDocumentPath(final int index) {
            return this.documentPath_.get(index);
        }
        
        public DocumentPathItemOrBuilder getDocumentPathOrBuilder(final int index) {
            return this.documentPath_.get(index);
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
        
        public boolean hasTableName() {
            return (this.bitField0_ & 0x2) == 0x2;
        }
        
        public String getTableName() {
            final Object ref = this.tableName_;
            if (ref instanceof String) {
                return (String)ref;
            }
            final ByteString bs = (ByteString)ref;
            final String s = bs.toStringUtf8();
            if (bs.isValidUtf8()) {
                this.tableName_ = s;
            }
            return s;
        }
        
        public ByteString getTableNameBytes() {
            final Object ref = this.tableName_;
            if (ref instanceof String) {
                final ByteString b = ByteString.copyFromUtf8((String)ref);
                return (ByteString)(this.tableName_ = b);
            }
            return (ByteString)ref;
        }
        
        public boolean hasSchemaName() {
            return (this.bitField0_ & 0x4) == 0x4;
        }
        
        public String getSchemaName() {
            final Object ref = this.schemaName_;
            if (ref instanceof String) {
                return (String)ref;
            }
            final ByteString bs = (ByteString)ref;
            final String s = bs.toStringUtf8();
            if (bs.isValidUtf8()) {
                this.schemaName_ = s;
            }
            return s;
        }
        
        public ByteString getSchemaNameBytes() {
            final Object ref = this.schemaName_;
            if (ref instanceof String) {
                final ByteString b = ByteString.copyFromUtf8((String)ref);
                return (ByteString)(this.schemaName_ = b);
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
            for (int i = 0; i < this.getDocumentPathCount(); ++i) {
                if (!this.getDocumentPath(i).isInitialized()) {
                    this.memoizedIsInitialized = 0;
                    return false;
                }
            }
            this.memoizedIsInitialized = 1;
            return true;
        }
        
        public void writeTo(final CodedOutputStream output) throws IOException {
            for (int i = 0; i < this.documentPath_.size(); ++i) {
                output.writeMessage(1, (MessageLite)this.documentPath_.get(i));
            }
            if ((this.bitField0_ & 0x1) == 0x1) {
                GeneratedMessageV3.writeString(output, 2, this.name_);
            }
            if ((this.bitField0_ & 0x2) == 0x2) {
                GeneratedMessageV3.writeString(output, 3, this.tableName_);
            }
            if ((this.bitField0_ & 0x4) == 0x4) {
                GeneratedMessageV3.writeString(output, 4, this.schemaName_);
            }
            this.unknownFields.writeTo(output);
        }
        
        public int getSerializedSize() {
            int size = this.memoizedSize;
            if (size != -1) {
                return size;
            }
            size = 0;
            for (int i = 0; i < this.documentPath_.size(); ++i) {
                size += CodedOutputStream.computeMessageSize(1, (MessageLite)this.documentPath_.get(i));
            }
            if ((this.bitField0_ & 0x1) == 0x1) {
                size += GeneratedMessageV3.computeStringSize(2, this.name_);
            }
            if ((this.bitField0_ & 0x2) == 0x2) {
                size += GeneratedMessageV3.computeStringSize(3, this.tableName_);
            }
            if ((this.bitField0_ & 0x4) == 0x4) {
                size += GeneratedMessageV3.computeStringSize(4, this.schemaName_);
            }
            size += this.unknownFields.getSerializedSize();
            return this.memoizedSize = size;
        }
        
        public boolean equals(final Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof ColumnIdentifier)) {
                return super.equals(obj);
            }
            final ColumnIdentifier other = (ColumnIdentifier)obj;
            boolean result = true;
            result = (result && this.getDocumentPathList().equals(other.getDocumentPathList()));
            result = (result && this.hasName() == other.hasName());
            if (this.hasName()) {
                result = (result && this.getName().equals(other.getName()));
            }
            result = (result && this.hasTableName() == other.hasTableName());
            if (this.hasTableName()) {
                result = (result && this.getTableName().equals(other.getTableName()));
            }
            result = (result && this.hasSchemaName() == other.hasSchemaName());
            if (this.hasSchemaName()) {
                result = (result && this.getSchemaName().equals(other.getSchemaName()));
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
            if (this.getDocumentPathCount() > 0) {
                hash = 37 * hash + 1;
                hash = 53 * hash + this.getDocumentPathList().hashCode();
            }
            if (this.hasName()) {
                hash = 37 * hash + 2;
                hash = 53 * hash + this.getName().hashCode();
            }
            if (this.hasTableName()) {
                hash = 37 * hash + 3;
                hash = 53 * hash + this.getTableName().hashCode();
            }
            if (this.hasSchemaName()) {
                hash = 37 * hash + 4;
                hash = 53 * hash + this.getSchemaName().hashCode();
            }
            hash = 29 * hash + this.unknownFields.hashCode();
            return this.memoizedHashCode = hash;
        }
        
        public static ColumnIdentifier parseFrom(final ByteBuffer data) throws InvalidProtocolBufferException {
            return (ColumnIdentifier)ColumnIdentifier.PARSER.parseFrom(data);
        }
        
        public static ColumnIdentifier parseFrom(final ByteBuffer data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ColumnIdentifier)ColumnIdentifier.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static ColumnIdentifier parseFrom(final ByteString data) throws InvalidProtocolBufferException {
            return (ColumnIdentifier)ColumnIdentifier.PARSER.parseFrom(data);
        }
        
        public static ColumnIdentifier parseFrom(final ByteString data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ColumnIdentifier)ColumnIdentifier.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static ColumnIdentifier parseFrom(final byte[] data) throws InvalidProtocolBufferException {
            return (ColumnIdentifier)ColumnIdentifier.PARSER.parseFrom(data);
        }
        
        public static ColumnIdentifier parseFrom(final byte[] data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ColumnIdentifier)ColumnIdentifier.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static ColumnIdentifier parseFrom(final InputStream input) throws IOException {
            return (ColumnIdentifier)GeneratedMessageV3.parseWithIOException((Parser)ColumnIdentifier.PARSER, input);
        }
        
        public static ColumnIdentifier parseFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ColumnIdentifier)GeneratedMessageV3.parseWithIOException((Parser)ColumnIdentifier.PARSER, input, extensionRegistry);
        }
        
        public static ColumnIdentifier parseDelimitedFrom(final InputStream input) throws IOException {
            return (ColumnIdentifier)GeneratedMessageV3.parseDelimitedWithIOException((Parser)ColumnIdentifier.PARSER, input);
        }
        
        public static ColumnIdentifier parseDelimitedFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ColumnIdentifier)GeneratedMessageV3.parseDelimitedWithIOException((Parser)ColumnIdentifier.PARSER, input, extensionRegistry);
        }
        
        public static ColumnIdentifier parseFrom(final CodedInputStream input) throws IOException {
            return (ColumnIdentifier)GeneratedMessageV3.parseWithIOException((Parser)ColumnIdentifier.PARSER, input);
        }
        
        public static ColumnIdentifier parseFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ColumnIdentifier)GeneratedMessageV3.parseWithIOException((Parser)ColumnIdentifier.PARSER, input, extensionRegistry);
        }
        
        public Builder newBuilderForType() {
            return newBuilder();
        }
        
        public static Builder newBuilder() {
            return ColumnIdentifier.DEFAULT_INSTANCE.toBuilder();
        }
        
        public static Builder newBuilder(final ColumnIdentifier prototype) {
            return ColumnIdentifier.DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }
        
        public Builder toBuilder() {
            return (this == ColumnIdentifier.DEFAULT_INSTANCE) ? new Builder() : new Builder().mergeFrom(this);
        }
        
        protected Builder newBuilderForType(final GeneratedMessageV3.BuilderParent parent) {
            final Builder builder = new Builder(parent);
            return builder;
        }
        
        public static ColumnIdentifier getDefaultInstance() {
            return ColumnIdentifier.DEFAULT_INSTANCE;
        }
        
        public static Parser<ColumnIdentifier> parser() {
            return ColumnIdentifier.PARSER;
        }
        
        public Parser<ColumnIdentifier> getParserForType() {
            return ColumnIdentifier.PARSER;
        }
        
        public ColumnIdentifier getDefaultInstanceForType() {
            return ColumnIdentifier.DEFAULT_INSTANCE;
        }
        
        static {
            DEFAULT_INSTANCE = new ColumnIdentifier();
            PARSER = (Parser)new AbstractParser<ColumnIdentifier>() {
                public ColumnIdentifier parsePartialFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new ColumnIdentifier(input, extensionRegistry);
                }
            };
        }
        
        public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements ColumnIdentifierOrBuilder
        {
            private int bitField0_;
            private List<DocumentPathItem> documentPath_;
            private RepeatedFieldBuilderV3<DocumentPathItem, DocumentPathItem.Builder, DocumentPathItemOrBuilder> documentPathBuilder_;
            private Object name_;
            private Object tableName_;
            private Object schemaName_;
            
            public static final Descriptors.Descriptor getDescriptor() {
                return MysqlxExpr.internal_static_Mysqlx_Expr_ColumnIdentifier_descriptor;
            }
            
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return MysqlxExpr.internal_static_Mysqlx_Expr_ColumnIdentifier_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)ColumnIdentifier.class, (Class)Builder.class);
            }
            
            private Builder() {
                this.documentPath_ = Collections.emptyList();
                this.name_ = "";
                this.tableName_ = "";
                this.schemaName_ = "";
                this.maybeForceBuilderInitialization();
            }
            
            private Builder(final GeneratedMessageV3.BuilderParent parent) {
                super(parent);
                this.documentPath_ = Collections.emptyList();
                this.name_ = "";
                this.tableName_ = "";
                this.schemaName_ = "";
                this.maybeForceBuilderInitialization();
            }
            
            private void maybeForceBuilderInitialization() {
                if (ColumnIdentifier.alwaysUseFieldBuilders) {
                    this.getDocumentPathFieldBuilder();
                }
            }
            
            public Builder clear() {
                super.clear();
                if (this.documentPathBuilder_ == null) {
                    this.documentPath_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFFE;
                }
                else {
                    this.documentPathBuilder_.clear();
                }
                this.name_ = "";
                this.bitField0_ &= 0xFFFFFFFD;
                this.tableName_ = "";
                this.bitField0_ &= 0xFFFFFFFB;
                this.schemaName_ = "";
                this.bitField0_ &= 0xFFFFFFF7;
                return this;
            }
            
            public Descriptors.Descriptor getDescriptorForType() {
                return MysqlxExpr.internal_static_Mysqlx_Expr_ColumnIdentifier_descriptor;
            }
            
            public ColumnIdentifier getDefaultInstanceForType() {
                return ColumnIdentifier.getDefaultInstance();
            }
            
            public ColumnIdentifier build() {
                final ColumnIdentifier result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw newUninitializedMessageException((Message)result);
                }
                return result;
            }
            
            public ColumnIdentifier buildPartial() {
                final ColumnIdentifier result = new ColumnIdentifier((GeneratedMessageV3.Builder)this);
                final int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if (this.documentPathBuilder_ == null) {
                    if ((this.bitField0_ & 0x1) == 0x1) {
                        this.documentPath_ = Collections.unmodifiableList((List<? extends DocumentPathItem>)this.documentPath_);
                        this.bitField0_ &= 0xFFFFFFFE;
                    }
                    result.documentPath_ = this.documentPath_;
                }
                else {
                    result.documentPath_ = (List<DocumentPathItem>)this.documentPathBuilder_.build();
                }
                if ((from_bitField0_ & 0x2) == 0x2) {
                    to_bitField0_ |= 0x1;
                }
                result.name_ = this.name_;
                if ((from_bitField0_ & 0x4) == 0x4) {
                    to_bitField0_ |= 0x2;
                }
                result.tableName_ = this.tableName_;
                if ((from_bitField0_ & 0x8) == 0x8) {
                    to_bitField0_ |= 0x4;
                }
                result.schemaName_ = this.schemaName_;
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
                if (other instanceof ColumnIdentifier) {
                    return this.mergeFrom((ColumnIdentifier)other);
                }
                super.mergeFrom(other);
                return this;
            }
            
            public Builder mergeFrom(final ColumnIdentifier other) {
                if (other == ColumnIdentifier.getDefaultInstance()) {
                    return this;
                }
                if (this.documentPathBuilder_ == null) {
                    if (!other.documentPath_.isEmpty()) {
                        if (this.documentPath_.isEmpty()) {
                            this.documentPath_ = other.documentPath_;
                            this.bitField0_ &= 0xFFFFFFFE;
                        }
                        else {
                            this.ensureDocumentPathIsMutable();
                            this.documentPath_.addAll(other.documentPath_);
                        }
                        this.onChanged();
                    }
                }
                else if (!other.documentPath_.isEmpty()) {
                    if (this.documentPathBuilder_.isEmpty()) {
                        this.documentPathBuilder_.dispose();
                        this.documentPathBuilder_ = null;
                        this.documentPath_ = other.documentPath_;
                        this.bitField0_ &= 0xFFFFFFFE;
                        this.documentPathBuilder_ = (ColumnIdentifier.alwaysUseFieldBuilders ? this.getDocumentPathFieldBuilder() : null);
                    }
                    else {
                        this.documentPathBuilder_.addAllMessages((Iterable)other.documentPath_);
                    }
                }
                if (other.hasName()) {
                    this.bitField0_ |= 0x2;
                    this.name_ = other.name_;
                    this.onChanged();
                }
                if (other.hasTableName()) {
                    this.bitField0_ |= 0x4;
                    this.tableName_ = other.tableName_;
                    this.onChanged();
                }
                if (other.hasSchemaName()) {
                    this.bitField0_ |= 0x8;
                    this.schemaName_ = other.schemaName_;
                    this.onChanged();
                }
                this.mergeUnknownFields(other.unknownFields);
                this.onChanged();
                return this;
            }
            
            public final boolean isInitialized() {
                for (int i = 0; i < this.getDocumentPathCount(); ++i) {
                    if (!this.getDocumentPath(i).isInitialized()) {
                        return false;
                    }
                }
                return true;
            }
            
            public Builder mergeFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
                ColumnIdentifier parsedMessage = null;
                try {
                    parsedMessage = (ColumnIdentifier)ColumnIdentifier.PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (ColumnIdentifier)e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                }
                finally {
                    if (parsedMessage != null) {
                        this.mergeFrom(parsedMessage);
                    }
                }
                return this;
            }
            
            private void ensureDocumentPathIsMutable() {
                if ((this.bitField0_ & 0x1) != 0x1) {
                    this.documentPath_ = new ArrayList<DocumentPathItem>(this.documentPath_);
                    this.bitField0_ |= 0x1;
                }
            }
            
            public List<DocumentPathItem> getDocumentPathList() {
                if (this.documentPathBuilder_ == null) {
                    return Collections.unmodifiableList((List<? extends DocumentPathItem>)this.documentPath_);
                }
                return (List<DocumentPathItem>)this.documentPathBuilder_.getMessageList();
            }
            
            public int getDocumentPathCount() {
                if (this.documentPathBuilder_ == null) {
                    return this.documentPath_.size();
                }
                return this.documentPathBuilder_.getCount();
            }
            
            public DocumentPathItem getDocumentPath(final int index) {
                if (this.documentPathBuilder_ == null) {
                    return this.documentPath_.get(index);
                }
                return (DocumentPathItem)this.documentPathBuilder_.getMessage(index);
            }
            
            public Builder setDocumentPath(final int index, final DocumentPathItem value) {
                if (this.documentPathBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureDocumentPathIsMutable();
                    this.documentPath_.set(index, value);
                    this.onChanged();
                }
                else {
                    this.documentPathBuilder_.setMessage(index, (AbstractMessage)value);
                }
                return this;
            }
            
            public Builder setDocumentPath(final int index, final DocumentPathItem.Builder builderForValue) {
                if (this.documentPathBuilder_ == null) {
                    this.ensureDocumentPathIsMutable();
                    this.documentPath_.set(index, builderForValue.build());
                    this.onChanged();
                }
                else {
                    this.documentPathBuilder_.setMessage(index, (AbstractMessage)builderForValue.build());
                }
                return this;
            }
            
            public Builder addDocumentPath(final DocumentPathItem value) {
                if (this.documentPathBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureDocumentPathIsMutable();
                    this.documentPath_.add(value);
                    this.onChanged();
                }
                else {
                    this.documentPathBuilder_.addMessage((AbstractMessage)value);
                }
                return this;
            }
            
            public Builder addDocumentPath(final int index, final DocumentPathItem value) {
                if (this.documentPathBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureDocumentPathIsMutable();
                    this.documentPath_.add(index, value);
                    this.onChanged();
                }
                else {
                    this.documentPathBuilder_.addMessage(index, (AbstractMessage)value);
                }
                return this;
            }
            
            public Builder addDocumentPath(final DocumentPathItem.Builder builderForValue) {
                if (this.documentPathBuilder_ == null) {
                    this.ensureDocumentPathIsMutable();
                    this.documentPath_.add(builderForValue.build());
                    this.onChanged();
                }
                else {
                    this.documentPathBuilder_.addMessage((AbstractMessage)builderForValue.build());
                }
                return this;
            }
            
            public Builder addDocumentPath(final int index, final DocumentPathItem.Builder builderForValue) {
                if (this.documentPathBuilder_ == null) {
                    this.ensureDocumentPathIsMutable();
                    this.documentPath_.add(index, builderForValue.build());
                    this.onChanged();
                }
                else {
                    this.documentPathBuilder_.addMessage(index, (AbstractMessage)builderForValue.build());
                }
                return this;
            }
            
            public Builder addAllDocumentPath(final Iterable<? extends DocumentPathItem> values) {
                if (this.documentPathBuilder_ == null) {
                    this.ensureDocumentPathIsMutable();
                    AbstractMessageLite.Builder.addAll((Iterable)values, (List)this.documentPath_);
                    this.onChanged();
                }
                else {
                    this.documentPathBuilder_.addAllMessages((Iterable)values);
                }
                return this;
            }
            
            public Builder clearDocumentPath() {
                if (this.documentPathBuilder_ == null) {
                    this.documentPath_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFFE;
                    this.onChanged();
                }
                else {
                    this.documentPathBuilder_.clear();
                }
                return this;
            }
            
            public Builder removeDocumentPath(final int index) {
                if (this.documentPathBuilder_ == null) {
                    this.ensureDocumentPathIsMutable();
                    this.documentPath_.remove(index);
                    this.onChanged();
                }
                else {
                    this.documentPathBuilder_.remove(index);
                }
                return this;
            }
            
            public DocumentPathItem.Builder getDocumentPathBuilder(final int index) {
                return (DocumentPathItem.Builder)this.getDocumentPathFieldBuilder().getBuilder(index);
            }
            
            public DocumentPathItemOrBuilder getDocumentPathOrBuilder(final int index) {
                if (this.documentPathBuilder_ == null) {
                    return this.documentPath_.get(index);
                }
                return (DocumentPathItemOrBuilder)this.documentPathBuilder_.getMessageOrBuilder(index);
            }
            
            public List<? extends DocumentPathItemOrBuilder> getDocumentPathOrBuilderList() {
                if (this.documentPathBuilder_ != null) {
                    return (List<? extends DocumentPathItemOrBuilder>)this.documentPathBuilder_.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList((List<? extends DocumentPathItemOrBuilder>)this.documentPath_);
            }
            
            public DocumentPathItem.Builder addDocumentPathBuilder() {
                return (DocumentPathItem.Builder)this.getDocumentPathFieldBuilder().addBuilder((AbstractMessage)DocumentPathItem.getDefaultInstance());
            }
            
            public DocumentPathItem.Builder addDocumentPathBuilder(final int index) {
                return (DocumentPathItem.Builder)this.getDocumentPathFieldBuilder().addBuilder(index, (AbstractMessage)DocumentPathItem.getDefaultInstance());
            }
            
            public List<DocumentPathItem.Builder> getDocumentPathBuilderList() {
                return (List<DocumentPathItem.Builder>)this.getDocumentPathFieldBuilder().getBuilderList();
            }
            
            private RepeatedFieldBuilderV3<DocumentPathItem, DocumentPathItem.Builder, DocumentPathItemOrBuilder> getDocumentPathFieldBuilder() {
                if (this.documentPathBuilder_ == null) {
                    this.documentPathBuilder_ = (RepeatedFieldBuilderV3<DocumentPathItem, DocumentPathItem.Builder, DocumentPathItemOrBuilder>)new RepeatedFieldBuilderV3((List)this.documentPath_, (this.bitField0_ & 0x1) == 0x1, (AbstractMessage.BuilderParent)this.getParentForChildren(), this.isClean());
                    this.documentPath_ = null;
                }
                return this.documentPathBuilder_;
            }
            
            public boolean hasName() {
                return (this.bitField0_ & 0x2) == 0x2;
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
                this.bitField0_ |= 0x2;
                this.name_ = value;
                this.onChanged();
                return this;
            }
            
            public Builder clearName() {
                this.bitField0_ &= 0xFFFFFFFD;
                this.name_ = ColumnIdentifier.getDefaultInstance().getName();
                this.onChanged();
                return this;
            }
            
            public Builder setNameBytes(final ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 0x2;
                this.name_ = value;
                this.onChanged();
                return this;
            }
            
            public boolean hasTableName() {
                return (this.bitField0_ & 0x4) == 0x4;
            }
            
            public String getTableName() {
                final Object ref = this.tableName_;
                if (!(ref instanceof String)) {
                    final ByteString bs = (ByteString)ref;
                    final String s = bs.toStringUtf8();
                    if (bs.isValidUtf8()) {
                        this.tableName_ = s;
                    }
                    return s;
                }
                return (String)ref;
            }
            
            public ByteString getTableNameBytes() {
                final Object ref = this.tableName_;
                if (ref instanceof String) {
                    final ByteString b = ByteString.copyFromUtf8((String)ref);
                    return (ByteString)(this.tableName_ = b);
                }
                return (ByteString)ref;
            }
            
            public Builder setTableName(final String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 0x4;
                this.tableName_ = value;
                this.onChanged();
                return this;
            }
            
            public Builder clearTableName() {
                this.bitField0_ &= 0xFFFFFFFB;
                this.tableName_ = ColumnIdentifier.getDefaultInstance().getTableName();
                this.onChanged();
                return this;
            }
            
            public Builder setTableNameBytes(final ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 0x4;
                this.tableName_ = value;
                this.onChanged();
                return this;
            }
            
            public boolean hasSchemaName() {
                return (this.bitField0_ & 0x8) == 0x8;
            }
            
            public String getSchemaName() {
                final Object ref = this.schemaName_;
                if (!(ref instanceof String)) {
                    final ByteString bs = (ByteString)ref;
                    final String s = bs.toStringUtf8();
                    if (bs.isValidUtf8()) {
                        this.schemaName_ = s;
                    }
                    return s;
                }
                return (String)ref;
            }
            
            public ByteString getSchemaNameBytes() {
                final Object ref = this.schemaName_;
                if (ref instanceof String) {
                    final ByteString b = ByteString.copyFromUtf8((String)ref);
                    return (ByteString)(this.schemaName_ = b);
                }
                return (ByteString)ref;
            }
            
            public Builder setSchemaName(final String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 0x8;
                this.schemaName_ = value;
                this.onChanged();
                return this;
            }
            
            public Builder clearSchemaName() {
                this.bitField0_ &= 0xFFFFFFF7;
                this.schemaName_ = ColumnIdentifier.getDefaultInstance().getSchemaName();
                this.onChanged();
                return this;
            }
            
            public Builder setSchemaNameBytes(final ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 0x8;
                this.schemaName_ = value;
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
    
    public static final class FunctionCall extends GeneratedMessageV3 implements FunctionCallOrBuilder
    {
        private static final long serialVersionUID = 0L;
        private int bitField0_;
        public static final int NAME_FIELD_NUMBER = 1;
        private Identifier name_;
        public static final int PARAM_FIELD_NUMBER = 2;
        private List<Expr> param_;
        private byte memoizedIsInitialized;
        private static final FunctionCall DEFAULT_INSTANCE;
        @Deprecated
        public static final Parser<FunctionCall> PARSER;
        
        private FunctionCall(final GeneratedMessageV3.Builder<?> builder) {
            super((GeneratedMessageV3.Builder)builder);
            this.memoizedIsInitialized = -1;
        }
        
        private FunctionCall() {
            this.memoizedIsInitialized = -1;
            this.param_ = Collections.emptyList();
        }
        
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }
        
        private FunctionCall(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            Identifier.Builder subBuilder = null;
                            if ((this.bitField0_ & 0x1) == 0x1) {
                                subBuilder = this.name_.toBuilder();
                            }
                            this.name_ = (Identifier)input.readMessage((Parser)Identifier.PARSER, extensionRegistry);
                            if (subBuilder != null) {
                                subBuilder.mergeFrom(this.name_);
                                this.name_ = subBuilder.buildPartial();
                            }
                            this.bitField0_ |= 0x1;
                            continue;
                        }
                        case 18: {
                            if ((mutable_bitField0_ & 0x2) != 0x2) {
                                this.param_ = new ArrayList<Expr>();
                                mutable_bitField0_ |= 0x2;
                            }
                            this.param_.add((Expr)input.readMessage((Parser)Expr.PARSER, extensionRegistry));
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
                    this.param_ = Collections.unmodifiableList((List<? extends Expr>)this.param_);
                }
                this.unknownFields = unknownFields.build();
                this.makeExtensionsImmutable();
            }
        }
        
        public static final Descriptors.Descriptor getDescriptor() {
            return MysqlxExpr.internal_static_Mysqlx_Expr_FunctionCall_descriptor;
        }
        
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return MysqlxExpr.internal_static_Mysqlx_Expr_FunctionCall_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)FunctionCall.class, (Class)Builder.class);
        }
        
        public boolean hasName() {
            return (this.bitField0_ & 0x1) == 0x1;
        }
        
        public Identifier getName() {
            return (this.name_ == null) ? Identifier.getDefaultInstance() : this.name_;
        }
        
        public IdentifierOrBuilder getNameOrBuilder() {
            return (this.name_ == null) ? Identifier.getDefaultInstance() : this.name_;
        }
        
        public List<Expr> getParamList() {
            return this.param_;
        }
        
        public List<? extends ExprOrBuilder> getParamOrBuilderList() {
            return this.param_;
        }
        
        public int getParamCount() {
            return this.param_.size();
        }
        
        public Expr getParam(final int index) {
            return this.param_.get(index);
        }
        
        public ExprOrBuilder getParamOrBuilder(final int index) {
            return this.param_.get(index);
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
            if (!this.getName().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            for (int i = 0; i < this.getParamCount(); ++i) {
                if (!this.getParam(i).isInitialized()) {
                    this.memoizedIsInitialized = 0;
                    return false;
                }
            }
            this.memoizedIsInitialized = 1;
            return true;
        }
        
        public void writeTo(final CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 0x1) == 0x1) {
                output.writeMessage(1, (MessageLite)this.getName());
            }
            for (int i = 0; i < this.param_.size(); ++i) {
                output.writeMessage(2, (MessageLite)this.param_.get(i));
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
                size += CodedOutputStream.computeMessageSize(1, (MessageLite)this.getName());
            }
            for (int i = 0; i < this.param_.size(); ++i) {
                size += CodedOutputStream.computeMessageSize(2, (MessageLite)this.param_.get(i));
            }
            size += this.unknownFields.getSerializedSize();
            return this.memoizedSize = size;
        }
        
        public boolean equals(final Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof FunctionCall)) {
                return super.equals(obj);
            }
            final FunctionCall other = (FunctionCall)obj;
            boolean result = true;
            result = (result && this.hasName() == other.hasName());
            if (this.hasName()) {
                result = (result && this.getName().equals(other.getName()));
            }
            result = (result && this.getParamList().equals(other.getParamList()));
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
            if (this.getParamCount() > 0) {
                hash = 37 * hash + 2;
                hash = 53 * hash + this.getParamList().hashCode();
            }
            hash = 29 * hash + this.unknownFields.hashCode();
            return this.memoizedHashCode = hash;
        }
        
        public static FunctionCall parseFrom(final ByteBuffer data) throws InvalidProtocolBufferException {
            return (FunctionCall)FunctionCall.PARSER.parseFrom(data);
        }
        
        public static FunctionCall parseFrom(final ByteBuffer data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (FunctionCall)FunctionCall.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static FunctionCall parseFrom(final ByteString data) throws InvalidProtocolBufferException {
            return (FunctionCall)FunctionCall.PARSER.parseFrom(data);
        }
        
        public static FunctionCall parseFrom(final ByteString data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (FunctionCall)FunctionCall.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static FunctionCall parseFrom(final byte[] data) throws InvalidProtocolBufferException {
            return (FunctionCall)FunctionCall.PARSER.parseFrom(data);
        }
        
        public static FunctionCall parseFrom(final byte[] data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (FunctionCall)FunctionCall.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static FunctionCall parseFrom(final InputStream input) throws IOException {
            return (FunctionCall)GeneratedMessageV3.parseWithIOException((Parser)FunctionCall.PARSER, input);
        }
        
        public static FunctionCall parseFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (FunctionCall)GeneratedMessageV3.parseWithIOException((Parser)FunctionCall.PARSER, input, extensionRegistry);
        }
        
        public static FunctionCall parseDelimitedFrom(final InputStream input) throws IOException {
            return (FunctionCall)GeneratedMessageV3.parseDelimitedWithIOException((Parser)FunctionCall.PARSER, input);
        }
        
        public static FunctionCall parseDelimitedFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (FunctionCall)GeneratedMessageV3.parseDelimitedWithIOException((Parser)FunctionCall.PARSER, input, extensionRegistry);
        }
        
        public static FunctionCall parseFrom(final CodedInputStream input) throws IOException {
            return (FunctionCall)GeneratedMessageV3.parseWithIOException((Parser)FunctionCall.PARSER, input);
        }
        
        public static FunctionCall parseFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (FunctionCall)GeneratedMessageV3.parseWithIOException((Parser)FunctionCall.PARSER, input, extensionRegistry);
        }
        
        public Builder newBuilderForType() {
            return newBuilder();
        }
        
        public static Builder newBuilder() {
            return FunctionCall.DEFAULT_INSTANCE.toBuilder();
        }
        
        public static Builder newBuilder(final FunctionCall prototype) {
            return FunctionCall.DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }
        
        public Builder toBuilder() {
            return (this == FunctionCall.DEFAULT_INSTANCE) ? new Builder() : new Builder().mergeFrom(this);
        }
        
        protected Builder newBuilderForType(final GeneratedMessageV3.BuilderParent parent) {
            final Builder builder = new Builder(parent);
            return builder;
        }
        
        public static FunctionCall getDefaultInstance() {
            return FunctionCall.DEFAULT_INSTANCE;
        }
        
        public static Parser<FunctionCall> parser() {
            return FunctionCall.PARSER;
        }
        
        public Parser<FunctionCall> getParserForType() {
            return FunctionCall.PARSER;
        }
        
        public FunctionCall getDefaultInstanceForType() {
            return FunctionCall.DEFAULT_INSTANCE;
        }
        
        static {
            DEFAULT_INSTANCE = new FunctionCall();
            PARSER = (Parser)new AbstractParser<FunctionCall>() {
                public FunctionCall parsePartialFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new FunctionCall(input, extensionRegistry);
                }
            };
        }
        
        public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements FunctionCallOrBuilder
        {
            private int bitField0_;
            private Identifier name_;
            private SingleFieldBuilderV3<Identifier, Identifier.Builder, IdentifierOrBuilder> nameBuilder_;
            private List<Expr> param_;
            private RepeatedFieldBuilderV3<Expr, Expr.Builder, ExprOrBuilder> paramBuilder_;
            
            public static final Descriptors.Descriptor getDescriptor() {
                return MysqlxExpr.internal_static_Mysqlx_Expr_FunctionCall_descriptor;
            }
            
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return MysqlxExpr.internal_static_Mysqlx_Expr_FunctionCall_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)FunctionCall.class, (Class)Builder.class);
            }
            
            private Builder() {
                this.name_ = null;
                this.param_ = Collections.emptyList();
                this.maybeForceBuilderInitialization();
            }
            
            private Builder(final GeneratedMessageV3.BuilderParent parent) {
                super(parent);
                this.name_ = null;
                this.param_ = Collections.emptyList();
                this.maybeForceBuilderInitialization();
            }
            
            private void maybeForceBuilderInitialization() {
                if (FunctionCall.alwaysUseFieldBuilders) {
                    this.getNameFieldBuilder();
                    this.getParamFieldBuilder();
                }
            }
            
            public Builder clear() {
                super.clear();
                if (this.nameBuilder_ == null) {
                    this.name_ = null;
                }
                else {
                    this.nameBuilder_.clear();
                }
                this.bitField0_ &= 0xFFFFFFFE;
                if (this.paramBuilder_ == null) {
                    this.param_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFFD;
                }
                else {
                    this.paramBuilder_.clear();
                }
                return this;
            }
            
            public Descriptors.Descriptor getDescriptorForType() {
                return MysqlxExpr.internal_static_Mysqlx_Expr_FunctionCall_descriptor;
            }
            
            public FunctionCall getDefaultInstanceForType() {
                return FunctionCall.getDefaultInstance();
            }
            
            public FunctionCall build() {
                final FunctionCall result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw newUninitializedMessageException((Message)result);
                }
                return result;
            }
            
            public FunctionCall buildPartial() {
                final FunctionCall result = new FunctionCall((GeneratedMessageV3.Builder)this);
                final int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 0x1) == 0x1) {
                    to_bitField0_ |= 0x1;
                }
                if (this.nameBuilder_ == null) {
                    result.name_ = this.name_;
                }
                else {
                    result.name_ = (Identifier)this.nameBuilder_.build();
                }
                if (this.paramBuilder_ == null) {
                    if ((this.bitField0_ & 0x2) == 0x2) {
                        this.param_ = Collections.unmodifiableList((List<? extends Expr>)this.param_);
                        this.bitField0_ &= 0xFFFFFFFD;
                    }
                    result.param_ = this.param_;
                }
                else {
                    result.param_ = (List<Expr>)this.paramBuilder_.build();
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
                if (other instanceof FunctionCall) {
                    return this.mergeFrom((FunctionCall)other);
                }
                super.mergeFrom(other);
                return this;
            }
            
            public Builder mergeFrom(final FunctionCall other) {
                if (other == FunctionCall.getDefaultInstance()) {
                    return this;
                }
                if (other.hasName()) {
                    this.mergeName(other.getName());
                }
                if (this.paramBuilder_ == null) {
                    if (!other.param_.isEmpty()) {
                        if (this.param_.isEmpty()) {
                            this.param_ = other.param_;
                            this.bitField0_ &= 0xFFFFFFFD;
                        }
                        else {
                            this.ensureParamIsMutable();
                            this.param_.addAll(other.param_);
                        }
                        this.onChanged();
                    }
                }
                else if (!other.param_.isEmpty()) {
                    if (this.paramBuilder_.isEmpty()) {
                        this.paramBuilder_.dispose();
                        this.paramBuilder_ = null;
                        this.param_ = other.param_;
                        this.bitField0_ &= 0xFFFFFFFD;
                        this.paramBuilder_ = (FunctionCall.alwaysUseFieldBuilders ? this.getParamFieldBuilder() : null);
                    }
                    else {
                        this.paramBuilder_.addAllMessages((Iterable)other.param_);
                    }
                }
                this.mergeUnknownFields(other.unknownFields);
                this.onChanged();
                return this;
            }
            
            public final boolean isInitialized() {
                if (!this.hasName()) {
                    return false;
                }
                if (!this.getName().isInitialized()) {
                    return false;
                }
                for (int i = 0; i < this.getParamCount(); ++i) {
                    if (!this.getParam(i).isInitialized()) {
                        return false;
                    }
                }
                return true;
            }
            
            public Builder mergeFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
                FunctionCall parsedMessage = null;
                try {
                    parsedMessage = (FunctionCall)FunctionCall.PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (FunctionCall)e.getUnfinishedMessage();
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
            
            public Identifier getName() {
                if (this.nameBuilder_ == null) {
                    return (this.name_ == null) ? Identifier.getDefaultInstance() : this.name_;
                }
                return (Identifier)this.nameBuilder_.getMessage();
            }
            
            public Builder setName(final Identifier value) {
                if (this.nameBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.name_ = value;
                    this.onChanged();
                }
                else {
                    this.nameBuilder_.setMessage((AbstractMessage)value);
                }
                this.bitField0_ |= 0x1;
                return this;
            }
            
            public Builder setName(final Identifier.Builder builderForValue) {
                if (this.nameBuilder_ == null) {
                    this.name_ = builderForValue.build();
                    this.onChanged();
                }
                else {
                    this.nameBuilder_.setMessage((AbstractMessage)builderForValue.build());
                }
                this.bitField0_ |= 0x1;
                return this;
            }
            
            public Builder mergeName(final Identifier value) {
                if (this.nameBuilder_ == null) {
                    if ((this.bitField0_ & 0x1) == 0x1 && this.name_ != null && this.name_ != Identifier.getDefaultInstance()) {
                        this.name_ = Identifier.newBuilder(this.name_).mergeFrom(value).buildPartial();
                    }
                    else {
                        this.name_ = value;
                    }
                    this.onChanged();
                }
                else {
                    this.nameBuilder_.mergeFrom((AbstractMessage)value);
                }
                this.bitField0_ |= 0x1;
                return this;
            }
            
            public Builder clearName() {
                if (this.nameBuilder_ == null) {
                    this.name_ = null;
                    this.onChanged();
                }
                else {
                    this.nameBuilder_.clear();
                }
                this.bitField0_ &= 0xFFFFFFFE;
                return this;
            }
            
            public Identifier.Builder getNameBuilder() {
                this.bitField0_ |= 0x1;
                this.onChanged();
                return (Identifier.Builder)this.getNameFieldBuilder().getBuilder();
            }
            
            public IdentifierOrBuilder getNameOrBuilder() {
                if (this.nameBuilder_ != null) {
                    return (IdentifierOrBuilder)this.nameBuilder_.getMessageOrBuilder();
                }
                return (this.name_ == null) ? Identifier.getDefaultInstance() : this.name_;
            }
            
            private SingleFieldBuilderV3<Identifier, Identifier.Builder, IdentifierOrBuilder> getNameFieldBuilder() {
                if (this.nameBuilder_ == null) {
                    this.nameBuilder_ = (SingleFieldBuilderV3<Identifier, Identifier.Builder, IdentifierOrBuilder>)new SingleFieldBuilderV3((AbstractMessage)this.getName(), (AbstractMessage.BuilderParent)this.getParentForChildren(), this.isClean());
                    this.name_ = null;
                }
                return this.nameBuilder_;
            }
            
            private void ensureParamIsMutable() {
                if ((this.bitField0_ & 0x2) != 0x2) {
                    this.param_ = new ArrayList<Expr>(this.param_);
                    this.bitField0_ |= 0x2;
                }
            }
            
            public List<Expr> getParamList() {
                if (this.paramBuilder_ == null) {
                    return Collections.unmodifiableList((List<? extends Expr>)this.param_);
                }
                return (List<Expr>)this.paramBuilder_.getMessageList();
            }
            
            public int getParamCount() {
                if (this.paramBuilder_ == null) {
                    return this.param_.size();
                }
                return this.paramBuilder_.getCount();
            }
            
            public Expr getParam(final int index) {
                if (this.paramBuilder_ == null) {
                    return this.param_.get(index);
                }
                return (Expr)this.paramBuilder_.getMessage(index);
            }
            
            public Builder setParam(final int index, final Expr value) {
                if (this.paramBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureParamIsMutable();
                    this.param_.set(index, value);
                    this.onChanged();
                }
                else {
                    this.paramBuilder_.setMessage(index, (AbstractMessage)value);
                }
                return this;
            }
            
            public Builder setParam(final int index, final Expr.Builder builderForValue) {
                if (this.paramBuilder_ == null) {
                    this.ensureParamIsMutable();
                    this.param_.set(index, builderForValue.build());
                    this.onChanged();
                }
                else {
                    this.paramBuilder_.setMessage(index, (AbstractMessage)builderForValue.build());
                }
                return this;
            }
            
            public Builder addParam(final Expr value) {
                if (this.paramBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureParamIsMutable();
                    this.param_.add(value);
                    this.onChanged();
                }
                else {
                    this.paramBuilder_.addMessage((AbstractMessage)value);
                }
                return this;
            }
            
            public Builder addParam(final int index, final Expr value) {
                if (this.paramBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureParamIsMutable();
                    this.param_.add(index, value);
                    this.onChanged();
                }
                else {
                    this.paramBuilder_.addMessage(index, (AbstractMessage)value);
                }
                return this;
            }
            
            public Builder addParam(final Expr.Builder builderForValue) {
                if (this.paramBuilder_ == null) {
                    this.ensureParamIsMutable();
                    this.param_.add(builderForValue.build());
                    this.onChanged();
                }
                else {
                    this.paramBuilder_.addMessage((AbstractMessage)builderForValue.build());
                }
                return this;
            }
            
            public Builder addParam(final int index, final Expr.Builder builderForValue) {
                if (this.paramBuilder_ == null) {
                    this.ensureParamIsMutable();
                    this.param_.add(index, builderForValue.build());
                    this.onChanged();
                }
                else {
                    this.paramBuilder_.addMessage(index, (AbstractMessage)builderForValue.build());
                }
                return this;
            }
            
            public Builder addAllParam(final Iterable<? extends Expr> values) {
                if (this.paramBuilder_ == null) {
                    this.ensureParamIsMutable();
                    AbstractMessageLite.Builder.addAll((Iterable)values, (List)this.param_);
                    this.onChanged();
                }
                else {
                    this.paramBuilder_.addAllMessages((Iterable)values);
                }
                return this;
            }
            
            public Builder clearParam() {
                if (this.paramBuilder_ == null) {
                    this.param_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFFD;
                    this.onChanged();
                }
                else {
                    this.paramBuilder_.clear();
                }
                return this;
            }
            
            public Builder removeParam(final int index) {
                if (this.paramBuilder_ == null) {
                    this.ensureParamIsMutable();
                    this.param_.remove(index);
                    this.onChanged();
                }
                else {
                    this.paramBuilder_.remove(index);
                }
                return this;
            }
            
            public Expr.Builder getParamBuilder(final int index) {
                return (Expr.Builder)this.getParamFieldBuilder().getBuilder(index);
            }
            
            public ExprOrBuilder getParamOrBuilder(final int index) {
                if (this.paramBuilder_ == null) {
                    return this.param_.get(index);
                }
                return (ExprOrBuilder)this.paramBuilder_.getMessageOrBuilder(index);
            }
            
            public List<? extends ExprOrBuilder> getParamOrBuilderList() {
                if (this.paramBuilder_ != null) {
                    return (List<? extends ExprOrBuilder>)this.paramBuilder_.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList((List<? extends ExprOrBuilder>)this.param_);
            }
            
            public Expr.Builder addParamBuilder() {
                return (Expr.Builder)this.getParamFieldBuilder().addBuilder((AbstractMessage)Expr.getDefaultInstance());
            }
            
            public Expr.Builder addParamBuilder(final int index) {
                return (Expr.Builder)this.getParamFieldBuilder().addBuilder(index, (AbstractMessage)Expr.getDefaultInstance());
            }
            
            public List<Expr.Builder> getParamBuilderList() {
                return (List<Expr.Builder>)this.getParamFieldBuilder().getBuilderList();
            }
            
            private RepeatedFieldBuilderV3<Expr, Expr.Builder, ExprOrBuilder> getParamFieldBuilder() {
                if (this.paramBuilder_ == null) {
                    this.paramBuilder_ = (RepeatedFieldBuilderV3<Expr, Expr.Builder, ExprOrBuilder>)new RepeatedFieldBuilderV3((List)this.param_, (this.bitField0_ & 0x2) == 0x2, (AbstractMessage.BuilderParent)this.getParentForChildren(), this.isClean());
                    this.param_ = null;
                }
                return this.paramBuilder_;
            }
            
            public final Builder setUnknownFields(final UnknownFieldSet unknownFields) {
                return (Builder)super.setUnknownFields(unknownFields);
            }
            
            public final Builder mergeUnknownFields(final UnknownFieldSet unknownFields) {
                return (Builder)super.mergeUnknownFields(unknownFields);
            }
        }
    }
    
    public static final class Operator extends GeneratedMessageV3 implements OperatorOrBuilder
    {
        private static final long serialVersionUID = 0L;
        private int bitField0_;
        public static final int NAME_FIELD_NUMBER = 1;
        private volatile Object name_;
        public static final int PARAM_FIELD_NUMBER = 2;
        private List<Expr> param_;
        private byte memoizedIsInitialized;
        private static final Operator DEFAULT_INSTANCE;
        @Deprecated
        public static final Parser<Operator> PARSER;
        
        private Operator(final GeneratedMessageV3.Builder<?> builder) {
            super((GeneratedMessageV3.Builder)builder);
            this.memoizedIsInitialized = -1;
        }
        
        private Operator() {
            this.memoizedIsInitialized = -1;
            this.name_ = "";
            this.param_ = Collections.emptyList();
        }
        
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }
        
        private Operator(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            final ByteString bs = input.readBytes();
                            this.bitField0_ |= 0x1;
                            this.name_ = bs;
                            continue;
                        }
                        case 18: {
                            if ((mutable_bitField0_ & 0x2) != 0x2) {
                                this.param_ = new ArrayList<Expr>();
                                mutable_bitField0_ |= 0x2;
                            }
                            this.param_.add((Expr)input.readMessage((Parser)Expr.PARSER, extensionRegistry));
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
                    this.param_ = Collections.unmodifiableList((List<? extends Expr>)this.param_);
                }
                this.unknownFields = unknownFields.build();
                this.makeExtensionsImmutable();
            }
        }
        
        public static final Descriptors.Descriptor getDescriptor() {
            return MysqlxExpr.internal_static_Mysqlx_Expr_Operator_descriptor;
        }
        
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return MysqlxExpr.internal_static_Mysqlx_Expr_Operator_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)Operator.class, (Class)Builder.class);
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
        
        public List<Expr> getParamList() {
            return this.param_;
        }
        
        public List<? extends ExprOrBuilder> getParamOrBuilderList() {
            return this.param_;
        }
        
        public int getParamCount() {
            return this.param_.size();
        }
        
        public Expr getParam(final int index) {
            return this.param_.get(index);
        }
        
        public ExprOrBuilder getParamOrBuilder(final int index) {
            return this.param_.get(index);
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
            for (int i = 0; i < this.getParamCount(); ++i) {
                if (!this.getParam(i).isInitialized()) {
                    this.memoizedIsInitialized = 0;
                    return false;
                }
            }
            this.memoizedIsInitialized = 1;
            return true;
        }
        
        public void writeTo(final CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 0x1) == 0x1) {
                GeneratedMessageV3.writeString(output, 1, this.name_);
            }
            for (int i = 0; i < this.param_.size(); ++i) {
                output.writeMessage(2, (MessageLite)this.param_.get(i));
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
            for (int i = 0; i < this.param_.size(); ++i) {
                size += CodedOutputStream.computeMessageSize(2, (MessageLite)this.param_.get(i));
            }
            size += this.unknownFields.getSerializedSize();
            return this.memoizedSize = size;
        }
        
        public boolean equals(final Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Operator)) {
                return super.equals(obj);
            }
            final Operator other = (Operator)obj;
            boolean result = true;
            result = (result && this.hasName() == other.hasName());
            if (this.hasName()) {
                result = (result && this.getName().equals(other.getName()));
            }
            result = (result && this.getParamList().equals(other.getParamList()));
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
            if (this.getParamCount() > 0) {
                hash = 37 * hash + 2;
                hash = 53 * hash + this.getParamList().hashCode();
            }
            hash = 29 * hash + this.unknownFields.hashCode();
            return this.memoizedHashCode = hash;
        }
        
        public static Operator parseFrom(final ByteBuffer data) throws InvalidProtocolBufferException {
            return (Operator)Operator.PARSER.parseFrom(data);
        }
        
        public static Operator parseFrom(final ByteBuffer data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Operator)Operator.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Operator parseFrom(final ByteString data) throws InvalidProtocolBufferException {
            return (Operator)Operator.PARSER.parseFrom(data);
        }
        
        public static Operator parseFrom(final ByteString data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Operator)Operator.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Operator parseFrom(final byte[] data) throws InvalidProtocolBufferException {
            return (Operator)Operator.PARSER.parseFrom(data);
        }
        
        public static Operator parseFrom(final byte[] data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Operator)Operator.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Operator parseFrom(final InputStream input) throws IOException {
            return (Operator)GeneratedMessageV3.parseWithIOException((Parser)Operator.PARSER, input);
        }
        
        public static Operator parseFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Operator)GeneratedMessageV3.parseWithIOException((Parser)Operator.PARSER, input, extensionRegistry);
        }
        
        public static Operator parseDelimitedFrom(final InputStream input) throws IOException {
            return (Operator)GeneratedMessageV3.parseDelimitedWithIOException((Parser)Operator.PARSER, input);
        }
        
        public static Operator parseDelimitedFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Operator)GeneratedMessageV3.parseDelimitedWithIOException((Parser)Operator.PARSER, input, extensionRegistry);
        }
        
        public static Operator parseFrom(final CodedInputStream input) throws IOException {
            return (Operator)GeneratedMessageV3.parseWithIOException((Parser)Operator.PARSER, input);
        }
        
        public static Operator parseFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Operator)GeneratedMessageV3.parseWithIOException((Parser)Operator.PARSER, input, extensionRegistry);
        }
        
        public Builder newBuilderForType() {
            return newBuilder();
        }
        
        public static Builder newBuilder() {
            return Operator.DEFAULT_INSTANCE.toBuilder();
        }
        
        public static Builder newBuilder(final Operator prototype) {
            return Operator.DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }
        
        public Builder toBuilder() {
            return (this == Operator.DEFAULT_INSTANCE) ? new Builder() : new Builder().mergeFrom(this);
        }
        
        protected Builder newBuilderForType(final GeneratedMessageV3.BuilderParent parent) {
            final Builder builder = new Builder(parent);
            return builder;
        }
        
        public static Operator getDefaultInstance() {
            return Operator.DEFAULT_INSTANCE;
        }
        
        public static Parser<Operator> parser() {
            return Operator.PARSER;
        }
        
        public Parser<Operator> getParserForType() {
            return Operator.PARSER;
        }
        
        public Operator getDefaultInstanceForType() {
            return Operator.DEFAULT_INSTANCE;
        }
        
        static {
            DEFAULT_INSTANCE = new Operator();
            PARSER = (Parser)new AbstractParser<Operator>() {
                public Operator parsePartialFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new Operator(input, extensionRegistry);
                }
            };
        }
        
        public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements OperatorOrBuilder
        {
            private int bitField0_;
            private Object name_;
            private List<Expr> param_;
            private RepeatedFieldBuilderV3<Expr, Expr.Builder, ExprOrBuilder> paramBuilder_;
            
            public static final Descriptors.Descriptor getDescriptor() {
                return MysqlxExpr.internal_static_Mysqlx_Expr_Operator_descriptor;
            }
            
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return MysqlxExpr.internal_static_Mysqlx_Expr_Operator_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)Operator.class, (Class)Builder.class);
            }
            
            private Builder() {
                this.name_ = "";
                this.param_ = Collections.emptyList();
                this.maybeForceBuilderInitialization();
            }
            
            private Builder(final GeneratedMessageV3.BuilderParent parent) {
                super(parent);
                this.name_ = "";
                this.param_ = Collections.emptyList();
                this.maybeForceBuilderInitialization();
            }
            
            private void maybeForceBuilderInitialization() {
                if (Operator.alwaysUseFieldBuilders) {
                    this.getParamFieldBuilder();
                }
            }
            
            public Builder clear() {
                super.clear();
                this.name_ = "";
                this.bitField0_ &= 0xFFFFFFFE;
                if (this.paramBuilder_ == null) {
                    this.param_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFFD;
                }
                else {
                    this.paramBuilder_.clear();
                }
                return this;
            }
            
            public Descriptors.Descriptor getDescriptorForType() {
                return MysqlxExpr.internal_static_Mysqlx_Expr_Operator_descriptor;
            }
            
            public Operator getDefaultInstanceForType() {
                return Operator.getDefaultInstance();
            }
            
            public Operator build() {
                final Operator result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw newUninitializedMessageException((Message)result);
                }
                return result;
            }
            
            public Operator buildPartial() {
                final Operator result = new Operator((GeneratedMessageV3.Builder)this);
                final int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 0x1) == 0x1) {
                    to_bitField0_ |= 0x1;
                }
                result.name_ = this.name_;
                if (this.paramBuilder_ == null) {
                    if ((this.bitField0_ & 0x2) == 0x2) {
                        this.param_ = Collections.unmodifiableList((List<? extends Expr>)this.param_);
                        this.bitField0_ &= 0xFFFFFFFD;
                    }
                    result.param_ = this.param_;
                }
                else {
                    result.param_ = (List<Expr>)this.paramBuilder_.build();
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
                if (other instanceof Operator) {
                    return this.mergeFrom((Operator)other);
                }
                super.mergeFrom(other);
                return this;
            }
            
            public Builder mergeFrom(final Operator other) {
                if (other == Operator.getDefaultInstance()) {
                    return this;
                }
                if (other.hasName()) {
                    this.bitField0_ |= 0x1;
                    this.name_ = other.name_;
                    this.onChanged();
                }
                if (this.paramBuilder_ == null) {
                    if (!other.param_.isEmpty()) {
                        if (this.param_.isEmpty()) {
                            this.param_ = other.param_;
                            this.bitField0_ &= 0xFFFFFFFD;
                        }
                        else {
                            this.ensureParamIsMutable();
                            this.param_.addAll(other.param_);
                        }
                        this.onChanged();
                    }
                }
                else if (!other.param_.isEmpty()) {
                    if (this.paramBuilder_.isEmpty()) {
                        this.paramBuilder_.dispose();
                        this.paramBuilder_ = null;
                        this.param_ = other.param_;
                        this.bitField0_ &= 0xFFFFFFFD;
                        this.paramBuilder_ = (Operator.alwaysUseFieldBuilders ? this.getParamFieldBuilder() : null);
                    }
                    else {
                        this.paramBuilder_.addAllMessages((Iterable)other.param_);
                    }
                }
                this.mergeUnknownFields(other.unknownFields);
                this.onChanged();
                return this;
            }
            
            public final boolean isInitialized() {
                if (!this.hasName()) {
                    return false;
                }
                for (int i = 0; i < this.getParamCount(); ++i) {
                    if (!this.getParam(i).isInitialized()) {
                        return false;
                    }
                }
                return true;
            }
            
            public Builder mergeFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
                Operator parsedMessage = null;
                try {
                    parsedMessage = (Operator)Operator.PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (Operator)e.getUnfinishedMessage();
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
                this.name_ = Operator.getDefaultInstance().getName();
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
            
            private void ensureParamIsMutable() {
                if ((this.bitField0_ & 0x2) != 0x2) {
                    this.param_ = new ArrayList<Expr>(this.param_);
                    this.bitField0_ |= 0x2;
                }
            }
            
            public List<Expr> getParamList() {
                if (this.paramBuilder_ == null) {
                    return Collections.unmodifiableList((List<? extends Expr>)this.param_);
                }
                return (List<Expr>)this.paramBuilder_.getMessageList();
            }
            
            public int getParamCount() {
                if (this.paramBuilder_ == null) {
                    return this.param_.size();
                }
                return this.paramBuilder_.getCount();
            }
            
            public Expr getParam(final int index) {
                if (this.paramBuilder_ == null) {
                    return this.param_.get(index);
                }
                return (Expr)this.paramBuilder_.getMessage(index);
            }
            
            public Builder setParam(final int index, final Expr value) {
                if (this.paramBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureParamIsMutable();
                    this.param_.set(index, value);
                    this.onChanged();
                }
                else {
                    this.paramBuilder_.setMessage(index, (AbstractMessage)value);
                }
                return this;
            }
            
            public Builder setParam(final int index, final Expr.Builder builderForValue) {
                if (this.paramBuilder_ == null) {
                    this.ensureParamIsMutable();
                    this.param_.set(index, builderForValue.build());
                    this.onChanged();
                }
                else {
                    this.paramBuilder_.setMessage(index, (AbstractMessage)builderForValue.build());
                }
                return this;
            }
            
            public Builder addParam(final Expr value) {
                if (this.paramBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureParamIsMutable();
                    this.param_.add(value);
                    this.onChanged();
                }
                else {
                    this.paramBuilder_.addMessage((AbstractMessage)value);
                }
                return this;
            }
            
            public Builder addParam(final int index, final Expr value) {
                if (this.paramBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureParamIsMutable();
                    this.param_.add(index, value);
                    this.onChanged();
                }
                else {
                    this.paramBuilder_.addMessage(index, (AbstractMessage)value);
                }
                return this;
            }
            
            public Builder addParam(final Expr.Builder builderForValue) {
                if (this.paramBuilder_ == null) {
                    this.ensureParamIsMutable();
                    this.param_.add(builderForValue.build());
                    this.onChanged();
                }
                else {
                    this.paramBuilder_.addMessage((AbstractMessage)builderForValue.build());
                }
                return this;
            }
            
            public Builder addParam(final int index, final Expr.Builder builderForValue) {
                if (this.paramBuilder_ == null) {
                    this.ensureParamIsMutable();
                    this.param_.add(index, builderForValue.build());
                    this.onChanged();
                }
                else {
                    this.paramBuilder_.addMessage(index, (AbstractMessage)builderForValue.build());
                }
                return this;
            }
            
            public Builder addAllParam(final Iterable<? extends Expr> values) {
                if (this.paramBuilder_ == null) {
                    this.ensureParamIsMutable();
                    AbstractMessageLite.Builder.addAll((Iterable)values, (List)this.param_);
                    this.onChanged();
                }
                else {
                    this.paramBuilder_.addAllMessages((Iterable)values);
                }
                return this;
            }
            
            public Builder clearParam() {
                if (this.paramBuilder_ == null) {
                    this.param_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFFD;
                    this.onChanged();
                }
                else {
                    this.paramBuilder_.clear();
                }
                return this;
            }
            
            public Builder removeParam(final int index) {
                if (this.paramBuilder_ == null) {
                    this.ensureParamIsMutable();
                    this.param_.remove(index);
                    this.onChanged();
                }
                else {
                    this.paramBuilder_.remove(index);
                }
                return this;
            }
            
            public Expr.Builder getParamBuilder(final int index) {
                return (Expr.Builder)this.getParamFieldBuilder().getBuilder(index);
            }
            
            public ExprOrBuilder getParamOrBuilder(final int index) {
                if (this.paramBuilder_ == null) {
                    return this.param_.get(index);
                }
                return (ExprOrBuilder)this.paramBuilder_.getMessageOrBuilder(index);
            }
            
            public List<? extends ExprOrBuilder> getParamOrBuilderList() {
                if (this.paramBuilder_ != null) {
                    return (List<? extends ExprOrBuilder>)this.paramBuilder_.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList((List<? extends ExprOrBuilder>)this.param_);
            }
            
            public Expr.Builder addParamBuilder() {
                return (Expr.Builder)this.getParamFieldBuilder().addBuilder((AbstractMessage)Expr.getDefaultInstance());
            }
            
            public Expr.Builder addParamBuilder(final int index) {
                return (Expr.Builder)this.getParamFieldBuilder().addBuilder(index, (AbstractMessage)Expr.getDefaultInstance());
            }
            
            public List<Expr.Builder> getParamBuilderList() {
                return (List<Expr.Builder>)this.getParamFieldBuilder().getBuilderList();
            }
            
            private RepeatedFieldBuilderV3<Expr, Expr.Builder, ExprOrBuilder> getParamFieldBuilder() {
                if (this.paramBuilder_ == null) {
                    this.paramBuilder_ = (RepeatedFieldBuilderV3<Expr, Expr.Builder, ExprOrBuilder>)new RepeatedFieldBuilderV3((List)this.param_, (this.bitField0_ & 0x2) == 0x2, (AbstractMessage.BuilderParent)this.getParentForChildren(), this.isClean());
                    this.param_ = null;
                }
                return this.paramBuilder_;
            }
            
            public final Builder setUnknownFields(final UnknownFieldSet unknownFields) {
                return (Builder)super.setUnknownFields(unknownFields);
            }
            
            public final Builder mergeUnknownFields(final UnknownFieldSet unknownFields) {
                return (Builder)super.mergeUnknownFields(unknownFields);
            }
        }
    }
    
    public static final class Object extends GeneratedMessageV3 implements ObjectOrBuilder
    {
        private static final long serialVersionUID = 0L;
        public static final int FLD_FIELD_NUMBER = 1;
        private List<ObjectField> fld_;
        private byte memoizedIsInitialized;
        private static final Object DEFAULT_INSTANCE;
        @Deprecated
        public static final Parser<Object> PARSER;
        
        private Object(final GeneratedMessageV3.Builder<?> builder) {
            super((GeneratedMessageV3.Builder)builder);
            this.memoizedIsInitialized = -1;
        }
        
        private Object() {
            this.memoizedIsInitialized = -1;
            this.fld_ = Collections.emptyList();
        }
        
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }
        
        private Object(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                                this.fld_ = new ArrayList<ObjectField>();
                                mutable_bitField0_ |= 0x1;
                            }
                            this.fld_.add((ObjectField)input.readMessage((Parser)ObjectField.PARSER, extensionRegistry));
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
                    this.fld_ = Collections.unmodifiableList((List<? extends ObjectField>)this.fld_);
                }
                this.unknownFields = unknownFields.build();
                this.makeExtensionsImmutable();
            }
        }
        
        public static final Descriptors.Descriptor getDescriptor() {
            return MysqlxExpr.internal_static_Mysqlx_Expr_Object_descriptor;
        }
        
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return MysqlxExpr.internal_static_Mysqlx_Expr_Object_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)Object.class, (Class)Builder.class);
        }
        
        public List<ObjectField> getFldList() {
            return this.fld_;
        }
        
        public List<? extends ObjectFieldOrBuilder> getFldOrBuilderList() {
            return this.fld_;
        }
        
        public int getFldCount() {
            return this.fld_.size();
        }
        
        public ObjectField getFld(final int index) {
            return this.fld_.get(index);
        }
        
        public ObjectFieldOrBuilder getFldOrBuilder(final int index) {
            return this.fld_.get(index);
        }
        
        public final boolean isInitialized() {
            final byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            for (int i = 0; i < this.getFldCount(); ++i) {
                if (!this.getFld(i).isInitialized()) {
                    this.memoizedIsInitialized = 0;
                    return false;
                }
            }
            this.memoizedIsInitialized = 1;
            return true;
        }
        
        public void writeTo(final CodedOutputStream output) throws IOException {
            for (int i = 0; i < this.fld_.size(); ++i) {
                output.writeMessage(1, (MessageLite)this.fld_.get(i));
            }
            this.unknownFields.writeTo(output);
        }
        
        public int getSerializedSize() {
            int size = this.memoizedSize;
            if (size != -1) {
                return size;
            }
            size = 0;
            for (int i = 0; i < this.fld_.size(); ++i) {
                size += CodedOutputStream.computeMessageSize(1, (MessageLite)this.fld_.get(i));
            }
            size += this.unknownFields.getSerializedSize();
            return this.memoizedSize = size;
        }
        
        public boolean equals(final java.lang.Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Object)) {
                return super.equals(obj);
            }
            final Object other = (Object)obj;
            boolean result = true;
            result = (result && this.getFldList().equals(other.getFldList()));
            result = (result && this.unknownFields.equals((java.lang.Object)other.unknownFields));
            return result;
        }
        
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + getDescriptor().hashCode();
            if (this.getFldCount() > 0) {
                hash = 37 * hash + 1;
                hash = 53 * hash + this.getFldList().hashCode();
            }
            hash = 29 * hash + this.unknownFields.hashCode();
            return this.memoizedHashCode = hash;
        }
        
        public static Object parseFrom(final ByteBuffer data) throws InvalidProtocolBufferException {
            return (Object)Object.PARSER.parseFrom(data);
        }
        
        public static Object parseFrom(final ByteBuffer data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Object)Object.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Object parseFrom(final ByteString data) throws InvalidProtocolBufferException {
            return (Object)Object.PARSER.parseFrom(data);
        }
        
        public static Object parseFrom(final ByteString data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Object)Object.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Object parseFrom(final byte[] data) throws InvalidProtocolBufferException {
            return (Object)Object.PARSER.parseFrom(data);
        }
        
        public static Object parseFrom(final byte[] data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Object)Object.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Object parseFrom(final InputStream input) throws IOException {
            return (Object)GeneratedMessageV3.parseWithIOException((Parser)Object.PARSER, input);
        }
        
        public static Object parseFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Object)GeneratedMessageV3.parseWithIOException((Parser)Object.PARSER, input, extensionRegistry);
        }
        
        public static Object parseDelimitedFrom(final InputStream input) throws IOException {
            return (Object)GeneratedMessageV3.parseDelimitedWithIOException((Parser)Object.PARSER, input);
        }
        
        public static Object parseDelimitedFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Object)GeneratedMessageV3.parseDelimitedWithIOException((Parser)Object.PARSER, input, extensionRegistry);
        }
        
        public static Object parseFrom(final CodedInputStream input) throws IOException {
            return (Object)GeneratedMessageV3.parseWithIOException((Parser)Object.PARSER, input);
        }
        
        public static Object parseFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Object)GeneratedMessageV3.parseWithIOException((Parser)Object.PARSER, input, extensionRegistry);
        }
        
        public Builder newBuilderForType() {
            return newBuilder();
        }
        
        public static Builder newBuilder() {
            return Object.DEFAULT_INSTANCE.toBuilder();
        }
        
        public static Builder newBuilder(final Object prototype) {
            return Object.DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }
        
        public Builder toBuilder() {
            return (this == Object.DEFAULT_INSTANCE) ? new Builder() : new Builder().mergeFrom(this);
        }
        
        protected Builder newBuilderForType(final GeneratedMessageV3.BuilderParent parent) {
            final Builder builder = new Builder(parent);
            return builder;
        }
        
        public static Object getDefaultInstance() {
            return Object.DEFAULT_INSTANCE;
        }
        
        public static Parser<Object> parser() {
            return Object.PARSER;
        }
        
        public Parser<Object> getParserForType() {
            return Object.PARSER;
        }
        
        public Object getDefaultInstanceForType() {
            return Object.DEFAULT_INSTANCE;
        }
        
        static {
            DEFAULT_INSTANCE = new Object();
            PARSER = (Parser)new AbstractParser<Object>() {
                public Object parsePartialFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new Object(input, extensionRegistry);
                }
            };
        }
        
        public static final class ObjectField extends GeneratedMessageV3 implements ObjectFieldOrBuilder
        {
            private static final long serialVersionUID = 0L;
            private int bitField0_;
            public static final int KEY_FIELD_NUMBER = 1;
            private volatile Object key_;
            public static final int VALUE_FIELD_NUMBER = 2;
            private Expr value_;
            private byte memoizedIsInitialized;
            private static final ObjectField DEFAULT_INSTANCE;
            @Deprecated
            public static final Parser<ObjectField> PARSER;
            
            private ObjectField(final GeneratedMessageV3.Builder<?> builder) {
                super((GeneratedMessageV3.Builder)builder);
                this.memoizedIsInitialized = -1;
            }
            
            private ObjectField() {
                this.memoizedIsInitialized = -1;
                this.key_ = "";
            }
            
            public final UnknownFieldSet getUnknownFields() {
                return this.unknownFields;
            }
            
            private ObjectField(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                                this.key_ = bs;
                                continue;
                            }
                            case 18: {
                                Expr.Builder subBuilder = null;
                                if ((this.bitField0_ & 0x2) == 0x2) {
                                    subBuilder = this.value_.toBuilder();
                                }
                                this.value_ = (Expr)input.readMessage((Parser)Expr.PARSER, extensionRegistry);
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
                return MysqlxExpr.internal_static_Mysqlx_Expr_Object_ObjectField_descriptor;
            }
            
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return MysqlxExpr.internal_static_Mysqlx_Expr_Object_ObjectField_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)ObjectField.class, (Class)Builder.class);
            }
            
            public boolean hasKey() {
                return (this.bitField0_ & 0x1) == 0x1;
            }
            
            public String getKey() {
                final Object ref = this.key_;
                if (ref instanceof String) {
                    return (String)ref;
                }
                final ByteString bs = (ByteString)ref;
                final String s = bs.toStringUtf8();
                if (bs.isValidUtf8()) {
                    this.key_ = s;
                }
                return s;
            }
            
            public ByteString getKeyBytes() {
                final Object ref = this.key_;
                if (ref instanceof String) {
                    final ByteString b = ByteString.copyFromUtf8((String)ref);
                    return (ByteString)(this.key_ = b);
                }
                return (ByteString)ref;
            }
            
            public boolean hasValue() {
                return (this.bitField0_ & 0x2) == 0x2;
            }
            
            public Expr getValue() {
                return (this.value_ == null) ? Expr.getDefaultInstance() : this.value_;
            }
            
            public ExprOrBuilder getValueOrBuilder() {
                return (this.value_ == null) ? Expr.getDefaultInstance() : this.value_;
            }
            
            public final boolean isInitialized() {
                final byte isInitialized = this.memoizedIsInitialized;
                if (isInitialized == 1) {
                    return true;
                }
                if (isInitialized == 0) {
                    return false;
                }
                if (!this.hasKey()) {
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
                    GeneratedMessageV3.writeString(output, 1, this.key_);
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
                    size += GeneratedMessageV3.computeStringSize(1, this.key_);
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
                if (!(obj instanceof ObjectField)) {
                    return super.equals(obj);
                }
                final ObjectField other = (ObjectField)obj;
                boolean result = true;
                result = (result && this.hasKey() == other.hasKey());
                if (this.hasKey()) {
                    result = (result && this.getKey().equals(other.getKey()));
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
                if (this.hasKey()) {
                    hash = 37 * hash + 1;
                    hash = 53 * hash + this.getKey().hashCode();
                }
                if (this.hasValue()) {
                    hash = 37 * hash + 2;
                    hash = 53 * hash + this.getValue().hashCode();
                }
                hash = 29 * hash + this.unknownFields.hashCode();
                return this.memoizedHashCode = hash;
            }
            
            public static ObjectField parseFrom(final ByteBuffer data) throws InvalidProtocolBufferException {
                return (ObjectField)ObjectField.PARSER.parseFrom(data);
            }
            
            public static ObjectField parseFrom(final ByteBuffer data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return (ObjectField)ObjectField.PARSER.parseFrom(data, extensionRegistry);
            }
            
            public static ObjectField parseFrom(final ByteString data) throws InvalidProtocolBufferException {
                return (ObjectField)ObjectField.PARSER.parseFrom(data);
            }
            
            public static ObjectField parseFrom(final ByteString data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return (ObjectField)ObjectField.PARSER.parseFrom(data, extensionRegistry);
            }
            
            public static ObjectField parseFrom(final byte[] data) throws InvalidProtocolBufferException {
                return (ObjectField)ObjectField.PARSER.parseFrom(data);
            }
            
            public static ObjectField parseFrom(final byte[] data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return (ObjectField)ObjectField.PARSER.parseFrom(data, extensionRegistry);
            }
            
            public static ObjectField parseFrom(final InputStream input) throws IOException {
                return (ObjectField)GeneratedMessageV3.parseWithIOException((Parser)ObjectField.PARSER, input);
            }
            
            public static ObjectField parseFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
                return (ObjectField)GeneratedMessageV3.parseWithIOException((Parser)ObjectField.PARSER, input, extensionRegistry);
            }
            
            public static ObjectField parseDelimitedFrom(final InputStream input) throws IOException {
                return (ObjectField)GeneratedMessageV3.parseDelimitedWithIOException((Parser)ObjectField.PARSER, input);
            }
            
            public static ObjectField parseDelimitedFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
                return (ObjectField)GeneratedMessageV3.parseDelimitedWithIOException((Parser)ObjectField.PARSER, input, extensionRegistry);
            }
            
            public static ObjectField parseFrom(final CodedInputStream input) throws IOException {
                return (ObjectField)GeneratedMessageV3.parseWithIOException((Parser)ObjectField.PARSER, input);
            }
            
            public static ObjectField parseFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
                return (ObjectField)GeneratedMessageV3.parseWithIOException((Parser)ObjectField.PARSER, input, extensionRegistry);
            }
            
            public Builder newBuilderForType() {
                return newBuilder();
            }
            
            public static Builder newBuilder() {
                return ObjectField.DEFAULT_INSTANCE.toBuilder();
            }
            
            public static Builder newBuilder(final ObjectField prototype) {
                return ObjectField.DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
            }
            
            public Builder toBuilder() {
                return (this == ObjectField.DEFAULT_INSTANCE) ? new Builder() : new Builder().mergeFrom(this);
            }
            
            protected Builder newBuilderForType(final GeneratedMessageV3.BuilderParent parent) {
                final Builder builder = new Builder(parent);
                return builder;
            }
            
            public static ObjectField getDefaultInstance() {
                return ObjectField.DEFAULT_INSTANCE;
            }
            
            public static Parser<ObjectField> parser() {
                return ObjectField.PARSER;
            }
            
            public Parser<ObjectField> getParserForType() {
                return ObjectField.PARSER;
            }
            
            public ObjectField getDefaultInstanceForType() {
                return ObjectField.DEFAULT_INSTANCE;
            }
            
            static {
                DEFAULT_INSTANCE = new ObjectField();
                PARSER = (Parser)new AbstractParser<ObjectField>() {
                    public ObjectField parsePartialFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                        return new ObjectField(input, extensionRegistry);
                    }
                };
            }
            
            public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements ObjectFieldOrBuilder
            {
                private int bitField0_;
                private Object key_;
                private Expr value_;
                private SingleFieldBuilderV3<Expr, Expr.Builder, ExprOrBuilder> valueBuilder_;
                
                public static final Descriptors.Descriptor getDescriptor() {
                    return MysqlxExpr.internal_static_Mysqlx_Expr_Object_ObjectField_descriptor;
                }
                
                protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                    return MysqlxExpr.internal_static_Mysqlx_Expr_Object_ObjectField_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)ObjectField.class, (Class)Builder.class);
                }
                
                private Builder() {
                    this.key_ = "";
                    this.value_ = null;
                    this.maybeForceBuilderInitialization();
                }
                
                private Builder(final GeneratedMessageV3.BuilderParent parent) {
                    super(parent);
                    this.key_ = "";
                    this.value_ = null;
                    this.maybeForceBuilderInitialization();
                }
                
                private void maybeForceBuilderInitialization() {
                    if (ObjectField.alwaysUseFieldBuilders) {
                        this.getValueFieldBuilder();
                    }
                }
                
                public Builder clear() {
                    super.clear();
                    this.key_ = "";
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
                    return MysqlxExpr.internal_static_Mysqlx_Expr_Object_ObjectField_descriptor;
                }
                
                public ObjectField getDefaultInstanceForType() {
                    return ObjectField.getDefaultInstance();
                }
                
                public ObjectField build() {
                    final ObjectField result = this.buildPartial();
                    if (!result.isInitialized()) {
                        throw newUninitializedMessageException((Message)result);
                    }
                    return result;
                }
                
                public ObjectField buildPartial() {
                    final ObjectField result = new ObjectField((GeneratedMessageV3.Builder)this);
                    final int from_bitField0_ = this.bitField0_;
                    int to_bitField0_ = 0;
                    if ((from_bitField0_ & 0x1) == 0x1) {
                        to_bitField0_ |= 0x1;
                    }
                    result.key_ = this.key_;
                    if ((from_bitField0_ & 0x2) == 0x2) {
                        to_bitField0_ |= 0x2;
                    }
                    if (this.valueBuilder_ == null) {
                        result.value_ = this.value_;
                    }
                    else {
                        result.value_ = (Expr)this.valueBuilder_.build();
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
                    if (other instanceof ObjectField) {
                        return this.mergeFrom((ObjectField)other);
                    }
                    super.mergeFrom(other);
                    return this;
                }
                
                public Builder mergeFrom(final ObjectField other) {
                    if (other == ObjectField.getDefaultInstance()) {
                        return this;
                    }
                    if (other.hasKey()) {
                        this.bitField0_ |= 0x1;
                        this.key_ = other.key_;
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
                    return this.hasKey() && this.hasValue() && this.getValue().isInitialized();
                }
                
                public Builder mergeFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
                    ObjectField parsedMessage = null;
                    try {
                        parsedMessage = (ObjectField)ObjectField.PARSER.parsePartialFrom(input, extensionRegistry);
                    }
                    catch (InvalidProtocolBufferException e) {
                        parsedMessage = (ObjectField)e.getUnfinishedMessage();
                        throw e.unwrapIOException();
                    }
                    finally {
                        if (parsedMessage != null) {
                            this.mergeFrom(parsedMessage);
                        }
                    }
                    return this;
                }
                
                public boolean hasKey() {
                    return (this.bitField0_ & 0x1) == 0x1;
                }
                
                public String getKey() {
                    final Object ref = this.key_;
                    if (!(ref instanceof String)) {
                        final ByteString bs = (ByteString)ref;
                        final String s = bs.toStringUtf8();
                        if (bs.isValidUtf8()) {
                            this.key_ = s;
                        }
                        return s;
                    }
                    return (String)ref;
                }
                
                public ByteString getKeyBytes() {
                    final Object ref = this.key_;
                    if (ref instanceof String) {
                        final ByteString b = ByteString.copyFromUtf8((String)ref);
                        return (ByteString)(this.key_ = b);
                    }
                    return (ByteString)ref;
                }
                
                public Builder setKey(final String value) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.bitField0_ |= 0x1;
                    this.key_ = value;
                    this.onChanged();
                    return this;
                }
                
                public Builder clearKey() {
                    this.bitField0_ &= 0xFFFFFFFE;
                    this.key_ = ObjectField.getDefaultInstance().getKey();
                    this.onChanged();
                    return this;
                }
                
                public Builder setKeyBytes(final ByteString value) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.bitField0_ |= 0x1;
                    this.key_ = value;
                    this.onChanged();
                    return this;
                }
                
                public boolean hasValue() {
                    return (this.bitField0_ & 0x2) == 0x2;
                }
                
                public Expr getValue() {
                    if (this.valueBuilder_ == null) {
                        return (this.value_ == null) ? Expr.getDefaultInstance() : this.value_;
                    }
                    return (Expr)this.valueBuilder_.getMessage();
                }
                
                public Builder setValue(final Expr value) {
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
                
                public Builder setValue(final Expr.Builder builderForValue) {
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
                
                public Builder mergeValue(final Expr value) {
                    if (this.valueBuilder_ == null) {
                        if ((this.bitField0_ & 0x2) == 0x2 && this.value_ != null && this.value_ != Expr.getDefaultInstance()) {
                            this.value_ = Expr.newBuilder(this.value_).mergeFrom(value).buildPartial();
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
                
                public Expr.Builder getValueBuilder() {
                    this.bitField0_ |= 0x2;
                    this.onChanged();
                    return (Expr.Builder)this.getValueFieldBuilder().getBuilder();
                }
                
                public ExprOrBuilder getValueOrBuilder() {
                    if (this.valueBuilder_ != null) {
                        return (ExprOrBuilder)this.valueBuilder_.getMessageOrBuilder();
                    }
                    return (this.value_ == null) ? Expr.getDefaultInstance() : this.value_;
                }
                
                private SingleFieldBuilderV3<Expr, Expr.Builder, ExprOrBuilder> getValueFieldBuilder() {
                    if (this.valueBuilder_ == null) {
                        this.valueBuilder_ = (SingleFieldBuilderV3<Expr, Expr.Builder, ExprOrBuilder>)new SingleFieldBuilderV3((AbstractMessage)this.getValue(), (AbstractMessage.BuilderParent)this.getParentForChildren(), this.isClean());
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
        
        public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements ObjectOrBuilder
        {
            private int bitField0_;
            private List<ObjectField> fld_;
            private RepeatedFieldBuilderV3<ObjectField, ObjectField.Builder, ObjectFieldOrBuilder> fldBuilder_;
            
            public static final Descriptors.Descriptor getDescriptor() {
                return MysqlxExpr.internal_static_Mysqlx_Expr_Object_descriptor;
            }
            
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return MysqlxExpr.internal_static_Mysqlx_Expr_Object_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)MysqlxExpr.Object.class, (Class)Builder.class);
            }
            
            private Builder() {
                this.fld_ = Collections.emptyList();
                this.maybeForceBuilderInitialization();
            }
            
            private Builder(final GeneratedMessageV3.BuilderParent parent) {
                super(parent);
                this.fld_ = Collections.emptyList();
                this.maybeForceBuilderInitialization();
            }
            
            private void maybeForceBuilderInitialization() {
                if (MysqlxExpr.Object.alwaysUseFieldBuilders) {
                    this.getFldFieldBuilder();
                }
            }
            
            public Builder clear() {
                super.clear();
                if (this.fldBuilder_ == null) {
                    this.fld_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFFE;
                }
                else {
                    this.fldBuilder_.clear();
                }
                return this;
            }
            
            public Descriptors.Descriptor getDescriptorForType() {
                return MysqlxExpr.internal_static_Mysqlx_Expr_Object_descriptor;
            }
            
            public MysqlxExpr.Object getDefaultInstanceForType() {
                return MysqlxExpr.Object.getDefaultInstance();
            }
            
            public MysqlxExpr.Object build() {
                final MysqlxExpr.Object result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw newUninitializedMessageException((Message)result);
                }
                return result;
            }
            
            public MysqlxExpr.Object buildPartial() {
                final MysqlxExpr.Object result = new MysqlxExpr.Object((GeneratedMessageV3.Builder)this);
                final int from_bitField0_ = this.bitField0_;
                if (this.fldBuilder_ == null) {
                    if ((this.bitField0_ & 0x1) == 0x1) {
                        this.fld_ = Collections.unmodifiableList((List<? extends ObjectField>)this.fld_);
                        this.bitField0_ &= 0xFFFFFFFE;
                    }
                    result.fld_ = this.fld_;
                }
                else {
                    result.fld_ = (List<ObjectField>)this.fldBuilder_.build();
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
                if (other instanceof MysqlxExpr.Object) {
                    return this.mergeFrom((MysqlxExpr.Object)other);
                }
                super.mergeFrom(other);
                return this;
            }
            
            public Builder mergeFrom(final MysqlxExpr.Object other) {
                if (other == MysqlxExpr.Object.getDefaultInstance()) {
                    return this;
                }
                if (this.fldBuilder_ == null) {
                    if (!other.fld_.isEmpty()) {
                        if (this.fld_.isEmpty()) {
                            this.fld_ = other.fld_;
                            this.bitField0_ &= 0xFFFFFFFE;
                        }
                        else {
                            this.ensureFldIsMutable();
                            this.fld_.addAll(other.fld_);
                        }
                        this.onChanged();
                    }
                }
                else if (!other.fld_.isEmpty()) {
                    if (this.fldBuilder_.isEmpty()) {
                        this.fldBuilder_.dispose();
                        this.fldBuilder_ = null;
                        this.fld_ = other.fld_;
                        this.bitField0_ &= 0xFFFFFFFE;
                        this.fldBuilder_ = (MysqlxExpr.Object.alwaysUseFieldBuilders ? this.getFldFieldBuilder() : null);
                    }
                    else {
                        this.fldBuilder_.addAllMessages((Iterable)other.fld_);
                    }
                }
                this.mergeUnknownFields(other.unknownFields);
                this.onChanged();
                return this;
            }
            
            public final boolean isInitialized() {
                for (int i = 0; i < this.getFldCount(); ++i) {
                    if (!this.getFld(i).isInitialized()) {
                        return false;
                    }
                }
                return true;
            }
            
            public Builder mergeFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
                MysqlxExpr.Object parsedMessage = null;
                try {
                    parsedMessage = (MysqlxExpr.Object)MysqlxExpr.Object.PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (MysqlxExpr.Object)e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                }
                finally {
                    if (parsedMessage != null) {
                        this.mergeFrom(parsedMessage);
                    }
                }
                return this;
            }
            
            private void ensureFldIsMutable() {
                if ((this.bitField0_ & 0x1) != 0x1) {
                    this.fld_ = new ArrayList<ObjectField>(this.fld_);
                    this.bitField0_ |= 0x1;
                }
            }
            
            public List<ObjectField> getFldList() {
                if (this.fldBuilder_ == null) {
                    return Collections.unmodifiableList((List<? extends ObjectField>)this.fld_);
                }
                return (List<ObjectField>)this.fldBuilder_.getMessageList();
            }
            
            public int getFldCount() {
                if (this.fldBuilder_ == null) {
                    return this.fld_.size();
                }
                return this.fldBuilder_.getCount();
            }
            
            public ObjectField getFld(final int index) {
                if (this.fldBuilder_ == null) {
                    return this.fld_.get(index);
                }
                return (ObjectField)this.fldBuilder_.getMessage(index);
            }
            
            public Builder setFld(final int index, final ObjectField value) {
                if (this.fldBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureFldIsMutable();
                    this.fld_.set(index, value);
                    this.onChanged();
                }
                else {
                    this.fldBuilder_.setMessage(index, (AbstractMessage)value);
                }
                return this;
            }
            
            public Builder setFld(final int index, final ObjectField.Builder builderForValue) {
                if (this.fldBuilder_ == null) {
                    this.ensureFldIsMutable();
                    this.fld_.set(index, builderForValue.build());
                    this.onChanged();
                }
                else {
                    this.fldBuilder_.setMessage(index, (AbstractMessage)builderForValue.build());
                }
                return this;
            }
            
            public Builder addFld(final ObjectField value) {
                if (this.fldBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureFldIsMutable();
                    this.fld_.add(value);
                    this.onChanged();
                }
                else {
                    this.fldBuilder_.addMessage((AbstractMessage)value);
                }
                return this;
            }
            
            public Builder addFld(final int index, final ObjectField value) {
                if (this.fldBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureFldIsMutable();
                    this.fld_.add(index, value);
                    this.onChanged();
                }
                else {
                    this.fldBuilder_.addMessage(index, (AbstractMessage)value);
                }
                return this;
            }
            
            public Builder addFld(final ObjectField.Builder builderForValue) {
                if (this.fldBuilder_ == null) {
                    this.ensureFldIsMutable();
                    this.fld_.add(builderForValue.build());
                    this.onChanged();
                }
                else {
                    this.fldBuilder_.addMessage((AbstractMessage)builderForValue.build());
                }
                return this;
            }
            
            public Builder addFld(final int index, final ObjectField.Builder builderForValue) {
                if (this.fldBuilder_ == null) {
                    this.ensureFldIsMutable();
                    this.fld_.add(index, builderForValue.build());
                    this.onChanged();
                }
                else {
                    this.fldBuilder_.addMessage(index, (AbstractMessage)builderForValue.build());
                }
                return this;
            }
            
            public Builder addAllFld(final Iterable<? extends ObjectField> values) {
                if (this.fldBuilder_ == null) {
                    this.ensureFldIsMutable();
                    AbstractMessageLite.Builder.addAll((Iterable)values, (List)this.fld_);
                    this.onChanged();
                }
                else {
                    this.fldBuilder_.addAllMessages((Iterable)values);
                }
                return this;
            }
            
            public Builder clearFld() {
                if (this.fldBuilder_ == null) {
                    this.fld_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFFE;
                    this.onChanged();
                }
                else {
                    this.fldBuilder_.clear();
                }
                return this;
            }
            
            public Builder removeFld(final int index) {
                if (this.fldBuilder_ == null) {
                    this.ensureFldIsMutable();
                    this.fld_.remove(index);
                    this.onChanged();
                }
                else {
                    this.fldBuilder_.remove(index);
                }
                return this;
            }
            
            public ObjectField.Builder getFldBuilder(final int index) {
                return (ObjectField.Builder)this.getFldFieldBuilder().getBuilder(index);
            }
            
            public ObjectFieldOrBuilder getFldOrBuilder(final int index) {
                if (this.fldBuilder_ == null) {
                    return this.fld_.get(index);
                }
                return (ObjectFieldOrBuilder)this.fldBuilder_.getMessageOrBuilder(index);
            }
            
            public List<? extends ObjectFieldOrBuilder> getFldOrBuilderList() {
                if (this.fldBuilder_ != null) {
                    return (List<? extends ObjectFieldOrBuilder>)this.fldBuilder_.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList((List<? extends ObjectFieldOrBuilder>)this.fld_);
            }
            
            public ObjectField.Builder addFldBuilder() {
                return (ObjectField.Builder)this.getFldFieldBuilder().addBuilder((AbstractMessage)ObjectField.getDefaultInstance());
            }
            
            public ObjectField.Builder addFldBuilder(final int index) {
                return (ObjectField.Builder)this.getFldFieldBuilder().addBuilder(index, (AbstractMessage)ObjectField.getDefaultInstance());
            }
            
            public List<ObjectField.Builder> getFldBuilderList() {
                return (List<ObjectField.Builder>)this.getFldFieldBuilder().getBuilderList();
            }
            
            private RepeatedFieldBuilderV3<ObjectField, ObjectField.Builder, ObjectFieldOrBuilder> getFldFieldBuilder() {
                if (this.fldBuilder_ == null) {
                    this.fldBuilder_ = (RepeatedFieldBuilderV3<ObjectField, ObjectField.Builder, ObjectFieldOrBuilder>)new RepeatedFieldBuilderV3((List)this.fld_, (this.bitField0_ & 0x1) == 0x1, (AbstractMessage.BuilderParent)this.getParentForChildren(), this.isClean());
                    this.fld_ = null;
                }
                return this.fldBuilder_;
            }
            
            public final Builder setUnknownFields(final UnknownFieldSet unknownFields) {
                return (Builder)super.setUnknownFields(unknownFields);
            }
            
            public final Builder mergeUnknownFields(final UnknownFieldSet unknownFields) {
                return (Builder)super.mergeUnknownFields(unknownFields);
            }
        }
        
        public interface ObjectFieldOrBuilder extends MessageOrBuilder
        {
            boolean hasKey();
            
            String getKey();
            
            ByteString getKeyBytes();
            
            boolean hasValue();
            
            Expr getValue();
            
            ExprOrBuilder getValueOrBuilder();
        }
    }
    
    public static final class Array extends GeneratedMessageV3 implements ArrayOrBuilder
    {
        private static final long serialVersionUID = 0L;
        public static final int VALUE_FIELD_NUMBER = 1;
        private List<Expr> value_;
        private byte memoizedIsInitialized;
        private static final Array DEFAULT_INSTANCE;
        @Deprecated
        public static final Parser<Array> PARSER;
        
        private Array(final GeneratedMessageV3.Builder<?> builder) {
            super((GeneratedMessageV3.Builder)builder);
            this.memoizedIsInitialized = -1;
        }
        
        private Array() {
            this.memoizedIsInitialized = -1;
            this.value_ = Collections.emptyList();
        }
        
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }
        
        private Array(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                                this.value_ = new ArrayList<Expr>();
                                mutable_bitField0_ |= 0x1;
                            }
                            this.value_.add((Expr)input.readMessage((Parser)Expr.PARSER, extensionRegistry));
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
                    this.value_ = Collections.unmodifiableList((List<? extends Expr>)this.value_);
                }
                this.unknownFields = unknownFields.build();
                this.makeExtensionsImmutable();
            }
        }
        
        public static final Descriptors.Descriptor getDescriptor() {
            return MysqlxExpr.internal_static_Mysqlx_Expr_Array_descriptor;
        }
        
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return MysqlxExpr.internal_static_Mysqlx_Expr_Array_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)Array.class, (Class)Builder.class);
        }
        
        public List<Expr> getValueList() {
            return this.value_;
        }
        
        public List<? extends ExprOrBuilder> getValueOrBuilderList() {
            return this.value_;
        }
        
        public int getValueCount() {
            return this.value_.size();
        }
        
        public Expr getValue(final int index) {
            return this.value_.get(index);
        }
        
        public ExprOrBuilder getValueOrBuilder(final int index) {
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
            for (int i = 0; i < this.value_.size(); ++i) {
                output.writeMessage(1, (MessageLite)this.value_.get(i));
            }
            this.unknownFields.writeTo(output);
        }
        
        public int getSerializedSize() {
            int size = this.memoizedSize;
            if (size != -1) {
                return size;
            }
            size = 0;
            for (int i = 0; i < this.value_.size(); ++i) {
                size += CodedOutputStream.computeMessageSize(1, (MessageLite)this.value_.get(i));
            }
            size += this.unknownFields.getSerializedSize();
            return this.memoizedSize = size;
        }
        
        public boolean equals(final Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Array)) {
                return super.equals(obj);
            }
            final Array other = (Array)obj;
            boolean result = true;
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
            if (this.getValueCount() > 0) {
                hash = 37 * hash + 1;
                hash = 53 * hash + this.getValueList().hashCode();
            }
            hash = 29 * hash + this.unknownFields.hashCode();
            return this.memoizedHashCode = hash;
        }
        
        public static Array parseFrom(final ByteBuffer data) throws InvalidProtocolBufferException {
            return (Array)Array.PARSER.parseFrom(data);
        }
        
        public static Array parseFrom(final ByteBuffer data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Array)Array.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Array parseFrom(final ByteString data) throws InvalidProtocolBufferException {
            return (Array)Array.PARSER.parseFrom(data);
        }
        
        public static Array parseFrom(final ByteString data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Array)Array.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Array parseFrom(final byte[] data) throws InvalidProtocolBufferException {
            return (Array)Array.PARSER.parseFrom(data);
        }
        
        public static Array parseFrom(final byte[] data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Array)Array.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Array parseFrom(final InputStream input) throws IOException {
            return (Array)GeneratedMessageV3.parseWithIOException((Parser)Array.PARSER, input);
        }
        
        public static Array parseFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Array)GeneratedMessageV3.parseWithIOException((Parser)Array.PARSER, input, extensionRegistry);
        }
        
        public static Array parseDelimitedFrom(final InputStream input) throws IOException {
            return (Array)GeneratedMessageV3.parseDelimitedWithIOException((Parser)Array.PARSER, input);
        }
        
        public static Array parseDelimitedFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Array)GeneratedMessageV3.parseDelimitedWithIOException((Parser)Array.PARSER, input, extensionRegistry);
        }
        
        public static Array parseFrom(final CodedInputStream input) throws IOException {
            return (Array)GeneratedMessageV3.parseWithIOException((Parser)Array.PARSER, input);
        }
        
        public static Array parseFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Array)GeneratedMessageV3.parseWithIOException((Parser)Array.PARSER, input, extensionRegistry);
        }
        
        public Builder newBuilderForType() {
            return newBuilder();
        }
        
        public static Builder newBuilder() {
            return Array.DEFAULT_INSTANCE.toBuilder();
        }
        
        public static Builder newBuilder(final Array prototype) {
            return Array.DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }
        
        public Builder toBuilder() {
            return (this == Array.DEFAULT_INSTANCE) ? new Builder() : new Builder().mergeFrom(this);
        }
        
        protected Builder newBuilderForType(final GeneratedMessageV3.BuilderParent parent) {
            final Builder builder = new Builder(parent);
            return builder;
        }
        
        public static Array getDefaultInstance() {
            return Array.DEFAULT_INSTANCE;
        }
        
        public static Parser<Array> parser() {
            return Array.PARSER;
        }
        
        public Parser<Array> getParserForType() {
            return Array.PARSER;
        }
        
        public Array getDefaultInstanceForType() {
            return Array.DEFAULT_INSTANCE;
        }
        
        static {
            DEFAULT_INSTANCE = new Array();
            PARSER = (Parser)new AbstractParser<Array>() {
                public Array parsePartialFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new Array(input, extensionRegistry);
                }
            };
        }
        
        public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements ArrayOrBuilder
        {
            private int bitField0_;
            private List<Expr> value_;
            private RepeatedFieldBuilderV3<Expr, Expr.Builder, ExprOrBuilder> valueBuilder_;
            
            public static final Descriptors.Descriptor getDescriptor() {
                return MysqlxExpr.internal_static_Mysqlx_Expr_Array_descriptor;
            }
            
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return MysqlxExpr.internal_static_Mysqlx_Expr_Array_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)Array.class, (Class)Builder.class);
            }
            
            private Builder() {
                this.value_ = Collections.emptyList();
                this.maybeForceBuilderInitialization();
            }
            
            private Builder(final GeneratedMessageV3.BuilderParent parent) {
                super(parent);
                this.value_ = Collections.emptyList();
                this.maybeForceBuilderInitialization();
            }
            
            private void maybeForceBuilderInitialization() {
                if (Array.alwaysUseFieldBuilders) {
                    this.getValueFieldBuilder();
                }
            }
            
            public Builder clear() {
                super.clear();
                if (this.valueBuilder_ == null) {
                    this.value_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFFE;
                }
                else {
                    this.valueBuilder_.clear();
                }
                return this;
            }
            
            public Descriptors.Descriptor getDescriptorForType() {
                return MysqlxExpr.internal_static_Mysqlx_Expr_Array_descriptor;
            }
            
            public Array getDefaultInstanceForType() {
                return Array.getDefaultInstance();
            }
            
            public Array build() {
                final Array result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw newUninitializedMessageException((Message)result);
                }
                return result;
            }
            
            public Array buildPartial() {
                final Array result = new Array((GeneratedMessageV3.Builder)this);
                final int from_bitField0_ = this.bitField0_;
                if (this.valueBuilder_ == null) {
                    if ((this.bitField0_ & 0x1) == 0x1) {
                        this.value_ = Collections.unmodifiableList((List<? extends Expr>)this.value_);
                        this.bitField0_ &= 0xFFFFFFFE;
                    }
                    result.value_ = this.value_;
                }
                else {
                    result.value_ = (List<Expr>)this.valueBuilder_.build();
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
                if (other instanceof Array) {
                    return this.mergeFrom((Array)other);
                }
                super.mergeFrom(other);
                return this;
            }
            
            public Builder mergeFrom(final Array other) {
                if (other == Array.getDefaultInstance()) {
                    return this;
                }
                if (this.valueBuilder_ == null) {
                    if (!other.value_.isEmpty()) {
                        if (this.value_.isEmpty()) {
                            this.value_ = other.value_;
                            this.bitField0_ &= 0xFFFFFFFE;
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
                        this.bitField0_ &= 0xFFFFFFFE;
                        this.valueBuilder_ = (Array.alwaysUseFieldBuilders ? this.getValueFieldBuilder() : null);
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
                for (int i = 0; i < this.getValueCount(); ++i) {
                    if (!this.getValue(i).isInitialized()) {
                        return false;
                    }
                }
                return true;
            }
            
            public Builder mergeFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
                Array parsedMessage = null;
                try {
                    parsedMessage = (Array)Array.PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (Array)e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                }
                finally {
                    if (parsedMessage != null) {
                        this.mergeFrom(parsedMessage);
                    }
                }
                return this;
            }
            
            private void ensureValueIsMutable() {
                if ((this.bitField0_ & 0x1) != 0x1) {
                    this.value_ = new ArrayList<Expr>(this.value_);
                    this.bitField0_ |= 0x1;
                }
            }
            
            public List<Expr> getValueList() {
                if (this.valueBuilder_ == null) {
                    return Collections.unmodifiableList((List<? extends Expr>)this.value_);
                }
                return (List<Expr>)this.valueBuilder_.getMessageList();
            }
            
            public int getValueCount() {
                if (this.valueBuilder_ == null) {
                    return this.value_.size();
                }
                return this.valueBuilder_.getCount();
            }
            
            public Expr getValue(final int index) {
                if (this.valueBuilder_ == null) {
                    return this.value_.get(index);
                }
                return (Expr)this.valueBuilder_.getMessage(index);
            }
            
            public Builder setValue(final int index, final Expr value) {
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
            
            public Builder setValue(final int index, final Expr.Builder builderForValue) {
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
            
            public Builder addValue(final Expr value) {
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
            
            public Builder addValue(final int index, final Expr value) {
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
            
            public Builder addValue(final Expr.Builder builderForValue) {
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
            
            public Builder addValue(final int index, final Expr.Builder builderForValue) {
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
            
            public Builder addAllValue(final Iterable<? extends Expr> values) {
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
                    this.bitField0_ &= 0xFFFFFFFE;
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
            
            public Expr.Builder getValueBuilder(final int index) {
                return (Expr.Builder)this.getValueFieldBuilder().getBuilder(index);
            }
            
            public ExprOrBuilder getValueOrBuilder(final int index) {
                if (this.valueBuilder_ == null) {
                    return this.value_.get(index);
                }
                return (ExprOrBuilder)this.valueBuilder_.getMessageOrBuilder(index);
            }
            
            public List<? extends ExprOrBuilder> getValueOrBuilderList() {
                if (this.valueBuilder_ != null) {
                    return (List<? extends ExprOrBuilder>)this.valueBuilder_.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList((List<? extends ExprOrBuilder>)this.value_);
            }
            
            public Expr.Builder addValueBuilder() {
                return (Expr.Builder)this.getValueFieldBuilder().addBuilder((AbstractMessage)Expr.getDefaultInstance());
            }
            
            public Expr.Builder addValueBuilder(final int index) {
                return (Expr.Builder)this.getValueFieldBuilder().addBuilder(index, (AbstractMessage)Expr.getDefaultInstance());
            }
            
            public List<Expr.Builder> getValueBuilderList() {
                return (List<Expr.Builder>)this.getValueFieldBuilder().getBuilderList();
            }
            
            private RepeatedFieldBuilderV3<Expr, Expr.Builder, ExprOrBuilder> getValueFieldBuilder() {
                if (this.valueBuilder_ == null) {
                    this.valueBuilder_ = (RepeatedFieldBuilderV3<Expr, Expr.Builder, ExprOrBuilder>)new RepeatedFieldBuilderV3((List)this.value_, (this.bitField0_ & 0x1) == 0x1, (AbstractMessage.BuilderParent)this.getParentForChildren(), this.isClean());
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
    
    public interface DocumentPathItemOrBuilder extends MessageOrBuilder
    {
        boolean hasType();
        
        DocumentPathItem.Type getType();
        
        boolean hasValue();
        
        String getValue();
        
        ByteString getValueBytes();
        
        boolean hasIndex();
        
        int getIndex();
    }
    
    public interface ColumnIdentifierOrBuilder extends MessageOrBuilder
    {
        List<DocumentPathItem> getDocumentPathList();
        
        DocumentPathItem getDocumentPath(final int p0);
        
        int getDocumentPathCount();
        
        List<? extends DocumentPathItemOrBuilder> getDocumentPathOrBuilderList();
        
        DocumentPathItemOrBuilder getDocumentPathOrBuilder(final int p0);
        
        boolean hasName();
        
        String getName();
        
        ByteString getNameBytes();
        
        boolean hasTableName();
        
        String getTableName();
        
        ByteString getTableNameBytes();
        
        boolean hasSchemaName();
        
        String getSchemaName();
        
        ByteString getSchemaNameBytes();
    }
    
    public interface IdentifierOrBuilder extends MessageOrBuilder
    {
        boolean hasName();
        
        String getName();
        
        ByteString getNameBytes();
        
        boolean hasSchemaName();
        
        String getSchemaName();
        
        ByteString getSchemaNameBytes();
    }
    
    public interface ExprOrBuilder extends MessageOrBuilder
    {
        boolean hasType();
        
        Expr.Type getType();
        
        boolean hasIdentifier();
        
        ColumnIdentifier getIdentifier();
        
        ColumnIdentifierOrBuilder getIdentifierOrBuilder();
        
        boolean hasVariable();
        
        String getVariable();
        
        ByteString getVariableBytes();
        
        boolean hasLiteral();
        
        MysqlxDatatypes.Scalar getLiteral();
        
        MysqlxDatatypes.ScalarOrBuilder getLiteralOrBuilder();
        
        boolean hasFunctionCall();
        
        FunctionCall getFunctionCall();
        
        FunctionCallOrBuilder getFunctionCallOrBuilder();
        
        boolean hasOperator();
        
        Operator getOperator();
        
        OperatorOrBuilder getOperatorOrBuilder();
        
        boolean hasPosition();
        
        int getPosition();
        
        boolean hasObject();
        
        MysqlxExpr.Object getObject();
        
        ObjectOrBuilder getObjectOrBuilder();
        
        boolean hasArray();
        
        Array getArray();
        
        ArrayOrBuilder getArrayOrBuilder();
    }
    
    public interface FunctionCallOrBuilder extends MessageOrBuilder
    {
        boolean hasName();
        
        Identifier getName();
        
        IdentifierOrBuilder getNameOrBuilder();
        
        List<Expr> getParamList();
        
        Expr getParam(final int p0);
        
        int getParamCount();
        
        List<? extends ExprOrBuilder> getParamOrBuilderList();
        
        ExprOrBuilder getParamOrBuilder(final int p0);
    }
    
    public interface OperatorOrBuilder extends MessageOrBuilder
    {
        boolean hasName();
        
        String getName();
        
        ByteString getNameBytes();
        
        List<Expr> getParamList();
        
        Expr getParam(final int p0);
        
        int getParamCount();
        
        List<? extends ExprOrBuilder> getParamOrBuilderList();
        
        ExprOrBuilder getParamOrBuilder(final int p0);
    }
    
    public interface ObjectOrBuilder extends MessageOrBuilder
    {
        List<MysqlxExpr.Object.ObjectField> getFldList();
        
        MysqlxExpr.Object.ObjectField getFld(final int p0);
        
        int getFldCount();
        
        List<? extends MysqlxExpr.Object.ObjectFieldOrBuilder> getFldOrBuilderList();
        
        MysqlxExpr.Object.ObjectFieldOrBuilder getFldOrBuilder(final int p0);
    }
    
    public interface ArrayOrBuilder extends MessageOrBuilder
    {
        List<Expr> getValueList();
        
        Expr getValue(final int p0);
        
        int getValueCount();
        
        List<? extends ExprOrBuilder> getValueOrBuilderList();
        
        ExprOrBuilder getValueOrBuilder(final int p0);
    }
}
