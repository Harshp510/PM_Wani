package com.zenwsmp.pmwani;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

public class RegistrationActivity extends AppCompatActivity {

    MaterialButton btn_register;
    TextView txt_sign_up;
    ImageView ivActionBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        btn_register=findViewById(R.id.btn_register);
        txt_sign_up=findViewById(R.id.txt_sign_up);
        ivActionBack=findViewById(R.id.ivActionBack);

        btn_register.setOnClickListener(v -> {
            Intent i = new Intent(RegistrationActivity.this,LoginActivity.class);
            startActivity(i);
        });

        txt_sign_up.setOnClickListener(v -> {
            Intent i = new Intent(RegistrationActivity.this,LoginActivity.class);
            startActivity(i);
        });
        ivActionBack.setOnClickListener(v -> {
            this.finish();
        });
    }
}