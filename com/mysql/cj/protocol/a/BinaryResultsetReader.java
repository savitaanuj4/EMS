
package com.mysql.cj.protocol.a;

import com.mysql.cj.protocol.Message;
import java.io.IOException;
import com.mysql.cj.protocol.ResultsetRows;
import com.mysql.cj.protocol.a.result.OkPacket;
import com.mysql.cj.protocol.ProtocolEntity;
import com.mysql.cj.protocol.a.result.ResultsetRowsStreaming;
import com.mysql.cj.result.Row;
import java.util.List;
import com.mysql.cj.protocol.a.result.ResultsetRowsStatic;
import com.mysql.cj.protocol.ResultsetRow;
import java.util.ArrayList;
import com.mysql.cj.protocol.a.result.ResultsetRowsCursor;
import com.mysql.cj.conf.PropertyKey;
import com.mysql.cj.protocol.ProtocolEntityFactory;
import com.mysql.cj.protocol.ColumnDefinition;
import com.mysql.cj.protocol.Resultset;
import com.mysql.cj.protocol.ProtocolEntityReader;

public class BinaryResultsetReader implements ProtocolEntityReader<Resultset, NativePacketPayload>
{
    protected NativeProtocol protocol;
    
    public BinaryResultsetReader(final NativeProtocol prot) {
        this.protocol = prot;
    }
    
    @Override
    public Resultset read(final int maxRows, final boolean streamResults, NativePacketPayload resultPacket, final ColumnDefinition metadata, final ProtocolEntityFactory<Resultset, NativePacketPayload> resultSetFactory) throws IOException {
        Resultset rs = null;
        final long columnCount = resultPacket.readInteger(NativeConstants.IntegerDataType.INT_LENENC);
        if (columnCount > 0L) {
            final ColumnDefinition cdef = this.protocol.read(ColumnDefinition.class, new MergingColumnDefinitionFactory(columnCount, metadata));
            final boolean isCursorPosible = this.protocol.getPropertySet().getBooleanProperty(PropertyKey.useCursorFetch).getValue() && resultSetFactory.getResultSetType() == Resultset.Type.FORWARD_ONLY && resultSetFactory.getFetchSize() > 0;
            if (isCursorPosible || !this.protocol.getServerSession().isEOFDeprecated()) {
                this.protocol.readServerStatusForResultSets(this.protocol.readMessage(this.protocol.getReusablePacket()), true);
            }
            ResultsetRows rows = null;
            if (isCursorPosible && this.protocol.getServerSession().cursorExists()) {
                rows = new ResultsetRowsCursor(this.protocol, cdef);
            }
            else if (!streamResults) {
                final BinaryRowFactory brf = new BinaryRowFactory(this.protocol, cdef, resultSetFactory.getResultSetConcurrency(), false);
                final ArrayList<ResultsetRow> rowList = new ArrayList<ResultsetRow>();
                for (ResultsetRow row = this.protocol.read(ResultsetRow.class, brf); row != null; row = this.protocol.read(ResultsetRow.class, brf)) {
                    if (maxRows == -1 || rowList.size() < maxRows) {
                        rowList.add(row);
                    }
                }
                rows = new ResultsetRowsStatic(rowList, cdef);
            }
            else {
                rows = new ResultsetRowsStreaming<Object>(this.protocol, cdef, true, resultSetFactory);
                this.protocol.setStreamingData(rows);
            }
            rs = resultSetFactory.createFromProtocolEntity(rows);
        }
        else {
            if (columnCount == -1L) {
                final String charEncoding = this.protocol.getPropertySet().getStringProperty(PropertyKey.characterEncoding).getValue();
                final String fileName = resultPacket.readString(NativeConstants.StringSelfDataType.STRING_TERM, this.protocol.doesPlatformDbCharsetMatches() ? charEncoding : null);
                resultPacket = this.protocol.sendFileToServer(fileName);
            }
            final OkPacket ok = this.protocol.readServerStatusForResultSets(resultPacket, false);
            rs = resultSetFactory.createFromProtocolEntity(ok);
        }
        return rs;
    }
}
