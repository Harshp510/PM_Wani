package com.zenwsmp.pmwani.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
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
import com.zenwsmp.pmwani.R;
import com.zenwsmp.pmwani.adapter.RazorPayNetBankingAdapter;
import com.zenwsmp.pmwani.common.CommonClass;
import com.zenwsmp.pmwani.common.Constant;
import com.zenwsmp.pmwani.common.Utility;
import com.zenwsmp.pmwani.objects.RazorPayBankList;
import com.zenwsmp.pmwani.objects.TicketSessionData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;


import in.ticketninja.widget.SRKLoaderDialog;


public class RazorPayNetBankingActivity extends AppCompatActivity implements RazorPayNetBankingAdapter.SelectedPaymentMethod {

    private static final String TAG = RazorPayNetBankingActivity.class.getSimpleName();
    private EditText et_search;
    private RazorPayNetBankingAdapter mAdapter;
    private RecyclerView recyclerView;
    private final ArrayList<RazorPayBankList> stringArrayList = new ArrayList<>();
    private Activity activity;
    private SRKLoaderDialog mLoader;
    private CommonClass CC;
    private RazorPayBankList selectedBank;
    private String eventSessionId = "";
    private String planid = "";
    private String planname = "";
    private TicketSessionData eventSessionData;
    private Button btnSubmit;
    private LinearLayout llPay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_razorpay_bank_selection);

        try {
            activity = this;
            mLoader = new SRKLoaderDialog(this);
            CC = new CommonClass(this);

            Utility.hideKeyboard(this);
            getIntentData();

            findViewById();

            btnSubmit.setOnClickListener(view -> {

                try {
                    Log.e(TAG, "selectedBank: " + selectedBank);
                    if (selectedBank != null) {
                        Intent intent = new Intent(RazorPayNetBankingActivity.this, RazorPayCustomActivity.class);
                        intent.putExtra(Constant.ScreenExtras.BANK_NAME, selectedBank.bankSortName);
                        intent.putExtra(Constant.ScreenExtras.PAYMENT_METHOD, Constant.RazorPayPaymentMethod.NET_BANKING);
                        intent.putExtra(Constant.ScreenExtras.EVENT_SESSION_ID, eventSessionId);
                        intent.putExtra(Constant.ScreenExtras.TICKET_SESSION_DATA, eventSessionData);
                        intent.putExtra("planid",planid);
                        intent.putExtra("planname",planname);
                        startActivity(intent);
                    } else {
                        Toast.makeText(this, "  select at least one bank", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            });

            // ACTIONBAR
            setUpToolbar();

            initializeRazorPay();


            et_search.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    mAdapter.getFilter().filter(s.toString());

                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });

            et_search.setOnEditorActionListener((v1, actionId, event) -> {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    Utility.hideKeyboard(RazorPayNetBankingActivity.this);
                    if (et_search != null) {
                        mAdapter.getFilter().filter(et_search.getText().toString().trim());
                    }
                    return true;
                }
                return false;
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void findViewById() {
        try {
            llPay = findViewById(R.id.llPay);
            recyclerView = findViewById(R.id.recyclerView);
            et_search = findViewById(R.id.et_search);
            Utility.setEditTextSingleLine(et_search);
            btnSubmit = findViewById(R.id.btnSubmit);
            llPay.setEnabled(false);
            btnSubmit.setEnabled(false);
            btnSubmit.setText(String.format("%s - %s %s", getResources().getString(R.string.btn_pay), getResources().getString(R.string.rs1), String.format(Locale.getDefault(), "%.2f",eventSessionData.getNetamount())));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void getIntentData() {
        try {
            Intent i = getIntent();
            planid = i.getStringExtra("planid");
            planname = i.getStringExtra("planname");
            eventSessionId = i.getStringExtra(Constant.ScreenExtras.EVENT_SESSION_ID);
            eventSessionData = (TicketSessionData) i.getSerializableExtra(Constant.ScreenExtras.TICKET_SESSION_DATA);
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
                tvTitle.setText(R.string.title_activity_select_bank);
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
            if (!mLoader.isShowing() && !isFinishing()) mLoader.show();
            Razorpay razorpay = new Razorpay(activity, getResources().getString(R.string.razor_pay_key));

            //This fetches the list of payment methods
            //razorpay.getPaymentMethods(new Razorpay.PaymentMethodsCallback() { //remove 04/04/2023
            razorpay.getPaymentMethods(new PaymentMethodsCallback() { //add 04/04/2023
                @Override
                public void onPaymentMethodsReceived(String result) {
                    try {

                        ArrayList<RazorPayBankList> firstBank = new ArrayList<>();
                        ArrayList<RazorPayBankList> secondBank = new ArrayList<>();
                        ArrayList<RazorPayBankList> thirdBank = new ArrayList<>();
                        ArrayList<RazorPayBankList> fourthBank = new ArrayList<>();
                        ArrayList<RazorPayBankList> fifthBank = new ArrayList<>();
                       // ArrayList<RazorPayBankList> sixBank = new ArrayList<>();

                        ArrayList<RazorPayBankList> firstArrayList = new ArrayList<>();
                        ArrayList<RazorPayBankList> arrayList = new ArrayList<>();

                        JSONObject paymentMethods = new JSONObject(result);
                        Log.e(TAG, "paymentMethods: " + paymentMethods);
                        JSONObject jo = paymentMethods.getJSONObject("netbanking");
                        // get all keys from categoryJSONObj
                        Iterator<String> iterator = jo.keys();
                        while (iterator.hasNext()) {
                            String key = iterator.next();
                            String value = jo.optString(key);
                            RazorPayBankList bankList = new RazorPayBankList();
                            bankList.bankName = value;
                            bankList.bankSortName = key;
                            bankList.bankIcon = razorpay.getBankLogoUrl(key);

                            if (Constant.BankName.SBI.equals(key)) {
                                firstBank.add(bankList);
                            } else if (Constant.BankName.HDFC.equals(key)) {
                                secondBank.add(bankList);
                            } else if (Constant.BankName.ICICI.equals(key)) {
                                thirdBank.add(bankList);
                            } else if (Constant.BankName.AXIS.equals(key)) {
                                fourthBank.add(bankList);
                            } else if (Constant.BankName.KOTAK.equals(key)) {
                                fifthBank.add(bankList);
                            } /*else if (Constant.BankName.YES.equals(key)) {
                                sixBank.add(bankList);
                            }*/ else {
                                arrayList.add(bankList);
                            }
                            //Log.e("TAG","key:"+key +"--Value::"+jo.optString(key));
                        }

                        firstArrayList.addAll(firstBank);
                        firstArrayList.addAll(secondBank);
                        firstArrayList.addAll(thirdBank);
                        firstArrayList.addAll(fourthBank);
                        firstArrayList.addAll(fifthBank);
                        //firstArrayList.addAll(sixBank);

                        stringArrayList.addAll(firstArrayList);
                        stringArrayList.addAll(arrayList);

                        Log.e(TAG, " stringArrayList: " + stringArrayList);
                        if (mLoader != null && mLoader.isShowing()) mLoader.dismiss();
                        setAdapter();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        if (mLoader != null && mLoader.isShowing()) mLoader.dismiss();
                    }
                }

                @Override
                public void onError(String error) {
                    if (mLoader != null && mLoader.isShowing()) mLoader.dismiss();
                    CC.showAlert(error, () -> onBackPressed());
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (mLoader != null && mLoader.isShowing()) mLoader.dismiss();
        }
    }

    private void setAdapter() {

        try {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            mAdapter = new RazorPayNetBankingAdapter(this, stringArrayList);
            mAdapter.setPayment(this);
            recyclerView.setAdapter(mAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        try {
            et_search.setText("");
            llPay.setEnabled(false);
            btnSubmit.setEnabled(false);
            selectedBank = null;
            mAdapter.updateData();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onBackPressed() {
        try {
            Utility.hideKeyboard(this);
            super.onBackPressed();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void getSelectedPaymentMethod(RazorPayBankList list) {
        try {
            selectedBank = list;
            llPay.setEnabled(true);
            btnSubmit.setEnabled(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
