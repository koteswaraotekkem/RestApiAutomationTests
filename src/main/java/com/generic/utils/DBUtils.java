package com.generic.utils;

import java.sql.*;

public class DBUtils {

    public void testDBMS(String query) throws SQLException, ClassNotFoundException {
        String connectionUrl =
                "jdbc:sqlserver://172.16.0.4:1433;"
                        + "database=exchange;"
                        + "user=tao;"
                        + "password=Windows@10;"
                        + "encrypt=true;"
                        + "trustServerCertificate=true;"
                        + "loginTimeout=30;";

        Connection connection = DriverManager.getConnection(connectionUrl);
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(query);
        while (rs.next()) {
            System.out.println(rs.getString("CustomerFirstName"));
            System.out.println(rs.getRow());
        }
    }
}
