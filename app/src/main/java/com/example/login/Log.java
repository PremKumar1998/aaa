package com.example.login;

public class Log {
    public String respnamee;
    public String datee;
    public int dur;


    public Log(String respnamee, String datee, int dur) {
        this.respnamee=respnamee;
        this.datee=datee;
        this.dur=dur;
    }

    public String getResp() { return respnamee; }

    public String getDate() {
        return datee;
    }

    public int getDur() {
        return dur;
    }
}
