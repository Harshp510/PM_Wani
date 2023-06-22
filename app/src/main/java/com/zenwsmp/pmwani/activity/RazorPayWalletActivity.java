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


public class RazorPayWalletActivity extends AppCompatActivity {

   // private static final String TAG = RazorPayWalletActivity.class.getSimpleName() ;
    private String eventSessionId ="";
    private TicketSessionData eventSessionData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {

            setContentView(R.layout.activity_razorpay_debitcredit_view);

            // ACTIONBAR
            setUpToolbar();
            getIntentData();

            Button btnSubmit = findViewById(R.id.btnSubmit);
            btnSubmit.setOnClickListener(view -> {
                Intent intent = new Intent(RazorPayWalletActivity.this, RazorPayCustomActivity.class);
                intent.putExtra(Constant.ScreenExtras.PAYMENT_METHOD, Constant.RazorPayPaymentMethod.WALLET);
                intent.putExtra(Constant.ScreenExtras.EVENT_SESSION_ID, eventSessionId);
                intent.putExtra(Constant.ScreenExtras.TICKET_SESSION_DATA, eventSessionData);
                startActivity(intent);
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setUpToolbar(){
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
                tvTitle.setText(R.string.title_activity_wallet);
                Utility.convertToLowerCase(tvTitle);

                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
                    findViewById(R.id.toolbarBottomDivider).setVisibility(View.VISIBLE);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void getIntentData(){
        try {
            Intent i = getIntent();
            eventSessionId = i.getStringExtra(Constant.ScreenExtras.EVENT_SESSION_ID);
            eventSessionData = (TicketSessionData) i.getSerializableExtra(Constant.ScreenExtras.TICKET_SESSION_DATA);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

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
