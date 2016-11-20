package com.mcteam.gestapp.Moduli.Gestionale.Allegati;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;

import com.mcteam.gestapp.Models.Allegato;
import com.mcteam.gestapp.R;
import com.mcteam.gestapp.Utils.GuiUtils;
import com.mcteam.gestapp.Utils.OverflowPopupMenu;

/**
 * @author
 * Created by Riccardo Rossi on 17/07/2016.
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

        //Creating the instance of PopupMenu
        OverflowPopupMenu popupMenu = new OverflowPopupMenu(mContext, v);
        //Inflating the Popup using xml file
        popupMenu.getMenuInflater().inflate(R.menu.menu_allegati, popupMenu.getMenu());

        //registering popup with OnMenuItemClickListener
        popupMenu.setOnMenuItemClickListener(new android.widget.PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.menu_action_elimina:
                        Intent eliminaIntent = new Intent(mContext, EliminaAllegatoActivity.class);
                        eliminaIntent.putExtra("allegatoToDelete", mElement);
                        mContext.startActivity(eliminaIntent);
                        return true;
                    case R.id.menu_action_download:
                        GuiUtils.downloadFile(mElement.getFile(), mContext);
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
