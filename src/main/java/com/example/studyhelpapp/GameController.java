package com.example.studyhelpapp;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Controller for the Mix & Match Flashcards game.
 *
 * This class loads flashcards from the selected study set,
 * displays a term, gives four possible definition choices,
 * and checks whether the user selected the correct match.
 */
public class GameController {
    @FXML
    private Button homeButton;

    @FXML
    private Label termLabel;

    @FXML
    private Label feedbackLabel;

    @FXML
    private Label progressLabel;

    @FXML
    private Button choiceButton1;

    @FXML
    private Button choiceButton2;

    @FXML
    private Button choiceButton3;

    @FXML
    private Button choiceButton4;

    private ArrayList<Flashcards> flashcards;
    private int currentIndex = 0;
    private Flashcards currentCard;

    @FXML
    public void initialize(){
        setupEventHandlers();
        loadGame();
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
        choiceButton1.setOnAction(e -> checkAnswer(choiceButton1.getText()));
        choiceButton2.setOnAction(e -> checkAnswer(choiceButton2.getText()));
        choiceButton3.setOnAction(e -> checkAnswer(choiceButton3.getText()));
        choiceButton4.setOnAction(e -> checkAnswer(choiceButton4.getText()));
    }
    
    /**
     * Loads flashcards from the selected study set.
     * The game requires at least 4 flashcards so it can show 4 answer choices.
     */
    private void loadGame() {
        if (Session.currentStudySet == null) {
            termLabel.setText("No study set selected.");
            feedbackLabel.setText("Please choose a study set first.");
            progressLabel.setText("");
            disableChoiceButtons();
            return;
        }

        if (Session.currentStudySet.getFlashcards().size() < 4) {
            termLabel.setText("Not enough flashcards.");
            feedbackLabel.setText("Create at least 4 flashcards.");
            progressLabel.setText("");
            disableChoiceButtons();
            return;
        }

        flashcards = new ArrayList<>(Session.currentStudySet.getFlashcards());
        Collections.shuffle(flashcards);
        currentIndex = 0;
        showQuestion();
    }

    /**
     * Displays the current flashcard term and four shuffled definition choices.
     */
    private void showQuestion() {
        if (currentIndex >= flashcards.size()) {
            termLabel.setText("Game Complete!");
            feedbackLabel.setText("You matched all flashcards.");
            progressLabel.setText(flashcards.size() + " / " + flashcards.size());
            disableChoiceButtons();
            return;
        }

        currentCard = flashcards.get(currentIndex);
        termLabel.setText(currentCard.getTerm());
        feedbackLabel.setText("");
        progressLabel.setText((currentIndex + 1) + " / " + flashcards.size());

        ArrayList<String> choices = new ArrayList<>();
        choices.add(currentCard.getDefinition());

        for (Flashcards card : flashcards) {
            if (!card.getDefinition().equals(currentCard.getDefinition()) && choices.size() < 4) {
                choices.add(card.getDefinition());
            }
        }

        Collections.shuffle(choices);

        choiceButton1.setText(choices.get(0));
        choiceButton2.setText(choices.get(1));
        choiceButton3.setText(choices.get(2));
        choiceButton4.setText(choices.get(3));
    }

    /**
     * Checks if the selected definition matches the current term.
     */
    UserManager userManager = new UserManager();
    private void checkAnswer(String selectedDefinition) {
        if (selectedDefinition.equals(currentCard.getDefinition())) {
            feedbackLabel.setText("Correct!");
            Session.currentUser.setPoint(Session.currentUser.getPoint() + 10);
            userManager.updateUser(Session.currentUser.getUsername(), Session.currentUser.getPassword(), Session.currentUser.getPoint());
            currentIndex++;
            showQuestion();
        } else {
            feedbackLabel.setText("Try again.");
        }
    }

    /**
     * Disables answer buttons when the game cannot continue.
     */
    private void disableChoiceButtons() {
        choiceButton1.setDisable(true);
        choiceButton2.setDisable(true);
        choiceButton3.setDisable(true);
        choiceButton4.setDisable(true);
    }
}
