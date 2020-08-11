package com.example.easandroid.Models;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.Date;

public class Attendance {
    private int id;
    private String userId;
    private Date markedTime;
    private double lat;
    private double lon;
    private int type;
    private int sequnceId;
    private int isSynced;
    private int venueCode;
    private String venue;
    private String createdDate;
    private int distributerId;
    private int salesRepId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getMarkedTime() {
        return markedTime;
    }

    public void setMarkedTime(Date markedTime) {
        this.markedTime = markedTime;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getSequnceId() {
        return sequnceId;
    }

    public void setSequnceId(int sequnceId) {
        this.sequnceId = sequnceId;
    }

    public int getIsSynced() {
        return isSynced;
    }

    public void setIsSynced(int isSynced) {
        this.isSynced = isSynced;
    }

    public void setCreatedTime(String createdTime) {
        this.createdDate= createdTime;
    }

    public String getVenue() { return venue;}

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public int getVenueCode() { return venueCode;}

    public void setVenueCode(int venueCode) {
        this.venueCode = venueCode;
    }


    public ContentValues getContentvalues(){
        ContentValues cv = new ContentValues();
        cv.put("userId",getUserId());
        cv.put("attendace_date",getMarkedTime().getTime());
        cv.put("type",getType());
        cv.put("sequence_id",getSequnceId());
        cv.put("lat",getLat());
        cv.put("lon",getLon());
        cv.put("is_synced",getIsSynced());
        cv.put("created_at",new Date().getTime());
        cv.put("venue",getVenue());
        cv.put("venueCode",getVenueCode());

        return cv;
    }


    public static Attendance getDBInstance(Cursor cursor) {
        Attendance attendance = new Attendance();
        int index = 0;
        attendance.setId(cursor.getInt(0));
        attendance.setUserId(cursor.getString(1));
        attendance.setMarkedTime(new Date(cursor.getLong(2)));
        attendance.setType(cursor.getInt(3));
        attendance.setSequnceId(cursor.getInt(4));
        attendance.setLat(Double.parseDouble(cursor.getString(5)));
        attendance.setLon(Double.parseDouble(cursor.getString(6)));
        attendance.setIsSynced(cursor.getInt(7));
        attendance.setCreatedTime(cursor.getString(8));
        attendance.setVenue(cursor.getString(9));
        attendance.setVenueCode(cursor.getInt(10));
        return  attendance;
    }
}
