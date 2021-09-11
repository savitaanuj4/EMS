
package com.mysql.cj.jdbc.admin;

import java.sql.ResultSet;
import com.mysql.cj.exceptions.ExceptionInterceptor;
import com.mysql.cj.util.TimeUtil;
import java.sql.DriverManager;

public class TimezoneDump
{
    private static final String DEFAULT_URL = "jdbc:mysql:///test";
    
    public static void main(final String[] args) throws Exception {
        String jdbcUrl = "jdbc:mysql:///test";
        if (args.length == 1 && args[0] != null) {
            jdbcUrl = args[0];
        }
        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        ResultSet rs = null;
        try {
            rs = DriverManager.getConnection(jdbcUrl).createStatement().executeQuery("SHOW VARIABLES LIKE 'timezone'");
            while (rs.next()) {
                final String timezoneFromServer = rs.getString(2);
                System.out.println("MySQL timezone name: " + timezoneFromServer);
                final String canonicalTimezone = TimeUtil.getCanonicalTimezone(timezoneFromServer, null);
                System.out.println("Java timezone name: " + canonicalTimezone);
            }
        }
        finally {
            if (rs != null) {
                rs.close();
            }
        }
    }
}
