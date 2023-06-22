package com.zenwsmp.pmwani.common;

import android.text.Editable;
import android.text.TextWatcher;

public class CreditCardNumberFormattingTextWatcher implements TextWatcher {

    private boolean lock;

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (lock || s.length() > 16) {
            return;
        }
        lock = true;
        for (int i = 4; i < s.length(); i += 5) {
            if (s.toString().charAt(i) != ' ') {
                s.insert(i, " ");
            }
        }
        lock = false;
    }
}