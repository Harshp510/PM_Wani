package com.zenwsmp.pmwani.wifi;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Pair;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.zenwsmp.pmwani.wifi.accesspoint.AccessPointsAdapter;
import com.zenwsmp.pmwani.wifi.band.WiFiBand;
import com.zenwsmp.pmwani.wifi.band.WiFiChannel;
import com.zenwsmp.pmwani.wifi.permission.PermissionService;
import com.zenwsmp.pmwani.wifi.scanner.ScannerService;
import com.zenwsmp.pmwani.wifi.settings.Settings;

import com.zenwsmp.pmwani.R;
import com.zenwsmp.pmwani.wifi.util.BuildUtilsKt;


public class AccessPointsActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {


    private SwipeRefreshLayout swipeRefreshLayout;
    private AccessPointsAdapter accessPointsAdapter;
    private String currentCountryCode;
    ImageView groupIndicator_c,securityImage_c;
    TextView ssid_c,level_c,channel_c,primaryFrequency_c,distance_c,channel_frequency_range_c,width_c,
            vendorShort_c,capabilities_c,linkspeed_c,ip_address_c;

    PermissionService permissionService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.access_points_content);

        MainContext mainContext = MainContext.INSTANCE;
        mainContext.initialize(this, isLargeScreen());

        Settings settings = mainContext.getSettings();
        settings.initializeDefaultValues();

       // setTheme(settings.getThemeStyle().getThemeNoActionBar());

       // setWiFiChannelPairs(mainContext);
     //   permissionService = new PermissionService(this)
       /* groupIndicator_c=findViewById(R.id.groupIndicator_c);
        securityImage_c=findViewById(R.id.securityImage_c);

        ssid_c=findViewById(R.id.ssid_c);
        level_c=findViewById(R.id.level_c);
        channel_c=findViewById(R.id.channel_c);

        primaryFrequency_c=findViewById(R.id.primaryFrequency_c);
        distance_c=findViewById(R.id.distance_c);
        channel_frequency_range_c=findViewById(R.id.channel_frequency_range_c);
        width_c=findViewById(R.id.width_c);
        vendorShort_c=findViewById(R.id.vendorShort_c);
        capabilities_c=findViewById(R.id.capabilities_c);
        linkspeed_c=findViewById(R.id.linkspeed_c);
        ip_address_c=findViewById(R.id.ip_address_c);*/



        swipeRefreshLayout = findViewById(R.id.accessPointsRefresh);
        swipeRefreshLayout.setOnRefreshListener(this);
        if (BuildUtilsKt.buildMinVersionP()) {
            swipeRefreshLayout.setRefreshing(false);
            swipeRefreshLayout.setEnabled(false);
        }


        ExpandableListView expandableListView = findViewById(R.id.accessPointsView);
        accessPointsAdapter=new AccessPointsAdapter();
        expandableListView.setAdapter(accessPointsAdapter);
        accessPointsAdapter.setExpandableListView(expandableListView);
        ScannerService service = MainContext.INSTANCE.getScannerService();
        service.resume();
        MainContext.INSTANCE.getScannerService().register(accessPointsAdapter);


    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        MainContext.INSTANCE.getScannerService().update();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        ScannerService service = MainContext.INSTANCE.getScannerService();
        service.resume();
        onRefresh();
    }

    @Override
    protected void onPause() {
        super.onPause();
        ScannerService service = MainContext.INSTANCE.getScannerService();
        service.stop();
        MainContext.INSTANCE.getScannerService().unregister(accessPointsAdapter);
    }

    @Override
    public void onDestroy() {
        ScannerService service = MainContext.INSTANCE.getScannerService();
        service.stop();
        MainContext.INSTANCE.getScannerService().unregister(accessPointsAdapter);
        super.onDestroy();
    }

    AccessPointsAdapter getAccessPointsAdapter() {
        return accessPointsAdapter;
    }


    private boolean isLargeScreen() {
        Configuration configuration = getResources().getConfiguration();
        int screenLayoutSize = configuration.screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;
        return screenLayoutSize == Configuration.SCREENLAYOUT_SIZE_LARGE ||
                screenLayoutSize == Configuration.SCREENLAYOUT_SIZE_XLARGE;
    }

  /*  private void setWiFiChannelPairs(MainContext mainContext) {
        Settings settings = mainContext.getSettings();
        String countryCode = settings.countryCode();
        if (!countryCode.equals(currentCountryCode)) {
            Pair<WiFiChannel, WiFiChannel> pair = WiFiBand.GHZ5.getWiFiChannels().wiFiChannelPairFirst(countryCode);
            mainContext.getConfiguration().wiFiChannelPair(currentCountryCode);
            currentCountryCode = countryCode;
        }
    }*/


/*    public void setHeader_Data(String str_ssid, String str_level, String str_channel, String str_primaryFrequency, String str_distance, String str_FrequencyStart, String str_FrequencyEnd,
                               String str_WiFiWidth, String str_VendorName, String str_Capabilities, String str_linkspeed, String str_ipaddress, Strength strength)
    {
        Log.d("CallActivity","method Header");


        ssid_c.setText(str_ssid);

        channel_c.setText(str_channel);

        primaryFrequency_c.setText(str_primaryFrequency+"MHz");
        distance_c.setText(str_distance);
        channel_frequency_range_c.setText(str_FrequencyStart +" - "+str_FrequencyEnd);

        width_c.setText(" ("+str_WiFiWidth+"MHz) ");
        vendorShort_c.setText(str_VendorName);
        capabilities_c.setText(str_Capabilities);

        linkspeed_c.setText(str_linkspeed+"Mbps");
        ip_address_c.setText(str_ipaddress);


        groupIndicator_c.setImageResource(strength.imageResource());
        groupIndicator_c.setColorFilter(ContextCompat.getColor(AccessPointsActivity.this, strength.colorResource()));

        level_c.setText(str_level+"dBm");
        if(strength.toString().equalsIgnoreCase("FOUR"))
        {
            level_c.setTextColor(Color.parseColor("#4CAF50"));
        }
        else if(strength.toString().equalsIgnoreCase("THREE"))
        {
            level_c.setTextColor(Color.parseColor("#4CAF50"));
        }
        else if(strength.toString().equalsIgnoreCase("TWO"))
        {
            level_c.setTextColor(Color.parseColor("#FFC107"));
        }
        else if(strength.toString().equalsIgnoreCase("ONE"))
        {
            level_c.setTextColor(Color.parseColor("#FFC107"));
        }
        else
        {
            level_c.setTextColor(Color.parseColor("#F44336"));
        }


        if(str_Capabilities.equalsIgnoreCase("[ESS]"))
        {
            securityImage_c.setImageResource(R.drawable.ic_lock_open);

        }
        else
        {
            securityImage_c.setImageResource(R.drawable.ic_lock);
        }



    }*/

}
