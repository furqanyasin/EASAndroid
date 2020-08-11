package com.example.easandroid.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class AttendanceDataBase extends SQLiteOpenHelper {

    // Database Info
    public static final String DATABASE_NAME = "attendance.db";
    public static final int DATABASE_VERSION = 1;

    // Table Names
    public static final String TABLE_EMPLOYEE = "TABLE_EMPLOYEE";
    public static final String TABLE_ATTENDANCE = "TABLE_ATTENDANCE";
    public static final String TABLE_NAME = "attendance";

    // Post Table Employee
    public static final String EMPLOYEE_ID = "id";
    public static final String EMPLOYEE_DESIGNATION = "designation";
    public static final String EMPLOYEE_IMAGE = "image";
    public static final String EMPLOYEE_IS_ADMIN = "isAdmin";
    public static final String EMPLOYEE_NAME = "name";
    public static final String EMPLOYEE_PASSWORD = "password";
    public static final String EMPLOYEE_PHONE = "phone";

    // Post Table Attendance
    public static final String ATTENDANCE_ID = "id";
    public static final String ATTENDANCE_DATE = "attendance_date";
    public static final String ATTENDANCE_TIME_IN = "time_in";
    public static final String ATTENDANCE_TIME_OUT = "time_out";
    public static final String ATTENDANCE_IS_SYNC_IN = "is_sync_in";
    public static final String ATTENDANCE_IS_SYNC_OUT = "is_sync_out";


    // Database constructor
    public AttendanceDataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String sql_user_attendace = "create table attendance(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "userId TEXT," +
                "attendace_date INTEGER," +
                "type INTEGER," +
                "sequence_id INTEGER," +
                "lat TEXT," +
                "lon TEXT," +
                "is_synced INTEGER," +
                "created_at INTEGER," +
                "venue TEXT," +
                "venueCode INTEGER)";
        System.out.println("sql_user_attendace - " + sql_user_attendace);
        sqLiteDatabase.execSQL(sql_user_attendace);


    /*    String sql_employee = "CREATE TABLE " + TABLE_EMPLOYEE + "(" +
                EMPLOYEE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                EMPLOYEE_DESIGNATION + " TEXT," +
                EMPLOYEE_IMAGE + " TEXT," +
                EMPLOYEE_IS_ADMIN + " TEXT," +
                EMPLOYEE_NAME + " TEXT," +
                EMPLOYEE_PASSWORD + " TEXT," +
                EMPLOYEE_PHONE + " TEXT,";
        System.out.println("sql_sync_attendance - " + sql_employee);
        sqLiteDatabase.execSQL(sql_employee);*/

        String sql_attendance = "CREATE TABLE " + TABLE_ATTENDANCE + "(" +
                ATTENDANCE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ATTENDANCE_DATE + " TEXT," +
                ATTENDANCE_TIME_IN + " TEXT," +
                ATTENDANCE_TIME_OUT + " TEXT," +
                ATTENDANCE_IS_SYNC_IN + " INTEGER," +
                ATTENDANCE_IS_SYNC_OUT + " INTEGER,";
        System.out.println("sql_attendance - " + sql_attendance);
        sqLiteDatabase.execSQL(sql_attendance);


    }

    public boolean insertData(ContentValues cv) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.insert(TABLE_ATTENDANCE, null, cv);

        if (result > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {


    }

    public boolean insertDataAll(ContentValues cv, String tableName) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.insert(tableName, null, cv);

        if (result > 0) {
            return true;
        } else {
            return false;
        }
    }

    public Cursor getAllData(String tableName) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery( "SELECT * FROM " + tableName, null );
        return cursor;
    }

    public Cursor getSelectedData(String tableName, String colName, String colName2) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery( "SELECT * FROM " + tableName + " WHERE " + colName + " = 1 " + " ORDER BY " + colName2 + " ASC ", null );
        return cursor;
    }

    public Cursor getOngoingJobsData(String tableName, String colName, String colName2) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery( "SELECT * FROM " + tableName + " WHERE " + colName + " = 2 " + " ORDER BY " + colName2 + " ASC ", null );
        return cursor;
    }

    public Cursor getHistoryJobsData(String tableName, String colName, String colName2) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery( "SELECT * FROM " + tableName + " WHERE " + colName + " = 3 " + " ORDER BY " + colName2 + " ASC ", null );
        return cursor;
    }

    public Cursor getSingleData(String url) {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery( url, null );
        return cursor;
    }

    /*db.update(TABLE_NAME, contentValues, NAME + " = ? AND " + LASTNAME + " = ?", new String[]{"Manas", "Bajaj"});*/

    public boolean updateTable(String id, ContentValues cv, String whereClause, String tableName) {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean isUpdated = false;
        int afectedRows = db.update( tableName, cv, whereClause, new String[]{id} );
        if (afectedRows > 0) {
            isUpdated = true;
            System.out.println( "======== Updated Row Count: " + afectedRows + " ================= " );
        }
        return isUpdated;
    }

    public boolean deleteAllData(String tableName) {
        boolean success = false;
        SQLiteDatabase db = this.getReadableDatabase();

        try {
            db.execSQL( "DELETE FROM " + tableName );
            success = true;

        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        return success;
    }


    public int totalROWS(String tablename) {
        int value = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
//        if(tablename.equalsIgnoreCase(All)){
//            cursor.getCount() = 0;
//        }else{
//
//        }
        cursor = db.rawQuery( "SELECT COUNT (*) FROM " + tablename, null );
        if (cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                value = cursor.getInt( 0 );
            }
        } else {
            value = 0;
        }

        return value;
    }

    public void deleteFromAnyTable(String tablename, String wherePart) {

        SQLiteDatabase db = this.getWritableDatabase();
        //Cursor cursor = db.rawQuery("delete from "+tablename+" where "+wherePart,null);

        String sql = "delete from " + tablename + " where " + wherePart;
        db.execSQL( sql );
    }

}
