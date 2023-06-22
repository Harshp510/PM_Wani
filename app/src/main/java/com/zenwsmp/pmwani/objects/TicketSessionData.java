/*
 * Copyright (c) 2017. Suthar Rohit
 * Developed by Suthar Rohit for NicheTech Computer Solutions Pvt. Ltd. use only.
 * <a href="http://RohitSuthar.com/">Suthar Rohit</a>
 *
 * @author Suthar Rohit
 */

package com.zenwsmp.pmwani.objects;

import com.zenwsmp.pmwani.common.Constant;
import com.zenwsmp.pmwani.common.RestApi;
import com.zenwsmp.pmwani.common.Utility;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Copyright (c) 2018. aditi
 * Developed by aditi for NicheTech Computer Solutions Pvt. Ltd. use only.
 * on 7/17/17.
 *
 * @author aditi
 */
public class TicketSessionData implements Serializable {

    private static final long serialVersionUID = 6663408501416574228L;


    private final int event_id = 0;
    private final int is_discount = 0;
    private final int total_ticket = 0;
    private final double amount = 0.00;
    private double netamount = 0.0;
    private final Float total_processingfee = 0F;
    private final Float processingfee = 0F;
    private final Float tax = 0F;
    private final int is_rsvp = 0;
    private final int is_inv_rsvp = 0;
    private final double discount_per = 0;


    private String detail_image_url;
    @Constant.EventDayTypeAnt
    private final String day_type = Constant.EventDayType.OTHER;
    private final String type = "";
    private final String event_name = "";
    private final String location_name = "";
    private final String event_date = "";
    private final String event_date_to = "";
    private final String event_time = "";
    private final String tax_type = "";
    private final String latitude = "";
    private final String longitude = "";
    private final boolean paytm_payment = false;
    private final boolean is_paytmwallet = false;
    private final boolean razerpay_payment = false;
    private ArrayList<PaymentNoteList> payment_note_list;
    private List<EventPriceCategory> category_list;

    //pending123 in get_ticket_session_data
    private final boolean seasonpass = false;


    //unused
    //private boolean Is_COD = false;
    private final boolean Invite_Only = false;
    // private String M_D_Date;
    // private String Y_Date;
    // private String Day_Name;
    private String Detail_Image_Id;


    public int getId() {
        return event_id;
    }

    public String getEvent_name() {
        return event_name;
    }

    public String getImageId() {
        return Detail_Image_Id;
    }

    public String getImageUrl() {
        return detail_image_url;
    }

    public String getEvent_date() {
        return event_date;
    }

    public String getEvent_date_to() {
        return event_date_to;
    }

    /*  public String getDay() {
        return Day_Name;
    }

    public String getYear() {
        return Y_Date;
    }

    public String getMDDate() {
        return M_D_Date;
    }*/


    public String getDay() {
        return Utility.getDayName(getEvent_date());
    }

    public String getYear() {
        return Utility.getYear(getEvent_date());
    }

    public String getMDDate() {
        return Utility.getDate(getEvent_date(), getEvent_date_to());
    }

    public String getMDDate1(String type) {
        return Utility.getDate1(getEvent_date(), getEvent_date_to(),type);
    }

    public String getEvent_time() {
        return event_time;
    }

    @Constant.EventDayTypeAnt
    public String getDayType() {
        return day_type;
    }

    public String getVenueName() {
        return location_name;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public List<EventPriceCategory> getPriceCategory() {
        return category_list;
    }

    public String eventType() {
        return type;
    }

    public ArrayList<PaymentNoteList> getPaymentNoteList() {
        return payment_note_list;
    }

    public int getTotalTicket() {
        return total_ticket;
    }

    public double getAmount() {
        return amount;
    }

    private boolean isSeasonPass() {
        return seasonpass;
    }

    public int isRSVP() {
        return is_rsvp;
    }

    public boolean isRazorpayPaymentAllow() {
        return razerpay_payment;
    }

    public boolean isPaytmPaymentAllow() {
        return paytm_payment;
    }

    private boolean isInviteOnly() {
        return Invite_Only;
    }

    public int isInviteRSVP() {
        return is_inv_rsvp;
    }

    public int canAddDiscount() {
        return is_discount;
    }

    private int ErrorCode;
    private String ErrorMessage;
    private String ServiceName;

    public boolean isSuccess() {
        return ErrorCode == RestApi.ErrorCode.SUCCESS;
    }

    public String message() {
        return ErrorMessage;
    }

    public Float getTotal_processingfee() {
        return total_processingfee;
    }

    public Float getProcessingfee() {
        return processingfee;
    }

    public Float getTax() {
        return tax;
    }

    public String getTax_type() {
        return tax_type;
    }

    public double getNetamount() {
        return netamount;
    }

    public boolean isIs_paytmwallet() {
        return is_paytmwallet;
    }

    public void setNetamount(double netamount) {
        this.netamount = netamount;
    }

    @Override
    public String toString() {
        return "TicketSessionData{" +
                "event_id=" + event_id +
                ", is_discount=" + is_discount +
                ", total_ticket=" + total_ticket +
                ", amount=" + amount +
                ", netamount=" + netamount +
                ", total_processingfee=" + total_processingfee +
                ", processingfee=" + processingfee +
                ", tax=" + tax +
                ", is_rsvp=" + is_rsvp +
                ", is_inv_rsvp=" + is_inv_rsvp +
                ", discount_per=" + discount_per +
                ", detail_image_url='" + detail_image_url + '\'' +
                ", day_type='" + day_type + '\'' +
                ", type='" + type + '\'' +
                ", event_name='" + event_name + '\'' +
                ", location_name='" + location_name + '\'' +
                ", event_date='" + event_date + '\'' +
                ", event_date_to='" + event_date_to + '\'' +
                ", event_time='" + event_time + '\'' +
                ", tax_type='" + tax_type + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", paytm_payment=" + paytm_payment +
                ", razerpay_payment=" + razerpay_payment +
                ", payment_note_list=" + payment_note_list +
                ", category_list=" + category_list +
                ", seasonpass=" + seasonpass +
                ", Invite_Only=" + Invite_Only +
                ", Detail_Image_Id='" + Detail_Image_Id + '\'' +
                ", ErrorCode=" + ErrorCode +
                ", ErrorMessage='" + ErrorMessage + '\'' +
                ", ServiceName='" + ServiceName + '\'' +
                '}';
    }
}