package com.example.rabin.testapplication;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import java.util.Calendar;
import android.os.Bundle;
import android.widget.DatePicker;

/**
 * Created by rabin on 8/10/16.
 */
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }


    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {

    }
}
