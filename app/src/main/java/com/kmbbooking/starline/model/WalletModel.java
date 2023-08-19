package com.kmbbooking.starline.model;

/**
 * Created by Rahul on 12/22/2020.
 */

public class WalletModel {


    String amount;
    String updated_amount;
    String remark;
    String date;
    String time;


    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getUpdated_amount() {
        return updated_amount;
    }

    public void setUpdated_amount(String updated_amount) {
        this.updated_amount = updated_amount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
