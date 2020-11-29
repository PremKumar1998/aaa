package com.example.login;

public class Log {
    public String respid;
    public String datee;
    public int dur;


    public Log(String respid, String datee, int dur) {
        this.respid=respid;
        this.datee=datee;
        this.dur=dur;
    }

    public String getResp() {
        return respid;
    }

    public String getDate() {
        return datee;
    }

    public int getDur() {
        return dur;
    }
}
