package com.orokalimpyo.okapp.data;

public class UserDetails {

    public String id,user_type,name,barangay,address,gender,number,email,password;

    public UserDetails() { }

    public UserDetails(String id,String user_type,String name,String barangay,
                       String address,String gender,String number,String email,String password) {
        this.id = id;
        this.user_type = user_type;
        this.name = name;
        this.barangay = barangay;
        this.address = address;
        this.gender = gender;
        this.number = number;
        this.email = email;
        this.password = password;
    }


}
