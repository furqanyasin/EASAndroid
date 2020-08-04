package com.example.easandroid.Models;

public class EmployeeModel2 {
    public String Name;
    public String Role;
    public Integer Image;

    public EmployeeModel2(String name, String role, Integer image) {
        Name = name;
        Role = role;
        Image = image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    public Integer getImage() {
        return Image;
    }

    public void setImage(Integer image) {
        Image = image;
    }
}
