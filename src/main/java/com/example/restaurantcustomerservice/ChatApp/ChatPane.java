package com.example.restaurantcustomerservice.ChatApp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ChatPane extends VBox {

    private static final ObservableList<SearchAction> data = FXCollections.observableArrayList();
    private static final AnswerService docsAnswerService = new AnswerService();
    private final TableView<SearchAction> table = new TableView<>();
    private final TextArea lastAnswer = new TextArea();

    public ChatPane() {
        this.setStyle("-fx-padding: 15px;");

        Label label = new Label("What is your question?");
        label.setStyle("-fx-font-size: 25px; -fx-font-weight: bold;");

        TextField input = new TextField();
        input.setOnAction(e -> doSearch(input.getText()));
        input.setMinWidth(400);

        Button search = new Button("Search");
        search.setOnAction(e -> doSearch(input.getText()));
        Button clear = new Button("Clear");
        clear.setOnAction(e -> input.clear());

        HBox inputHolder = new HBox(10, input, search, clear);
        inputHolder.setStyle("-fx-padding: 0 0 25px 0");

        TableColumn<SearchAction, String> timestamp = new TableColumn<>("Timestamp");
        timestamp.setCellValueFactory(cellData -> cellData.getValue().getTimestampProperty());
        timestamp.setMinWidth(250);
        TableColumn<SearchAction, String> question = new TableColumn<>("Question");
        question.setCellValueFactory(cellData -> cellData.getValue().getQuestionProperty());
        question.setMinWidth(250);
        TableColumn<SearchAction, String> answer = new TableColumn<>("Answer");
        answer.setCellValueFactory(cellData -> cellData.getValue().getAnswerProperty());
        answer.setMinWidth(250);
        TableColumn<SearchAction, Boolean> finished = new TableColumn<>("Finished");
        finished.setCellValueFactory(cellData -> cellData.getValue().getFinishedProperty());
        finished.setMinWidth(50);

        table.getColumns().addAll(timestamp, question, answer, finished);
        table.setItems(data);
        table.setStyle("-fx-padding: 0 25px 0 0");

        lastAnswer.setWrapText(true);

        // 🔙 Back Button
        Button backButton = new Button("Back");
        backButton.setStyle("-fx-padding: 5px 15px;");
        backButton.setOnAction(e -> {
            // Close the window
            Stage stage = (Stage) this.getScene().getWindow();
            stage.close();
        });

        this.getChildren().addAll(label, inputHolder, new HBox(table, lastAnswer), backButton);

        data.add(new SearchAction("Application started", true));

        var initAction = new SearchAction("Initializing search engine, please stand by...");
        data.add(initAction);
        lastAnswer.textProperty().bind(initAction.getAnswerProperty());
        new Thread(() -> docsAnswerService.init(initAction)).start();
    }

    private void doSearch(String question) {
        if (question.isEmpty()) return;

        var searchAction = new SearchAction(question);
        data.add(searchAction);
        lastAnswer.textProperty().bind(searchAction.getAnswerProperty());
        new Thread(() -> docsAnswerService.ask(searchAction)).start();
    }
}
