package com.example.adminbook.Model;

import android.widget.ImageView;

public class bookmodel {
public String imgbook, namebook , auteur ;
    public  String bookid ;

    public bookmodel(String imgbook, String namebook, String auteur, String bookid) {
        this.imgbook = imgbook;
        this.namebook = namebook;
        this.auteur = auteur;
        this.bookid = bookid;
    }


    public bookmodel() {
    }

    public String getImgbook() {
        return imgbook;
    }

    public void setImgbook(String imgbook) {
        this.imgbook = imgbook;
    }

    public String getNamebook() {
        return namebook;
    }

    public void setNamebook(String namebook) {
        this.namebook = namebook;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public String getBookid() {
        return bookid;
    }

    public void setBookid(String bookid) {
        this.bookid = bookid;
    }
}
