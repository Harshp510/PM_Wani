/*
 * Copyright (c) 2017. Suthar Rohit
 * Developed by Suthar Rohit for NicheTech Computer Solutions Pvt. Ltd. use only.
 * <a href="http://RohitSuthar.com/">Suthar Rohit</a>
 *
 * @author Suthar Rohit
 */

package com.zenwsmp.pmwani.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatDialog;

import com.zenwsmp.pmwani.R;
import com.zenwsmp.pmwani.common.CommonClass;
import com.zenwsmp.pmwani.common.TypefaceUtils;
import com.zenwsmp.pmwani.common.Validate;

import java.util.Objects;



/**
 * Matrubharti(com.nichetech.matrubharti) <br />
 * Developed by <b><a href="http://www.RohitSuthar.com/">Suthar Rohit</a></b>  <br />
 * on 23-Jun-2017.
 *
 * @author Suthar Rohit
 */
public class PaymentSuccessDialog extends AppCompatDialog {

    private final String TAG = PaymentSuccessDialog.class.getSimpleName();
    private CommonClass CC;

    private String transactionId;

    private String totalAmount;
    @StringRes
    private int totalAmountResId;
    private TextView tvTransId;
    private TextView tvTotalAmount;

    private OnOkClickListener clickListener;

    public PaymentSuccessDialog(Context context) {
        super(context);
        CC = new CommonClass(context);
    }

    public PaymentSuccessDialog(Context context, int theme) {
        super(context, theme);
        CC = new CommonClass(context);
    }

    protected PaymentSuccessDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        CC = new CommonClass(context);
    }

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {


            //getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.layout_dialog_payment_success);

            setCancelable(false);

            // TYPEFACE
            //Typeface tfLight = TypefaceUtils.HelveticaLight(getContext());
            Typeface tfRegular = TypefaceUtils.HelveticaRegular(getContext());
            Typeface tfMedium = TypefaceUtils.HelveticaMedium(getContext());

            TextView tvLabel1 = findViewById(R.id.tvLabel1);
            Objects.requireNonNull(tvLabel1).setTypeface(tfRegular);
            TextView tvLabel2 = findViewById(R.id.tvLabel2);
            Objects.requireNonNull(tvLabel2).setTypeface(tfMedium);

            TextView tvTransIdLabel = findViewById(R.id.tvTransIdLabel);
            Objects.requireNonNull(tvTransIdLabel).setTypeface(tfMedium);
            tvTransId = findViewById(R.id.tvTransId);
            Objects.requireNonNull(tvTransId).setTypeface(tfMedium);
            TextView tvTotalAmountLabel = findViewById(R.id.tvTotalAmountLabel);
            Objects.requireNonNull(tvTotalAmountLabel).setTypeface(tfMedium);
            tvTotalAmount = findViewById(R.id.tvTotalAmount);
            Objects.requireNonNull(tvTotalAmount).setTypeface(tfMedium);

            Button btnSubmit = findViewById(R.id.btnSubmit);
            Objects.requireNonNull(btnSubmit).setTypeface(tfMedium);
            btnSubmit.setOnClickListener(v -> {
                if (clickListener != null) clickListener.onOkClick();
            });

            if (Validate.isNotNull(transactionId))
                tvTransId.setText(transactionId);

            if (totalAmountResId > 0)
                tvTotalAmount.setText(totalAmountResId);
            else if (Validate.isNotNull(totalAmount))
                tvTotalAmount.setText(totalAmount);

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void setTransactionId(@NonNull String transactionId) {
        this.transactionId = transactionId;
        if (tvTransId != null) {
            tvTransId.setText(transactionId);
        }
    }

    public void setAmount(@NonNull String totalAmount) {
        this.totalAmount = totalAmount;
        if (tvTotalAmount != null) {
            tvTotalAmount.setText(totalAmount);
        }
    }

    public void setAmount(@StringRes int totalAmountResId) {
        this.totalAmountResId = totalAmountResId;
        if (tvTotalAmount != null) {
            tvTotalAmount.setText(totalAmount);
        }
    }

    // LOAD MORE LISTENER
    public void setOnOkClickListener(@NonNull OnOkClickListener listener) {
        this.clickListener = listener;
    }

    public interface OnOkClickListener {
        void onOkClick();
    }

}
