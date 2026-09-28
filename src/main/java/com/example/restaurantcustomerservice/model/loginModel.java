package com.example.restaurantcustomerservice.model;

public class loginModel {
    private int idlogin;
    private String username;
    private String password;
    private String question;
    private String answer;
    private String date;

    public loginModel(int idlogin, String username, String password, String question, String answer) {
        this.idlogin = idlogin;
        this.username = username;
        this.password = password;
        this.question = question;
        this.answer = answer;
    }

    public loginModel(int idlogin, String username, String date) {
        this.idlogin = idlogin;
        this.username = username;
        this.date = date;
    }


    public int getIdlogin() {
        return idlogin;
    }

    public void setIdlogin(int idlogin) {
        this.idlogin = idlogin;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

}
