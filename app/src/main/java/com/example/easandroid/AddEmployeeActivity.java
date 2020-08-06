package com.example.easandroid;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.easandroid.Models.EmployeeModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

import static com.example.easandroid.Constants.Constants.employeeModel;

public class AddEmployeeActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference employee;
    FirebaseStorage storage;
    StorageReference storageReference;
    String name, phone, password, designation, image;

    EditText employeeFullName, employeePassword, employeePhone, employeeDesignation;
    Button btnSelect, btnUpload, btnSaveEmployee;

    EmployeeModel newEmployeeModel;
    Uri saveUri;

    private final int PICK_IMAGE_REQUEST = 71;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);

        employeeFullName = findViewById(R.id.et_employee_full_name);
        employeePhone = findViewById(R.id.et_employee_phone);
        employeeDesignation = findViewById(R.id.et_designation);
        employeePassword = findViewById(R.id.et_password);
        btnSelect = findViewById(R.id.btn_image_select);
        btnUpload = findViewById(R.id.btn_image_upload);
        btnSaveEmployee = findViewById(R.id.btn_save_employee);


        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage();
            }
        });

        //init firebase
        database = FirebaseDatabase.getInstance();
        employee = database.getReference("Employee");
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            saveUri = data.getData();
            btnSelect.setText("Selected!");
        }

    }

    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "select picture"), PICK_IMAGE_REQUEST);
    }

    private void uploadImage() {
        name = employeeFullName.getText().toString();
        phone = employeePhone.getText().toString();
        password = employeePassword.getText().toString();
        designation = employeeDesignation.getText().toString();


        if (saveUri != null) {
            final ProgressDialog mDialog = new ProgressDialog(this);
            mDialog.setMessage("Uploading...");
            mDialog.show();

            String imageName = UUID.randomUUID().toString();
            final StorageReference imageFolder = storageReference.child("images/" + imageName);
            imageFolder.putFile(saveUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            mDialog.dismiss();
                            //Toast.makeText(AddEmployeeActivity.this, "Uploaded!", Toast.LENGTH_SHORT).show();
                            btnUpload.setText("Uploaded");
                            imageFolder.getDownloadUrl()
                                    .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(final Uri uri) {


                                            //set value for new category if image upload and we can get download it
                                            //newEmployeeModel = new EmployeeModel(name,password,designation,phone, uri.toString());

                                        }
                                    });

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            mDialog.dismiss();
                            Toast.makeText(AddEmployeeActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            mDialog.setMessage("Uploaded " + progress + "%");

                        }
                    });
            btnSaveEmployee.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    employee.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            //check if user not exit in database
                            if (dataSnapshot.child(phone).exists()) {
                                //Get User Information
                                mDialog.dismiss();
                                Toast.makeText(AddEmployeeActivity.this, "Phone Number already exists", Toast.LENGTH_SHORT).show();
                                finish();

                            } else {
                                mDialog.dismiss();
                                final EmployeeModel employeeModel = new EmployeeModel(name, password, designation, phone, saveUri.toString());
                                //employee.child(phone).setValue(employeeModel);
                                if (employeeModel != null) {
                                    //employee.push().setValue(newEmployeeModel);
                                    employee.child(phone).setValue(employeeModel);
                                    Toast.makeText(AddEmployeeActivity.this, "Sign Up Successfully", Toast.LENGTH_SHORT).show();
                                    finish();
                                }

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
            });
        }
    }
}