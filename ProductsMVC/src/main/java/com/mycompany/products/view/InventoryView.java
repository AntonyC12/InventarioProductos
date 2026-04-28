package com.mycompany.products.view;

import java.util.Scanner;

public class InventoryView {
    private final Scanner scanner = new Scanner(System.in);

    public int showMenuAndGetOption() {
        System.out.println("\n--- INVENTORY MANAGEMENT SYSTEM ---");
        System.out.println("1. Register Product");
        System.out.println("2. Display Inventory");
        System.out.println("3. Update Stock");
        System.out.println("4. Save Changes");
        System.out.println("5. Exit");
        System.out.print("Select an option: ");
        return scanner.nextInt();
    }

    public String promptForString(String message) {
        System.out.print(message);
        scanner.nextLine(); // Clear buffer
        return scanner.nextLine();
    }

    public double promptForDouble(String message) {
        System.out.print(message);
        return scanner.nextDouble();
    }

    public int promptForInt(String message) {
        System.out.print(message);
        return scanner.nextInt();
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }
}