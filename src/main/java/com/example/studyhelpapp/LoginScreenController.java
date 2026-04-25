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

    //Switches to Create Account Screen
    @FXML
    private Button newAccountButton;

    //Validates login and switches to Home Screen
    @FXML
    private Button loginButton;

    //Receives username input from the user
    @FXML
    private TextField usernameTextField;

    //Receives password input from the user
    @FXML
    private PasswordField passwordTextField;

    //Displays error if account is not found
    @FXML
    private Label accountNotFoundLabel;

    /***
     * Initializes the controller after the FXML is loaded.
     */
    @FXML
    public void initialize(){
        setupEventHandlers();
    }

    //Manager that allows access to the userManager data and methods
    UserManager userManager = new UserManager();

    @FXML
    private void setupEventHandlers() {

        //Handles switching to Create Account Screen
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

        //Handles validation of existing account and switching to the Home Screen
        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                String username = usernameTextField.getText().toLowerCase();//Get username
                String password = passwordTextField.getText();//Get password

                //Validates that username/password exists and that they are valid in syntax
                if (username != null && !username.isEmpty() && password != null && !password.isEmpty() && userManager.login(username, password)){
                    try {
                        Session.currentUser = new User(username, password);//Set the current user to a new instance of the User class
                        SceneLoader.swapScene("Home-Screen.fxml", "Home");
                    }
                    catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                else{
                    accountNotFoundLabel.setText("Account not found!");//Display error if account is not found
                }
            }
        });
    }
}
