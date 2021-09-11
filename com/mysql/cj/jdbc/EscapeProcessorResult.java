
package com.mysql.cj.jdbc;

class EscapeProcessorResult
{
    boolean callingStoredFunction;
    String escapedSql;
    byte usesVariables;
    
    EscapeProcessorResult() {
        this.callingStoredFunction = false;
        this.usesVariables = 0;
    }
}
