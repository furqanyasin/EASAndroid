package com.example.easandroid.Models;

public class EmployeeModel {
    private String Name;
    private String Password;
    private String Phone;
    private String IsAdmin;

    public EmployeeModel() {
    }

    public EmployeeModel(String name, String password, String phone, String isAdmin) {
        Name = name;
        Password = password;
        Phone = phone;
        IsAdmin = "false";
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getIsAdmin() {
        return IsAdmin;
    }

    public void setIsAdmin(String isAdmin) {
        IsAdmin = isAdmin;
    }
}
