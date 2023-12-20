/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.seproject;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/**
 *
 * @author Mega-PC
 */
public class SalesTransaction extends Transaction {
    private Product product;
    private int quantity;
    private LocalDate date;
    private String transactionType; // "Sale" or "Purchase"

    // Constructor
    public SalesTransaction(Product product, int quantity, LocalDate currentDate, String transactionType) {
        this.product = product;
        this.quantity = quantity;
        this.date = currentDate;
        this.transactionType = transactionType;
    }

    // Getters and setters
    @Override
    public Product getProduct() {
        return product;
    }

    @Override
    public int getQuantity() {
        return quantity;
    }

    @Override
    public LocalDate getDate() {
        return date;
    }

    public String getTransactionType() {
        return transactionType;
    }

    // Convert SalesTransaction to a string representation
    public String toString_sales() {
        double total = quantity * product.getPrice();
        return "SalesTransaction{" +
                "product=" + product +
                ", quantity=" + quantity +
                ", date=" + date +
                ", transactionType='" + transactionType + '\'' +
                ", total=" + total +
                '}';
    }

    @Override
    public double calculateTotal() {
        return quantity * product.getPrice();
    }


 public static List<SalesTransaction> loadTransactionsFromFile(String filePath, InventoryManager inventoryManager) {
	    List<SalesTransaction> transactions = new ArrayList<>();

	    try (Scanner scanner = new Scanner(new File(filePath))) {
	        while (scanner.hasNextLine()) {
	            String line = scanner.nextLine();
	            String[] parts = line.split(",");

	            if (parts.length == 4) {
	                String productId = parts[0].trim();
	                int quantity = Integer.parseInt(parts[1].trim());
	                LocalDate date = LocalDate.parse(parts[2].trim());
	                String transactionType = parts[3].trim();
	                Product product = inventoryManager.getProductById(productId);

	                if (product != null) {
	                    SalesTransaction transaction = new SalesTransaction(product, quantity, date, transactionType);
	                    transactions.add(transaction);
	                }
	            }
	        }
	    } catch (FileNotFoundException e) {
	        System.err.println("Error loading transactions from file: " + e.getMessage());
	    }

	    return transactions;
	}


 public String toFileString() {
     return product.getProductId() + "," + quantity + "," + date + "," + transactionType;
 }

 public void recordToFile() {
     try (PrintWriter writer = new PrintWriter(new FileWriter("C:\\Users\\Mega-PC\\Documents\\NetBeansProjects\\SEproject\\src\\main\\java\\com\\mycompany\\seproject\\transactions.txt", true))) {
         writer.println(toFileString());
         System.out.println("Transaction recorded to file successfully.");
     } catch (IOException e) {
         System.err.println("Error recording transaction to file: " + e.getMessage());
     }
 }}
