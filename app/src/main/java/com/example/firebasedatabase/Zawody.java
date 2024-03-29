package com.example.firebasedatabase;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;

public class Zawody implements Serializable {
    String key;
    String title;
    String content;


    LinkedList<Uczestnik> uczestnikLinkedList = new LinkedList<>();


    String kategoriaString = "P";

    Kategoria kategoria = Kategoria.P;
    Date date;

    public Zawody() {
        date = new Date();
    }

    public Zawody(Zawody zawody){
        this.key=zawody.key;
        this.uczestnikLinkedList = zawody.uczestnikLinkedList;
        this.date = zawody.date;
        this.kategoriaString = zawody.kategoriaString;
        this.kategoria = zawody.kategoria;
        this.title = zawody.title;
        this.content = zawody.content;
    }

    //CRUD UCZESTNIKA
    public void addUczestnik(Uczestnik uczestnik){
        uczestnikLinkedList.add(uczestnik);
    }
    public void removeUczestnik(Uczestnik uczestnik){
        uczestnikLinkedList.remove(uczestnik);
    }
    public Uczestnik getUczestnik(Uczestnik uczestnik){
        int index;
        index=uczestnikLinkedList.indexOf(uczestnik);
        return uczestnikLinkedList.get(index);
    }



    public String getKategoriaString() {
        return kategoriaString;
    }

    public void setKategoriaString(String kategoriaString) {
        this.kategoriaString = kategoriaString;
    }

    public CharSequence getKategoria() {
        return kategoria.toString();
    }

    public void setKategoria(Kategoria kategoria) {
        this.kategoria = kategoria;
    }

    public Kategoria setKategoria(@NonNull String litera) {
        switch (litera) {
            case "P":
                kategoriaString = "P";
                return Kategoria.P;
            case "K":
                kategoriaString = "K";
                return Kategoria.K;
            case "S":
                kategoriaString = "S";
                return Kategoria.S;
            case "D":
                kategoriaString = "D";
                return Kategoria.D;
            case "PK":
                kategoriaString = "PK";
                return Kategoria.PK;
            case "PS":
                kategoriaString = "PS";
                return Kategoria.PS;
            case "PD":
                kategoriaString = "PD";
                return Kategoria.PD;
            case "KS":
                kategoriaString = "KS";
                return Kategoria.KS;
            case "KD":
                kategoriaString = "KD";
                return Kategoria.KD;
            case "SD":
                kategoriaString = "SD";
                return Kategoria.SD;
            case "PKS":
                kategoriaString = "PKS";
                return Kategoria.PKS;
            case "PKD":
                kategoriaString = "PKD";
                return Kategoria.PKD;
            case "PSD":
                kategoriaString = "PSD";
                return Kategoria.PSD;
            case "KSD":
                kategoriaString = "KSD";
                return Kategoria.KSD;
            case "PKSD":
                kategoriaString = "PKSD";
                return Kategoria.PKSD;
            default:
                throw new IllegalArgumentException("Nieznana litera: " + litera);

        }
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getKey() {
        return key;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Kategoria getKategoriaEnum() {
        return Kategoria.valueOf(String.valueOf(kategoria));
    }
}