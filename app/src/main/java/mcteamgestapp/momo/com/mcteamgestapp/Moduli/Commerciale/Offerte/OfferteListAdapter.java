package mcteamgestapp.momo.com.mcteamgestapp.Moduli.Commerciale.Offerte;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import mcteamgestapp.momo.com.mcteamgestapp.Models.Commessa;
import mcteamgestapp.momo.com.mcteamgestapp.R;

/**
 * @author Created by Riccardo Rossi on 02/10/2016.
 */
public class OfferteListAdapter extends RecyclerView.Adapter<OfferteListAdapter.MyViewHolder> {

    private ArrayList<Commessa> items;
    private OnItemClickListener listener;
    private HashSet<String> mAlphaIndex = new HashSet<>();

    public interface OnItemClickListener {
        void onItemClick(Commessa item);
    }

    public OfferteListAdapter(ArrayList<Commessa> items, OnItemClickListener listener) {
        this.items = items;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.commesse_item,
                parent,
                false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        //System.out.println("Chiamato onBindViewHolder alla posizione: " + position);
        holder.bind(items.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView header, cliente, codCommessa, nomeCommessa;
        private ImageButton overflow;

        public MyViewHolder(View itemView) {
            super(itemView);
            header = (TextView) itemView.findViewById(R.id.commesse_item_header);
            cliente = (TextView) itemView.findViewById(R.id.commesse_item_cliente);
            codCommessa = (TextView) itemView.findViewById(R.id.commesse_item_codice_risorsa);
            nomeCommessa = (TextView) itemView.findViewById(R.id.commesse_item_nome);
            overflow = (ImageButton) itemView.findViewById(R.id.commesse_item_overflow);
        }

        public void bind(final Commessa commessa, final OnItemClickListener listener) {
            String firstLetter = commessa.getNome_commessa().toUpperCase().substring(0, 1);
            if(!mAlphaIndex.contains(firstLetter)) {
                header.setText(firstLetter);
                mAlphaIndex.add(firstLetter);
            }
            else
                header.setVisibility(View.GONE);

            cliente.setText(commessa.getCliente().getNomeSocietà());
            codCommessa.setText(commessa.getCodice_commessa());
            nomeCommessa.setText(commessa.getNome_commessa());
            overflow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(commessa);
                }
            });
        }
    }
}
