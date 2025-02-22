package com.example.application.views.model;

public class Personel {
    private String ad;
    private String soyad;
    private String tc;

    public Personel(String ad, String soyad, String tc) {
        this.ad = ad;
        this.soyad = soyad;
        this.tc = tc;
    }

    public String getAd() { return ad; }  
    public void setAd(String ad) { this.ad = ad; }  

    public String getSoyad() { return soyad; }  
    public void setSoyad(String soyad) { this.soyad = soyad; } 

    public String getTC() { return tc; }  
    public void setTC(String tc) { this.tc = tc; }  
}