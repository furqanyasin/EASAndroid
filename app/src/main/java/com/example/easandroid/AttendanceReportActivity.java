package com.example.easandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.easandroid.Adapters.EmployeeAdapter;
import com.example.easandroid.Models.EmployeeModel2;

import java.util.ArrayList;

public class AttendanceReportActivity extends AppCompatActivity {
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<EmployeeModel2> employeeList;
    RecyclerView recyclerViewEmployee;
    EmployeeAdapter employeeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_report);
        employeeList();
        setRecyclerView();
    }

    public void employeeList() {
        employeeList = new ArrayList<>();
        employeeList.add(new EmployeeModel2("Muhammad Furqan", "android developer", R.drawable.ic_person_outline));
        employeeList.add(new EmployeeModel2("Muhammad Afnan", " developer", R.drawable.ic_person_outline));
        employeeList.add(new EmployeeModel2("Muhammad Zohaib", "php developer", R.drawable.ic_person_outline));
        employeeList.add(new EmployeeModel2("Muhammad Hamza", "full stack  developer", R.drawable.ic_person_outline));

    }

    public void setRecyclerView() {
        recyclerViewEmployee = findViewById(R.id.recyclerview_employee);
        recyclerViewEmployee.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        employeeAdapter = new EmployeeAdapter(employeeList);
        recyclerViewEmployee.setLayoutManager(mLayoutManager);
        recyclerViewEmployee.setAdapter(employeeAdapter);

    }
}