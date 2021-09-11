
package com.mysql.cj.xdevapi;

import com.mysql.cj.protocol.x.StatementExecuteOk;

public class InsertResultImpl extends UpdateResult implements InsertResult
{
    public InsertResultImpl(final StatementExecuteOk ok) {
        super(ok);
    }
    
    @Override
    public Long getAutoIncrementValue() {
        return this.ok.getLastInsertId();
    }
}
