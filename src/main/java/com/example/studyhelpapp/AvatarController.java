package com.example.studyhelpapp;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.util.Optional;

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
    public void initialize() {
        setupEventHandlers();

        BrownGirlHairEquip.setVisible(false);
        buyButton.setText("Buy");
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

    @FXML
    public void handleBrownHairButton() {

        if (!brownHairOwned) {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Confirm Purchase");
            confirm.setHeaderText("Buy Brown Hair?");
            confirm.setContentText("Are you sure you want to buy this item?");

            Optional<ButtonType> result = confirm.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                brownHairOwned = true;
                brownHairEquipped = true;

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
}
