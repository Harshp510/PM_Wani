package com.zenwsmp.pmwani;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import com.hbb20.CountryCodePicker;

public class LoginActivity extends AppCompatActivity {

    EditText edt_username,edt_Contact;
    LinearLayout layout_username,layout_mobilenumber;
    CountryCodePicker cpp;
    TextView txt_sign_up;
    MaterialButton btn_login;
    ImageView ivActionBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ivActionBack=findViewById(R.id.ivActionBack);
        edt_username = findViewById(R.id.edt_username);
        txt_sign_up = findViewById(R.id.txt_sign_up);
        layout_username = findViewById(R.id.layout_username);
        layout_mobilenumber = findViewById(R.id.layout_mobilenumber);
        edt_Contact = findViewById(R.id.edt_Contact);
        btn_login = findViewById(R.id.btn_login);
        cpp=findViewById(R.id.ccp);
        edt_username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
               /* if(!isValidEmail(edt_username.getText().toString().trim())){
                   // edt_username.setError("Enter a valid address");
                    layout_username.setBackgroundResource(R.drawable.edittext_outer_border_error);
                }else{
                    layout_username.setBackgroundResource(R.drawable.edittext_outer_border);
                }*/
            }
        });



        txt_sign_up.setOnClickListener(v -> {
            Intent i = new Intent(LoginActivity.this,RegistrationActivity.class);
            startActivity(i);
        });
        btn_login.setOnClickListener(v -> {
            Intent i = new Intent(LoginActivity.this,OTP_Activity.class);
            startActivity(i);
        });

        ivActionBack.setOnClickListener(v -> {
            this.finish();
        });
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }

    public final static boolean isValidEmail(String target) {
        if (target == null) {
            return false;
        } else {
            //android Regex to check the email address Validation
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

   /* boolean isvalidMobileNumber(String mobilenumber,String countrycode){
       // String swissNumberStr = "044 668 18 00";
        boolean valid = true;
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        try {
            Phonenumber.PhoneNumber swissNumberProto = phoneUtil.parse(mobilenumber, countrycode);
//  This will check if the phone number is real and it's length is valid.
            boolean isPossible = phoneUtil.isPossibleNumber(swissNumberProto);
            if(isPossible){
                valid = true;
            }else{
                valid = false;
            }
        } catch (NumberParseException e) {
            System.err.println("NumberParseException was thrown: " + e.toString());
        }
        return valid;
    }*/
}