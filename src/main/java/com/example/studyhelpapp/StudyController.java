package com.example.studyhelpapp;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.IOException;
import java.util.ArrayList;
/**
 * Controller for the Study screen.
 *
 * Manages study set selection, flashcard navigation, and user interactions
 * such as flipping cards, moving between cards, and creating/editing study sets.
 *
 */
public class StudyController {
    //Goes back to Home Screen
    @FXML
    private Button homeButton;

    //Displays term or definition
    @FXML
    private Button flipButton;

    //Moves forward through flashcard list
    @FXML
    private Button nextButton;

    //Moves backward through flashcard list
    @FXML
    private Button backButton;

    //Displays prompt to create a new study set name
    @FXML
    private Button newStudySetButton;

    //Swaps to Create Flashcard Screen with selected study set
    @FXML
    private Button editStudySetButton;

    // Opens the mix-match game screen
    @FXML
    private Button startGameButton;

    //Label that displays the term and definition of flashcard
    @FXML
    private Label flashcardLabel;

    @FXML
    private Label nameLabel;

    //Creates the name for the study set from user input
    @FXML
    private TextField setNameTextField;

    //List of users study sets
    @FXML
    private ListView<StudySet> studySetListView;

    //Current user
    private String username = Session.currentUser.getUsername();

    //Manger that gives access to saved flashcard information
    private FlashcardManager manager = new FlashcardManager();

    //List of flashcards within a study set
    private ArrayList<Flashcards> flashcards;

    //Index to keep track of position within flashcard list
    private int currentIndex = 0;

    //Determines if the flashcardLabel is displaying the term or definition
    private boolean isNotTerm = false;


    /**
     * Initializes the controller after the FXML is loaded.
     * Loads study sets for the current user and sets up event handlers.
     */
    @FXML
    public void initialize() {
        setNameTextField.setVisible(false);
        nameLabel.setVisible(false);

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
    /**
     * Registers all UI event handlers including button actions,
     * list selection behavior, and navigation between scenes.
     */
    private void setupEventHandlers() {

        //Handles going back to the Home Screen
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

        //Handles showing either the term or definition of the flashcard
        flipButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (flashcards == null || flashcards.isEmpty()) return;//If no card exists, return empty
                //Display term if currently displaying definition
                if (isNotTerm) {
                    displayTerm();
                    isNotTerm = false;
                }
                //Displays definition if currently displaying term
                else {
                    displayDefinition();
                    isNotTerm = true;
                }
            }
        });

        //Handles looping forward through list to display the next flashcard
        // Also resets the flip state so each new card starts by displaying the term.
        nextButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (flashcards == null || flashcards.isEmpty()) {
                    return;
                }
                currentIndex++;
                //Go back to the beginning of the list if the end is reached
                if (currentIndex >= flashcards.size()) currentIndex = 0;

                // Reset flip state so new card always shows the term first
                isNotTerm = false;
                displayTerm();
            }
        });

        //Handles looping backword through the list to display the previous flashcard
        // Resets the flip state so the card always displays the term first
        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (flashcards == null || flashcards.isEmpty()) {
                    return;
                }
                currentIndex--;
                //Go to the end of the list if beginning of the list is reached
                if (currentIndex < 0) currentIndex = flashcards.size() - 1;

                // Reset flip state when switching cards
                isNotTerm = false;
                displayTerm();
            }
        });

        //Handles getting the text field to input a new name
        newStudySetButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                setNameTextField.setVisible(true);
                nameLabel.setVisible(true);
                setNameTextField.requestFocus();

                newStudySetButton.setVisible(false);
                editStudySetButton.setVisible(false);
            }
        });

        //Handles receiving a new name and switching to the Create Flashcard Screen
        setNameTextField.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                String name = setNameTextField.getText().trim();

                if (name.isEmpty()) {
                    return;
                }

                StudySet newSet = new StudySet(name);//Creates new instance of StudySet object
                Session.currentStudySet = newSet;//Set the current study set being accessed to the newly created set

                try {
                    SceneLoader.swapScene("Create-Flashcards-Screen.fxml", "New Set");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        //Handles selecting a study set to edit and switching to Create Flashcards Screen
        editStudySetButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                StudySet selectedSet = studySetListView.getSelectionModel().getSelectedItem();
                //No set selected
                if (selectedSet == null) {
                    return;
                }

                Session.currentStudySet = selectedSet;//Set the selected set as the currently used set

                try {
                    SceneLoader.swapScene("Create-Flashcards-Screen.fxml", "Edit Set");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

       //Handles selecting a study set to edit by double-clicking. swaps to Create Flashcards Screen
        studySetListView.setOnMouseClicked(event -> {
            //Double-click listener
            if (event.getClickCount() == 2) {
                StudySet selectedSet = studySetListView.getSelectionModel().getSelectedItem();

                if (selectedSet != null) {
                    Session.currentStudySet = selectedSet;//Set the selected set as the currently used set

                    try {
                        SceneLoader.swapScene("Create-Flashcards-Screen.fxml", "Edit Set");
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        //Study set list view that handles displaying the flashcards of the selected set
        studySetListView.getSelectionModel().selectedItemProperty().addListener(
                new javafx.beans.value.ChangeListener<StudySet>() {
                    @Override
                    public void changed(javafx.beans.value.ObservableValue<? extends StudySet> obs, StudySet oldVal, StudySet selectedSet) {

                        if (selectedSet != null) {
                            Session.currentStudySet = selectedSet; //Set the selected set as the currently used set
                            flashcards = selectedSet.getFlashcards();//Gets the list of flashcards from the current study set
                            currentIndex = 0;

                            // Reset flip state when switching sets
                            isNotTerm = false;
                            displayTerm(); //Displays the first card
                        }
                    }
                }
        );

        // Handles opening the Mix & Match game screen
        startGameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                // Ensures a study set is selected
                if (Session.currentStudySet == null) {
                    flashcardLabel.setText("Please select a study set first.");
                    return;
                }

                try {
                    SceneLoader.swapScene("Game-Screen.fxml", "Mix & Match Flashcards");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        }
    /**
     * Displays the current flashcard's term in the label.
     * If no flashcards exist, shows a default message.
     */
    private void displayTerm() {
        if (flashcards == null || flashcards.isEmpty()) {
            flashcardLabel.setText("No flashcards available");
            return;
        }
        Flashcards card = flashcards.get(currentIndex);
        flashcardLabel.setText(card.getTerm());
    }

    /**
     * Displays the current flashcard's definition in the label.
     * If no flashcards exist, shows a default message.
     */
    private void displayDefinition() {
        if (flashcards == null || flashcards.isEmpty()) {
            flashcardLabel.setText("No flashcards available");

        } else {
            Flashcards card = flashcards.get(currentIndex);
            flashcardLabel.setText(card.getDefinition());

        }
    }
    }





