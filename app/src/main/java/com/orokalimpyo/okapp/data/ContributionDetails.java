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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContribution_id() {
        return contribution_id;
    }

    public void setContribution_id(String contribution_id) {
        this.contribution_id = contribution_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBarangay() {
        return barangay;
    }

    public void setBarangay(String barangay) {
        this.barangay = barangay;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPlastic() {
        return plastic;
    }

    public void setPlastic(String plastic) {
        this.plastic = plastic;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getKilo() {
        return kilo;
    }

    public void setKilo(String kilo) {
        this.kilo = kilo;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
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

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }
}
