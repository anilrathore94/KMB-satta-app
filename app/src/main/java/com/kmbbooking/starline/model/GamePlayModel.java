package com.kmbbooking.starline.model;

/**
 * Created by rahul on 11/05/2019.
 */

public class GamePlayModel {


    String id;
    String date;
    String session;
    String open_pana;
    String close_pana;
    String open_digit;
    String close_digit;
    String points;
    String push_type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getOpen_pana() {
        return open_pana;
    }

    public void setOpen_pana(String open_pana) {
        this.open_pana = open_pana;
    }

    public String getClose_pana() {
        return close_pana;
    }

    public void setClose_pana(String close_pana) {
        this.close_pana = close_pana;
    }

    public String getOpen_digit() {
        return open_digit;
    }

    public void setOpen_digit(String open_digit) {
        this.open_digit = open_digit;
    }

    public String getClose_digit() {
        return close_digit;
    }

    public void setClose_digit(String close_digit) {
        this.close_digit = close_digit;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getPush_type() {
        return push_type;
    }

    public void setPush_type(String push_type) {
        this.push_type = push_type;
    }

    @Override
    public String toString() {
        return push_type + "$$$$" + points + "$$$$" +
                close_digit + "$$$$" + open_digit + "$$$$" + close_pana + "$$$$" + open_pana + "$$$$" + session + "$$$$" +
                date + "$$$$" + id;
    }

}
