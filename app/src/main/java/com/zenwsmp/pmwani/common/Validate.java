/*
 * Copyright (c) 2017. Suthar Rohit
 * Developed by Suthar Rohit for NicheTech Computer Solutions Pvt. Ltd. use only.
 * <a href="http://RohitSuthar.com/">Suthar Rohit</a>
 *
 * @author Suthar Rohit
 */

package com.zenwsmp.pmwani.common;

import android.text.TextUtils;
import android.util.Patterns;
import android.widget.EditText;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import java.util.List;
import java.util.regex.Pattern;

/**
 * TicketNinja(in.ticketninja) <br />
 * Developed by <b><a href="http://RohitSuthar.com/">Suthar Rohit</a></b>  <br />
 * on 11/11/16.
 *
 * @author Suthar Rohit
 */
public class Validate {

    public static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

    /**
     * Returns true if the string is null or 0-length.<br />
     * <br />
     * Developed by <b><a href="http://RohitSuthar.com/">Suthar Rohit</a></b>
     *
     * @param str the string to be examined
     * @return true if str is null or zero length
     */
    public static boolean isNull(String str) {
        return str == null || str.equalsIgnoreCase("null") || str.trim().length() == 0;
    }

    /**
     * Returns true if the string is null or 0-length.<br />
     * <br />
     * Developed by <b><a href="http://RohitSuthar.com/">Suthar Rohit</a></b>
     *
     * @param editText the {@link EditText} to be examined
     * @return true if str is null or zero length
     */
    public static boolean isNull(EditText editText) {
        String str = editText.getText().toString().trim();
        return str.equalsIgnoreCase("null") || str.trim().length() == 0;
    }

    /**
     * Returns true if the <u>string is not null</u> and <u>length > 0</u>.<br />
     * <br />
     * Developed by <b><a href="http://RohitSuthar.com/">Suthar Rohit</a></b>
     *
     * @param str the string to be examined
     * @return true if str is not null and length > 0
     */
    public static boolean isNotNull(String str) {
        return !(str == null || str.equalsIgnoreCase("null") || str.trim().length() == 0);
    }

    /**
     * Returns true if the <u>event_date as {@link String}is not null</u> and <u>length > 0</u>.<br />
     * <br />
     * Developed by <b><a href="http://RohitSuthar.com/">Suthar Rohit</a></b>
     *
     * @param strDate the string to be examined
     * @return true if str is not null and length > 0
     */
    public static boolean isDateNotNull(String strDate) {
        return !(strDate == null
                || strDate.equalsIgnoreCase("null")
                || strDate.trim().length() == 0
                || strDate.trim().equalsIgnoreCase("0000-00-00 00:00:00")
                || strDate.trim().equalsIgnoreCase("0000-00-00")
                || strDate.trim().equalsIgnoreCase("0001-01-01 00:00:00")
                || strDate.trim().equalsIgnoreCase("0001-01-01"));
    }

    /**
     * Returns true if the <u>string is not null</u> and <u>length > 0</u>.<br />
     * <br />
     * Developed by <b><a href="http://RohitSuthar.com/">Suthar Rohit</a></b>
     *
     * @param str the string to be examined
     * @return true if str is not null and length > 0
     */
    public static boolean isNotNullList(List<?> str) {
        return !(str == null || str.isEmpty());
    }

    /**
     * Check email Address is valid or not.?<br />
     * <br />
     * Developed by <b><a href="http://RohitSuthar.com/">Suthar Rohit</a></b>
     *
     * @param emailAddress {@link String} email Address
     * @return true if email Address is valid
     */
    public static boolean checkEmail(String emailAddress) {
        Pattern EMAIL_ADDRESS_PATTERN = Pattern
                .compile(EMAIL_PATTERN);
        return EMAIL_ADDRESS_PATTERN.matcher(emailAddress).matches();
    }

    /**
     * Check Mobile Number is valid or not.?<br />
     * <br />
     * Developed by <b><a href="http://RohitSuthar.com/">Suthar Rohit</a></b>
     *
     * @param mobileNumber {@link String} mobile number
     * @return true if mobile number is valid
     */
    public static boolean checkPhone(String mobileNumber) {
        // FOR 10 NUMBER
        // Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile("^([1-9])*([0-9]{9})$");
        // FOR 8 to 15 NUMBERS WITH +
        //Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile("^\\+*([1-9])*([0-9]{8,14})$");
        Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile("^(\\d{3}-)?(\\(\\d{3}\\))?\\d{3}-\\d{4}$");
        return EMAIL_ADDRESS_PATTERN.matcher(mobileNumber).matches();
    }

    public static boolean checkMobileNumber(String mobileNumber) {
        Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile("^([1-9])*([0-9]{9})$");
        return EMAIL_ADDRESS_PATTERN.matcher(mobileNumber).matches();
    }

    /**
     * Check Mobile Number is valid or not.?<br />
     * <br />
     * Developed by <b><a href="http://RohitSuthar.com/">Suthar Rohit</a></b>
     *
     * @param mobileNumber {@link String} mobile number
     * @return true if mobile number is valid
     */
    public static boolean checkMobileNumberInternational(String mobileNumber) {
        Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile("^(\\+?\\d{1,4}[\\s-])?(?!0+\\s+,?$)\\d{10}\\s*,?$");
        return EMAIL_ADDRESS_PATTERN.matcher(mobileNumber).matches();
    }

    /**
     * Check event_date is proper in following format : <b>DD MMM, YYYY</b><br />
     * <br />
     * Developed by <b><a href="http://RohitSuthar.com/">Suthar Rohit</a></b>
     *
     * @param date {@link String} event_date time
     * @return true if event_date is valid
     */
    public static boolean isValidDate(String date) {
        Pattern DATE_PATTERN = Pattern.compile("^([0-9]{2})* ([A-Z,a-z]{3})*(, )([0-9]{4})$");
        return DATE_PATTERN.matcher(date).matches();
    }

    public static boolean checkPhone_libPhoneNumber(String countryCode, String phNumber) {
        if (!TextUtils.isEmpty(phNumber) && Patterns.PHONE.matcher(phNumber).matches()) {
            try {
                PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
                //countryCode = countryCode.replace("+", "");
                //String isoCode = phoneNumberUtil.getRegionCodeForCountryCode(Integer.parseInt(countryCode));
                Phonenumber.PhoneNumber phoneNumber = phoneNumberUtil.parse(countryCode + phNumber, "IN");
                boolean isValid = phoneNumberUtil.isValidNumber(phoneNumber);
                if (isValid) {
                    String internationalFormat = phoneNumberUtil.format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL);
                    //  MessageUtils.showToast(this, "Phone Number is Valid " + internationalFormat);
                    return true;
                } else {
                    // MessageUtils.showToast(this, "Phone Number is Invalid " + phoneNumber);
                    return false;
                }
            } catch (NumberParseException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
    }


    public static boolean CheckMobileNumber(String phNumber){
        return !TextUtils.isEmpty(phNumber) && Patterns.PHONE.matcher(phNumber).matches();
    }


}
