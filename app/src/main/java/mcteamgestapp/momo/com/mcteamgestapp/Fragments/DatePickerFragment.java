package mcteamgestapp.momo.com.mcteamgestapp.Fragments;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.DecimalFormat;

/**
 * Created by Riccardo Rossi on 17/07/2016.
 */
@SuppressLint("ValidFragment")
public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    private TextView date;

    public DatePickerFragment(TextView date) {
        this.date = date;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Usa la data selezionata dal form
        String[] dayMonthYear = date.getText().toString().split("-");

        int day = Integer.parseInt(dayMonthYear[0]);
        int month = Integer.parseInt(dayMonthYear[1]);
        int year = Integer.parseInt(dayMonthYear[2]);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month - 1, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        DecimalFormat mFormat = new DecimalFormat("00");

        date.setText(mFormat.format(day) + "-" + mFormat.format(month + 1) + "-" + year);
    }
}
