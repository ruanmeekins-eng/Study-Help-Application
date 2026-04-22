package com.example.studyhelpapp;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
    public void saveUser(String username, String password) {
        try (FileWriter writer = new FileWriter(FILE_NAME, true)) {

            //Store user in format: username,password
            writer.write(username.toLowerCase() + "," + password + "\n");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Validates a users login credentials.
     * Checks the users.txt file for a matching username and password.
     */
    public boolean login(String username, String password) {
        try {
            File file = new File(FILE_NAME);

            //If no user file exists login automatically fails
            if (!file.exists()) return false;

            Scanner scanner = new Scanner(file);

            //Read each stored user by line
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split(",");

                //Check for matching information
                if (parts[0].equals(username) && parts[1].equals(password)) {
                    scanner.close();
                    return true;
                }
            }
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
