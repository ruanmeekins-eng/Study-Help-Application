package com.example.studyhelpapp;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.util.ArrayList;

public class StudyController {
    @FXML
    private Button homeButton;

    @FXML
    private Button addStudySetButton;

    @FXML
    private Button nextButton;

    @FXML
    private Button backButton;

    @FXML
    private Button flipButton;

    @FXML
    private Label flashcardLabel;

    @FXML
    private ListView studySetList;

    private boolean isTerm = false;

    private ArrayList<Flashcards> flashcards;
    private int currentIndex = 0;


    @FXML
    public void initialize(){
        System.out.println(flashcardLabel);
        FlashcardManager manager = new FlashcardManager();
        flashcards = manager.loadFlashcards(Session.currentUser.getUsername());
        displayTerm();
        setupEventHandlers();
    }


    @FXML
    private void setupEventHandlers() {
        homeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {
                    SceneLoader.swapScene("Home-Screen.fxml", "Home");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        addStudySetButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {
                    SceneLoader.swapScene("Create-Flashcards-Screen.fxml", "Home");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        flipButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if(isTerm){
                    displayTerm();
                    isTerm = false;
                }
                else if(!isTerm){
                    displayDefinition();
                    isTerm = true;
                }
            }
        });
        nextButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (flashcards == null || flashcards.isEmpty()) {
                    return;
                }
                currentIndex++;
                if (currentIndex >= flashcards.size()) currentIndex = 0;
                displayTerm();
            }
        });
        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (flashcards == null || flashcards.isEmpty()) {
                    return;
                }
                currentIndex--;
                if (currentIndex < 0) currentIndex = flashcards.size() - 1;
                displayTerm();
            }
        });
    }





    private void displayTerm() {
        if (flashcards == null || flashcards.isEmpty()) {
            flashcardLabel.setText("No flashcards available");
        }
        else {

            Flashcards card = flashcards.get(currentIndex);
            flashcardLabel.setText(card.getTerm());
        }
    }

    private void displayDefinition() {
        if (flashcards == null || flashcards.isEmpty()) {
            flashcardLabel.setText("No flashcards available");

        } else {

            Flashcards card = flashcards.get(currentIndex);
            flashcardLabel.setText(card.getDefinition());

        }
    }

}
