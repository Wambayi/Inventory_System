package ui;

import dao.ProductDAO;
import model.Product;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Pos;
import javafx.scene.Scene;

import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.beans.property.*;

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
    private ProductDAO dao = new ProductDAO();
    private TableView<Product> table = new TableView<>();
    private ObservableList<Product> masterData = FXCollections.observableArrayList();
    private Label totalValueLabel = new Label("Total Value: $0.00");

    private TextField nameIn = new TextField();
    private TextField qtyIn = new TextField();
    private TextField priceIn = new TextField();
    private TextField searchBar = new TextField();

    @Override
    public void start(Stage stage) {
        searchBar.setPromptText("Search AURA'D Collection...");
        nameIn.setPromptText("Product Name");
        qtyIn.setPromptText("Quantity");
        priceIn.setPromptText("Price");

        String inputStyle = "-fx-background-color: #222; -fx-text-fill: white; -fx-prompt-text-fill: #888; -fx-border-color: #444;";
        searchBar.setStyle("-fx-background-color: #1a1a1a; -fx-text-fill: #FFC0CB; -fx-border-color: #FFC0CB;");
        nameIn.setStyle(inputStyle); qtyIn.setStyle(inputStyle); priceIn.setStyle(inputStyle);
        totalValueLabel.setStyle("-fx-text-fill: #FFC0CB; -fx-font-size: 16px; -fx-font-weight: bold;");

        Button addBtn = new Button("ADD");
        Button updateBtn = new Button("UPDATE");
        Button deleteBtn = new Button("DELETE");

        String pinkBtn = "-fx-background-color: #FFC0CB; -fx-text-fill: black; -fx-font-weight: bold; -fx-min-width: 90px;";
        addBtn.setStyle(pinkBtn); updateBtn.setStyle(pinkBtn);
        deleteBtn.setStyle("-fx-background-color: #ff4d4d; -fx-text-fill: white; -fx-font-weight: bold; -fx-min-width: 90px;");

        TableColumn<Product, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getName()));
        TableColumn<Product, Number> qtyCol = new TableColumn<>("Stock");
        qtyCol.setCellValueFactory(d -> new SimpleIntegerProperty(d.getValue().getQuantity()));
        TableColumn<Product, Number> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(d -> new SimpleDoubleProperty(d.getValue().getPrice()));

        table.getColumns().addAll(nameCol, qtyCol, priceCol);
        refreshData();

        FilteredList<Product> filteredData = new FilteredList<>(masterData, p -> true);
        searchBar.textProperty().addListener((obs, oldVal, newVal) -> {
            filteredData.setPredicate(product -> {
                if (newVal == null || newVal.isEmpty()) return true;
                return product.getName().toLowerCase().contains(newVal.toLowerCase());
            });
        });
        table.setItems(filteredData);


        table.getSelectionModel().selectedItemProperty().addListener((obs, old, newVal) -> {
            if (newVal != null) {
                nameIn.setText(newVal.getName());
                qtyIn.setText(String.valueOf(newVal.getQuantity()));
                priceIn.setText(String.valueOf(newVal.getPrice()));

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

        addBtn.setOnAction(e -> {
            try {
                dao.addProduct(new Product(nameIn.getText(), Integer.parseInt(qtyIn.getText()), Double.parseDouble(priceIn.getText())));
                refreshData();
                clearFields();
            } catch (Exception ex) { showAlert("Error", "Check Database/Input"); }
        });

        updateBtn.setOnAction(e -> {
            Product s = table.getSelectionModel().getSelectedItem();
            if (s != null) {
                try {
                    dao.updateProduct(new Product(s.getName(), Integer.parseInt(qtyIn.getText()), Double.parseDouble(priceIn.getText())));
                    refreshData();
                } catch (Exception ex) { showAlert("Error", "Update Failed"); }
            }
        });

        deleteBtn.setOnAction(e -> {
            Product s = table.getSelectionModel().getSelectedItem();
            if (s != null) {
                try {
                    dao.deleteProduct(s.getName());
                    refreshData();
                    clearFields();
                } catch (Exception ex) { showAlert("Error", "Delete Failed"); }
            }
        });

        HBox btnBox = new HBox(10, addBtn, updateBtn, deleteBtn);
        btnBox.setAlignment(Pos.CENTER);

        VBox root = new VBox(15, searchBar, nameIn, qtyIn, priceIn, btnBox, table, totalValueLabel);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #000000; -fx-padding: 30;");

        stage.setScene(new Scene(root, 600, 700));
        stage.setTitle("AURA'D | MANAGEMENT");

                Product p = new Product(
                        nameField.getText(),
                        Integer.parseInt(quantityField.getText()),
                        Double.parseDouble(priceField.getText())
                );
                dao.addProduct(p);



            } catch (Exception ex) {
                System.out.println("Invalid input");
            }});
      
            Button deleteBtn = new Button("Delete Product");
            Button updateBtn = new Button("Update Price");

            
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
          
            TextField nameField = new TextField("Item Name");
            TextField quantityField = new TextField("Quantity");
            TextField priceField = new TextField("Price");
            Button addBtn = new Button("Add Item");
            Button updateBtn = new Button("Update Item");
            Button deleteBtn = new Button("Delete Item");
            TableView<Object> table = new TableView<>(); 

            VBox layout = new VBox(15, nameField, quantityField, priceField, addBtn, updateBtn, deleteBtn, table);
        layout.setAlignment(Pos.CENTER);
      
        layout.setStyle("-fx-background-color: #000000; -fx-padding: 30;");

    String pinkStyle = "-fx-background-color: #FFC0CB; -fx-text-fill: black; -fx-font-weight: bold;";
    addBtn.setStyle(pinkStyle);
    updateBtn.setStyle(pinkStyle);
    deleteBtn.setStyle(pinkStyle);

   
    Scene scene = new Scene(layout, 600, 700);
    stage.setScene(scene);
    stage.setTitle("AURA'D Inventory System");
    stage.show();
        stage.setScene(scene);
        stage.setTitle("Inventory System"
        stage.show();
    }

    private void refreshData() {
        masterData.setAll(dao.getProducts());
        totalValueLabel.setText("Total Value: $" + String.format("%.2f", dao.getTotalValue()));
    }

    private void clearFields() { nameIn.clear(); qtyIn.clear(); priceIn.clear(); }

    private void showAlert(String t, String c) {
        Alert a = new Alert(Alert.AlertType.INFORMATION); a.setTitle(t); a.setHeaderText(null); a.setContentText(c); a.showAndWait();
    }


    public static void main(String[] args) { launch(args); }
}

};;;}
