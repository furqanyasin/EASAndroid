package com.example.easandroid.Models;

public class EmployeeModel {
    private String Name;
    private String Password;
    private String Phone;
    private String IsAdmin;
    private String Image;
    private String Designation;

    public EmployeeModel() {
    }

    public EmployeeModel(String name, String password, String designation, String phone,  String image) {
        Name = name;
        Designation = designation;
        Phone = phone;
        IsAdmin = "false";
        Image = image;
        Password = password;
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

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getDesignation() {
        return Designation;
    }

    public void setDesignation(String designation) {
        Designation = designation;
    }


}
