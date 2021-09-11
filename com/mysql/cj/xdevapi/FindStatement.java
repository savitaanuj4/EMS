
package com.mysql.cj.xdevapi;

public interface FindStatement extends Statement<FindStatement, DocResult>
{
    FindStatement fields(final String... p0);
    
    FindStatement fields(final Expression p0);
    
    FindStatement groupBy(final String... p0);
    
    FindStatement having(final String p0);
    
    FindStatement orderBy(final String... p0);
    
    FindStatement sort(final String... p0);
    
    @Deprecated
    default FindStatement skip(final long limitOffset) {
        return this.offset(limitOffset);
    }
    
    FindStatement offset(final long p0);
    
    FindStatement limit(final long p0);
    
    FindStatement lockShared();
    
    FindStatement lockShared(final LockContention p0);
    
    FindStatement lockExclusive();
    
    FindStatement lockExclusive(final LockContention p0);
}
