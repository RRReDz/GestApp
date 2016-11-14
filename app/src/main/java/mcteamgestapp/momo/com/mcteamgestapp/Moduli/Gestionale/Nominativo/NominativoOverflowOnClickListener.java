package mcteamgestapp.momo.com.mcteamgestapp.Moduli.Gestionale.Nominativo;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;

import mcteamgestapp.momo.com.mcteamgestapp.Models.Rubrica.Nominativo;
import mcteamgestapp.momo.com.mcteamgestapp.Models.UserInfo;
import mcteamgestapp.momo.com.mcteamgestapp.R;
import mcteamgestapp.momo.com.mcteamgestapp.Utils.OverflowPopupMenu;

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
