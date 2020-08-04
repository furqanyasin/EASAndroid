package com.example.easandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.easandroid.Constants.Constants;
import com.example.easandroid.Models.EmployeeModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EmployeeLoginActivity extends AppCompatActivity {
    Button btnSignIn;
    EditText phone, password;


    FirebaseDatabase database;
    DatabaseReference users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_login);

        //FirebaseApp.initializeApp(this);
        database = FirebaseDatabase.getInstance();
        users = database.getReference("users");

        phone = findViewById(R.id.employee_signin);
        password = findViewById(R.id.employee_password_signin);

        btnSignIn = findViewById(R.id.btn_sign_in);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonEmployeeSignIn(phone.getText().toString(), password.getText().toString());
            }
        });
    }

    private void buttonEmployeeSignIn(String Phone, String Password) {
        final ProgressDialog mDialog = new ProgressDialog(EmployeeLoginActivity.this);
        mDialog.setMessage("Please waiting...");
        mDialog.show();

        final String LocPhone = Phone;
        final String LocPassword = Password;

        users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(LocPhone).exists()) {
                    mDialog.dismiss();
                    EmployeeModel employee = dataSnapshot.child(LocPhone).getValue(EmployeeModel.class);
                    employee.setPhone(LocPhone);

                    if (employee.getPassword().equals(LocPassword)) {
                        Toast.makeText(EmployeeLoginActivity.this, "Sign In Successfully !", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(EmployeeLoginActivity.this, EmployeeMainActivity.class);
                        Constants.employeeModel = employee;
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(EmployeeLoginActivity.this, "Wrong Password!!", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    mDialog.dismiss();
                    Toast.makeText(EmployeeLoginActivity.this, "User not exists ", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}