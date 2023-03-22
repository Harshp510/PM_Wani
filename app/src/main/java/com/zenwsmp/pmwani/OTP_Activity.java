package com.zenwsmp.pmwani;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.ByteArrayEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;


public class OTP_Activity extends AppCompatActivity {

   // private static final int REQ_USER_CONSENT = 200;
   // SmsBroadcastReceiver smsBroadcastReceiver;
   private static AsyncHttpClient client = new AsyncHttpClient();
    TextView txt_timer;
    DecimalFormat formater = new DecimalFormat("00");
    CountDownTimer timer;
    MaterialButton btn_verify;
    TextView txt_resend_code,txt_otp_label;
    String phone,cpp_code;
    private static final int SMS_CONSENT_REQUEST = 1002;  // Set to an unused request code
    private int beginIndex = 25, endIndex = 33;
    private int RESOLVE_HINT = 1001;
    PinView firstPinView;
    boolean isregister;
    boolean isforgot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        String message=getHashCode(this);
        Log.d("mesage",""+message);
        isregister = getIntent().getBooleanExtra("isregister",false);
        isforgot = getIntent().getBooleanExtra("isforgot",false);
        txt_timer= findViewById(R.id.txt_timer);
        btn_verify= findViewById(R.id.btn_verify);
        txt_resend_code= findViewById(R.id.txt_resend_code);
        txt_otp_label= findViewById(R.id.txt_otp_label);
        firstPinView= findViewById(R.id.firstPinView);
        //startSmartUserConsent();
        phone=getIntent().getStringExtra("phone");
        cpp_code=getIntent().getStringExtra("cpp_code");
        txt_otp_label.setText("We sent Code to Phone "+cpp_code+" "+phone);

       /* MySMSBroadcastReceiver.initSMSListener(null);
        IntentFilter intentFilter = new IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION);
        registerReceiver(smsVerificationReceiver, intentFilter);
        SmsRetriever.getClient(this).startSmsUserConsent(null);*/


        timer = new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {
                txt_resend_code.setEnabled(false);
                txt_timer.setText(getResources().getString(R.string.resend_code_in) +" "+(formater.format(millisUntilFinished / 60000))+":"+(formater.format(millisUntilFinished % 60000 / 1000)));
                //here you can have your logic to set text to edittext
            }

            public void onFinish() {
                txt_timer.setText("Time out!");
                txt_resend_code.setEnabled(true);
            }

        };
        timer.start();

        txt_resend_code.setOnClickListener(v -> {

            if(new connectionDector(this).isConnectingToInternet())
            {
                timer.start();

                Call_Resend();
            }else{
                new ConfigAPI().ShowToastMessage(this,"No Internet Connection");
            }
        });

        btn_verify.setOnClickListener(v -> {
            if(new connectionDector(this).isConnectingToInternet())
            {
                Log.d("test",firstPinView.getText().toString());
                Call_Otpverification(firstPinView.getText().toString());
            }else{
                new ConfigAPI().ShowToastMessage(this,"No Internet Connection");
            }
        });


        Toast.makeText(this,  " listening started", Toast.LENGTH_SHORT).show();
        SmsRetrieverClient client = SmsRetriever.getClient(this);
        Task<Void> task = client.startSmsRetriever();

        task.addOnSuccessListener(aVoid -> {
            MySMSBroadcastReceiver.initSMSListener(new SMSListener() {
                @Override
                public void onSuccess(String message) {
                    if (message != null)
                       // etOTPSet.setText(parseOneTimeCode(message));
                    Log.d("TAG_otp",message);
                }

                @Override
                public void onError(String message) {
                    if (message != null)
                        Log.d("TAG",message);
                }
            });
        });

        task.addOnFailureListener(e -> {
            e.printStackTrace();
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private void Call_Otpverification(String otp){

        try {
            JSONObject object1=new JSONObject();
            object1.put("token","11");
            object1.put("mobile",phone);
            object1.put("otp",otp);

            object1.put("country_code",cpp_code);
           // object1.put("hash_code",getHashCode(RegistrationActivity.this));
            Log.d("param",object1.toString());
            OTP_Verification(object1);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void Call_Resend(){

        try {
            JSONObject object1=new JSONObject();
            object1.put("token","11");
            object1.put("mobile",phone);
           // object1.put("otp",otp);

            object1.put("country_code",cpp_code);
            object1.put("hash_code",getHashCode(OTP_Activity.this));
            Log.d("param",object1.toString());
            OTP_Resend(object1);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void OTP_Verification(JSONObject jsonObject) throws UnsupportedEncodingException {

        Dialog dialog = new Dialog(OTP_Activity.this);
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
        final String url= ConfigAPI.MAIN_URL+"otp/verify";
        Log.d("url",url);
        //client.addHeader("Authorization","Bearer "+preferences.getString("accesstoken",null));
        client.setTimeout(20*1000);
        client.post(OTP_Activity.this,url,entity,"application/json",new JsonHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.d("res",response.toString());

                try {

                    int responseCode=response.getInt("responseCode");
                    String responseMsg=response.getString("message");

                    if(responseCode == 200){

                     /*  */
                        new ConfigAPI().ShowToastMessage(OTP_Activity.this,responseMsg);
                        Intent i = new Intent(OTP_Activity.this,Change_Password_Activity.class);
                        i.putExtra("isregister",isregister);
                        i.putExtra("isforgot",isforgot);
                        i.putExtra("phone",phone);
                        i.putExtra("cpp_code",cpp_code);
                        startActivity(i);
                    }else
                    {

                        new ConfigAPI().ShowToastMessage(OTP_Activity.this,responseMsg);
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
                       // int responseCode=errorResponse.getInt("responseCode");
                        Log.d("error_res",errorResponse.toString());
                        new ConfigAPI().ShowToastMessage(OTP_Activity.this,errorResponse.getString("message"));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else{
                    new ConfigAPI().ShowToastMessage(OTP_Activity.this,"Something went wrong,Please try again later");
                }


            }
        });


    }
    private void OTP_Resend(JSONObject jsonObject) throws UnsupportedEncodingException {

        Dialog dialog = new Dialog(OTP_Activity.this);
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
        client.post(OTP_Activity.this,url,entity,"application/json",new JsonHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.d("res",response.toString());

                try {

                    int responseCode=response.getInt("responseCode");
                    String responseMsg=response.getString("message");

                    if(responseCode == 200){

                     /*   Intent i = new Intent(OTP_Activity.this,OTP_Activity.class);
                        i.putExtra("phone",edt_contact.getText().toString());
                        i.putExtra("cpp_code",ccp.getSelectedCountryCode());
                        startActivity(i);*/
                        new ConfigAPI().ShowToastMessage(OTP_Activity.this,responseMsg);

                    }else
                    {

                        new ConfigAPI().ShowToastMessage(OTP_Activity.this,responseMsg);
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
                        new ConfigAPI().ShowToastMessage(OTP_Activity.this,errorResponse.getString("message"));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else{
                    new ConfigAPI().ShowToastMessage(OTP_Activity.this,"Something went wrong,Please try again later");
                }


            }
        });


    }
    public static String getHashCode(Context context){
        AppSignatureHelper appSignature = new AppSignatureHelper(context);
        Log.e(" getAppSignatures ",""+appSignature.getAppSignatures());
        return appSignature.getAppSignatures().get(0);

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // This block for request hint --> getting user phone number.
        if (requestCode == RESOLVE_HINT) {
            if (resultCode == RESULT_OK) {
                Credential credential = data.getParcelableExtra(Credential.EXTRA_KEY);
                if (credential != null) {
                    // credential.getId();  <-- will need to process phone number string
                    // Toast.makeText(this, credential.getId(), Toast.LENGTH_LONG).show();
                    // sendPhoneNumberToServer(credential.getId());
                }
            }
        } else if (requestCode == SMS_CONSENT_REQUEST) {
            if (resultCode == RESULT_OK) {
                // Get SMS message content
                String message = data.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE);
                // Extract one-time code from the message and complete verification
                // `sms` contains the entire text of the SMS message, so you will need
                // to parse the string.
                if (message != null) {
                   // etOTPSet.setText(parseOneTimeCode(message));
                    Log.d("OTP-->",parseOneTimeCode(message));
                }
                // send one time code to the server
            } else {
                // Consent canceled, handle the error ...
            }
        }
    }

    private String parseOneTimeCode(String otp) {
        return otp.substring(beginIndex, endIndex);
    }
    private final BroadcastReceiver smsVerificationReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (SmsRetriever.SMS_RETRIEVED_ACTION.equals(intent.getAction())) {
                Bundle extras = intent.getExtras();
                Status smsRetrieverStatus = (Status) extras.get(SmsRetriever.EXTRA_STATUS);

                switch (smsRetrieverStatus.getStatusCode()) {
                    case CommonStatusCodes.SUCCESS:
                        // Get consent intent
                        Intent consentIntent = extras.getParcelable(SmsRetriever.EXTRA_CONSENT_INTENT);
                        try {
                            // Start activity to show consent dialog to user, activity must be started in
                            // 5 minutes, otherwise you'll receive another TIMEOUT intent
                            startActivityForResult(consentIntent, SMS_CONSENT_REQUEST);
                        } catch (ActivityNotFoundException e) {
                            // Handle the exception ...
                        }
                        break;
                    case CommonStatusCodes.TIMEOUT:
                        // Time out occurred, handle the error.
                        break;
                }
            }
        }
    };
}