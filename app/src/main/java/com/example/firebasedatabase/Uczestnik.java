package com.example.firebasedatabase;

import android.content.Intent;

import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedList;

public class Uczestnik implements Serializable {
    String key;
    String imie,nazwaZwiazku,kodLicencji;


    String punktacjaStr = "empty";
    LinkedList<Integer> punktacja = new LinkedList<>();

    public void setPunktacja(LinkedList<Integer> punktacja){
        this.punktacja = punktacja;
        punktacjaStr = punktacja.toString();
    }

    public String getPunktacjaStr(){
        return punktacjaStr;
    }



    public LinkedList<Integer> getPunkacja(){
        return punktacja;
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
