
package com.mysql.cj;

import com.mysql.cj.protocol.Resultset;
import com.mysql.cj.protocol.a.NativePacketPayload;
import com.mysql.cj.protocol.ProtocolEntityFactory;
import com.mysql.cj.protocol.ColumnDefinition;
import java.io.IOException;
import com.mysql.cj.util.TestUtils;

public class ServerPreparedQueryTestcaseGenerator extends ServerPreparedQuery
{
    public ServerPreparedQueryTestcaseGenerator(final NativeSession sess) {
        super(sess);
    }
    
    @Override
    public void closeQuery() {
        this.dumpCloseForTestcase();
        super.closeQuery();
    }
    
    private void dumpCloseForTestcase() {
        final StringBuilder buf = new StringBuilder();
        this.session.getProtocol().generateQueryCommentBlock(buf);
        buf.append("DEALLOCATE PREPARE debug_stmt_");
        buf.append(this.statementId);
        buf.append(";\n");
        TestUtils.dumpTestcaseQuery(buf.toString());
    }
    
    @Override
    public void serverPrepare(final String sql) throws IOException {
        this.dumpPrepareForTestcase();
        super.serverPrepare(sql);
    }
    
    private void dumpPrepareForTestcase() {
        final StringBuilder buf = new StringBuilder(this.getOriginalSql().length() + 64);
        this.session.getProtocol().generateQueryCommentBlock(buf);
        buf.append("PREPARE debug_stmt_");
        buf.append(this.statementId);
        buf.append(" FROM \"");
        buf.append(this.getOriginalSql());
        buf.append("\";\n");
        TestUtils.dumpTestcaseQuery(buf.toString());
    }
    
    @Override
    public <T extends Resultset> T serverExecute(final int maxRowsToRetrieve, final boolean createStreamingResultSet, final ColumnDefinition metadata, final ProtocolEntityFactory<T, NativePacketPayload> resultSetFactory) {
        this.dumpExecuteForTestcase();
        return super.serverExecute(maxRowsToRetrieve, createStreamingResultSet, metadata, resultSetFactory);
    }
    
    private void dumpExecuteForTestcase() {
        final StringBuilder buf = new StringBuilder();
        for (int i = 0; i < this.getParameterCount(); ++i) {
            this.session.getProtocol().generateQueryCommentBlock(buf);
            buf.append("SET @debug_stmt_param");
            buf.append(this.statementId);
            buf.append("_");
            buf.append(i);
            buf.append("=");
            final ServerPreparedQueryBindValue bv = ((ServerPreparedQueryBindings)this.queryBindings).getBindValues()[i];
            buf.append(bv.isNull() ? "NULL" : bv.toString(true));
            buf.append(";\n");
        }
        this.session.getProtocol().generateQueryCommentBlock(buf);
        buf.append("EXECUTE debug_stmt_");
        buf.append(this.statementId);
        if (this.getParameterCount() > 0) {
            buf.append(" USING ");
            for (int i = 0; i < this.getParameterCount(); ++i) {
                if (i > 0) {
                    buf.append(", ");
                }
                buf.append("@debug_stmt_param");
                buf.append(this.statementId);
                buf.append("_");
                buf.append(i);
            }
        }
        buf.append(";\n");
        TestUtils.dumpTestcaseQuery(buf.toString());
    }
}
