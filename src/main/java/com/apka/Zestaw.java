package com.apka;

public class Zestaw {

    private int id_w_bazie;
    private String zestaw;
    private String jezyk;

    public Zestaw(String zestaw, int id_w_bazie, String jezyk)
    {
        this.zestaw = zestaw;
        this.id_w_bazie = id_w_bazie;
        this.jezyk = jezyk;
    }


    public int getId_w_bazie() {
        return id_w_bazie;
    }

    public void setId_w_bazie(int id_w_bazie) {
        this.id_w_bazie = id_w_bazie;
    }

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
    }
}
