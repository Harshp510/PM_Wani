/*
 * Copyright (c) 2017. Suthar Rohit
 * Developed by Suthar Rohit for NicheTech Computer Solutions Pvt. Ltd. use only.
 * <a href="http://RohitSuthar.com/">Suthar Rohit</a>
 *
 * @author Suthar Rohit
 */

package com.zenwsmp.pmwani.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.jetbrains.annotations.Nullable;

/**
 * TicketNinja(in.ticketninja) <br />
 * Developed by <b><a href="http://RohitSuthar.com/">Suthar Rohit</a></b>  <br />
 * on 15/12/15.
 *
 * @author Suthar Rohit
 */
public class PreferencesUtils {

    /* -=-=-=-=-=-=-=-=-=-=-=- HANDLE NOTIFICATION -=-=-=-=-=-=-=-=-=-=-=- */

    private static final String PUSH_KEY = "push_key";
    private static final String PUSH_TOPIC_KEY = "push_topic_key";
    private static final String APP_VERSION = "app_version";
    private static final String IS_FORCEFULLY = "is_forcefully";
    private static final String APP_MESSAGE = "app_message";
    private final String CITY_ID = "city_id";
    private final String CITY_NAME = "city_name";
    private final String USER_EMAIL = "user_email_id";
    private final String USER_PHONE= "user_phone";
    private final String USER_COUNTRY_CODE= "user_country_code";
    private final String API_TYPE= "api_type";
    private final String USER_CODE_TYPE= "type";
    private final String USER_TICKET_TYPE= "ticket_type";
    private static final String PREF_LOGIN_REQUIRED = "pref_login_required";

    public SharedPreferences pref;


    public PreferencesUtils(Context context) {
        pref = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static String getAPP_VERSION(Context context) {
        SharedPreferences savedSession = PreferenceManager.getDefaultSharedPreferences(context);
        return savedSession.getString(APP_VERSION, null);
    }

    public static boolean isIS_FORCEFULLY(Context context) {
        SharedPreferences savedSession = PreferenceManager.getDefaultSharedPreferences(context);
        return savedSession.getBoolean(IS_FORCEFULLY, false);
    }

    public static void setVersionCode(Context context, String APP_VERSIO) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putString(APP_VERSION, APP_VERSIO);
        editor.apply();
    }

    public static void setIS_FORCEFULLY(Context context, boolean is_forcefully) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putBoolean(IS_FORCEFULLY, is_forcefully);
        editor.apply();
    }

    public static void setVersionMessage(Context context, String msg) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putString(APP_MESSAGE, msg);
        editor.apply();
    }

    public static String getAppMessage(Context context) {
        SharedPreferences savedSession = PreferenceManager.getDefaultSharedPreferences(context);
        return savedSession.getString(APP_MESSAGE, null);
    }

    public static void setFCMPushKey(Context context, String push_key) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putString(PUSH_KEY, push_key);
        editor.apply();
    }

    public static String getFCMPushKey(Context context) {
        SharedPreferences savedSession = PreferenceManager.getDefaultSharedPreferences(context);
        return savedSession.getString(PUSH_KEY, null);
    }

    public static void setFCMTopicRegister(Context context, boolean topicRegister) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putBoolean(PUSH_TOPIC_KEY, topicRegister);
        editor.apply();
    }

    public static boolean isFCMTopicRegister(Context context) {
        SharedPreferences savedSession = PreferenceManager.getDefaultSharedPreferences(context);
        return savedSession.getBoolean(PUSH_TOPIC_KEY, false);
    }


    /* -=-=-=-=-=-=-=-=-=-=-=- RATE US -=-=-=-=-=-=-=-=-=-=-=- */
    private static final String RateUs = "RateUs";

    static void setRateUs(Context context, boolean b) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putBoolean(RateUs, b);
        editor.apply();
    }

    public static boolean getRateUs(Context context) {
        SharedPreferences savedSession = PreferenceManager.getDefaultSharedPreferences(context);
        return savedSession.getBoolean(RateUs, false);
    }

    public void setCityId(long id) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putLong(CITY_ID, id);
        editor.apply();

    }

    public long getCityId() {
        return pref.getLong(CITY_ID, 0);
    }

    public void setCityName(String cityName) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(CITY_NAME, cityName);
        editor.apply();
    }

    public String getCityName() {
        return pref.getString(CITY_NAME, "");
    }

    public void setUserEmailId(String emailId) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(USER_EMAIL, emailId);
        editor.apply();
    }

    public String getUserEmailId() {
        return pref.getString(USER_EMAIL,"");
    }

    public void setUserPhoneNumber(String phoneNumber) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(USER_PHONE, phoneNumber);
        editor.apply();
    }

    public String getUserPhoneNumber() {
        return pref.getString(USER_PHONE,"");
    }


    public void setCountryCode(String code) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(USER_COUNTRY_CODE, code);
        editor.apply();
    }

    @Nullable
    public String getCountryCode() {
        return pref.getString(USER_COUNTRY_CODE,"");
    }


    public void setIsUpdate(boolean topicRegister) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(API_TYPE, topicRegister);
        editor.apply();
    }

    public boolean getIsUpdate() {
        return pref.getBoolean(API_TYPE,false);
    }

    public static void setIsLoginRequired(Context context ,boolean b) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putBoolean(PREF_LOGIN_REQUIRED, b);
        editor.apply();
    }

    public boolean getPrefLoginRequired() {
        return pref.getBoolean(PREF_LOGIN_REQUIRED,false);
    }

    public  void setUserCode(Context context, boolean b) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putBoolean(USER_CODE_TYPE, b);
        editor.apply();
    }

    public boolean getUserCode() {
        return pref.getBoolean(USER_CODE_TYPE,false);
    }

    public  void setUserTickets(Context context, boolean b) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putBoolean(USER_TICKET_TYPE, b);
        editor.apply();
    }

    public boolean getUserTickets() {
        return pref.getBoolean(USER_TICKET_TYPE,false);
    }
}
