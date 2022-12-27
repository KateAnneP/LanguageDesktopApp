package com.apka;

public class Kategoria {

    private int id_w_bazie;
    private String kategoria;

    public Kategoria(String kategoria, int id_w_bazie)
    {
        this.kategoria = kategoria;
        this.id_w_bazie = id_w_bazie;
    }


    public int getId_w_bazie() {
        return id_w_bazie;
    }

    public void setId_w_bazie(int id_w_bazie) {
        this.id_w_bazie = id_w_bazie;
    }

    public String getKategoria() {
        return kategoria;
    }

    public void setKategoria(String kategoria) {
        this.kategoria = kategoria;
    }
}
