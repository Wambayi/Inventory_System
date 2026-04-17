package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

public class DBConnection {
    public static Connection getConnection() {
        try {
            Map<String, String> env = System.getenv();

            String url = env.get("DB_URL");
            String user = env.get("DB_USER");
            String password = env.get("DB_PASSWORD");

            System.out.println("URL: " + url);
            System.out.println("USER: " + user);
            System.out.println("PASSWORD: " + password);

            return DriverManager.getConnection(url, user, password);

        } catch (SQLException e) {
            System.out.println("Database connection failed");
        }

        return null;
    }
    }
