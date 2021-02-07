package com.example.mylogowanie.DTO;


public class UserRepresentation {
    private String firstName;
    private String lastName;
    private int age;
    private String gender;
    private float weight;
    private float height;
    private String login;
    private float demandProtein;
    private float demandFat;
    private float demandCarbs;


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public float getDemandProtein() {
        return demandProtein;
    }

    public void setDemandProtein(float demandProtein) {
        this.demandProtein = demandProtein;
    }

    public float getDemandFat() {
        return demandFat;
    }

    public void setDemandFat(float demandFat) {
        this.demandFat = demandFat;
    }

    public float getDemandCarbs() {
        return demandCarbs;
    }

    public void setDemandCarbs(float demandCarbs) {
        this.demandCarbs = demandCarbs;
    }
}
