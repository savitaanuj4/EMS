
package com.mysql.cj.jdbc.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.Map;

public class ResultSetUtil
{
    public static void resultSetToMap(final Map mappedValues, final ResultSet rs) throws SQLException {
        while (rs.next()) {
            mappedValues.put(rs.getObject(1), rs.getObject(2));
        }
    }
    
    public static void resultSetToMap(final Map mappedValues, final ResultSet rs, final int key, final int value) throws SQLException {
        while (rs.next()) {
            mappedValues.put(rs.getObject(key), rs.getObject(value));
        }
    }
    
    public static Object readObject(final ResultSet resultSet, final int index) throws IOException, SQLException, ClassNotFoundException {
        final ObjectInputStream objIn = new ObjectInputStream(resultSet.getBinaryStream(index));
        final Object obj = objIn.readObject();
        objIn.close();
        return obj;
    }
}
