package com.mcteam.gestapp.Moduli.Sistemi;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;

import com.mcteam.gestapp.Models.UserInfo;
import com.mcteam.gestapp.R;
import com.mcteam.gestapp.Utils.OverflowPopupMenu;

/**
 * Created by Riccardo Rossi on 12/11/2015.
 */
public class OverflowOnClickListener implements View.OnClickListener {

    private UserInfo mElement;
    private Context mContext;


    public OverflowOnClickListener(UserInfo element, Context context) {
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
                        Intent eliminaIntent = new Intent(mContext, EliminaActivity.class);
                        eliminaIntent.putExtra("userToDelete", mElement);
                        mContext.startActivity(eliminaIntent);
                        return true;
                    case R.id.menu_action_modifica:
                        Intent modificaIntent = new Intent(mContext, ModificaUtenteDialog.class);
                        modificaIntent.putExtra("userToModify", mElement);
                        mContext.startActivity(modificaIntent);
                        return true;
                    case R.id.menu_action_stampa:
                        Intent stampaIntent = new Intent(mContext, StampaAccessiActivity.class);
                        stampaIntent.putExtra("userToPrint", mElement);
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
