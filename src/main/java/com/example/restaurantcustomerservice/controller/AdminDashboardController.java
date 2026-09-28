package com.example.restaurantcustomerservice.controller;

import com.example.restaurantcustomerservice.model.loginModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.sql.*;

public class AdminDashboardController {

    @FXML private TableView<loginModel> userTable;
    @FXML private TableColumn<loginModel, Integer> colId;
    @FXML private TableColumn<loginModel, String> colUsername;
    @FXML private TableColumn<loginModel, String> colPassword;
    @FXML private TableColumn<loginModel, String> colQuestion;
    @FXML private TableColumn<loginModel, String> colAnswer;

    @FXML private TextField usernameField;
    @FXML private TextField passwordField;
    @FXML private TextField answerField;
    @FXML private ComboBox<String> questionField;

    private Connection conn;

    private final ObservableList<String> questionList = FXCollections.observableArrayList(
            "What is your favorite Color?",
            "What is your favorite food?",
            "What is your birth date?"
    );
    public void initialize() {
        connect();
        setupTable();
        loadUsers();
        questionField.setItems(questionList);
    }

    private void connect() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/login_schema", "username", "password"); // Your own MYSQL username and password
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupTable() {
        colId.setCellValueFactory(new PropertyValueFactory<>("idlogin"));
        colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        colQuestion.setCellValueFactory(new PropertyValueFactory<>("question"));
        colAnswer.setCellValueFactory(new PropertyValueFactory<>("answer"));
    }

    private void loadUsers() {
        ObservableList<loginModel> list = FXCollections.observableArrayList();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM login");
            while (rs.next()) {
                list.add(new loginModel(
                        rs.getInt("idlogin"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("question"),
                        rs.getString("answer")
                ));
            }
            userTable.setItems(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAddUser() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();
        String question = questionField.getSelectionModel().getSelectedItem();
        String answer = answerField.getText().trim();

        if (username.isEmpty() || password.isEmpty() || question == null || answer.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Please fill all fields.");
            return;
        }

        try {
            // Check if username already exists
            PreparedStatement checkStmt = conn.prepareStatement("SELECT * FROM login WHERE username = ?");
            checkStmt.setString(1, username);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                showAlert(Alert.AlertType.ERROR, "Duplicate Username", "Username already exists.");
                return;
            }

            // Insert new user
            PreparedStatement pst = conn.prepareStatement("INSERT INTO login (username, password, question, answer) VALUES (?, ?, ?, ?)");
            pst.setString(1, username);
            pst.setString(2, password);
            pst.setString(3, question);
            pst.setString(4, answer);
            pst.executeUpdate();

            loadUsers(); // refresh table
            showAlert(Alert.AlertType.INFORMATION, "Success", "User added successfully!");

        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error", "Error occurred while adding user.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }



    @FXML
    private void handleEditUser() {
        loginModel selected = userTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                PreparedStatement pst = conn.prepareStatement(
                        "UPDATE login SET username=?, password=?, question=?, answer=? WHERE idlogin=?"
                );
                pst.setString(1, usernameField.getText());
                pst.setString(2, passwordField.getText());
                pst.setString(3, questionField.getSelectionModel().getSelectedItem());
                pst.setString(4, answerField.getText());
                pst.setInt(5, selected.getIdlogin());

                int updated = pst.executeUpdate();
                if (updated > 0) {
                    loadUsers();
                    showAlert(Alert.AlertType.INFORMATION, "Edit User", "User successfully updated.");
                } else {
                    showAlert(Alert.AlertType.ERROR, "Edit User", "Failed to update user.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Edit User", "An error occurred while updating user.");
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Edit User", "Please select a user to edit.");
        }
    }


    @FXML
    private void handleDeleteUser() {
        loginModel selected = userTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                PreparedStatement pst = conn.prepareStatement("DELETE FROM login WHERE idlogin=?");
                pst.setInt(1, selected.getIdlogin());
                int deleted = pst.executeUpdate();

                if (deleted > 0) {
                    loadUsers();
                    showAlert(Alert.AlertType.INFORMATION, "Delete User", "User successfully deleted.");
                } else {
                    showAlert(Alert.AlertType.ERROR, "Delete User", "Failed to delete user.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Delete User", "An error occurred while deleting user.");
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Delete User", "Please select a user to delete.");
        }
    }


    @FXML
    private void handleLogout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/restaurantcustomerservice/view/hello-view.fxml"));
            Parent root = loader.load();  // Use Parent, not AnchorPane

            Stage stage = (Stage) userTable.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Login");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML 
    public void handleBack(javafx.event.ActionEvent e) throws Exception{
        Stage stage = (Stage)((Button)e.getSource()).getScene().getWindow();
        new AdminMainController().switchToMain(stage);
    
    }


}
