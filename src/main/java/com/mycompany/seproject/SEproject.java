/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.seproject;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Mega-PC
 */
public class SEproject {
    private static List<SalesTransaction> transactions;  
    public static void main(String[] args) {
        InventoryManager inventoryManager = new InventoryManager();
        inventoryManager.loadProductsFromFile("C:\\Users\\Mega-PC\\Documents\\NetBeansProjects\\SEproject\\src\\main\\java\\com\\mycompany\\seproject\\products.txt");
        transactions = SalesTransaction.loadTransactionsFromFile("C:\\Users\\Mega-PC\\Documents\\NetBeansProjects\\SEproject\\src\\main\\java\\com\\mycompany\\seproject\\transactions.txt", inventoryManager);
        inventoryManager.setTransactionsList(transactions);
        String usersFilePath = "C:\\Users\\Mega-PC\\Documents\\NetBeansProjects\\SEproject\\src\\main\\java\\com\\mycompany\\seproject\\users.txt";
        List<User> users = User.loadUsersFromFile(usersFilePath);
        Scanner scanner = new Scanner(System.in);
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        System.out.println("%                Inventory Management System                 %");
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        User authenticatedUser = authenticate(users, scanner);
        if (authenticatedUser != null) {
            System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
            System.out.println("%                 Login successful. Welcome, " + padRight(authenticatedUser.getUsername(), 15) + "!               %");
            System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        } else {
            System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
            System.out.println("%            Login failed. Do you want to create a new account? (yes/no)            %");
            System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
            String createAccountOption = scanner.next();
            if ("yes".equalsIgnoreCase(createAccountOption)) {
                createUser(users, scanner);
                System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
                System.out.println("%                  Account created successfully. Exiting program.                  %");
                System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
                System.exit(0);
            } else {
                System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
                System.out.println("%                             Exiting program.                              %");
                System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
                System.exit(0);
            }
        }
        boolean continueLoop = true;
        while (continueLoop) {
            handleUserOptions(authenticatedUser, inventoryManager, users, scanner);

            // Ask if the user wants to continue
            System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
            System.out.print("%        Do you want to perform another operation? (yes/no):        %");
            String continueOption = scanner.next().toLowerCase();

            if ("no".equals(continueOption)) {
                continueLoop = false;
                System.out.println("%                          Exiting program.                           %");
            }
        }
        scanner.close();
    }
    private static String padRight(String s, int n) {
        return String.format("%-" + n + "s", s);
    }

    private static void handleUserOptions(User authenticatedUser, InventoryManager inventoryManager,
            List<User> users, Scanner scanner) {
    	if ("admin".equals(authenticatedUser.getRole())) {
    	    System.out.println("1. Update user");
    	    System.out.println("2. Delete user");
    	    System.out.println("3. List all users"); 
    	    System.out.println("4. Go to non admin options");
  int adminOption =scanner.nextInt();
		switch (adminOption) {
			case 1:
                System.out.println("Provide me with the id  ");
                String userid_update = scanner.next();
                User userupdate = User.getUserById(userid_update);
                if (userupdate != null) {
                    System.out.println("Provide me with the new role  ");
                    String userrole_update = scanner.next();
                    userupdate.update_user_role(userid_update, userrole_update, users, "C:\\Users\\Mega-PC\\Documents\\NetBeansProjects\\SEproject\\src\\main\\java\\com\\mycompany\\seproject\\users.txt");
                    System.out.println("User role updated successfully.");
                } else {
                    System.out.println("User not found.");
                }break;
			case 2:
			    System.out.println("Provide me with the id to delete: ");
			    String userIdToDelete = scanner.next();
			    User deleteUser = User.getUserById(userIdToDelete);
			    if (deleteUser != null) {
			        User.deleteUser(userIdToDelete, users, "C:\\Users\\Mega-PC\\Documents\\NetBeansProjects\\SEproject\\src\\main\\java\\com\\mycompany\\seproject\\users.txt");
			        System.out.println("User deleted successfully.");
			    } else {
			        System.out.println("User not found.");
			    }
			    break;
			 case 3:
	                System.out.println("List of all users:");
	                for (User user : users) {
	                    System.out.println("User ID: " + user.getUserId() +
	                            ", Username: " + user.getUsername() +
	                            ", Role: " + user.getRole());
	                }
	                break;

	            default:
	                System.out.println("Invalid option.");
	                break;
	            case 4:
	            	System.out.println("Proceeding to non admin options");
	            	
	        }

            }
        

       

        if (!"customer".equals(authenticatedUser.getRole())&& !"supplier".equals(authenticatedUser.getRole())) {
           
            System.out.println("Pick the number of the function that you want to use ");
            System.out.println("1. Inventory display");
            System.out.println("2. Update product ");
            System.out.println("3. Delete product");
            System.out.println("4. Add product");
            System.out.println("5. Add Purchase Transaction");
            System.out.println("6.Generate Report ");
            System.out.println("7.Generate transactions in Excel file ");
            System.out.println("8.Display Charts ");
            System.out.println("9.Proceed to non manager options");
            System.out.print("Type the number of  command that you want: ");
            int choice = scanner.nextInt();

            switch (choice) {
            case 1:
                inventoryManager.displayInventory();
                break;
            case 2:
                System.out.println("Provide me with the updated product ID: ");
                String productId_update = scanner.next();
                System.out.println("Provide me with the updated quantity: ");
                int Quantity_update = scanner.nextInt();
                System.out.println("Provide me with the updated price: ");
                double Price_update = scanner.nextDouble();
                System.out.println("Provide me with the updated category: ");
                String Category_update = scanner.next();
                Product updatedProduct = new Product(productId_update, null, Quantity_update, Price_update, Category_update);
                inventoryManager.updateProduct(productId_update, updatedProduct);
                inventoryManager.saveProductsToFile("C:\\Users\\Mega-PC\\Documents\\NetBeansProjects\\SEproject\\src\\main\\java\\com\\mycompany\\seproject\\products.txt");
                System.out.println("Product updated successfully");
                break;
            case 3:
                System.out.println("Provide me with the product ID to remove: ");
                String productId_delete = scanner.next();
                inventoryManager.deleteProduct(productId_delete);
                inventoryManager.saveProductsToFile("C:\\Users\\Mega-PC\\Documents\\NetBeansProjects\\SEproject\\src\\main\\java\\com\\mycompany\\seproject\\products.txt");
                System.out.println("Product deleted successfully");
                break;
            case 4:
                System.out.println("Provide me with the product ID: ");
                String productId_Add = scanner.next();
                System.out.println("Provide me with the product name: ");
                String productname_Add = scanner.next();
                System.out.println("Provide me with the quantity: ");
                int Quantity_Add = scanner.nextInt();
                System.out.println("Provide me with the price: ");
                double Price_Add = scanner.nextDouble();
                System.out.println("Provide me with the category: ");
                String Category_Add = scanner.next();
                Product Product_add = new Product(productId_Add, productname_Add, Quantity_Add, Price_Add, Category_Add);
                inventoryManager.addProduct(Product_add);
                inventoryManager.saveProductsToFile("C:\\Users\\Mega-PC\\Documents\\NetBeansProjects\\SEproject\\src\\main\\java\\com\\mycompany\\seproject\\products.txt");
                System.out.println("Product added successfully");
                break;
            case 5:
            	System.out.println("Provide me with the product id ");
            	String productId_purchase = scanner.next();
            	Product product_purchase = inventoryManager.getProductById(productId_purchase);
            	System.out.println("Provide me with the quantity: ");
            	int Quantity_purchase = scanner.nextInt();
            	LocalDate currentDate1 = LocalDate.now();
            	SalesTransaction purchase_add = new SalesTransaction(product_purchase, Quantity_purchase, currentDate1, "purchase");
            	product_purchase.incrementQuantity(Quantity_purchase);
            	inventoryManager.addTransaction(purchase_add);
            	inventoryManager.saveProductsToFile("C:\\Users\\Mega-PC\\Documents\\NetBeansProjects\\SEproject\\src\\main\\java\\com\\mycompany\\seproject\\products.txt");  // Save the updated product quantity to file
            	purchase_add.recordToFile();
            	System.out.println("Purchase Transaction added successfully");
            	System.out.println(product_purchase.toString());

                break;
                   case 6:
                    System.out.println("Generating sales report...");
                    SalesReportGenerator salesReportGenerator = new SalesReportGenerator(transactions, inventoryManager);
                    LocalDate startDate = LocalDate.of(2023, 1, 1);
                    LocalDate endDate = LocalDate.now();
                    salesReportGenerator.generateSalesReport("C:\\Users\\Mega-PC\\Documents\\NetBeansProjects\\SEproject\\src\\main\\java\\com\\mycompany\\seproject\\report.txt", startDate, endDate);
                PdfReportGenerator.generatePdfReport("C:\\Users\\Mega-PC\\Documents\\NetBeansProjects\\SEproject\\src\\main\\java\\com\\mycompany\\seproject\\report.txt", "C:\\Users\\Mega-PC\\Documents\\NetBeansProjects\\SEproject\\src\\main\\java\\com\\mycompany\\seproject\\reportPDF.pdf");
                
                break;
                case 7:
                    System.out.println("Generating transaction excel...");
                    ExcelReportGenerator.generateExcelReport("C:\\Users\\Mega-PC\\Documents\\NetBeansProjects\\SEproject\\src\\main\\java\\com\\mycompany\\seproject\\transactions.txt","C:\\Users\\Mega-PC\\Documents\\NetBeansProjects\\SEproject\\src\\main\\java\\com\\mycompany\\seproject\\Exeltransactions.xlsx");  
                    break;
             case 8:
                    System.out.println("Displaying charts...");
                    SalesReportGenerator salesReportGenerators = new SalesReportGenerator(transactions, inventoryManager);
                   salesReportGenerators.generateAndDisplayPieChart();
                    break;
                    
                case 9:
	            	System.out.println("Proceeding to non manager options");
                        if ("customer".equalsIgnoreCase(authenticatedUser.getRole()) ||
            	    "admin".equalsIgnoreCase(authenticatedUser.getRole()) ||
            	    "manager".equalsIgnoreCase(authenticatedUser.getRole())) { 
                    System.out.println("Pick the number of the function that you want to use ");
                    System.out.println("1. Add Sale Transaction");
                    System.out.println("2. check the products");
                    int choice_customer = scanner.nextInt();
                   
                     switch (choice_customer) {
                     case 1: System.out.println("Provide me with the product id ");
                     String productId_sale = scanner.next();
                     Product product_sale = inventoryManager.getProductById(productId_sale);
                     System.out.println("Provide me with the quantity: ");
                int Quantity_sale = scanner.nextInt();
                LocalDate currentDate = LocalDate.now(); 
                
                if (inventoryManager.checkProductAvailability(productId_sale, Quantity_sale)) {
                    SalesTransaction sale_add = new SalesTransaction(product_sale, Quantity_sale, currentDate, "sale");
                    product_sale.decrementQuantity(Quantity_sale);
                    inventoryManager.addTransaction(sale_add);
                    inventoryManager.saveProductsToFile("C:\\Users\\Mega-PC\\Documents\\NetBeansProjects\\SEproject\\src\\main\\java\\com\\mycompany\\seproject\\products.txt");  // Save the updated product quantity to file
                    sale_add.recordToFile();

                    System.out.println("Sales Transaction added successfully");
                } else {
                    System.out.println("Product not available in sufficient quantity.");
                }
                break;
                case 2: inventoryManager.displayInventory_customer();break;
                }
            }
                default:
                    System.out.println("Invalid choice");
                    break;
                   }
                    
        }if ("supplier".equalsIgnoreCase(authenticatedUser.getRole())){
        	 System.out.println("Pick the number of the function that you want to use ");
             System.out.println("1. Refill inventory");
             System.out.println("2. check the inventory status");
             int choice_supp = scanner.nextInt();
             
             switch (choice_supp) {
                 case 1: System.out.println("Provide me with the product id ");
             	String productId_purchase = scanner.next();
             	Product product_purchase = inventoryManager.getProductById(productId_purchase);
             	System.out.println("Provide me with the quantity: ");
             	int Quantity_purchase = scanner.nextInt();
             	LocalDate currentDate1 = LocalDate.now();
             	SalesTransaction purchase_add = new SalesTransaction(product_purchase, Quantity_purchase, currentDate1, "purchase");
             	product_purchase.incrementQuantity(Quantity_purchase);
             	inventoryManager.addTransaction(purchase_add);
             	inventoryManager.saveProductsToFile("C:\\Users\\Mega-PC\\Documents\\NetBeansProjects\\SEproject\\src\\main\\java\\com\\mycompany\\seproject\\products.txt");  // Save the updated product quantity to file
             	purchase_add.recordToFile();
             	System.out.println("Purchase Transaction added successfully");
             	System.out.println(product_purchase.toString());
                 break;
                 case 2:inventoryManager.displayInventory_supplier();break;
        }}
           
            
    }



    

    private static void createUser(List<User> users, Scanner scanner) {
        System.out.print("Enter a new username: ");
        String newUsername = scanner.next();
        System.out.print("Enter a new password: ");
        String newPassword = scanner.next();        
        String newRole = "customer";
        User newUser = new User(generateUserId(), newUsername, newPassword, newRole);
        users.add(newUser);
        User.saveUsersToFile(users, "C:\\Users\\Mega-PC\\Documents\\NetBeansProjects\\SEproject\\src\\main\\java\\com\\mycompany\\seproject\\users.txt");
    }

    private static String generateUserId() {
        return "UID" + System.currentTimeMillis();
    }

    private static User authenticate(List<User> users, Scanner scanner) {
        System.out.println("----- Login -----");

        System.out.print("Enter username: ");
        String enteredUsername = scanner.next();

        System.out.print("Enter password: ");
        String enteredPassword = scanner.next();
        for (User user : users) {
            if (user.authenticate(enteredUsername, enteredPassword)) {
                System.out.println("Authentication successful!");
                return user; 
            }
        }

        System.out.println("Authentication failed.");
        return null; 
    }
}
