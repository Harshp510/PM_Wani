package com.zenwsmp.pmwani.model;

public class PlanBean {

    String id,plan_name,description,price_unit,plan_validity_unit,allow_time_unit;
      int price,allow_time,plan_validity;

    public String getId() {
        return id;
    }

    public String getAllow_time_unit() {
        return allow_time_unit;
    }

    public void setAllow_time_unit(String allow_time_unit) {
        this.allow_time_unit = allow_time_unit;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlan_name() {
        return plan_name;
    }

    public void setPlan_name(String plan_name) {
        this.plan_name = plan_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice_unit() {
        return price_unit;
    }

    public void setPrice_unit(String price_unit) {
        this.price_unit = price_unit;
    }

    public String getPlan_validity_unit() {
        return plan_validity_unit;
    }

    public void setPlan_validity_unit(String plan_validity_unit) {
        this.plan_validity_unit = plan_validity_unit;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getAllow_time() {
        return allow_time;
    }

    public void setAllow_time(int allow_time) {
        this.allow_time = allow_time;
    }

    public int getPlan_validity() {
        return plan_validity;
    }

    public void setPlan_validity(int plan_validity) {
        this.plan_validity = plan_validity;
    }
}
