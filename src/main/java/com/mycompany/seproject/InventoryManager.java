/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


package com.mycompany.seproject;
import java.util.Scanner;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Mega-PC
 */
public class InventoryManager { private List<Product> products;
    private List<SalesTransaction> transactions;

    public InventoryManager(List<SalesTransaction> transactions) {
        this.products = new ArrayList<>();
        this.transactions = transactions;
    }

   
    public InventoryManager() {
        this.products = new ArrayList<>();
        this.transactions = new ArrayList<>();
    }
    
    public List<Product> getProducts() {
        return products;
    }

    public void addProduct(Product product) {
        boolean productExists = products.stream().anyMatch(p -> p.getProductId().equals(product.getProductId()));

        if (!productExists) {
            products.add(product);
            System.out.println("Product added successfully");
        } else {
            System.out.println("Product with the same ID");}}
    public void updateProduct(String productId, Product updatedProduct) {
        for (Product product : products) {
            if (product.getProductId().equals(productId)) {
                product.setQuantityInStock(updatedProduct.getQuantityInStock());
                product.setPrice(updatedProduct.getPrice());
                product.setCategory(updatedProduct.getCategory());
                break;
            }
        }
    }
    public void setTransactionsList(List<SalesTransaction> transactions) {
        this.transactions = transactions;
    }

    public void deleteProduct(String productId) {
        products.removeIf(product -> product.getProductId().equals(productId));
        saveProductsToFile("C:\\Users\\Mega-PC\\Documents\\NetBeansProjects\\SEproject\\src\\main\\java\\com\\mycompany\\seproject\\products.txt");

        System.out.println("Product deleted successfully");
    }

    public void saveProductsToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(filename), StandardCharsets.UTF_8))) {

            for (Product product : products) {
               
                String productString = String.format("%s,%s,%d,%.2f,%s",
                        product.getProductId(), product.getProductName(),
                        product.getQuantityInStock(), product.getPrice(), product.getCategory());

                
                writer.write(productString);
                writer.newLine(); 
            }

            System.out.println("Products saved to file.");
        } catch (IOException e) {
            e.getMessage();
        }
    }

    //method to clear the file
    private void clearFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(filename, false), StandardCharsets.UTF_8))) {
            
            writer.write("");
        } catch (IOException e) {
            e.getMessage();
        }
    }
    public List<Product> getProductsByCategory(String category) {
        return products.stream()
                .filter(product -> product.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }

    public void sortProductsAlphabetically() {
        Collections.sort(products, Comparator.comparing(Product::getProductName));
    }

    public void sortProductsByQuantity() {
        Collections.sort(products, Comparator.comparingInt(Product::getQuantityInStock));
    }

    public void displayInventory() {
        System.out.println("Current Inventory:");
        for (Product product : products) {
            System.out.println(product);
        }
    }

     public void displayInventory_supplier() {
        System.out.println("Current Inventory:");
        for (Product product : products) {
            System.out.println(product.getProductId()+" , "+product.getProductName()+" , "+product.getCategory()+" , "+product.getQuantityInStock());
        }
    }
    
    public void displayInventory_customer() {
        System.out.println("Current Inventory:");
        for (Product product : products) {
            System.out.println(product.getProductId()+" , "+product.getProductName()+" , "+product.getPrice()+" , "+product.getCategory());
        }
    }


    public boolean checkProductAvailability(String productId, int quantity) {
        for (Product product : products) {
            if (product.getProductId().equals(productId) && product.getQuantityInStock() >= quantity) {
                return true;
            }
        }
        return false;
    }

    public void addTransaction(SalesTransaction transaction) {
        transactions.add(transaction);
    }
    public void saveDataToFile(String productsFile, String transactionsFile, List<SalesTransaction> transactions) {
        saveProductsToFile(productsFile);
        saveTransactionsToFile(transactionsFile);};
        private void saveTransactionsToFile(String transactionsFile) {
            try (PrintWriter writer = new PrintWriter(new FileWriter(transactionsFile))) {
                for (SalesTransaction transaction : transactions) {
                    // Format the transaction data as needed
                    String transactionData = transaction.toString(); // Implement the toString() method in SalesTransaction

                    // Write the formatted transaction data to the file
                    writer.println(transactionData);
                }

                System.out.println("Transactions saved to file successfully.");
            } catch (IOException e) {
                System.err.println("Error saving transactions to file: " + e.getMessage());
            }
        
    }
    // Helper method to load products from a file
    public List<Product> loadProductsFromFile(String filePath) {
        List<Product> productList = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                // Assuming the format is: productId,productName,quantity,price,category
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    String productId = parts[0].trim();
                    String productName = parts[1].trim();
                    int quantity = Integer.parseInt(parts[2].trim());
                    double price = Double.parseDouble(parts[3].trim());
                    String category = parts[4].trim();

                    Product product = new Product(productId, productName, quantity, price, category);
                    productList.add(product);
                }
            }
        } catch (FileNotFoundException e) {
            e.getMessage(); 
        }

        // Set the loaded products to the inventory manager's products list
        this.products = productList;
   
        return productList;}
        public Product getProductById(String productId) {
            for (Product product : products) {
                if (product.getProductId().equals(productId)) {
                    return product;
                }
            }
            return null; 
        }
    }
    

