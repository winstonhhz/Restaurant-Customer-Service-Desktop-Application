package com.example.restaurantcustomerservice.controller;

import com.example.restaurantcustomerservice.model.loginModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CustomerDashboardController {

    @FXML
    private Label lblUsername;

    @FXML
    private TableView<loginModel> tableCustomerInfo;

    @FXML
    private TableColumn<loginModel, Integer> colId;

    @FXML
    private TableColumn<loginModel, String> colUsername;

    @FXML
    private TableColumn<loginModel, String> colQuestion;

    private final String URL = "jdbc:mysql://127.0.0.1:3306/login_schema";
    private final String USER = "username"; // Enter your own MYSQL username
    private final String PASSWORD = "password"; // Enter your own MYSQL password

    private String username;

    public void setCustomerData(int id, String username, String question) {
        this.username = username;
        lblUsername.setText(username);

        ObservableList<loginModel> customerInfo = FXCollections.observableArrayList();
        customerInfo.add(new loginModel(id, username, null, question, null));
        tableCustomerInfo.setItems(customerInfo);
    }

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("idlogin"));
        colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        colQuestion.setCellValueFactory(new PropertyValueFactory<>("question"));
    }

    @FXML
    private void handleLogout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/restaurantcustomerservice/view/hello-view.fxml"));
            StackPane root = loader.load();

            Stage stage = (Stage) lblUsername.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Login / Signup");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    public void handleBack(javafx.event.ActionEvent e){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/restaurantcustomerservice/view/customerMain.fxml"));
            javafx.scene.Parent root = loader.load();
            Stage stage = (Stage) lblUsername.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Customer Main");
            stage.show();
        } catch (IOException ex) {
            System.out.println("Error: " + ex);
        }
    
    }
    
}
