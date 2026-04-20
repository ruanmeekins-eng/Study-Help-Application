package com.example.studyhelpapp;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FlashcardManager {
    private final String FOLDER = "users/";



    public void ensureFolderExists() {
        File folder = new File(FOLDER);
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }




    public void saveFlashcard(String username, String term, String definition) {
        ensureFolderExists();
        String filename = FOLDER + username + ".txt";

        try (FileWriter writer = new FileWriter(filename, true)) {
            writer.write(term + "|" + definition + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Flashcards> loadFlashcards(String username) {
        ArrayList<Flashcards> list = new ArrayList<>();
        String filename = FOLDER + username + ".txt";

        try {
            File file = new File(filename);
            if (!file.exists()) {
                return list;
            }
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split("\\|");

                if (parts.length == 2) {
                    list.add(new Flashcards(parts[0], parts[1]));
                }
            }
            scanner.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public void saveAllFlashcards(String username, List<Flashcards> list) {
        String filename = "users/" + username + ".txt";

        try (FileWriter writer = new FileWriter(filename)) { // overwrite
            for (Flashcards card : list) {
                writer.write(card.getTerm() + "|" + card.getDefinition() + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
