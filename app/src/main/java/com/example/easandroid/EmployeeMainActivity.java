package com.example.easandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class EmployeeMainActivity extends AppCompatActivity implements View.OnClickListener{

    CardView markAttendance, viewAttendance, reports, profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_main);
        markAttendance = findViewById(R.id.cv_employee_mark_attendance);
        markAttendance.setOnClickListener(this);
        viewAttendance = findViewById(R.id.cv_employee_view_attendance);
        viewAttendance.setOnClickListener(this);
        reports = findViewById(R.id.cv_employee_reports);
        reports.setOnClickListener(this);
        profile = findViewById(R.id.cv_employee_profile);
        profile.setOnClickListener(this);



    }

    @Override
    public void onClick(View view) {
        if (view == markAttendance){
            Intent intent = new Intent( EmployeeMainActivity.this, MarkAttendanceActivity.class);
            startActivity(intent);

        }else if (view ==viewAttendance){

        }else if (view==reports){

        }else if (view==profile){

        }

    }
}