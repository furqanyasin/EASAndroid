package com.example.easandroid;

import android.app.Activity;
import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import com.example.easandroid.Models.Attendance;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@RequiresApi(api = Build.VERSION_CODES.M)
public class CheckOutHandler extends FingerprintManager.AuthenticationCallback {
    private Context context;
    String name, phone, date, time;




    public CheckOutHandler(Context context) {

        this.context = context;
    }


    public void startAuth(FingerprintManager fingerprintManager, FingerprintManager.CryptoObject cryptoObject) {

        CancellationSignal cancellationSignal = new CancellationSignal();
        fingerprintManager.authenticate(cryptoObject, cancellationSignal, 0, this, null);

    }

    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString) {

        this.update("There was an Auth Error. " + errString, false);

    }

    @Override
    public void onAuthenticationFailed() {

        this.update("Auth Failed. ", false);

    }

    @Override
    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {

        this.update("Error: " + helpString, false);

    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {

        this.update("Attendance Marked!", true);
        //this.getCheckOutTime();

    }


    private void update(String s, boolean b) {

        TextView paraLabel = ((Activity) context).findViewById(R.id.paraLabel);
        ImageView imageView = ((Activity) context).findViewById(R.id.fingerprintImage);

        paraLabel.setText(s);

        if (b == false) {

            paraLabel.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));

        } else {

            paraLabel.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
            imageView.setImageResource(R.drawable.ic_fingerprint_green);

        }

    }

 /*   public void getCheckOutTime() {

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Attendance");

       // Attendance attendance = new Attendance (date, time, name, phone);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss  a");
        String time = "Check Out Time: " + timeFormat.format(calendar.getTime());

        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());


        //databaseReference.setValue(time);
        databaseReference.child(currentDate).setValue(attendance);

        TextView textView = ((Activity) context).findViewById(R.id.tv_get_time);
        textView.setText(time);
    }*/


}