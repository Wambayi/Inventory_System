package model;

public class Product extends Item {
    private int quantity;
    private double price;

    public Product(String name, int quantity, double price) {
        super(name);
        this.quantity = quantity;
        this.price = price;
    }

    public int getQuantity() { return quantity; }
    public double getPrice() { return price; }

    // Polymorphism (method overriding)
    @Override
    public String toString() {
        return name + " - " + quantity + " - " + price;
    }
}
