package mcteamgestapp.momo.com.mcteamgestapp.Moduli.Commerciale.Offerte;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import mcteamgestapp.momo.com.mcteamgestapp.Models.Commerciale.Offerta;
import mcteamgestapp.momo.com.mcteamgestapp.R;

/**
 * @author Created by Riccardo Rossi on 13/10/2016.
 */

public class DettaglioOffertaAdapter extends RecyclerView.Adapter<DettaglioOffertaAdapter.MyViewHolder> {

    private ArrayList<Offerta> items;
    private OnItemClickListener listener;

    public DettaglioOffertaAdapter(ArrayList<Offerta> items, OnItemClickListener listener) {
        this.items = items;
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(Offerta item);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.offerte_item,
                parent,
                false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.bind(items.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView holderVersione, holderDataOfferta, holderAccettata, holderAllegato;
        private ImageView holderOverflow;

        public MyViewHolder(View itemView) {
            super(itemView);
            holderVersione = (TextView) itemView.findViewById(R.id.offerte_item_versione);
            holderDataOfferta = (TextView) itemView.findViewById(R.id.offerte_item_data);
            holderAccettata = (TextView) itemView.findViewById(R.id.offerte_item_accettata);
            holderAllegato = (TextView) itemView.findViewById(R.id.offerte_item_allegato);
            holderOverflow = (ImageView) itemView.findViewById(R.id.offerte_item_overflow);
        }

        public void bind(final Offerta offerta, final OnItemClickListener listener) {
            holderVersione.setText(String.valueOf(offerta.getVersione()));
            holderDataOfferta.setText(String.valueOf(offerta.getDataOfferta()));
            holderAccettata.setText(String.valueOf(offerta.getAccettata()));
            holderAllegato.setText(String.valueOf(offerta.getAllegato()));
            holderOverflow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(offerta);
                }
            });
        }
    }
}
