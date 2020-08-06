package com.example.easandroid.Models;

public class Attendance {
    private String date;
    private String time;
    private String name;
    private String phone;

    public Attendance() {
    }

    public Attendance(String date, String time, String name, String phone) {
        this.date = date;
        this.time = time;
        this.name = name;
        this.phone = phone;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
