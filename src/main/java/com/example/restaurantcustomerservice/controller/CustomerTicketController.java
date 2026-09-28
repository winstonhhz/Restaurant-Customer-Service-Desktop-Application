/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.restaurantcustomerservice.controller;

import com.example.restaurantcustomerservice.model.Ticket;
//import Main.*;
import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 *
 * @author Hello
 */
public class CustomerTicketController implements Initializable{
    
    @FXML private TableView<Ticket> customerMyTicketTable;
    @FXML private TableColumn<Ticket, String> myTicTicketIdCol;
    @FXML private TableColumn<Ticket, String> myTicTitleCol;
    @FXML private TableColumn<Ticket, String> myTicStatusCol;
    @FXML private TableColumn<Ticket, Void> myTicChatCol;
    private String CID = "";
   
    public void setCustomerID(String cid){ 
        this.CID = cid; 
        customerMyTicketTable.setItems(uploadTicketFromFile(CID));
    }
    
    public ObservableList<Ticket> uploadTicketFromFile(String customerID){
    
        ObservableList<Ticket> storeTickets = FXCollections.observableArrayList(); // Use ObservableList becuz arrayList doesn't auto refresh
        try (BufferedReader br  = new BufferedReader(new FileReader("data/tickets.txt"))){
            String x;
            while((x=br.readLine())!= null){
                String[] split = x.split(",", -1);
                if (split[0].equals(customerID) && split.length >= 6){
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
        
        myTicTicketIdCol.setCellValueFactory(new PropertyValueFactory<>("ID")); // PropertyValueFactory will automatically use the ID and set it to getID
        myTicTitleCol.setCellValueFactory(new PropertyValueFactory<>("title")); // It doesn't handle for loading ticket, just tell column what to display
        myTicStatusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        
        
        
        myTicChatCol.setCellFactory(cell -> new TableCell<>() {
            
            Button chatBtn = new Button("Chat");

        { // Anonymous inner class of the tablecell
            chatBtn.setOnAction(e -> {
                Ticket ticket = getTableView().getItems().get(getIndex());
                System.out.println("The chat for ticketID: " + ticket.getID());
//                LiveChatViewController LCVC = new LiveChatViewController();
//                LCVC.loadData(CID, ticket.getID(), "Customer");
//                LCVC.show();
                try{
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/restaurantcustomerservice/view/LiveChatView.fxml"));
                    Parent root = loader.load();
                    LiveChatViewController controller = loader.getController();
                    controller.loadData(CID, ticket.getID(), "Customer");
                    controller.autoRefresh();
                    controller.show(root);
                    
                } catch (IOException IOE){
                    System.out.println("Error: " +IOE );
                }
            });  
        }
       
            @Override
            protected void updateItem(Void item, boolean empty) { // an inner class, empty == true means current row have item in it, not just a display row
                if (empty){
                    setGraphic(null);
                } else {
//                    setText("Hi");                    
                    setGraphic(chatBtn); // Use for place element
                }                             
            }
            
//        });
//        }
        
        });
        
        customerMyTicketTable.setItems(uploadTicketFromFile(CID));

    }
  
    public void customerBackBtn() throws Exception{
        
        Stage passStage = ((Stage)customerMyTicketTable.getScene().getWindow());
        (new CustomerMainController()).switchToMain(passStage);
   
    }
    public void customerNewTicketBtn() throws Exception{
                       
//        System.out.println("Print");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/restaurantcustomerservice/view/newTicket.fxml"));
        Parent root = loader.load();
        NewTicketController nt = loader.getController();
        nt.setCustomerID(CID);
        nt.show(root);

  
    }
    
    public void customerRefreshBtn(){
        
        customerMyTicketTable.setItems(uploadTicketFromFile(CID));

        
    }
    
}


