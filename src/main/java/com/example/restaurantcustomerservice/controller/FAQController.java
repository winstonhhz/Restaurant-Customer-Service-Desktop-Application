/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.restaurantcustomerservice.controller;

/**
 *
 * @author User
 */
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FAQController implements Initializable {

    @FXML private TextArea faqDisplayArea;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadFAQsToDisplay();
    }

    public void loadFAQsToDisplay() {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader("data/faq_data.txt"))) {
            String question;
            while ((question = reader.readLine()) != null) {
                String answer = reader.readLine();
                reader.readLine(); // skip blank
                content.append("Question: ").append(question).append("\n");
                content.append("Answer: ").append(answer).append("\n\n");
            }
        } catch (IOException e) {
            faqDisplayArea.setText("Unable to load FAQs.");
        }
        faqDisplayArea.setText(content.toString());
    }

    @FXML
    public void goBack() throws IOException, Exception {
        Stage stage = (Stage) faqDisplayArea.getScene().getWindow();
        new CustomerMainController().switchToMain(stage);

    }
}




