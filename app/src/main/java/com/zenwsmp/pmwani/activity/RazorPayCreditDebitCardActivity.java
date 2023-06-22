package com.zenwsmp.pmwani.activity;

import static com.zenwsmp.pmwani.common.Utility.changeDrawableColor;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.razorpay.Razorpay;
import com.razorpay.ValidationListener;
import com.zenwsmp.pmwani.R;
import com.zenwsmp.pmwani.adapter.RazorPayCardSpinner;
import com.zenwsmp.pmwani.adapter.RazorPayCardSpinner1;
import com.zenwsmp.pmwani.common.Constant;
import com.zenwsmp.pmwani.common.PreferencesUtils;
import com.zenwsmp.pmwani.common.Utility;
import com.zenwsmp.pmwani.objects.RazorPayCardList;
import com.zenwsmp.pmwani.objects.TicketSessionData;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;




public class RazorPayCreditDebitCardActivity extends AppCompatActivity implements RazorPayCardSpinner.SelectedValue, RazorPayCardSpinner1.SelectedValue {

    private static final String TAG = RazorPayCreditDebitCardActivity.class.getSimpleName();
    private Activity activity;
    private Spinner monthSpinner, yearSpinner;
    private TextInputLayout tiCardNo, tiCardName, tiCardCvv;
    private TextInputEditText edtCardName, edtCardCvv;
    private TextInputEditText edtCardNo;
    private Razorpay razorpay;
    private PreferencesUtils mPref;
    // private LinearLayout llRazorPay;
    private Dialog dialog;
    private Button btnSubmit;
    private LinearLayout llMonthSpinner, llYearSpinner;

    private boolean isValidCardNumber;
    private String eventSessionId = "";
    private String planid = "";
    private String planname = "";
    private TicketSessionData eventSessionData;
    private TextView txtMonth, txtYear;

    private ImageView img_info;
    private int selectedMonthPos = 0, selectedYearPos = 0;
    private String selectedMonthItemNo = "";
    private String selectedMonthItem = "";
    private String selectedYearItem = "";
    private String selectedYear;
    private int currentMonth, currentYear;


   /* private static final int CARD_DATE_TOTAL_SYMBOLS = 5; // size of pattern MM/YY
    private static final int CARD_DATE_TOTAL_DIGITS = 4; // max numbers of digits in pattern: MM + YY
    private static final int CARD_DATE_DIVIDER_MODULO = 3; // means divider position is every 3rd symbol beginning with 1
    private static final int CARD_DATE_DIVIDER_POSITION = CARD_DATE_DIVIDER_MODULO - 1; // means divider position is every 2nd symbol beginning with 0
    private static final char CARD_DATE_DIVIDER = '/';
    private static final int CARD_CVC_TOTAL_SYMBOLS = 3;*/


    private static final String EMPTY_STRING = "";
    private static final String WHITE_SPACE = "  ";
    private String lastSource = EMPTY_STRING;
    //private boolean lock;
    TextWatcher tt = null;
    private ScrollView sv;
    SharedPreferences sp_userdetail;
    String Str_cpp;
    String Str_UserName;
    String Str_email;
    String Str_password;
    String Str_fullname;
    String Str_mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_razorpay_debitcredit_view);
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
            initializeData();

            getIntentData();

            // ACTIONBAR
            setUpToolbar();

            initializeRazorPay();

            findViewById();

            onTextChange();

            // month spinner
            ArrayList<RazorPayCardList> spinnerList = getMonthArray();
            txtMonth.setText(selectedMonthItem);
            llMonthSpinner.setOnClickListener(view -> {
                Log.e(TAG, "");
                openDialog(spinnerList, "month", selectedMonthItem, getResources().getString(R.string.select_month), selectedMonthPos);
            });

            // year spinner
            ArrayList<RazorPayCardList> spinnerYearList = getYearArray();
            txtYear.setText(selectedYearItem);
            llYearSpinner.setOnClickListener(view -> openDialog(spinnerYearList, "year", selectedYearItem, getResources().getString(R.string.select_year), selectedYearPos));

            addItemsOnMonthSpinner();
            addItemsOnYearSpinner();

            btnSubmit.setOnClickListener(view -> {
                try {
                    debitCreditCard();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }

            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void initializeData() {
        try {
            activity = this;
            mPref = new PreferencesUtils(this);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void findViewById() {
        try {
            //llRazorPay = findViewById(R.id.llRazorPay);
            img_info = findViewById(R.id.img_info);
            btnSubmit = findViewById(R.id.btnSubmit);
            btnSubmit.setText(String.format("%s - %s %s", getResources().getString(R.string.btn_pay), getResources().getString(R.string.rs1), String.format(Locale.getDefault(), "%.2f", eventSessionData.getNetamount())));

            edtCardName = findViewById(R.id.edtCardName);
            edtCardNo = findViewById(R.id.edtCardNo);
            edtCardCvv = findViewById(R.id.edtCardCvv);
            Drawable wrappedDrawable = changeDrawableColor(this, R.drawable.ic_info, getResources().getColor(R.color.colorPrimaryControlNormal));
          //  edtCardCvv.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(RazorPayCreditDebitCardActivity.this,R.drawable.ic_info), null);
            tiCardNo = findViewById(R.id.tiCardNo);
            tiCardName = findViewById(R.id.tiCardName);
            tiCardCvv = findViewById(R.id.tiCardCvv);

            TextView txtAmt = findViewById(R.id.txtAmt);
            txtAmt.setText(String.format("%s %s", getResources().getString(R.string.rs1), String.format(Locale.getDefault(), "%.2f", eventSessionData.getNetamount())));
            txtMonth = findViewById(R.id.txtMonth);
            llMonthSpinner = findViewById(R.id.monthSpinner1);
            txtYear = findViewById(R.id.txtYear);
            llYearSpinner = findViewById(R.id.yearSpinner1);
            sv = findViewById(R.id.sv);
           // LinearLayout llPay = findViewById(R.id.llPay);
           // EditText cardNumberEditText = findViewById(R.id.cardNumberEditText);
            //llPay.setEnabled(false);
            //  btnSubmit.setEnabled(false);


        }catch (Exception e){
            e.printStackTrace();
        }

    }


    public void initializeRazorPay() {
        try {
            razorpay = new Razorpay(this, getResources().getString(R.string.razor_pay_key));
            setUpWebView();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //Setup WebView
    public void setUpWebView() {
        try {
            // private PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
            WebView webView = findViewById(R.id.webView);
            webView.setVisibility(View.GONE);
            razorpay.setWebView(webView);
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
                tvTitle.setText(R.string.title_activity_credit_debit_card);
                Utility.convertToLowerCase(tvTitle);

                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
                    findViewById(R.id.toolbarBottomDivider).setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
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

    private ArrayList<RazorPayCardList> getMonthArray() {
        currentMonth = Calendar.getInstance().get(Calendar.MONTH);
        ArrayList<RazorPayCardList> categories = new ArrayList<>();
        try {
            DateFormatSymbols symbols = new DateFormatSymbols();
            String[] monthNames = symbols.getMonths();
            Log.e(TAG, "" + Arrays.toString(monthNames));
            for (int i = 1; i <= 12; i++) {
                RazorPayCardList cardList = new RazorPayCardList();

                if (i < 10) {
                    cardList.no = "0" + i;
                    cardList.monthName = monthNames[i - 1];
                } else {
                    cardList.no = "" + i;
                    cardList.monthName = monthNames[i - 1];
                }
                cardList.monthNo = "" + i;
                categories.add(cardList);
            }
            selectedMonthItem = categories.get(0).no;
            selectedMonthItemNo = categories.get(0).monthNo;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return categories;
    }

    private ArrayList<RazorPayCardList> getYearArray() {
        currentYear = Calendar.getInstance().get(Calendar.YEAR)% 100;
        ArrayList<RazorPayCardList> categories = new ArrayList<>();

        try {
            int lastTwoDigits = Calendar.getInstance().get(Calendar.YEAR) % 100;
            Log.e(TAG, "year: " + lastTwoDigits);

            for (int i = lastTwoDigits; i <= 93; i++) {
                RazorPayCardList cardList = new RazorPayCardList();
                cardList.no = "" + i;
                cardList.currentYear = "" + currentYear;
                categories.add(cardList);
            }

            selectedYearItem = categories.get(0).no;
            selectedYear = categories.get(0).currentYear;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return categories;
    }

    //add items into monthSpinner dynamically not used
    public void addItemsOnMonthSpinner() {
        try {
            currentMonth = Calendar.getInstance().get(Calendar.MONTH);
            monthSpinner = findViewById(R.id.monthSpinner);
            DateFormatSymbols symbols = new DateFormatSymbols();
            String[] monthNames = symbols.getMonths();
            Log.e(TAG, "" + Arrays.toString(monthNames));

            ArrayList<RazorPayCardList> categories = new ArrayList<>();

            for (int i = 1; i <= 12; i++) {
                RazorPayCardList cardList = new RazorPayCardList();

                if (i < 10) {
                    cardList.no = "0" + i;
                    cardList.monthName = monthNames[i - 1];
                } else {
                    cardList.no = "" + i;
                    cardList.monthName = monthNames[i - 1];
                }
                categories.add(cardList);
            }

            RazorPayCardSpinner razorPayCardSpinner = new RazorPayCardSpinner(this, categories, "month");
            razorPayCardSpinner.setSelectedItem(this);
            monthSpinner.setAdapter(razorPayCardSpinner);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //add items into monthSpinner dynamically not used
    public void addItemsOnYearSpinner() {
        try {
            yearSpinner = findViewById(R.id.yearSpinner);
            ArrayList<RazorPayCardList> categories = new ArrayList<>();
            currentYear = Calendar.getInstance().get(Calendar.YEAR)% 100;
            int lastTwoDigitsCurrentYear = Calendar.getInstance().get(Calendar.YEAR) % 100;
            Log.e(TAG, "year: " + lastTwoDigitsCurrentYear);
            for (int i = lastTwoDigitsCurrentYear; i <= 93; i++) {
                RazorPayCardList cardList = new RazorPayCardList();
                cardList.no = "" + i;
                cardList.currentYear = "" + currentYear;
                categories.add(cardList);
            }

            // ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
            // dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // dataAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
            RazorPayCardSpinner razorPayCardSpinner = new RazorPayCardSpinner(this, categories, "year");
            razorPayCardSpinner.setSelectedItem(this);
            yearSpinner.setAdapter(razorPayCardSpinner);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @SuppressLint("SetTextI18n")
    private void openDialog(ArrayList<RazorPayCardList> arrayList, String type, String selectItem, String title, int position) {
        try {
            dialog = new Dialog(this);
            //Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            //dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.setContentView(R.layout.razor_pay_dialog);
            dialog.setCanceledOnTouchOutside(true);
            dialog.setCancelable(true);
            dialog.show();

            TextView tvTitle = dialog.findViewById(R.id.tvTitle);
            tvTitle.setText(title);
            RecyclerView rvTest = dialog.findViewById(R.id.rv);
            rvTest.setHasFixedSize(true);
            rvTest.setLayoutManager(new LinearLayoutManager(this));

            RazorPayCardSpinner1 rvAdapter = new RazorPayCardSpinner1(arrayList, type, selectItem);
            rvAdapter.setSelectedItem(this);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            rvTest.setLayoutManager(layoutManager);
            rvTest.setAdapter(rvAdapter);

            //Scroll item 2 to 20 pixels from the top
            layoutManager.scrollToPositionWithOffset(position, 0);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onBackPressed() {
        Utility.hideKeyboard(this);
        super.onBackPressed();
    }


    private void openRazorPayWebView() {
        try {
            Intent intent = new Intent(RazorPayCreditDebitCardActivity.this, RazorPayCustomActivity.class);
            intent.putExtra(Constant.ScreenExtras.PAYMENT_METHOD, Constant.RazorPayPaymentMethod.CARD);
            intent.putExtra(Constant.ScreenExtras.EVENT_SESSION_ID, eventSessionId);
            intent.putExtra(Constant.ScreenExtras.TICKET_SESSION_DATA, eventSessionData);
            intent.putExtra(Constant.CARD.NAME, Objects.requireNonNull(edtCardName.getText()).toString());
            intent.putExtra(Constant.CARD.NUMBER, Objects.requireNonNull(edtCardNo.getText()).toString());
            intent.putExtra(Constant.CARD.CVV, Objects.requireNonNull(edtCardCvv.getText()).toString());
            intent.putExtra(Constant.CARD.EXPIRY_MONTH, txtMonth.getText().toString());
            intent.putExtra(Constant.CARD.EXPIRY_YEAR, txtYear.getText().toString());
            intent.putExtra("planid",planid);
            intent.putExtra("planname",planname);

            //intent.putExtra(Constant.CARD.EXPIRY_MONTH, monthSpinner.getSelectedItem().toString());
            // intent.putExtra(Constant.CARD.EXPIRY_YEAR, yearSpinner.getSelectedItem().toString());

            startActivity(intent);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @SuppressLint("ClickableViewAccessibility")
    public void onTextChange() {

        try {

            focusOnView();

            //edtCardNo.addTextChangedListener(new CreditCardNumberFormattingTextWatcher());
           /* cardNumberEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    try {
                        String source = s.toString();
                        if (!lastSource.equals(source)) {
                            source = source.replace(WHITE_SPACE, EMPTY_STRING);
                            StringBuilder stringBuilder = new StringBuilder();
                            for (int i = 0; i < source.length(); i++) {
                                if (i > 0 && i % 4 == 0) {
                                    stringBuilder.append(WHITE_SPACE);
                                }
                                stringBuilder.append(source.charAt(i));
                            }
                            lastSource = stringBuilder.toString();
                            s.replace(0, s.length(), lastSource);
                        }

                       *//* if (lock || s.length() > 16) {
                            return;
                        }
                        lock = true;
                        for (int i = 4; i < s.length(); i += 5) {
                            if (s.toString().charAt(i) != ' ') {
                                s.insert(i, " ");
                            }
                        }
                        lock = false;*//*
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });*/

            tt = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    try {
                        String cardNetwork = razorpay.getCardNetwork(charSequence.toString());
                        Log.e(TAG, "cardNetwork: " + cardNetwork);
                        if (!TextUtils.isEmpty(cardNetwork)) {
                            int length = razorpay.getCardNetworkLength(cardNetwork);
                            Log.e(TAG, "length: " + length);
                            if (length > 0) {
                                edtCardNo.setFilters(new InputFilter[]{new InputFilter.LengthFilter(length)});
                            }
                        }

                        if (Constant.CardName.VISA.equals(cardNetwork)) {
                            edtCardNo.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_visa, 0);
                        } else if (Constant.CardName.MASTER_CARD.equals(cardNetwork)) {
                            edtCardNo.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_mastercard, 0);
                        } else if (Constant.CardName.GOOGLE_PAY.equals(cardNetwork)) {
                            edtCardNo.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_gpay, 0);
                        } else if (Constant.CardName.AMEX.equals(cardNetwork)) {
                            edtCardNo.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_amex, 0);
                        } else if (Constant.CardName.RUPAY.equals(cardNetwork)) {
                            edtCardNo.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_rupay, 0);
                        } else if (Constant.CardName.MAESTRO.equals(cardNetwork)) {
                            edtCardNo.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_maestro, 0);
                        } else if (Constant.CardName.DINER.equals(cardNetwork)) {
                            edtCardNo.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_diner, 0);
                        } else if (Constant.CardName.UNKNOWN.equals(cardNetwork)) {
                            edtCardNo.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        }

                        isValidCardNumber = razorpay.isValidCardNumber(charSequence.toString().replace(WHITE_SPACE, ""));
                        Log.e(TAG, "cardNetwork = " + cardNetwork + "  ,  " + isValidCardNumber);

                        if (TextUtils.isEmpty(charSequence.toString())) {
                            tiCardNo.setError("Please enter card no");
                        }/* else if (!isValidCardNumber) {
                            tiCardNo.setError("Invalid card number");
                        } */ else {
                            tiCardNo.setError("");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {
                    try {
                        String source = s.toString();
                        if (!lastSource.equals(source)) {
                            source = source.replace(WHITE_SPACE, EMPTY_STRING);
                            StringBuilder stringBuilder = new StringBuilder();
                            for (int i = 0; i < source.length(); i++) {
                                if (i > 0 && i % 4 == 0) {
                                    stringBuilder.append(WHITE_SPACE);
                                }
                                stringBuilder.append(source.charAt(i));
                            }
                            lastSource = stringBuilder.toString();
                            // Log.e(TAG, "after: " + lastSource + " , " + s.length());

                            // Unregister self before update
                            // edtCardNo.removeTextChangedListener(this);

                            // The trick to update text smoothly.
                            // s.replace(0, s.length(), lastSource);

                            // Re-register self after update
                            // edtCardNo.addTextChangedListener(tt);


                            //s.replace(0, s.length(), lastSource);
                            //edtCardNo.setText(lastSource);
                            //b,edtCardNo.setSelection(lastSource.length()); //moves the pointer to end

                            edtCardNo.setOnKeyListener((view, keyCode, keyEvent) -> {

                                if (keyCode == KeyEvent.KEYCODE_DEL) {
                                    Log.e(TAG, "remove");
                                }

                                return false;
                            });


                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };

            edtCardNo.addTextChangedListener(tt);

            edtCardName.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (TextUtils.isEmpty(charSequence.toString().trim())) {
                        tiCardName.setError("Please enter card name");
                    } /*else if (!isValidCardNumber) {
                        tiCardName.setError("Please enter valid card name");
                    }*/ else {
                        tiCardName.setError("");
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });


            edtCardCvv.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (TextUtils.isEmpty(charSequence.toString().trim())) {
                        tiCardCvv.setError("Please enter card cvv");
                    } else {
                        tiCardCvv.setError("");
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });


            edtCardCvv.setOnFocusChangeListener((v, hasFocus) -> {
                if (!hasFocus) {
                    Log.e(TAG, "edtCardCvv focus false");
                } else {
                    if (!TextUtils.isEmpty(Objects.requireNonNull(edtCardNo.getText()).toString().trim())) {
                        if (!isValidCardNumber) {
                            tiCardNo.setError("Invalid card number");
                        } else {
                            tiCardNo.setError("");
                        }
                    } else {
                        tiCardNo.setError("Please enter card no");
                    }
                }
            });

            edtCardName.setOnFocusChangeListener((v, hasFocus) -> {
                if (!hasFocus) {
                    Log.e(TAG, "edtCardName focus false");
                } else {
                    if (!TextUtils.isEmpty(Objects.requireNonNull(edtCardNo.getText()).toString().trim())) {
                        if (!isValidCardNumber) {
                            tiCardNo.setError("Invalid card number");
                        } else {
                            tiCardNo.setError("");
                        }
                    } else {
                        tiCardNo.setError("Please enter card no");
                    }
                }
            });


           /* edtCardCvv.setOnTouchListener((v, event) -> {
               // final int DRAWABLE_LEFT = 0;
                //final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
               // final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getX() >= (edtCardCvv.getRight() - edtCardCvv.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                        //MessageUtils.showAlert(this,"Cvv",getResources().getString(R.string.cvv_des));

                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                        //alertDialog.setTitle("Cvv");
                        //alertDialog.setMessage(getResources().getString(R.string.cvv_des));
                        alertDialog.setCancelable(true);
                        alertDialog.setView(R.layout.cvv_des_layout);
                        // alertDialog.setPositiveButton(android.R.string.ok, (dialogInterface, i) -> dialogInterface.dismiss());
                        AlertDialog dialog = alertDialog.create();
                        if (!isFinishing()) dialog.show();


                        return true;
                    }
                }
                return false;
            });*/
            img_info.setOnClickListener(v -> {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                //alertDialog.setTitle("Cvv");
                //alertDialog.setMessage(getResources().getString(R.string.cvv_des));
                alertDialog.setCancelable(true);
                alertDialog.setView(R.layout.cvv_des_layout);
                // alertDialog.setPositiveButton(android.R.string.ok, (dialogInterface, i) -> dialogInterface.dismiss());
                AlertDialog dialog = alertDialog.create();
                if (!isFinishing()) dialog.show();
            });


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //Debit and Credit Card
    private void debitCreditCard() {

        try {
            String cardName = Objects.requireNonNull(edtCardName.getText()).toString().trim();
            String cardNo = Objects.requireNonNull(edtCardNo.getText()).toString().trim();
            String cardCvv = Objects.requireNonNull(edtCardCvv.getText()).toString().trim();
            //String expiry_month = monthSpinner.getSelectedItem().toString();
            //String expiry_year = yearSpinner.getSelectedItem().toString();
            String expiry_month = txtMonth.getText().toString();
            String expiry_year = txtYear.getText().toString();
            Log.e(TAG, "selectedYear : " + selectedYear);//2020
            Log.e(TAG, "currentYear : " + currentYear);//2019
            Log.e(TAG, "selectedYearItem : " + selectedYearItem);//2019

            if (TextUtils.isEmpty(cardNo)) {
                edtCardNo.requestFocus();
                tiCardNo.setError("Please enter card no");
                return;
            } else if (!isValidCardNumber) {
                tiCardNo.setError("Invalid card number");
                return;
            } else if (currentYear == Integer.parseInt(selectedYearItem)) {
                if (currentMonth >= Integer.parseInt(selectedMonthItem)) {
                    Toast.makeText(activity, "Card Expire", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            try {
                JSONObject data = new JSONObject();
                data.put("amount", (int) (eventSessionData.getNetamount() * 100));
                data.put("email", Str_email);
                data.put("contact", Str_mobile);
                data.put("method", Constant.RazorPayPaymentMethod.CARD);
                data.put("currency", "INR");
                data.put("card[name]", cardName);
                data.put("card[number]", cardNo);
                data.put("card[expiry_month]", expiry_month);
                data.put("card[expiry_year]", expiry_year);
                data.put("card[cvv]", cardCvv);

               // razorpay.validateFields(data, new Razorpay.ValidationListener() {  //remove 04/04/2023
                razorpay.validateFields(data, new ValidationListener() { //add 04/04/2023
                    @Override
                    public void onValidationSuccess() {
                        Log.e(TAG, "onValidationSuccess selectedMonthItemNo : " + selectedMonthItemNo);
                        Log.e(TAG, "selectedMonthItem : " + selectedMonthItem);
                        Log.e(TAG, "selectedYearItem : " + selectedYearItem);
                        Log.e(TAG, "currentMonth : " + currentMonth);
                        Log.e(TAG, "selectedYear : " + selectedYear);//2020
                        Log.e(TAG, "currentYear : " + currentYear);//2019
                        try {
                            if (TextUtils.isEmpty(cardName)) {
                                tiCardName.setError("Please enter card name");
                                edtCardName.requestFocus();
                                return;
                            }else if (TextUtils.isEmpty(cardCvv)) {
                                tiCardCvv.setError("Please enter card cvv");
                                edtCardCvv.requestFocus();
                                return;
                            }else if (currentYear == Integer.parseInt(selectedYearItem)) {
                                if (currentMonth >= Integer.parseInt(selectedMonthItem)) {
                                    Toast.makeText(activity, "Card Expire", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }
                            openRazorPayWebView();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onValidationError(Map<String, String> error) {
                        // error.get("field") error.get("description")
                        Log.e(TAG, "error: " + error);

                        switch (error.get("field")) {

                            case "card_number":
                                Toast.makeText(activity, error.get("description"), Toast.LENGTH_SHORT).show();
                                break;
                            case "name":
                                edtCardName.requestFocus();
                                tiCardName.setError("Please enter valid card name");
                                break;
                            case "card[cvv]":
                                Toast.makeText(activity, error.get("description"), Toast.LENGTH_SHORT).show();
                                break;
                            case "card[expiry_month]":
                                Toast.makeText(activity, "Please select expiry month", Toast.LENGTH_SHORT).show();
                                break;
                            case "card[expiry_year]":
                                Toast.makeText(activity, "Please select expiry year", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                });


            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void focusOnView() {
        sv.post(() -> sv.scrollTo(0, edtCardName.getBottom()));
    }

    /*private void clearData() {
        try {
            edtCardCvv.setText("");
            edtCardNo.setText("");
            edtCardName.setText("");
            monthSpinner.setSelection(0);
            yearSpinner.setSelection(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    @Override
    public void getSelectedValue(int pos, String item, String type, String selectedMonthNo, String mSelectedYear) {
        try {
            Log.e(TAG, "pos:" + pos);
            dialog.dismiss();
            if ("month".equals(type)) {
                monthSpinner.setSelection(pos);
                txtMonth.setText(item);
                selectedMonthItem = item;
                selectedMonthItemNo = selectedMonthNo;
                selectedMonthPos = pos;
            } else {
                yearSpinner.setSelection(pos);
                txtYear.setText(item);
                selectedYearItem = item;
                selectedYear = mSelectedYear;
                selectedYearPos = pos;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
