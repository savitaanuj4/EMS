
package com.mysql.cj;

public class ServerVersion implements Comparable<ServerVersion>
{
    private String completeVersion;
    private Integer major;
    private Integer minor;
    private Integer subminor;
    
    public ServerVersion(final String completeVersion, final int major, final int minor, final int subminor) {
        this.completeVersion = completeVersion;
        this.major = major;
        this.minor = minor;
        this.subminor = subminor;
    }
    
    public ServerVersion(final int major, final int minor, final int subminor) {
        this(null, major, minor, subminor);
    }
    
    public int getMajor() {
        return this.major;
    }
    
    public int getMinor() {
        return this.minor;
    }
    
    public int getSubminor() {
        return this.subminor;
    }
    
    @Override
    public String toString() {
        if (this.completeVersion != null) {
            return this.completeVersion;
        }
        return String.format("%d.%d.%d", this.major, this.minor, this.subminor);
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !ServerVersion.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        final ServerVersion another = (ServerVersion)obj;
        return this.getMajor() == another.getMajor() && this.getMinor() == another.getMinor() && this.getSubminor() == another.getSubminor();
    }
    
    @Override
    public int hashCode() {
        int hash = 23;
        hash += 19 * hash + this.major;
        hash += 19 * hash + this.minor;
        hash += 19 * hash + this.subminor;
        return hash;
    }
    
    @Override
    public int compareTo(final ServerVersion other) {
        int c;
        if ((c = this.major.compareTo(other.getMajor())) != 0) {
            return c;
        }
        if ((c = this.minor.compareTo(other.getMinor())) != 0) {
            return c;
        }
        return this.subminor.compareTo(other.getSubminor());
    }
    
    public boolean meetsMinimum(final ServerVersion min) {
        return this.compareTo(min) >= 0;
    }
    
    public static ServerVersion parseVersion(final String versionString) {
        int point = versionString.indexOf(46);
        if (point != -1) {
            try {
                final int serverMajorVersion = Integer.parseInt(versionString.substring(0, point));
                String remaining = versionString.substring(point + 1, versionString.length());
                point = remaining.indexOf(46);
                if (point != -1) {
                    final int serverMinorVersion = Integer.parseInt(remaining.substring(0, point));
                    int pos;
                    for (remaining = remaining.substring(point + 1, remaining.length()), pos = 0; pos < remaining.length() && remaining.charAt(pos) >= '0' && remaining.charAt(pos) <= '9'; ++pos) {}
                    final int serverSubminorVersion = Integer.parseInt(remaining.substring(0, pos));
                    return new ServerVersion(versionString, serverMajorVersion, serverMinorVersion, serverSubminorVersion);
                }
            }
            catch (NumberFormatException ex) {}
        }
        return new ServerVersion(0, 0, 0);
    }
}
