package ui;

import dao.ProductDAO;
import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Product;
import javafx.geometry.Pos;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableCell;


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
        qtyCol.setCellFactory(col -> new TableCell<Product,Number>(){
            @Override
            protected void updateItem(Number item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item ==  null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item.toString());

                    if (item.intValue() == 0) {
                        setStyle("-fx-text-fill: red;");
                        setTextFill(javafx.scene.paint.Color.RED);
                    } else {
                        setStyle("");
                        setTextFill(javafx.scene.paint.Color.BLACK);
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



            } catch (Exception ex) {
                System.out.println("Invalid input");
            }});
            // Create the extra buttons
            Button deleteBtn = new Button("Delete Product");
            Button updateBtn = new Button("Update Price");

            // Apply the AURA'D Pink/Black Styling to all buttons
            String pinkStyle = "-fx-background-color: #FFC0CB; -fx-text-fill: black; -fx-font-weight: bold;";
            addBtn.setStyle(pinkStyle);
            deleteBtn.setStyle(pinkStyle);
            updateBtn.setStyle(pinkStyle);
            //dao.addProduct();
            table.getItems().setAll(dao.getProducts());

        };


    public class InventoryApp extends Application {

        @Override
        public void start(Stage stage) {
            // Assuming these were initialized elsewhere in your code
            TextField nameField = new TextField("Item Name");
            TextField quantityField = new TextField("Quantity");
            TextField priceField = new TextField("Price");
            Button addBtn = new Button("Add Item");
            Button updateBtn = new Button("Update Item");
            Button deleteBtn = new Button("Delete Item");
            TableView<Object> table = new TableView<>(); // Replace Object with your Data Model

            VBox layout = new VBox(15, nameField, quantityField, priceField, addBtn, updateBtn, deleteBtn, table);
        layout.setAlignment(Pos.CENTER);
        // Apply the Baby Pink and Black Aesthetic
        layout.setStyle("-fx-background-color: #000000; -fx-padding: 30;");

    String pinkStyle = "-fx-background-color: #FFC0CB; -fx-text-fill: black; -fx-font-weight: bold;";
    addBtn.setStyle(pinkStyle);
    updateBtn.setStyle(pinkStyle);
    deleteBtn.setStyle(pinkStyle);

    //Set the Stage
    Scene scene = new Scene(layout, 600, 700);
    stage.setScene(scene);
    stage.setTitle("AURA'D Inventory System");
    stage.show();
        stage.setScene(scene);
        stage.setTitle("Inventory System");
        stage.show();
    }

    public static void main(String[] args) {
        launch();


    }
};;;}