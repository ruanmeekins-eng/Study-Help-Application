package com.example.studyhelpapp;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

public class Main extends Application {
    private static Stage primaryStage;
    public static void main(String[] args) {

        launch(args);
    }

    public static Stage getPrimaryStage() {return primaryStage;}

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        primaryStage.setTitle("Login");
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Login-Screen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 450);
        stage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
