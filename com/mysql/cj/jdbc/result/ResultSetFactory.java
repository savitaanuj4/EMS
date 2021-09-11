
package com.mysql.cj.jdbc.result;

import com.mysql.cj.protocol.a.result.ResultsetRowsCursor;
import com.mysql.cj.exceptions.WrongArgumentException;
import com.mysql.cj.protocol.ResultsetRows;
import com.mysql.cj.protocol.a.result.OkPacket;
import com.mysql.cj.protocol.ProtocolEntity;
import com.mysql.cj.exceptions.ExceptionFactory;
import java.sql.SQLException;
import com.mysql.cj.protocol.Resultset;
import com.mysql.cj.jdbc.StatementImpl;
import com.mysql.cj.jdbc.JdbcConnection;
import com.mysql.cj.protocol.a.NativePacketPayload;
import com.mysql.cj.protocol.ProtocolEntityFactory;

public class ResultSetFactory implements ProtocolEntityFactory<ResultSetImpl, NativePacketPayload>
{
    private JdbcConnection conn;
    private StatementImpl stmt;
    private Resultset.Type type;
    private Resultset.Concurrency concurrency;
    
    public ResultSetFactory(final JdbcConnection connection, final StatementImpl creatorStmt) throws SQLException {
        this.type = Resultset.Type.FORWARD_ONLY;
        this.concurrency = Resultset.Concurrency.READ_ONLY;
        this.conn = connection;
        this.stmt = creatorStmt;
        if (creatorStmt != null) {
            this.type = Resultset.Type.fromValue(creatorStmt.getResultSetType(), Resultset.Type.FORWARD_ONLY);
            this.concurrency = Resultset.Concurrency.fromValue(creatorStmt.getResultSetConcurrency(), Resultset.Concurrency.READ_ONLY);
        }
    }
    
    @Override
    public Resultset.Type getResultSetType() {
        return this.type;
    }
    
    @Override
    public Resultset.Concurrency getResultSetConcurrency() {
        return this.concurrency;
    }
    
    @Override
    public int getFetchSize() {
        try {
            return this.stmt.getFetchSize();
        }
        catch (SQLException ex) {
            throw ExceptionFactory.createException(ex.getMessage(), ex);
        }
    }
    
    @Override
    public ResultSetImpl createFromProtocolEntity(final ProtocolEntity protocolEntity) {
        try {
            if (protocolEntity instanceof OkPacket) {
                return new ResultSetImpl((OkPacket)protocolEntity, this.conn, this.stmt);
            }
            if (protocolEntity instanceof ResultsetRows) {
                final int resultSetConcurrency = this.getResultSetConcurrency().getIntValue();
                final int resultSetType = this.getResultSetType().getIntValue();
                return this.createFromResultsetRows(resultSetConcurrency, resultSetType, (ResultsetRows)protocolEntity);
            }
            throw ExceptionFactory.createException(WrongArgumentException.class, "Unknown ProtocolEntity class " + protocolEntity);
        }
        catch (SQLException ex) {
            throw ExceptionFactory.createException(ex.getMessage(), ex);
        }
    }
    
    public ResultSetImpl createFromResultsetRows(final int resultSetConcurrency, final int resultSetType, final ResultsetRows rows) throws SQLException {
        StatementImpl st = this.stmt;
        if (rows.getOwner() != null) {
            st = ((ResultSetImpl)rows.getOwner()).getOwningStatement();
        }
        ResultSetImpl rs = null;
        switch (resultSetConcurrency) {
            case 1008: {
                rs = new UpdatableResultSet(rows, this.conn, st);
                break;
            }
            default: {
                rs = new ResultSetImpl(rows, this.conn, st);
                break;
            }
        }
        rs.setResultSetType(resultSetType);
        rs.setResultSetConcurrency(resultSetConcurrency);
        if (rows instanceof ResultsetRowsCursor && st != null) {
            rs.setFetchSize(st.getFetchSize());
        }
        return rs;
    }
}
