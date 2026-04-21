package com.example.studyhelpapp;

import java.util.ArrayList;

/***
 * Object that represents a study set of flashcards which includes
 * a name and an array list of Flashcards objects
 */
public class StudySet {

    //Attributes
    private String name;
    private ArrayList<Flashcards> flashcards;

    //Constructor
    public StudySet(String name){
        this.name = name;
        this.flashcards = new ArrayList<>();
    }
    //Accessors
    public String getName(){return name;}
    public ArrayList<Flashcards> getFlashcards(){return flashcards;}

    //Information displayed in the list view
    @Override
    public String toString() {
        return name;
    }
}
