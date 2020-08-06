package com.example.easandroid;

import android.app.Activity;
import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import com.example.easandroid.Models.Attendance;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@RequiresApi(api = Build.VERSION_CODES.M)
public class CheckInHandler extends FingerprintManager.AuthenticationCallback {
    private Context context;
    String date, mtime;

    public CheckInHandler(Context context) {

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
        this.getCheckInTime();


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

    public void getCheckInTime() {

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = firebaseDatabase.getReference("Attendance");

        Attendance attendance = new Attendance(date, mtime, null, null);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss  a");
        final String time = "Check In Time: " + timeFormat.format(calendar.getTime());

        final String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());


        //databaseReference.setValue(time);
        databaseReference.child(currentDate).setValue(attendance);

        TextView mtime = ((Activity) context).findViewById(R.id.tv_get_time);
        mtime.setText(time);

    }


}