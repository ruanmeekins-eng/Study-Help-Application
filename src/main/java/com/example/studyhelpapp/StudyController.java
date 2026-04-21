package com.example.studyhelpapp;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.ArrayList;

public class StudyController {

    @FXML
    private Button homeButton;

    @FXML
    private Button flipButton;

    @FXML
    private Button nextButton;

    @FXML
    private Button backButton;

    @FXML
    private Button newStudySetButton;

    @FXML
    private Button editStudySetButton;

    @FXML
    private Label flashcardLabel;

    @FXML
    private TextField setNameTextField;

    @FXML
    private ListView<StudySet> studySetListView;

    private String username = Session.currentUser.getUsername();
    private FlashcardManager manager = new FlashcardManager();
    private ArrayList<Flashcards> flashcards;
    private int currentIndex = 0;
    private boolean isTerm = false;

    @FXML
    public void initialize() {
        setNameTextField.setVisible(false);

        if (Session.currentUser != null) {
            username = Session.currentUser.getUsername();
        }

        if (username != null) {
            ArrayList<StudySet> sets = manager.loadStudySets(username);
            studySetListView.getItems().clear();
            studySetListView.getItems().addAll(sets);
        }

        setupEventHandlers();
    }

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
        flipButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (flashcards == null || flashcards.isEmpty()) return;

                if (isTerm) {
                    displayTerm();
                    isTerm = false;
                } else {
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


        newStudySetButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                setNameTextField.setVisible(true);
                setNameTextField.requestFocus();

                newStudySetButton.setVisible(false);
                editStudySetButton.setVisible(false);
            }
        });


        setNameTextField.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                String name = setNameTextField.getText().trim();

                if (name.isEmpty()) {
                    return;
                }

                StudySet newSet = new StudySet(name);
                Session.currentStudySet = newSet;

                try {
                    SceneLoader.swapScene("Create-Flashcards-Screen.fxml", "New Set");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });


        editStudySetButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                StudySet selectedSet = studySetListView.getSelectionModel().getSelectedItem();

                if (selectedSet == null) {
                    return;
                }

                Session.currentStudySet = selectedSet;

                try {
                    SceneLoader.swapScene("Create-Flashcards-Screen.fxml", "Edit Set");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        // 🔹 Optional: Double-click ListView to edit
        studySetListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                StudySet selectedSet = studySetListView.getSelectionModel().getSelectedItem();

                if (selectedSet != null) {
                    Session.currentStudySet = selectedSet;

                    try {
                        SceneLoader.swapScene("Create-Flashcards-Screen.fxml", "Edit Set");
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
        studySetListView.getSelectionModel().selectedItemProperty().addListener(
                new javafx.beans.value.ChangeListener<StudySet>() {
                    @Override
                    public void changed(javafx.beans.value.ObservableValue<? extends StudySet> obs,
                                        StudySet oldVal,
                                        StudySet selectedSet) {

                        if (selectedSet != null) {
                            Session.currentStudySet = selectedSet;


                            flashcards = selectedSet.getFlashcards();
                            currentIndex = 0;

                            displayTerm(); // update label
                        }
                    }
                }
        );
    }
    private void displayTerm() {
        if (flashcards == null || flashcards.isEmpty()) {
            flashcardLabel.setText("No flashcards available");
            return;
        }

        Flashcards card = flashcards.get(currentIndex);
        flashcardLabel.setText(card.getTerm());
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