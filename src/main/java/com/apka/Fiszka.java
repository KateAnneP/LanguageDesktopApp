package com.apka;

public class Fiszka {
    private String slowko;
    private String tlumaczenie;
    //private String jezyk;
    //private String zestaw;


    public Fiszka(String slowko, String tlumaczenie /*, String jezyk, String zestaw*/) {
        this.slowko = slowko;
        this.tlumaczenie = tlumaczenie;
        //this.jezyk = jezyk;
        //this.zestaw = zestaw;
    }

    /*
    public String getZestaw() {
        return zestaw;
    }

    public void setZestaw(String zestaw) {
        this.zestaw = zestaw;
    }

    public String getJezyk() {
        return jezyk;
    }

    public void setJezyk(String jezyk) {
        this.jezyk = jezyk;
    }*/

    public String getTlumaczenie() {
        return tlumaczenie;
    }

    public void setTlumaczenie(String tlumaczenie) {
        this.tlumaczenie = tlumaczenie;
    }

    public String getSlowko() {
        return slowko;
    }

    public void setSlowko(String slowko) {
        this.slowko = slowko;
    }
}
