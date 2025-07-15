package ch20;

import java.sql.*;

public class DBUtil {
   
    private static final String USER = "root";
    private static final String PASS = "1234";
    private static final String URL  = "jdbc:mysql://localhost:3306/";
    static {                       
        try { Class.forName("com.mysql.cj.jdbc.Driver"); }
        catch (Exception ignored) {}
    }

    public static void init() throws Exception {
        try (Connection c = DriverManager.getConnection(URL, USER, PASS);
             Statement  s = c.createStatement()) {
            s.executeUpdate("CREATE DATABASE IF NOT EXISTS boarddb " +
                    "DEFAULT CHARACTER SET utf8mb4");
        }
        try (Connection c = getConnection(); Statement s = c.createStatement()) {
            s.executeUpdate("""
                CREATE TABLE IF NOT EXISTS board (
                  id INT AUTO_INCREMENT PRIMARY KEY,
                  title   VARCHAR(100) NOT NULL,
                  writer  VARCHAR(30)  NOT NULL,
                  content TEXT         NOT NULL,  
                  regdate DATETIME DEFAULT CURRENT_TIMESTAMP,
                  hits    INT DEFAULT 0
                )
            """);
        }
    }
    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(
                URL + "boarddb?serverTimezone=Asia/Seoul", USER, PASS);
    }
}
