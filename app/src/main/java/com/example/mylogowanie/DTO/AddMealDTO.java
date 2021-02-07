package com.example.mylogowanie.DTO;

import java.time.LocalDate;

public class AddMealDTO {
    private float gram;
    private String date;
    private String login;
    private String productName;

    public AddMealDTO(float gram, String date, String login, String productName) {
        this.gram = gram;
        this.date = date;
        this.login = login;
        this.productName = productName;
    }

    public float getGram() {
        return gram;
    }

    public void setGram(float gram) {
        this.gram = gram;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
