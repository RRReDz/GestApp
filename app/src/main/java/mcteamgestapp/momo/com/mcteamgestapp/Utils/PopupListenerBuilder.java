package mcteamgestapp.momo.com.mcteamgestapp.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import mcteamgestapp.momo.com.mcteamgestapp.R;

/**
 * Classe per costruire il listener del click di una opzione del menu di overflow.
 * Realizzata per evitare di riscrivere sempre le stessa porzione con poche modifiche (Ridondante)
 * Implementa al suo interno il listener del click di un item del menu
 *
 * @author Riccardo Rossi on 09/11/2016.
 */

public class PopupListenerBuilder implements PopupMenu.OnMenuItemClickListener {

    private Context mContext;
    private Parcelable mObjectArgument;
    private View mAnchorView;
    private PopupMenu mPopupmenu;
    private Class mDelClass, mEditClass, mPrintClass;
    private String mConstIntent, mExtraVisElimStr;
    private Integer mConstActivityResultDel, mConstActivityResultEdit, mConstActivityResultPrint;
    private boolean mExtraVisElimBool;

    /**
     * Inizializza il builder e crea il popup
     *
     * @param context   contesto di esecuzione
     * @param anchor    view nel quale il popup si trova
     * @param argToPass Oggetto (NotaCassa, NotaBanca ecc.) da passare all'activity successiva
     */
    public PopupListenerBuilder(Context context, View anchor, Parcelable argToPass) {
        mObjectArgument = argToPass;
        mContext = context;
        mAnchorView = anchor;
        mPopupmenu = new PopupMenu(context, anchor);
    }

    /**
     * Inflate per assegnare il layout al Popup
     *
     * @param menuRes R.menu.blablabla
     * @return
     */
    public PopupListenerBuilder inflateLayout(int menuRes) {
        //TODO: TUTTI I CONTROLLI DELLA CLASSE 09/11/2016
        mPopupmenu.getMenuInflater()
                .inflate(menuRes, mPopupmenu.getMenu());
        return this;
    }

    /**
     * Assegnazione dei nomi delle activity da chiamare al click di uno degli item nel menu
     *
     * @param delClass   EliminaAssoccActity
     * @param editClass  ModificaActivity
     * @param printClass StampaActivity
     * @return
     * @throws ClassNotFoundException
     */
    public PopupListenerBuilder setClassesForIntent(@Nullable String delClass, @Nullable String editClass, @Nullable String printClass) throws ClassNotFoundException {
        mDelClass = delClass != null ? Class.forName(delClass) : null;
        mEditClass = editClass != null ? Class.forName(editClass) : null;
        mPrintClass = printClass != null ? Class.forName(printClass) : null;
        return this;
    }

    /**
     * Set della stringa utilizzata solitamente come parametro da passare all'intent
     *
     * @param constIntent
     * @return
     */
    public PopupListenerBuilder setConstForIntent(String constIntent) {
        mConstIntent = constIntent;
        return this;
    }

    /**
     * Set di un parametro extra per l'intent elimina
     * con relativo valore booleano
     *
     * @param extraStr       Constants.VISUAL_ELIMINA (Utlizzato da VisualElimBancaActivity)
     * @param extraBoolValue false
     * @return
     */
    public PopupListenerBuilder setExtraParamIntent(String extraStr, boolean extraBoolValue) {
        mExtraVisElimStr = extraStr;
        mExtraVisElimBool = extraBoolValue;
        return this;
    }

    /**
     * Set costanti (intere) per metodo startActivityForResult
     *
     * @param constActivityResultDel
     * @param constActivityResultEdit
     * @param constActivityResultPrint
     * @return
     */
    public PopupListenerBuilder setConstActivityResult(@Nullable Integer constActivityResultDel,
                                                       @Nullable Integer constActivityResultEdit,
                                                       @Nullable Integer constActivityResultPrint) {
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
                if (mExtraVisElimStr != null)
                    eliminaIntent.putExtra(mExtraVisElimStr, mExtraVisElimBool);
                //eliminaIntent.putExtra("actualUser", mUser);
                if(mConstActivityResultDel != null)
                    ((Activity) mContext).startActivityForResult(eliminaIntent, mConstActivityResultDel);
                else
                    mContext.startActivity(eliminaIntent);
                return true;
            case R.id.menu_action_modifica:
                Intent modificaIntent = new Intent(mContext, mEditClass); //NuovoModifBancaActivity.class
                modificaIntent.putExtra(mConstIntent, mObjectArgument);
                //modificaIntent.putExtra("actualUser", mUser);
                if(mConstActivityResultEdit != null)
                    ((Activity) mContext).startActivityForResult(modificaIntent, mConstActivityResultEdit);
                else
                    mContext.startActivity(modificaIntent);
                return true;
            case R.id.menu_action_stampa:
                Intent stampaIntent = new Intent(mContext, mPrintClass);
                stampaIntent.putExtra(mConstIntent, mEditClass);
                if(mConstActivityResultPrint != null)
                    ((Activity) mContext).startActivityForResult(stampaIntent, mConstActivityResultEdit);
                else
                    mContext.startActivity(stampaIntent);
                return true;
            default:
                return false;
        }
    }

    /*private findClassPackage(String className) {
        ClassPath classpath = new ClassPathFactory().createFromJVM();
        RegExpResourceFilter regExpResourceFilter = new RegExpResourceFilter(".*", ".*\\.class");
        String[] resources = classpath.findResources("", regExpResourceFilter);
    }*/
}
