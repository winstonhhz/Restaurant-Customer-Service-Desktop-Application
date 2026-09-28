/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
*/
package com.example.restaurantcustomerservice.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import com.example.restaurantcustomerservice.model.EmailSender;
import com.example.restaurantcustomerservice.model.Ticket;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import com.example.restaurantcustomerservice.model.ChatData;

/**
 *
 * @author Hello
 */


public class NewTicketController {
    
    private String CID;
    
    @FXML private TextField newTicketTitle;
    @FXML private TextArea newTicketDetails;
    
    
    private final Stage primaryStage = new Stage();
    
    public void setCustomerID(String x){
        this.CID = x;
    }
    
    
    public void newTicketCancelBtn(){
    
        System.out.println("Test");
        ((Stage) newTicketTitle.getScene().getWindow()).close();        
    }
    public void newTicketSubmitBtn(){
        System.out.println("Print print");
//        customerTicketController ctc = new customerTicketController();
        if (newTicketTitle.getText().equals("") || newTicketDetails.getText().equals("")) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Both Title and Details MUST be filled");
            alert.show();
        }                    
        else{
            int TID;
            ObservableList<Ticket> ob = (new CustomerTicketController()).uploadTicketFromFile(CID);
            if (!(ob.isEmpty())){
                TID = Integer.parseInt(ob.getLast().getID().substring(1));
                TID ++;
            }
            else {
                TID = 1;
            }
            
            LocalDateTime dt = LocalDateTime.now();
            String j = dt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            
            Ticket t = new Ticket(CID, "t" + TID, newTicketTitle.getText(), "Unsolved", j, "");
            writeIntoFile(t);
            
            // Create Json file for live chat
            createJSONFile(CID,"t"+TID);
            
            
            ((Stage) newTicketTitle.getScene().getWindow()).close();
            
            new Thread(() -> { // Without running it on a new thread cause crash.. Generic crash error , jakarta and Javafx run on different thread
                EmailSender es = new EmailSender(); 
                es.sendEmail(t.getStatus(), "");
            }).start();
            
            
        }
    }
    
    public void writeIntoFile(Ticket t){
        try (PrintWriter pw = new PrintWriter(new FileWriter("data/tickets.txt", true))){       
            pw.write(t.getCustomerID()+","+t.getID()+","+t.getTitle()+","+t.getStatus()+","
                    +t.getCreatedDate()+","+t.getSolvedDate()+"\n");
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void createJSONFile(String customerID, String ticketID){
        String FilePath = "data/chatfile/livechat_" + customerID + ticketID + ".json";
        File ChatFile = new File(FilePath);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        
        if (!ChatFile.exists()){
            try (FileWriter fw = new FileWriter(ChatFile)){
                ArrayList<ChatData> init = new ArrayList<>();            
                ChatData first = new ChatData("Customer", getTimestamp(), newTicketDetails.getText());
                init.add(first);
                
                gson.toJson(init, fw);
            } catch (IOException e){
                System.out.println("Error: " + e);
            }
        }
    }
    
    public String getTimestamp(){
        return LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
    }
    
    public void show(Parent root){
  
        primaryStage.setTitle("Create New Ticket");
        primaryStage.setScene(new Scene(root, 600, 550)); 
        primaryStage.initModality(Modality.APPLICATION_MODAL); // block other window
        primaryStage.show();
        
    }
}
