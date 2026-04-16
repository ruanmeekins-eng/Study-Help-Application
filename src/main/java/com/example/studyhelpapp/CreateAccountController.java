package com.example.studyhelpapp;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;

public class CreateAccountController {
    @FXML
    private Button createAccountButton;

    @FXML
    private Button backButton;

    @FXML
    private TextField createUsernameTextField;

    @FXML
    private TextField createPasswordTextField;

    @FXML
    public void initialize(){
        setupEventHandlers();
    }

    UserManager userManager = new UserManager();
    @FXML
    private void setupEventHandlers() {
        createAccountButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                String username = createUsernameTextField.getText();
                String password = createPasswordTextField.getText();
                if (username != null && !username.isEmpty() && password != null && !password.isEmpty()) {
                    try {
                        userManager.saveUser(username, password);
                        SceneLoader.swapScene("Home-Screen.fxml", "Create Account");
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                    try {
                        SceneLoader.swapScene("Login-Screen.fxml", "Login");
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }

            }
        });
    }
}
