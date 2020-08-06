package com.example.easandroid.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class AttendanceDataBase extends SQLiteOpenHelper {

    // Database Info
    private static final String DATABASE_NAME = "attendance.db";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    private static final String TABLE_EMPLOYEE = "employee";
    private static final String TABLE_ATTENDANCE = "attendance";

    // Post Table Employee
    private static final String EMPLOYEE_ID = "id";
    private static final String EMPLOYEE_DESIGNATION = "designation";
    private static final String EMPLOYEE_IMAGE = "image";
    private static final String EMPLOYEE_IS_ADMIN = "isAdmin";
    private static final String EMPLOYEE_NAME = "name";
    private static final String EMPLOYEE_PASSWORD = "password";
    private static final String EMPLOYEE_PHONE = "phone";

    // Post Table Attendance
    public static final String ATTENDANCE_ID = "id";
    public static final String ATTENDANCE_DATE = "attendance_date";
    public static final String ATTENDANCE_TIME_IN = "time_in";
    public static final String ATTENDANCE_TIME_OUT = "time_out";


    // Database constructor
    public AttendanceDataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String sql_employee = "CREATE TABLE " + TABLE_EMPLOYEE + "(" +
                EMPLOYEE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                EMPLOYEE_DESIGNATION + " TEXT," +
                EMPLOYEE_IMAGE + " TEXT," +
                EMPLOYEE_IS_ADMIN + " TEXT," +
                EMPLOYEE_NAME + " TEXT," +
                EMPLOYEE_PASSWORD + " TEXT," +
                EMPLOYEE_PHONE + " TEXT,";
        System.out.println("sql_sync_attendance - " + sql_employee);
        sqLiteDatabase.execSQL(sql_employee);

        String sql_attendance = "CREATE TABLE " + TABLE_ATTENDANCE + "(" +
                ATTENDANCE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ATTENDANCE_DATE + " TEXT," +
                ATTENDANCE_TIME_IN + " TEXT," +
                ATTENDANCE_TIME_OUT + " TEXT,";
        System.out.println("sql_sync_attendance - " + sql_attendance);
        sqLiteDatabase.execSQL(sql_attendance);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
