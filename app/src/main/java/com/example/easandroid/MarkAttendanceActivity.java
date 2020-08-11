package com.example.easandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.KeyguardManager;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easandroid.Models.Attendance;
import com.example.easandroid.Models.EmployeeModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class MarkAttendanceActivity extends AppCompatActivity {
    private TextView mHeadingLabel;
    private ImageView mFingerprintImage;
    private TextView mParaLabel, getTime;
    private Button btnCheckIn, btnCheckOut;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;

    String name, phone, date, time;


    private FingerprintManager fingerprintManager;
    private KeyguardManager keyguardManager;

    private KeyStore keyStore;
    private Cipher cipher;
    private String KEY_NAME = "AndroidKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_attendance);

        //init firebase
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Attendance");
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        mHeadingLabel = findViewById(R.id.headingLabel);
        mFingerprintImage = findViewById(R.id.fingerprintImage);
        mParaLabel = findViewById(R.id.paraLabel);
        btnCheckIn = findViewById(R.id.btn_check_in);
        btnCheckOut = findViewById(R.id.btn_check_out);
        getTime = findViewById(R.id.tv_get_time);

        btnCheckIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkInPermissions();
                //addCheckIn();

            }
        });

        btnCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkOutPermissions();


            }

        });


    }

/*    private void addCheckIn() {
        date = getTime.getText().toString();
        time = getTime.getText().toString();

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //check if user not exit in database


                final Attendance attendance = new Attendance(date, time, name, phone);
                if (attendance != null) {
                    //employee.push().setValue(newEmployeeModel);
                    databaseReference.child(name).child("checkIn").setValue(date, time);
                    Toast.makeText(MarkAttendanceActivity.this, "Attendance Marked as Check In", Toast.LENGTH_SHORT).show();
                    finish();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }*/

    private void checkOutPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            fingerprintManager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);
            keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);

            if (!fingerprintManager.isHardwareDetected()) {

                mParaLabel.setText("Fingerprint Scanner not detected in Device");

            } else if (ContextCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {

                mParaLabel.setText("Permission not granted to use Fingerprint Scanner");

            } else if (!keyguardManager.isKeyguardSecure()) {

                mParaLabel.setText("Add Lock to your Phone in Settings");

            } else if (!fingerprintManager.hasEnrolledFingerprints()) {

                mParaLabel.setText("You should add atleast 1 Fingerprint to use this Feature");

            } else {

                mParaLabel.setText("Place your Finger on Scanner to\n Check Out.");

                generateKey();

                if (cipherInit()) {

                    FingerprintManager.CryptoObject cryptoObject = new FingerprintManager.CryptoObject(cipher);
                    CheckOutHandler checkOutHandler = new CheckOutHandler(this);
                    checkOutHandler.startAuth(fingerprintManager, cryptoObject);

                }
            }

        }
    }

    private void checkInPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            fingerprintManager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);
            keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);

            if (!fingerprintManager.isHardwareDetected()) {

                mParaLabel.setText("Fingerprint Scanner not detected in Device");

            } else if (ContextCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {

                mParaLabel.setText("Permission not granted to use Fingerprint Scanner");

            } else if (!keyguardManager.isKeyguardSecure()) {

                mParaLabel.setText("Add Lock to your Phone in Settings");

            } else if (!fingerprintManager.hasEnrolledFingerprints()) {

                mParaLabel.setText("You should add atleast 1 Fingerprint to use this Feature");

            } else {

                mParaLabel.setText("Place your Finger on Scanner to \n Check In");

                generateKey();

                if (cipherInit()) {

                    FingerprintManager.CryptoObject cryptoObject = new FingerprintManager.CryptoObject(cipher);
                    CheckInHandler checkInHandler = new CheckInHandler(this);
                    checkInHandler.startAuth(fingerprintManager, cryptoObject);

                }
            }

        }
    }


    @TargetApi(Build.VERSION_CODES.M)
    private void generateKey() {

        try {

            keyStore = KeyStore.getInstance("AndroidKeyStore");
            KeyGenerator keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");

            keyStore.load(null);
            keyGenerator.init(new
                    KeyGenParameterSpec.Builder(KEY_NAME,
                    KeyProperties.PURPOSE_ENCRYPT |
                            KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(
                            KeyProperties.ENCRYPTION_PADDING_PKCS7)
                    .build());
            keyGenerator.generateKey();

        } catch (KeyStoreException | IOException | CertificateException
                | NoSuchAlgorithmException | InvalidAlgorithmParameterException
                | NoSuchProviderException e) {

            e.printStackTrace();

        }

    }

    @TargetApi(Build.VERSION_CODES.M)
    public boolean cipherInit() {
        try {
            cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/" + KeyProperties.BLOCK_MODE_CBC + "/" + KeyProperties.ENCRYPTION_PADDING_PKCS7);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new RuntimeException("Failed to get Cipher", e);
        }


        try {

            keyStore.load(null);

            SecretKey key = (SecretKey) keyStore.getKey(KEY_NAME,
                    null);

            cipher.init(Cipher.ENCRYPT_MODE, key);

            return true;

        } catch (KeyPermanentlyInvalidatedException e) {
            return false;
        } catch (KeyStoreException | CertificateException | UnrecoverableKeyException | IOException | NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Failed to init Cipher", e);
        }

    }


}