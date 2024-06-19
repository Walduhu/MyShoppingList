package de.walduhu.myshoppinglist;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;

public class ShoppingListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_HEADER = 0;
    private static final int VIEW_TYPE_ITEM = 1;
    private static final int VIEW_TYPE_SUM = 2;

    private final ArrayList<ShoppingEntry> shoppingEntryArrayList;
    private int selectedPosition = RecyclerView.NO_POSITION;

    public ShoppingListAdapter(ArrayList<ShoppingEntry> shoppingEntryArrayList) {
        this.shoppingEntryArrayList = shoppingEntryArrayList;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIEW_TYPE_HEADER;
        } else if (position == shoppingEntryArrayList.size() + 1) {
            return VIEW_TYPE_SUM;
        } else {
            return VIEW_TYPE_ITEM;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_HEADER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_header, parent, false);
            return new HeaderViewHolder(view);
        } else if (viewType == VIEW_TYPE_SUM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_sum, parent, false);
            return new SumViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_shoppinglist, parent, false);
            return new ShoppingListViewHolder(view);
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ShoppingListViewHolder) {
            ShoppingEntry se = shoppingEntryArrayList.get(position - 1); // minus 1 für Tabellenkopf
            ShoppingListViewHolder itemHolder = (ShoppingListViewHolder) holder;
            itemHolder.textViewArtikelname.setText(se.getArtikel());
            itemHolder.textViewAnzahl.setText(String.valueOf(se.getAnzahl()));

            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
            currencyFormat.setCurrency(Currency.getInstance("EUR"));
            String formattedPrice = currencyFormat.format(se.getPreis() * se.getAnzahl());
            itemHolder.textViewPreis.setText(formattedPrice);

            itemHolder.textViewDatum.setText(se.getFormattedDate());

            // gewähltes Element markieren
            itemHolder.itemView.setBackgroundColor(selectedPosition == position ? itemHolder.itemView.getContext().getColor(R.color.lightblue) : Color.TRANSPARENT);

            itemHolder.itemView.setOnClickListener(v -> {
                notifyItemChanged(selectedPosition);
                selectedPosition = holder.getAdapterPosition();
                notifyItemChanged(selectedPosition);
            });

        } else if (holder instanceof SumViewHolder) {
            double totalPrice = calculateTotalPrice();
            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
            currencyFormat.setCurrency(Currency.getInstance("EUR"));
            ((SumViewHolder) holder).textViewSum.setText("Summe = " + currencyFormat.format(totalPrice));
        }
    }

    private double calculateTotalPrice() {
        double total = 0;
        for (ShoppingEntry entry : shoppingEntryArrayList) {
            total += entry.getPreis() * entry.getAnzahl();
        }
        return total;
    }

    @Override
    public int getItemCount() {
        return shoppingEntryArrayList.size() + 2; // +1 für Tabellenkopf, +1 für Summe
    }

    public void removeItem() {
        if (selectedPosition != RecyclerView.NO_POSITION) {
            shoppingEntryArrayList.remove(selectedPosition - 1); // -1 für Tabellenkopf
            notifyItemRemoved(selectedPosition);
            notifyItemChanged(shoppingEntryArrayList.size() + 1); // SumViewHolder benachrichtigen
            selectedPosition = RecyclerView.NO_POSITION;
        }
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    public static class ShoppingListViewHolder extends RecyclerView.ViewHolder {
        TextView textViewArtikelname, textViewAnzahl, textViewPreis, textViewDatum;

        public ShoppingListViewHolder(View view) {
            super(view);
            textViewArtikelname = view.findViewById(R.id.textViewArtikelname);
            textViewAnzahl = view.findViewById(R.id.textViewAnzahl);
            textViewPreis = view.findViewById(R.id.textViewPreis);
            textViewDatum = view.findViewById(R.id.textViewKaufdatum);
        }
    }

    public static class HeaderViewHolder extends RecyclerView.ViewHolder {
        public HeaderViewHolder(View view) {
            super(view);
        }
    }

    public static class SumViewHolder extends RecyclerView.ViewHolder {
        TextView textViewSum;

        public SumViewHolder(View view) {
            super(view);
            textViewSum = view.findViewById(R.id.textViewSum);
        }
    }
}
