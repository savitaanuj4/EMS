
package com.mysql.cj.xdevapi;

import com.mysql.cj.protocol.ProtocolEntityFactory;
import com.mysql.cj.protocol.x.StatementExecuteOk;
import java.util.function.Supplier;
import com.mysql.cj.result.RowList;

public class DocResultImpl extends AbstractDataResult<DbDoc> implements DocResult
{
    public DocResultImpl(final RowList rows, final Supplier<StatementExecuteOk> completer) {
        super(rows, completer, new DbDocFactory());
    }
}
