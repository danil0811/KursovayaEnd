package com.example.kursovayaend.Model;

public class Users {
    private String name;
    private String phone;
    private String Number;
    private String Password;

    public Users() {
        // Пустой конструктор (необходим для Firebase)
    }

    public Users(String name, String phone, String password, String number) {
        this.name = name;
        this.phone = phone;
        this.Password = password;
        this.Number = number;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }
}