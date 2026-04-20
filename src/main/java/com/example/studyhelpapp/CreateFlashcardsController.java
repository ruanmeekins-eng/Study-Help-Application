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
    private TextField termTextField;

    @FXML
    private TextArea definitionTextArea;


    @FXML
    ListView<Flashcards> flashcardListView = new ListView<>();

    FlashcardManager manager = new FlashcardManager();

    ArrayList<Flashcards> flashcardList = manager.loadFlashcards(Session.currentUser.getUsername());

    public void initialize(){
        populateListView(flashcardList);
        setupEventHandlers();
        saveChangesButton.setVisible(false);
        flashcardListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Flashcards>() {
            @Override
            public void changed(ObservableValue<? extends Flashcards> observable, Flashcards oldValue, Flashcards newValue) {
                flashcardListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, selectedCard) -> {
                    if (selectedCard != null) {
                        saveChangesButton.setVisible(true);
                        addButton.setVisible(false);

                        termTextField.setText(selectedCard.getTerm());
                        definitionTextArea.setText(selectedCard.getDefinition());
                    }
                });
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
                termTextField.setPromptText("Term:");
                String term = termTextField.getText();
                String definition = definitionTextArea.getText();

                Flashcards newFlashcard = new Flashcards(term, definition);

                if (!termExists(term) && !term.isEmpty()) {
                    manager.saveFlashcard(Session.currentUser.getUsername(), term, definition);
                    flashcardListView.getItems().add(newFlashcard);

                }
                else{
                    termTextField.setPromptText("Term already exists!");
                }
                termTextField.setText("");
                definitionTextArea.setText("");
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
                    manager.saveAllFlashcards(Session.currentUser.getUsername(), flashcardList);
                }
                manager.saveAllFlashcards(Session.currentUser.getUsername(), flashcardList);
                saveChangesButton.setVisible(false);
                addButton.setVisible(true);
                termTextField.setText("");
                definitionTextArea.setText("");
            }

        });

    }
    public void populateListView(ArrayList<Flashcards> flashcardList){
        for (Flashcards flashcards : flashcardList){
            flashcardListView.getItems().add(flashcards);
        }
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
