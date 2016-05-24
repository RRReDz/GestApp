package mcteamgestapp.momo.com.mcteamgestapp.Moduli.Gestionale.Associazioni;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import mcteamgestapp.momo.com.mcteamgestapp.Models.Commessa;
import mcteamgestapp.momo.com.mcteamgestapp.R;

/**
 * Created by meddaakouri on 28/01/2016.
 */
public class CommessaSpinnerAdapter extends ArrayAdapter<Commessa> implements SpinnerAdapter {

    private Context mContext;
    private ArrayList<Commessa> data;
    Commessa commessaAttuale = null;
    LayoutInflater inflater;

    public CommessaSpinnerAdapter(Context context, int textViewResourceId, ArrayList<Commessa> objects) {
        super(context, textViewResourceId, objects);

        mContext = context;
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

    public View getCustomView(int position, View convertView, ViewGroup parent) {

        commessaAttuale = getItem(position);

        convertView = inflater.inflate(R.layout.nominativo_societa_spinner_row, parent, false);

        TextView societaName = (TextView) convertView.findViewById(R.id.nominativo_societa_spinner_name);

        if (position == 0) {
            // Default selected Spinner item
            societaName.setText("Scegliere...");
        } else {
            String nomeCommessa = "";
            nomeCommessa += commessaAttuale.getCodice_commessa() + " - ";
            if (commessaAttuale != null && commessaAttuale.getCliente() != null && commessaAttuale.getCliente().getNomeSocietà() != null) {
                nomeCommessa += commessaAttuale.getCliente().getNomeSocietà() + " - ";
            }
            nomeCommessa += commessaAttuale.getNome_commessa();

            societaName.setText(nomeCommessa);

        }

        return convertView;
    }
}
