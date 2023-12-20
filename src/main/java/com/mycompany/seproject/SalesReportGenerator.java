/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.seproject;
import java.awt.Color;
import java.awt.Dimension;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class SalesReportGenerator {
    private List<SalesTransaction> transactionList;
    private InventoryManager inventoryManager;
    private List<String> distinctCategories; 

    public SalesReportGenerator(List<SalesTransaction> transactionList, InventoryManager inventoryManager) {
        this.transactionList = transactionList;
        this.inventoryManager = inventoryManager;
        this.distinctCategories = calculateDistinctCategories(); 
    }
    public void recordTransaction(SalesTransaction transaction) {
        transactionList.add(transaction);
    }

    public void generateSalesReport(String outputPath, LocalDate startDate, LocalDate endDate) {
        try (PrintWriter printWriter = new PrintWriter(new File(outputPath))) {
            printWriter.println("----- Sales Report -----");
            for (SalesTransaction transaction : transactionList) {
                printWriter.println(transaction.toString_sales());
            }

            calculateTotalSales(printWriter);
            calculateRevenueByProductCategory(printWriter);

            generateBasicReports(outputPath);
            double averageSales = calculateAverageSales();
            printWriter.println("Average Sales: $" + averageSales);
            identifyPeakSalesPeriods(printWriter);
            determineMostPopularProducts(printWriter);

            calculateTotalSalesLastYear(printWriter);

  
            
        } catch (FileNotFoundException e) {
            e.getMessage();
        }
    }

    private List<String> calculateDistinctCategories() {
        return inventoryManager.getProducts().stream()
                .map(Product::getCategory)
                .distinct()
                .collect(Collectors.toList());
    }
  
    public void generateAndDisplayPieChart() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        int totalSoldQuantity = transactionList.stream()
                .filter(transaction -> "Sale".equalsIgnoreCase(transaction.getTransactionType()))
                .mapToInt(SalesTransaction::getQuantity)
                .sum();
        Map<String, Double> categoryPercentages = new HashMap<>();
        for (String category : distinctCategories) {
            double categorySales = transactionList.stream()
                    .filter(transaction -> "Sale".equalsIgnoreCase(transaction.getTransactionType())
                            && transaction.getProduct().getCategory().equalsIgnoreCase(category))
                    .mapToInt(SalesTransaction::getQuantity)
                    .sum();
            double percentage = (categorySales / totalSoldQuantity) * 100;
            categoryPercentages.put(category, percentage);
        }
        for (Map.Entry<String, Double> entry : categoryPercentages.entrySet()) {
            dataset.setValue(entry.getKey(), entry.getValue());
        }
        JFreeChart chart = ChartFactory.createPieChart(
                "Sales Distribution by Category",
                dataset,
                true,
                true,
                false
        );

        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setSectionPaint("Category", new Color(160, 160, 255)); 
        PieChartFrame frame = new PieChartFrame("Sales Distribution by Category", chart);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void calculateTotalSales(PrintWriter printWriter) {
        int totalSales = 0;
        for (SalesTransaction transaction : transactionList) {
            if ("sale".equals(transaction.getTransactionType())) {
                totalSales += transaction.getQuantity() * transaction.getProduct().getPrice();
            }
        }
        printWriter.println("Total Sales: $" + totalSales);
    }
    public void generateBasicReports(String outputPath) {
        try (PrintWriter printWriter = new PrintWriter(new File(outputPath))) {
            generateInventoryStatusReport(printWriter);
            generateSalesHistoryReport(printWriter);
            generatePopularProducts(outputPath, transactionList);
           

        } catch (FileNotFoundException e) {
            e.getMessage();
        }
    } 
    private void generateInventoryStatusReport(PrintWriter printWriter) {
        printWriter.println("----- Inventory Status Report -----");
        for (Product product : inventoryManager.getProducts()) {
            printWriter.println("Product ID: " + product.getProductId() +
                    ", Name: " + product.getProductName() +
                    ", Quantity in Stock: " + product.getQuantityInStock() +
                    ", Price: $" + product.getPrice());
        }
    }
    private void generateSalesHistoryReport(PrintWriter printWriter) {
        printWriter.println("----- Sales History Report -----");
        for (SalesTransaction transaction : transactionList) {
            printWriter.println(transaction.toString_sales());
        }
    }
    
    public double calculateAverageSales() {
        return transactionList.stream()
                .filter(transaction -> "Sale".equalsIgnoreCase(transaction.getTransactionType()))
                .mapToDouble(transaction -> (transaction.getProduct().getPrice())* transaction.getQuantity())
                .average()
                .orElse(0.0);
    }
    public static class PieChartFrame extends JFrame {
        public PieChartFrame(String title, JFreeChart chart) {
            super(title);
            ChartPanel chartPanel = new ChartPanel(chart);
            chartPanel.setPreferredSize(new Dimension(560, 370));
            setContentPane(chartPanel);
            setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        }
    }
    public void identifyPeakSalesPeriods(PrintWriter printWriter) {
        printWriter.println("----- Peak Sales Periods -----");
        transactionList.stream()
                .filter(transaction -> "Sale".equalsIgnoreCase(transaction.getTransactionType()))
                .collect(Collectors.groupingBy(
                        SalesTransaction::getDate,
                        Collectors.summingDouble(transaction -> transaction.getQuantity() * transaction.getProduct().getPrice())
                ))
                .entrySet()
                .stream()
                .max(Comparator.comparingDouble(entry -> entry.getValue()))
                .ifPresent(entry -> printWriter.println("Peak Sales Date: " + entry.getKey() +
                        ", Total Sales: $" + entry.getValue()));
    }

    public void determineMostPopularProducts(PrintWriter printWriter) {
        printWriter.println("----- Most Popular Products -----");
        Map<String, Integer> productSalesCount = new HashMap<>();

        for (SalesTransaction transaction : transactionList) {
            if ("sale".equalsIgnoreCase(transaction.getTransactionType())) {
                String productId = transaction.getProduct().getProductId();
                productSalesCount.put(productId, productSalesCount.getOrDefault(productId, 0) + transaction.getQuantity());
            }
        }

        productSalesCount.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(5)
                .forEach(entry -> printWriter.println("Product ID: " + entry.getKey() + " - Sales Count: " + entry.getValue()));
    }

    
    private void calculateRevenueByProductCategory(PrintWriter printWriter) {
        List<String> distinctCategories = inventoryManager.getProducts().stream()
                .map(Product::getCategory)
                .distinct()
                .collect(Collectors.toList());

        for (String category : distinctCategories) {
            double categoryRevenue = transactionList.stream()
                    .filter(transaction -> transaction.getProduct().getCategory().equalsIgnoreCase(category))
                    .filter(transaction -> "Sale".equalsIgnoreCase(transaction.getTransactionType()))
                    .mapToDouble(transaction -> transaction.getQuantity() * transaction.getProduct().getPrice())
                    .sum();

            printWriter.println("Category: " + category + ", Revenue: $" + categoryRevenue +"    Net profit: $  " + categoryRevenue*0.3);
        }
    }
    
    public static void generatePopularProducts(String outputPath, List<SalesTransaction> transactionList) {
        try (PrintWriter printWriter = new PrintWriter(new File(outputPath))) {
            printWriter.println("----- Popular Products -----");
            Map<String, Integer> productSalesCount = new HashMap<>();

            for (SalesTransaction transaction : transactionList) {
                String productId = transaction.getProduct().getProductId();
                productSalesCount.put(productId, productSalesCount.getOrDefault(productId, 0) + transaction.getQuantity());
            }

            // Find the most popular products
            int maxSalesCount = 0;
            for (Map.Entry<String, Integer> entry : productSalesCount.entrySet()) {
                if (entry.getValue() > maxSalesCount) {
                    maxSalesCount = entry.getValue();
                }
            }

            for (Map.Entry<String, Integer> entry : productSalesCount.entrySet()) {
                if (entry.getValue() == maxSalesCount) {
                    printWriter.println("Product ID: " + entry.getKey() + " - Sales Count: " + entry.getValue());
                }
            }

        } catch (FileNotFoundException e) {
            e.getMessage();
        }
    }
    
    
    private void calculateTotalSalesLastYear(PrintWriter printWriter) {
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusYears(1);

        int totalSales = 0;
        for (SalesTransaction transaction : transactionList) {
            if ("sale".equals(transaction.getTransactionType()) &&
                    (transaction.getDate().isEqual(startDate) || transaction.getDate().isAfter(startDate)) &&
                    (transaction.getDate().isEqual(endDate) || transaction.getDate().isBefore(endDate) || transaction.getDate().isEqual(endDate))) {
                totalSales += transaction.getQuantity() * transaction.getProduct().getPrice();
            }
        }
        printWriter.println("Total Sales for the Last Year (" + startDate + " to " + endDate + "): $" + totalSales);
    }
   



}