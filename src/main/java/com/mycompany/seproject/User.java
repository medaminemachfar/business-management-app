/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.seproject;

/**
 *
 * @author Mega-PC
 */
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class User {
    private String userId;
    private String username;
    private String password;
    private String role; // (admin, manager, customer, supplier)

    private static List<User> users = new ArrayList<>();

    public User(String userId, String username, String password, String role) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // Getters and setters

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public void setuserId(String userId) {
        this.userId = userId;
    }

    public void setusername(String username) {
        this.username = username;
    }

    public void setpassword(String password) {
        this.password = password;
    }

    public void setrole(String role) {
        this.role = role;
    }

    // Authentication method
    public boolean authenticate(String inputUsername, String inputPassword) {
        return this.username.equals(inputUsername) && this.password.equals(inputPassword);
    }

    // Authorization check method
    public boolean hasPermission(String requiredRole) {
        return this.role.equals(requiredRole);
    }

    // Used for loading users from a file
    public String toStringRepresentation() {
        return userId + "," + username + "," + password + "," + role;
    }

    // Update user password
    public void update_user_password(String new_password, String input_user_Id, String input_user_name, String input_user_role) {
        if (this.userId.equals(input_user_Id) && this.username.equals(input_user_name)
                && this.role.equals(input_user_role)) {
            this.password = new_password;
        }
    }

    // Update user name
    public void update_user_name(String input_password, String input_user_Id, String new_user_name,
            String input_user_role) {
        if (this.userId.equals(input_user_Id) && this.password.equals(input_password)
                && this.role.equals(input_user_role)) {
            this.username = new_user_name;
        }
    }


    public static List<User> loadUsersFromFile(String filePath) {
        users = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

               
                String[] parts = line.split(",");

                if (parts.length == 4) {
                    String userId = parts[0].trim();
                    String username = parts[1].trim();
                    String password = parts[2].trim();
                    String role = parts[3].trim();

                    User user = new User(userId, username, password, role);
                    users.add(user);
                }
            }
        } catch (FileNotFoundException e) {
            e.getMessage(); 
        }

        return users;
    }

    public static User getUserById(String userId) {
        for (User user : users) {
            if (user.getUserId().equals(userId)) {
                return user; 
            }
        }
        return null; 
    }

    public static void setUsersList(List<User> userList) {
        users = userList;
    }
    public static void saveUsersToFile(List<User> users, String filename) {
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(filename), StandardCharsets.UTF_8))) {

            for (User user : users) {
               
                String userString = user.toStringRepresentation();

                
                writer.write(userString);
                writer.newLine(); 
            }

            System.out.println("Users saved to file.");
        } catch (IOException e) {
            e.getMessage();
            System.err.println("Error saving users to file: " + e.getMessage());
        }
    }



 //method to add a user
    public static void addUser(User user, List<User> users, String filename) {
        users.add(user);
        saveUsersToFile(users, filename);
    }

    // New method to delete a user
    public static void deleteUser(String userId, List<User> users, String filename) {
        users.removeIf(user -> user.getUserId().equals(userId));
        saveUsersToFile(users, filename);
    }

    // New method to update user role
    public void update_user_role(String input_user_Id, String new_user_role, List<User> users, String filename) {
        if (this.userId.equals(input_user_Id)) {
            this.role = new_user_role;
            saveUsersToFile(users, filename);
        }
    }}

