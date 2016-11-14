package com.mcteam.gestapp.Moduli.Gestionale.Societa;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;

import com.mcteam.gestapp.Models.Rubrica.Societa;
import com.mcteam.gestapp.R;
import com.mcteam.gestapp.Utils.OverflowPopupMenu;

/**
 * Created by Riccardo Rossi on 24/11/2015.
 */
public class SocietaOverflowClickListener implements View.OnClickListener {

    private Societa mElement;
    private Context mContext;


    public SocietaOverflowClickListener(Societa element, Context context) {
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
                        Intent eliminaIntent = new Intent(mContext, EliminaSocietaActivity.class);
                        eliminaIntent.putExtra("societaToDelete", mElement);
                        mContext.startActivity(eliminaIntent);
                        return true;
                    case R.id.menu_action_modifica:
                        Intent modificaIntent = new Intent(mContext, ModificaSocietaActivity.class);
                        modificaIntent.putExtra("societaToModify", mElement);
                        mContext.startActivity(modificaIntent);
                        return true;
                    case R.id.menu_action_stampa:
                        Intent stampaIntent = new Intent(mContext, StampaSocietaActivity.class);
                        stampaIntent.putExtra("societaToPrint", mElement);
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
