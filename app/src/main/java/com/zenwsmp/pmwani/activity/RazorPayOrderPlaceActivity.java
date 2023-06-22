package com.zenwsmp.pmwani.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.razorpay.PaymentMethodsCallback;
import com.razorpay.Razorpay;
import com.zenwsmp.pmwani.ConfigAPI;
import com.zenwsmp.pmwani.R;
import com.zenwsmp.pmwani.adapter.RazorPayRadioAdapter;
import com.zenwsmp.pmwani.common.CommonClass;
import com.zenwsmp.pmwani.common.Constant;
import com.zenwsmp.pmwani.common.Utility;
import com.zenwsmp.pmwani.objects.RazorPayPayment;
import com.zenwsmp.pmwani.objects.TicketSessionData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;


import in.ticketninja.widget.SRKLoaderDialog;


public class RazorPayOrderPlaceActivity extends AppCompatActivity implements RazorPayRadioAdapter.SelectedPaymentMethod {
    private static final String TAG = RazorPayOrderPlaceActivity.class.getSimpleName();
    private String selectedItem = "";
    private String eventSessionId = "";
    private String planid = "";
    private String planname = "";
    private double planprice = 0;
    private TicketSessionData eventSessionData;
    private final ArrayList<RazorPayPayment> stringArrayList = new ArrayList<>();
    private String type = "";
    private SRKLoaderDialog mLoader;
    private CommonClass CC;
    private RecyclerView recyclerView;
    private Razorpay razorpay;
    private Button btnSubmit;
    private LinearLayout llPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_razorpay_place_order);
        eventSessionData = new TicketSessionData();
        getIntentData();

        findViewById();

        initializeData();

        btnSubmit.setOnClickListener(view -> {
            Log.e(TAG, "selectedItem =" +selectedItem);
            try {
                if (Constant.RazorPayPaymentType.CREDIT_DEBIT_CARD.equals(selectedItem)) {
                    Intent intent = new Intent(RazorPayOrderPlaceActivity.this, RazorPayCreditDebitCardActivity.class);
                    intent.putExtra("planid",planid);
                    intent.putExtra("planname",planname);
                    //intent.putExtra("planid",planprice);
                    intent.putExtra(Constant.ScreenExtras.EVENT_SESSION_ID, eventSessionId);
                    intent.putExtra(Constant.ScreenExtras.TICKET_SESSION_DATA, eventSessionData);
                    startActivity(intent);
                } else if (Constant.RazorPayPaymentType.NETBANKING.equals(selectedItem)) {
                    Intent intent = new Intent(RazorPayOrderPlaceActivity.this, RazorPayNetBankingActivity.class);
                    intent.putExtra("planid",planid);
                    intent.putExtra("planname",planname);
                    intent.putExtra(Constant.ScreenExtras.EVENT_SESSION_ID, eventSessionId);
                    intent.putExtra(Constant.ScreenExtras.TICKET_SESSION_DATA, eventSessionData);
                    startActivity(intent);
                } else if (Constant.RazorPayPaymentType.WALLET.equals(selectedItem)) {
                    Intent intent = new Intent(RazorPayOrderPlaceActivity.this, RazorPayOrderPlaceActivity.class);
                    intent.putExtra("planid",planid);
                    intent.putExtra("planname",planname);
                    intent.putExtra(Constant.ScreenExtras.EVENT_SESSION_ID, eventSessionId);
                    intent.putExtra(Constant.ScreenExtras.TICKET_SESSION_DATA, eventSessionData);
                    intent.putExtra(Constant.ScreenExtras.TYPE, Constant.RazorPayPaymentType.WALLET);
                    startActivity(intent);
                } else if (Constant.RazorPayPaymentType.UPI.equals(selectedItem)) {
                    Intent intent = new Intent(RazorPayOrderPlaceActivity.this, RazorPayUpiActivity.class);
                    intent.putExtra("planid",planid);
                    intent.putExtra("planname",planname);
                    intent.putExtra(Constant.ScreenExtras.EVENT_SESSION_ID, eventSessionId);
                    intent.putExtra(Constant.ScreenExtras.TICKET_SESSION_DATA, eventSessionData);
                    startActivity(intent);
                } else {
                    if (Constant.RazorPayPaymentType.WALLET.equals(type) && !TextUtils.isEmpty(selectedItem)) {
                        Intent intent = new Intent(RazorPayOrderPlaceActivity.this, RazorPayCustomActivity.class);
                        intent.putExtra("planid",planid);
                        intent.putExtra("planname",planname);
                        intent.putExtra(Constant.ScreenExtras.PAYMENT_METHOD, Constant.RazorPayPaymentMethod.WALLET);
                        intent.putExtra(Constant.ScreenExtras.EVENT_SESSION_ID, eventSessionId);
                        intent.putExtra(Constant.ScreenExtras.TICKET_SESSION_DATA, eventSessionData);
                        intent.putExtra(Constant.ScreenExtras.WALLET_TYPE, selectedItem);
                        startActivity(intent);
                    } else {
                        Toast.makeText(this, "Please select at least one payment method", Toast.LENGTH_SHORT).show();
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        });


        setUpToolbar();

        if (Constant.RazorPayPaymentType.WALLET.equals(type)) {
            initializeRazorPayWalletMethod();
        } else {
            RazorPayPayment payment = new RazorPayPayment();
            payment.setTitle(Constant.RazorPayPaymentType.CREDIT_DEBIT_CARD);
            payment.setIcon(R.drawable.card);
            stringArrayList.add(payment);

            RazorPayPayment payment1 = new RazorPayPayment();
            payment1.setTitle(Constant.RazorPayPaymentType.NETBANKING);
            payment1.setIcon(R.drawable.net_banking);
            stringArrayList.add(payment1);

            RazorPayPayment payment2 = new RazorPayPayment();
            payment2.setTitle(Constant.RazorPayPaymentType.WALLET);
            payment2.setIcon(R.drawable.mobile_walet);
            stringArrayList.add(payment2);

            RazorPayPayment payment3 = new RazorPayPayment();
            payment3.setTitle(Constant.RazorPayPaymentType.UPI);
            payment3.setIcon(R.drawable.ic_bhim_upi);
            stringArrayList.add(payment3);

            setAdapter("");
        }

    }

    private void initializeData() {
        try {
            mLoader = new SRKLoaderDialog(this);
            CC = new CommonClass(this);
            razorpay = new Razorpay(this, getResources().getString(R.string.razor_pay_key));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void findViewById() {
        try {
            recyclerView = findViewById(R.id.recyclerView);
            btnSubmit = findViewById(R.id.btnSubmit);
            llPay = findViewById(R.id.llPay);

            if (Constant.RazorPayPaymentType.WALLET.equals(type)) {
                llPay.setEnabled(false);
                btnSubmit.setEnabled(false);
                btnSubmit.setText(String.format("%s - %s %s", getResources().getString(R.string.btn_pay), getResources().getString(R.string.rs1),String.format(Locale.getDefault(), " %.2f", eventSessionData.getNetamount())));
            }else {
                btnSubmit.setText(getResources().getString(R.string.btn_pay));
                btnSubmit.setText(String.format("%s - %s %s", getResources().getString(R.string.btn_pay), getResources().getString(R.string.rs1),String.format(Locale.getDefault(), " %.2f", eventSessionData.getNetamount())));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private void getIntentData() {
        try {
            Intent i = getIntent();
            planid = i.getStringExtra("planid");
            planname = i.getStringExtra("planname");
            planprice = i.getDoubleExtra("planprice",0.0);
            eventSessionId = i.getStringExtra(Constant.ScreenExtras.EVENT_SESSION_ID);
            type = i.getStringExtra(Constant.ScreenExtras.TYPE);
            eventSessionData = (TicketSessionData) i.getSerializableExtra(Constant.ScreenExtras.TICKET_SESSION_DATA);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void getSelectedPaymentMethod(String s) {
        try {
            Log.e("SelectedPaymentMethod", s);
            selectedItem = s;
            llPay.setEnabled(true);
            btnSubmit.setEnabled(true);
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    private void setUpToolbar() {
        try {
            // ACTIONBAR
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

                if (Constant.RazorPayPaymentType.WALLET.equals(type)) {
                    tvTitle.setText(R.string.title_activity_wallet);
                }else {
                    tvTitle.setText(R.string.title_activity_select_payment);
                }
                Utility.convertToLowerCase(tvTitle);

                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
                    findViewById(R.id.toolbarBottomDivider).setVisibility(View.VISIBLE);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void setAdapter(String type) {
        try {
            selectedItem = stringArrayList.get(0).getTitle();
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            RazorPayRadioAdapter adapter = new RazorPayRadioAdapter(this, stringArrayList,type);
            adapter.setPayment(this);
            recyclerView.setAdapter(adapter);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    //instantiate and Initialize RazorPay Android Custom SDK
    public void initializeRazorPayWalletMethod() {

        try {
            if (!mLoader.isShowing() && !isFinishing()) mLoader.show();
            // Razorpay razorpay = new Razorpay(this, getResources().getString(R.string.razor_pay_key));

            //This fetches the list of payment methods
            //razorpay.getPaymentMethods(new Razorpay.PaymentMethodsCallback() { //remove 04/04/2023
            razorpay.getPaymentMethods(new PaymentMethodsCallback() { //add 04/04/2023
                @Override
                public void onPaymentMethodsReceived(String result) {
                    try {

                        if (ConfigAPI.isIs_paytmwallet()) {
                            RazorPayPayment razorpayPayment = new RazorPayPayment();
                            razorpayPayment.setTitle(Constant.WalletName.PAYTM);
                            razorpayPayment.setWalletIcon("");
                            razorpayPayment.setIcon(R.drawable.ic_paytm);
                            razorpayPayment.setWalletName(Constant.WalletName.PAYTM);
                            stringArrayList.add(razorpayPayment);
                        }

                        JSONObject paymentMethods = new JSONObject(result);
                        JSONObject jo = paymentMethods.getJSONObject("wallet");
                        // get all keys from categoryJSONObj
                        Iterator<String> iterator = jo.keys();
                        while (iterator.hasNext()) {
                            String key = iterator.next();
                            Log.e(TAG, "key= " + key);
                            boolean value = jo.optBoolean(key);
                            if (value) {
                                String walletIcon = razorpay.getWalletLogoUrl(key);
                                RazorPayPayment payment = new RazorPayPayment();

                                if (Constant.WalletApiName.PAY_Z.equals(key)) {
                                    payment.setTitle(Constant.WalletName.PAY_Z);
                                } else if (Constant.WalletApiName.AMAZON_PAY.equals(key)) {
                                    payment.setTitle(Constant.WalletName.AMAZON_PAY);
                                } else if (Constant.WalletApiName.FREE_CHARGE.equals(key)) {
                                    payment.setTitle(Constant.WalletName.FREE_CHARGE);
                                } else if (Constant.WalletApiName.MOBIKWIK.equals(key)) {
                                    payment.setTitle(Constant.WalletName.MOBIKWIK);
                                } else if (Constant.WalletApiName.AIRTEL_MONEY.equals(key)) {
                                    payment.setTitle(Constant.WalletName.AIRTEL_MONEY);
                                } else if (Constant.WalletApiName.JIO_MONEY.equals(key)) {
                                    payment.setTitle(Constant.WalletName.JIO_MONEY);
                                } else if (Constant.WalletApiName.PHONE_PE.equals(key)) {
                                    payment.setTitle(Constant.WalletName.PHONE_PE);
                                } else {
                                    payment.setTitle(key);
                                }

                                payment.setWalletIcon(walletIcon);
                                payment.setWalletName(key);
                                stringArrayList.add(payment);
                                //add on 04/07/2023
                               /* if (!Constant.WalletApiName.PAYTM.equals(key)) {
                                    stringArrayList.add(payment);
                                }*/
                            }
                            //Log.e("TAG","key:"+key +"--Value::"+jo.optString(key));
                        }


                        Log.e(TAG," stringArrayList: " + stringArrayList);
                        if (mLoader != null && mLoader.isShowing()) mLoader.dismiss();
                        setAdapter(Constant.RazorPayPaymentType.WALLET);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(String error) {
                    CC.showAlert(error, () -> onBackPressed());
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
       /* Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, returnIntent);
        finish();*/
    }

}
