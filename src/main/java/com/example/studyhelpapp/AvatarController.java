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

    // Brown Hair
    @FXML
    private Button buyButton;

    @FXML
    private ImageView BrownGirlHairEquip;

    private boolean brownHairOwned = false;
    private boolean brownHairEquipped = false;

    // Glasses
    @FXML
    private ImageView GlassesEquip;

    @FXML
    private Button GlassesButton;

    private boolean glassesOwned = false;
    private boolean glassesEquipped = false;


    Hashtable<String, Boolean> shopItems = new Hashtable<>();



    // Birthday Hat
    @FXML
    private ImageView BdayHatEquip;

    @FXML
    private Button BdayHatButton;

    private boolean bdayHatOwned = false;
    private boolean bdayHatEquipped = false;

    // Brown Boy Hair
    @FXML
    private ImageView BrownBoyHairEquip;

    @FXML
    private Button BrownBoyHairButton;

    private boolean brownBoyHairOwned = false;
    private boolean brownBoyHairEquipped = false;

    // Flower Clip
    @FXML
    private ImageView FlowerClipEquip;

    @FXML
    private Button FlowerClipButton;

    private boolean flowerClipOwned = false;
    private boolean flowerClipEquipped = false;

    // Pearl Necklace
    @FXML
    private ImageView PearlsEquip;

    @FXML
    private Button PearlsButton;

    private boolean pearlsOwned = false;
    private boolean pearlsEquipped = false;

    // Red Bow
    @FXML
    private ImageView RedBowEquip;

    @FXML
    private Button RedBowButton;

    private boolean redBowOwned = false;
    private boolean redBowEquipped = false;

    // Sunglasses
    @FXML
    private ImageView SunglassesEquip;

    @FXML
    private Button SunglassesButton;

    private boolean sunglassesOwned = false;
    private boolean sunglassesEquipped = false;

    // Buzzcut hair
    @FXML
    private ImageView BuzzCutHairEquip;

    @FXML
    private Button BuzzCutHairButton;

    private boolean buzzCutHairOwned = false;
    private boolean buzzCutHairEquipped = false;

    // Visor Glasses
    @FXML
    private ImageView VisorGlassesEquip;

    @FXML
    private Button VisorGlassesButton;

    private boolean visorGlassesOwned = false;
    private boolean visorGlassesEquipped = false;

    // UNCW Hat
    @FXML
    private ImageView UncwHatEquip;

    @FXML
    private Button UncwHatButton;

    private boolean uncwHatOwned = false;
    private boolean uncwHatEquipped = false;

    // Iphone
    @FXML
    private ImageView IphoneEquip;

    @FXML
    private Button IphoneButton;

    private boolean iphoneOwned = false;
    private boolean iphoneEquipped = false;

    // Sets up event handlers

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

    // Home button
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


    public void getOwnedItems() {
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

    // Birthday Hat
    @FXML
    public void handleBdayHatButton() {

        if (!bdayHatOwned) {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Confirm Purchase");
            confirm.setHeaderText("Buy Birthday Hat?");
            confirm.setContentText("Are you sure you want to buy this item?");

            Optional<ButtonType> result = confirm.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                bdayHatOwned = true;
                bdayHatEquipped = true;

                BdayHatEquip.setVisible(true);
                BdayHatButton.setText("Unequip");
            }

        } else {
            if (bdayHatEquipped) {
                bdayHatEquipped = false;
                BdayHatEquip.setVisible(false);
                BdayHatButton.setText("Equip");
            } else {
                bdayHatEquipped = true;
                BdayHatEquip.setVisible(true);
                BdayHatButton.setText("Unequip");
            }

        }
    }
    // Brown Boy Hair
    @FXML
    public void handleBrownBoyHairButton() {

        if (!brownBoyHairOwned) {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Confirm Purchase");
            confirm.setHeaderText("Buy Brown Hair?");
            confirm.setContentText("Are you sure you want to buy this item?");

            Optional<ButtonType> result = confirm.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                brownBoyHairOwned = true;
                brownBoyHairEquipped = true;

                BrownBoyHairEquip.setVisible(true);
                BrownBoyHairButton.setText("Unequip");
            }

        } else {
            if (brownBoyHairEquipped) {
                brownBoyHairEquipped = false;
                BrownBoyHairEquip.setVisible(false);
                BrownBoyHairButton.setText("Equip");
            } else {
                brownBoyHairEquipped = true;
                BrownBoyHairEquip.setVisible(true);
                BrownBoyHairButton.setText("Unequip");
            }

        }
    }
    // Flower Clip
    @FXML
    public void handleFlowerClipButton() {

        if (!flowerClipOwned) {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Confirm Purchase");
            confirm.setHeaderText("Buy Flower Clip?");
            confirm.setContentText("Are you sure you want to buy this item?");

            Optional<ButtonType> result = confirm.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                flowerClipOwned = true;
                flowerClipEquipped = true;

                FlowerClipEquip.setVisible(true);
                FlowerClipButton.setText("Unequip");
            }

        } else {
            if (flowerClipEquipped) {
                flowerClipEquipped = false;
                FlowerClipEquip.setVisible(false);
                FlowerClipButton.setText("Equip");
            } else {
                flowerClipEquipped = true;
                FlowerClipEquip.setVisible(true);
               FlowerClipButton.setText("Unequip");
            }

        }
    }
    // Pearl Necklace
    @FXML
    public void handlePearlsButton() {

        if (!pearlsOwned) {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Confirm Purchase");
            confirm.setHeaderText("Buy Pearl Necklace?");
            confirm.setContentText("Are you sure you want to buy this item?");

            Optional<ButtonType> result = confirm.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                pearlsOwned = true;
                pearlsEquipped = true;

                PearlsEquip.setVisible(true);
                PearlsButton.setText("Unequip");
            }

        } else {
            if (pearlsEquipped) {
                pearlsEquipped = false;
                PearlsEquip.setVisible(false);
                PearlsButton.setText("Equip");
            } else {
                pearlsEquipped = true;
                PearlsEquip.setVisible(true);
                PearlsButton.setText("Unequip");
            }

        }
    }
    // Red Bow
    @FXML
    public void handleRedBowButton() {

        if (!redBowOwned) {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Confirm Purchase");
            confirm.setHeaderText("Buy Red Bow?");
            confirm.setContentText("Are you sure you want to buy this item?");

            Optional<ButtonType> result = confirm.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                redBowOwned = true;
                redBowEquipped = true;

                RedBowEquip.setVisible(true);
                RedBowButton.setText("Unequip");
            }

        } else {
            if (redBowEquipped) {
                redBowEquipped = false;
                RedBowEquip.setVisible(false);
                RedBowButton.setText("Equip");
            } else {
                redBowEquipped = true;
                RedBowEquip.setVisible(true);
                RedBowButton.setText("Unequip");
            }

        }
    }
    // Sunglasses
    @FXML
    public void handleSunglassesButton() {

        if (!sunglassesOwned) {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Confirm Purchase");
            confirm.setHeaderText("Buy Sunglasses?");
            confirm.setContentText("Are you sure you want to buy this item?");

            Optional<ButtonType> result = confirm.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                sunglassesOwned = true;
                sunglassesEquipped = true;

                SunglassesEquip.setVisible(true);
                SunglassesButton.setText("Unequip");
            }

        } else {
            if (sunglassesEquipped) {
                sunglassesEquipped = false;
                SunglassesEquip.setVisible(false);
                SunglassesButton.setText("Equip");
            } else {
                sunglassesEquipped = true;
                SunglassesEquip.setVisible(true);
                SunglassesButton.setText("Unequip");
            }

        }
    }
    // Buzzcut hair
    @FXML
    public void handleBuzzCutHairButton() {

        if (!buzzCutHairOwned) {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Confirm Purchase");
            confirm.setHeaderText("Buy Buzzcut?");
            confirm.setContentText("Are you sure you want to buy this item?");

            Optional<ButtonType> result = confirm.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                buzzCutHairOwned = true;
                buzzCutHairEquipped = true;

                BuzzCutHairEquip.setVisible(true);
                BuzzCutHairButton.setText("Unequip");
            }

        } else {
            if (buzzCutHairEquipped) {
                buzzCutHairEquipped = false;
                BuzzCutHairEquip.setVisible(false);
                BuzzCutHairButton.setText("Equip");
            } else {
                buzzCutHairEquipped = true;
                BuzzCutHairEquip.setVisible(true);
                BuzzCutHairButton.setText("Unequip");
            }

        }
    }
    // Visor Glasses
    @FXML
    public void handleVisorGlassesButton() {

        if (!visorGlassesOwned) {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Confirm Purchase");
            confirm.setHeaderText("Buy Visor Glasses?");
            confirm.setContentText("Are you sure you want to buy this item?");

            Optional<ButtonType> result = confirm.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                visorGlassesOwned = true;
                visorGlassesEquipped = true;

                VisorGlassesEquip.setVisible(true);
                VisorGlassesButton.setText("Unequip");
            }

        } else {
            if (visorGlassesEquipped) {
                visorGlassesEquipped = false;
                VisorGlassesEquip.setVisible(false);
                VisorGlassesButton.setText("Equip");
            } else {
                visorGlassesEquipped = true;
                VisorGlassesEquip.setVisible(true);
                VisorGlassesButton.setText("Unequip");
            }

        }
    }
    // UNCW Hat
    @FXML
    public void handleUncwHatButton() {

        if (!uncwHatOwned) {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Confirm Purchase");
            confirm.setHeaderText("Buy UNCW Hat?");
            confirm.setContentText("Are you sure you want to buy this item?");

            Optional<ButtonType> result = confirm.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                uncwHatOwned = true;
                uncwHatEquipped = true;

                UncwHatEquip.setVisible(true);
                UncwHatButton.setText("Unequip");
            }

        } else {
            if (uncwHatEquipped) {
                uncwHatEquipped = false;
                UncwHatEquip.setVisible(false);
                UncwHatButton.setText("Equip");
            } else {
                uncwHatEquipped = true;
                UncwHatEquip.setVisible(true);
                UncwHatButton.setText("Unequip");
            }

        }
    }
    // Iphone
    @FXML
    public void handleIphoneButton() {

        if (!iphoneOwned) {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Confirm Purchase");
            confirm.setHeaderText("Buy Iphone?");
            confirm.setContentText("Are you sure you want to buy this item?");

            Optional<ButtonType> result = confirm.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                iphoneOwned = true;
                iphoneEquipped = true;

                IphoneEquip.setVisible(true);
                IphoneButton.setText("Unequip");
            }

        } else {
            if (iphoneEquipped) {
                iphoneEquipped = false;
                IphoneEquip.setVisible(false);
                IphoneButton.setText("Equip");
            } else {
                iphoneEquipped = true;
                IphoneEquip.setVisible(true);
                IphoneButton.setText("Unequip");
            }

        }
    }
}
