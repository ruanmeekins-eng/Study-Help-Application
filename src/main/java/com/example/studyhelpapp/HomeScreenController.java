package com.example.studyhelpapp;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeScreenController {

    @FXML
    private Button studyButton;

    @FXML
    private Button gameButton;

    @FXML
    private Button avatarButton;

    @FXML
    private Button logoutButton;

    @FXML
    public void initialize(){
        setupEventHandlers();
    }

    @FXML
    private void setupEventHandlers(){
        studyButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {
                    SceneLoader.swapScene("Study-Screen.fxml", "Study");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        gameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {
                    SceneLoader.swapScene("Game-Screen.fxml", "Game");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        avatarButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {
                    SceneLoader.swapScene("Avatar-Screen.fxml", "Avatar");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        logoutButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {
                    SceneLoader.swapScene("Login-Screen.fxml", "Avatar");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });


    }


}