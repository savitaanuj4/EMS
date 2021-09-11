
package com.mysql.cj.protocol.x;

import java.util.Collections;
import com.mysql.cj.protocol.Warning;
import java.util.List;
import com.mysql.cj.QueryResult;
import com.mysql.cj.protocol.ProtocolEntity;

public class StatementExecuteOk implements ProtocolEntity, QueryResult
{
    private long rowsAffected;
    private Long lastInsertId;
    private List<String> generatedIds;
    private List<Warning> warnings;
    
    public StatementExecuteOk(final long rowsAffected, final Long lastInsertId, final List<String> generatedIds, final List<Warning> warnings) {
        this.rowsAffected = rowsAffected;
        this.lastInsertId = lastInsertId;
        this.generatedIds = Collections.unmodifiableList((List<? extends String>)generatedIds);
        this.warnings = warnings;
    }
    
    public long getRowsAffected() {
        return this.rowsAffected;
    }
    
    public Long getLastInsertId() {
        return this.lastInsertId;
    }
    
    public List<String> getGeneratedIds() {
        return this.generatedIds;
    }
    
    public List<Warning> getWarnings() {
        return this.warnings;
    }
}
