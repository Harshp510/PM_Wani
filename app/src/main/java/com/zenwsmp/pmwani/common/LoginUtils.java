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



/**
 * Matrubharti(com.nichetech.common) <br />
 * Developed by <b><a href="http://RohitSuthar.com/">Suthar Rohit</a></b>  <br />
 * on 15/12/15.
 *
 * @author Suthar Rohit
 */
public class LoginUtils {

    public static final String PREF_ID = "pref_user_id";
    private static final String PREF_FIRST_NAME = "pref_first_name";
    private static final String PREF_LAST_NAME = "pref_last_name";
    private static final String PREF_EMAIL = "pref_user_email";
    private static final String PREF_PHONE = "pref_user_phone";
    private static final String PREF_PICTURE = "pref_user_picture";
    private static final String PREF_DOB = "pref_user_dob";
    private static final String PREF_GENDER = "pref_user_gender";
    private static final String PREF_COUNTRY_CODE = "pref_country_code";
    private static final String PREF_NOTIFICATIONS = "pref_notificationCount";
    private static final String PREF_GOOGLE_ID = "pref_google_id";
    private static final String PREF_FACEBOOK_ID = "pref_facebook_id";
    private SharedPreferences pref;
    private String userId = "";
    private String firstName = "";
    private String lastName = "";
    private String email = "";
    private String phone = "";
    private String picture = "";
    private String dob = "";
    private String countryCode = "";
    private String gender = "";
    private int notifications = 0;
    private String googleId = "";
    private String facebookId = "";

    public LoginUtils(Context context) {
        try {
            pref = PreferenceManager.getDefaultSharedPreferences(context);
            refreshFirstName();
            refreshLastName();
            refreshPicture();
            refreshEmail();
            refreshMobile();
            refreshUserDOB();
            refreshCountryCode();
            refreshGender();
            refreshNotifications();
            refreshGoogleId();
            refreshFacebookId();
            refreshUserId();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int getNotifications(Context context) {
        SharedPreferences savedSession = PreferenceManager.getDefaultSharedPreferences(context);
        return savedSession.getInt(PREF_NOTIFICATIONS, 0);
    }

    // ID
    private void refreshUserId() {
        try {
            userId = pref.getString(PREF_ID, "");
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(PREF_ID, userId);
        editor.apply();
    }

    // USER FIRST NAME
    private void refreshFirstName() {
        firstName = pref.getString(PREF_FIRST_NAME, "");
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(PREF_FIRST_NAME, firstName);
        editor.apply();
    }

    // USER LAST NAME
    private void refreshLastName() {
        lastName = pref.getString(PREF_LAST_NAME, "");
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(PREF_LAST_NAME, lastName);
        editor.apply();
    }

    public String getName() {
        return firstName + " " + lastName;
    }

    // PICTURE
    private void refreshPicture() {
        picture = pref.getString(PREF_PICTURE, "");
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(PREF_PICTURE, picture);
        editor.apply();
    }

    // EMAIL_CODE
    private void refreshEmail() {
        email = pref.getString(PREF_EMAIL, "");
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(PREF_EMAIL, email);
        editor.apply();
    }

    // PHONE
    private void refreshMobile() {
        phone = pref.getString(PREF_PHONE, "");
    }

    public String getMobile() {
        return phone;
    }

    public void setMobile(String phone) {
        this.phone = phone;
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(PREF_PHONE, phone);
        editor.apply();
    }

    // DOB
    private void refreshUserDOB() {
        dob = pref.getString(PREF_DOB, "");
    }

    public String getDOB() {
        return dob;
    }

    public void setUserDOB(String dob) {
        this.dob = dob;
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(PREF_DOB, dob);
        editor.apply();
    }



    // COUNTRY CODE
    private void refreshCountryCode() {
        countryCode = pref.getString(PREF_COUNTRY_CODE, "");
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(PREF_COUNTRY_CODE, countryCode);
        editor.apply();
    }

    // GENDER_CODE
    private void refreshGender() {
        gender = pref.getString(PREF_GENDER, "");
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String userGender) {
        this.gender = userGender;
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(PREF_GENDER, userGender);
        editor.apply();
    }

    // NOTIFICATIONS
    private void refreshNotifications() {
        notifications = pref.getInt(PREF_NOTIFICATIONS, 0);
    }

    public int getNotifications() {
        return notifications;
    }

    public void setNotifications(int notifications) {
        this.notifications = notifications;
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(PREF_NOTIFICATIONS, notifications);
        editor.apply();
    }

    // GOOGLE ID
    private void refreshGoogleId() {
        googleId = pref.getString(PREF_GOOGLE_ID, "");
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(PREF_GOOGLE_ID, googleId);
        editor.apply();
    }

    // FACEBOOK ID
    private void refreshFacebookId() {
        facebookId = pref.getString(PREF_FACEBOOK_ID, "");
    }

    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String fbID) {
        this.facebookId = fbID;
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(PREF_FACEBOOK_ID, fbID);
        editor.apply();
    }

    // LOGIN
    public boolean isLoggedIn() {
        return !userId.equals("");
    }

    // LOGOUT
    public void logout() {
        setUserId("");
        setFirstName("");
        setLastName("");
        setEmail("");
        setMobile("");
        setPicture("");
        setUserDOB("");
        setCountryCode("");
        setGender("");
        setGoogleId("");
        setFacebookId("");

        setNotifications(0);
       // FirebaseAuth.getInstance().signOut();
    }
}
