
package com.mysql.cj.xdevapi;

import java.io.IOException;
import com.mysql.cj.exceptions.AssertionFailedException;
import java.io.StringReader;
import com.mysql.cj.util.StringUtils;
import com.mysql.cj.result.DefaultValueFactory;

public class DbDocValueFactory extends DefaultValueFactory<DbDoc>
{
    private String encoding;
    
    public DbDocValueFactory() {
    }
    
    public DbDocValueFactory(final String encoding) {
        this.encoding = encoding;
    }
    
    @Override
    public DbDoc createFromBytes(final byte[] bytes, final int offset, final int length) {
        try {
            return JsonParser.parseDoc(new StringReader(StringUtils.toString(bytes, offset, length, this.encoding)));
        }
        catch (IOException ex) {
            throw AssertionFailedException.shouldNotHappen(ex);
        }
    }
    
    @Override
    public DbDoc createFromNull() {
        return null;
    }
    
    @Override
    public String getTargetTypeName() {
        return DbDoc.class.getName();
    }
}
