/*
 * Copyright (c) 2017. Suthar Rohit
 * Developed by Suthar Rohit for NicheTech Computer Solutions Pvt. Ltd. use only.
 * <a href="http://RohitSuthar.com/">Suthar Rohit</a>
 *
 * @author Suthar Rohit
 */

package com.zenwsmp.pmwani.objects;

import java.io.Serializable;

/**
 * Matrubharti(com.nichetech.matrubharti) <br />
 * Developed by <b><a href="http://www.RohitSuthar.com/">Suthar Rohit</a></b>  <br />
 * on 22-Jun-2017.
 *
 * @author Suthar Rohit
 */
public class EventPriceCategory implements Serializable {

    private static final long serialVersionUID = 6663408501416574225L;

    private final long category_id = 0;
    private final long show_id = 0;
    private double mrp = 0;
    private final double rate = 0;
    private int noofticket = 0;
    private double amount = 0;
    private final int addons = 0;
    private final int seasonpass = 0;
    private final int identification = 0;
    private final int total_seat = 0;//total_seat
    private double totalMRP = 0;
    private double totalRate = 0;

    private final String category_name = "";
    private final String show_name = "";
    private final String event_date = "";
    private final String time = "";
    private final String seat_type = "";
    private final String c_desc = "";
    private final String seat_no = "";

    public long getCategory_id() {
        return category_id;
    }

    private double getTotalMRP() {
        return totalMRP;
    }

    public void setTotalMRP(double totalMRP) {
        this.totalMRP = totalMRP;
    }

    public void setMrp(double mrp) {
        this.mrp = mrp;
    }

    public double getMrp() {
        return mrp;
    }

    public EventPriceCategory() {
    }

    public long id() {
        return category_id;
    }

    public String name() {
        return category_name;
    }

    public String description() {
        return c_desc;
    }

    public int noOfTicket() {
        return noofticket;
    }

    public void noOfTicket(int noOfTicket) {
        noofticket = noOfTicket;
    }

    public double amount() {
        return amount;
    }

    public void amount(double amount) {
        this.amount = amount;
    }

    public double rate() {
        return rate;
    }

    public String seatNo() {
        return seat_no;
    }

    public String seatType() {
        return seat_type;
    }

    public String eventDate() {
        return event_date;
    }

    public long showId() {
        return show_id;
    }

    public int isAddons() {
        return addons;
    }

    public int isSeasonPass() {
        return seasonpass;
    }

    public int stock() {
        return total_seat;
    }

    public String time() {
        return time;
    }

    public int isIdentification() {
        return identification;
    }

    public double getTotalRate() {
        return totalRate;
    }

    public void setTotalRate(double totalRate) {
        this.totalRate = totalRate;
    }

    @Override
    public String toString() {
        return "EventPriceCategory{" +
                "c_desc='" + c_desc + '\'' +
                ", category_id=" + category_id +
                ", category_name='" + category_name + '\'' +
                ", noofticket=" + noofticket +
                ", seat_no='" + seat_no + '\'' +
                ", seat_type='" + seat_type + '\'' +
                ", identification=" + identification +

                '}';
    }
}
