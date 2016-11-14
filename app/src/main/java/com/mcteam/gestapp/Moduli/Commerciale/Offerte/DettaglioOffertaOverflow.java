package com.mcteam.gestapp.Moduli.Commerciale.Offerte;

import android.content.Context;
import android.view.View;

import com.mcteam.gestapp.Models.Commerciale.Offerta;
import com.mcteam.gestapp.R;
import com.mcteam.gestapp.Utils.Constants;
import com.mcteam.gestapp.Utils.OverflowPopupMenu;
import com.mcteam.gestapp.Utils.PopupListenerBuilder;

/**
 * @author Created by Riccardo Rossi on 15/10/2016.
 */

public class DettaglioOffertaOverflow implements View.OnClickListener {
    private Offerta mElement;
    private Context mContext;


    public DettaglioOffertaOverflow(Offerta element, Context context) {
        mElement = element;
        mContext = context;
    }

    @Override
    public void onClick(View v) {

        //Creating the instance of PopupMenu
        OverflowPopupMenu popupMenu = new OverflowPopupMenu(mContext, v);
        //Inflating the Popup using xml file
        popupMenu.getMenuInflater().inflate(R.menu.accessi_overflow_menu, popupMenu.getMenu());
        //Creating class that extends OnMenuItemClickListener
        PopupListenerBuilder listenerBuilder = null;
        try {
            listenerBuilder = new PopupListenerBuilder(mContext, v, mElement)
                    .setClassesForIntent(
                            "com.mcteam.gestapp.Moduli.Commerciale.Offerte.EliminaOffertaActivity",
                            "com.mcteam.gestapp.Moduli.Commerciale.Offerte.ModificaOffertaActivity",
                            "com.mcteam.gestapp.Moduli.Commerciale.Offerte.StampaOffertaActivity")
                    .setConstForIntent(Constants.OFFERTA); //Manca la gestione di questa costante lato activity
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //registering popup with OnMenuItemClickListener
        popupMenu.setOnMenuItemClickListener(listenerBuilder);
        //Forza le icone a mostrarsi
        popupMenu.forceIconToShow();
        //Mostra il Popup
        popupMenu.show();

        /*PopupMenu popupMenu = new PopupMenu(mContext, v) {

            @Override
            public boolean onMenuItemSelected(MenuBuilder menu, MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_action_elimina:
                        Intent eliminaIntent = new Intent(mContext, EliminaOffertaActivity.class);
                        eliminaIntent.putExtra("OFFERTA_TO_DELETE", mElement);
                        mContext.startActivity(eliminaIntent);
                        return true;
                    case R.id.menu_action_modifica:
                        Intent modificaIntent = new Intent(mContext, NuovaOffertaActivity.class);
                        modificaIntent.putExtra("OFFERTA_TO_MODIFY", mElement);
                        mContext.startActivity(modificaIntent);
                        return true;
                    case R.id.menu_action_stampa:
                        Intent stampaIntent = new Intent(mContext, StampaOffertaActivity.class);
                        stampaIntent.putExtra("OFFERTA_TO_PRINT", mElement);
                        mContext.startActivity(stampaIntent);
                        return true;
                    default:
                        return super.onMenuItemSelected(menu, item);
                }
            }
        };
        popupMenu.inflate(R.menu.accessi_overflow_menu);

        //Forse icons to show
        Object menuHelper;
        Class[] argTypes;
        try {
            Field fMenuHelper = PopupMenu.class.getDeclaredField("mPopup");
            fMenuHelper.setAccessible(true);
            menuHelper = fMenuHelper.get(popupMenu);
            argTypes = new Class[]{boolean.class};
            menuHelper.getClass().getDeclaredMethod("setForceShowIcon", argTypes).invoke(menuHelper, true);

        } catch (Exception e) {
            e.printStackTrace();
            popupMenu.show();
            return;
        }
        popupMenu.show();*/
    }

}
