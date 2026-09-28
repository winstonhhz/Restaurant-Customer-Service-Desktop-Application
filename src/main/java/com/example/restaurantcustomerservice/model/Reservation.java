/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.restaurantcustomerservice.model;

/**
 *
 * @author User
 */
public class Reservation {
    private String id;
    private String name;
    private String email;
    private String phone;
    private String date;
    private String time;
    private String people;
    private String requirement;
    private String status;

    // Constructor
    public Reservation(String id, String name, String email, String phone,
                       String date, String time, String people,
                       String requirement, String status) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.date = date;
        this.time = time;
        this.people = people;
        this.requirement = requirement;
        this.status = status;
    }

    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getDate() { return date; }
    public String getTime() { return time; }
    public String getPeople() { return people; }
    public String getRequirement() { return requirement; }
    public String getStatus() { return status; }

    // Setters
    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setDate(String date) { this.date = date; }
    public void setTime(String time) { this.time = time; }
    public void setPeople(String people) { this.people = people; }
    public void setRequirement(String requirement) { this.requirement = requirement; }
    public void setStatus(String status) { this.status = status; }
}



