package com.orokalimpyo.okapp.data;

public class ContributionListData {
    public String id;
    public String contribution_id;
    public String barangay;
    public String date;
    public String time;
    public String plastic;

    public String link;
    public ContributionListData(){

    }

    public ContributionListData(String id,String contribution_id, String barangay, String date, String time, String plastic,String link) {
        this.id = id;
        this.contribution_id = contribution_id;
        this.barangay = barangay;
        this.date = date;
        this.time = time;
        this.plastic = plastic;
        this.link = link;
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

    public String getBarangay() {
        return barangay;
    }

    public void setBarangay(String barangay) {
        this.barangay = barangay;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime(){
        return time;
    }

    public void setTime(String time){
        this.time = time;
    }

    public String getPlastic() {
        return plastic;
    }

    public void setPlastic(String plastic) {
        this.plastic = plastic;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
