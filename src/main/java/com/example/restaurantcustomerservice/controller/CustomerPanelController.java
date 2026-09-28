/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.example.restaurantcustomerservice.controller;
/**
 * FXML Controller class
 *
 * @author User
 */
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.event.ActionEvent;

import java.io.*;
import java.util.UUID;

public class CustomerPanelController {

    @FXML private TextField nameField;
    @FXML private TextField emailField;
    @FXML private TextField phoneField;
    @FXML private TextField dateField;
    @FXML private TextField timeField;
    @FXML private TextField peopleField;
    @FXML private TextField requirementField;
    @FXML private TextArea resultArea;

    private final String filePath = "data/reservationdata.txt";

    @FXML
    private void handleSubmit() {
        if (!isInputValid()) {
            return;
        }

        String id = UUID.randomUUID().toString().substring(0, 5);
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();
        String date = dateField.getText().trim();
        String time = timeField.getText().trim();
        String people = peopleField.getText().trim();
        String requirement = requirementField.getText().trim();
        String status = "Waiting to be Seated";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(String.join(",", id, name, email, phone, date, time, people, requirement, status));
            writer.newLine();
        } catch (IOException e) {
            resultArea.setText("❌ Failed to save reservation.");
            e.printStackTrace();
            return;
        }

        resultArea.setText(
            "✅ You have successfully booked a table!\n\n" +
            "Remember to save your Reservation ID.\n\n" +
            "Reservation ID: " + id + "\n" +
            "Name: " + name + "\n" +
            "Date: " + date + "\n" +
            "Time: " + time + "\n" +
            "People: " + people + "\n" +
            "Requirement: " + requirement + "\n" +
            "Status: " + status + "\n\n" +
            "Please check the status of your reservation!!!"
        );

        clearFields();
    }

    private boolean isInputValid() {
        if (nameField.getText().trim().isEmpty() ||
            emailField.getText().trim().isEmpty() ||
            phoneField.getText().trim().isEmpty() ||
            dateField.getText().trim().isEmpty() ||
            timeField.getText().trim().isEmpty() ||
            peopleField.getText().trim().isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Missing Information");
            alert.setHeaderText(null);
            alert.setContentText("Please fill in all required fields before booking.");
            alert.show();
            return false;
        }
        return true;
    }

    private void clearFields() {
        nameField.clear();
        emailField.clear();
        phoneField.clear();
        dateField.clear();
        timeField.clear();
        peopleField.clear();
        requirementField.clear();
    }

    @FXML
    private void goBack(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/restaurantcustomerservice/view/ReservationMain.fxml"));
        Stage stage = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
