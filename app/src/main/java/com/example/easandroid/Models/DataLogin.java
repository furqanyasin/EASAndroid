package com.example.easandroid.Models;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DataLogin implements Serializable {

    private String Address;
    private String ContactNumber;
    private String Name;
    private String UserType;
    private String UserPassword;
    private String UserName;
    private int UserID;
    private String LastLoginDate;

    public static DataLogin objectFromData(String str) {

        return new Gson().fromJson(str, DataLogin.class);
    }

    public static DataLogin objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(str), DataLogin.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<DataLogin> arrayDataLoginFromData(String str) {

        Type listType = new TypeToken<ArrayList<DataLogin>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static List<DataLogin> arrayDataLoginFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<DataLogin>>() {
            }.getType();

            return new Gson().fromJson(jsonObject.getString(str), listType);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new ArrayList();


    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public String getContactNumber() {
        return ContactNumber;
    }

    public void setContactNumber(String ContactNumber) {
        this.ContactNumber = ContactNumber;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getUserType() {
        return UserType;
    }

    public void setUserType(String UserType) {
        this.UserType = UserType;
    }

    public String getUserPassword() {
        return UserPassword;
    }

    public void setUserPassword(String UserPassword) {
        this.UserPassword = UserPassword;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int UserID) {
        this.UserID = UserID;
    }

    public String getLastLoginDate() {
        return LastLoginDate;
    }

    public void setLastLoginDate(String lastLoginDate) {
        LastLoginDate = lastLoginDate;
    }
}
