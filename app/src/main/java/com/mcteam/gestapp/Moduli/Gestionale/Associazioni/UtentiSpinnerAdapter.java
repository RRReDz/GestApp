package com.mcteam.gestapp.Moduli.Gestionale.Associazioni;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.mcteam.gestapp.Models.UserInfo;
import com.mcteam.gestapp.R;

import java.util.ArrayList;

/**
 * Created by Riccardo Rossi on 28/01/2016.
 */
public class UtentiSpinnerAdapter extends ArrayAdapter<UserInfo> implements SpinnerAdapter {

    private ArrayList<UserInfo> data;
    UserInfo userAttuale = null;
    LayoutInflater inflater;


    public UtentiSpinnerAdapter(Context context, int textViewResourceId, ArrayList<UserInfo> objects) {
        super(context, textViewResourceId, objects);
        data = objects;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    // This funtion called for each row ( Called data.size() times )
    public View getCustomView(int position, View convertView, ViewGroup parent) {


        /***** Get each Model object from Arraylist ********/
        userAttuale = data.get(position);


        /********** Inflate spinner_rows.xml file for each row ( Defined below ) ************/
        convertView = inflater.inflate(R.layout.nominativo_societa_spinner_row, parent, false);

        TextView societaName = (TextView) convertView.findViewById(R.id.nominativo_societa_spinner_name);
        LinearLayout layout = (LinearLayout) convertView.findViewById(R.id.nominativo_societa_spinner);

        if (position == 0) {
            // Default selected Spinner item
            societaName.setText("Scegliere...");
        } else {
            societaName.setText(userAttuale.getCognome() + " " + userAttuale.getNome());

        }

        return convertView;
    }

}
