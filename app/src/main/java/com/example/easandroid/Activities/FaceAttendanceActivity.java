package com.example.easandroid.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.easandroid.Database.AttendanceDataBase;
import com.example.easandroid.Database.MySingleton;
import com.example.easandroid.Models.Attendance;
import com.example.easandroid.Models.DataLogin;
import com.example.easandroid.R;
import com.example.easandroid.Utils.DateManager;
import com.example.easandroid.Utils.DateTimeFormatings;
import com.example.easandroid.web.HTTPPaths;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static com.example.easandroid.Database.AttendanceDataBase.ATTENDANCE_DATE;
import static com.example.easandroid.Database.AttendanceDataBase.ATTENDANCE_ID;
import static com.example.easandroid.Database.AttendanceDataBase.ATTENDANCE_IS_SYNC_IN;
import static com.example.easandroid.Database.AttendanceDataBase.ATTENDANCE_IS_SYNC_OUT;
import static com.example.easandroid.Database.AttendanceDataBase.ATTENDANCE_TIME_IN;
import static com.example.easandroid.Database.AttendanceDataBase.ATTENDANCE_TIME_OUT;
import static com.example.easandroid.Database.AttendanceDataBase.TABLE_ATTENDANCE;

public class FaceAttendanceActivity extends AppCompatActivity {

    private Toolbar mToolBar;
    TextView tvMobileTime, tvIn, tvOut;
    Button btnMarkIn, btnMarkOut;
    LinearLayout llDataA;
    java.util.Date noteTS;
    String enteredUser, status;
    private int venueCode, voltage;

    CardView cvHeader;
    private static final int UPDATE_INTERVAL = 5000;
    private static final int FASTEST_INTERVAL = 3000;
    private static final int DISPLACEMENT = 5;

    private int attendanceCount = 0;
    public AttendanceDataBase dbh;

    DataLogin dataLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_attendance);

        if (getIntent().getSerializableExtra("DataLogin") != null) {
            dataLogin = (DataLogin) getIntent().getSerializableExtra("DataLogin");
        }

        //init
        dbh = new AttendanceDataBase(getApplicationContext());
        tvMobileTime = (TextView) findViewById(R.id.tvMobileTime);
        tvIn = (TextView) findViewById(R.id.tvIn);
        tvOut = (TextView) findViewById(R.id.tvOut);


        btnMarkIn = (Button) findViewById(R.id.btnMarkIn);
        btnMarkOut = (Button) findViewById(R.id.btnMarkOut);
        llDataA = (LinearLayout) findViewById(R.id.llDataA);
        cvHeader = (CardView) findViewById(R.id.cvHeader);

        mToolBar = findViewById(R.id.tb_main);
        mToolBar.setTitle("Attendance");

        //time
        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                getCurrentTime();
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };
        t.start();
        setAttendanceType();
        btnMarkIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                markAttendance(0);
            }
        });

        btnMarkOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (attendanceCount == 0) {
                    displayStatusMessage("Do you want to mark OUT without mark IN?", 3, 1);
                } else {

                    markAttendance(attendanceCount);
                }

            }
        });

    }

    private void getCurrentTime() {
        noteTS = Calendar.getInstance().getTime();
        String time = "hh:mm a"; // 12:00
        tvMobileTime.setText(DateFormat.format(time, noteTS));
    }

    public ArrayList<Attendance> getAttendanceCount() {
        Calendar calOn = Calendar.getInstance();
        Calendar calOff = Calendar.getInstance();

        calOn.set(Calendar.HOUR_OF_DAY, 00);
        calOn.set(Calendar.MINUTE, 01);

        calOff.set(Calendar.HOUR_OF_DAY, 23);
        calOff.set(Calendar.MINUTE, 59);
        return getAttendance(calOn.getTimeInMillis(), calOff.getTimeInMillis());
    }

    private void setAttendanceType() {
        ArrayList<Attendance> attendances = getAttendanceCount();
        attendanceCount = attendances.size();
        if (attendanceCount == 1) {

            int timeStatus = attendances.get(0).getType();
            if (timeStatus == 0) {
                System.out.println(" attendance count 1 Status: " + timeStatus);
                btnMarkOut.setVisibility(View.VISIBLE);
                btnMarkIn.setVisibility(View.GONE);
                tvIn.setText(DateTimeFormatings.getDateTime(attendances.get(0).getMarkedTime()));

            } else {
                System.out.println(" attendance count 1 Status: " + timeStatus);
                btnMarkIn.setVisibility(View.GONE);
                btnMarkOut.setVisibility(View.GONE);
                cvHeader.setVisibility(View.GONE);
                tvOut.setText(DateTimeFormatings.getDateTime(attendances.get(0).getMarkedTime()));

            }

        } else {
            System.out.println(" attendance count 2 Status: ");
            btnMarkIn.setVisibility(View.VISIBLE);
            btnMarkOut.setVisibility(View.GONE);
            btnMarkIn.setText("Already Marked both");
            int timeStatus = attendances.get(0).getType();

            if (timeStatus == 0) {
                tvIn.setText(DateTimeFormatings.getDateTime(attendances.get(0).getMarkedTime()));
                tvOut.setText(DateTimeFormatings.getDateTime(attendances.get(1).getMarkedTime()));

            } else {
                tvIn.setText(DateTimeFormatings.getDateTime(attendances.get(1).getMarkedTime()));
                tvOut.setText(DateTimeFormatings.getDateTime(attendances.get(0).getMarkedTime()));
            }

            btnMarkIn.setEnabled(false);

        }
    }

    public ArrayList<Attendance> getAttendance(long min, long max) {
        String sql = "select * from attendance where attendace_date >= " + min + " and attendace_date <= " + max;
        SQLiteDatabase db = dbh.getWritableDatabase();
        Cursor cursor = null;
        ArrayList<Attendance> attendances = new ArrayList<Attendance>();
        try {
            cursor = db.rawQuery(sql, null);
            if (cursor.moveToFirst()) {
                do {
                    Attendance attendance = Attendance.getDBInstance(cursor);
                    attendances.add(attendance);
                } while (cursor.moveToNext());
            }
            return attendances;

        } catch (Exception e) {
            throw e;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
    }

    private void markAttendance(int attendanceCount) {
        System.out.println("First Attendance count: " + attendanceCount);

        Attendance attendance = new Attendance();

        attendance.setIsSynced(0);
        attendance.setMarkedTime(new Date());
        attendance.setUserId(enteredUser);
        //attendance.setLon(lon);
        //attendance.setLat(lat);
        attendance.setType(attendanceCount);
        //attendance.setVenue(selectedLocation);
        attendance.setVenueCode(venueCode);

        syncAttendance(attendance, attendanceCount);


        System.out.println("Attendance OnButtonClick Count: " + attendanceCount);

    }

    private void insertAttendanceToDb(Attendance attendance) {
        ContentValues cv = attendance.getContentvalues();
        boolean success = dbh.insertData(cv);
        if (success) {
            Toast.makeText(FaceAttendanceActivity.this, "Your attendance is inserted", Toast.LENGTH_SHORT).show();
            setAttendanceType();
        } else {
            Toast.makeText(FaceAttendanceActivity.this, "Your attendance is not inserted", Toast.LENGTH_SHORT).show();
        }
    }

    public void displayStatusMessage(String s, int colorValue, final int i) {

        AlertDialog.Builder builder = null;
        View view = null;
        TextView tvOk, tvMessage, tvCancel;

        int defaultColor = R.color.textGray;
        int successColor = R.color.successColor; // 1
        int errorColor = R.color.errorColor; // 2
        int warningColor = R.color.warningColor; // 3
        //1,2,3

        int color = defaultColor;
        if (colorValue == 1) {
            color = successColor;
        } else if (colorValue == 2) {
            color = errorColor;
        } else if (colorValue == 3) {
            color = warningColor;
        }

        builder = new AlertDialog.Builder(FaceAttendanceActivity.this);
        view = getLayoutInflater().inflate(R.layout.layout_for_custom_message_with_ok_cancel, null);

        tvOk = (TextView) view.findViewById(R.id.tvOk);
        tvCancel = (TextView) view.findViewById(R.id.tvCancel);
        tvMessage = (TextView) view.findViewById(R.id.tvMessage);
        tvMessage.setTextColor(getResources().getColor(color));
        tvMessage.setText(s);


        builder.setView(view);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                if (i == 1) {
                    markAttendance(1);
                }

            }
        });

    }

    private void insertSyncData(ContentValues cv) {
        boolean success = dbh.insertDataAll(cv, TABLE_ATTENDANCE);

        if (success) {
            System.out.println("Data is inserted to " + TABLE_ATTENDANCE + " IsSyncIn: " + cv.get(ATTENDANCE_IS_SYNC_IN));
        }
    }

    private void updateMarkOut(ContentValues cv, String attendanceDate) {
        SQLiteDatabase database = dbh.getWritableDatabase();
        try {
            int afectedRows = database.update(TABLE_ATTENDANCE, cv, ATTENDANCE_DATE + " = ? ", new String[]{"" + attendanceDate});
            if (afectedRows > 0) {
                System.out.println("Attendance is  updated at row " + attendanceDate);
            }
        } catch (SQLException e) {
            System.out.println("Error at data update at SYNC_ATTENDANCE " + e.getMessage());
        } finally {
            database.close();
        }

    }

    private void syncAttendance(final Attendance attendance, int attendanceCount) {

        System.out.println("Atendance Count: " + attendanceCount);
        final String attendanceDate = DateManager.getTodayDateString();
        String distributorID = "";
        String userId = dataLogin.getUserID() + "";
        StringRequest stringRequest;
        if (attendanceCount == 0) {
            String timeIn = DateManager.getDateWithTime();
            timeIn = timeIn.replace(" ", "%20");

            String url = HTTPPaths.SERVICE_URL + "SaveAttendance?attendanceDate=" + attendanceDate +
                    "&userID=" + userId +
                    "&timeIN=" + timeIn +
                    "&timeOut=" + timeIn +
                    "&locationOut=Not%20available";

            System.out.println("Attendance Insertion URL: " + url);

            //Insert MarkIn to db
            final ContentValues cv = new ContentValues();
            cv.put(ATTENDANCE_DATE, attendanceDate);
            cv.put(ATTENDANCE_ID, userId);
            cv.put(ATTENDANCE_TIME_IN, timeIn);
            cv.put(ATTENDANCE_TIME_OUT, timeIn);
            cv.put(ATTENDANCE_IS_SYNC_IN, 0);

            stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //JsonObject object = Json.parse(response).asObject();
                            JsonObject object = JsonParser.parseString(response).getAsJsonObject();
                            //int id = object.get("ID").asInt();
                            int id = object.get("ID").getAsInt();
                            if (id == 200) {
                                insertAttendanceToDb(attendance);
                                Toast.makeText(FaceAttendanceActivity.this, "Successfully inserted your mark In time", Toast.LENGTH_SHORT).show();
                                System.out.println("Sync Successful at Attendance Activity");
                                FaceAttendanceActivity.this.finish();
                                cv.put(ATTENDANCE_IS_SYNC_IN, 1);
                                insertSyncData(cv);

                            } else if (id == 500) {
                                insertAttendanceToDb(attendance);
                            } else {
                                Toast.makeText(FaceAttendanceActivity.this, "Please try again", Toast.LENGTH_SHORT).show();
                                System.out.println("Sync Fail at Attendance Activity");
                                insertSyncData(cv);
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(FaceAttendanceActivity.this, "Please try again", Toast.LENGTH_SHORT).show();
                            System.out.println("Error at syncing Attendance at Attendance Activity");
                            insertSyncData(cv);
                        }
                    }
            );
        } else {
            String timeOut = DateManager.getDateWithTime();
            timeOut = timeOut.replace(" ", "%20");

            String url = HTTPPaths.SERVICE_URL + "UpdateAttendance?attendanceDate=" + attendanceDate +
                    "&userID=" + userId +
                    "&timeOut=" + timeOut;

            System.out.println("Attendance updated URL: " + url);

            //Insert MarkOut to db
            final ContentValues cv = new ContentValues();

            cv.put(ATTENDANCE_TIME_OUT, timeOut);
            cv.put(ATTENDANCE_ID, userId);
            cv.put(ATTENDANCE_IS_SYNC_OUT, 0);

            stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //JsonObject object = Json.parse(response).asObject();
                            JsonObject object = JsonParser.parseString(response).getAsJsonObject();
                            //int id = object.get("ID").asInt();
                            int id = object.get("ID").getAsInt();
                            System.out.println("++++++++++++ Attendance Response: " + response.toString());

                            if (id == 200) {

                                insertAttendanceToDb(attendance);
                                Toast.makeText(FaceAttendanceActivity.this, "Successfully inserted your mark out time", Toast.LENGTH_SHORT).show();
                                System.out.println("Successfully updated mark out time at Attendance SyncActivity");
                                cv.put(ATTENDANCE_IS_SYNC_OUT, 1);
                                updateMarkOut(cv, attendanceDate);
                            } else if (id == 500) {
                                //insertAttendanceToDb(attendance);
                                Toast.makeText(FaceAttendanceActivity.this, "Please try again", Toast.LENGTH_SHORT).show();
                            } else {
                                System.out.println("SYNC FAIL Attendance SyncActivity");
                                Toast.makeText(FaceAttendanceActivity.this, "Please try again", Toast.LENGTH_SHORT).show();
                                updateMarkOut(cv, attendanceDate);
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            displayStatusMessage("Please try again", 1, 0);
                            System.out.println("Error at update Attendance at Attendance Activity");
                            updateMarkOut(cv, attendanceDate);
                        }
                    }
            );
        }


        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }
}