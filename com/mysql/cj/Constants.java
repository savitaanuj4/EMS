
package com.mysql.cj;

public class Constants
{
    public static final byte[] EMPTY_BYTE_ARRAY;
    public static final String MILLIS_I18N;
    public static final byte[] SLASH_STAR_SPACE_AS_BYTES;
    public static final byte[] SPACE_STAR_SLASH_SPACE_AS_BYTES;
    public static final String JVM_VENDOR;
    public static final String JVM_VERSION;
    public static final String OS_NAME;
    public static final String OS_ARCH;
    public static final String OS_VERSION;
    public static final String PLATFORM_ENCODING;
    public static final String CJ_NAME = "MySQL Connector/J";
    public static final String CJ_FULL_NAME = "mysql-connector-java-8.0.14";
    public static final String CJ_REVISION = "36534fa273b4d7824a8668ca685465cf8eaeadd9";
    public static final String CJ_VERSION = "8.0.14";
    public static final String CJ_MAJOR_VERSION = "8";
    public static final String CJ_MINOR_VERSION = "0";
    public static final String CJ_LICENSE = "GPL";
    
    private Constants() {
    }
    
    static {
        EMPTY_BYTE_ARRAY = new byte[0];
        MILLIS_I18N = Messages.getString("Milliseconds");
        SLASH_STAR_SPACE_AS_BYTES = new byte[] { 47, 42, 32 };
        SPACE_STAR_SLASH_SPACE_AS_BYTES = new byte[] { 32, 42, 47, 32 };
        JVM_VENDOR = System.getProperty("java.vendor");
        JVM_VERSION = System.getProperty("java.version");
        OS_NAME = System.getProperty("os.name");
        OS_ARCH = System.getProperty("os.arch");
        OS_VERSION = System.getProperty("os.version");
        PLATFORM_ENCODING = System.getProperty("file.encoding");
    }
}
