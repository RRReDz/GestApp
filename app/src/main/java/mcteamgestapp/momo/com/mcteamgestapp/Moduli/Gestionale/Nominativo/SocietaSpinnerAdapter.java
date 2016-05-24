package mcteamgestapp.momo.com.mcteamgestapp.Moduli.Gestionale.Nominativo;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Locale;

import mcteamgestapp.momo.com.mcteamgestapp.Models.Rubrica.Societa;
import mcteamgestapp.momo.com.mcteamgestapp.R;

/**
 * Created by meddaakouri on 14/12/2015.
 */
public class SocietaSpinnerAdapter extends ArrayAdapter<Societa> implements SpinnerAdapter {

    private Context mContext;
    private ArrayList<Societa> data;
    Societa tempValues = null;
    LayoutInflater inflater;


    /*************
     * CustomAdapter Constructor
     *****************/
    public SocietaSpinnerAdapter(Context context, int textViewResourceId, ArrayList<Societa> objects) {
        super(context, textViewResourceId, objects);


        /********** Take passed values **********/
        mContext = context;
        data = objects;

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
        tempValues = data.get(position);


        /********** Inflate spinner_rows.xml file for each row ( Defined below ) ************/
        convertView = inflater.inflate(R.layout.nominativo_societa_spinner_row, parent, false);

        TextView societaName = (TextView) convertView.findViewById(R.id.nominativo_societa_spinner_name);
        LinearLayout layout = (LinearLayout) convertView.findViewById(R.id.nominativo_societa_spinner);

        if (position == 0) {
            // Default selected Spinner item
            societaName.setText("Scegliere...");
        } else {
            societaName.setText(tempValues.getNomeSocietà());

        }

        return convertView;
    }

}
