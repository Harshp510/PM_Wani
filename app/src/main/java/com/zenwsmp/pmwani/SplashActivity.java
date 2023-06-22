package com.zenwsmp.pmwani;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.ByteArrayEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;

public class SplashActivity extends AppCompatActivity {

    private static AsyncHttpClient client = new AsyncHttpClient();
    SharedPreferences sp;
    String cpp,mobile,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        sp=getSharedPreferences("userdetail.txt", Context.MODE_PRIVATE);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(sp.contains("mobile") && sp.contains("password"))
        {
            try{
                 mobile=sp.getString("mobile",null);
                 password=sp.getString("password",null);
                 cpp=sp.getString("cpp",null);

                if(new connectionDector(this).isConnectingToInternet())
                {
                    Call_Login(mobile,password,cpp);
                }else{
                    new ConfigAPI().ShowToastMessage(this,"No Internet Connection");
                    new Handler().postDelayed(() -> {
                        Intent i = new Intent(SplashActivity.this,Startup_Activity.class);
                        startActivity(i);
                    },2000);
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            new Handler().postDelayed(() -> {
                Intent i = new Intent(SplashActivity.this,Startup_Activity.class);
                startActivity(i);
            },2000);
        }
    }

    private void Call_Login(String mobile,String password,String cpp){

        try {
            JSONObject object1=new JSONObject();
            object1.put("token",ConfigAPI.ACCESS_TOKEN);
            object1.put("mobile",mobile);

            object1.put("country_code",cpp);
            object1.put("password",password);
            //  object1.put("hash_code",getHashCode(RegistrationActivity.this));
            Log.d("param",object1.toString());
            Register(object1,mobile,password,cpp);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void Register(JSONObject jsonObject,String mobile,String password,String cpp) throws UnsupportedEncodingException {

        Dialog dialog = new Dialog(SplashActivity.this);
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
        final String url= ConfigAPI.MAIN_URL+"signin";
        Log.d("url",url);
        //client.addHeader("Authorization","Bearer "+preferences.getString("accesstoken",null));
        client.setTimeout(20*1000);
        client.post(SplashActivity.this,url,entity,"application/json",new JsonHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.d("res",response.toString());

                try {

                    int responseCode=response.getInt("responseCode");
                    String responseMsg=response.getString("message");

                    if(responseCode==200){

                        JSONObject object = response.getJSONObject("data");
                        SharedPreferences sp_detail=getSharedPreferences("userdetail.txt", MODE_PRIVATE);
                        SharedPreferences.Editor editor=sp_detail.edit();
                        editor.putString("auth_token",object.getString("auth_token"));
                        editor.putString("full_name",object.getString("full_name"));
                        editor.putString("email",object.getString("email"));
                        editor.putString("mobile",mobile);
                        editor.putString("password",password);
                        editor.putString("cpp",cpp);

                        editor.apply();
                        Intent i = new Intent(SplashActivity.this,DashboardActivity.class);

                        startActivity(i);


                    } else if(responseCode == 201){
                        new ConfigAPI().ShowToastMessage(SplashActivity.this,responseMsg);
                    }else
                    {

                        new ConfigAPI().ShowToastMessage(SplashActivity.this,responseMsg);
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
                        new ConfigAPI().ShowToastMessage(SplashActivity.this,errorResponse.getString("message"));
                        new Handler().postDelayed(() -> {
                            Intent i = new Intent(SplashActivity.this,Startup_Activity.class);
                            startActivity(i);
                        },2000);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else{
                    new ConfigAPI().ShowToastMessage(SplashActivity.this,"Something went wrong,Please try again later");
                }


            }
        });


    }
}