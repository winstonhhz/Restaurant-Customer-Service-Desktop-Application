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
import javafx.scene.control.Button;
import javafx.stage.Stage;
import com.example.restaurantcustomerservice.ChatApp.ChatPane;

/**
 *
 * @author Hello
 */
public class CustomerMainController {
    
    
    @FXML
    
    public void switchToMyTicket(ActionEvent e) throws Exception{
        Stage stage = ((Stage) ((Node) e.getSource()).getScene().getWindow());
        String path = "/com/example/restaurantcustomerservice/view/customerTicket.fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        stage = stageSetup(stage, loader, "Customer Ticket"); 
        CustomerTicketController ctc = loader.getController();
        ctc.setCustomerID(Integer.toString(SessionHolder.getUserId()));
        stage.show();
        
    }
    
    public void switchToChatBot(ActionEvent e) throws Exception{
//        System.out.println("Not available yet");
          ChatPane chatPane = new ChatPane();
          Scene chatbotScene = new Scene(chatPane, 1000, 600);
          
          Stage forChatBot = new Stage();
          forChatBot.setTitle("Chatbot Assistant");
          forChatBot.setScene(chatbotScene);
          forChatBot.show();          

          
//        Stage stage = ((Stage) ((Node) e.getSource()).getScene().getWindow());
//        Parent root = FXMLLoader.load(getClass().getResource("/View/staffTicket.fxml")); 
//        stage.setTitle("Your personal ChatBot"); 
//        stageSetup(stage, root);
    }
    
    public void switchToReservation(ActionEvent e) throws Exception {        
        Stage stage = ((Stage) ((Node) e.getSource()).getScene().getWindow());
        String path = "/com/example/restaurantcustomerservice/view/ReservationMain.fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));        
        stage = stageSetup(stage, loader, "Reservation Main page"); 
        stage.show();
    }
    
    public void switchToDashboard(ActionEvent e) throws Exception{
//        System.out.println("Not available yet");
        Stage stage = ((Stage) ((Node) e.getSource()).getScene().getWindow());
        String path = "/com/example/restaurantcustomerservice/view/customerDashboard.fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path)); 
        stage = stageSetup(stage, loader, "Customer Dashboard");                
        CustomerDashboardController cdc = loader.getController();
        cdc.setCustomerData(SessionHolder.getUserId(), SessionHolder.getUsername(), SessionHolder.getQuestion()); 
        stage.show();      
    }
    
    public void switchToFAQ(ActionEvent e) throws Exception{
//        System.out.println("Not available yet");    
        Stage stage = ((Stage) ((Node) e.getSource()).getScene().getWindow());
        String path = "/com/example/restaurantcustomerservice/view/faq.fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));        
        stage = stageSetup(stage, loader, "Customer FAQ"); 
        stage.show();
        
    }
    public void switchToMain(Stage stage) throws Exception{
        String path = "/com/example/restaurantcustomerservice/view/customerMain.fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));        
        stage = stageSetup(stage, loader, "Customer Main");        
        stage.show();
    }
    public void switchToViewMenu(ActionEvent e) throws Exception{
        Stage stage = ((Stage) ((Node) e.getSource()).getScene().getWindow());
        String path = "/com/example/restaurantcustomerservice/view/CustomerView.fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));        
        stage = stageSetup(stage, loader, "View Menu");     
        stage.show();
    
    }
    public void handleLogout(ActionEvent e) throws IOException{
        Stage stage = ((Stage) ((Node) e.getSource()).getScene().getWindow());
        String path = "/com/example/restaurantcustomerservice/view/hello-view.fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));        
        stage = stageSetup(stage, loader, "Restaurant Customer Service System");     
        SessionHolder.clear();
        stage.show();
    
    
    }
    
    
    
    public Stage stageSetup(Stage stage, FXMLLoader loader, String x) throws IOException{
        Parent root = loader.load();
        stage.setTitle(x);
        stage.setScene(new Scene(root, 600, 550)); 
        return stage;
    }
    
}
