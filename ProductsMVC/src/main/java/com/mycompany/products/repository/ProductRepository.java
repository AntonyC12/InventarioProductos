package com.mycompany.products.repository;

import com.mycompany.products.model.Product;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository {
    private final String fileName = "inventory.txt";

    public void saveToFile(List<Product> products) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            for (Product p : products) {
                writer.println(p.getName() + "," + p.getPrice() + "," + p.getStock());
            }
        } catch (IOException e) {
            System.err.println("Persistence Error: Could not save data.");
        }
    }

    public List<Product> loadFromFile() {
        List<Product> products = new ArrayList<>();
        File file = new File(fileName);
        
        if (!file.exists()) return products;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                products.add(new Product(data[0], Double.parseDouble(data[1]), Integer.parseInt(data[2])));
            }
        } catch (Exception e) {
            System.err.println("Persistence Error: Data corrupted.");
        }
        return products;
    }
}