package mcteamgestapp.momo.com.mcteamgestapp.Moduli.Sistemi;

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
import mcteamgestapp.momo.com.mcteamgestapp.R;

/**
 * Created by meddaakouri on 11/11/2015.
 */
public class SistemiListAdapter extends ArrayAdapter<UserInfo> {

    private ArrayList<UserInfo> mUserList;
    OverflowOnClickListener mOverflowListener;
    HashMap<String, Integer> mAlphabeticIndex = new HashMap<>();


    public SistemiListAdapter(Context context, ArrayList<UserInfo> usersList) {
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


        convertView = mInflater.inflate(R.layout.sistemi_single_item, null);


        TextView txtTipologia = (TextView) convertView.findViewById(R.id.sistemi_tipologia);
        TextView txtCognome = (TextView) convertView.findViewById(R.id.sistemi_cognome);
        TextView txtNome = (TextView) convertView.findViewById(R.id.sistemi_nome);
        TextView txtHeader = (TextView) convertView.findViewById(R.id.sistemi_item_header);

        if (mAlphabeticIndex.containsValue(position)) {
            txtHeader.setText(iniziale);
        } else {
            txtHeader.setVisibility(View.GONE);
        }

        if (!user.isAbilitato()) {
            txtCognome.setTextColor(getContext().getResources().getColor(R.color.disable));
            txtNome.setTextColor(getContext().getResources().getColor(R.color.disable));
            txtTipologia.setTextColor(getContext().getResources().getColor(R.color.disable));
        } else {
            txtCognome.setTextColor(getContext().getResources().getColor(R.color.black));
            txtNome.setTextColor(getContext().getResources().getColor(R.color.black));
            txtTipologia.setTextColor(getContext().getResources().getColor(R.color.black));
        }

        String tipologiaUser = "";
        tipologiaUser += user.isAmministratore() ? "AMM, " : "";
        tipologiaUser += user.isCommerciale() ? "COMM, " : "";
        tipologiaUser += user.isSistemi() ? "SIS, " : "";
        tipologiaUser += user.isGestionale() ? "GES, " : "";
        tipologiaUser += user.isProduzione() ? "PRO, " : "";
        tipologiaUser += user.isCapoProgetto() ? "CPR, " : "";
        tipologiaUser += user.isConsulente() ? "CONS, " : "";
        tipologiaUser += user.isDirezione() ? "DIR, " : "";
        tipologiaUser += user.isPersonale() ? "PER, " : "";

        txtTipologia.setText(tipologiaUser);
        txtCognome.setText(user.getCognome());
        txtNome.setText(user.getNome());


        //------------------------------------------------------
        // Menu overflow listener
        //------------------------------------------------------
        mOverflowListener = new OverflowOnClickListener(user, getContext());

        ImageButton accessiOverflowMenu = (ImageButton) convertView.findViewById(R.id.accessi_overflow_menu);

        accessiOverflowMenu.setOnClickListener(mOverflowListener);

        return convertView;
    }


}
