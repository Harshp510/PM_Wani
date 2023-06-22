package com.zenwsmp.pmwani.common;


import com.zenwsmp.pmwani.BuildConfig;

public class PaytmConstant {
    public static final String M_ID = "MID"; //Paytm Merchand Id we got it in paytm credentials
    public static final String CHANNEL_ID = "CHANNEL_ID"; //Paytm Channel Id , got it in paytm credentials
    public static final String INDUSTRY_TYPE_ID = "INDUSTRY_TYPE_ID"; //Paytm industry type  ,got it in paytm credential
    public static final String WEBSITE = "WEBSITE";
    public static final String CALLBACK_URL = "CALLBACK_URL";
    public static final String EMAIL_CODE = "EMAIL";
    public static final String MOBILE_NO_CODE = "MOBILE_NO";
    public static final String CHECKSUMHASH = "CHECKSUMHASH";
    public static final String TXN_AMOUNT = "TXN_AMOUNT";
    public static final String ORDER_ID = "ORDER_ID";
    public static final String CUST_ID = "CUST_ID";
    public static final String CURRENCY = "CURRENCY";

    public static class payTmConfig {
        public static final String WEBSITE = "WEBSTAGING";//APPSTAGING ,WEBSTAGING
        public static final String CHANNEL_ID = "WEB";//WEB ,WAP ,APP,SYSTEM
        public static final String INDUSTRY_TYPE_ID = "Retail";
        public static final String CALLBACK_URL = BuildConfig.PAYTM_CALLBACK_URL ;
        public static final String CURRENCY ="INR";
        public static final boolean IS_PAYTM_LIVE = BuildConfig.IS_PAYTM_LIVE;
        //public static final String CALLBACK_URL = "https://pguat.paytm.com/oltp-web/processTransaction?ORDER_ID=" ;
    }

}


//https://securegw.paytm.in/theia/paytmCallback?ORDER_ID= is dev
//https://securegw-stage.paytm.in/theia/paytmCallback?ORDER_ID=order1
//https://pguat.paytm.com/oltp-web/processTransaction
//https://securegw-stage.paytm.in/theia/paytmCallback?ORDER_ID= is prod
//https://pguat.paytm.com/oltp-web/processTransaction
