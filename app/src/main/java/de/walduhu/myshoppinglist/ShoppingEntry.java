package de.walduhu.myshoppinglist;

import android.annotation.SuppressLint;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ShoppingEntry {

    private String artikel;
    private int anzahl;
    private double preis;
    private Calendar datum;

    public ShoppingEntry(String artikelname, int anzahl, double preis, Calendar datum) {
        this.artikel = artikelname;
        this.anzahl = anzahl;
        this.preis = preis;
        this.datum = datum;
    }

    public String getArtikel() {
        return artikel;
    }

    public int getAnzahl() {
        return anzahl;
    }

    public double getPreis() {
        return preis;
    }

    // Methode um Datum zu formatieren
    public String getFormattedDate() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        return sdf.format(datum.getTime());
    }
}
