package com.example.restaurantcustomerservice.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainMenuController {
    @FXML private Button customerButton, adminButton, staffButton;
    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void initialize() {
        customerButton.setOnAction(e -> switchScene("/com/example/restaurantcustomerservice/view/customerMain.fxml"));
        adminButton.setOnAction(e -> switchScene("/com/example/restaurantcustomerservice/view/adminMain.fxml"));
        staffButton.setOnAction(e -> switchScene("/com/example/restaurantcustomerservice/view/staffMain.fxml"));
    }

    private void switchScene(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Object controller = loader.getController();
            if (controller instanceof CustomerViewController cvc) cvc.setStage(stage);
            if (controller instanceof AdminViewController avc) avc.setStage(stage);
//            if (controller instanceof  )
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}