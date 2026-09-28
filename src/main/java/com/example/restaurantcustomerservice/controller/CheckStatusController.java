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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;

import java.io.*;

public class CheckStatusController {

    @FXML private TextField nameField;
    @FXML private TextArea resultArea;

    private final String filePath = "data/reservationdata.txt";

    @FXML
    private void handleCheckStatus() {
        String enteredName = nameField.getText().trim().toLowerCase();
        if (enteredName.isEmpty()) {
            resultArea.setText("❗ Please enter your name.");
            return;
        }

        boolean found = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while((line = reader.readLine()) != null) {
                String[] parts = line.split(",", -1);
                if (parts.length >= 9 && parts[1].toLowerCase().equals(enteredName)) {
                    
                    if (parts.length < 9) {
                        continue; // Skip malformed line
                    }
                    found = true;
                    String status = parts[8];
                    String msg = status.equalsIgnoreCase("Ready to be Seated")
                            ? "✅ Your table is ready!"
                            : "⌛ Please wait, your table is not ready yet.";
                    resultArea.setText(
                            "Reservation Found:\n\n" +
                            "Name: " + parts[1] + "\n" +
                            "Date: " + parts[4] + "\n" +
                            "Time: " + parts[5] + "\n" +
                            "Status: " + status + "\n\n" +
                            msg
                    );
                    break;
                }
            }
        } catch (IOException e) {
            resultArea.setText("❗ Error reading reservation file.");
        }

        if (!found) {
            resultArea.setText("❌ No reservation found under name: " + enteredName);
        }
    }

    @FXML
    private void handleBack(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/restaurantcustomerservice/view/ReservationMain.fxml"));
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
    }
}
