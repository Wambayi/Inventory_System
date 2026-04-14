package dao;

import database.DBConnection;
import model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    public void addProduct(Product product) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO products(name, quantity, price) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, product.getName());
            stmt.setInt(2, product.getQuantity());
            stmt.setDouble(3, product.getPrice());

            stmt.executeUpdate();

        } catch (Exception e) {
            System.out.println("Error inserting product");
        }
    }

    public List<Product> getProducts() {
        List<Product> list = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection()) {
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM products");

            while (rs.next()) {
                list.add(new Product(
                        rs.getString("name"),
                        rs.getInt("quantity"),
                        rs.getDouble("price")
                ));
            }

        } catch (Exception e) {
            System.out.println("Error retrieving products");
        }

        return list;
    }
}
