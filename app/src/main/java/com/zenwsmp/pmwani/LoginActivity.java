package com.zenwsmp.pmwani;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.os.Build.VERSION.SDK_INT;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import com.hbb20.CountryCodePicker;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.ByteArrayEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;

public class LoginActivity extends AppCompatActivity {
    private static AsyncHttpClient client = new AsyncHttpClient();
    final String regexStr = "^(?:(?:\\+|0{0,2})91(\\s*[\\-]\\s*)?|[0]?)?[789]\\d{9}$";
    EditText edt_username,edt_Contact;
    LinearLayout layout_username,layout_mobilenumber;
    CountryCodePicker cpp;
    TextView txt_sign_up;
    MaterialButton btn_login;
    ImageView ivActionBack;
    TextView txt_forgot_password;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    TextView txt_mobile_error,txt_password_error;
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
        txt_forgot_password = findViewById(R.id.txt_forgot_password);
        txt_mobile_error = findViewById(R.id.txt_mobile_error);
        txt_password_error = findViewById(R.id.txt_password_error);
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

            if(new connectionDector(this).isConnectingToInternet())
            {
                if(validate()){
                    Call_Login();
                   /* Intent i = new Intent(RegistrationActivity.this,OTP_Activity.class);
                    i.putExtra("phone",edt_contact.getText().toString());
                    i.putExtra("cpp_code",ccp.getSelectedCountryCodeWithPlus());
                    startActivity(i);*/
                }
            }else{
                new ConfigAPI().ShowToastMessage(this,"No Internet Connection");
            }
        });

        ivActionBack.setOnClickListener(v -> {
            this.finish();
        });
        txt_forgot_password.setOnClickListener(v->
        {
            Intent i = new Intent(LoginActivity.this,ForgotPassword_Activity.class);
            startActivity(i);
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        if(checkPermission())
        {
            Log.d("All permition","Granted");
        }
        else
        {
            requestPermission();
        }
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
    private void Call_Login(){

        try {
            JSONObject object1=new JSONObject();
            object1.put("token","11");
            object1.put("mobile",edt_Contact.getText().toString());

            object1.put("country_code",cpp.getSelectedCountryCodeWithPlus());
            object1.put("password",edt_username.getText().toString());
          //  object1.put("hash_code",getHashCode(RegistrationActivity.this));
            Log.d("param",object1.toString());
            Register(object1);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void Register(JSONObject jsonObject) throws UnsupportedEncodingException {

        Dialog dialog = new Dialog(LoginActivity.this);
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
        client.post(LoginActivity.this,url,entity,"application/json",new JsonHttpResponseHandler()
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
                        editor.apply();
                        Intent i = new Intent(LoginActivity.this,DashboardActivity.class);

                        startActivity(i);


                    } else if(responseCode == 201){
                        new ConfigAPI().ShowToastMessage(LoginActivity.this,responseMsg);
                    }else
                    {

                        new ConfigAPI().ShowToastMessage(LoginActivity.this,responseMsg);
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
                        new ConfigAPI().ShowToastMessage(LoginActivity.this,errorResponse.getString("message"));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else{
                    new ConfigAPI().ShowToastMessage(LoginActivity.this,"Something went wrong,Please try again later");
                }


            }
        });


    }

    public boolean validate() {
        boolean valid = true;
        /// edt_site_name,edt_site_description,edt_site_address1,edt_site_address2,edt_site_city,edt_site_state,edt_site_zipcode,edt_site_latitude,edt_site_longitude
        String fname=edt_username.getText().toString();

        String phone=edt_Contact.getText().toString();
        if(!phone.matches(regexStr)){
            txt_mobile_error.setText("Invalid Mobile Number.");
            txt_mobile_error.setVisibility(View.VISIBLE);
            valid = false;
        } else {
            txt_mobile_error.setText("");
            txt_mobile_error.setVisibility(View.GONE);
        }

        if (fname.isEmpty()) {
            txt_password_error.setText("Password is required.");
            txt_password_error.setVisibility(View.VISIBLE);
            valid = false;
        } else {
            txt_password_error.setText("");
            txt_password_error.setVisibility(View.GONE);
        }







        return valid;
    }

    private boolean checkPermission()
    {
        if (SDK_INT >= Build.VERSION_CODES.R)
        {
            //int result = ContextCompat.checkSelfPermission(Splash_Screen.this, CAMERA);
            //int phonestatePermission = ContextCompat.checkSelfPermission(LoginActivity.this, READ_PHONE_STATE);
            int Location_fine_per = ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);
            int Location_corse_per = ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION);
            return ((Location_fine_per == PackageManager.PERMISSION_GRANTED) && (Location_corse_per == PackageManager.PERMISSION_GRANTED));
        } else {
            //int phonestatePermission = ContextCompat.checkSelfPermission(LoginActivity.this, READ_PHONE_STATE);
            int Location_fine_per = ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);
            int Location_corse_per = ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION);
            //int storage_read = ContextCompat.checkSelfPermission(LoginActivity.this, READ_EXTERNAL_STORAGE);
            //int storage_write = ContextCompat.checkSelfPermission(LoginActivity.this, WRITE_EXTERNAL_STORAGE);
            return Location_fine_per == PackageManager.PERMISSION_GRANTED && Location_corse_per == PackageManager.PERMISSION_GRANTED;
        }
    }


    private void requestPermission()
    {
        /*if (SDK_INT >= Build.VERSION_CODES.R)
        {
            //int result = ContextCompat.checkSelfPermission(SplashActivityQN.this, CAMERA);
            int phonestatePermission = ContextCompat.checkSelfPermission(this, READ_PHONE_STATE);
            int Location_fine_per = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
            int Location_corse_per = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
            if(!Environment.isExternalStorageManager())
            {
                try {

                    //ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),REQUEST_ID_MULTIPLE_PERMISSIONS);
                    Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                    intent.addCategory("android.intent.category.DEFAULT");
                    intent.setData(Uri.parse(String.format("package:%s",getApplicationContext().getPackageName())));
                    startActivityForResult(intent, 2296);
                } catch (Exception e) {
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                    startActivityForResult(intent, 2296);
                }
            }
            else if(!(phonestatePermission == PackageManager.PERMISSION_GRANTED) && !(Location_fine_per == PackageManager.PERMISSION_GRANTED) && !(Location_corse_per == PackageManager.PERMISSION_GRANTED))
            {

                List<String> listPermissionsNeeded = new ArrayList<>();

                if (phonestatePermission != PackageManager.PERMISSION_GRANTED) {
                    listPermissionsNeeded.add(READ_PHONE_STATE);
                }

                if (Location_fine_per != PackageManager.PERMISSION_GRANTED) {
                    listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
                }

                if (Location_corse_per != PackageManager.PERMISSION_GRANTED) {
                    listPermissionsNeeded.add(Manifest.permission.ACCESS_COARSE_LOCATION);
                }

                if (!listPermissionsNeeded.isEmpty()) {
                    ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),REQUEST_ID_MULTIPLE_PERMISSIONS);
                }
            }
            else
            {

            }


        }
        else
        {*/
            //int phonestatePermission = ContextCompat.checkSelfPermission(this, READ_PHONE_STATE);
            //int permissionStorage = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int Location_fine_per = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
            int Location_corse_per = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);

            List<String> listPermissionsNeeded = new ArrayList<>();

           /* if (phonestatePermission != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(READ_PHONE_STATE);
            }
            if (permissionStorage != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }*/

            if (Location_fine_per != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }

            if (Location_corse_per != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            }

            if (!listPermissionsNeeded.isEmpty()) {
                ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),REQUEST_ID_MULTIPLE_PERMISSIONS);
            }
        //}
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_ID_MULTIPLE_PERMISSIONS: {

                Map<String, Integer> perms = new HashMap<>();
                // Initialize the map with both permissions

                perms.put(READ_PHONE_STATE, PackageManager.PERMISSION_GRANTED);
                perms.put(WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);

                // Fill with actual results from user
                if (grantResults.length > 0) {
                    for (int i = 0; i < permissions.length; i++)
                        perms.put(permissions[i], grantResults[i]);
                    // Check for both permissions
                    if (perms.get(ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && perms.get(ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        Log.d("call", "permisiongranted");
                        // process the normal flow
                        //else any one or both the permissions are not granted
                    } else {
                        Log.d("call", "permissions not granted ask again ");
                        //permission is denied (this is the first time, when "never ask again" is not checked) so ask again explaining the usage of permission
//                        // shouldShowRequestPermissionRationale will return true
                        //show the dialog or snackbar saying its necessary and try again otherwise proceed with setup.
                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, ACCESS_COARSE_LOCATION) || ActivityCompat.shouldShowRequestPermissionRationale(this, ACCESS_FINE_LOCATION) ) {
                            showDialogOK("Location Permission required for this app",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            switch (which) {
                                                case DialogInterface.BUTTON_POSITIVE:
                                                    checkPermission();
                                                    break;
                                                case DialogInterface.BUTTON_NEGATIVE:
                                                    // proceed with logic by disabling the related features or quit the app.
                                                    break;
                                            }
                                        }
                                    });
                        }
                        //permission is denied (and never ask again is  checked)
                        //shouldShowRequestPermissionRationale will return false
                        else {
                            //                            //proceed with logic by disabling the related features or quit the app.
                        }
                    }
                }
            }
        }
    }





    private void showDialogOK(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", okListener)
                .create()
                .show();
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