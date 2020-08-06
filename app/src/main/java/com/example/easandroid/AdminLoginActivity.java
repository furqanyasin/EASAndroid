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
import com.example.easandroid.Models.AdminModel;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminLoginActivity extends AppCompatActivity {

    Button btnSignIn;
    EditText phone, password;


    FirebaseDatabase database;
    DatabaseReference users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        FirebaseApp.initializeApp(this);
        database = FirebaseDatabase.getInstance();
        users = database.getReference("Employee");

        phone = findViewById(R.id.admin_sign_in);
        password = findViewById(R.id.admin_password_signin);

        btnSignIn = findViewById(R.id.btn_sign_in);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonAdminSignIn(phone.getText().toString(), password.getText().toString());
            }
        });

    }

    private void buttonAdminSignIn(String Phone, String Password) {
        final ProgressDialog mDialog = new ProgressDialog(AdminLoginActivity.this);
        mDialog.setMessage("Please waiting...");
        mDialog.show();

        final String LocPhone = Phone;
        final String LocPassword = Password;

        users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(LocPhone).exists()){
                    mDialog.dismiss();
                    AdminModel admin = dataSnapshot.child(LocPhone).getValue(AdminModel.class);
                    admin.setPhone(LocPhone);
                    if (Boolean.parseBoolean(admin.getIsAdmin())){
                        if (admin.getPassword().equals(LocPassword)){
                            Toast.makeText(AdminLoginActivity.this, "Sign In Successfully !", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(AdminLoginActivity.this, AdminMainActivity.class);
                            Constants.adminModel = admin;
                            startActivity(intent);
                            finish();
                        }else {
                            Toast.makeText(AdminLoginActivity.this, "Wrong Password!!", Toast.LENGTH_SHORT).show();
                        }
                    }else {

                        Toast.makeText(AdminLoginActivity.this, "Please Login with Admin Account", Toast.LENGTH_SHORT).show();

                    }
                }else {
                    mDialog.dismiss();
                    Toast.makeText(AdminLoginActivity.this, "User not exists ", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}