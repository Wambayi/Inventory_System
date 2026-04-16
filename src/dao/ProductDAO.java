package dao;

import database.DBConnection;
import model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    // 1. CREATE: Adds a new product
    public void addProduct(Product product) {
        // Section B Validation: Prevent negative pricing
        if (product.getPrice() < 0) {
            System.out.println("Invalid price. Product not added.");
            return;
        }

        String sql = "INSERT INTO products(name, quantity, price) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, product.getName());
            stmt.setInt(2, product.getQuantity());
            stmt.setDouble(3, product.getPrice());

            stmt.executeUpdate();
            System.out.println("Product added successfully!");

        } catch (Exception e) {
            System.out.println("Error inserting product: " + e.getMessage());
        }
    }

    // 2. READ: Gets all products for the TableView
    public List<Product> getProducts() {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM products";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new Product(
                        rs.getString("name"),
                        rs.getInt("quantity"),
                        rs.getDouble("price")
                ));
            }
        } catch (Exception e) {
            System.out.println("Error retrieving products: " + e.getMessage());
        }
        return list;
    }


    public void updateProductPrice(String productName, double newPrice) {
        String sql = "UPDATE products SET price = ? WHERE name = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDouble(1, newPrice);
            pstmt.setString(2, productName);
            pstmt.executeUpdate();
            System.out.println("Price updated for " + productName);

        } catch (SQLException e) {
            System.out.println("Error updating price: " + e.getMessage());
        }
    }


    public void deleteProduct(String productName) {
        String sql = "DELETE FROM products WHERE name = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, productName);
            pstmt.executeUpdate();
            System.out.println("Product deleted successfully.");

        } catch (SQLException e) {
            System.out.println("Error deleting product: " + e.getMessage());
        }
    }
}