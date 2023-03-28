package com.zenwsmp.pmwani;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

public class Account_Activity extends AppCompatActivity {

    LinearLayout layout_addvouchercode;
    CardView basic_detail_card;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        layout_addvouchercode =findViewById(R.id.layout_addvouchercode);
        basic_detail_card =findViewById(R.id.basic_detail_card);


        layout_addvouchercode.setOnClickListener(v -> {
            Intent i =new Intent(Account_Activity.this,AddVoucherCode_Activity.class);
            startActivity(i);
        });

        basic_detail_card.setOnClickListener(v -> {
            Intent i =new Intent(Account_Activity.this,EditPersonalDetail_Activity.class);
            startActivity(i);
        });
    }
}