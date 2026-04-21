package com.example.studyhelpapp;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FlashcardManager {
    private final String FOLDER = "users/";




    public void saveStudySet(String username, StudySet set) {
        String folder = "users/" + username + "/";
        new File(folder).mkdirs();

        String filename = folder + set.getName() + ".txt";

        try (FileWriter writer = new FileWriter(filename)) {
            for (Flashcards card : set.getFlashcards()) {
                writer.write(card.getTerm() + "|" + card.getDefinition() + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<StudySet> loadStudySets(String username) {
        ArrayList<StudySet> sets = new ArrayList<>();

        File folder = new File("users/" + username + "/");
        if (!folder.exists()) return sets;

        for (File file : folder.listFiles()) {
            String setName = file.getName().replace(".txt", "");
            StudySet set = new StudySet(setName);

            try (Scanner scanner = new Scanner(file)) {
                while (scanner.hasNextLine()) {
                    String[] parts = scanner.nextLine().split("\\|");
                    if (parts.length == 2) {
                        set.getFlashcards().add(new Flashcards(parts[0], parts[1]));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            sets.add(set);
        }

        return sets;
    }

}
