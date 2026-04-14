package ui;

import dao.ProductDAO;
import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Product;

public class MainApp extends Application {

    ProductDAO dao = new ProductDAO();

    @Override
    public void start(Stage stage) {

        TextField nameField = new TextField();
        nameField.setPromptText("Product Name");

        TextField quantityField = new TextField();
        quantityField.setPromptText("Quantity");

        TextField priceField = new TextField();
        priceField.setPromptText("Price");

        Button addBtn = new Button("Add Product");

        TableView<Product> table = new TableView<>();

        TableColumn<Product, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getName()));

        TableColumn<Product, Number> qtyCol = new TableColumn<>("Quantity");
        qtyCol.setCellValueFactory(data ->
                new SimpleIntegerProperty(data.getValue().getQuantity()));

        TableColumn<Product, Number> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(data ->
                new SimpleDoubleProperty(data.getValue().getPrice()));

        // RED color when quantity = 0
        qtyCol.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Number item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(item.toString());

                    if (item.intValue() == 0) {
                        setStyle("-fx-text-fill: red;");
                    } else {
                        setStyle("");
                    }
                }
            }
        });

        table.getColumns().addAll(nameCol, qtyCol, priceCol);

        addBtn.setOnAction(e -> {
            try {
                Product p = new Product(
                        nameField.getText(),
                        Integer.parseInt(quantityField.getText()),
                        Double.parseDouble(priceField.getText())
                );

                dao.addProduct(p);
                table.getItems().setAll(dao.getProducts());

            } catch (Exception ex) {
                System.out.println("Invalid input");
            }
        });

        VBox layout = new VBox(10, nameField, quantityField, priceField, addBtn, table);

        Scene scene = new Scene(layout, 500, 400);
        stage.setScene(scene);
        stage.setTitle("Inventory System");
        stage.show();
    }

    public static void main(String[] args) {
        launch();


    }
}