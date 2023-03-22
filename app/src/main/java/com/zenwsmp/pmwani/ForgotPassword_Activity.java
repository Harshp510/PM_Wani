package com.zenwsmp.pmwani;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hbb20.CountryCodePicker;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.ByteArrayEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;

public class ForgotPassword_Activity extends AppCompatActivity {

    CountryCodePicker ccp;
    EditText edt_Contact;
    TextView txt_mobile_error;
    Button btn_verify_forgot_password;
    final String regexStr = "^(?:(?:\\+|0{0,2})91(\\s*[\\-]\\s*)?|[0]?)?[789]\\d{9}$";
    private static AsyncHttpClient client = new AsyncHttpClient();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        btn_verify_forgot_password=findViewById(R.id.btn_verify_forgot_password);
        edt_Contact=findViewById(R.id.edt_Contact);
        txt_mobile_error=findViewById(R.id.txt_mobile_error);
        txt_mobile_error.setVisibility(View.GONE);
        ccp=findViewById(R.id.ccp);

        btn_verify_forgot_password.setOnClickListener(v->
        {

            if(new connectionDector(this).isConnectingToInternet())
            {
                if(validate()){
                    Call_Resend();
                   /* Intent i = new Intent(RegistrationActivity.this,OTP_Activity.class);
                    i.putExtra("phone",edt_contact.getText().toString());
                    i.putExtra("cpp_code",ccp.getSelectedCountryCodeWithPlus());
                    startActivity(i);*/
                }
            }else{
                new ConfigAPI().ShowToastMessage(this,"No Internet Connection");
            }
        });

    }

    private void Call_Resend(){

        try {
            JSONObject object1=new JSONObject();
            object1.put("token","11");
            object1.put("mobile",edt_Contact.getText().toString());
            // object1.put("otp",otp);

            object1.put("country_code",ccp.getSelectedCountryCodeWithPlus().toString());
            object1.put("hash_code",getHashCode(ForgotPassword_Activity.this));
            Log.d("param",object1.toString());
            OTP_Resend(object1);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static String getHashCode(Context context){
        AppSignatureHelper appSignature = new AppSignatureHelper(context);
        Log.e(" getAppSignatures ",""+appSignature.getAppSignatures());
        return appSignature.getAppSignatures().get(0);

    }
    private void OTP_Resend(JSONObject jsonObject) throws UnsupportedEncodingException {

        Dialog dialog = new Dialog(ForgotPassword_Activity.this);
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
        final String url= ConfigAPI.MAIN_URL+"otp/send";
        Log.d("url",url);
        //client.addHeader("Authorization","Bearer "+preferences.getString("accesstoken",null));
        client.setTimeout(20*1000);
        client.post(ForgotPassword_Activity.this,url,entity,"application/json",new JsonHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.d("res",response.toString());

                try {

                    int responseCode=response.getInt("responseCode");
                    String responseMsg=response.getString("message");

                    if(responseCode == 200){

                        new ConfigAPI().ShowToastMessage(ForgotPassword_Activity.this,responseMsg);
                        Intent i = new Intent(ForgotPassword_Activity.this,OTP_Activity.class);
                        i.putExtra("phone",edt_Contact.getText().toString());
                        i.putExtra("cpp_code",ccp.getSelectedCountryCodeWithPlus());
                        i.putExtra("isforgot",true);
                        startActivity(i);


                    }else
                    {

                        new ConfigAPI().ShowToastMessage(ForgotPassword_Activity.this,responseMsg);
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
                        new ConfigAPI().ShowToastMessage(ForgotPassword_Activity.this,errorResponse.getString("message"));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else{
                    new ConfigAPI().ShowToastMessage(ForgotPassword_Activity.this,"Something went wrong,Please try again later");
                }


            }
        });


    }

    public boolean validate() {
        boolean valid = true;
        /// edt_site_name,edt_site_description,edt_site_address1,edt_site_address2,edt_site_city,edt_site_state,edt_site_zipcode,edt_site_latitude,edt_site_longitude

        String phone=edt_Contact.getText().toString();
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
}