package com.example.restaurantcustomerservice.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import com.example.restaurantcustomerservice.model.MenuData;
import com.example.restaurantcustomerservice.model.MenuItem;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.io.IOException;


import java.util.List;

public class CustomerViewController {
    @FXML private VBox menuContainer;
    private javafx.stage.Stage stage;

    public void setStage(javafx.stage.Stage stage) {
        
    }

    @FXML
    public void initialize() {
         // Initialize the customer view by populating categories
        menuContainer.getChildren().clear();
        populateCategory("Starters");
        populateCategory("Mains");
        populateCategory("Desserts");
        populateCategory("Drinks");
    }
    
    @FXML
    private void handleBack() {
        // Handle back button to return to the main menu
        try {
            stage = (javafx.stage.Stage) menuContainer.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/restaurantcustomerservice/view/customerMain.fxml"));
            Parent root = loader.load();
            CustomerMainController controller = loader.getController();
//            controller.setStage(stage);
            stage.setScene(new Scene(root, 600, 550));
            
//        Stage stage = ((Stage) ((Node) e.getSource()).getScene().getWindow());
//        Parent root = FXMLLoader.load(getClass().getResource("/View/customerTicket.fxml")); 
//        stage.setTitle("Customer Ticket"); 
//        stageSetup(stage, root); 
            
            
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception ex){
            System.out.println("Error: " + ex);
        }
    }

    private void populateCategory(String category) {
        // Populate each category with menu items
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
                // Load image based on item name
                String filename = item.getName().toLowerCase().replace(" ", "_") + ".jpeg";
                
                imageView = new ImageView(new Image(getClass().getResourceAsStream("/images/" + filename)));
//                imageView = new ImageView(new Image(getClass().getResourceAsStream("images/soup.jpeg")));
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
}