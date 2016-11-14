package com.mcteam.gestapp.Moduli.Produzione.Consuntivi;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mcteam.gestapp.Models.OrariAttivita;
import com.mcteam.gestapp.R;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Riccardo Rossi on 08/02/2016.
 */
public class ConsuntiviCalendarioAdapter extends ArrayAdapter<OrariAttivita> {
    Context mContext = null;


    public ConsuntiviCalendarioAdapter(Context context, ArrayList<OrariAttivita> giorni) {
        super(context, 0, giorni);
        mContext = context;
    }


    public View getView(int position, View convertView, ViewGroup parent) {

        OrariAttivita attivita = getItem(position);


        ViewHolder holder;
        holder = new ViewHolder();

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.consuntivo_item, parent, false);

            holder.txtGiorno = (TextView) convertView.findViewById(R.id.consuntivo_item_giorno);
            holder.txtNome = (TextView) convertView.findViewById(R.id.consuntivo_item_nome_giorno);
            holder.txtDescrizione = (TextView) convertView.findViewById(R.id.consuntivo_item_descrizione);
            holder.txtSede = (TextView) convertView.findViewById(R.id.consuntivo_item_sede);
            holder.txtDurata = (TextView) convertView.findViewById(R.id.consuntivo_item_durata);
            holder.layout = (LinearLayout) convertView.findViewById(R.id.consuntivo_item_layout);
            holder.txtHalfSede = (TextView) convertView.findViewById(R.id.consuntivo_item_sede_half);
            holder.txtHalfDescizione = (TextView) convertView.findViewById(R.id.consuntivo_item_descrizione_half);
            holder.layoutHalf = (LinearLayout) convertView.findViewById(R.id.consuntivo_item_half_view);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtGiorno.setText("");
        holder.txtNome.setText("");
        holder.txtDescrizione.setText("");
        holder.txtDurata.setText("");
        holder.txtSede.setText("");

        holder.txtHalfDescizione.setText("");
        holder.txtHalfSede.setText("");

        if (attivita.getOtherHalf() != null) {
            OrariAttivita otherHalf = attivita.getOtherHalf();
            holder.layoutHalf.setVisibility(View.VISIBLE);
            holder.txtHalfDescizione.setText(otherHalf.getDescrizione());
            holder.txtHalfSede.setText(otherHalf.getCommessa().getCliente().getNomeSocietà());
        } else {
            holder.layoutHalf.setVisibility(View.GONE);
        }

        if (attivita.getDay_of_week() == Calendar.SATURDAY || attivita.getDay_of_week() == Calendar.SUNDAY || attivita.isFerie()) {
            Drawable weekendBackground = getContext().getResources().getDrawable(R.drawable.consulenti_weekend_background);
            holder.layout.setBackground(weekendBackground);
            holder.txtGiorno.setTextColor(Color.WHITE);
            holder.txtNome.setTextColor(Color.WHITE);
            holder.txtDescrizione.setTextColor(Color.WHITE);
            holder.txtDurata.setTextColor(Color.WHITE);
            holder.txtSede.setTextColor(Color.WHITE);
            holder.txtHalfDescizione.setTextColor(Color.WHITE);
            holder.txtHalfSede.setTextColor(Color.WHITE);
        } else {
            holder.layout.setBackgroundColor(Color.WHITE);
            holder.txtGiorno.setTextColor(Color.GRAY);
            holder.txtNome.setTextColor(Color.BLACK);
            holder.txtDescrizione.setTextColor(Color.GRAY);
            holder.txtDurata.setTextColor(Color.BLACK);
            holder.txtSede.setTextColor(Color.BLACK);
            holder.txtHalfSede.setTextColor(Color.BLACK);
            holder.txtHalfDescizione.setTextColor(Color.GRAY);
        }

        holder.txtGiorno.setText("" + attivita.getDay());
        holder.txtNome.setText(attivita.getDay_name());
        if (attivita.getCommessa() != null) {
            if (attivita.getCommessa().getCliente() != null) {
                holder.txtSede.setText(attivita.getCommessa().getCliente().getNomeSocietà());
            }
        }
        holder.txtDescrizione.setText(attivita.getDescrizione());
        if (attivita.getOtherHalf() == null) {
            holder.txtDurata.setText("" + attivita.getOre_totali());
        } else
            holder.txtDurata.setText("1.0");

        return convertView;
    }

    class ViewHolder {

        TextView txtGiorno = null;
        TextView txtNome = null;
        TextView txtDescrizione = null;
        TextView txtSede = null;
        TextView txtDurata = null;
        LinearLayout layout = null;

        //Half day layout
        TextView txtHalfSede;
        TextView txtHalfDescizione;
        LinearLayout layoutHalf;
    }
}
