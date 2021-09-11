
package com.mysql.cj.protocol.x;

import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Collections;
import com.mysql.cj.protocol.Warning;
import java.util.List;

public class StatementExecuteOkBuilder
{
    private long rowsAffected;
    private Long lastInsertId;
    private List<String> generatedIds;
    private List<Warning> warnings;
    
    public StatementExecuteOkBuilder() {
        this.rowsAffected = 0L;
        this.lastInsertId = null;
        this.generatedIds = Collections.emptyList();
        this.warnings = new ArrayList<Warning>();
    }
    
    public void addNotice(final Notice notice) {
        if (notice instanceof Notice.XWarning) {
            this.warnings.add((Notice.XWarning)notice);
        }
        else if (notice instanceof Notice.XSessionStateChanged) {
            switch (((Notice.XSessionStateChanged)notice).getParamType()) {
                case 3: {
                    this.lastInsertId = ((Notice.XSessionStateChanged)notice).getValue().getVUnsignedInt();
                    break;
                }
                case 4: {
                    this.rowsAffected = ((Notice.XSessionStateChanged)notice).getValue().getVUnsignedInt();
                    break;
                }
                case 12: {
                    this.generatedIds = ((Notice.XSessionStateChanged)notice).getValueList().stream().map(v -> v.getVOctets().getValue().toStringUtf8()).collect((Collector<? super Object, ?, List<String>>)Collectors.toList());
                    break;
                }
            }
        }
    }
    
    public StatementExecuteOk build() {
        return new StatementExecuteOk(this.rowsAffected, this.lastInsertId, this.generatedIds, this.warnings);
    }
}
