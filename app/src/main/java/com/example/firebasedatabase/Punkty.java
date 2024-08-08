package com.example.firebasedatabase;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.LinkedList;

public class Punkty {


    //test
    String key;
     LinkedList<Integer> punktacja = new LinkedList<>();
    private int index = 0;

    Punkty(){
        for(int i = 0 ; i < 5;i++){
            punktacja.add(-1);

        }
    }

    public LinkedList<Integer> getPunktacja(){
        return punktacja;
    }
    public Integer getPunkt(){ //TODO:poprawic?\
        if(index >= 10){
            index = 0;
        }
        return punktacja.get(index);
    }

    @NonNull
    public String toString(){
        StringBuilder wynik = new StringBuilder();
        for(Integer punkt: punktacja){
            wynik.append(punkt).append("|");
        }
        return wynik.toString();
    }

    public void setPunktacja(LinkedList punktacja){
        this.punktacja = punktacja;
    }

    public void setPunktacja(Integer punkt,int lokalizacja){
        punktacja.add(lokalizacja,punkt);
    }



}
