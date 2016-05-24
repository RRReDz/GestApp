package mcteamgestapp.momo.com.mcteamgestapp.Moduli.Gestionale.Nominativo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;

import java.lang.reflect.Field;

import mcteamgestapp.momo.com.mcteamgestapp.Models.Rubrica.Nominativo;
import mcteamgestapp.momo.com.mcteamgestapp.Models.Rubrica.Societa;
import mcteamgestapp.momo.com.mcteamgestapp.Models.UserInfo;
import mcteamgestapp.momo.com.mcteamgestapp.Moduli.Sistemi.EliminaActivity;
import mcteamgestapp.momo.com.mcteamgestapp.Moduli.Sistemi.ModificaUtenteDialog;
import mcteamgestapp.momo.com.mcteamgestapp.Moduli.Sistemi.StampaAccessiActivity;
import mcteamgestapp.momo.com.mcteamgestapp.R;

/**
 * Created by meddaakouri on 10/12/2015.
 */
public class NominativoOverflowOnClickListener implements View.OnClickListener {
    private Nominativo mElement;
    private Context mContext;
    private UserInfo mUser;


    public NominativoOverflowOnClickListener(Nominativo element, Context context, UserInfo user) {
        mElement = element;
        mContext = context;
        mUser = user;
    }

    @Override
    public void onClick(View v) {

        PopupMenu popupMenu = new PopupMenu(mContext, v) {

            @Override
            public boolean onMenuItemSelected(MenuBuilder menu, MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_action_elimina:
                        Intent eliminaIntent = new Intent(mContext, EliminaNominativoActivity.class);
                        eliminaIntent.putExtra("nominativoToDelete", mElement);
                        eliminaIntent.putExtra("actualUser", mUser);
                        mContext.startActivity(eliminaIntent);
                        return true;
                    case R.id.menu_action_modifica:
                        Intent modificaIntent = new Intent(mContext, ModificaNominativoActivity.class);
                        modificaIntent.putExtra("nominativoToModify", mElement);
                        modificaIntent.putExtra("actualUser", mUser);
                        mContext.startActivity(modificaIntent);
                        return true;
                    case R.id.menu_action_stampa:
                        Intent stampaIntent = new Intent(mContext, StampaNominativoActivity.class);
                        stampaIntent.putExtra("nominativoToPrint", mElement);
                        stampaIntent.putExtra("actualUser", mUser);
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
        popupMenu.show();
    }
}
