package com.example.restaurantcustomerservice.controller;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import com.example.restaurantcustomerservice.model.ChatData;
//import com.example.restaurantcustomerservice.model.ChatIntoJson;
import com.example.restaurantcustomerservice.model.Ticket;

/**
 *
 * @author Hello
 */
public class LiveChatViewController implements Initializable{
    
    @FXML private VBox chatArea;
    @FXML private TextArea chatInput;
    @FXML private Button chatBtn;
    @FXML private ScrollPane chatScrollPane;
    @FXML private javafx.scene.control.TextField liveChatRoleTF;
    
    private String customerID;
    private String ticketID;
    private String chatFileName;
    private String initiator;
    private ArrayList<ChatData> chatHistory = new ArrayList<>();
    private Timeline refreshTimeline;
    private int lastLoadedSize = 0; // This variable keep track of how many message showed
    private Stage primaryStage;
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    
    public void loadData(String customerID, String ticketID, String initiator){
        this.customerID = customerID;
        this.ticketID = ticketID;
        this.chatFileName = "data/chatfile/livechat_" + customerID + ticketID + ".json";
        this.initiator = initiator;
        chatHistory = loadChat(chatFileName);
        
        for (ChatData c : chatHistory){
            appendMsgToUI(c);
        }
        
        liveChatRoleTF.setText(initiator);
        lastLoadedSize = chatHistory.size();
        chatScrollPane.setVvalue(1.0); // Auto scroll toward the bottom of the chat
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        chatBtn.setOnAction(e ->{
            String msg = chatInput.getText();
            if (!(msg.isEmpty() || msg.isBlank())){
                ChatData cd = new ChatData(initiator, getTimestamp(), msg);
                appendMsgToUI(cd);
                appendMessage(chatFileName, cd);
                lastLoadedSize ++;
                
                chatInput.clear();
                stayAtBottom(); // Move toward the bottom of the scrollpane
                
            }

        });
        
    }

    public void handleBack(){
        primaryStage.close();
    }
    
    public void appendMsgToUI(ChatData msg){
        Label msgLabel = new Label("[" + msg.getTimestamp() + "] " + msg.getSender() + ": " + msg.getMessage());
        msgLabel.setWrapText(true);
        msgLabel.setStyle("-fx-padding: 5; -fx-background-radius: 5;");
        msgLabel.setStyle((msg.getSender().equals("Customer")) ? "-fx-background-color: #e0e0e0;" : "-fx-background-color: #C8E6C9");
        chatArea.getChildren().add(msgLabel);

    }
    
//    public void saveChat(){
//        try (FileWriter fw = new FileWriter(new File(chatFileName))){
//            
//            
//        } catch (IOException e){
//            System.out.println("Error: " + e);
//        }
//    
//    
//    }
    
    public ArrayList<ChatData> loadChat(String filepath){
        File file = new File(filepath);
        if (!file.exists())
            return new ArrayList<>();
        try (FileReader reader = new FileReader(file)){
            Type arrayListType = new TypeToken<ArrayList<ChatData>>(){}.getType();
            return gson.fromJson(reader, arrayListType);
        
        } catch (IOException e){
            System.out.println("Error: " + e);
            return new ArrayList<>();
        }
        
    }
    
    public void appendMessage(String filepath, ChatData chatdata){
        ArrayList<ChatData> data = loadChat(filepath);
        data.add(chatdata);
        
        try(FileWriter fw = new FileWriter(filepath)){
            gson.toJson(data, fw);
        
        } catch (IOException e){
            System.out.println("Error: " + e);
        }
    
    }
    
    public void autoRefresh(){
        refreshTimeline = new Timeline(new KeyFrame(Duration.seconds(2), e -> {
            ArrayList<ChatData> current = loadChat(chatFileName); // Load chatdata obj into arraylist
            if (current.size() > lastLoadedSize){
                for (int i = lastLoadedSize; i < current.size(); i ++){
                    ChatData cd = current.get(i);
                    appendMsgToUI(cd); // Append messgae to the vbox, so that the receiver
                                       // will receive new messages every 2 second
                }
            }
            lastLoadedSize = current.size(); // Update size tracker
            stayAtBottom(); 
            
        }));
        refreshTimeline.setCycleCount(Timeline.INDEFINITE); // this code makes it loop forever
        refreshTimeline.play(); // starts the auto-refresh
    
    }
    
    public void stayAtBottom(){
        Platform.runLater(() -> { 
            double scrollPaneVal = chatScrollPane.getVvalue();
            if (scrollPaneVal > 0.9){
                chatScrollPane.setVvalue(1.0);   // Had to place this inside runLater so that it'll execute after jfx layout done
            }
        });
        
    }
    
    
    
    public String getTimestamp(){
        return LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
    }
    
    public void show(Parent root){
      
            primaryStage = new Stage();
            primaryStage.setTitle("Live Chat");
            primaryStage.setScene(new Scene(root)); 
            primaryStage.initModality(Modality.APPLICATION_MODAL); // block other window
            primaryStage.show();
        
    }   
    
}
