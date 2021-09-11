
package com.mysql.cj;

import java.util.Iterator;
import java.util.Set;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Locale;
import java.util.List;

class MysqlCharset
{
    public final String charsetName;
    public final int mblen;
    public final int priority;
    public final List<String> javaEncodingsUc;
    public final ServerVersion minimumVersion;
    
    public MysqlCharset(final String charsetName, final int mblen, final int priority, final String[] javaEncodings) {
        this(charsetName, mblen, priority, javaEncodings, new ServerVersion(0, 0, 0));
    }
    
    private void addEncodingMapping(final String encoding) {
        final String encodingUc = encoding.toUpperCase(Locale.ENGLISH);
        if (!this.javaEncodingsUc.contains(encodingUc)) {
            this.javaEncodingsUc.add(encodingUc);
        }
    }
    
    public MysqlCharset(final String charsetName, final int mblen, final int priority, final String[] javaEncodings, final ServerVersion minimumVersion) {
        this.javaEncodingsUc = new ArrayList<String>();
        this.charsetName = charsetName;
        this.mblen = mblen;
        this.priority = priority;
        for (int i = 0; i < javaEncodings.length; ++i) {
            final String encoding = javaEncodings[i];
            try {
                final Charset cs = Charset.forName(encoding);
                this.addEncodingMapping(cs.name());
                final Set<String> als = cs.aliases();
                final Iterator<String> ali = als.iterator();
                while (ali.hasNext()) {
                    this.addEncodingMapping(ali.next());
                }
            }
            catch (Exception e) {
                if (mblen == 1) {
                    this.addEncodingMapping(encoding);
                }
            }
        }
        if (this.javaEncodingsUc.size() == 0) {
            if (mblen > 1) {
                this.addEncodingMapping("UTF-8");
            }
            else {
                this.addEncodingMapping("Cp1252");
            }
        }
        this.minimumVersion = minimumVersion;
    }
    
    @Override
    public String toString() {
        final StringBuilder asString = new StringBuilder();
        asString.append("[");
        asString.append("charsetName=");
        asString.append(this.charsetName);
        asString.append(",mblen=");
        asString.append(this.mblen);
        asString.append("]");
        return asString.toString();
    }
    
    boolean isOkayForVersion(final ServerVersion version) {
        return version.meetsMinimum(this.minimumVersion);
    }
    
    String getMatchingJavaEncoding(final String javaEncoding) {
        if (javaEncoding != null && this.javaEncodingsUc.contains(javaEncoding.toUpperCase(Locale.ENGLISH))) {
            return javaEncoding;
        }
        return this.javaEncodingsUc.get(0);
    }
}
