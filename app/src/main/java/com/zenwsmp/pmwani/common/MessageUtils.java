/*
 * Copyright (c) 2017. Suthar Rohit
 * Developed by Suthar Rohit for NicheTech Computer Solutions Pvt. Ltd. use only.
 * <a href="http://RohitSuthar.com/">Suthar Rohit</a>
 *
 * @author Suthar Rohit
 */

package com.zenwsmp.pmwani.common;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;

import com.google.android.material.snackbar.Snackbar;

/**
 * TicketNinja(in.ticketninja) <br />
 * Developed by <b><a href="http://RohitSuthar.com/">Suthar Rohit</a></b>  <br />
 * on 15/12/15.
 *
 * @author Suthar Rohit
 */
public class MessageUtils {

    /* -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=- SHOW TOAST -=-=-=-=-=-=-=-=-=-=-=-=-=- */
    public static void showToast(@NonNull Context context, @NonNull String message) {
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static void showToast(@NonNull Context context, @StringRes int resId) {
        Toast toast = Toast.makeText(context, resId, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    /* -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=- SHOW SNACK-BAR -=-=-=-=-=-=-=-=-=-=-=-=-=- */
    public static void showSnackbar(@NonNull View view, @NonNull String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
    }

    public static void showSnackbar(@NonNull View view, @StringRes int resId) {
        Snackbar.make(view, resId, Snackbar.LENGTH_LONG).show();
    }

    /* -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=- SHOW ALERT -=-=-=-=-=-=-=-=-=-=-=-=-=- */

    public static void showAlert(@NonNull Activity context, @NonNull String title, @NonNull String message) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton(android.R.string.ok, (dialogInterface, i) -> dialogInterface.dismiss());
        AlertDialog dialog = alertDialog.create();
        if (!context.isFinishing()) dialog.show();

    }

    public static void showAlert(@NonNull Activity context, @NonNull String message) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setMessage(message);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton(android.R.string.ok, (dialogInterface, i) -> dialogInterface.dismiss());
        AlertDialog dialog = alertDialog.create();
        if (!context.isFinishing()) dialog.show();

    }

    public static void showAlert(@NonNull Activity context, @StringRes int resId) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setMessage(resId);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton(android.R.string.ok, (dialogInterface, i) -> dialogInterface.dismiss());
        AlertDialog dialog = alertDialog.create();
        if (!context.isFinishing()) dialog.show();

    }

    public static void showAlert(@NonNull Activity context, @NonNull String message,
                                 final OnOkClickListener listener) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setMessage(message);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton(android.R.string.ok, (dialogInterface, i) -> {
            dialogInterface.dismiss();
            if (listener != null) listener.onOkClick();
        });
        AlertDialog dialog = alertDialog.create();
        if (!context.isFinishing()) dialog.show();
    }

    public static void showAlert(@NonNull Activity context, @StringRes int resId,
                                 final OnOkClickListener listener) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setMessage(resId);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton(android.R.string.ok, (dialogInterface, i) -> {
            dialogInterface.dismiss();
            if (listener != null) listener.onOkClick();
        });
        AlertDialog dialog = alertDialog.create();
        if (!context.isFinishing()) dialog.show();

    }

    public static void showAlert(@NonNull Activity context, @StringRes int resId_msg, @StringRes int resId_Title,
                                 @StringRes int resId_posBtn, @StringRes int resId_negBtn, @Nullable final OnOkClickListener listener) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setMessage(resId_msg);
        // alertDialog.setTitle(resId_Title);
        alertDialog.setCancelable(false);
        alertDialog.setNegativeButton(resId_negBtn, (dialog, which) -> dialog.dismiss());
        alertDialog.setPositiveButton(resId_posBtn, (dialogInterface, i) -> {
            dialogInterface.dismiss();
            if (listener != null) listener.onOkClick();
        });
        AlertDialog dialog = alertDialog.create();
        if (!context.isFinishing()) dialog.show();

    }

    public static void showAlert(@NonNull Activity context, @StringRes int resId_msg,
                                 @StringRes int resId_posBtn, @StringRes int resId_negBtn, @Nullable final OnOkClickListener listener) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setMessage(resId_msg);
        // alertDialog.setTitle(resId_Title);
        alertDialog.setCancelable(false);
        alertDialog.setNegativeButton(resId_negBtn, (dialog, which) -> dialog.dismiss());
        alertDialog.setPositiveButton(resId_posBtn, (dialogInterface, i) -> {
            dialogInterface.dismiss();
            if (listener != null) listener.onOkClick();
        });
        AlertDialog dialog = alertDialog.create();
        if (!context.isFinishing()) dialog.show();
    }

    public static void showAlert(@NonNull Activity context, String resId_msg,
                                 @StringRes int resId_posBtn, @StringRes int resId_negBtn, @Nullable final OnOkClickListener listener) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setMessage(resId_msg);
        // alertDialog.setTitle(resId_Title);
        alertDialog.setCancelable(false);
        alertDialog.setNegativeButton(resId_negBtn, (dialog, which) -> dialog.dismiss());
        alertDialog.setPositiveButton(resId_posBtn, (dialogInterface, i) -> {
            dialogInterface.dismiss();
            if (listener != null) listener.onOkClick();
        });
        AlertDialog dialog = alertDialog.create();
        if (!context.isFinishing()) dialog.show();

    }

    public static void showAlert(@NonNull Activity context, @NonNull String title, @NonNull String message,int resId_posBtn,  @Nullable final OnOkClickListener listener) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton(resId_posBtn, (dialogInterface, i) -> {
            dialogInterface.dismiss();
            if (listener != null) listener.onOkClick();
        });
        AlertDialog dialog = alertDialog.create();
        if (!context.isFinishing()) dialog.show();

    }

    public static void showAlert(Activity activity, String message, int postive_button, OnOkClickListener listener) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
        alertDialog.setMessage(message);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton(postive_button, (dialogInterface, i) -> {
            dialogInterface.dismiss();
            if (listener != null) listener.onOkClick();
        });
        AlertDialog dialog = alertDialog.create();
        if (!activity.isFinishing()) dialog.show();
    }


    public interface OnOkClickListener {
        void onOkClick();
    }

    public interface OnCancelClickListener {
        void onCancelClick();
    }

}
