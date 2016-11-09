package mcteamgestapp.momo.com.mcteamgestapp.Moduli.Amministrazione.RubricaBanche;

import android.content.Context;
import android.content.Intent;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;

import java.lang.reflect.Field;

import mcteamgestapp.momo.com.mcteamgestapp.Models.Rubrica.Banca;
import mcteamgestapp.momo.com.mcteamgestapp.Moduli.Gestionale.Commesse.EliminaCommessaActivity;
import mcteamgestapp.momo.com.mcteamgestapp.Moduli.Gestionale.Commesse.NuovaCommessaActivity;
import mcteamgestapp.momo.com.mcteamgestapp.Moduli.Gestionale.Commesse.StampaCommessaActivity;
import mcteamgestapp.momo.com.mcteamgestapp.R;

/**
 * Created by meddaakouri on 14/01/2016.
 */
public class BancaOverflowOnClickListener implements View.OnClickListener {
    private Banca mElement;
    private Context mContext;


    public BancaOverflowOnClickListener(Banca element, Context context) {
        mElement = element;
        mContext = context;
    }


    @Override
    public void onClick(View v) {

        /*PopupMenu popupMenu = new PopupMenu(mContext, v) {

            @Override
            public boolean onMenuItemSelected(MenuBuilder menu, MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_action_elimina:
                        Intent eliminaIntent = new Intent(mContext, EliminaBancaActivity.class);
                        eliminaIntent.putExtra("bancaToDelete", mElement);
                        mContext.startActivity(eliminaIntent);
                        return true;
                    case R.id.menu_action_modifica:
                        Intent modificaIntent = new Intent(mContext, NuovaBancaActivity.class);
                        modificaIntent.putExtra("bancaToModify", mElement);
                        mContext.startActivity(modificaIntent);
                        return true;
                    case R.id.menu_action_stampa:
                        Intent stampaIntent = new Intent(mContext, StampaBancaActivity.class);
                        stampaIntent.putExtra("bancaToPrint", mElement);
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
