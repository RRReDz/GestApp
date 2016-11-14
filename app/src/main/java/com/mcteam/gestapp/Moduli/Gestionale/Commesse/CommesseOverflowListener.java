package com.mcteam.gestapp.Moduli.Gestionale.Commesse;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;

import com.mcteam.gestapp.Models.Commessa;
import com.mcteam.gestapp.R;
import com.mcteam.gestapp.Utils.OverflowPopupMenu;

/**
 * Created by Riccardo Rossi on 18/12/2015.
 */
public class CommesseOverflowListener implements View.OnClickListener {
    private Commessa mElement;
    private Context mContext;


    public CommesseOverflowListener(Commessa element, Context context) {
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
        popupMenu.setOnMenuItemClickListener(new android.widget.PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.menu_action_elimina:
                        Intent eliminaIntent = new Intent(mContext, EliminaCommessaActivity.class);
                        eliminaIntent.putExtra("commessaToDelete", mElement);
                        mContext.startActivity(eliminaIntent);
                        return true;
                    case R.id.menu_action_modifica:
                        Intent modificaIntent = new Intent(mContext, NuovaCommessaActivity.class);
                        modificaIntent.putExtra("commessaToModify", mElement);
                        mContext.startActivity(modificaIntent);
                        return true;
                    case R.id.menu_action_stampa:
                        Intent stampaIntent = new Intent(mContext, StampaCommessaActivity.class);
                        stampaIntent.putExtra("commessaToPrint", mElement);
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