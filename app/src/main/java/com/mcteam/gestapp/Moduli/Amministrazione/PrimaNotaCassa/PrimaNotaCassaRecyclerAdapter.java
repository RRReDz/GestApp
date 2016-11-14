package com.mcteam.gestapp.Moduli.Amministrazione.PrimaNotaCassa;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;


import com.mcteam.gestapp.Models.PrimaNota.NotaCassa;
import com.mcteam.gestapp.R;
import com.mcteam.gestapp.Utils.Functions;

/**
 * Created by Riccardo Rossi on 16/05/2016.
 */

public class PrimaNotaCassaRecyclerAdapter extends RecyclerView.Adapter<PrimaNotaCassaRecyclerAdapter.MyViewHolder> {


    public interface OnItemClickListener {
        void onItemClick(NotaCassa item);
    }

    private final OnItemClickListener listener;
    private final ArrayList<NotaCassa> items;

    public PrimaNotaCassaRecyclerAdapter(ArrayList<NotaCassa> items, OnItemClickListener listener) {
        this.listener = listener;
        this.items = items;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        // TODO: inflate your view and create viewholder, most likely looks like this though
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.recyclerview_cassa_item,
                viewGroup,
                false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, int i) {
        viewHolder.bind(items.get(i), listener);
    }

    @Override
    public int getItemCount() {
        // TODO: return total item count of your views
        return items.size();
    }

    public ArrayList<NotaCassa> getArrayList() {
        return items;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // TODO: whatever views you need to bind
        public TextView dataOperazione, totale, descrizione;
        public ImageButton overflow;

        public MyViewHolder(View v) {
            super(v); // done this way instead of view tagging
            dataOperazione = (TextView) v.findViewById(R.id.cassa_item_data_operazione);
            totale = (TextView) v.findViewById(R.id.cassa_item_data_totale_riga);
            descrizione = (TextView) v.findViewById(R.id.cassa_item_descrizione);
            overflow = (ImageButton) v.findViewById(R.id.cassa_item_overflow);
        }

        public void bind(final NotaCassa item, final OnItemClickListener listener) {

            //System.out.println("ADAPTER RECYCLER VIEW" + item.toString());

            dataOperazione.setText(item.getDataPagamento());
            descrizione.setText(item.getDescrizione());
            float tot = item.getDare() - item.getAvere();
            item.setTotale(tot);
            String price_formatted = Functions.format(Math.abs(tot));
            if (tot > 0) {
                totale.setText("+" + price_formatted + " €");
                totale.setTextColor(Color.BLUE);
            } else if (tot < 0) {
                totale.setText("-" + price_formatted + " €");
                totale.setTextColor(Color.RED);
            } else {
                totale.setText(price_formatted + " €");
                totale.setTextColor(Color.BLACK);
            }

            //Set listener item del recicler view
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });

            PrimaNotaCassaOverflowListener listenerOverflow = new PrimaNotaCassaOverflowListener(itemView.getContext(), item);
            overflow.setOnClickListener(listenerOverflow);

        }
    }
}
