package com.mcteam.gestapp.Moduli.Gestionale.Associazioni;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mcteam.gestapp.Models.UserInfo;
import com.mcteam.gestapp.R;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Riccardo Rossi on 02/02/2016.
 */
public class AssociazioneConsulentiAdapter extends ArrayAdapter<UserInfo> {

    private ArrayList<UserInfo> mUserList;


    public AssociazioneConsulentiAdapter(Context context, ArrayList<UserInfo> usersList) {
        super(context, 0, usersList);
        mUserList = usersList;
    }


    public View getView(int position, View convertView, ViewGroup parent) {

        //get the data from the position
        UserInfo user = getItem(position);

        String nome = user.getCognome();
        String iniziale = nome.substring(0, 1);
        iniziale = iniziale.toUpperCase(Locale.ITALIAN);

        LayoutInflater mInflater = (LayoutInflater) getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);


        convertView = mInflater.inflate(R.layout.associazioni_consulenti_item, null);


        TextView txtCognome = (TextView) convertView.findViewById(R.id.associazione_consulente_item_cognome);
        TextView txtNome = (TextView) convertView.findViewById(R.id.associazione_consulente_item_nome);

        if (!user.isAbilitato()) {
            txtCognome.setTextColor(getContext().getResources().getColor(R.color.disable));
            txtNome.setTextColor(getContext().getResources().getColor(R.color.disable));
        } else {
            txtCognome.setTextColor(getContext().getResources().getColor(R.color.black));
            txtNome.setTextColor(getContext().getResources().getColor(R.color.black));
        }


        txtCognome.setText(user.getCognome());
        txtNome.setText(user.getNome());

        return convertView;
    }


}

