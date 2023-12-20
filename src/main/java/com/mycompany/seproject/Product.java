/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.seproject;
import java.util.Scanner;

/**
 *
 * @author Mega-PC
 */
public class Product implements Displayable { 
    private String productId;
    private String productName;
    private int quantityInStock;
    private double price;
    private String category;

    // Constructors
    public Product(String productId, String productName, int quantityInStock, double price, String category) {
        this.productId = productId;
        this.productName = productName;
        this.quantityInStock = quantityInStock;
        this.price = price;
        this.category = category;
    }

    // Getters and setters
    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public int getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(int quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    // Additional setters for individual fields
    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public static boolean isValidPrice(double price) {
        return price >= 0;
    }

    public static boolean isValidQuantity(int quantity) {
        return quantity >= 0;
    }

    public boolean isLowStock(int threshold) {
        return quantityInStock < threshold;
    }

    public void incrementQuantity(int quantityToAdd) {
        this.quantityInStock += quantityToAdd;
    }

    public void decrementQuantity(int quantityToSubtract) {
        if (quantityToSubtract > this.quantityInStock) {
            throw new IllegalArgumentException("Error: Insufficient quantity in stock.");
        }
        this.quantityInStock -= quantityToSubtract;
    }

    public void updateQuantity(int newQuantity) {
        if (isValidQuantity(newQuantity)) {
            this.quantityInStock = newQuantity;
        } else {
            throw new IllegalArgumentException("Error: Invalid quantity value.");
        }
    }
    public void addProductFromUserInput(Scanner scanner) {
        System.out.println("Enter product details:");

        System.out.print("Product ID: ");
        String productId = scanner.nextLine();

        System.out.print("Product Name: ");
        String productName = scanner.nextLine();

        System.out.print("Quantity in Stock: ");
        int quantityInStock = scanner.nextInt();
        scanner.nextLine(); 

        System.out.print("Price: ");
        double price = scanner.nextDouble();
        scanner.nextLine(); 

        System.out.print("Category: ");
        String category = scanner.nextLine();

        // Create a new Product with the entered details
        Product newProduct = new Product(productId, productName, quantityInStock, price, category);

        InventoryManager inventoryManager = new InventoryManager();
		// Add the new product to the inventory
        inventoryManager.addProduct(newProduct);
    }

    @Override
    public void display() {
        System.out.println("Product ID: " + getProductId() +
                ", Name: " + getProductName() +
                ", Quantity in Stock: " + getQuantityInStock() +
                ", Price: $" + getPrice());
    }

    // toString method for representation
    @Override
    public String toString() {
        return "Product{" +
                "productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", quantityInStock=" + quantityInStock +
                ", price=" + price +
                ", category='" + category + '\'' +
                '}';
    }
    

}