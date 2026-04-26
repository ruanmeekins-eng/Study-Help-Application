package com.example.studyhelpapp;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class AvatarManager {

    private final String FOLDER = "avatars/";

    //Saves all avatar items for a user
    public void saveAvatarState(String username, Map<String, Boolean> items) {

        //Create user folder if it doesn't exist
        String folder = FOLDER + username + "/";
        new File(folder).mkdirs();

        //File where avatar data is stored
        String filename = folder + username + "Avatar.txt";

        try (FileWriter writer = new FileWriter(filename)) {

            //Write each item and whether it's selected (true/false)
            for (String item : items.keySet()) {
                writer.write(item + "," + items.get(item) + "\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Loads all avatar items for a user
    public Map<String, Boolean> loadAvatarState(String username) {

        Map<String, Boolean> items = new HashMap<>();

        //File to read avatar data from
        String filename = FOLDER + username + "/" + username + "Avatar.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {

            String line;

            //Read file line by line
            while ((line = br.readLine()) != null) {

                String[] parts = line.split(",");

                //Make sure line is valid before adding
                if (parts.length == 2) {
                    items.put(parts[0], Boolean.parseBoolean(parts[1]));
                }
            }

        } catch (IOException e) {

        }
        return items;
    }
}