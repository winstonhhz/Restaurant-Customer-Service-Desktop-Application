/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.example.restaurantcustomerservice.model;

/**
 *
 * @author User
 */
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class AdminFAQ {
    private final StringProperty question;
    private final StringProperty answer;

    public AdminFAQ(String question, String answer) {
        this.question = new SimpleStringProperty(question);
        this.answer = new SimpleStringProperty(answer);
    }

    public String getQuestion() { return question.get(); }
    public void setQuestion(String q) { question.set(q); }
    public StringProperty questionProperty() { return question; }

    public String getAnswer() { return answer.get(); }
    public void setAnswer(String a) { answer.set(a); }
    public StringProperty answerProperty() { return answer; }
}

