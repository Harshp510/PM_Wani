package com.zenwsmp.pmwani;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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

public class Change_Password_Activity extends AppCompatActivity {

    private static AsyncHttpClient client = new AsyncHttpClient();
    EditText edt_old_password,edt_new_password,edt_confirm_password;
    TextView txt_oldpass_error,txt_newpass_error,txt_confirmpass_error;
    Button btn_submit_change_password;
    TextView txt_setpassword_label;
    boolean isregister;
    boolean isforgot;
    String phone,cpp_code;
    LinearLayout layout_old_password;
    String Str_cpp;
    String Str_UserName;
    String Str_email;
    String Str_password;
    String Str_fullname;
    String Str_mobile;
    SharedPreferences sp_userdetail;
    ImageView ivActionBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        sp_userdetail = getSharedPreferences("userdetail.txt", Context.MODE_PRIVATE);

        try {
            //Str_Userid = sp_userdetail.getString("user_id", null);
            Str_fullname = sp_userdetail.getString("full_name", null);
            Str_email = sp_userdetail.getString("email", null);
            Str_mobile = sp_userdetail.getString("mobile", null);
            Str_cpp = sp_userdetail.getString("cpp", null);
            Str_password = sp_userdetail.getString("password", null);

        }catch (Exception e)
        {
            e.printStackTrace();
        }
        isregister = getIntent().getBooleanExtra("isregister",false);
        isforgot = getIntent().getBooleanExtra("isforgot",false);
        edt_old_password=findViewById(R.id.edt_old_password);
        edt_new_password=findViewById(R.id.edt_new_password);
        edt_confirm_password=findViewById(R.id.edt_confirm_password);
        txt_setpassword_label=findViewById(R.id.txt_setpassword_label);
        layout_old_password=findViewById(R.id.layout_old_password);

        txt_oldpass_error=findViewById(R.id.txt_oldpass_error);
        txt_newpass_error=findViewById(R.id.txt_newpass_error);
        txt_confirmpass_error=findViewById(R.id.txt_confirmpass_error);
        ivActionBack=findViewById(R.id.ivActionBack);

        txt_oldpass_error.setVisibility(View.GONE);
        txt_newpass_error.setVisibility(View.GONE);
        txt_confirmpass_error.setVisibility(View.GONE);
        phone=getIntent().getStringExtra("phone");
        cpp_code=getIntent().getStringExtra("cpp_code");
        if(isregister){
            txt_setpassword_label.setText("Set Password");
            layout_old_password.setVisibility(View.GONE);
            txt_oldpass_error.setVisibility(View.GONE);
        }else if(isforgot){
            txt_setpassword_label.setText("Set Password");
            layout_old_password.setVisibility(View.GONE);
            txt_oldpass_error.setVisibility(View.GONE);
        }else{
            txt_setpassword_label.setText("Change Password");
            layout_old_password.setVisibility(View.VISIBLE);
            txt_oldpass_error.setVisibility(View.GONE);
        }
        ivActionBack.setOnClickListener(v -> onBackPressed());
        btn_submit_change_password=findViewById(R.id.btn_submit_change_password);


        btn_submit_change_password.setOnClickListener(v->
        {

            if(!isregister && !isforgot){
                if(new connectionDector(this).isConnectingToInternet())
                {
                    try {
                        JSONObject object1=new JSONObject();
                        object1.put("token",ConfigAPI.ACCESS_TOKEN);
                        // object1.put("country_code",cpp_code);
                        // object1.put("mobile",phone);
                        object1.put("password",edt_confirm_password.getText().toString().trim());
                        object1.put("old_password",edt_old_password.getText().toString().trim());
                        Log.d("object1",""+object1.toString());
                        Change_password_data_NEW(object1);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else{
                    new ConfigAPI().ShowToastMessage(this,"No Internet Connection");
                }
            }else{
                if(validate())
                {
                    if(edt_new_password.getText().toString().trim().equals(edt_confirm_password.getText().toString().trim()))
                    {
                        if(new connectionDector(this).isConnectingToInternet())
                        {
                            try {
                                JSONObject object1=new JSONObject();
                                object1.put("token",ConfigAPI.ACCESS_TOKEN);
                                object1.put("country_code",cpp_code);
                                object1.put("mobile",phone);
                                object1.put("password",edt_confirm_password.getText().toString().trim());
                                Log.d("object1",""+object1.toString());
                                Change_password_data(object1);
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }else{
                            new ConfigAPI().ShowToastMessage(this,"No Internet Connection");
                        }
                    }
                    else
                    {
                        new ConfigAPI().ShowToastMessage(this,"new password and confirm password does not match");
                    }
                }
            }




        });
    }




    public boolean validate() {
        boolean valid = true;
        String old_pass=edt_old_password.getText().toString().trim();
        String new_pass=edt_new_password.getText().toString().trim();
        String confirm_pass=edt_confirm_password.getText().toString().trim();

        if(!isregister && !isforgot){
            if (old_pass.isEmpty()) {
                txt_oldpass_error.setText("Please enter old password");
                txt_oldpass_error.setVisibility(View.VISIBLE);
                valid = false;
            } else {
                txt_oldpass_error.setText("");
                txt_oldpass_error.setVisibility(View.GONE);
            }
        }



        if (new_pass.isEmpty()) {
            txt_newpass_error.setText("new password does not allow blank");
            txt_newpass_error.setVisibility(View.VISIBLE);
            valid = false;
        } else {
            txt_newpass_error.setText("");
            txt_newpass_error.setVisibility(View.GONE);
        }

        if (confirm_pass.isEmpty()) {
            txt_confirmpass_error.setText("confirm password does not allow blank");
            txt_confirmpass_error.setVisibility(View.VISIBLE);
            valid = false;
        } else {
            txt_confirmpass_error.setText("");
            txt_confirmpass_error.setVisibility(View.GONE);
        }

        return valid;
    }


    private void Change_password_data(JSONObject jsonObject) throws UnsupportedEncodingException {

        Dialog dialog = new Dialog(Change_Password_Activity.this);
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
        final String url= ConfigAPI.MAIN_URL+"password/update";
        Log.d("url",url);
        //client.addHeader("Authorization","Bearer "+preferences.getString("accesstoken",null));
        client.setTimeout(20*1000);
        client.setMaxRetriesAndTimeout(0,10*1000);
        client.post(Change_Password_Activity.this,url,entity,"application/json",new JsonHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.d("res",response.toString());

                try {

                    int responseCode=response.getInt("responseCode");
                    String responseMsg=response.getString("message");

                    if(responseCode==200){

                        new ConfigAPI().ShowToastMessage(Change_Password_Activity.this,responseMsg);

                        Intent i = new Intent(Change_Password_Activity.this,LoginActivity.class);
                        startActivity(i);


                    }else
                    {

                        new ConfigAPI().ShowToastMessage(Change_Password_Activity.this,responseMsg);
                    }

                    dialog.dismiss();
                    dialog.cancel();

                }
                catch (Exception e)
                {

                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                dialog.dismiss();
                dialog.cancel();
                if(errorResponse!=null){
                    try{
                        new ConfigAPI().ShowToastMessage(Change_Password_Activity.this,errorResponse.getString("message"));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else{
                    new ConfigAPI().ShowToastMessage(Change_Password_Activity.this,"Something went wrong,Please try again later");
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                dialog.dismiss();
                dialog.cancel();
                if(responseString!=null){
                    try{
                        new ConfigAPI().ShowToastMessage(Change_Password_Activity.this,responseString);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else{
                    new ConfigAPI().ShowToastMessage(Change_Password_Activity.this,"Something went wrong,Please try again later");
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                dialog.dismiss();
                dialog.cancel();
                if(errorResponse!=null){
                    try{
                        new ConfigAPI().ShowToastMessage(Change_Password_Activity.this,errorResponse.toString());
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else{
                    new ConfigAPI().ShowToastMessage(Change_Password_Activity.this,"Something went wrong,Please try again later");
                }
            }
        });


    }
    private void Change_password_data_NEW(JSONObject jsonObject) throws UnsupportedEncodingException {

        Dialog dialog = new Dialog(Change_Password_Activity.this);
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
        final String url= ConfigAPI.MAIN_URL+"password/change";
        Log.d("url",url);
        client.addHeader(ConfigAPI.Authorization,ConfigAPI.Bearer+sp_userdetail.getString("auth_token",null));
        client.setTimeout(20*1000);
        client.setMaxRetriesAndTimeout(0,10*1000);
        client.post(Change_Password_Activity.this,url,entity,"application/json",new JsonHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.d("res",response.toString());

                try {

                    int responseCode=response.getInt("responseCode");
                    String responseMsg=response.getString("message");

                    if(responseCode==200){

                        new ConfigAPI().ShowToastMessage(Change_Password_Activity.this,responseMsg);

                      //  Intent i = new Intent(Change_Password_Activity.this,LoginActivity.class);
                       // startActivity(i);

                        SharedPreferences sp_detail=getSharedPreferences("userdetail.txt", MODE_PRIVATE);
                        SharedPreferences.Editor editor=sp_detail.edit();
                       // editor.putString("auth_token",object.getString("auth_token"));
                       // editor.putString("full_name",object.getString("full_name"));
                       // editor.putString("email",object.getString("email"));
                       // editor.putString("mobile",mobile);
                        editor.putString("password",edt_confirm_password.getText().toString());
                       // editor.putString("cpp",cpp);

                        editor.apply();
                    }else
                    {

                        new ConfigAPI().ShowToastMessage(Change_Password_Activity.this,responseMsg);
                    }

                    dialog.dismiss();
                    dialog.cancel();

                }
                catch (Exception e)
                {

                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                dialog.dismiss();
                dialog.cancel();
                if(errorResponse!=null){
                    try{
                        new ConfigAPI().ShowToastMessage(Change_Password_Activity.this,errorResponse.getString("message"));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else{
                    new ConfigAPI().ShowToastMessage(Change_Password_Activity.this,"Something went wrong,Please try again later");
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                dialog.dismiss();
                dialog.cancel();
                if(responseString!=null){
                    try{
                        new ConfigAPI().ShowToastMessage(Change_Password_Activity.this,responseString);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else{
                    new ConfigAPI().ShowToastMessage(Change_Password_Activity.this,"Something went wrong,Please try again later");
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                dialog.dismiss();
                dialog.cancel();
                if(errorResponse!=null){
                    try{
                        new ConfigAPI().ShowToastMessage(Change_Password_Activity.this,errorResponse.toString());
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else{
                    new ConfigAPI().ShowToastMessage(Change_Password_Activity.this,"Something went wrong,Please try again later");
                }
            }
        });


    }


}