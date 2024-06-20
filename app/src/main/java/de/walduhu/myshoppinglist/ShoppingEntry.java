package de.walduhu.myshoppinglist;

import android.annotation.SuppressLint;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ShoppingEntry {

    private final String artikel;
    private final int anzahl;
    private final double preis;
    private final Calendar datum;

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

    public String getDatum() {
        return datum;
    }
}
