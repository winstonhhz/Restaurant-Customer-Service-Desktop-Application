package com.example.restaurantcustomerservice.controller;

import com.example.restaurantcustomerservice.model.loginModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.control.Label;

import java.sql.*;

public class StaffDashboardController {

    @FXML private TableView<loginModel> userTable;
    @FXML private TableColumn<loginModel, Integer> colId;
    @FXML private TableColumn<loginModel, String> colUsername;
    @FXML private TableColumn<loginModel, String> colPassword;
    @FXML private TableColumn<loginModel, String> colQuestion;
    @FXML private TableColumn<loginModel, String> colAnswer;
    @FXML private TableColumn<loginModel, String> colDate;
    @FXML private TextField searchField;
    @FXML private Label loginTimeLabel;

    private Connection conn;
    private ObservableList<loginModel> masterList = FXCollections.observableArrayList();

    public void initialize() {
        connect();
        setupTable();
        loadUsers();
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
        masterList.clear();
        try {
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM login");
            while (rs.next()) {
                masterList.add(new loginModel(
                        rs.getInt("idlogin"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("question"),
                        rs.getString("answer")
                ));
            }
            userTable.setItems(masterList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setLoginTime() {
        loginTimeLabel.setText("Login Time: " + java.time.LocalTime.now().withNano(0));
    }

    @FXML
    private void handleSearch() {
        String keyword = searchField.getText().toLowerCase();
        if (keyword.isEmpty()) {
            userTable.setItems(masterList);
            return;
        }

        ObservableList<loginModel> filteredList = FXCollections.observableArrayList();
        for (loginModel user : masterList) {
            if (user.getUsername().toLowerCase().contains(keyword)) {
                filteredList.add(user);
            }
        }

        userTable.setItems(filteredList);
    }

    @FXML
    private void handleLogout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/restaurantcustomerservice/view/hello-view.fxml"));
            StackPane pane = loader.load();
            Stage stage = (Stage) userTable.getScene().getWindow();
            stage.setScene(new Scene(pane));
            stage.setTitle("Login Page");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
