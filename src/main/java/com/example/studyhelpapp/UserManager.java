package com.example.studyhelpapp;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class UserManager {
    private final String FILE_NAME = "users.txt";

    // Save new user
    public void saveUser(String username, String password) {
        try (FileWriter writer = new FileWriter(FILE_NAME, true)) {
            writer.write(username + "," + password + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Check login
    public boolean login(String username, String password) {
        try {
            File file = new File(FILE_NAME);

            // If file doesn't exist yet
            if (!file.exists()) return false;

            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split(",");

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
