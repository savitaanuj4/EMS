
package com.mysql.cj.x.protobuf;

import com.google.protobuf.ProtocolStringList;
import com.google.protobuf.LazyStringArrayList;
import com.google.protobuf.LazyStringList;
import com.google.protobuf.MessageOrBuilder;
import com.google.protobuf.SingleFieldBuilderV3;
import com.google.protobuf.AbstractMessageLite;
import com.google.protobuf.AbstractMessage;
import java.util.Collection;
import com.google.protobuf.RepeatedFieldBuilderV3;
import com.google.protobuf.AbstractParser;
import com.google.protobuf.Message;
import java.io.InputStream;
import java.nio.ByteBuffer;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.ByteString;
import java.io.IOException;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.MessageLite;
import java.util.ArrayList;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.UnknownFieldSet;
import java.util.Collections;
import com.google.protobuf.Parser;
import java.util.List;
import com.google.protobuf.Internal;
import com.google.protobuf.ProtocolMessageEnum;
import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.Descriptors;

public final class MysqlxCrud
{
    private static final Descriptors.Descriptor internal_static_Mysqlx_Crud_Column_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_Mysqlx_Crud_Column_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_Mysqlx_Crud_Projection_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_Mysqlx_Crud_Projection_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_Mysqlx_Crud_Collection_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_Mysqlx_Crud_Collection_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_Mysqlx_Crud_Limit_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_Mysqlx_Crud_Limit_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_Mysqlx_Crud_Order_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_Mysqlx_Crud_Order_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_Mysqlx_Crud_UpdateOperation_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_Mysqlx_Crud_UpdateOperation_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_Mysqlx_Crud_Find_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_Mysqlx_Crud_Find_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_Mysqlx_Crud_Insert_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_Mysqlx_Crud_Insert_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_Mysqlx_Crud_Insert_TypedRow_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_Mysqlx_Crud_Insert_TypedRow_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_Mysqlx_Crud_Update_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_Mysqlx_Crud_Update_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_Mysqlx_Crud_Delete_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_Mysqlx_Crud_Delete_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_Mysqlx_Crud_CreateView_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_Mysqlx_Crud_CreateView_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_Mysqlx_Crud_ModifyView_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_Mysqlx_Crud_ModifyView_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_Mysqlx_Crud_DropView_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_Mysqlx_Crud_DropView_fieldAccessorTable;
    private static Descriptors.FileDescriptor descriptor;
    
    private MysqlxCrud() {
    }
    
    public static void registerAllExtensions(final ExtensionRegistryLite registry) {
    }
    
    public static void registerAllExtensions(final ExtensionRegistry registry) {
        registerAllExtensions((ExtensionRegistryLite)registry);
    }
    
    public static Descriptors.FileDescriptor getDescriptor() {
        return MysqlxCrud.descriptor;
    }
    
    static {
        final String[] descriptorData = { "\n\u0011mysqlx_crud.proto\u0012\u000bMysqlx.Crud\u001a\fmysqlx.proto\u001a\u0011mysqlx_expr.proto\u001a\u0016mysqlx_datatypes.proto\"[\n\u0006Column\u0012\f\n\u0004name\u0018\u0001 \u0001(\t\u0012\r\n\u0005alias\u0018\u0002 \u0001(\t\u00124\n\rdocument_path\u0018\u0003 \u0003(\u000b2\u001d.Mysqlx.Expr.DocumentPathItem\">\n\nProjection\u0012!\n\u0006source\u0018\u0001 \u0002(\u000b2\u0011.Mysqlx.Expr.Expr\u0012\r\n\u0005alias\u0018\u0002 \u0001(\t\"*\n\nCollection\u0012\f\n\u0004name\u0018\u0001 \u0002(\t\u0012\u000e\n\u0006schema\u0018\u0002 \u0001(\t\"*\n\u0005Limit\u0012\u0011\n\trow_count\u0018\u0001 \u0002(\u0004\u0012\u000e\n\u0006offset\u0018\u0002 \u0001(\u0004\"~\n\u0005Order\u0012\u001f\n\u0004expr\u0018\u0001 \u0002(\u000b2\u0011.Mysqlx.Expr.Expr\u00124\n\tdirection\u0018\u0002 \u0001(\u000e2\u001c.Mysqlx.Crud.Order.Direction:\u0003ASC\"\u001e\n\tDirection\u0012\u0007\n\u0003ASC\u0010\u0001\u0012\b\n\u0004DESC\u0010\u0002\"¬\u0002\n\u000fUpdateOperation\u0012-\n\u0006source\u0018\u0001 \u0002(\u000b2\u001d.Mysqlx.Expr.ColumnIdentifier\u0012:\n\toperation\u0018\u0002 \u0002(\u000e2'.Mysqlx.Crud.UpdateOperation.UpdateType\u0012 \n\u0005value\u0018\u0003 \u0001(\u000b2\u0011.Mysqlx.Expr.Expr\"\u008b\u0001\n\nUpdateType\u0012\u0007\n\u0003SET\u0010\u0001\u0012\u000f\n\u000bITEM_REMOVE\u0010\u0002\u0012\f\n\bITEM_SET\u0010\u0003\u0012\u0010\n\fITEM_REPLACE\u0010\u0004\u0012\u000e\n\nITEM_MERGE\u0010\u0005\u0012\u0010\n\fARRAY_INSERT\u0010\u0006\u0012\u0010\n\fARRAY_APPEND\u0010\u0007\u0012\u000f\n\u000bMERGE_PATCH\u0010\b\"¾\u0004\n\u0004Find\u0012+\n\ncollection\u0018\u0002 \u0002(\u000b2\u0017.Mysqlx.Crud.Collection\u0012*\n\ndata_model\u0018\u0003 \u0001(\u000e2\u0016.Mysqlx.Crud.DataModel\u0012+\n\nprojection\u0018\u0004 \u0003(\u000b2\u0017.Mysqlx.Crud.Projection\u0012#\n\bcriteria\u0018\u0005 \u0001(\u000b2\u0011.Mysqlx.Expr.Expr\u0012&\n\u0004args\u0018\u000b \u0003(\u000b2\u0018.Mysqlx.Datatypes.Scalar\u0012!\n\u0005limit\u0018\u0006 \u0001(\u000b2\u0012.Mysqlx.Crud.Limit\u0012!\n\u0005order\u0018\u0007 \u0003(\u000b2\u0012.Mysqlx.Crud.Order\u0012#\n\bgrouping\u0018\b \u0003(\u000b2\u0011.Mysqlx.Expr.Expr\u0012,\n\u0011grouping_criteria\u0018\t \u0001(\u000b2\u0011.Mysqlx.Expr.Expr\u0012*\n\u0007locking\u0018\f \u0001(\u000e2\u0019.Mysqlx.Crud.Find.RowLock\u00129\n\u000flocking_options\u0018\r \u0001(\u000e2 .Mysqlx.Crud.Find.RowLockOptions\".\n\u0007RowLock\u0012\u000f\n\u000bSHARED_LOCK\u0010\u0001\u0012\u0012\n\u000eEXCLUSIVE_LOCK\u0010\u0002\"-\n\u000eRowLockOptions\u0012\n\n\u0006NOWAIT\u0010\u0001\u0012\u000f\n\u000bSKIP_LOCKED\u0010\u0002:\u0004\u0088\u00ea0\u0011\"¨\u0002\n\u0006Insert\u0012+\n\ncollection\u0018\u0001 \u0002(\u000b2\u0017.Mysqlx.Crud.Collection\u0012*\n\ndata_model\u0018\u0002 \u0001(\u000e2\u0016.Mysqlx.Crud.DataModel\u0012'\n\nprojection\u0018\u0003 \u0003(\u000b2\u0013.Mysqlx.Crud.Column\u0012)\n\u0003row\u0018\u0004 \u0003(\u000b2\u001c.Mysqlx.Crud.Insert.TypedRow\u0012&\n\u0004args\u0018\u0005 \u0003(\u000b2\u0018.Mysqlx.Datatypes.Scalar\u0012\u0015\n\u0006upsert\u0018\u0006 \u0001(\b:\u0005false\u001a,\n\bTypedRow\u0012 \n\u0005field\u0018\u0001 \u0003(\u000b2\u0011.Mysqlx.Expr.Expr:\u0004\u0088\u00ea0\u0012\"«\u0002\n\u0006Update\u0012+\n\ncollection\u0018\u0002 \u0002(\u000b2\u0017.Mysqlx.Crud.Collection\u0012*\n\ndata_model\u0018\u0003 \u0001(\u000e2\u0016.Mysqlx.Crud.DataModel\u0012#\n\bcriteria\u0018\u0004 \u0001(\u000b2\u0011.Mysqlx.Expr.Expr\u0012&\n\u0004args\u0018\b \u0003(\u000b2\u0018.Mysqlx.Datatypes.Scalar\u0012!\n\u0005limit\u0018\u0005 \u0001(\u000b2\u0012.Mysqlx.Crud.Limit\u0012!\n\u0005order\u0018\u0006 \u0003(\u000b2\u0012.Mysqlx.Crud.Order\u0012/\n\toperation\u0018\u0007 \u0003(\u000b2\u001c.Mysqlx.Crud.UpdateOperation:\u0004\u0088\u00ea0\u0013\"\u00fa\u0001\n\u0006Delete\u0012+\n\ncollection\u0018\u0001 \u0002(\u000b2\u0017.Mysqlx.Crud.Collection\u0012*\n\ndata_model\u0018\u0002 \u0001(\u000e2\u0016.Mysqlx.Crud.DataModel\u0012#\n\bcriteria\u0018\u0003 \u0001(\u000b2\u0011.Mysqlx.Expr.Expr\u0012&\n\u0004args\u0018\u0006 \u0003(\u000b2\u0018.Mysqlx.Datatypes.Scalar\u0012!\n\u0005limit\u0018\u0004 \u0001(\u000b2\u0012.Mysqlx.Crud.Limit\u0012!\n\u0005order\u0018\u0005 \u0003(\u000b2\u0012.Mysqlx.Crud.Order:\u0004\u0088\u00ea0\u0014\"\u00c2\u0002\n\nCreateView\u0012+\n\ncollection\u0018\u0001 \u0002(\u000b2\u0017.Mysqlx.Crud.Collection\u0012\u000f\n\u0007definer\u0018\u0002 \u0001(\t\u00128\n\talgorithm\u0018\u0003 \u0001(\u000e2\u001a.Mysqlx.Crud.ViewAlgorithm:\tUNDEFINED\u00127\n\bsecurity\u0018\u0004 \u0001(\u000e2\u001c.Mysqlx.Crud.ViewSqlSecurity:\u0007DEFINER\u0012+\n\u0005check\u0018\u0005 \u0001(\u000e2\u001c.Mysqlx.Crud.ViewCheckOption\u0012\u000e\n\u0006column\u0018\u0006 \u0003(\t\u0012\u001f\n\u0004stmt\u0018\u0007 \u0002(\u000b2\u0011.Mysqlx.Crud.Find\u0012\u001f\n\u0010replace_existing\u0018\b \u0001(\b:\u0005false:\u0004\u0088\u00ea0\u001e\"\u008d\u0002\n\nModifyView\u0012+\n\ncollection\u0018\u0001 \u0002(\u000b2\u0017.Mysqlx.Crud.Collection\u0012\u000f\n\u0007definer\u0018\u0002 \u0001(\t\u0012-\n\talgorithm\u0018\u0003 \u0001(\u000e2\u001a.Mysqlx.Crud.ViewAlgorithm\u0012.\n\bsecurity\u0018\u0004 \u0001(\u000e2\u001c.Mysqlx.Crud.ViewSqlSecurity\u0012+\n\u0005check\u0018\u0005 \u0001(\u000e2\u001c.Mysqlx.Crud.ViewCheckOption\u0012\u000e\n\u0006column\u0018\u0006 \u0003(\t\u0012\u001f\n\u0004stmt\u0018\u0007 \u0001(\u000b2\u0011.Mysqlx.Crud.Find:\u0004\u0088\u00ea0\u001f\"W\n\bDropView\u0012+\n\ncollection\u0018\u0001 \u0002(\u000b2\u0017.Mysqlx.Crud.Collection\u0012\u0018\n\tif_exists\u0018\u0002 \u0001(\b:\u0005false:\u0004\u0088\u00ea0 *$\n\tDataModel\u0012\f\n\bDOCUMENT\u0010\u0001\u0012\t\n\u0005TABLE\u0010\u0002*8\n\rViewAlgorithm\u0012\r\n\tUNDEFINED\u0010\u0001\u0012\t\n\u0005MERGE\u0010\u0002\u0012\r\n\tTEMPTABLE\u0010\u0003*+\n\u000fViewSqlSecurity\u0012\u000b\n\u0007INVOKER\u0010\u0001\u0012\u000b\n\u0007DEFINER\u0010\u0002**\n\u000fViewCheckOption\u0012\t\n\u0005LOCAL\u0010\u0001\u0012\f\n\bCASCADED\u0010\u0002B\u0019\n\u0017com.mysql.cj.x.protobuf" };
        final Descriptors.FileDescriptor.InternalDescriptorAssigner assigner = (Descriptors.FileDescriptor.InternalDescriptorAssigner)new Descriptors.FileDescriptor.InternalDescriptorAssigner() {
            public ExtensionRegistry assignDescriptors(final Descriptors.FileDescriptor root) {
                MysqlxCrud.descriptor = root;
                return null;
            }
        };
        Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(descriptorData, new Descriptors.FileDescriptor[] { Mysqlx.getDescriptor(), MysqlxExpr.getDescriptor(), MysqlxDatatypes.getDescriptor() }, assigner);
        internal_static_Mysqlx_Crud_Column_descriptor = getDescriptor().getMessageTypes().get(0);
        internal_static_Mysqlx_Crud_Column_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(MysqlxCrud.internal_static_Mysqlx_Crud_Column_descriptor, new String[] { "Name", "Alias", "DocumentPath" });
        internal_static_Mysqlx_Crud_Projection_descriptor = getDescriptor().getMessageTypes().get(1);
        internal_static_Mysqlx_Crud_Projection_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(MysqlxCrud.internal_static_Mysqlx_Crud_Projection_descriptor, new String[] { "Source", "Alias" });
        internal_static_Mysqlx_Crud_Collection_descriptor = getDescriptor().getMessageTypes().get(2);
        internal_static_Mysqlx_Crud_Collection_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(MysqlxCrud.internal_static_Mysqlx_Crud_Collection_descriptor, new String[] { "Name", "Schema" });
        internal_static_Mysqlx_Crud_Limit_descriptor = getDescriptor().getMessageTypes().get(3);
        internal_static_Mysqlx_Crud_Limit_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(MysqlxCrud.internal_static_Mysqlx_Crud_Limit_descriptor, new String[] { "RowCount", "Offset" });
        internal_static_Mysqlx_Crud_Order_descriptor = getDescriptor().getMessageTypes().get(4);
        internal_static_Mysqlx_Crud_Order_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(MysqlxCrud.internal_static_Mysqlx_Crud_Order_descriptor, new String[] { "Expr", "Direction" });
        internal_static_Mysqlx_Crud_UpdateOperation_descriptor = getDescriptor().getMessageTypes().get(5);
        internal_static_Mysqlx_Crud_UpdateOperation_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(MysqlxCrud.internal_static_Mysqlx_Crud_UpdateOperation_descriptor, new String[] { "Source", "Operation", "Value" });
        internal_static_Mysqlx_Crud_Find_descriptor = getDescriptor().getMessageTypes().get(6);
        internal_static_Mysqlx_Crud_Find_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(MysqlxCrud.internal_static_Mysqlx_Crud_Find_descriptor, new String[] { "Collection", "DataModel", "Projection", "Criteria", "Args", "Limit", "Order", "Grouping", "GroupingCriteria", "Locking", "LockingOptions" });
        internal_static_Mysqlx_Crud_Insert_descriptor = getDescriptor().getMessageTypes().get(7);
        internal_static_Mysqlx_Crud_Insert_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(MysqlxCrud.internal_static_Mysqlx_Crud_Insert_descriptor, new String[] { "Collection", "DataModel", "Projection", "Row", "Args", "Upsert" });
        internal_static_Mysqlx_Crud_Insert_TypedRow_descriptor = MysqlxCrud.internal_static_Mysqlx_Crud_Insert_descriptor.getNestedTypes().get(0);
        internal_static_Mysqlx_Crud_Insert_TypedRow_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(MysqlxCrud.internal_static_Mysqlx_Crud_Insert_TypedRow_descriptor, new String[] { "Field" });
        internal_static_Mysqlx_Crud_Update_descriptor = getDescriptor().getMessageTypes().get(8);
        internal_static_Mysqlx_Crud_Update_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(MysqlxCrud.internal_static_Mysqlx_Crud_Update_descriptor, new String[] { "Collection", "DataModel", "Criteria", "Args", "Limit", "Order", "Operation" });
        internal_static_Mysqlx_Crud_Delete_descriptor = getDescriptor().getMessageTypes().get(9);
        internal_static_Mysqlx_Crud_Delete_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(MysqlxCrud.internal_static_Mysqlx_Crud_Delete_descriptor, new String[] { "Collection", "DataModel", "Criteria", "Args", "Limit", "Order" });
        internal_static_Mysqlx_Crud_CreateView_descriptor = getDescriptor().getMessageTypes().get(10);
        internal_static_Mysqlx_Crud_CreateView_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(MysqlxCrud.internal_static_Mysqlx_Crud_CreateView_descriptor, new String[] { "Collection", "Definer", "Algorithm", "Security", "Check", "Column", "Stmt", "ReplaceExisting" });
        internal_static_Mysqlx_Crud_ModifyView_descriptor = getDescriptor().getMessageTypes().get(11);
        internal_static_Mysqlx_Crud_ModifyView_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(MysqlxCrud.internal_static_Mysqlx_Crud_ModifyView_descriptor, new String[] { "Collection", "Definer", "Algorithm", "Security", "Check", "Column", "Stmt" });
        internal_static_Mysqlx_Crud_DropView_descriptor = getDescriptor().getMessageTypes().get(12);
        internal_static_Mysqlx_Crud_DropView_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(MysqlxCrud.internal_static_Mysqlx_Crud_DropView_descriptor, new String[] { "Collection", "IfExists" });
        final ExtensionRegistry registry = ExtensionRegistry.newInstance();
        registry.add((GeneratedMessage.GeneratedExtension)Mysqlx.clientMessageId);
        Descriptors.FileDescriptor.internalUpdateFileDescriptor(MysqlxCrud.descriptor, registry);
        Mysqlx.getDescriptor();
        MysqlxExpr.getDescriptor();
        MysqlxDatatypes.getDescriptor();
    }
    
    public enum DataModel implements ProtocolMessageEnum
    {
        DOCUMENT(1), 
        TABLE(2);
        
        public static final int DOCUMENT_VALUE = 1;
        public static final int TABLE_VALUE = 2;
        private static final Internal.EnumLiteMap<DataModel> internalValueMap;
        private static final DataModel[] VALUES;
        private final int value;
        
        public final int getNumber() {
            return this.value;
        }
        
        @Deprecated
        public static DataModel valueOf(final int value) {
            return forNumber(value);
        }
        
        public static DataModel forNumber(final int value) {
            switch (value) {
                case 1: {
                    return DataModel.DOCUMENT;
                }
                case 2: {
                    return DataModel.TABLE;
                }
                default: {
                    return null;
                }
            }
        }
        
        public static Internal.EnumLiteMap<DataModel> internalGetValueMap() {
            return DataModel.internalValueMap;
        }
        
        public final Descriptors.EnumValueDescriptor getValueDescriptor() {
            return getDescriptor().getValues().get(this.ordinal());
        }
        
        public final Descriptors.EnumDescriptor getDescriptorForType() {
            return getDescriptor();
        }
        
        public static final Descriptors.EnumDescriptor getDescriptor() {
            return MysqlxCrud.getDescriptor().getEnumTypes().get(0);
        }
        
        public static DataModel valueOf(final Descriptors.EnumValueDescriptor desc) {
            if (desc.getType() != getDescriptor()) {
                throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
            }
            return DataModel.VALUES[desc.getIndex()];
        }
        
        private DataModel(final int value) {
            this.value = value;
        }
        
        static {
            internalValueMap = (Internal.EnumLiteMap)new Internal.EnumLiteMap<DataModel>() {
                public DataModel findValueByNumber(final int number) {
                    return DataModel.forNumber(number);
                }
            };
            VALUES = values();
        }
    }
    
    public enum ViewAlgorithm implements ProtocolMessageEnum
    {
        UNDEFINED(1), 
        MERGE(2), 
        TEMPTABLE(3);
        
        public static final int UNDEFINED_VALUE = 1;
        public static final int MERGE_VALUE = 2;
        public static final int TEMPTABLE_VALUE = 3;
        private static final Internal.EnumLiteMap<ViewAlgorithm> internalValueMap;
        private static final ViewAlgorithm[] VALUES;
        private final int value;
        
        public final int getNumber() {
            return this.value;
        }
        
        @Deprecated
        public static ViewAlgorithm valueOf(final int value) {
            return forNumber(value);
        }
        
        public static ViewAlgorithm forNumber(final int value) {
            switch (value) {
                case 1: {
                    return ViewAlgorithm.UNDEFINED;
                }
                case 2: {
                    return ViewAlgorithm.MERGE;
                }
                case 3: {
                    return ViewAlgorithm.TEMPTABLE;
                }
                default: {
                    return null;
                }
            }
        }
        
        public static Internal.EnumLiteMap<ViewAlgorithm> internalGetValueMap() {
            return ViewAlgorithm.internalValueMap;
        }
        
        public final Descriptors.EnumValueDescriptor getValueDescriptor() {
            return getDescriptor().getValues().get(this.ordinal());
        }
        
        public final Descriptors.EnumDescriptor getDescriptorForType() {
            return getDescriptor();
        }
        
        public static final Descriptors.EnumDescriptor getDescriptor() {
            return MysqlxCrud.getDescriptor().getEnumTypes().get(1);
        }
        
        public static ViewAlgorithm valueOf(final Descriptors.EnumValueDescriptor desc) {
            if (desc.getType() != getDescriptor()) {
                throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
            }
            return ViewAlgorithm.VALUES[desc.getIndex()];
        }
        
        private ViewAlgorithm(final int value) {
            this.value = value;
        }
        
        static {
            internalValueMap = (Internal.EnumLiteMap)new Internal.EnumLiteMap<ViewAlgorithm>() {
                public ViewAlgorithm findValueByNumber(final int number) {
                    return ViewAlgorithm.forNumber(number);
                }
            };
            VALUES = values();
        }
    }
    
    public enum ViewSqlSecurity implements ProtocolMessageEnum
    {
        INVOKER(1), 
        DEFINER(2);
        
        public static final int INVOKER_VALUE = 1;
        public static final int DEFINER_VALUE = 2;
        private static final Internal.EnumLiteMap<ViewSqlSecurity> internalValueMap;
        private static final ViewSqlSecurity[] VALUES;
        private final int value;
        
        public final int getNumber() {
            return this.value;
        }
        
        @Deprecated
        public static ViewSqlSecurity valueOf(final int value) {
            return forNumber(value);
        }
        
        public static ViewSqlSecurity forNumber(final int value) {
            switch (value) {
                case 1: {
                    return ViewSqlSecurity.INVOKER;
                }
                case 2: {
                    return ViewSqlSecurity.DEFINER;
                }
                default: {
                    return null;
                }
            }
        }
        
        public static Internal.EnumLiteMap<ViewSqlSecurity> internalGetValueMap() {
            return ViewSqlSecurity.internalValueMap;
        }
        
        public final Descriptors.EnumValueDescriptor getValueDescriptor() {
            return getDescriptor().getValues().get(this.ordinal());
        }
        
        public final Descriptors.EnumDescriptor getDescriptorForType() {
            return getDescriptor();
        }
        
        public static final Descriptors.EnumDescriptor getDescriptor() {
            return MysqlxCrud.getDescriptor().getEnumTypes().get(2);
        }
        
        public static ViewSqlSecurity valueOf(final Descriptors.EnumValueDescriptor desc) {
            if (desc.getType() != getDescriptor()) {
                throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
            }
            return ViewSqlSecurity.VALUES[desc.getIndex()];
        }
        
        private ViewSqlSecurity(final int value) {
            this.value = value;
        }
        
        static {
            internalValueMap = (Internal.EnumLiteMap)new Internal.EnumLiteMap<ViewSqlSecurity>() {
                public ViewSqlSecurity findValueByNumber(final int number) {
                    return ViewSqlSecurity.forNumber(number);
                }
            };
            VALUES = values();
        }
    }
    
    public enum ViewCheckOption implements ProtocolMessageEnum
    {
        LOCAL(1), 
        CASCADED(2);
        
        public static final int LOCAL_VALUE = 1;
        public static final int CASCADED_VALUE = 2;
        private static final Internal.EnumLiteMap<ViewCheckOption> internalValueMap;
        private static final ViewCheckOption[] VALUES;
        private final int value;
        
        public final int getNumber() {
            return this.value;
        }
        
        @Deprecated
        public static ViewCheckOption valueOf(final int value) {
            return forNumber(value);
        }
        
        public static ViewCheckOption forNumber(final int value) {
            switch (value) {
                case 1: {
                    return ViewCheckOption.LOCAL;
                }
                case 2: {
                    return ViewCheckOption.CASCADED;
                }
                default: {
                    return null;
                }
            }
        }
        
        public static Internal.EnumLiteMap<ViewCheckOption> internalGetValueMap() {
            return ViewCheckOption.internalValueMap;
        }
        
        public final Descriptors.EnumValueDescriptor getValueDescriptor() {
            return getDescriptor().getValues().get(this.ordinal());
        }
        
        public final Descriptors.EnumDescriptor getDescriptorForType() {
            return getDescriptor();
        }
        
        public static final Descriptors.EnumDescriptor getDescriptor() {
            return MysqlxCrud.getDescriptor().getEnumTypes().get(3);
        }
        
        public static ViewCheckOption valueOf(final Descriptors.EnumValueDescriptor desc) {
            if (desc.getType() != getDescriptor()) {
                throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
            }
            return ViewCheckOption.VALUES[desc.getIndex()];
        }
        
        private ViewCheckOption(final int value) {
            this.value = value;
        }
        
        static {
            internalValueMap = (Internal.EnumLiteMap)new Internal.EnumLiteMap<ViewCheckOption>() {
                public ViewCheckOption findValueByNumber(final int number) {
                    return ViewCheckOption.forNumber(number);
                }
            };
            VALUES = values();
        }
    }
    
    public static final class Column extends GeneratedMessageV3 implements ColumnOrBuilder
    {
        private static final long serialVersionUID = 0L;
        private int bitField0_;
        public static final int NAME_FIELD_NUMBER = 1;
        private volatile Object name_;
        public static final int ALIAS_FIELD_NUMBER = 2;
        private volatile Object alias_;
        public static final int DOCUMENT_PATH_FIELD_NUMBER = 3;
        private List<MysqlxExpr.DocumentPathItem> documentPath_;
        private byte memoizedIsInitialized;
        private static final Column DEFAULT_INSTANCE;
        @Deprecated
        public static final Parser<Column> PARSER;
        
        private Column(final GeneratedMessageV3.Builder<?> builder) {
            super((GeneratedMessageV3.Builder)builder);
            this.memoizedIsInitialized = -1;
        }
        
        private Column() {
            this.memoizedIsInitialized = -1;
            this.name_ = "";
            this.alias_ = "";
            this.documentPath_ = Collections.emptyList();
        }
        
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }
        
        private Column(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            final ByteString bs = input.readBytes();
                            this.bitField0_ |= 0x2;
                            this.alias_ = bs;
                            continue;
                        }
                        case 26: {
                            if ((mutable_bitField0_ & 0x4) != 0x4) {
                                this.documentPath_ = new ArrayList<MysqlxExpr.DocumentPathItem>();
                                mutable_bitField0_ |= 0x4;
                            }
                            this.documentPath_.add((MysqlxExpr.DocumentPathItem)input.readMessage((Parser)MysqlxExpr.DocumentPathItem.PARSER, extensionRegistry));
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
                    this.documentPath_ = Collections.unmodifiableList((List<? extends MysqlxExpr.DocumentPathItem>)this.documentPath_);
                }
                this.unknownFields = unknownFields.build();
                this.makeExtensionsImmutable();
            }
        }
        
        public static final Descriptors.Descriptor getDescriptor() {
            return MysqlxCrud.internal_static_Mysqlx_Crud_Column_descriptor;
        }
        
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return MysqlxCrud.internal_static_Mysqlx_Crud_Column_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)Column.class, (Class)Builder.class);
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
        
        public boolean hasAlias() {
            return (this.bitField0_ & 0x2) == 0x2;
        }
        
        public String getAlias() {
            final Object ref = this.alias_;
            if (ref instanceof String) {
                return (String)ref;
            }
            final ByteString bs = (ByteString)ref;
            final String s = bs.toStringUtf8();
            if (bs.isValidUtf8()) {
                this.alias_ = s;
            }
            return s;
        }
        
        public ByteString getAliasBytes() {
            final Object ref = this.alias_;
            if (ref instanceof String) {
                final ByteString b = ByteString.copyFromUtf8((String)ref);
                return (ByteString)(this.alias_ = b);
            }
            return (ByteString)ref;
        }
        
        public List<MysqlxExpr.DocumentPathItem> getDocumentPathList() {
            return this.documentPath_;
        }
        
        public List<? extends MysqlxExpr.DocumentPathItemOrBuilder> getDocumentPathOrBuilderList() {
            return this.documentPath_;
        }
        
        public int getDocumentPathCount() {
            return this.documentPath_.size();
        }
        
        public MysqlxExpr.DocumentPathItem getDocumentPath(final int index) {
            return this.documentPath_.get(index);
        }
        
        public MysqlxExpr.DocumentPathItemOrBuilder getDocumentPathOrBuilder(final int index) {
            return this.documentPath_.get(index);
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
            if ((this.bitField0_ & 0x1) == 0x1) {
                GeneratedMessageV3.writeString(output, 1, this.name_);
            }
            if ((this.bitField0_ & 0x2) == 0x2) {
                GeneratedMessageV3.writeString(output, 2, this.alias_);
            }
            for (int i = 0; i < this.documentPath_.size(); ++i) {
                output.writeMessage(3, (MessageLite)this.documentPath_.get(i));
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
                size += GeneratedMessageV3.computeStringSize(2, this.alias_);
            }
            for (int i = 0; i < this.documentPath_.size(); ++i) {
                size += CodedOutputStream.computeMessageSize(3, (MessageLite)this.documentPath_.get(i));
            }
            size += this.unknownFields.getSerializedSize();
            return this.memoizedSize = size;
        }
        
        public boolean equals(final Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Column)) {
                return super.equals(obj);
            }
            final Column other = (Column)obj;
            boolean result = true;
            result = (result && this.hasName() == other.hasName());
            if (this.hasName()) {
                result = (result && this.getName().equals(other.getName()));
            }
            result = (result && this.hasAlias() == other.hasAlias());
            if (this.hasAlias()) {
                result = (result && this.getAlias().equals(other.getAlias()));
            }
            result = (result && this.getDocumentPathList().equals(other.getDocumentPathList()));
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
            if (this.hasAlias()) {
                hash = 37 * hash + 2;
                hash = 53 * hash + this.getAlias().hashCode();
            }
            if (this.getDocumentPathCount() > 0) {
                hash = 37 * hash + 3;
                hash = 53 * hash + this.getDocumentPathList().hashCode();
            }
            hash = 29 * hash + this.unknownFields.hashCode();
            return this.memoizedHashCode = hash;
        }
        
        public static Column parseFrom(final ByteBuffer data) throws InvalidProtocolBufferException {
            return (Column)Column.PARSER.parseFrom(data);
        }
        
        public static Column parseFrom(final ByteBuffer data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Column)Column.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Column parseFrom(final ByteString data) throws InvalidProtocolBufferException {
            return (Column)Column.PARSER.parseFrom(data);
        }
        
        public static Column parseFrom(final ByteString data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Column)Column.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Column parseFrom(final byte[] data) throws InvalidProtocolBufferException {
            return (Column)Column.PARSER.parseFrom(data);
        }
        
        public static Column parseFrom(final byte[] data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Column)Column.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Column parseFrom(final InputStream input) throws IOException {
            return (Column)GeneratedMessageV3.parseWithIOException((Parser)Column.PARSER, input);
        }
        
        public static Column parseFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Column)GeneratedMessageV3.parseWithIOException((Parser)Column.PARSER, input, extensionRegistry);
        }
        
        public static Column parseDelimitedFrom(final InputStream input) throws IOException {
            return (Column)GeneratedMessageV3.parseDelimitedWithIOException((Parser)Column.PARSER, input);
        }
        
        public static Column parseDelimitedFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Column)GeneratedMessageV3.parseDelimitedWithIOException((Parser)Column.PARSER, input, extensionRegistry);
        }
        
        public static Column parseFrom(final CodedInputStream input) throws IOException {
            return (Column)GeneratedMessageV3.parseWithIOException((Parser)Column.PARSER, input);
        }
        
        public static Column parseFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Column)GeneratedMessageV3.parseWithIOException((Parser)Column.PARSER, input, extensionRegistry);
        }
        
        public Builder newBuilderForType() {
            return newBuilder();
        }
        
        public static Builder newBuilder() {
            return Column.DEFAULT_INSTANCE.toBuilder();
        }
        
        public static Builder newBuilder(final Column prototype) {
            return Column.DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }
        
        public Builder toBuilder() {
            return (this == Column.DEFAULT_INSTANCE) ? new Builder() : new Builder().mergeFrom(this);
        }
        
        protected Builder newBuilderForType(final GeneratedMessageV3.BuilderParent parent) {
            final Builder builder = new Builder(parent);
            return builder;
        }
        
        public static Column getDefaultInstance() {
            return Column.DEFAULT_INSTANCE;
        }
        
        public static Parser<Column> parser() {
            return Column.PARSER;
        }
        
        public Parser<Column> getParserForType() {
            return Column.PARSER;
        }
        
        public Column getDefaultInstanceForType() {
            return Column.DEFAULT_INSTANCE;
        }
        
        static {
            DEFAULT_INSTANCE = new Column();
            PARSER = (Parser)new AbstractParser<Column>() {
                public Column parsePartialFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new Column(input, extensionRegistry);
                }
            };
        }
        
        public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements ColumnOrBuilder
        {
            private int bitField0_;
            private Object name_;
            private Object alias_;
            private List<MysqlxExpr.DocumentPathItem> documentPath_;
            private RepeatedFieldBuilderV3<MysqlxExpr.DocumentPathItem, MysqlxExpr.DocumentPathItem.Builder, MysqlxExpr.DocumentPathItemOrBuilder> documentPathBuilder_;
            
            public static final Descriptors.Descriptor getDescriptor() {
                return MysqlxCrud.internal_static_Mysqlx_Crud_Column_descriptor;
            }
            
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return MysqlxCrud.internal_static_Mysqlx_Crud_Column_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)Column.class, (Class)Builder.class);
            }
            
            private Builder() {
                this.name_ = "";
                this.alias_ = "";
                this.documentPath_ = Collections.emptyList();
                this.maybeForceBuilderInitialization();
            }
            
            private Builder(final GeneratedMessageV3.BuilderParent parent) {
                super(parent);
                this.name_ = "";
                this.alias_ = "";
                this.documentPath_ = Collections.emptyList();
                this.maybeForceBuilderInitialization();
            }
            
            private void maybeForceBuilderInitialization() {
                if (Column.alwaysUseFieldBuilders) {
                    this.getDocumentPathFieldBuilder();
                }
            }
            
            public Builder clear() {
                super.clear();
                this.name_ = "";
                this.bitField0_ &= 0xFFFFFFFE;
                this.alias_ = "";
                this.bitField0_ &= 0xFFFFFFFD;
                if (this.documentPathBuilder_ == null) {
                    this.documentPath_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFFB;
                }
                else {
                    this.documentPathBuilder_.clear();
                }
                return this;
            }
            
            public Descriptors.Descriptor getDescriptorForType() {
                return MysqlxCrud.internal_static_Mysqlx_Crud_Column_descriptor;
            }
            
            public Column getDefaultInstanceForType() {
                return Column.getDefaultInstance();
            }
            
            public Column build() {
                final Column result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw newUninitializedMessageException((Message)result);
                }
                return result;
            }
            
            public Column buildPartial() {
                final Column result = new Column((GeneratedMessageV3.Builder)this);
                final int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 0x1) == 0x1) {
                    to_bitField0_ |= 0x1;
                }
                result.name_ = this.name_;
                if ((from_bitField0_ & 0x2) == 0x2) {
                    to_bitField0_ |= 0x2;
                }
                result.alias_ = this.alias_;
                if (this.documentPathBuilder_ == null) {
                    if ((this.bitField0_ & 0x4) == 0x4) {
                        this.documentPath_ = Collections.unmodifiableList((List<? extends MysqlxExpr.DocumentPathItem>)this.documentPath_);
                        this.bitField0_ &= 0xFFFFFFFB;
                    }
                    result.documentPath_ = this.documentPath_;
                }
                else {
                    result.documentPath_ = (List<MysqlxExpr.DocumentPathItem>)this.documentPathBuilder_.build();
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
                if (other instanceof Column) {
                    return this.mergeFrom((Column)other);
                }
                super.mergeFrom(other);
                return this;
            }
            
            public Builder mergeFrom(final Column other) {
                if (other == Column.getDefaultInstance()) {
                    return this;
                }
                if (other.hasName()) {
                    this.bitField0_ |= 0x1;
                    this.name_ = other.name_;
                    this.onChanged();
                }
                if (other.hasAlias()) {
                    this.bitField0_ |= 0x2;
                    this.alias_ = other.alias_;
                    this.onChanged();
                }
                if (this.documentPathBuilder_ == null) {
                    if (!other.documentPath_.isEmpty()) {
                        if (this.documentPath_.isEmpty()) {
                            this.documentPath_ = other.documentPath_;
                            this.bitField0_ &= 0xFFFFFFFB;
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
                        this.bitField0_ &= 0xFFFFFFFB;
                        this.documentPathBuilder_ = (Column.alwaysUseFieldBuilders ? this.getDocumentPathFieldBuilder() : null);
                    }
                    else {
                        this.documentPathBuilder_.addAllMessages((Iterable)other.documentPath_);
                    }
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
                Column parsedMessage = null;
                try {
                    parsedMessage = (Column)Column.PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (Column)e.getUnfinishedMessage();
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
                this.name_ = Column.getDefaultInstance().getName();
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
            
            public boolean hasAlias() {
                return (this.bitField0_ & 0x2) == 0x2;
            }
            
            public String getAlias() {
                final Object ref = this.alias_;
                if (!(ref instanceof String)) {
                    final ByteString bs = (ByteString)ref;
                    final String s = bs.toStringUtf8();
                    if (bs.isValidUtf8()) {
                        this.alias_ = s;
                    }
                    return s;
                }
                return (String)ref;
            }
            
            public ByteString getAliasBytes() {
                final Object ref = this.alias_;
                if (ref instanceof String) {
                    final ByteString b = ByteString.copyFromUtf8((String)ref);
                    return (ByteString)(this.alias_ = b);
                }
                return (ByteString)ref;
            }
            
            public Builder setAlias(final String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 0x2;
                this.alias_ = value;
                this.onChanged();
                return this;
            }
            
            public Builder clearAlias() {
                this.bitField0_ &= 0xFFFFFFFD;
                this.alias_ = Column.getDefaultInstance().getAlias();
                this.onChanged();
                return this;
            }
            
            public Builder setAliasBytes(final ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 0x2;
                this.alias_ = value;
                this.onChanged();
                return this;
            }
            
            private void ensureDocumentPathIsMutable() {
                if ((this.bitField0_ & 0x4) != 0x4) {
                    this.documentPath_ = new ArrayList<MysqlxExpr.DocumentPathItem>(this.documentPath_);
                    this.bitField0_ |= 0x4;
                }
            }
            
            public List<MysqlxExpr.DocumentPathItem> getDocumentPathList() {
                if (this.documentPathBuilder_ == null) {
                    return Collections.unmodifiableList((List<? extends MysqlxExpr.DocumentPathItem>)this.documentPath_);
                }
                return (List<MysqlxExpr.DocumentPathItem>)this.documentPathBuilder_.getMessageList();
            }
            
            public int getDocumentPathCount() {
                if (this.documentPathBuilder_ == null) {
                    return this.documentPath_.size();
                }
                return this.documentPathBuilder_.getCount();
            }
            
            public MysqlxExpr.DocumentPathItem getDocumentPath(final int index) {
                if (this.documentPathBuilder_ == null) {
                    return this.documentPath_.get(index);
                }
                return (MysqlxExpr.DocumentPathItem)this.documentPathBuilder_.getMessage(index);
            }
            
            public Builder setDocumentPath(final int index, final MysqlxExpr.DocumentPathItem value) {
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
            
            public Builder setDocumentPath(final int index, final MysqlxExpr.DocumentPathItem.Builder builderForValue) {
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
            
            public Builder addDocumentPath(final MysqlxExpr.DocumentPathItem value) {
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
            
            public Builder addDocumentPath(final int index, final MysqlxExpr.DocumentPathItem value) {
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
            
            public Builder addDocumentPath(final MysqlxExpr.DocumentPathItem.Builder builderForValue) {
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
            
            public Builder addDocumentPath(final int index, final MysqlxExpr.DocumentPathItem.Builder builderForValue) {
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
            
            public Builder addAllDocumentPath(final Iterable<? extends MysqlxExpr.DocumentPathItem> values) {
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
                    this.bitField0_ &= 0xFFFFFFFB;
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
            
            public MysqlxExpr.DocumentPathItem.Builder getDocumentPathBuilder(final int index) {
                return (MysqlxExpr.DocumentPathItem.Builder)this.getDocumentPathFieldBuilder().getBuilder(index);
            }
            
            public MysqlxExpr.DocumentPathItemOrBuilder getDocumentPathOrBuilder(final int index) {
                if (this.documentPathBuilder_ == null) {
                    return this.documentPath_.get(index);
                }
                return (MysqlxExpr.DocumentPathItemOrBuilder)this.documentPathBuilder_.getMessageOrBuilder(index);
            }
            
            public List<? extends MysqlxExpr.DocumentPathItemOrBuilder> getDocumentPathOrBuilderList() {
                if (this.documentPathBuilder_ != null) {
                    return (List<? extends MysqlxExpr.DocumentPathItemOrBuilder>)this.documentPathBuilder_.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList((List<? extends MysqlxExpr.DocumentPathItemOrBuilder>)this.documentPath_);
            }
            
            public MysqlxExpr.DocumentPathItem.Builder addDocumentPathBuilder() {
                return (MysqlxExpr.DocumentPathItem.Builder)this.getDocumentPathFieldBuilder().addBuilder((AbstractMessage)MysqlxExpr.DocumentPathItem.getDefaultInstance());
            }
            
            public MysqlxExpr.DocumentPathItem.Builder addDocumentPathBuilder(final int index) {
                return (MysqlxExpr.DocumentPathItem.Builder)this.getDocumentPathFieldBuilder().addBuilder(index, (AbstractMessage)MysqlxExpr.DocumentPathItem.getDefaultInstance());
            }
            
            public List<MysqlxExpr.DocumentPathItem.Builder> getDocumentPathBuilderList() {
                return (List<MysqlxExpr.DocumentPathItem.Builder>)this.getDocumentPathFieldBuilder().getBuilderList();
            }
            
            private RepeatedFieldBuilderV3<MysqlxExpr.DocumentPathItem, MysqlxExpr.DocumentPathItem.Builder, MysqlxExpr.DocumentPathItemOrBuilder> getDocumentPathFieldBuilder() {
                if (this.documentPathBuilder_ == null) {
                    this.documentPathBuilder_ = (RepeatedFieldBuilderV3<MysqlxExpr.DocumentPathItem, MysqlxExpr.DocumentPathItem.Builder, MysqlxExpr.DocumentPathItemOrBuilder>)new RepeatedFieldBuilderV3((List)this.documentPath_, (this.bitField0_ & 0x4) == 0x4, (AbstractMessage.BuilderParent)this.getParentForChildren(), this.isClean());
                    this.documentPath_ = null;
                }
                return this.documentPathBuilder_;
            }
            
            public final Builder setUnknownFields(final UnknownFieldSet unknownFields) {
                return (Builder)super.setUnknownFields(unknownFields);
            }
            
            public final Builder mergeUnknownFields(final UnknownFieldSet unknownFields) {
                return (Builder)super.mergeUnknownFields(unknownFields);
            }
        }
    }
    
    public static final class Projection extends GeneratedMessageV3 implements ProjectionOrBuilder
    {
        private static final long serialVersionUID = 0L;
        private int bitField0_;
        public static final int SOURCE_FIELD_NUMBER = 1;
        private MysqlxExpr.Expr source_;
        public static final int ALIAS_FIELD_NUMBER = 2;
        private volatile Object alias_;
        private byte memoizedIsInitialized;
        private static final Projection DEFAULT_INSTANCE;
        @Deprecated
        public static final Parser<Projection> PARSER;
        
        private Projection(final GeneratedMessageV3.Builder<?> builder) {
            super((GeneratedMessageV3.Builder)builder);
            this.memoizedIsInitialized = -1;
        }
        
        private Projection() {
            this.memoizedIsInitialized = -1;
            this.alias_ = "";
        }
        
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }
        
        private Projection(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            MysqlxExpr.Expr.Builder subBuilder = null;
                            if ((this.bitField0_ & 0x1) == 0x1) {
                                subBuilder = this.source_.toBuilder();
                            }
                            this.source_ = (MysqlxExpr.Expr)input.readMessage((Parser)MysqlxExpr.Expr.PARSER, extensionRegistry);
                            if (subBuilder != null) {
                                subBuilder.mergeFrom(this.source_);
                                this.source_ = subBuilder.buildPartial();
                            }
                            this.bitField0_ |= 0x1;
                            continue;
                        }
                        case 18: {
                            final ByteString bs = input.readBytes();
                            this.bitField0_ |= 0x2;
                            this.alias_ = bs;
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
            return MysqlxCrud.internal_static_Mysqlx_Crud_Projection_descriptor;
        }
        
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return MysqlxCrud.internal_static_Mysqlx_Crud_Projection_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)Projection.class, (Class)Builder.class);
        }
        
        public boolean hasSource() {
            return (this.bitField0_ & 0x1) == 0x1;
        }
        
        public MysqlxExpr.Expr getSource() {
            return (this.source_ == null) ? MysqlxExpr.Expr.getDefaultInstance() : this.source_;
        }
        
        public MysqlxExpr.ExprOrBuilder getSourceOrBuilder() {
            return (this.source_ == null) ? MysqlxExpr.Expr.getDefaultInstance() : this.source_;
        }
        
        public boolean hasAlias() {
            return (this.bitField0_ & 0x2) == 0x2;
        }
        
        public String getAlias() {
            final Object ref = this.alias_;
            if (ref instanceof String) {
                return (String)ref;
            }
            final ByteString bs = (ByteString)ref;
            final String s = bs.toStringUtf8();
            if (bs.isValidUtf8()) {
                this.alias_ = s;
            }
            return s;
        }
        
        public ByteString getAliasBytes() {
            final Object ref = this.alias_;
            if (ref instanceof String) {
                final ByteString b = ByteString.copyFromUtf8((String)ref);
                return (ByteString)(this.alias_ = b);
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
            if (!this.hasSource()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            if (!this.getSource().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            this.memoizedIsInitialized = 1;
            return true;
        }
        
        public void writeTo(final CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 0x1) == 0x1) {
                output.writeMessage(1, (MessageLite)this.getSource());
            }
            if ((this.bitField0_ & 0x2) == 0x2) {
                GeneratedMessageV3.writeString(output, 2, this.alias_);
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
                size += CodedOutputStream.computeMessageSize(1, (MessageLite)this.getSource());
            }
            if ((this.bitField0_ & 0x2) == 0x2) {
                size += GeneratedMessageV3.computeStringSize(2, this.alias_);
            }
            size += this.unknownFields.getSerializedSize();
            return this.memoizedSize = size;
        }
        
        public boolean equals(final Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Projection)) {
                return super.equals(obj);
            }
            final Projection other = (Projection)obj;
            boolean result = true;
            result = (result && this.hasSource() == other.hasSource());
            if (this.hasSource()) {
                result = (result && this.getSource().equals(other.getSource()));
            }
            result = (result && this.hasAlias() == other.hasAlias());
            if (this.hasAlias()) {
                result = (result && this.getAlias().equals(other.getAlias()));
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
            if (this.hasSource()) {
                hash = 37 * hash + 1;
                hash = 53 * hash + this.getSource().hashCode();
            }
            if (this.hasAlias()) {
                hash = 37 * hash + 2;
                hash = 53 * hash + this.getAlias().hashCode();
            }
            hash = 29 * hash + this.unknownFields.hashCode();
            return this.memoizedHashCode = hash;
        }
        
        public static Projection parseFrom(final ByteBuffer data) throws InvalidProtocolBufferException {
            return (Projection)Projection.PARSER.parseFrom(data);
        }
        
        public static Projection parseFrom(final ByteBuffer data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Projection)Projection.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Projection parseFrom(final ByteString data) throws InvalidProtocolBufferException {
            return (Projection)Projection.PARSER.parseFrom(data);
        }
        
        public static Projection parseFrom(final ByteString data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Projection)Projection.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Projection parseFrom(final byte[] data) throws InvalidProtocolBufferException {
            return (Projection)Projection.PARSER.parseFrom(data);
        }
        
        public static Projection parseFrom(final byte[] data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Projection)Projection.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Projection parseFrom(final InputStream input) throws IOException {
            return (Projection)GeneratedMessageV3.parseWithIOException((Parser)Projection.PARSER, input);
        }
        
        public static Projection parseFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Projection)GeneratedMessageV3.parseWithIOException((Parser)Projection.PARSER, input, extensionRegistry);
        }
        
        public static Projection parseDelimitedFrom(final InputStream input) throws IOException {
            return (Projection)GeneratedMessageV3.parseDelimitedWithIOException((Parser)Projection.PARSER, input);
        }
        
        public static Projection parseDelimitedFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Projection)GeneratedMessageV3.parseDelimitedWithIOException((Parser)Projection.PARSER, input, extensionRegistry);
        }
        
        public static Projection parseFrom(final CodedInputStream input) throws IOException {
            return (Projection)GeneratedMessageV3.parseWithIOException((Parser)Projection.PARSER, input);
        }
        
        public static Projection parseFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Projection)GeneratedMessageV3.parseWithIOException((Parser)Projection.PARSER, input, extensionRegistry);
        }
        
        public Builder newBuilderForType() {
            return newBuilder();
        }
        
        public static Builder newBuilder() {
            return Projection.DEFAULT_INSTANCE.toBuilder();
        }
        
        public static Builder newBuilder(final Projection prototype) {
            return Projection.DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }
        
        public Builder toBuilder() {
            return (this == Projection.DEFAULT_INSTANCE) ? new Builder() : new Builder().mergeFrom(this);
        }
        
        protected Builder newBuilderForType(final GeneratedMessageV3.BuilderParent parent) {
            final Builder builder = new Builder(parent);
            return builder;
        }
        
        public static Projection getDefaultInstance() {
            return Projection.DEFAULT_INSTANCE;
        }
        
        public static Parser<Projection> parser() {
            return Projection.PARSER;
        }
        
        public Parser<Projection> getParserForType() {
            return Projection.PARSER;
        }
        
        public Projection getDefaultInstanceForType() {
            return Projection.DEFAULT_INSTANCE;
        }
        
        static {
            DEFAULT_INSTANCE = new Projection();
            PARSER = (Parser)new AbstractParser<Projection>() {
                public Projection parsePartialFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new Projection(input, extensionRegistry);
                }
            };
        }
        
        public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements ProjectionOrBuilder
        {
            private int bitField0_;
            private MysqlxExpr.Expr source_;
            private SingleFieldBuilderV3<MysqlxExpr.Expr, MysqlxExpr.Expr.Builder, MysqlxExpr.ExprOrBuilder> sourceBuilder_;
            private Object alias_;
            
            public static final Descriptors.Descriptor getDescriptor() {
                return MysqlxCrud.internal_static_Mysqlx_Crud_Projection_descriptor;
            }
            
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return MysqlxCrud.internal_static_Mysqlx_Crud_Projection_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)Projection.class, (Class)Builder.class);
            }
            
            private Builder() {
                this.source_ = null;
                this.alias_ = "";
                this.maybeForceBuilderInitialization();
            }
            
            private Builder(final GeneratedMessageV3.BuilderParent parent) {
                super(parent);
                this.source_ = null;
                this.alias_ = "";
                this.maybeForceBuilderInitialization();
            }
            
            private void maybeForceBuilderInitialization() {
                if (Projection.alwaysUseFieldBuilders) {
                    this.getSourceFieldBuilder();
                }
            }
            
            public Builder clear() {
                super.clear();
                if (this.sourceBuilder_ == null) {
                    this.source_ = null;
                }
                else {
                    this.sourceBuilder_.clear();
                }
                this.bitField0_ &= 0xFFFFFFFE;
                this.alias_ = "";
                this.bitField0_ &= 0xFFFFFFFD;
                return this;
            }
            
            public Descriptors.Descriptor getDescriptorForType() {
                return MysqlxCrud.internal_static_Mysqlx_Crud_Projection_descriptor;
            }
            
            public Projection getDefaultInstanceForType() {
                return Projection.getDefaultInstance();
            }
            
            public Projection build() {
                final Projection result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw newUninitializedMessageException((Message)result);
                }
                return result;
            }
            
            public Projection buildPartial() {
                final Projection result = new Projection((GeneratedMessageV3.Builder)this);
                final int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 0x1) == 0x1) {
                    to_bitField0_ |= 0x1;
                }
                if (this.sourceBuilder_ == null) {
                    result.source_ = this.source_;
                }
                else {
                    result.source_ = (MysqlxExpr.Expr)this.sourceBuilder_.build();
                }
                if ((from_bitField0_ & 0x2) == 0x2) {
                    to_bitField0_ |= 0x2;
                }
                result.alias_ = this.alias_;
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
                if (other instanceof Projection) {
                    return this.mergeFrom((Projection)other);
                }
                super.mergeFrom(other);
                return this;
            }
            
            public Builder mergeFrom(final Projection other) {
                if (other == Projection.getDefaultInstance()) {
                    return this;
                }
                if (other.hasSource()) {
                    this.mergeSource(other.getSource());
                }
                if (other.hasAlias()) {
                    this.bitField0_ |= 0x2;
                    this.alias_ = other.alias_;
                    this.onChanged();
                }
                this.mergeUnknownFields(other.unknownFields);
                this.onChanged();
                return this;
            }
            
            public final boolean isInitialized() {
                return this.hasSource() && this.getSource().isInitialized();
            }
            
            public Builder mergeFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
                Projection parsedMessage = null;
                try {
                    parsedMessage = (Projection)Projection.PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (Projection)e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                }
                finally {
                    if (parsedMessage != null) {
                        this.mergeFrom(parsedMessage);
                    }
                }
                return this;
            }
            
            public boolean hasSource() {
                return (this.bitField0_ & 0x1) == 0x1;
            }
            
            public MysqlxExpr.Expr getSource() {
                if (this.sourceBuilder_ == null) {
                    return (this.source_ == null) ? MysqlxExpr.Expr.getDefaultInstance() : this.source_;
                }
                return (MysqlxExpr.Expr)this.sourceBuilder_.getMessage();
            }
            
            public Builder setSource(final MysqlxExpr.Expr value) {
                if (this.sourceBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.source_ = value;
                    this.onChanged();
                }
                else {
                    this.sourceBuilder_.setMessage((AbstractMessage)value);
                }
                this.bitField0_ |= 0x1;
                return this;
            }
            
            public Builder setSource(final MysqlxExpr.Expr.Builder builderForValue) {
                if (this.sourceBuilder_ == null) {
                    this.source_ = builderForValue.build();
                    this.onChanged();
                }
                else {
                    this.sourceBuilder_.setMessage((AbstractMessage)builderForValue.build());
                }
                this.bitField0_ |= 0x1;
                return this;
            }
            
            public Builder mergeSource(final MysqlxExpr.Expr value) {
                if (this.sourceBuilder_ == null) {
                    if ((this.bitField0_ & 0x1) == 0x1 && this.source_ != null && this.source_ != MysqlxExpr.Expr.getDefaultInstance()) {
                        this.source_ = MysqlxExpr.Expr.newBuilder(this.source_).mergeFrom(value).buildPartial();
                    }
                    else {
                        this.source_ = value;
                    }
                    this.onChanged();
                }
                else {
                    this.sourceBuilder_.mergeFrom((AbstractMessage)value);
                }
                this.bitField0_ |= 0x1;
                return this;
            }
            
            public Builder clearSource() {
                if (this.sourceBuilder_ == null) {
                    this.source_ = null;
                    this.onChanged();
                }
                else {
                    this.sourceBuilder_.clear();
                }
                this.bitField0_ &= 0xFFFFFFFE;
                return this;
            }
            
            public MysqlxExpr.Expr.Builder getSourceBuilder() {
                this.bitField0_ |= 0x1;
                this.onChanged();
                return (MysqlxExpr.Expr.Builder)this.getSourceFieldBuilder().getBuilder();
            }
            
            public MysqlxExpr.ExprOrBuilder getSourceOrBuilder() {
                if (this.sourceBuilder_ != null) {
                    return (MysqlxExpr.ExprOrBuilder)this.sourceBuilder_.getMessageOrBuilder();
                }
                return (this.source_ == null) ? MysqlxExpr.Expr.getDefaultInstance() : this.source_;
            }
            
            private SingleFieldBuilderV3<MysqlxExpr.Expr, MysqlxExpr.Expr.Builder, MysqlxExpr.ExprOrBuilder> getSourceFieldBuilder() {
                if (this.sourceBuilder_ == null) {
                    this.sourceBuilder_ = (SingleFieldBuilderV3<MysqlxExpr.Expr, MysqlxExpr.Expr.Builder, MysqlxExpr.ExprOrBuilder>)new SingleFieldBuilderV3((AbstractMessage)this.getSource(), (AbstractMessage.BuilderParent)this.getParentForChildren(), this.isClean());
                    this.source_ = null;
                }
                return this.sourceBuilder_;
            }
            
            public boolean hasAlias() {
                return (this.bitField0_ & 0x2) == 0x2;
            }
            
            public String getAlias() {
                final Object ref = this.alias_;
                if (!(ref instanceof String)) {
                    final ByteString bs = (ByteString)ref;
                    final String s = bs.toStringUtf8();
                    if (bs.isValidUtf8()) {
                        this.alias_ = s;
                    }
                    return s;
                }
                return (String)ref;
            }
            
            public ByteString getAliasBytes() {
                final Object ref = this.alias_;
                if (ref instanceof String) {
                    final ByteString b = ByteString.copyFromUtf8((String)ref);
                    return (ByteString)(this.alias_ = b);
                }
                return (ByteString)ref;
            }
            
            public Builder setAlias(final String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 0x2;
                this.alias_ = value;
                this.onChanged();
                return this;
            }
            
            public Builder clearAlias() {
                this.bitField0_ &= 0xFFFFFFFD;
                this.alias_ = Projection.getDefaultInstance().getAlias();
                this.onChanged();
                return this;
            }
            
            public Builder setAliasBytes(final ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 0x2;
                this.alias_ = value;
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
    
    public static final class Collection extends GeneratedMessageV3 implements CollectionOrBuilder
    {
        private static final long serialVersionUID = 0L;
        private int bitField0_;
        public static final int NAME_FIELD_NUMBER = 1;
        private volatile Object name_;
        public static final int SCHEMA_FIELD_NUMBER = 2;
        private volatile Object schema_;
        private byte memoizedIsInitialized;
        private static final Collection DEFAULT_INSTANCE;
        @Deprecated
        public static final Parser<Collection> PARSER;
        
        private Collection(final GeneratedMessageV3.Builder<?> builder) {
            super((GeneratedMessageV3.Builder)builder);
            this.memoizedIsInitialized = -1;
        }
        
        private Collection() {
            this.memoizedIsInitialized = -1;
            this.name_ = "";
            this.schema_ = "";
        }
        
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }
        
        private Collection(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            this.schema_ = bs;
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
            return MysqlxCrud.internal_static_Mysqlx_Crud_Collection_descriptor;
        }
        
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return MysqlxCrud.internal_static_Mysqlx_Crud_Collection_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)Collection.class, (Class)Builder.class);
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
        
        public boolean hasSchema() {
            return (this.bitField0_ & 0x2) == 0x2;
        }
        
        public String getSchema() {
            final Object ref = this.schema_;
            if (ref instanceof String) {
                return (String)ref;
            }
            final ByteString bs = (ByteString)ref;
            final String s = bs.toStringUtf8();
            if (bs.isValidUtf8()) {
                this.schema_ = s;
            }
            return s;
        }
        
        public ByteString getSchemaBytes() {
            final Object ref = this.schema_;
            if (ref instanceof String) {
                final ByteString b = ByteString.copyFromUtf8((String)ref);
                return (ByteString)(this.schema_ = b);
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
                GeneratedMessageV3.writeString(output, 2, this.schema_);
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
                size += GeneratedMessageV3.computeStringSize(2, this.schema_);
            }
            size += this.unknownFields.getSerializedSize();
            return this.memoizedSize = size;
        }
        
        public boolean equals(final Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Collection)) {
                return super.equals(obj);
            }
            final Collection other = (Collection)obj;
            boolean result = true;
            result = (result && this.hasName() == other.hasName());
            if (this.hasName()) {
                result = (result && this.getName().equals(other.getName()));
            }
            result = (result && this.hasSchema() == other.hasSchema());
            if (this.hasSchema()) {
                result = (result && this.getSchema().equals(other.getSchema()));
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
            if (this.hasSchema()) {
                hash = 37 * hash + 2;
                hash = 53 * hash + this.getSchema().hashCode();
            }
            hash = 29 * hash + this.unknownFields.hashCode();
            return this.memoizedHashCode = hash;
        }
        
        public static Collection parseFrom(final ByteBuffer data) throws InvalidProtocolBufferException {
            return (Collection)Collection.PARSER.parseFrom(data);
        }
        
        public static Collection parseFrom(final ByteBuffer data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Collection)Collection.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Collection parseFrom(final ByteString data) throws InvalidProtocolBufferException {
            return (Collection)Collection.PARSER.parseFrom(data);
        }
        
        public static Collection parseFrom(final ByteString data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Collection)Collection.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Collection parseFrom(final byte[] data) throws InvalidProtocolBufferException {
            return (Collection)Collection.PARSER.parseFrom(data);
        }
        
        public static Collection parseFrom(final byte[] data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Collection)Collection.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Collection parseFrom(final InputStream input) throws IOException {
            return (Collection)GeneratedMessageV3.parseWithIOException((Parser)Collection.PARSER, input);
        }
        
        public static Collection parseFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Collection)GeneratedMessageV3.parseWithIOException((Parser)Collection.PARSER, input, extensionRegistry);
        }
        
        public static Collection parseDelimitedFrom(final InputStream input) throws IOException {
            return (Collection)GeneratedMessageV3.parseDelimitedWithIOException((Parser)Collection.PARSER, input);
        }
        
        public static Collection parseDelimitedFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Collection)GeneratedMessageV3.parseDelimitedWithIOException((Parser)Collection.PARSER, input, extensionRegistry);
        }
        
        public static Collection parseFrom(final CodedInputStream input) throws IOException {
            return (Collection)GeneratedMessageV3.parseWithIOException((Parser)Collection.PARSER, input);
        }
        
        public static Collection parseFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Collection)GeneratedMessageV3.parseWithIOException((Parser)Collection.PARSER, input, extensionRegistry);
        }
        
        public Builder newBuilderForType() {
            return newBuilder();
        }
        
        public static Builder newBuilder() {
            return Collection.DEFAULT_INSTANCE.toBuilder();
        }
        
        public static Builder newBuilder(final Collection prototype) {
            return Collection.DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }
        
        public Builder toBuilder() {
            return (this == Collection.DEFAULT_INSTANCE) ? new Builder() : new Builder().mergeFrom(this);
        }
        
        protected Builder newBuilderForType(final GeneratedMessageV3.BuilderParent parent) {
            final Builder builder = new Builder(parent);
            return builder;
        }
        
        public static Collection getDefaultInstance() {
            return Collection.DEFAULT_INSTANCE;
        }
        
        public static Parser<Collection> parser() {
            return Collection.PARSER;
        }
        
        public Parser<Collection> getParserForType() {
            return Collection.PARSER;
        }
        
        public Collection getDefaultInstanceForType() {
            return Collection.DEFAULT_INSTANCE;
        }
        
        static {
            DEFAULT_INSTANCE = new Collection();
            PARSER = (Parser)new AbstractParser<Collection>() {
                public Collection parsePartialFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new Collection(input, extensionRegistry);
                }
            };
        }
        
        public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements CollectionOrBuilder
        {
            private int bitField0_;
            private Object name_;
            private Object schema_;
            
            public static final Descriptors.Descriptor getDescriptor() {
                return MysqlxCrud.internal_static_Mysqlx_Crud_Collection_descriptor;
            }
            
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return MysqlxCrud.internal_static_Mysqlx_Crud_Collection_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)Collection.class, (Class)Builder.class);
            }
            
            private Builder() {
                this.name_ = "";
                this.schema_ = "";
                this.maybeForceBuilderInitialization();
            }
            
            private Builder(final GeneratedMessageV3.BuilderParent parent) {
                super(parent);
                this.name_ = "";
                this.schema_ = "";
                this.maybeForceBuilderInitialization();
            }
            
            private void maybeForceBuilderInitialization() {
                if (Collection.alwaysUseFieldBuilders) {}
            }
            
            public Builder clear() {
                super.clear();
                this.name_ = "";
                this.bitField0_ &= 0xFFFFFFFE;
                this.schema_ = "";
                this.bitField0_ &= 0xFFFFFFFD;
                return this;
            }
            
            public Descriptors.Descriptor getDescriptorForType() {
                return MysqlxCrud.internal_static_Mysqlx_Crud_Collection_descriptor;
            }
            
            public Collection getDefaultInstanceForType() {
                return Collection.getDefaultInstance();
            }
            
            public Collection build() {
                final Collection result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw newUninitializedMessageException((Message)result);
                }
                return result;
            }
            
            public Collection buildPartial() {
                final Collection result = new Collection((GeneratedMessageV3.Builder)this);
                final int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 0x1) == 0x1) {
                    to_bitField0_ |= 0x1;
                }
                result.name_ = this.name_;
                if ((from_bitField0_ & 0x2) == 0x2) {
                    to_bitField0_ |= 0x2;
                }
                result.schema_ = this.schema_;
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
                if (other instanceof Collection) {
                    return this.mergeFrom((Collection)other);
                }
                super.mergeFrom(other);
                return this;
            }
            
            public Builder mergeFrom(final Collection other) {
                if (other == Collection.getDefaultInstance()) {
                    return this;
                }
                if (other.hasName()) {
                    this.bitField0_ |= 0x1;
                    this.name_ = other.name_;
                    this.onChanged();
                }
                if (other.hasSchema()) {
                    this.bitField0_ |= 0x2;
                    this.schema_ = other.schema_;
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
                Collection parsedMessage = null;
                try {
                    parsedMessage = (Collection)Collection.PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (Collection)e.getUnfinishedMessage();
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
                this.name_ = Collection.getDefaultInstance().getName();
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
            
            public boolean hasSchema() {
                return (this.bitField0_ & 0x2) == 0x2;
            }
            
            public String getSchema() {
                final Object ref = this.schema_;
                if (!(ref instanceof String)) {
                    final ByteString bs = (ByteString)ref;
                    final String s = bs.toStringUtf8();
                    if (bs.isValidUtf8()) {
                        this.schema_ = s;
                    }
                    return s;
                }
                return (String)ref;
            }
            
            public ByteString getSchemaBytes() {
                final Object ref = this.schema_;
                if (ref instanceof String) {
                    final ByteString b = ByteString.copyFromUtf8((String)ref);
                    return (ByteString)(this.schema_ = b);
                }
                return (ByteString)ref;
            }
            
            public Builder setSchema(final String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 0x2;
                this.schema_ = value;
                this.onChanged();
                return this;
            }
            
            public Builder clearSchema() {
                this.bitField0_ &= 0xFFFFFFFD;
                this.schema_ = Collection.getDefaultInstance().getSchema();
                this.onChanged();
                return this;
            }
            
            public Builder setSchemaBytes(final ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 0x2;
                this.schema_ = value;
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
    
    public static final class Limit extends GeneratedMessageV3 implements LimitOrBuilder
    {
        private static final long serialVersionUID = 0L;
        private int bitField0_;
        public static final int ROW_COUNT_FIELD_NUMBER = 1;
        private long rowCount_;
        public static final int OFFSET_FIELD_NUMBER = 2;
        private long offset_;
        private byte memoizedIsInitialized;
        private static final Limit DEFAULT_INSTANCE;
        @Deprecated
        public static final Parser<Limit> PARSER;
        
        private Limit(final GeneratedMessageV3.Builder<?> builder) {
            super((GeneratedMessageV3.Builder)builder);
            this.memoizedIsInitialized = -1;
        }
        
        private Limit() {
            this.memoizedIsInitialized = -1;
            this.rowCount_ = 0L;
            this.offset_ = 0L;
        }
        
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }
        
        private Limit(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            this.rowCount_ = input.readUInt64();
                            continue;
                        }
                        case 16: {
                            this.bitField0_ |= 0x2;
                            this.offset_ = input.readUInt64();
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
            return MysqlxCrud.internal_static_Mysqlx_Crud_Limit_descriptor;
        }
        
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return MysqlxCrud.internal_static_Mysqlx_Crud_Limit_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)Limit.class, (Class)Builder.class);
        }
        
        public boolean hasRowCount() {
            return (this.bitField0_ & 0x1) == 0x1;
        }
        
        public long getRowCount() {
            return this.rowCount_;
        }
        
        public boolean hasOffset() {
            return (this.bitField0_ & 0x2) == 0x2;
        }
        
        public long getOffset() {
            return this.offset_;
        }
        
        public final boolean isInitialized() {
            final byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (!this.hasRowCount()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            this.memoizedIsInitialized = 1;
            return true;
        }
        
        public void writeTo(final CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 0x1) == 0x1) {
                output.writeUInt64(1, this.rowCount_);
            }
            if ((this.bitField0_ & 0x2) == 0x2) {
                output.writeUInt64(2, this.offset_);
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
                size += CodedOutputStream.computeUInt64Size(1, this.rowCount_);
            }
            if ((this.bitField0_ & 0x2) == 0x2) {
                size += CodedOutputStream.computeUInt64Size(2, this.offset_);
            }
            size += this.unknownFields.getSerializedSize();
            return this.memoizedSize = size;
        }
        
        public boolean equals(final Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Limit)) {
                return super.equals(obj);
            }
            final Limit other = (Limit)obj;
            boolean result = true;
            result = (result && this.hasRowCount() == other.hasRowCount());
            if (this.hasRowCount()) {
                result = (result && this.getRowCount() == other.getRowCount());
            }
            result = (result && this.hasOffset() == other.hasOffset());
            if (this.hasOffset()) {
                result = (result && this.getOffset() == other.getOffset());
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
            if (this.hasRowCount()) {
                hash = 37 * hash + 1;
                hash = 53 * hash + Internal.hashLong(this.getRowCount());
            }
            if (this.hasOffset()) {
                hash = 37 * hash + 2;
                hash = 53 * hash + Internal.hashLong(this.getOffset());
            }
            hash = 29 * hash + this.unknownFields.hashCode();
            return this.memoizedHashCode = hash;
        }
        
        public static Limit parseFrom(final ByteBuffer data) throws InvalidProtocolBufferException {
            return (Limit)Limit.PARSER.parseFrom(data);
        }
        
        public static Limit parseFrom(final ByteBuffer data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Limit)Limit.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Limit parseFrom(final ByteString data) throws InvalidProtocolBufferException {
            return (Limit)Limit.PARSER.parseFrom(data);
        }
        
        public static Limit parseFrom(final ByteString data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Limit)Limit.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Limit parseFrom(final byte[] data) throws InvalidProtocolBufferException {
            return (Limit)Limit.PARSER.parseFrom(data);
        }
        
        public static Limit parseFrom(final byte[] data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Limit)Limit.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Limit parseFrom(final InputStream input) throws IOException {
            return (Limit)GeneratedMessageV3.parseWithIOException((Parser)Limit.PARSER, input);
        }
        
        public static Limit parseFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Limit)GeneratedMessageV3.parseWithIOException((Parser)Limit.PARSER, input, extensionRegistry);
        }
        
        public static Limit parseDelimitedFrom(final InputStream input) throws IOException {
            return (Limit)GeneratedMessageV3.parseDelimitedWithIOException((Parser)Limit.PARSER, input);
        }
        
        public static Limit parseDelimitedFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Limit)GeneratedMessageV3.parseDelimitedWithIOException((Parser)Limit.PARSER, input, extensionRegistry);
        }
        
        public static Limit parseFrom(final CodedInputStream input) throws IOException {
            return (Limit)GeneratedMessageV3.parseWithIOException((Parser)Limit.PARSER, input);
        }
        
        public static Limit parseFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Limit)GeneratedMessageV3.parseWithIOException((Parser)Limit.PARSER, input, extensionRegistry);
        }
        
        public Builder newBuilderForType() {
            return newBuilder();
        }
        
        public static Builder newBuilder() {
            return Limit.DEFAULT_INSTANCE.toBuilder();
        }
        
        public static Builder newBuilder(final Limit prototype) {
            return Limit.DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }
        
        public Builder toBuilder() {
            return (this == Limit.DEFAULT_INSTANCE) ? new Builder() : new Builder().mergeFrom(this);
        }
        
        protected Builder newBuilderForType(final GeneratedMessageV3.BuilderParent parent) {
            final Builder builder = new Builder(parent);
            return builder;
        }
        
        public static Limit getDefaultInstance() {
            return Limit.DEFAULT_INSTANCE;
        }
        
        public static Parser<Limit> parser() {
            return Limit.PARSER;
        }
        
        public Parser<Limit> getParserForType() {
            return Limit.PARSER;
        }
        
        public Limit getDefaultInstanceForType() {
            return Limit.DEFAULT_INSTANCE;
        }
        
        static {
            DEFAULT_INSTANCE = new Limit();
            PARSER = (Parser)new AbstractParser<Limit>() {
                public Limit parsePartialFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new Limit(input, extensionRegistry);
                }
            };
        }
        
        public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements LimitOrBuilder
        {
            private int bitField0_;
            private long rowCount_;
            private long offset_;
            
            public static final Descriptors.Descriptor getDescriptor() {
                return MysqlxCrud.internal_static_Mysqlx_Crud_Limit_descriptor;
            }
            
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return MysqlxCrud.internal_static_Mysqlx_Crud_Limit_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)Limit.class, (Class)Builder.class);
            }
            
            private Builder() {
                this.maybeForceBuilderInitialization();
            }
            
            private Builder(final GeneratedMessageV3.BuilderParent parent) {
                super(parent);
                this.maybeForceBuilderInitialization();
            }
            
            private void maybeForceBuilderInitialization() {
                if (Limit.alwaysUseFieldBuilders) {}
            }
            
            public Builder clear() {
                super.clear();
                this.rowCount_ = 0L;
                this.bitField0_ &= 0xFFFFFFFE;
                this.offset_ = 0L;
                this.bitField0_ &= 0xFFFFFFFD;
                return this;
            }
            
            public Descriptors.Descriptor getDescriptorForType() {
                return MysqlxCrud.internal_static_Mysqlx_Crud_Limit_descriptor;
            }
            
            public Limit getDefaultInstanceForType() {
                return Limit.getDefaultInstance();
            }
            
            public Limit build() {
                final Limit result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw newUninitializedMessageException((Message)result);
                }
                return result;
            }
            
            public Limit buildPartial() {
                final Limit result = new Limit((GeneratedMessageV3.Builder)this);
                final int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 0x1) == 0x1) {
                    to_bitField0_ |= 0x1;
                }
                result.rowCount_ = this.rowCount_;
                if ((from_bitField0_ & 0x2) == 0x2) {
                    to_bitField0_ |= 0x2;
                }
                result.offset_ = this.offset_;
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
                if (other instanceof Limit) {
                    return this.mergeFrom((Limit)other);
                }
                super.mergeFrom(other);
                return this;
            }
            
            public Builder mergeFrom(final Limit other) {
                if (other == Limit.getDefaultInstance()) {
                    return this;
                }
                if (other.hasRowCount()) {
                    this.setRowCount(other.getRowCount());
                }
                if (other.hasOffset()) {
                    this.setOffset(other.getOffset());
                }
                this.mergeUnknownFields(other.unknownFields);
                this.onChanged();
                return this;
            }
            
            public final boolean isInitialized() {
                return this.hasRowCount();
            }
            
            public Builder mergeFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
                Limit parsedMessage = null;
                try {
                    parsedMessage = (Limit)Limit.PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (Limit)e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                }
                finally {
                    if (parsedMessage != null) {
                        this.mergeFrom(parsedMessage);
                    }
                }
                return this;
            }
            
            public boolean hasRowCount() {
                return (this.bitField0_ & 0x1) == 0x1;
            }
            
            public long getRowCount() {
                return this.rowCount_;
            }
            
            public Builder setRowCount(final long value) {
                this.bitField0_ |= 0x1;
                this.rowCount_ = value;
                this.onChanged();
                return this;
            }
            
            public Builder clearRowCount() {
                this.bitField0_ &= 0xFFFFFFFE;
                this.rowCount_ = 0L;
                this.onChanged();
                return this;
            }
            
            public boolean hasOffset() {
                return (this.bitField0_ & 0x2) == 0x2;
            }
            
            public long getOffset() {
                return this.offset_;
            }
            
            public Builder setOffset(final long value) {
                this.bitField0_ |= 0x2;
                this.offset_ = value;
                this.onChanged();
                return this;
            }
            
            public Builder clearOffset() {
                this.bitField0_ &= 0xFFFFFFFD;
                this.offset_ = 0L;
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
    
    public static final class Order extends GeneratedMessageV3 implements OrderOrBuilder
    {
        private static final long serialVersionUID = 0L;
        private int bitField0_;
        public static final int EXPR_FIELD_NUMBER = 1;
        private MysqlxExpr.Expr expr_;
        public static final int DIRECTION_FIELD_NUMBER = 2;
        private int direction_;
        private byte memoizedIsInitialized;
        private static final Order DEFAULT_INSTANCE;
        @Deprecated
        public static final Parser<Order> PARSER;
        
        private Order(final GeneratedMessageV3.Builder<?> builder) {
            super((GeneratedMessageV3.Builder)builder);
            this.memoizedIsInitialized = -1;
        }
        
        private Order() {
            this.memoizedIsInitialized = -1;
            this.direction_ = 1;
        }
        
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }
        
        private Order(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            MysqlxExpr.Expr.Builder subBuilder = null;
                            if ((this.bitField0_ & 0x1) == 0x1) {
                                subBuilder = this.expr_.toBuilder();
                            }
                            this.expr_ = (MysqlxExpr.Expr)input.readMessage((Parser)MysqlxExpr.Expr.PARSER, extensionRegistry);
                            if (subBuilder != null) {
                                subBuilder.mergeFrom(this.expr_);
                                this.expr_ = subBuilder.buildPartial();
                            }
                            this.bitField0_ |= 0x1;
                            continue;
                        }
                        case 16: {
                            final int rawValue = input.readEnum();
                            final Direction value = Direction.valueOf(rawValue);
                            if (value == null) {
                                unknownFields.mergeVarintField(2, rawValue);
                                continue;
                            }
                            this.bitField0_ |= 0x2;
                            this.direction_ = rawValue;
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
            return MysqlxCrud.internal_static_Mysqlx_Crud_Order_descriptor;
        }
        
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return MysqlxCrud.internal_static_Mysqlx_Crud_Order_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)Order.class, (Class)Builder.class);
        }
        
        public boolean hasExpr() {
            return (this.bitField0_ & 0x1) == 0x1;
        }
        
        public MysqlxExpr.Expr getExpr() {
            return (this.expr_ == null) ? MysqlxExpr.Expr.getDefaultInstance() : this.expr_;
        }
        
        public MysqlxExpr.ExprOrBuilder getExprOrBuilder() {
            return (this.expr_ == null) ? MysqlxExpr.Expr.getDefaultInstance() : this.expr_;
        }
        
        public boolean hasDirection() {
            return (this.bitField0_ & 0x2) == 0x2;
        }
        
        public Direction getDirection() {
            final Direction result = Direction.valueOf(this.direction_);
            return (result == null) ? Direction.ASC : result;
        }
        
        public final boolean isInitialized() {
            final byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (!this.hasExpr()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            if (!this.getExpr().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            this.memoizedIsInitialized = 1;
            return true;
        }
        
        public void writeTo(final CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 0x1) == 0x1) {
                output.writeMessage(1, (MessageLite)this.getExpr());
            }
            if ((this.bitField0_ & 0x2) == 0x2) {
                output.writeEnum(2, this.direction_);
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
                size += CodedOutputStream.computeMessageSize(1, (MessageLite)this.getExpr());
            }
            if ((this.bitField0_ & 0x2) == 0x2) {
                size += CodedOutputStream.computeEnumSize(2, this.direction_);
            }
            size += this.unknownFields.getSerializedSize();
            return this.memoizedSize = size;
        }
        
        public boolean equals(final Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Order)) {
                return super.equals(obj);
            }
            final Order other = (Order)obj;
            boolean result = true;
            result = (result && this.hasExpr() == other.hasExpr());
            if (this.hasExpr()) {
                result = (result && this.getExpr().equals(other.getExpr()));
            }
            result = (result && this.hasDirection() == other.hasDirection());
            if (this.hasDirection()) {
                result = (result && this.direction_ == other.direction_);
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
            if (this.hasExpr()) {
                hash = 37 * hash + 1;
                hash = 53 * hash + this.getExpr().hashCode();
            }
            if (this.hasDirection()) {
                hash = 37 * hash + 2;
                hash = 53 * hash + this.direction_;
            }
            hash = 29 * hash + this.unknownFields.hashCode();
            return this.memoizedHashCode = hash;
        }
        
        public static Order parseFrom(final ByteBuffer data) throws InvalidProtocolBufferException {
            return (Order)Order.PARSER.parseFrom(data);
        }
        
        public static Order parseFrom(final ByteBuffer data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Order)Order.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Order parseFrom(final ByteString data) throws InvalidProtocolBufferException {
            return (Order)Order.PARSER.parseFrom(data);
        }
        
        public static Order parseFrom(final ByteString data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Order)Order.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Order parseFrom(final byte[] data) throws InvalidProtocolBufferException {
            return (Order)Order.PARSER.parseFrom(data);
        }
        
        public static Order parseFrom(final byte[] data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Order)Order.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Order parseFrom(final InputStream input) throws IOException {
            return (Order)GeneratedMessageV3.parseWithIOException((Parser)Order.PARSER, input);
        }
        
        public static Order parseFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Order)GeneratedMessageV3.parseWithIOException((Parser)Order.PARSER, input, extensionRegistry);
        }
        
        public static Order parseDelimitedFrom(final InputStream input) throws IOException {
            return (Order)GeneratedMessageV3.parseDelimitedWithIOException((Parser)Order.PARSER, input);
        }
        
        public static Order parseDelimitedFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Order)GeneratedMessageV3.parseDelimitedWithIOException((Parser)Order.PARSER, input, extensionRegistry);
        }
        
        public static Order parseFrom(final CodedInputStream input) throws IOException {
            return (Order)GeneratedMessageV3.parseWithIOException((Parser)Order.PARSER, input);
        }
        
        public static Order parseFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Order)GeneratedMessageV3.parseWithIOException((Parser)Order.PARSER, input, extensionRegistry);
        }
        
        public Builder newBuilderForType() {
            return newBuilder();
        }
        
        public static Builder newBuilder() {
            return Order.DEFAULT_INSTANCE.toBuilder();
        }
        
        public static Builder newBuilder(final Order prototype) {
            return Order.DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }
        
        public Builder toBuilder() {
            return (this == Order.DEFAULT_INSTANCE) ? new Builder() : new Builder().mergeFrom(this);
        }
        
        protected Builder newBuilderForType(final GeneratedMessageV3.BuilderParent parent) {
            final Builder builder = new Builder(parent);
            return builder;
        }
        
        public static Order getDefaultInstance() {
            return Order.DEFAULT_INSTANCE;
        }
        
        public static Parser<Order> parser() {
            return Order.PARSER;
        }
        
        public Parser<Order> getParserForType() {
            return Order.PARSER;
        }
        
        public Order getDefaultInstanceForType() {
            return Order.DEFAULT_INSTANCE;
        }
        
        static {
            DEFAULT_INSTANCE = new Order();
            PARSER = (Parser)new AbstractParser<Order>() {
                public Order parsePartialFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new Order(input, extensionRegistry);
                }
            };
        }
        
        public enum Direction implements ProtocolMessageEnum
        {
            ASC(1), 
            DESC(2);
            
            public static final int ASC_VALUE = 1;
            public static final int DESC_VALUE = 2;
            private static final Internal.EnumLiteMap<Direction> internalValueMap;
            private static final Direction[] VALUES;
            private final int value;
            
            public final int getNumber() {
                return this.value;
            }
            
            @Deprecated
            public static Direction valueOf(final int value) {
                return forNumber(value);
            }
            
            public static Direction forNumber(final int value) {
                switch (value) {
                    case 1: {
                        return Direction.ASC;
                    }
                    case 2: {
                        return Direction.DESC;
                    }
                    default: {
                        return null;
                    }
                }
            }
            
            public static Internal.EnumLiteMap<Direction> internalGetValueMap() {
                return Direction.internalValueMap;
            }
            
            public final Descriptors.EnumValueDescriptor getValueDescriptor() {
                return getDescriptor().getValues().get(this.ordinal());
            }
            
            public final Descriptors.EnumDescriptor getDescriptorForType() {
                return getDescriptor();
            }
            
            public static final Descriptors.EnumDescriptor getDescriptor() {
                return Order.getDescriptor().getEnumTypes().get(0);
            }
            
            public static Direction valueOf(final Descriptors.EnumValueDescriptor desc) {
                if (desc.getType() != getDescriptor()) {
                    throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
                }
                return Direction.VALUES[desc.getIndex()];
            }
            
            private Direction(final int value) {
                this.value = value;
            }
            
            static {
                internalValueMap = (Internal.EnumLiteMap)new Internal.EnumLiteMap<Direction>() {
                    public Direction findValueByNumber(final int number) {
                        return Direction.forNumber(number);
                    }
                };
                VALUES = values();
            }
        }
        
        public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements OrderOrBuilder
        {
            private int bitField0_;
            private MysqlxExpr.Expr expr_;
            private SingleFieldBuilderV3<MysqlxExpr.Expr, MysqlxExpr.Expr.Builder, MysqlxExpr.ExprOrBuilder> exprBuilder_;
            private int direction_;
            
            public static final Descriptors.Descriptor getDescriptor() {
                return MysqlxCrud.internal_static_Mysqlx_Crud_Order_descriptor;
            }
            
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return MysqlxCrud.internal_static_Mysqlx_Crud_Order_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)Order.class, (Class)Builder.class);
            }
            
            private Builder() {
                this.expr_ = null;
                this.direction_ = 1;
                this.maybeForceBuilderInitialization();
            }
            
            private Builder(final GeneratedMessageV3.BuilderParent parent) {
                super(parent);
                this.expr_ = null;
                this.direction_ = 1;
                this.maybeForceBuilderInitialization();
            }
            
            private void maybeForceBuilderInitialization() {
                if (Order.alwaysUseFieldBuilders) {
                    this.getExprFieldBuilder();
                }
            }
            
            public Builder clear() {
                super.clear();
                if (this.exprBuilder_ == null) {
                    this.expr_ = null;
                }
                else {
                    this.exprBuilder_.clear();
                }
                this.bitField0_ &= 0xFFFFFFFE;
                this.direction_ = 1;
                this.bitField0_ &= 0xFFFFFFFD;
                return this;
            }
            
            public Descriptors.Descriptor getDescriptorForType() {
                return MysqlxCrud.internal_static_Mysqlx_Crud_Order_descriptor;
            }
            
            public Order getDefaultInstanceForType() {
                return Order.getDefaultInstance();
            }
            
            public Order build() {
                final Order result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw newUninitializedMessageException((Message)result);
                }
                return result;
            }
            
            public Order buildPartial() {
                final Order result = new Order((GeneratedMessageV3.Builder)this);
                final int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 0x1) == 0x1) {
                    to_bitField0_ |= 0x1;
                }
                if (this.exprBuilder_ == null) {
                    result.expr_ = this.expr_;
                }
                else {
                    result.expr_ = (MysqlxExpr.Expr)this.exprBuilder_.build();
                }
                if ((from_bitField0_ & 0x2) == 0x2) {
                    to_bitField0_ |= 0x2;
                }
                result.direction_ = this.direction_;
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
                if (other instanceof Order) {
                    return this.mergeFrom((Order)other);
                }
                super.mergeFrom(other);
                return this;
            }
            
            public Builder mergeFrom(final Order other) {
                if (other == Order.getDefaultInstance()) {
                    return this;
                }
                if (other.hasExpr()) {
                    this.mergeExpr(other.getExpr());
                }
                if (other.hasDirection()) {
                    this.setDirection(other.getDirection());
                }
                this.mergeUnknownFields(other.unknownFields);
                this.onChanged();
                return this;
            }
            
            public final boolean isInitialized() {
                return this.hasExpr() && this.getExpr().isInitialized();
            }
            
            public Builder mergeFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
                Order parsedMessage = null;
                try {
                    parsedMessage = (Order)Order.PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (Order)e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                }
                finally {
                    if (parsedMessage != null) {
                        this.mergeFrom(parsedMessage);
                    }
                }
                return this;
            }
            
            public boolean hasExpr() {
                return (this.bitField0_ & 0x1) == 0x1;
            }
            
            public MysqlxExpr.Expr getExpr() {
                if (this.exprBuilder_ == null) {
                    return (this.expr_ == null) ? MysqlxExpr.Expr.getDefaultInstance() : this.expr_;
                }
                return (MysqlxExpr.Expr)this.exprBuilder_.getMessage();
            }
            
            public Builder setExpr(final MysqlxExpr.Expr value) {
                if (this.exprBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.expr_ = value;
                    this.onChanged();
                }
                else {
                    this.exprBuilder_.setMessage((AbstractMessage)value);
                }
                this.bitField0_ |= 0x1;
                return this;
            }
            
            public Builder setExpr(final MysqlxExpr.Expr.Builder builderForValue) {
                if (this.exprBuilder_ == null) {
                    this.expr_ = builderForValue.build();
                    this.onChanged();
                }
                else {
                    this.exprBuilder_.setMessage((AbstractMessage)builderForValue.build());
                }
                this.bitField0_ |= 0x1;
                return this;
            }
            
            public Builder mergeExpr(final MysqlxExpr.Expr value) {
                if (this.exprBuilder_ == null) {
                    if ((this.bitField0_ & 0x1) == 0x1 && this.expr_ != null && this.expr_ != MysqlxExpr.Expr.getDefaultInstance()) {
                        this.expr_ = MysqlxExpr.Expr.newBuilder(this.expr_).mergeFrom(value).buildPartial();
                    }
                    else {
                        this.expr_ = value;
                    }
                    this.onChanged();
                }
                else {
                    this.exprBuilder_.mergeFrom((AbstractMessage)value);
                }
                this.bitField0_ |= 0x1;
                return this;
            }
            
            public Builder clearExpr() {
                if (this.exprBuilder_ == null) {
                    this.expr_ = null;
                    this.onChanged();
                }
                else {
                    this.exprBuilder_.clear();
                }
                this.bitField0_ &= 0xFFFFFFFE;
                return this;
            }
            
            public MysqlxExpr.Expr.Builder getExprBuilder() {
                this.bitField0_ |= 0x1;
                this.onChanged();
                return (MysqlxExpr.Expr.Builder)this.getExprFieldBuilder().getBuilder();
            }
            
            public MysqlxExpr.ExprOrBuilder getExprOrBuilder() {
                if (this.exprBuilder_ != null) {
                    return (MysqlxExpr.ExprOrBuilder)this.exprBuilder_.getMessageOrBuilder();
                }
                return (this.expr_ == null) ? MysqlxExpr.Expr.getDefaultInstance() : this.expr_;
            }
            
            private SingleFieldBuilderV3<MysqlxExpr.Expr, MysqlxExpr.Expr.Builder, MysqlxExpr.ExprOrBuilder> getExprFieldBuilder() {
                if (this.exprBuilder_ == null) {
                    this.exprBuilder_ = (SingleFieldBuilderV3<MysqlxExpr.Expr, MysqlxExpr.Expr.Builder, MysqlxExpr.ExprOrBuilder>)new SingleFieldBuilderV3((AbstractMessage)this.getExpr(), (AbstractMessage.BuilderParent)this.getParentForChildren(), this.isClean());
                    this.expr_ = null;
                }
                return this.exprBuilder_;
            }
            
            public boolean hasDirection() {
                return (this.bitField0_ & 0x2) == 0x2;
            }
            
            public Direction getDirection() {
                final Direction result = Direction.valueOf(this.direction_);
                return (result == null) ? Direction.ASC : result;
            }
            
            public Builder setDirection(final Direction value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 0x2;
                this.direction_ = value.getNumber();
                this.onChanged();
                return this;
            }
            
            public Builder clearDirection() {
                this.bitField0_ &= 0xFFFFFFFD;
                this.direction_ = 1;
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
    
    public static final class UpdateOperation extends GeneratedMessageV3 implements UpdateOperationOrBuilder
    {
        private static final long serialVersionUID = 0L;
        private int bitField0_;
        public static final int SOURCE_FIELD_NUMBER = 1;
        private MysqlxExpr.ColumnIdentifier source_;
        public static final int OPERATION_FIELD_NUMBER = 2;
        private int operation_;
        public static final int VALUE_FIELD_NUMBER = 3;
        private MysqlxExpr.Expr value_;
        private byte memoizedIsInitialized;
        private static final UpdateOperation DEFAULT_INSTANCE;
        @Deprecated
        public static final Parser<UpdateOperation> PARSER;
        
        private UpdateOperation(final GeneratedMessageV3.Builder<?> builder) {
            super((GeneratedMessageV3.Builder)builder);
            this.memoizedIsInitialized = -1;
        }
        
        private UpdateOperation() {
            this.memoizedIsInitialized = -1;
            this.operation_ = 1;
        }
        
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }
        
        private UpdateOperation(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            MysqlxExpr.ColumnIdentifier.Builder subBuilder = null;
                            if ((this.bitField0_ & 0x1) == 0x1) {
                                subBuilder = this.source_.toBuilder();
                            }
                            this.source_ = (MysqlxExpr.ColumnIdentifier)input.readMessage((Parser)MysqlxExpr.ColumnIdentifier.PARSER, extensionRegistry);
                            if (subBuilder != null) {
                                subBuilder.mergeFrom(this.source_);
                                this.source_ = subBuilder.buildPartial();
                            }
                            this.bitField0_ |= 0x1;
                            continue;
                        }
                        case 16: {
                            final int rawValue = input.readEnum();
                            final UpdateType value = UpdateType.valueOf(rawValue);
                            if (value == null) {
                                unknownFields.mergeVarintField(2, rawValue);
                                continue;
                            }
                            this.bitField0_ |= 0x2;
                            this.operation_ = rawValue;
                            continue;
                        }
                        case 26: {
                            MysqlxExpr.Expr.Builder subBuilder2 = null;
                            if ((this.bitField0_ & 0x4) == 0x4) {
                                subBuilder2 = this.value_.toBuilder();
                            }
                            this.value_ = (MysqlxExpr.Expr)input.readMessage((Parser)MysqlxExpr.Expr.PARSER, extensionRegistry);
                            if (subBuilder2 != null) {
                                subBuilder2.mergeFrom(this.value_);
                                this.value_ = subBuilder2.buildPartial();
                            }
                            this.bitField0_ |= 0x4;
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
            return MysqlxCrud.internal_static_Mysqlx_Crud_UpdateOperation_descriptor;
        }
        
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return MysqlxCrud.internal_static_Mysqlx_Crud_UpdateOperation_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)UpdateOperation.class, (Class)Builder.class);
        }
        
        public boolean hasSource() {
            return (this.bitField0_ & 0x1) == 0x1;
        }
        
        public MysqlxExpr.ColumnIdentifier getSource() {
            return (this.source_ == null) ? MysqlxExpr.ColumnIdentifier.getDefaultInstance() : this.source_;
        }
        
        public MysqlxExpr.ColumnIdentifierOrBuilder getSourceOrBuilder() {
            return (this.source_ == null) ? MysqlxExpr.ColumnIdentifier.getDefaultInstance() : this.source_;
        }
        
        public boolean hasOperation() {
            return (this.bitField0_ & 0x2) == 0x2;
        }
        
        public UpdateType getOperation() {
            final UpdateType result = UpdateType.valueOf(this.operation_);
            return (result == null) ? UpdateType.SET : result;
        }
        
        public boolean hasValue() {
            return (this.bitField0_ & 0x4) == 0x4;
        }
        
        public MysqlxExpr.Expr getValue() {
            return (this.value_ == null) ? MysqlxExpr.Expr.getDefaultInstance() : this.value_;
        }
        
        public MysqlxExpr.ExprOrBuilder getValueOrBuilder() {
            return (this.value_ == null) ? MysqlxExpr.Expr.getDefaultInstance() : this.value_;
        }
        
        public final boolean isInitialized() {
            final byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (!this.hasSource()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            if (!this.hasOperation()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            if (!this.getSource().isInitialized()) {
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
                output.writeMessage(1, (MessageLite)this.getSource());
            }
            if ((this.bitField0_ & 0x2) == 0x2) {
                output.writeEnum(2, this.operation_);
            }
            if ((this.bitField0_ & 0x4) == 0x4) {
                output.writeMessage(3, (MessageLite)this.getValue());
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
                size += CodedOutputStream.computeMessageSize(1, (MessageLite)this.getSource());
            }
            if ((this.bitField0_ & 0x2) == 0x2) {
                size += CodedOutputStream.computeEnumSize(2, this.operation_);
            }
            if ((this.bitField0_ & 0x4) == 0x4) {
                size += CodedOutputStream.computeMessageSize(3, (MessageLite)this.getValue());
            }
            size += this.unknownFields.getSerializedSize();
            return this.memoizedSize = size;
        }
        
        public boolean equals(final Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof UpdateOperation)) {
                return super.equals(obj);
            }
            final UpdateOperation other = (UpdateOperation)obj;
            boolean result = true;
            result = (result && this.hasSource() == other.hasSource());
            if (this.hasSource()) {
                result = (result && this.getSource().equals(other.getSource()));
            }
            result = (result && this.hasOperation() == other.hasOperation());
            if (this.hasOperation()) {
                result = (result && this.operation_ == other.operation_);
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
            if (this.hasSource()) {
                hash = 37 * hash + 1;
                hash = 53 * hash + this.getSource().hashCode();
            }
            if (this.hasOperation()) {
                hash = 37 * hash + 2;
                hash = 53 * hash + this.operation_;
            }
            if (this.hasValue()) {
                hash = 37 * hash + 3;
                hash = 53 * hash + this.getValue().hashCode();
            }
            hash = 29 * hash + this.unknownFields.hashCode();
            return this.memoizedHashCode = hash;
        }
        
        public static UpdateOperation parseFrom(final ByteBuffer data) throws InvalidProtocolBufferException {
            return (UpdateOperation)UpdateOperation.PARSER.parseFrom(data);
        }
        
        public static UpdateOperation parseFrom(final ByteBuffer data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (UpdateOperation)UpdateOperation.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static UpdateOperation parseFrom(final ByteString data) throws InvalidProtocolBufferException {
            return (UpdateOperation)UpdateOperation.PARSER.parseFrom(data);
        }
        
        public static UpdateOperation parseFrom(final ByteString data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (UpdateOperation)UpdateOperation.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static UpdateOperation parseFrom(final byte[] data) throws InvalidProtocolBufferException {
            return (UpdateOperation)UpdateOperation.PARSER.parseFrom(data);
        }
        
        public static UpdateOperation parseFrom(final byte[] data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (UpdateOperation)UpdateOperation.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static UpdateOperation parseFrom(final InputStream input) throws IOException {
            return (UpdateOperation)GeneratedMessageV3.parseWithIOException((Parser)UpdateOperation.PARSER, input);
        }
        
        public static UpdateOperation parseFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (UpdateOperation)GeneratedMessageV3.parseWithIOException((Parser)UpdateOperation.PARSER, input, extensionRegistry);
        }
        
        public static UpdateOperation parseDelimitedFrom(final InputStream input) throws IOException {
            return (UpdateOperation)GeneratedMessageV3.parseDelimitedWithIOException((Parser)UpdateOperation.PARSER, input);
        }
        
        public static UpdateOperation parseDelimitedFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (UpdateOperation)GeneratedMessageV3.parseDelimitedWithIOException((Parser)UpdateOperation.PARSER, input, extensionRegistry);
        }
        
        public static UpdateOperation parseFrom(final CodedInputStream input) throws IOException {
            return (UpdateOperation)GeneratedMessageV3.parseWithIOException((Parser)UpdateOperation.PARSER, input);
        }
        
        public static UpdateOperation parseFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (UpdateOperation)GeneratedMessageV3.parseWithIOException((Parser)UpdateOperation.PARSER, input, extensionRegistry);
        }
        
        public Builder newBuilderForType() {
            return newBuilder();
        }
        
        public static Builder newBuilder() {
            return UpdateOperation.DEFAULT_INSTANCE.toBuilder();
        }
        
        public static Builder newBuilder(final UpdateOperation prototype) {
            return UpdateOperation.DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }
        
        public Builder toBuilder() {
            return (this == UpdateOperation.DEFAULT_INSTANCE) ? new Builder() : new Builder().mergeFrom(this);
        }
        
        protected Builder newBuilderForType(final GeneratedMessageV3.BuilderParent parent) {
            final Builder builder = new Builder(parent);
            return builder;
        }
        
        public static UpdateOperation getDefaultInstance() {
            return UpdateOperation.DEFAULT_INSTANCE;
        }
        
        public static Parser<UpdateOperation> parser() {
            return UpdateOperation.PARSER;
        }
        
        public Parser<UpdateOperation> getParserForType() {
            return UpdateOperation.PARSER;
        }
        
        public UpdateOperation getDefaultInstanceForType() {
            return UpdateOperation.DEFAULT_INSTANCE;
        }
        
        static {
            DEFAULT_INSTANCE = new UpdateOperation();
            PARSER = (Parser)new AbstractParser<UpdateOperation>() {
                public UpdateOperation parsePartialFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new UpdateOperation(input, extensionRegistry);
                }
            };
        }
        
        public enum UpdateType implements ProtocolMessageEnum
        {
            SET(1), 
            ITEM_REMOVE(2), 
            ITEM_SET(3), 
            ITEM_REPLACE(4), 
            ITEM_MERGE(5), 
            ARRAY_INSERT(6), 
            ARRAY_APPEND(7), 
            MERGE_PATCH(8);
            
            public static final int SET_VALUE = 1;
            public static final int ITEM_REMOVE_VALUE = 2;
            public static final int ITEM_SET_VALUE = 3;
            public static final int ITEM_REPLACE_VALUE = 4;
            public static final int ITEM_MERGE_VALUE = 5;
            public static final int ARRAY_INSERT_VALUE = 6;
            public static final int ARRAY_APPEND_VALUE = 7;
            public static final int MERGE_PATCH_VALUE = 8;
            private static final Internal.EnumLiteMap<UpdateType> internalValueMap;
            private static final UpdateType[] VALUES;
            private final int value;
            
            public final int getNumber() {
                return this.value;
            }
            
            @Deprecated
            public static UpdateType valueOf(final int value) {
                return forNumber(value);
            }
            
            public static UpdateType forNumber(final int value) {
                switch (value) {
                    case 1: {
                        return UpdateType.SET;
                    }
                    case 2: {
                        return UpdateType.ITEM_REMOVE;
                    }
                    case 3: {
                        return UpdateType.ITEM_SET;
                    }
                    case 4: {
                        return UpdateType.ITEM_REPLACE;
                    }
                    case 5: {
                        return UpdateType.ITEM_MERGE;
                    }
                    case 6: {
                        return UpdateType.ARRAY_INSERT;
                    }
                    case 7: {
                        return UpdateType.ARRAY_APPEND;
                    }
                    case 8: {
                        return UpdateType.MERGE_PATCH;
                    }
                    default: {
                        return null;
                    }
                }
            }
            
            public static Internal.EnumLiteMap<UpdateType> internalGetValueMap() {
                return UpdateType.internalValueMap;
            }
            
            public final Descriptors.EnumValueDescriptor getValueDescriptor() {
                return getDescriptor().getValues().get(this.ordinal());
            }
            
            public final Descriptors.EnumDescriptor getDescriptorForType() {
                return getDescriptor();
            }
            
            public static final Descriptors.EnumDescriptor getDescriptor() {
                return UpdateOperation.getDescriptor().getEnumTypes().get(0);
            }
            
            public static UpdateType valueOf(final Descriptors.EnumValueDescriptor desc) {
                if (desc.getType() != getDescriptor()) {
                    throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
                }
                return UpdateType.VALUES[desc.getIndex()];
            }
            
            private UpdateType(final int value) {
                this.value = value;
            }
            
            static {
                internalValueMap = (Internal.EnumLiteMap)new Internal.EnumLiteMap<UpdateType>() {
                    public UpdateType findValueByNumber(final int number) {
                        return UpdateType.forNumber(number);
                    }
                };
                VALUES = values();
            }
        }
        
        public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements UpdateOperationOrBuilder
        {
            private int bitField0_;
            private MysqlxExpr.ColumnIdentifier source_;
            private SingleFieldBuilderV3<MysqlxExpr.ColumnIdentifier, MysqlxExpr.ColumnIdentifier.Builder, MysqlxExpr.ColumnIdentifierOrBuilder> sourceBuilder_;
            private int operation_;
            private MysqlxExpr.Expr value_;
            private SingleFieldBuilderV3<MysqlxExpr.Expr, MysqlxExpr.Expr.Builder, MysqlxExpr.ExprOrBuilder> valueBuilder_;
            
            public static final Descriptors.Descriptor getDescriptor() {
                return MysqlxCrud.internal_static_Mysqlx_Crud_UpdateOperation_descriptor;
            }
            
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return MysqlxCrud.internal_static_Mysqlx_Crud_UpdateOperation_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)UpdateOperation.class, (Class)Builder.class);
            }
            
            private Builder() {
                this.source_ = null;
                this.operation_ = 1;
                this.value_ = null;
                this.maybeForceBuilderInitialization();
            }
            
            private Builder(final GeneratedMessageV3.BuilderParent parent) {
                super(parent);
                this.source_ = null;
                this.operation_ = 1;
                this.value_ = null;
                this.maybeForceBuilderInitialization();
            }
            
            private void maybeForceBuilderInitialization() {
                if (UpdateOperation.alwaysUseFieldBuilders) {
                    this.getSourceFieldBuilder();
                    this.getValueFieldBuilder();
                }
            }
            
            public Builder clear() {
                super.clear();
                if (this.sourceBuilder_ == null) {
                    this.source_ = null;
                }
                else {
                    this.sourceBuilder_.clear();
                }
                this.bitField0_ &= 0xFFFFFFFE;
                this.operation_ = 1;
                this.bitField0_ &= 0xFFFFFFFD;
                if (this.valueBuilder_ == null) {
                    this.value_ = null;
                }
                else {
                    this.valueBuilder_.clear();
                }
                this.bitField0_ &= 0xFFFFFFFB;
                return this;
            }
            
            public Descriptors.Descriptor getDescriptorForType() {
                return MysqlxCrud.internal_static_Mysqlx_Crud_UpdateOperation_descriptor;
            }
            
            public UpdateOperation getDefaultInstanceForType() {
                return UpdateOperation.getDefaultInstance();
            }
            
            public UpdateOperation build() {
                final UpdateOperation result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw newUninitializedMessageException((Message)result);
                }
                return result;
            }
            
            public UpdateOperation buildPartial() {
                final UpdateOperation result = new UpdateOperation((GeneratedMessageV3.Builder)this);
                final int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 0x1) == 0x1) {
                    to_bitField0_ |= 0x1;
                }
                if (this.sourceBuilder_ == null) {
                    result.source_ = this.source_;
                }
                else {
                    result.source_ = (MysqlxExpr.ColumnIdentifier)this.sourceBuilder_.build();
                }
                if ((from_bitField0_ & 0x2) == 0x2) {
                    to_bitField0_ |= 0x2;
                }
                result.operation_ = this.operation_;
                if ((from_bitField0_ & 0x4) == 0x4) {
                    to_bitField0_ |= 0x4;
                }
                if (this.valueBuilder_ == null) {
                    result.value_ = this.value_;
                }
                else {
                    result.value_ = (MysqlxExpr.Expr)this.valueBuilder_.build();
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
                if (other instanceof UpdateOperation) {
                    return this.mergeFrom((UpdateOperation)other);
                }
                super.mergeFrom(other);
                return this;
            }
            
            public Builder mergeFrom(final UpdateOperation other) {
                if (other == UpdateOperation.getDefaultInstance()) {
                    return this;
                }
                if (other.hasSource()) {
                    this.mergeSource(other.getSource());
                }
                if (other.hasOperation()) {
                    this.setOperation(other.getOperation());
                }
                if (other.hasValue()) {
                    this.mergeValue(other.getValue());
                }
                this.mergeUnknownFields(other.unknownFields);
                this.onChanged();
                return this;
            }
            
            public final boolean isInitialized() {
                return this.hasSource() && this.hasOperation() && this.getSource().isInitialized() && (!this.hasValue() || this.getValue().isInitialized());
            }
            
            public Builder mergeFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
                UpdateOperation parsedMessage = null;
                try {
                    parsedMessage = (UpdateOperation)UpdateOperation.PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (UpdateOperation)e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                }
                finally {
                    if (parsedMessage != null) {
                        this.mergeFrom(parsedMessage);
                    }
                }
                return this;
            }
            
            public boolean hasSource() {
                return (this.bitField0_ & 0x1) == 0x1;
            }
            
            public MysqlxExpr.ColumnIdentifier getSource() {
                if (this.sourceBuilder_ == null) {
                    return (this.source_ == null) ? MysqlxExpr.ColumnIdentifier.getDefaultInstance() : this.source_;
                }
                return (MysqlxExpr.ColumnIdentifier)this.sourceBuilder_.getMessage();
            }
            
            public Builder setSource(final MysqlxExpr.ColumnIdentifier value) {
                if (this.sourceBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.source_ = value;
                    this.onChanged();
                }
                else {
                    this.sourceBuilder_.setMessage((AbstractMessage)value);
                }
                this.bitField0_ |= 0x1;
                return this;
            }
            
            public Builder setSource(final MysqlxExpr.ColumnIdentifier.Builder builderForValue) {
                if (this.sourceBuilder_ == null) {
                    this.source_ = builderForValue.build();
                    this.onChanged();
                }
                else {
                    this.sourceBuilder_.setMessage((AbstractMessage)builderForValue.build());
                }
                this.bitField0_ |= 0x1;
                return this;
            }
            
            public Builder mergeSource(final MysqlxExpr.ColumnIdentifier value) {
                if (this.sourceBuilder_ == null) {
                    if ((this.bitField0_ & 0x1) == 0x1 && this.source_ != null && this.source_ != MysqlxExpr.ColumnIdentifier.getDefaultInstance()) {
                        this.source_ = MysqlxExpr.ColumnIdentifier.newBuilder(this.source_).mergeFrom(value).buildPartial();
                    }
                    else {
                        this.source_ = value;
                    }
                    this.onChanged();
                }
                else {
                    this.sourceBuilder_.mergeFrom((AbstractMessage)value);
                }
                this.bitField0_ |= 0x1;
                return this;
            }
            
            public Builder clearSource() {
                if (this.sourceBuilder_ == null) {
                    this.source_ = null;
                    this.onChanged();
                }
                else {
                    this.sourceBuilder_.clear();
                }
                this.bitField0_ &= 0xFFFFFFFE;
                return this;
            }
            
            public MysqlxExpr.ColumnIdentifier.Builder getSourceBuilder() {
                this.bitField0_ |= 0x1;
                this.onChanged();
                return (MysqlxExpr.ColumnIdentifier.Builder)this.getSourceFieldBuilder().getBuilder();
            }
            
            public MysqlxExpr.ColumnIdentifierOrBuilder getSourceOrBuilder() {
                if (this.sourceBuilder_ != null) {
                    return (MysqlxExpr.ColumnIdentifierOrBuilder)this.sourceBuilder_.getMessageOrBuilder();
                }
                return (this.source_ == null) ? MysqlxExpr.ColumnIdentifier.getDefaultInstance() : this.source_;
            }
            
            private SingleFieldBuilderV3<MysqlxExpr.ColumnIdentifier, MysqlxExpr.ColumnIdentifier.Builder, MysqlxExpr.ColumnIdentifierOrBuilder> getSourceFieldBuilder() {
                if (this.sourceBuilder_ == null) {
                    this.sourceBuilder_ = (SingleFieldBuilderV3<MysqlxExpr.ColumnIdentifier, MysqlxExpr.ColumnIdentifier.Builder, MysqlxExpr.ColumnIdentifierOrBuilder>)new SingleFieldBuilderV3((AbstractMessage)this.getSource(), (AbstractMessage.BuilderParent)this.getParentForChildren(), this.isClean());
                    this.source_ = null;
                }
                return this.sourceBuilder_;
            }
            
            public boolean hasOperation() {
                return (this.bitField0_ & 0x2) == 0x2;
            }
            
            public UpdateType getOperation() {
                final UpdateType result = UpdateType.valueOf(this.operation_);
                return (result == null) ? UpdateType.SET : result;
            }
            
            public Builder setOperation(final UpdateType value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 0x2;
                this.operation_ = value.getNumber();
                this.onChanged();
                return this;
            }
            
            public Builder clearOperation() {
                this.bitField0_ &= 0xFFFFFFFD;
                this.operation_ = 1;
                this.onChanged();
                return this;
            }
            
            public boolean hasValue() {
                return (this.bitField0_ & 0x4) == 0x4;
            }
            
            public MysqlxExpr.Expr getValue() {
                if (this.valueBuilder_ == null) {
                    return (this.value_ == null) ? MysqlxExpr.Expr.getDefaultInstance() : this.value_;
                }
                return (MysqlxExpr.Expr)this.valueBuilder_.getMessage();
            }
            
            public Builder setValue(final MysqlxExpr.Expr value) {
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
                this.bitField0_ |= 0x4;
                return this;
            }
            
            public Builder setValue(final MysqlxExpr.Expr.Builder builderForValue) {
                if (this.valueBuilder_ == null) {
                    this.value_ = builderForValue.build();
                    this.onChanged();
                }
                else {
                    this.valueBuilder_.setMessage((AbstractMessage)builderForValue.build());
                }
                this.bitField0_ |= 0x4;
                return this;
            }
            
            public Builder mergeValue(final MysqlxExpr.Expr value) {
                if (this.valueBuilder_ == null) {
                    if ((this.bitField0_ & 0x4) == 0x4 && this.value_ != null && this.value_ != MysqlxExpr.Expr.getDefaultInstance()) {
                        this.value_ = MysqlxExpr.Expr.newBuilder(this.value_).mergeFrom(value).buildPartial();
                    }
                    else {
                        this.value_ = value;
                    }
                    this.onChanged();
                }
                else {
                    this.valueBuilder_.mergeFrom((AbstractMessage)value);
                }
                this.bitField0_ |= 0x4;
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
                this.bitField0_ &= 0xFFFFFFFB;
                return this;
            }
            
            public MysqlxExpr.Expr.Builder getValueBuilder() {
                this.bitField0_ |= 0x4;
                this.onChanged();
                return (MysqlxExpr.Expr.Builder)this.getValueFieldBuilder().getBuilder();
            }
            
            public MysqlxExpr.ExprOrBuilder getValueOrBuilder() {
                if (this.valueBuilder_ != null) {
                    return (MysqlxExpr.ExprOrBuilder)this.valueBuilder_.getMessageOrBuilder();
                }
                return (this.value_ == null) ? MysqlxExpr.Expr.getDefaultInstance() : this.value_;
            }
            
            private SingleFieldBuilderV3<MysqlxExpr.Expr, MysqlxExpr.Expr.Builder, MysqlxExpr.ExprOrBuilder> getValueFieldBuilder() {
                if (this.valueBuilder_ == null) {
                    this.valueBuilder_ = (SingleFieldBuilderV3<MysqlxExpr.Expr, MysqlxExpr.Expr.Builder, MysqlxExpr.ExprOrBuilder>)new SingleFieldBuilderV3((AbstractMessage)this.getValue(), (AbstractMessage.BuilderParent)this.getParentForChildren(), this.isClean());
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
    
    public static final class Find extends GeneratedMessageV3 implements FindOrBuilder
    {
        private static final long serialVersionUID = 0L;
        private int bitField0_;
        public static final int COLLECTION_FIELD_NUMBER = 2;
        private Collection collection_;
        public static final int DATA_MODEL_FIELD_NUMBER = 3;
        private int dataModel_;
        public static final int PROJECTION_FIELD_NUMBER = 4;
        private List<Projection> projection_;
        public static final int CRITERIA_FIELD_NUMBER = 5;
        private MysqlxExpr.Expr criteria_;
        public static final int ARGS_FIELD_NUMBER = 11;
        private List<MysqlxDatatypes.Scalar> args_;
        public static final int LIMIT_FIELD_NUMBER = 6;
        private Limit limit_;
        public static final int ORDER_FIELD_NUMBER = 7;
        private List<Order> order_;
        public static final int GROUPING_FIELD_NUMBER = 8;
        private List<MysqlxExpr.Expr> grouping_;
        public static final int GROUPING_CRITERIA_FIELD_NUMBER = 9;
        private MysqlxExpr.Expr groupingCriteria_;
        public static final int LOCKING_FIELD_NUMBER = 12;
        private int locking_;
        public static final int LOCKING_OPTIONS_FIELD_NUMBER = 13;
        private int lockingOptions_;
        private byte memoizedIsInitialized;
        private static final Find DEFAULT_INSTANCE;
        @Deprecated
        public static final Parser<Find> PARSER;
        
        private Find(final GeneratedMessageV3.Builder<?> builder) {
            super((GeneratedMessageV3.Builder)builder);
            this.memoizedIsInitialized = -1;
        }
        
        private Find() {
            this.memoizedIsInitialized = -1;
            this.dataModel_ = 1;
            this.projection_ = Collections.emptyList();
            this.args_ = Collections.emptyList();
            this.order_ = Collections.emptyList();
            this.grouping_ = Collections.emptyList();
            this.locking_ = 1;
            this.lockingOptions_ = 1;
        }
        
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }
        
        private Find(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                        case 18: {
                            Collection.Builder subBuilder = null;
                            if ((this.bitField0_ & 0x1) == 0x1) {
                                subBuilder = this.collection_.toBuilder();
                            }
                            this.collection_ = (Collection)input.readMessage((Parser)Collection.PARSER, extensionRegistry);
                            if (subBuilder != null) {
                                subBuilder.mergeFrom(this.collection_);
                                this.collection_ = subBuilder.buildPartial();
                            }
                            this.bitField0_ |= 0x1;
                            continue;
                        }
                        case 24: {
                            final int rawValue = input.readEnum();
                            final DataModel value = DataModel.valueOf(rawValue);
                            if (value == null) {
                                unknownFields.mergeVarintField(3, rawValue);
                                continue;
                            }
                            this.bitField0_ |= 0x2;
                            this.dataModel_ = rawValue;
                            continue;
                        }
                        case 34: {
                            if ((mutable_bitField0_ & 0x4) != 0x4) {
                                this.projection_ = new ArrayList<Projection>();
                                mutable_bitField0_ |= 0x4;
                            }
                            this.projection_.add((Projection)input.readMessage((Parser)Projection.PARSER, extensionRegistry));
                            continue;
                        }
                        case 42: {
                            MysqlxExpr.Expr.Builder subBuilder2 = null;
                            if ((this.bitField0_ & 0x4) == 0x4) {
                                subBuilder2 = this.criteria_.toBuilder();
                            }
                            this.criteria_ = (MysqlxExpr.Expr)input.readMessage((Parser)MysqlxExpr.Expr.PARSER, extensionRegistry);
                            if (subBuilder2 != null) {
                                subBuilder2.mergeFrom(this.criteria_);
                                this.criteria_ = subBuilder2.buildPartial();
                            }
                            this.bitField0_ |= 0x4;
                            continue;
                        }
                        case 50: {
                            Limit.Builder subBuilder3 = null;
                            if ((this.bitField0_ & 0x8) == 0x8) {
                                subBuilder3 = this.limit_.toBuilder();
                            }
                            this.limit_ = (Limit)input.readMessage((Parser)Limit.PARSER, extensionRegistry);
                            if (subBuilder3 != null) {
                                subBuilder3.mergeFrom(this.limit_);
                                this.limit_ = subBuilder3.buildPartial();
                            }
                            this.bitField0_ |= 0x8;
                            continue;
                        }
                        case 58: {
                            if ((mutable_bitField0_ & 0x40) != 0x40) {
                                this.order_ = new ArrayList<Order>();
                                mutable_bitField0_ |= 0x40;
                            }
                            this.order_.add((Order)input.readMessage((Parser)Order.PARSER, extensionRegistry));
                            continue;
                        }
                        case 66: {
                            if ((mutable_bitField0_ & 0x80) != 0x80) {
                                this.grouping_ = new ArrayList<MysqlxExpr.Expr>();
                                mutable_bitField0_ |= 0x80;
                            }
                            this.grouping_.add((MysqlxExpr.Expr)input.readMessage((Parser)MysqlxExpr.Expr.PARSER, extensionRegistry));
                            continue;
                        }
                        case 74: {
                            MysqlxExpr.Expr.Builder subBuilder2 = null;
                            if ((this.bitField0_ & 0x10) == 0x10) {
                                subBuilder2 = this.groupingCriteria_.toBuilder();
                            }
                            this.groupingCriteria_ = (MysqlxExpr.Expr)input.readMessage((Parser)MysqlxExpr.Expr.PARSER, extensionRegistry);
                            if (subBuilder2 != null) {
                                subBuilder2.mergeFrom(this.groupingCriteria_);
                                this.groupingCriteria_ = subBuilder2.buildPartial();
                            }
                            this.bitField0_ |= 0x10;
                            continue;
                        }
                        case 90: {
                            if ((mutable_bitField0_ & 0x10) != 0x10) {
                                this.args_ = new ArrayList<MysqlxDatatypes.Scalar>();
                                mutable_bitField0_ |= 0x10;
                            }
                            this.args_.add((MysqlxDatatypes.Scalar)input.readMessage((Parser)MysqlxDatatypes.Scalar.PARSER, extensionRegistry));
                            continue;
                        }
                        case 96: {
                            final int rawValue = input.readEnum();
                            final RowLock value2 = RowLock.valueOf(rawValue);
                            if (value2 == null) {
                                unknownFields.mergeVarintField(12, rawValue);
                                continue;
                            }
                            this.bitField0_ |= 0x20;
                            this.locking_ = rawValue;
                            continue;
                        }
                        case 104: {
                            final int rawValue = input.readEnum();
                            final RowLockOptions value3 = RowLockOptions.valueOf(rawValue);
                            if (value3 == null) {
                                unknownFields.mergeVarintField(13, rawValue);
                                continue;
                            }
                            this.bitField0_ |= 0x40;
                            this.lockingOptions_ = rawValue;
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
                    this.projection_ = Collections.unmodifiableList((List<? extends Projection>)this.projection_);
                }
                if ((mutable_bitField0_ & 0x40) == 0x40) {
                    this.order_ = Collections.unmodifiableList((List<? extends Order>)this.order_);
                }
                if ((mutable_bitField0_ & 0x80) == 0x80) {
                    this.grouping_ = Collections.unmodifiableList((List<? extends MysqlxExpr.Expr>)this.grouping_);
                }
                if ((mutable_bitField0_ & 0x10) == 0x10) {
                    this.args_ = Collections.unmodifiableList((List<? extends MysqlxDatatypes.Scalar>)this.args_);
                }
                this.unknownFields = unknownFields.build();
                this.makeExtensionsImmutable();
            }
        }
        
        public static final Descriptors.Descriptor getDescriptor() {
            return MysqlxCrud.internal_static_Mysqlx_Crud_Find_descriptor;
        }
        
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return MysqlxCrud.internal_static_Mysqlx_Crud_Find_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)Find.class, (Class)Builder.class);
        }
        
        public boolean hasCollection() {
            return (this.bitField0_ & 0x1) == 0x1;
        }
        
        public Collection getCollection() {
            return (this.collection_ == null) ? Collection.getDefaultInstance() : this.collection_;
        }
        
        public CollectionOrBuilder getCollectionOrBuilder() {
            return (this.collection_ == null) ? Collection.getDefaultInstance() : this.collection_;
        }
        
        public boolean hasDataModel() {
            return (this.bitField0_ & 0x2) == 0x2;
        }
        
        public DataModel getDataModel() {
            final DataModel result = DataModel.valueOf(this.dataModel_);
            return (result == null) ? DataModel.DOCUMENT : result;
        }
        
        public List<Projection> getProjectionList() {
            return this.projection_;
        }
        
        public List<? extends ProjectionOrBuilder> getProjectionOrBuilderList() {
            return this.projection_;
        }
        
        public int getProjectionCount() {
            return this.projection_.size();
        }
        
        public Projection getProjection(final int index) {
            return this.projection_.get(index);
        }
        
        public ProjectionOrBuilder getProjectionOrBuilder(final int index) {
            return this.projection_.get(index);
        }
        
        public boolean hasCriteria() {
            return (this.bitField0_ & 0x4) == 0x4;
        }
        
        public MysqlxExpr.Expr getCriteria() {
            return (this.criteria_ == null) ? MysqlxExpr.Expr.getDefaultInstance() : this.criteria_;
        }
        
        public MysqlxExpr.ExprOrBuilder getCriteriaOrBuilder() {
            return (this.criteria_ == null) ? MysqlxExpr.Expr.getDefaultInstance() : this.criteria_;
        }
        
        public List<MysqlxDatatypes.Scalar> getArgsList() {
            return this.args_;
        }
        
        public List<? extends MysqlxDatatypes.ScalarOrBuilder> getArgsOrBuilderList() {
            return this.args_;
        }
        
        public int getArgsCount() {
            return this.args_.size();
        }
        
        public MysqlxDatatypes.Scalar getArgs(final int index) {
            return this.args_.get(index);
        }
        
        public MysqlxDatatypes.ScalarOrBuilder getArgsOrBuilder(final int index) {
            return this.args_.get(index);
        }
        
        public boolean hasLimit() {
            return (this.bitField0_ & 0x8) == 0x8;
        }
        
        public Limit getLimit() {
            return (this.limit_ == null) ? Limit.getDefaultInstance() : this.limit_;
        }
        
        public LimitOrBuilder getLimitOrBuilder() {
            return (this.limit_ == null) ? Limit.getDefaultInstance() : this.limit_;
        }
        
        public List<Order> getOrderList() {
            return this.order_;
        }
        
        public List<? extends OrderOrBuilder> getOrderOrBuilderList() {
            return this.order_;
        }
        
        public int getOrderCount() {
            return this.order_.size();
        }
        
        public Order getOrder(final int index) {
            return this.order_.get(index);
        }
        
        public OrderOrBuilder getOrderOrBuilder(final int index) {
            return this.order_.get(index);
        }
        
        public List<MysqlxExpr.Expr> getGroupingList() {
            return this.grouping_;
        }
        
        public List<? extends MysqlxExpr.ExprOrBuilder> getGroupingOrBuilderList() {
            return this.grouping_;
        }
        
        public int getGroupingCount() {
            return this.grouping_.size();
        }
        
        public MysqlxExpr.Expr getGrouping(final int index) {
            return this.grouping_.get(index);
        }
        
        public MysqlxExpr.ExprOrBuilder getGroupingOrBuilder(final int index) {
            return this.grouping_.get(index);
        }
        
        public boolean hasGroupingCriteria() {
            return (this.bitField0_ & 0x10) == 0x10;
        }
        
        public MysqlxExpr.Expr getGroupingCriteria() {
            return (this.groupingCriteria_ == null) ? MysqlxExpr.Expr.getDefaultInstance() : this.groupingCriteria_;
        }
        
        public MysqlxExpr.ExprOrBuilder getGroupingCriteriaOrBuilder() {
            return (this.groupingCriteria_ == null) ? MysqlxExpr.Expr.getDefaultInstance() : this.groupingCriteria_;
        }
        
        public boolean hasLocking() {
            return (this.bitField0_ & 0x20) == 0x20;
        }
        
        public RowLock getLocking() {
            final RowLock result = RowLock.valueOf(this.locking_);
            return (result == null) ? RowLock.SHARED_LOCK : result;
        }
        
        public boolean hasLockingOptions() {
            return (this.bitField0_ & 0x40) == 0x40;
        }
        
        public RowLockOptions getLockingOptions() {
            final RowLockOptions result = RowLockOptions.valueOf(this.lockingOptions_);
            return (result == null) ? RowLockOptions.NOWAIT : result;
        }
        
        public final boolean isInitialized() {
            final byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (!this.hasCollection()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            if (!this.getCollection().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            for (int i = 0; i < this.getProjectionCount(); ++i) {
                if (!this.getProjection(i).isInitialized()) {
                    this.memoizedIsInitialized = 0;
                    return false;
                }
            }
            if (this.hasCriteria() && !this.getCriteria().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            for (int i = 0; i < this.getArgsCount(); ++i) {
                if (!this.getArgs(i).isInitialized()) {
                    this.memoizedIsInitialized = 0;
                    return false;
                }
            }
            if (this.hasLimit() && !this.getLimit().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            for (int i = 0; i < this.getOrderCount(); ++i) {
                if (!this.getOrder(i).isInitialized()) {
                    this.memoizedIsInitialized = 0;
                    return false;
                }
            }
            for (int i = 0; i < this.getGroupingCount(); ++i) {
                if (!this.getGrouping(i).isInitialized()) {
                    this.memoizedIsInitialized = 0;
                    return false;
                }
            }
            if (this.hasGroupingCriteria() && !this.getGroupingCriteria().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            this.memoizedIsInitialized = 1;
            return true;
        }
        
        public void writeTo(final CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 0x1) == 0x1) {
                output.writeMessage(2, (MessageLite)this.getCollection());
            }
            if ((this.bitField0_ & 0x2) == 0x2) {
                output.writeEnum(3, this.dataModel_);
            }
            for (int i = 0; i < this.projection_.size(); ++i) {
                output.writeMessage(4, (MessageLite)this.projection_.get(i));
            }
            if ((this.bitField0_ & 0x4) == 0x4) {
                output.writeMessage(5, (MessageLite)this.getCriteria());
            }
            if ((this.bitField0_ & 0x8) == 0x8) {
                output.writeMessage(6, (MessageLite)this.getLimit());
            }
            for (int i = 0; i < this.order_.size(); ++i) {
                output.writeMessage(7, (MessageLite)this.order_.get(i));
            }
            for (int i = 0; i < this.grouping_.size(); ++i) {
                output.writeMessage(8, (MessageLite)this.grouping_.get(i));
            }
            if ((this.bitField0_ & 0x10) == 0x10) {
                output.writeMessage(9, (MessageLite)this.getGroupingCriteria());
            }
            for (int i = 0; i < this.args_.size(); ++i) {
                output.writeMessage(11, (MessageLite)this.args_.get(i));
            }
            if ((this.bitField0_ & 0x20) == 0x20) {
                output.writeEnum(12, this.locking_);
            }
            if ((this.bitField0_ & 0x40) == 0x40) {
                output.writeEnum(13, this.lockingOptions_);
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
                size += CodedOutputStream.computeMessageSize(2, (MessageLite)this.getCollection());
            }
            if ((this.bitField0_ & 0x2) == 0x2) {
                size += CodedOutputStream.computeEnumSize(3, this.dataModel_);
            }
            for (int i = 0; i < this.projection_.size(); ++i) {
                size += CodedOutputStream.computeMessageSize(4, (MessageLite)this.projection_.get(i));
            }
            if ((this.bitField0_ & 0x4) == 0x4) {
                size += CodedOutputStream.computeMessageSize(5, (MessageLite)this.getCriteria());
            }
            if ((this.bitField0_ & 0x8) == 0x8) {
                size += CodedOutputStream.computeMessageSize(6, (MessageLite)this.getLimit());
            }
            for (int i = 0; i < this.order_.size(); ++i) {
                size += CodedOutputStream.computeMessageSize(7, (MessageLite)this.order_.get(i));
            }
            for (int i = 0; i < this.grouping_.size(); ++i) {
                size += CodedOutputStream.computeMessageSize(8, (MessageLite)this.grouping_.get(i));
            }
            if ((this.bitField0_ & 0x10) == 0x10) {
                size += CodedOutputStream.computeMessageSize(9, (MessageLite)this.getGroupingCriteria());
            }
            for (int i = 0; i < this.args_.size(); ++i) {
                size += CodedOutputStream.computeMessageSize(11, (MessageLite)this.args_.get(i));
            }
            if ((this.bitField0_ & 0x20) == 0x20) {
                size += CodedOutputStream.computeEnumSize(12, this.locking_);
            }
            if ((this.bitField0_ & 0x40) == 0x40) {
                size += CodedOutputStream.computeEnumSize(13, this.lockingOptions_);
            }
            size += this.unknownFields.getSerializedSize();
            return this.memoizedSize = size;
        }
        
        public boolean equals(final Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Find)) {
                return super.equals(obj);
            }
            final Find other = (Find)obj;
            boolean result = true;
            result = (result && this.hasCollection() == other.hasCollection());
            if (this.hasCollection()) {
                result = (result && this.getCollection().equals(other.getCollection()));
            }
            result = (result && this.hasDataModel() == other.hasDataModel());
            if (this.hasDataModel()) {
                result = (result && this.dataModel_ == other.dataModel_);
            }
            result = (result && this.getProjectionList().equals(other.getProjectionList()));
            result = (result && this.hasCriteria() == other.hasCriteria());
            if (this.hasCriteria()) {
                result = (result && this.getCriteria().equals(other.getCriteria()));
            }
            result = (result && this.getArgsList().equals(other.getArgsList()));
            result = (result && this.hasLimit() == other.hasLimit());
            if (this.hasLimit()) {
                result = (result && this.getLimit().equals(other.getLimit()));
            }
            result = (result && this.getOrderList().equals(other.getOrderList()));
            result = (result && this.getGroupingList().equals(other.getGroupingList()));
            result = (result && this.hasGroupingCriteria() == other.hasGroupingCriteria());
            if (this.hasGroupingCriteria()) {
                result = (result && this.getGroupingCriteria().equals(other.getGroupingCriteria()));
            }
            result = (result && this.hasLocking() == other.hasLocking());
            if (this.hasLocking()) {
                result = (result && this.locking_ == other.locking_);
            }
            result = (result && this.hasLockingOptions() == other.hasLockingOptions());
            if (this.hasLockingOptions()) {
                result = (result && this.lockingOptions_ == other.lockingOptions_);
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
            if (this.hasCollection()) {
                hash = 37 * hash + 2;
                hash = 53 * hash + this.getCollection().hashCode();
            }
            if (this.hasDataModel()) {
                hash = 37 * hash + 3;
                hash = 53 * hash + this.dataModel_;
            }
            if (this.getProjectionCount() > 0) {
                hash = 37 * hash + 4;
                hash = 53 * hash + this.getProjectionList().hashCode();
            }
            if (this.hasCriteria()) {
                hash = 37 * hash + 5;
                hash = 53 * hash + this.getCriteria().hashCode();
            }
            if (this.getArgsCount() > 0) {
                hash = 37 * hash + 11;
                hash = 53 * hash + this.getArgsList().hashCode();
            }
            if (this.hasLimit()) {
                hash = 37 * hash + 6;
                hash = 53 * hash + this.getLimit().hashCode();
            }
            if (this.getOrderCount() > 0) {
                hash = 37 * hash + 7;
                hash = 53 * hash + this.getOrderList().hashCode();
            }
            if (this.getGroupingCount() > 0) {
                hash = 37 * hash + 8;
                hash = 53 * hash + this.getGroupingList().hashCode();
            }
            if (this.hasGroupingCriteria()) {
                hash = 37 * hash + 9;
                hash = 53 * hash + this.getGroupingCriteria().hashCode();
            }
            if (this.hasLocking()) {
                hash = 37 * hash + 12;
                hash = 53 * hash + this.locking_;
            }
            if (this.hasLockingOptions()) {
                hash = 37 * hash + 13;
                hash = 53 * hash + this.lockingOptions_;
            }
            hash = 29 * hash + this.unknownFields.hashCode();
            return this.memoizedHashCode = hash;
        }
        
        public static Find parseFrom(final ByteBuffer data) throws InvalidProtocolBufferException {
            return (Find)Find.PARSER.parseFrom(data);
        }
        
        public static Find parseFrom(final ByteBuffer data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Find)Find.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Find parseFrom(final ByteString data) throws InvalidProtocolBufferException {
            return (Find)Find.PARSER.parseFrom(data);
        }
        
        public static Find parseFrom(final ByteString data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Find)Find.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Find parseFrom(final byte[] data) throws InvalidProtocolBufferException {
            return (Find)Find.PARSER.parseFrom(data);
        }
        
        public static Find parseFrom(final byte[] data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Find)Find.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Find parseFrom(final InputStream input) throws IOException {
            return (Find)GeneratedMessageV3.parseWithIOException((Parser)Find.PARSER, input);
        }
        
        public static Find parseFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Find)GeneratedMessageV3.parseWithIOException((Parser)Find.PARSER, input, extensionRegistry);
        }
        
        public static Find parseDelimitedFrom(final InputStream input) throws IOException {
            return (Find)GeneratedMessageV3.parseDelimitedWithIOException((Parser)Find.PARSER, input);
        }
        
        public static Find parseDelimitedFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Find)GeneratedMessageV3.parseDelimitedWithIOException((Parser)Find.PARSER, input, extensionRegistry);
        }
        
        public static Find parseFrom(final CodedInputStream input) throws IOException {
            return (Find)GeneratedMessageV3.parseWithIOException((Parser)Find.PARSER, input);
        }
        
        public static Find parseFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Find)GeneratedMessageV3.parseWithIOException((Parser)Find.PARSER, input, extensionRegistry);
        }
        
        public Builder newBuilderForType() {
            return newBuilder();
        }
        
        public static Builder newBuilder() {
            return Find.DEFAULT_INSTANCE.toBuilder();
        }
        
        public static Builder newBuilder(final Find prototype) {
            return Find.DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }
        
        public Builder toBuilder() {
            return (this == Find.DEFAULT_INSTANCE) ? new Builder() : new Builder().mergeFrom(this);
        }
        
        protected Builder newBuilderForType(final GeneratedMessageV3.BuilderParent parent) {
            final Builder builder = new Builder(parent);
            return builder;
        }
        
        public static Find getDefaultInstance() {
            return Find.DEFAULT_INSTANCE;
        }
        
        public static Parser<Find> parser() {
            return Find.PARSER;
        }
        
        public Parser<Find> getParserForType() {
            return Find.PARSER;
        }
        
        public Find getDefaultInstanceForType() {
            return Find.DEFAULT_INSTANCE;
        }
        
        static {
            DEFAULT_INSTANCE = new Find();
            PARSER = (Parser)new AbstractParser<Find>() {
                public Find parsePartialFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new Find(input, extensionRegistry);
                }
            };
        }
        
        public enum RowLock implements ProtocolMessageEnum
        {
            SHARED_LOCK(1), 
            EXCLUSIVE_LOCK(2);
            
            public static final int SHARED_LOCK_VALUE = 1;
            public static final int EXCLUSIVE_LOCK_VALUE = 2;
            private static final Internal.EnumLiteMap<RowLock> internalValueMap;
            private static final RowLock[] VALUES;
            private final int value;
            
            public final int getNumber() {
                return this.value;
            }
            
            @Deprecated
            public static RowLock valueOf(final int value) {
                return forNumber(value);
            }
            
            public static RowLock forNumber(final int value) {
                switch (value) {
                    case 1: {
                        return RowLock.SHARED_LOCK;
                    }
                    case 2: {
                        return RowLock.EXCLUSIVE_LOCK;
                    }
                    default: {
                        return null;
                    }
                }
            }
            
            public static Internal.EnumLiteMap<RowLock> internalGetValueMap() {
                return RowLock.internalValueMap;
            }
            
            public final Descriptors.EnumValueDescriptor getValueDescriptor() {
                return getDescriptor().getValues().get(this.ordinal());
            }
            
            public final Descriptors.EnumDescriptor getDescriptorForType() {
                return getDescriptor();
            }
            
            public static final Descriptors.EnumDescriptor getDescriptor() {
                return Find.getDescriptor().getEnumTypes().get(0);
            }
            
            public static RowLock valueOf(final Descriptors.EnumValueDescriptor desc) {
                if (desc.getType() != getDescriptor()) {
                    throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
                }
                return RowLock.VALUES[desc.getIndex()];
            }
            
            private RowLock(final int value) {
                this.value = value;
            }
            
            static {
                internalValueMap = (Internal.EnumLiteMap)new Internal.EnumLiteMap<RowLock>() {
                    public RowLock findValueByNumber(final int number) {
                        return RowLock.forNumber(number);
                    }
                };
                VALUES = values();
            }
        }
        
        public enum RowLockOptions implements ProtocolMessageEnum
        {
            NOWAIT(1), 
            SKIP_LOCKED(2);
            
            public static final int NOWAIT_VALUE = 1;
            public static final int SKIP_LOCKED_VALUE = 2;
            private static final Internal.EnumLiteMap<RowLockOptions> internalValueMap;
            private static final RowLockOptions[] VALUES;
            private final int value;
            
            public final int getNumber() {
                return this.value;
            }
            
            @Deprecated
            public static RowLockOptions valueOf(final int value) {
                return forNumber(value);
            }
            
            public static RowLockOptions forNumber(final int value) {
                switch (value) {
                    case 1: {
                        return RowLockOptions.NOWAIT;
                    }
                    case 2: {
                        return RowLockOptions.SKIP_LOCKED;
                    }
                    default: {
                        return null;
                    }
                }
            }
            
            public static Internal.EnumLiteMap<RowLockOptions> internalGetValueMap() {
                return RowLockOptions.internalValueMap;
            }
            
            public final Descriptors.EnumValueDescriptor getValueDescriptor() {
                return getDescriptor().getValues().get(this.ordinal());
            }
            
            public final Descriptors.EnumDescriptor getDescriptorForType() {
                return getDescriptor();
            }
            
            public static final Descriptors.EnumDescriptor getDescriptor() {
                return Find.getDescriptor().getEnumTypes().get(1);
            }
            
            public static RowLockOptions valueOf(final Descriptors.EnumValueDescriptor desc) {
                if (desc.getType() != getDescriptor()) {
                    throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
                }
                return RowLockOptions.VALUES[desc.getIndex()];
            }
            
            private RowLockOptions(final int value) {
                this.value = value;
            }
            
            static {
                internalValueMap = (Internal.EnumLiteMap)new Internal.EnumLiteMap<RowLockOptions>() {
                    public RowLockOptions findValueByNumber(final int number) {
                        return RowLockOptions.forNumber(number);
                    }
                };
                VALUES = values();
            }
        }
        
        public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements FindOrBuilder
        {
            private int bitField0_;
            private Collection collection_;
            private SingleFieldBuilderV3<Collection, Collection.Builder, CollectionOrBuilder> collectionBuilder_;
            private int dataModel_;
            private List<Projection> projection_;
            private RepeatedFieldBuilderV3<Projection, Projection.Builder, ProjectionOrBuilder> projectionBuilder_;
            private MysqlxExpr.Expr criteria_;
            private SingleFieldBuilderV3<MysqlxExpr.Expr, MysqlxExpr.Expr.Builder, MysqlxExpr.ExprOrBuilder> criteriaBuilder_;
            private List<MysqlxDatatypes.Scalar> args_;
            private RepeatedFieldBuilderV3<MysqlxDatatypes.Scalar, MysqlxDatatypes.Scalar.Builder, MysqlxDatatypes.ScalarOrBuilder> argsBuilder_;
            private Limit limit_;
            private SingleFieldBuilderV3<Limit, Limit.Builder, LimitOrBuilder> limitBuilder_;
            private List<Order> order_;
            private RepeatedFieldBuilderV3<Order, Order.Builder, OrderOrBuilder> orderBuilder_;
            private List<MysqlxExpr.Expr> grouping_;
            private RepeatedFieldBuilderV3<MysqlxExpr.Expr, MysqlxExpr.Expr.Builder, MysqlxExpr.ExprOrBuilder> groupingBuilder_;
            private MysqlxExpr.Expr groupingCriteria_;
            private SingleFieldBuilderV3<MysqlxExpr.Expr, MysqlxExpr.Expr.Builder, MysqlxExpr.ExprOrBuilder> groupingCriteriaBuilder_;
            private int locking_;
            private int lockingOptions_;
            
            public static final Descriptors.Descriptor getDescriptor() {
                return MysqlxCrud.internal_static_Mysqlx_Crud_Find_descriptor;
            }
            
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return MysqlxCrud.internal_static_Mysqlx_Crud_Find_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)Find.class, (Class)Builder.class);
            }
            
            private Builder() {
                this.collection_ = null;
                this.dataModel_ = 1;
                this.projection_ = Collections.emptyList();
                this.criteria_ = null;
                this.args_ = Collections.emptyList();
                this.limit_ = null;
                this.order_ = Collections.emptyList();
                this.grouping_ = Collections.emptyList();
                this.groupingCriteria_ = null;
                this.locking_ = 1;
                this.lockingOptions_ = 1;
                this.maybeForceBuilderInitialization();
            }
            
            private Builder(final GeneratedMessageV3.BuilderParent parent) {
                super(parent);
                this.collection_ = null;
                this.dataModel_ = 1;
                this.projection_ = Collections.emptyList();
                this.criteria_ = null;
                this.args_ = Collections.emptyList();
                this.limit_ = null;
                this.order_ = Collections.emptyList();
                this.grouping_ = Collections.emptyList();
                this.groupingCriteria_ = null;
                this.locking_ = 1;
                this.lockingOptions_ = 1;
                this.maybeForceBuilderInitialization();
            }
            
            private void maybeForceBuilderInitialization() {
                if (Find.alwaysUseFieldBuilders) {
                    this.getCollectionFieldBuilder();
                    this.getProjectionFieldBuilder();
                    this.getCriteriaFieldBuilder();
                    this.getArgsFieldBuilder();
                    this.getLimitFieldBuilder();
                    this.getOrderFieldBuilder();
                    this.getGroupingFieldBuilder();
                    this.getGroupingCriteriaFieldBuilder();
                }
            }
            
            public Builder clear() {
                super.clear();
                if (this.collectionBuilder_ == null) {
                    this.collection_ = null;
                }
                else {
                    this.collectionBuilder_.clear();
                }
                this.bitField0_ &= 0xFFFFFFFE;
                this.dataModel_ = 1;
                this.bitField0_ &= 0xFFFFFFFD;
                if (this.projectionBuilder_ == null) {
                    this.projection_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFFB;
                }
                else {
                    this.projectionBuilder_.clear();
                }
                if (this.criteriaBuilder_ == null) {
                    this.criteria_ = null;
                }
                else {
                    this.criteriaBuilder_.clear();
                }
                this.bitField0_ &= 0xFFFFFFF7;
                if (this.argsBuilder_ == null) {
                    this.args_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFEF;
                }
                else {
                    this.argsBuilder_.clear();
                }
                if (this.limitBuilder_ == null) {
                    this.limit_ = null;
                }
                else {
                    this.limitBuilder_.clear();
                }
                this.bitField0_ &= 0xFFFFFFDF;
                if (this.orderBuilder_ == null) {
                    this.order_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFBF;
                }
                else {
                    this.orderBuilder_.clear();
                }
                if (this.groupingBuilder_ == null) {
                    this.grouping_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFF7F;
                }
                else {
                    this.groupingBuilder_.clear();
                }
                if (this.groupingCriteriaBuilder_ == null) {
                    this.groupingCriteria_ = null;
                }
                else {
                    this.groupingCriteriaBuilder_.clear();
                }
                this.bitField0_ &= 0xFFFFFEFF;
                this.locking_ = 1;
                this.bitField0_ &= 0xFFFFFDFF;
                this.lockingOptions_ = 1;
                this.bitField0_ &= 0xFFFFFBFF;
                return this;
            }
            
            public Descriptors.Descriptor getDescriptorForType() {
                return MysqlxCrud.internal_static_Mysqlx_Crud_Find_descriptor;
            }
            
            public Find getDefaultInstanceForType() {
                return Find.getDefaultInstance();
            }
            
            public Find build() {
                final Find result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw newUninitializedMessageException((Message)result);
                }
                return result;
            }
            
            public Find buildPartial() {
                final Find result = new Find((GeneratedMessageV3.Builder)this);
                final int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 0x1) == 0x1) {
                    to_bitField0_ |= 0x1;
                }
                if (this.collectionBuilder_ == null) {
                    result.collection_ = this.collection_;
                }
                else {
                    result.collection_ = (Collection)this.collectionBuilder_.build();
                }
                if ((from_bitField0_ & 0x2) == 0x2) {
                    to_bitField0_ |= 0x2;
                }
                result.dataModel_ = this.dataModel_;
                if (this.projectionBuilder_ == null) {
                    if ((this.bitField0_ & 0x4) == 0x4) {
                        this.projection_ = Collections.unmodifiableList((List<? extends Projection>)this.projection_);
                        this.bitField0_ &= 0xFFFFFFFB;
                    }
                    result.projection_ = this.projection_;
                }
                else {
                    result.projection_ = (List<Projection>)this.projectionBuilder_.build();
                }
                if ((from_bitField0_ & 0x8) == 0x8) {
                    to_bitField0_ |= 0x4;
                }
                if (this.criteriaBuilder_ == null) {
                    result.criteria_ = this.criteria_;
                }
                else {
                    result.criteria_ = (MysqlxExpr.Expr)this.criteriaBuilder_.build();
                }
                if (this.argsBuilder_ == null) {
                    if ((this.bitField0_ & 0x10) == 0x10) {
                        this.args_ = Collections.unmodifiableList((List<? extends MysqlxDatatypes.Scalar>)this.args_);
                        this.bitField0_ &= 0xFFFFFFEF;
                    }
                    result.args_ = this.args_;
                }
                else {
                    result.args_ = (List<MysqlxDatatypes.Scalar>)this.argsBuilder_.build();
                }
                if ((from_bitField0_ & 0x20) == 0x20) {
                    to_bitField0_ |= 0x8;
                }
                if (this.limitBuilder_ == null) {
                    result.limit_ = this.limit_;
                }
                else {
                    result.limit_ = (Limit)this.limitBuilder_.build();
                }
                if (this.orderBuilder_ == null) {
                    if ((this.bitField0_ & 0x40) == 0x40) {
                        this.order_ = Collections.unmodifiableList((List<? extends Order>)this.order_);
                        this.bitField0_ &= 0xFFFFFFBF;
                    }
                    result.order_ = this.order_;
                }
                else {
                    result.order_ = (List<Order>)this.orderBuilder_.build();
                }
                if (this.groupingBuilder_ == null) {
                    if ((this.bitField0_ & 0x80) == 0x80) {
                        this.grouping_ = Collections.unmodifiableList((List<? extends MysqlxExpr.Expr>)this.grouping_);
                        this.bitField0_ &= 0xFFFFFF7F;
                    }
                    result.grouping_ = this.grouping_;
                }
                else {
                    result.grouping_ = (List<MysqlxExpr.Expr>)this.groupingBuilder_.build();
                }
                if ((from_bitField0_ & 0x100) == 0x100) {
                    to_bitField0_ |= 0x10;
                }
                if (this.groupingCriteriaBuilder_ == null) {
                    result.groupingCriteria_ = this.groupingCriteria_;
                }
                else {
                    result.groupingCriteria_ = (MysqlxExpr.Expr)this.groupingCriteriaBuilder_.build();
                }
                if ((from_bitField0_ & 0x200) == 0x200) {
                    to_bitField0_ |= 0x20;
                }
                result.locking_ = this.locking_;
                if ((from_bitField0_ & 0x400) == 0x400) {
                    to_bitField0_ |= 0x40;
                }
                result.lockingOptions_ = this.lockingOptions_;
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
                if (other instanceof Find) {
                    return this.mergeFrom((Find)other);
                }
                super.mergeFrom(other);
                return this;
            }
            
            public Builder mergeFrom(final Find other) {
                if (other == Find.getDefaultInstance()) {
                    return this;
                }
                if (other.hasCollection()) {
                    this.mergeCollection(other.getCollection());
                }
                if (other.hasDataModel()) {
                    this.setDataModel(other.getDataModel());
                }
                if (this.projectionBuilder_ == null) {
                    if (!other.projection_.isEmpty()) {
                        if (this.projection_.isEmpty()) {
                            this.projection_ = other.projection_;
                            this.bitField0_ &= 0xFFFFFFFB;
                        }
                        else {
                            this.ensureProjectionIsMutable();
                            this.projection_.addAll(other.projection_);
                        }
                        this.onChanged();
                    }
                }
                else if (!other.projection_.isEmpty()) {
                    if (this.projectionBuilder_.isEmpty()) {
                        this.projectionBuilder_.dispose();
                        this.projectionBuilder_ = null;
                        this.projection_ = other.projection_;
                        this.bitField0_ &= 0xFFFFFFFB;
                        this.projectionBuilder_ = (Find.alwaysUseFieldBuilders ? this.getProjectionFieldBuilder() : null);
                    }
                    else {
                        this.projectionBuilder_.addAllMessages((Iterable)other.projection_);
                    }
                }
                if (other.hasCriteria()) {
                    this.mergeCriteria(other.getCriteria());
                }
                if (this.argsBuilder_ == null) {
                    if (!other.args_.isEmpty()) {
                        if (this.args_.isEmpty()) {
                            this.args_ = other.args_;
                            this.bitField0_ &= 0xFFFFFFEF;
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
                        this.bitField0_ &= 0xFFFFFFEF;
                        this.argsBuilder_ = (Find.alwaysUseFieldBuilders ? this.getArgsFieldBuilder() : null);
                    }
                    else {
                        this.argsBuilder_.addAllMessages((Iterable)other.args_);
                    }
                }
                if (other.hasLimit()) {
                    this.mergeLimit(other.getLimit());
                }
                if (this.orderBuilder_ == null) {
                    if (!other.order_.isEmpty()) {
                        if (this.order_.isEmpty()) {
                            this.order_ = other.order_;
                            this.bitField0_ &= 0xFFFFFFBF;
                        }
                        else {
                            this.ensureOrderIsMutable();
                            this.order_.addAll(other.order_);
                        }
                        this.onChanged();
                    }
                }
                else if (!other.order_.isEmpty()) {
                    if (this.orderBuilder_.isEmpty()) {
                        this.orderBuilder_.dispose();
                        this.orderBuilder_ = null;
                        this.order_ = other.order_;
                        this.bitField0_ &= 0xFFFFFFBF;
                        this.orderBuilder_ = (Find.alwaysUseFieldBuilders ? this.getOrderFieldBuilder() : null);
                    }
                    else {
                        this.orderBuilder_.addAllMessages((Iterable)other.order_);
                    }
                }
                if (this.groupingBuilder_ == null) {
                    if (!other.grouping_.isEmpty()) {
                        if (this.grouping_.isEmpty()) {
                            this.grouping_ = other.grouping_;
                            this.bitField0_ &= 0xFFFFFF7F;
                        }
                        else {
                            this.ensureGroupingIsMutable();
                            this.grouping_.addAll(other.grouping_);
                        }
                        this.onChanged();
                    }
                }
                else if (!other.grouping_.isEmpty()) {
                    if (this.groupingBuilder_.isEmpty()) {
                        this.groupingBuilder_.dispose();
                        this.groupingBuilder_ = null;
                        this.grouping_ = other.grouping_;
                        this.bitField0_ &= 0xFFFFFF7F;
                        this.groupingBuilder_ = (Find.alwaysUseFieldBuilders ? this.getGroupingFieldBuilder() : null);
                    }
                    else {
                        this.groupingBuilder_.addAllMessages((Iterable)other.grouping_);
                    }
                }
                if (other.hasGroupingCriteria()) {
                    this.mergeGroupingCriteria(other.getGroupingCriteria());
                }
                if (other.hasLocking()) {
                    this.setLocking(other.getLocking());
                }
                if (other.hasLockingOptions()) {
                    this.setLockingOptions(other.getLockingOptions());
                }
                this.mergeUnknownFields(other.unknownFields);
                this.onChanged();
                return this;
            }
            
            public final boolean isInitialized() {
                if (!this.hasCollection()) {
                    return false;
                }
                if (!this.getCollection().isInitialized()) {
                    return false;
                }
                for (int i = 0; i < this.getProjectionCount(); ++i) {
                    if (!this.getProjection(i).isInitialized()) {
                        return false;
                    }
                }
                if (this.hasCriteria() && !this.getCriteria().isInitialized()) {
                    return false;
                }
                for (int i = 0; i < this.getArgsCount(); ++i) {
                    if (!this.getArgs(i).isInitialized()) {
                        return false;
                    }
                }
                if (this.hasLimit() && !this.getLimit().isInitialized()) {
                    return false;
                }
                for (int i = 0; i < this.getOrderCount(); ++i) {
                    if (!this.getOrder(i).isInitialized()) {
                        return false;
                    }
                }
                for (int i = 0; i < this.getGroupingCount(); ++i) {
                    if (!this.getGrouping(i).isInitialized()) {
                        return false;
                    }
                }
                return !this.hasGroupingCriteria() || this.getGroupingCriteria().isInitialized();
            }
            
            public Builder mergeFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
                Find parsedMessage = null;
                try {
                    parsedMessage = (Find)Find.PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (Find)e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                }
                finally {
                    if (parsedMessage != null) {
                        this.mergeFrom(parsedMessage);
                    }
                }
                return this;
            }
            
            public boolean hasCollection() {
                return (this.bitField0_ & 0x1) == 0x1;
            }
            
            public Collection getCollection() {
                if (this.collectionBuilder_ == null) {
                    return (this.collection_ == null) ? Collection.getDefaultInstance() : this.collection_;
                }
                return (Collection)this.collectionBuilder_.getMessage();
            }
            
            public Builder setCollection(final Collection value) {
                if (this.collectionBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.collection_ = value;
                    this.onChanged();
                }
                else {
                    this.collectionBuilder_.setMessage((AbstractMessage)value);
                }
                this.bitField0_ |= 0x1;
                return this;
            }
            
            public Builder setCollection(final Collection.Builder builderForValue) {
                if (this.collectionBuilder_ == null) {
                    this.collection_ = builderForValue.build();
                    this.onChanged();
                }
                else {
                    this.collectionBuilder_.setMessage((AbstractMessage)builderForValue.build());
                }
                this.bitField0_ |= 0x1;
                return this;
            }
            
            public Builder mergeCollection(final Collection value) {
                if (this.collectionBuilder_ == null) {
                    if ((this.bitField0_ & 0x1) == 0x1 && this.collection_ != null && this.collection_ != Collection.getDefaultInstance()) {
                        this.collection_ = Collection.newBuilder(this.collection_).mergeFrom(value).buildPartial();
                    }
                    else {
                        this.collection_ = value;
                    }
                    this.onChanged();
                }
                else {
                    this.collectionBuilder_.mergeFrom((AbstractMessage)value);
                }
                this.bitField0_ |= 0x1;
                return this;
            }
            
            public Builder clearCollection() {
                if (this.collectionBuilder_ == null) {
                    this.collection_ = null;
                    this.onChanged();
                }
                else {
                    this.collectionBuilder_.clear();
                }
                this.bitField0_ &= 0xFFFFFFFE;
                return this;
            }
            
            public Collection.Builder getCollectionBuilder() {
                this.bitField0_ |= 0x1;
                this.onChanged();
                return (Collection.Builder)this.getCollectionFieldBuilder().getBuilder();
            }
            
            public CollectionOrBuilder getCollectionOrBuilder() {
                if (this.collectionBuilder_ != null) {
                    return (CollectionOrBuilder)this.collectionBuilder_.getMessageOrBuilder();
                }
                return (this.collection_ == null) ? Collection.getDefaultInstance() : this.collection_;
            }
            
            private SingleFieldBuilderV3<Collection, Collection.Builder, CollectionOrBuilder> getCollectionFieldBuilder() {
                if (this.collectionBuilder_ == null) {
                    this.collectionBuilder_ = (SingleFieldBuilderV3<Collection, Collection.Builder, CollectionOrBuilder>)new SingleFieldBuilderV3((AbstractMessage)this.getCollection(), (AbstractMessage.BuilderParent)this.getParentForChildren(), this.isClean());
                    this.collection_ = null;
                }
                return this.collectionBuilder_;
            }
            
            public boolean hasDataModel() {
                return (this.bitField0_ & 0x2) == 0x2;
            }
            
            public DataModel getDataModel() {
                final DataModel result = DataModel.valueOf(this.dataModel_);
                return (result == null) ? DataModel.DOCUMENT : result;
            }
            
            public Builder setDataModel(final DataModel value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 0x2;
                this.dataModel_ = value.getNumber();
                this.onChanged();
                return this;
            }
            
            public Builder clearDataModel() {
                this.bitField0_ &= 0xFFFFFFFD;
                this.dataModel_ = 1;
                this.onChanged();
                return this;
            }
            
            private void ensureProjectionIsMutable() {
                if ((this.bitField0_ & 0x4) != 0x4) {
                    this.projection_ = new ArrayList<Projection>(this.projection_);
                    this.bitField0_ |= 0x4;
                }
            }
            
            public List<Projection> getProjectionList() {
                if (this.projectionBuilder_ == null) {
                    return Collections.unmodifiableList((List<? extends Projection>)this.projection_);
                }
                return (List<Projection>)this.projectionBuilder_.getMessageList();
            }
            
            public int getProjectionCount() {
                if (this.projectionBuilder_ == null) {
                    return this.projection_.size();
                }
                return this.projectionBuilder_.getCount();
            }
            
            public Projection getProjection(final int index) {
                if (this.projectionBuilder_ == null) {
                    return this.projection_.get(index);
                }
                return (Projection)this.projectionBuilder_.getMessage(index);
            }
            
            public Builder setProjection(final int index, final Projection value) {
                if (this.projectionBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureProjectionIsMutable();
                    this.projection_.set(index, value);
                    this.onChanged();
                }
                else {
                    this.projectionBuilder_.setMessage(index, (AbstractMessage)value);
                }
                return this;
            }
            
            public Builder setProjection(final int index, final Projection.Builder builderForValue) {
                if (this.projectionBuilder_ == null) {
                    this.ensureProjectionIsMutable();
                    this.projection_.set(index, builderForValue.build());
                    this.onChanged();
                }
                else {
                    this.projectionBuilder_.setMessage(index, (AbstractMessage)builderForValue.build());
                }
                return this;
            }
            
            public Builder addProjection(final Projection value) {
                if (this.projectionBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureProjectionIsMutable();
                    this.projection_.add(value);
                    this.onChanged();
                }
                else {
                    this.projectionBuilder_.addMessage((AbstractMessage)value);
                }
                return this;
            }
            
            public Builder addProjection(final int index, final Projection value) {
                if (this.projectionBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureProjectionIsMutable();
                    this.projection_.add(index, value);
                    this.onChanged();
                }
                else {
                    this.projectionBuilder_.addMessage(index, (AbstractMessage)value);
                }
                return this;
            }
            
            public Builder addProjection(final Projection.Builder builderForValue) {
                if (this.projectionBuilder_ == null) {
                    this.ensureProjectionIsMutable();
                    this.projection_.add(builderForValue.build());
                    this.onChanged();
                }
                else {
                    this.projectionBuilder_.addMessage((AbstractMessage)builderForValue.build());
                }
                return this;
            }
            
            public Builder addProjection(final int index, final Projection.Builder builderForValue) {
                if (this.projectionBuilder_ == null) {
                    this.ensureProjectionIsMutable();
                    this.projection_.add(index, builderForValue.build());
                    this.onChanged();
                }
                else {
                    this.projectionBuilder_.addMessage(index, (AbstractMessage)builderForValue.build());
                }
                return this;
            }
            
            public Builder addAllProjection(final Iterable<? extends Projection> values) {
                if (this.projectionBuilder_ == null) {
                    this.ensureProjectionIsMutable();
                    AbstractMessageLite.Builder.addAll((Iterable)values, (List)this.projection_);
                    this.onChanged();
                }
                else {
                    this.projectionBuilder_.addAllMessages((Iterable)values);
                }
                return this;
            }
            
            public Builder clearProjection() {
                if (this.projectionBuilder_ == null) {
                    this.projection_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFFB;
                    this.onChanged();
                }
                else {
                    this.projectionBuilder_.clear();
                }
                return this;
            }
            
            public Builder removeProjection(final int index) {
                if (this.projectionBuilder_ == null) {
                    this.ensureProjectionIsMutable();
                    this.projection_.remove(index);
                    this.onChanged();
                }
                else {
                    this.projectionBuilder_.remove(index);
                }
                return this;
            }
            
            public Projection.Builder getProjectionBuilder(final int index) {
                return (Projection.Builder)this.getProjectionFieldBuilder().getBuilder(index);
            }
            
            public ProjectionOrBuilder getProjectionOrBuilder(final int index) {
                if (this.projectionBuilder_ == null) {
                    return this.projection_.get(index);
                }
                return (ProjectionOrBuilder)this.projectionBuilder_.getMessageOrBuilder(index);
            }
            
            public List<? extends ProjectionOrBuilder> getProjectionOrBuilderList() {
                if (this.projectionBuilder_ != null) {
                    return (List<? extends ProjectionOrBuilder>)this.projectionBuilder_.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList((List<? extends ProjectionOrBuilder>)this.projection_);
            }
            
            public Projection.Builder addProjectionBuilder() {
                return (Projection.Builder)this.getProjectionFieldBuilder().addBuilder((AbstractMessage)Projection.getDefaultInstance());
            }
            
            public Projection.Builder addProjectionBuilder(final int index) {
                return (Projection.Builder)this.getProjectionFieldBuilder().addBuilder(index, (AbstractMessage)Projection.getDefaultInstance());
            }
            
            public List<Projection.Builder> getProjectionBuilderList() {
                return (List<Projection.Builder>)this.getProjectionFieldBuilder().getBuilderList();
            }
            
            private RepeatedFieldBuilderV3<Projection, Projection.Builder, ProjectionOrBuilder> getProjectionFieldBuilder() {
                if (this.projectionBuilder_ == null) {
                    this.projectionBuilder_ = (RepeatedFieldBuilderV3<Projection, Projection.Builder, ProjectionOrBuilder>)new RepeatedFieldBuilderV3((List)this.projection_, (this.bitField0_ & 0x4) == 0x4, (AbstractMessage.BuilderParent)this.getParentForChildren(), this.isClean());
                    this.projection_ = null;
                }
                return this.projectionBuilder_;
            }
            
            public boolean hasCriteria() {
                return (this.bitField0_ & 0x8) == 0x8;
            }
            
            public MysqlxExpr.Expr getCriteria() {
                if (this.criteriaBuilder_ == null) {
                    return (this.criteria_ == null) ? MysqlxExpr.Expr.getDefaultInstance() : this.criteria_;
                }
                return (MysqlxExpr.Expr)this.criteriaBuilder_.getMessage();
            }
            
            public Builder setCriteria(final MysqlxExpr.Expr value) {
                if (this.criteriaBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.criteria_ = value;
                    this.onChanged();
                }
                else {
                    this.criteriaBuilder_.setMessage((AbstractMessage)value);
                }
                this.bitField0_ |= 0x8;
                return this;
            }
            
            public Builder setCriteria(final MysqlxExpr.Expr.Builder builderForValue) {
                if (this.criteriaBuilder_ == null) {
                    this.criteria_ = builderForValue.build();
                    this.onChanged();
                }
                else {
                    this.criteriaBuilder_.setMessage((AbstractMessage)builderForValue.build());
                }
                this.bitField0_ |= 0x8;
                return this;
            }
            
            public Builder mergeCriteria(final MysqlxExpr.Expr value) {
                if (this.criteriaBuilder_ == null) {
                    if ((this.bitField0_ & 0x8) == 0x8 && this.criteria_ != null && this.criteria_ != MysqlxExpr.Expr.getDefaultInstance()) {
                        this.criteria_ = MysqlxExpr.Expr.newBuilder(this.criteria_).mergeFrom(value).buildPartial();
                    }
                    else {
                        this.criteria_ = value;
                    }
                    this.onChanged();
                }
                else {
                    this.criteriaBuilder_.mergeFrom((AbstractMessage)value);
                }
                this.bitField0_ |= 0x8;
                return this;
            }
            
            public Builder clearCriteria() {
                if (this.criteriaBuilder_ == null) {
                    this.criteria_ = null;
                    this.onChanged();
                }
                else {
                    this.criteriaBuilder_.clear();
                }
                this.bitField0_ &= 0xFFFFFFF7;
                return this;
            }
            
            public MysqlxExpr.Expr.Builder getCriteriaBuilder() {
                this.bitField0_ |= 0x8;
                this.onChanged();
                return (MysqlxExpr.Expr.Builder)this.getCriteriaFieldBuilder().getBuilder();
            }
            
            public MysqlxExpr.ExprOrBuilder getCriteriaOrBuilder() {
                if (this.criteriaBuilder_ != null) {
                    return (MysqlxExpr.ExprOrBuilder)this.criteriaBuilder_.getMessageOrBuilder();
                }
                return (this.criteria_ == null) ? MysqlxExpr.Expr.getDefaultInstance() : this.criteria_;
            }
            
            private SingleFieldBuilderV3<MysqlxExpr.Expr, MysqlxExpr.Expr.Builder, MysqlxExpr.ExprOrBuilder> getCriteriaFieldBuilder() {
                if (this.criteriaBuilder_ == null) {
                    this.criteriaBuilder_ = (SingleFieldBuilderV3<MysqlxExpr.Expr, MysqlxExpr.Expr.Builder, MysqlxExpr.ExprOrBuilder>)new SingleFieldBuilderV3((AbstractMessage)this.getCriteria(), (AbstractMessage.BuilderParent)this.getParentForChildren(), this.isClean());
                    this.criteria_ = null;
                }
                return this.criteriaBuilder_;
            }
            
            private void ensureArgsIsMutable() {
                if ((this.bitField0_ & 0x10) != 0x10) {
                    this.args_ = new ArrayList<MysqlxDatatypes.Scalar>(this.args_);
                    this.bitField0_ |= 0x10;
                }
            }
            
            public List<MysqlxDatatypes.Scalar> getArgsList() {
                if (this.argsBuilder_ == null) {
                    return Collections.unmodifiableList((List<? extends MysqlxDatatypes.Scalar>)this.args_);
                }
                return (List<MysqlxDatatypes.Scalar>)this.argsBuilder_.getMessageList();
            }
            
            public int getArgsCount() {
                if (this.argsBuilder_ == null) {
                    return this.args_.size();
                }
                return this.argsBuilder_.getCount();
            }
            
            public MysqlxDatatypes.Scalar getArgs(final int index) {
                if (this.argsBuilder_ == null) {
                    return this.args_.get(index);
                }
                return (MysqlxDatatypes.Scalar)this.argsBuilder_.getMessage(index);
            }
            
            public Builder setArgs(final int index, final MysqlxDatatypes.Scalar value) {
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
            
            public Builder setArgs(final int index, final MysqlxDatatypes.Scalar.Builder builderForValue) {
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
            
            public Builder addArgs(final MysqlxDatatypes.Scalar value) {
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
            
            public Builder addArgs(final int index, final MysqlxDatatypes.Scalar value) {
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
            
            public Builder addArgs(final MysqlxDatatypes.Scalar.Builder builderForValue) {
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
            
            public Builder addArgs(final int index, final MysqlxDatatypes.Scalar.Builder builderForValue) {
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
            
            public Builder addAllArgs(final Iterable<? extends MysqlxDatatypes.Scalar> values) {
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
                    this.bitField0_ &= 0xFFFFFFEF;
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
            
            public MysqlxDatatypes.Scalar.Builder getArgsBuilder(final int index) {
                return (MysqlxDatatypes.Scalar.Builder)this.getArgsFieldBuilder().getBuilder(index);
            }
            
            public MysqlxDatatypes.ScalarOrBuilder getArgsOrBuilder(final int index) {
                if (this.argsBuilder_ == null) {
                    return this.args_.get(index);
                }
                return (MysqlxDatatypes.ScalarOrBuilder)this.argsBuilder_.getMessageOrBuilder(index);
            }
            
            public List<? extends MysqlxDatatypes.ScalarOrBuilder> getArgsOrBuilderList() {
                if (this.argsBuilder_ != null) {
                    return (List<? extends MysqlxDatatypes.ScalarOrBuilder>)this.argsBuilder_.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList((List<? extends MysqlxDatatypes.ScalarOrBuilder>)this.args_);
            }
            
            public MysqlxDatatypes.Scalar.Builder addArgsBuilder() {
                return (MysqlxDatatypes.Scalar.Builder)this.getArgsFieldBuilder().addBuilder((AbstractMessage)MysqlxDatatypes.Scalar.getDefaultInstance());
            }
            
            public MysqlxDatatypes.Scalar.Builder addArgsBuilder(final int index) {
                return (MysqlxDatatypes.Scalar.Builder)this.getArgsFieldBuilder().addBuilder(index, (AbstractMessage)MysqlxDatatypes.Scalar.getDefaultInstance());
            }
            
            public List<MysqlxDatatypes.Scalar.Builder> getArgsBuilderList() {
                return (List<MysqlxDatatypes.Scalar.Builder>)this.getArgsFieldBuilder().getBuilderList();
            }
            
            private RepeatedFieldBuilderV3<MysqlxDatatypes.Scalar, MysqlxDatatypes.Scalar.Builder, MysqlxDatatypes.ScalarOrBuilder> getArgsFieldBuilder() {
                if (this.argsBuilder_ == null) {
                    this.argsBuilder_ = (RepeatedFieldBuilderV3<MysqlxDatatypes.Scalar, MysqlxDatatypes.Scalar.Builder, MysqlxDatatypes.ScalarOrBuilder>)new RepeatedFieldBuilderV3((List)this.args_, (this.bitField0_ & 0x10) == 0x10, (AbstractMessage.BuilderParent)this.getParentForChildren(), this.isClean());
                    this.args_ = null;
                }
                return this.argsBuilder_;
            }
            
            public boolean hasLimit() {
                return (this.bitField0_ & 0x20) == 0x20;
            }
            
            public Limit getLimit() {
                if (this.limitBuilder_ == null) {
                    return (this.limit_ == null) ? Limit.getDefaultInstance() : this.limit_;
                }
                return (Limit)this.limitBuilder_.getMessage();
            }
            
            public Builder setLimit(final Limit value) {
                if (this.limitBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.limit_ = value;
                    this.onChanged();
                }
                else {
                    this.limitBuilder_.setMessage((AbstractMessage)value);
                }
                this.bitField0_ |= 0x20;
                return this;
            }
            
            public Builder setLimit(final Limit.Builder builderForValue) {
                if (this.limitBuilder_ == null) {
                    this.limit_ = builderForValue.build();
                    this.onChanged();
                }
                else {
                    this.limitBuilder_.setMessage((AbstractMessage)builderForValue.build());
                }
                this.bitField0_ |= 0x20;
                return this;
            }
            
            public Builder mergeLimit(final Limit value) {
                if (this.limitBuilder_ == null) {
                    if ((this.bitField0_ & 0x20) == 0x20 && this.limit_ != null && this.limit_ != Limit.getDefaultInstance()) {
                        this.limit_ = Limit.newBuilder(this.limit_).mergeFrom(value).buildPartial();
                    }
                    else {
                        this.limit_ = value;
                    }
                    this.onChanged();
                }
                else {
                    this.limitBuilder_.mergeFrom((AbstractMessage)value);
                }
                this.bitField0_ |= 0x20;
                return this;
            }
            
            public Builder clearLimit() {
                if (this.limitBuilder_ == null) {
                    this.limit_ = null;
                    this.onChanged();
                }
                else {
                    this.limitBuilder_.clear();
                }
                this.bitField0_ &= 0xFFFFFFDF;
                return this;
            }
            
            public Limit.Builder getLimitBuilder() {
                this.bitField0_ |= 0x20;
                this.onChanged();
                return (Limit.Builder)this.getLimitFieldBuilder().getBuilder();
            }
            
            public LimitOrBuilder getLimitOrBuilder() {
                if (this.limitBuilder_ != null) {
                    return (LimitOrBuilder)this.limitBuilder_.getMessageOrBuilder();
                }
                return (this.limit_ == null) ? Limit.getDefaultInstance() : this.limit_;
            }
            
            private SingleFieldBuilderV3<Limit, Limit.Builder, LimitOrBuilder> getLimitFieldBuilder() {
                if (this.limitBuilder_ == null) {
                    this.limitBuilder_ = (SingleFieldBuilderV3<Limit, Limit.Builder, LimitOrBuilder>)new SingleFieldBuilderV3((AbstractMessage)this.getLimit(), (AbstractMessage.BuilderParent)this.getParentForChildren(), this.isClean());
                    this.limit_ = null;
                }
                return this.limitBuilder_;
            }
            
            private void ensureOrderIsMutable() {
                if ((this.bitField0_ & 0x40) != 0x40) {
                    this.order_ = new ArrayList<Order>(this.order_);
                    this.bitField0_ |= 0x40;
                }
            }
            
            public List<Order> getOrderList() {
                if (this.orderBuilder_ == null) {
                    return Collections.unmodifiableList((List<? extends Order>)this.order_);
                }
                return (List<Order>)this.orderBuilder_.getMessageList();
            }
            
            public int getOrderCount() {
                if (this.orderBuilder_ == null) {
                    return this.order_.size();
                }
                return this.orderBuilder_.getCount();
            }
            
            public Order getOrder(final int index) {
                if (this.orderBuilder_ == null) {
                    return this.order_.get(index);
                }
                return (Order)this.orderBuilder_.getMessage(index);
            }
            
            public Builder setOrder(final int index, final Order value) {
                if (this.orderBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureOrderIsMutable();
                    this.order_.set(index, value);
                    this.onChanged();
                }
                else {
                    this.orderBuilder_.setMessage(index, (AbstractMessage)value);
                }
                return this;
            }
            
            public Builder setOrder(final int index, final Order.Builder builderForValue) {
                if (this.orderBuilder_ == null) {
                    this.ensureOrderIsMutable();
                    this.order_.set(index, builderForValue.build());
                    this.onChanged();
                }
                else {
                    this.orderBuilder_.setMessage(index, (AbstractMessage)builderForValue.build());
                }
                return this;
            }
            
            public Builder addOrder(final Order value) {
                if (this.orderBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureOrderIsMutable();
                    this.order_.add(value);
                    this.onChanged();
                }
                else {
                    this.orderBuilder_.addMessage((AbstractMessage)value);
                }
                return this;
            }
            
            public Builder addOrder(final int index, final Order value) {
                if (this.orderBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureOrderIsMutable();
                    this.order_.add(index, value);
                    this.onChanged();
                }
                else {
                    this.orderBuilder_.addMessage(index, (AbstractMessage)value);
                }
                return this;
            }
            
            public Builder addOrder(final Order.Builder builderForValue) {
                if (this.orderBuilder_ == null) {
                    this.ensureOrderIsMutable();
                    this.order_.add(builderForValue.build());
                    this.onChanged();
                }
                else {
                    this.orderBuilder_.addMessage((AbstractMessage)builderForValue.build());
                }
                return this;
            }
            
            public Builder addOrder(final int index, final Order.Builder builderForValue) {
                if (this.orderBuilder_ == null) {
                    this.ensureOrderIsMutable();
                    this.order_.add(index, builderForValue.build());
                    this.onChanged();
                }
                else {
                    this.orderBuilder_.addMessage(index, (AbstractMessage)builderForValue.build());
                }
                return this;
            }
            
            public Builder addAllOrder(final Iterable<? extends Order> values) {
                if (this.orderBuilder_ == null) {
                    this.ensureOrderIsMutable();
                    AbstractMessageLite.Builder.addAll((Iterable)values, (List)this.order_);
                    this.onChanged();
                }
                else {
                    this.orderBuilder_.addAllMessages((Iterable)values);
                }
                return this;
            }
            
            public Builder clearOrder() {
                if (this.orderBuilder_ == null) {
                    this.order_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFBF;
                    this.onChanged();
                }
                else {
                    this.orderBuilder_.clear();
                }
                return this;
            }
            
            public Builder removeOrder(final int index) {
                if (this.orderBuilder_ == null) {
                    this.ensureOrderIsMutable();
                    this.order_.remove(index);
                    this.onChanged();
                }
                else {
                    this.orderBuilder_.remove(index);
                }
                return this;
            }
            
            public Order.Builder getOrderBuilder(final int index) {
                return (Order.Builder)this.getOrderFieldBuilder().getBuilder(index);
            }
            
            public OrderOrBuilder getOrderOrBuilder(final int index) {
                if (this.orderBuilder_ == null) {
                    return this.order_.get(index);
                }
                return (OrderOrBuilder)this.orderBuilder_.getMessageOrBuilder(index);
            }
            
            public List<? extends OrderOrBuilder> getOrderOrBuilderList() {
                if (this.orderBuilder_ != null) {
                    return (List<? extends OrderOrBuilder>)this.orderBuilder_.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList((List<? extends OrderOrBuilder>)this.order_);
            }
            
            public Order.Builder addOrderBuilder() {
                return (Order.Builder)this.getOrderFieldBuilder().addBuilder((AbstractMessage)Order.getDefaultInstance());
            }
            
            public Order.Builder addOrderBuilder(final int index) {
                return (Order.Builder)this.getOrderFieldBuilder().addBuilder(index, (AbstractMessage)Order.getDefaultInstance());
            }
            
            public List<Order.Builder> getOrderBuilderList() {
                return (List<Order.Builder>)this.getOrderFieldBuilder().getBuilderList();
            }
            
            private RepeatedFieldBuilderV3<Order, Order.Builder, OrderOrBuilder> getOrderFieldBuilder() {
                if (this.orderBuilder_ == null) {
                    this.orderBuilder_ = (RepeatedFieldBuilderV3<Order, Order.Builder, OrderOrBuilder>)new RepeatedFieldBuilderV3((List)this.order_, (this.bitField0_ & 0x40) == 0x40, (AbstractMessage.BuilderParent)this.getParentForChildren(), this.isClean());
                    this.order_ = null;
                }
                return this.orderBuilder_;
            }
            
            private void ensureGroupingIsMutable() {
                if ((this.bitField0_ & 0x80) != 0x80) {
                    this.grouping_ = new ArrayList<MysqlxExpr.Expr>(this.grouping_);
                    this.bitField0_ |= 0x80;
                }
            }
            
            public List<MysqlxExpr.Expr> getGroupingList() {
                if (this.groupingBuilder_ == null) {
                    return Collections.unmodifiableList((List<? extends MysqlxExpr.Expr>)this.grouping_);
                }
                return (List<MysqlxExpr.Expr>)this.groupingBuilder_.getMessageList();
            }
            
            public int getGroupingCount() {
                if (this.groupingBuilder_ == null) {
                    return this.grouping_.size();
                }
                return this.groupingBuilder_.getCount();
            }
            
            public MysqlxExpr.Expr getGrouping(final int index) {
                if (this.groupingBuilder_ == null) {
                    return this.grouping_.get(index);
                }
                return (MysqlxExpr.Expr)this.groupingBuilder_.getMessage(index);
            }
            
            public Builder setGrouping(final int index, final MysqlxExpr.Expr value) {
                if (this.groupingBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureGroupingIsMutable();
                    this.grouping_.set(index, value);
                    this.onChanged();
                }
                else {
                    this.groupingBuilder_.setMessage(index, (AbstractMessage)value);
                }
                return this;
            }
            
            public Builder setGrouping(final int index, final MysqlxExpr.Expr.Builder builderForValue) {
                if (this.groupingBuilder_ == null) {
                    this.ensureGroupingIsMutable();
                    this.grouping_.set(index, builderForValue.build());
                    this.onChanged();
                }
                else {
                    this.groupingBuilder_.setMessage(index, (AbstractMessage)builderForValue.build());
                }
                return this;
            }
            
            public Builder addGrouping(final MysqlxExpr.Expr value) {
                if (this.groupingBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureGroupingIsMutable();
                    this.grouping_.add(value);
                    this.onChanged();
                }
                else {
                    this.groupingBuilder_.addMessage((AbstractMessage)value);
                }
                return this;
            }
            
            public Builder addGrouping(final int index, final MysqlxExpr.Expr value) {
                if (this.groupingBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureGroupingIsMutable();
                    this.grouping_.add(index, value);
                    this.onChanged();
                }
                else {
                    this.groupingBuilder_.addMessage(index, (AbstractMessage)value);
                }
                return this;
            }
            
            public Builder addGrouping(final MysqlxExpr.Expr.Builder builderForValue) {
                if (this.groupingBuilder_ == null) {
                    this.ensureGroupingIsMutable();
                    this.grouping_.add(builderForValue.build());
                    this.onChanged();
                }
                else {
                    this.groupingBuilder_.addMessage((AbstractMessage)builderForValue.build());
                }
                return this;
            }
            
            public Builder addGrouping(final int index, final MysqlxExpr.Expr.Builder builderForValue) {
                if (this.groupingBuilder_ == null) {
                    this.ensureGroupingIsMutable();
                    this.grouping_.add(index, builderForValue.build());
                    this.onChanged();
                }
                else {
                    this.groupingBuilder_.addMessage(index, (AbstractMessage)builderForValue.build());
                }
                return this;
            }
            
            public Builder addAllGrouping(final Iterable<? extends MysqlxExpr.Expr> values) {
                if (this.groupingBuilder_ == null) {
                    this.ensureGroupingIsMutable();
                    AbstractMessageLite.Builder.addAll((Iterable)values, (List)this.grouping_);
                    this.onChanged();
                }
                else {
                    this.groupingBuilder_.addAllMessages((Iterable)values);
                }
                return this;
            }
            
            public Builder clearGrouping() {
                if (this.groupingBuilder_ == null) {
                    this.grouping_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFF7F;
                    this.onChanged();
                }
                else {
                    this.groupingBuilder_.clear();
                }
                return this;
            }
            
            public Builder removeGrouping(final int index) {
                if (this.groupingBuilder_ == null) {
                    this.ensureGroupingIsMutable();
                    this.grouping_.remove(index);
                    this.onChanged();
                }
                else {
                    this.groupingBuilder_.remove(index);
                }
                return this;
            }
            
            public MysqlxExpr.Expr.Builder getGroupingBuilder(final int index) {
                return (MysqlxExpr.Expr.Builder)this.getGroupingFieldBuilder().getBuilder(index);
            }
            
            public MysqlxExpr.ExprOrBuilder getGroupingOrBuilder(final int index) {
                if (this.groupingBuilder_ == null) {
                    return this.grouping_.get(index);
                }
                return (MysqlxExpr.ExprOrBuilder)this.groupingBuilder_.getMessageOrBuilder(index);
            }
            
            public List<? extends MysqlxExpr.ExprOrBuilder> getGroupingOrBuilderList() {
                if (this.groupingBuilder_ != null) {
                    return (List<? extends MysqlxExpr.ExprOrBuilder>)this.groupingBuilder_.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList((List<? extends MysqlxExpr.ExprOrBuilder>)this.grouping_);
            }
            
            public MysqlxExpr.Expr.Builder addGroupingBuilder() {
                return (MysqlxExpr.Expr.Builder)this.getGroupingFieldBuilder().addBuilder((AbstractMessage)MysqlxExpr.Expr.getDefaultInstance());
            }
            
            public MysqlxExpr.Expr.Builder addGroupingBuilder(final int index) {
                return (MysqlxExpr.Expr.Builder)this.getGroupingFieldBuilder().addBuilder(index, (AbstractMessage)MysqlxExpr.Expr.getDefaultInstance());
            }
            
            public List<MysqlxExpr.Expr.Builder> getGroupingBuilderList() {
                return (List<MysqlxExpr.Expr.Builder>)this.getGroupingFieldBuilder().getBuilderList();
            }
            
            private RepeatedFieldBuilderV3<MysqlxExpr.Expr, MysqlxExpr.Expr.Builder, MysqlxExpr.ExprOrBuilder> getGroupingFieldBuilder() {
                if (this.groupingBuilder_ == null) {
                    this.groupingBuilder_ = (RepeatedFieldBuilderV3<MysqlxExpr.Expr, MysqlxExpr.Expr.Builder, MysqlxExpr.ExprOrBuilder>)new RepeatedFieldBuilderV3((List)this.grouping_, (this.bitField0_ & 0x80) == 0x80, (AbstractMessage.BuilderParent)this.getParentForChildren(), this.isClean());
                    this.grouping_ = null;
                }
                return this.groupingBuilder_;
            }
            
            public boolean hasGroupingCriteria() {
                return (this.bitField0_ & 0x100) == 0x100;
            }
            
            public MysqlxExpr.Expr getGroupingCriteria() {
                if (this.groupingCriteriaBuilder_ == null) {
                    return (this.groupingCriteria_ == null) ? MysqlxExpr.Expr.getDefaultInstance() : this.groupingCriteria_;
                }
                return (MysqlxExpr.Expr)this.groupingCriteriaBuilder_.getMessage();
            }
            
            public Builder setGroupingCriteria(final MysqlxExpr.Expr value) {
                if (this.groupingCriteriaBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.groupingCriteria_ = value;
                    this.onChanged();
                }
                else {
                    this.groupingCriteriaBuilder_.setMessage((AbstractMessage)value);
                }
                this.bitField0_ |= 0x100;
                return this;
            }
            
            public Builder setGroupingCriteria(final MysqlxExpr.Expr.Builder builderForValue) {
                if (this.groupingCriteriaBuilder_ == null) {
                    this.groupingCriteria_ = builderForValue.build();
                    this.onChanged();
                }
                else {
                    this.groupingCriteriaBuilder_.setMessage((AbstractMessage)builderForValue.build());
                }
                this.bitField0_ |= 0x100;
                return this;
            }
            
            public Builder mergeGroupingCriteria(final MysqlxExpr.Expr value) {
                if (this.groupingCriteriaBuilder_ == null) {
                    if ((this.bitField0_ & 0x100) == 0x100 && this.groupingCriteria_ != null && this.groupingCriteria_ != MysqlxExpr.Expr.getDefaultInstance()) {
                        this.groupingCriteria_ = MysqlxExpr.Expr.newBuilder(this.groupingCriteria_).mergeFrom(value).buildPartial();
                    }
                    else {
                        this.groupingCriteria_ = value;
                    }
                    this.onChanged();
                }
                else {
                    this.groupingCriteriaBuilder_.mergeFrom((AbstractMessage)value);
                }
                this.bitField0_ |= 0x100;
                return this;
            }
            
            public Builder clearGroupingCriteria() {
                if (this.groupingCriteriaBuilder_ == null) {
                    this.groupingCriteria_ = null;
                    this.onChanged();
                }
                else {
                    this.groupingCriteriaBuilder_.clear();
                }
                this.bitField0_ &= 0xFFFFFEFF;
                return this;
            }
            
            public MysqlxExpr.Expr.Builder getGroupingCriteriaBuilder() {
                this.bitField0_ |= 0x100;
                this.onChanged();
                return (MysqlxExpr.Expr.Builder)this.getGroupingCriteriaFieldBuilder().getBuilder();
            }
            
            public MysqlxExpr.ExprOrBuilder getGroupingCriteriaOrBuilder() {
                if (this.groupingCriteriaBuilder_ != null) {
                    return (MysqlxExpr.ExprOrBuilder)this.groupingCriteriaBuilder_.getMessageOrBuilder();
                }
                return (this.groupingCriteria_ == null) ? MysqlxExpr.Expr.getDefaultInstance() : this.groupingCriteria_;
            }
            
            private SingleFieldBuilderV3<MysqlxExpr.Expr, MysqlxExpr.Expr.Builder, MysqlxExpr.ExprOrBuilder> getGroupingCriteriaFieldBuilder() {
                if (this.groupingCriteriaBuilder_ == null) {
                    this.groupingCriteriaBuilder_ = (SingleFieldBuilderV3<MysqlxExpr.Expr, MysqlxExpr.Expr.Builder, MysqlxExpr.ExprOrBuilder>)new SingleFieldBuilderV3((AbstractMessage)this.getGroupingCriteria(), (AbstractMessage.BuilderParent)this.getParentForChildren(), this.isClean());
                    this.groupingCriteria_ = null;
                }
                return this.groupingCriteriaBuilder_;
            }
            
            public boolean hasLocking() {
                return (this.bitField0_ & 0x200) == 0x200;
            }
            
            public RowLock getLocking() {
                final RowLock result = RowLock.valueOf(this.locking_);
                return (result == null) ? RowLock.SHARED_LOCK : result;
            }
            
            public Builder setLocking(final RowLock value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 0x200;
                this.locking_ = value.getNumber();
                this.onChanged();
                return this;
            }
            
            public Builder clearLocking() {
                this.bitField0_ &= 0xFFFFFDFF;
                this.locking_ = 1;
                this.onChanged();
                return this;
            }
            
            public boolean hasLockingOptions() {
                return (this.bitField0_ & 0x400) == 0x400;
            }
            
            public RowLockOptions getLockingOptions() {
                final RowLockOptions result = RowLockOptions.valueOf(this.lockingOptions_);
                return (result == null) ? RowLockOptions.NOWAIT : result;
            }
            
            public Builder setLockingOptions(final RowLockOptions value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 0x400;
                this.lockingOptions_ = value.getNumber();
                this.onChanged();
                return this;
            }
            
            public Builder clearLockingOptions() {
                this.bitField0_ &= 0xFFFFFBFF;
                this.lockingOptions_ = 1;
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
    
    public static final class Insert extends GeneratedMessageV3 implements InsertOrBuilder
    {
        private static final long serialVersionUID = 0L;
        private int bitField0_;
        public static final int COLLECTION_FIELD_NUMBER = 1;
        private Collection collection_;
        public static final int DATA_MODEL_FIELD_NUMBER = 2;
        private int dataModel_;
        public static final int PROJECTION_FIELD_NUMBER = 3;
        private List<Column> projection_;
        public static final int ROW_FIELD_NUMBER = 4;
        private List<TypedRow> row_;
        public static final int ARGS_FIELD_NUMBER = 5;
        private List<MysqlxDatatypes.Scalar> args_;
        public static final int UPSERT_FIELD_NUMBER = 6;
        private boolean upsert_;
        private byte memoizedIsInitialized;
        private static final Insert DEFAULT_INSTANCE;
        @Deprecated
        public static final Parser<Insert> PARSER;
        
        private Insert(final GeneratedMessageV3.Builder<?> builder) {
            super((GeneratedMessageV3.Builder)builder);
            this.memoizedIsInitialized = -1;
        }
        
        private Insert() {
            this.memoizedIsInitialized = -1;
            this.dataModel_ = 1;
            this.projection_ = Collections.emptyList();
            this.row_ = Collections.emptyList();
            this.args_ = Collections.emptyList();
            this.upsert_ = false;
        }
        
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }
        
        private Insert(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            Collection.Builder subBuilder = null;
                            if ((this.bitField0_ & 0x1) == 0x1) {
                                subBuilder = this.collection_.toBuilder();
                            }
                            this.collection_ = (Collection)input.readMessage((Parser)Collection.PARSER, extensionRegistry);
                            if (subBuilder != null) {
                                subBuilder.mergeFrom(this.collection_);
                                this.collection_ = subBuilder.buildPartial();
                            }
                            this.bitField0_ |= 0x1;
                            continue;
                        }
                        case 16: {
                            final int rawValue = input.readEnum();
                            final DataModel value = DataModel.valueOf(rawValue);
                            if (value == null) {
                                unknownFields.mergeVarintField(2, rawValue);
                                continue;
                            }
                            this.bitField0_ |= 0x2;
                            this.dataModel_ = rawValue;
                            continue;
                        }
                        case 26: {
                            if ((mutable_bitField0_ & 0x4) != 0x4) {
                                this.projection_ = new ArrayList<Column>();
                                mutable_bitField0_ |= 0x4;
                            }
                            this.projection_.add((Column)input.readMessage((Parser)Column.PARSER, extensionRegistry));
                            continue;
                        }
                        case 34: {
                            if ((mutable_bitField0_ & 0x8) != 0x8) {
                                this.row_ = new ArrayList<TypedRow>();
                                mutable_bitField0_ |= 0x8;
                            }
                            this.row_.add((TypedRow)input.readMessage((Parser)TypedRow.PARSER, extensionRegistry));
                            continue;
                        }
                        case 42: {
                            if ((mutable_bitField0_ & 0x10) != 0x10) {
                                this.args_ = new ArrayList<MysqlxDatatypes.Scalar>();
                                mutable_bitField0_ |= 0x10;
                            }
                            this.args_.add((MysqlxDatatypes.Scalar)input.readMessage((Parser)MysqlxDatatypes.Scalar.PARSER, extensionRegistry));
                            continue;
                        }
                        case 48: {
                            this.bitField0_ |= 0x4;
                            this.upsert_ = input.readBool();
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
                    this.projection_ = Collections.unmodifiableList((List<? extends Column>)this.projection_);
                }
                if ((mutable_bitField0_ & 0x8) == 0x8) {
                    this.row_ = Collections.unmodifiableList((List<? extends TypedRow>)this.row_);
                }
                if ((mutable_bitField0_ & 0x10) == 0x10) {
                    this.args_ = Collections.unmodifiableList((List<? extends MysqlxDatatypes.Scalar>)this.args_);
                }
                this.unknownFields = unknownFields.build();
                this.makeExtensionsImmutable();
            }
        }
        
        public static final Descriptors.Descriptor getDescriptor() {
            return MysqlxCrud.internal_static_Mysqlx_Crud_Insert_descriptor;
        }
        
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return MysqlxCrud.internal_static_Mysqlx_Crud_Insert_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)Insert.class, (Class)Builder.class);
        }
        
        public boolean hasCollection() {
            return (this.bitField0_ & 0x1) == 0x1;
        }
        
        public Collection getCollection() {
            return (this.collection_ == null) ? Collection.getDefaultInstance() : this.collection_;
        }
        
        public CollectionOrBuilder getCollectionOrBuilder() {
            return (this.collection_ == null) ? Collection.getDefaultInstance() : this.collection_;
        }
        
        public boolean hasDataModel() {
            return (this.bitField0_ & 0x2) == 0x2;
        }
        
        public DataModel getDataModel() {
            final DataModel result = DataModel.valueOf(this.dataModel_);
            return (result == null) ? DataModel.DOCUMENT : result;
        }
        
        public List<Column> getProjectionList() {
            return this.projection_;
        }
        
        public List<? extends ColumnOrBuilder> getProjectionOrBuilderList() {
            return this.projection_;
        }
        
        public int getProjectionCount() {
            return this.projection_.size();
        }
        
        public Column getProjection(final int index) {
            return this.projection_.get(index);
        }
        
        public ColumnOrBuilder getProjectionOrBuilder(final int index) {
            return this.projection_.get(index);
        }
        
        public List<TypedRow> getRowList() {
            return this.row_;
        }
        
        public List<? extends TypedRowOrBuilder> getRowOrBuilderList() {
            return this.row_;
        }
        
        public int getRowCount() {
            return this.row_.size();
        }
        
        public TypedRow getRow(final int index) {
            return this.row_.get(index);
        }
        
        public TypedRowOrBuilder getRowOrBuilder(final int index) {
            return this.row_.get(index);
        }
        
        public List<MysqlxDatatypes.Scalar> getArgsList() {
            return this.args_;
        }
        
        public List<? extends MysqlxDatatypes.ScalarOrBuilder> getArgsOrBuilderList() {
            return this.args_;
        }
        
        public int getArgsCount() {
            return this.args_.size();
        }
        
        public MysqlxDatatypes.Scalar getArgs(final int index) {
            return this.args_.get(index);
        }
        
        public MysqlxDatatypes.ScalarOrBuilder getArgsOrBuilder(final int index) {
            return this.args_.get(index);
        }
        
        public boolean hasUpsert() {
            return (this.bitField0_ & 0x4) == 0x4;
        }
        
        public boolean getUpsert() {
            return this.upsert_;
        }
        
        public final boolean isInitialized() {
            final byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (!this.hasCollection()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            if (!this.getCollection().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            for (int i = 0; i < this.getProjectionCount(); ++i) {
                if (!this.getProjection(i).isInitialized()) {
                    this.memoizedIsInitialized = 0;
                    return false;
                }
            }
            for (int i = 0; i < this.getRowCount(); ++i) {
                if (!this.getRow(i).isInitialized()) {
                    this.memoizedIsInitialized = 0;
                    return false;
                }
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
            if ((this.bitField0_ & 0x1) == 0x1) {
                output.writeMessage(1, (MessageLite)this.getCollection());
            }
            if ((this.bitField0_ & 0x2) == 0x2) {
                output.writeEnum(2, this.dataModel_);
            }
            for (int i = 0; i < this.projection_.size(); ++i) {
                output.writeMessage(3, (MessageLite)this.projection_.get(i));
            }
            for (int i = 0; i < this.row_.size(); ++i) {
                output.writeMessage(4, (MessageLite)this.row_.get(i));
            }
            for (int i = 0; i < this.args_.size(); ++i) {
                output.writeMessage(5, (MessageLite)this.args_.get(i));
            }
            if ((this.bitField0_ & 0x4) == 0x4) {
                output.writeBool(6, this.upsert_);
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
                size += CodedOutputStream.computeMessageSize(1, (MessageLite)this.getCollection());
            }
            if ((this.bitField0_ & 0x2) == 0x2) {
                size += CodedOutputStream.computeEnumSize(2, this.dataModel_);
            }
            for (int i = 0; i < this.projection_.size(); ++i) {
                size += CodedOutputStream.computeMessageSize(3, (MessageLite)this.projection_.get(i));
            }
            for (int i = 0; i < this.row_.size(); ++i) {
                size += CodedOutputStream.computeMessageSize(4, (MessageLite)this.row_.get(i));
            }
            for (int i = 0; i < this.args_.size(); ++i) {
                size += CodedOutputStream.computeMessageSize(5, (MessageLite)this.args_.get(i));
            }
            if ((this.bitField0_ & 0x4) == 0x4) {
                size += CodedOutputStream.computeBoolSize(6, this.upsert_);
            }
            size += this.unknownFields.getSerializedSize();
            return this.memoizedSize = size;
        }
        
        public boolean equals(final Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Insert)) {
                return super.equals(obj);
            }
            final Insert other = (Insert)obj;
            boolean result = true;
            result = (result && this.hasCollection() == other.hasCollection());
            if (this.hasCollection()) {
                result = (result && this.getCollection().equals(other.getCollection()));
            }
            result = (result && this.hasDataModel() == other.hasDataModel());
            if (this.hasDataModel()) {
                result = (result && this.dataModel_ == other.dataModel_);
            }
            result = (result && this.getProjectionList().equals(other.getProjectionList()));
            result = (result && this.getRowList().equals(other.getRowList()));
            result = (result && this.getArgsList().equals(other.getArgsList()));
            result = (result && this.hasUpsert() == other.hasUpsert());
            if (this.hasUpsert()) {
                result = (result && this.getUpsert() == other.getUpsert());
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
            if (this.hasCollection()) {
                hash = 37 * hash + 1;
                hash = 53 * hash + this.getCollection().hashCode();
            }
            if (this.hasDataModel()) {
                hash = 37 * hash + 2;
                hash = 53 * hash + this.dataModel_;
            }
            if (this.getProjectionCount() > 0) {
                hash = 37 * hash + 3;
                hash = 53 * hash + this.getProjectionList().hashCode();
            }
            if (this.getRowCount() > 0) {
                hash = 37 * hash + 4;
                hash = 53 * hash + this.getRowList().hashCode();
            }
            if (this.getArgsCount() > 0) {
                hash = 37 * hash + 5;
                hash = 53 * hash + this.getArgsList().hashCode();
            }
            if (this.hasUpsert()) {
                hash = 37 * hash + 6;
                hash = 53 * hash + Internal.hashBoolean(this.getUpsert());
            }
            hash = 29 * hash + this.unknownFields.hashCode();
            return this.memoizedHashCode = hash;
        }
        
        public static Insert parseFrom(final ByteBuffer data) throws InvalidProtocolBufferException {
            return (Insert)Insert.PARSER.parseFrom(data);
        }
        
        public static Insert parseFrom(final ByteBuffer data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Insert)Insert.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Insert parseFrom(final ByteString data) throws InvalidProtocolBufferException {
            return (Insert)Insert.PARSER.parseFrom(data);
        }
        
        public static Insert parseFrom(final ByteString data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Insert)Insert.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Insert parseFrom(final byte[] data) throws InvalidProtocolBufferException {
            return (Insert)Insert.PARSER.parseFrom(data);
        }
        
        public static Insert parseFrom(final byte[] data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Insert)Insert.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Insert parseFrom(final InputStream input) throws IOException {
            return (Insert)GeneratedMessageV3.parseWithIOException((Parser)Insert.PARSER, input);
        }
        
        public static Insert parseFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Insert)GeneratedMessageV3.parseWithIOException((Parser)Insert.PARSER, input, extensionRegistry);
        }
        
        public static Insert parseDelimitedFrom(final InputStream input) throws IOException {
            return (Insert)GeneratedMessageV3.parseDelimitedWithIOException((Parser)Insert.PARSER, input);
        }
        
        public static Insert parseDelimitedFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Insert)GeneratedMessageV3.parseDelimitedWithIOException((Parser)Insert.PARSER, input, extensionRegistry);
        }
        
        public static Insert parseFrom(final CodedInputStream input) throws IOException {
            return (Insert)GeneratedMessageV3.parseWithIOException((Parser)Insert.PARSER, input);
        }
        
        public static Insert parseFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Insert)GeneratedMessageV3.parseWithIOException((Parser)Insert.PARSER, input, extensionRegistry);
        }
        
        public Builder newBuilderForType() {
            return newBuilder();
        }
        
        public static Builder newBuilder() {
            return Insert.DEFAULT_INSTANCE.toBuilder();
        }
        
        public static Builder newBuilder(final Insert prototype) {
            return Insert.DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }
        
        public Builder toBuilder() {
            return (this == Insert.DEFAULT_INSTANCE) ? new Builder() : new Builder().mergeFrom(this);
        }
        
        protected Builder newBuilderForType(final GeneratedMessageV3.BuilderParent parent) {
            final Builder builder = new Builder(parent);
            return builder;
        }
        
        public static Insert getDefaultInstance() {
            return Insert.DEFAULT_INSTANCE;
        }
        
        public static Parser<Insert> parser() {
            return Insert.PARSER;
        }
        
        public Parser<Insert> getParserForType() {
            return Insert.PARSER;
        }
        
        public Insert getDefaultInstanceForType() {
            return Insert.DEFAULT_INSTANCE;
        }
        
        static {
            DEFAULT_INSTANCE = new Insert();
            PARSER = (Parser)new AbstractParser<Insert>() {
                public Insert parsePartialFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new Insert(input, extensionRegistry);
                }
            };
        }
        
        public static final class TypedRow extends GeneratedMessageV3 implements TypedRowOrBuilder
        {
            private static final long serialVersionUID = 0L;
            public static final int FIELD_FIELD_NUMBER = 1;
            private List<MysqlxExpr.Expr> field_;
            private byte memoizedIsInitialized;
            private static final TypedRow DEFAULT_INSTANCE;
            @Deprecated
            public static final Parser<TypedRow> PARSER;
            
            private TypedRow(final GeneratedMessageV3.Builder<?> builder) {
                super((GeneratedMessageV3.Builder)builder);
                this.memoizedIsInitialized = -1;
            }
            
            private TypedRow() {
                this.memoizedIsInitialized = -1;
                this.field_ = Collections.emptyList();
            }
            
            public final UnknownFieldSet getUnknownFields() {
                return this.unknownFields;
            }
            
            private TypedRow(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                                    this.field_ = new ArrayList<MysqlxExpr.Expr>();
                                    mutable_bitField0_ |= 0x1;
                                }
                                this.field_.add((MysqlxExpr.Expr)input.readMessage((Parser)MysqlxExpr.Expr.PARSER, extensionRegistry));
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
                        this.field_ = Collections.unmodifiableList((List<? extends MysqlxExpr.Expr>)this.field_);
                    }
                    this.unknownFields = unknownFields.build();
                    this.makeExtensionsImmutable();
                }
            }
            
            public static final Descriptors.Descriptor getDescriptor() {
                return MysqlxCrud.internal_static_Mysqlx_Crud_Insert_TypedRow_descriptor;
            }
            
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return MysqlxCrud.internal_static_Mysqlx_Crud_Insert_TypedRow_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)TypedRow.class, (Class)Builder.class);
            }
            
            public List<MysqlxExpr.Expr> getFieldList() {
                return this.field_;
            }
            
            public List<? extends MysqlxExpr.ExprOrBuilder> getFieldOrBuilderList() {
                return this.field_;
            }
            
            public int getFieldCount() {
                return this.field_.size();
            }
            
            public MysqlxExpr.Expr getField(final int index) {
                return this.field_.get(index);
            }
            
            public MysqlxExpr.ExprOrBuilder getFieldOrBuilder(final int index) {
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
                for (int i = 0; i < this.getFieldCount(); ++i) {
                    if (!this.getField(i).isInitialized()) {
                        this.memoizedIsInitialized = 0;
                        return false;
                    }
                }
                this.memoizedIsInitialized = 1;
                return true;
            }
            
            public void writeTo(final CodedOutputStream output) throws IOException {
                for (int i = 0; i < this.field_.size(); ++i) {
                    output.writeMessage(1, (MessageLite)this.field_.get(i));
                }
                this.unknownFields.writeTo(output);
            }
            
            public int getSerializedSize() {
                int size = this.memoizedSize;
                if (size != -1) {
                    return size;
                }
                size = 0;
                for (int i = 0; i < this.field_.size(); ++i) {
                    size += CodedOutputStream.computeMessageSize(1, (MessageLite)this.field_.get(i));
                }
                size += this.unknownFields.getSerializedSize();
                return this.memoizedSize = size;
            }
            
            public boolean equals(final Object obj) {
                if (obj == this) {
                    return true;
                }
                if (!(obj instanceof TypedRow)) {
                    return super.equals(obj);
                }
                final TypedRow other = (TypedRow)obj;
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
            
            public static TypedRow parseFrom(final ByteBuffer data) throws InvalidProtocolBufferException {
                return (TypedRow)TypedRow.PARSER.parseFrom(data);
            }
            
            public static TypedRow parseFrom(final ByteBuffer data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return (TypedRow)TypedRow.PARSER.parseFrom(data, extensionRegistry);
            }
            
            public static TypedRow parseFrom(final ByteString data) throws InvalidProtocolBufferException {
                return (TypedRow)TypedRow.PARSER.parseFrom(data);
            }
            
            public static TypedRow parseFrom(final ByteString data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return (TypedRow)TypedRow.PARSER.parseFrom(data, extensionRegistry);
            }
            
            public static TypedRow parseFrom(final byte[] data) throws InvalidProtocolBufferException {
                return (TypedRow)TypedRow.PARSER.parseFrom(data);
            }
            
            public static TypedRow parseFrom(final byte[] data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return (TypedRow)TypedRow.PARSER.parseFrom(data, extensionRegistry);
            }
            
            public static TypedRow parseFrom(final InputStream input) throws IOException {
                return (TypedRow)GeneratedMessageV3.parseWithIOException((Parser)TypedRow.PARSER, input);
            }
            
            public static TypedRow parseFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
                return (TypedRow)GeneratedMessageV3.parseWithIOException((Parser)TypedRow.PARSER, input, extensionRegistry);
            }
            
            public static TypedRow parseDelimitedFrom(final InputStream input) throws IOException {
                return (TypedRow)GeneratedMessageV3.parseDelimitedWithIOException((Parser)TypedRow.PARSER, input);
            }
            
            public static TypedRow parseDelimitedFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
                return (TypedRow)GeneratedMessageV3.parseDelimitedWithIOException((Parser)TypedRow.PARSER, input, extensionRegistry);
            }
            
            public static TypedRow parseFrom(final CodedInputStream input) throws IOException {
                return (TypedRow)GeneratedMessageV3.parseWithIOException((Parser)TypedRow.PARSER, input);
            }
            
            public static TypedRow parseFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
                return (TypedRow)GeneratedMessageV3.parseWithIOException((Parser)TypedRow.PARSER, input, extensionRegistry);
            }
            
            public Builder newBuilderForType() {
                return newBuilder();
            }
            
            public static Builder newBuilder() {
                return TypedRow.DEFAULT_INSTANCE.toBuilder();
            }
            
            public static Builder newBuilder(final TypedRow prototype) {
                return TypedRow.DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
            }
            
            public Builder toBuilder() {
                return (this == TypedRow.DEFAULT_INSTANCE) ? new Builder() : new Builder().mergeFrom(this);
            }
            
            protected Builder newBuilderForType(final GeneratedMessageV3.BuilderParent parent) {
                final Builder builder = new Builder(parent);
                return builder;
            }
            
            public static TypedRow getDefaultInstance() {
                return TypedRow.DEFAULT_INSTANCE;
            }
            
            public static Parser<TypedRow> parser() {
                return TypedRow.PARSER;
            }
            
            public Parser<TypedRow> getParserForType() {
                return TypedRow.PARSER;
            }
            
            public TypedRow getDefaultInstanceForType() {
                return TypedRow.DEFAULT_INSTANCE;
            }
            
            static {
                DEFAULT_INSTANCE = new TypedRow();
                PARSER = (Parser)new AbstractParser<TypedRow>() {
                    public TypedRow parsePartialFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                        return new TypedRow(input, extensionRegistry);
                    }
                };
            }
            
            public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements TypedRowOrBuilder
            {
                private int bitField0_;
                private List<MysqlxExpr.Expr> field_;
                private RepeatedFieldBuilderV3<MysqlxExpr.Expr, MysqlxExpr.Expr.Builder, MysqlxExpr.ExprOrBuilder> fieldBuilder_;
                
                public static final Descriptors.Descriptor getDescriptor() {
                    return MysqlxCrud.internal_static_Mysqlx_Crud_Insert_TypedRow_descriptor;
                }
                
                protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                    return MysqlxCrud.internal_static_Mysqlx_Crud_Insert_TypedRow_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)TypedRow.class, (Class)Builder.class);
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
                    if (TypedRow.alwaysUseFieldBuilders) {
                        this.getFieldFieldBuilder();
                    }
                }
                
                public Builder clear() {
                    super.clear();
                    if (this.fieldBuilder_ == null) {
                        this.field_ = Collections.emptyList();
                        this.bitField0_ &= 0xFFFFFFFE;
                    }
                    else {
                        this.fieldBuilder_.clear();
                    }
                    return this;
                }
                
                public Descriptors.Descriptor getDescriptorForType() {
                    return MysqlxCrud.internal_static_Mysqlx_Crud_Insert_TypedRow_descriptor;
                }
                
                public TypedRow getDefaultInstanceForType() {
                    return TypedRow.getDefaultInstance();
                }
                
                public TypedRow build() {
                    final TypedRow result = this.buildPartial();
                    if (!result.isInitialized()) {
                        throw newUninitializedMessageException((Message)result);
                    }
                    return result;
                }
                
                public TypedRow buildPartial() {
                    final TypedRow result = new TypedRow((GeneratedMessageV3.Builder)this);
                    final int from_bitField0_ = this.bitField0_;
                    if (this.fieldBuilder_ == null) {
                        if ((this.bitField0_ & 0x1) == 0x1) {
                            this.field_ = Collections.unmodifiableList((List<? extends MysqlxExpr.Expr>)this.field_);
                            this.bitField0_ &= 0xFFFFFFFE;
                        }
                        result.field_ = this.field_;
                    }
                    else {
                        result.field_ = (List<MysqlxExpr.Expr>)this.fieldBuilder_.build();
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
                    if (other instanceof TypedRow) {
                        return this.mergeFrom((TypedRow)other);
                    }
                    super.mergeFrom(other);
                    return this;
                }
                
                public Builder mergeFrom(final TypedRow other) {
                    if (other == TypedRow.getDefaultInstance()) {
                        return this;
                    }
                    if (this.fieldBuilder_ == null) {
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
                    }
                    else if (!other.field_.isEmpty()) {
                        if (this.fieldBuilder_.isEmpty()) {
                            this.fieldBuilder_.dispose();
                            this.fieldBuilder_ = null;
                            this.field_ = other.field_;
                            this.bitField0_ &= 0xFFFFFFFE;
                            this.fieldBuilder_ = (TypedRow.alwaysUseFieldBuilders ? this.getFieldFieldBuilder() : null);
                        }
                        else {
                            this.fieldBuilder_.addAllMessages((Iterable)other.field_);
                        }
                    }
                    this.mergeUnknownFields(other.unknownFields);
                    this.onChanged();
                    return this;
                }
                
                public final boolean isInitialized() {
                    for (int i = 0; i < this.getFieldCount(); ++i) {
                        if (!this.getField(i).isInitialized()) {
                            return false;
                        }
                    }
                    return true;
                }
                
                public Builder mergeFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
                    TypedRow parsedMessage = null;
                    try {
                        parsedMessage = (TypedRow)TypedRow.PARSER.parsePartialFrom(input, extensionRegistry);
                    }
                    catch (InvalidProtocolBufferException e) {
                        parsedMessage = (TypedRow)e.getUnfinishedMessage();
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
                        this.field_ = new ArrayList<MysqlxExpr.Expr>(this.field_);
                        this.bitField0_ |= 0x1;
                    }
                }
                
                public List<MysqlxExpr.Expr> getFieldList() {
                    if (this.fieldBuilder_ == null) {
                        return Collections.unmodifiableList((List<? extends MysqlxExpr.Expr>)this.field_);
                    }
                    return (List<MysqlxExpr.Expr>)this.fieldBuilder_.getMessageList();
                }
                
                public int getFieldCount() {
                    if (this.fieldBuilder_ == null) {
                        return this.field_.size();
                    }
                    return this.fieldBuilder_.getCount();
                }
                
                public MysqlxExpr.Expr getField(final int index) {
                    if (this.fieldBuilder_ == null) {
                        return this.field_.get(index);
                    }
                    return (MysqlxExpr.Expr)this.fieldBuilder_.getMessage(index);
                }
                
                public Builder setField(final int index, final MysqlxExpr.Expr value) {
                    if (this.fieldBuilder_ == null) {
                        if (value == null) {
                            throw new NullPointerException();
                        }
                        this.ensureFieldIsMutable();
                        this.field_.set(index, value);
                        this.onChanged();
                    }
                    else {
                        this.fieldBuilder_.setMessage(index, (AbstractMessage)value);
                    }
                    return this;
                }
                
                public Builder setField(final int index, final MysqlxExpr.Expr.Builder builderForValue) {
                    if (this.fieldBuilder_ == null) {
                        this.ensureFieldIsMutable();
                        this.field_.set(index, builderForValue.build());
                        this.onChanged();
                    }
                    else {
                        this.fieldBuilder_.setMessage(index, (AbstractMessage)builderForValue.build());
                    }
                    return this;
                }
                
                public Builder addField(final MysqlxExpr.Expr value) {
                    if (this.fieldBuilder_ == null) {
                        if (value == null) {
                            throw new NullPointerException();
                        }
                        this.ensureFieldIsMutable();
                        this.field_.add(value);
                        this.onChanged();
                    }
                    else {
                        this.fieldBuilder_.addMessage((AbstractMessage)value);
                    }
                    return this;
                }
                
                public Builder addField(final int index, final MysqlxExpr.Expr value) {
                    if (this.fieldBuilder_ == null) {
                        if (value == null) {
                            throw new NullPointerException();
                        }
                        this.ensureFieldIsMutable();
                        this.field_.add(index, value);
                        this.onChanged();
                    }
                    else {
                        this.fieldBuilder_.addMessage(index, (AbstractMessage)value);
                    }
                    return this;
                }
                
                public Builder addField(final MysqlxExpr.Expr.Builder builderForValue) {
                    if (this.fieldBuilder_ == null) {
                        this.ensureFieldIsMutable();
                        this.field_.add(builderForValue.build());
                        this.onChanged();
                    }
                    else {
                        this.fieldBuilder_.addMessage((AbstractMessage)builderForValue.build());
                    }
                    return this;
                }
                
                public Builder addField(final int index, final MysqlxExpr.Expr.Builder builderForValue) {
                    if (this.fieldBuilder_ == null) {
                        this.ensureFieldIsMutable();
                        this.field_.add(index, builderForValue.build());
                        this.onChanged();
                    }
                    else {
                        this.fieldBuilder_.addMessage(index, (AbstractMessage)builderForValue.build());
                    }
                    return this;
                }
                
                public Builder addAllField(final Iterable<? extends MysqlxExpr.Expr> values) {
                    if (this.fieldBuilder_ == null) {
                        this.ensureFieldIsMutable();
                        AbstractMessageLite.Builder.addAll((Iterable)values, (List)this.field_);
                        this.onChanged();
                    }
                    else {
                        this.fieldBuilder_.addAllMessages((Iterable)values);
                    }
                    return this;
                }
                
                public Builder clearField() {
                    if (this.fieldBuilder_ == null) {
                        this.field_ = Collections.emptyList();
                        this.bitField0_ &= 0xFFFFFFFE;
                        this.onChanged();
                    }
                    else {
                        this.fieldBuilder_.clear();
                    }
                    return this;
                }
                
                public Builder removeField(final int index) {
                    if (this.fieldBuilder_ == null) {
                        this.ensureFieldIsMutable();
                        this.field_.remove(index);
                        this.onChanged();
                    }
                    else {
                        this.fieldBuilder_.remove(index);
                    }
                    return this;
                }
                
                public MysqlxExpr.Expr.Builder getFieldBuilder(final int index) {
                    return (MysqlxExpr.Expr.Builder)this.getFieldFieldBuilder().getBuilder(index);
                }
                
                public MysqlxExpr.ExprOrBuilder getFieldOrBuilder(final int index) {
                    if (this.fieldBuilder_ == null) {
                        return this.field_.get(index);
                    }
                    return (MysqlxExpr.ExprOrBuilder)this.fieldBuilder_.getMessageOrBuilder(index);
                }
                
                public List<? extends MysqlxExpr.ExprOrBuilder> getFieldOrBuilderList() {
                    if (this.fieldBuilder_ != null) {
                        return (List<? extends MysqlxExpr.ExprOrBuilder>)this.fieldBuilder_.getMessageOrBuilderList();
                    }
                    return Collections.unmodifiableList((List<? extends MysqlxExpr.ExprOrBuilder>)this.field_);
                }
                
                public MysqlxExpr.Expr.Builder addFieldBuilder() {
                    return (MysqlxExpr.Expr.Builder)this.getFieldFieldBuilder().addBuilder((AbstractMessage)MysqlxExpr.Expr.getDefaultInstance());
                }
                
                public MysqlxExpr.Expr.Builder addFieldBuilder(final int index) {
                    return (MysqlxExpr.Expr.Builder)this.getFieldFieldBuilder().addBuilder(index, (AbstractMessage)MysqlxExpr.Expr.getDefaultInstance());
                }
                
                public List<MysqlxExpr.Expr.Builder> getFieldBuilderList() {
                    return (List<MysqlxExpr.Expr.Builder>)this.getFieldFieldBuilder().getBuilderList();
                }
                
                private RepeatedFieldBuilderV3<MysqlxExpr.Expr, MysqlxExpr.Expr.Builder, MysqlxExpr.ExprOrBuilder> getFieldFieldBuilder() {
                    if (this.fieldBuilder_ == null) {
                        this.fieldBuilder_ = (RepeatedFieldBuilderV3<MysqlxExpr.Expr, MysqlxExpr.Expr.Builder, MysqlxExpr.ExprOrBuilder>)new RepeatedFieldBuilderV3((List)this.field_, (this.bitField0_ & 0x1) == 0x1, (AbstractMessage.BuilderParent)this.getParentForChildren(), this.isClean());
                        this.field_ = null;
                    }
                    return this.fieldBuilder_;
                }
                
                public final Builder setUnknownFields(final UnknownFieldSet unknownFields) {
                    return (Builder)super.setUnknownFields(unknownFields);
                }
                
                public final Builder mergeUnknownFields(final UnknownFieldSet unknownFields) {
                    return (Builder)super.mergeUnknownFields(unknownFields);
                }
            }
        }
        
        public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements InsertOrBuilder
        {
            private int bitField0_;
            private Collection collection_;
            private SingleFieldBuilderV3<Collection, Collection.Builder, CollectionOrBuilder> collectionBuilder_;
            private int dataModel_;
            private List<Column> projection_;
            private RepeatedFieldBuilderV3<Column, Column.Builder, ColumnOrBuilder> projectionBuilder_;
            private List<TypedRow> row_;
            private RepeatedFieldBuilderV3<TypedRow, TypedRow.Builder, TypedRowOrBuilder> rowBuilder_;
            private List<MysqlxDatatypes.Scalar> args_;
            private RepeatedFieldBuilderV3<MysqlxDatatypes.Scalar, MysqlxDatatypes.Scalar.Builder, MysqlxDatatypes.ScalarOrBuilder> argsBuilder_;
            private boolean upsert_;
            
            public static final Descriptors.Descriptor getDescriptor() {
                return MysqlxCrud.internal_static_Mysqlx_Crud_Insert_descriptor;
            }
            
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return MysqlxCrud.internal_static_Mysqlx_Crud_Insert_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)Insert.class, (Class)Builder.class);
            }
            
            private Builder() {
                this.collection_ = null;
                this.dataModel_ = 1;
                this.projection_ = Collections.emptyList();
                this.row_ = Collections.emptyList();
                this.args_ = Collections.emptyList();
                this.maybeForceBuilderInitialization();
            }
            
            private Builder(final GeneratedMessageV3.BuilderParent parent) {
                super(parent);
                this.collection_ = null;
                this.dataModel_ = 1;
                this.projection_ = Collections.emptyList();
                this.row_ = Collections.emptyList();
                this.args_ = Collections.emptyList();
                this.maybeForceBuilderInitialization();
            }
            
            private void maybeForceBuilderInitialization() {
                if (Insert.alwaysUseFieldBuilders) {
                    this.getCollectionFieldBuilder();
                    this.getProjectionFieldBuilder();
                    this.getRowFieldBuilder();
                    this.getArgsFieldBuilder();
                }
            }
            
            public Builder clear() {
                super.clear();
                if (this.collectionBuilder_ == null) {
                    this.collection_ = null;
                }
                else {
                    this.collectionBuilder_.clear();
                }
                this.bitField0_ &= 0xFFFFFFFE;
                this.dataModel_ = 1;
                this.bitField0_ &= 0xFFFFFFFD;
                if (this.projectionBuilder_ == null) {
                    this.projection_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFFB;
                }
                else {
                    this.projectionBuilder_.clear();
                }
                if (this.rowBuilder_ == null) {
                    this.row_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFF7;
                }
                else {
                    this.rowBuilder_.clear();
                }
                if (this.argsBuilder_ == null) {
                    this.args_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFEF;
                }
                else {
                    this.argsBuilder_.clear();
                }
                this.upsert_ = false;
                this.bitField0_ &= 0xFFFFFFDF;
                return this;
            }
            
            public Descriptors.Descriptor getDescriptorForType() {
                return MysqlxCrud.internal_static_Mysqlx_Crud_Insert_descriptor;
            }
            
            public Insert getDefaultInstanceForType() {
                return Insert.getDefaultInstance();
            }
            
            public Insert build() {
                final Insert result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw newUninitializedMessageException((Message)result);
                }
                return result;
            }
            
            public Insert buildPartial() {
                final Insert result = new Insert((GeneratedMessageV3.Builder)this);
                final int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 0x1) == 0x1) {
                    to_bitField0_ |= 0x1;
                }
                if (this.collectionBuilder_ == null) {
                    result.collection_ = this.collection_;
                }
                else {
                    result.collection_ = (Collection)this.collectionBuilder_.build();
                }
                if ((from_bitField0_ & 0x2) == 0x2) {
                    to_bitField0_ |= 0x2;
                }
                result.dataModel_ = this.dataModel_;
                if (this.projectionBuilder_ == null) {
                    if ((this.bitField0_ & 0x4) == 0x4) {
                        this.projection_ = Collections.unmodifiableList((List<? extends Column>)this.projection_);
                        this.bitField0_ &= 0xFFFFFFFB;
                    }
                    result.projection_ = this.projection_;
                }
                else {
                    result.projection_ = (List<Column>)this.projectionBuilder_.build();
                }
                if (this.rowBuilder_ == null) {
                    if ((this.bitField0_ & 0x8) == 0x8) {
                        this.row_ = Collections.unmodifiableList((List<? extends TypedRow>)this.row_);
                        this.bitField0_ &= 0xFFFFFFF7;
                    }
                    result.row_ = this.row_;
                }
                else {
                    result.row_ = (List<TypedRow>)this.rowBuilder_.build();
                }
                if (this.argsBuilder_ == null) {
                    if ((this.bitField0_ & 0x10) == 0x10) {
                        this.args_ = Collections.unmodifiableList((List<? extends MysqlxDatatypes.Scalar>)this.args_);
                        this.bitField0_ &= 0xFFFFFFEF;
                    }
                    result.args_ = this.args_;
                }
                else {
                    result.args_ = (List<MysqlxDatatypes.Scalar>)this.argsBuilder_.build();
                }
                if ((from_bitField0_ & 0x20) == 0x20) {
                    to_bitField0_ |= 0x4;
                }
                result.upsert_ = this.upsert_;
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
                if (other instanceof Insert) {
                    return this.mergeFrom((Insert)other);
                }
                super.mergeFrom(other);
                return this;
            }
            
            public Builder mergeFrom(final Insert other) {
                if (other == Insert.getDefaultInstance()) {
                    return this;
                }
                if (other.hasCollection()) {
                    this.mergeCollection(other.getCollection());
                }
                if (other.hasDataModel()) {
                    this.setDataModel(other.getDataModel());
                }
                if (this.projectionBuilder_ == null) {
                    if (!other.projection_.isEmpty()) {
                        if (this.projection_.isEmpty()) {
                            this.projection_ = other.projection_;
                            this.bitField0_ &= 0xFFFFFFFB;
                        }
                        else {
                            this.ensureProjectionIsMutable();
                            this.projection_.addAll(other.projection_);
                        }
                        this.onChanged();
                    }
                }
                else if (!other.projection_.isEmpty()) {
                    if (this.projectionBuilder_.isEmpty()) {
                        this.projectionBuilder_.dispose();
                        this.projectionBuilder_ = null;
                        this.projection_ = other.projection_;
                        this.bitField0_ &= 0xFFFFFFFB;
                        this.projectionBuilder_ = (Insert.alwaysUseFieldBuilders ? this.getProjectionFieldBuilder() : null);
                    }
                    else {
                        this.projectionBuilder_.addAllMessages((Iterable)other.projection_);
                    }
                }
                if (this.rowBuilder_ == null) {
                    if (!other.row_.isEmpty()) {
                        if (this.row_.isEmpty()) {
                            this.row_ = other.row_;
                            this.bitField0_ &= 0xFFFFFFF7;
                        }
                        else {
                            this.ensureRowIsMutable();
                            this.row_.addAll(other.row_);
                        }
                        this.onChanged();
                    }
                }
                else if (!other.row_.isEmpty()) {
                    if (this.rowBuilder_.isEmpty()) {
                        this.rowBuilder_.dispose();
                        this.rowBuilder_ = null;
                        this.row_ = other.row_;
                        this.bitField0_ &= 0xFFFFFFF7;
                        this.rowBuilder_ = (Insert.alwaysUseFieldBuilders ? this.getRowFieldBuilder() : null);
                    }
                    else {
                        this.rowBuilder_.addAllMessages((Iterable)other.row_);
                    }
                }
                if (this.argsBuilder_ == null) {
                    if (!other.args_.isEmpty()) {
                        if (this.args_.isEmpty()) {
                            this.args_ = other.args_;
                            this.bitField0_ &= 0xFFFFFFEF;
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
                        this.bitField0_ &= 0xFFFFFFEF;
                        this.argsBuilder_ = (Insert.alwaysUseFieldBuilders ? this.getArgsFieldBuilder() : null);
                    }
                    else {
                        this.argsBuilder_.addAllMessages((Iterable)other.args_);
                    }
                }
                if (other.hasUpsert()) {
                    this.setUpsert(other.getUpsert());
                }
                this.mergeUnknownFields(other.unknownFields);
                this.onChanged();
                return this;
            }
            
            public final boolean isInitialized() {
                if (!this.hasCollection()) {
                    return false;
                }
                if (!this.getCollection().isInitialized()) {
                    return false;
                }
                for (int i = 0; i < this.getProjectionCount(); ++i) {
                    if (!this.getProjection(i).isInitialized()) {
                        return false;
                    }
                }
                for (int i = 0; i < this.getRowCount(); ++i) {
                    if (!this.getRow(i).isInitialized()) {
                        return false;
                    }
                }
                for (int i = 0; i < this.getArgsCount(); ++i) {
                    if (!this.getArgs(i).isInitialized()) {
                        return false;
                    }
                }
                return true;
            }
            
            public Builder mergeFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
                Insert parsedMessage = null;
                try {
                    parsedMessage = (Insert)Insert.PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (Insert)e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                }
                finally {
                    if (parsedMessage != null) {
                        this.mergeFrom(parsedMessage);
                    }
                }
                return this;
            }
            
            public boolean hasCollection() {
                return (this.bitField0_ & 0x1) == 0x1;
            }
            
            public Collection getCollection() {
                if (this.collectionBuilder_ == null) {
                    return (this.collection_ == null) ? Collection.getDefaultInstance() : this.collection_;
                }
                return (Collection)this.collectionBuilder_.getMessage();
            }
            
            public Builder setCollection(final Collection value) {
                if (this.collectionBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.collection_ = value;
                    this.onChanged();
                }
                else {
                    this.collectionBuilder_.setMessage((AbstractMessage)value);
                }
                this.bitField0_ |= 0x1;
                return this;
            }
            
            public Builder setCollection(final Collection.Builder builderForValue) {
                if (this.collectionBuilder_ == null) {
                    this.collection_ = builderForValue.build();
                    this.onChanged();
                }
                else {
                    this.collectionBuilder_.setMessage((AbstractMessage)builderForValue.build());
                }
                this.bitField0_ |= 0x1;
                return this;
            }
            
            public Builder mergeCollection(final Collection value) {
                if (this.collectionBuilder_ == null) {
                    if ((this.bitField0_ & 0x1) == 0x1 && this.collection_ != null && this.collection_ != Collection.getDefaultInstance()) {
                        this.collection_ = Collection.newBuilder(this.collection_).mergeFrom(value).buildPartial();
                    }
                    else {
                        this.collection_ = value;
                    }
                    this.onChanged();
                }
                else {
                    this.collectionBuilder_.mergeFrom((AbstractMessage)value);
                }
                this.bitField0_ |= 0x1;
                return this;
            }
            
            public Builder clearCollection() {
                if (this.collectionBuilder_ == null) {
                    this.collection_ = null;
                    this.onChanged();
                }
                else {
                    this.collectionBuilder_.clear();
                }
                this.bitField0_ &= 0xFFFFFFFE;
                return this;
            }
            
            public Collection.Builder getCollectionBuilder() {
                this.bitField0_ |= 0x1;
                this.onChanged();
                return (Collection.Builder)this.getCollectionFieldBuilder().getBuilder();
            }
            
            public CollectionOrBuilder getCollectionOrBuilder() {
                if (this.collectionBuilder_ != null) {
                    return (CollectionOrBuilder)this.collectionBuilder_.getMessageOrBuilder();
                }
                return (this.collection_ == null) ? Collection.getDefaultInstance() : this.collection_;
            }
            
            private SingleFieldBuilderV3<Collection, Collection.Builder, CollectionOrBuilder> getCollectionFieldBuilder() {
                if (this.collectionBuilder_ == null) {
                    this.collectionBuilder_ = (SingleFieldBuilderV3<Collection, Collection.Builder, CollectionOrBuilder>)new SingleFieldBuilderV3((AbstractMessage)this.getCollection(), (AbstractMessage.BuilderParent)this.getParentForChildren(), this.isClean());
                    this.collection_ = null;
                }
                return this.collectionBuilder_;
            }
            
            public boolean hasDataModel() {
                return (this.bitField0_ & 0x2) == 0x2;
            }
            
            public DataModel getDataModel() {
                final DataModel result = DataModel.valueOf(this.dataModel_);
                return (result == null) ? DataModel.DOCUMENT : result;
            }
            
            public Builder setDataModel(final DataModel value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 0x2;
                this.dataModel_ = value.getNumber();
                this.onChanged();
                return this;
            }
            
            public Builder clearDataModel() {
                this.bitField0_ &= 0xFFFFFFFD;
                this.dataModel_ = 1;
                this.onChanged();
                return this;
            }
            
            private void ensureProjectionIsMutable() {
                if ((this.bitField0_ & 0x4) != 0x4) {
                    this.projection_ = new ArrayList<Column>(this.projection_);
                    this.bitField0_ |= 0x4;
                }
            }
            
            public List<Column> getProjectionList() {
                if (this.projectionBuilder_ == null) {
                    return Collections.unmodifiableList((List<? extends Column>)this.projection_);
                }
                return (List<Column>)this.projectionBuilder_.getMessageList();
            }
            
            public int getProjectionCount() {
                if (this.projectionBuilder_ == null) {
                    return this.projection_.size();
                }
                return this.projectionBuilder_.getCount();
            }
            
            public Column getProjection(final int index) {
                if (this.projectionBuilder_ == null) {
                    return this.projection_.get(index);
                }
                return (Column)this.projectionBuilder_.getMessage(index);
            }
            
            public Builder setProjection(final int index, final Column value) {
                if (this.projectionBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureProjectionIsMutable();
                    this.projection_.set(index, value);
                    this.onChanged();
                }
                else {
                    this.projectionBuilder_.setMessage(index, (AbstractMessage)value);
                }
                return this;
            }
            
            public Builder setProjection(final int index, final Column.Builder builderForValue) {
                if (this.projectionBuilder_ == null) {
                    this.ensureProjectionIsMutable();
                    this.projection_.set(index, builderForValue.build());
                    this.onChanged();
                }
                else {
                    this.projectionBuilder_.setMessage(index, (AbstractMessage)builderForValue.build());
                }
                return this;
            }
            
            public Builder addProjection(final Column value) {
                if (this.projectionBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureProjectionIsMutable();
                    this.projection_.add(value);
                    this.onChanged();
                }
                else {
                    this.projectionBuilder_.addMessage((AbstractMessage)value);
                }
                return this;
            }
            
            public Builder addProjection(final int index, final Column value) {
                if (this.projectionBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureProjectionIsMutable();
                    this.projection_.add(index, value);
                    this.onChanged();
                }
                else {
                    this.projectionBuilder_.addMessage(index, (AbstractMessage)value);
                }
                return this;
            }
            
            public Builder addProjection(final Column.Builder builderForValue) {
                if (this.projectionBuilder_ == null) {
                    this.ensureProjectionIsMutable();
                    this.projection_.add(builderForValue.build());
                    this.onChanged();
                }
                else {
                    this.projectionBuilder_.addMessage((AbstractMessage)builderForValue.build());
                }
                return this;
            }
            
            public Builder addProjection(final int index, final Column.Builder builderForValue) {
                if (this.projectionBuilder_ == null) {
                    this.ensureProjectionIsMutable();
                    this.projection_.add(index, builderForValue.build());
                    this.onChanged();
                }
                else {
                    this.projectionBuilder_.addMessage(index, (AbstractMessage)builderForValue.build());
                }
                return this;
            }
            
            public Builder addAllProjection(final Iterable<? extends Column> values) {
                if (this.projectionBuilder_ == null) {
                    this.ensureProjectionIsMutable();
                    AbstractMessageLite.Builder.addAll((Iterable)values, (List)this.projection_);
                    this.onChanged();
                }
                else {
                    this.projectionBuilder_.addAllMessages((Iterable)values);
                }
                return this;
            }
            
            public Builder clearProjection() {
                if (this.projectionBuilder_ == null) {
                    this.projection_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFFB;
                    this.onChanged();
                }
                else {
                    this.projectionBuilder_.clear();
                }
                return this;
            }
            
            public Builder removeProjection(final int index) {
                if (this.projectionBuilder_ == null) {
                    this.ensureProjectionIsMutable();
                    this.projection_.remove(index);
                    this.onChanged();
                }
                else {
                    this.projectionBuilder_.remove(index);
                }
                return this;
            }
            
            public Column.Builder getProjectionBuilder(final int index) {
                return (Column.Builder)this.getProjectionFieldBuilder().getBuilder(index);
            }
            
            public ColumnOrBuilder getProjectionOrBuilder(final int index) {
                if (this.projectionBuilder_ == null) {
                    return this.projection_.get(index);
                }
                return (ColumnOrBuilder)this.projectionBuilder_.getMessageOrBuilder(index);
            }
            
            public List<? extends ColumnOrBuilder> getProjectionOrBuilderList() {
                if (this.projectionBuilder_ != null) {
                    return (List<? extends ColumnOrBuilder>)this.projectionBuilder_.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList((List<? extends ColumnOrBuilder>)this.projection_);
            }
            
            public Column.Builder addProjectionBuilder() {
                return (Column.Builder)this.getProjectionFieldBuilder().addBuilder((AbstractMessage)Column.getDefaultInstance());
            }
            
            public Column.Builder addProjectionBuilder(final int index) {
                return (Column.Builder)this.getProjectionFieldBuilder().addBuilder(index, (AbstractMessage)Column.getDefaultInstance());
            }
            
            public List<Column.Builder> getProjectionBuilderList() {
                return (List<Column.Builder>)this.getProjectionFieldBuilder().getBuilderList();
            }
            
            private RepeatedFieldBuilderV3<Column, Column.Builder, ColumnOrBuilder> getProjectionFieldBuilder() {
                if (this.projectionBuilder_ == null) {
                    this.projectionBuilder_ = (RepeatedFieldBuilderV3<Column, Column.Builder, ColumnOrBuilder>)new RepeatedFieldBuilderV3((List)this.projection_, (this.bitField0_ & 0x4) == 0x4, (AbstractMessage.BuilderParent)this.getParentForChildren(), this.isClean());
                    this.projection_ = null;
                }
                return this.projectionBuilder_;
            }
            
            private void ensureRowIsMutable() {
                if ((this.bitField0_ & 0x8) != 0x8) {
                    this.row_ = new ArrayList<TypedRow>(this.row_);
                    this.bitField0_ |= 0x8;
                }
            }
            
            public List<TypedRow> getRowList() {
                if (this.rowBuilder_ == null) {
                    return Collections.unmodifiableList((List<? extends TypedRow>)this.row_);
                }
                return (List<TypedRow>)this.rowBuilder_.getMessageList();
            }
            
            public int getRowCount() {
                if (this.rowBuilder_ == null) {
                    return this.row_.size();
                }
                return this.rowBuilder_.getCount();
            }
            
            public TypedRow getRow(final int index) {
                if (this.rowBuilder_ == null) {
                    return this.row_.get(index);
                }
                return (TypedRow)this.rowBuilder_.getMessage(index);
            }
            
            public Builder setRow(final int index, final TypedRow value) {
                if (this.rowBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureRowIsMutable();
                    this.row_.set(index, value);
                    this.onChanged();
                }
                else {
                    this.rowBuilder_.setMessage(index, (AbstractMessage)value);
                }
                return this;
            }
            
            public Builder setRow(final int index, final TypedRow.Builder builderForValue) {
                if (this.rowBuilder_ == null) {
                    this.ensureRowIsMutable();
                    this.row_.set(index, builderForValue.build());
                    this.onChanged();
                }
                else {
                    this.rowBuilder_.setMessage(index, (AbstractMessage)builderForValue.build());
                }
                return this;
            }
            
            public Builder addRow(final TypedRow value) {
                if (this.rowBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureRowIsMutable();
                    this.row_.add(value);
                    this.onChanged();
                }
                else {
                    this.rowBuilder_.addMessage((AbstractMessage)value);
                }
                return this;
            }
            
            public Builder addRow(final int index, final TypedRow value) {
                if (this.rowBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureRowIsMutable();
                    this.row_.add(index, value);
                    this.onChanged();
                }
                else {
                    this.rowBuilder_.addMessage(index, (AbstractMessage)value);
                }
                return this;
            }
            
            public Builder addRow(final TypedRow.Builder builderForValue) {
                if (this.rowBuilder_ == null) {
                    this.ensureRowIsMutable();
                    this.row_.add(builderForValue.build());
                    this.onChanged();
                }
                else {
                    this.rowBuilder_.addMessage((AbstractMessage)builderForValue.build());
                }
                return this;
            }
            
            public Builder addRow(final int index, final TypedRow.Builder builderForValue) {
                if (this.rowBuilder_ == null) {
                    this.ensureRowIsMutable();
                    this.row_.add(index, builderForValue.build());
                    this.onChanged();
                }
                else {
                    this.rowBuilder_.addMessage(index, (AbstractMessage)builderForValue.build());
                }
                return this;
            }
            
            public Builder addAllRow(final Iterable<? extends TypedRow> values) {
                if (this.rowBuilder_ == null) {
                    this.ensureRowIsMutable();
                    AbstractMessageLite.Builder.addAll((Iterable)values, (List)this.row_);
                    this.onChanged();
                }
                else {
                    this.rowBuilder_.addAllMessages((Iterable)values);
                }
                return this;
            }
            
            public Builder clearRow() {
                if (this.rowBuilder_ == null) {
                    this.row_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFF7;
                    this.onChanged();
                }
                else {
                    this.rowBuilder_.clear();
                }
                return this;
            }
            
            public Builder removeRow(final int index) {
                if (this.rowBuilder_ == null) {
                    this.ensureRowIsMutable();
                    this.row_.remove(index);
                    this.onChanged();
                }
                else {
                    this.rowBuilder_.remove(index);
                }
                return this;
            }
            
            public TypedRow.Builder getRowBuilder(final int index) {
                return (TypedRow.Builder)this.getRowFieldBuilder().getBuilder(index);
            }
            
            public TypedRowOrBuilder getRowOrBuilder(final int index) {
                if (this.rowBuilder_ == null) {
                    return this.row_.get(index);
                }
                return (TypedRowOrBuilder)this.rowBuilder_.getMessageOrBuilder(index);
            }
            
            public List<? extends TypedRowOrBuilder> getRowOrBuilderList() {
                if (this.rowBuilder_ != null) {
                    return (List<? extends TypedRowOrBuilder>)this.rowBuilder_.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList((List<? extends TypedRowOrBuilder>)this.row_);
            }
            
            public TypedRow.Builder addRowBuilder() {
                return (TypedRow.Builder)this.getRowFieldBuilder().addBuilder((AbstractMessage)TypedRow.getDefaultInstance());
            }
            
            public TypedRow.Builder addRowBuilder(final int index) {
                return (TypedRow.Builder)this.getRowFieldBuilder().addBuilder(index, (AbstractMessage)TypedRow.getDefaultInstance());
            }
            
            public List<TypedRow.Builder> getRowBuilderList() {
                return (List<TypedRow.Builder>)this.getRowFieldBuilder().getBuilderList();
            }
            
            private RepeatedFieldBuilderV3<TypedRow, TypedRow.Builder, TypedRowOrBuilder> getRowFieldBuilder() {
                if (this.rowBuilder_ == null) {
                    this.rowBuilder_ = (RepeatedFieldBuilderV3<TypedRow, TypedRow.Builder, TypedRowOrBuilder>)new RepeatedFieldBuilderV3((List)this.row_, (this.bitField0_ & 0x8) == 0x8, (AbstractMessage.BuilderParent)this.getParentForChildren(), this.isClean());
                    this.row_ = null;
                }
                return this.rowBuilder_;
            }
            
            private void ensureArgsIsMutable() {
                if ((this.bitField0_ & 0x10) != 0x10) {
                    this.args_ = new ArrayList<MysqlxDatatypes.Scalar>(this.args_);
                    this.bitField0_ |= 0x10;
                }
            }
            
            public List<MysqlxDatatypes.Scalar> getArgsList() {
                if (this.argsBuilder_ == null) {
                    return Collections.unmodifiableList((List<? extends MysqlxDatatypes.Scalar>)this.args_);
                }
                return (List<MysqlxDatatypes.Scalar>)this.argsBuilder_.getMessageList();
            }
            
            public int getArgsCount() {
                if (this.argsBuilder_ == null) {
                    return this.args_.size();
                }
                return this.argsBuilder_.getCount();
            }
            
            public MysqlxDatatypes.Scalar getArgs(final int index) {
                if (this.argsBuilder_ == null) {
                    return this.args_.get(index);
                }
                return (MysqlxDatatypes.Scalar)this.argsBuilder_.getMessage(index);
            }
            
            public Builder setArgs(final int index, final MysqlxDatatypes.Scalar value) {
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
            
            public Builder setArgs(final int index, final MysqlxDatatypes.Scalar.Builder builderForValue) {
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
            
            public Builder addArgs(final MysqlxDatatypes.Scalar value) {
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
            
            public Builder addArgs(final int index, final MysqlxDatatypes.Scalar value) {
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
            
            public Builder addArgs(final MysqlxDatatypes.Scalar.Builder builderForValue) {
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
            
            public Builder addArgs(final int index, final MysqlxDatatypes.Scalar.Builder builderForValue) {
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
            
            public Builder addAllArgs(final Iterable<? extends MysqlxDatatypes.Scalar> values) {
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
                    this.bitField0_ &= 0xFFFFFFEF;
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
            
            public MysqlxDatatypes.Scalar.Builder getArgsBuilder(final int index) {
                return (MysqlxDatatypes.Scalar.Builder)this.getArgsFieldBuilder().getBuilder(index);
            }
            
            public MysqlxDatatypes.ScalarOrBuilder getArgsOrBuilder(final int index) {
                if (this.argsBuilder_ == null) {
                    return this.args_.get(index);
                }
                return (MysqlxDatatypes.ScalarOrBuilder)this.argsBuilder_.getMessageOrBuilder(index);
            }
            
            public List<? extends MysqlxDatatypes.ScalarOrBuilder> getArgsOrBuilderList() {
                if (this.argsBuilder_ != null) {
                    return (List<? extends MysqlxDatatypes.ScalarOrBuilder>)this.argsBuilder_.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList((List<? extends MysqlxDatatypes.ScalarOrBuilder>)this.args_);
            }
            
            public MysqlxDatatypes.Scalar.Builder addArgsBuilder() {
                return (MysqlxDatatypes.Scalar.Builder)this.getArgsFieldBuilder().addBuilder((AbstractMessage)MysqlxDatatypes.Scalar.getDefaultInstance());
            }
            
            public MysqlxDatatypes.Scalar.Builder addArgsBuilder(final int index) {
                return (MysqlxDatatypes.Scalar.Builder)this.getArgsFieldBuilder().addBuilder(index, (AbstractMessage)MysqlxDatatypes.Scalar.getDefaultInstance());
            }
            
            public List<MysqlxDatatypes.Scalar.Builder> getArgsBuilderList() {
                return (List<MysqlxDatatypes.Scalar.Builder>)this.getArgsFieldBuilder().getBuilderList();
            }
            
            private RepeatedFieldBuilderV3<MysqlxDatatypes.Scalar, MysqlxDatatypes.Scalar.Builder, MysqlxDatatypes.ScalarOrBuilder> getArgsFieldBuilder() {
                if (this.argsBuilder_ == null) {
                    this.argsBuilder_ = (RepeatedFieldBuilderV3<MysqlxDatatypes.Scalar, MysqlxDatatypes.Scalar.Builder, MysqlxDatatypes.ScalarOrBuilder>)new RepeatedFieldBuilderV3((List)this.args_, (this.bitField0_ & 0x10) == 0x10, (AbstractMessage.BuilderParent)this.getParentForChildren(), this.isClean());
                    this.args_ = null;
                }
                return this.argsBuilder_;
            }
            
            public boolean hasUpsert() {
                return (this.bitField0_ & 0x20) == 0x20;
            }
            
            public boolean getUpsert() {
                return this.upsert_;
            }
            
            public Builder setUpsert(final boolean value) {
                this.bitField0_ |= 0x20;
                this.upsert_ = value;
                this.onChanged();
                return this;
            }
            
            public Builder clearUpsert() {
                this.bitField0_ &= 0xFFFFFFDF;
                this.upsert_ = false;
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
        
        public interface TypedRowOrBuilder extends MessageOrBuilder
        {
            List<MysqlxExpr.Expr> getFieldList();
            
            MysqlxExpr.Expr getField(final int p0);
            
            int getFieldCount();
            
            List<? extends MysqlxExpr.ExprOrBuilder> getFieldOrBuilderList();
            
            MysqlxExpr.ExprOrBuilder getFieldOrBuilder(final int p0);
        }
    }
    
    public static final class Update extends GeneratedMessageV3 implements UpdateOrBuilder
    {
        private static final long serialVersionUID = 0L;
        private int bitField0_;
        public static final int COLLECTION_FIELD_NUMBER = 2;
        private Collection collection_;
        public static final int DATA_MODEL_FIELD_NUMBER = 3;
        private int dataModel_;
        public static final int CRITERIA_FIELD_NUMBER = 4;
        private MysqlxExpr.Expr criteria_;
        public static final int ARGS_FIELD_NUMBER = 8;
        private List<MysqlxDatatypes.Scalar> args_;
        public static final int LIMIT_FIELD_NUMBER = 5;
        private Limit limit_;
        public static final int ORDER_FIELD_NUMBER = 6;
        private List<Order> order_;
        public static final int OPERATION_FIELD_NUMBER = 7;
        private List<UpdateOperation> operation_;
        private byte memoizedIsInitialized;
        private static final Update DEFAULT_INSTANCE;
        @Deprecated
        public static final Parser<Update> PARSER;
        
        private Update(final GeneratedMessageV3.Builder<?> builder) {
            super((GeneratedMessageV3.Builder)builder);
            this.memoizedIsInitialized = -1;
        }
        
        private Update() {
            this.memoizedIsInitialized = -1;
            this.dataModel_ = 1;
            this.args_ = Collections.emptyList();
            this.order_ = Collections.emptyList();
            this.operation_ = Collections.emptyList();
        }
        
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }
        
        private Update(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                        case 18: {
                            Collection.Builder subBuilder = null;
                            if ((this.bitField0_ & 0x1) == 0x1) {
                                subBuilder = this.collection_.toBuilder();
                            }
                            this.collection_ = (Collection)input.readMessage((Parser)Collection.PARSER, extensionRegistry);
                            if (subBuilder != null) {
                                subBuilder.mergeFrom(this.collection_);
                                this.collection_ = subBuilder.buildPartial();
                            }
                            this.bitField0_ |= 0x1;
                            continue;
                        }
                        case 24: {
                            final int rawValue = input.readEnum();
                            final DataModel value = DataModel.valueOf(rawValue);
                            if (value == null) {
                                unknownFields.mergeVarintField(3, rawValue);
                                continue;
                            }
                            this.bitField0_ |= 0x2;
                            this.dataModel_ = rawValue;
                            continue;
                        }
                        case 34: {
                            MysqlxExpr.Expr.Builder subBuilder2 = null;
                            if ((this.bitField0_ & 0x4) == 0x4) {
                                subBuilder2 = this.criteria_.toBuilder();
                            }
                            this.criteria_ = (MysqlxExpr.Expr)input.readMessage((Parser)MysqlxExpr.Expr.PARSER, extensionRegistry);
                            if (subBuilder2 != null) {
                                subBuilder2.mergeFrom(this.criteria_);
                                this.criteria_ = subBuilder2.buildPartial();
                            }
                            this.bitField0_ |= 0x4;
                            continue;
                        }
                        case 42: {
                            Limit.Builder subBuilder3 = null;
                            if ((this.bitField0_ & 0x8) == 0x8) {
                                subBuilder3 = this.limit_.toBuilder();
                            }
                            this.limit_ = (Limit)input.readMessage((Parser)Limit.PARSER, extensionRegistry);
                            if (subBuilder3 != null) {
                                subBuilder3.mergeFrom(this.limit_);
                                this.limit_ = subBuilder3.buildPartial();
                            }
                            this.bitField0_ |= 0x8;
                            continue;
                        }
                        case 50: {
                            if ((mutable_bitField0_ & 0x20) != 0x20) {
                                this.order_ = new ArrayList<Order>();
                                mutable_bitField0_ |= 0x20;
                            }
                            this.order_.add((Order)input.readMessage((Parser)Order.PARSER, extensionRegistry));
                            continue;
                        }
                        case 58: {
                            if ((mutable_bitField0_ & 0x40) != 0x40) {
                                this.operation_ = new ArrayList<UpdateOperation>();
                                mutable_bitField0_ |= 0x40;
                            }
                            this.operation_.add((UpdateOperation)input.readMessage((Parser)UpdateOperation.PARSER, extensionRegistry));
                            continue;
                        }
                        case 66: {
                            if ((mutable_bitField0_ & 0x8) != 0x8) {
                                this.args_ = new ArrayList<MysqlxDatatypes.Scalar>();
                                mutable_bitField0_ |= 0x8;
                            }
                            this.args_.add((MysqlxDatatypes.Scalar)input.readMessage((Parser)MysqlxDatatypes.Scalar.PARSER, extensionRegistry));
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
                if ((mutable_bitField0_ & 0x20) == 0x20) {
                    this.order_ = Collections.unmodifiableList((List<? extends Order>)this.order_);
                }
                if ((mutable_bitField0_ & 0x40) == 0x40) {
                    this.operation_ = Collections.unmodifiableList((List<? extends UpdateOperation>)this.operation_);
                }
                if ((mutable_bitField0_ & 0x8) == 0x8) {
                    this.args_ = Collections.unmodifiableList((List<? extends MysqlxDatatypes.Scalar>)this.args_);
                }
                this.unknownFields = unknownFields.build();
                this.makeExtensionsImmutable();
            }
        }
        
        public static final Descriptors.Descriptor getDescriptor() {
            return MysqlxCrud.internal_static_Mysqlx_Crud_Update_descriptor;
        }
        
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return MysqlxCrud.internal_static_Mysqlx_Crud_Update_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)Update.class, (Class)Builder.class);
        }
        
        public boolean hasCollection() {
            return (this.bitField0_ & 0x1) == 0x1;
        }
        
        public Collection getCollection() {
            return (this.collection_ == null) ? Collection.getDefaultInstance() : this.collection_;
        }
        
        public CollectionOrBuilder getCollectionOrBuilder() {
            return (this.collection_ == null) ? Collection.getDefaultInstance() : this.collection_;
        }
        
        public boolean hasDataModel() {
            return (this.bitField0_ & 0x2) == 0x2;
        }
        
        public DataModel getDataModel() {
            final DataModel result = DataModel.valueOf(this.dataModel_);
            return (result == null) ? DataModel.DOCUMENT : result;
        }
        
        public boolean hasCriteria() {
            return (this.bitField0_ & 0x4) == 0x4;
        }
        
        public MysqlxExpr.Expr getCriteria() {
            return (this.criteria_ == null) ? MysqlxExpr.Expr.getDefaultInstance() : this.criteria_;
        }
        
        public MysqlxExpr.ExprOrBuilder getCriteriaOrBuilder() {
            return (this.criteria_ == null) ? MysqlxExpr.Expr.getDefaultInstance() : this.criteria_;
        }
        
        public List<MysqlxDatatypes.Scalar> getArgsList() {
            return this.args_;
        }
        
        public List<? extends MysqlxDatatypes.ScalarOrBuilder> getArgsOrBuilderList() {
            return this.args_;
        }
        
        public int getArgsCount() {
            return this.args_.size();
        }
        
        public MysqlxDatatypes.Scalar getArgs(final int index) {
            return this.args_.get(index);
        }
        
        public MysqlxDatatypes.ScalarOrBuilder getArgsOrBuilder(final int index) {
            return this.args_.get(index);
        }
        
        public boolean hasLimit() {
            return (this.bitField0_ & 0x8) == 0x8;
        }
        
        public Limit getLimit() {
            return (this.limit_ == null) ? Limit.getDefaultInstance() : this.limit_;
        }
        
        public LimitOrBuilder getLimitOrBuilder() {
            return (this.limit_ == null) ? Limit.getDefaultInstance() : this.limit_;
        }
        
        public List<Order> getOrderList() {
            return this.order_;
        }
        
        public List<? extends OrderOrBuilder> getOrderOrBuilderList() {
            return this.order_;
        }
        
        public int getOrderCount() {
            return this.order_.size();
        }
        
        public Order getOrder(final int index) {
            return this.order_.get(index);
        }
        
        public OrderOrBuilder getOrderOrBuilder(final int index) {
            return this.order_.get(index);
        }
        
        public List<UpdateOperation> getOperationList() {
            return this.operation_;
        }
        
        public List<? extends UpdateOperationOrBuilder> getOperationOrBuilderList() {
            return this.operation_;
        }
        
        public int getOperationCount() {
            return this.operation_.size();
        }
        
        public UpdateOperation getOperation(final int index) {
            return this.operation_.get(index);
        }
        
        public UpdateOperationOrBuilder getOperationOrBuilder(final int index) {
            return this.operation_.get(index);
        }
        
        public final boolean isInitialized() {
            final byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (!this.hasCollection()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            if (!this.getCollection().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            if (this.hasCriteria() && !this.getCriteria().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            for (int i = 0; i < this.getArgsCount(); ++i) {
                if (!this.getArgs(i).isInitialized()) {
                    this.memoizedIsInitialized = 0;
                    return false;
                }
            }
            if (this.hasLimit() && !this.getLimit().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            for (int i = 0; i < this.getOrderCount(); ++i) {
                if (!this.getOrder(i).isInitialized()) {
                    this.memoizedIsInitialized = 0;
                    return false;
                }
            }
            for (int i = 0; i < this.getOperationCount(); ++i) {
                if (!this.getOperation(i).isInitialized()) {
                    this.memoizedIsInitialized = 0;
                    return false;
                }
            }
            this.memoizedIsInitialized = 1;
            return true;
        }
        
        public void writeTo(final CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 0x1) == 0x1) {
                output.writeMessage(2, (MessageLite)this.getCollection());
            }
            if ((this.bitField0_ & 0x2) == 0x2) {
                output.writeEnum(3, this.dataModel_);
            }
            if ((this.bitField0_ & 0x4) == 0x4) {
                output.writeMessage(4, (MessageLite)this.getCriteria());
            }
            if ((this.bitField0_ & 0x8) == 0x8) {
                output.writeMessage(5, (MessageLite)this.getLimit());
            }
            for (int i = 0; i < this.order_.size(); ++i) {
                output.writeMessage(6, (MessageLite)this.order_.get(i));
            }
            for (int i = 0; i < this.operation_.size(); ++i) {
                output.writeMessage(7, (MessageLite)this.operation_.get(i));
            }
            for (int i = 0; i < this.args_.size(); ++i) {
                output.writeMessage(8, (MessageLite)this.args_.get(i));
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
                size += CodedOutputStream.computeMessageSize(2, (MessageLite)this.getCollection());
            }
            if ((this.bitField0_ & 0x2) == 0x2) {
                size += CodedOutputStream.computeEnumSize(3, this.dataModel_);
            }
            if ((this.bitField0_ & 0x4) == 0x4) {
                size += CodedOutputStream.computeMessageSize(4, (MessageLite)this.getCriteria());
            }
            if ((this.bitField0_ & 0x8) == 0x8) {
                size += CodedOutputStream.computeMessageSize(5, (MessageLite)this.getLimit());
            }
            for (int i = 0; i < this.order_.size(); ++i) {
                size += CodedOutputStream.computeMessageSize(6, (MessageLite)this.order_.get(i));
            }
            for (int i = 0; i < this.operation_.size(); ++i) {
                size += CodedOutputStream.computeMessageSize(7, (MessageLite)this.operation_.get(i));
            }
            for (int i = 0; i < this.args_.size(); ++i) {
                size += CodedOutputStream.computeMessageSize(8, (MessageLite)this.args_.get(i));
            }
            size += this.unknownFields.getSerializedSize();
            return this.memoizedSize = size;
        }
        
        public boolean equals(final Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Update)) {
                return super.equals(obj);
            }
            final Update other = (Update)obj;
            boolean result = true;
            result = (result && this.hasCollection() == other.hasCollection());
            if (this.hasCollection()) {
                result = (result && this.getCollection().equals(other.getCollection()));
            }
            result = (result && this.hasDataModel() == other.hasDataModel());
            if (this.hasDataModel()) {
                result = (result && this.dataModel_ == other.dataModel_);
            }
            result = (result && this.hasCriteria() == other.hasCriteria());
            if (this.hasCriteria()) {
                result = (result && this.getCriteria().equals(other.getCriteria()));
            }
            result = (result && this.getArgsList().equals(other.getArgsList()));
            result = (result && this.hasLimit() == other.hasLimit());
            if (this.hasLimit()) {
                result = (result && this.getLimit().equals(other.getLimit()));
            }
            result = (result && this.getOrderList().equals(other.getOrderList()));
            result = (result && this.getOperationList().equals(other.getOperationList()));
            result = (result && this.unknownFields.equals((Object)other.unknownFields));
            return result;
        }
        
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + getDescriptor().hashCode();
            if (this.hasCollection()) {
                hash = 37 * hash + 2;
                hash = 53 * hash + this.getCollection().hashCode();
            }
            if (this.hasDataModel()) {
                hash = 37 * hash + 3;
                hash = 53 * hash + this.dataModel_;
            }
            if (this.hasCriteria()) {
                hash = 37 * hash + 4;
                hash = 53 * hash + this.getCriteria().hashCode();
            }
            if (this.getArgsCount() > 0) {
                hash = 37 * hash + 8;
                hash = 53 * hash + this.getArgsList().hashCode();
            }
            if (this.hasLimit()) {
                hash = 37 * hash + 5;
                hash = 53 * hash + this.getLimit().hashCode();
            }
            if (this.getOrderCount() > 0) {
                hash = 37 * hash + 6;
                hash = 53 * hash + this.getOrderList().hashCode();
            }
            if (this.getOperationCount() > 0) {
                hash = 37 * hash + 7;
                hash = 53 * hash + this.getOperationList().hashCode();
            }
            hash = 29 * hash + this.unknownFields.hashCode();
            return this.memoizedHashCode = hash;
        }
        
        public static Update parseFrom(final ByteBuffer data) throws InvalidProtocolBufferException {
            return (Update)Update.PARSER.parseFrom(data);
        }
        
        public static Update parseFrom(final ByteBuffer data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Update)Update.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Update parseFrom(final ByteString data) throws InvalidProtocolBufferException {
            return (Update)Update.PARSER.parseFrom(data);
        }
        
        public static Update parseFrom(final ByteString data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Update)Update.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Update parseFrom(final byte[] data) throws InvalidProtocolBufferException {
            return (Update)Update.PARSER.parseFrom(data);
        }
        
        public static Update parseFrom(final byte[] data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Update)Update.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Update parseFrom(final InputStream input) throws IOException {
            return (Update)GeneratedMessageV3.parseWithIOException((Parser)Update.PARSER, input);
        }
        
        public static Update parseFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Update)GeneratedMessageV3.parseWithIOException((Parser)Update.PARSER, input, extensionRegistry);
        }
        
        public static Update parseDelimitedFrom(final InputStream input) throws IOException {
            return (Update)GeneratedMessageV3.parseDelimitedWithIOException((Parser)Update.PARSER, input);
        }
        
        public static Update parseDelimitedFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Update)GeneratedMessageV3.parseDelimitedWithIOException((Parser)Update.PARSER, input, extensionRegistry);
        }
        
        public static Update parseFrom(final CodedInputStream input) throws IOException {
            return (Update)GeneratedMessageV3.parseWithIOException((Parser)Update.PARSER, input);
        }
        
        public static Update parseFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Update)GeneratedMessageV3.parseWithIOException((Parser)Update.PARSER, input, extensionRegistry);
        }
        
        public Builder newBuilderForType() {
            return newBuilder();
        }
        
        public static Builder newBuilder() {
            return Update.DEFAULT_INSTANCE.toBuilder();
        }
        
        public static Builder newBuilder(final Update prototype) {
            return Update.DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }
        
        public Builder toBuilder() {
            return (this == Update.DEFAULT_INSTANCE) ? new Builder() : new Builder().mergeFrom(this);
        }
        
        protected Builder newBuilderForType(final GeneratedMessageV3.BuilderParent parent) {
            final Builder builder = new Builder(parent);
            return builder;
        }
        
        public static Update getDefaultInstance() {
            return Update.DEFAULT_INSTANCE;
        }
        
        public static Parser<Update> parser() {
            return Update.PARSER;
        }
        
        public Parser<Update> getParserForType() {
            return Update.PARSER;
        }
        
        public Update getDefaultInstanceForType() {
            return Update.DEFAULT_INSTANCE;
        }
        
        static {
            DEFAULT_INSTANCE = new Update();
            PARSER = (Parser)new AbstractParser<Update>() {
                public Update parsePartialFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new Update(input, extensionRegistry);
                }
            };
        }
        
        public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements UpdateOrBuilder
        {
            private int bitField0_;
            private Collection collection_;
            private SingleFieldBuilderV3<Collection, Collection.Builder, CollectionOrBuilder> collectionBuilder_;
            private int dataModel_;
            private MysqlxExpr.Expr criteria_;
            private SingleFieldBuilderV3<MysqlxExpr.Expr, MysqlxExpr.Expr.Builder, MysqlxExpr.ExprOrBuilder> criteriaBuilder_;
            private List<MysqlxDatatypes.Scalar> args_;
            private RepeatedFieldBuilderV3<MysqlxDatatypes.Scalar, MysqlxDatatypes.Scalar.Builder, MysqlxDatatypes.ScalarOrBuilder> argsBuilder_;
            private Limit limit_;
            private SingleFieldBuilderV3<Limit, Limit.Builder, LimitOrBuilder> limitBuilder_;
            private List<Order> order_;
            private RepeatedFieldBuilderV3<Order, Order.Builder, OrderOrBuilder> orderBuilder_;
            private List<UpdateOperation> operation_;
            private RepeatedFieldBuilderV3<UpdateOperation, UpdateOperation.Builder, UpdateOperationOrBuilder> operationBuilder_;
            
            public static final Descriptors.Descriptor getDescriptor() {
                return MysqlxCrud.internal_static_Mysqlx_Crud_Update_descriptor;
            }
            
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return MysqlxCrud.internal_static_Mysqlx_Crud_Update_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)Update.class, (Class)Builder.class);
            }
            
            private Builder() {
                this.collection_ = null;
                this.dataModel_ = 1;
                this.criteria_ = null;
                this.args_ = Collections.emptyList();
                this.limit_ = null;
                this.order_ = Collections.emptyList();
                this.operation_ = Collections.emptyList();
                this.maybeForceBuilderInitialization();
            }
            
            private Builder(final GeneratedMessageV3.BuilderParent parent) {
                super(parent);
                this.collection_ = null;
                this.dataModel_ = 1;
                this.criteria_ = null;
                this.args_ = Collections.emptyList();
                this.limit_ = null;
                this.order_ = Collections.emptyList();
                this.operation_ = Collections.emptyList();
                this.maybeForceBuilderInitialization();
            }
            
            private void maybeForceBuilderInitialization() {
                if (Update.alwaysUseFieldBuilders) {
                    this.getCollectionFieldBuilder();
                    this.getCriteriaFieldBuilder();
                    this.getArgsFieldBuilder();
                    this.getLimitFieldBuilder();
                    this.getOrderFieldBuilder();
                    this.getOperationFieldBuilder();
                }
            }
            
            public Builder clear() {
                super.clear();
                if (this.collectionBuilder_ == null) {
                    this.collection_ = null;
                }
                else {
                    this.collectionBuilder_.clear();
                }
                this.bitField0_ &= 0xFFFFFFFE;
                this.dataModel_ = 1;
                this.bitField0_ &= 0xFFFFFFFD;
                if (this.criteriaBuilder_ == null) {
                    this.criteria_ = null;
                }
                else {
                    this.criteriaBuilder_.clear();
                }
                this.bitField0_ &= 0xFFFFFFFB;
                if (this.argsBuilder_ == null) {
                    this.args_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFF7;
                }
                else {
                    this.argsBuilder_.clear();
                }
                if (this.limitBuilder_ == null) {
                    this.limit_ = null;
                }
                else {
                    this.limitBuilder_.clear();
                }
                this.bitField0_ &= 0xFFFFFFEF;
                if (this.orderBuilder_ == null) {
                    this.order_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFDF;
                }
                else {
                    this.orderBuilder_.clear();
                }
                if (this.operationBuilder_ == null) {
                    this.operation_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFBF;
                }
                else {
                    this.operationBuilder_.clear();
                }
                return this;
            }
            
            public Descriptors.Descriptor getDescriptorForType() {
                return MysqlxCrud.internal_static_Mysqlx_Crud_Update_descriptor;
            }
            
            public Update getDefaultInstanceForType() {
                return Update.getDefaultInstance();
            }
            
            public Update build() {
                final Update result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw newUninitializedMessageException((Message)result);
                }
                return result;
            }
            
            public Update buildPartial() {
                final Update result = new Update((GeneratedMessageV3.Builder)this);
                final int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 0x1) == 0x1) {
                    to_bitField0_ |= 0x1;
                }
                if (this.collectionBuilder_ == null) {
                    result.collection_ = this.collection_;
                }
                else {
                    result.collection_ = (Collection)this.collectionBuilder_.build();
                }
                if ((from_bitField0_ & 0x2) == 0x2) {
                    to_bitField0_ |= 0x2;
                }
                result.dataModel_ = this.dataModel_;
                if ((from_bitField0_ & 0x4) == 0x4) {
                    to_bitField0_ |= 0x4;
                }
                if (this.criteriaBuilder_ == null) {
                    result.criteria_ = this.criteria_;
                }
                else {
                    result.criteria_ = (MysqlxExpr.Expr)this.criteriaBuilder_.build();
                }
                if (this.argsBuilder_ == null) {
                    if ((this.bitField0_ & 0x8) == 0x8) {
                        this.args_ = Collections.unmodifiableList((List<? extends MysqlxDatatypes.Scalar>)this.args_);
                        this.bitField0_ &= 0xFFFFFFF7;
                    }
                    result.args_ = this.args_;
                }
                else {
                    result.args_ = (List<MysqlxDatatypes.Scalar>)this.argsBuilder_.build();
                }
                if ((from_bitField0_ & 0x10) == 0x10) {
                    to_bitField0_ |= 0x8;
                }
                if (this.limitBuilder_ == null) {
                    result.limit_ = this.limit_;
                }
                else {
                    result.limit_ = (Limit)this.limitBuilder_.build();
                }
                if (this.orderBuilder_ == null) {
                    if ((this.bitField0_ & 0x20) == 0x20) {
                        this.order_ = Collections.unmodifiableList((List<? extends Order>)this.order_);
                        this.bitField0_ &= 0xFFFFFFDF;
                    }
                    result.order_ = this.order_;
                }
                else {
                    result.order_ = (List<Order>)this.orderBuilder_.build();
                }
                if (this.operationBuilder_ == null) {
                    if ((this.bitField0_ & 0x40) == 0x40) {
                        this.operation_ = Collections.unmodifiableList((List<? extends UpdateOperation>)this.operation_);
                        this.bitField0_ &= 0xFFFFFFBF;
                    }
                    result.operation_ = this.operation_;
                }
                else {
                    result.operation_ = (List<UpdateOperation>)this.operationBuilder_.build();
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
                if (other instanceof Update) {
                    return this.mergeFrom((Update)other);
                }
                super.mergeFrom(other);
                return this;
            }
            
            public Builder mergeFrom(final Update other) {
                if (other == Update.getDefaultInstance()) {
                    return this;
                }
                if (other.hasCollection()) {
                    this.mergeCollection(other.getCollection());
                }
                if (other.hasDataModel()) {
                    this.setDataModel(other.getDataModel());
                }
                if (other.hasCriteria()) {
                    this.mergeCriteria(other.getCriteria());
                }
                if (this.argsBuilder_ == null) {
                    if (!other.args_.isEmpty()) {
                        if (this.args_.isEmpty()) {
                            this.args_ = other.args_;
                            this.bitField0_ &= 0xFFFFFFF7;
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
                        this.bitField0_ &= 0xFFFFFFF7;
                        this.argsBuilder_ = (Update.alwaysUseFieldBuilders ? this.getArgsFieldBuilder() : null);
                    }
                    else {
                        this.argsBuilder_.addAllMessages((Iterable)other.args_);
                    }
                }
                if (other.hasLimit()) {
                    this.mergeLimit(other.getLimit());
                }
                if (this.orderBuilder_ == null) {
                    if (!other.order_.isEmpty()) {
                        if (this.order_.isEmpty()) {
                            this.order_ = other.order_;
                            this.bitField0_ &= 0xFFFFFFDF;
                        }
                        else {
                            this.ensureOrderIsMutable();
                            this.order_.addAll(other.order_);
                        }
                        this.onChanged();
                    }
                }
                else if (!other.order_.isEmpty()) {
                    if (this.orderBuilder_.isEmpty()) {
                        this.orderBuilder_.dispose();
                        this.orderBuilder_ = null;
                        this.order_ = other.order_;
                        this.bitField0_ &= 0xFFFFFFDF;
                        this.orderBuilder_ = (Update.alwaysUseFieldBuilders ? this.getOrderFieldBuilder() : null);
                    }
                    else {
                        this.orderBuilder_.addAllMessages((Iterable)other.order_);
                    }
                }
                if (this.operationBuilder_ == null) {
                    if (!other.operation_.isEmpty()) {
                        if (this.operation_.isEmpty()) {
                            this.operation_ = other.operation_;
                            this.bitField0_ &= 0xFFFFFFBF;
                        }
                        else {
                            this.ensureOperationIsMutable();
                            this.operation_.addAll(other.operation_);
                        }
                        this.onChanged();
                    }
                }
                else if (!other.operation_.isEmpty()) {
                    if (this.operationBuilder_.isEmpty()) {
                        this.operationBuilder_.dispose();
                        this.operationBuilder_ = null;
                        this.operation_ = other.operation_;
                        this.bitField0_ &= 0xFFFFFFBF;
                        this.operationBuilder_ = (Update.alwaysUseFieldBuilders ? this.getOperationFieldBuilder() : null);
                    }
                    else {
                        this.operationBuilder_.addAllMessages((Iterable)other.operation_);
                    }
                }
                this.mergeUnknownFields(other.unknownFields);
                this.onChanged();
                return this;
            }
            
            public final boolean isInitialized() {
                if (!this.hasCollection()) {
                    return false;
                }
                if (!this.getCollection().isInitialized()) {
                    return false;
                }
                if (this.hasCriteria() && !this.getCriteria().isInitialized()) {
                    return false;
                }
                for (int i = 0; i < this.getArgsCount(); ++i) {
                    if (!this.getArgs(i).isInitialized()) {
                        return false;
                    }
                }
                if (this.hasLimit() && !this.getLimit().isInitialized()) {
                    return false;
                }
                for (int i = 0; i < this.getOrderCount(); ++i) {
                    if (!this.getOrder(i).isInitialized()) {
                        return false;
                    }
                }
                for (int i = 0; i < this.getOperationCount(); ++i) {
                    if (!this.getOperation(i).isInitialized()) {
                        return false;
                    }
                }
                return true;
            }
            
            public Builder mergeFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
                Update parsedMessage = null;
                try {
                    parsedMessage = (Update)Update.PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (Update)e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                }
                finally {
                    if (parsedMessage != null) {
                        this.mergeFrom(parsedMessage);
                    }
                }
                return this;
            }
            
            public boolean hasCollection() {
                return (this.bitField0_ & 0x1) == 0x1;
            }
            
            public Collection getCollection() {
                if (this.collectionBuilder_ == null) {
                    return (this.collection_ == null) ? Collection.getDefaultInstance() : this.collection_;
                }
                return (Collection)this.collectionBuilder_.getMessage();
            }
            
            public Builder setCollection(final Collection value) {
                if (this.collectionBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.collection_ = value;
                    this.onChanged();
                }
                else {
                    this.collectionBuilder_.setMessage((AbstractMessage)value);
                }
                this.bitField0_ |= 0x1;
                return this;
            }
            
            public Builder setCollection(final Collection.Builder builderForValue) {
                if (this.collectionBuilder_ == null) {
                    this.collection_ = builderForValue.build();
                    this.onChanged();
                }
                else {
                    this.collectionBuilder_.setMessage((AbstractMessage)builderForValue.build());
                }
                this.bitField0_ |= 0x1;
                return this;
            }
            
            public Builder mergeCollection(final Collection value) {
                if (this.collectionBuilder_ == null) {
                    if ((this.bitField0_ & 0x1) == 0x1 && this.collection_ != null && this.collection_ != Collection.getDefaultInstance()) {
                        this.collection_ = Collection.newBuilder(this.collection_).mergeFrom(value).buildPartial();
                    }
                    else {
                        this.collection_ = value;
                    }
                    this.onChanged();
                }
                else {
                    this.collectionBuilder_.mergeFrom((AbstractMessage)value);
                }
                this.bitField0_ |= 0x1;
                return this;
            }
            
            public Builder clearCollection() {
                if (this.collectionBuilder_ == null) {
                    this.collection_ = null;
                    this.onChanged();
                }
                else {
                    this.collectionBuilder_.clear();
                }
                this.bitField0_ &= 0xFFFFFFFE;
                return this;
            }
            
            public Collection.Builder getCollectionBuilder() {
                this.bitField0_ |= 0x1;
                this.onChanged();
                return (Collection.Builder)this.getCollectionFieldBuilder().getBuilder();
            }
            
            public CollectionOrBuilder getCollectionOrBuilder() {
                if (this.collectionBuilder_ != null) {
                    return (CollectionOrBuilder)this.collectionBuilder_.getMessageOrBuilder();
                }
                return (this.collection_ == null) ? Collection.getDefaultInstance() : this.collection_;
            }
            
            private SingleFieldBuilderV3<Collection, Collection.Builder, CollectionOrBuilder> getCollectionFieldBuilder() {
                if (this.collectionBuilder_ == null) {
                    this.collectionBuilder_ = (SingleFieldBuilderV3<Collection, Collection.Builder, CollectionOrBuilder>)new SingleFieldBuilderV3((AbstractMessage)this.getCollection(), (AbstractMessage.BuilderParent)this.getParentForChildren(), this.isClean());
                    this.collection_ = null;
                }
                return this.collectionBuilder_;
            }
            
            public boolean hasDataModel() {
                return (this.bitField0_ & 0x2) == 0x2;
            }
            
            public DataModel getDataModel() {
                final DataModel result = DataModel.valueOf(this.dataModel_);
                return (result == null) ? DataModel.DOCUMENT : result;
            }
            
            public Builder setDataModel(final DataModel value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 0x2;
                this.dataModel_ = value.getNumber();
                this.onChanged();
                return this;
            }
            
            public Builder clearDataModel() {
                this.bitField0_ &= 0xFFFFFFFD;
                this.dataModel_ = 1;
                this.onChanged();
                return this;
            }
            
            public boolean hasCriteria() {
                return (this.bitField0_ & 0x4) == 0x4;
            }
            
            public MysqlxExpr.Expr getCriteria() {
                if (this.criteriaBuilder_ == null) {
                    return (this.criteria_ == null) ? MysqlxExpr.Expr.getDefaultInstance() : this.criteria_;
                }
                return (MysqlxExpr.Expr)this.criteriaBuilder_.getMessage();
            }
            
            public Builder setCriteria(final MysqlxExpr.Expr value) {
                if (this.criteriaBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.criteria_ = value;
                    this.onChanged();
                }
                else {
                    this.criteriaBuilder_.setMessage((AbstractMessage)value);
                }
                this.bitField0_ |= 0x4;
                return this;
            }
            
            public Builder setCriteria(final MysqlxExpr.Expr.Builder builderForValue) {
                if (this.criteriaBuilder_ == null) {
                    this.criteria_ = builderForValue.build();
                    this.onChanged();
                }
                else {
                    this.criteriaBuilder_.setMessage((AbstractMessage)builderForValue.build());
                }
                this.bitField0_ |= 0x4;
                return this;
            }
            
            public Builder mergeCriteria(final MysqlxExpr.Expr value) {
                if (this.criteriaBuilder_ == null) {
                    if ((this.bitField0_ & 0x4) == 0x4 && this.criteria_ != null && this.criteria_ != MysqlxExpr.Expr.getDefaultInstance()) {
                        this.criteria_ = MysqlxExpr.Expr.newBuilder(this.criteria_).mergeFrom(value).buildPartial();
                    }
                    else {
                        this.criteria_ = value;
                    }
                    this.onChanged();
                }
                else {
                    this.criteriaBuilder_.mergeFrom((AbstractMessage)value);
                }
                this.bitField0_ |= 0x4;
                return this;
            }
            
            public Builder clearCriteria() {
                if (this.criteriaBuilder_ == null) {
                    this.criteria_ = null;
                    this.onChanged();
                }
                else {
                    this.criteriaBuilder_.clear();
                }
                this.bitField0_ &= 0xFFFFFFFB;
                return this;
            }
            
            public MysqlxExpr.Expr.Builder getCriteriaBuilder() {
                this.bitField0_ |= 0x4;
                this.onChanged();
                return (MysqlxExpr.Expr.Builder)this.getCriteriaFieldBuilder().getBuilder();
            }
            
            public MysqlxExpr.ExprOrBuilder getCriteriaOrBuilder() {
                if (this.criteriaBuilder_ != null) {
                    return (MysqlxExpr.ExprOrBuilder)this.criteriaBuilder_.getMessageOrBuilder();
                }
                return (this.criteria_ == null) ? MysqlxExpr.Expr.getDefaultInstance() : this.criteria_;
            }
            
            private SingleFieldBuilderV3<MysqlxExpr.Expr, MysqlxExpr.Expr.Builder, MysqlxExpr.ExprOrBuilder> getCriteriaFieldBuilder() {
                if (this.criteriaBuilder_ == null) {
                    this.criteriaBuilder_ = (SingleFieldBuilderV3<MysqlxExpr.Expr, MysqlxExpr.Expr.Builder, MysqlxExpr.ExprOrBuilder>)new SingleFieldBuilderV3((AbstractMessage)this.getCriteria(), (AbstractMessage.BuilderParent)this.getParentForChildren(), this.isClean());
                    this.criteria_ = null;
                }
                return this.criteriaBuilder_;
            }
            
            private void ensureArgsIsMutable() {
                if ((this.bitField0_ & 0x8) != 0x8) {
                    this.args_ = new ArrayList<MysqlxDatatypes.Scalar>(this.args_);
                    this.bitField0_ |= 0x8;
                }
            }
            
            public List<MysqlxDatatypes.Scalar> getArgsList() {
                if (this.argsBuilder_ == null) {
                    return Collections.unmodifiableList((List<? extends MysqlxDatatypes.Scalar>)this.args_);
                }
                return (List<MysqlxDatatypes.Scalar>)this.argsBuilder_.getMessageList();
            }
            
            public int getArgsCount() {
                if (this.argsBuilder_ == null) {
                    return this.args_.size();
                }
                return this.argsBuilder_.getCount();
            }
            
            public MysqlxDatatypes.Scalar getArgs(final int index) {
                if (this.argsBuilder_ == null) {
                    return this.args_.get(index);
                }
                return (MysqlxDatatypes.Scalar)this.argsBuilder_.getMessage(index);
            }
            
            public Builder setArgs(final int index, final MysqlxDatatypes.Scalar value) {
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
            
            public Builder setArgs(final int index, final MysqlxDatatypes.Scalar.Builder builderForValue) {
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
            
            public Builder addArgs(final MysqlxDatatypes.Scalar value) {
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
            
            public Builder addArgs(final int index, final MysqlxDatatypes.Scalar value) {
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
            
            public Builder addArgs(final MysqlxDatatypes.Scalar.Builder builderForValue) {
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
            
            public Builder addArgs(final int index, final MysqlxDatatypes.Scalar.Builder builderForValue) {
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
            
            public Builder addAllArgs(final Iterable<? extends MysqlxDatatypes.Scalar> values) {
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
                    this.bitField0_ &= 0xFFFFFFF7;
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
            
            public MysqlxDatatypes.Scalar.Builder getArgsBuilder(final int index) {
                return (MysqlxDatatypes.Scalar.Builder)this.getArgsFieldBuilder().getBuilder(index);
            }
            
            public MysqlxDatatypes.ScalarOrBuilder getArgsOrBuilder(final int index) {
                if (this.argsBuilder_ == null) {
                    return this.args_.get(index);
                }
                return (MysqlxDatatypes.ScalarOrBuilder)this.argsBuilder_.getMessageOrBuilder(index);
            }
            
            public List<? extends MysqlxDatatypes.ScalarOrBuilder> getArgsOrBuilderList() {
                if (this.argsBuilder_ != null) {
                    return (List<? extends MysqlxDatatypes.ScalarOrBuilder>)this.argsBuilder_.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList((List<? extends MysqlxDatatypes.ScalarOrBuilder>)this.args_);
            }
            
            public MysqlxDatatypes.Scalar.Builder addArgsBuilder() {
                return (MysqlxDatatypes.Scalar.Builder)this.getArgsFieldBuilder().addBuilder((AbstractMessage)MysqlxDatatypes.Scalar.getDefaultInstance());
            }
            
            public MysqlxDatatypes.Scalar.Builder addArgsBuilder(final int index) {
                return (MysqlxDatatypes.Scalar.Builder)this.getArgsFieldBuilder().addBuilder(index, (AbstractMessage)MysqlxDatatypes.Scalar.getDefaultInstance());
            }
            
            public List<MysqlxDatatypes.Scalar.Builder> getArgsBuilderList() {
                return (List<MysqlxDatatypes.Scalar.Builder>)this.getArgsFieldBuilder().getBuilderList();
            }
            
            private RepeatedFieldBuilderV3<MysqlxDatatypes.Scalar, MysqlxDatatypes.Scalar.Builder, MysqlxDatatypes.ScalarOrBuilder> getArgsFieldBuilder() {
                if (this.argsBuilder_ == null) {
                    this.argsBuilder_ = (RepeatedFieldBuilderV3<MysqlxDatatypes.Scalar, MysqlxDatatypes.Scalar.Builder, MysqlxDatatypes.ScalarOrBuilder>)new RepeatedFieldBuilderV3((List)this.args_, (this.bitField0_ & 0x8) == 0x8, (AbstractMessage.BuilderParent)this.getParentForChildren(), this.isClean());
                    this.args_ = null;
                }
                return this.argsBuilder_;
            }
            
            public boolean hasLimit() {
                return (this.bitField0_ & 0x10) == 0x10;
            }
            
            public Limit getLimit() {
                if (this.limitBuilder_ == null) {
                    return (this.limit_ == null) ? Limit.getDefaultInstance() : this.limit_;
                }
                return (Limit)this.limitBuilder_.getMessage();
            }
            
            public Builder setLimit(final Limit value) {
                if (this.limitBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.limit_ = value;
                    this.onChanged();
                }
                else {
                    this.limitBuilder_.setMessage((AbstractMessage)value);
                }
                this.bitField0_ |= 0x10;
                return this;
            }
            
            public Builder setLimit(final Limit.Builder builderForValue) {
                if (this.limitBuilder_ == null) {
                    this.limit_ = builderForValue.build();
                    this.onChanged();
                }
                else {
                    this.limitBuilder_.setMessage((AbstractMessage)builderForValue.build());
                }
                this.bitField0_ |= 0x10;
                return this;
            }
            
            public Builder mergeLimit(final Limit value) {
                if (this.limitBuilder_ == null) {
                    if ((this.bitField0_ & 0x10) == 0x10 && this.limit_ != null && this.limit_ != Limit.getDefaultInstance()) {
                        this.limit_ = Limit.newBuilder(this.limit_).mergeFrom(value).buildPartial();
                    }
                    else {
                        this.limit_ = value;
                    }
                    this.onChanged();
                }
                else {
                    this.limitBuilder_.mergeFrom((AbstractMessage)value);
                }
                this.bitField0_ |= 0x10;
                return this;
            }
            
            public Builder clearLimit() {
                if (this.limitBuilder_ == null) {
                    this.limit_ = null;
                    this.onChanged();
                }
                else {
                    this.limitBuilder_.clear();
                }
                this.bitField0_ &= 0xFFFFFFEF;
                return this;
            }
            
            public Limit.Builder getLimitBuilder() {
                this.bitField0_ |= 0x10;
                this.onChanged();
                return (Limit.Builder)this.getLimitFieldBuilder().getBuilder();
            }
            
            public LimitOrBuilder getLimitOrBuilder() {
                if (this.limitBuilder_ != null) {
                    return (LimitOrBuilder)this.limitBuilder_.getMessageOrBuilder();
                }
                return (this.limit_ == null) ? Limit.getDefaultInstance() : this.limit_;
            }
            
            private SingleFieldBuilderV3<Limit, Limit.Builder, LimitOrBuilder> getLimitFieldBuilder() {
                if (this.limitBuilder_ == null) {
                    this.limitBuilder_ = (SingleFieldBuilderV3<Limit, Limit.Builder, LimitOrBuilder>)new SingleFieldBuilderV3((AbstractMessage)this.getLimit(), (AbstractMessage.BuilderParent)this.getParentForChildren(), this.isClean());
                    this.limit_ = null;
                }
                return this.limitBuilder_;
            }
            
            private void ensureOrderIsMutable() {
                if ((this.bitField0_ & 0x20) != 0x20) {
                    this.order_ = new ArrayList<Order>(this.order_);
                    this.bitField0_ |= 0x20;
                }
            }
            
            public List<Order> getOrderList() {
                if (this.orderBuilder_ == null) {
                    return Collections.unmodifiableList((List<? extends Order>)this.order_);
                }
                return (List<Order>)this.orderBuilder_.getMessageList();
            }
            
            public int getOrderCount() {
                if (this.orderBuilder_ == null) {
                    return this.order_.size();
                }
                return this.orderBuilder_.getCount();
            }
            
            public Order getOrder(final int index) {
                if (this.orderBuilder_ == null) {
                    return this.order_.get(index);
                }
                return (Order)this.orderBuilder_.getMessage(index);
            }
            
            public Builder setOrder(final int index, final Order value) {
                if (this.orderBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureOrderIsMutable();
                    this.order_.set(index, value);
                    this.onChanged();
                }
                else {
                    this.orderBuilder_.setMessage(index, (AbstractMessage)value);
                }
                return this;
            }
            
            public Builder setOrder(final int index, final Order.Builder builderForValue) {
                if (this.orderBuilder_ == null) {
                    this.ensureOrderIsMutable();
                    this.order_.set(index, builderForValue.build());
                    this.onChanged();
                }
                else {
                    this.orderBuilder_.setMessage(index, (AbstractMessage)builderForValue.build());
                }
                return this;
            }
            
            public Builder addOrder(final Order value) {
                if (this.orderBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureOrderIsMutable();
                    this.order_.add(value);
                    this.onChanged();
                }
                else {
                    this.orderBuilder_.addMessage((AbstractMessage)value);
                }
                return this;
            }
            
            public Builder addOrder(final int index, final Order value) {
                if (this.orderBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureOrderIsMutable();
                    this.order_.add(index, value);
                    this.onChanged();
                }
                else {
                    this.orderBuilder_.addMessage(index, (AbstractMessage)value);
                }
                return this;
            }
            
            public Builder addOrder(final Order.Builder builderForValue) {
                if (this.orderBuilder_ == null) {
                    this.ensureOrderIsMutable();
                    this.order_.add(builderForValue.build());
                    this.onChanged();
                }
                else {
                    this.orderBuilder_.addMessage((AbstractMessage)builderForValue.build());
                }
                return this;
            }
            
            public Builder addOrder(final int index, final Order.Builder builderForValue) {
                if (this.orderBuilder_ == null) {
                    this.ensureOrderIsMutable();
                    this.order_.add(index, builderForValue.build());
                    this.onChanged();
                }
                else {
                    this.orderBuilder_.addMessage(index, (AbstractMessage)builderForValue.build());
                }
                return this;
            }
            
            public Builder addAllOrder(final Iterable<? extends Order> values) {
                if (this.orderBuilder_ == null) {
                    this.ensureOrderIsMutable();
                    AbstractMessageLite.Builder.addAll((Iterable)values, (List)this.order_);
                    this.onChanged();
                }
                else {
                    this.orderBuilder_.addAllMessages((Iterable)values);
                }
                return this;
            }
            
            public Builder clearOrder() {
                if (this.orderBuilder_ == null) {
                    this.order_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFDF;
                    this.onChanged();
                }
                else {
                    this.orderBuilder_.clear();
                }
                return this;
            }
            
            public Builder removeOrder(final int index) {
                if (this.orderBuilder_ == null) {
                    this.ensureOrderIsMutable();
                    this.order_.remove(index);
                    this.onChanged();
                }
                else {
                    this.orderBuilder_.remove(index);
                }
                return this;
            }
            
            public Order.Builder getOrderBuilder(final int index) {
                return (Order.Builder)this.getOrderFieldBuilder().getBuilder(index);
            }
            
            public OrderOrBuilder getOrderOrBuilder(final int index) {
                if (this.orderBuilder_ == null) {
                    return this.order_.get(index);
                }
                return (OrderOrBuilder)this.orderBuilder_.getMessageOrBuilder(index);
            }
            
            public List<? extends OrderOrBuilder> getOrderOrBuilderList() {
                if (this.orderBuilder_ != null) {
                    return (List<? extends OrderOrBuilder>)this.orderBuilder_.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList((List<? extends OrderOrBuilder>)this.order_);
            }
            
            public Order.Builder addOrderBuilder() {
                return (Order.Builder)this.getOrderFieldBuilder().addBuilder((AbstractMessage)Order.getDefaultInstance());
            }
            
            public Order.Builder addOrderBuilder(final int index) {
                return (Order.Builder)this.getOrderFieldBuilder().addBuilder(index, (AbstractMessage)Order.getDefaultInstance());
            }
            
            public List<Order.Builder> getOrderBuilderList() {
                return (List<Order.Builder>)this.getOrderFieldBuilder().getBuilderList();
            }
            
            private RepeatedFieldBuilderV3<Order, Order.Builder, OrderOrBuilder> getOrderFieldBuilder() {
                if (this.orderBuilder_ == null) {
                    this.orderBuilder_ = (RepeatedFieldBuilderV3<Order, Order.Builder, OrderOrBuilder>)new RepeatedFieldBuilderV3((List)this.order_, (this.bitField0_ & 0x20) == 0x20, (AbstractMessage.BuilderParent)this.getParentForChildren(), this.isClean());
                    this.order_ = null;
                }
                return this.orderBuilder_;
            }
            
            private void ensureOperationIsMutable() {
                if ((this.bitField0_ & 0x40) != 0x40) {
                    this.operation_ = new ArrayList<UpdateOperation>(this.operation_);
                    this.bitField0_ |= 0x40;
                }
            }
            
            public List<UpdateOperation> getOperationList() {
                if (this.operationBuilder_ == null) {
                    return Collections.unmodifiableList((List<? extends UpdateOperation>)this.operation_);
                }
                return (List<UpdateOperation>)this.operationBuilder_.getMessageList();
            }
            
            public int getOperationCount() {
                if (this.operationBuilder_ == null) {
                    return this.operation_.size();
                }
                return this.operationBuilder_.getCount();
            }
            
            public UpdateOperation getOperation(final int index) {
                if (this.operationBuilder_ == null) {
                    return this.operation_.get(index);
                }
                return (UpdateOperation)this.operationBuilder_.getMessage(index);
            }
            
            public Builder setOperation(final int index, final UpdateOperation value) {
                if (this.operationBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureOperationIsMutable();
                    this.operation_.set(index, value);
                    this.onChanged();
                }
                else {
                    this.operationBuilder_.setMessage(index, (AbstractMessage)value);
                }
                return this;
            }
            
            public Builder setOperation(final int index, final UpdateOperation.Builder builderForValue) {
                if (this.operationBuilder_ == null) {
                    this.ensureOperationIsMutable();
                    this.operation_.set(index, builderForValue.build());
                    this.onChanged();
                }
                else {
                    this.operationBuilder_.setMessage(index, (AbstractMessage)builderForValue.build());
                }
                return this;
            }
            
            public Builder addOperation(final UpdateOperation value) {
                if (this.operationBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureOperationIsMutable();
                    this.operation_.add(value);
                    this.onChanged();
                }
                else {
                    this.operationBuilder_.addMessage((AbstractMessage)value);
                }
                return this;
            }
            
            public Builder addOperation(final int index, final UpdateOperation value) {
                if (this.operationBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureOperationIsMutable();
                    this.operation_.add(index, value);
                    this.onChanged();
                }
                else {
                    this.operationBuilder_.addMessage(index, (AbstractMessage)value);
                }
                return this;
            }
            
            public Builder addOperation(final UpdateOperation.Builder builderForValue) {
                if (this.operationBuilder_ == null) {
                    this.ensureOperationIsMutable();
                    this.operation_.add(builderForValue.build());
                    this.onChanged();
                }
                else {
                    this.operationBuilder_.addMessage((AbstractMessage)builderForValue.build());
                }
                return this;
            }
            
            public Builder addOperation(final int index, final UpdateOperation.Builder builderForValue) {
                if (this.operationBuilder_ == null) {
                    this.ensureOperationIsMutable();
                    this.operation_.add(index, builderForValue.build());
                    this.onChanged();
                }
                else {
                    this.operationBuilder_.addMessage(index, (AbstractMessage)builderForValue.build());
                }
                return this;
            }
            
            public Builder addAllOperation(final Iterable<? extends UpdateOperation> values) {
                if (this.operationBuilder_ == null) {
                    this.ensureOperationIsMutable();
                    AbstractMessageLite.Builder.addAll((Iterable)values, (List)this.operation_);
                    this.onChanged();
                }
                else {
                    this.operationBuilder_.addAllMessages((Iterable)values);
                }
                return this;
            }
            
            public Builder clearOperation() {
                if (this.operationBuilder_ == null) {
                    this.operation_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFBF;
                    this.onChanged();
                }
                else {
                    this.operationBuilder_.clear();
                }
                return this;
            }
            
            public Builder removeOperation(final int index) {
                if (this.operationBuilder_ == null) {
                    this.ensureOperationIsMutable();
                    this.operation_.remove(index);
                    this.onChanged();
                }
                else {
                    this.operationBuilder_.remove(index);
                }
                return this;
            }
            
            public UpdateOperation.Builder getOperationBuilder(final int index) {
                return (UpdateOperation.Builder)this.getOperationFieldBuilder().getBuilder(index);
            }
            
            public UpdateOperationOrBuilder getOperationOrBuilder(final int index) {
                if (this.operationBuilder_ == null) {
                    return this.operation_.get(index);
                }
                return (UpdateOperationOrBuilder)this.operationBuilder_.getMessageOrBuilder(index);
            }
            
            public List<? extends UpdateOperationOrBuilder> getOperationOrBuilderList() {
                if (this.operationBuilder_ != null) {
                    return (List<? extends UpdateOperationOrBuilder>)this.operationBuilder_.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList((List<? extends UpdateOperationOrBuilder>)this.operation_);
            }
            
            public UpdateOperation.Builder addOperationBuilder() {
                return (UpdateOperation.Builder)this.getOperationFieldBuilder().addBuilder((AbstractMessage)UpdateOperation.getDefaultInstance());
            }
            
            public UpdateOperation.Builder addOperationBuilder(final int index) {
                return (UpdateOperation.Builder)this.getOperationFieldBuilder().addBuilder(index, (AbstractMessage)UpdateOperation.getDefaultInstance());
            }
            
            public List<UpdateOperation.Builder> getOperationBuilderList() {
                return (List<UpdateOperation.Builder>)this.getOperationFieldBuilder().getBuilderList();
            }
            
            private RepeatedFieldBuilderV3<UpdateOperation, UpdateOperation.Builder, UpdateOperationOrBuilder> getOperationFieldBuilder() {
                if (this.operationBuilder_ == null) {
                    this.operationBuilder_ = (RepeatedFieldBuilderV3<UpdateOperation, UpdateOperation.Builder, UpdateOperationOrBuilder>)new RepeatedFieldBuilderV3((List)this.operation_, (this.bitField0_ & 0x40) == 0x40, (AbstractMessage.BuilderParent)this.getParentForChildren(), this.isClean());
                    this.operation_ = null;
                }
                return this.operationBuilder_;
            }
            
            public final Builder setUnknownFields(final UnknownFieldSet unknownFields) {
                return (Builder)super.setUnknownFields(unknownFields);
            }
            
            public final Builder mergeUnknownFields(final UnknownFieldSet unknownFields) {
                return (Builder)super.mergeUnknownFields(unknownFields);
            }
        }
    }
    
    public static final class Delete extends GeneratedMessageV3 implements DeleteOrBuilder
    {
        private static final long serialVersionUID = 0L;
        private int bitField0_;
        public static final int COLLECTION_FIELD_NUMBER = 1;
        private Collection collection_;
        public static final int DATA_MODEL_FIELD_NUMBER = 2;
        private int dataModel_;
        public static final int CRITERIA_FIELD_NUMBER = 3;
        private MysqlxExpr.Expr criteria_;
        public static final int ARGS_FIELD_NUMBER = 6;
        private List<MysqlxDatatypes.Scalar> args_;
        public static final int LIMIT_FIELD_NUMBER = 4;
        private Limit limit_;
        public static final int ORDER_FIELD_NUMBER = 5;
        private List<Order> order_;
        private byte memoizedIsInitialized;
        private static final Delete DEFAULT_INSTANCE;
        @Deprecated
        public static final Parser<Delete> PARSER;
        
        private Delete(final GeneratedMessageV3.Builder<?> builder) {
            super((GeneratedMessageV3.Builder)builder);
            this.memoizedIsInitialized = -1;
        }
        
        private Delete() {
            this.memoizedIsInitialized = -1;
            this.dataModel_ = 1;
            this.args_ = Collections.emptyList();
            this.order_ = Collections.emptyList();
        }
        
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }
        
        private Delete(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            Collection.Builder subBuilder = null;
                            if ((this.bitField0_ & 0x1) == 0x1) {
                                subBuilder = this.collection_.toBuilder();
                            }
                            this.collection_ = (Collection)input.readMessage((Parser)Collection.PARSER, extensionRegistry);
                            if (subBuilder != null) {
                                subBuilder.mergeFrom(this.collection_);
                                this.collection_ = subBuilder.buildPartial();
                            }
                            this.bitField0_ |= 0x1;
                            continue;
                        }
                        case 16: {
                            final int rawValue = input.readEnum();
                            final DataModel value = DataModel.valueOf(rawValue);
                            if (value == null) {
                                unknownFields.mergeVarintField(2, rawValue);
                                continue;
                            }
                            this.bitField0_ |= 0x2;
                            this.dataModel_ = rawValue;
                            continue;
                        }
                        case 26: {
                            MysqlxExpr.Expr.Builder subBuilder2 = null;
                            if ((this.bitField0_ & 0x4) == 0x4) {
                                subBuilder2 = this.criteria_.toBuilder();
                            }
                            this.criteria_ = (MysqlxExpr.Expr)input.readMessage((Parser)MysqlxExpr.Expr.PARSER, extensionRegistry);
                            if (subBuilder2 != null) {
                                subBuilder2.mergeFrom(this.criteria_);
                                this.criteria_ = subBuilder2.buildPartial();
                            }
                            this.bitField0_ |= 0x4;
                            continue;
                        }
                        case 34: {
                            Limit.Builder subBuilder3 = null;
                            if ((this.bitField0_ & 0x8) == 0x8) {
                                subBuilder3 = this.limit_.toBuilder();
                            }
                            this.limit_ = (Limit)input.readMessage((Parser)Limit.PARSER, extensionRegistry);
                            if (subBuilder3 != null) {
                                subBuilder3.mergeFrom(this.limit_);
                                this.limit_ = subBuilder3.buildPartial();
                            }
                            this.bitField0_ |= 0x8;
                            continue;
                        }
                        case 42: {
                            if ((mutable_bitField0_ & 0x20) != 0x20) {
                                this.order_ = new ArrayList<Order>();
                                mutable_bitField0_ |= 0x20;
                            }
                            this.order_.add((Order)input.readMessage((Parser)Order.PARSER, extensionRegistry));
                            continue;
                        }
                        case 50: {
                            if ((mutable_bitField0_ & 0x8) != 0x8) {
                                this.args_ = new ArrayList<MysqlxDatatypes.Scalar>();
                                mutable_bitField0_ |= 0x8;
                            }
                            this.args_.add((MysqlxDatatypes.Scalar)input.readMessage((Parser)MysqlxDatatypes.Scalar.PARSER, extensionRegistry));
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
                if ((mutable_bitField0_ & 0x20) == 0x20) {
                    this.order_ = Collections.unmodifiableList((List<? extends Order>)this.order_);
                }
                if ((mutable_bitField0_ & 0x8) == 0x8) {
                    this.args_ = Collections.unmodifiableList((List<? extends MysqlxDatatypes.Scalar>)this.args_);
                }
                this.unknownFields = unknownFields.build();
                this.makeExtensionsImmutable();
            }
        }
        
        public static final Descriptors.Descriptor getDescriptor() {
            return MysqlxCrud.internal_static_Mysqlx_Crud_Delete_descriptor;
        }
        
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return MysqlxCrud.internal_static_Mysqlx_Crud_Delete_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)Delete.class, (Class)Builder.class);
        }
        
        public boolean hasCollection() {
            return (this.bitField0_ & 0x1) == 0x1;
        }
        
        public Collection getCollection() {
            return (this.collection_ == null) ? Collection.getDefaultInstance() : this.collection_;
        }
        
        public CollectionOrBuilder getCollectionOrBuilder() {
            return (this.collection_ == null) ? Collection.getDefaultInstance() : this.collection_;
        }
        
        public boolean hasDataModel() {
            return (this.bitField0_ & 0x2) == 0x2;
        }
        
        public DataModel getDataModel() {
            final DataModel result = DataModel.valueOf(this.dataModel_);
            return (result == null) ? DataModel.DOCUMENT : result;
        }
        
        public boolean hasCriteria() {
            return (this.bitField0_ & 0x4) == 0x4;
        }
        
        public MysqlxExpr.Expr getCriteria() {
            return (this.criteria_ == null) ? MysqlxExpr.Expr.getDefaultInstance() : this.criteria_;
        }
        
        public MysqlxExpr.ExprOrBuilder getCriteriaOrBuilder() {
            return (this.criteria_ == null) ? MysqlxExpr.Expr.getDefaultInstance() : this.criteria_;
        }
        
        public List<MysqlxDatatypes.Scalar> getArgsList() {
            return this.args_;
        }
        
        public List<? extends MysqlxDatatypes.ScalarOrBuilder> getArgsOrBuilderList() {
            return this.args_;
        }
        
        public int getArgsCount() {
            return this.args_.size();
        }
        
        public MysqlxDatatypes.Scalar getArgs(final int index) {
            return this.args_.get(index);
        }
        
        public MysqlxDatatypes.ScalarOrBuilder getArgsOrBuilder(final int index) {
            return this.args_.get(index);
        }
        
        public boolean hasLimit() {
            return (this.bitField0_ & 0x8) == 0x8;
        }
        
        public Limit getLimit() {
            return (this.limit_ == null) ? Limit.getDefaultInstance() : this.limit_;
        }
        
        public LimitOrBuilder getLimitOrBuilder() {
            return (this.limit_ == null) ? Limit.getDefaultInstance() : this.limit_;
        }
        
        public List<Order> getOrderList() {
            return this.order_;
        }
        
        public List<? extends OrderOrBuilder> getOrderOrBuilderList() {
            return this.order_;
        }
        
        public int getOrderCount() {
            return this.order_.size();
        }
        
        public Order getOrder(final int index) {
            return this.order_.get(index);
        }
        
        public OrderOrBuilder getOrderOrBuilder(final int index) {
            return this.order_.get(index);
        }
        
        public final boolean isInitialized() {
            final byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (!this.hasCollection()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            if (!this.getCollection().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            if (this.hasCriteria() && !this.getCriteria().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            for (int i = 0; i < this.getArgsCount(); ++i) {
                if (!this.getArgs(i).isInitialized()) {
                    this.memoizedIsInitialized = 0;
                    return false;
                }
            }
            if (this.hasLimit() && !this.getLimit().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            for (int i = 0; i < this.getOrderCount(); ++i) {
                if (!this.getOrder(i).isInitialized()) {
                    this.memoizedIsInitialized = 0;
                    return false;
                }
            }
            this.memoizedIsInitialized = 1;
            return true;
        }
        
        public void writeTo(final CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 0x1) == 0x1) {
                output.writeMessage(1, (MessageLite)this.getCollection());
            }
            if ((this.bitField0_ & 0x2) == 0x2) {
                output.writeEnum(2, this.dataModel_);
            }
            if ((this.bitField0_ & 0x4) == 0x4) {
                output.writeMessage(3, (MessageLite)this.getCriteria());
            }
            if ((this.bitField0_ & 0x8) == 0x8) {
                output.writeMessage(4, (MessageLite)this.getLimit());
            }
            for (int i = 0; i < this.order_.size(); ++i) {
                output.writeMessage(5, (MessageLite)this.order_.get(i));
            }
            for (int i = 0; i < this.args_.size(); ++i) {
                output.writeMessage(6, (MessageLite)this.args_.get(i));
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
                size += CodedOutputStream.computeMessageSize(1, (MessageLite)this.getCollection());
            }
            if ((this.bitField0_ & 0x2) == 0x2) {
                size += CodedOutputStream.computeEnumSize(2, this.dataModel_);
            }
            if ((this.bitField0_ & 0x4) == 0x4) {
                size += CodedOutputStream.computeMessageSize(3, (MessageLite)this.getCriteria());
            }
            if ((this.bitField0_ & 0x8) == 0x8) {
                size += CodedOutputStream.computeMessageSize(4, (MessageLite)this.getLimit());
            }
            for (int i = 0; i < this.order_.size(); ++i) {
                size += CodedOutputStream.computeMessageSize(5, (MessageLite)this.order_.get(i));
            }
            for (int i = 0; i < this.args_.size(); ++i) {
                size += CodedOutputStream.computeMessageSize(6, (MessageLite)this.args_.get(i));
            }
            size += this.unknownFields.getSerializedSize();
            return this.memoizedSize = size;
        }
        
        public boolean equals(final Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Delete)) {
                return super.equals(obj);
            }
            final Delete other = (Delete)obj;
            boolean result = true;
            result = (result && this.hasCollection() == other.hasCollection());
            if (this.hasCollection()) {
                result = (result && this.getCollection().equals(other.getCollection()));
            }
            result = (result && this.hasDataModel() == other.hasDataModel());
            if (this.hasDataModel()) {
                result = (result && this.dataModel_ == other.dataModel_);
            }
            result = (result && this.hasCriteria() == other.hasCriteria());
            if (this.hasCriteria()) {
                result = (result && this.getCriteria().equals(other.getCriteria()));
            }
            result = (result && this.getArgsList().equals(other.getArgsList()));
            result = (result && this.hasLimit() == other.hasLimit());
            if (this.hasLimit()) {
                result = (result && this.getLimit().equals(other.getLimit()));
            }
            result = (result && this.getOrderList().equals(other.getOrderList()));
            result = (result && this.unknownFields.equals((Object)other.unknownFields));
            return result;
        }
        
        public int hashCode() {
            if (this.memoizedHashCode != 0) {
                return this.memoizedHashCode;
            }
            int hash = 41;
            hash = 19 * hash + getDescriptor().hashCode();
            if (this.hasCollection()) {
                hash = 37 * hash + 1;
                hash = 53 * hash + this.getCollection().hashCode();
            }
            if (this.hasDataModel()) {
                hash = 37 * hash + 2;
                hash = 53 * hash + this.dataModel_;
            }
            if (this.hasCriteria()) {
                hash = 37 * hash + 3;
                hash = 53 * hash + this.getCriteria().hashCode();
            }
            if (this.getArgsCount() > 0) {
                hash = 37 * hash + 6;
                hash = 53 * hash + this.getArgsList().hashCode();
            }
            if (this.hasLimit()) {
                hash = 37 * hash + 4;
                hash = 53 * hash + this.getLimit().hashCode();
            }
            if (this.getOrderCount() > 0) {
                hash = 37 * hash + 5;
                hash = 53 * hash + this.getOrderList().hashCode();
            }
            hash = 29 * hash + this.unknownFields.hashCode();
            return this.memoizedHashCode = hash;
        }
        
        public static Delete parseFrom(final ByteBuffer data) throws InvalidProtocolBufferException {
            return (Delete)Delete.PARSER.parseFrom(data);
        }
        
        public static Delete parseFrom(final ByteBuffer data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Delete)Delete.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Delete parseFrom(final ByteString data) throws InvalidProtocolBufferException {
            return (Delete)Delete.PARSER.parseFrom(data);
        }
        
        public static Delete parseFrom(final ByteString data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Delete)Delete.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Delete parseFrom(final byte[] data) throws InvalidProtocolBufferException {
            return (Delete)Delete.PARSER.parseFrom(data);
        }
        
        public static Delete parseFrom(final byte[] data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Delete)Delete.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static Delete parseFrom(final InputStream input) throws IOException {
            return (Delete)GeneratedMessageV3.parseWithIOException((Parser)Delete.PARSER, input);
        }
        
        public static Delete parseFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Delete)GeneratedMessageV3.parseWithIOException((Parser)Delete.PARSER, input, extensionRegistry);
        }
        
        public static Delete parseDelimitedFrom(final InputStream input) throws IOException {
            return (Delete)GeneratedMessageV3.parseDelimitedWithIOException((Parser)Delete.PARSER, input);
        }
        
        public static Delete parseDelimitedFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Delete)GeneratedMessageV3.parseDelimitedWithIOException((Parser)Delete.PARSER, input, extensionRegistry);
        }
        
        public static Delete parseFrom(final CodedInputStream input) throws IOException {
            return (Delete)GeneratedMessageV3.parseWithIOException((Parser)Delete.PARSER, input);
        }
        
        public static Delete parseFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Delete)GeneratedMessageV3.parseWithIOException((Parser)Delete.PARSER, input, extensionRegistry);
        }
        
        public Builder newBuilderForType() {
            return newBuilder();
        }
        
        public static Builder newBuilder() {
            return Delete.DEFAULT_INSTANCE.toBuilder();
        }
        
        public static Builder newBuilder(final Delete prototype) {
            return Delete.DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }
        
        public Builder toBuilder() {
            return (this == Delete.DEFAULT_INSTANCE) ? new Builder() : new Builder().mergeFrom(this);
        }
        
        protected Builder newBuilderForType(final GeneratedMessageV3.BuilderParent parent) {
            final Builder builder = new Builder(parent);
            return builder;
        }
        
        public static Delete getDefaultInstance() {
            return Delete.DEFAULT_INSTANCE;
        }
        
        public static Parser<Delete> parser() {
            return Delete.PARSER;
        }
        
        public Parser<Delete> getParserForType() {
            return Delete.PARSER;
        }
        
        public Delete getDefaultInstanceForType() {
            return Delete.DEFAULT_INSTANCE;
        }
        
        static {
            DEFAULT_INSTANCE = new Delete();
            PARSER = (Parser)new AbstractParser<Delete>() {
                public Delete parsePartialFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new Delete(input, extensionRegistry);
                }
            };
        }
        
        public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements DeleteOrBuilder
        {
            private int bitField0_;
            private Collection collection_;
            private SingleFieldBuilderV3<Collection, Collection.Builder, CollectionOrBuilder> collectionBuilder_;
            private int dataModel_;
            private MysqlxExpr.Expr criteria_;
            private SingleFieldBuilderV3<MysqlxExpr.Expr, MysqlxExpr.Expr.Builder, MysqlxExpr.ExprOrBuilder> criteriaBuilder_;
            private List<MysqlxDatatypes.Scalar> args_;
            private RepeatedFieldBuilderV3<MysqlxDatatypes.Scalar, MysqlxDatatypes.Scalar.Builder, MysqlxDatatypes.ScalarOrBuilder> argsBuilder_;
            private Limit limit_;
            private SingleFieldBuilderV3<Limit, Limit.Builder, LimitOrBuilder> limitBuilder_;
            private List<Order> order_;
            private RepeatedFieldBuilderV3<Order, Order.Builder, OrderOrBuilder> orderBuilder_;
            
            public static final Descriptors.Descriptor getDescriptor() {
                return MysqlxCrud.internal_static_Mysqlx_Crud_Delete_descriptor;
            }
            
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return MysqlxCrud.internal_static_Mysqlx_Crud_Delete_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)Delete.class, (Class)Builder.class);
            }
            
            private Builder() {
                this.collection_ = null;
                this.dataModel_ = 1;
                this.criteria_ = null;
                this.args_ = Collections.emptyList();
                this.limit_ = null;
                this.order_ = Collections.emptyList();
                this.maybeForceBuilderInitialization();
            }
            
            private Builder(final GeneratedMessageV3.BuilderParent parent) {
                super(parent);
                this.collection_ = null;
                this.dataModel_ = 1;
                this.criteria_ = null;
                this.args_ = Collections.emptyList();
                this.limit_ = null;
                this.order_ = Collections.emptyList();
                this.maybeForceBuilderInitialization();
            }
            
            private void maybeForceBuilderInitialization() {
                if (Delete.alwaysUseFieldBuilders) {
                    this.getCollectionFieldBuilder();
                    this.getCriteriaFieldBuilder();
                    this.getArgsFieldBuilder();
                    this.getLimitFieldBuilder();
                    this.getOrderFieldBuilder();
                }
            }
            
            public Builder clear() {
                super.clear();
                if (this.collectionBuilder_ == null) {
                    this.collection_ = null;
                }
                else {
                    this.collectionBuilder_.clear();
                }
                this.bitField0_ &= 0xFFFFFFFE;
                this.dataModel_ = 1;
                this.bitField0_ &= 0xFFFFFFFD;
                if (this.criteriaBuilder_ == null) {
                    this.criteria_ = null;
                }
                else {
                    this.criteriaBuilder_.clear();
                }
                this.bitField0_ &= 0xFFFFFFFB;
                if (this.argsBuilder_ == null) {
                    this.args_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFF7;
                }
                else {
                    this.argsBuilder_.clear();
                }
                if (this.limitBuilder_ == null) {
                    this.limit_ = null;
                }
                else {
                    this.limitBuilder_.clear();
                }
                this.bitField0_ &= 0xFFFFFFEF;
                if (this.orderBuilder_ == null) {
                    this.order_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFDF;
                }
                else {
                    this.orderBuilder_.clear();
                }
                return this;
            }
            
            public Descriptors.Descriptor getDescriptorForType() {
                return MysqlxCrud.internal_static_Mysqlx_Crud_Delete_descriptor;
            }
            
            public Delete getDefaultInstanceForType() {
                return Delete.getDefaultInstance();
            }
            
            public Delete build() {
                final Delete result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw newUninitializedMessageException((Message)result);
                }
                return result;
            }
            
            public Delete buildPartial() {
                final Delete result = new Delete((GeneratedMessageV3.Builder)this);
                final int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 0x1) == 0x1) {
                    to_bitField0_ |= 0x1;
                }
                if (this.collectionBuilder_ == null) {
                    result.collection_ = this.collection_;
                }
                else {
                    result.collection_ = (Collection)this.collectionBuilder_.build();
                }
                if ((from_bitField0_ & 0x2) == 0x2) {
                    to_bitField0_ |= 0x2;
                }
                result.dataModel_ = this.dataModel_;
                if ((from_bitField0_ & 0x4) == 0x4) {
                    to_bitField0_ |= 0x4;
                }
                if (this.criteriaBuilder_ == null) {
                    result.criteria_ = this.criteria_;
                }
                else {
                    result.criteria_ = (MysqlxExpr.Expr)this.criteriaBuilder_.build();
                }
                if (this.argsBuilder_ == null) {
                    if ((this.bitField0_ & 0x8) == 0x8) {
                        this.args_ = Collections.unmodifiableList((List<? extends MysqlxDatatypes.Scalar>)this.args_);
                        this.bitField0_ &= 0xFFFFFFF7;
                    }
                    result.args_ = this.args_;
                }
                else {
                    result.args_ = (List<MysqlxDatatypes.Scalar>)this.argsBuilder_.build();
                }
                if ((from_bitField0_ & 0x10) == 0x10) {
                    to_bitField0_ |= 0x8;
                }
                if (this.limitBuilder_ == null) {
                    result.limit_ = this.limit_;
                }
                else {
                    result.limit_ = (Limit)this.limitBuilder_.build();
                }
                if (this.orderBuilder_ == null) {
                    if ((this.bitField0_ & 0x20) == 0x20) {
                        this.order_ = Collections.unmodifiableList((List<? extends Order>)this.order_);
                        this.bitField0_ &= 0xFFFFFFDF;
                    }
                    result.order_ = this.order_;
                }
                else {
                    result.order_ = (List<Order>)this.orderBuilder_.build();
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
                if (other instanceof Delete) {
                    return this.mergeFrom((Delete)other);
                }
                super.mergeFrom(other);
                return this;
            }
            
            public Builder mergeFrom(final Delete other) {
                if (other == Delete.getDefaultInstance()) {
                    return this;
                }
                if (other.hasCollection()) {
                    this.mergeCollection(other.getCollection());
                }
                if (other.hasDataModel()) {
                    this.setDataModel(other.getDataModel());
                }
                if (other.hasCriteria()) {
                    this.mergeCriteria(other.getCriteria());
                }
                if (this.argsBuilder_ == null) {
                    if (!other.args_.isEmpty()) {
                        if (this.args_.isEmpty()) {
                            this.args_ = other.args_;
                            this.bitField0_ &= 0xFFFFFFF7;
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
                        this.bitField0_ &= 0xFFFFFFF7;
                        this.argsBuilder_ = (Delete.alwaysUseFieldBuilders ? this.getArgsFieldBuilder() : null);
                    }
                    else {
                        this.argsBuilder_.addAllMessages((Iterable)other.args_);
                    }
                }
                if (other.hasLimit()) {
                    this.mergeLimit(other.getLimit());
                }
                if (this.orderBuilder_ == null) {
                    if (!other.order_.isEmpty()) {
                        if (this.order_.isEmpty()) {
                            this.order_ = other.order_;
                            this.bitField0_ &= 0xFFFFFFDF;
                        }
                        else {
                            this.ensureOrderIsMutable();
                            this.order_.addAll(other.order_);
                        }
                        this.onChanged();
                    }
                }
                else if (!other.order_.isEmpty()) {
                    if (this.orderBuilder_.isEmpty()) {
                        this.orderBuilder_.dispose();
                        this.orderBuilder_ = null;
                        this.order_ = other.order_;
                        this.bitField0_ &= 0xFFFFFFDF;
                        this.orderBuilder_ = (Delete.alwaysUseFieldBuilders ? this.getOrderFieldBuilder() : null);
                    }
                    else {
                        this.orderBuilder_.addAllMessages((Iterable)other.order_);
                    }
                }
                this.mergeUnknownFields(other.unknownFields);
                this.onChanged();
                return this;
            }
            
            public final boolean isInitialized() {
                if (!this.hasCollection()) {
                    return false;
                }
                if (!this.getCollection().isInitialized()) {
                    return false;
                }
                if (this.hasCriteria() && !this.getCriteria().isInitialized()) {
                    return false;
                }
                for (int i = 0; i < this.getArgsCount(); ++i) {
                    if (!this.getArgs(i).isInitialized()) {
                        return false;
                    }
                }
                if (this.hasLimit() && !this.getLimit().isInitialized()) {
                    return false;
                }
                for (int i = 0; i < this.getOrderCount(); ++i) {
                    if (!this.getOrder(i).isInitialized()) {
                        return false;
                    }
                }
                return true;
            }
            
            public Builder mergeFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
                Delete parsedMessage = null;
                try {
                    parsedMessage = (Delete)Delete.PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (Delete)e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                }
                finally {
                    if (parsedMessage != null) {
                        this.mergeFrom(parsedMessage);
                    }
                }
                return this;
            }
            
            public boolean hasCollection() {
                return (this.bitField0_ & 0x1) == 0x1;
            }
            
            public Collection getCollection() {
                if (this.collectionBuilder_ == null) {
                    return (this.collection_ == null) ? Collection.getDefaultInstance() : this.collection_;
                }
                return (Collection)this.collectionBuilder_.getMessage();
            }
            
            public Builder setCollection(final Collection value) {
                if (this.collectionBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.collection_ = value;
                    this.onChanged();
                }
                else {
                    this.collectionBuilder_.setMessage((AbstractMessage)value);
                }
                this.bitField0_ |= 0x1;
                return this;
            }
            
            public Builder setCollection(final Collection.Builder builderForValue) {
                if (this.collectionBuilder_ == null) {
                    this.collection_ = builderForValue.build();
                    this.onChanged();
                }
                else {
                    this.collectionBuilder_.setMessage((AbstractMessage)builderForValue.build());
                }
                this.bitField0_ |= 0x1;
                return this;
            }
            
            public Builder mergeCollection(final Collection value) {
                if (this.collectionBuilder_ == null) {
                    if ((this.bitField0_ & 0x1) == 0x1 && this.collection_ != null && this.collection_ != Collection.getDefaultInstance()) {
                        this.collection_ = Collection.newBuilder(this.collection_).mergeFrom(value).buildPartial();
                    }
                    else {
                        this.collection_ = value;
                    }
                    this.onChanged();
                }
                else {
                    this.collectionBuilder_.mergeFrom((AbstractMessage)value);
                }
                this.bitField0_ |= 0x1;
                return this;
            }
            
            public Builder clearCollection() {
                if (this.collectionBuilder_ == null) {
                    this.collection_ = null;
                    this.onChanged();
                }
                else {
                    this.collectionBuilder_.clear();
                }
                this.bitField0_ &= 0xFFFFFFFE;
                return this;
            }
            
            public Collection.Builder getCollectionBuilder() {
                this.bitField0_ |= 0x1;
                this.onChanged();
                return (Collection.Builder)this.getCollectionFieldBuilder().getBuilder();
            }
            
            public CollectionOrBuilder getCollectionOrBuilder() {
                if (this.collectionBuilder_ != null) {
                    return (CollectionOrBuilder)this.collectionBuilder_.getMessageOrBuilder();
                }
                return (this.collection_ == null) ? Collection.getDefaultInstance() : this.collection_;
            }
            
            private SingleFieldBuilderV3<Collection, Collection.Builder, CollectionOrBuilder> getCollectionFieldBuilder() {
                if (this.collectionBuilder_ == null) {
                    this.collectionBuilder_ = (SingleFieldBuilderV3<Collection, Collection.Builder, CollectionOrBuilder>)new SingleFieldBuilderV3((AbstractMessage)this.getCollection(), (AbstractMessage.BuilderParent)this.getParentForChildren(), this.isClean());
                    this.collection_ = null;
                }
                return this.collectionBuilder_;
            }
            
            public boolean hasDataModel() {
                return (this.bitField0_ & 0x2) == 0x2;
            }
            
            public DataModel getDataModel() {
                final DataModel result = DataModel.valueOf(this.dataModel_);
                return (result == null) ? DataModel.DOCUMENT : result;
            }
            
            public Builder setDataModel(final DataModel value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 0x2;
                this.dataModel_ = value.getNumber();
                this.onChanged();
                return this;
            }
            
            public Builder clearDataModel() {
                this.bitField0_ &= 0xFFFFFFFD;
                this.dataModel_ = 1;
                this.onChanged();
                return this;
            }
            
            public boolean hasCriteria() {
                return (this.bitField0_ & 0x4) == 0x4;
            }
            
            public MysqlxExpr.Expr getCriteria() {
                if (this.criteriaBuilder_ == null) {
                    return (this.criteria_ == null) ? MysqlxExpr.Expr.getDefaultInstance() : this.criteria_;
                }
                return (MysqlxExpr.Expr)this.criteriaBuilder_.getMessage();
            }
            
            public Builder setCriteria(final MysqlxExpr.Expr value) {
                if (this.criteriaBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.criteria_ = value;
                    this.onChanged();
                }
                else {
                    this.criteriaBuilder_.setMessage((AbstractMessage)value);
                }
                this.bitField0_ |= 0x4;
                return this;
            }
            
            public Builder setCriteria(final MysqlxExpr.Expr.Builder builderForValue) {
                if (this.criteriaBuilder_ == null) {
                    this.criteria_ = builderForValue.build();
                    this.onChanged();
                }
                else {
                    this.criteriaBuilder_.setMessage((AbstractMessage)builderForValue.build());
                }
                this.bitField0_ |= 0x4;
                return this;
            }
            
            public Builder mergeCriteria(final MysqlxExpr.Expr value) {
                if (this.criteriaBuilder_ == null) {
                    if ((this.bitField0_ & 0x4) == 0x4 && this.criteria_ != null && this.criteria_ != MysqlxExpr.Expr.getDefaultInstance()) {
                        this.criteria_ = MysqlxExpr.Expr.newBuilder(this.criteria_).mergeFrom(value).buildPartial();
                    }
                    else {
                        this.criteria_ = value;
                    }
                    this.onChanged();
                }
                else {
                    this.criteriaBuilder_.mergeFrom((AbstractMessage)value);
                }
                this.bitField0_ |= 0x4;
                return this;
            }
            
            public Builder clearCriteria() {
                if (this.criteriaBuilder_ == null) {
                    this.criteria_ = null;
                    this.onChanged();
                }
                else {
                    this.criteriaBuilder_.clear();
                }
                this.bitField0_ &= 0xFFFFFFFB;
                return this;
            }
            
            public MysqlxExpr.Expr.Builder getCriteriaBuilder() {
                this.bitField0_ |= 0x4;
                this.onChanged();
                return (MysqlxExpr.Expr.Builder)this.getCriteriaFieldBuilder().getBuilder();
            }
            
            public MysqlxExpr.ExprOrBuilder getCriteriaOrBuilder() {
                if (this.criteriaBuilder_ != null) {
                    return (MysqlxExpr.ExprOrBuilder)this.criteriaBuilder_.getMessageOrBuilder();
                }
                return (this.criteria_ == null) ? MysqlxExpr.Expr.getDefaultInstance() : this.criteria_;
            }
            
            private SingleFieldBuilderV3<MysqlxExpr.Expr, MysqlxExpr.Expr.Builder, MysqlxExpr.ExprOrBuilder> getCriteriaFieldBuilder() {
                if (this.criteriaBuilder_ == null) {
                    this.criteriaBuilder_ = (SingleFieldBuilderV3<MysqlxExpr.Expr, MysqlxExpr.Expr.Builder, MysqlxExpr.ExprOrBuilder>)new SingleFieldBuilderV3((AbstractMessage)this.getCriteria(), (AbstractMessage.BuilderParent)this.getParentForChildren(), this.isClean());
                    this.criteria_ = null;
                }
                return this.criteriaBuilder_;
            }
            
            private void ensureArgsIsMutable() {
                if ((this.bitField0_ & 0x8) != 0x8) {
                    this.args_ = new ArrayList<MysqlxDatatypes.Scalar>(this.args_);
                    this.bitField0_ |= 0x8;
                }
            }
            
            public List<MysqlxDatatypes.Scalar> getArgsList() {
                if (this.argsBuilder_ == null) {
                    return Collections.unmodifiableList((List<? extends MysqlxDatatypes.Scalar>)this.args_);
                }
                return (List<MysqlxDatatypes.Scalar>)this.argsBuilder_.getMessageList();
            }
            
            public int getArgsCount() {
                if (this.argsBuilder_ == null) {
                    return this.args_.size();
                }
                return this.argsBuilder_.getCount();
            }
            
            public MysqlxDatatypes.Scalar getArgs(final int index) {
                if (this.argsBuilder_ == null) {
                    return this.args_.get(index);
                }
                return (MysqlxDatatypes.Scalar)this.argsBuilder_.getMessage(index);
            }
            
            public Builder setArgs(final int index, final MysqlxDatatypes.Scalar value) {
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
            
            public Builder setArgs(final int index, final MysqlxDatatypes.Scalar.Builder builderForValue) {
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
            
            public Builder addArgs(final MysqlxDatatypes.Scalar value) {
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
            
            public Builder addArgs(final int index, final MysqlxDatatypes.Scalar value) {
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
            
            public Builder addArgs(final MysqlxDatatypes.Scalar.Builder builderForValue) {
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
            
            public Builder addArgs(final int index, final MysqlxDatatypes.Scalar.Builder builderForValue) {
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
            
            public Builder addAllArgs(final Iterable<? extends MysqlxDatatypes.Scalar> values) {
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
                    this.bitField0_ &= 0xFFFFFFF7;
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
            
            public MysqlxDatatypes.Scalar.Builder getArgsBuilder(final int index) {
                return (MysqlxDatatypes.Scalar.Builder)this.getArgsFieldBuilder().getBuilder(index);
            }
            
            public MysqlxDatatypes.ScalarOrBuilder getArgsOrBuilder(final int index) {
                if (this.argsBuilder_ == null) {
                    return this.args_.get(index);
                }
                return (MysqlxDatatypes.ScalarOrBuilder)this.argsBuilder_.getMessageOrBuilder(index);
            }
            
            public List<? extends MysqlxDatatypes.ScalarOrBuilder> getArgsOrBuilderList() {
                if (this.argsBuilder_ != null) {
                    return (List<? extends MysqlxDatatypes.ScalarOrBuilder>)this.argsBuilder_.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList((List<? extends MysqlxDatatypes.ScalarOrBuilder>)this.args_);
            }
            
            public MysqlxDatatypes.Scalar.Builder addArgsBuilder() {
                return (MysqlxDatatypes.Scalar.Builder)this.getArgsFieldBuilder().addBuilder((AbstractMessage)MysqlxDatatypes.Scalar.getDefaultInstance());
            }
            
            public MysqlxDatatypes.Scalar.Builder addArgsBuilder(final int index) {
                return (MysqlxDatatypes.Scalar.Builder)this.getArgsFieldBuilder().addBuilder(index, (AbstractMessage)MysqlxDatatypes.Scalar.getDefaultInstance());
            }
            
            public List<MysqlxDatatypes.Scalar.Builder> getArgsBuilderList() {
                return (List<MysqlxDatatypes.Scalar.Builder>)this.getArgsFieldBuilder().getBuilderList();
            }
            
            private RepeatedFieldBuilderV3<MysqlxDatatypes.Scalar, MysqlxDatatypes.Scalar.Builder, MysqlxDatatypes.ScalarOrBuilder> getArgsFieldBuilder() {
                if (this.argsBuilder_ == null) {
                    this.argsBuilder_ = (RepeatedFieldBuilderV3<MysqlxDatatypes.Scalar, MysqlxDatatypes.Scalar.Builder, MysqlxDatatypes.ScalarOrBuilder>)new RepeatedFieldBuilderV3((List)this.args_, (this.bitField0_ & 0x8) == 0x8, (AbstractMessage.BuilderParent)this.getParentForChildren(), this.isClean());
                    this.args_ = null;
                }
                return this.argsBuilder_;
            }
            
            public boolean hasLimit() {
                return (this.bitField0_ & 0x10) == 0x10;
            }
            
            public Limit getLimit() {
                if (this.limitBuilder_ == null) {
                    return (this.limit_ == null) ? Limit.getDefaultInstance() : this.limit_;
                }
                return (Limit)this.limitBuilder_.getMessage();
            }
            
            public Builder setLimit(final Limit value) {
                if (this.limitBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.limit_ = value;
                    this.onChanged();
                }
                else {
                    this.limitBuilder_.setMessage((AbstractMessage)value);
                }
                this.bitField0_ |= 0x10;
                return this;
            }
            
            public Builder setLimit(final Limit.Builder builderForValue) {
                if (this.limitBuilder_ == null) {
                    this.limit_ = builderForValue.build();
                    this.onChanged();
                }
                else {
                    this.limitBuilder_.setMessage((AbstractMessage)builderForValue.build());
                }
                this.bitField0_ |= 0x10;
                return this;
            }
            
            public Builder mergeLimit(final Limit value) {
                if (this.limitBuilder_ == null) {
                    if ((this.bitField0_ & 0x10) == 0x10 && this.limit_ != null && this.limit_ != Limit.getDefaultInstance()) {
                        this.limit_ = Limit.newBuilder(this.limit_).mergeFrom(value).buildPartial();
                    }
                    else {
                        this.limit_ = value;
                    }
                    this.onChanged();
                }
                else {
                    this.limitBuilder_.mergeFrom((AbstractMessage)value);
                }
                this.bitField0_ |= 0x10;
                return this;
            }
            
            public Builder clearLimit() {
                if (this.limitBuilder_ == null) {
                    this.limit_ = null;
                    this.onChanged();
                }
                else {
                    this.limitBuilder_.clear();
                }
                this.bitField0_ &= 0xFFFFFFEF;
                return this;
            }
            
            public Limit.Builder getLimitBuilder() {
                this.bitField0_ |= 0x10;
                this.onChanged();
                return (Limit.Builder)this.getLimitFieldBuilder().getBuilder();
            }
            
            public LimitOrBuilder getLimitOrBuilder() {
                if (this.limitBuilder_ != null) {
                    return (LimitOrBuilder)this.limitBuilder_.getMessageOrBuilder();
                }
                return (this.limit_ == null) ? Limit.getDefaultInstance() : this.limit_;
            }
            
            private SingleFieldBuilderV3<Limit, Limit.Builder, LimitOrBuilder> getLimitFieldBuilder() {
                if (this.limitBuilder_ == null) {
                    this.limitBuilder_ = (SingleFieldBuilderV3<Limit, Limit.Builder, LimitOrBuilder>)new SingleFieldBuilderV3((AbstractMessage)this.getLimit(), (AbstractMessage.BuilderParent)this.getParentForChildren(), this.isClean());
                    this.limit_ = null;
                }
                return this.limitBuilder_;
            }
            
            private void ensureOrderIsMutable() {
                if ((this.bitField0_ & 0x20) != 0x20) {
                    this.order_ = new ArrayList<Order>(this.order_);
                    this.bitField0_ |= 0x20;
                }
            }
            
            public List<Order> getOrderList() {
                if (this.orderBuilder_ == null) {
                    return Collections.unmodifiableList((List<? extends Order>)this.order_);
                }
                return (List<Order>)this.orderBuilder_.getMessageList();
            }
            
            public int getOrderCount() {
                if (this.orderBuilder_ == null) {
                    return this.order_.size();
                }
                return this.orderBuilder_.getCount();
            }
            
            public Order getOrder(final int index) {
                if (this.orderBuilder_ == null) {
                    return this.order_.get(index);
                }
                return (Order)this.orderBuilder_.getMessage(index);
            }
            
            public Builder setOrder(final int index, final Order value) {
                if (this.orderBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureOrderIsMutable();
                    this.order_.set(index, value);
                    this.onChanged();
                }
                else {
                    this.orderBuilder_.setMessage(index, (AbstractMessage)value);
                }
                return this;
            }
            
            public Builder setOrder(final int index, final Order.Builder builderForValue) {
                if (this.orderBuilder_ == null) {
                    this.ensureOrderIsMutable();
                    this.order_.set(index, builderForValue.build());
                    this.onChanged();
                }
                else {
                    this.orderBuilder_.setMessage(index, (AbstractMessage)builderForValue.build());
                }
                return this;
            }
            
            public Builder addOrder(final Order value) {
                if (this.orderBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureOrderIsMutable();
                    this.order_.add(value);
                    this.onChanged();
                }
                else {
                    this.orderBuilder_.addMessage((AbstractMessage)value);
                }
                return this;
            }
            
            public Builder addOrder(final int index, final Order value) {
                if (this.orderBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureOrderIsMutable();
                    this.order_.add(index, value);
                    this.onChanged();
                }
                else {
                    this.orderBuilder_.addMessage(index, (AbstractMessage)value);
                }
                return this;
            }
            
            public Builder addOrder(final Order.Builder builderForValue) {
                if (this.orderBuilder_ == null) {
                    this.ensureOrderIsMutable();
                    this.order_.add(builderForValue.build());
                    this.onChanged();
                }
                else {
                    this.orderBuilder_.addMessage((AbstractMessage)builderForValue.build());
                }
                return this;
            }
            
            public Builder addOrder(final int index, final Order.Builder builderForValue) {
                if (this.orderBuilder_ == null) {
                    this.ensureOrderIsMutable();
                    this.order_.add(index, builderForValue.build());
                    this.onChanged();
                }
                else {
                    this.orderBuilder_.addMessage(index, (AbstractMessage)builderForValue.build());
                }
                return this;
            }
            
            public Builder addAllOrder(final Iterable<? extends Order> values) {
                if (this.orderBuilder_ == null) {
                    this.ensureOrderIsMutable();
                    AbstractMessageLite.Builder.addAll((Iterable)values, (List)this.order_);
                    this.onChanged();
                }
                else {
                    this.orderBuilder_.addAllMessages((Iterable)values);
                }
                return this;
            }
            
            public Builder clearOrder() {
                if (this.orderBuilder_ == null) {
                    this.order_ = Collections.emptyList();
                    this.bitField0_ &= 0xFFFFFFDF;
                    this.onChanged();
                }
                else {
                    this.orderBuilder_.clear();
                }
                return this;
            }
            
            public Builder removeOrder(final int index) {
                if (this.orderBuilder_ == null) {
                    this.ensureOrderIsMutable();
                    this.order_.remove(index);
                    this.onChanged();
                }
                else {
                    this.orderBuilder_.remove(index);
                }
                return this;
            }
            
            public Order.Builder getOrderBuilder(final int index) {
                return (Order.Builder)this.getOrderFieldBuilder().getBuilder(index);
            }
            
            public OrderOrBuilder getOrderOrBuilder(final int index) {
                if (this.orderBuilder_ == null) {
                    return this.order_.get(index);
                }
                return (OrderOrBuilder)this.orderBuilder_.getMessageOrBuilder(index);
            }
            
            public List<? extends OrderOrBuilder> getOrderOrBuilderList() {
                if (this.orderBuilder_ != null) {
                    return (List<? extends OrderOrBuilder>)this.orderBuilder_.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList((List<? extends OrderOrBuilder>)this.order_);
            }
            
            public Order.Builder addOrderBuilder() {
                return (Order.Builder)this.getOrderFieldBuilder().addBuilder((AbstractMessage)Order.getDefaultInstance());
            }
            
            public Order.Builder addOrderBuilder(final int index) {
                return (Order.Builder)this.getOrderFieldBuilder().addBuilder(index, (AbstractMessage)Order.getDefaultInstance());
            }
            
            public List<Order.Builder> getOrderBuilderList() {
                return (List<Order.Builder>)this.getOrderFieldBuilder().getBuilderList();
            }
            
            private RepeatedFieldBuilderV3<Order, Order.Builder, OrderOrBuilder> getOrderFieldBuilder() {
                if (this.orderBuilder_ == null) {
                    this.orderBuilder_ = (RepeatedFieldBuilderV3<Order, Order.Builder, OrderOrBuilder>)new RepeatedFieldBuilderV3((List)this.order_, (this.bitField0_ & 0x20) == 0x20, (AbstractMessage.BuilderParent)this.getParentForChildren(), this.isClean());
                    this.order_ = null;
                }
                return this.orderBuilder_;
            }
            
            public final Builder setUnknownFields(final UnknownFieldSet unknownFields) {
                return (Builder)super.setUnknownFields(unknownFields);
            }
            
            public final Builder mergeUnknownFields(final UnknownFieldSet unknownFields) {
                return (Builder)super.mergeUnknownFields(unknownFields);
            }
        }
    }
    
    public static final class CreateView extends GeneratedMessageV3 implements CreateViewOrBuilder
    {
        private static final long serialVersionUID = 0L;
        private int bitField0_;
        public static final int COLLECTION_FIELD_NUMBER = 1;
        private Collection collection_;
        public static final int DEFINER_FIELD_NUMBER = 2;
        private volatile Object definer_;
        public static final int ALGORITHM_FIELD_NUMBER = 3;
        private int algorithm_;
        public static final int SECURITY_FIELD_NUMBER = 4;
        private int security_;
        public static final int CHECK_FIELD_NUMBER = 5;
        private int check_;
        public static final int COLUMN_FIELD_NUMBER = 6;
        private LazyStringList column_;
        public static final int STMT_FIELD_NUMBER = 7;
        private Find stmt_;
        public static final int REPLACE_EXISTING_FIELD_NUMBER = 8;
        private boolean replaceExisting_;
        private byte memoizedIsInitialized;
        private static final CreateView DEFAULT_INSTANCE;
        @Deprecated
        public static final Parser<CreateView> PARSER;
        
        private CreateView(final GeneratedMessageV3.Builder<?> builder) {
            super((GeneratedMessageV3.Builder)builder);
            this.memoizedIsInitialized = -1;
        }
        
        private CreateView() {
            this.memoizedIsInitialized = -1;
            this.definer_ = "";
            this.algorithm_ = 1;
            this.security_ = 2;
            this.check_ = 1;
            this.column_ = LazyStringArrayList.EMPTY;
            this.replaceExisting_ = false;
        }
        
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }
        
        private CreateView(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            Collection.Builder subBuilder = null;
                            if ((this.bitField0_ & 0x1) == 0x1) {
                                subBuilder = this.collection_.toBuilder();
                            }
                            this.collection_ = (Collection)input.readMessage((Parser)Collection.PARSER, extensionRegistry);
                            if (subBuilder != null) {
                                subBuilder.mergeFrom(this.collection_);
                                this.collection_ = subBuilder.buildPartial();
                            }
                            this.bitField0_ |= 0x1;
                            continue;
                        }
                        case 18: {
                            final ByteString bs = input.readBytes();
                            this.bitField0_ |= 0x2;
                            this.definer_ = bs;
                            continue;
                        }
                        case 24: {
                            final int rawValue = input.readEnum();
                            final ViewAlgorithm value = ViewAlgorithm.valueOf(rawValue);
                            if (value == null) {
                                unknownFields.mergeVarintField(3, rawValue);
                                continue;
                            }
                            this.bitField0_ |= 0x4;
                            this.algorithm_ = rawValue;
                            continue;
                        }
                        case 32: {
                            final int rawValue = input.readEnum();
                            final ViewSqlSecurity value2 = ViewSqlSecurity.valueOf(rawValue);
                            if (value2 == null) {
                                unknownFields.mergeVarintField(4, rawValue);
                                continue;
                            }
                            this.bitField0_ |= 0x8;
                            this.security_ = rawValue;
                            continue;
                        }
                        case 40: {
                            final int rawValue = input.readEnum();
                            final ViewCheckOption value3 = ViewCheckOption.valueOf(rawValue);
                            if (value3 == null) {
                                unknownFields.mergeVarintField(5, rawValue);
                                continue;
                            }
                            this.bitField0_ |= 0x10;
                            this.check_ = rawValue;
                            continue;
                        }
                        case 50: {
                            final ByteString bs = input.readBytes();
                            if ((mutable_bitField0_ & 0x20) != 0x20) {
                                this.column_ = (LazyStringList)new LazyStringArrayList();
                                mutable_bitField0_ |= 0x20;
                            }
                            this.column_.add(bs);
                            continue;
                        }
                        case 58: {
                            Find.Builder subBuilder2 = null;
                            if ((this.bitField0_ & 0x20) == 0x20) {
                                subBuilder2 = this.stmt_.toBuilder();
                            }
                            this.stmt_ = (Find)input.readMessage((Parser)Find.PARSER, extensionRegistry);
                            if (subBuilder2 != null) {
                                subBuilder2.mergeFrom(this.stmt_);
                                this.stmt_ = subBuilder2.buildPartial();
                            }
                            this.bitField0_ |= 0x20;
                            continue;
                        }
                        case 64: {
                            this.bitField0_ |= 0x40;
                            this.replaceExisting_ = input.readBool();
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
                if ((mutable_bitField0_ & 0x20) == 0x20) {
                    this.column_ = this.column_.getUnmodifiableView();
                }
                this.unknownFields = unknownFields.build();
                this.makeExtensionsImmutable();
            }
        }
        
        public static final Descriptors.Descriptor getDescriptor() {
            return MysqlxCrud.internal_static_Mysqlx_Crud_CreateView_descriptor;
        }
        
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return MysqlxCrud.internal_static_Mysqlx_Crud_CreateView_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)CreateView.class, (Class)Builder.class);
        }
        
        public boolean hasCollection() {
            return (this.bitField0_ & 0x1) == 0x1;
        }
        
        public Collection getCollection() {
            return (this.collection_ == null) ? Collection.getDefaultInstance() : this.collection_;
        }
        
        public CollectionOrBuilder getCollectionOrBuilder() {
            return (this.collection_ == null) ? Collection.getDefaultInstance() : this.collection_;
        }
        
        public boolean hasDefiner() {
            return (this.bitField0_ & 0x2) == 0x2;
        }
        
        public String getDefiner() {
            final Object ref = this.definer_;
            if (ref instanceof String) {
                return (String)ref;
            }
            final ByteString bs = (ByteString)ref;
            final String s = bs.toStringUtf8();
            if (bs.isValidUtf8()) {
                this.definer_ = s;
            }
            return s;
        }
        
        public ByteString getDefinerBytes() {
            final Object ref = this.definer_;
            if (ref instanceof String) {
                final ByteString b = ByteString.copyFromUtf8((String)ref);
                return (ByteString)(this.definer_ = b);
            }
            return (ByteString)ref;
        }
        
        public boolean hasAlgorithm() {
            return (this.bitField0_ & 0x4) == 0x4;
        }
        
        public ViewAlgorithm getAlgorithm() {
            final ViewAlgorithm result = ViewAlgorithm.valueOf(this.algorithm_);
            return (result == null) ? ViewAlgorithm.UNDEFINED : result;
        }
        
        public boolean hasSecurity() {
            return (this.bitField0_ & 0x8) == 0x8;
        }
        
        public ViewSqlSecurity getSecurity() {
            final ViewSqlSecurity result = ViewSqlSecurity.valueOf(this.security_);
            return (result == null) ? ViewSqlSecurity.DEFINER : result;
        }
        
        public boolean hasCheck() {
            return (this.bitField0_ & 0x10) == 0x10;
        }
        
        public ViewCheckOption getCheck() {
            final ViewCheckOption result = ViewCheckOption.valueOf(this.check_);
            return (result == null) ? ViewCheckOption.LOCAL : result;
        }
        
        public ProtocolStringList getColumnList() {
            return (ProtocolStringList)this.column_;
        }
        
        public int getColumnCount() {
            return this.column_.size();
        }
        
        public String getColumn(final int index) {
            return (String)this.column_.get(index);
        }
        
        public ByteString getColumnBytes(final int index) {
            return this.column_.getByteString(index);
        }
        
        public boolean hasStmt() {
            return (this.bitField0_ & 0x20) == 0x20;
        }
        
        public Find getStmt() {
            return (this.stmt_ == null) ? Find.getDefaultInstance() : this.stmt_;
        }
        
        public FindOrBuilder getStmtOrBuilder() {
            return (this.stmt_ == null) ? Find.getDefaultInstance() : this.stmt_;
        }
        
        public boolean hasReplaceExisting() {
            return (this.bitField0_ & 0x40) == 0x40;
        }
        
        public boolean getReplaceExisting() {
            return this.replaceExisting_;
        }
        
        public final boolean isInitialized() {
            final byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (!this.hasCollection()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            if (!this.hasStmt()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            if (!this.getCollection().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            if (!this.getStmt().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            this.memoizedIsInitialized = 1;
            return true;
        }
        
        public void writeTo(final CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 0x1) == 0x1) {
                output.writeMessage(1, (MessageLite)this.getCollection());
            }
            if ((this.bitField0_ & 0x2) == 0x2) {
                GeneratedMessageV3.writeString(output, 2, this.definer_);
            }
            if ((this.bitField0_ & 0x4) == 0x4) {
                output.writeEnum(3, this.algorithm_);
            }
            if ((this.bitField0_ & 0x8) == 0x8) {
                output.writeEnum(4, this.security_);
            }
            if ((this.bitField0_ & 0x10) == 0x10) {
                output.writeEnum(5, this.check_);
            }
            for (int i = 0; i < this.column_.size(); ++i) {
                GeneratedMessageV3.writeString(output, 6, this.column_.getRaw(i));
            }
            if ((this.bitField0_ & 0x20) == 0x20) {
                output.writeMessage(7, (MessageLite)this.getStmt());
            }
            if ((this.bitField0_ & 0x40) == 0x40) {
                output.writeBool(8, this.replaceExisting_);
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
                size += CodedOutputStream.computeMessageSize(1, (MessageLite)this.getCollection());
            }
            if ((this.bitField0_ & 0x2) == 0x2) {
                size += GeneratedMessageV3.computeStringSize(2, this.definer_);
            }
            if ((this.bitField0_ & 0x4) == 0x4) {
                size += CodedOutputStream.computeEnumSize(3, this.algorithm_);
            }
            if ((this.bitField0_ & 0x8) == 0x8) {
                size += CodedOutputStream.computeEnumSize(4, this.security_);
            }
            if ((this.bitField0_ & 0x10) == 0x10) {
                size += CodedOutputStream.computeEnumSize(5, this.check_);
            }
            int dataSize = 0;
            for (int i = 0; i < this.column_.size(); ++i) {
                dataSize += computeStringSizeNoTag(this.column_.getRaw(i));
            }
            size += dataSize;
            size += 1 * this.getColumnList().size();
            if ((this.bitField0_ & 0x20) == 0x20) {
                size += CodedOutputStream.computeMessageSize(7, (MessageLite)this.getStmt());
            }
            if ((this.bitField0_ & 0x40) == 0x40) {
                size += CodedOutputStream.computeBoolSize(8, this.replaceExisting_);
            }
            size += this.unknownFields.getSerializedSize();
            return this.memoizedSize = size;
        }
        
        public boolean equals(final Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof CreateView)) {
                return super.equals(obj);
            }
            final CreateView other = (CreateView)obj;
            boolean result = true;
            result = (result && this.hasCollection() == other.hasCollection());
            if (this.hasCollection()) {
                result = (result && this.getCollection().equals(other.getCollection()));
            }
            result = (result && this.hasDefiner() == other.hasDefiner());
            if (this.hasDefiner()) {
                result = (result && this.getDefiner().equals(other.getDefiner()));
            }
            result = (result && this.hasAlgorithm() == other.hasAlgorithm());
            if (this.hasAlgorithm()) {
                result = (result && this.algorithm_ == other.algorithm_);
            }
            result = (result && this.hasSecurity() == other.hasSecurity());
            if (this.hasSecurity()) {
                result = (result && this.security_ == other.security_);
            }
            result = (result && this.hasCheck() == other.hasCheck());
            if (this.hasCheck()) {
                result = (result && this.check_ == other.check_);
            }
            result = (result && this.getColumnList().equals(other.getColumnList()));
            result = (result && this.hasStmt() == other.hasStmt());
            if (this.hasStmt()) {
                result = (result && this.getStmt().equals(other.getStmt()));
            }
            result = (result && this.hasReplaceExisting() == other.hasReplaceExisting());
            if (this.hasReplaceExisting()) {
                result = (result && this.getReplaceExisting() == other.getReplaceExisting());
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
            if (this.hasCollection()) {
                hash = 37 * hash + 1;
                hash = 53 * hash + this.getCollection().hashCode();
            }
            if (this.hasDefiner()) {
                hash = 37 * hash + 2;
                hash = 53 * hash + this.getDefiner().hashCode();
            }
            if (this.hasAlgorithm()) {
                hash = 37 * hash + 3;
                hash = 53 * hash + this.algorithm_;
            }
            if (this.hasSecurity()) {
                hash = 37 * hash + 4;
                hash = 53 * hash + this.security_;
            }
            if (this.hasCheck()) {
                hash = 37 * hash + 5;
                hash = 53 * hash + this.check_;
            }
            if (this.getColumnCount() > 0) {
                hash = 37 * hash + 6;
                hash = 53 * hash + this.getColumnList().hashCode();
            }
            if (this.hasStmt()) {
                hash = 37 * hash + 7;
                hash = 53 * hash + this.getStmt().hashCode();
            }
            if (this.hasReplaceExisting()) {
                hash = 37 * hash + 8;
                hash = 53 * hash + Internal.hashBoolean(this.getReplaceExisting());
            }
            hash = 29 * hash + this.unknownFields.hashCode();
            return this.memoizedHashCode = hash;
        }
        
        public static CreateView parseFrom(final ByteBuffer data) throws InvalidProtocolBufferException {
            return (CreateView)CreateView.PARSER.parseFrom(data);
        }
        
        public static CreateView parseFrom(final ByteBuffer data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (CreateView)CreateView.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static CreateView parseFrom(final ByteString data) throws InvalidProtocolBufferException {
            return (CreateView)CreateView.PARSER.parseFrom(data);
        }
        
        public static CreateView parseFrom(final ByteString data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (CreateView)CreateView.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static CreateView parseFrom(final byte[] data) throws InvalidProtocolBufferException {
            return (CreateView)CreateView.PARSER.parseFrom(data);
        }
        
        public static CreateView parseFrom(final byte[] data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (CreateView)CreateView.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static CreateView parseFrom(final InputStream input) throws IOException {
            return (CreateView)GeneratedMessageV3.parseWithIOException((Parser)CreateView.PARSER, input);
        }
        
        public static CreateView parseFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (CreateView)GeneratedMessageV3.parseWithIOException((Parser)CreateView.PARSER, input, extensionRegistry);
        }
        
        public static CreateView parseDelimitedFrom(final InputStream input) throws IOException {
            return (CreateView)GeneratedMessageV3.parseDelimitedWithIOException((Parser)CreateView.PARSER, input);
        }
        
        public static CreateView parseDelimitedFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (CreateView)GeneratedMessageV3.parseDelimitedWithIOException((Parser)CreateView.PARSER, input, extensionRegistry);
        }
        
        public static CreateView parseFrom(final CodedInputStream input) throws IOException {
            return (CreateView)GeneratedMessageV3.parseWithIOException((Parser)CreateView.PARSER, input);
        }
        
        public static CreateView parseFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (CreateView)GeneratedMessageV3.parseWithIOException((Parser)CreateView.PARSER, input, extensionRegistry);
        }
        
        public Builder newBuilderForType() {
            return newBuilder();
        }
        
        public static Builder newBuilder() {
            return CreateView.DEFAULT_INSTANCE.toBuilder();
        }
        
        public static Builder newBuilder(final CreateView prototype) {
            return CreateView.DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }
        
        public Builder toBuilder() {
            return (this == CreateView.DEFAULT_INSTANCE) ? new Builder() : new Builder().mergeFrom(this);
        }
        
        protected Builder newBuilderForType(final GeneratedMessageV3.BuilderParent parent) {
            final Builder builder = new Builder(parent);
            return builder;
        }
        
        public static CreateView getDefaultInstance() {
            return CreateView.DEFAULT_INSTANCE;
        }
        
        public static Parser<CreateView> parser() {
            return CreateView.PARSER;
        }
        
        public Parser<CreateView> getParserForType() {
            return CreateView.PARSER;
        }
        
        public CreateView getDefaultInstanceForType() {
            return CreateView.DEFAULT_INSTANCE;
        }
        
        static {
            DEFAULT_INSTANCE = new CreateView();
            PARSER = (Parser)new AbstractParser<CreateView>() {
                public CreateView parsePartialFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new CreateView(input, extensionRegistry);
                }
            };
        }
        
        public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements CreateViewOrBuilder
        {
            private int bitField0_;
            private Collection collection_;
            private SingleFieldBuilderV3<Collection, Collection.Builder, CollectionOrBuilder> collectionBuilder_;
            private Object definer_;
            private int algorithm_;
            private int security_;
            private int check_;
            private LazyStringList column_;
            private Find stmt_;
            private SingleFieldBuilderV3<Find, Find.Builder, FindOrBuilder> stmtBuilder_;
            private boolean replaceExisting_;
            
            public static final Descriptors.Descriptor getDescriptor() {
                return MysqlxCrud.internal_static_Mysqlx_Crud_CreateView_descriptor;
            }
            
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return MysqlxCrud.internal_static_Mysqlx_Crud_CreateView_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)CreateView.class, (Class)Builder.class);
            }
            
            private Builder() {
                this.collection_ = null;
                this.definer_ = "";
                this.algorithm_ = 1;
                this.security_ = 2;
                this.check_ = 1;
                this.column_ = LazyStringArrayList.EMPTY;
                this.stmt_ = null;
                this.maybeForceBuilderInitialization();
            }
            
            private Builder(final GeneratedMessageV3.BuilderParent parent) {
                super(parent);
                this.collection_ = null;
                this.definer_ = "";
                this.algorithm_ = 1;
                this.security_ = 2;
                this.check_ = 1;
                this.column_ = LazyStringArrayList.EMPTY;
                this.stmt_ = null;
                this.maybeForceBuilderInitialization();
            }
            
            private void maybeForceBuilderInitialization() {
                if (CreateView.alwaysUseFieldBuilders) {
                    this.getCollectionFieldBuilder();
                    this.getStmtFieldBuilder();
                }
            }
            
            public Builder clear() {
                super.clear();
                if (this.collectionBuilder_ == null) {
                    this.collection_ = null;
                }
                else {
                    this.collectionBuilder_.clear();
                }
                this.bitField0_ &= 0xFFFFFFFE;
                this.definer_ = "";
                this.bitField0_ &= 0xFFFFFFFD;
                this.algorithm_ = 1;
                this.bitField0_ &= 0xFFFFFFFB;
                this.security_ = 2;
                this.bitField0_ &= 0xFFFFFFF7;
                this.check_ = 1;
                this.bitField0_ &= 0xFFFFFFEF;
                this.column_ = LazyStringArrayList.EMPTY;
                this.bitField0_ &= 0xFFFFFFDF;
                if (this.stmtBuilder_ == null) {
                    this.stmt_ = null;
                }
                else {
                    this.stmtBuilder_.clear();
                }
                this.bitField0_ &= 0xFFFFFFBF;
                this.replaceExisting_ = false;
                this.bitField0_ &= 0xFFFFFF7F;
                return this;
            }
            
            public Descriptors.Descriptor getDescriptorForType() {
                return MysqlxCrud.internal_static_Mysqlx_Crud_CreateView_descriptor;
            }
            
            public CreateView getDefaultInstanceForType() {
                return CreateView.getDefaultInstance();
            }
            
            public CreateView build() {
                final CreateView result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw newUninitializedMessageException((Message)result);
                }
                return result;
            }
            
            public CreateView buildPartial() {
                final CreateView result = new CreateView((GeneratedMessageV3.Builder)this);
                final int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 0x1) == 0x1) {
                    to_bitField0_ |= 0x1;
                }
                if (this.collectionBuilder_ == null) {
                    result.collection_ = this.collection_;
                }
                else {
                    result.collection_ = (Collection)this.collectionBuilder_.build();
                }
                if ((from_bitField0_ & 0x2) == 0x2) {
                    to_bitField0_ |= 0x2;
                }
                result.definer_ = this.definer_;
                if ((from_bitField0_ & 0x4) == 0x4) {
                    to_bitField0_ |= 0x4;
                }
                result.algorithm_ = this.algorithm_;
                if ((from_bitField0_ & 0x8) == 0x8) {
                    to_bitField0_ |= 0x8;
                }
                result.security_ = this.security_;
                if ((from_bitField0_ & 0x10) == 0x10) {
                    to_bitField0_ |= 0x10;
                }
                result.check_ = this.check_;
                if ((this.bitField0_ & 0x20) == 0x20) {
                    this.column_ = this.column_.getUnmodifiableView();
                    this.bitField0_ &= 0xFFFFFFDF;
                }
                result.column_ = this.column_;
                if ((from_bitField0_ & 0x40) == 0x40) {
                    to_bitField0_ |= 0x20;
                }
                if (this.stmtBuilder_ == null) {
                    result.stmt_ = this.stmt_;
                }
                else {
                    result.stmt_ = (Find)this.stmtBuilder_.build();
                }
                if ((from_bitField0_ & 0x80) == 0x80) {
                    to_bitField0_ |= 0x40;
                }
                result.replaceExisting_ = this.replaceExisting_;
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
                if (other instanceof CreateView) {
                    return this.mergeFrom((CreateView)other);
                }
                super.mergeFrom(other);
                return this;
            }
            
            public Builder mergeFrom(final CreateView other) {
                if (other == CreateView.getDefaultInstance()) {
                    return this;
                }
                if (other.hasCollection()) {
                    this.mergeCollection(other.getCollection());
                }
                if (other.hasDefiner()) {
                    this.bitField0_ |= 0x2;
                    this.definer_ = other.definer_;
                    this.onChanged();
                }
                if (other.hasAlgorithm()) {
                    this.setAlgorithm(other.getAlgorithm());
                }
                if (other.hasSecurity()) {
                    this.setSecurity(other.getSecurity());
                }
                if (other.hasCheck()) {
                    this.setCheck(other.getCheck());
                }
                if (!other.column_.isEmpty()) {
                    if (this.column_.isEmpty()) {
                        this.column_ = other.column_;
                        this.bitField0_ &= 0xFFFFFFDF;
                    }
                    else {
                        this.ensureColumnIsMutable();
                        this.column_.addAll((java.util.Collection)other.column_);
                    }
                    this.onChanged();
                }
                if (other.hasStmt()) {
                    this.mergeStmt(other.getStmt());
                }
                if (other.hasReplaceExisting()) {
                    this.setReplaceExisting(other.getReplaceExisting());
                }
                this.mergeUnknownFields(other.unknownFields);
                this.onChanged();
                return this;
            }
            
            public final boolean isInitialized() {
                return this.hasCollection() && this.hasStmt() && this.getCollection().isInitialized() && this.getStmt().isInitialized();
            }
            
            public Builder mergeFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
                CreateView parsedMessage = null;
                try {
                    parsedMessage = (CreateView)CreateView.PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (CreateView)e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                }
                finally {
                    if (parsedMessage != null) {
                        this.mergeFrom(parsedMessage);
                    }
                }
                return this;
            }
            
            public boolean hasCollection() {
                return (this.bitField0_ & 0x1) == 0x1;
            }
            
            public Collection getCollection() {
                if (this.collectionBuilder_ == null) {
                    return (this.collection_ == null) ? Collection.getDefaultInstance() : this.collection_;
                }
                return (Collection)this.collectionBuilder_.getMessage();
            }
            
            public Builder setCollection(final Collection value) {
                if (this.collectionBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.collection_ = value;
                    this.onChanged();
                }
                else {
                    this.collectionBuilder_.setMessage((AbstractMessage)value);
                }
                this.bitField0_ |= 0x1;
                return this;
            }
            
            public Builder setCollection(final Collection.Builder builderForValue) {
                if (this.collectionBuilder_ == null) {
                    this.collection_ = builderForValue.build();
                    this.onChanged();
                }
                else {
                    this.collectionBuilder_.setMessage((AbstractMessage)builderForValue.build());
                }
                this.bitField0_ |= 0x1;
                return this;
            }
            
            public Builder mergeCollection(final Collection value) {
                if (this.collectionBuilder_ == null) {
                    if ((this.bitField0_ & 0x1) == 0x1 && this.collection_ != null && this.collection_ != Collection.getDefaultInstance()) {
                        this.collection_ = Collection.newBuilder(this.collection_).mergeFrom(value).buildPartial();
                    }
                    else {
                        this.collection_ = value;
                    }
                    this.onChanged();
                }
                else {
                    this.collectionBuilder_.mergeFrom((AbstractMessage)value);
                }
                this.bitField0_ |= 0x1;
                return this;
            }
            
            public Builder clearCollection() {
                if (this.collectionBuilder_ == null) {
                    this.collection_ = null;
                    this.onChanged();
                }
                else {
                    this.collectionBuilder_.clear();
                }
                this.bitField0_ &= 0xFFFFFFFE;
                return this;
            }
            
            public Collection.Builder getCollectionBuilder() {
                this.bitField0_ |= 0x1;
                this.onChanged();
                return (Collection.Builder)this.getCollectionFieldBuilder().getBuilder();
            }
            
            public CollectionOrBuilder getCollectionOrBuilder() {
                if (this.collectionBuilder_ != null) {
                    return (CollectionOrBuilder)this.collectionBuilder_.getMessageOrBuilder();
                }
                return (this.collection_ == null) ? Collection.getDefaultInstance() : this.collection_;
            }
            
            private SingleFieldBuilderV3<Collection, Collection.Builder, CollectionOrBuilder> getCollectionFieldBuilder() {
                if (this.collectionBuilder_ == null) {
                    this.collectionBuilder_ = (SingleFieldBuilderV3<Collection, Collection.Builder, CollectionOrBuilder>)new SingleFieldBuilderV3((AbstractMessage)this.getCollection(), (AbstractMessage.BuilderParent)this.getParentForChildren(), this.isClean());
                    this.collection_ = null;
                }
                return this.collectionBuilder_;
            }
            
            public boolean hasDefiner() {
                return (this.bitField0_ & 0x2) == 0x2;
            }
            
            public String getDefiner() {
                final Object ref = this.definer_;
                if (!(ref instanceof String)) {
                    final ByteString bs = (ByteString)ref;
                    final String s = bs.toStringUtf8();
                    if (bs.isValidUtf8()) {
                        this.definer_ = s;
                    }
                    return s;
                }
                return (String)ref;
            }
            
            public ByteString getDefinerBytes() {
                final Object ref = this.definer_;
                if (ref instanceof String) {
                    final ByteString b = ByteString.copyFromUtf8((String)ref);
                    return (ByteString)(this.definer_ = b);
                }
                return (ByteString)ref;
            }
            
            public Builder setDefiner(final String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 0x2;
                this.definer_ = value;
                this.onChanged();
                return this;
            }
            
            public Builder clearDefiner() {
                this.bitField0_ &= 0xFFFFFFFD;
                this.definer_ = CreateView.getDefaultInstance().getDefiner();
                this.onChanged();
                return this;
            }
            
            public Builder setDefinerBytes(final ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 0x2;
                this.definer_ = value;
                this.onChanged();
                return this;
            }
            
            public boolean hasAlgorithm() {
                return (this.bitField0_ & 0x4) == 0x4;
            }
            
            public ViewAlgorithm getAlgorithm() {
                final ViewAlgorithm result = ViewAlgorithm.valueOf(this.algorithm_);
                return (result == null) ? ViewAlgorithm.UNDEFINED : result;
            }
            
            public Builder setAlgorithm(final ViewAlgorithm value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 0x4;
                this.algorithm_ = value.getNumber();
                this.onChanged();
                return this;
            }
            
            public Builder clearAlgorithm() {
                this.bitField0_ &= 0xFFFFFFFB;
                this.algorithm_ = 1;
                this.onChanged();
                return this;
            }
            
            public boolean hasSecurity() {
                return (this.bitField0_ & 0x8) == 0x8;
            }
            
            public ViewSqlSecurity getSecurity() {
                final ViewSqlSecurity result = ViewSqlSecurity.valueOf(this.security_);
                return (result == null) ? ViewSqlSecurity.DEFINER : result;
            }
            
            public Builder setSecurity(final ViewSqlSecurity value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 0x8;
                this.security_ = value.getNumber();
                this.onChanged();
                return this;
            }
            
            public Builder clearSecurity() {
                this.bitField0_ &= 0xFFFFFFF7;
                this.security_ = 2;
                this.onChanged();
                return this;
            }
            
            public boolean hasCheck() {
                return (this.bitField0_ & 0x10) == 0x10;
            }
            
            public ViewCheckOption getCheck() {
                final ViewCheckOption result = ViewCheckOption.valueOf(this.check_);
                return (result == null) ? ViewCheckOption.LOCAL : result;
            }
            
            public Builder setCheck(final ViewCheckOption value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 0x10;
                this.check_ = value.getNumber();
                this.onChanged();
                return this;
            }
            
            public Builder clearCheck() {
                this.bitField0_ &= 0xFFFFFFEF;
                this.check_ = 1;
                this.onChanged();
                return this;
            }
            
            private void ensureColumnIsMutable() {
                if ((this.bitField0_ & 0x20) != 0x20) {
                    this.column_ = (LazyStringList)new LazyStringArrayList(this.column_);
                    this.bitField0_ |= 0x20;
                }
            }
            
            public ProtocolStringList getColumnList() {
                return (ProtocolStringList)this.column_.getUnmodifiableView();
            }
            
            public int getColumnCount() {
                return this.column_.size();
            }
            
            public String getColumn(final int index) {
                return (String)this.column_.get(index);
            }
            
            public ByteString getColumnBytes(final int index) {
                return this.column_.getByteString(index);
            }
            
            public Builder setColumn(final int index, final String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureColumnIsMutable();
                this.column_.set(index, (Object)value);
                this.onChanged();
                return this;
            }
            
            public Builder addColumn(final String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureColumnIsMutable();
                this.column_.add((Object)value);
                this.onChanged();
                return this;
            }
            
            public Builder addAllColumn(final Iterable<String> values) {
                this.ensureColumnIsMutable();
                AbstractMessageLite.Builder.addAll((Iterable)values, (List)this.column_);
                this.onChanged();
                return this;
            }
            
            public Builder clearColumn() {
                this.column_ = LazyStringArrayList.EMPTY;
                this.bitField0_ &= 0xFFFFFFDF;
                this.onChanged();
                return this;
            }
            
            public Builder addColumnBytes(final ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureColumnIsMutable();
                this.column_.add(value);
                this.onChanged();
                return this;
            }
            
            public boolean hasStmt() {
                return (this.bitField0_ & 0x40) == 0x40;
            }
            
            public Find getStmt() {
                if (this.stmtBuilder_ == null) {
                    return (this.stmt_ == null) ? Find.getDefaultInstance() : this.stmt_;
                }
                return (Find)this.stmtBuilder_.getMessage();
            }
            
            public Builder setStmt(final Find value) {
                if (this.stmtBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.stmt_ = value;
                    this.onChanged();
                }
                else {
                    this.stmtBuilder_.setMessage((AbstractMessage)value);
                }
                this.bitField0_ |= 0x40;
                return this;
            }
            
            public Builder setStmt(final Find.Builder builderForValue) {
                if (this.stmtBuilder_ == null) {
                    this.stmt_ = builderForValue.build();
                    this.onChanged();
                }
                else {
                    this.stmtBuilder_.setMessage((AbstractMessage)builderForValue.build());
                }
                this.bitField0_ |= 0x40;
                return this;
            }
            
            public Builder mergeStmt(final Find value) {
                if (this.stmtBuilder_ == null) {
                    if ((this.bitField0_ & 0x40) == 0x40 && this.stmt_ != null && this.stmt_ != Find.getDefaultInstance()) {
                        this.stmt_ = Find.newBuilder(this.stmt_).mergeFrom(value).buildPartial();
                    }
                    else {
                        this.stmt_ = value;
                    }
                    this.onChanged();
                }
                else {
                    this.stmtBuilder_.mergeFrom((AbstractMessage)value);
                }
                this.bitField0_ |= 0x40;
                return this;
            }
            
            public Builder clearStmt() {
                if (this.stmtBuilder_ == null) {
                    this.stmt_ = null;
                    this.onChanged();
                }
                else {
                    this.stmtBuilder_.clear();
                }
                this.bitField0_ &= 0xFFFFFFBF;
                return this;
            }
            
            public Find.Builder getStmtBuilder() {
                this.bitField0_ |= 0x40;
                this.onChanged();
                return (Find.Builder)this.getStmtFieldBuilder().getBuilder();
            }
            
            public FindOrBuilder getStmtOrBuilder() {
                if (this.stmtBuilder_ != null) {
                    return (FindOrBuilder)this.stmtBuilder_.getMessageOrBuilder();
                }
                return (this.stmt_ == null) ? Find.getDefaultInstance() : this.stmt_;
            }
            
            private SingleFieldBuilderV3<Find, Find.Builder, FindOrBuilder> getStmtFieldBuilder() {
                if (this.stmtBuilder_ == null) {
                    this.stmtBuilder_ = (SingleFieldBuilderV3<Find, Find.Builder, FindOrBuilder>)new SingleFieldBuilderV3((AbstractMessage)this.getStmt(), (AbstractMessage.BuilderParent)this.getParentForChildren(), this.isClean());
                    this.stmt_ = null;
                }
                return this.stmtBuilder_;
            }
            
            public boolean hasReplaceExisting() {
                return (this.bitField0_ & 0x80) == 0x80;
            }
            
            public boolean getReplaceExisting() {
                return this.replaceExisting_;
            }
            
            public Builder setReplaceExisting(final boolean value) {
                this.bitField0_ |= 0x80;
                this.replaceExisting_ = value;
                this.onChanged();
                return this;
            }
            
            public Builder clearReplaceExisting() {
                this.bitField0_ &= 0xFFFFFF7F;
                this.replaceExisting_ = false;
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
    
    public static final class ModifyView extends GeneratedMessageV3 implements ModifyViewOrBuilder
    {
        private static final long serialVersionUID = 0L;
        private int bitField0_;
        public static final int COLLECTION_FIELD_NUMBER = 1;
        private Collection collection_;
        public static final int DEFINER_FIELD_NUMBER = 2;
        private volatile Object definer_;
        public static final int ALGORITHM_FIELD_NUMBER = 3;
        private int algorithm_;
        public static final int SECURITY_FIELD_NUMBER = 4;
        private int security_;
        public static final int CHECK_FIELD_NUMBER = 5;
        private int check_;
        public static final int COLUMN_FIELD_NUMBER = 6;
        private LazyStringList column_;
        public static final int STMT_FIELD_NUMBER = 7;
        private Find stmt_;
        private byte memoizedIsInitialized;
        private static final ModifyView DEFAULT_INSTANCE;
        @Deprecated
        public static final Parser<ModifyView> PARSER;
        
        private ModifyView(final GeneratedMessageV3.Builder<?> builder) {
            super((GeneratedMessageV3.Builder)builder);
            this.memoizedIsInitialized = -1;
        }
        
        private ModifyView() {
            this.memoizedIsInitialized = -1;
            this.definer_ = "";
            this.algorithm_ = 1;
            this.security_ = 1;
            this.check_ = 1;
            this.column_ = LazyStringArrayList.EMPTY;
        }
        
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }
        
        private ModifyView(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            Collection.Builder subBuilder = null;
                            if ((this.bitField0_ & 0x1) == 0x1) {
                                subBuilder = this.collection_.toBuilder();
                            }
                            this.collection_ = (Collection)input.readMessage((Parser)Collection.PARSER, extensionRegistry);
                            if (subBuilder != null) {
                                subBuilder.mergeFrom(this.collection_);
                                this.collection_ = subBuilder.buildPartial();
                            }
                            this.bitField0_ |= 0x1;
                            continue;
                        }
                        case 18: {
                            final ByteString bs = input.readBytes();
                            this.bitField0_ |= 0x2;
                            this.definer_ = bs;
                            continue;
                        }
                        case 24: {
                            final int rawValue = input.readEnum();
                            final ViewAlgorithm value = ViewAlgorithm.valueOf(rawValue);
                            if (value == null) {
                                unknownFields.mergeVarintField(3, rawValue);
                                continue;
                            }
                            this.bitField0_ |= 0x4;
                            this.algorithm_ = rawValue;
                            continue;
                        }
                        case 32: {
                            final int rawValue = input.readEnum();
                            final ViewSqlSecurity value2 = ViewSqlSecurity.valueOf(rawValue);
                            if (value2 == null) {
                                unknownFields.mergeVarintField(4, rawValue);
                                continue;
                            }
                            this.bitField0_ |= 0x8;
                            this.security_ = rawValue;
                            continue;
                        }
                        case 40: {
                            final int rawValue = input.readEnum();
                            final ViewCheckOption value3 = ViewCheckOption.valueOf(rawValue);
                            if (value3 == null) {
                                unknownFields.mergeVarintField(5, rawValue);
                                continue;
                            }
                            this.bitField0_ |= 0x10;
                            this.check_ = rawValue;
                            continue;
                        }
                        case 50: {
                            final ByteString bs = input.readBytes();
                            if ((mutable_bitField0_ & 0x20) != 0x20) {
                                this.column_ = (LazyStringList)new LazyStringArrayList();
                                mutable_bitField0_ |= 0x20;
                            }
                            this.column_.add(bs);
                            continue;
                        }
                        case 58: {
                            Find.Builder subBuilder2 = null;
                            if ((this.bitField0_ & 0x20) == 0x20) {
                                subBuilder2 = this.stmt_.toBuilder();
                            }
                            this.stmt_ = (Find)input.readMessage((Parser)Find.PARSER, extensionRegistry);
                            if (subBuilder2 != null) {
                                subBuilder2.mergeFrom(this.stmt_);
                                this.stmt_ = subBuilder2.buildPartial();
                            }
                            this.bitField0_ |= 0x20;
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
                if ((mutable_bitField0_ & 0x20) == 0x20) {
                    this.column_ = this.column_.getUnmodifiableView();
                }
                this.unknownFields = unknownFields.build();
                this.makeExtensionsImmutable();
            }
        }
        
        public static final Descriptors.Descriptor getDescriptor() {
            return MysqlxCrud.internal_static_Mysqlx_Crud_ModifyView_descriptor;
        }
        
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return MysqlxCrud.internal_static_Mysqlx_Crud_ModifyView_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)ModifyView.class, (Class)Builder.class);
        }
        
        public boolean hasCollection() {
            return (this.bitField0_ & 0x1) == 0x1;
        }
        
        public Collection getCollection() {
            return (this.collection_ == null) ? Collection.getDefaultInstance() : this.collection_;
        }
        
        public CollectionOrBuilder getCollectionOrBuilder() {
            return (this.collection_ == null) ? Collection.getDefaultInstance() : this.collection_;
        }
        
        public boolean hasDefiner() {
            return (this.bitField0_ & 0x2) == 0x2;
        }
        
        public String getDefiner() {
            final Object ref = this.definer_;
            if (ref instanceof String) {
                return (String)ref;
            }
            final ByteString bs = (ByteString)ref;
            final String s = bs.toStringUtf8();
            if (bs.isValidUtf8()) {
                this.definer_ = s;
            }
            return s;
        }
        
        public ByteString getDefinerBytes() {
            final Object ref = this.definer_;
            if (ref instanceof String) {
                final ByteString b = ByteString.copyFromUtf8((String)ref);
                return (ByteString)(this.definer_ = b);
            }
            return (ByteString)ref;
        }
        
        public boolean hasAlgorithm() {
            return (this.bitField0_ & 0x4) == 0x4;
        }
        
        public ViewAlgorithm getAlgorithm() {
            final ViewAlgorithm result = ViewAlgorithm.valueOf(this.algorithm_);
            return (result == null) ? ViewAlgorithm.UNDEFINED : result;
        }
        
        public boolean hasSecurity() {
            return (this.bitField0_ & 0x8) == 0x8;
        }
        
        public ViewSqlSecurity getSecurity() {
            final ViewSqlSecurity result = ViewSqlSecurity.valueOf(this.security_);
            return (result == null) ? ViewSqlSecurity.INVOKER : result;
        }
        
        public boolean hasCheck() {
            return (this.bitField0_ & 0x10) == 0x10;
        }
        
        public ViewCheckOption getCheck() {
            final ViewCheckOption result = ViewCheckOption.valueOf(this.check_);
            return (result == null) ? ViewCheckOption.LOCAL : result;
        }
        
        public ProtocolStringList getColumnList() {
            return (ProtocolStringList)this.column_;
        }
        
        public int getColumnCount() {
            return this.column_.size();
        }
        
        public String getColumn(final int index) {
            return (String)this.column_.get(index);
        }
        
        public ByteString getColumnBytes(final int index) {
            return this.column_.getByteString(index);
        }
        
        public boolean hasStmt() {
            return (this.bitField0_ & 0x20) == 0x20;
        }
        
        public Find getStmt() {
            return (this.stmt_ == null) ? Find.getDefaultInstance() : this.stmt_;
        }
        
        public FindOrBuilder getStmtOrBuilder() {
            return (this.stmt_ == null) ? Find.getDefaultInstance() : this.stmt_;
        }
        
        public final boolean isInitialized() {
            final byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (!this.hasCollection()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            if (!this.getCollection().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            if (this.hasStmt() && !this.getStmt().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            this.memoizedIsInitialized = 1;
            return true;
        }
        
        public void writeTo(final CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 0x1) == 0x1) {
                output.writeMessage(1, (MessageLite)this.getCollection());
            }
            if ((this.bitField0_ & 0x2) == 0x2) {
                GeneratedMessageV3.writeString(output, 2, this.definer_);
            }
            if ((this.bitField0_ & 0x4) == 0x4) {
                output.writeEnum(3, this.algorithm_);
            }
            if ((this.bitField0_ & 0x8) == 0x8) {
                output.writeEnum(4, this.security_);
            }
            if ((this.bitField0_ & 0x10) == 0x10) {
                output.writeEnum(5, this.check_);
            }
            for (int i = 0; i < this.column_.size(); ++i) {
                GeneratedMessageV3.writeString(output, 6, this.column_.getRaw(i));
            }
            if ((this.bitField0_ & 0x20) == 0x20) {
                output.writeMessage(7, (MessageLite)this.getStmt());
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
                size += CodedOutputStream.computeMessageSize(1, (MessageLite)this.getCollection());
            }
            if ((this.bitField0_ & 0x2) == 0x2) {
                size += GeneratedMessageV3.computeStringSize(2, this.definer_);
            }
            if ((this.bitField0_ & 0x4) == 0x4) {
                size += CodedOutputStream.computeEnumSize(3, this.algorithm_);
            }
            if ((this.bitField0_ & 0x8) == 0x8) {
                size += CodedOutputStream.computeEnumSize(4, this.security_);
            }
            if ((this.bitField0_ & 0x10) == 0x10) {
                size += CodedOutputStream.computeEnumSize(5, this.check_);
            }
            int dataSize = 0;
            for (int i = 0; i < this.column_.size(); ++i) {
                dataSize += computeStringSizeNoTag(this.column_.getRaw(i));
            }
            size += dataSize;
            size += 1 * this.getColumnList().size();
            if ((this.bitField0_ & 0x20) == 0x20) {
                size += CodedOutputStream.computeMessageSize(7, (MessageLite)this.getStmt());
            }
            size += this.unknownFields.getSerializedSize();
            return this.memoizedSize = size;
        }
        
        public boolean equals(final Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof ModifyView)) {
                return super.equals(obj);
            }
            final ModifyView other = (ModifyView)obj;
            boolean result = true;
            result = (result && this.hasCollection() == other.hasCollection());
            if (this.hasCollection()) {
                result = (result && this.getCollection().equals(other.getCollection()));
            }
            result = (result && this.hasDefiner() == other.hasDefiner());
            if (this.hasDefiner()) {
                result = (result && this.getDefiner().equals(other.getDefiner()));
            }
            result = (result && this.hasAlgorithm() == other.hasAlgorithm());
            if (this.hasAlgorithm()) {
                result = (result && this.algorithm_ == other.algorithm_);
            }
            result = (result && this.hasSecurity() == other.hasSecurity());
            if (this.hasSecurity()) {
                result = (result && this.security_ == other.security_);
            }
            result = (result && this.hasCheck() == other.hasCheck());
            if (this.hasCheck()) {
                result = (result && this.check_ == other.check_);
            }
            result = (result && this.getColumnList().equals(other.getColumnList()));
            result = (result && this.hasStmt() == other.hasStmt());
            if (this.hasStmt()) {
                result = (result && this.getStmt().equals(other.getStmt()));
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
            if (this.hasCollection()) {
                hash = 37 * hash + 1;
                hash = 53 * hash + this.getCollection().hashCode();
            }
            if (this.hasDefiner()) {
                hash = 37 * hash + 2;
                hash = 53 * hash + this.getDefiner().hashCode();
            }
            if (this.hasAlgorithm()) {
                hash = 37 * hash + 3;
                hash = 53 * hash + this.algorithm_;
            }
            if (this.hasSecurity()) {
                hash = 37 * hash + 4;
                hash = 53 * hash + this.security_;
            }
            if (this.hasCheck()) {
                hash = 37 * hash + 5;
                hash = 53 * hash + this.check_;
            }
            if (this.getColumnCount() > 0) {
                hash = 37 * hash + 6;
                hash = 53 * hash + this.getColumnList().hashCode();
            }
            if (this.hasStmt()) {
                hash = 37 * hash + 7;
                hash = 53 * hash + this.getStmt().hashCode();
            }
            hash = 29 * hash + this.unknownFields.hashCode();
            return this.memoizedHashCode = hash;
        }
        
        public static ModifyView parseFrom(final ByteBuffer data) throws InvalidProtocolBufferException {
            return (ModifyView)ModifyView.PARSER.parseFrom(data);
        }
        
        public static ModifyView parseFrom(final ByteBuffer data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ModifyView)ModifyView.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static ModifyView parseFrom(final ByteString data) throws InvalidProtocolBufferException {
            return (ModifyView)ModifyView.PARSER.parseFrom(data);
        }
        
        public static ModifyView parseFrom(final ByteString data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ModifyView)ModifyView.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static ModifyView parseFrom(final byte[] data) throws InvalidProtocolBufferException {
            return (ModifyView)ModifyView.PARSER.parseFrom(data);
        }
        
        public static ModifyView parseFrom(final byte[] data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ModifyView)ModifyView.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static ModifyView parseFrom(final InputStream input) throws IOException {
            return (ModifyView)GeneratedMessageV3.parseWithIOException((Parser)ModifyView.PARSER, input);
        }
        
        public static ModifyView parseFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ModifyView)GeneratedMessageV3.parseWithIOException((Parser)ModifyView.PARSER, input, extensionRegistry);
        }
        
        public static ModifyView parseDelimitedFrom(final InputStream input) throws IOException {
            return (ModifyView)GeneratedMessageV3.parseDelimitedWithIOException((Parser)ModifyView.PARSER, input);
        }
        
        public static ModifyView parseDelimitedFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ModifyView)GeneratedMessageV3.parseDelimitedWithIOException((Parser)ModifyView.PARSER, input, extensionRegistry);
        }
        
        public static ModifyView parseFrom(final CodedInputStream input) throws IOException {
            return (ModifyView)GeneratedMessageV3.parseWithIOException((Parser)ModifyView.PARSER, input);
        }
        
        public static ModifyView parseFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ModifyView)GeneratedMessageV3.parseWithIOException((Parser)ModifyView.PARSER, input, extensionRegistry);
        }
        
        public Builder newBuilderForType() {
            return newBuilder();
        }
        
        public static Builder newBuilder() {
            return ModifyView.DEFAULT_INSTANCE.toBuilder();
        }
        
        public static Builder newBuilder(final ModifyView prototype) {
            return ModifyView.DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }
        
        public Builder toBuilder() {
            return (this == ModifyView.DEFAULT_INSTANCE) ? new Builder() : new Builder().mergeFrom(this);
        }
        
        protected Builder newBuilderForType(final GeneratedMessageV3.BuilderParent parent) {
            final Builder builder = new Builder(parent);
            return builder;
        }
        
        public static ModifyView getDefaultInstance() {
            return ModifyView.DEFAULT_INSTANCE;
        }
        
        public static Parser<ModifyView> parser() {
            return ModifyView.PARSER;
        }
        
        public Parser<ModifyView> getParserForType() {
            return ModifyView.PARSER;
        }
        
        public ModifyView getDefaultInstanceForType() {
            return ModifyView.DEFAULT_INSTANCE;
        }
        
        static {
            DEFAULT_INSTANCE = new ModifyView();
            PARSER = (Parser)new AbstractParser<ModifyView>() {
                public ModifyView parsePartialFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new ModifyView(input, extensionRegistry);
                }
            };
        }
        
        public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements ModifyViewOrBuilder
        {
            private int bitField0_;
            private Collection collection_;
            private SingleFieldBuilderV3<Collection, Collection.Builder, CollectionOrBuilder> collectionBuilder_;
            private Object definer_;
            private int algorithm_;
            private int security_;
            private int check_;
            private LazyStringList column_;
            private Find stmt_;
            private SingleFieldBuilderV3<Find, Find.Builder, FindOrBuilder> stmtBuilder_;
            
            public static final Descriptors.Descriptor getDescriptor() {
                return MysqlxCrud.internal_static_Mysqlx_Crud_ModifyView_descriptor;
            }
            
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return MysqlxCrud.internal_static_Mysqlx_Crud_ModifyView_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)ModifyView.class, (Class)Builder.class);
            }
            
            private Builder() {
                this.collection_ = null;
                this.definer_ = "";
                this.algorithm_ = 1;
                this.security_ = 1;
                this.check_ = 1;
                this.column_ = LazyStringArrayList.EMPTY;
                this.stmt_ = null;
                this.maybeForceBuilderInitialization();
            }
            
            private Builder(final GeneratedMessageV3.BuilderParent parent) {
                super(parent);
                this.collection_ = null;
                this.definer_ = "";
                this.algorithm_ = 1;
                this.security_ = 1;
                this.check_ = 1;
                this.column_ = LazyStringArrayList.EMPTY;
                this.stmt_ = null;
                this.maybeForceBuilderInitialization();
            }
            
            private void maybeForceBuilderInitialization() {
                if (ModifyView.alwaysUseFieldBuilders) {
                    this.getCollectionFieldBuilder();
                    this.getStmtFieldBuilder();
                }
            }
            
            public Builder clear() {
                super.clear();
                if (this.collectionBuilder_ == null) {
                    this.collection_ = null;
                }
                else {
                    this.collectionBuilder_.clear();
                }
                this.bitField0_ &= 0xFFFFFFFE;
                this.definer_ = "";
                this.bitField0_ &= 0xFFFFFFFD;
                this.algorithm_ = 1;
                this.bitField0_ &= 0xFFFFFFFB;
                this.security_ = 1;
                this.bitField0_ &= 0xFFFFFFF7;
                this.check_ = 1;
                this.bitField0_ &= 0xFFFFFFEF;
                this.column_ = LazyStringArrayList.EMPTY;
                this.bitField0_ &= 0xFFFFFFDF;
                if (this.stmtBuilder_ == null) {
                    this.stmt_ = null;
                }
                else {
                    this.stmtBuilder_.clear();
                }
                this.bitField0_ &= 0xFFFFFFBF;
                return this;
            }
            
            public Descriptors.Descriptor getDescriptorForType() {
                return MysqlxCrud.internal_static_Mysqlx_Crud_ModifyView_descriptor;
            }
            
            public ModifyView getDefaultInstanceForType() {
                return ModifyView.getDefaultInstance();
            }
            
            public ModifyView build() {
                final ModifyView result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw newUninitializedMessageException((Message)result);
                }
                return result;
            }
            
            public ModifyView buildPartial() {
                final ModifyView result = new ModifyView((GeneratedMessageV3.Builder)this);
                final int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 0x1) == 0x1) {
                    to_bitField0_ |= 0x1;
                }
                if (this.collectionBuilder_ == null) {
                    result.collection_ = this.collection_;
                }
                else {
                    result.collection_ = (Collection)this.collectionBuilder_.build();
                }
                if ((from_bitField0_ & 0x2) == 0x2) {
                    to_bitField0_ |= 0x2;
                }
                result.definer_ = this.definer_;
                if ((from_bitField0_ & 0x4) == 0x4) {
                    to_bitField0_ |= 0x4;
                }
                result.algorithm_ = this.algorithm_;
                if ((from_bitField0_ & 0x8) == 0x8) {
                    to_bitField0_ |= 0x8;
                }
                result.security_ = this.security_;
                if ((from_bitField0_ & 0x10) == 0x10) {
                    to_bitField0_ |= 0x10;
                }
                result.check_ = this.check_;
                if ((this.bitField0_ & 0x20) == 0x20) {
                    this.column_ = this.column_.getUnmodifiableView();
                    this.bitField0_ &= 0xFFFFFFDF;
                }
                result.column_ = this.column_;
                if ((from_bitField0_ & 0x40) == 0x40) {
                    to_bitField0_ |= 0x20;
                }
                if (this.stmtBuilder_ == null) {
                    result.stmt_ = this.stmt_;
                }
                else {
                    result.stmt_ = (Find)this.stmtBuilder_.build();
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
                if (other instanceof ModifyView) {
                    return this.mergeFrom((ModifyView)other);
                }
                super.mergeFrom(other);
                return this;
            }
            
            public Builder mergeFrom(final ModifyView other) {
                if (other == ModifyView.getDefaultInstance()) {
                    return this;
                }
                if (other.hasCollection()) {
                    this.mergeCollection(other.getCollection());
                }
                if (other.hasDefiner()) {
                    this.bitField0_ |= 0x2;
                    this.definer_ = other.definer_;
                    this.onChanged();
                }
                if (other.hasAlgorithm()) {
                    this.setAlgorithm(other.getAlgorithm());
                }
                if (other.hasSecurity()) {
                    this.setSecurity(other.getSecurity());
                }
                if (other.hasCheck()) {
                    this.setCheck(other.getCheck());
                }
                if (!other.column_.isEmpty()) {
                    if (this.column_.isEmpty()) {
                        this.column_ = other.column_;
                        this.bitField0_ &= 0xFFFFFFDF;
                    }
                    else {
                        this.ensureColumnIsMutable();
                        this.column_.addAll((java.util.Collection)other.column_);
                    }
                    this.onChanged();
                }
                if (other.hasStmt()) {
                    this.mergeStmt(other.getStmt());
                }
                this.mergeUnknownFields(other.unknownFields);
                this.onChanged();
                return this;
            }
            
            public final boolean isInitialized() {
                return this.hasCollection() && this.getCollection().isInitialized() && (!this.hasStmt() || this.getStmt().isInitialized());
            }
            
            public Builder mergeFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
                ModifyView parsedMessage = null;
                try {
                    parsedMessage = (ModifyView)ModifyView.PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (ModifyView)e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                }
                finally {
                    if (parsedMessage != null) {
                        this.mergeFrom(parsedMessage);
                    }
                }
                return this;
            }
            
            public boolean hasCollection() {
                return (this.bitField0_ & 0x1) == 0x1;
            }
            
            public Collection getCollection() {
                if (this.collectionBuilder_ == null) {
                    return (this.collection_ == null) ? Collection.getDefaultInstance() : this.collection_;
                }
                return (Collection)this.collectionBuilder_.getMessage();
            }
            
            public Builder setCollection(final Collection value) {
                if (this.collectionBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.collection_ = value;
                    this.onChanged();
                }
                else {
                    this.collectionBuilder_.setMessage((AbstractMessage)value);
                }
                this.bitField0_ |= 0x1;
                return this;
            }
            
            public Builder setCollection(final Collection.Builder builderForValue) {
                if (this.collectionBuilder_ == null) {
                    this.collection_ = builderForValue.build();
                    this.onChanged();
                }
                else {
                    this.collectionBuilder_.setMessage((AbstractMessage)builderForValue.build());
                }
                this.bitField0_ |= 0x1;
                return this;
            }
            
            public Builder mergeCollection(final Collection value) {
                if (this.collectionBuilder_ == null) {
                    if ((this.bitField0_ & 0x1) == 0x1 && this.collection_ != null && this.collection_ != Collection.getDefaultInstance()) {
                        this.collection_ = Collection.newBuilder(this.collection_).mergeFrom(value).buildPartial();
                    }
                    else {
                        this.collection_ = value;
                    }
                    this.onChanged();
                }
                else {
                    this.collectionBuilder_.mergeFrom((AbstractMessage)value);
                }
                this.bitField0_ |= 0x1;
                return this;
            }
            
            public Builder clearCollection() {
                if (this.collectionBuilder_ == null) {
                    this.collection_ = null;
                    this.onChanged();
                }
                else {
                    this.collectionBuilder_.clear();
                }
                this.bitField0_ &= 0xFFFFFFFE;
                return this;
            }
            
            public Collection.Builder getCollectionBuilder() {
                this.bitField0_ |= 0x1;
                this.onChanged();
                return (Collection.Builder)this.getCollectionFieldBuilder().getBuilder();
            }
            
            public CollectionOrBuilder getCollectionOrBuilder() {
                if (this.collectionBuilder_ != null) {
                    return (CollectionOrBuilder)this.collectionBuilder_.getMessageOrBuilder();
                }
                return (this.collection_ == null) ? Collection.getDefaultInstance() : this.collection_;
            }
            
            private SingleFieldBuilderV3<Collection, Collection.Builder, CollectionOrBuilder> getCollectionFieldBuilder() {
                if (this.collectionBuilder_ == null) {
                    this.collectionBuilder_ = (SingleFieldBuilderV3<Collection, Collection.Builder, CollectionOrBuilder>)new SingleFieldBuilderV3((AbstractMessage)this.getCollection(), (AbstractMessage.BuilderParent)this.getParentForChildren(), this.isClean());
                    this.collection_ = null;
                }
                return this.collectionBuilder_;
            }
            
            public boolean hasDefiner() {
                return (this.bitField0_ & 0x2) == 0x2;
            }
            
            public String getDefiner() {
                final Object ref = this.definer_;
                if (!(ref instanceof String)) {
                    final ByteString bs = (ByteString)ref;
                    final String s = bs.toStringUtf8();
                    if (bs.isValidUtf8()) {
                        this.definer_ = s;
                    }
                    return s;
                }
                return (String)ref;
            }
            
            public ByteString getDefinerBytes() {
                final Object ref = this.definer_;
                if (ref instanceof String) {
                    final ByteString b = ByteString.copyFromUtf8((String)ref);
                    return (ByteString)(this.definer_ = b);
                }
                return (ByteString)ref;
            }
            
            public Builder setDefiner(final String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 0x2;
                this.definer_ = value;
                this.onChanged();
                return this;
            }
            
            public Builder clearDefiner() {
                this.bitField0_ &= 0xFFFFFFFD;
                this.definer_ = ModifyView.getDefaultInstance().getDefiner();
                this.onChanged();
                return this;
            }
            
            public Builder setDefinerBytes(final ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 0x2;
                this.definer_ = value;
                this.onChanged();
                return this;
            }
            
            public boolean hasAlgorithm() {
                return (this.bitField0_ & 0x4) == 0x4;
            }
            
            public ViewAlgorithm getAlgorithm() {
                final ViewAlgorithm result = ViewAlgorithm.valueOf(this.algorithm_);
                return (result == null) ? ViewAlgorithm.UNDEFINED : result;
            }
            
            public Builder setAlgorithm(final ViewAlgorithm value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 0x4;
                this.algorithm_ = value.getNumber();
                this.onChanged();
                return this;
            }
            
            public Builder clearAlgorithm() {
                this.bitField0_ &= 0xFFFFFFFB;
                this.algorithm_ = 1;
                this.onChanged();
                return this;
            }
            
            public boolean hasSecurity() {
                return (this.bitField0_ & 0x8) == 0x8;
            }
            
            public ViewSqlSecurity getSecurity() {
                final ViewSqlSecurity result = ViewSqlSecurity.valueOf(this.security_);
                return (result == null) ? ViewSqlSecurity.INVOKER : result;
            }
            
            public Builder setSecurity(final ViewSqlSecurity value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 0x8;
                this.security_ = value.getNumber();
                this.onChanged();
                return this;
            }
            
            public Builder clearSecurity() {
                this.bitField0_ &= 0xFFFFFFF7;
                this.security_ = 1;
                this.onChanged();
                return this;
            }
            
            public boolean hasCheck() {
                return (this.bitField0_ & 0x10) == 0x10;
            }
            
            public ViewCheckOption getCheck() {
                final ViewCheckOption result = ViewCheckOption.valueOf(this.check_);
                return (result == null) ? ViewCheckOption.LOCAL : result;
            }
            
            public Builder setCheck(final ViewCheckOption value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 0x10;
                this.check_ = value.getNumber();
                this.onChanged();
                return this;
            }
            
            public Builder clearCheck() {
                this.bitField0_ &= 0xFFFFFFEF;
                this.check_ = 1;
                this.onChanged();
                return this;
            }
            
            private void ensureColumnIsMutable() {
                if ((this.bitField0_ & 0x20) != 0x20) {
                    this.column_ = (LazyStringList)new LazyStringArrayList(this.column_);
                    this.bitField0_ |= 0x20;
                }
            }
            
            public ProtocolStringList getColumnList() {
                return (ProtocolStringList)this.column_.getUnmodifiableView();
            }
            
            public int getColumnCount() {
                return this.column_.size();
            }
            
            public String getColumn(final int index) {
                return (String)this.column_.get(index);
            }
            
            public ByteString getColumnBytes(final int index) {
                return this.column_.getByteString(index);
            }
            
            public Builder setColumn(final int index, final String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureColumnIsMutable();
                this.column_.set(index, (Object)value);
                this.onChanged();
                return this;
            }
            
            public Builder addColumn(final String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureColumnIsMutable();
                this.column_.add((Object)value);
                this.onChanged();
                return this;
            }
            
            public Builder addAllColumn(final Iterable<String> values) {
                this.ensureColumnIsMutable();
                AbstractMessageLite.Builder.addAll((Iterable)values, (List)this.column_);
                this.onChanged();
                return this;
            }
            
            public Builder clearColumn() {
                this.column_ = LazyStringArrayList.EMPTY;
                this.bitField0_ &= 0xFFFFFFDF;
                this.onChanged();
                return this;
            }
            
            public Builder addColumnBytes(final ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureColumnIsMutable();
                this.column_.add(value);
                this.onChanged();
                return this;
            }
            
            public boolean hasStmt() {
                return (this.bitField0_ & 0x40) == 0x40;
            }
            
            public Find getStmt() {
                if (this.stmtBuilder_ == null) {
                    return (this.stmt_ == null) ? Find.getDefaultInstance() : this.stmt_;
                }
                return (Find)this.stmtBuilder_.getMessage();
            }
            
            public Builder setStmt(final Find value) {
                if (this.stmtBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.stmt_ = value;
                    this.onChanged();
                }
                else {
                    this.stmtBuilder_.setMessage((AbstractMessage)value);
                }
                this.bitField0_ |= 0x40;
                return this;
            }
            
            public Builder setStmt(final Find.Builder builderForValue) {
                if (this.stmtBuilder_ == null) {
                    this.stmt_ = builderForValue.build();
                    this.onChanged();
                }
                else {
                    this.stmtBuilder_.setMessage((AbstractMessage)builderForValue.build());
                }
                this.bitField0_ |= 0x40;
                return this;
            }
            
            public Builder mergeStmt(final Find value) {
                if (this.stmtBuilder_ == null) {
                    if ((this.bitField0_ & 0x40) == 0x40 && this.stmt_ != null && this.stmt_ != Find.getDefaultInstance()) {
                        this.stmt_ = Find.newBuilder(this.stmt_).mergeFrom(value).buildPartial();
                    }
                    else {
                        this.stmt_ = value;
                    }
                    this.onChanged();
                }
                else {
                    this.stmtBuilder_.mergeFrom((AbstractMessage)value);
                }
                this.bitField0_ |= 0x40;
                return this;
            }
            
            public Builder clearStmt() {
                if (this.stmtBuilder_ == null) {
                    this.stmt_ = null;
                    this.onChanged();
                }
                else {
                    this.stmtBuilder_.clear();
                }
                this.bitField0_ &= 0xFFFFFFBF;
                return this;
            }
            
            public Find.Builder getStmtBuilder() {
                this.bitField0_ |= 0x40;
                this.onChanged();
                return (Find.Builder)this.getStmtFieldBuilder().getBuilder();
            }
            
            public FindOrBuilder getStmtOrBuilder() {
                if (this.stmtBuilder_ != null) {
                    return (FindOrBuilder)this.stmtBuilder_.getMessageOrBuilder();
                }
                return (this.stmt_ == null) ? Find.getDefaultInstance() : this.stmt_;
            }
            
            private SingleFieldBuilderV3<Find, Find.Builder, FindOrBuilder> getStmtFieldBuilder() {
                if (this.stmtBuilder_ == null) {
                    this.stmtBuilder_ = (SingleFieldBuilderV3<Find, Find.Builder, FindOrBuilder>)new SingleFieldBuilderV3((AbstractMessage)this.getStmt(), (AbstractMessage.BuilderParent)this.getParentForChildren(), this.isClean());
                    this.stmt_ = null;
                }
                return this.stmtBuilder_;
            }
            
            public final Builder setUnknownFields(final UnknownFieldSet unknownFields) {
                return (Builder)super.setUnknownFields(unknownFields);
            }
            
            public final Builder mergeUnknownFields(final UnknownFieldSet unknownFields) {
                return (Builder)super.mergeUnknownFields(unknownFields);
            }
        }
    }
    
    public static final class DropView extends GeneratedMessageV3 implements DropViewOrBuilder
    {
        private static final long serialVersionUID = 0L;
        private int bitField0_;
        public static final int COLLECTION_FIELD_NUMBER = 1;
        private Collection collection_;
        public static final int IF_EXISTS_FIELD_NUMBER = 2;
        private boolean ifExists_;
        private byte memoizedIsInitialized;
        private static final DropView DEFAULT_INSTANCE;
        @Deprecated
        public static final Parser<DropView> PARSER;
        
        private DropView(final GeneratedMessageV3.Builder<?> builder) {
            super((GeneratedMessageV3.Builder)builder);
            this.memoizedIsInitialized = -1;
        }
        
        private DropView() {
            this.memoizedIsInitialized = -1;
            this.ifExists_ = false;
        }
        
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }
        
        private DropView(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            Collection.Builder subBuilder = null;
                            if ((this.bitField0_ & 0x1) == 0x1) {
                                subBuilder = this.collection_.toBuilder();
                            }
                            this.collection_ = (Collection)input.readMessage((Parser)Collection.PARSER, extensionRegistry);
                            if (subBuilder != null) {
                                subBuilder.mergeFrom(this.collection_);
                                this.collection_ = subBuilder.buildPartial();
                            }
                            this.bitField0_ |= 0x1;
                            continue;
                        }
                        case 16: {
                            this.bitField0_ |= 0x2;
                            this.ifExists_ = input.readBool();
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
            return MysqlxCrud.internal_static_Mysqlx_Crud_DropView_descriptor;
        }
        
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return MysqlxCrud.internal_static_Mysqlx_Crud_DropView_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)DropView.class, (Class)Builder.class);
        }
        
        public boolean hasCollection() {
            return (this.bitField0_ & 0x1) == 0x1;
        }
        
        public Collection getCollection() {
            return (this.collection_ == null) ? Collection.getDefaultInstance() : this.collection_;
        }
        
        public CollectionOrBuilder getCollectionOrBuilder() {
            return (this.collection_ == null) ? Collection.getDefaultInstance() : this.collection_;
        }
        
        public boolean hasIfExists() {
            return (this.bitField0_ & 0x2) == 0x2;
        }
        
        public boolean getIfExists() {
            return this.ifExists_;
        }
        
        public final boolean isInitialized() {
            final byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (!this.hasCollection()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            if (!this.getCollection().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            this.memoizedIsInitialized = 1;
            return true;
        }
        
        public void writeTo(final CodedOutputStream output) throws IOException {
            if ((this.bitField0_ & 0x1) == 0x1) {
                output.writeMessage(1, (MessageLite)this.getCollection());
            }
            if ((this.bitField0_ & 0x2) == 0x2) {
                output.writeBool(2, this.ifExists_);
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
                size += CodedOutputStream.computeMessageSize(1, (MessageLite)this.getCollection());
            }
            if ((this.bitField0_ & 0x2) == 0x2) {
                size += CodedOutputStream.computeBoolSize(2, this.ifExists_);
            }
            size += this.unknownFields.getSerializedSize();
            return this.memoizedSize = size;
        }
        
        public boolean equals(final Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof DropView)) {
                return super.equals(obj);
            }
            final DropView other = (DropView)obj;
            boolean result = true;
            result = (result && this.hasCollection() == other.hasCollection());
            if (this.hasCollection()) {
                result = (result && this.getCollection().equals(other.getCollection()));
            }
            result = (result && this.hasIfExists() == other.hasIfExists());
            if (this.hasIfExists()) {
                result = (result && this.getIfExists() == other.getIfExists());
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
            if (this.hasCollection()) {
                hash = 37 * hash + 1;
                hash = 53 * hash + this.getCollection().hashCode();
            }
            if (this.hasIfExists()) {
                hash = 37 * hash + 2;
                hash = 53 * hash + Internal.hashBoolean(this.getIfExists());
            }
            hash = 29 * hash + this.unknownFields.hashCode();
            return this.memoizedHashCode = hash;
        }
        
        public static DropView parseFrom(final ByteBuffer data) throws InvalidProtocolBufferException {
            return (DropView)DropView.PARSER.parseFrom(data);
        }
        
        public static DropView parseFrom(final ByteBuffer data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (DropView)DropView.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static DropView parseFrom(final ByteString data) throws InvalidProtocolBufferException {
            return (DropView)DropView.PARSER.parseFrom(data);
        }
        
        public static DropView parseFrom(final ByteString data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (DropView)DropView.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static DropView parseFrom(final byte[] data) throws InvalidProtocolBufferException {
            return (DropView)DropView.PARSER.parseFrom(data);
        }
        
        public static DropView parseFrom(final byte[] data, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (DropView)DropView.PARSER.parseFrom(data, extensionRegistry);
        }
        
        public static DropView parseFrom(final InputStream input) throws IOException {
            return (DropView)GeneratedMessageV3.parseWithIOException((Parser)DropView.PARSER, input);
        }
        
        public static DropView parseFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (DropView)GeneratedMessageV3.parseWithIOException((Parser)DropView.PARSER, input, extensionRegistry);
        }
        
        public static DropView parseDelimitedFrom(final InputStream input) throws IOException {
            return (DropView)GeneratedMessageV3.parseDelimitedWithIOException((Parser)DropView.PARSER, input);
        }
        
        public static DropView parseDelimitedFrom(final InputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (DropView)GeneratedMessageV3.parseDelimitedWithIOException((Parser)DropView.PARSER, input, extensionRegistry);
        }
        
        public static DropView parseFrom(final CodedInputStream input) throws IOException {
            return (DropView)GeneratedMessageV3.parseWithIOException((Parser)DropView.PARSER, input);
        }
        
        public static DropView parseFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
            return (DropView)GeneratedMessageV3.parseWithIOException((Parser)DropView.PARSER, input, extensionRegistry);
        }
        
        public Builder newBuilderForType() {
            return newBuilder();
        }
        
        public static Builder newBuilder() {
            return DropView.DEFAULT_INSTANCE.toBuilder();
        }
        
        public static Builder newBuilder(final DropView prototype) {
            return DropView.DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }
        
        public Builder toBuilder() {
            return (this == DropView.DEFAULT_INSTANCE) ? new Builder() : new Builder().mergeFrom(this);
        }
        
        protected Builder newBuilderForType(final GeneratedMessageV3.BuilderParent parent) {
            final Builder builder = new Builder(parent);
            return builder;
        }
        
        public static DropView getDefaultInstance() {
            return DropView.DEFAULT_INSTANCE;
        }
        
        public static Parser<DropView> parser() {
            return DropView.PARSER;
        }
        
        public Parser<DropView> getParserForType() {
            return DropView.PARSER;
        }
        
        public DropView getDefaultInstanceForType() {
            return DropView.DEFAULT_INSTANCE;
        }
        
        static {
            DEFAULT_INSTANCE = new DropView();
            PARSER = (Parser)new AbstractParser<DropView>() {
                public DropView parsePartialFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new DropView(input, extensionRegistry);
                }
            };
        }
        
        public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements DropViewOrBuilder
        {
            private int bitField0_;
            private Collection collection_;
            private SingleFieldBuilderV3<Collection, Collection.Builder, CollectionOrBuilder> collectionBuilder_;
            private boolean ifExists_;
            
            public static final Descriptors.Descriptor getDescriptor() {
                return MysqlxCrud.internal_static_Mysqlx_Crud_DropView_descriptor;
            }
            
            protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
                return MysqlxCrud.internal_static_Mysqlx_Crud_DropView_fieldAccessorTable.ensureFieldAccessorsInitialized((Class)DropView.class, (Class)Builder.class);
            }
            
            private Builder() {
                this.collection_ = null;
                this.maybeForceBuilderInitialization();
            }
            
            private Builder(final GeneratedMessageV3.BuilderParent parent) {
                super(parent);
                this.collection_ = null;
                this.maybeForceBuilderInitialization();
            }
            
            private void maybeForceBuilderInitialization() {
                if (DropView.alwaysUseFieldBuilders) {
                    this.getCollectionFieldBuilder();
                }
            }
            
            public Builder clear() {
                super.clear();
                if (this.collectionBuilder_ == null) {
                    this.collection_ = null;
                }
                else {
                    this.collectionBuilder_.clear();
                }
                this.bitField0_ &= 0xFFFFFFFE;
                this.ifExists_ = false;
                this.bitField0_ &= 0xFFFFFFFD;
                return this;
            }
            
            public Descriptors.Descriptor getDescriptorForType() {
                return MysqlxCrud.internal_static_Mysqlx_Crud_DropView_descriptor;
            }
            
            public DropView getDefaultInstanceForType() {
                return DropView.getDefaultInstance();
            }
            
            public DropView build() {
                final DropView result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw newUninitializedMessageException((Message)result);
                }
                return result;
            }
            
            public DropView buildPartial() {
                final DropView result = new DropView((GeneratedMessageV3.Builder)this);
                final int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 0x1) == 0x1) {
                    to_bitField0_ |= 0x1;
                }
                if (this.collectionBuilder_ == null) {
                    result.collection_ = this.collection_;
                }
                else {
                    result.collection_ = (Collection)this.collectionBuilder_.build();
                }
                if ((from_bitField0_ & 0x2) == 0x2) {
                    to_bitField0_ |= 0x2;
                }
                result.ifExists_ = this.ifExists_;
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
                if (other instanceof DropView) {
                    return this.mergeFrom((DropView)other);
                }
                super.mergeFrom(other);
                return this;
            }
            
            public Builder mergeFrom(final DropView other) {
                if (other == DropView.getDefaultInstance()) {
                    return this;
                }
                if (other.hasCollection()) {
                    this.mergeCollection(other.getCollection());
                }
                if (other.hasIfExists()) {
                    this.setIfExists(other.getIfExists());
                }
                this.mergeUnknownFields(other.unknownFields);
                this.onChanged();
                return this;
            }
            
            public final boolean isInitialized() {
                return this.hasCollection() && this.getCollection().isInitialized();
            }
            
            public Builder mergeFrom(final CodedInputStream input, final ExtensionRegistryLite extensionRegistry) throws IOException {
                DropView parsedMessage = null;
                try {
                    parsedMessage = (DropView)DropView.PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (DropView)e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                }
                finally {
                    if (parsedMessage != null) {
                        this.mergeFrom(parsedMessage);
                    }
                }
                return this;
            }
            
            public boolean hasCollection() {
                return (this.bitField0_ & 0x1) == 0x1;
            }
            
            public Collection getCollection() {
                if (this.collectionBuilder_ == null) {
                    return (this.collection_ == null) ? Collection.getDefaultInstance() : this.collection_;
                }
                return (Collection)this.collectionBuilder_.getMessage();
            }
            
            public Builder setCollection(final Collection value) {
                if (this.collectionBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.collection_ = value;
                    this.onChanged();
                }
                else {
                    this.collectionBuilder_.setMessage((AbstractMessage)value);
                }
                this.bitField0_ |= 0x1;
                return this;
            }
            
            public Builder setCollection(final Collection.Builder builderForValue) {
                if (this.collectionBuilder_ == null) {
                    this.collection_ = builderForValue.build();
                    this.onChanged();
                }
                else {
                    this.collectionBuilder_.setMessage((AbstractMessage)builderForValue.build());
                }
                this.bitField0_ |= 0x1;
                return this;
            }
            
            public Builder mergeCollection(final Collection value) {
                if (this.collectionBuilder_ == null) {
                    if ((this.bitField0_ & 0x1) == 0x1 && this.collection_ != null && this.collection_ != Collection.getDefaultInstance()) {
                        this.collection_ = Collection.newBuilder(this.collection_).mergeFrom(value).buildPartial();
                    }
                    else {
                        this.collection_ = value;
                    }
                    this.onChanged();
                }
                else {
                    this.collectionBuilder_.mergeFrom((AbstractMessage)value);
                }
                this.bitField0_ |= 0x1;
                return this;
            }
            
            public Builder clearCollection() {
                if (this.collectionBuilder_ == null) {
                    this.collection_ = null;
                    this.onChanged();
                }
                else {
                    this.collectionBuilder_.clear();
                }
                this.bitField0_ &= 0xFFFFFFFE;
                return this;
            }
            
            public Collection.Builder getCollectionBuilder() {
                this.bitField0_ |= 0x1;
                this.onChanged();
                return (Collection.Builder)this.getCollectionFieldBuilder().getBuilder();
            }
            
            public CollectionOrBuilder getCollectionOrBuilder() {
                if (this.collectionBuilder_ != null) {
                    return (CollectionOrBuilder)this.collectionBuilder_.getMessageOrBuilder();
                }
                return (this.collection_ == null) ? Collection.getDefaultInstance() : this.collection_;
            }
            
            private SingleFieldBuilderV3<Collection, Collection.Builder, CollectionOrBuilder> getCollectionFieldBuilder() {
                if (this.collectionBuilder_ == null) {
                    this.collectionBuilder_ = (SingleFieldBuilderV3<Collection, Collection.Builder, CollectionOrBuilder>)new SingleFieldBuilderV3((AbstractMessage)this.getCollection(), (AbstractMessage.BuilderParent)this.getParentForChildren(), this.isClean());
                    this.collection_ = null;
                }
                return this.collectionBuilder_;
            }
            
            public boolean hasIfExists() {
                return (this.bitField0_ & 0x2) == 0x2;
            }
            
            public boolean getIfExists() {
                return this.ifExists_;
            }
            
            public Builder setIfExists(final boolean value) {
                this.bitField0_ |= 0x2;
                this.ifExists_ = value;
                this.onChanged();
                return this;
            }
            
            public Builder clearIfExists() {
                this.bitField0_ &= 0xFFFFFFFD;
                this.ifExists_ = false;
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
    
    public interface CollectionOrBuilder extends MessageOrBuilder
    {
        boolean hasName();
        
        String getName();
        
        ByteString getNameBytes();
        
        boolean hasSchema();
        
        String getSchema();
        
        ByteString getSchemaBytes();
    }
    
    public interface DropViewOrBuilder extends MessageOrBuilder
    {
        boolean hasCollection();
        
        Collection getCollection();
        
        CollectionOrBuilder getCollectionOrBuilder();
        
        boolean hasIfExists();
        
        boolean getIfExists();
    }
    
    public interface ProjectionOrBuilder extends MessageOrBuilder
    {
        boolean hasSource();
        
        MysqlxExpr.Expr getSource();
        
        MysqlxExpr.ExprOrBuilder getSourceOrBuilder();
        
        boolean hasAlias();
        
        String getAlias();
        
        ByteString getAliasBytes();
    }
    
    public interface LimitOrBuilder extends MessageOrBuilder
    {
        boolean hasRowCount();
        
        long getRowCount();
        
        boolean hasOffset();
        
        long getOffset();
    }
    
    public interface OrderOrBuilder extends MessageOrBuilder
    {
        boolean hasExpr();
        
        MysqlxExpr.Expr getExpr();
        
        MysqlxExpr.ExprOrBuilder getExprOrBuilder();
        
        boolean hasDirection();
        
        Order.Direction getDirection();
    }
    
    public interface FindOrBuilder extends MessageOrBuilder
    {
        boolean hasCollection();
        
        Collection getCollection();
        
        CollectionOrBuilder getCollectionOrBuilder();
        
        boolean hasDataModel();
        
        DataModel getDataModel();
        
        List<Projection> getProjectionList();
        
        Projection getProjection(final int p0);
        
        int getProjectionCount();
        
        List<? extends ProjectionOrBuilder> getProjectionOrBuilderList();
        
        ProjectionOrBuilder getProjectionOrBuilder(final int p0);
        
        boolean hasCriteria();
        
        MysqlxExpr.Expr getCriteria();
        
        MysqlxExpr.ExprOrBuilder getCriteriaOrBuilder();
        
        List<MysqlxDatatypes.Scalar> getArgsList();
        
        MysqlxDatatypes.Scalar getArgs(final int p0);
        
        int getArgsCount();
        
        List<? extends MysqlxDatatypes.ScalarOrBuilder> getArgsOrBuilderList();
        
        MysqlxDatatypes.ScalarOrBuilder getArgsOrBuilder(final int p0);
        
        boolean hasLimit();
        
        Limit getLimit();
        
        LimitOrBuilder getLimitOrBuilder();
        
        List<Order> getOrderList();
        
        Order getOrder(final int p0);
        
        int getOrderCount();
        
        List<? extends OrderOrBuilder> getOrderOrBuilderList();
        
        OrderOrBuilder getOrderOrBuilder(final int p0);
        
        List<MysqlxExpr.Expr> getGroupingList();
        
        MysqlxExpr.Expr getGrouping(final int p0);
        
        int getGroupingCount();
        
        List<? extends MysqlxExpr.ExprOrBuilder> getGroupingOrBuilderList();
        
        MysqlxExpr.ExprOrBuilder getGroupingOrBuilder(final int p0);
        
        boolean hasGroupingCriteria();
        
        MysqlxExpr.Expr getGroupingCriteria();
        
        MysqlxExpr.ExprOrBuilder getGroupingCriteriaOrBuilder();
        
        boolean hasLocking();
        
        Find.RowLock getLocking();
        
        boolean hasLockingOptions();
        
        Find.RowLockOptions getLockingOptions();
    }
    
    public interface ModifyViewOrBuilder extends MessageOrBuilder
    {
        boolean hasCollection();
        
        Collection getCollection();
        
        CollectionOrBuilder getCollectionOrBuilder();
        
        boolean hasDefiner();
        
        String getDefiner();
        
        ByteString getDefinerBytes();
        
        boolean hasAlgorithm();
        
        ViewAlgorithm getAlgorithm();
        
        boolean hasSecurity();
        
        ViewSqlSecurity getSecurity();
        
        boolean hasCheck();
        
        ViewCheckOption getCheck();
        
        List<String> getColumnList();
        
        int getColumnCount();
        
        String getColumn(final int p0);
        
        ByteString getColumnBytes(final int p0);
        
        boolean hasStmt();
        
        Find getStmt();
        
        FindOrBuilder getStmtOrBuilder();
    }
    
    public interface CreateViewOrBuilder extends MessageOrBuilder
    {
        boolean hasCollection();
        
        Collection getCollection();
        
        CollectionOrBuilder getCollectionOrBuilder();
        
        boolean hasDefiner();
        
        String getDefiner();
        
        ByteString getDefinerBytes();
        
        boolean hasAlgorithm();
        
        ViewAlgorithm getAlgorithm();
        
        boolean hasSecurity();
        
        ViewSqlSecurity getSecurity();
        
        boolean hasCheck();
        
        ViewCheckOption getCheck();
        
        List<String> getColumnList();
        
        int getColumnCount();
        
        String getColumn(final int p0);
        
        ByteString getColumnBytes(final int p0);
        
        boolean hasStmt();
        
        Find getStmt();
        
        FindOrBuilder getStmtOrBuilder();
        
        boolean hasReplaceExisting();
        
        boolean getReplaceExisting();
    }
    
    public interface DeleteOrBuilder extends MessageOrBuilder
    {
        boolean hasCollection();
        
        Collection getCollection();
        
        CollectionOrBuilder getCollectionOrBuilder();
        
        boolean hasDataModel();
        
        DataModel getDataModel();
        
        boolean hasCriteria();
        
        MysqlxExpr.Expr getCriteria();
        
        MysqlxExpr.ExprOrBuilder getCriteriaOrBuilder();
        
        List<MysqlxDatatypes.Scalar> getArgsList();
        
        MysqlxDatatypes.Scalar getArgs(final int p0);
        
        int getArgsCount();
        
        List<? extends MysqlxDatatypes.ScalarOrBuilder> getArgsOrBuilderList();
        
        MysqlxDatatypes.ScalarOrBuilder getArgsOrBuilder(final int p0);
        
        boolean hasLimit();
        
        Limit getLimit();
        
        LimitOrBuilder getLimitOrBuilder();
        
        List<Order> getOrderList();
        
        Order getOrder(final int p0);
        
        int getOrderCount();
        
        List<? extends OrderOrBuilder> getOrderOrBuilderList();
        
        OrderOrBuilder getOrderOrBuilder(final int p0);
    }
    
    public interface UpdateOperationOrBuilder extends MessageOrBuilder
    {
        boolean hasSource();
        
        MysqlxExpr.ColumnIdentifier getSource();
        
        MysqlxExpr.ColumnIdentifierOrBuilder getSourceOrBuilder();
        
        boolean hasOperation();
        
        UpdateOperation.UpdateType getOperation();
        
        boolean hasValue();
        
        MysqlxExpr.Expr getValue();
        
        MysqlxExpr.ExprOrBuilder getValueOrBuilder();
    }
    
    public interface UpdateOrBuilder extends MessageOrBuilder
    {
        boolean hasCollection();
        
        Collection getCollection();
        
        CollectionOrBuilder getCollectionOrBuilder();
        
        boolean hasDataModel();
        
        DataModel getDataModel();
        
        boolean hasCriteria();
        
        MysqlxExpr.Expr getCriteria();
        
        MysqlxExpr.ExprOrBuilder getCriteriaOrBuilder();
        
        List<MysqlxDatatypes.Scalar> getArgsList();
        
        MysqlxDatatypes.Scalar getArgs(final int p0);
        
        int getArgsCount();
        
        List<? extends MysqlxDatatypes.ScalarOrBuilder> getArgsOrBuilderList();
        
        MysqlxDatatypes.ScalarOrBuilder getArgsOrBuilder(final int p0);
        
        boolean hasLimit();
        
        Limit getLimit();
        
        LimitOrBuilder getLimitOrBuilder();
        
        List<Order> getOrderList();
        
        Order getOrder(final int p0);
        
        int getOrderCount();
        
        List<? extends OrderOrBuilder> getOrderOrBuilderList();
        
        OrderOrBuilder getOrderOrBuilder(final int p0);
        
        List<UpdateOperation> getOperationList();
        
        UpdateOperation getOperation(final int p0);
        
        int getOperationCount();
        
        List<? extends UpdateOperationOrBuilder> getOperationOrBuilderList();
        
        UpdateOperationOrBuilder getOperationOrBuilder(final int p0);
    }
    
    public interface ColumnOrBuilder extends MessageOrBuilder
    {
        boolean hasName();
        
        String getName();
        
        ByteString getNameBytes();
        
        boolean hasAlias();
        
        String getAlias();
        
        ByteString getAliasBytes();
        
        List<MysqlxExpr.DocumentPathItem> getDocumentPathList();
        
        MysqlxExpr.DocumentPathItem getDocumentPath(final int p0);
        
        int getDocumentPathCount();
        
        List<? extends MysqlxExpr.DocumentPathItemOrBuilder> getDocumentPathOrBuilderList();
        
        MysqlxExpr.DocumentPathItemOrBuilder getDocumentPathOrBuilder(final int p0);
    }
    
    public interface InsertOrBuilder extends MessageOrBuilder
    {
        boolean hasCollection();
        
        Collection getCollection();
        
        CollectionOrBuilder getCollectionOrBuilder();
        
        boolean hasDataModel();
        
        DataModel getDataModel();
        
        List<Column> getProjectionList();
        
        Column getProjection(final int p0);
        
        int getProjectionCount();
        
        List<? extends ColumnOrBuilder> getProjectionOrBuilderList();
        
        ColumnOrBuilder getProjectionOrBuilder(final int p0);
        
        List<Insert.TypedRow> getRowList();
        
        Insert.TypedRow getRow(final int p0);
        
        int getRowCount();
        
        List<? extends Insert.TypedRowOrBuilder> getRowOrBuilderList();
        
        Insert.TypedRowOrBuilder getRowOrBuilder(final int p0);
        
        List<MysqlxDatatypes.Scalar> getArgsList();
        
        MysqlxDatatypes.Scalar getArgs(final int p0);
        
        int getArgsCount();
        
        List<? extends MysqlxDatatypes.ScalarOrBuilder> getArgsOrBuilderList();
        
        MysqlxDatatypes.ScalarOrBuilder getArgsOrBuilder(final int p0);
        
        boolean hasUpsert();
        
        boolean getUpsert();
    }
}
