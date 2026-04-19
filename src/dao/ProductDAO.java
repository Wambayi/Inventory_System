package dao;

import database.DBConnection;
import model.Product;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    public List<Product> getProducts() {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM products ORDER BY id DESC";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Product(rs.getString("name"), rs.getInt("quantity"), rs.getDouble("price")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public double getTotalValue() {
        String sql = "SELECT SUM(price * quantity) FROM products";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) return rs.getDouble(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    public void addProduct(Product p) throws SQLException {
        String sql = "INSERT INTO products (name, quantity, price) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, p.getName());
            pstmt.setInt(2, p.getQuantity());
            pstmt.setDouble(3, p.getPrice());
            pstmt.executeUpdate();
        }
    }

    public void updateProduct(Product p) throws SQLException {
        String sql = "UPDATE products SET quantity = ?, price = ? WHERE name = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, p.getQuantity());
            pstmt.setDouble(2, p.getPrice());
            pstmt.setString(3, p.getName());
            pstmt.executeUpdate();
        }
    }

    public void deleteProduct(String name) throws SQLException {
        String sql = "DELETE FROM products WHERE name = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.executeUpdate();
        }
    }
}