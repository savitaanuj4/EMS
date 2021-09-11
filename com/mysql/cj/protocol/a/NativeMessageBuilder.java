
package com.mysql.cj.protocol.a;

import com.mysql.cj.protocol.Message;
import com.mysql.cj.util.StringUtils;
import java.util.List;
import com.mysql.cj.exceptions.ExceptionFactory;
import com.mysql.cj.exceptions.CJOperationNotSupportedException;
import com.mysql.cj.MessageBuilder;

public class NativeMessageBuilder implements MessageBuilder<NativePacketPayload>
{
    @Override
    public NativePacketPayload buildSqlStatement(final String statement) {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, "Not supported");
    }
    
    @Override
    public NativePacketPayload buildSqlStatement(final String statement, final List<Object> args) {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, "Not supported");
    }
    
    @Override
    public NativePacketPayload buildClose() {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, "Not supported");
    }
    
    public NativePacketPayload buildComQuery(final NativePacketPayload sharedPacket, final byte[] query) {
        final NativePacketPayload packet = (sharedPacket != null) ? sharedPacket : new NativePacketPayload(query.length + 1);
        packet.writeInteger(NativeConstants.IntegerDataType.INT1, 3L);
        packet.writeBytes(NativeConstants.StringLengthDataType.STRING_FIXED, query);
        return packet;
    }
    
    public NativePacketPayload buildComQuery(final NativePacketPayload sharedPacket, final String query) {
        return this.buildComQuery(sharedPacket, StringUtils.getBytes(query));
    }
    
    public NativePacketPayload buildComQuery(final NativePacketPayload sharedPacket, final String query, final String encoding) {
        return this.buildComQuery(sharedPacket, StringUtils.getBytes(query, encoding));
    }
    
    public NativePacketPayload buildComInitDb(final NativePacketPayload sharedPacket, final byte[] dbName) {
        final NativePacketPayload packet = (sharedPacket != null) ? sharedPacket : new NativePacketPayload(dbName.length + 1);
        packet.writeInteger(NativeConstants.IntegerDataType.INT1, 2L);
        packet.writeBytes(NativeConstants.StringLengthDataType.STRING_FIXED, dbName);
        return packet;
    }
    
    public NativePacketPayload buildComInitDb(final NativePacketPayload sharedPacket, final String dbName) {
        return this.buildComInitDb(sharedPacket, StringUtils.getBytes(dbName));
    }
    
    public NativePacketPayload buildComShutdown(final NativePacketPayload sharedPacket) {
        final NativePacketPayload packet = (sharedPacket != null) ? sharedPacket : new NativePacketPayload(1);
        packet.writeInteger(NativeConstants.IntegerDataType.INT1, 8L);
        return packet;
    }
    
    public NativePacketPayload buildComSetOption(final NativePacketPayload sharedPacket, final int val) {
        final NativePacketPayload packet = (sharedPacket != null) ? sharedPacket : new NativePacketPayload(3);
        packet.writeInteger(NativeConstants.IntegerDataType.INT1, 27L);
        packet.writeInteger(NativeConstants.IntegerDataType.INT2, val);
        return packet;
    }
    
    public NativePacketPayload buildComPing(final NativePacketPayload sharedPacket) {
        final NativePacketPayload packet = (sharedPacket != null) ? sharedPacket : new NativePacketPayload(1);
        packet.writeInteger(NativeConstants.IntegerDataType.INT1, 14L);
        return packet;
    }
    
    public NativePacketPayload buildComQuit(final NativePacketPayload sharedPacket) {
        final NativePacketPayload packet = (sharedPacket != null) ? sharedPacket : new NativePacketPayload(1);
        packet.writeInteger(NativeConstants.IntegerDataType.INT1, 1L);
        return packet;
    }
    
    public NativePacketPayload buildComStmtPrepare(final NativePacketPayload sharedPacket, final byte[] query) {
        final NativePacketPayload packet = (sharedPacket != null) ? sharedPacket : new NativePacketPayload(query.length + 1);
        packet.writeInteger(NativeConstants.IntegerDataType.INT1, 22L);
        packet.writeBytes(NativeConstants.StringLengthDataType.STRING_FIXED, query);
        return packet;
    }
    
    public NativePacketPayload buildComStmtPrepare(final NativePacketPayload sharedPacket, final String queryString, final String characterEncoding) {
        return this.buildComStmtPrepare(sharedPacket, StringUtils.getBytes(queryString, characterEncoding));
    }
    
    public NativePacketPayload buildComStmtClose(final NativePacketPayload sharedPacket, final long serverStatementId) {
        final NativePacketPayload packet = (sharedPacket != null) ? sharedPacket : new NativePacketPayload(5);
        packet.writeInteger(NativeConstants.IntegerDataType.INT1, 25L);
        packet.writeInteger(NativeConstants.IntegerDataType.INT4, serverStatementId);
        return packet;
    }
    
    public NativePacketPayload buildComStmtReset(final NativePacketPayload sharedPacket, final long serverStatementId) {
        final NativePacketPayload packet = (sharedPacket != null) ? sharedPacket : new NativePacketPayload(5);
        packet.writeInteger(NativeConstants.IntegerDataType.INT1, 26L);
        packet.writeInteger(NativeConstants.IntegerDataType.INT4, serverStatementId);
        return packet;
    }
    
    public NativePacketPayload buildComStmtFetch(final NativePacketPayload sharedPacket, final long serverStatementId, final long numRowsToFetch) {
        final NativePacketPayload packet = (sharedPacket != null) ? sharedPacket : new NativePacketPayload(9);
        packet.writeInteger(NativeConstants.IntegerDataType.INT1, 28L);
        packet.writeInteger(NativeConstants.IntegerDataType.INT4, serverStatementId);
        packet.writeInteger(NativeConstants.IntegerDataType.INT4, numRowsToFetch);
        return packet;
    }
    
    public NativePacketPayload buildComStmtSendLongData(final NativePacketPayload sharedPacket, final long serverStatementId, final int parameterIndex, final byte[] longData) {
        final NativePacketPayload packet = (sharedPacket != null) ? sharedPacket : new NativePacketPayload(9);
        packet.writeInteger(NativeConstants.IntegerDataType.INT1, 24L);
        packet.writeInteger(NativeConstants.IntegerDataType.INT4, serverStatementId);
        packet.writeInteger(NativeConstants.IntegerDataType.INT2, parameterIndex);
        packet.writeBytes(NativeConstants.StringLengthDataType.STRING_FIXED, longData);
        return packet;
    }
}
