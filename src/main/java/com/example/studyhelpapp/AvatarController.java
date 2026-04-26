package com.example.studyhelpapp;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class AvatarController {

    @FXML
    private Button homeButton;

    @FXML
    private Button buyButton;

    @FXML
    private ImageView BrownGirlHairEquip;

    private boolean brownHairOwned = false;
    private boolean brownHairEquipped = false;

    @FXML
    private ImageView GlassesEquip;

    @FXML
    private Button GlassesButton;

    private boolean glassesOwned = false;
    private boolean glassesEquipped = false;

    Hashtable<String, Boolean> shopItems = new Hashtable<>();


    @FXML
    public void initialize() {
        setupEventHandlers();
        shopItems.put("brownHair", false);
        shopItems.put("glasses", false);
        getOwnedItems();
        if (shopItems.get("brownHair") == true){
            brownHairOwned = true;
            buyButton.setText("Equip");
        }
        else{
            buyButton.setText("Buy");
        }
        if (shopItems.get("glasses") == true){
            glassesOwned = true;
            GlassesButton.setText("Equip");
        }
        else{
            GlassesButton.setText("Buy");
        }
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
    }
    AvatarManager avatarManager = new AvatarManager();
    // Brown Hair item
    @FXML
    public void handleBrownHairButton() {

        if (!brownHairOwned) {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Confirm Purchase");
            confirm.setHeaderText("Buy Brown Hair?");
            confirm.setContentText("Are you sure you want to buy this item?");

            Optional<ButtonType> result = confirm.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                avatarManager.saveAvatar(Session.currentUser.getUsername(), "brownHair");
                getOwnedItems();

                BrownGirlHairEquip.setVisible(true);
                buyButton.setText("Unequip");

                Alert success = new Alert(Alert.AlertType.INFORMATION);
                success.setHeaderText(null);
                success.setContentText("Item purchased and equipped!");
                success.showAndWait();
            }

        } else {
            if (brownHairEquipped) {
                brownHairEquipped = false;
                BrownGirlHairEquip.setVisible(false);
                buyButton.setText("Equip");
            } else {
                brownHairEquipped = true;
                BrownGirlHairEquip.setVisible(true);
                buyButton.setText("Unequip");
            }
        }
    }

    // glasses item
    @FXML
    public void handleGlassesButton() {
        
        if (!glassesOwned) {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Confirm Purchase");
            confirm.setHeaderText("Buy Glasses?");
            confirm.setContentText("Are you sure you want to buy this item?");

            Optional<ButtonType> result = confirm.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                avatarManager.saveAvatar(Session.currentUser.getUsername(), "glasses");
                glassesOwned = true;
                glassesEquipped = true;

                GlassesEquip.setVisible(true);
                GlassesButton.setText("Unequip");
            }

        } else {
            if (glassesEquipped) {
                glassesEquipped = false;
                GlassesEquip.setVisible(false);
                GlassesButton.setText("Equip");
            } else {
                glassesEquipped = true;
                GlassesEquip.setVisible(true);
                GlassesButton.setText("Unequip");
            }
        }
    }

    public void getOwnedItems(){
        String username = Session.currentUser.getUsername();

        try (BufferedReader br = new BufferedReader(new FileReader("users/" + username + "/" + username + "Avatar.txt"))) {

            String line;
            while ((line = br.readLine()) != null) {
                shopItems.put(line, true);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
