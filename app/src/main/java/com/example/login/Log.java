package com.example.login;

public class Log {
    public String respnamee= null;
    public String datee =null;
    public int dur = 0;


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
