package com.zenwsmp.pmwani;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.button.MaterialButton;

public class Startup_Activity extends AppCompatActivity {

    MaterialButton btn_login_phone,btn_register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);

        btn_login_phone=findViewById(R.id.btn_login_phone);
        btn_register=findViewById(R.id.btn_register);

        btn_login_phone.setOnClickListener(v -> {

            Intent i = new Intent(Startup_Activity.this,LoginActivity.class);
            startActivity(i);
            Log.d("wifiState", String.valueOf(wifiState()));
        });

        btn_register.setOnClickListener(v -> {

            //Intent i = new Intent(Startup_Activity.this,RegistrationActivity.class);
            Intent i = new Intent(Startup_Activity.this,RegistrationActivity.class);
            startActivity(i);
        });
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }
     boolean wifiState() {
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        return wifiManager.isWifiEnabled();
    }
}