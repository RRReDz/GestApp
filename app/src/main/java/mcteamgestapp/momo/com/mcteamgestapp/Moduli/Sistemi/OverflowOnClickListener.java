package mcteamgestapp.momo.com.mcteamgestapp.Moduli.Sistemi;

import android.content.Context;
import android.content.Intent;
import android.support.v7.view.menu.MenuBuilder;
import android.view.MenuItem;
import android.view.View;
import android.support.v7.widget.PopupMenu;
import android.widget.Toast;

import java.lang.reflect.Field;

import mcteamgestapp.momo.com.mcteamgestapp.Models.UserInfo;
import mcteamgestapp.momo.com.mcteamgestapp.Moduli.Gestionale.Societa.EliminaSocietaActivity;
import mcteamgestapp.momo.com.mcteamgestapp.Moduli.Gestionale.Societa.ModificaSocietaActivity;
import mcteamgestapp.momo.com.mcteamgestapp.Moduli.Gestionale.Societa.StampaSocietaActivity;
import mcteamgestapp.momo.com.mcteamgestapp.Moduli.Sistemi.EliminaActivity;
import mcteamgestapp.momo.com.mcteamgestapp.Moduli.Sistemi.ModificaUtenteDialog;
import mcteamgestapp.momo.com.mcteamgestapp.Moduli.Sistemi.StampaAccessiActivity;
import mcteamgestapp.momo.com.mcteamgestapp.R;
import mcteamgestapp.momo.com.mcteamgestapp.Utils.OverflowPopupMenu;

/**
 * Created by meddaakouri on 12/11/2015.
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
