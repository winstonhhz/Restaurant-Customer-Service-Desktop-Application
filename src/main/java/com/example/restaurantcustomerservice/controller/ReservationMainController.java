/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.restaurantcustomerservice.controller;

/**
 *
 * @author User
 */
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.Node;

public class ReservationMainController {

    @FXML
    private void handleBack(ActionEvent event) throws Exception {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        new CustomerMainController().switchToMain(stage);
    }

    @FXML
    private void goToCustomer(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/restaurantcustomerservice/view/CustomerPanel.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();    
    }
    
    @FXML
    private void goToCheckStatus(ActionEvent event) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("/com/example/restaurantcustomerservice/view/CheckStatus.fxml"));
    Stage stage = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
    stage.setScene(new Scene(root));
    }   
}


