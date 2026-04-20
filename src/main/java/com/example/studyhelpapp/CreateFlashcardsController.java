package com.example.studyhelpapp;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;

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

    public void initialize(){
        setupEventHandlers();
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
                FlashcardManager card = new FlashcardManager();
                card.saveFlashcard(Session.currentUser.getUsername(), term, definition);
                termTextField.setText("");
                definitionTextArea.setText("");

            }

        });
    }

}
