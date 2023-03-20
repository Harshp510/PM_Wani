package com.zenwsmp.pmwani;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

public class DashboardActivity extends AppCompatActivity {

    RelativeLayout wifi_disable_layout;
    Button btn_goto_wifisettings;
    WifiManager wifiManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        wifi_disable_layout =findViewById(R.id.wifi_disable_layout);
        btn_goto_wifisettings =findViewById(R.id.btn_goto_wifisettings);
        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if(wifiState()){
            wifi_disable_layout.setVisibility(View.GONE);
        }else{
            wifi_disable_layout.setVisibility(View.VISIBLE);
        }

        btn_goto_wifisettings.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
            {
                Intent panelIntent = new Intent(Settings.Panel.ACTION_WIFI);
                startActivityForResult(panelIntent, 545);
            }else{
                if(!wifiManager.isWifiEnabled()){
                    wifiManager.setWifiEnabled(true);
                }
            }
        });
    }

    boolean wifiState() {
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        return wifiManager.isWifiEnabled();
    }
}