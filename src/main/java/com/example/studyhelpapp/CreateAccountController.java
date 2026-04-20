package com.example.studyhelpapp;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    private Label invalidAccountLabel;

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
                if (username != null && !username.isEmpty() && password != null && !password.isEmpty() && !username.contains(" ")) {
                    try {
                        User user = new User(username, password);
                        userManager.saveUser(username, password);
                        Session.currentUser = user;
                        SceneLoader.swapScene("Home-Screen.fxml", "Create Account");
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                else{
                    invalidAccountLabel.setText("Invalid Username/Password");
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
