package com.zenwsmp.pmwani;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.google.android.material.button.MaterialButton;

public class EditPersonalDetail_Activity extends AppCompatActivity {

    MaterialButton btn_save;
    ImageView ivActionBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_personal_detail);

        btn_save = findViewById(R.id.btn_save);
        ivActionBack = findViewById(R.id.ivActionBack);

        btn_save.setOnClickListener(v -> this.finish());
        ivActionBack.setOnClickListener(v -> {
            this.finish();
        });
    }
}