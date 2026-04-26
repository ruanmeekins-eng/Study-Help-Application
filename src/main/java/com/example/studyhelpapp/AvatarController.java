package com.example.studyhelpapp;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.util.*;

public class AvatarController {

    @FXML
    private Button homeButton;

    // Buttons for avatar items
    @FXML private Button BrownHairButton;
    @FXML private Button GlassesButton;
    @FXML private Button BdayHatButton;
    @FXML private Button BrownBoyHairButton;
    @FXML private Button FlowerClipButton;
    @FXML private Button PearlsButton;
    @FXML private Button RedBowButton;
    @FXML private Button SunglassesButton;
    @FXML private Button BuzzCutHairButton;
    @FXML private Button VisorGlassesButton;
    @FXML private Button UncwHatButton;
    @FXML private Button IphoneButton;

    // ImageViews that show equipped items
    @FXML private ImageView BrownGirlHairEquip;
    @FXML private ImageView GlassesEquip;
    @FXML private ImageView BdayHatEquip;
    @FXML private ImageView BrownBoyHairEquip;
    @FXML private ImageView FlowerClipEquip;
    @FXML private ImageView PearlsEquip;
    @FXML private ImageView RedBowEquip;
    @FXML private ImageView SunglassesEquip;
    @FXML private ImageView BuzzCutHairEquip;
    @FXML private ImageView VisorGlassesEquip;
    @FXML private ImageView UncwHatEquip;
    @FXML private ImageView IphoneEquip;

    @FXML
    private Label pointLabel;

    //Stores whether items are equipped
    private Map<String, Boolean> equippedItems = new HashMap<>();

    //Stores which items the user owns
    private Set<String> ownedItems = new HashSet<>();

    //Maps item names to buttons and images
    private Map<String, Button> buttonMap = new HashMap<>();
    private Map<String, ImageView> imageMap = new HashMap<>();

    //Managers for saving/loading data
    private UserManager userManager = new UserManager();
    private AvatarManager avatarManager = new AvatarManager();

    //Current user info
    private String username = Session.currentUser.getUsername();
    private int userPoints = Session.currentUser.getPoint();

    @FXML
    public void initialize() {

        setupMaps();//Link items to UI elements
        setupEventHandlers(); //Hook up button clicks

        pointLabel.setText("Points: " + userPoints);

        //Load saved avatar data
        Map<String, Boolean> saved = avatarManager.loadAvatarState(username);

        for (String item : saved.keySet()) {
            ownedItems.add(item);
            equippedItems.put(item, saved.get(item));
        }

        updateUI(); //Refresh screen
    }

    //Connect item names to buttons and images
    private void setupMaps() {

        buttonMap.put("brownHair", BrownHairButton);
        buttonMap.put("glasses", GlassesButton);
        buttonMap.put("bdayHat", BdayHatButton);
        buttonMap.put("brownBoyHair", BrownBoyHairButton);
        buttonMap.put("flowerClip", FlowerClipButton);
        buttonMap.put("pearls", PearlsButton);
        buttonMap.put("redBow", RedBowButton);
        buttonMap.put("sunglasses", SunglassesButton);
        buttonMap.put("buzzCut", BuzzCutHairButton);
        buttonMap.put("visorGlasses", VisorGlassesButton);
        buttonMap.put("uncwHat", UncwHatButton);
        buttonMap.put("iphone", IphoneButton);

        imageMap.put("brownHair", BrownGirlHairEquip);
        imageMap.put("glasses", GlassesEquip);
        imageMap.put("bdayHat", BdayHatEquip);
        imageMap.put("brownBoyHair", BrownBoyHairEquip);
        imageMap.put("flowerClip", FlowerClipEquip);
        imageMap.put("pearls", PearlsEquip);
        imageMap.put("redBow", RedBowEquip);
        imageMap.put("sunglasses", SunglassesEquip);
        imageMap.put("buzzCut", BuzzCutHairEquip);
        imageMap.put("visorGlasses", VisorGlassesEquip);
        imageMap.put("uncwHat", UncwHatEquip);
        imageMap.put("iphone", IphoneEquip);
    }

    //Update button text show/hide images based on state
    private void updateUI() {

        for (String item : buttonMap.keySet()) {

            Button btn = buttonMap.get(item);
            ImageView img = imageMap.get(item);

            //If user owns item
            if (ownedItems.contains(item)) {

                boolean equipped = equippedItems.getOrDefault(item, false);

                if (equipped) {
                    btn.setText("Unequip");
                } else {
                    btn.setText("Equip");
                }
                img.setVisible(equipped);

            } else {
                //If not owned show price
                btn.setText("100");
                img.setVisible(false);
            }
        }
    }

    //Handles buying and equipping items
    private void handleItem(String item) {

        //If user does not own item yet
        if (!ownedItems.contains(item)) {

            if (userPoints > 100) {

                Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                confirm.setHeaderText("Buy " + item + "?");
                confirm.setContentText("Are you sure?");

                Optional<ButtonType> result = confirm.showAndWait();

                if (result.isPresent() && result.get() == ButtonType.OK) {

                    ownedItems.add(item);
                    equippedItems.put(item, true);

                    //Deduct points
                    Session.currentUser.setPoint(userPoints - 100);
                    userPoints = Session.currentUser.getPoint();

                    pointLabel.setText("Points: " + userPoints);

                    //Save to user file
                    userManager.updateUser(username, Session.currentUser.getPassword(), userPoints);
                }
            }

        } else {
            //Set equip or unequip
            boolean equipped = equippedItems.getOrDefault(item, false);
            equippedItems.put(item, !equipped);
        }

        //Save avatar state
        avatarManager.saveAvatarState(username, equippedItems);

        updateUI();
    }

    //Connect each button to its action
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

        BrownHairButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                handleItem("brownHair");
            }
        });

        GlassesButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                handleItem("glasses");
            }
        });

        BdayHatButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                handleItem("bdayHat");
            }
        });

        BrownBoyHairButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                handleItem("brownBoyHair");
            }
        });

        FlowerClipButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                handleItem("flowerClip");
            }
        });

        PearlsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                handleItem("pearls");
            }
        });

        RedBowButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                handleItem("redBow");
            }
        });

        SunglassesButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                handleItem("sunglasses");
            }
        });

        BuzzCutHairButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                handleItem("buzzCut");
            }
        });

        VisorGlassesButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                handleItem("visorGlasses");
            }
        });

        UncwHatButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                handleItem("uncwHat");
            }
        });

        IphoneButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                handleItem("iphone");
            }
        });
    }
}