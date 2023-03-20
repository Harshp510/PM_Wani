package com.zenwsmp.pmwani;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.material.button.MaterialButton;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class OTP_Activity extends AppCompatActivity {

   // private static final int REQ_USER_CONSENT = 200;
   // SmsBroadcastReceiver smsBroadcastReceiver;
    TextView txt_timer;
    DecimalFormat formater = new DecimalFormat("00");
    CountDownTimer timer;
    MaterialButton btn_verify;
    TextView txt_resend_code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        txt_timer= findViewById(R.id.txt_timer);
        btn_verify= findViewById(R.id.btn_verify);
        txt_resend_code= findViewById(R.id.txt_resend_code);
        //startSmartUserConsent();

        timer = new CountDownTimer(45000, 1000) {

            public void onTick(long millisUntilFinished) {
                txt_resend_code.setEnabled(false);
                txt_timer.setText(getResources().getString(R.string.resend_code_in) +" "+(formater.format(millisUntilFinished / 60000))+":"+(formater.format(millisUntilFinished % 60000 / 1000)));
                //here you can have your logic to set text to edittext
            }

            public void onFinish() {
                txt_timer.setText("done!");
                txt_resend_code.setEnabled(true);
            }

        };
        timer.start();

        txt_resend_code.setOnClickListener(v -> {
            timer.start();
        });

        btn_verify.setOnClickListener(v -> {
            Intent i = new Intent(OTP_Activity.this,DashboardActivity.class);
            startActivity(i);
        });

    }


}