/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.restaurantcustomerservice.model;

/**
 *
 * @author Hello
 */
public class SessionHolder {
    private static int userId;
    private static String username;
    private static String userType;
    private static String question;

    public static void set(String type, int id, String name, String q) {
        userType = type;
        userId = id;
        username = name;
        question = q;
    }

    public static int getUserId() { return userId; }
    public static String getUsername() { return username; }
    public static String getUserType() { return userType; }
    public static String getQuestion() { return question; }

    public static boolean isCustomer() {
        return "customer".equalsIgnoreCase(userType);
    }

    public static boolean isStaff() {
        return "staff".equalsIgnoreCase(userType);
    }

    public static boolean isAdmin() {
        return "admin".equalsIgnoreCase(userType);
    }

    public static void clear() {
        userId = -1;
        username = null;
        userType = null;
        question = null;
    }
}
