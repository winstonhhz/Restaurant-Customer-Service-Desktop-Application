package com.example.restaurantcustomerservice.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.example.restaurantcustomerservice.model.MenuData;
import com.example.restaurantcustomerservice.model.MenuItem;

import java.util.List;

public class AdminMenuViewController {
    @FXML private VBox menuContainer;
    private Stage stage;

    public void setStage(Stage stage) {
        // Store stage reference for navigation        
        this.stage = stage;
    }

    @FXML
    public void initialize() {
        // Load and display menu by categories
        
        menuContainer.getChildren().clear();
        populateCategory("Starters");
        populateCategory("Mains");
        populateCategory("Desserts");
        populateCategory("Drinks");
    }

    private void populateCategory(String category) {
        // Populate a grid of menu item cards by category
        Label title = new Label(category);
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        GridPane grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(20);

        List<MenuItem> items = MenuData.getItems(category);
        for (int i = 0; i < items.size(); i++) {
            MenuItem item = items.get(i);

            VBox card = new VBox(5);
            card.setPrefWidth(150);
            card.setStyle("-fx-border-color: #ccc; -fx-padding: 10; -fx-background-color: #f4f4f4; -fx-alignment: center;");

            ImageView imageView;
            try {
                String filename = item.getName().toLowerCase().replace(" ", "_") + ".jpeg";
                imageView = new ImageView(new Image(getClass().getResourceAsStream("/images/" + filename)));
            } catch (Exception e) {
                imageView = new ImageView();
            }
            imageView.setFitWidth(120);
            imageView.setFitHeight(90);

            Label name = new Label(item.getName());
            Label price = new Label("$" + String.format("%.2f", item.getPrice()));

            card.getChildren().addAll(imageView, name, price);
            grid.add(card, i % 3, i / 3);
        }

        menuContainer.getChildren().addAll(title, grid);
    }

    @FXML
    private void handleBack() {
        // Go back to Admin input form
        try {
            stage = (javafx.stage.Stage) menuContainer.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/restaurantcustomerservice/view/AdminView.fxml"));
            Parent root = loader.load();
            AdminViewController controller = loader.getController();
            controller.setStage(stage);
            stage.setScene(new Scene(root, 600, 550));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}