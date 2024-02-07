package db;

import java.sql.*;

public class DBConnection {
    private static final String url = "jdbc:mysql://localhost:3306/ecomerce";
    private static final String user = "root";
    private static final String password = "admin";
    
    private static Connection conn = null;
    
    public static Connection getConnection() {
        if(conn == null) {
            try {
                conn = DriverManager.getConnection(url, user, password);
                return conn;
            } catch (SQLException e) {
                throw new DBException(e.getMessage());
            }
        }
            return conn;
    }

    public static void validConnection() {
        if(conn != null) {
            System.out.println("No is null");
        }
        else {
            System.out.println("error is null");
        }
    }
}