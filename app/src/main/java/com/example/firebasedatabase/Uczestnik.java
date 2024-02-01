package com.example.firebasedatabase;

import java.io.Serializable;

public class Uczestnik implements Serializable {
    String key;
    String imie,nazwaZwiazku,kodLicencji;

    Punkty punkty;



    public void setPunkty(Punkty punkty){
        this.punkty = punkty;
    }

    public Punkty getPunkty(){
        return punkty;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getnazwaZwiazku() {
        return nazwaZwiazku;
    }

    public void setnazwaZwiazku(String nazwisko) {
        this.nazwaZwiazku = nazwisko;
    }

    public String getKodLicencji() {
        return kodLicencji;
    }

    public void setKodLicencji(String kodLicencji) {
        this.kodLicencji = kodLicencji;
    }


    public void setKey(String key) {
        this.key = key;
    }

    public String  getKey(){
        return key;
    }
}
