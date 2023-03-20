package com.zenwsmp.pmwani;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.google.android.material.button.MaterialButton;

public class LoginWith2FA_Activity extends AppCompatActivity {

    ImageView ivActionBack;
    MaterialButton btn_verify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_with2_fa);
        ivActionBack=findViewById(R.id.ivActionBack);
        btn_verify=findViewById(R.id.btn_verify);

        ivActionBack.setOnClickListener(v -> {
            this.finish();
        });
        btn_verify.setOnClickListener(v -> {
            Intent i = new Intent(LoginWith2FA_Activity.this,Setup2FA_Activity.class);
            startActivity(i);
        });
    }
}