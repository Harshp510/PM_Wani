/*
 * Copyright (c) 2017. Suthar Rohit
 * Developed by Suthar Rohit for NicheTech Computer Solutions Pvt. Ltd. use only.
 * <a href="http://RohitSuthar.com/">Suthar Rohit</a>
 *
 * @author Suthar Rohit
 */

package com.zenwsmp.pmwani.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialog;

import com.zenwsmp.pmwani.R;
import com.zenwsmp.pmwani.common.CommonClass;
import com.zenwsmp.pmwani.common.TypefaceUtils;
import com.zenwsmp.pmwani.common.Utility;
import com.zenwsmp.pmwani.common.Validate;

import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.TimeUnit;



/**
 * Matrubharti(com.nichetech.matrubharti) <br />
 * Developed by <b><a href="http://www.RohitSuthar.com/">Suthar Rohit</a></b>  <br />
 * on 23-Jun-2017.
 *
 * @author Suthar Rohit
 */
public class OTPVerificationDialog extends AppCompatDialog {

    private final String TAG = OTPVerificationDialog.class.getSimpleName();
    private final CommonClass CC;

    private EditText etOTP;
    private Button btnSubmit;
    private TextView tvResendOTPTime;
    private Button btnResend;

    private OnOTPVerificationListener clickListener;

    public OTPVerificationDialog(Context context) {
        super(context);
        CC = new CommonClass(context);
    }

    public OTPVerificationDialog(Context context, int theme) {
        super(context, theme);
        CC = new CommonClass(context);
    }

    protected OTPVerificationDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        CC = new CommonClass(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
//getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.layout_dialog_otp_verification);

            setCancelable(false);

            // TYPEFACE
            //Typeface tfLight = TypefaceUtils.HelveticaLight(getContext());
            Typeface tfRegular = TypefaceUtils.HelveticaRegular(getContext());
            Typeface tfMedium = TypefaceUtils.HelveticaMedium(getContext());

            TextView tvLabel1 = findViewById(R.id.tvLabel1);
            Objects.requireNonNull(tvLabel1).setTypeface(tfRegular);
            TextView tvLabel2 = findViewById(R.id.tvLabel2);
            Objects.requireNonNull(tvLabel2).setTypeface(tfMedium);

            tvResendOTPTime = findViewById(R.id.tvResendOTPTime);
            tvResendOTPTime.setTypeface(tfMedium);

            //ImageView ivOtpIcon = (ImageView) findViewById(R.id.ivOtpIcon);

            etOTP = findViewById(R.id.etOTP);
            Objects.requireNonNull(etOTP).setTypeface(tfMedium);

            btnResend = findViewById(R.id.btnResendOTP);
            Objects.requireNonNull(btnResend).setTypeface(tfMedium);
            btnResend.setOnClickListener(v -> {
                dismiss();
                if (clickListener != null) clickListener.onResendClick();
            });
            btnSubmit = findViewById(R.id.btnSubmit);
            Objects.requireNonNull(btnSubmit).setTypeface(tfMedium);
            btnSubmit.setOnClickListener(v -> {
                Utility.hideKeyboard(CC.getActivity(), etOTP);
                String otp = etOTP.getText().toString().trim();
                if (Validate.isNull(otp)) {
                    CC.showToast(R.string.otp_msg_enter_otp);
                } else if (otp.length() != 6) {
                    CC.showToast(R.string.otp_msg_enter_otp_valid);
                } else {
                    if (clickListener != null) clickListener.onSuccess(otp);
                }
            });
            Button btnClose = findViewById(R.id.btnClose);
            Objects.requireNonNull(btnClose).setTypeface(tfMedium);
            btnClose.setOnClickListener(v -> {
                dismiss();
            });

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    // LOAD MORE LISTENER
    public void setOnOTPVerificationListener(OnOTPVerificationListener listener) {
        this.clickListener = listener;
    }

    public interface OnOTPVerificationListener {
        void onSuccess(@NonNull String otp);

        void onFail();

        void onCancel();

        void onResendClick();
    }

    public void reset() {
        if (etOTP != null) etOTP.setText("");
    }

    public void setOTP(String otp) {
        if (etOTP != null) etOTP.setText(otp);
    }

    public void performSubmitButton() {
        if (btnSubmit != null) btnSubmit.performClick();
    }

    @Override
    public void show() {
        super.show();
        if (timer != null) timer.start();
        if (etOTP != null) etOTP.requestFocus();
    }

    @Override
    public void dismiss() {
        Utility.hideKeyboard(CC.getActivity(), etOTP);
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (clickListener != null) clickListener.onCancel();
        super.dismiss();
    }

    @Override
    public void cancel() {
        Utility.hideKeyboard(CC.getActivity(), etOTP);
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (clickListener != null) clickListener.onCancel();
        super.cancel();
    }

    private MyCountDownTimer timer = new MyCountDownTimer(TimeUnit.SECONDS.toMillis(30), 1000) {

        private static final String FORMAT = "Resend OTP in %02d:%02d";

        public void onTick(long millisUntilFinished) {
            long minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished));
            long seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)
                    - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished));

            tvResendOTPTime.setText(String.format(Locale.getDefault(), FORMAT, minutes, seconds + 1));
            tvResendOTPTime.setVisibility(View.VISIBLE);
            btnResend.setVisibility(View.GONE);
        }

        public void onFinish() {
            tvResendOTPTime.setVisibility(View.GONE);
            btnResend.setVisibility(View.VISIBLE);
        }

    };

}
