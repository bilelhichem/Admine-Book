package com.example.adminbook.Model;


public class pdfclass {
public String namebook , auteur , descripion ;
public  String urlpdf ;
public  String imgbook ;
public  String category ;
public  String bookid ;

    public pdfclass(String namebook, String auteur, String descripion, String urlpdf, String imgbook, String category, String bookid) {
        this.namebook = namebook;
        this.auteur = auteur;
        this.descripion = descripion;
        this.urlpdf = urlpdf;
        this.imgbook = imgbook;
        this.category = category;
        this.bookid = bookid;
    }

    public pdfclass() {
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

    public String getDescripion() {
        return descripion;
    }

    public void setDescripion(String descripion) {
        this.descripion = descripion;
    }

    public String getUrlpdf() {
        return urlpdf;
    }

    public void setUrlpdf(String urlpdf) {
        this.urlpdf = urlpdf;
    }

    public String getImgbook() {
        return imgbook;
    }

    public void setImgbook(String imgbook) {
        this.imgbook = imgbook;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBookid() {
        return bookid;
    }

    public void setBookid(String bookid) {
        this.bookid = bookid;
    }
}
