package com.example.firebasedatabase;

public class Uczestnik {
    String imie,nazwisko,kodLicencji;

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

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public String getKodLicencji() {
        return kodLicencji;
    }

    public void setKodLicencji(String kodLicencji) {
        this.kodLicencji = kodLicencji;
    }


}
