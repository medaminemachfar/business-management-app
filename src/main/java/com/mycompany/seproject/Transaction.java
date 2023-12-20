/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.seproject;

/**
 *
 * @author Mega-PC
 */
import java.time.LocalDate;

public abstract class Transaction {
    private Product product;
    private int quantity;
    private LocalDate date;

    // Constructors, getters, and setters

    // Default constructor
    public Transaction() {
    }

    // Parameterized constructor
    public Transaction(Product product, int quantity, LocalDate date) {
        this.product = product;
        this.quantity = quantity;
        this.date = date;
    }

    // Getters and setters
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    // Additional methods as needed
    public void display() {
        System.out.println("Transaction Details:");
        System.out.println("Product: " + product.getProductName());
        System.out.println("Quantity: " + quantity);
        System.out.println("Date: " + date);
    }

    public abstract double calculateTotal();
    
	}


