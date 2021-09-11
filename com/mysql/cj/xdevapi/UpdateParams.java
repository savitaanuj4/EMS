
package com.mysql.cj.xdevapi;

import java.util.HashMap;
import com.mysql.cj.x.protobuf.MysqlxExpr;
import java.util.Map;

public class UpdateParams
{
    private Map<MysqlxExpr.ColumnIdentifier, MysqlxExpr.Expr> updateOps;
    
    public UpdateParams() {
        this.updateOps = new HashMap<MysqlxExpr.ColumnIdentifier, MysqlxExpr.Expr>();
    }
    
    public void setUpdates(final Map<String, Object> updates) {
        updates.entrySet().forEach(e -> this.addUpdate(e.getKey(), e.getValue()));
    }
    
    public void addUpdate(final String path, final Object value) {
        this.updateOps.put(new ExprParser(path, true).parseTableUpdateField(), ExprUtil.argObjectToExpr(value, true));
    }
    
    public Object getUpdates() {
        return this.updateOps;
    }
}
