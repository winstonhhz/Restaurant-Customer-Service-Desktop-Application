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
public class AdminMainController {
    
    
    @FXML
    public void manageUserBtn(ActionEvent e) throws Exception{
        // Not done yet 
//        System.out.println("Not Available Yet");
        Stage stage = ((Stage) ((Node) e.getSource()).getScene().getWindow());
        String fxmlPath = "/com/example/restaurantcustomerservice/view/adminDashboard.fxml";
        stageSetup(stage, fxmlPath, "Manage User");

    }
    @FXML
    public void manageFAQBtn(ActionEvent e) throws Exception{
        // Not done yet
        System.out.println("Not Available Yet");        
        Stage stage = ((Stage) ((Node) e.getSource()).getScene().getWindow());
        String fxmlPath = "/com/example/restaurantcustomerservice/view/editfaq.fxml";
        stageSetup(stage, fxmlPath, "Admin FAQ Page");
         
     
    }
    @FXML
    public void manageMenuBtn(ActionEvent e) throws Exception{
        Stage stage = ((Stage) ((Node) e.getSource()).getScene().getWindow());
        String fxmlPath = "/com/example/restaurantcustomerservice/view/AdminView.fxml";
        stageSetup(stage, fxmlPath, "Manage Menu");     
  
    }
    @FXML
    public void manageLogoutBtn(ActionEvent e) throws Exception{
        Stage stage = ((Stage) ((Node) e.getSource()).getScene().getWindow());
        String fxmlPath = "/com/example/restaurantcustomerservice/view/hello-view.fxml";
        
        SessionHolder.clear();
        stageSetup(stage, fxmlPath, "Restaurant Customer Service System");         
    }
    
    
    public void switchToMain(Stage stage) throws Exception{
        // Not Complete yet
        String fxmlPath = "/com/example/restaurantcustomerservice/view/adminMain.fxml";
//        Parent root = FXMLLoader.load(getClass().getResource("/View/adminMain.fxml")); 
//        stage.setTitle("Admin Main"); 
        stageSetup(stage, fxmlPath, "Admin Main");   
    }
    public void stageSetup(Stage stage, String path, String x){
        try{
        Parent root = FXMLLoader.load(getClass().getResource(path));
        stage.setTitle(x);
        stage.setScene(new Scene(root)); 
        stage.show();
        } catch (IOException e){
            System.out.println("Error: " + e);
        }
    }
    
}
