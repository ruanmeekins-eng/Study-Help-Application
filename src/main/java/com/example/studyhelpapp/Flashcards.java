package com.example.studyhelpapp;

/***
 * Object that represents a flashcard containing a term and
 * a definition.
 */
public class Flashcards {
    //Attributes
    private String term;
    private String definition;

    //Constructor
    public Flashcards(String term, String definition){
        this.term = term;
        this.definition = definition;
    }
    //Mutators
    public void setTerm(String term){this.term = term;}
    public void setDefinition(String definition){this. definition = definition;}

    //Accessors
    public String getTerm(){return term;}
    public String getDefinition(){return definition;}

    //Displays the term of flashcard in the list
    @Override
    public String toString() {
        return term;
    }
}
