package mcteamgestapp.momo.com.mcteamgestapp.Moduli.Gestionale.Commesse;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;

import mcteamgestapp.momo.com.mcteamgestapp.Models.Commessa;
import mcteamgestapp.momo.com.mcteamgestapp.R;
import mcteamgestapp.momo.com.mcteamgestapp.Utils.OverflowPopupMenu;

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
        //Creating the instance of PopupMenu
        OverflowPopupMenu popupMenu = new OverflowPopupMenu(mContext, v);
        //Inflating the Popup using xml file
        popupMenu.getMenuInflater().inflate(R.menu.menu_overflow_storico, popupMenu.getMenu());
        //registering popup with OnMenuItemClickListener
        popupMenu.setOnMenuItemClickListener(new android.widget.PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.menu_action_stampa:
                        Intent stampaIntent = new Intent(mContext, StampaCommessaActivity.class);
                        stampaIntent.putExtra("commessaToPrint", mElement);
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
