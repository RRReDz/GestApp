package mcteamgestapp.momo.com.mcteamgestapp.Moduli.Gestionale.Commesse;

import android.content.Context;
import android.content.Intent;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;

import java.lang.reflect.Field;

import mcteamgestapp.momo.com.mcteamgestapp.Models.Commessa;
import mcteamgestapp.momo.com.mcteamgestapp.R;

/**
 * Created by meddaakouri on 22/01/2016.
 */
public class StoricoCommesseOverflowListener implements View.OnClickListener {
    private Commessa mElement;
    private Context mContext;


    public StoricoCommesseOverflowListener(Commessa element, Context context) {
        mElement = element;
        mContext = context;
    }

    @Override
    public void onClick(View v) {

        PopupMenu popupMenu = new PopupMenu(mContext, v) {

            @Override
            public boolean onMenuItemSelected(MenuBuilder menu, MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_action_stampa:
                        Intent stampaIntent = new Intent(mContext, StampaCommessaActivity.class);
                        stampaIntent.putExtra("commessaToPrint", mElement);
                        mContext.startActivity(stampaIntent);
                        return true;
                    default:
                        return super.onMenuItemSelected(menu, item);
                }

            }
        };
        popupMenu.inflate(R.menu.menu_overflow_storico);

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
