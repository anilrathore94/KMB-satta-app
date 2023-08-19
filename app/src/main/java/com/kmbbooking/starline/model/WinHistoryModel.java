package com.kmbbooking.starline.model;

public class WinHistoryModel {

    String bid_id;
    String game_name;

    String game_type;
    String session;

    String open_pana;
    String open_digit;

    String close_pana;
    String close_digit;

    String winning_points;
    String points_action;
    String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWinning_points() {
        return winning_points;
    }

    public void setWinning_points(String winning_points) {
        this.winning_points = winning_points;
    }

    public String getPoints_action() {
        return points_action;
    }

    public void setPoints_action(String points_action) {
        this.points_action = points_action;
    }

    public String getBid_id() {
        return bid_id;
    }

    public void setBid_id(String bid_id) {
        this.bid_id = bid_id;
    }

    public String getGame_name() {
        return game_name;
    }

    public void setGame_name(String game_name) {
        this.game_name = game_name;
    }

    public String getGame_type() {
        return game_type;
    }

    public void setGame_type(String game_type) {
        this.game_type = game_type;
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

    public String getOpen_digit() {
        return open_digit;
    }

    public void setOpen_digit(String open_digit) {
        this.open_digit = open_digit;
    }

    public String getClose_pana() {
        return close_pana;
    }

    public void setClose_pana(String close_pana) {
        this.close_pana = close_pana;
    }

    public String getClose_digit() {
        return close_digit;
    }

    public void setClose_digit(String close_digit) {
        this.close_digit = close_digit;
    }
}
