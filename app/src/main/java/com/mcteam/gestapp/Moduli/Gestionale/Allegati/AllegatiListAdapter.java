package com.mcteam.gestapp.Moduli.Gestionale.Allegati;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.mcteam.gestapp.Models.Allegato;
import com.mcteam.gestapp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

/**
 * Created by Riccardo Rossi on 17/07/2016.
 */
public class AllegatiListAdapter extends ArrayAdapter<Allegato> {

    ArrayList<Allegato> mAllegato;
    Context mContext;
    HashMap<String, Integer> mAlphabeticIndex = new HashMap<>();
    AllegatoOverflowOnClickListener overflowOnClickListener;


    public AllegatiListAdapter(Context context, ArrayList<Allegato> allegatos) {
        super(context, 0, allegatos);
        mAllegato = allegatos;
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Allegato allegato = getItem(position);

        String nome = allegato.getDescrizione();
        String iniziale = nome.substring(0, 1);
        iniziale = iniziale.toUpperCase(Locale.ITALIAN);
        if (!mAlphabeticIndex.containsKey(iniziale))
            mAlphabeticIndex.put(iniziale, position);


        LayoutInflater mInflater = (LayoutInflater) getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        //if(convertView == null){

        convertView = mInflater.inflate(R.layout.allegati_item, null);

        ImageView imgLogo = (ImageView) convertView.findViewById(R.id.allegato_item_logo);
        ImageButton overflow = (ImageButton) convertView.findViewById(R.id.allegato_item_overflow);
        TextView txtHeader = (TextView) convertView.findViewById(R.id.allegati_item_header);

        TextView txtDescrizione = (TextView) convertView.findViewById(R.id.allegato_item_descrizione);
        TextView txtData = (TextView) convertView.findViewById(R.id.allegato_item_data);
        TextView txtNome = (TextView) convertView.findViewById(R.id.allegato_item_nome);

        txtNome.setText(allegato.getFile());
        txtDescrizione.setText(allegato.getDescrizione());
        txtData.setText(allegato.getUpload());

        overflowOnClickListener = new AllegatoOverflowOnClickListener(allegato, getContext());

        overflow.setOnClickListener(overflowOnClickListener);

        Bitmap extLogo = AllegatiUtils.getAllegatoLogo(mContext.getResources(), allegato.getFile());


        imgLogo.setImageBitmap(extLogo);

        if (mAlphabeticIndex.containsValue(position)) {
            txtHeader.setText(iniziale);
        } else {
            txtHeader.setVisibility(View.GONE);
        }

        return convertView;
    }

    public void cleanAlphabeticIndex() {
        mAlphabeticIndex.clear();
    }


}
