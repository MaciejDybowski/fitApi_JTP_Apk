package com.example.mylogowanie.DTO;

public class AddProduct {
    private String name;
    private float carbo;
    private float fat;
    private float protein;

    public AddProduct(String name, float carbo, float fat, float protein) {
        this.name = name;
        this.carbo = carbo;
        this.fat = fat;
        this.protein = protein;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getCarbo() {
        return carbo;
    }

    public void setCarbo(float carbo) {
        this.carbo = carbo;
    }

    public float getFat() {
        return fat;
    }

    public void setFat(float fat) {
        this.fat = fat;
    }

    public float getProtein() {
        return protein;
    }

    public void setProtein(float protein) {
        this.protein = protein;
    }
}
