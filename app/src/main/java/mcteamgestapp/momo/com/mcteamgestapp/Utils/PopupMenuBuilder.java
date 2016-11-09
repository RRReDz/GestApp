package mcteamgestapp.momo.com.mcteamgestapp.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import mcteamgestapp.momo.com.mcteamgestapp.R;

/**
 * Classe wrapper per la creazione di un Popup custom
 * quando vengono selezionate le opzioni di un item di una lista.
 * Implementa al suo interno il listener del click di un item del menu
 *
 * @author Created by Riccardo Rossi on 09/11/2016.
 */

public class PopupMenuBuilder implements PopupMenu.OnMenuItemClickListener {

    Context mContext;
    Parcelable mObjectArgument;
    View mAnchorView;
    PopupMenu mPopupmenu;
    Class mDelClass, mEditClass, mPrintClass;
    String mConstIntent, mExtraVisElimStr;
    int mConstActivityResultDel, mConstActivityResultEdit, mConstActivityResultPrint;
    boolean mExtraVisElimBool;

    /**
     * Inizializza il builder e crea il popup
     * @param context contesto di esecuzione
     * @param anchor view nel quale il popup si trova
     * @param argToPass Oggetto (NotaCassa, NotaBanca ecc.) da passare all'activity successiva
     */
    public PopupMenuBuilder(Context context, View anchor, Parcelable argToPass) {
        mObjectArgument = argToPass;
        mContext = context;
        mAnchorView = anchor;
        mPopupmenu = new PopupMenu(context, anchor);
    }

    /**
     * Inflate per assegnare il layout al Popup
     * @param menuRes R.menu.blablabla
     * @return
     */
    public PopupMenuBuilder inflateLayout(int menuRes) {
        //TODO: TUTTI I CONTROLLI DELLA CLASSE 09/11/2016
        mPopupmenu.getMenuInflater()
                .inflate(menuRes, mPopupmenu.getMenu());
        return this;
    }

    /**
     * Assegnazione dei nomi delle activity da chiamare al click di uno degli item nel menu
     * @param delClass EliminaAssoccActity
     * @param editClass ModificaActivity
     * @param printClass StampaActivity
     * @return
     * @throws ClassNotFoundException
     */
    public PopupMenuBuilder setClassesForIntent(String delClass, String editClass, String printClass) throws ClassNotFoundException {
        mDelClass = Class.forName(delClass);
        mEditClass = Class.forName(editClass);
        mPrintClass = Class.forName(printClass);
        return this;
    }

    /**
     * Set della stringa utilizzata solitamente come parametro da passare all'intent
     * @param constIntent
     * @return
     */
    public PopupMenuBuilder setConstForIntent(String constIntent) {
        mConstIntent = constIntent;
        return this;
    }

    /**
     * Set di un parametro extra per l'intent elimina
     * con relativo valore booleano
     * @param extraStr Constants.VISUAL_ELIMINA (Utlizzato da VisualElimBancaActivity)
     * @param extraBoolValue false
     * @return
     */
    public PopupMenuBuilder setExtraParamIntent(String extraStr, boolean extraBoolValue) {
        mExtraVisElimStr = extraStr;
        mExtraVisElimBool = extraBoolValue;
        return this;
    }

    /**
     * Set costanti (intere) per metodo startActivityForResult
     * @param constActivityResultDel
     * @param constActivityResultEdit
     * @param constActivityResultPrint
     * @return
     */
    public PopupMenuBuilder setConstActivityResult(int constActivityResultDel,
                                                   int constActivityResultEdit,
                                                   int constActivityResultPrint) {
        mConstActivityResultDel = constActivityResultDel;
        mConstActivityResultEdit = constActivityResultEdit;
        mConstActivityResultPrint = constActivityResultPrint;
        return this;
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menu_action_elimina:
                Intent eliminaIntent = new Intent(mContext, mDelClass); // VisualElimBancaActivity.class
                eliminaIntent.putExtra(mConstIntent, mObjectArgument);
                if(mExtraVisElimStr != null)
                    eliminaIntent.putExtra(mExtraVisElimStr, mExtraVisElimBool);
                //eliminaIntent.putExtra("actualUser", mUser);
                ((Activity) mContext).startActivityForResult(eliminaIntent, mConstActivityResultDel);
                return true;
            case R.id.menu_action_modifica:
                Intent modificaIntent = new Intent(mContext, mEditClass); //NuovoModifBancaActivity.class
                modificaIntent.putExtra(mConstIntent, mObjectArgument);
                //modificaIntent.putExtra("actualUser", mUser);
                ((Activity) mContext).startActivityForResult(modificaIntent, mConstActivityResultEdit);
                return true;
            case R.id.menu_action_stampa:
                Intent stampaIntent = new Intent(mContext, mPrintClass);
                stampaIntent.putExtra(mConstIntent, mEditClass);
                mContext.startActivity(stampaIntent);
                return true;
            default:
                return false;
        }
    }
}
