
package com.mysql.cj.protocol.x;

import com.mysql.cj.protocol.Message;
import com.google.protobuf.GeneratedMessageV3;
import com.mysql.cj.exceptions.WrongArgumentException;
import com.mysql.cj.x.protobuf.MysqlxNotice;
import com.mysql.cj.x.protobuf.Mysqlx;
import com.mysql.cj.result.Row;
import com.mysql.cj.x.protobuf.MysqlxSql;
import com.mysql.cj.result.DefaultColumnDefinition;
import com.mysql.cj.x.protobuf.MysqlxResultset;
import com.mysql.cj.protocol.ColumnDefinition;
import java.util.ArrayList;
import com.mysql.cj.result.Field;
import com.mysql.cj.protocol.ProtocolEntityFactory;
import com.mysql.cj.protocol.ResultListener;
import com.mysql.cj.protocol.MessageListener;

public class ResultMessageListener implements MessageListener<XMessage>
{
    private ResultListener<StatementExecuteOk> callbacks;
    private ProtocolEntityFactory<Field, XMessage> fieldFactory;
    private ArrayList<Field> fields;
    private ColumnDefinition metadata;
    private boolean metadataSent;
    private StatementExecuteOkBuilder okBuilder;
    
    public ResultMessageListener(final ProtocolEntityFactory<Field, XMessage> colToField, final ResultListener<StatementExecuteOk> callbacks) {
        this.fields = new ArrayList<Field>();
        this.metadata = null;
        this.metadataSent = false;
        this.okBuilder = new StatementExecuteOkBuilder();
        this.callbacks = callbacks;
        this.fieldFactory = colToField;
    }
    
    @Override
    public Boolean createFromMessage(final XMessage message) {
        final Class<? extends GeneratedMessageV3> msgClass = (Class<? extends GeneratedMessageV3>)message.getMessage().getClass();
        if (MysqlxResultset.ColumnMetaData.class.equals(msgClass)) {
            final Field f = this.fieldFactory.createFromMessage(message);
            this.fields.add(f);
            return false;
        }
        if (!this.metadataSent) {
            if (this.metadata == null) {
                this.metadata = new DefaultColumnDefinition(this.fields.toArray(new Field[0]));
            }
            this.callbacks.onMetadata(this.metadata);
            this.metadataSent = true;
        }
        if (MysqlxSql.StmtExecuteOk.class.equals(msgClass)) {
            this.callbacks.onComplete(this.okBuilder.build());
            return true;
        }
        if (MysqlxResultset.FetchDone.class.equals(msgClass)) {
            return false;
        }
        if (MysqlxResultset.Row.class.equals(msgClass)) {
            if (this.metadata == null) {
                this.metadata = new DefaultColumnDefinition(this.fields.toArray(new Field[0]));
            }
            final XProtocolRow row = new XProtocolRow(this.metadata, MysqlxResultset.Row.class.cast(message.getMessage()));
            this.callbacks.onRow(row);
            return false;
        }
        if (Mysqlx.Error.class.equals(msgClass)) {
            final XProtocolError e = new XProtocolError(Mysqlx.Error.class.cast(message.getMessage()));
            this.callbacks.onException(e);
            return true;
        }
        if (MysqlxNotice.Frame.class.equals(msgClass)) {
            this.okBuilder.addNotice(Notice.getInstance(message));
            return false;
        }
        this.callbacks.onException(new WrongArgumentException("Unhandled msg class (" + msgClass + ") + msg=" + message.getMessage()));
        return false;
    }
    
    @Override
    public void error(final Throwable ex) {
        this.callbacks.onException(ex);
    }
}
