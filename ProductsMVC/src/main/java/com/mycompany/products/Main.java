package com.mycompany.products;

import com.mycompany.products.controller.InventoryController;
import com.mycompany.products.repository.ProductRepository;
import com.mycompany.products.view.InventoryView;

/**
 * Main class responsible for bootstrapping the application.
 * It follows the Dependency Injection (DI) pattern.
 */
public class Main {
    public static void main(String[] args) {
        // 1. Initialize dependencies (Low-level modules)
        InventoryView view = new InventoryView();
        ProductRepository repository = new ProductRepository();

        // 2. Inject dependencies into the Controller via Constructor
        // The Controller doesn't create these; it receives them.
        InventoryController controller = new InventoryController(view, repository);

        // 3. Start the application logic
        controller.start();
    }
}