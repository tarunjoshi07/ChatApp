package com.example.sandeep.dmsat;

public class SavedClass {
    String msj;
    int code;
    long time;
    public SavedClass()
    {}
    public SavedClass(String msj, int code, long time) {
        this.msj = msj;
        this.code = code;
        this.time = time;
    }

    public String getMsj() {
        return msj;
    }

    public int getCode() {
        return code;
    }

    public long getTime() {
        return time;
    }
}
