package com.example.firebasedatabase;

import java.util.ArrayList;

public class Punkty {

    Integer[] punktacja = new Integer[10];
    int index = 0;

    Punkty(){
        for(Integer punkt: punktacja){
            punkt = -1;
        }
    }

    public Integer[] getPunktacja(){
        return punktacja;
    }
    public Integer getPunkt(){ //TODO:poprawic?\
        if(index >= 10){
            index = 0;
        }
        return punktacja[index];
    }

    public String toString(){
        String wynik = "";
        for(Integer punkt: punktacja){
            wynik += punkt + "|";
        }
        return wynik;
    }

    public void setPunktacja(Integer[] punktacja){
        this.punktacja = punktacja;
    }

    public void setPunktacja(Integer punkt,int lokalizacja){
        punktacja[lokalizacja] = punkt;
    }



}
