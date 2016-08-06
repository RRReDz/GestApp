package mcteamgestapp.momo.com.mcteamgestapp.Moduli.Amministrazione.PrimaNotaCassa;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;

import java.lang.reflect.Field;

import mcteamgestapp.momo.com.mcteamgestapp.Utils.Constants;
import mcteamgestapp.momo.com.mcteamgestapp.Models.PrimaNota.NotaCassa;
import mcteamgestapp.momo.com.mcteamgestapp.R;

/**
 * Created by Rrossi on 17/05/2016.
 */
public class PrimaNotaCassaOverflowListener implements View.OnClickListener {
    private Context mContext;
    private NotaCassa mElement;
    //private UserInfo mUser;


    /*public PrimaNotaCassaOverflowListener(Nominativo element, Context context, UserInfo user){
        mElement = element;
        mContext = context;
        mUser = user;
    }*/

    public PrimaNotaCassaOverflowListener(Context context, NotaCassa element){
        mContext = context;
        mElement = element;
    }

    @Override
    public void onClick(View v) {

        PopupMenu popupMenu = new PopupMenu(mContext, v){

            @Override
            public boolean onMenuItemSelected(MenuBuilder menu, MenuItem item){
                switch (item.getItemId()){
                    case R.id.menu_action_elimina:
                        Intent eliminaIntent = new Intent(mContext, VisualElimCassaActivity.class);
                        eliminaIntent.putExtra(Constants.NOTA_CASSA, mElement);
                        eliminaIntent.putExtra(Constants.VISUAL_ELIMINA, false);
                        //eliminaIntent.putExtra("actualUser", mUser);
                        ((Activity)mContext).startActivityForResult(eliminaIntent, Constants.NOTA_DELETE);
                        return true;
                    case R.id.menu_action_modifica:
                        Intent modificaIntent = new Intent(mContext, NuovoModifCassaActivity.class);
                        modificaIntent.putExtra(Constants.NOTA_CASSA, mElement);
                        //modificaIntent.putExtra("actualUser", mUser);
                        ((Activity)mContext).startActivityForResult(modificaIntent, Constants.NOTA_EDIT);
                        return true;
                    default:
                        return super.onMenuItemSelected(menu, item);
                }

            }
        };
        popupMenu.inflate(R.menu.overflow_menu_noprint);

        //Force icons to show
        Object menuHelper;
        Class[] argTypes;
        try {
            Field fMenuHelper = PopupMenu.class.getDeclaredField("mPopup");
            fMenuHelper.setAccessible(true);
            menuHelper = fMenuHelper.get(popupMenu);
            argTypes = new Class[] {boolean.class};
            menuHelper.getClass().getDeclaredMethod("setForceShowIcon", argTypes).invoke(menuHelper, true);

        } catch (Exception e) {
            e.printStackTrace();
            popupMenu.show();
            return;
        }
        popupMenu.show();
    }
}
