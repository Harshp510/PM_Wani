package com.zenwsmp.pmwani;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.github.dhaval2404.colorpicker.MaterialColorPickerDialog;
import com.github.dhaval2404.colorpicker.listener.ColorListener;
import com.github.dhaval2404.colorpicker.listener.DismissListener;
import com.github.dhaval2404.colorpicker.model.ColorShape;
import com.github.dhaval2404.colorpicker.model.ColorSwatch;
import com.google.android.material.button.MaterialButton;

import org.jetbrains.annotations.NotNull;

public class ProfilePicture_Activity extends AppCompatActivity {

    private String mMaterialColorSquare = "";
    TextView txt_change_background;
    CardView card_round;
    MaterialButton btn_submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_picture);

        txt_change_background = findViewById(R.id.txt_change_background);
        card_round = findViewById(R.id.card_round);
        btn_submit = findViewById(R.id.btn_submit);

        txt_change_background.setOnClickListener(v -> {
            new MaterialColorPickerDialog
                    .Builder(ProfilePicture_Activity.this)
                    .setColorShape(ColorShape.CIRCLE) // Or ColorShape.CIRCLE
                    .setColorSwatch(ColorSwatch._300) // Default ColorSwatch._500
                    .setDefaultColor(mMaterialColorSquare) // Pass Default Color
                    .setColorListener(new ColorListener() {
                        @Override
                        public void onColorSelected(int color, @NotNull String colorHex) {
                            mMaterialColorSquare = colorHex;
                            card_round.setCardBackgroundColor(ColorStateList.valueOf(color));
                        }
                    })
                    .show();
        });

        btn_submit.setOnClickListener(v -> {
            Intent i = new Intent(ProfilePicture_Activity.this,PreparationSetup_Activity.class);
            startActivity(i);
        });
    }
}