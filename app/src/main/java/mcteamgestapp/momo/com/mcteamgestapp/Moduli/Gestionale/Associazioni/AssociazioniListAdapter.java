package mcteamgestapp.momo.com.mcteamgestapp.Moduli.Gestionale.Associazioni;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import mcteamgestapp.momo.com.mcteamgestapp.Models.Associazione;
import mcteamgestapp.momo.com.mcteamgestapp.R;

/**
 * Created by meddaakouri on 27/01/2016.
 */
public class AssociazioniListAdapter extends ArrayAdapter<Associazione> {
    Context mContext = null;
    ArrayList<Associazione> mAssociazioni;
    AssociazioniOverflowListener mOverflow;
    HashMap<String, Integer> mAlphabeticIndex = new HashMap<>();

    public AssociazioniListAdapter(Context context, ArrayList<Associazione> objects) {
        super(context, 0, objects);
        mContext = context;
        mAssociazioni = objects;
    }

    public void cleanAlphaIndex() {
        mAlphabeticIndex.clear();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Associazione associazione = getItem(position);

        String iniziale = "";

        if (associazione.getCommessa().getCliente() != null) {
            String nome = associazione.getCommessa().getCliente().getNomeSocietà();
            iniziale = nome.substring(0, 1);
            iniziale = iniziale.toUpperCase(Locale.ITALIAN);
            if (!mAlphabeticIndex.containsKey(iniziale))
                mAlphabeticIndex.put(iniziale, position);
        } else {
            if (!mAlphabeticIndex.containsKey("?"))
                mAlphabeticIndex.put("?", position);
        }

        convertView = LayoutInflater.from(getContext()).inflate(R.layout.associazione_item, parent, false);

        TextView txtCliente = (TextView) convertView.findViewById(R.id.associazione_cliente);
        TextView txtRisorsa = (TextView) convertView.findViewById(R.id.associazione_risorsa);
        TextView txtCodice = (TextView) convertView.findViewById(R.id.associazione_codice_commessa);
        TextView txtHeader = (TextView) convertView.findViewById(R.id.associazione_header);

        if (associazione.getCommessa() != null && associazione.getCommessa().getCliente() != null) {
            if (associazione.getCommessa().getCliente().getNomeSocietà() != null)
                txtCliente.setText(associazione.getCommessa().getCliente().getNomeSocietà());
            txtCodice.setText(associazione.getCommessa().getNome_commessa());
        }
        if (associazione.getRisorsa() != null) {
            txtRisorsa.setText(associazione.getRisorsa().getCognome() + " " + associazione.getRisorsa().getNome());
        }

        mOverflow = new AssociazioniOverflowListener(associazione, mContext);

        ImageView overflowButton = (ImageView) convertView.findViewById(R.id.associazione_overflow);

        overflowButton.setOnClickListener(mOverflow);

        if (mAlphabeticIndex.containsValue(position)) {
            txtHeader.setText(iniziale);
        } else {
            txtHeader.setVisibility(View.GONE);
        }

        return convertView;
    }
}
