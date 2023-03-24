package com.zenwsmp.pmwani;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.MySSLSocketFactory;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.Collection;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.ByteArrayEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;

public class DashboardActivity extends AppCompatActivity {


    WifiManager wifiManager;
    boolean mStopHandler = false;
    Handler handler = new Handler();
    TextView txt_error_notes;
    private TextView txt_tool_strength_name, txt_tool_network_name, txt_tool_bssid_name,txt_tool_ip_name;
    private TextView ll_devices_title;
    LinearLayout ll_devices;
    ImageView img_settings;
    SharedPreferences sp_userdetail;
    String Str_Userid;
    String Str_UserName;
    String Str_Password;
    String Str_Rudder_Id;
    String Str_cloud_id;
    String Str_Device_uuid;
    private static AsyncHttpClient client;
    private boolean backPressedToExitOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        ll_devices=findViewById(R.id.ll_devices);
        ll_devices_title=findViewById(R.id.ll_devices_title);



        sp_userdetail = getSharedPreferences("userdetail.txt", Context.MODE_PRIVATE);
        client= new AsyncHttpClient();



        try {
            Str_Userid = sp_userdetail.getString("user_id", null);
            Str_UserName = sp_userdetail.getString("user_name", null);
            Str_Password = sp_userdetail.getString("password", null);
            Str_Rudder_Id = sp_userdetail.getString("rudder_id", null);
            Str_cloud_id = sp_userdetail.getString("cloud_id", null);

        }catch (Exception e)
        {
            e.printStackTrace();
        }

        txt_tool_ip_name= findViewById(R.id.txt_tool_ip_name);
        txt_error_notes = findViewById(R.id.txt_error_notes);

        txt_tool_strength_name = findViewById(R.id.txt_tool_strength_name);
        txt_tool_network_name = findViewById(R.id.txt_tool_network_name);
        txt_tool_bssid_name = findViewById(R.id.txt_tool_bssid_name);
        img_settings=findViewById(R.id.img_settings);

        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        Log.d("linkspeed", "" + wifiInfo.getLinkSpeed()+" "+WifiInfo.LINK_SPEED_UNITS);
        Log.d("linkspeed", "" + wifiInfo.getFrequency());


        img_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenu(v);


            }
        });




        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                // do your stuff - don't create a new runnable here!
                if (!mStopHandler) {
                    // WifiManager wifiManager = (WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                    if (wifiManager.isWifiEnabled()) {
                        WifiInfo wifiInfo = wifiManager.getConnectionInfo();

                        if (wifiInfo != null) {
                            int dbm = wifiInfo.getRssi();
                            Log.d("dbm", "" + dbm);

                            txt_tool_strength_name.setText("" + dbm);

                            txt_tool_network_name.setText(wifiInfo.getSSID());
                            txt_tool_bssid_name.setText(wifiInfo.getBSSID());
                            String ip = Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());
                            txt_tool_ip_name.setText(ip);

                            if (dbm >= -55) {
                                txt_tool_strength_name.setTextColor(getResources().getColor(R.color.wifigreen));
                                txt_tool_strength_name.setText(dbm +" dbm");
                                // img_singnal.setImageResource(R.drawable.ic_signal_wifi_4_bar_black_48dp);
                                //  img_singnal.setColorFilter(ContextCompat.getColor(ToolsActivity.this, R.color.wifigreen), android.graphics.PorterDuff.Mode.SRC_IN);
                            } else if (dbm < -55 && dbm >= -60) {
                                txt_tool_strength_name.setTextColor(getResources().getColor(R.color.wifigreen));
                                txt_tool_strength_name.setText(dbm +" dbm");
                                //  img_singnal.setImageResource(R.drawable.ic_signal_wifi_3_bar_black_48dp);
                                // img_singnal.setColorFilter(ContextCompat.getColor(ToolsActivity.this, R.color.wifigreen), android.graphics.PorterDuff.Mode.SRC_IN);
                            } else if (dbm < -60 && dbm >= -70) {
                                txt_tool_strength_name.setTextColor(getResources().getColor(R.color.wifiyellow));
                                txt_tool_strength_name.setText(dbm +" dbm");
                                //  img_singnal.setImageResource(R.drawable.ic_signal_wifi_2_bar_black_48dp);
                                //  img_singnal.setColorFilter(ContextCompat.getColor(ToolsActivity.this, R.color.wifiyellow), android.graphics.PorterDuff.Mode.SRC_IN);
                            } else if (dbm < -70 && dbm >= -80) {
                                txt_tool_strength_name.setTextColor(getResources().getColor(R.color.wifiyellow));
                                txt_tool_strength_name.setText(dbm +" dbm");
                                // img_singnal.setImageResource(R.drawable.ic_signal_wifi_1_bar_black_48dp);
                                // img_singnal.setColorFilter(ContextCompat.getColor(ToolsActivity.this, R.color.wifiyellow), android.graphics.PorterDuff.Mode.SRC_IN);
                            } else {
                                txt_tool_strength_name.setTextColor(getResources().getColor(R.color.wifired));
                                txt_tool_strength_name.setText("" + dbm);
                                //  img_singnal.setImageResource(R.drawable.ic_signal_wifi_0_bar_black_48dp);
                                // img_singnal.setColorFilter(ContextCompat.getColor(ToolsActivity.this, R.color.wifired), android.graphics.PorterDuff.Mode.SRC_IN);
                            }
                        }
                    }
                    handler.postDelayed(this, 2000);
                }
            }
        };

// start it with:
        handler.post(runnable);
    }




    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(Wifichangereceiver);
        mStopHandler=true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mStopHandler=false;


        registerReceiver(Wifichangereceiver, new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION));

        if(isLocationEnabled(DashboardActivity.this))
        {
            if (wifiManager.isWifiEnabled()) {
                ll_devices.setVisibility(View.VISIBLE);
                ll_devices_title.setVisibility(View.VISIBLE);
                //  ll_netinfo.setVisibility(View.VISIBLE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                if (wifiInfo != null) {
                    txt_tool_network_name.setText(wifiInfo.getSSID());
                    txt_tool_bssid_name.setText(wifiInfo.getBSSID());
                    String ip = Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());
                    txt_tool_ip_name.setText(ip);
                }
            } else {
                Log.i("Wi-Fi network state", "off");
                ll_devices.setVisibility(View.GONE);
                ll_devices_title.setVisibility(View.GONE);
                txt_error_notes.setVisibility(View.VISIBLE);
                //   ll_netinfo.setVisibility(View.INVISIBLE);
            }
        }
        else {
            AlertDialog.Builder builder =
                    new AlertDialog.Builder(this);
            builder.setTitle("Enable Loaction Service");
            builder.setMessage("Enable Location To Find Available Wi-Fi");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), 1);
                }
            });
            builder.setNegativeButton("CLOSE",(dialog, which) -> {
                ll_devices.setVisibility(View.GONE);
                ll_devices_title.setVisibility(View.GONE);
                txt_error_notes.setVisibility(View.VISIBLE);
                //  ll_netinfo.setVisibility(View.INVISIBLE);
            });
            builder.setCancelable(false);
            builder.show();

        }


    }






    public void showMenu(View v) {
        /*PopupMenu popup1 = new PopupMenu(this, v);
        popup1.setOnMenuItemClickListener(MainDashboard_Activity_QnUnGrid.this);
        popup1.inflate(R.menu.menu_example);

        popup1.show();*/



        PopupMenu popup = new PopupMenu(DashboardActivity.this, v);

        try {
            Field[] fields = popup.getClass().getDeclaredFields();
            for (Field field : fields) {
                if ("mPopup".equals(field.getName())) {
                    field.setAccessible(true);
                    Object menuPopupHelper = field.get(popup);
                    Class<?> classPopupHelper = Class.forName(menuPopupHelper.getClass().getName());
                    Method setForceIcons = classPopupHelper.getMethod("setForceShowIcon", boolean.class);
                    setForceIcons.invoke(menuPopupHelper, true);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        popup.getMenuInflater().inflate(R.menu.menu_qam, popup.getMenu());
        Menu menuOpts = popup.getMenu();

        menuOpts.findItem(R.id.menu_username).setTitle(Str_UserName);
        menuOpts.findItem(R.id.menu_mobile).setTitle(Str_Rudder_Id);


        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.logout:
                    {
                        setlogoutdata();
                        break;
                    }
                    case R.id.menu_mobile:
                    {

                        break;
                    }
                    case R.id.menu_username:
                    {

                        break;
                    }

                }
                return true;
            }
        });
        popup.show();

    }




   /* public void setlogout_url()
    {
        //{"rudder_id":"zenexim1"}
        try {


            JSONObject Rider_reg_info_Jobj=new JSONObject();

            Rider_reg_info_Jobj.put("rudder_id",Str_Rudder_Id);
            Rider_reg_info_Jobj.put("user_id",Str_Userid);
            Rider_reg_info_Jobj.put("type","logout");

            Log.d("Qnlogout_Jason",Rider_reg_info_Jobj.toString());

            Set_Logout_Url(Rider_reg_info_Jobj);


        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }*/

    /*private void Set_Logout_Url(JSONObject jsonObject) throws UnsupportedEncodingException
    {

        final Dialog dialog;


        dialog = new Dialog(Qam_Dashboard_Activity.this);
        // Include dialog.xml file
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        // Set dialog title
        // dialog.setTitle("Custom Dialog");
        dialog.show();
        RequestParams params = new RequestParams();
        params.put("",jsonObject);
        // StringEntity entity = new StringEntity(params.toString());
        final ByteArrayEntity entity = new ByteArrayEntity(jsonObject.toString().getBytes("UTF-8"));
        entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        //  params.put("Password",jsonpassword);
        final String url= ConfigQn.Main_URL+"update_qim_profile";
        Log.d("url",url);
        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(null, null);
            MyCustomSSLFactory socketFactory = new MyCustomSSLFactory(trustStore);
            socketFactory.setHostnameVerifier(MySSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            client.setSSLSocketFactory(socketFactory);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        client.setTimeout(20*1000);
        client.post(Qam_Dashboard_Activity.this,url,entity,"application/json",new JsonHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.d("res_logout",response.toString());

                try{

                    if(response.length()>0)
                    {
                        dialog.dismiss();
                        dialog.cancel();


                        if(response.getInt("responseCode")==200)
                        {

                            Log.d("Logout","success");
                            setlogoutdata();

                            //Toast.makeText(Services_WebView_Activity.this, "Successfully Disable Two Factor Authentication", Toast.LENGTH_LONG).show();


                        }
                        else
                        {


                            Toast.makeText(Qam_Dashboard_Activity.this, response.getString("responseMessage"), Toast.LENGTH_LONG).show();
                        }
                    }
                    else
                    {
                        dialog.dismiss();
                        dialog.cancel();

                        Toast.makeText(Qam_Dashboard_Activity.this, "something went wrong", Toast.LENGTH_LONG).show();
                    }


                }catch (Exception e){
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                dialog.dismiss();
                dialog.cancel();
                //Toast.makeText(Qam_Dashboard_Activity.this,"something went wrong",Toast.LENGTH_SHORT).show();
                setlogoutdata();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                dialog.dismiss();
                dialog.cancel();
                setlogoutdata();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                dialog.dismiss();
                dialog.cancel();
                setlogoutdata();
            }
        });

    }*/


    public void setlogoutdata()
    {

        SharedPreferences sp_detail=getSharedPreferences("userdetail.txt", MODE_PRIVATE);
        SharedPreferences.Editor editor=sp_detail.edit();
        editor.clear();
        editor.commit();


        handler.removeCallbacksAndMessages(null);
        //handler.removeCallbacks(runnable);
        this.finish();
        Intent intent=new Intent(DashboardActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(intent);
    }













    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1) {
            switch (requestCode) {
                case 1:
                    if (wifiManager.isWifiEnabled()) {
                        ll_devices.setVisibility(View.VISIBLE);
                        ll_devices_title.setVisibility(View.VISIBLE);
                        //   ll_netinfo.setVisibility(View.VISIBLE);
                        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                        if (wifiInfo != null) {
                            txt_tool_network_name.setText(wifiInfo.getSSID());
                            txt_tool_bssid_name.setText(wifiInfo.getBSSID());
                            String ip = Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());
                            txt_tool_ip_name.setText(ip);
                        }
                    } else {
                        Log.i("Wi-Fi network state", "off");
                        ll_devices.setVisibility(View.GONE);
                        ll_devices_title.setVisibility(View.GONE);
                        txt_error_notes.setVisibility(View.VISIBLE);
                        //  ll_netinfo.setVisibility(View.INVISIBLE);
                    }
                    break;
            }
        }
    }



    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
            Log.i("Wi-Fi network state", info.getDetailedState().toString());
        }
    };




    public BroadcastReceiver Wifichangereceiver=new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent) {

            int extraWifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE,
                    WifiManager.WIFI_STATE_UNKNOWN);
            Log.d("extraWifiState",""+extraWifiState);

            switch (extraWifiState) {
                case WifiManager.WIFI_STATE_DISABLED:
                    // txt_message.setVisibility(View.VISIBLE);
                    // ll_netinfo.setVisibility(View.GONE);
                    txt_error_notes.setVisibility(View.VISIBLE);
                    break;
                case WifiManager.WIFI_STATE_DISABLING:
                    //txt_message.setVisibility(View.VISIBLE);
                    // ll_netinfo.setVisibility(View.GONE);

                    txt_error_notes.setVisibility(View.VISIBLE);
                    break;
                case WifiManager.WIFI_STATE_ENABLED:
                    //Toast.makeText(context,"Wifi enabled",Toast.LENGTH_LONG).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                            Log.d("wifiInfo", wifiInfo.toString());
                            Log.d("SSID", wifiInfo.getSSID());

                            if (wifiInfo.getSSID() != null) {
                                String ip = Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());
                                txt_tool_ip_name.setText(ip);
                                txt_tool_network_name.setText(wifiInfo.getSSID());
                                txt_tool_bssid_name.setText(wifiInfo.getBSSID());
                                txt_error_notes.setVisibility(View.GONE);
                                //   ll_netinfo.setVisibility(View.VISIBLE);
                                if(!isLocationEnabled(DashboardActivity.this))
                                {
                                    txt_error_notes.setVisibility(View.VISIBLE);
                                    // ll_netinfo.setVisibility(View.GONE);
                                }

                            }
                        }
                    }, 1200);

                    break;
                case WifiManager.WIFI_STATE_ENABLING:
                    //Toast.makeText(context,"Wifi enabled", Toast.LENGTH_LONG).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                            Log.d("wifiInfo", wifiInfo.toString());
                            Log.d("SSID", wifiInfo.getSSID());

                            if (wifiInfo.getSSID() != null) {
                                String ip = Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());
                                txt_tool_ip_name.setText(ip);
                                txt_tool_network_name.setText(wifiInfo.getSSID());
                                txt_tool_bssid_name.setText(wifiInfo.getBSSID());
                                //  txt_message.setVisibility(View.GONE);
                                //  ll_netinfo.setVisibility(View.VISIBLE);
                                if(!isLocationEnabled(DashboardActivity.this))
                                {
                                    txt_error_notes.setVisibility(View.VISIBLE);
                                    //  ll_netinfo.setVisibility(View.GONE);
                                }
                            }
                        }
                    }, 1200);

                    break;
                case WifiManager.WIFI_STATE_UNKNOWN:
                    //  WifiState.setText("WIFI STATE UNKNOWN");
                    break;
            }
        }
    };

    public static boolean isLocationEnabled(Context context) {
        int locationMode = 0;
        String locationProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);

            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
            }

            return locationMode != Settings.Secure.LOCATION_MODE_OFF;

        }else{
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }


    }


    @Override
    public void onBackPressed() {
        //mStopHandler=true;
        if (backPressedToExitOnce) {
            //super.onBackPressed();
            moveTaskToBack(false);
            mStopHandler=true;
              /*  this.finish();
                Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                homeIntent.addCategory( Intent.CATEGORY_HOME );
                homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);*/

            Intent startMain = new Intent(Intent.ACTION_MAIN);
            startMain.addCategory(Intent.CATEGORY_HOME);
            startMain.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(startMain);
        }
        else
        {
            this.backPressedToExitOnce = true;
            Toast.makeText(DashboardActivity.this, "Press again to exit", Toast.LENGTH_LONG).show();
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    backPressedToExitOnce = false;


                }
            }, 3000);
        }
        //super.onBackPressed();

    }
}