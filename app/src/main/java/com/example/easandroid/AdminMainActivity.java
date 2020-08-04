package com.example.easandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AdminMainActivity extends AppCompatActivity implements View.OnClickListener{

    CardView presentEmployee, addEmployee, viewEmployee, attendanceReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presentEmployee = findViewById(R.id.cv_present_employee);
        presentEmployee.setOnClickListener(this);

        addEmployee = findViewById(R.id.cv_add_employee);
        addEmployee.setOnClickListener(this);

        viewEmployee = findViewById(R.id.cv_view_employee);
        viewEmployee.setOnClickListener(this);

        attendanceReport = findViewById(R.id.cv_attendance_report);
        attendanceReport.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view == presentEmployee){
            Intent intent = new Intent(AdminMainActivity.this,ViewEmployeeActivity.class);
            startActivity(intent);

        }else if (view ==addEmployee){
            Intent intent = new Intent(AdminMainActivity.this,AddEmployeeActivity.class);
            startActivity(intent);

        }else if (view ==viewEmployee){
            Intent intent = new Intent(AdminMainActivity.this,ViewEmployeeActivity.class);
            startActivity(intent);

        }else if (view ==attendanceReport){
            Intent intent = new Intent(AdminMainActivity.this,AttendanceReportActivity.class);
            startActivity(intent);

        }

    }
}