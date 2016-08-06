package mcteamgestapp.momo.com.mcteamgestapp.Moduli.Gestionale.Allegati;

import android.content.Context;
import android.content.Intent;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;

import java.lang.reflect.Field;

import mcteamgestapp.momo.com.mcteamgestapp.Models.Allegato;
import mcteamgestapp.momo.com.mcteamgestapp.R;
import mcteamgestapp.momo.com.mcteamgestapp.Utils.AndroidUtils;

/**
 * @author
 * Created by meddaakouri on 18/01/2016.
 */
public class AllegatoOverflowOnClickListener implements View.OnClickListener {
    private Allegato mElement;
    private Context mContext;

    public AllegatoOverflowOnClickListener(Allegato allegato, Context context) {
        mElement = allegato;
        mContext = context;
    }

    @Override
    public void onClick(View v) {

        PopupMenu popupMenu = new PopupMenu(mContext, v) {

            @Override
            public boolean onMenuItemSelected(MenuBuilder menu, MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_action_elimina:
                        Intent eliminaIntent = new Intent(mContext, EliminaAllegatoActivity.class);
                        eliminaIntent.putExtra("allegatoToDelete", mElement);
                        mContext.startActivity(eliminaIntent);
                        return true;
                    case R.id.menu_action_download:
                        AndroidUtils.downloadFile(mElement.getFile(), mContext);
                        return true;
                    default:
                        return super.onMenuItemSelected(menu, item);
                }

            }
        };

        popupMenu.inflate(R.menu.menu_allegati);

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
