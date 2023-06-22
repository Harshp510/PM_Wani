/*
 * Copyright (c) 2017. Suthar Rohit
 * Developed by Suthar Rohit for NicheTech Computer Solutions Pvt. Ltd. use only.
 * <a href="http://RohitSuthar.com/">Suthar Rohit</a>
 *
 * @author Suthar Rohit
 */

package com.zenwsmp.pmwani.common;

import android.os.Environment;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.EditText;

import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * TicketNinja(in.ticketninja) <br />
 * Developed by <b><a href="http://RohitSuthar.com/">Suthar Rohit</a></b>  <br />
 * on 13/01/2016.
 *
 * @author Suthar Rohit
 */
public class Constant {

    public static final String URL = "https://www.ticketninja.in/event/-chakravyuh-featuring-nitish-bharadwaj/1073";

    public static final String SVG_BLOCK = "SVG_Block";
    public static final String ONWARDS = "Onwards";
    public static final String DEFAULT_COUNTRY_REGION = "IN";
    public static final String DEVICE_TYPE = "android";
    public static final String ENGINE_TYPE = "App";
    public static final String DEVICE_INFO = "App";
    public static final String TIMER_FORMAT = "%02d:%02d";

    public static final int MAXIMUM_UPLOAD_FILE_SIZE = 1024 * 4;
    public static final int PAYMENT_SESSION_TIMEOUT = 12;

    public static final String EVENT_TYPE_ATTRACTION = "Attraction";
    public static final String SEAT_TYPE_FREE_SEATING = "Free Seating";
    public static final String SEAT_TYPE_ARRANGE_SEATING = "Reserved Seating";
    public static final String IMAGE_DOWNLOAD_PATH = Environment.getExternalStorageDirectory() + "/.TicketNinja/";

    public static final String CONTACT_REPLACE_EXPRESSION = "[^+0-9]";
    public static final int REQUEST_CODE_GOOGLE_SIGN_IN = 1001;
   // public static final int PERMISSION_FOR_FACEBOOK_LOGIN = 2001;
    public static final String HASH = "#";

    //public static final String PROMO_SLIDER_LIST = "Promo_Slider_List";

    public static final String WALLET = "wallet";
    public static final String DASHBOARD = "dashboard";

    public static class Code {
        public static final int ONE = 1;
        public static final int ZERO = 0;
    }

    public static class InviteStatus {
        public static final String AVAILABLE = "available";
        public static final String LEFT = "left";
        public static final String USED = "used";
    }

    public static class PROMO_SLIDER_LIST {
        public static final String PROMO = "promo";
        public static final String EVENT = "event";
        public static final String COLLECTION = "collection";
    }


    public static class Gender {
        public static final String MALE = "Male";
        public static final String FEMALE = "Female";
    }

    public static class ActivityForResult {
        public static final int CAMERA = 1010;
        public static final int GALLERY = 1011;
        public static final int CROP_IMAGE = 1012;

        public static final int LOGIN = 1013;
        public static final int REGISTER = 1014;
        public static final int EDIT_PROFILE = 1015;
        public static final int FILE_SELECT = 1016;
        public static final int PICK_CONTACT = 1017;
        public static final int NOTIFICATION = 1018;

        public static final int FROM_MY_TICKETS = 1019;
        public static final int SHARE_TICKETS = 1020;
        public static final int UPDATE_CONTACT_DETAIL = 1021;
    }

    public static class RequestPermissions {
        public static final int CAMERA = 1111;
        public static final int GALLERY = 1112;
        public static final int WRITE_EXTERNAL_STORAGE = 1113;
        public static final int WRITE_EXTERNAL_STORAGE_FOR_BGDWNLD = 1115;
        public static final int READ_EXTERNAL_STORAGE = 1114;
        public static final int READ_CONTACTS = 1115;
    }

    public static class ScreenExtras {
        public static final String PAGE = "page";
        public static final String MESSAGE = "message";
        public static final String FROM_SCREEN = "extraFromScreen";
        public static final String REDIRECT_TO = "extraRedirectTo";
        public static final String PAGE_TYPE = "page_type";
        public static final String DATA = "extraData";
        public static final String EMAIL = "extraEmail";
        public static final String LOGIN_REQUIRED = "login_required";

        public static final int FROM_SPLASH = 101;
        public static final int FROM_ACTIVITY_MAIN = 102;
        public static final int FROM_ACTIVITY_MY_PROFILE = 103;
        public static final int FROM_ACTIVITY_NOTIFICATION = 104;
        public static final int FROM_ACTIVITY_PAYMENT = 105;
        public static final int FROM_ACTIVITY_SERVICE = 106;
        public static final int FROM_ACTIVITY_BOOKING_HISTORY = 107;
        public static final int FROM_ACTIVITY_MY_TICKETS = 108;


        public static final int PRESS_LOGIN = 104;
        public static final int PRESS_SIGNUP = 105;

        public static final String POSITION = "position";
        public static final String EVENT_GALLERY_DATA = "gallery";
        public static final String EVENT_C_TYPE = "c_tag";
        public static final String EVENT_TYPE_NAME = "extraEventTypeName";
        public static final String EVENT_TYPE_KEY = "extraEventTypeKey";
        public static final String EVENT_DATA = "extraEventData";
        public static final String EVENT_DATA1 = "extraEventData1";
        public static final String EVENT_ID = "extraEventId";
        public static final String SHOW_ID = "extraShowId";
        public static final String EVENT_NAME = "extraEventName";
        public static final String EVENT_DISCOUNT_PER = "extraDiscountPer";
        public static final String EVENT_DISCOUNT_LABEL = "extraDiscountLabel";
        public static final String PLAN_CATEGORY_ID = "extraPlanCategoryId";
        public static final String SEAT_COUNT = "extraSeatCount";
        public static final String EVENT_TIME = "extraEventTime";
        public static final String EVENT_DATE = "extraEventDate";
        public static final String EVENT_ADDRESS = "extraEventAddress";
        public static final String EVENT_CITY = "extraEventCity";
        public static final String BOOKING_DATA = "extraBookingData";
        public static final String EVENT_SESSION_ID = "extraSessionId";
        public static final String EVENT_HISTORY_TYPE = "extraHistoryType";
        public static final String EVENT_TRANSACTION_ID = "extraTransactionId";
        public static final String EVENT_TRANSACTION_TYPE = "extraTransactionType";
        public static final String EVENT_TRANSACTION_TICKET_LIVE = "ticket_live";
        public static final String EVENT_TRANSACTION_APP_TICKET = "app_ticket";
        public static final String EVENT_SESSION_DATA = "extraSessionData";
        public static final String EVENT_NOTE_LIST = "extraNoteList";
        public static final String KEY_SECTION = "extraSection";
        public static final String KEY_IMAGE_TITLE = "extraImageTitle";
        public static final String KEY_IMAGE_LINK = "extraImageLink";
        public static final String CONTACT_ACTION = "extraContactActionEdit";
        public static final String TICKET_SESSION_DATA = "ticketSessionData";

        public static final String PRIVACY = "privacy";
        public static final String SHARE_VIA = "extraShareVia";
        public static final String TRAN_ID = "tranid";
        public static final String BOOKING = "Booking";
        public static final String TICKET = "Ticket";


        public static final String TYPE = "type";
        public static final String IMAGE_URI = "imageUri";
        public static final String YOU_TUBE_CODE = "VideoCode";

        public static final String EVENT_ADVANCE_DAYS = "advance_days";
        public static final String EVENT_TOTAL_TICKET = "total_ticket";
        public static final String HOURS = "hours";
        public static final String EXCEPTION_DATES = "exception_date";
        public static final String MULTI_CATEGORY_SELECT = "multi_catergory_select";
        public static final String IDENTIFICATION = "identification";
        public static final String USER_IDENTIFICATION_LIST = "user_identification_list";
        public static final String USER_IDENTIFICATION_DATA = "User_Identification_Data";
        //public static final String USER_INFO_DATA = "User_Info_Data";
        public static final String USER_INFO_DATA = "user_info_data";//User_Info_List
        public static final String SESSION_ID = "session_id";
        public static final String DEEP_LINK_URI = "uri";
        public static final String DEEP_LINK = "deepLink";

        public static final String BANK_NAME = "bank_name";
        public static final String PAYMENT_METHOD = "payment_method";
        public static final String WALLET_TYPE = "wallet_type";


        public static final String C_ID = "c_id";
        public static final String M_ID = "m_id";
    }

    // HISTORY TYPE
    public static class HistoryType {
        public static final String FUTURE = "future";
        public static final String PAST = "old";
    }

    // REGISTRATION MODE
    public static class RegisterMode {
        public static final String INSERT = "IN";
        public static final String UPDATE = "UP";
    }

    // EVENT DAY_TYPE
    public static class EventDayType {
        public static final String SINGLE_DAY = "Singleday";
        public static final String MULTIPLE_DAY = "Multipleday";
        public static final String MULTI_DAY_MULTI_SHOW = "Multipledayevent";
        public static final String PERPETUAL = "Perpetual";
        public static final String OTHER = "";
    }

    // NOTIFICATION TYPE
    public static class NotificationType {
        public static final int EVENT = 1;
        public static final int TICKET = 2;
        public static final int BULK_EVENT_DETAIL = 3;
    }

    //Define the list of accepted constants
    @StringDef({EventDayType.SINGLE_DAY, EventDayType.MULTIPLE_DAY, EventDayType.MULTI_DAY_MULTI_SHOW, EventDayType.PERPETUAL, EventDayType.OTHER})
    @Retention(RetentionPolicy.SOURCE)
    public @interface EventDayTypeAnt {
    }

    //Define the list of accepted constants
    @StringDef({RegisterMode.INSERT, RegisterMode.UPDATE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface RegisterWSMode {
    }

    // PAYMENT TYPE
    public static class TicketStatus {
        public static final String OPEN = "Open";
        public static final String RECEIVED = "Received";
        public static final String USED = "Used";
        public static final String REVOKE = "Revoke";
        public static final String SHARED = "Shared";
    }

    // PAYMENT TYPE
    public static class PaymentType {
        public static final String COD = "COD";
        //public static final String ONLINE = "ONLINE";
        public static final String ONLINE = "Online";
        public static final String RSVP = "rsvp";
        public static final String COMPLEMENTARY = "Complementary";
    }

    // MERCHANT TYPE
    public static class MerchantType {
        public static final String RAZORPAY = "Razorpay";
        public static final String PAYTM = "Paytm";
    }

    public static class RazorPayPaymentType {
        public static final String CREDIT_DEBIT_CARD = "Credit / Debit Card";
        public static final String NETBANKING = "Net Banking";
        public static final String WALLET = "Wallet";
        public static final String UPI = "UPI";
    }

    public static class RazorPayPaymentMethod {
        public static final String CARD = "card";
        public static final String NET_BANKING = "netbanking";
        public static final String WALLET = "wallet";
        public static final String UPI = "upi";
    }

    public static class CARD {
        public static final String NAME = "name";
        public static final String NUMBER = "number";
        public static final String EXPIRY_MONTH = "expiry_month";
        public static final String EXPIRY_YEAR = "expiry_year";
        public static final String CVV = "cvv";
    }

    public static class UpiIntentMethod {
        public static final String GOOGLE_PAY_PACKAGE_NAME = "com.google.android.apps.nbu.paisa.user";
        public static final String PAYTM_PACKAGE_NAME = "net.one97.paytm";
        public static final String PHONE_PAY_PACKAGE_NAME = "com.phonepe.app";
        public static final String AMAZON_PAY_PACKAGE_NAME = "in.amazon.mShop.android.shopping";
        public static final String BHIM_PACKAGE_NAME = "in.org.npci.upiapp";
    }

    public static class BankName {

        public static final String SBI = "SBIN";
        public static final String HDFC = "HDFC";
        public static final String ICICI = "ICIC";
        public static final String AXIS = "UTIB";
        public static final String KOTAK = "KKBK";
        public static final String YES = "YESB";
    }

    public static class WalletName {
        public static final String AMAZON_PAY = "Amazon Pay";
        public static final String FREE_CHARGE = "Freecharge";
        public static final String PAY_Z = "PayZapp";
        public static final String PAYTM = "Paytm";
        public static final String MOBIKWIK = "Mobikwik";
        public static final String AIRTEL_MONEY = "Airtel Money";
        public static final String JIO_MONEY = "JioMoney";
        public static final String PHONE_PE = "PhonePe";
    }

    public static class WalletApiName {
        public static final String AMAZON_PAY = "amazonpay";
        public static final String FREE_CHARGE = "freecharge";
        public static final String PAY_Z = "payzapp";
        public static final String MOBIKWIK = "mobikwik";
        public static final String AIRTEL_MONEY = "airtelmoney";
        public static final String JIO_MONEY = "jiomoney";
        public static final String PHONE_PE = "phonepe";

        public static final String PAYTM = "paytm";
    }

    public static class CardName {
        public static final String VISA = "visa";
        public static final String MASTER_CARD = "mastercard";
        public static final String GOOGLE_PAY = "maestro16";
        public static final String AMEX = "amex";
        public static final String RUPAY = "rupay";
        public static final String MAESTRO = "maestro";
        public static final String DINER = "diners";
        public static final String UNKNOWN = "unknown";

    }

    // DeepLinkPathPrefix TYPE
    public static class DeepLinkPathPrefix {
        public static final String BOOKING_CONFIRMATION = "BookingConfirmation";
        public static final String VIEW_TICKET = "viewticket";
        public static final String EVENT = "event";
    }

    //Define the list of accepted constants
    @StringDef({PaymentType.COD, PaymentType.ONLINE, PaymentType.RSVP, PaymentType.COMPLEMENTARY})
    @Retention(RetentionPolicy.SOURCE)
    public @interface PaymentWSType {
    }

    //Define the list of accepted constants
    @StringDef({MerchantType.RAZORPAY, MerchantType.PAYTM})
    @Retention(RetentionPolicy.SOURCE)
    public @interface MerchantWSType {
    }

    // PAYMENT
    public static final int PAYMENT_SUCCESS = 1;
    public static final int PAYMENT_FAILED = 2;
    public static final int PAYMENT_PENDING = 3;
    public static final String PAYMENT_SUCCESS_TITLE = "success";
    public static final String PAYMENT_FAILED_TITLE = "fail";
    public static final String PAYMENT_PENDING_TITLE = "pending";
    public static final String PAYMENT_CANCELED_TITLE = "cancel";

    public static class SelectTicket {

        public static final String SELECTED_DATE = "event_date";
        public static final String IMAGE_ID = "imageid";
        public static final String COUNTRYNAME = "cname";
        public static final String TIME = "time";
        public static final String TIME_LIST = "timelist";
        public static final String CATEGORYLIST = "categorylist";
        public static final String COUNTRY_CODE = "code";


    }

    public static class Register {
        public static final String EMAIL = "email";
    }

    public static String generateQRCodeURL(String value) {
        return "https://api.qrserver.com/v1/create-qr-code/?data=" + value + "&size=100x100.png";
    }

    public static String generateQRCodeURL1(String value) {
        return value;
        //return "https://api.qrserver.com/v1/create-qr-code/?data=" + value + "&size=100x100.png";
    }

    public static void showPassword(EditText editText) {
        editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
    }

    public static void hidePassword(EditText editText) {
        editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
    }

    /*public static String getQRCodeFromURL(String src){
        if (Validate.isNotNull(src)) {
            Uri uri = Uri.parse(src);
            return uri.getQueryParameter("data");
        }else {
            return "";
        }
    }*/

    // SHARE VIA
    public static class ShareVia {
        public static final int MOBILE_NO = 1;
        public static final int EMAIL = 2;
        public static final int CONTACT = 3;
    }

    public class ContactActions {
        public static final String EDIT = "mActionEdit";
        public static final String CREATE = "mActionCreate";
    }

    public class EventFilter {
        public static final String TODAY = "Today";
        public static final String TOMORROW = "Tomorrow";
        public static final String THIS_WEEKEND = "This Weekend";
    }
}
