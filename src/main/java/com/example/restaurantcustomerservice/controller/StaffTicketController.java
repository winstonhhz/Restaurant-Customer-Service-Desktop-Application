/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.restaurantcustomerservice.controller;

import com.example.restaurantcustomerservice.model.Ticket;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import com.example.restaurantcustomerservice.model.EmailSender;
import javafx.stage.Stage;

/**
 *
 * @author Hello
 */
public class StaffTicketController implements Initializable {

    
    @FXML private TableView<Ticket> staffTicketTable;
    @FXML private TableColumn<Ticket, String> staffCustomerIdCol;
    @FXML private TableColumn<Ticket, String> staffTicketIdCol;
    @FXML private TableColumn<Ticket, String> staffTicTitleCol;
    @FXML private TableColumn<Ticket, String> staffTicStatusCol;
    @FXML private TableColumn<Ticket, Void> staffTicChatCol;    
    @FXML private TextField selectedCustomerIDTF;
    @FXML private TextField selectedTicketIDTF;
    @FXML private ComboBox ticketStatusComboBox;
    @FXML private TextArea TicketnotesTA;
    @FXML private Button TicketUpdateBtn;

    
    
    public ObservableList<Ticket> uploadTicketFromFile(){
    
        ObservableList<Ticket> storeTickets = FXCollections.observableArrayList(); // Use ObservableList becuz arrayList doesn't auto refresh
        try (BufferedReader br  = new BufferedReader(new FileReader("data/tickets.txt"))){
            String x;
            while((x=br.readLine())!= null){
                String[] split = x.split(",", -1);
                if (split.length >= 6){
                    Ticket t = new Ticket(split[0], split[1], split[2], split[3], split[4], split[5]);
                    storeTickets.add(t); 
                }
            } 
        } catch (IOException ex) {
            System.out.println("Error:" + ex);
        } catch (Exception e){
            System.out.println("Error:" + e);
        }
        return storeTickets;    
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        staffCustomerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        staffTicketIdCol.setCellValueFactory(new PropertyValueFactory<>("ID")); // PropertyValueFactory will automatically use the ID and set it to getID
        staffTicTitleCol.setCellValueFactory(new PropertyValueFactory<>("title")); // It doesn't handle for loading ticket, just tell column what to display
        staffTicStatusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        
        
        
        staffTicChatCol.setCellFactory(cell -> new TableCell<>() {
            Button chatBtn = new Button("Chat");

        {
        chatBtn.setOnAction(e -> {
            
            Ticket ticket = this.getTableView().getItems().get(getIndex());
            
            System.out.println("The chat for ticketID: " + ticket.getID());

            try{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/restaurantcustomerservice/view/LiveChatView.fxml"));
                Parent root = loader.load();
                LiveChatViewController controller = loader.getController();
                controller.loadData(ticket.getCustomerID(), ticket.getID(), "Staff");
                controller.autoRefresh();
                controller.show(root);
                    
            } catch (IOException IOE){
                System.out.println("Error: " +IOE );
            }            

            
        });
        }
            @Override
            protected void updateItem(Void item, boolean empty) { // empty == true means current row have item in it, not just a display row
                if (empty){
                    setGraphic(null);
                } else {
//                    setText("Hi");                    
                    setGraphic(chatBtn); // Use for place element
                }               
            }       
        });
   
        staffTicketTable.setItems(uploadTicketFromFile());
        
        staffTicketTable.getSelectionModel().selectedItemProperty().addListener((observarableVal, oldVal, newVal) ->{ // the three parameter is a must, 
                                                                                                                      // althought only one is being used
        
            if (newVal != null){
                if ((newVal.getStatus()).equals("Solved"))
                    TicketUpdateBtn.setDisable(true);
                else{
                    TicketUpdateBtn.setDisable(false);
                }
                selectedCustomerIDTF.setText(newVal.getCustomerID());
                selectedTicketIDTF.setText(newVal.getID());
                ticketStatusComboBox.getItems().setAll("Unsolved", "In progress", "Solved");
//                ticketStatusComboBox.getItems().setAll((newVal.getStatus().equals("Unsolved")) ? List.of("In progress", "Solved") : List.of("Solved"));
                ticketStatusComboBox.getSelectionModel().select(newVal.getStatus()); // 
                TicketUpdateBtn.setOnAction(e->{
                    if (ticketStatusComboBox.getValue().equals(newVal.getStatus())){
                            
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Status");
                        alert.setContentText("Same status, nothing changed");
                        alert.show();
                    } else {
                        try {
                            String comboBoxStatus = ticketStatusComboBox.getValue().toString();
                            String givenNotes = TicketnotesTA.getText();
                            LocalDateTime dt = LocalDateTime.now();
                            String j = dt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                            TicketUpdate(newVal, comboBoxStatus, j);     
                            staffTicketTable.setItems(uploadTicketFromFile());
                            
                            new Thread(() -> { // Send email requires long running, without using another thread will freeze the javafx
                                EmailSender es = new EmailSender(); 
                                es.sendEmail(comboBoxStatus, givenNotes);
                            }).start();
                            
                            TicketnotesTA.clear();
                            selectedCustomerIDTF.setText(null);
                            selectedTicketIDTF.setText(null);
                            ticketStatusComboBox.getItems().clear();
                            
                            
                            
                        } catch (FileNotFoundException ex) {
                            System.out.println("Error: " + ex);
                        } catch (IOException ex) {
                            System.out.println("Error: " + ex);
                    }
                    }
                });
                
                
            }
        });
 
    }
    public void TicketUpdate(Ticket t, String status, String solvedDate) throws FileNotFoundException, IOException{
        File file = new File("data/tickets.txt");
        File tempFile = new File("data/tickets_temp.txt");
        
        BufferedReader br = new BufferedReader(new FileReader(file));
        PrintWriter pw = new PrintWriter(tempFile);
        
        String line;
        while((line = br.readLine()) != null){
            String[] separate = line.split(",", -1);
            
            if (separate.length < 6){
                continue;
            }
            if (separate[0].equals(t.getCustomerID()) && separate[1].equals(t.getID())){
                separate[3] = status;
                separate[5] = solvedDate;
                line = String.join(",", separate);
              
            }
            pw.println(line);
        }
        
        br.close();
        pw.close();
        
        if (file.delete()){
            tempFile.renameTo(file);         
//            System.out.println("Passed");
        } else {
            System.out.println("Error renaming to the original file");
        } 
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Successfully Modifed");
        alert.setContentText("Row have been modified \n Ticket ID >> " + t.getID() + "\n Customer ID >> " + t.getCustomerID());
        alert.show();
       
        
    }
 
    public void TicketBackBtn(javafx.event.ActionEvent e) throws IOException{
        Stage stage = (Stage)((Button)e.getSource()).getScene().getWindow();
        new StaffMainController().backToStaffMain(stage);
        
    }
    
    
    
}
