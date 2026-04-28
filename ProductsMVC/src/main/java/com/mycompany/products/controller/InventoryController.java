package com.mycompany.products.controller;

import com.mycompany.products.model.Product;
import com.mycompany.products.repository.ProductRepository;
import com.mycompany.products.view.InventoryView;
import java.util.List;

public class InventoryController {
    private final List<Product> inventory;
    private final InventoryView view;
    private final ProductRepository repository;

    public InventoryController(InventoryView view, ProductRepository repository) {
        this.view = view;
        this.repository = repository;
        this.inventory = repository.loadFromFile();
    }

    public void start() {
        int option;
        do {
            option = view.showMenuAndGetOption();
            processOption(option);
        } while (option != 5);
    }

    private void processOption(int option) {
        switch (option) {
            case 1:
                addProduct();
                break;
            case 2:
                listInventory();
                break;
            case 3:
                updateStock();
                break;
            case 4:
                save();
                break;
            case 5:
                view.displayMessage("Exiting system...");
                break;
            default:
                view.displayMessage("Invalid option.");
                break;
        }
    }

    private void addProduct() {
        String name = view.promptForString("Enter Product Name: ");
        double price = view.promptForDouble("Enter Price: ");
        int stock = view.promptForInt("Enter Initial Stock: ");
        inventory.add(new Product(name, price, stock));
        view.displayMessage("Product added successfully.");
    }

    private void listInventory() {
        if (inventory.isEmpty()) {
            view.displayMessage("Inventory is empty.");
        } else {
            inventory.forEach(p -> view.displayMessage(p.toString()));
        }
    }

    private void updateStock() {
        int id = view.promptForInt("Enter Product ID: ");
        if (id >= 0 && id < inventory.size()) {
            int newStock = view.promptForInt("Enter New Stock Level: ");
            inventory.get(id).setStock(newStock);
            view.displayMessage("Stock updated.");
        }
    }

    private void save() {
        repository.saveToFile(inventory);
        view.displayMessage("Data persisted to disk.");
    }
}