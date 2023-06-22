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
import android.content.pm.PackageInfo;

/**
 * Matrubharti(com.nichetech.common) <br />
 * Developed by <b><a href="http://RohitSuthar.com/">Suthar Rohit</a></b>  <br />
 * on 15/12/15.
 *
 * @author Suthar Rohit
 */
public class CommonClass {

    private static final String TAG = CommonClass.class.getSimpleName();
    private Context context;
    private Activity activity;

    public CommonClass(Context context) {
        this.context = context;
        this.activity = (Activity) context;
    }

    public CommonClass(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }


    public Context getContext() {
        return context;
    }

    public Activity getActivity() {
        return activity;
    }

    public boolean isOnline() {
        return Utility.isOnline(context);
    }


    public String getAppVersionName() {
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pInfo.versionName;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    // ALERT
    public void showAlert(int messageId) {
        MessageUtils.showAlert(activity, messageId);
    }

    public void showAlert(String title, String message) {
        MessageUtils.showAlert(activity, title, message);
    }

    public void showAlert(String message) {
        MessageUtils.showAlert(activity, message);
    }

    public void showAlert(int messageId, MessageUtils.OnOkClickListener listener) {
        MessageUtils.showAlert(activity, messageId, listener);
    }

    public void showAlert(String message, MessageUtils.OnOkClickListener listener) {
        MessageUtils.showAlert(activity, message, listener);
    }

    public void showAlert(int message, int Title, int postive_Button, int negative_Button, MessageUtils.OnOkClickListener listener) {
        MessageUtils.showAlert(activity, message, Title, postive_Button, negative_Button, listener);
    }

    public void showAlert(int message, int postive_Button, int negative_Button, MessageUtils.OnOkClickListener listener) {
        MessageUtils.showAlert(activity, message, postive_Button, negative_Button, listener);
    }
    public void showAlert(String message, int postive_Button, int negative_Button, MessageUtils.OnOkClickListener listener) {
        MessageUtils.showAlert(activity, message, postive_Button, negative_Button, listener);
    }
    public void showAlert(String Title,String message, int postive_Button,  MessageUtils.OnOkClickListener listener) {
        MessageUtils.showAlert(activity, Title,message , postive_Button, listener);
    }

    public void showAlert(String message, int postive_Button,  MessageUtils.OnOkClickListener listener) {
        MessageUtils.showAlert(activity, message , postive_Button, listener);
    }

    // TOAST
    public void showToast(String message) {
        MessageUtils.showToast(context, message);
    }

    public void showToast(int resId) {
        MessageUtils.showToast(context, resId);
    }

    public void showComingSoon(String moduleName) {
        MessageUtils.showToast(context, moduleName + " - Coming Soon…");
    }
}