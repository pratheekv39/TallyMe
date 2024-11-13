package com.example.taxloancalci;

public class HistoryItem {
    private String calculationType;
    private String details;
    private String date;

    public HistoryItem(String calculationType, String details, String date) {
        this.calculationType = calculationType;
        this.details = details;
        this.date = date;
    }

    // Getters
    public String getCalculationType() { return calculationType; }
    public String getDetails() { return details; }
    public String getDate() { return date; }
}