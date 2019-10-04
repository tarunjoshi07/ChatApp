package com.example.sandeep.dmsat;

import java.util.ArrayList;

public class Member {
    private String  fname;
    private  String lname;
    private  String id;
    private String password;
    private ArrayList<String> friendreq=new ArrayList<>();

    public Member(){

    }
    public Member(String fname, String lname, String id, String password) {
        this.fname = fname;
        this.lname = lname;
        this.id = id;
        this.password = password;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }
}
