package com.example.studyhelpapp;

import java.util.ArrayList;

public class StudySet {
    private String name;
    private ArrayList<Flashcards> flashcards;

    public StudySet(String name){
        this.name = name;
        this.flashcards = new ArrayList<>();
    }
    public String getName(){return name;}
    public ArrayList<Flashcards> getFlashcards(){return flashcards;}

    @Override
    public String toString() {
        return name; // shows in ListView
    }


}
