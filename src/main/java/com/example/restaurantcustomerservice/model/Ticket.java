package com.example.restaurantcustomerservice.model;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Hello
 */
public class Ticket {
    
    private String customerID;
    private String ticketID;
    private String ticketTitle;
    private String status;
    private String createdDate;
    private String solvedDate;
    
    public Ticket(String customerID, String id, String title, String status, String createdDate, String solvedDate ){
        
        this.customerID = customerID;
        this.ticketID = id;
        this.ticketTitle = title;
        this.status = status;
        this.createdDate = createdDate;
        this.solvedDate = solvedDate;
    
    }

    // Getter
    public String getCustomerID(){return this.customerID;}
    public String getID(){return this.ticketID;}
    public String getTitle(){return this.ticketTitle;}
    public String getStatus(){return this.status;}
    public String getCreatedDate(){return this.createdDate;}
    public String getSolvedDate(){return this.solvedDate;} 
    // Setter
    public void setSolvedDate(String x){this.solvedDate = x;}
    
    
}
