package com.mcteam.gestapp.Moduli.Amministrazione.PrimaNotaBanca;

import android.content.Context;
import android.view.View;

import com.mcteam.gestapp.Models.PrimaNota.NotaBanca;
import com.mcteam.gestapp.R;
import com.mcteam.gestapp.Utils.Constants;
import com.mcteam.gestapp.Utils.OverflowPopupMenu;
import com.mcteam.gestapp.Utils.PopupListenerBuilder;

/**
 * Created by Riccardo Rossi on 30/05/2016.
 */
public class PrimaNotaBancaOverflowListener implements View.OnClickListener {
    private Context mContext;
    private NotaBanca mElement;
    //private UserInfo mUser;


    /*public PrimaNotaCassaOverflowListener(Nominativo element, Context context, UserInfo user){
        mElement = element;
        mContext = context;
        mUser = user;
    }*/

    public PrimaNotaBancaOverflowListener(Context context, NotaBanca element) {
        mContext = context;
        mElement = element;
    }

    @Override
    public void onClick(View v) {

        //Creating the instance of PopupMenu
        OverflowPopupMenu popupMenu = new OverflowPopupMenu(mContext, v);
        //Inflating the Popup using xml file
        popupMenu.getMenuInflater().inflate(R.menu.overflow_menu_noprint, popupMenu.getMenu());
        //Creating class that extends OnMenuItemClickListener
        PopupListenerBuilder listenerBuilder = null;
        try {
            listenerBuilder = new PopupListenerBuilder(mContext, v, mElement)
                    .setClassesForIntent(
                            "com.mcteam.gestapp.Moduli.Amministrazione.PrimaNotaBanca.VisualElimBancaActivity",
                            "com.mcteam.gestapp.Moduli.Amministrazione.PrimaNotaBanca.NuovoModifBancaActivity",
                            null)
                    .setConstForIntent(Constants.NOTA_BANCA)
                    .setExtraParamIntent(Constants.VISUAL_ELIM_STAMPA, false)
                    .setConstActivityResult(Constants.NOTA_DELETE, Constants.NOTA_EDIT, null);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //registering popup with OnMenuItemClickListener
        popupMenu.setOnMenuItemClickListener(listenerBuilder);
        //Forza le icone a mostrarsi
        popupMenu.forceIconToShow();
        //Mostra il Popup
        popupMenu.show();

        //Creating the instance of PopupMenu
        /*PopupMenu popupMenu = new PopupMenu(mContext, v);

        //Inflating the Popup using xml file
        popupMenu.getMenuInflater()
                .inflate(R.menu.overflow_menu_noprint, popupMenu.getMenu());

        //registering popup with OnMenuItemClickListener
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.menu_action_elimina:
                        Intent eliminaIntent = new Intent(mContext, VisualElimBancaActivity.class);
                        eliminaIntent.putExtra(Constants.NOTA_BANCA, mElement);
                        eliminaIntent.putExtra(Constants.VISUAL_ELIM_STAMPA, false);
                        //eliminaIntent.putExtra("actualUser", mUser);
                        ((Activity) mContext).startActivityForResult(eliminaIntent, Constants.NOTA_DELETE);
                        return true;
                    case R.id.menu_action_modifica:
                        Intent modificaIntent = new Intent(mContext, NuovoModifBancaActivity.class);
                        modificaIntent.putExtra(Constants.NOTA_BANCA, mElement);
                        //modificaIntent.putExtra("actualUser", mUser);
                        ((Activity) mContext).startActivityForResult(modificaIntent, Constants.NOTA_EDIT);
                        return true;
                    default:
                        return false;
                }
            }
        });

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
        popupMenu.show();*/
    }
}
