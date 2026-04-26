package com.example.studyhelpapp;

import java.io.File;
import java.io.FileWriter;

public class AvatarManager {

    private final String FOLDER = "users/";

    public void saveAvatar(String username, String item) {

        //Create user-specific folder if it doesn't exist
        String folder = FOLDER + username + "/";
        new File(folder).mkdirs();

        //File is named after the study set
        String filename = folder + username + "Avatar.txt";

        try (FileWriter writer = new FileWriter(filename, true)) {

            //Write each flashcard to the file

            writer.write(item + "\n");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}