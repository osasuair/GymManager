package com.example.gymmanagementsystem.models;

public class MonthPayment {
    private final String month, fee;
    public MonthPayment(String month, String fee) {
        this.month = month;
        this.fee = fee;
    }

    public String getMonth() {
        return month;
    }

    public String getFee() {
        return fee;
    }
}
