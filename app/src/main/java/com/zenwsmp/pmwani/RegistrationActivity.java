package com.zenwsmp.pmwani;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import com.hbb20.CountryCodePicker;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.ByteArrayEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;

public class RegistrationActivity extends AppCompatActivity {

    final String regexStr = "^(?:(?:\\+|0{0,2})91(\\s*[\\-]\\s*)?|[0]?)?[789]\\d{9}$";
    private static AsyncHttpClient client = new AsyncHttpClient();
    MaterialButton btn_register;
    TextView txt_sign_up;
    ImageView ivActionBack;
    EditText edt_contact,edt_fname,edt_lname,edt_email;
    CountryCodePicker ccp;
    TextView txt_mobile_error,txt_fname_error,txt_lname_error,txt_email_error;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        btn_register=findViewById(R.id.btn_register);
        txt_sign_up=findViewById(R.id.txt_sign_up);
        ivActionBack=findViewById(R.id.ivActionBack);
        edt_contact=findViewById(R.id.edt_contact);
        edt_fname=findViewById(R.id.edt_fname);
        edt_email=findViewById(R.id.edt_email);
        edt_lname=findViewById(R.id.edt_lname);
        ccp=findViewById(R.id.ccp);
        txt_mobile_error=findViewById(R.id.txt_mobile_error);
        txt_fname_error=findViewById(R.id.txt_fname_error);
        txt_lname_error=findViewById(R.id.txt_lname_error);
        txt_email_error=findViewById(R.id.txt_email_error);
        //ccp.setDefaultCountryUsingNameCode("+1");
        btn_register.setOnClickListener(v -> {
           /* Intent i = new Intent(RegistrationActivity.this,LoginActivity.class);
            startActivity(i);*/

            if(new connectionDector(this).isConnectingToInternet())
            {
                if(validate()){
                   Call_register();
                   /* Intent i = new Intent(RegistrationActivity.this,OTP_Activity.class);
                    i.putExtra("phone",edt_contact.getText().toString());
                    i.putExtra("cpp_code",ccp.getSelectedCountryCodeWithPlus());
                    startActivity(i);*/
                }
            }else{
                new ConfigAPI().ShowToastMessage(this,"No Internet Connection");
            }
        });

        txt_sign_up.setOnClickListener(v -> {
            Intent i = new Intent(RegistrationActivity.this,LoginActivity.class);
            startActivity(i);
        });
        ivActionBack.setOnClickListener(v -> {
            this.finish();
        });
    }

    private void Call_register(){

        try {
            JSONObject object1=new JSONObject();
            object1.put("token","11");
            object1.put("mobile",edt_contact.getText().toString());
            object1.put("full_name",edt_fname.getText().toString()+" "+edt_lname.getText().toString());
            object1.put("email",edt_email.getText().toString());
            object1.put("country_code",ccp.getSelectedCountryCodeWithPlus());
            object1.put("hash_code",getHashCode(RegistrationActivity.this));
            Log.d("param",object1.toString());
            Register(object1);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void Register(JSONObject jsonObject) throws UnsupportedEncodingException {

        Dialog dialog = new Dialog(RegistrationActivity.this);
        // Include dialog.xml file
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        // Set dialog title
        // dialog.setTitle("Custom Dialog");
        dialog.show();

        RequestParams params = new RequestParams();
        params.put("",jsonObject);
        // StringEntity entity = new StringEntity(params.toString());
        final ByteArrayEntity entity = new ByteArrayEntity(jsonObject.toString().getBytes("UTF-8"));
        entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        //  params.put("Password",jsonpassword);
        final String url= ConfigAPI.MAIN_URL+"signup";
        Log.d("url",url);
        //client.addHeader("Authorization","Bearer "+preferences.getString("accesstoken",null));
        client.setTimeout(20*1000);
        client.post(RegistrationActivity.this,url,entity,"application/json",new JsonHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.d("res",response.toString());

                try {

                    int responseCode=response.getInt("responseCode");
                    String responseMsg=response.getString("message");

                    if(responseCode==200){

                        Intent i = new Intent(RegistrationActivity.this,OTP_Activity.class);
                        i.putExtra("phone",edt_contact.getText().toString());
                        i.putExtra("cpp_code",ccp.getSelectedCountryCode());
                        startActivity(i);


                    }else
                    {

                        new ConfigAPI().ShowToastMessage(RegistrationActivity.this,responseMsg);
                    }

                    dialog.dismiss();
                    dialog.cancel();

                }
                catch (Exception e)
                {

                    e.printStackTrace();
                }
                // dialog.dismiss();
                //dialog.cancel();

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                dialog.dismiss();
                dialog.cancel();
                if(errorResponse!=null){
                    try{
                        int responseCode=errorResponse.getInt("responseCode");
                        new ConfigAPI().ShowToastMessage(RegistrationActivity.this,errorResponse.getString("message"));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else{
                    new ConfigAPI().ShowToastMessage(RegistrationActivity.this,"Something went wrong,Please try again later");
                }


            }
        });


    }

    public boolean validate() {
        boolean valid = true;
        /// edt_site_name,edt_site_description,edt_site_address1,edt_site_address2,edt_site_city,edt_site_state,edt_site_zipcode,edt_site_latitude,edt_site_longitude
        String fname=edt_fname.getText().toString();
        String lname=edt_lname.getText().toString();
        String email=edt_email.getText().toString();


        if (fname.isEmpty()) {
            txt_fname_error.setText("Firstname is required.");
            txt_fname_error.setVisibility(View.VISIBLE);
            valid = false;
        } else {
            txt_fname_error.setText("");
            txt_fname_error.setVisibility(View.GONE);
        }


        if (lname.isEmpty()) {
            txt_lname_error.setText("Lastname is required.");
            txt_lname_error.setVisibility(View.VISIBLE);
            valid = false;
        } else {
            txt_lname_error.setText("");
            txt_lname_error.setVisibility(View.GONE);
        }
        if (!email.isEmpty()) {
            if(!isValidEmail(email)){
                txt_email_error.setText("Invalid Email Address.");
                txt_email_error.setVisibility(View.VISIBLE);
                valid = false;
            }

        } else {
            txt_email_error.setText("");
            txt_email_error.setVisibility(View.GONE);
        }

        String phone=edt_contact.getText().toString();
        if(!phone.matches(regexStr)){
            txt_mobile_error.setText("Invalid Mobile Number.");
            txt_mobile_error.setVisibility(View.VISIBLE);
            valid = false;
        } else {
            txt_mobile_error.setText("");
            txt_mobile_error.setVisibility(View.GONE);
        }


        return valid;
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
    public static String getHashCode(Context context){
        AppSignatureHelper appSignature = new AppSignatureHelper(context);
        Log.e(" getAppSignatures ",""+appSignature.getAppSignatures());
        return appSignature.getAppSignatures().get(0);

    }
}
