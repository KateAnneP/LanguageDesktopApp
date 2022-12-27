package com.apka;

public class Jezyk {

    private int id_w_bazie;
    private String jezyk;

    public Jezyk(String jezyk, int id_w_bazie)
    {
        this.jezyk = jezyk;
        this.id_w_bazie = id_w_bazie;
    }


    public String getJezyk() {
        return jezyk;
    }

    public void setJezyk(String jezyk) {
        this.jezyk = jezyk;
    }

    public int getId_w_bazie() {
        return id_w_bazie;
    }

    public void setId_w_bazie(int id_w_bazie) {
        this.id_w_bazie = id_w_bazie;
    }
}
