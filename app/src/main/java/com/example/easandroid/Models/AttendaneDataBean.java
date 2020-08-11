package com.example.easandroid.Models;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AttendaneDataBean implements Serializable {
    /**
     * AttendanceDate : /Date(1515349800000)/
     * UserID : 213050
     * TimeIN : /Date(1533090600000)/
     * TimeOut : /Date(1533133800000)/
     * LongitudeIn : 2.45454
     * LatitudeIn : 3.43543534
     * LongitudeOut : 2.35345345
     * LatitudeOut : 3.3453454534
     * LocationIn:DCSL
     * LocationOut:STASSEN
     */

    private String AttendanceDate;
    private int UserID;
    private String TimeIN;
    private String TimeOut;
    private String LongitudeIn;
    private String LatitudeIn;
    private String LongitudeOut;
    private String LatitudeOut;
    private String LocationIn;
    private String LocationOut;

    public static AttendaneDataBean objectFromData(String str) {

        return new Gson().fromJson(str, AttendaneDataBean.class);
    }

    public static AttendaneDataBean objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(str), AttendaneDataBean.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<AttendaneDataBean> arrayAttendaneDataBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<AttendaneDataBean>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static List<AttendaneDataBean> arrayAttendaneDataBeanFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<AttendaneDataBean>>() {
            }.getType();

            return new Gson().fromJson(jsonObject.getString(str), listType);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new ArrayList();


    }

    public String getAttendanceDate() {
        return AttendanceDate;
    }

    public void setAttendanceDate(String AttendanceDate) {
        this.AttendanceDate = AttendanceDate;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int UserID) {
        this.UserID = UserID;
    }

    public String getTimeIN() {
        return TimeIN;
    }

    public void setTimeIN(String TimeIN) {
        this.TimeIN = TimeIN;
    }

    public String getTimeOut() {
        return TimeOut;
    }

    public void setTimeOut(String TimeOut) {
        this.TimeOut = TimeOut;
    }

    public String getLongitudeIn() {
        return LongitudeIn;
    }

    public void setLongitudeIn(String LongitudeIn) {
        this.LongitudeIn = LongitudeIn;
    }

    public String getLatitudeIn() {
        return LatitudeIn;
    }

    public void setLatitudeIn(String LatitudeIn) {
        this.LatitudeIn = LatitudeIn;
    }

    public String getLongitudeOut() {
        return LongitudeOut;
    }

    public void setLongitudeOut(String LongitudeOut) {
        this.LongitudeOut = LongitudeOut;
    }

    public String getLatitudeOut() {
        return LatitudeOut;
    }

    public void setLatitudeOut(String LatitudeOut) {
        this.LatitudeOut = LatitudeOut;
    }

    public String getLocationIn() {
        return LocationIn;
    }

    public void setLocationIn(String locationIn) {
        LocationIn = locationIn;
    }

    public String getLocationOut() {
        return LocationOut;
    }

    public void setLocationOut(String locationOut) {
        LocationOut = locationOut;
    }
}
