
package com.mysql.cj.protocol.x;

import com.google.protobuf.MessageLite;
import java.util.Collection;
import java.util.ArrayList;
import com.google.protobuf.Parser;
import com.google.protobuf.UnknownFieldSet;
import com.google.protobuf.Descriptors;
import java.util.Map;
import com.google.protobuf.CodedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import com.google.protobuf.ByteString;
import java.util.List;
import com.mysql.cj.protocol.Message;

public class XMessage implements Message, com.google.protobuf.Message
{
    private com.google.protobuf.Message message;
    private List<Notice> notices;
    
    public XMessage(final com.google.protobuf.Message mess) {
        this.notices = null;
        this.message = mess;
    }
    
    public com.google.protobuf.Message getMessage() {
        return this.message;
    }
    
    @Override
    public byte[] getByteBuffer() {
        return this.message.toByteArray();
    }
    
    @Override
    public int getPosition() {
        return 0;
    }
    
    public int getSerializedSize() {
        return this.message.getSerializedSize();
    }
    
    public byte[] toByteArray() {
        return this.message.toByteArray();
    }
    
    public ByteString toByteString() {
        return this.message.toByteString();
    }
    
    public void writeDelimitedTo(final OutputStream arg0) throws IOException {
        this.message.writeDelimitedTo(arg0);
    }
    
    public void writeTo(final CodedOutputStream arg0) throws IOException {
        this.message.writeTo(arg0);
    }
    
    public void writeTo(final OutputStream arg0) throws IOException {
        this.message.writeTo(arg0);
    }
    
    public boolean isInitialized() {
        return this.message.isInitialized();
    }
    
    public List<String> findInitializationErrors() {
        return (List<String>)this.message.findInitializationErrors();
    }
    
    public Map<Descriptors.FieldDescriptor, Object> getAllFields() {
        return (Map<Descriptors.FieldDescriptor, Object>)this.message.getAllFields();
    }
    
    public com.google.protobuf.Message getDefaultInstanceForType() {
        return this.message.getDefaultInstanceForType();
    }
    
    public Descriptors.Descriptor getDescriptorForType() {
        return this.message.getDescriptorForType();
    }
    
    public Object getField(final Descriptors.FieldDescriptor arg0) {
        return this.message.getField(arg0);
    }
    
    public String getInitializationErrorString() {
        return this.message.getInitializationErrorString();
    }
    
    public Descriptors.FieldDescriptor getOneofFieldDescriptor(final Descriptors.OneofDescriptor arg0) {
        return this.message.getOneofFieldDescriptor(arg0);
    }
    
    public Object getRepeatedField(final Descriptors.FieldDescriptor arg0, final int arg1) {
        return this.message.getRepeatedField(arg0, arg1);
    }
    
    public int getRepeatedFieldCount(final Descriptors.FieldDescriptor arg0) {
        return this.message.getRepeatedFieldCount(arg0);
    }
    
    public UnknownFieldSet getUnknownFields() {
        return this.message.getUnknownFields();
    }
    
    public boolean hasField(final Descriptors.FieldDescriptor arg0) {
        return this.message.hasField(arg0);
    }
    
    public boolean hasOneof(final Descriptors.OneofDescriptor arg0) {
        return this.message.hasOneof(arg0);
    }
    
    public Parser<? extends com.google.protobuf.Message> getParserForType() {
        return (Parser<? extends com.google.protobuf.Message>)this.message.getParserForType();
    }
    
    public com.google.protobuf.Message.Builder newBuilderForType() {
        return this.message.newBuilderForType();
    }
    
    public com.google.protobuf.Message.Builder toBuilder() {
        return this.message.toBuilder();
    }
    
    public List<Notice> getNotices() {
        return this.notices;
    }
    
    public XMessage addNotices(final List<Notice> n) {
        if (n != null) {
            if (this.notices == null) {
                this.notices = new ArrayList<Notice>();
            }
            this.notices.addAll(n);
        }
        return this;
    }
}
