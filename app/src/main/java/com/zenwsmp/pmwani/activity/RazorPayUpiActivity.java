package com.zenwsmp.pmwani.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;

import com.zenwsmp.pmwani.R;
import com.zenwsmp.pmwani.common.Constant;
import com.zenwsmp.pmwani.common.Utility;
import com.zenwsmp.pmwani.objects.TicketSessionData;


public class RazorPayUpiActivity extends AppCompatActivity {

    private String eventSessionId = "";
    private String planid = "";
    private String planname = "";
    private TicketSessionData eventSessionData;
    //private GridView recyclerView;
    //private ArrayList<String> stringArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_razorpay_upi_view);

            // ACTIONBAR
            setUpToolbar();
 
            getIntentData();

            //recyclerView = findViewById(R.id.recyclerView);
            //setAdapter();

            Intent intent = new Intent(RazorPayUpiActivity.this, RazorPayCustomActivity.class);
            intent.putExtra(Constant.ScreenExtras.BANK_NAME, "");
            intent.putExtra("planid",planid);
            intent.putExtra("planname",planname);
            intent.putExtra(Constant.ScreenExtras.PAYMENT_METHOD, Constant.RazorPayPaymentMethod.UPI);
            intent.putExtra(Constant.ScreenExtras.EVENT_SESSION_ID, eventSessionId);
            intent.putExtra(Constant.ScreenExtras.TICKET_SESSION_DATA,eventSessionData);
            startActivity(intent);
            finish();

            Button btnSubmit = findViewById(R.id.btnSubmit);
            btnSubmit.setVisibility(View.VISIBLE);
            btnSubmit.setOnClickListener(view -> {
           /* Intent intent = new Intent(RazorPayUpiActivity.this, RazorPayCustomActivity.class);
            intent.putExtra(Constant.ScreenExtras.BANK_NAME, "");
            intent.putExtra(Constant.ScreenExtras.PAYMENT_METHOD, Constant.RazorPayPaymentMethod.UPI);
            intent.putExtra(Constant.ScreenExtras.EVENT_SESSION_ID, eventSessionId);
            intent.putExtra(Constant.ScreenExtras.TICKET_SESSION_DATA,eventSessionData);
            startActivity(intent);*/
            });
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void setUpToolbar() {
        try {
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                getSupportActionBar().setDisplayShowHomeEnabled(false);
                ViewCompat.setElevation(toolbar, 10);

                ImageView ivActionBack = toolbar.findViewById(R.id.ivActionBack);
                ivActionBack.setOnClickListener(v -> onBackPressed());
                TextView tvTitle = toolbar.findViewById(R.id.tvTitle);
               // tvTitle.setText(R.string.select_upi_method);
                Utility.convertToLowerCase(tvTitle);

                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
                    findViewById(R.id.toolbarBottomDivider).setVisibility(View.VISIBLE);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    private void getIntentData() {
        try {
            Intent i = getIntent();
            planid = i.getStringExtra("planid");
            planname = i.getStringExtra("planname");
            eventSessionId = i.getStringExtra(Constant.ScreenExtras.EVENT_SESSION_ID);
            eventSessionData = (TicketSessionData) i.getSerializableExtra(Constant.ScreenExtras.TICKET_SESSION_DATA);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /*private void setAdapter() {
        try {
            stringArrayList.add("BHIM");
            stringArrayList.add("Google Pay");
            stringArrayList.add("WhatsApp");
            stringArrayList.add("Paytm");
            stringArrayList.add("PhonePe");
            RazorPayUpiAdapter adapter = new RazorPayUpiAdapter(this, stringArrayList);
            recyclerView.setAdapter(adapter);
        }catch (Exception e){
            e.printStackTrace();
        }
    }*/


    @Override
    public void onBackPressed() {
        try {
            Utility.hideKeyboard(this);
            super.onBackPressed();
        }catch (Exception e){
            e.printStackTrace();
        }

    }


}
