/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.restaurantcustomerservice.model;

/**
 *
 * @author Hello
 */
public class ChatData {
    private String sender;
    private String timestamp;
    private String message;
    
    public ChatData(String sender, String timestamp, String msg){
        this.sender = sender;
        this.timestamp = timestamp;
        this.message = msg;    
    }
    public String getSender(){return sender;}
    public String getTimestamp(){return timestamp;}
    public String getMessage(){return message;}
    
    public void setSender(String sender){this.sender = sender;};
    public void setTimestamp(String ts){this.timestamp = ts;};
    public void setMessage(String msg){this.message = msg;};
    
    
    
}
