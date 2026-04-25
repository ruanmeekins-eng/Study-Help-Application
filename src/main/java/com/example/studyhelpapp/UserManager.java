package com.example.studyhelpapp;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Handles user account storage and validation.
 *
 * User data is stored in a plain text file users.txt in the format:
 * username,password
 *
 */
public class UserManager {

    //File that stores all registered users
    private final String FILE_NAME = "users.txt";

    /**
     * Saves a new user to the users file.
     * Adds the username and password to the file.
     */
    public void saveUser(String username, String password, int point) {
        try (FileWriter writer = new FileWriter(FILE_NAME, true)) {

            //Store user in format: username,password,points
            writer.write(username.toLowerCase() + "," + password + "," +  point + "\n");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void updateUser(String username, String password, int point) {
        File file = new File("users.txt");
        List<String> lines = new ArrayList<>();

        try (Scanner scanner = new Scanner(file)) {

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");

                if (parts[0].equals(username)) {
                    //Replace this user
                    lines.add(username + "," + password + "," + point);
                } else {
                    //Keep the rest
                    lines.add(line);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        //Rewrite entire file
        try (FileWriter writer = new FileWriter(file)) {
            for (String l : lines) {
                writer.write(l + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Validates a users login credentials.
     * Checks the users.txt file for a matching username and password.
     */
    public User login(String username, String password) {
        try {
            File file = new File(FILE_NAME);

            //If no user file exists login automatically fails
            if (!file.exists()) return null;

            Scanner scanner = new Scanner(file);

            //Read each stored user by line
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split(",");

                //Check for matching information
                if (parts[0].equals(username) && parts[1].equals(password)) {
                    User user = new User(username, password, Integer.parseInt(parts[2]));
                    scanner.close();
                    return user;
                }
            }
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
