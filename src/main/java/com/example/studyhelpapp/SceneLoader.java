package com.example.studyhelpapp;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneLoader {

    public static void swapScene(String sceneName, String sceneTitle) throws IOException {

        FXMLLoader loader = new FXMLLoader(SceneLoader.class.getResource(sceneName));
        Parent root = loader.load();
        Scene newScene = new Scene(root, 600, 450);

        Stage stage = Main.getPrimaryStage();
        stage.setScene(newScene);
        stage.setTitle(sceneTitle);
        stage.show();

    }
}
