package com.example.firebasedatabase;

import java.util.Date;

public class Zawody {
    String key, title, content;

    Kategoria kategoria = Kategoria.P;



    Date date;

    public Zawody() {

        date = new Date();
    }

    public CharSequence getKategoria() {
        return kategoria.toString();
    }

    public void setKategoria(Kategoria kategoria) {
        this.kategoria = kategoria;
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