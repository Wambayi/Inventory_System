package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Map;

public class DBConnection {
    public static Connection getConnection() {
        try {
            Map<String, String> env = System.getenv();

            String url = env.get("DB_URL");
            String user = env.get("DB_USER");
            String password = env.get("DB_PASSWORD");

            return DriverManager.getConnection(url, user, password);

        } catch (Exception e) {
            System.out.println("Database connection failed");
            return null;
        }
    }
}