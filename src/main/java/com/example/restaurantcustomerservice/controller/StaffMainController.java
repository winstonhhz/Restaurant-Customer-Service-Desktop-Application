/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.restaurantcustomerservice.controller;

import com.example.restaurantcustomerservice.model.SessionHolder;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Hello
 */


public class StaffMainController {
    
    
    @FXML  
    public void manageReservationBtn(ActionEvent e) throws IOException{
        Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
        String path = "/com/example/restaurantcustomerservice/view/StaffReservationPanel.fxml";
        stageSetup(stage, path, "Staff Reservation Panel");
    }
    public void manageTicketBtn(ActionEvent e) throws IOException{
        Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
        String path = "/com/example/restaurantcustomerservice/view/staffTicket.fxml";
        stageSetup(stage, path, "Staff Manage Ticket");
    }
    public void staffLogoutBtn(ActionEvent e) throws IOException{
        Stage stage = ((Stage) ((Node) e.getSource()).getScene().getWindow());
        String fxmlPath = "/com/example/restaurantcustomerservice/view/hello-view.fxml";
        
        SessionHolder.clear();
        stageSetup(stage, fxmlPath, "Restaurant Customer Service System");  
    }
    
    public void backToStaffMain(Stage stage) throws IOException{
        String fxmlPath = "/com/example/restaurantcustomerservice/view/staffMain.fxml";
        stageSetup(stage, fxmlPath, "Staff Main Page");  
        SessionHolder.clear();
        
        
    
    }
    
    public void stageSetup(Stage stage, String path, String x) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource(path));
        stage.setTitle(x);
        stage.setScene(new Scene(root)); 
        stage.show();  
    }
    
}
