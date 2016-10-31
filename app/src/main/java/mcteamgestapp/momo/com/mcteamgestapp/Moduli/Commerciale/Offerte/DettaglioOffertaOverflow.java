package mcteamgestapp.momo.com.mcteamgestapp.Moduli.Commerciale.Offerte;

import android.content.Context;
import android.content.Intent;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;

import java.lang.reflect.Field;

import mcteamgestapp.momo.com.mcteamgestapp.Models.Commerciale.Offerta;
import mcteamgestapp.momo.com.mcteamgestapp.R;

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

        PopupMenu popupMenu = new PopupMenu(mContext, v) {

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
        popupMenu.show();
    }

}
