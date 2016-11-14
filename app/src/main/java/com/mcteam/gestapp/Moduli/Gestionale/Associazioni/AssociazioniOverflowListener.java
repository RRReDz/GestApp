package com.mcteam.gestapp.Moduli.Gestionale.Associazioni;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;

import com.mcteam.gestapp.Models.Associazione;
import com.mcteam.gestapp.R;
import com.mcteam.gestapp.Utils.OverflowPopupMenu;

/**
 * Created by Riccardo Rossi on 27/01/2016.
 */
public class AssociazioniOverflowListener implements View.OnClickListener {
    private Associazione mElement;
    private Context mContext;

    public AssociazioniOverflowListener(Associazione associazione, Context context) {
        mElement = associazione;
        mContext = context;
    }

    @Override
    public void onClick(View v) {

        //Creating the instance of PopupMenu
        OverflowPopupMenu popupMenu = new OverflowPopupMenu(mContext, v);
        //Inflating the Popup using xml file
        popupMenu.getMenuInflater().inflate(R.menu.accessi_overflow_menu, popupMenu.getMenu());
        //registering popup with OnMenuItemClickListener
        popupMenu.setOnMenuItemClickListener(new android.widget.PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.menu_action_elimina:
                        Intent eliminaIntent = new Intent(mContext, EliminaAssociazioneActivity.class);
                        eliminaIntent.putExtra("associazioneToDelete", mElement);
                        mContext.startActivity(eliminaIntent);
                        return true;
                    case R.id.menu_action_modifica:
                        Intent modificaIntent = new Intent(mContext, NuovaAssociazioneActivity.class);
                        modificaIntent.putExtra("associazioneToModify", mElement);
                        modificaIntent.putExtra("isModifica", true);
                        mContext.startActivity(modificaIntent);
                        return true;
                    case R.id.menu_action_stampa:
                        Intent stampaIntent = new Intent(mContext, StampaAssociazioneActivity.class);
                        stampaIntent.putExtra("associazioneToPrint", mElement);
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
