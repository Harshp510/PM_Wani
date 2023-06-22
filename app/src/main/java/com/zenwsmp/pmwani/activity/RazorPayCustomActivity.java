package com.zenwsmp.pmwani.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultListener;
import com.razorpay.PaymentResultWithDataListener;
import com.razorpay.Razorpay;
import com.zenwsmp.pmwani.AppSignatureHelper;
import com.zenwsmp.pmwani.AvailablePlanActivity;
import com.zenwsmp.pmwani.ConfigAPI;
import com.zenwsmp.pmwani.DashboardActivity;
import com.zenwsmp.pmwani.ForgotPassword_Activity;
import com.zenwsmp.pmwani.OTP_Activity;
import com.zenwsmp.pmwani.R;
import com.zenwsmp.pmwani.common.CommonClass;
import com.zenwsmp.pmwani.common.Constant;
import com.zenwsmp.pmwani.common.LoginUtils;
import com.zenwsmp.pmwani.common.PaytmConstant;
import com.zenwsmp.pmwani.common.PreferencesUtils;
import com.zenwsmp.pmwani.common.Utility;
import com.zenwsmp.pmwani.common.Validate;
import com.zenwsmp.pmwani.objects.TicketSessionData;
import com.zenwsmp.pmwani.widget.OTPVerificationDialog;
import com.zenwsmp.pmwani.widget.PaymentSuccessDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.ByteArrayEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;
import in.ticketninja.widget.SRKLoaderDialog;


public class RazorPayCustomActivity extends AppCompatActivity {
    private static final String TAG = RazorPayCustomActivity.class.getSimpleName();
    private WebView webView;
    private Razorpay razorpay;
    private Activity activity;
    private String paymentMethod, bank = "";
    private CommonClass CC;
    private SRKLoaderDialog mLoader;
    private PreferencesUtils mPref;
    private LoginUtils mLogin;
    private final PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
    private OTPVerificationDialog otpVerificationDialog;
    private String eventSessionId = "";
    private String planid = "";
    private String planname = "";
    private String currency = "";
    private String orderId = "";
    private TicketSessionData eventSessionData;
    private String cardName, cardNo, cardCvv, expiry_month, expiry_year, walletType;
    private double totalAmt = 0.0;
   // private OrderSummaryActivity orderSummaryActivity;
    private  JSONObject joNotes;
    private String eventName;
    private static AsyncHttpClient client = new AsyncHttpClient();
    private Context mContext;
    SharedPreferences sp_userdetail;
    String Str_cpp;
    String Str_UserName;
    String Str_email;
    String Str_password;
    String Str_fullname;
    String Str_mobile;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_razorpay_custom_view);

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
        try {
            activity = this;
            initializeData();
            getIntentData();
            setUpToolbar();
            initializeRazorPay();
            Call_CreateOrder();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void initializeData() {
        try {
            CC = new CommonClass(this);
            mPref = new PreferencesUtils(this);
            mLogin = new LoginUtils(this);
            mLoader = new SRKLoaderDialog(this);
            otpVerificationDialog = new OTPVerificationDialog(this);
            //orderSummaryActivity = OrderSummaryActivity.getSingletonObject();
            mContext = this;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getIntentData() {
        try {
            Intent i = getIntent();
            planid = i.getStringExtra("planid");
            planname = i.getStringExtra("planname");
            paymentMethod = i.getStringExtra(Constant.ScreenExtras.PAYMENT_METHOD);
            Log.e(TAG, "paymentMethod = " + paymentMethod) ;

            eventSessionId = i.getStringExtra(Constant.ScreenExtras.EVENT_SESSION_ID);
            eventSessionData = (TicketSessionData) i.getSerializableExtra(Constant.ScreenExtras.TICKET_SESSION_DATA);
            //totalAmt = (eventSessionData).getNetamount();
            //eventName = eventSessionData.getEvent_name();
            String notes = "Android_" + planid;
            joNotes = new JSONObject();
            joNotes.put("address", notes);

            //for net banking
            if (i.hasExtra(Constant.ScreenExtras.BANK_NAME)) {
                bank = i.getStringExtra(Constant.ScreenExtras.BANK_NAME);
            }


            //for credit debit card
            if (i.hasExtra(Constant.CARD.EXPIRY_MONTH)) {
                expiry_month = i.getStringExtra(Constant.CARD.EXPIRY_MONTH);
            }

            if (i.hasExtra(Constant.CARD.EXPIRY_YEAR)) {
                expiry_year = i.getStringExtra(Constant.CARD.EXPIRY_YEAR);
            }

            if (i.hasExtra(Constant.CARD.NAME)) {
                cardName = i.getStringExtra(Constant.CARD.NAME);
            }

            if (i.hasExtra(Constant.CARD.NUMBER)) {
                cardNo = i.getStringExtra(Constant.CARD.NUMBER);
            }

            if (i.hasExtra(Constant.CARD.CVV)) {
                cardCvv = i.getStringExtra(Constant.CARD.CVV);
            }

            //for wallet
            if (i.hasExtra(Constant.ScreenExtras.WALLET_TYPE)) {
                walletType = i.getStringExtra(Constant.ScreenExtras.WALLET_TYPE);
            }
            //Log.e(TAG,"bank:" + bank+" , "+method);
            //Log.e(TAG,"eventSessionData:" + eventSessionData);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void setUpToolbar() {
        try {
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                getSupportActionBar().setDisplayShowHomeEnabled(false);
                ViewCompat.setElevation(toolbar, 10);

                ImageView ivActionBack = toolbar.findViewById(R.id.ivActionBack);
                ivActionBack.setOnClickListener(v -> onBackPressed());
                TextView tvTitle = toolbar.findViewById(R.id.tvTitle);
                if (Constant.RazorPayPaymentMethod.NET_BANKING.equals(paymentMethod)) {
                    tvTitle.setText(R.string.title_activity_select_bank);
                } else if (Constant.RazorPayPaymentMethod.CARD.equals(paymentMethod)) {
                    tvTitle.setText(R.string.title_activity_credit_debit_card);
                } else if (Constant.RazorPayPaymentMethod.WALLET.equals(paymentMethod)) {
                    tvTitle.setText(R.string.title_activity_wallet);
                } else if (Constant.RazorPayPaymentMethod.UPI.equals(paymentMethod)) {
                    tvTitle.setText(R.string.title_activity_upi);
                }
                Utility.convertToLowerCase(tvTitle);

                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
                    findViewById(R.id.toolbarBottomDivider).setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //instantiate and Initialize RazorPay Android Custom SDK
    public void initializeRazorPay() {
        try {
            razorpay = new Razorpay(activity, getResources().getString(R.string.razor_pay_key));
            setUpWebView();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //Setup WebView
    public void setUpWebView() {
        try {
            webView = findViewById(R.id.webView);
            // Hide the webView until the payment details are submitted
            webView.setVisibility(View.GONE);
            razorpay.setWebView(webView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void paymentStart() {
        try {
            if (Constant.RazorPayPaymentMethod.NET_BANKING.equals(paymentMethod)) {
                netBanking();
            } else if (Constant.RazorPayPaymentMethod.CARD.equals(paymentMethod)) {
                debitCreditCard();
            } else if (Constant.RazorPayPaymentMethod.WALLET.equals(paymentMethod)) {
                if (Constant.WalletName.PAYTM.equals(walletType)) {
                    paytm();
                    //wallet();
                } else {
                    wallet();
                }
            } else if (Constant.RazorPayPaymentMethod.UPI.equals(paymentMethod)) {
                 upiIntentFlow("");
                 //showAlertDialog();
                //payUsingUpi("100","yash@okaxis","yash","money");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void Call_CreateOrder(){

        try {
            JSONObject object1=new JSONObject();

            object1.put("plan_id",planid);
            Log.d("param",object1.toString());
            CreateOrder(object1);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private void CreateOrder(JSONObject jsonObject) throws UnsupportedEncodingException {


        if (!mLoader.isShowing() && !isFinishing()) mLoader.show();
        RequestParams params = new RequestParams();
        params.put("",jsonObject);
        // StringEntity entity = new StringEntity(params.toString());
        final ByteArrayEntity entity = new ByteArrayEntity(jsonObject.toString().getBytes("UTF-8"));
        entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        //  params.put("Password",jsonpassword);
        final String url= ConfigAPI.MAIN_URL+"create_order";
        Log.d("url",url);
        //client.addHeader("Authorization","Bearer "+preferences.getString("accesstoken",null));
        client.addHeader(ConfigAPI.Authorization,ConfigAPI.Bearer+sp_userdetail.getString("auth_token",null));
        client.setTimeout(20*1000);
        client.post(RazorPayCustomActivity.this,url,entity,"application/json",new JsonHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.d("res",response.toString());

                try {

                    int responseCode=response.getInt("responseCode");
                    String responseMsg=response.getString("message");

                    if(responseCode == 200){

                        JSONObject object = response.getJSONObject("data");
                         orderId = object.getString("id");
                         totalAmt = object.getDouble("amount");
                        currency = object.getString("currency");
                        paymentStart();

                    }else
                    {

                        CC.showAlert("Payment Failure :", responseCode + " " + responseMsg, android.R.string.yes, () -> onBackPressed());
                    }

                    if (mLoader != null && mLoader.isShowing()) mLoader.dismiss();

                }
                catch (Exception e)
                {
                    if (mLoader != null && mLoader.isShowing()) mLoader.dismiss();
                    e.printStackTrace();
                }
                // dialog.dismiss();
                //dialog.cancel();

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable t, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, t, errorResponse);
                t.printStackTrace();
                if (mLoader != null && mLoader.isShowing()) mLoader.dismiss();

            }
        });


    }
   /* private void wsCreateOrder() {
        try {
            //{"error_code":"1","error_description":"sucess","data":{"session_id":"ZG7TafybkGI19OEP1MQnRL1Q1qqCGMim"}}
            if (!mLoader.isShowing() && !isFinishing()) mLoader.show();
            RequestRazorPayOrder request = new RequestRazorPayOrder();
            request.user_id = mLogin.getUserId();
            request.email = mLogin.getEmail();
            request.mobileno = mLogin.getMobile();
            request.session_id = eventSessionId;
            Log.e(TAG, "request =" + request);
            TicketNinjaAPI tnAPI = RestApi.createAPI();
            Call<ResponseBody> call = tnAPI.createRazorPayOrder(request);
            call.enqueue(new Callback<ResponseBody>() {
                @SuppressWarnings("ConstantConditions")
                @Override
                public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                    try {
                        //{"error_code":"1","error_description":"Check Sucessfully","data":{"razerpay_orderid":"order_DI2fIyN554sS22"}}
                        if (mLoader != null && mLoader.isShowing()) mLoader.dismiss();
                        if (response.isSuccessful()) {
                            String myData = response.body().string();
                            Log.e(TAG, "data =" + myData);
                            try {
                                JSONObject jo = new JSONObject(myData);
                                String des = jo.getString("error_description");
                                if (String.valueOf(RestApi.ErrorCode.SUCCESS).equals(Objects.requireNonNull(jo.getString("error_code")))) {
                                    Log.e(TAG, "if");
                                    JSONObject joData = jo.getJSONObject("data");
                                    orderId = joData.getString("razerpay_orderid");
                                    Log.e(TAG, "orderId =" + orderId);
                                    paymentStart();
                                } else {
                                    Log.e(TAG, "des: " + des);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Log.e(TAG, "error");
                            //CC.showToast(R.string.msg_something_wrong);
                            CC.showAlert("Payment Failure :", response.code() + " " + response.message(), android.R.string.yes, () -> onBackPressed());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        if (mLoader != null && mLoader.isShowing()) mLoader.dismiss();
                        Log.e(TAG, e.getMessage());
                    }

                }

                @Override
                public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                    t.printStackTrace();
                    if (mLoader != null && mLoader.isShowing()) mLoader.dismiss();
                }

            });
        } catch (Exception e) {
            if (mLoader != null && mLoader.isShowing()) mLoader.dismiss();
            e.printStackTrace();
            Log.e(TAG, Objects.requireNonNull(e.getMessage()));
        }
    }*/


    private void Call_save_payment(String razorpay_order_id,String razorpay_signature,String razorpay_payment_id){

        try {
            JSONObject object1=new JSONObject();

            object1.put("razorpay_order_id",razorpay_order_id);
            object1.put("razorpay_signature",razorpay_signature);
            object1.put("razorpay_payment_id",razorpay_payment_id);
            Log.d("param",object1.toString());
            save_payment(object1);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private void save_payment(JSONObject jsonObject) throws UnsupportedEncodingException {


        if (!mLoader.isShowing() && !isFinishing()) mLoader.show();
        RequestParams params = new RequestParams();
        params.put("",jsonObject);
        // StringEntity entity = new StringEntity(params.toString());
        final ByteArrayEntity entity = new ByteArrayEntity(jsonObject.toString().getBytes("UTF-8"));
        entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        //  params.put("Password",jsonpassword);
        final String url= ConfigAPI.MAIN_URL+"save_payment";
        Log.d("url",url);
        //client.addHeader("Authorization","Bearer "+preferences.getString("accesstoken",null));
        client.addHeader(ConfigAPI.Authorization,ConfigAPI.Bearer+sp_userdetail.getString("auth_token",null));
        client.setTimeout(20*1000);
        client.post(RazorPayCustomActivity.this,url,entity,"application/json",new JsonHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.d("res",response.toString());
                if (mLoader != null && mLoader.isShowing()) mLoader.dismiss();
                try {

                    int responseCode=response.getInt("responseCode");
                    String responseMsg=response.getString("message");

                    if(responseCode == 200){

                        if (otpVerificationDialog != null) otpVerificationDialog.dismiss();
                        JSONObject object = response.getJSONObject("data");
                        String payment_id = object.getString("payment_id");
                        String order_id = object.getString("order_id");
                        PaymentSuccessDialog successDialog = new PaymentSuccessDialog(RazorPayCustomActivity.this);
                        // successDialog.setTransactionId(String.valueOf(transId));
                        successDialog.setTransactionId(String.valueOf(order_id));
                        if (totalAmt == 0) {
                            successDialog.setAmount(R.string.str_price_free);
                        } else {
                            successDialog.setAmount(String.format(Locale.getDefault(), "₹ %.2f", (totalAmt / 100)));
                        }

                        successDialog.setOnOkClickListener(() -> {
                            successDialog.show();
                            Intent intConfirm = new Intent(getApplicationContext(), AvailablePlanActivity.class);
                            intConfirm.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                           // intConfirm.putExtra(Constant.ScreenExtras.EVENT_TRANSACTION_TYPE, Constant.PaymentType.ONLINE);
                           // intConfirm.putExtra(Constant.ScreenExtras.EVENT_TRANSACTION_ID, transId);
                            startActivity(intConfirm);

                        });

                        try {

                            if (!isFinishing()) {
                                successDialog.show();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }else
                    {

                        CC.showAlert("Payment Failure :", responseCode + " " + responseMsg, android.R.string.yes, () -> onBackPressed());
                    }



                }
                catch (Exception e)
                {
                    if (mLoader != null && mLoader.isShowing()) mLoader.dismiss();
                    e.printStackTrace();
                }
                // dialog.dismiss();
                //dialog.cancel();

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable t, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, t, errorResponse);
                t.printStackTrace();
                if (mLoader != null && mLoader.isShowing()) mLoader.dismiss();

            }
        });


    }
   /*  private void wsInsertPlaceOrder(String user_Id, String session_Id, String orderId, String merchantType, String payment_Id, String countryCode,
                                    String mobileNo, String email) {
        try {
            if (!mLoader.isShowing() && !isFinishing()) mLoader.show();

            RequestInsertPlaceOrder request = new RequestInsertPlaceOrder(String.valueOf(user_Id), session_Id, orderId,
                    Constant.PaymentType.ONLINE, merchantType, payment_Id, countryCode, mobileNo, email);

            TicketNinjaAPI tnAPI = RestApi.createAPI();
            Call<CallbackInsertPlaceOrder> call = tnAPI.InsertPlaceOrder(request);
            call.enqueue(new Callback<CallbackInsertPlaceOrder>() {
                @Override
                public void onResponse(@NonNull Call<CallbackInsertPlaceOrder> call, @NonNull Response<CallbackInsertPlaceOrder> response) {
                    if (mLoader != null && mLoader.isShowing()) mLoader.dismiss();
                    try {
                        if (response.isSuccessful()) {
                            if (String.valueOf(RestApi.ErrorCode.SUCCESS).equals(Objects.requireNonNull(response.body()).getError_code())) {
                                if (otpVerificationDialog != null) otpVerificationDialog.dismiss();

                                long transId = Objects.requireNonNull(response.body()).data().getTransId();
                                String transNo = Objects.requireNonNull(response.body()).data().getTransNo();

                                //stopTimerService();
                                //mTimer.cancel();

                                PaymentSuccessDialog successDialog = new PaymentSuccessDialog(RazorPayCustomActivity.this);
                                // successDialog.setTransactionId(String.valueOf(transId));
                                successDialog.setTransactionId(String.valueOf(transNo));
                                if (totalAmt == 0) {
                                    successDialog.setAmount(R.string.str_price_free);
                                } else {
                                    successDialog.setAmount(String.format(Locale.getDefault(), "₹ %.2f", totalAmt));
                                }

                                successDialog.setOnOkClickListener(() -> {
                                    Intent intConfirm = new Intent(getApplicationContext(), BookingConfirmationActivity.class);
                                    intConfirm.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    intConfirm.putExtra(Constant.ScreenExtras.EVENT_TRANSACTION_TYPE, Constant.PaymentType.ONLINE);
                                    intConfirm.putExtra(Constant.ScreenExtras.EVENT_TRANSACTION_ID, transId);
                                    startActivity(intConfirm);

                                });

                                try {

                                    if (!isFinishing()) {
                                        successDialog.show();
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }


                            } else if (Validate.isNotNull(Objects.requireNonNull(response.body()).getError_description())) {
                                if (otpVerificationDialog != null && otpVerificationDialog.isShowing())
                                    otpVerificationDialog.reset();
                                CC.showAlert(Objects.requireNonNull(response.body()).getError_description());

                            } else {
                                CC.showToast(R.string.msg_something_wrong);
                            }
                        } else {
                            CC.showToast(R.string.msg_no_response);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<CallbackInsertPlaceOrder> call, @NonNull Throwable t) {
                    if (mLoader != null && mLoader.isShowing()) mLoader.dismiss();
                    CC.showToast(R.string.msg_something_wrong);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            if (mLoader != null && mLoader.isShowing()) mLoader.dismiss();
            e.printStackTrace();
        }
    }*/

    //Submit Payment Data and Handle Success and Error Events
    public void netBanking() {
        try {
            //razorpay_order_id

            Log.e(TAG, "netBanking orderId =" + orderId + " , " + joNotes);


            JSONObject data = new JSONObject();
            data.put("email", Str_email);
            data.put("contact", Str_mobile);
            data.put("method", paymentMethod);
            data.put("bank", bank);
            data.put("currency", "INR");
            data.put("amount", (int) (totalAmt));
            data.put("order_id", orderId);
            data.put("notes", joNotes);
            data.put("description", planname);
            //data.put("razorpay_order_id", orderId);
            Log.d("data",data.toString());
            razorPaySubmitData(data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //Debit and Credit Card
    private void debitCreditCard() {
        try {
            JSONObject data = new JSONObject();
            data.put("amount", (int) (totalAmt));
            data.put("email", Str_email);
            data.put("contact", Str_mobile);
            data.put("method", paymentMethod);
            data.put("currency", "INR");
            data.put("card[name]", cardName);
            data.put("card[number]", cardNo);
            data.put("card[expiry_month]", expiry_month);
            data.put("card[expiry_year]", expiry_year);
            data.put("card[cvv]", cardCvv);
            data.put("order_id", orderId);
            data.put("notes", joNotes);
            data.put("description", planname);
            //data.put("notes", joNotes);
           // data.put("description", eventName);
            Log.d("data",data.toString());
            razorPaySubmitData(data);

            // data.put("card[name]", "Gaurav Kumar");
            //data.put("email", "gaurav.kumar@razorpay.com");
            //data.put("card[number]", "4111111111111111");
            //data.put("card[expiry_month]", "12");
            // data.put("card[expiry_year]", "20");
            //data.put("card[cvv]", "100");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void wallet() {
        try {
            JSONObject data = new JSONObject();
            data.put("amount", (int) (totalAmt));
            data.put("email", Str_email);
            data.put("contact", Str_mobile);
            data.put("method", paymentMethod);
            data.put("currency", "INR");
            data.put("wallet", walletType);
            data.put("order_id", orderId);
            data.put("notes", joNotes);
            data.put("description", planname);
          //  data.put("notes", joNotes);
           // data.put("description", eventName);
            Log.d("data",data.toString());
            razorPaySubmitData(data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

   /* private void upiCollectFlow(){
        try {
            JSONObject data = new JSONObject();
            data.put("amount",  (int) (totalAmt * 100));
            data.put("email", "" +  mPref.getUserEmailId());
            data.put("contact", "" +  mPref.getUserPhoneNumber());
            data.put("method", method);
            data.put("currency", "INR");
            data.put("vpa", "vpa@bank");
            razorPaySubmitData(data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }*/

    private void upiIntentFlow(String vpa) {
        try {
            JSONArray prefAppsJArray = new JSONArray();
            prefAppsJArray.put(Constant.UpiIntentMethod.BHIM_PACKAGE_NAME);
            prefAppsJArray.put(Constant.UpiIntentMethod.GOOGLE_PAY_PACKAGE_NAME);
            prefAppsJArray.put(Constant.UpiIntentMethod.PAYTM_PACKAGE_NAME);
            //prefAppsJArray.put(Constant.UpiIntentMethod.AMAZON_PAY_PACKAGE_NAME);
            //prefAppsJArray.put(Constant.UpiIntentMethod.PHONE_PAY_PACKAGE_NAME);

            JSONObject data = new JSONObject();
            data.put("amount", (int) (totalAmt));
            data.put("email", Str_email);
            data.put("contact", Str_mobile);
            data.put("currency", "INR");
            data.put("method", paymentMethod);
            data.put("_[flow]", "intent");
            data.put("order_id", orderId);
            data.put("notes", joNotes);
            data.put("description", planname);
           // data.put("notes", joNotes);
           // data.put("description", eventName);


            // pass package name that is returned in getAppsWhichSupportUpi and/or getAppsWhichSupportUpiAutoPay
            //data.put("upi_app_package_name", "in.org.npci.upiapp"); //For BHIM app
            //data.put("upi_app_package_name", prefAppsJArray);
            //data.put("preferred_apps_order", prefAppsJArray); //working
            //data.put("other_apps_order", prefAppsJArray);

            //This field is only valid when _[flow] is collect and is not required if _[flow] is intent
            //data.put("vpa", vpa);
            //data.put("_[flow]", "collect");
            Log.d("data",data.toString());
            razorPaySubmitData(data);
            //validateRequest(data);// add on 03/04/2023
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void razorPaySubmitData(JSONObject data) {
        try {
            Log.e(TAG, "razorPaySubmitData:" + data);

            // Make webView visible before submitting payment details
            webView.setVisibility(View.VISIBLE);


            razorpay.submit(data, new PaymentResultWithDataListener() {
                @Override
                public void onPaymentSuccess(String s, PaymentData paymentData) {
                    // Razorpay payment ID is passed here after a successful payment
                    Log.e(TAG, "onPaymentSuccess: PaymentId" + paymentData.getPaymentId());
                    Log.e(TAG, "onPaymentSuccess: Signature" + paymentData.getSignature());

                   /* try {
                        Answers.getInstance().logPurchase(new PurchaseEvent()
                                .putItemPrice(BigDecimal.valueOf(totalAmt))
                                .putCurrency(Currency.getInstance("INR"))
                                .putItemName(eventSessionData.getEvent_name())
                                .putItemType(eventSessionData.eventType())
                                .putItemId("" + eventSessionData.getId())
                                .putCustomAttribute("paymentID", "" + razorpayPaymentId)
                                .putSuccess(true));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }*/

                    try {
                        if (CC.isOnline()) {
                            String email = mLogin.getEmail();
                            String countryCode = mPref.getCountryCode();
                            String mobile = mLogin.getMobile();
                            String sessionId = eventSessionId;
                            try {
                                if (Validate.isNotNull(countryCode) && Validate.isNotNull(mobile)) {
                                    Phonenumber.PhoneNumber phoneNumber = phoneNumberUtil.parse(countryCode + mobile, Constant.DEFAULT_COUNTRY_REGION);
                                    countryCode = "+" + phoneNumber.getCountryCode();
                                    mobile = phoneNumber.getNationalNumber() + "";
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            Call_save_payment(paymentData.getOrderId(),paymentData.getSignature(),paymentData.getPaymentId());
                           // wsInsertPlaceOrder(mLogin.getUserId(), sessionId, eventSessionId, Constant.MerchantType.RAZORPAY, razorpayPaymentId, countryCode, mobile, email);

                        } else {
                            CC.showToast(R.string.msg_no_internet);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e(TAG, "onPaymentSuccess: " + e.getMessage());
                    }

                }

                @Override
                public void onPaymentError(int code, String description, PaymentData paymentData) {
                    // Error code and description is passed here
                    Log.e(TAG, "onPaymentError: " + " , code : " + code + ", description: " + description);
                    try {
                        JSONObject jsonObject = new JSONObject(description);
                        JSONObject joError = jsonObject.getJSONObject("error");
                        String des = joError.getString("description");

                        //if (code > 0) CC.showAlert("Payment Failure :", des);


                        if (code > 0) {
                            //CC.showAlert("Payment Failure :", des, android.R.string.yes, () -> onBackPressed());
                            CC.showAlert("Payment Failed", "If amount is debit from account it will refund in next 7 to 10 working days or mail us at cs@ticketninja.in", android.R.string.yes, () -> onBackPressed());
                            //openDialog("Payment Failed","If amount is debit from account it will refund in next 7 to 10 working days or mail us at cs@ticketninja.in");
                        }


                        /*Answers.getInstance().logPurchase(new PurchaseEvent()
                                .putItemPrice(BigDecimal.valueOf(totalAmt))
                                .putCurrency(Currency.getInstance("INR"))
                                .putItemName(eventSessionData.getEvent_name())
                                .putItemType(eventSessionData.eventType())
                                .putItemId("" + eventSessionData.getId())
                                .putCustomAttribute("errorCode", "" + code)
                                .putCustomAttribute("errorMessage", "" + des)
                                .putSuccess(false));*/

                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e(TAG, "onPaymentError: " + e.getMessage());
                    }

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "Error in submitting payment details", e);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            Log.e(TAG, "requestCode:" + requestCode + ",resultCode: " + resultCode);
            Log.e(TAG, "data: " + data);
            if (razorpay != null) {
                webView.setVisibility(View.VISIBLE);
                razorpay.onActivityResult(requestCode, resultCode, data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

  /*  private void wsInsertPlaceOrder(String user_Id, String session_Id, String orderId, String merchantType, String payment_Id, String countryCode,
                                    String mobileNo, String email) {
        try {
            if (!mLoader.isShowing() && !isFinishing()) mLoader.show();

            RequestInsertPlaceOrder request = new RequestInsertPlaceOrder(String.valueOf(user_Id), session_Id, orderId,
                    Constant.PaymentType.ONLINE, merchantType, payment_Id, countryCode, mobileNo, email);

            TicketNinjaAPI tnAPI = RestApi.createAPI();
            Call<CallbackInsertPlaceOrder> call = tnAPI.InsertPlaceOrder(request);
            call.enqueue(new Callback<CallbackInsertPlaceOrder>() {
                @Override
                public void onResponse(@NonNull Call<CallbackInsertPlaceOrder> call, @NonNull Response<CallbackInsertPlaceOrder> response) {
                    if (mLoader != null && mLoader.isShowing()) mLoader.dismiss();
                    try {
                        if (response.isSuccessful()) {
                            if (String.valueOf(RestApi.ErrorCode.SUCCESS).equals(Objects.requireNonNull(response.body()).getError_code())) {
                                if (otpVerificationDialog != null) otpVerificationDialog.dismiss();

                                long transId = Objects.requireNonNull(response.body()).data().getTransId();
                                String transNo = Objects.requireNonNull(response.body()).data().getTransNo();

                                //stopTimerService();
                                //mTimer.cancel();

                                PaymentSuccessDialog successDialog = new PaymentSuccessDialog(RazorPayCustomActivity.this);
                                // successDialog.setTransactionId(String.valueOf(transId));
                                successDialog.setTransactionId(String.valueOf(transNo));
                                if (totalAmt == 0) {
                                    successDialog.setAmount(R.string.str_price_free);
                                } else {
                                    successDialog.setAmount(String.format(Locale.getDefault(), "₹ %.2f", totalAmt));
                                }

                                successDialog.setOnOkClickListener(() -> {
                                    Intent intConfirm = new Intent(getApplicationContext(), BookingConfirmationActivity.class);
                                    intConfirm.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    intConfirm.putExtra(Constant.ScreenExtras.EVENT_TRANSACTION_TYPE, Constant.PaymentType.ONLINE);
                                    intConfirm.putExtra(Constant.ScreenExtras.EVENT_TRANSACTION_ID, transId);
                                    startActivity(intConfirm);

                                });

                                try {

                                    if (!isFinishing()) {
                                        successDialog.show();
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }


                            } else if (Validate.isNotNull(Objects.requireNonNull(response.body()).getError_description())) {
                                if (otpVerificationDialog != null && otpVerificationDialog.isShowing())
                                    otpVerificationDialog.reset();
                                CC.showAlert(Objects.requireNonNull(response.body()).getError_description());

                            } else {
                                CC.showToast(R.string.msg_something_wrong);
                            }
                        } else {
                            CC.showToast(R.string.msg_no_response);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<CallbackInsertPlaceOrder> call, @NonNull Throwable t) {
                    if (mLoader != null && mLoader.isShowing()) mLoader.dismiss();
                    CC.showToast(R.string.msg_something_wrong);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            if (mLoader != null && mLoader.isShowing()) mLoader.dismiss();
            e.printStackTrace();
        }
    }*/

    private void paytm() {
        try {
            if (isUserLoggedIn()) {
                if (CC.isOnline()) {

                    String mobileNo = mPref.getUserPhoneNumber();
                    try {
                        if (Validate.isNotNull(mPref.getCountryCode())) {
                            Phonenumber.PhoneNumber phoneNumber = phoneNumberUtil.parse(mPref.getCountryCode() + mobileNo, Constant.DEFAULT_COUNTRY_REGION);
                            mobileNo = phoneNumber.getNationalNumber() + "";
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                   // wsGeneratePaytmChecksum(eventSessionId, mobileNo);
                } else {
                    CC.showToast(R.string.msg_no_internet);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private boolean isUserLoggedIn() {
        try {
            // if (!backAlertDialog.isShowing()) {
            if (CC.isOnline()) {
//                if (mLogin.isLoggedIn()) {
                return true;
//                } else {
//                    openLogin("");
//                    return false;
//                }
            } else {
                CC.showToast(R.string.msg_no_internet);
                return false;
            }
          /*  } else {
                return false;
            }*/
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }


    // WS - Generate Paytm Checksum
   /* private void wsGeneratePaytmChecksum(String sessionId, String mobileNo) {

        try {
            if (!mLoader.isShowing() && !isFinishing()) mLoader.show();

            String value1 = String.format(Locale.getDefault(), "%.2f", totalAmt);
            Log.e(TAG, "value1:" + value1);

            boolean isDebuggable = (0 != (getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE));
            Log.e(TAG, "isDebuggable : " + !isDebuggable);

            RequestGeneratePaytmChecksum request = new RequestGeneratePaytmChecksum(mLogin.getUserId(), sessionId, value1, mPref.getUserEmailId(), mobileNo);

            TicketNinjaAPI tnAPI = RestApi.createAPI();
            Call<CallbackGeneratePaytmChecksum> call = tnAPI.GeneratePaytmChecksum(request);
            call.enqueue(new Callback<CallbackGeneratePaytmChecksum>() {
                @Override
                public void onResponse(@NonNull Call<CallbackGeneratePaytmChecksum> call, @NonNull Response<CallbackGeneratePaytmChecksum> response) {
                    if (mLoader != null && mLoader.isShowing()) mLoader.dismiss();
                    try {
                        if (response.isSuccessful()) {
                            if (String.valueOf(RestApi.ErrorCode.SUCCESS).equals(Objects.requireNonNull(response.body()).getError_code())) {

                                String mid = response.body().data().merchantID();
                                String orderId = response.body().data().orderId();
                                String custId = response.body().data().custID();
                                String industryTypeId = response.body().data().industryTypeId();
                                String channelId = response.body().data().channelID();
                                String website = response.body().data().website();
                                String amount = response.body().data().txnAmount();
                                String paytmChecksum = response.body().data().getCHECKSUMHASH();
                                String callbackUrl = response.body().data().callbackUrl();
                                String email = response.body().data().email();
                                String mobile = response.body().data().mobileNo();
                                String payment_mode_only = response.body().data().getPAYMENT_MODE_ONLY();
                                String typeId = response.body().data().getPAYMENT_TYPE_ID();
                               // boolean isPaytmLive = response.body().data().isPaytmLive();

                                boolean isDebuggable = (0 != (getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE));
                                Log.e(TAG, "isDebuggable : " + isDebuggable);

                                initializePayTmPayment(mid, orderId, custId, industryTypeId, channelId, website, amount, paytmChecksum, callbackUrl, email, mobile, !isDebuggable, payment_mode_only, typeId);


                            } else if (Validate.isNotNull(response.body().getError_description())) {
                                CC.showAlert(response.body().getError_description());
                            } else {
                                CC.showToast(R.string.msg_something_wrong);
                                finish();
                            }
                        } else {
                            CC.showToast(R.string.msg_no_response);
                            finish();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        CC.showToast(R.string.msg_something_wrong);
                        finish();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<CallbackGeneratePaytmChecksum> call, @NonNull Throwable t) {
                    if (mLoader != null && mLoader.isShowing()) mLoader.dismiss();
                    CC.showToast(R.string.msg_something_wrong);
                    t.printStackTrace();
                    finish();
                }
            });
        } catch (Exception e) {
            if (mLoader != null && mLoader.isShowing()) mLoader.dismiss();
            e.printStackTrace();
            finish();
        }

    }*/


    //paytm payment
    private void initializePayTmPayment(String mid, String orderId, String custId, String industryTypeId,
                                        String channelId, String website, String amount, String paytmChecksum,
                                        String callbackUrl, String email, String mobile, boolean isPaytmLive, String payment_mode_only, String typeId) {

        /*Log.e(TAG, "mid:" + mid);
        Log.e(TAG, "orderId:" + orderId);
        Log.e(TAG, "custId:" + custId);
        Log.e(TAG, "industryTypeId:" + industryTypeId);
        Log.e(TAG, "channelId:" + channelId);
        Log.e(TAG, "website:" + website);
        Log.e(TAG, "amount:" + amount);
        Log.e(TAG, "paytmChecksum:" + paytmChecksum);
        Log.e(TAG, "callbackUrl:" + callbackUrl);
        Log.e(TAG, "email:" + email);
        Log.e(TAG, "mobile:" + mobile);

        Log.e(TAG, "PAYTM_CALLBACK_URL:" + BuildConfig.PAYTM_CALLBACK_URL);*/
        Log.e(TAG, "isPaytmLive:" + isPaytmLive);
        try {

            //getting paytm service
            PaytmPGService service = PaytmPGService.getStagingService();

            //use this when using for production
            // if (isPaytmLive) service = PaytmPGService.getProductionService();
            try {
                if (PaytmConstant.payTmConfig.IS_PAYTM_LIVE)
                    service = PaytmPGService.getProductionService();
            } catch (Exception e) {
                e.printStackTrace();
            }

            //creating a hashmap and adding all the values require
            HashMap<String, String> paramMap = new HashMap<>();
          /*  paramMap.put(PaytmConstant.CHANNEL_ID, PaytmConstant.payTmConfig.CHANNEL_ID);
            paramMap.put(PaytmConstant.WEBSITE, PaytmConstant.payTmConfig.WEBSITE);
            paramMap.put(PaytmConstant.CALLBACK_URL, PaytmConstant.payTmConfig.CALLBACK_URL+ orderID);
            paramMap.put(PaytmConstant.INDUSTRY_TYPE_ID, PaytmConstant.payTmConfig.INDUSTRY_TYPE_ID);*/
            //paramMap.put(PaytmConstant.CALLBACK_URL, PaytmConstant.payTmConfig.CALLBACK_URL+ orderId);

            paramMap.put(PaytmConstant.CHANNEL_ID, channelId);
            paramMap.put(PaytmConstant.WEBSITE, website);
            paramMap.put(PaytmConstant.CALLBACK_URL, callbackUrl);
            paramMap.put(PaytmConstant.INDUSTRY_TYPE_ID, industryTypeId);
            paramMap.put(PaytmConstant.M_ID, mid);
            paramMap.put(PaytmConstant.ORDER_ID, orderId);
            paramMap.put(PaytmConstant.CUST_ID, custId);
            paramMap.put(PaytmConstant.TXN_AMOUNT, amount);
            paramMap.put(PaytmConstant.CHECKSUMHASH, paytmChecksum);
            paramMap.put(PaytmConstant.EMAIL_CODE, email);
            paramMap.put(PaytmConstant.MOBILE_NO_CODE, mobile);
            paramMap.put("PAYMENT_MODE_ONLY", payment_mode_only);
            paramMap.put("PAYMENT_TYPE_ID", typeId);
            Log.e(TAG, "paramMap:" + paramMap);


            //creating a paytm order object using the hashmap
            PaytmOrder Order = new PaytmOrder(paramMap);

            service.enableLog(this);

            //intializing the paytm service
            service.initialize(Order, null);

            if (!mLoader.isShowing() && !isFinishing()) mLoader.show();
            //finally starting the payment transaction
            service.startPaymentTransaction(this, true, false, new PaytmPaymentTransactionCallback() {
                @Override
                public void onTransactionResponse(Bundle inResponse) {

                    Log.e(TAG, "Paytm onTransactionResponse : " + inResponse);

                    //Toast.makeText(getApplicationContext(), "Payment Transaction response " + inResponse.toString(), Toast.LENGTH_LONG).show();

                    //Paytm onTransactionResponse : Bundle[{STATUS=TXN_FAILURE, ORDERID=ORD1414451, TXNAMOUNT=105.90, MID=NpQQaF98778758377058, RESPCODE=330, BANKTXNID=, CURRENCY=INR, RESPMSG=Invalid checksum}]

                    String status = inResponse.getString("STATUS", "");
                    if (status.equalsIgnoreCase("TXN_SUCCESS")) {


                   /* String txnamnt = inResponse.getString("TXNAMOUNT", "");
                    long userId = mLogin.getUserId();
                    String txndate = inResponse.getString("TXNDATE", "");
                    String MID = inResponse.getString("MID", "");
                    String respcode = inResponse.getString("RESPCODE", "");
                    String bankName = inResponse.getString("BANKNAME", "");
                    String paymentmode = inRhlesponse.getString("PAYMENTMODE", "");
                    String banktxnid = inResponse.getString("BANKTXNID", "");
                    String currency = inResponse.getString("CURRENCY", "INR");
                    String gatwayname = inResponse.getString("GATEWAYNAME", "");
                    String IS_CHECKSUM_VALID = inResponse.getString("IS_CHECKSUM_VALID", "Y");
                    String respmsg = inResponse.getString("RESPMSG", "");*/

                        //String payStatus = Constant.PAYMENT_SUCCESS_TITLE;
                        String orderId = inResponse.getString("ORDERID", "");
                        String txnid = inResponse.getString("TXNID", "");

                        if (CC.isOnline()) {
                            String countryCode = mPref.getCountryCode();
                            String mobile = mPref.getUserPhoneNumber();
                            try {
                                if (Validate.isNotNull(countryCode) && Validate.isNotNull(mobile)) {
                                    Phonenumber.PhoneNumber phoneNumber = phoneNumberUtil.parse(countryCode + mobile,
                                            Constant.DEFAULT_COUNTRY_REGION);
                                    countryCode = "+" + phoneNumber.getCountryCode();
                                    mobile = phoneNumber.getNationalNumber() + "";
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                         //   wsInsertPlaceOrder(mLogin.getUserId(), eventSessionId, orderId, Constant.MerchantType.PAYTM, txnid, countryCode, mobile, email);
                       /* callPaymentRespondWS(userId, orderId, bankName, payStatus,
                                txnamnt, txndate, txnid, respcode, paymentmode,
                                banktxnid, currency, gatwayname, respmsg, status, IS_CHECKSUM_VALID);*/
                        } else {
                            CC.showAlert(R.string.payment_msg_success_no_internet,
                                    () -> {
                                       // Intent intSuccess = new Intent(getApplicationContext(), LandingActivity.class);
                                       Intent intSuccess = new Intent(getApplicationContext(), DashboardActivity.class);
                                        intSuccess.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
                                                | Intent.FLAG_ACTIVITY_CLEAR_TOP
                                                | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intSuccess);
                                    });
                        }

                        // After successful transaction this method gets called.
                        // // Response bundle contains the merchant response
                        // parameters.
                        Log.e(TAG, "Paytm Payment Transaction is successful " + inResponse);
                    } else {
                    /*long userId = mLogin.getUserId();
                    String orderId = inResponse.getString("ORDERID", "");
                    String txnamnt = inResponse.getString("TXNAMOUNT", "");
                    String txndate = inResponse.getString("TXNDATE", "");
                    String MID = inResponse.getString("MID", "");
                    String respcode = inResponse.getString("RESPCODE", "");
                    String bankName = inResponse.getString("BANKNAME", "");
                    String paymentmode = inResponse.getString("PAYMENTMODE", "");
                    String txnid = inResponse.getString("TXNID", "");
                    String banktxnid = inResponse.getString("BANKTXNID", "");
                    String currency = inResponse.getString("CURRENCY", "INR");
                    String gatwayname = inResponse.getString("GATEWAYNAME", "");
                    String IS_CHECKSUM_VALID = inResponse.getString("IS_CHECKSUM_VALID", "Y");*/

                        // String payStatus = Constant.PAYMENT_FAILED_TITLE;
                        String respMsg = inResponse.getString("RESPMSG", "");

                        if (Validate.isNotNull(respMsg)) {
                            //CC.showAlert(respMsg);
                            CC.showAlert("Payment Failure :", respMsg, android.R.string.yes, () -> onBackPressed());
                            return;
                        }

                       /* if (status.equalsIgnoreCase("PENDING")) {
                            payStatus = Constant.PAYMENT_PENDING_TITLE;
                        }*/

                    /*callPaymentRespondWS(userId, orderId, bankName, payStatus,
                            txnamnt, txndate, txnid, respcode, paymentmode,
                            banktxnid, currency, gatwayname, respmsg, status, IS_CHECKSUM_VALID);*/

                        //Log.e(TAG, "Payment Transaction Failed " + inErrorMessage);
                        Log.e(TAG, "Paytm Payment Transaction Failed " + inResponse);
                    }
                }

                @Override
                public void networkNotAvailable() {
                    Log.e(TAG, "Paytm networkNotAvailable ");
                    CC.showToast(R.string.payment_msg_no_internet);
                    finish();
                }

                @Override
                public void clientAuthenticationFailed(String inErrorMessage) {
                    Log.e(TAG, "Paytm clientAuthenticationFailed " + inErrorMessage);
                    CC.showToast(R.string.payment_msg_error);
                    finish();
                }

                @Override
                public void someUIErrorOccurred(String inErrorMessage) {
                    Log.e(TAG, "Paytm someUIErrorOccurred = " + inErrorMessage);
                    CC.showToast(R.string.payment_msg_error);
                    finish();
                }

                @Override
                public void onErrorLoadingWebPage(int iniErrorCode, String inErrorMessage, String inFailingUrl) {
                    Log.e(TAG, "Paytm onErrorLoadingWebPage " + iniErrorCode);
                    Log.e(TAG, "Paytm onErrorLoadingWebPage " + inErrorMessage);
                    Log.e(TAG, "Paytm onErrorLoadingWebPage " + inFailingUrl);
                    CC.showToast(R.string.payment_msg_error);
                    finish();
                }

                @Override
                public void onBackPressedCancelTransaction() {
                    Log.e(TAG, "Paytm onBackPressedCancelTransaction");
                    //callPaymentStatusUpdateWS(data.getOrderId(), Constant.PAYMENT_CANCELED_TITLE);
                    CC.showToast(R.string.payment_msg_cancel);
                    finish();
                }

                @Override
                public void onTransactionCancel(String s, Bundle bundle) {
                    Log.e(TAG, "Paytm onTransactionCancel");
                    //callPaymentStatusUpdateWS(data.getOrderId(), Constant.PAYMENT_CANCELED_TITLE);
                    CC.showToast(R.string.payment_msg_cancel);
                    finish();
                }


            });
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "paytm catch: " + e.getMessage());
        }
    }



    //add on 03/04/2023
    /*
    private void validateRequest(JSONObject jsonObject) {
        razorpay.validateFields(jsonObject, new ValidationListener() {
            @Override
            public void onValidationSuccess() {
                try {
                    //razorPaySubmitData(jsonObject);
                     Razorpay.getAppsWhichSupportUpi(mContext, new RzpUpiSupportedAppsCallback() {
                        @Override
                        public void onReceiveUpiSupportedApps(List<ApplicationDetails> list) {
                            List<ApplicationDetails> mList = list;
                            Log.e(TAG, "mList:" + mList);
                            razorPaySubmitData(jsonObject);
                        }
                    });
                } catch (Exception e) {
                    Log.e(TAG, "Exception: ", e);
                }
            }

            @Override
            public void onValidationError(Map<String, String> error) {
                Log.e(TAG, "Validation failed: " + error.get("field") + " " + error.get("description"));
            }
        });
    }
  */

    //add on 03/04/2023
    /*
    public void showAlertDialog() {
        // Create an alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Please enter VPA");

        // set the custom layout
        final View customLayout = getLayoutInflater().inflate(R.layout.alert_vpa_dialog, null);
        builder.setView(customLayout);

        EditText editText = customLayout.findViewById(R.id.editText);
        editText.setText("gaurav.kumar@exampleupi");

        // add a button
        builder.setPositiveButton("OK", (dialog, which) -> {
            // send data from the AlertDialog to the Activity
            upiIntentFlow(editText.getText().toString());
        });
        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }
 */

    /*private void openDialog(String title, String msg) {
        try {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            // alertDialog.setTitle("Android Alert Message");
            //alertDialog.setMessage("Put your description text here!");
            alertDialog.setCancelable(true);
            alertDialog.setView(R.layout.razor_pay_alert_dialog);
            alertDialog.setPositiveButton(android.R.string.ok, (dialogInterface, i) -> dialogInterface.dismiss());
            AlertDialog dialog = alertDialog.create();
            if (!isFinishing()) dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }*/

   /* private void updateTimer() {
        if (orderSummaryActivity != null) {
            orderSummaryActivity.resetTimer(this);
        }
    }*/

    /*public void payUsingUpiIntent(String amount, String upiId, String name, String note) {

     *//* String appPackageName = "net.one97.paytm";
        PackageManager pm = getPackageManager();
        Intent appstart = pm.getLaunchIntentForPackage(appPackageName);
        if(null!=appstart)
        {
            startActivity(appstart);
        }
        else {
            Toast.makeText(this, "Install PayTm on your device", Toast.LENGTH_SHORT).show();
        }*//*

        Uri uri = Uri.parse("upi://pay").buildUpon()
                .appendQueryParameter("pa", upiId)
                .appendQueryParameter("pn", name)
                .appendQueryParameter("tn", note)
                .appendQueryParameter("am", amount)
                .appendQueryParameter("cu", "INR")
                .build();

        Intent upiPayIntent = new Intent(Intent.ACTION_VIEW);
        upiPayIntent.setData(uri);

        // will always show a dialog to user to choose an apptk/f
        Intent chooser = Intent.createChooser(upiPayIntent, "Pay with");

        // check if intent resolves
        if (null != chooser.resolveActivity(getPackageManager())) {
            startActivityForResult(chooser, 0);
        } else {
            Toast.makeText(this, "No UPI app found, please install one to continue", Toast.LENGTH_SHORT).show();
        }

    }*/
}


/*

https://api.razorpay.com/v1/methods?key_id=rzp_test_MMS488XxffOfEy

{"entity":"methods","card":true,"debit_card":true,"credit_card":true,"prepaid_card":true,"card_networks":{"AMEX":0,"DICL":0,"MC":1,"MAES":1,"VISA":1,"JCB":1,"RUPAY":1,"BAJAJ":0},"amex":false,"netbanking":{"AUBL":"AU Small Finance Bank","ABPB":"Aditya Birla Idea Payments Bank","AIRP":"Airtel Payments Bank","ALLA":"Allahabad Bank","ANDB":"Andhra Bank","UTIB":"Axis Bank","BBKM":"Bank of Bahrein and Kuwait","BARB_R":"Bank of Baroda - Retail Banking","BKID":"Bank of India","MAHB":"Bank of Maharashtra","CNRB":"Canara Bank","CSBK":"Catholic Syrian Bank","CBIN":"Central Bank of India","CIUB":"City Union Bank","CORP":"Corporation Bank","COSB":"Cosmos Co-operative Bank","DCBL":"DCB Bank","BKDN":"Dena Bank","DEUT":"Deutsche Bank","DBSS":"Development Bank of Singapore","DLXB":"Dhanlaxmi Bank","ESFB":"Equitas Small Finance Bank","FDRL":"Federal Bank","HDFC":"HDFC Bank","ICIC":"ICICI Bank","IBKL":"IDBI","IDFB":"IDFC FIRST Bank","IDIB":"Indian Bank","IOBA":"Indian Overseas Bank","INDB":"Indusind Bank","JAKA":"Jammu and Kashmir Bank","JSBP":"Janata Sahakari Bank (Pune)","KARB":"Karnataka Bank","KVBL":"Karur Vysya Bank","KKBK":"Kotak Mahindra Bank","LAVB_C":"Lakshmi Vilas Bank - Corporate Banking","LAVB_R":"Lakshmi Vilas Bank - Retail Banking","NKGS":"NKGSB Co-operative Bank","ORBC":"Oriental Bank of Commerce","PMCB":"Punjab & Maharashtra Co-operative Bank","PSIB":"Punjab & Sind Bank","PUNB_R":"Punjab National Bank - Retail Banking","RATN":"RBL Bank","SRCB":"Saraswat Co-operative Bank","SVCB":"Shamrao Vithal Co-operative Bank","SIBL":"South Indian Bank","SCBL":"Standard Chartered Bank","SBBJ":"State Bank of Bikaner and Jaipur","SBHY":"State Bank of Hyderabad","SBIN":"State Bank of India","SBMY":"State Bank of Mysore","STBP":"State Bank of Patiala","SBTR":"State Bank of Travancore","SYNB":"Syndicate Bank","TMBL":"Tamilnadu Mercantile Bank","TNSC":"Tamilnadu State Apex Co-operative Bank","UCBA":"UCO Bank","UBIN":"Union Bank of India","UTBI":"United Bank of India","VIJB":"Vijaya Bank","YESB":"Yes Bank"},"wallet":{"payzapp":true,"olamoney":true,"amazonpay":true,"freecharge":true},"emi":false,"upi":true,"cardless_emi":[],"paylater":[],"upi_intent":true}
http://joshskeen.com/building-a-radiogroup-recyclerview/

 */