package de.walduhu.myshoppinglist;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton saveIB, deleteIB;
    private EditText newProductET;
    private final ArrayList<ShoppingEntry> shoppingEntryArrayList = new ArrayList<>();
    private ShoppingListAdapter shoppingListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // verknüpfen der XML-Elemente mit den Java-Objekten
        saveIB = findViewById(R.id.saveIB);
        saveIB.setOnClickListener(this);

        deleteIB = findViewById(R.id.deleteIB);
        deleteIB.setOnClickListener(this);

        newProductET = findViewById(R.id.newProductET);
        RecyclerView listRV = findViewById(R.id.listRV);

        // Adapter initialisieren und RecyclerView konfigurieren
        shoppingListAdapter = new ShoppingListAdapter(shoppingEntryArrayList);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(getApplicationContext());
        listRV.setLayoutManager(lm);
        listRV.setAdapter(shoppingListAdapter);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onClick(View v) {
        if (v.getId() == saveIB.getId() && !newProductET.getText().toString().isEmpty()) {
            String eingabe = newProductET.getText().toString();
            String[] eingabeWerte = eingabe.split(" ");
            if (eingabeWerte.length == 3) {
                try {
                    String artikel = eingabeWerte[0];
                    int anzahl = Integer.parseInt(eingabeWerte[1]);
                    double preis = Double.parseDouble(eingabeWerte[2].replace(",", "."));
                    Calendar kaufdatum = Calendar.getInstance(); // heutiges Datum

                    // neues ShoppingEntry-Objekt erstellen
                    ShoppingEntry neuerEintrag = new ShoppingEntry(artikel, anzahl, preis, kaufdatum);
                    shoppingEntryArrayList.add(neuerEintrag);

                    // Adapter benachrichtigen
                    shoppingListAdapter.notifyItemInserted(shoppingEntryArrayList.size());
                    shoppingListAdapter.notifyItemChanged(shoppingEntryArrayList.size() + 1);

                    // Eingabefeld leeren
                    newProductET.setText("");

                } catch (NumberFormatException e) {
                    Toast.makeText(getApplicationContext(), "Bitte gültige Werte eingeben", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Bitte gültige Werte eingeben", Toast.LENGTH_SHORT).show();
            }
        } else if (v.getId() == deleteIB.getId()) {
            if (shoppingEntryArrayList.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Einkaufsliste ist leer", Toast.LENGTH_SHORT).show();
            } else if (shoppingListAdapter.getSelectedPosition() == RecyclerView.NO_POSITION) {
                Toast.makeText(getApplicationContext(), "Kein Artikel zum Löschen markiert", Toast.LENGTH_SHORT).show();
            } else {
                shoppingListAdapter.removeItem();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Bitte geben Sie ein Produkt ein", Toast.LENGTH_SHORT).show();
        }
    }

}
