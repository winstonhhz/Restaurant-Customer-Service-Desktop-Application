package com.example.restaurantcustomerservice.main;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/restaurantcustomerservice/view/hello-view.fxml")));

        Scene scene = new Scene(root);

        stage.setTitle("Restaurant Customer Service System");
        stage.setMinHeight(550);
        stage.setMinWidth(600);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}