package com.example.studyhelpapp;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Manages saving and loading of study sets to and from the file system.
 * Each user has their own folder, and each study set is stored as a text file.
 */
public class FlashcardManager {

    //Base directory where all user data is stored
    private final String FOLDER = "users/";

    /**
     * Saves a study set to a file for the given user.
     * Each flashcard is written as term|definition on a new line.
     */
    public void saveStudySet(String username, StudySet set) {

        //Create user-specific folder if it doesn't exist
        String folder = FOLDER + username + "/";
        new File(folder).mkdirs();

        //File is named after the study set
        String filename = folder + set.getName() + ".txt";

        try (FileWriter writer = new FileWriter(filename)) {

            //Write each flashcard to the file
            for (Flashcards card : set.getFlashcards()) {
                writer.write(card.getTerm() + "|" + card.getDefinition() + "\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads all study sets for a given user from the file system.
     * Each file represents a study set.
     */
    public ArrayList<StudySet> loadStudySets(String username) {

        //List to store loaded study sets
        ArrayList<StudySet> sets = new ArrayList<>();

        //Locate the users folder
        File folder = new File(FOLDER + username + "/");

        //If folder doesn't exist return empty list
        if (!folder.exists()) return sets;

        //Loop through each file
        for (File file : folder.listFiles()) {

            //Get study set name from filename
            String setName = file.getName().replace(".txt", "");
            StudySet set = new StudySet(setName);

            try (Scanner scanner = new Scanner(file)) {

                //Read each line
                while (scanner.hasNextLine()) {
                    //Split line into term and definition
                    String[] parts = scanner.nextLine().split("\\|");
                    //Only add valid flashcards
                    if (parts.length == 2) {
                        set.getFlashcards().add(new Flashcards(parts[0], parts[1]));
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            // Add the fully loaded study set to the list
            sets.add(set);
        }
        return sets;
    }
}
