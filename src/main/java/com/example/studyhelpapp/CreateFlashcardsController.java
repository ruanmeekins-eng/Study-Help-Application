package com.example.studyhelpapp;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CreateFlashcardsController {

    @FXML
    private Button backButton;

    @FXML
    private Button addButton;

    @FXML
    private Button saveChangesButton;

    @FXML
    private Button saveStudySetButton;

    @FXML
    private TextField termTextField;

    @FXML
    private TextArea definitionTextArea;


    @FXML
    ListView<Flashcards> flashcardListView = new ListView<>();

    private String username = Session.currentUser.getUsername();

    private FlashcardManager manager = new FlashcardManager();



    public void initialize(){

        setupEventHandlers();
        saveChangesButton.setVisible(false);
        StudySet set = Session.currentStudySet;

        if (set != null) {
            flashcardListView.getItems().addAll(set.getFlashcards());
        }
        flashcardListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, selectedCard) -> {
            if (selectedCard != null) {
                saveChangesButton.setVisible(true);
                addButton.setVisible(false);

                termTextField.setText(selectedCard.getTerm());
                definitionTextArea.setText(selectedCard.getDefinition());
            }

        });
    }

    @FXML
    private void setupEventHandlers() {
        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {
                    SceneLoader.swapScene("Study-Screen.fxml", "Create Account");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }

        });
        addButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                String term = termTextField.getText();
                String definition = definitionTextArea.getText();
                Flashcards newFlashcard = new Flashcards(termTextField.getText(), definition);

                if (!termExists(term) && !term.isEmpty()) {

                    Session.currentStudySet.getFlashcards().add(newFlashcard);

                    flashcardListView.getItems().add(newFlashcard);
                    termTextField.clear();
                    definitionTextArea.clear();

                } else {
                    termTextField.setPromptText("Term already exists!");
                }
            }

        });
        saveChangesButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                termTextField.setPromptText("Term");
                Flashcards selectedCard = flashcardListView.getSelectionModel().getSelectedItem();

                if (selectedCard != null) {
                    selectedCard.setTerm(termTextField.getText());
                    selectedCard.setDefinition(definitionTextArea.getText());

                    flashcardListView.refresh(); // update UI

                    // Save to file (overwrite)
                    manager.saveStudySet(username, Session.currentStudySet);
                }

                saveChangesButton.setVisible(false);
                addButton.setVisible(true);
                termTextField.clear();
                definitionTextArea.clear();

            }

        });
        saveStudySetButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                manager.saveStudySet(username, Session.currentStudySet);
                try {
                    SceneLoader.swapScene("Study-Screen.fxml", "Create Account");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });


    }


    private boolean termExists(String term){
        for (Flashcards card : flashcardListView.getItems()) {
            if (card.getTerm().equalsIgnoreCase(term)) {
                return true;
            }
        }
        return false;
    }
}
