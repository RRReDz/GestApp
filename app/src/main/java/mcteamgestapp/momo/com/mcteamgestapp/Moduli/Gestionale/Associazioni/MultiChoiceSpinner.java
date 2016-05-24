package mcteamgestapp.momo.com.mcteamgestapp.Moduli.Gestionale.Associazioni;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import mcteamgestapp.momo.com.mcteamgestapp.Models.UserInfo;
import mcteamgestapp.momo.com.mcteamgestapp.R;

/**
 * Created by meddaakouri on 02/02/2016.
 */
public class MultiChoiceSpinner extends Spinner implements DialogInterface.OnMultiChoiceClickListener, DialogInterface.OnCancelListener {
    private List<String> items;
    private boolean[] selected;
    private String defaultText;
    private UtentiSpinnerAdapter mSpinnerAdapter;
    private MultiSpinnerListener listener;
    private List<UserInfo> consulentiSelected;
    private ArrayList<UserInfo> consulenti;

    public MultiChoiceSpinner(Context context) {
        super(context);
    }

    public MultiChoiceSpinner(Context arg0, AttributeSet arg1) {
        super(arg0, arg1);
    }

    public MultiChoiceSpinner(Context arg0, AttributeSet arg1, int arg2) {
        super(arg0, arg1, arg2);
    }

    @Override
    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
        if (isChecked)
            selected[which] = true;
        else
            selected[which] = false;
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        // refresh text on spinner
        StringBuffer spinnerBuffer = new StringBuffer();
        boolean someUnselected = false;
        for (int i = 0; i < items.size(); i++) {
            if (selected[i] == true) {
                spinnerBuffer.append(items.get(i));
                spinnerBuffer.append(", ");
            } else {
                someUnselected = true;
            }
        }
        String spinnerText;
        if (someUnselected) {
            spinnerText = spinnerBuffer.toString();
            if (spinnerText.length() > 2)
                spinnerText = spinnerText.substring(0, spinnerText.length() - 2);
        } else {
            spinnerText = defaultText;
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item,
                new String[]{spinnerText});
        setAdapter(adapter);
        listener.onItemsSelected(selected);
    }

    @Override
    public boolean performClick() {
        /*
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMultiChoiceItems(
                items.toArray(new CharSequence[items.size()]), selected, this);
        builder.setPositiveButton(android.R.string.ok,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        builder.setOnCancelListener(this);
        builder.show();*/
        Intent multipleChooser = new Intent(getContext(), ConsulenteMultiSelectionDialog.class);
        multipleChooser.putParcelableArrayListExtra("consulentiList", consulenti);
        getContext().startActivity(multipleChooser);

        return true;
    }


    public void setItems(List<String> items, ArrayList<UserInfo> users, String allText,
                         MultiSpinnerListener listener) {
        this.items = items;
        this.defaultText = allText;
        this.listener = listener;
        // all selected by default
        selected = new boolean[items.size()];
        for (int i = 0; i < selected.length; i++)
            selected[i] = true;
        // all text on the spinner
        //ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
        //      android.R.layout.simple_spinner_item, new String[] { allText });

        mSpinnerAdapter = new UtentiSpinnerAdapter(getContext(), R.layout.nominativo_societa_spinner_row, users);

        setAdapter(mSpinnerAdapter);
    }

    public void setItems(ArrayList<UserInfo> users, String allText,
                         MultiSpinnerListener listener) {
        this.items = items;
        consulenti = users;
        this.defaultText = allText;
        this.listener = listener;
        // all selected by default
        selected = new boolean[users.size()];
        for (int i = 0; i < selected.length; i++)
            selected[i] = false;
        // all text on the spinner
        //ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
        //      android.R.layout.simple_spinner_item, new String[] { allText });

        mSpinnerAdapter = new UtentiSpinnerAdapter(getContext(), R.layout.nominativo_societa_spinner_row, users);

        setAdapter(mSpinnerAdapter);
    }

    public interface MultiSpinnerListener {
        public void onItemsSelected(boolean[] selected);
    }
}
