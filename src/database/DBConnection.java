package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

public class DBConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/inventory_db";
    private static final String USER = "postgres";
    private static final String PASS = "Wambzy20";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (ClassNotFoundException e) {
            throw new SQLException(e);
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
