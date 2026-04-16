package com.example.studyhelpapp;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginScreenController {

    @FXML
    private Button newAccountButton;

    @FXML
    private Button loginButton;

    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private Label accountNotFoundLabel;

    @FXML
    public void initialize(){
        setupEventHandlers();
    }

    UserManager userManager = new UserManager();
    @FXML
    private void setupEventHandlers() {
        newAccountButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {
                    SceneLoader.swapScene("Create-Account-Screen.fxml", "Create Account");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }

        });
        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                String username = usernameTextField.getText();
                String password = passwordTextField.getText();
                if (username != null && !username.isEmpty() && password != null && !password.isEmpty() && userManager.login(username, password)){
                    try {
                        SceneLoader.swapScene("Home-Screen.fxml", "Home");
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                else{
                    accountNotFoundLabel.setText("Account not found!");
                }
            }
        });
    }
}
