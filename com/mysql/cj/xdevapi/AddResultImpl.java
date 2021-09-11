
package com.mysql.cj.xdevapi;

import java.util.List;
import com.mysql.cj.protocol.x.StatementExecuteOk;

public class AddResultImpl extends UpdateResult implements AddResult
{
    public AddResultImpl(final StatementExecuteOk ok) {
        super(ok);
    }
    
    @Override
    public List<String> getGeneratedIds() {
        return this.ok.getGeneratedIds();
    }
}
