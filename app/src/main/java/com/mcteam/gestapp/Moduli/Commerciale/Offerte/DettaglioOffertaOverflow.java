package com.mcteam.gestapp.Moduli.Commerciale.Offerte;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import com.mcteam.gestapp.Models.Commerciale.Offerta;
import com.mcteam.gestapp.Models.Commessa;
import com.mcteam.gestapp.R;
import com.mcteam.gestapp.Utils.Constants;
import com.mcteam.gestapp.Utils.OverflowPopupMenu;

/**
 * @author Created by Riccardo Rossi on 15/10/2016.
 */

public class DettaglioOffertaOverflow implements View.OnClickListener {
    private Commessa mCommessa;
    private Offerta mElement;
    private Context mContext;

    public DettaglioOffertaOverflow(Commessa commessa, Offerta element, Context context) {
        mCommessa = commessa;
        mElement = element;
        mContext = context;
    }

    @Override
    public void onClick(View v) {

        //Creating the instance of PopupMenu
        OverflowPopupMenu popupMenu = new OverflowPopupMenu(mContext, v);
        //Inflating the Popup using xml file
        popupMenu.getMenuInflater().inflate(R.menu.accessi_overflow_menu, popupMenu.getMenu());

        //registering popup with OnMenuItemClickListener
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_action_elimina:
                        Intent eliminaIntent = new Intent(mContext, VisElimPrintOffertaActivity.class);
                        eliminaIntent.putExtra("OFFERTA", mElement);
                        eliminaIntent.putExtra("COMMESSA", mCommessa);
                        eliminaIntent.putExtra(Constants.VISUAL_ELIM_STAMPA, "ELIM");
                        ((Activity) mContext).startActivityForResult(eliminaIntent, Constants.OFFERTA_DEL);
                        return true;
                    case R.id.menu_action_modifica:
                        Intent modificaIntent = new Intent(mContext, NuovaModifOffertaActivity.class);
                        modificaIntent.putExtra("OFFERTA_TO_EDIT", mElement);
                        modificaIntent.putExtra("COMMESSA", mCommessa);
                        modificaIntent.putExtra("NUOVO", false);
                        ((Activity) mContext).startActivityForResult(modificaIntent, Constants.OFFERTA_EDIT);
                        return true;
                    case R.id.menu_action_stampa:
                        Intent stampaIntent = new Intent(mContext, VisElimPrintOffertaActivity.class);
                        stampaIntent.putExtra("OFFERTA", mElement);
                        stampaIntent.putExtra("COMMESSA", mCommessa);
                        stampaIntent.putExtra(Constants.VISUAL_ELIM_STAMPA, "STAMPA");
                        mContext.startActivity(stampaIntent);
                        return true;
                    default:
                        return false;
                }
            }
        });
        //Forza le icone a mostrarsi
        popupMenu.forceIconToShow();
        //Mostra il Popup
        popupMenu.show();
    }
}
