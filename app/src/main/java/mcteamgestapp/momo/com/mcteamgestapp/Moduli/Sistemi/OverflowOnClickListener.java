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
import mcteamgestapp.momo.com.mcteamgestapp.Moduli.Sistemi.EliminaActivity;
import mcteamgestapp.momo.com.mcteamgestapp.Moduli.Sistemi.ModificaUtenteDialog;
import mcteamgestapp.momo.com.mcteamgestapp.Moduli.Sistemi.StampaAccessiActivity;
import mcteamgestapp.momo.com.mcteamgestapp.R;

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


        PopupMenu popupMenu = new PopupMenu(mContext, v) {

            @Override
            public boolean onMenuItemSelected(MenuBuilder menu, MenuItem item) {
                switch (item.getItemId()) {
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
