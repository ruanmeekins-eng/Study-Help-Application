package com.example.studyhelpapp;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

/***
 * Controller for Create Account Screen
 *
 * Manages the creation, validation, and saving of a new
 * user account.
 */
public class CreateAccountController {

    //Creates a new account and swaps screens
    @FXML
    private Button createAccountButton;

    //Goes back to Home Screen
    @FXML
    private Button backButton;

    //Receives input from the user to create username
    @FXML
    private TextField createUsernameTextField;

    //Receives input from the user to create password
    @FXML
    private TextField createPasswordTextField;

    //Outputs error if username or password are invalid in their syntax
    @FXML
    private Label invalidAccountLabel;

    /***
     * Initializes the controller after the FXML is loaded.
     */
    @FXML
    public void initialize(){
        setupEventHandlers();
    }
    //Manager used to access the saving and loading methods of the UserManager
    UserManager userManager = new UserManager();

    @FXML
    private void setupEventHandlers() {

        //Handles creating a new account
        createAccountButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                String username = createUsernameTextField.getText().toLowerCase();//Get the username
                String password = createPasswordTextField.getText();//Get the password

                //Checks that no username or password is empty or contains spaces
                if (!username.isEmpty() && password != null && !password.isEmpty() && !username.contains(" ") && userManager.login(username, password) == null) {
                    try {
                        User user = new User(username, password, 0);//Create new instance of user from the data
                        userManager.saveUser(username, password, 0);//Saves the user information to respected directory
                        Session.currentUser = user;//Sets the current user to this instance of user
                        SceneLoader.swapScene("Home-Screen.fxml", "Create Account");

                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                else if (userManager.login(username, password) != null){
                    invalidAccountLabel.setText("Username is taken!");
                }
                else{
                    invalidAccountLabel.setText("Invalid username/password!");
                }
            }
        });

        //Handles switching back to Login Screen
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
