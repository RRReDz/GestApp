package mcteamgestapp.momo.com.mcteamgestapp.Utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.PopupMenu;

import java.lang.reflect.Field;

/**
 * @author Created by Riccardo Rossi on 10/11/2016.
 */

public class OverflowPopupMenu extends PopupMenu {

    public OverflowPopupMenu(@NonNull Context context, @NonNull View anchor) {
        super(context, anchor);
    }

    public void forceIconToShow() {
        //Force icons to show
        Object menuHelper;
        Class[] argTypes;
        try {
            Field fMenuHelper = android.widget.PopupMenu.class.getDeclaredField("mPopup");
            fMenuHelper.setAccessible(true);
            menuHelper = fMenuHelper.get(this);
            argTypes = new Class[]{boolean.class};
            menuHelper.getClass().getDeclaredMethod("setForceShowIcon", argTypes).invoke(menuHelper, true);

        } catch (Exception e) {
            e.printStackTrace();
            this.show();
            return;
        }
    }

}
