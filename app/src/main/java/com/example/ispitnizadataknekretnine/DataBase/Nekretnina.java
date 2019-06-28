package com.example.ispitnizadataknekretnine.DataBase;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "nekretnina")
public class Nekretnina {

   @DatabaseField(columnName = "id", generatedId = true)
    private int id;
    @DatabaseField(columnName = "naziv")
    private String naziv;
    @DatabaseField(columnName = "opis")
    private String opis;
    @DatabaseField(columnName = "adresa")
    private String adresa;
    @DatabaseField(columnName = "kontakt")
    private int kontakt;
    @DatabaseField(columnName = "kvadratura")
    private int kvadratura;
    @DatabaseField(columnName = "brojSoba")
    private int brojSoba;
    @DatabaseField(columnName = "cena")
    private int cena;
    @DatabaseField(columnName = "slika")
    private String slika;

    public String getSlika() {
        return slika;
    }

    public void setSlika(String slika) {
        this.slika = slika;
    }

    public Nekretnina() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public int getKontakt() {
        return kontakt;
    }

    public void setKontakt(int kontakt) {
        this.kontakt = kontakt;
    }

    public int getKvadratura() {
        return kvadratura;
    }

    public void setKvadratura(int kvadratura) {
        this.kvadratura = kvadratura;
    }

    public int getBrojSoba() {
        return brojSoba;
    }

    public void setBrojSoba(int brojSoba) {
        this.brojSoba = brojSoba;
    }

    public int getCena() {
        return cena;
    }

    public void setCena(int cena) {
        this.cena = cena;
    }

    @Override
    public String toString() {
        return naziv + " " + adresa;
    }



}
