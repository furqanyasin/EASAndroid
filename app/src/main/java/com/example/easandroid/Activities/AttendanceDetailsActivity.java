package com.example.easandroid.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.easandroid.Models.AttendaneDataBean;
import com.example.easandroid.Models.DataLogin;
import com.example.easandroid.R;
import com.example.easandroid.Utils.DateManager;

public class AttendanceDetailsActivity extends AppCompatActivity {
    //ToolBar
    Toolbar toolbar;

    //TextViews
    TextView tvUserName,tvDate,tvInTime,tvInTimeLocation,tvOutTime,
            tvOutTimeLocation,tvInTimeGeoLocation,tvOutTimeGeoLocation;

    //CardViews
    CardView cvAttendanceGeoLocation;

    //Layouts
    LinearLayout llIn,llOut,llData;

    AttendaneDataBean attendaneDataBean;
    DataLogin dataLogin;

    String date,intime,outTime,inTimeLocation,outTimeLocation;
    String inTimeLatitude,outTimeLatitude,inTimeLongitude,outTimeLongitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_details);
        initializeViews();


        toolbar.setTitle("Attendance Details");

        if (getIntent().getSerializableExtra("AttendaneDataBean")!=null){
            attendaneDataBean = (AttendaneDataBean) getIntent().getSerializableExtra("AttendaneDataBean");
            dataLogin = (DataLogin) getIntent().getSerializableExtra("DataLogin");
        }

        //data
        try{
            //assign data
            date = attendaneDataBean.getAttendanceDate();
            intime = attendaneDataBean.getTimeIN();
            inTimeLocation = attendaneDataBean.getLocationIn();
            outTime = attendaneDataBean.getTimeOut();
            outTimeLocation = attendaneDataBean.getLocationOut();
            inTimeLatitude = attendaneDataBean.getLatitudeIn();
            inTimeLongitude = attendaneDataBean.getLongitudeIn();
            System.out.println("in time longitude "+ inTimeLongitude);
            outTimeLatitude = attendaneDataBean.getLatitudeOut();
            outTimeLongitude = attendaneDataBean.getLongitudeOut();

            int start = date.indexOf("(");
            int end = date.indexOf(")");
            String attendanceDate = date.substring(start+1,end);
            tvDate.setText(new DateManager().getDateAccordingToMil(Long.parseLong(attendanceDate), "yyyy/MM/dd"));

            start = intime.indexOf("(");
            end = intime.indexOf(")");
            intime = intime.substring(start+1,end);
            tvInTime.setText(new DateManager().getDateAccordingToMil(Long.parseLong(intime), "HH:mm:ss"));

            start = outTime.indexOf("(");
            end = outTime.indexOf(")");
            outTime = outTime.substring(start+1,end);

            if (intime.equals(outTime)){
                tvOutTime.setText("Not Available");
            }else{
                tvOutTime.setText(new DateManager().getDateAccordingToMil(Long.parseLong(outTime), "HH:mm:ss"));
            }

            //set Data
            tvUserName.setText(dataLogin.getName());
            if (inTimeLocation.equals("")){
                inTimeLocation = "Not Available";
            }
            if ( outTimeLocation.equals("")){
                outTimeLocation = "Not Available";
            }

            tvInTimeLocation.setText(inTimeLocation);
            tvOutTimeLocation.setText(outTimeLocation);

            final double inLon,inLat,outLon,outLat;
            if (TextUtils.isEmpty(inTimeLatitude) || TextUtils.isEmpty(inTimeLongitude)){
                inTimeLatitude = "0";
                inTimeLongitude = "0";
            }

            if (TextUtils.isEmpty(outTimeLongitude) || TextUtils.isEmpty(outTimeLatitude)){
                outTimeLatitude = "0";
                outTimeLongitude = "0";
            }

            inLon = Double.parseDouble(inTimeLongitude);
            inLat = Double.parseDouble(inTimeLatitude);
            outLon = Double.parseDouble(outTimeLongitude);
            outLat = Double.parseDouble(outTimeLatitude);

            //View Geo Locations


        }catch (Exception e){
            System.out.println("=========== Error wehen assign data");
            e.printStackTrace();
        }


    }

    private void initializeViews() {
        //ToolBar
        toolbar = findViewById(R.id.tb_main);

        //TextViews
        tvUserName = findViewById(R.id.tvUserName);
        tvDate = findViewById(R.id.tvDate);
        tvInTime = findViewById(R.id.tvInTime);
        tvInTimeLocation = findViewById(R.id.tvInTimeLocation);
        tvOutTime = findViewById(R.id.tvOutTime);
        tvOutTimeLocation = findViewById(R.id.tvOutTimeLocation);

        llData = findViewById(R.id.llData);
    }

    private void displayStatusMessage(String s, int colorValue) {

        AlertDialog.Builder builder = null;
        View view = null;
        TextView tvOk, tvMessage;
        ImageView imageView;

        int defaultColor = R.color.textGray;
        int successColor = R.color.successColor; // 1
        int errorColor = R.color.errorColor; // 2
        int warningColor = R.color.warningColor; // 3

        int success = R.drawable.ic_success;
        int error_image = R.drawable.ic_error;
        int warning_image = R.drawable.ic_warning;
        //1,2,3

        int color = defaultColor;
        int img = success;
        if (colorValue == 1) {
            color = successColor;
            img = success;

        } else if (colorValue == 2) {
            color = errorColor;
            img = error_image;

        } else if (colorValue == 3) {
            color = warningColor;
            img = warning_image;
        }

        builder = new AlertDialog.Builder(AttendanceDetailsActivity.this);
        view = getLayoutInflater().inflate(R.layout.layout_for_custom_message, null);

        tvOk = (TextView) view.findViewById(R.id.tvOk);
        tvMessage = (TextView) view.findViewById(R.id.tvMessage);
        imageView = (ImageView) view.findViewById(R.id.iv_status);

        tvMessage.setTextColor(getResources().getColor(color));
        tvMessage.setText(s);
        imageView.setImageResource(img);


        builder.setView(view);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

    }
}
