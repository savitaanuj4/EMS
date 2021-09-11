
package com.mysql.cj.xdevapi;

import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.List;
import java.util.Iterator;
import com.mysql.cj.protocol.x.StatementExecuteOk;

public class UpdateResult implements Result
{
    protected StatementExecuteOk ok;
    
    public UpdateResult(final StatementExecuteOk ok) {
        this.ok = ok;
    }
    
    @Override
    public long getAffectedItemsCount() {
        return this.ok.getRowsAffected();
    }
    
    @Override
    public int getWarningsCount() {
        return this.ok.getWarnings().size();
    }
    
    @Override
    public Iterator<Warning> getWarnings() {
        return this.ok.getWarnings().stream().map(w -> new WarningImpl(w)).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList()).iterator();
    }
}
