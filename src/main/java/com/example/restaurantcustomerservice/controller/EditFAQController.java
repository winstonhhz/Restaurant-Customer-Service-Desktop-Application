/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.restaurantcustomerservice.controller;

/**
 *
 * @author User
 */
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;
import com.example.restaurantcustomerservice.model.AdminFAQ;

public class EditFAQController implements Initializable {

    @FXML private TextArea questionField;
    @FXML private TextArea answerArea;
    @FXML private TableView<AdminFAQ> faqTable;
    @FXML private TableColumn<AdminFAQ, String> questionCol;
    @FXML private TableColumn<AdminFAQ, String> answerCol;

    private final ObservableList<AdminFAQ> faqList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        questionCol.setCellValueFactory(new PropertyValueFactory<>("question"));
        answerCol.setCellValueFactory(new PropertyValueFactory<>("answer"));
        faqTable.setItems(faqList);
        loadFAQsFromFile();

        // Load selected item into fields
        faqTable.setOnMouseClicked((MouseEvent event) -> {
            AdminFAQ selected = faqTable.getSelectionModel().getSelectedItem();
            if (selected != null) {
                questionField.setText(selected.getQuestion());
                answerArea.setText(selected.getAnswer());
            }
        });

        // Prevent Enter key in TextArea
        questionField.addEventFilter(KeyEvent.KEY_PRESSED, this::preventNewline);
        answerArea.addEventFilter(KeyEvent.KEY_PRESSED, this::preventNewline);
    }

    private void preventNewline(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            showAlert("Multiline input not allowed.\nPlease keep input to a single line.");
            event.consume();
        }
    }

    private void loadFAQsFromFile() {
        faqList.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader("data/faq_data.txt"))) {
            String question;
            while ((question = reader.readLine()) != null) {
                String answer = reader.readLine();
                reader.readLine(); // skip blank line
                if (answer != null) {
                    faqList.add(new AdminFAQ(question, answer));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveFAQsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/faq_data.txt"))) {
            for (AdminFAQ faq : faqList) {
                writer.write(faq.getQuestion());
                writer.newLine();
                writer.write(faq.getAnswer());
                writer.newLine();
                writer.newLine(); // blank line between entries
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean containsNewline(String text) {
        return text.contains("\n") || text.contains("\r");
    }

    @FXML
    private void addFAQ() {
        String question = questionField.getText().trim();
        String answer = answerArea.getText().trim();

        if (containsNewline(question) || containsNewline(answer)) {
            showAlert("Multiline input not allowed.\nPlease keep input to a single line.");
            return;
        }

        if (!question.isEmpty() && !answer.isEmpty()) {
            faqList.add(new AdminFAQ(question, answer));
            saveFAQsToFile();
            clearFields();
        }
    }

    @FXML
    private void editFAQ() {
        AdminFAQ selected = faqTable.getSelectionModel().getSelectedItem();
        String question = questionField.getText().trim();
        String answer = answerArea.getText().trim();

        if (containsNewline(question) || containsNewline(answer)) {
            showAlert("Multiline input not allowed.\nPlease keep input to a single line.");
            return;
        }

        if (selected != null && !question.isEmpty() && !answer.isEmpty()) {
            selected.setQuestion(question);
            selected.setAnswer(answer);
            faqTable.refresh();
            saveFAQsToFile();
            clearFields();
        }
    }

    @FXML
    private void deleteFAQ() {
        AdminFAQ selected = faqTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            faqList.remove(selected);
            saveFAQsToFile();
            clearFields();
        }
    }

    @FXML
    private void viewFAQ() {
        AdminFAQ selected = faqTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Question: " + selected.getQuestion());
            alert.setContentText("Answer: " + selected.getAnswer());
            alert.showAndWait();
        }
    }

    private void clearFields() {
        questionField.clear();
        answerArea.clear();
        faqTable.getSelectionModel().clearSelection();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Input Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void goBack() throws IOException, Exception {
        Stage stage = (Stage) faqTable.getScene().getWindow();        
        new AdminMainController().switchToMain(stage);

    }
}

