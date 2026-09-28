package com.example.restaurantcustomerservice.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import com.example.restaurantcustomerservice.model.MenuData;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class AdminViewController {
    @FXML private ComboBox<String> categoryBox;
    @FXML private TextField itemField, priceField;
    @FXML private Button addButton, updateButton, deleteButton, importImageButton, viewMenuButton;
    @FXML private Label statusLabel, imageStatusLabel;

    private File selectedImageFile;
    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void initialize() {
        // Initialize dropdown and button actions
        categoryBox.getItems().addAll("Starters", "Mains", "Desserts", "Drinks");

        importImageButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choose Image for Menu Item");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.jpeg", "*.png"));
            File file = fileChooser.showOpenDialog(stage);
            if (file != null) {
                selectedImageFile = file;
                imageStatusLabel.setText("Selected: " + file.getName());
            } else {
                imageStatusLabel.setText("No image selected");
            }
        });

        addButton.setOnAction(e -> {
            // Add a new item to the menu
            
            String category = categoryBox.getValue();
            String item = itemField.getText().trim();
            String priceText = priceField.getText().trim();

            if (category != null && !item.isEmpty() && !priceText.isEmpty()) {
                try {
                    double price = Double.parseDouble(priceText);
                    MenuData.addItem(category, item, price);
                    saveImageToResource(item);
                    statusLabel.setText("Item added successfully.");
                    itemField.clear();
                    priceField.clear();
                    selectedImageFile = null;
                    imageStatusLabel.setText("No image selected");
                } catch (NumberFormatException ex) {
                    statusLabel.setText("Invalid price.");
                }
            } else {
                statusLabel.setText("All fields are required.");
            }
        });

        updateButton.setOnAction(e -> {
            // Update existing item
            String category = categoryBox.getValue();
            String item = itemField.getText().trim();
            String priceText = priceField.getText().trim();

            if (category != null && !item.isEmpty() && !priceText.isEmpty()) {
                try {
                    double price = Double.parseDouble(priceText);
                    MenuData.getItems(category).removeIf(m -> m.getName().equals(item));
                    MenuData.addItem(category, item, price);
                    saveImageToResource(item);
                    statusLabel.setText("Item updated.");
                } catch (NumberFormatException ex) {
                    statusLabel.setText("Invalid price.");
                }
            } else {
                statusLabel.setText("Fill all fields to update.");
            }
        });

        deleteButton.setOnAction(e -> {
            // Delete selected item
            String category = categoryBox.getValue();
            String item = itemField.getText().trim();

            if (category != null && !item.isEmpty()) {
                boolean removed = MenuData.getItems(category).removeIf(m -> m.getName().equals(item));
                statusLabel.setText(removed ? "Item deleted." : "Item not found.");
            } else {
                statusLabel.setText("Select category and item to delete.");
            }
        });

        viewMenuButton.setOnAction(e -> {
            // Switch to admin menu preview screen
            try {
                stage = (javafx.stage.Stage)categoryBox.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/restaurantcustomerservice/view/AdminMenuView.fxml"));
                Parent root = loader.load();
                AdminMenuViewController controller = loader.getController();
                controller.setStage(stage);
                stage.setScene(new Scene(root, 600, 700));
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (Exception exc){
                exc.printStackTrace();
            }
        });
    }

    private void saveImageToResource(String itemName) {
        // Save the imported image to the images folder
        if (selectedImageFile == null) return;

        String fileName = itemName.toLowerCase().replace(" ", "_") + ".jpeg";
        Path dest = Paths.get("target/classes/images/" + fileName);

        try {
            Files.copy(selectedImageFile.toPath(), dest, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    public void handleBack() {
        // Return to the main menu
        try {
            this.stage = (Stage)categoryBox.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/restaurantcustomerservice/view/adminMain.fxml"));
            Parent root = loader.load();
//            MainMenuController controller = loader.getController();
//            controller.setStage(stage);
            stage.setScene(new Scene(root, 600, 550));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}