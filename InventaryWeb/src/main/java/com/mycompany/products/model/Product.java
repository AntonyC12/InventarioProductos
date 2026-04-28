package com.mycompany.products.model;

public class Product {
    private String name;
    private double price;
    private int stock;

    public Product() {
    }

    public Product(String name, double price, int stock) {
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    // Business Logic: The model knows how to calculate its own total
    public double getTotalValue() {
        return this.price * this.stock;
    }

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    @Override
    public String toString() {
        return String.format("%s | $%.2f | %d units | Total: $%.2f", 
                name, price, stock, getTotalValue());
    }
}