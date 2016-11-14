package com.mcteam.gestapp.Moduli.Gestionale.Commesse;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.mcteam.gestapp.Models.Rubrica.Nominativo;
import com.mcteam.gestapp.R;

import java.util.ArrayList;

/**
 * Created by Riccardo Rossi on 18/12/2015.
 */
public class NominativoSpinnerAdapter extends ArrayAdapter<Nominativo> implements SpinnerAdapter {

    private Context mContext;
    private ArrayList<Nominativo> data;
    Nominativo tempValues = null;
    LayoutInflater inflater;
    int count;

    /*************
     * CustomAdapter Constructor
     *****************/
    public NominativoSpinnerAdapter(Context context, int textViewResourceId, ArrayList<Nominativo> objects) {
        super(context, textViewResourceId, objects);

        /********** Take passed values **********/
        mContext = context;
        data = objects;
        count = 0;
        /***********  Layout inflator to call external xml layout () **********************/
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
        tempValues = (Nominativo) data.get(position);
        ViewHolder vh = new ViewHolder();

        /********** Inflate spinner_rows.xml file for each row ( Defined below ) ************/
        convertView = inflater.inflate(R.layout.nominativo_societa_spinner_row, parent, false);

        vh.societaName = (TextView) convertView.findViewById(R.id.nominativo_societa_spinner_name);
        vh.layout = (LinearLayout) convertView.findViewById(R.id.nominativo_societa_spinner);

        if (position == 0) {
            // Default selected Spinner item
            vh.societaName.setText("Scegliere...");
        } else {

            vh.societaName.setText(tempValues.getCognome() + " " + tempValues.getNome());

            if (count % 2 != 0) {
                count++;
                vh.layout.setBackgroundColor(Color.rgb(26, 188, 156));
            }
        }

        return convertView;
    }

    class ViewHolder {
        TextView societaName;
        LinearLayout layout;
    }

}
