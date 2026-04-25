package com.example.studyhelpapp;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.IOException;
/**
 * Controller for the Create Flashcards screen.
 *
 * Handles user interactions for creating, editing, and saving flashcards
 * within a study set. Also manages UI updates such as displaying flashcards
 * and switching between add and edit modes.
 *
 */
public class CreateFlashcardsController {

    //Goes back to Study Screen
    @FXML
    private Button backButton;

    //Adds new flashcard
    @FXML
    private Button addButton;

    //Saves changes to selected flashcard
    @FXML
    private Button saveChangesButton;

    //Saves a new study set
    @FXML
    private Button saveStudySetButton;

    //Input field for the term of the flashcard
    @FXML
    private TextField termTextField;

    //Input field for the definition of the flashcard
    @FXML
    private TextArea definitionTextArea;

    //Displays the flashcards in current study set
    @FXML
    ListView<Flashcards> flashcardListView = new ListView<>();

    //Current user
    private String username = Session.currentUser.getUsername();

    //Used to gain access to the saved flashcard information
    private FlashcardManager manager = new FlashcardManager();

    /**
     * Initializes the controller after the FXML is loaded.
     * Sets up event handlers, loads flashcards into the ListView,
     * and configures selection behavior.
     */
    public void initialize(){

        setupEventHandlers();//Sets up controls to be handled
        saveChangesButton.setVisible(false);//Hides this button when Screen is first loaded
        StudySet set = Session.currentStudySet;//Get the current study set

        //Adds all the flashcards of current set to the list view if it is not empty
        if (set != null) {
            flashcardListView.getItems().addAll(set.getFlashcards());
        }

        //Listener for the flashcard list view that determines which card has been selected
        flashcardListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, selectedCard) -> {
            if (selectedCard != null) {
                saveChangesButton.setVisible(true);
                addButton.setVisible(false);
                //Updates the current select cards information
                termTextField.setText(selectedCard.getTerm());
                definitionTextArea.setText(selectedCard.getDefinition());
            }
        });
    }

    //Sets up the listeners for all controls
    @FXML
    private void setupEventHandlers() {

        //Handles going back to the Study Screen
        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {
                    SceneLoader.swapScene("Study-Screen.fxml", "Study");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        //Handles adding a new flashcard to the current study set
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                String term = termTextField.getText();
                String definition = definitionTextArea.getText();
                Flashcards newFlashcard = new Flashcards(termTextField.getText(), definition); //Create a new flashcard

                //Adds flashcard to study set and the flashcard list
                //view if it doesn't exist already
                if (!termExists(term) && !term.isEmpty()) {
                    Session.currentStudySet.getFlashcards().add(newFlashcard);
                    flashcardListView.getItems().add(newFlashcard);
                    termTextField.clear();
                    definitionTextArea.clear();

                } else {//Term already exists
                    termTextField.setPromptText("Term already exists!");
                }
            }
        });

        //Handles saving changes to already existing flashcards
        saveChangesButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                termTextField.setPromptText("Term");
                //Retrieves the selected card from the list view
                Flashcards selectedCard = flashcardListView.getSelectionModel().getSelectedItem();

                if (selectedCard != null) {
                    selectedCard.setTerm(termTextField.getText());
                    selectedCard.setDefinition(definitionTextArea.getText());

                    flashcardListView.refresh(); //Update the list

                    //Save to file
                    manager.saveStudySet(username, Session.currentStudySet);
                }
                //Updates the buttons and clears the term and definition text boxes
                saveChangesButton.setVisible(false);
                addButton.setVisible(true);
                termTextField.clear();
                definitionTextArea.clear();
            }
        });

        //Handles saving the current study set and returning to the Study Screen
        saveStudySetButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                //Saves the current study set to the specified user
                manager.saveStudySet(username, Session.currentStudySet);
                try {
                    SceneLoader.swapScene("Study-Screen.fxml", "Study");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    /**
     * Checks if a flashcard with the given term already exists
     * in the current ListView.
     *
     * @param term the term to check
     * @return true if the term already exists, false otherwise
     */
    private boolean termExists(String term){
        for (Flashcards card : flashcardListView.getItems()) {
            if (card.getTerm().equalsIgnoreCase(term)) {
                return true;
            }
        }
        return false;
    }
}
