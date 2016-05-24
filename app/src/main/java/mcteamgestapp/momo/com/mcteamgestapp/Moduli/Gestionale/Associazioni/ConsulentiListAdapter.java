package mcteamgestapp.momo.com.mcteamgestapp.Moduli.Gestionale.Associazioni;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import mcteamgestapp.momo.com.mcteamgestapp.Models.UserInfo;
import mcteamgestapp.momo.com.mcteamgestapp.Moduli.Sistemi.OverflowOnClickListener;
import mcteamgestapp.momo.com.mcteamgestapp.R;

/**
 * Created by meddaakouri on 02/02/2016.
 */
public class ConsulentiListAdapter extends ArrayAdapter<UserInfo> {

    private ArrayList<UserInfo> mUserList;
    HashMap<String, Integer> mAlphabeticIndex = new HashMap<>();


    public ConsulentiListAdapter(Context context, ArrayList<UserInfo> usersList) {
        super(context, 0, usersList);
        mUserList = usersList;
    }

    public void cleanAlphabeticIndex() {
        mAlphabeticIndex.clear();
    }


    public View getView(int position, View convertView, ViewGroup parent) {

        //get the data from the position
        UserInfo user = getItem(position);

        String nome = user.getCognome();
        String iniziale = nome.substring(0, 1);
        iniziale = iniziale.toUpperCase(Locale.ITALIAN);
        if (!mAlphabeticIndex.containsKey(iniziale))
            mAlphabeticIndex.put(iniziale, position);

        LayoutInflater mInflater = (LayoutInflater) getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);


        convertView = mInflater.inflate(R.layout.consulente_item, null);


        TextView txtCognome = (TextView) convertView.findViewById(R.id.consulente_item_cognome);
        TextView txtNome = (TextView) convertView.findViewById(R.id.consulente_item_nome);
        //TextView txtHeader = (TextView) convertView.findViewById(R.id.sistemi_item_header);

        if (mAlphabeticIndex.containsValue(position)) {
            //  txtHeader.setText(iniziale);
        } else {
            //txtHeader.setVisibility(View.GONE);
        }

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

