package com.example.mumbae_mart.model;

public class RewardModel {

    private String title;
    private final String expiryDate;
    private final String coupenBody;

    public RewardModel(String title, String expiryDate, String coupenBody) {
        this.title = title;
        this.expiryDate = expiryDate;
        this.coupenBody = coupenBody;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public String getCoupenBody() {
        return coupenBody;
    }
}
