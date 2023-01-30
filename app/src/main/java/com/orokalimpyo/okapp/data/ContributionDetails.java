package com.orokalimpyo.okapp.data;

public class ContributionDetails {
    public String id;
    public String contribution_id;
    public String name;
    public String type;
    public String barangay;
    public String address;
    public String number;
    public String plastic;
    public String brand;
    public String kilo;
    public String month;
    public String day;
    public String year;
    public String date;
    public String time;
    public String imageLink;

    public ContributionDetails(){}

    public ContributionDetails(String id,String contribution_id, String name, String type, String barangay, String address, String number, String plastic, String brand, String kilo, String month, String day, String year, String date, String time,String imageLink) {
        this.id = id;
        this.contribution_id = contribution_id;
        this.name = name;
        this.type = type;
        this.barangay = barangay;
        this.address = address;
        this.number = number;
        this.plastic = plastic;
        this.brand = brand;
        this.kilo = kilo;
        this.month = month;
        this.day = day;
        this.year = year;
        this.date = date;
        this.time = time;
        this.imageLink = imageLink;
    }



}
