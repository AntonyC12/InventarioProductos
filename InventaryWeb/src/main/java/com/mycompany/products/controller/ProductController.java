package com.mycompany.products.controller;

import com.mycompany.products.model.Product;
import com.mycompany.products.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ProductController {

    private final ProductRepository repository;

    @Autowired
    public ProductController(ProductRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/")
    public String index(Model model) {
        List<Product> inventory = repository.loadFromFile();
        model.addAttribute("products", inventory);
        model.addAttribute("newProduct", new Product());
        return "inventory";
    }

    @PostMapping("/add")
    public String addProduct(@ModelAttribute Product product) {
        List<Product> inventory = repository.loadFromFile();
        inventory.add(product);
        repository.saveToFile(inventory);
        return "redirect:/";
    }

    @PostMapping("/update-stock")
    public String updateStock(@RequestParam("index") int index, @RequestParam("stock") int stock) {
        List<Product> inventory = repository.loadFromFile();
        if (index >= 0 && index < inventory.size()) {
            inventory.get(index).setStock(stock);
            repository.saveToFile(inventory);
        }
        return "redirect:/";
    }
}
