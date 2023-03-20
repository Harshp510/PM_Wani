package com.zenwsmp.pmwani;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.google.android.material.button.MaterialButton;

public class Setup2FA_Activity extends AppCompatActivity {

    MaterialButton btn_submit;
    ImageView ivActionBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup2_fa);

        btn_submit=findViewById(R.id.btn_submit);
        ivActionBack=findViewById(R.id.ivActionBack);

        btn_submit.setOnClickListener(v -> {
            Intent i = new Intent(Setup2FA_Activity.this,ProfilePicture_Activity.class);
            startActivity(i);
        });

        ivActionBack.setOnClickListener(v -> {
            this.finish();
        });
    }
}