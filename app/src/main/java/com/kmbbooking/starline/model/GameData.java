package com.kmbbooking.starline.model;

public class GameData {
    String gameName;
    String openTime;
    String closeTime;
    String marketStatus;
    String openPana;
    String openDigit;
    String closePana;
    String closeDigit;
    String gameStatus;

    public String getOpenPana() {
        return openPana;
    }

    public void setOpenPana(String openPana) {
        this.openPana = openPana;
    }

    public String getOpenDigit() {
        return openDigit;
    }

    public void setOpenDigit(String openDigit) {
        this.openDigit = openDigit;
    }

    public String getClosePana() {
        return closePana;
    }

    public void setClosePana(String closePana) {
        this.closePana = closePana;
    }

    public String getCloseDigit() {
        return closeDigit;
    }

    public void setCloseDigit(String closeDigit) {
        this.closeDigit = closeDigit;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }

    public String getMarketStatus() {
        return marketStatus;
    }

    public void setMarketStatus(String marketStatus) {
        this.marketStatus = marketStatus;
    }

    public String getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(String gameStatus) {
        this.gameStatus = gameStatus;
    }
}
