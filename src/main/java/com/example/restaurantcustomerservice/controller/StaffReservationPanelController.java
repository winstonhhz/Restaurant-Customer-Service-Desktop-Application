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
import com.example.restaurantcustomerservice.model.Reservation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.*;
import java.util.UUID;

public class StaffReservationPanelController {

    @FXML private TableView<Reservation> reservationTable;
    @FXML private TableColumn<Reservation, String> idCol;
    @FXML private TableColumn<Reservation, String> nameCol;
    @FXML private TableColumn<Reservation, String> dateCol;
    @FXML private TableColumn<Reservation, String> timeCol;
    @FXML private TableColumn<Reservation, String> peopleCol;
    @FXML private TableColumn<Reservation, String> requirementCol;
    @FXML private TableColumn<Reservation, String> statusCol;

    @FXML private ComboBox<String> statusCombo;

    private final ObservableList<Reservation> reservationList = FXCollections.observableArrayList();
    private final String filePath = "data/reservationdata.txt";

    @FXML
    public void initialize() {
        idCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getId()));
        nameCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getName()));
        dateCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getDate()));
        timeCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getTime()));
        peopleCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getPeople()));
        requirementCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getRequirement()));
        statusCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getStatus()));

        statusCombo.setItems(FXCollections.observableArrayList("Waiting to be Seated", "Ready to be Seated"));

        loadReservations();
    }

    private void loadReservations() {
        reservationList.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", -1);
                if (parts.length == 9) {
                    reservationList.add(new Reservation(
                            parts[0], parts[1], parts[2], parts[3],
                            parts[4], parts[5], parts[6], parts[7], parts[8]
                    ));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        reservationTable.setItems(reservationList);
    }

    private void saveReservations() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Reservation r : reservationList) {
                writer.write(String.join(",", r.getId(), r.getName(), r.getEmail(), r.getPhone(),
                        r.getDate(), r.getTime(), r.getPeople(), r.getRequirement(), r.getStatus()));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleRowClick(MouseEvent event) {
        Reservation selected = reservationTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            statusCombo.setValue(selected.getStatus());
        }
    }

    @FXML
    private void handleUpdate() {
        Reservation selected = reservationTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            String newStatus = statusCombo.getValue();

            if (newStatus == null) {
                showAlert("Please select a status before updating.");
                return;
            }

            selected.setStatus(newStatus);
            saveReservations();
            reservationTable.refresh();
        }
    }

    @FXML
    private void handleDelete() {
        Reservation selected = reservationTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            reservationList.remove(selected);
            saveReservations();
        }
    }

    @FXML
    private void handleAdd() {
        Reservation dummy = new Reservation(
                UUID.randomUUID().toString().substring(0, 6),
                "Dummy Name", "dummy@example.com", "0123456789",
                "2025-08-01", "19:00", "4", "None", "Waiting to be Seated"
        );
        reservationList.add(dummy);
        saveReservations();
        reservationTable.refresh();
    }

    @FXML
    private void goBack(ActionEvent event) throws Exception {
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        new StaffMainController().backToStaffMain(stage);

    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Input Warning");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}

