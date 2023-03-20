package com.zenwsmp.pmwani;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.google.android.material.button.MaterialButton;

public class AddVoucherCode_Activity extends AppCompatActivity {


    ImageView ivActionBack;
    MaterialButton btn_apply;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_voucher_code);

        ivActionBack=findViewById(R.id.ivActionBack);
        btn_apply=findViewById(R.id.btn_apply);

        ivActionBack.setOnClickListener(v -> {
            this.finish();
        });
        btn_apply.setOnClickListener(v -> {
            this.finish();
        });
    }
}